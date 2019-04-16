/*******************************************************************************
 * Copyright (c) 2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.phoebus.applications.filebrowser;

import java.io.File;
import java.util.List;

import org.phoebus.framework.jobs.JobManager;
import org.phoebus.framework.workbench.FileHelper;
import org.phoebus.ui.dialog.DialogHelper;
import org.phoebus.ui.javafx.ImageCache;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

/** Menu item to delete one file or directory
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class DeleteAction extends MenuItem
{
    /** @param node Node used to position confirmation dialog
     *  @param item Item to delete
     */
    public DeleteAction(final Node node, final List<TreeItem<File>> items)
    {
        super(Messages.Delete, ImageCache.getImageView(ImageCache.class, "/icons/delete.png"));

        setOnAction(event ->
        {
            final Alert prompt = new Alert(AlertType.CONFIRMATION);
            prompt.setTitle(Messages.DeletePromptTitle);

            final StringBuilder buf = new StringBuilder();
            for (TreeItem<File> item : items)
            {
                if (buf.length() > 0)
                    buf.append(", ");
                buf.append(item.getValue().getName());
            }

            prompt.setHeaderText(Messages.DeletePromptHeader + buf.toString() + "?");
            DialogHelper.positionDialog(prompt, node, 0, 0);
            if (prompt.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK)
                return;

            JobManager.schedule(Messages.DeleteJobName + buf.toString(), monitor ->
            {
                for (TreeItem<File> item : items)
                {
                    final File file = item.getValue();
                    if (file.isFile())
                        file.delete();
                    else if (file.isDirectory())
                        FileHelper.delete(file);

                    final FileTreeItem parent = (FileTreeItem)item.getParent();
                    Platform.runLater(() ->  parent.forceRefresh());
                }
            });
        });
    }
}
