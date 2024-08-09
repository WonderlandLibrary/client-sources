/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Level
 *  org.apache.log4j.Logger
 *  org.apache.log4j.Priority
 */
package org.apache.commons.logging.impl;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class Log4JLogger
implements Log,
Serializable {
    private static final long serialVersionUID = 5160705895411730424L;
    private static final String FQCN;
    private volatile transient Logger logger = null;
    private final String name;
    private static final Priority traceLevel;
    static Class class$org$apache$commons$logging$impl$Log4JLogger;
    static Class class$org$apache$log4j$Level;
    static Class class$org$apache$log4j$Priority;

    public Log4JLogger() {
        this.name = null;
    }

    public Log4JLogger(String string) {
        this.name = string;
        this.logger = this.getLogger();
    }

    public Log4JLogger(Logger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Warning - null logger in constructor; possible log4j misconfiguration.");
        }
        this.name = logger.getName();
        this.logger = logger;
    }

    public void trace(Object object) {
        this.getLogger().log(FQCN, traceLevel, object, null);
    }

    public void trace(Object object, Throwable throwable) {
        this.getLogger().log(FQCN, traceLevel, object, throwable);
    }

    public void debug(Object object) {
        this.getLogger().log(FQCN, (Priority)Level.DEBUG, object, null);
    }

    public void debug(Object object, Throwable throwable) {
        this.getLogger().log(FQCN, (Priority)Level.DEBUG, object, throwable);
    }

    public void info(Object object) {
        this.getLogger().log(FQCN, (Priority)Level.INFO, object, null);
    }

    public void info(Object object, Throwable throwable) {
        this.getLogger().log(FQCN, (Priority)Level.INFO, object, throwable);
    }

    public void warn(Object object) {
        this.getLogger().log(FQCN, (Priority)Level.WARN, object, null);
    }

    public void warn(Object object, Throwable throwable) {
        this.getLogger().log(FQCN, (Priority)Level.WARN, object, throwable);
    }

    public void error(Object object) {
        this.getLogger().log(FQCN, (Priority)Level.ERROR, object, null);
    }

    public void error(Object object, Throwable throwable) {
        this.getLogger().log(FQCN, (Priority)Level.ERROR, object, throwable);
    }

    public void fatal(Object object) {
        this.getLogger().log(FQCN, (Priority)Level.FATAL, object, null);
    }

    public void fatal(Object object, Throwable throwable) {
        this.getLogger().log(FQCN, (Priority)Level.FATAL, object, throwable);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Logger getLogger() {
        Logger logger = this.logger;
        if (logger == null) {
            Log4JLogger log4JLogger = this;
            synchronized (log4JLogger) {
                logger = this.logger;
                if (logger == null) {
                    this.logger = logger = Logger.getLogger((String)this.name);
                }
            }
        }
        return logger;
    }

    public boolean isDebugEnabled() {
        return this.getLogger().isDebugEnabled();
    }

    public boolean isErrorEnabled() {
        return this.getLogger().isEnabledFor((Priority)Level.ERROR);
    }

    public boolean isFatalEnabled() {
        return this.getLogger().isEnabledFor((Priority)Level.FATAL);
    }

    public boolean isInfoEnabled() {
        return this.getLogger().isInfoEnabled();
    }

    public boolean isTraceEnabled() {
        return this.getLogger().isEnabledFor(traceLevel);
    }

    public boolean isWarnEnabled() {
        return this.getLogger().isEnabledFor((Priority)Level.WARN);
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static {
        Level level;
        FQCN = (class$org$apache$commons$logging$impl$Log4JLogger == null ? (class$org$apache$commons$logging$impl$Log4JLogger = Log4JLogger.class$("org.apache.commons.logging.impl.Log4JLogger")) : class$org$apache$commons$logging$impl$Log4JLogger).getName();
        if (!(class$org$apache$log4j$Priority == null ? (class$org$apache$log4j$Priority = Log4JLogger.class$("org.apache.log4j.Priority")) : class$org$apache$log4j$Priority).isAssignableFrom(class$org$apache$log4j$Level == null ? (class$org$apache$log4j$Level = Log4JLogger.class$("org.apache.log4j.Level")) : class$org$apache$log4j$Level)) {
            throw new InstantiationError("Log4J 1.2 not available");
        }
        try {
            level = (Priority)(class$org$apache$log4j$Level == null ? (class$org$apache$log4j$Level = Log4JLogger.class$("org.apache.log4j.Level")) : class$org$apache$log4j$Level).getDeclaredField("TRACE").get(null);
        } catch (Exception exception) {
            level = Level.DEBUG;
        }
        traceLevel = level;
    }
}

