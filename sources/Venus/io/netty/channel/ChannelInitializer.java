/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ConcurrentMap;

@ChannelHandler.Sharable
public abstract class ChannelInitializer<C extends Channel>
extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
    private final ConcurrentMap<ChannelHandlerContext, Boolean> initMap = PlatformDependent.newConcurrentHashMap();

    protected abstract void initChannel(C var1) throws Exception;

    @Override
    public final void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.initChannel(channelHandlerContext)) {
            channelHandlerContext.pipeline().fireChannelRegistered();
        } else {
            channelHandlerContext.fireChannelRegistered();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        logger.warn("Failed to initialize a channel. Closing: " + channelHandlerContext.channel(), throwable);
        channelHandlerContext.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isRegistered()) {
            this.initChannel(channelHandlerContext);
        }
    }

    private boolean initChannel(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.initMap.putIfAbsent(channelHandlerContext, Boolean.TRUE) == null) {
            try {
                this.initChannel(channelHandlerContext.channel());
            } catch (Throwable throwable) {
                this.exceptionCaught(channelHandlerContext, throwable);
            } finally {
                this.remove(channelHandlerContext);
            }
            return false;
        }
        return true;
    }

    private void remove(ChannelHandlerContext channelHandlerContext) {
        try {
            ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
            if (channelPipeline.context(this) != null) {
                channelPipeline.remove(this);
            }
        } finally {
            this.initMap.remove(channelHandlerContext);
        }
    }
}

