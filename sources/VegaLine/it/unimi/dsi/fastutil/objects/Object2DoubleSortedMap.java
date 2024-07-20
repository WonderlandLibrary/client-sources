/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Object2DoubleSortedMap<K>
extends Object2DoubleMap<K>,
SortedMap<K, Double> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Double>> entrySet();

    @Override
    public ObjectSortedSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet();

    @Override
    public ObjectSortedSet<K> keySet();

    @Override
    public DoubleCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Object2DoubleSortedMap<K> subMap(K var1, K var2);

    public Object2DoubleSortedMap<K> headMap(K var1);

    public Object2DoubleSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Object2DoubleMap.Entry<K>>,
    Object2DoubleMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> fastIterator(Object2DoubleMap.Entry<K> var1);
    }
}

