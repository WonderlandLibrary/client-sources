/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.ForwardingCache;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import java.util.concurrent.ExecutionException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtIncompatible
public abstract class ForwardingLoadingCache<K, V>
extends ForwardingCache<K, V>
implements LoadingCache<K, V> {
    protected ForwardingLoadingCache() {
    }

    @Override
    protected abstract LoadingCache<K, V> delegate();

    @Override
    public V get(K k) throws ExecutionException {
        return this.delegate().get(k);
    }

    @Override
    public V getUnchecked(K k) {
        return this.delegate().getUnchecked(k);
    }

    @Override
    public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
        return this.delegate().getAll(iterable);
    }

    @Override
    public V apply(K k) {
        return this.delegate().apply(k);
    }

    @Override
    public void refresh(K k) {
        this.delegate().refresh(k);
    }

    @Override
    protected Cache delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class SimpleForwardingLoadingCache<K, V>
    extends ForwardingLoadingCache<K, V> {
        private final LoadingCache<K, V> delegate;

        protected SimpleForwardingLoadingCache(LoadingCache<K, V> loadingCache) {
            this.delegate = Preconditions.checkNotNull(loadingCache);
        }

        @Override
        protected final LoadingCache<K, V> delegate() {
            return this.delegate;
        }

        @Override
        protected Cache delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}

