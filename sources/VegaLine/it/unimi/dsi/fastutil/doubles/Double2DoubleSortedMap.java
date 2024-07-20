/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2DoubleMap;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Double2DoubleSortedMap
extends Double2DoubleMap,
SortedMap<Double, Double> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, Double>> entrySet();

    public ObjectSortedSet<Double2DoubleMap.Entry> double2DoubleEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public DoubleCollection values();

    public DoubleComparator comparator();

    public Double2DoubleSortedMap subMap(Double var1, Double var2);

    public Double2DoubleSortedMap headMap(Double var1);

    public Double2DoubleSortedMap tailMap(Double var1);

    public Double2DoubleSortedMap subMap(double var1, double var3);

    public Double2DoubleSortedMap headMap(double var1);

    public Double2DoubleSortedMap tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Double2DoubleMap.Entry>,
    Double2DoubleMap.FastEntrySet {
        public ObjectBidirectionalIterator<Double2DoubleMap.Entry> fastIterator(Double2DoubleMap.Entry var1);
    }
}

