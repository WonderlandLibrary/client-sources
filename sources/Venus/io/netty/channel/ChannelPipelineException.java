/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.ChannelException;

public class ChannelPipelineException
extends ChannelException {
    private static final long serialVersionUID = 3379174210419885980L;

    public ChannelPipelineException() {
    }

    public ChannelPipelineException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ChannelPipelineException(String string) {
        super(string);
    }

    public ChannelPipelineException(Throwable throwable) {
        super(throwable);
    }
}

