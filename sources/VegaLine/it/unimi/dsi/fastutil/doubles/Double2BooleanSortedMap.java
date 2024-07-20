/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Double2BooleanSortedMap
extends Double2BooleanMap,
SortedMap<Double, Boolean> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, Boolean>> entrySet();

    public ObjectSortedSet<Double2BooleanMap.Entry> double2BooleanEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public BooleanCollection values();

    public DoubleComparator comparator();

    public Double2BooleanSortedMap subMap(Double var1, Double var2);

    public Double2BooleanSortedMap headMap(Double var1);

    public Double2BooleanSortedMap tailMap(Double var1);

    public Double2BooleanSortedMap subMap(double var1, double var3);

    public Double2BooleanSortedMap headMap(double var1);

    public Double2BooleanSortedMap tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Double2BooleanMap.Entry>,
    Double2BooleanMap.FastEntrySet {
        public ObjectBidirectionalIterator<Double2BooleanMap.Entry> fastIterator(Double2BooleanMap.Entry var1);
    }
}

