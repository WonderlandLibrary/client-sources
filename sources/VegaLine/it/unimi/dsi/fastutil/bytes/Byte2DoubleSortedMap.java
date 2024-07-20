/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Byte2DoubleSortedMap
extends Byte2DoubleMap,
SortedMap<Byte, Double> {
    @Override
    public ObjectSortedSet<Map.Entry<Byte, Double>> entrySet();

    public ObjectSortedSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet();

    @Override
    public ByteSortedSet keySet();

    @Override
    public DoubleCollection values();

    public ByteComparator comparator();

    public Byte2DoubleSortedMap subMap(Byte var1, Byte var2);

    public Byte2DoubleSortedMap headMap(Byte var1);

    public Byte2DoubleSortedMap tailMap(Byte var1);

    public Byte2DoubleSortedMap subMap(byte var1, byte var2);

    public Byte2DoubleSortedMap headMap(byte var1);

    public Byte2DoubleSortedMap tailMap(byte var1);

    public byte firstByteKey();

    public byte lastByteKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Byte2DoubleMap.Entry>,
    Byte2DoubleMap.FastEntrySet {
        public ObjectBidirectionalIterator<Byte2DoubleMap.Entry> fastIterator(Byte2DoubleMap.Entry var1);
    }
}

