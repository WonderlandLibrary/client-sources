/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;

@GwtIncompatible
public abstract class ForwardingCache<K, V>
extends ForwardingObject
implements Cache<K, V> {
    protected ForwardingCache() {
    }

    @Override
    protected abstract Cache<K, V> delegate();

    @Override
    @Nullable
    public V getIfPresent(Object object) {
        return this.delegate().getIfPresent(object);
    }

    @Override
    public V get(K k, Callable<? extends V> callable) throws ExecutionException {
        return this.delegate().get(k, callable);
    }

    @Override
    public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        return this.delegate().getAllPresent(iterable);
    }

    @Override
    public void put(K k, V v) {
        this.delegate().put(k, v);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        this.delegate().putAll(map);
    }

    @Override
    public void invalidate(Object object) {
        this.delegate().invalidate(object);
    }

    @Override
    public void invalidateAll(Iterable<?> iterable) {
        this.delegate().invalidateAll(iterable);
    }

    @Override
    public void invalidateAll() {
        this.delegate().invalidateAll();
    }

    @Override
    public long size() {
        return this.delegate().size();
    }

    @Override
    public CacheStats stats() {
        return this.delegate().stats();
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        return this.delegate().asMap();
    }

    @Override
    public void cleanUp() {
        this.delegate().cleanUp();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    public static abstract class SimpleForwardingCache<K, V>
    extends ForwardingCache<K, V> {
        private final Cache<K, V> delegate;

        protected SimpleForwardingCache(Cache<K, V> cache) {
            this.delegate = Preconditions.checkNotNull(cache);
        }

        @Override
        protected final Cache<K, V> delegate() {
            return this.delegate;
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}

