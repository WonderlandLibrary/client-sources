/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2FloatSortedMap
extends Short2FloatMap,
SortedMap<Short, Float> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, Float>> entrySet();

    public ObjectSortedSet<Short2FloatMap.Entry> short2FloatEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public FloatCollection values();

    public ShortComparator comparator();

    public Short2FloatSortedMap subMap(Short var1, Short var2);

    public Short2FloatSortedMap headMap(Short var1);

    public Short2FloatSortedMap tailMap(Short var1);

    public Short2FloatSortedMap subMap(short var1, short var2);

    public Short2FloatSortedMap headMap(short var1);

    public Short2FloatSortedMap tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Short2FloatMap.Entry>,
    Short2FloatMap.FastEntrySet {
        public ObjectBidirectionalIterator<Short2FloatMap.Entry> fastIterator(Short2FloatMap.Entry var1);
    }
}

