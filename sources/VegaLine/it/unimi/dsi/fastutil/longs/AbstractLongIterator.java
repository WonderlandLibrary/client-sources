/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongIterator;

public abstract class AbstractLongIterator
implements LongIterator {
    protected AbstractLongIterator() {
    }

    @Override
    public long nextLong() {
        return this.next();
    }

    @Override
    @Deprecated
    public Long next() {
        return this.nextLong();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int skip(int n) {
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.nextLong();
        }
        return n - i - 1;
    }
}

