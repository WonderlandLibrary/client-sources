/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.pool;

import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import java.io.Closeable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractChannelPoolMap<K, P extends ChannelPool>
implements ChannelPoolMap<K, P>,
Iterable<Map.Entry<K, P>>,
Closeable {
    private final ConcurrentMap<K, P> map = PlatformDependent.newConcurrentHashMap();

    @Override
    public final P get(K k) {
        ChannelPool channelPool;
        ChannelPool channelPool2 = (ChannelPool)this.map.get(ObjectUtil.checkNotNull(k, "key"));
        if (channelPool2 == null && (channelPool = this.map.putIfAbsent(k, channelPool2 = this.newPool(k))) != null) {
            channelPool2.close();
            channelPool2 = channelPool;
        }
        return (P)channelPool2;
    }

    public final boolean remove(K k) {
        ChannelPool channelPool = (ChannelPool)this.map.remove(ObjectUtil.checkNotNull(k, "key"));
        if (channelPool != null) {
            channelPool.close();
            return false;
        }
        return true;
    }

    @Override
    public final Iterator<Map.Entry<K, P>> iterator() {
        return new ReadOnlyIterator<Map.Entry<K, P>>(this.map.entrySet().iterator());
    }

    public final int size() {
        return this.map.size();
    }

    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public final boolean contains(K k) {
        return this.map.containsKey(ObjectUtil.checkNotNull(k, "key"));
    }

    protected abstract P newPool(K var1);

    @Override
    public final void close() {
        for (Object k : this.map.keySet()) {
            this.remove(k);
        }
    }
}

