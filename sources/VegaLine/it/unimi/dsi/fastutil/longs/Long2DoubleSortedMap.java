/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Long2DoubleSortedMap
extends Long2DoubleMap,
SortedMap<Long, Double> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, Double>> entrySet();

    public ObjectSortedSet<Long2DoubleMap.Entry> long2DoubleEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public DoubleCollection values();

    public LongComparator comparator();

    public Long2DoubleSortedMap subMap(Long var1, Long var2);

    public Long2DoubleSortedMap headMap(Long var1);

    public Long2DoubleSortedMap tailMap(Long var1);

    public Long2DoubleSortedMap subMap(long var1, long var3);

    public Long2DoubleSortedMap headMap(long var1);

    public Long2DoubleSortedMap tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Long2DoubleMap.Entry>,
    Long2DoubleMap.FastEntrySet {
        public ObjectBidirectionalIterator<Long2DoubleMap.Entry> fastIterator(Long2DoubleMap.Entry var1);
    }
}

