/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import java.io.ObjectStreamException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

public abstract class AbstractLogger
implements Logger,
Serializable {
    private static final long serialVersionUID = -2529255052481744503L;
    protected String name;

    @Override
    public String getName() {
        return this.name;
    }

    protected Object readResolve() throws ObjectStreamException {
        return LoggerFactory.getLogger(this.getName());
    }

    @Override
    public void trace(String string) {
        if (this.isTraceEnabled()) {
            this.handle_0ArgsCall(Level.TRACE, null, string, null);
        }
    }

    @Override
    public void trace(String string, Object object) {
        if (this.isTraceEnabled()) {
            this.handle_1ArgsCall(Level.TRACE, null, string, object);
        }
    }

    @Override
    public void trace(String string, Object object, Object object2) {
        if (this.isTraceEnabled()) {
            this.handle2ArgsCall(Level.TRACE, null, string, object, object2);
        }
    }

    @Override
    public void trace(String string, Object ... objectArray) {
        if (this.isTraceEnabled()) {
            this.handleArgArrayCall(Level.TRACE, null, string, objectArray);
        }
    }

    @Override
    public void trace(String string, Throwable throwable) {
        if (this.isTraceEnabled()) {
            this.handle_0ArgsCall(Level.TRACE, null, string, throwable);
        }
    }

    @Override
    public void trace(Marker marker, String string) {
        if (this.isTraceEnabled(marker)) {
            this.handle_0ArgsCall(Level.TRACE, marker, string, null);
        }
    }

    @Override
    public void trace(Marker marker, String string, Object object) {
        if (this.isTraceEnabled(marker)) {
            this.handle_1ArgsCall(Level.TRACE, marker, string, object);
        }
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2) {
        if (this.isTraceEnabled(marker)) {
            this.handle2ArgsCall(Level.TRACE, marker, string, object, object2);
        }
    }

    @Override
    public void trace(Marker marker, String string, Object ... objectArray) {
        if (this.isTraceEnabled(marker)) {
            this.handleArgArrayCall(Level.TRACE, marker, string, objectArray);
        }
    }

    @Override
    public void trace(Marker marker, String string, Throwable throwable) {
        if (this.isTraceEnabled(marker)) {
            this.handle_0ArgsCall(Level.TRACE, marker, string, throwable);
        }
    }

    @Override
    public void debug(String string) {
        if (this.isDebugEnabled()) {
            this.handle_0ArgsCall(Level.DEBUG, null, string, null);
        }
    }

    @Override
    public void debug(String string, Object object) {
        if (this.isDebugEnabled()) {
            this.handle_1ArgsCall(Level.DEBUG, null, string, object);
        }
    }

    @Override
    public void debug(String string, Object object, Object object2) {
        if (this.isDebugEnabled()) {
            this.handle2ArgsCall(Level.DEBUG, null, string, object, object2);
        }
    }

    @Override
    public void debug(String string, Object ... objectArray) {
        if (this.isDebugEnabled()) {
            this.handleArgArrayCall(Level.DEBUG, null, string, objectArray);
        }
    }

    @Override
    public void debug(String string, Throwable throwable) {
        if (this.isDebugEnabled()) {
            this.handle_0ArgsCall(Level.DEBUG, null, string, throwable);
        }
    }

    @Override
    public void debug(Marker marker, String string) {
        if (this.isDebugEnabled(marker)) {
            this.handle_0ArgsCall(Level.DEBUG, marker, string, null);
        }
    }

    @Override
    public void debug(Marker marker, String string, Object object) {
        if (this.isDebugEnabled(marker)) {
            this.handle_1ArgsCall(Level.DEBUG, marker, string, object);
        }
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2) {
        if (this.isDebugEnabled(marker)) {
            this.handle2ArgsCall(Level.DEBUG, marker, string, object, object2);
        }
    }

    @Override
    public void debug(Marker marker, String string, Object ... objectArray) {
        if (this.isDebugEnabled(marker)) {
            this.handleArgArrayCall(Level.DEBUG, marker, string, objectArray);
        }
    }

    @Override
    public void debug(Marker marker, String string, Throwable throwable) {
        if (this.isDebugEnabled(marker)) {
            this.handle_0ArgsCall(Level.DEBUG, marker, string, throwable);
        }
    }

    @Override
    public void info(String string) {
        if (this.isInfoEnabled()) {
            this.handle_0ArgsCall(Level.INFO, null, string, null);
        }
    }

    @Override
    public void info(String string, Object object) {
        if (this.isInfoEnabled()) {
            this.handle_1ArgsCall(Level.INFO, null, string, object);
        }
    }

    @Override
    public void info(String string, Object object, Object object2) {
        if (this.isInfoEnabled()) {
            this.handle2ArgsCall(Level.INFO, null, string, object, object2);
        }
    }

    @Override
    public void info(String string, Object ... objectArray) {
        if (this.isInfoEnabled()) {
            this.handleArgArrayCall(Level.INFO, null, string, objectArray);
        }
    }

    @Override
    public void info(String string, Throwable throwable) {
        if (this.isInfoEnabled()) {
            this.handle_0ArgsCall(Level.INFO, null, string, throwable);
        }
    }

    @Override
    public void info(Marker marker, String string) {
        if (this.isInfoEnabled(marker)) {
            this.handle_0ArgsCall(Level.INFO, marker, string, null);
        }
    }

    @Override
    public void info(Marker marker, String string, Object object) {
        if (this.isInfoEnabled(marker)) {
            this.handle_1ArgsCall(Level.INFO, marker, string, object);
        }
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2) {
        if (this.isInfoEnabled(marker)) {
            this.handle2ArgsCall(Level.INFO, marker, string, object, object2);
        }
    }

    @Override
    public void info(Marker marker, String string, Object ... objectArray) {
        if (this.isInfoEnabled(marker)) {
            this.handleArgArrayCall(Level.INFO, marker, string, objectArray);
        }
    }

    @Override
    public void info(Marker marker, String string, Throwable throwable) {
        if (this.isInfoEnabled(marker)) {
            this.handle_0ArgsCall(Level.INFO, marker, string, throwable);
        }
    }

    @Override
    public void warn(String string) {
        if (this.isWarnEnabled()) {
            this.handle_0ArgsCall(Level.WARN, null, string, null);
        }
    }

    @Override
    public void warn(String string, Object object) {
        if (this.isWarnEnabled()) {
            this.handle_1ArgsCall(Level.WARN, null, string, object);
        }
    }

    @Override
    public void warn(String string, Object object, Object object2) {
        if (this.isWarnEnabled()) {
            this.handle2ArgsCall(Level.WARN, null, string, object, object2);
        }
    }

    @Override
    public void warn(String string, Object ... objectArray) {
        if (this.isWarnEnabled()) {
            this.handleArgArrayCall(Level.WARN, null, string, objectArray);
        }
    }

    @Override
    public void warn(String string, Throwable throwable) {
        if (this.isWarnEnabled()) {
            this.handle_0ArgsCall(Level.WARN, null, string, throwable);
        }
    }

    @Override
    public void warn(Marker marker, String string) {
        if (this.isWarnEnabled(marker)) {
            this.handle_0ArgsCall(Level.WARN, marker, string, null);
        }
    }

    @Override
    public void warn(Marker marker, String string, Object object) {
        if (this.isWarnEnabled(marker)) {
            this.handle_1ArgsCall(Level.WARN, marker, string, object);
        }
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2) {
        if (this.isWarnEnabled(marker)) {
            this.handle2ArgsCall(Level.WARN, marker, string, object, object2);
        }
    }

    @Override
    public void warn(Marker marker, String string, Object ... objectArray) {
        if (this.isWarnEnabled(marker)) {
            this.handleArgArrayCall(Level.WARN, marker, string, objectArray);
        }
    }

    @Override
    public void warn(Marker marker, String string, Throwable throwable) {
        if (this.isWarnEnabled(marker)) {
            this.handle_0ArgsCall(Level.WARN, marker, string, throwable);
        }
    }

    @Override
    public void error(String string) {
        if (this.isErrorEnabled()) {
            this.handle_0ArgsCall(Level.ERROR, null, string, null);
        }
    }

    @Override
    public void error(String string, Object object) {
        if (this.isErrorEnabled()) {
            this.handle_1ArgsCall(Level.ERROR, null, string, object);
        }
    }

    @Override
    public void error(String string, Object object, Object object2) {
        if (this.isErrorEnabled()) {
            this.handle2ArgsCall(Level.ERROR, null, string, object, object2);
        }
    }

    @Override
    public void error(String string, Object ... objectArray) {
        if (this.isErrorEnabled()) {
            this.handleArgArrayCall(Level.ERROR, null, string, objectArray);
        }
    }

    @Override
    public void error(String string, Throwable throwable) {
        if (this.isErrorEnabled()) {
            this.handle_0ArgsCall(Level.ERROR, null, string, throwable);
        }
    }

    @Override
    public void error(Marker marker, String string) {
        if (this.isErrorEnabled(marker)) {
            this.handle_0ArgsCall(Level.ERROR, marker, string, null);
        }
    }

    @Override
    public void error(Marker marker, String string, Object object) {
        if (this.isErrorEnabled(marker)) {
            this.handle_1ArgsCall(Level.ERROR, marker, string, object);
        }
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2) {
        if (this.isErrorEnabled(marker)) {
            this.handle2ArgsCall(Level.ERROR, marker, string, object, object2);
        }
    }

    @Override
    public void error(Marker marker, String string, Object ... objectArray) {
        if (this.isErrorEnabled(marker)) {
            this.handleArgArrayCall(Level.ERROR, marker, string, objectArray);
        }
    }

    @Override
    public void error(Marker marker, String string, Throwable throwable) {
        if (this.isErrorEnabled(marker)) {
            this.handle_0ArgsCall(Level.ERROR, marker, string, throwable);
        }
    }

    private void handle_0ArgsCall(Level level, Marker marker, String string, Throwable throwable) {
        this.handleNormalizedLoggingCall(level, marker, string, null, throwable);
    }

    private void handle_1ArgsCall(Level level, Marker marker, String string, Object object) {
        this.handleNormalizedLoggingCall(level, marker, string, new Object[]{object}, null);
    }

    private void handle2ArgsCall(Level level, Marker marker, String string, Object object, Object object2) {
        if (object2 instanceof Throwable) {
            this.handleNormalizedLoggingCall(level, marker, string, new Object[]{object}, (Throwable)object2);
        } else {
            this.handleNormalizedLoggingCall(level, marker, string, new Object[]{object, object2}, null);
        }
    }

    private void handleArgArrayCall(Level level, Marker marker, String string, Object[] objectArray) {
        Throwable throwable = MessageFormatter.getThrowableCandidate(objectArray);
        if (throwable != null) {
            Object[] objectArray2 = MessageFormatter.trimmedCopy(objectArray);
            this.handleNormalizedLoggingCall(level, marker, string, objectArray2, throwable);
        } else {
            this.handleNormalizedLoggingCall(level, marker, string, objectArray, null);
        }
    }

    protected abstract String getFullyQualifiedCallerName();

    protected abstract void handleNormalizedLoggingCall(Level var1, Marker var2, String var3, Object[] var4, Throwable var5);
}

