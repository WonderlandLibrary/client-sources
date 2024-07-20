/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Long2ByteSortedMap
extends Long2ByteMap,
SortedMap<Long, Byte> {
    @Override
    public ObjectSortedSet<Map.Entry<Long, Byte>> entrySet();

    public ObjectSortedSet<Long2ByteMap.Entry> long2ByteEntrySet();

    @Override
    public LongSortedSet keySet();

    @Override
    public ByteCollection values();

    public LongComparator comparator();

    public Long2ByteSortedMap subMap(Long var1, Long var2);

    public Long2ByteSortedMap headMap(Long var1);

    public Long2ByteSortedMap tailMap(Long var1);

    public Long2ByteSortedMap subMap(long var1, long var3);

    public Long2ByteSortedMap headMap(long var1);

    public Long2ByteSortedMap tailMap(long var1);

    public long firstLongKey();

    public long lastLongKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Long2ByteMap.Entry>,
    Long2ByteMap.FastEntrySet {
        public ObjectBidirectionalIterator<Long2ByteMap.Entry> fastIterator(Long2ByteMap.Entry var1);
    }
}

