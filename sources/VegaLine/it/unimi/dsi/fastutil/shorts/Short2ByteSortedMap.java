/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2ByteSortedMap
extends Short2ByteMap,
SortedMap<Short, Byte> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet();

    public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public ByteCollection values();

    public ShortComparator comparator();

    public Short2ByteSortedMap subMap(Short var1, Short var2);

    public Short2ByteSortedMap headMap(Short var1);

    public Short2ByteSortedMap tailMap(Short var1);

    public Short2ByteSortedMap subMap(short var1, short var2);

    public Short2ByteSortedMap headMap(short var1);

    public Short2ByteSortedMap tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Short2ByteMap.Entry>,
    Short2ByteMap.FastEntrySet {
        public ObjectBidirectionalIterator<Short2ByteMap.Entry> fastIterator(Short2ByteMap.Entry var1);
    }
}

