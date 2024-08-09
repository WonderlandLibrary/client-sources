/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.timeout;

import io.netty.channel.ChannelException;

public class TimeoutException
extends ChannelException {
    private static final long serialVersionUID = 4673641882869672533L;

    TimeoutException() {
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

