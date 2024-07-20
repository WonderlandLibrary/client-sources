/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2BooleanSortedMap
extends Short2BooleanMap,
SortedMap<Short, Boolean> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, Boolean>> entrySet();

    public ObjectSortedSet<Short2BooleanMap.Entry> short2BooleanEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public BooleanCollection values();

    public ShortComparator comparator();

    public Short2BooleanSortedMap subMap(Short var1, Short var2);

    public Short2BooleanSortedMap headMap(Short var1);

    public Short2BooleanSortedMap tailMap(Short var1);

    public Short2BooleanSortedMap subMap(short var1, short var2);

    public Short2BooleanSortedMap headMap(short var1);

    public Short2BooleanSortedMap tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Short2BooleanMap.Entry>,
    Short2BooleanMap.FastEntrySet {
        public ObjectBidirectionalIterator<Short2BooleanMap.Entry> fastIterator(Short2BooleanMap.Entry var1);
    }
}

