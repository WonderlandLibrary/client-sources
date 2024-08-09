/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.NamedLoggerBase;

public abstract class MarkerIgnoringBase
extends NamedLoggerBase
implements Logger {
    private static final long serialVersionUID = 9044267456635152283L;

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return this.isTraceEnabled();
    }

    @Override
    public void trace(Marker marker, String string) {
        this.trace(string);
    }

    @Override
    public void trace(Marker marker, String string, Object object) {
        this.trace(string, object);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2) {
        this.trace(string, object, object2);
    }

    @Override
    public void trace(Marker marker, String string, Object ... objectArray) {
        this.trace(string, objectArray);
    }

    @Override
    public void trace(Marker marker, String string, Throwable throwable) {
        this.trace(string, throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return this.isDebugEnabled();
    }

    @Override
    public void debug(Marker marker, String string) {
        this.debug(string);
    }

    @Override
    public void debug(Marker marker, String string, Object object) {
        this.debug(string, object);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2) {
        this.debug(string, object, object2);
    }

    @Override
    public void debug(Marker marker, String string, Object ... objectArray) {
        this.debug(string, objectArray);
    }

    @Override
    public void debug(Marker marker, String string, Throwable throwable) {
        this.debug(string, throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return this.isInfoEnabled();
    }

    @Override
    public void info(Marker marker, String string) {
        this.info(string);
    }

    @Override
    public void info(Marker marker, String string, Object object) {
        this.info(string, object);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2) {
        this.info(string, object, object2);
    }

    @Override
    public void info(Marker marker, String string, Object ... objectArray) {
        this.info(string, objectArray);
    }

    @Override
    public void info(Marker marker, String string, Throwable throwable) {
        this.info(string, throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return this.isWarnEnabled();
    }

    @Override
    public void warn(Marker marker, String string) {
        this.warn(string);
    }

    @Override
    public void warn(Marker marker, String string, Object object) {
        this.warn(string, object);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2) {
        this.warn(string, object, object2);
    }

    @Override
    public void warn(Marker marker, String string, Object ... objectArray) {
        this.warn(string, objectArray);
    }

    @Override
    public void warn(Marker marker, String string, Throwable throwable) {
        this.warn(string, throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return this.isErrorEnabled();
    }

    @Override
    public void error(Marker marker, String string) {
        this.error(string);
    }

    @Override
    public void error(Marker marker, String string, Object object) {
        this.error(string, object);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2) {
        this.error(string, object, object2);
    }

    @Override
    public void error(Marker marker, String string, Object ... objectArray) {
        this.error(string, objectArray);
    }

    @Override
    public void error(Marker marker, String string, Throwable throwable) {
        this.error(string, throwable);
    }

    public String toString() {
        return this.getClass().getName() + "(" + this.getName() + ")";
    }

    @Override
    public String getName() {
        return super.getName();
    }
}

