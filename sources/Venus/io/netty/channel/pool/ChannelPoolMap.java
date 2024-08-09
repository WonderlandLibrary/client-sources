/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.pool;

import io.netty.channel.pool.ChannelPool;

public interface ChannelPoolMap<K, P extends ChannelPool> {
    public P get(K var1);

    public boolean contains(K var1);
}

