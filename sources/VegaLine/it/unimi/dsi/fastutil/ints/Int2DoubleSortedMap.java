/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Int2DoubleSortedMap
extends Int2DoubleMap,
SortedMap<Integer, Double> {
    @Override
    public ObjectSortedSet<Map.Entry<Integer, Double>> entrySet();

    public ObjectSortedSet<Int2DoubleMap.Entry> int2DoubleEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public DoubleCollection values();

    public IntComparator comparator();

    public Int2DoubleSortedMap subMap(Integer var1, Integer var2);

    public Int2DoubleSortedMap headMap(Integer var1);

    public Int2DoubleSortedMap tailMap(Integer var1);

    public Int2DoubleSortedMap subMap(int var1, int var2);

    public Int2DoubleSortedMap headMap(int var1);

    public Int2DoubleSortedMap tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Int2DoubleMap.Entry>,
    Int2DoubleMap.FastEntrySet {
        public ObjectBidirectionalIterator<Int2DoubleMap.Entry> fastIterator(Int2DoubleMap.Entry var1);
    }
}

