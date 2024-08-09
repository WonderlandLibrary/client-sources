/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ipfilter;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.SocketAddress;

public abstract class AbstractRemoteAddressFilter<T extends SocketAddress>
extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.handleNewChannel(channelHandlerContext);
        channelHandlerContext.fireChannelRegistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.handleNewChannel(channelHandlerContext)) {
            throw new IllegalStateException("cannot determine to accept or reject a channel: " + channelHandlerContext.channel());
        }
        channelHandlerContext.fireChannelActive();
    }

    private boolean handleNewChannel(ChannelHandlerContext channelHandlerContext) throws Exception {
        SocketAddress socketAddress = channelHandlerContext.channel().remoteAddress();
        if (socketAddress == null) {
            return true;
        }
        channelHandlerContext.pipeline().remove(this);
        if (this.accept(channelHandlerContext, socketAddress)) {
            this.channelAccepted(channelHandlerContext, socketAddress);
        } else {
            ChannelFuture channelFuture = this.channelRejected(channelHandlerContext, socketAddress);
            if (channelFuture != null) {
                channelFuture.addListener(ChannelFutureListener.CLOSE);
            } else {
                channelHandlerContext.close();
            }
        }
        return false;
    }

    protected abstract boolean accept(ChannelHandlerContext var1, T var2) throws Exception;

    protected void channelAccepted(ChannelHandlerContext channelHandlerContext, T t) {
    }

    protected ChannelFuture channelRejected(ChannelHandlerContext channelHandlerContext, T t) {
        return null;
    }
}

