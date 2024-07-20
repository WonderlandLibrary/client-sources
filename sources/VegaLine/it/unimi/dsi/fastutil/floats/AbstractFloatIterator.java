/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatIterator;

public abstract class AbstractFloatIterator
implements FloatIterator {
    protected AbstractFloatIterator() {
    }

    @Override
    public float nextFloat() {
        return this.next().floatValue();
    }

    @Override
    @Deprecated
    public Float next() {
        return Float.valueOf(this.nextFloat());
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int skip(int n) {
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.nextFloat();
        }
        return n - i - 1;
    }
}

