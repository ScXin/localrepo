/*******************************************************************************
 * Copyright (c) 2017 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.phoebus.framework.nls;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** JUnit test of the {@link NLS} message initialization
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class NLSMessagesTest
{
    // A 'Messages' type of class needs public static String member variables
    public static String Hello;
    public static String Bye;

    private Locale original;

    // They are initialized from a "messages*.properties" file in the same package,
    // typically using 'static' code like this:
    //
    // static
    // {
    //     NLS.initializeMessages(NLSMessagesTest.class);
    // }
    //
    // For the test, we call NLS.initializeMessages with various locates
    // in a non-static way

    @Before
    public void saveLocale()
    {
        original = Locale.getDefault();
    }

    /** Check if the expected messages were read */
    @Test
    public void testDefaultMessages()
    {
        Locale.setDefault(Locale.US);
        NLS.initializeMessages(NLSMessagesTest.class);
        System.out.println("Messages for '" + Locale.getDefault().getLanguage() + "': " + Hello + ", " + Bye);
        assertThat(Hello, equalTo("Hello"));
        assertThat(Bye, equalTo("Bye"));
    }

    /** Check if the expected messages were read */
    @Test
    public void testGermanMessages()
    {
        Locale.setDefault(Locale.GERMANY);
        NLS.initializeMessages(NLSMessagesTest.class);
        System.out.println("Messages for '" + Locale.getDefault().getLanguage() + "': " + Hello + ", " + Bye);
        assertThat(Hello, equalTo("Moin"));
        assertThat(Bye, equalTo("Tschüss"));
    }

    @After
    public void restoreLocale()
    {
        Locale.setDefault(original);
    }
}
