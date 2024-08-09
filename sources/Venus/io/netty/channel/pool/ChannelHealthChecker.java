/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.pool;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.Future;

public interface ChannelHealthChecker {
    public static final ChannelHealthChecker ACTIVE = new ChannelHealthChecker(){

        @Override
        public Future<Boolean> isHealthy(Channel channel) {
            EventLoop eventLoop = channel.eventLoop();
            return channel.isActive() ? eventLoop.newSucceededFuture(Boolean.TRUE) : eventLoop.newSucceededFuture(Boolean.FALSE);
        }
    };

    public Future<Boolean> isHealthy(Channel var1);
}

