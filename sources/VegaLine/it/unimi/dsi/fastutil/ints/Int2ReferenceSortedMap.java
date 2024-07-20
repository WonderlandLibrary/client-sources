/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Int2ReferenceSortedMap<V>
extends Int2ReferenceMap<V>,
SortedMap<Integer, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Integer, V>> entrySet();

    @Override
    public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public ReferenceCollection<V> values();

    public IntComparator comparator();

    public Int2ReferenceSortedMap<V> subMap(Integer var1, Integer var2);

    public Int2ReferenceSortedMap<V> headMap(Integer var1);

    public Int2ReferenceSortedMap<V> tailMap(Integer var1);

    public Int2ReferenceSortedMap<V> subMap(int var1, int var2);

    public Int2ReferenceSortedMap<V> headMap(int var1);

    public Int2ReferenceSortedMap<V> tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Int2ReferenceMap.Entry<V>>,
    Int2ReferenceMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Int2ReferenceMap.Entry<V>> fastIterator(Int2ReferenceMap.Entry<V> var1);
    }
}

