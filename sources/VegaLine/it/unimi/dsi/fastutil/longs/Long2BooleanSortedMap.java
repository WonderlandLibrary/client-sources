/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Long2BooleanSortedMap
extends Long2BooleanMap,
SortedMap<Long, Boolean> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, Boolean>> entrySet();

    public ObjectSortedSet<Long2BooleanMap.Entry> long2BooleanEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public BooleanCollection values();

    public LongComparator comparator();

    public Long2BooleanSortedMap subMap(Long var1, Long var2);

    public Long2BooleanSortedMap headMap(Long var1);

    public Long2BooleanSortedMap tailMap(Long var1);

    public Long2BooleanSortedMap subMap(long var1, long var3);

    public Long2BooleanSortedMap headMap(long var1);

    public Long2BooleanSortedMap tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Long2BooleanMap.Entry>,
    Long2BooleanMap.FastEntrySet {
        public ObjectBidirectionalIterator<Long2BooleanMap.Entry> fastIterator(Long2BooleanMap.Entry var1);
    }
}

