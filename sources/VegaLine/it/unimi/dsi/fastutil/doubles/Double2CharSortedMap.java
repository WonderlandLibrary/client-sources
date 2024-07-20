/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.doubles.Double2CharMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Double2CharSortedMap
extends Double2CharMap,
SortedMap<Double, Character> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, Character>> entrySet();

    public ObjectSortedSet<Double2CharMap.Entry> double2CharEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public CharCollection values();

    public DoubleComparator comparator();

    public Double2CharSortedMap subMap(Double var1, Double var2);

    public Double2CharSortedMap headMap(Double var1);

    public Double2CharSortedMap tailMap(Double var1);

    public Double2CharSortedMap subMap(double var1, double var3);

    public Double2CharSortedMap headMap(double var1);

    public Double2CharSortedMap tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Double2CharMap.Entry>,
    Double2CharMap.FastEntrySet {
        public ObjectBidirectionalIterator<Double2CharMap.Entry> fastIterator(Double2CharMap.Entry var1);
    }
}

