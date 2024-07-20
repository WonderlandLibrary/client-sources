/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Int2BooleanSortedMap
extends Int2BooleanMap,
SortedMap<Integer, Boolean> {
    @Override
    public ObjectSortedSet<Map.Entry<Integer, Boolean>> entrySet();

    public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public BooleanCollection values();

    public IntComparator comparator();

    public Int2BooleanSortedMap subMap(Integer var1, Integer var2);

    public Int2BooleanSortedMap headMap(Integer var1);

    public Int2BooleanSortedMap tailMap(Integer var1);

    public Int2BooleanSortedMap subMap(int var1, int var2);

    public Int2BooleanSortedMap headMap(int var1);

    public Int2BooleanSortedMap tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Int2BooleanMap.Entry>,
    Int2BooleanMap.FastEntrySet {
        public ObjectBidirectionalIterator<Int2BooleanMap.Entry> fastIterator(Int2BooleanMap.Entry var1);
    }
}

