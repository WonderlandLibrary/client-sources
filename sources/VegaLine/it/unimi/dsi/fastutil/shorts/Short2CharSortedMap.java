/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2CharMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2CharSortedMap
extends Short2CharMap,
SortedMap<Short, Character> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, Character>> entrySet();

    public ObjectSortedSet<Short2CharMap.Entry> short2CharEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public CharCollection values();

    public ShortComparator comparator();

    public Short2CharSortedMap subMap(Short var1, Short var2);

    public Short2CharSortedMap headMap(Short var1);

    public Short2CharSortedMap tailMap(Short var1);

    public Short2CharSortedMap subMap(short var1, short var2);

    public Short2CharSortedMap headMap(short var1);

    public Short2CharSortedMap tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Short2CharMap.Entry>,
    Short2CharMap.FastEntrySet {
        public ObjectBidirectionalIterator<Short2CharMap.Entry> fastIterator(Short2CharMap.Entry var1);
    }
}

