/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2DoubleSortedMap
extends Short2DoubleMap,
SortedMap<Short, Double> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, Double>> entrySet();

    public ObjectSortedSet<Short2DoubleMap.Entry> short2DoubleEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public DoubleCollection values();

    public ShortComparator comparator();

    public Short2DoubleSortedMap subMap(Short var1, Short var2);

    public Short2DoubleSortedMap headMap(Short var1);

    public Short2DoubleSortedMap tailMap(Short var1);

    public Short2DoubleSortedMap subMap(short var1, short var2);

    public Short2DoubleSortedMap headMap(short var1);

    public Short2DoubleSortedMap tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Short2DoubleMap.Entry>,
    Short2DoubleMap.FastEntrySet {
        public ObjectBidirectionalIterator<Short2DoubleMap.Entry> fastIterator(Short2DoubleMap.Entry var1);
    }
}

