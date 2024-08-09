/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;

public interface LongBigListIterator
extends LongBidirectionalIterator,
BigListIterator<Long> {
    @Override
    default public void set(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(long l) {
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

    default public long skip(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasNext()) {
            this.nextLong();
        }
        return l - l2 - 1L;
    }

    default public long back(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasPrevious()) {
            this.previousLong();
        }
        return l - l2 - 1L;
    }

    @Override
    default public int skip(int n) {
        return SafeMath.safeLongToInt(this.skip((long)n));
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

