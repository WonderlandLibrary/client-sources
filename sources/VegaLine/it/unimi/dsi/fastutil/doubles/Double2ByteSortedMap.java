/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.doubles.Double2ByteMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Double2ByteSortedMap
extends Double2ByteMap,
SortedMap<Double, Byte> {
    @Override
    public ObjectSortedSet<Map.Entry<Double, Byte>> entrySet();

    public ObjectSortedSet<Double2ByteMap.Entry> double2ByteEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public ByteCollection values();

    public DoubleComparator comparator();

    public Double2ByteSortedMap subMap(Double var1, Double var2);

    public Double2ByteSortedMap headMap(Double var1);

    public Double2ByteSortedMap tailMap(Double var1);

    public Double2ByteSortedMap subMap(double var1, double var3);

    public Double2ByteSortedMap headMap(double var1);

    public Double2ByteSortedMap tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Double2ByteMap.Entry>,
    Double2ByteMap.FastEntrySet {
        public ObjectBidirectionalIterator<Double2ByteMap.Entry> fastIterator(Double2ByteMap.Entry var1);
    }
}

