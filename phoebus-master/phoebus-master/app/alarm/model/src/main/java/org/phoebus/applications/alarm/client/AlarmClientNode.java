/*******************************************************************************
 * Copyright (c) 2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.phoebus.applications.alarm.client;

import java.util.concurrent.CopyOnWriteArrayList;

import org.phoebus.applications.alarm.model.AlarmTreeItemWithState;
import org.phoebus.applications.alarm.model.BasicState;
import org.phoebus.applications.alarm.model.SeverityLevel;

/** Node in the body of the alarm tree, i.e. non-leaf
 *  @author Kay Kasemir
 */
public class AlarmClientNode extends AlarmTreeItemWithState<BasicState>
{
    private volatile long timestamp = 0;

    /** Create alarm tree item (non-leaf)
     *  @param parent Parent item, <code>null</code> for root
     *  @param name Name of this item
     */
    public AlarmClientNode(final AlarmClientNode parent, final String name)
    {
        super(parent, name, new CopyOnWriteArrayList<>());
        state = new BasicState(SeverityLevel.OK);
    }

    /** @param timestamp Timestamp for this update (epoch millisec)
     *  @param state State
     *  @return <code>true</code> if this changed the state
     */
    public boolean setState(final long timestamp, final BasicState state)
    {
        this.timestamp = timestamp;
        return setState(state);
    }

    /** @return Timestamp of last state update (epoch millisec) */
    public long getLastUpdateTimestamp()
    {
        return timestamp;
    }
}
