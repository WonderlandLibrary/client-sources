/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Int2ObjectSortedMap<V>
extends Int2ObjectMap<V>,
SortedMap<Integer, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Integer, V>> entrySet();

    @Override
    public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public ObjectCollection<V> values();

    public IntComparator comparator();

    public Int2ObjectSortedMap<V> subMap(Integer var1, Integer var2);

    public Int2ObjectSortedMap<V> headMap(Integer var1);

    public Int2ObjectSortedMap<V> tailMap(Integer var1);

    public Int2ObjectSortedMap<V> subMap(int var1, int var2);

    public Int2ObjectSortedMap<V> headMap(int var1);

    public Int2ObjectSortedMap<V> tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Int2ObjectMap.Entry<V>>,
    Int2ObjectMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap.Entry<V> var1);
    }
}

