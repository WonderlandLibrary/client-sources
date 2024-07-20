/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2FloatMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Double2FloatSortedMap
extends Double2FloatMap,
SortedMap<Double, Float> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, Float>> entrySet();

    public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public FloatCollection values();

    public DoubleComparator comparator();

    public Double2FloatSortedMap subMap(Double var1, Double var2);

    public Double2FloatSortedMap headMap(Double var1);

    public Double2FloatSortedMap tailMap(Double var1);

    public Double2FloatSortedMap subMap(double var1, double var3);

    public Double2FloatSortedMap headMap(double var1);

    public Double2FloatSortedMap tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Double2FloatMap.Entry>,
    Double2FloatMap.FastEntrySet {
        public ObjectBidirectionalIterator<Double2FloatMap.Entry> fastIterator(Double2FloatMap.Entry var1);
    }
}

