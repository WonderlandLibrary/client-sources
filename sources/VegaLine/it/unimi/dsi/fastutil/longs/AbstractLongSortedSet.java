/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;

public abstract class AbstractLongSortedSet
extends AbstractLongSet
implements LongSortedSet {
    protected AbstractLongSortedSet() {
    }

    @Override
    @Deprecated
    public LongSortedSet headSet(Long to) {
        return this.headSet((long)to);
    }

    @Override
    @Deprecated
    public LongSortedSet tailSet(Long from) {
        return this.tailSet((long)from);
    }

    @Override
    @Deprecated
    public LongSortedSet subSet(Long from, Long to) {
        return this.subSet((long)from, (long)to);
    }

    @Override
    @Deprecated
    public Long first() {
        return this.firstLong();
    }

    @Override
    @Deprecated
    public Long last() {
        return this.lastLong();
    }

    @Override
    @Deprecated
    public LongBidirectionalIterator longIterator() {
        return this.iterator();
    }

    @Override
    public abstract LongBidirectionalIterator iterator();
}

