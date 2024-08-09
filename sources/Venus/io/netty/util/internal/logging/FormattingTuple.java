/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.logging;

final class FormattingTuple {
    private final String message;
    private final Throwable throwable;

    FormattingTuple(String string, Throwable throwable) {
        this.message = string;
        this.throwable = throwable;
    }

    public String getMessage() {
        return this.message;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}

