/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.velocity.util;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.slf4j.Logger;

public class LoggerWrapper
extends java.util.logging.Logger {
    private final Logger base;

    public LoggerWrapper(Logger logger) {
        super("logger", null);
        this.base = logger;
    }

    @Override
    public void log(LogRecord logRecord) {
        this.log(logRecord.getLevel(), logRecord.getMessage());
    }

    @Override
    public void log(Level level, String string) {
        if (level == Level.FINE) {
            this.base.debug(string);
        } else if (level == Level.WARNING) {
            this.base.warn(string);
        } else if (level == Level.SEVERE) {
            this.base.error(string);
        } else if (level == Level.INFO) {
            this.base.info(string);
        } else {
            this.base.trace(string);
        }
    }

    @Override
    public void log(Level level, String string, Object object) {
        if (level == Level.FINE) {
            this.base.debug(string, object);
        } else if (level == Level.WARNING) {
            this.base.warn(string, object);
        } else if (level == Level.SEVERE) {
            this.base.error(string, object);
        } else if (level == Level.INFO) {
            this.base.info(string, object);
        } else {
            this.base.trace(string, object);
        }
    }

    @Override
    public void log(Level level, String string, Object[] objectArray) {
        this.log(level, MessageFormat.format(string, objectArray));
    }

    @Override
    public void log(Level level, String string, Throwable throwable) {
        if (level == Level.FINE) {
            this.base.debug(string, throwable);
        } else if (level == Level.WARNING) {
            this.base.warn(string, throwable);
        } else if (level == Level.SEVERE) {
            this.base.error(string, throwable);
        } else if (level == Level.INFO) {
            this.base.info(string, throwable);
        } else {
            this.base.trace(string, throwable);
        }
    }
}

