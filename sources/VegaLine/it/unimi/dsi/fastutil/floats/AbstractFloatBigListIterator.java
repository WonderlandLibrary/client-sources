/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatBigListIterator;

public abstract class AbstractFloatBigListIterator
extends AbstractFloatBidirectionalIterator
implements FloatBigListIterator {
    protected AbstractFloatBigListIterator() {
    }

    @Override
    public void set(Float ok) {
        this.set(ok.floatValue());
    }

    @Override
    public void add(Float ok) {
        this.add(ok.floatValue());
    }

    @Override
    public void set(float k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(float k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) {
        long i = n;
        while (i-- != 0L && this.hasNext()) {
            this.nextFloat();
        }
        return n - i - 1L;
    }

    public long back(long n) {
        long i = n;
        while (i-- != 0L && this.hasPrevious()) {
            this.previousFloat();
        }
        return n - i - 1L;
    }
}

