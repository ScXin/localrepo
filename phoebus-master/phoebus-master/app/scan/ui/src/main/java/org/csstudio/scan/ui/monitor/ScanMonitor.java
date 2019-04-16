/*******************************************************************************
 * Copyright (c) 2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.scan.ui.monitor;

import static org.csstudio.scan.ScanSystem.logger;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.csstudio.scan.client.ScanInfoModel;
import org.csstudio.scan.client.ScanInfoModelListener;
import org.csstudio.scan.info.ScanInfo;
import org.csstudio.scan.info.ScanServerInfo;
import org.phoebus.framework.persistence.Memento;
import org.phoebus.framework.spi.AppDescriptor;
import org.phoebus.framework.spi.AppInstance;
import org.phoebus.ui.docking.DockItem;
import org.phoebus.ui.docking.DockPane;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

/** Scan monitor application instance (singleton)
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class ScanMonitor implements AppInstance
{
    /** Singleton instance maintained by {@link ScanMonitorApplication} */
    static ScanMonitor INSTANCE = null;

    private ScanMonitorApplication app;
    private final DockItem tab;
    private ScanInfoModel model;
    private ScansTable scans;

    private final ScanInfoModelListener model_listener = new ScanInfoModelListener()
    {
        @Override
        public void scanServerUpdate(final ScanServerInfo server_info)
        {
            Platform.runLater(() -> scans.update(server_info));
        }

        @Override
        public void scanUpdate(final List<ScanInfo> infos)
        {
            Platform.runLater(() -> scans.update(infos));
        }

        @Override
        public void connectionError()
        {
            Platform.runLater(() ->
            {
                scans.update(Collections.emptyList());
                scans.update((ScanServerInfo) null);
            });
        }
    };

    public ScanMonitor(final ScanMonitorApplication app)
    {
        this.app = app;

        tab = new DockItem(this, create());
        tab.addCloseCheck(() ->
        {
            dispose();
            return true;
        });
        tab.addClosedNotification(() -> INSTANCE = null);
        DockPane.getActiveDockPane().addTab(tab);
    }

    @Override
    public AppDescriptor getAppDescriptor()
    {
        return app;
    }

    void raise()
    {
        tab.select();
    }

    private Node create()
    {
        try
        {
            model = ScanInfoModel.getInstance();
            scans = new ScansTable(model.getScanClient());
            model.addListener(model_listener);
            return scans;
        }
        catch (Exception ex)
        {
            logger.log(Level.WARNING, "Cannot create scan monitor", ex);
            return new Label("Cannot create scan monitor");
        }
    }

    @Override
    public void restore(final Memento memento)
    {
        final List<TableColumn<ScanInfoProxy, ?>> columns = scans.getTableColumns();
        // Don't restore width of the last column, the "Error",
        // because its pref.width is bound to a computation from table width
        // and sum of other columns
        for (int i=0; i<columns.size()-1; ++i)
        {
            final TableColumn<?, ?> col = columns.get(i);
            memento.getNumber("COL" + i).ifPresent(wid -> col.setPrefWidth(wid.doubleValue()));
        }
    }

    @Override
    public void save(final Memento memento)
    {
        int i = 0;
        for (TableColumn<?,?> col : scans.getTableColumns())
            memento.setNumber("COL" + i++, col.getWidth());
    }

    private void dispose()
    {
        model.removeListener(model_listener);
        model.release();
        model = null;
    }
}
