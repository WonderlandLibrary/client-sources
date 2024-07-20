/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.ints.Int2CharMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Int2CharSortedMap
extends Int2CharMap,
SortedMap<Integer, Character> {
    @Override
    public ObjectSortedSet<Map.Entry<Integer, Character>> entrySet();

    public ObjectSortedSet<Int2CharMap.Entry> int2CharEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public CharCollection values();

    public IntComparator comparator();

    public Int2CharSortedMap subMap(Integer var1, Integer var2);

    public Int2CharSortedMap headMap(Integer var1);

    public Int2CharSortedMap tailMap(Integer var1);

    public Int2CharSortedMap subMap(int var1, int var2);

    public Int2CharSortedMap headMap(int var1);

    public Int2CharSortedMap tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Int2CharMap.Entry>,
    Int2CharMap.FastEntrySet {
        public ObjectBidirectionalIterator<Int2CharMap.Entry> fastIterator(Int2CharMap.Entry var1);
    }
}

