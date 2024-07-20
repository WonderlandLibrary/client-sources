/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ShortMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Byte2ShortSortedMap
extends Byte2ShortMap,
SortedMap<Byte, Short> {
    @Override
    public ObjectSortedSet<Map.Entry<Byte, Short>> entrySet();

    public ObjectSortedSet<Byte2ShortMap.Entry> byte2ShortEntrySet();

    @Override
    public ByteSortedSet keySet();

    @Override
    public ShortCollection values();

    public ByteComparator comparator();

    public Byte2ShortSortedMap subMap(Byte var1, Byte var2);

    public Byte2ShortSortedMap headMap(Byte var1);

    public Byte2ShortSortedMap tailMap(Byte var1);

    public Byte2ShortSortedMap subMap(byte var1, byte var2);

    public Byte2ShortSortedMap headMap(byte var1);

    public Byte2ShortSortedMap tailMap(byte var1);

    public byte firstByteKey();

    public byte lastByteKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Byte2ShortMap.Entry>,
    Byte2ShortMap.FastEntrySet {
        public ObjectBidirectionalIterator<Byte2ShortMap.Entry> fastIterator(Byte2ShortMap.Entry var1);
    }
}

