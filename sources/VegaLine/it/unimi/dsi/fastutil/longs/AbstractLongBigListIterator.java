/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongBigListIterator;

public abstract class AbstractLongBigListIterator
extends AbstractLongBidirectionalIterator
implements LongBigListIterator {
    protected AbstractLongBigListIterator() {
    }

    @Override
    public void set(Long ok) {
        this.set((long)ok);
    }

    @Override
    public void add(Long ok) {
        this.add((long)ok);
    }

    @Override
    public void set(long k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(long k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) {
        long i = n;
        while (i-- != 0L && this.hasNext()) {
            this.nextLong();
        }
        return n - i - 1L;
    }

    public long back(long n) {
        long i = n;
        while (i-- != 0L && this.hasPrevious()) {
            this.previousLong();
        }
        return n - i - 1L;
    }
}

