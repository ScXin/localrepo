/*******************************************************************************
 * Copyright (c) 2015-2016 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.csstudio.display.builder.representation.test;

import org.csstudio.display.builder.model.properties.Points;
import org.csstudio.display.builder.representation.javafx.PointsDialog;

import javafx.application.Application;
import javafx.stage.Stage;

/** Demo of {@link PointsDialog}
 *  @author Kay Kasemir
 */
public class JFXPointsDialogDemo  extends Application
{
    public static void main(final String[] args)
    {
        launch(args);
    }

    @Override
    public void start(final Stage stage)
    {
        final Points points = new Points();
        points.add(1, 10);
        points.add(2, 20);
        points.add(3, 30);
        final PointsDialog dialog = new PointsDialog(points, null);
        System.out.println(dialog.showAndWait());
    }
}
