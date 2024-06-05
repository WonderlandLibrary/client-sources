/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package viamcp.utils;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.apache.logging.log4j.Logger;

public class JLoggerToLog4j
extends java.util.logging.Logger {
    private final Logger base;

    public JLoggerToLog4j(Logger logger) {
        super("logger", null);
        this.base = logger;
    }

    @Override
    public void log(LogRecord record) {
        this.log(record.getLevel(), record.getMessage());
    }

    @Override
    public void log(Level level, String msg) {
        if (level == Level.FINE) {
            this.base.debug(msg);
            return;
        }
        if (level == Level.WARNING) {
            this.base.warn(msg);
            return;
        }
        if (level == Level.SEVERE) {
            this.base.error(msg);
            return;
        }
        if (level == Level.INFO) {
            this.base.info(msg);
            return;
        }
        this.base.trace(msg);
    }

    @Override
    public void log(Level level, String msg, Object param1) {
        if (level == Level.FINE) {
            this.base.debug(msg, new Object[]{param1});
            return;
        }
        if (level == Level.WARNING) {
            this.base.warn(msg, new Object[]{param1});
            return;
        }
        if (level == Level.SEVERE) {
            this.base.error(msg, new Object[]{param1});
            return;
        }
        if (level == Level.INFO) {
            this.base.info(msg, new Object[]{param1});
            return;
        }
        this.base.trace(msg, new Object[]{param1});
    }

    @Override
    public void log(Level level, String msg, Object[] params) {
        this.log(level, MessageFormat.format(msg, params));
    }

    @Override
    public void log(Level level, String msg, Throwable params) {
        if (level == Level.FINE) {
            this.base.debug(msg, params);
            return;
        }
        if (level == Level.WARNING) {
            this.base.warn(msg, params);
            return;
        }
        if (level == Level.SEVERE) {
            this.base.error(msg, params);
            return;
        }
        if (level == Level.INFO) {
            this.base.info(msg, params);
            return;
        }
        this.base.trace(msg, params);
    }
}

