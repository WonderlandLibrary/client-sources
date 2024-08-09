/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.AbstractChannelHandlerContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.util.concurrent.EventExecutor;

final class DefaultChannelHandlerContext
extends AbstractChannelHandlerContext {
    private final ChannelHandler handler;

    DefaultChannelHandlerContext(DefaultChannelPipeline defaultChannelPipeline, EventExecutor eventExecutor, String string, ChannelHandler channelHandler) {
        super(defaultChannelPipeline, eventExecutor, string, DefaultChannelHandlerContext.isInbound(channelHandler), DefaultChannelHandlerContext.isOutbound(channelHandler));
        if (channelHandler == null) {
            throw new NullPointerException("handler");
        }
        this.handler = channelHandler;
    }

    @Override
    public ChannelHandler handler() {
        return this.handler;
    }

    private static boolean isInbound(ChannelHandler channelHandler) {
        return channelHandler instanceof ChannelInboundHandler;
    }

    private static boolean isOutbound(ChannelHandler channelHandler) {
        return channelHandler instanceof ChannelOutboundHandler;
    }
}

