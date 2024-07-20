/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Long2ReferenceSortedMap<V>
extends Long2ReferenceMap<V>,
SortedMap<Long, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, V>> entrySet();

    @Override
    public ObjectSortedSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public ReferenceCollection<V> values();

    public LongComparator comparator();

    public Long2ReferenceSortedMap<V> subMap(Long var1, Long var2);

    public Long2ReferenceSortedMap<V> headMap(Long var1);

    public Long2ReferenceSortedMap<V> tailMap(Long var1);

    public Long2ReferenceSortedMap<V> subMap(long var1, long var3);

    public Long2ReferenceSortedMap<V> headMap(long var1);

    public Long2ReferenceSortedMap<V> tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Long2ReferenceMap.Entry<V>>,
    Long2ReferenceMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Long2ReferenceMap.Entry<V>> fastIterator(Long2ReferenceMap.Entry<V> var1);
    }
}

