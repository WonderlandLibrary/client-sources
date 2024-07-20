/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2LongMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Double2LongSortedMap
extends Double2LongMap,
SortedMap<Double, Long> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, Long>> entrySet();

    public ObjectSortedSet<Double2LongMap.Entry> double2LongEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public LongCollection values();

    public DoubleComparator comparator();

    public Double2LongSortedMap subMap(Double var1, Double var2);

    public Double2LongSortedMap headMap(Double var1);

    public Double2LongSortedMap tailMap(Double var1);

    public Double2LongSortedMap subMap(double var1, double var3);

    public Double2LongSortedMap headMap(double var1);

    public Double2LongSortedMap tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Double2LongMap.Entry>,
    Double2LongMap.FastEntrySet {
        public ObjectBidirectionalIterator<Double2LongMap.Entry> fastIterator(Double2LongMap.Entry var1);
    }
}

