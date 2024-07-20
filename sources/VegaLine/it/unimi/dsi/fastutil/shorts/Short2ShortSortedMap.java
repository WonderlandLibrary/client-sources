/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2ShortSortedMap
extends Short2ShortMap,
SortedMap<Short, Short> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, Short>> entrySet();

    public ObjectSortedSet<Short2ShortMap.Entry> short2ShortEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public ShortCollection values();

    public ShortComparator comparator();

    public Short2ShortSortedMap subMap(Short var1, Short var2);

    public Short2ShortSortedMap headMap(Short var1);

    public Short2ShortSortedMap tailMap(Short var1);

    public Short2ShortSortedMap subMap(short var1, short var2);

    public Short2ShortSortedMap headMap(short var1);

    public Short2ShortSortedMap tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Short2ShortMap.Entry>,
    Short2ShortMap.FastEntrySet {
        public ObjectBidirectionalIterator<Short2ShortMap.Entry> fastIterator(Short2ShortMap.Entry var1);
    }
}

