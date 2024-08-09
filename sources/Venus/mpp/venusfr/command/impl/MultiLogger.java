/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl;

import java.util.List;
import mpp.venusfr.command.Logger;

public class MultiLogger
implements Logger {
    private final List<Logger> loggers;

    @Override
    public void log(String string) {
        for (Logger logger : this.loggers) {
            logger.log(string);
        }
    }

    public MultiLogger(List<Logger> list) {
        this.loggers = list;
    }
}

