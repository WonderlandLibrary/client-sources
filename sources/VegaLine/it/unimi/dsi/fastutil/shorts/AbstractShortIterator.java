/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortIterator;

public abstract class AbstractShortIterator
implements ShortIterator {
    protected AbstractShortIterator() {
    }

    @Override
    public short nextShort() {
        return this.next();
    }

    @Override
    @Deprecated
    public Short next() {
        return this.nextShort();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int skip(int n) {
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.nextShort();
        }
        return n - i - 1;
    }
}

