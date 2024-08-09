/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public abstract class LocaleObjectCache<K, V> {
    private ConcurrentHashMap<K, CacheEntry<K, V>> _map;
    private ReferenceQueue<V> _queue = new ReferenceQueue();

    public LocaleObjectCache() {
        this(16, 0.75f, 16);
    }

    public LocaleObjectCache(int n, float f, int n2) {
        this._map = new ConcurrentHashMap(n, f, n2);
    }

    public V get(K k) {
        V v = null;
        this.cleanStaleEntries();
        CacheEntry<K, V> cacheEntry = this._map.get(k);
        if (cacheEntry != null) {
            v = (V)cacheEntry.get();
        }
        if (v == null) {
            k = this.normalizeKey(k);
            V v2 = this.createObject(k);
            if (k == null || v2 == null) {
                return null;
            }
            CacheEntry<K, V> cacheEntry2 = new CacheEntry<K, V>(k, v2, this._queue);
            while (v == null) {
                this.cleanStaleEntries();
                cacheEntry = this._map.putIfAbsent(k, cacheEntry2);
                if (cacheEntry == null) {
                    v = v2;
                    break;
                }
                v = (V)cacheEntry.get();
            }
        }
        return v;
    }

    private void cleanStaleEntries() {
        CacheEntry cacheEntry;
        while ((cacheEntry = (CacheEntry)this._queue.poll()) != null) {
            this._map.remove(cacheEntry.getKey());
        }
    }

    protected abstract V createObject(K var1);

    protected K normalizeKey(K k) {
        return k;
    }

    private static class CacheEntry<K, V>
    extends SoftReference<V> {
        private K _key;

        CacheEntry(K k, V v, ReferenceQueue<V> referenceQueue) {
            super(v, referenceQueue);
            this._key = k;
        }

        K getKey() {
            return this._key;
        }
    }
}

