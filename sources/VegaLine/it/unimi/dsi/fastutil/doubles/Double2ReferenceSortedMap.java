/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ReferenceMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Double2ReferenceSortedMap<V>
extends Double2ReferenceMap<V>,
SortedMap<Double, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, V>> entrySet();

    @Override
    public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public ReferenceCollection<V> values();

    public DoubleComparator comparator();

    public Double2ReferenceSortedMap<V> subMap(Double var1, Double var2);

    public Double2ReferenceSortedMap<V> headMap(Double var1);

    public Double2ReferenceSortedMap<V> tailMap(Double var1);

    public Double2ReferenceSortedMap<V> subMap(double var1, double var3);

    public Double2ReferenceSortedMap<V> headMap(double var1);

    public Double2ReferenceSortedMap<V> tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Double2ReferenceMap.Entry<V>>,
    Double2ReferenceMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> fastIterator(Double2ReferenceMap.Entry<V> var1);
    }
}

