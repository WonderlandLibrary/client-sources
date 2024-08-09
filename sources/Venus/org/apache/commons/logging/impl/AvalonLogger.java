/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.avalon.framework.logger.Logger
 */
package org.apache.commons.logging.impl;

import org.apache.avalon.framework.logger.Logger;
import org.apache.commons.logging.Log;

public class AvalonLogger
implements Log {
    private static volatile Logger defaultLogger = null;
    private final transient Logger logger;

    public AvalonLogger(Logger logger) {
        this.logger = logger;
    }

    public AvalonLogger(String string) {
        if (defaultLogger == null) {
            throw new NullPointerException("default logger has to be specified if this constructor is used!");
        }
        this.logger = defaultLogger.getChildLogger(string);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public static void setDefaultLogger(Logger logger) {
        defaultLogger = logger;
    }

    public void debug(Object object, Throwable throwable) {
        if (this.getLogger().isDebugEnabled()) {
            this.getLogger().debug(String.valueOf(object), throwable);
        }
    }

    public void debug(Object object) {
        if (this.getLogger().isDebugEnabled()) {
            this.getLogger().debug(String.valueOf(object));
        }
    }

    public void error(Object object, Throwable throwable) {
        if (this.getLogger().isErrorEnabled()) {
            this.getLogger().error(String.valueOf(object), throwable);
        }
    }

    public void error(Object object) {
        if (this.getLogger().isErrorEnabled()) {
            this.getLogger().error(String.valueOf(object));
        }
    }

    public void fatal(Object object, Throwable throwable) {
        if (this.getLogger().isFatalErrorEnabled()) {
            this.getLogger().fatalError(String.valueOf(object), throwable);
        }
    }

    public void fatal(Object object) {
        if (this.getLogger().isFatalErrorEnabled()) {
            this.getLogger().fatalError(String.valueOf(object));
        }
    }

    public void info(Object object, Throwable throwable) {
        if (this.getLogger().isInfoEnabled()) {
            this.getLogger().info(String.valueOf(object), throwable);
        }
    }

    public void info(Object object) {
        if (this.getLogger().isInfoEnabled()) {
            this.getLogger().info(String.valueOf(object));
        }
    }

    public boolean isDebugEnabled() {
        return this.getLogger().isDebugEnabled();
    }

    public boolean isErrorEnabled() {
        return this.getLogger().isErrorEnabled();
    }

    public boolean isFatalEnabled() {
        return this.getLogger().isFatalErrorEnabled();
    }

    public boolean isInfoEnabled() {
        return this.getLogger().isInfoEnabled();
    }

    public boolean isTraceEnabled() {
        return this.getLogger().isDebugEnabled();
    }

    public boolean isWarnEnabled() {
        return this.getLogger().isWarnEnabled();
    }

    public void trace(Object object, Throwable throwable) {
        if (this.getLogger().isDebugEnabled()) {
            this.getLogger().debug(String.valueOf(object), throwable);
        }
    }

    public void trace(Object object) {
        if (this.getLogger().isDebugEnabled()) {
            this.getLogger().debug(String.valueOf(object));
        }
    }

    public void warn(Object object, Throwable throwable) {
        if (this.getLogger().isWarnEnabled()) {
            this.getLogger().warn(String.valueOf(object), throwable);
        }
    }

    public void warn(Object object) {
        if (this.getLogger().isWarnEnabled()) {
            this.getLogger().warn(String.valueOf(object));
        }
    }
}

