/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2LongMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Int2LongSortedMap
extends Int2LongMap,
SortedMap<Integer, Long> {
    @Override
    public ObjectSortedSet<Map.Entry<Integer, Long>> entrySet();

    public ObjectSortedSet<Int2LongMap.Entry> int2LongEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public LongCollection values();

    public IntComparator comparator();

    public Int2LongSortedMap subMap(Integer var1, Integer var2);

    public Int2LongSortedMap headMap(Integer var1);

    public Int2LongSortedMap tailMap(Integer var1);

    public Int2LongSortedMap subMap(int var1, int var2);

    public Int2LongSortedMap headMap(int var1);

    public Int2LongSortedMap tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Int2LongMap.Entry>,
    Int2LongMap.FastEntrySet {
        public ObjectBidirectionalIterator<Int2LongMap.Entry> fastIterator(Int2LongMap.Entry var1);
    }
}

