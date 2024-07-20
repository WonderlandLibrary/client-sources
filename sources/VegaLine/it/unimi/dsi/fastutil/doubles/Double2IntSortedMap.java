/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2IntMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Double2IntSortedMap
extends Double2IntMap,
SortedMap<Double, Integer> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet();

    public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public IntCollection values();

    public DoubleComparator comparator();

    public Double2IntSortedMap subMap(Double var1, Double var2);

    public Double2IntSortedMap headMap(Double var1);

    public Double2IntSortedMap tailMap(Double var1);

    public Double2IntSortedMap subMap(double var1, double var3);

    public Double2IntSortedMap headMap(double var1);

    public Double2IntSortedMap tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Double2IntMap.Entry>,
    Double2IntMap.FastEntrySet {
        public ObjectBidirectionalIterator<Double2IntMap.Entry> fastIterator(Double2IntMap.Entry var1);
    }
}

