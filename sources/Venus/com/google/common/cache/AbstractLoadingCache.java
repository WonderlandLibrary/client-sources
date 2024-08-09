/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

@GwtIncompatible
public abstract class AbstractLoadingCache<K, V>
extends AbstractCache<K, V>
implements LoadingCache<K, V> {
    protected AbstractLoadingCache() {
    }

    @Override
    public V getUnchecked(K k) {
        try {
            return this.get(k);
        } catch (ExecutionException executionException) {
            throw new UncheckedExecutionException(executionException.getCause());
        }
    }

    @Override
    public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
        LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        for (K k : iterable) {
            if (linkedHashMap.containsKey(k)) continue;
            linkedHashMap.put(k, this.get(k));
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }

    @Override
    public final V apply(K k) {
        return this.getUnchecked(k);
    }

    @Override
    public void refresh(K k) {
        throw new UnsupportedOperationException();
    }
}

