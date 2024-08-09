/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.logging.impl;

import java.io.Serializable;
import org.apache.commons.logging.Log;

public class NoOpLog
implements Log,
Serializable {
    private static final long serialVersionUID = 561423906191706148L;

    public NoOpLog() {
    }

    public NoOpLog(String string) {
    }

    public void trace(Object object) {
    }

    public void trace(Object object, Throwable throwable) {
    }

    public void debug(Object object) {
    }

    public void debug(Object object, Throwable throwable) {
    }

    public void info(Object object) {
    }

    public void info(Object object, Throwable throwable) {
    }

    public void warn(Object object) {
    }

    public void warn(Object object, Throwable throwable) {
    }

    public void error(Object object) {
    }

    public void error(Object object, Throwable throwable) {
    }

    public void fatal(Object object) {
    }

    public void fatal(Object object, Throwable throwable) {
    }

    public final boolean isDebugEnabled() {
        return true;
    }

    public final boolean isErrorEnabled() {
        return true;
    }

    public final boolean isFatalEnabled() {
        return true;
    }

    public final boolean isInfoEnabled() {
        return true;
    }

    public final boolean isTraceEnabled() {
        return true;
    }

    public final boolean isWarnEnabled() {
        return true;
    }
}

