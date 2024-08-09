/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.graph.MapIteratorCache;
import java.util.Map;
import javax.annotation.Nullable;

class MapRetrievalCache<K, V>
extends MapIteratorCache<K, V> {
    @Nullable
    private transient CacheEntry<K, V> cacheEntry1;
    @Nullable
    private transient CacheEntry<K, V> cacheEntry2;

    MapRetrievalCache(Map<K, V> map) {
        super(map);
    }

    @Override
    public V get(@Nullable Object object) {
        V v = this.getIfCached(object);
        if (v != null) {
            return v;
        }
        v = this.getWithoutCaching(object);
        if (v != null) {
            this.addToCache(object, v);
        }
        return v;
    }

    @Override
    protected V getIfCached(@Nullable Object object) {
        Object v = super.getIfCached(object);
        if (v != null) {
            return v;
        }
        CacheEntry<K, V> cacheEntry = this.cacheEntry1;
        if (cacheEntry != null && cacheEntry.key == object) {
            return cacheEntry.value;
        }
        cacheEntry = this.cacheEntry2;
        if (cacheEntry != null && cacheEntry.key == object) {
            this.addToCache(cacheEntry);
            return cacheEntry.value;
        }
        return null;
    }

    @Override
    protected void clearCache() {
        super.clearCache();
        this.cacheEntry1 = null;
        this.cacheEntry2 = null;
    }

    private void addToCache(K k, V v) {
        this.addToCache(new CacheEntry<K, V>(k, v));
    }

    private void addToCache(CacheEntry<K, V> cacheEntry) {
        this.cacheEntry2 = this.cacheEntry1;
        this.cacheEntry1 = cacheEntry;
    }

    private static final class CacheEntry<K, V> {
        final K key;
        final V value;

        CacheEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }
}

