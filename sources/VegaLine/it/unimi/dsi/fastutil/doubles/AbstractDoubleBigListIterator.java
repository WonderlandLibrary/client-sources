/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleBigListIterator;

public abstract class AbstractDoubleBigListIterator
extends AbstractDoubleBidirectionalIterator
implements DoubleBigListIterator {
    protected AbstractDoubleBigListIterator() {
    }

    @Override
    public void set(Double ok) {
        this.set((double)ok);
    }

    @Override
    public void add(Double ok) {
        this.add((double)ok);
    }

    @Override
    public void set(double k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(double k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) {
        long i = n;
        while (i-- != 0L && this.hasNext()) {
            this.nextDouble();
        }
        return n - i - 1L;
    }

    public long back(long n) {
        long i = n;
        while (i-- != 0L && this.hasPrevious()) {
            this.previousDouble();
        }
        return n - i - 1L;
    }
}

