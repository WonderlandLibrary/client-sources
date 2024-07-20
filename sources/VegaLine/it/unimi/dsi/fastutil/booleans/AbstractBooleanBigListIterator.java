/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanBidirectionalIterator;
import it.unimi.dsi.fastutil.booleans.BooleanBigListIterator;

public abstract class AbstractBooleanBigListIterator
extends AbstractBooleanBidirectionalIterator
implements BooleanBigListIterator {
    protected AbstractBooleanBigListIterator() {
    }

    @Override
    public void set(Boolean ok) {
        this.set((boolean)ok);
    }

    @Override
    public void add(Boolean ok) {
        this.add((boolean)ok);
    }

    @Override
    public void set(boolean k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(boolean k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) {
        long i = n;
        while (i-- != 0L && this.hasNext()) {
            this.nextBoolean();
        }
        return n - i - 1L;
    }

    public long back(long n) {
        long i = n;
        while (i-- != 0L && this.hasPrevious()) {
            this.previousBoolean();
        }
        return n - i - 1L;
    }
}

