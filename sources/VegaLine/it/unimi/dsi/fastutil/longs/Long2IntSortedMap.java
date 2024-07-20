/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Long2IntSortedMap
extends Long2IntMap,
SortedMap<Long, Integer> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, Integer>> entrySet();

    public ObjectSortedSet<Long2IntMap.Entry> long2IntEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public IntCollection values();

    public LongComparator comparator();

    public Long2IntSortedMap subMap(Long var1, Long var2);

    public Long2IntSortedMap headMap(Long var1);

    public Long2IntSortedMap tailMap(Long var1);

    public Long2IntSortedMap subMap(long var1, long var3);

    public Long2IntSortedMap headMap(long var1);

    public Long2IntSortedMap tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Long2IntMap.Entry>,
    Long2IntMap.FastEntrySet {
        public ObjectBidirectionalIterator<Long2IntMap.Entry> fastIterator(Long2IntMap.Entry var1);
    }
}

