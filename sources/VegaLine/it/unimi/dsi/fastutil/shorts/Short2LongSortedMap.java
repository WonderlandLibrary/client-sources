/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2LongSortedMap
extends Short2LongMap,
SortedMap<Short, Long> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, Long>> entrySet();

    public ObjectSortedSet<Short2LongMap.Entry> short2LongEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public LongCollection values();

    public ShortComparator comparator();

    public Short2LongSortedMap subMap(Short var1, Short var2);

    public Short2LongSortedMap headMap(Short var1);

    public Short2LongSortedMap tailMap(Short var1);

    public Short2LongSortedMap subMap(short var1, short var2);

    public Short2LongSortedMap headMap(short var1);

    public Short2LongSortedMap tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Short2LongMap.Entry>,
    Short2LongMap.FastEntrySet {
        public ObjectBidirectionalIterator<Short2LongMap.Entry> fastIterator(Short2LongMap.Entry var1);
    }
}

