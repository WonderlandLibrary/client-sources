/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.pool;

import io.netty.channel.Channel;

public interface ChannelPoolHandler {
    public void channelReleased(Channel var1) throws Exception;

    public void channelAcquired(Channel var1) throws Exception;

    public void channelCreated(Channel var1) throws Exception;
}

