/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Long2ObjectSortedMap<V>
extends Long2ObjectMap<V>,
SortedMap<Long, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, V>> entrySet();

    @Override
    public ObjectSortedSet<Long2ObjectMap.Entry<V>> long2ObjectEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public ObjectCollection<V> values();

    public LongComparator comparator();

    public Long2ObjectSortedMap<V> subMap(Long var1, Long var2);

    public Long2ObjectSortedMap<V> headMap(Long var1);

    public Long2ObjectSortedMap<V> tailMap(Long var1);

    public Long2ObjectSortedMap<V> subMap(long var1, long var3);

    public Long2ObjectSortedMap<V> headMap(long var1);

    public Long2ObjectSortedMap<V> tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Long2ObjectMap.Entry<V>>,
    Long2ObjectMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> fastIterator(Long2ObjectMap.Entry<V> var1);
    }
}

