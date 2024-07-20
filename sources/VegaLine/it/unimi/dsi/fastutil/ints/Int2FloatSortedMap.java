/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Int2FloatSortedMap
extends Int2FloatMap,
SortedMap<Integer, Float> {
    @Override
    public ObjectSortedSet<Map.Entry<Integer, Float>> entrySet();

    public ObjectSortedSet<Int2FloatMap.Entry> int2FloatEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public FloatCollection values();

    public IntComparator comparator();

    public Int2FloatSortedMap subMap(Integer var1, Integer var2);

    public Int2FloatSortedMap headMap(Integer var1);

    public Int2FloatSortedMap tailMap(Integer var1);

    public Int2FloatSortedMap subMap(int var1, int var2);

    public Int2FloatSortedMap headMap(int var1);

    public Int2FloatSortedMap tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Int2FloatMap.Entry>,
    Int2FloatMap.FastEntrySet {
        public ObjectBidirectionalIterator<Int2FloatMap.Entry> fastIterator(Int2FloatMap.Entry var1);
    }
}

