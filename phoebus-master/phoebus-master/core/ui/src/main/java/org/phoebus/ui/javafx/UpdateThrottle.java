/*******************************************************************************
 * Copyright (c) 2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.phoebus.ui.javafx;

import static org.phoebus.ui.application.PhoebusApplication.logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import org.phoebus.framework.jobs.NamedThreadFactory;

/** Throttle for updates.
 *
 *  <p>Can be triggered from any thread.
 *  The throttled 'update' will typically
 *  transfer to the UI thread.
 *
 *  <p>Initial trigger will result in update.
 *  Further triggers are suppressed during a 'dormant' period.
 *  At end, accumulated triggers received while dormant result
 *  in another update.
 *
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class UpdateThrottle
{
    public static final ScheduledExecutorService TIMER = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("UpdateThrottle"));

    /** How long to stay dormant after an update */
    private volatile long dormant_ms;

    /** The update to perform */
    private final Runnable update_then_wake;

    /** Are updates currently suppressed? */
    private final AtomicBoolean dormant = new AtomicBoolean();

    /** Scheduled wakeUp call when no longer dormant */
    private ScheduledFuture<?> scheduled_wakeup;

    /** Any pending triggers while dormant */
    private final AtomicBoolean pending_trigger = new AtomicBoolean();

    /** Stop ongoing activity because throttle was disposed? */
    private volatile boolean disposed = false;

    /** Initialize
     *  @param dormant_time How long throttle remains dormant after a trigger
     *  @param unit Units for the dormant period
     *  @param update {@link Runnable} to invoke for triggers
     */
    public UpdateThrottle(final long dormant_time, final TimeUnit unit, final Runnable update)
    {
        setDormantTime(dormant_time, unit);
        this.update_then_wake = () ->
        {   // Perform the update
            try
            {
                // Wait a little to allow more updates to accumulate
                Thread.sleep(20);
                pending_trigger.set(false);
                if (! disposed)
                    update.run();
            }
            catch (InterruptedException ex)
            {
                // Shutdown, don't schedule another wakeup
                return;
            }
            catch (Throwable ex)
            {
                logger.log(Level.WARNING, "Update failed", ex);
            }
            // Schedule wakeup
            if (! disposed)
                scheduled_wakeup = TIMER.schedule(this::wakeUp, dormant_ms, TimeUnit.MILLISECONDS);
        };
    }

    /** Update the dormant time
     *  @param dormant_time How long throttle remains dormant after a trigger
     *  @param unit Units for the dormant period
     */
    public void setDormantTime(final long dormant_time, final TimeUnit unit)
    {
        dormant_ms = unit.toMillis(dormant_time);
    }

    /** Call to request an update.
     *
     *  <p>First call will cause an update.
     *  Follow-up calls are noted but delayed until end of dormant period,
     *  resulting in one(!) follow-up update.
     */
    public void trigger()
    {
        if (disposed)
            return;
        if (dormant.getAndSet(true))
        {   // In dormant period, note additional triggers but don't act
            pending_trigger.set(true);
        }
        else
        {
            // In idle period, react to trigger, but on timer thread
            TIMER.execute(update_then_wake);
        }
    }

    private void wakeUp()
    {   // End dormant period
        dormant.set(false);
        // React on (one or more) pending triggers
        // received while dormant.
        if (pending_trigger.getAndSet(false))
            trigger();
    }

    /** Call to cancel scheduled updates */
    public void dispose()
    {
        disposed = true;
        pending_trigger.set(false);
        if (scheduled_wakeup != null)
            scheduled_wakeup.cancel(false);
    }
}
