/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.pool;

import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;

public abstract class AbstractChannelPoolHandler
implements ChannelPoolHandler {
    @Override
    public void channelAcquired(Channel ch) throws Exception {
    }

    @Override
    public void channelReleased(Channel ch) throws Exception {
    }
}

