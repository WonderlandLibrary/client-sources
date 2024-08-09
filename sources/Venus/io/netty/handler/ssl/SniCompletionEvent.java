/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.SslCompletionEvent;

public final class SniCompletionEvent
extends SslCompletionEvent {
    private final String hostname;

    SniCompletionEvent(String string) {
        this.hostname = string;
    }

    SniCompletionEvent(String string, Throwable throwable) {
        super(throwable);
        this.hostname = string;
    }

    SniCompletionEvent(Throwable throwable) {
        this(null, throwable);
    }

    public String hostname() {
        return this.hostname;
    }

    @Override
    public String toString() {
        Throwable throwable = this.cause();
        return throwable == null ? this.getClass().getSimpleName() + "(SUCCESS='" + this.hostname + "'\")" : this.getClass().getSimpleName() + '(' + throwable + ')';
    }
}

