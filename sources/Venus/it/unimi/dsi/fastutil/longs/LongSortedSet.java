/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongBidirectionalIterable;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongSortedSet
extends LongSet,
SortedSet<Long>,
LongBidirectionalIterable {
    public LongBidirectionalIterator iterator(long var1);

    @Override
    public LongBidirectionalIterator iterator();

    public LongSortedSet subSet(long var1, long var3);

    public LongSortedSet headSet(long var1);

    public LongSortedSet tailSet(long var1);

    public LongComparator comparator();

    public long firstLong();

    public long lastLong();

    @Deprecated
    default public LongSortedSet subSet(Long l, Long l2) {
        return this.subSet((long)l, (long)l2);
    }

    @Deprecated
    default public LongSortedSet headSet(Long l) {
        return this.headSet((long)l);
    }

    @Deprecated
    default public LongSortedSet tailSet(Long l) {
        return this.tailSet((long)l);
    }

    @Override
    @Deprecated
    default public Long first() {
        return this.firstLong();
    }

    @Override
    @Deprecated
    default public Long last() {
        return this.lastLong();
    }

    @Override
    default public LongIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    @Deprecated
    default public Object last() {
        return this.last();
    }

    @Override
    @Deprecated
    default public Object first() {
        return this.first();
    }

    @Override
    @Deprecated
    default public SortedSet tailSet(Object object) {
        return this.tailSet((Long)object);
    }

    @Override
    @Deprecated
    default public SortedSet headSet(Object object) {
        return this.headSet((Long)object);
    }

    @Override
    @Deprecated
    default public SortedSet subSet(Object object, Object object2) {
        return this.subSet((Long)object, (Long)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }
}

