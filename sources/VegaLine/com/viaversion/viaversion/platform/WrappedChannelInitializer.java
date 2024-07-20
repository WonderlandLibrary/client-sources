/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.platform;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public interface WrappedChannelInitializer {
    public ChannelInitializer<Channel> original();
}

