/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

public class MessageAggregationException
extends IllegalStateException {
    private static final long serialVersionUID = -1995826182950310255L;

    public MessageAggregationException() {
    }

    public MessageAggregationException(String string) {
        super(string);
    }

    public MessageAggregationException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public MessageAggregationException(Throwable throwable) {
        super(throwable);
    }
}

