/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Long2FloatSortedMap
extends Long2FloatMap,
SortedMap<Long, Float> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, Float>> entrySet();

    public ObjectSortedSet<Long2FloatMap.Entry> long2FloatEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public FloatCollection values();

    public LongComparator comparator();

    public Long2FloatSortedMap subMap(Long var1, Long var2);

    public Long2FloatSortedMap headMap(Long var1);

    public Long2FloatSortedMap tailMap(Long var1);

    public Long2FloatSortedMap subMap(long var1, long var3);

    public Long2FloatSortedMap headMap(long var1);

    public Long2FloatSortedMap tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Long2FloatMap.Entry>,
    Long2FloatMap.FastEntrySet {
        public ObjectBidirectionalIterator<Long2FloatMap.Entry> fastIterator(Long2FloatMap.Entry var1);
    }
}

