/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.longs.Long2CharMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Long2CharSortedMap
extends Long2CharMap,
SortedMap<Long, Character> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, Character>> entrySet();

    public ObjectSortedSet<Long2CharMap.Entry> long2CharEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public CharCollection values();

    public LongComparator comparator();

    public Long2CharSortedMap subMap(Long var1, Long var2);

    public Long2CharSortedMap headMap(Long var1);

    public Long2CharSortedMap tailMap(Long var1);

    public Long2CharSortedMap subMap(long var1, long var3);

    public Long2CharSortedMap headMap(long var1);

    public Long2CharSortedMap tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Long2CharMap.Entry>,
    Long2CharMap.FastEntrySet {
        public ObjectBidirectionalIterator<Long2CharMap.Entry> fastIterator(Long2CharMap.Entry var1);
    }
}

