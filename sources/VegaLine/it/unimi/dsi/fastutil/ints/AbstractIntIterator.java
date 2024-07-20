/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntIterator;

public abstract class AbstractIntIterator
implements IntIterator {
    protected AbstractIntIterator() {
    }

    @Override
    public int nextInt() {
        return this.next();
    }

    @Override
    @Deprecated
    public Integer next() {
        return this.nextInt();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int skip(int n) {
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.nextInt();
        }
        return n - i - 1;
    }
}

