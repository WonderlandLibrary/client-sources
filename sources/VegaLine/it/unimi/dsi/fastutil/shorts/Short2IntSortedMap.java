/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2IntSortedMap
extends Short2IntMap,
SortedMap<Short, Integer> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, Integer>> entrySet();

    public ObjectSortedSet<Short2IntMap.Entry> short2IntEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public IntCollection values();

    public ShortComparator comparator();

    public Short2IntSortedMap subMap(Short var1, Short var2);

    public Short2IntSortedMap headMap(Short var1);

    public Short2IntSortedMap tailMap(Short var1);

    public Short2IntSortedMap subMap(short var1, short var2);

    public Short2IntSortedMap headMap(short var1);

    public Short2IntSortedMap tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Short2IntMap.Entry>,
    Short2IntMap.FastEntrySet {
        public ObjectBidirectionalIterator<Short2IntMap.Entry> fastIterator(Short2IntMap.Entry var1);
    }
}

