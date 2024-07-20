/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortBigListIterator;

public abstract class AbstractShortBigListIterator
extends AbstractShortBidirectionalIterator
implements ShortBigListIterator {
    protected AbstractShortBigListIterator() {
    }

    @Override
    public void set(Short ok) {
        this.set((short)ok);
    }

    @Override
    public void add(Short ok) {
        this.add((short)ok);
    }

    @Override
    public void set(short k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(short k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) {
        long i = n;
        while (i-- != 0L && this.hasNext()) {
            this.nextShort();
        }
        return n - i - 1L;
    }

    public long back(long n) {
        long i = n;
        while (i-- != 0L && this.hasPrevious()) {
            this.previousShort();
        }
        return n - i - 1L;
    }
}

