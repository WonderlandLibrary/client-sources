/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.logging;

public class LogConfigurationException
extends RuntimeException {
    private static final long serialVersionUID = 8486587136871052495L;
    protected Throwable cause = null;

    public LogConfigurationException() {
    }

    public LogConfigurationException(String string) {
        super(string);
    }

    public LogConfigurationException(Throwable throwable) {
        this(throwable == null ? null : throwable.toString(), throwable);
    }

    public LogConfigurationException(String string, Throwable throwable) {
        super(string + " (Caused by " + throwable + ")");
        this.cause = throwable;
    }

    public Throwable getCause() {
        return this.cause;
    }
}

