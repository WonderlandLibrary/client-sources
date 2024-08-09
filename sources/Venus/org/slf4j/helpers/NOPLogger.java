/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.NamedLoggerBase;

public class NOPLogger
extends NamedLoggerBase
implements Logger {
    private static final long serialVersionUID = -517220405410904473L;
    public static final NOPLogger NOP_LOGGER = new NOPLogger();

    protected NOPLogger() {
    }

    @Override
    public String getName() {
        return "NOP";
    }

    @Override
    public final boolean isTraceEnabled() {
        return true;
    }

    @Override
    public final void trace(String string) {
    }

    @Override
    public final void trace(String string, Object object) {
    }

    @Override
    public final void trace(String string, Object object, Object object2) {
    }

    @Override
    public final void trace(String string, Object ... objectArray) {
    }

    @Override
    public final void trace(String string, Throwable throwable) {
    }

    @Override
    public final boolean isDebugEnabled() {
        return true;
    }

    @Override
    public final void debug(String string) {
    }

    @Override
    public final void debug(String string, Object object) {
    }

    @Override
    public final void debug(String string, Object object, Object object2) {
    }

    @Override
    public final void debug(String string, Object ... objectArray) {
    }

    @Override
    public final void debug(String string, Throwable throwable) {
    }

    @Override
    public final boolean isInfoEnabled() {
        return true;
    }

    @Override
    public final void info(String string) {
    }

    @Override
    public final void info(String string, Object object) {
    }

    @Override
    public final void info(String string, Object object, Object object2) {
    }

    @Override
    public final void info(String string, Object ... objectArray) {
    }

    @Override
    public final void info(String string, Throwable throwable) {
    }

    @Override
    public final boolean isWarnEnabled() {
        return true;
    }

    @Override
    public final void warn(String string) {
    }

    @Override
    public final void warn(String string, Object object) {
    }

    @Override
    public final void warn(String string, Object object, Object object2) {
    }

    @Override
    public final void warn(String string, Object ... objectArray) {
    }

    @Override
    public final void warn(String string, Throwable throwable) {
    }

    @Override
    public final boolean isErrorEnabled() {
        return true;
    }

    @Override
    public final void error(String string) {
    }

    @Override
    public final void error(String string, Object object) {
    }

    @Override
    public final void error(String string, Object object, Object object2) {
    }

    @Override
    public final void error(String string, Object ... objectArray) {
    }

    @Override
    public final void error(String string, Throwable throwable) {
    }

    @Override
    public final boolean isTraceEnabled(Marker marker) {
        return true;
    }

    @Override
    public final void trace(Marker marker, String string) {
    }

    @Override
    public final void trace(Marker marker, String string, Object object) {
    }

    @Override
    public final void trace(Marker marker, String string, Object object, Object object2) {
    }

    @Override
    public final void trace(Marker marker, String string, Object ... objectArray) {
    }

    @Override
    public final void trace(Marker marker, String string, Throwable throwable) {
    }

    @Override
    public final boolean isDebugEnabled(Marker marker) {
        return true;
    }

    @Override
    public final void debug(Marker marker, String string) {
    }

    @Override
    public final void debug(Marker marker, String string, Object object) {
    }

    @Override
    public final void debug(Marker marker, String string, Object object, Object object2) {
    }

    @Override
    public final void debug(Marker marker, String string, Object ... objectArray) {
    }

    @Override
    public final void debug(Marker marker, String string, Throwable throwable) {
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    @Override
    public final void info(Marker marker, String string) {
    }

    @Override
    public final void info(Marker marker, String string, Object object) {
    }

    @Override
    public final void info(Marker marker, String string, Object object, Object object2) {
    }

    @Override
    public final void info(Marker marker, String string, Object ... objectArray) {
    }

    @Override
    public final void info(Marker marker, String string, Throwable throwable) {
    }

    @Override
    public final boolean isWarnEnabled(Marker marker) {
        return true;
    }

    @Override
    public final void warn(Marker marker, String string) {
    }

    @Override
    public final void warn(Marker marker, String string, Object object) {
    }

    @Override
    public final void warn(Marker marker, String string, Object object, Object object2) {
    }

    @Override
    public final void warn(Marker marker, String string, Object ... objectArray) {
    }

    @Override
    public final void warn(Marker marker, String string, Throwable throwable) {
    }

    @Override
    public final boolean isErrorEnabled(Marker marker) {
        return true;
    }

    @Override
    public final void error(Marker marker, String string) {
    }

    @Override
    public final void error(Marker marker, String string, Object object) {
    }

    @Override
    public final void error(Marker marker, String string, Object object, Object object2) {
    }

    @Override
    public final void error(Marker marker, String string, Object ... objectArray) {
    }

    @Override
    public final void error(Marker marker, String string, Throwable throwable) {
    }
}

