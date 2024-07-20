/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntBigListIterator;

public abstract class AbstractIntBigListIterator
extends AbstractIntBidirectionalIterator
implements IntBigListIterator {
    protected AbstractIntBigListIterator() {
    }

    @Override
    public void set(Integer ok) {
        this.set((int)ok);
    }

    @Override
    public void add(Integer ok) {
        this.add((int)ok);
    }

    @Override
    public void set(int k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) {
        long i = n;
        while (i-- != 0L && this.hasNext()) {
            this.nextInt();
        }
        return n - i - 1L;
    }

    public long back(long n) {
        long i = n;
        while (i-- != 0L && this.hasPrevious()) {
            this.previousInt();
        }
        return n - i - 1L;
    }
}

