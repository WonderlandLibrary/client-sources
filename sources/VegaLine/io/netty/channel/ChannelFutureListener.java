/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

public interface ChannelFutureListener
extends GenericFutureListener<ChannelFuture> {
    public static final ChannelFutureListener CLOSE = new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture future) {
            future.channel().close();
        }
    };
    public static final ChannelFutureListener CLOSE_ON_FAILURE = new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture future) {
            if (!future.isSuccess()) {
                future.channel().close();
            }
        }
    };
    public static final ChannelFutureListener FIRE_EXCEPTION_ON_FAILURE = new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture future) {
            if (!future.isSuccess()) {
                future.channel().pipeline().fireExceptionCaught(future.cause());
            }
        }
    };
}

