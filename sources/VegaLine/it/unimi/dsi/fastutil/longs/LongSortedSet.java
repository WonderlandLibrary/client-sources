/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.SortedSet;

public interface LongSortedSet
extends LongSet,
SortedSet<Long> {
    public LongBidirectionalIterator iterator(long var1);

    @Override
    @Deprecated
    public LongBidirectionalIterator longIterator();

    @Override
    public LongBidirectionalIterator iterator();

    public LongSortedSet subSet(Long var1, Long var2);

    public LongSortedSet headSet(Long var1);

    public LongSortedSet tailSet(Long var1);

    public LongComparator comparator();

    public LongSortedSet subSet(long var1, long var3);

    public LongSortedSet headSet(long var1);

    public LongSortedSet tailSet(long var1);

    public long firstLong();

    public long lastLong();
}

