/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Byte2IntSortedMap
extends Byte2IntMap,
SortedMap<Byte, Integer> {
    @Override
    public ObjectSortedSet<Map.Entry<Byte, Integer>> entrySet();

    public ObjectSortedSet<Byte2IntMap.Entry> byte2IntEntrySet();

    @Override
    public ByteSortedSet keySet();

    @Override
    public IntCollection values();

    public ByteComparator comparator();

    public Byte2IntSortedMap subMap(Byte var1, Byte var2);

    public Byte2IntSortedMap headMap(Byte var1);

    public Byte2IntSortedMap tailMap(Byte var1);

    public Byte2IntSortedMap subMap(byte var1, byte var2);

    public Byte2IntSortedMap headMap(byte var1);

    public Byte2IntSortedMap tailMap(byte var1);

    public byte firstByteKey();

    public byte lastByteKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Byte2IntMap.Entry>,
    Byte2IntMap.FastEntrySet {
        public ObjectBidirectionalIterator<Byte2IntMap.Entry> fastIterator(Byte2IntMap.Entry var1);
    }
}

