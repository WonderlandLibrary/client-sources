/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ShortMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Double2ShortSortedMap
extends Double2ShortMap,
SortedMap<Double, Short> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, Short>> entrySet();

    public ObjectSortedSet<Double2ShortMap.Entry> double2ShortEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public ShortCollection values();

    public DoubleComparator comparator();

    public Double2ShortSortedMap subMap(Double var1, Double var2);

    public Double2ShortSortedMap headMap(Double var1);

    public Double2ShortSortedMap tailMap(Double var1);

    public Double2ShortSortedMap subMap(double var1, double var3);

    public Double2ShortSortedMap headMap(double var1);

    public Double2ShortSortedMap tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Double2ShortMap.Entry>,
    Double2ShortMap.FastEntrySet {
        public ObjectBidirectionalIterator<Double2ShortMap.Entry> fastIterator(Double2ShortMap.Entry var1);
    }
}

