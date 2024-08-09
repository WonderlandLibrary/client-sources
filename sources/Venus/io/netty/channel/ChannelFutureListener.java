/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public interface ChannelFutureListener
extends GenericFutureListener<ChannelFuture> {
    public static final ChannelFutureListener CLOSE = new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture channelFuture) {
            channelFuture.channel().close();
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };
    public static final ChannelFutureListener CLOSE_ON_FAILURE = new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture channelFuture) {
            if (!channelFuture.isSuccess()) {
                channelFuture.channel().close();
            }
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };
    public static final ChannelFutureListener FIRE_EXCEPTION_ON_FAILURE = new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture channelFuture) {
            if (!channelFuture.isSuccess()) {
                channelFuture.channel().pipeline().fireExceptionCaught(channelFuture.cause());
            }
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };
}

