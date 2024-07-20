/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ShortMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Long2ShortSortedMap
extends Long2ShortMap,
SortedMap<Long, Short> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, Short>> entrySet();

    public ObjectSortedSet<Long2ShortMap.Entry> long2ShortEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public ShortCollection values();

    public LongComparator comparator();

    public Long2ShortSortedMap subMap(Long var1, Long var2);

    public Long2ShortSortedMap headMap(Long var1);

    public Long2ShortSortedMap tailMap(Long var1);

    public Long2ShortSortedMap subMap(long var1, long var3);

    public Long2ShortSortedMap headMap(long var1);

    public Long2ShortSortedMap tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Long2ShortMap.Entry>,
    Long2ShortMap.FastEntrySet {
        public ObjectBidirectionalIterator<Long2ShortMap.Entry> fastIterator(Long2ShortMap.Entry var1);
    }
}

