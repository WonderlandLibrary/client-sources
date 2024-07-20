/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Int2IntSortedMap
extends Int2IntMap,
SortedMap<Integer, Integer> {
    @Override
    public ObjectSortedSet<Map.Entry<Integer, Integer>> entrySet();

    public ObjectSortedSet<Int2IntMap.Entry> int2IntEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public IntCollection values();

    public IntComparator comparator();

    public Int2IntSortedMap subMap(Integer var1, Integer var2);

    public Int2IntSortedMap headMap(Integer var1);

    public Int2IntSortedMap tailMap(Integer var1);

    public Int2IntSortedMap subMap(int var1, int var2);

    public Int2IntSortedMap headMap(int var1);

    public Int2IntSortedMap tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Int2IntMap.Entry>,
    Int2IntMap.FastEntrySet {
        public ObjectBidirectionalIterator<Int2IntMap.Entry> fastIterator(Int2IntMap.Entry var1);
    }
}

