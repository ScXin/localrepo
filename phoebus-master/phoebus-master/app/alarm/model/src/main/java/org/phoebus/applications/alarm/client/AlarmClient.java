/*******************************************************************************
 * Copyright (c) 2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.phoebus.applications.alarm.client;

import static org.phoebus.applications.alarm.AlarmSystem.logger;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.record.TimestampType;
import org.phoebus.applications.alarm.AlarmSystem;
import org.phoebus.applications.alarm.model.AlarmTreeItem;
import org.phoebus.applications.alarm.model.AlarmTreeLeaf;
import org.phoebus.applications.alarm.model.AlarmTreePath;
import org.phoebus.applications.alarm.model.json.JsonModelReader;
import org.phoebus.applications.alarm.model.json.JsonModelWriter;
import org.phoebus.applications.alarm.model.json.JsonTags;
import org.phoebus.util.time.TimestampFormats;

/** Alarm client model
 *
 *  <p>Given an alarm configuration name like "Accelerator",
 *  subscribes to the "Accelerator" topic for configuration updates
 *  and the "AcceleratorState" topic for alarm state updates.
 *
 *  <p>Updates from either topic are merged into an in-memory model
 *  of the complete alarm information,
 *  updating listeners with all changes.
 *
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class AlarmClient
{
    private final String config_topic, command_topic;
    private final CopyOnWriteArrayList<AlarmClientListener> listeners = new CopyOnWriteArrayList<>();
    private final AlarmClientNode root;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicBoolean maintenance_mode = new AtomicBoolean(false);
    private final Consumer<String, String> consumer;
    private final Producer<String, String> producer;
    private final Thread thread;
    private long last_state_update = 0;
    private boolean has_timed_out = false;

    /** @param server Kafka Server host:port
     *  @param config_name Name of alarm tree root
     */
    public AlarmClient(final String server, final String config_name)
    {
        Objects.requireNonNull(server);
        Objects.requireNonNull(config_name);

        config_topic = config_name;
        command_topic = config_name + AlarmSystem.COMMAND_TOPIC_SUFFIX;

        root = new AlarmClientNode(null, config_name);
        final List<String> topics = List.of(config_topic, config_name + AlarmSystem.STATE_TOPIC_SUFFIX);
        consumer = KafkaHelper.connectConsumer(server, topics, topics);
        producer = KafkaHelper.connectProducer(server);

        thread = new Thread(this::run, "AlarmClientModel");
        thread.setDaemon(true);
    }

    /** @param listener Listener to add */
    public void addListener(final AlarmClientListener listener)
    {
        listeners.add(listener);
    }

    /** @param listener Listener to remove */
    public void removeListener(final AlarmClientListener listener)
    {
        if (! listeners.remove(listener))
            throw new IllegalStateException("Unknown listener");
    }

    /** Start client
     *  @see #shutdown()
     */
    public void start()
    {
        thread.start();
    }

    /** @return <code>true</code> if <code>start()</code> had been called */
    public boolean isRunning()
    {
        return thread.isAlive();
    }

    /** @return Root of alarm configuration */
    public AlarmClientNode getRoot()
    {
        return root;
    }

    /** @return Is alarm server in maintenance mode? */
    public boolean isMaintenanceMode()
    {
        return maintenance_mode.get();
    }

    /** @param maintenance Select maintenance mode? */
    public void setMode(final boolean maintenance)
    {
        final String cmd = maintenance ? JsonTags.MAINTENANCE : JsonTags.NORMAL;
        try
        {
            final String json = new String (JsonModelWriter.commandToBytes(cmd));
            final ProducerRecord<String, String> record = new ProducerRecord<>(command_topic, root.getPathName(), json);
            producer.send(record);
        }
        catch (final Exception ex)
        {
            logger.log(Level.WARNING, "Cannot set mode for " + root + " to " + cmd, ex);
        }
    }

    /** Background thread loop that checks for alarm tree updates */
    private void run()
    {
        // Send an initial "no server" notification,
        // to be cleared once we receive data from server.
        checkServerState();
        try
        {
            while (running.get())
            {
                checkUpdates();
                checkServerState();
            }
        }
        catch (final Throwable ex)
        {
            if (running.get())
                logger.log(Level.SEVERE, "Alarm client model error", ex);
            // else: Intended shutdown
        }
        finally
        {
            consumer.close();
            producer.close();
        }
    }

    /** Time spent in checkUpdates() waiting for, well, updates */
    private static final Duration POLL_PERIOD = Duration.ofMillis(100);

    /** Perform one check for updates */
    private void checkUpdates()
    {
        // Check for messages, with timeout.
        // TODO Because of Kafka bug, this will hang if Kafka isn't running.
        // Fixed according to https://issues.apache.org/jira/browse/KAFKA-1894 ,
        // but update to kafka-client 1.1.1 (latest in July 2018) makes no difference.
        final ConsumerRecords<String, String> records = consumer.poll(POLL_PERIOD);
        for (final ConsumerRecord<String, String> record : records)
        {
            final long timestamp = record.timestamp();
            final String path = record.key();
            final String node_config = record.value();

            if (record.timestampType() != TimestampType.CREATE_TIME)
                logger.log(Level.WARNING, "Expect updates with CreateTime, got " + record.timestampType() + ": " + record.timestamp() + " " + path + " = " + node_config);

            logger.log(Level.FINE, () ->
                record.topic() + " @ " +
                TimestampFormats.MILLI_FORMAT.format(Instant.ofEpochMilli(timestamp)) + " " +
                path + " = " + node_config);

            // Messages within a topic are ordered by time,
            // but messages from the 'config' and 'state' topic can be mixed.
            // Deleting an item and adding it back creates these messages:
            //
            // Config, Time  1: PV description="Original description"
            // State , Time  2: PV state=MINOR
            // Config, Time  5: PV null  (deleted)
            // Config, Time 10: PV description="Added back in"
            // State , Time 11: PV state=MINOR
            //
            // We could receive them like this, with State updates combined,
            // i.e. no longer ordered by time:
            //
            // Config, Time  1: PV description="Original description"
            // State , Time  2: PV state=MINOR
            // State , Time 11: PV state=MINOR
            // Config, Time  5: PV null  (deleted)
            // Config, Time 10: PV description="Added back in"
            //
            // As a result, the PV would be deleted and then re-created
            // with an OK state, missing the correct MINOR state.
            //
            // To guard against this, each PV node (leaf) tracks the timestamp
            // of the last _state_ update.
            // When receiving a deletion with an _older_ time stamp, we ignore it:
            //
            // Config, Time  1: PV description="Original description"
            // State , Time  2: PV state=MINOR
            // State , Time 11: PV state=MINOR
            // Config, Time 10: PV description="Added back in"

            try
            {
                if (node_config == null)
                {   // No config -> Delete node
                    // Message may actually come from either config topic,
                    // where some client originally requested the removal,
                    // or the state topic, where running alarm server
                    // replaced the last state update with an empty one.
                    final AlarmTreeItem<?> node = deleteNode(timestamp, path);
                    // If this was a known node, notify listeners
                    if (node != null)
                    {
                        if (node instanceof AlarmTreeLeaf)
                            logger.log(Level.FINE, () -> "Delete " + path);
                        for (final AlarmClientListener listener : listeners)
                            listener.itemRemoved(node);
                    }
                }
                else
                {
                    // Get node_config as JSON map to check for "pv" key
                    final Object json = JsonModelReader.parseJsonText(node_config);
                    AlarmTreeItem<?> node = findNode(path);
                    // Only update listeners if this is a new node or the config changed
                    if (node == null)
                        node = findOrCreateNode(path, JsonModelReader.isLeafConfigOrState(json));
                    final boolean need_update;
                    if (JsonModelReader.isStateUpdate(json))
                    {
                        final boolean maint = JsonModelReader.isMaintenanceMode(json);
                        if (maintenance_mode.getAndSet(maint) != maint)
                            for (final AlarmClientListener listener : listeners)
                                listener.serverModeChanged(maint);

                        need_update = JsonModelReader.updateAlarmState(timestamp, node, json);
                        last_state_update = System.currentTimeMillis();
                    }
                    else
                        need_update = JsonModelReader.updateAlarmItemConfig(node, json);
                    // If there were changes, notify listeners
                    if (need_update)
                    {
                        final AlarmTreeItem<?> changed_node = node;
                        logger.log(Level.FINE, () -> "Update " + path + " to " + changed_node.getState());
                        for (final AlarmClientListener listener : listeners)
                            listener.itemUpdated(changed_node);
                    }
                }
            }
            catch (final Exception ex)
            {
                logger.log(Level.WARNING,
                           "Alarm config update error for path " + path +
                           ", config " + node_config, ex);
            }
        }
    }

    /** Find existing node
     *
     *  @param path Path to node
     *  @return Node, <code>null</code> if model does not contain the node
     *  @throws Exception on error
     */
    private AlarmTreeItem<?> findNode(final String path) throws Exception
    {
        final String[] path_elements = AlarmTreePath.splitPath(path);

        // Start of path must match the alarm tree root
        if (path_elements.length < 1  ||
            !root.getName().equals(path_elements[0]))
            throw new Exception("Invalid path for alarm configuration " + root.getName() + ": " + path);

        // Walk down the path
        AlarmTreeItem<?> node = root;
        for (int i=1; i<path_elements.length; ++i)
        {
            final String name = path_elements[i];
            node = node.getChild(name);
            if (node == null)
                return null;
        }
        return node;
    }

    /** Delete node
     *
     *  <p>It's OK to try delete an unknown node:
     *  The node might have once existed, but was then deleted.
     *  The last entry in the configuration database is then the deletion hint.
     *  A new model that reads this node-to-delete information
     *  thus never knew the node.
     *
     *  @param timestamp Timestamp of the deletion request
     *  @param path Path to node to delete
     *  @return Node that was removed, or <code>null</code> if model never knew that node
     *  @throws Exception on error
     */
    private AlarmTreeItem<?> deleteNode(final long timestamp, final String path) throws Exception
    {
        final AlarmTreeItem<?> node = findNode(path);
        if (node == null)
            return null;

        final long last_update = node instanceof AlarmClientLeaf
            ? ((AlarmClientLeaf) node).getLastUpdateTimestamp()
            : ((AlarmClientNode) node).getLastUpdateTimestamp();
        if (last_update > timestamp)
        {
            logger.log(Level.FINE,
                       "Ignoring deletion of " + node.getPathName() + " at " +
                       TimestampFormats.MILLI_FORMAT.format(Instant.ofEpochMilli(timestamp)) +
                       ". Superseded by more recent state update at " +
                       TimestampFormats.MILLI_FORMAT.format(Instant.ofEpochMilli(last_update))
                      );
            return null;
        }

        // Node is known: Detach it
        node.detachFromParent();
        return node;
    }

    /** Find an existing alarm tree item or create a new one
     *
     *  <p>Informs listener about created nodes,
     *  if necessary one notification for each created node along the path.
     *
     *  @param path Alarm tree path
     *  @param is_leaf Is this the path to a leaf?
     *  @return {@link AlarmTreeItem}
     *  @throws Exception on error
     */
    private AlarmTreeItem<?> findOrCreateNode(final String path, final boolean is_leaf) throws Exception
    {
        final String[] path_elements = AlarmTreePath.splitPath(path);

        // Start of path must match the alarm tree root
        if (path_elements.length < 1  ||
            !root.getName().equals(path_elements[0]))
            throw new Exception("Invalid path for alarm configuration " + root.getName() + ": " + path);

        // Walk down the path
        AlarmClientNode parent = root;
        for (int i=1; i<path_elements.length; ++i)
        {
            final String name = path_elements[i];
            final boolean last = i == path_elements.length-1;
            AlarmTreeItem<?> node = parent.getChild(name);
            // Create missing nodes
            if (node == null)
            {   // Done when creating leaf
                if (last &&  is_leaf)
                {
                    node = new AlarmClientLeaf(parent, name);
                    logger.log(Level.FINE, "Create " + path);
                    for (final AlarmClientListener listener : listeners)
                        listener.itemAdded(node);
                    return node;
                }
                else
                {
                    node = new AlarmClientNode(parent, name);
                    for (final AlarmClientListener listener : listeners)
                        listener.itemAdded(node);
                }
            }
            // Reached desired node?
            if (last)
                return node;
            // Found or created intermediate node; continue walking down the path
            parent = (AlarmClientNode) node;
        }

        // If path_elements.length == 1, loop never ran. Return root == parent
        return parent;
    }

    /** Add a component to the alarm tree
     *  @param path to parent Root or parent component under which to add the component
     *  @param name Name of the new component
     */
    public void addComponent(final String path_name, final String new_name)
    {
        try
        {
            sendNewItemInfo(path_name, new_name, new AlarmClientNode(null, new_name));
        }
        catch (final Exception ex)
        {
            logger.log(Level.WARNING, "Cannot add component " + new_name + " to " + path_name, ex);
        }
    }

    /** Add a component to the alarm tree
     *  @param path to parent Root or parent component under which to add the component
     *  @param name Name of the new component
     */
    public void addPV(final String path_name, final String new_name)
    {
        try
        {
            sendNewItemInfo(path_name, new_name, new AlarmClientLeaf(null, new_name));
        }
        catch (final Exception ex)
        {
            logger.log(Level.WARNING, "Cannot add pv " + new_name + " to " + path_name, ex);
        }
    }

    private void sendNewItemInfo(String path_name, final String new_name, final AlarmTreeItem<?> content) throws Exception
    {
        // Send message about new component.
        // All clients, including this one, will receive and then add the new component.
        final String new_path = AlarmTreePath.makePath(path_name, new_name);
        sendItemConfigurationUpdate(new_path, content);
    }

    /** Send item configuration
     *
     *  <p>All clients, including this one, will update when they receive the message
     *
     *  @aram path Path to the item
     *  @param config A prototype item (path is ignored) that holds the new configuration
     *  @throws Exception on error
     */
    public void sendItemConfigurationUpdate(final String path, final AlarmTreeItem<?> config) throws Exception
    {
        final String json = new String(JsonModelWriter.toJsonBytes(config));
        final ProducerRecord<String, String> record = new ProducerRecord<>(config_topic, path, json);
        producer.send(record);
    }

    /** Remove a component (and sub-items) from alarm tree
     *  @param item Item to remove
     *  @throws Exception on error
     */
    public void removeComponent(final AlarmTreeItem<?> item) throws Exception
    {
        try
        {
        	// Depth first deletion of all child nodes.
        	final List<AlarmTreeItem<?>> children = item.getChildren();
        	for (final AlarmTreeItem<?> child : children)
        		removeComponent(child);

            // Send message about item to remove
            // All clients, including this one, will receive and then remove the item.
            // Remove from configuration

            // Create and send a message identifying who is deleting the node.
            // The id message must arrive before the tombstone.
            final String json = new String(JsonModelWriter.deleteMessageToBytes());
            final ProducerRecord<String, String> id = new ProducerRecord<>(config_topic, item.getPathName(), json);
            producer.send(id);

            final ProducerRecord<String, String> tombstone = new ProducerRecord<>(config_topic, item.getPathName(), null);
            producer.send(tombstone);
        }
        catch (Exception ex)
        {
            throw new Exception("Error deleting " + item.getPathName(), ex);
        }
    }

    /** @param item Item for which to acknowledge alarm
     *  @param acknowledge <code>true</code> to acknowledge, else un-acknowledge
     */
    public void acknowledge(final AlarmTreeItem<?> item, final boolean acknowledge)
    {
        try
        {
            final String cmd = acknowledge ? "acknowledge" : "unacknowledge";
            final String json = new String (JsonModelWriter.commandToBytes(cmd));
            final ProducerRecord<String, String> record = new ProducerRecord<>(command_topic, item.getPathName(), json);
            producer.send(record);
        }
        catch (final Exception ex)
        {
            logger.log(Level.WARNING, "Cannot acknowledge component " + item, ex);
        }
    }

    /** Check if there have been any messages from server */
    private void checkServerState()
    {
        final long now = System.currentTimeMillis();
        if (now - last_state_update  >  AlarmSystem.idle_timeout_ms*3)
        {
            if (! has_timed_out)
            {
                has_timed_out = true;
                for (final AlarmClientListener listener : listeners)
                    listener.serverStateChanged(false);
            }
        }
        else
            if (has_timed_out)
            {
                has_timed_out = false;
                for (final AlarmClientListener listener : listeners)
                    listener.serverStateChanged(true);
            }
    }

    /** Stop client */
    public void shutdown()
    {
        running.set(false);
        consumer.wakeup();
        try
        {
            thread.join(2000);
        }
        catch (final InterruptedException ex)
        {
            logger.log(Level.WARNING, "Alarm client thread doesn't shut down", ex);
        }
        logger.info(thread.getName() + " shut down");

    }
}
