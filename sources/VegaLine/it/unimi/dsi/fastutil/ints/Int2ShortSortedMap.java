/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Int2ShortSortedMap
extends Int2ShortMap,
SortedMap<Integer, Short> {
    @Override
    public ObjectSortedSet<Map.Entry<Integer, Short>> entrySet();

    public ObjectSortedSet<Int2ShortMap.Entry> int2ShortEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public ShortCollection values();

    public IntComparator comparator();

    public Int2ShortSortedMap subMap(Integer var1, Integer var2);

    public Int2ShortSortedMap headMap(Integer var1);

    public Int2ShortSortedMap tailMap(Integer var1);

    public Int2ShortSortedMap subMap(int var1, int var2);

    public Int2ShortSortedMap headMap(int var1);

    public Int2ShortSortedMap tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Int2ShortMap.Entry>,
    Int2ShortMap.FastEntrySet {
        public ObjectBidirectionalIterator<Int2ShortMap.Entry> fastIterator(Int2ShortMap.Entry var1);
    }
}

