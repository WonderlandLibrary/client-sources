/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Double2ObjectSortedMap<V>
extends Double2ObjectMap<V>,
SortedMap<Double, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, V>> entrySet();

    @Override
    public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public ObjectCollection<V> values();

    public DoubleComparator comparator();

    public Double2ObjectSortedMap<V> subMap(Double var1, Double var2);

    public Double2ObjectSortedMap<V> headMap(Double var1);

    public Double2ObjectSortedMap<V> tailMap(Double var1);

    public Double2ObjectSortedMap<V> subMap(double var1, double var3);

    public Double2ObjectSortedMap<V> headMap(double var1);

    public Double2ObjectSortedMap<V> tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Double2ObjectMap.Entry<V>>,
    Double2ObjectMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> fastIterator(Double2ObjectMap.Entry<V> var1);
    }
}

