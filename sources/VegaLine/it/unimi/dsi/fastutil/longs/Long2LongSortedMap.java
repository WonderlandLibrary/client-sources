/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Long2LongSortedMap
extends Long2LongMap,
SortedMap<Long, Long> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, Long>> entrySet();

    public ObjectSortedSet<Long2LongMap.Entry> long2LongEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public LongCollection values();

    public LongComparator comparator();

    public Long2LongSortedMap subMap(Long var1, Long var2);

    public Long2LongSortedMap headMap(Long var1);

    public Long2LongSortedMap tailMap(Long var1);

    public Long2LongSortedMap subMap(long var1, long var3);

    public Long2LongSortedMap headMap(long var1);

    public Long2LongSortedMap tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Long2LongMap.Entry>,
    Long2LongMap.FastEntrySet {
        public ObjectBidirectionalIterator<Long2LongMap.Entry> fastIterator(Long2LongMap.Entry var1);
    }
}

