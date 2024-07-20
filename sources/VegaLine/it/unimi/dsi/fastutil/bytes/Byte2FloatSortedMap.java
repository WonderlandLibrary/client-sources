/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2FloatMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Byte2FloatSortedMap
extends Byte2FloatMap,
SortedMap<Byte, Float> {
    @Override
    public ObjectSortedSet<Map.Entry<Byte, Float>> entrySet();

    public ObjectSortedSet<Byte2FloatMap.Entry> byte2FloatEntrySet();

    @Override
    public ByteSortedSet keySet();

    @Override
    public FloatCollection values();

    public ByteComparator comparator();

    public Byte2FloatSortedMap subMap(Byte var1, Byte var2);

    public Byte2FloatSortedMap headMap(Byte var1);

    public Byte2FloatSortedMap tailMap(Byte var1);

    public Byte2FloatSortedMap subMap(byte var1, byte var2);

    public Byte2FloatSortedMap headMap(byte var1);

    public Byte2FloatSortedMap tailMap(byte var1);

    public byte firstByteKey();

    public byte lastByteKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Byte2FloatMap.Entry>,
    Byte2FloatMap.FastEntrySet {
        public ObjectBidirectionalIterator<Byte2FloatMap.Entry> fastIterator(Byte2FloatMap.Entry var1);
    }
}

