/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl;

import mpp.venusfr.command.Logger;

public class ConsoleLogger
implements Logger {
    @Override
    public void log(String string) {
        System.out.println("message = " + string);
    }
}

