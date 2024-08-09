/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.EventRecordingLogger;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.NOPLogger;
import org.slf4j.spi.LoggingEventBuilder;

public class SubstituteLogger
implements Logger {
    private final String name;
    private volatile Logger _delegate;
    private Boolean delegateEventAware;
    private Method logMethodCache;
    private EventRecordingLogger eventRecordingLogger;
    private final Queue<SubstituteLoggingEvent> eventQueue;
    public final boolean createdPostInitialization;

    public SubstituteLogger(String string, Queue<SubstituteLoggingEvent> queue, boolean bl) {
        this.name = string;
        this.eventQueue = queue;
        this.createdPostInitialization = bl;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public LoggingEventBuilder makeLoggingEventBuilder(Level level) {
        return this.delegate().makeLoggingEventBuilder(level);
    }

    @Override
    public LoggingEventBuilder atLevel(Level level) {
        return this.delegate().atLevel(level);
    }

    @Override
    public boolean isEnabledForLevel(Level level) {
        return this.delegate().isEnabledForLevel(level);
    }

    @Override
    public boolean isTraceEnabled() {
        return this.delegate().isTraceEnabled();
    }

    @Override
    public void trace(String string) {
        this.delegate().trace(string);
    }

    @Override
    public void trace(String string, Object object) {
        this.delegate().trace(string, object);
    }

    @Override
    public void trace(String string, Object object, Object object2) {
        this.delegate().trace(string, object, object2);
    }

    @Override
    public void trace(String string, Object ... objectArray) {
        this.delegate().trace(string, objectArray);
    }

    @Override
    public void trace(String string, Throwable throwable) {
        this.delegate().trace(string, throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return this.delegate().isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String string) {
        this.delegate().trace(marker, string);
    }

    @Override
    public void trace(Marker marker, String string, Object object) {
        this.delegate().trace(marker, string, object);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2) {
        this.delegate().trace(marker, string, object, object2);
    }

    @Override
    public void trace(Marker marker, String string, Object ... objectArray) {
        this.delegate().trace(marker, string, objectArray);
    }

    @Override
    public void trace(Marker marker, String string, Throwable throwable) {
        this.delegate().trace(marker, string, throwable);
    }

    @Override
    public LoggingEventBuilder atTrace() {
        return this.delegate().atTrace();
    }

    @Override
    public boolean isDebugEnabled() {
        return this.delegate().isDebugEnabled();
    }

    @Override
    public void debug(String string) {
        this.delegate().debug(string);
    }

    @Override
    public void debug(String string, Object object) {
        this.delegate().debug(string, object);
    }

    @Override
    public void debug(String string, Object object, Object object2) {
        this.delegate().debug(string, object, object2);
    }

    @Override
    public void debug(String string, Object ... objectArray) {
        this.delegate().debug(string, objectArray);
    }

    @Override
    public void debug(String string, Throwable throwable) {
        this.delegate().debug(string, throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return this.delegate().isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String string) {
        this.delegate().debug(marker, string);
    }

    @Override
    public void debug(Marker marker, String string, Object object) {
        this.delegate().debug(marker, string, object);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2) {
        this.delegate().debug(marker, string, object, object2);
    }

    @Override
    public void debug(Marker marker, String string, Object ... objectArray) {
        this.delegate().debug(marker, string, objectArray);
    }

    @Override
    public void debug(Marker marker, String string, Throwable throwable) {
        this.delegate().debug(marker, string, throwable);
    }

    @Override
    public LoggingEventBuilder atDebug() {
        return this.delegate().atDebug();
    }

    @Override
    public boolean isInfoEnabled() {
        return this.delegate().isInfoEnabled();
    }

    @Override
    public void info(String string) {
        this.delegate().info(string);
    }

    @Override
    public void info(String string, Object object) {
        this.delegate().info(string, object);
    }

    @Override
    public void info(String string, Object object, Object object2) {
        this.delegate().info(string, object, object2);
    }

    @Override
    public void info(String string, Object ... objectArray) {
        this.delegate().info(string, objectArray);
    }

    @Override
    public void info(String string, Throwable throwable) {
        this.delegate().info(string, throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return this.delegate().isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String string) {
        this.delegate().info(marker, string);
    }

    @Override
    public void info(Marker marker, String string, Object object) {
        this.delegate().info(marker, string, object);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2) {
        this.delegate().info(marker, string, object, object2);
    }

    @Override
    public void info(Marker marker, String string, Object ... objectArray) {
        this.delegate().info(marker, string, objectArray);
    }

    @Override
    public void info(Marker marker, String string, Throwable throwable) {
        this.delegate().info(marker, string, throwable);
    }

    @Override
    public LoggingEventBuilder atInfo() {
        return this.delegate().atInfo();
    }

    @Override
    public boolean isWarnEnabled() {
        return this.delegate().isWarnEnabled();
    }

    @Override
    public void warn(String string) {
        this.delegate().warn(string);
    }

    @Override
    public void warn(String string, Object object) {
        this.delegate().warn(string, object);
    }

    @Override
    public void warn(String string, Object object, Object object2) {
        this.delegate().warn(string, object, object2);
    }

    @Override
    public void warn(String string, Object ... objectArray) {
        this.delegate().warn(string, objectArray);
    }

    @Override
    public void warn(String string, Throwable throwable) {
        this.delegate().warn(string, throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return this.delegate().isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String string) {
        this.delegate().warn(marker, string);
    }

    @Override
    public void warn(Marker marker, String string, Object object) {
        this.delegate().warn(marker, string, object);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2) {
        this.delegate().warn(marker, string, object, object2);
    }

    @Override
    public void warn(Marker marker, String string, Object ... objectArray) {
        this.delegate().warn(marker, string, objectArray);
    }

    @Override
    public void warn(Marker marker, String string, Throwable throwable) {
        this.delegate().warn(marker, string, throwable);
    }

    @Override
    public LoggingEventBuilder atWarn() {
        return this.delegate().atWarn();
    }

    @Override
    public boolean isErrorEnabled() {
        return this.delegate().isErrorEnabled();
    }

    @Override
    public void error(String string) {
        this.delegate().error(string);
    }

    @Override
    public void error(String string, Object object) {
        this.delegate().error(string, object);
    }

    @Override
    public void error(String string, Object object, Object object2) {
        this.delegate().error(string, object, object2);
    }

    @Override
    public void error(String string, Object ... objectArray) {
        this.delegate().error(string, objectArray);
    }

    @Override
    public void error(String string, Throwable throwable) {
        this.delegate().error(string, throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return this.delegate().isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String string) {
        this.delegate().error(marker, string);
    }

    @Override
    public void error(Marker marker, String string, Object object) {
        this.delegate().error(marker, string, object);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2) {
        this.delegate().error(marker, string, object, object2);
    }

    @Override
    public void error(Marker marker, String string, Object ... objectArray) {
        this.delegate().error(marker, string, objectArray);
    }

    @Override
    public void error(Marker marker, String string, Throwable throwable) {
        this.delegate().error(marker, string, throwable);
    }

    @Override
    public LoggingEventBuilder atError() {
        return this.delegate().atError();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        SubstituteLogger substituteLogger = (SubstituteLogger)object;
        return !this.name.equals(substituteLogger.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public Logger delegate() {
        if (this._delegate != null) {
            return this._delegate;
        }
        if (this.createdPostInitialization) {
            return NOPLogger.NOP_LOGGER;
        }
        return this.getEventRecordingLogger();
    }

    private Logger getEventRecordingLogger() {
        if (this.eventRecordingLogger == null) {
            this.eventRecordingLogger = new EventRecordingLogger(this, this.eventQueue);
        }
        return this.eventRecordingLogger;
    }

    public void setDelegate(Logger logger) {
        this._delegate = logger;
    }

    public boolean isDelegateEventAware() {
        if (this.delegateEventAware != null) {
            return this.delegateEventAware;
        }
        try {
            this.logMethodCache = this._delegate.getClass().getMethod("log", LoggingEvent.class);
            this.delegateEventAware = Boolean.TRUE;
        } catch (NoSuchMethodException noSuchMethodException) {
            this.delegateEventAware = Boolean.FALSE;
        }
        return this.delegateEventAware;
    }

    public void log(LoggingEvent loggingEvent) {
        if (this.isDelegateEventAware()) {
            try {
                this.logMethodCache.invoke(this._delegate, loggingEvent);
            } catch (IllegalAccessException illegalAccessException) {
            } catch (IllegalArgumentException illegalArgumentException) {
            } catch (InvocationTargetException invocationTargetException) {
                // empty catch block
            }
        }
    }

    public boolean isDelegateNull() {
        return this._delegate == null;
    }

    public boolean isDelegateNOP() {
        return this._delegate instanceof NOPLogger;
    }
}

