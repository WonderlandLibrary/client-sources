/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2LongMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Byte2LongSortedMap
extends Byte2LongMap,
SortedMap<Byte, Long> {
    @Override
    public ObjectSortedSet<Map.Entry<Byte, Long>> entrySet();

    public ObjectSortedSet<Byte2LongMap.Entry> byte2LongEntrySet();

    @Override
    public ByteSortedSet keySet();

    @Override
    public LongCollection values();

    public ByteComparator comparator();

    public Byte2LongSortedMap subMap(Byte var1, Byte var2);

    public Byte2LongSortedMap headMap(Byte var1);

    public Byte2LongSortedMap tailMap(Byte var1);

    public Byte2LongSortedMap subMap(byte var1, byte var2);

    public Byte2LongSortedMap headMap(byte var1);

    public Byte2LongSortedMap tailMap(byte var1);

    public byte firstByteKey();

    public byte lastByteKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Byte2LongMap.Entry>,
    Byte2LongMap.FastEntrySet {
        public ObjectBidirectionalIterator<Byte2LongMap.Entry> fastIterator(Byte2LongMap.Entry var1);
    }
}

