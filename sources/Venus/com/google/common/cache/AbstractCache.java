/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LongAddable;
import com.google.common.cache.LongAddables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@GwtCompatible
public abstract class AbstractCache<K, V>
implements Cache<K, V> {
    protected AbstractCache() {
    }

    @Override
    public V get(K k, Callable<? extends V> callable) throws ExecutionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        for (Object obj : iterable) {
            if (linkedHashMap.containsKey(obj)) continue;
            Object obj2 = obj;
            Object v = this.getIfPresent(obj);
            if (v == null) continue;
            linkedHashMap.put(obj2, v);
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }

    @Override
    public void put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void cleanUp() {
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invalidate(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invalidateAll(Iterable<?> iterable) {
        for (Object obj : iterable) {
            this.invalidate(obj);
        }
    }

    @Override
    public void invalidateAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CacheStats stats() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        throw new UnsupportedOperationException();
    }

    public static final class SimpleStatsCounter
    implements StatsCounter {
        private final LongAddable hitCount = LongAddables.create();
        private final LongAddable missCount = LongAddables.create();
        private final LongAddable loadSuccessCount = LongAddables.create();
        private final LongAddable loadExceptionCount = LongAddables.create();
        private final LongAddable totalLoadTime = LongAddables.create();
        private final LongAddable evictionCount = LongAddables.create();

        @Override
        public void recordHits(int n) {
            this.hitCount.add(n);
        }

        @Override
        public void recordMisses(int n) {
            this.missCount.add(n);
        }

        @Override
        public void recordLoadSuccess(long l) {
            this.loadSuccessCount.increment();
            this.totalLoadTime.add(l);
        }

        @Override
        public void recordLoadException(long l) {
            this.loadExceptionCount.increment();
            this.totalLoadTime.add(l);
        }

        @Override
        public void recordEviction() {
            this.evictionCount.increment();
        }

        @Override
        public CacheStats snapshot() {
            return new CacheStats(this.hitCount.sum(), this.missCount.sum(), this.loadSuccessCount.sum(), this.loadExceptionCount.sum(), this.totalLoadTime.sum(), this.evictionCount.sum());
        }

        public void incrementBy(StatsCounter statsCounter) {
            CacheStats cacheStats = statsCounter.snapshot();
            this.hitCount.add(cacheStats.hitCount());
            this.missCount.add(cacheStats.missCount());
            this.loadSuccessCount.add(cacheStats.loadSuccessCount());
            this.loadExceptionCount.add(cacheStats.loadExceptionCount());
            this.totalLoadTime.add(cacheStats.totalLoadTime());
            this.evictionCount.add(cacheStats.evictionCount());
        }
    }

    public static interface StatsCounter {
        public void recordHits(int var1);

        public void recordMisses(int var1);

        public void recordLoadSuccess(long var1);

        public void recordLoadException(long var1);

        public void recordEviction();

        public CacheStats snapshot();
    }
}

