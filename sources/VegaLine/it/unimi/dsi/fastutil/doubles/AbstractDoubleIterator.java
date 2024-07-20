/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleIterator;

public abstract class AbstractDoubleIterator
implements DoubleIterator {
    protected AbstractDoubleIterator() {
    }

    @Override
    public double nextDouble() {
        return this.next();
    }

    @Override
    @Deprecated
    public Double next() {
        return this.nextDouble();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int skip(int n) {
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.nextDouble();
        }
        return n - i - 1;
    }
}

