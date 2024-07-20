/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;

public abstract class SslCompletionEvent {
    private final Throwable cause;

    SslCompletionEvent() {
        this.cause = null;
    }

    SslCompletionEvent(Throwable cause) {
        this.cause = ObjectUtil.checkNotNull(cause, "cause");
    }

    public final boolean isSuccess() {
        return this.cause == null;
    }

    public final Throwable cause() {
        return this.cause;
    }

    public final String toString() {
        Throwable cause = this.cause();
        return cause == null ? this.getClass().getSimpleName() + "(SUCCESS)" : this.getClass().getSimpleName() + '(' + cause + ')';
    }
}

