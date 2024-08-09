/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

class MapIteratorCache<K, V> {
    private final Map<K, V> backingMap;
    @Nullable
    private transient Map.Entry<K, V> entrySetCache;

    MapIteratorCache(Map<K, V> map) {
        this.backingMap = Preconditions.checkNotNull(map);
    }

    @CanIgnoreReturnValue
    public V put(@Nullable K k, @Nullable V v) {
        this.clearCache();
        return this.backingMap.put(k, v);
    }

    @CanIgnoreReturnValue
    public V remove(@Nullable Object object) {
        this.clearCache();
        return this.backingMap.remove(object);
    }

    public void clear() {
        this.clearCache();
        this.backingMap.clear();
    }

    public V get(@Nullable Object object) {
        V v = this.getIfCached(object);
        return v != null ? v : this.getWithoutCaching(object);
    }

    public final V getWithoutCaching(@Nullable Object object) {
        return this.backingMap.get(object);
    }

    public final boolean containsKey(@Nullable Object object) {
        return this.getIfCached(object) != null || this.backingMap.containsKey(object);
    }

    public final Set<K> unmodifiableKeySet() {
        return new AbstractSet<K>(this){
            final MapIteratorCache this$0;
            {
                this.this$0 = mapIteratorCache;
            }

            @Override
            public UnmodifiableIterator<K> iterator() {
                Iterator iterator2 = MapIteratorCache.access$000(this.this$0).entrySet().iterator();
                return new UnmodifiableIterator<K>(this, iterator2){
                    final Iterator val$entryIterator;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.val$entryIterator = iterator2;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.val$entryIterator.hasNext();
                    }

                    @Override
                    public K next() {
                        Map.Entry entry = (Map.Entry)this.val$entryIterator.next();
                        MapIteratorCache.access$102(this.this$1.this$0, entry);
                        return entry.getKey();
                    }
                };
            }

            @Override
            public int size() {
                return MapIteratorCache.access$000(this.this$0).size();
            }

            @Override
            public boolean contains(@Nullable Object object) {
                return this.this$0.containsKey(object);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    protected V getIfCached(@Nullable Object object) {
        Map.Entry<K, V> entry = this.entrySetCache;
        if (entry != null && entry.getKey() == object) {
            return entry.getValue();
        }
        return null;
    }

    protected void clearCache() {
        this.entrySetCache = null;
    }

    static Map access$000(MapIteratorCache mapIteratorCache) {
        return mapIteratorCache.backingMap;
    }

    static Map.Entry access$102(MapIteratorCache mapIteratorCache, Map.Entry entry) {
        mapIteratorCache.entrySetCache = entry;
        return mapIteratorCache.entrySetCache;
    }
}

