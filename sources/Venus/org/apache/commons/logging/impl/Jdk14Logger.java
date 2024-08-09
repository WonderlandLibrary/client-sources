/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.logging.impl;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;

public class Jdk14Logger
implements Log,
Serializable {
    private static final long serialVersionUID = 4784713551416303804L;
    protected static final Level dummyLevel = Level.FINE;
    protected transient Logger logger = null;
    protected String name = null;

    public Jdk14Logger(String string) {
        this.name = string;
        this.logger = this.getLogger();
    }

    protected void log(Level level, String string, Throwable throwable) {
        Logger logger = this.getLogger();
        if (logger.isLoggable(level)) {
            Throwable throwable2 = new Throwable();
            StackTraceElement[] stackTraceElementArray = throwable2.getStackTrace();
            String string2 = this.name;
            String string3 = "unknown";
            if (stackTraceElementArray != null && stackTraceElementArray.length > 2) {
                StackTraceElement stackTraceElement = stackTraceElementArray[0];
                string3 = stackTraceElement.getMethodName();
            }
            if (throwable == null) {
                logger.logp(level, string2, string3, string);
            } else {
                logger.logp(level, string2, string3, string, throwable);
            }
        }
    }

    public void debug(Object object) {
        this.log(Level.FINE, String.valueOf(object), null);
    }

    public void debug(Object object, Throwable throwable) {
        this.log(Level.FINE, String.valueOf(object), throwable);
    }

    public void error(Object object) {
        this.log(Level.SEVERE, String.valueOf(object), null);
    }

    public void error(Object object, Throwable throwable) {
        this.log(Level.SEVERE, String.valueOf(object), throwable);
    }

    public void fatal(Object object) {
        this.log(Level.SEVERE, String.valueOf(object), null);
    }

    public void fatal(Object object, Throwable throwable) {
        this.log(Level.SEVERE, String.valueOf(object), throwable);
    }

    public Logger getLogger() {
        if (this.logger == null) {
            this.logger = Logger.getLogger(this.name);
        }
        return this.logger;
    }

    public void info(Object object) {
        this.log(Level.INFO, String.valueOf(object), null);
    }

    public void info(Object object, Throwable throwable) {
        this.log(Level.INFO, String.valueOf(object), throwable);
    }

    public boolean isDebugEnabled() {
        return this.getLogger().isLoggable(Level.FINE);
    }

    public boolean isErrorEnabled() {
        return this.getLogger().isLoggable(Level.SEVERE);
    }

    public boolean isFatalEnabled() {
        return this.getLogger().isLoggable(Level.SEVERE);
    }

    public boolean isInfoEnabled() {
        return this.getLogger().isLoggable(Level.INFO);
    }

    public boolean isTraceEnabled() {
        return this.getLogger().isLoggable(Level.FINEST);
    }

    public boolean isWarnEnabled() {
        return this.getLogger().isLoggable(Level.WARNING);
    }

    public void trace(Object object) {
        this.log(Level.FINEST, String.valueOf(object), null);
    }

    public void trace(Object object, Throwable throwable) {
        this.log(Level.FINEST, String.valueOf(object), throwable);
    }

    public void warn(Object object) {
        this.log(Level.WARNING, String.valueOf(object), null);
    }

    public void warn(Object object, Throwable throwable) {
        this.log(Level.WARNING, String.valueOf(object), throwable);
    }
}

