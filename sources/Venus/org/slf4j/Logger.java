/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.CheckReturnValue;
import org.slf4j.spi.DefaultLoggingEventBuilder;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

public interface Logger {
    public static final String ROOT_LOGGER_NAME = "ROOT";

    public String getName();

    default public LoggingEventBuilder makeLoggingEventBuilder(Level level) {
        return new DefaultLoggingEventBuilder(this, level);
    }

    @CheckReturnValue
    default public LoggingEventBuilder atLevel(Level level) {
        if (this.isEnabledForLevel(level)) {
            return this.makeLoggingEventBuilder(level);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    default public boolean isEnabledForLevel(Level level) {
        int n = level.toInt();
        switch (n) {
            case 0: {
                return this.isTraceEnabled();
            }
            case 10: {
                return this.isDebugEnabled();
            }
            case 20: {
                return this.isInfoEnabled();
            }
            case 30: {
                return this.isWarnEnabled();
            }
            case 40: {
                return this.isErrorEnabled();
            }
        }
        throw new IllegalArgumentException("Level [" + (Object)((Object)level) + "] not recognized.");
    }

    public boolean isTraceEnabled();

    public void trace(String var1);

    public void trace(String var1, Object var2);

    public void trace(String var1, Object var2, Object var3);

    public void trace(String var1, Object ... var2);

    public void trace(String var1, Throwable var2);

    public boolean isTraceEnabled(Marker var1);

    @CheckReturnValue
    default public LoggingEventBuilder atTrace() {
        if (this.isTraceEnabled()) {
            return this.makeLoggingEventBuilder(Level.TRACE);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    public void trace(Marker var1, String var2);

    public void trace(Marker var1, String var2, Object var3);

    public void trace(Marker var1, String var2, Object var3, Object var4);

    public void trace(Marker var1, String var2, Object ... var3);

    public void trace(Marker var1, String var2, Throwable var3);

    public boolean isDebugEnabled();

    public void debug(String var1);

    public void debug(String var1, Object var2);

    public void debug(String var1, Object var2, Object var3);

    public void debug(String var1, Object ... var2);

    public void debug(String var1, Throwable var2);

    public boolean isDebugEnabled(Marker var1);

    public void debug(Marker var1, String var2);

    public void debug(Marker var1, String var2, Object var3);

    public void debug(Marker var1, String var2, Object var3, Object var4);

    public void debug(Marker var1, String var2, Object ... var3);

    public void debug(Marker var1, String var2, Throwable var3);

    @CheckReturnValue
    default public LoggingEventBuilder atDebug() {
        if (this.isDebugEnabled()) {
            return this.makeLoggingEventBuilder(Level.DEBUG);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    public boolean isInfoEnabled();

    public void info(String var1);

    public void info(String var1, Object var2);

    public void info(String var1, Object var2, Object var3);

    public void info(String var1, Object ... var2);

    public void info(String var1, Throwable var2);

    public boolean isInfoEnabled(Marker var1);

    public void info(Marker var1, String var2);

    public void info(Marker var1, String var2, Object var3);

    public void info(Marker var1, String var2, Object var3, Object var4);

    public void info(Marker var1, String var2, Object ... var3);

    public void info(Marker var1, String var2, Throwable var3);

    @CheckReturnValue
    default public LoggingEventBuilder atInfo() {
        if (this.isInfoEnabled()) {
            return this.makeLoggingEventBuilder(Level.INFO);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    public boolean isWarnEnabled();

    public void warn(String var1);

    public void warn(String var1, Object var2);

    public void warn(String var1, Object ... var2);

    public void warn(String var1, Object var2, Object var3);

    public void warn(String var1, Throwable var2);

    public boolean isWarnEnabled(Marker var1);

    public void warn(Marker var1, String var2);

    public void warn(Marker var1, String var2, Object var3);

    public void warn(Marker var1, String var2, Object var3, Object var4);

    public void warn(Marker var1, String var2, Object ... var3);

    public void warn(Marker var1, String var2, Throwable var3);

    @CheckReturnValue
    default public LoggingEventBuilder atWarn() {
        if (this.isWarnEnabled()) {
            return this.makeLoggingEventBuilder(Level.WARN);
        }
        return NOPLoggingEventBuilder.singleton();
    }

    public boolean isErrorEnabled();

    public void error(String var1);

    public void error(String var1, Object var2);

    public void error(String var1, Object var2, Object var3);

    public void error(String var1, Object ... var2);

    public void error(String var1, Throwable var2);

    public boolean isErrorEnabled(Marker var1);

    public void error(Marker var1, String var2);

    public void error(Marker var1, String var2, Object var3);

    public void error(Marker var1, String var2, Object var3, Object var4);

    public void error(Marker var1, String var2, Object ... var3);

    public void error(Marker var1, String var2, Throwable var3);

    @CheckReturnValue
    default public LoggingEventBuilder atError() {
        if (this.isErrorEnabled()) {
            return this.makeLoggingEventBuilder(Level.ERROR);
        }
        return NOPLoggingEventBuilder.singleton();
    }
}

