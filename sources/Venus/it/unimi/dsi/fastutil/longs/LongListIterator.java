/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongListIterator
extends LongBidirectionalIterator,
ListIterator<Long> {
    @Override
    default public void set(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Long l) {
        this.set((long)l);
    }

    @Override
    @Deprecated
    default public void add(Long l) {
        this.add((long)l);
    }

    @Override
    @Deprecated
    default public Long next() {
        return LongBidirectionalIterator.super.next();
    }

    @Override
    @Deprecated
    default public Long previous() {
        return LongBidirectionalIterator.super.previous();
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }

    @Override
    @Deprecated
    default public Object previous() {
        return this.previous();
    }

    @Override
    @Deprecated
    default public void add(Object object) {
        this.add((Long)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Long)object);
    }
}

