/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.log.Hierarchy
 *  org.apache.log.Logger
 */
package org.apache.commons.logging.impl;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.log.Hierarchy;
import org.apache.log.Logger;

public class LogKitLogger
implements Log,
Serializable {
    private static final long serialVersionUID = 3768538055836059519L;
    protected volatile transient Logger logger = null;
    protected String name = null;

    public LogKitLogger(String string) {
        this.name = string;
        this.logger = this.getLogger();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Logger getLogger() {
        Logger logger = this.logger;
        if (logger == null) {
            LogKitLogger logKitLogger = this;
            synchronized (logKitLogger) {
                logger = this.logger;
                if (logger == null) {
                    this.logger = logger = Hierarchy.getDefaultHierarchy().getLoggerFor(this.name);
                }
            }
        }
        return logger;
    }

    public void trace(Object object) {
        this.debug(object);
    }

    public void trace(Object object, Throwable throwable) {
        this.debug(object, throwable);
    }

    public void debug(Object object) {
        if (object != null) {
            this.getLogger().debug(String.valueOf(object));
        }
    }

    public void debug(Object object, Throwable throwable) {
        if (object != null) {
            this.getLogger().debug(String.valueOf(object), throwable);
        }
    }

    public void info(Object object) {
        if (object != null) {
            this.getLogger().info(String.valueOf(object));
        }
    }

    public void info(Object object, Throwable throwable) {
        if (object != null) {
            this.getLogger().info(String.valueOf(object), throwable);
        }
    }

    public void warn(Object object) {
        if (object != null) {
            this.getLogger().warn(String.valueOf(object));
        }
    }

    public void warn(Object object, Throwable throwable) {
        if (object != null) {
            this.getLogger().warn(String.valueOf(object), throwable);
        }
    }

    public void error(Object object) {
        if (object != null) {
            this.getLogger().error(String.valueOf(object));
        }
    }

    public void error(Object object, Throwable throwable) {
        if (object != null) {
            this.getLogger().error(String.valueOf(object), throwable);
        }
    }

    public void fatal(Object object) {
        if (object != null) {
            this.getLogger().fatalError(String.valueOf(object));
        }
    }

    public void fatal(Object object, Throwable throwable) {
        if (object != null) {
            this.getLogger().fatalError(String.valueOf(object), throwable);
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
}

