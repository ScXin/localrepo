/*******************************************************************************
 * Copyright (c) 2015-2016 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.csstudio.display.builder.representation.test;

import org.csstudio.display.builder.representation.javafx.MacrosDialog;
import org.phoebus.framework.macros.Macros;

import javafx.application.Application;
import javafx.stage.Stage;

/** Demo of {@link MacrosDialog}
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class JFXMacrosDialogDemo extends Application
{
    public static void main(final String[] args)
    {
        launch(args);
    }

    @Override
    public void start(final Stage stage)
    {
        final Macros macros = new Macros();
        macros.add("S", "Test");
        macros.add("N", "17");
        final MacrosDialog dialog = new MacrosDialog(macros, null);
        System.out.println(dialog.showAndWait());
    }
}
