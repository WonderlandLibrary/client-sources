/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Byte2BooleanSortedMap
extends Byte2BooleanMap,
SortedMap<Byte, Boolean> {
    @Override
    public ObjectSortedSet<Map.Entry<Byte, Boolean>> entrySet();

    public ObjectSortedSet<Byte2BooleanMap.Entry> byte2BooleanEntrySet();

    @Override
    public ByteSortedSet keySet();

    @Override
    public BooleanCollection values();

    public ByteComparator comparator();

    public Byte2BooleanSortedMap subMap(Byte var1, Byte var2);

    public Byte2BooleanSortedMap headMap(Byte var1);

    public Byte2BooleanSortedMap tailMap(Byte var1);

    public Byte2BooleanSortedMap subMap(byte var1, byte var2);

    public Byte2BooleanSortedMap headMap(byte var1);

    public Byte2BooleanSortedMap tailMap(byte var1);

    public byte firstByteKey();

    public byte lastByteKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Byte2BooleanMap.Entry>,
    Byte2BooleanMap.FastEntrySet {
        public ObjectBidirectionalIterator<Byte2BooleanMap.Entry> fastIterator(Byte2BooleanMap.Entry var1);
    }
}

