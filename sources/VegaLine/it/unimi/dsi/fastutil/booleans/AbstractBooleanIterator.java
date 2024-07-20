/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanIterator;

public abstract class AbstractBooleanIterator
implements BooleanIterator {
    protected AbstractBooleanIterator() {
    }

    @Override
    public boolean nextBoolean() {
        return this.next();
    }

    @Override
    @Deprecated
    public Boolean next() {
        return this.nextBoolean();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int skip(int n) {
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.nextBoolean();
        }
        return n - i - 1;
    }
}

