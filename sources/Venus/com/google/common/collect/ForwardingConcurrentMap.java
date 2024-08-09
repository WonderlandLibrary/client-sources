/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible
public abstract class ForwardingConcurrentMap<K, V>
extends ForwardingMap<K, V>
implements ConcurrentMap<K, V> {
    protected ForwardingConcurrentMap() {
    }

    @Override
    protected abstract ConcurrentMap<K, V> delegate();

    @Override
    @CanIgnoreReturnValue
    public V putIfAbsent(K k, V v) {
        return this.delegate().putIfAbsent(k, v);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(Object object, Object object2) {
        return this.delegate().remove(object, object2);
    }

    @Override
    @CanIgnoreReturnValue
    public V replace(K k, V v) {
        return this.delegate().replace(k, v);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean replace(K k, V v, V v2) {
        return this.delegate().replace(k, v, v2);
    }

    @Override
    protected Map delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

