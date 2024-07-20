/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;

public abstract class AbstractFloatBidirectionalIterator
extends AbstractFloatIterator
implements FloatBidirectionalIterator {
    protected AbstractFloatBidirectionalIterator() {
    }

    @Override
    public float previousFloat() {
        return this.previous().floatValue();
    }

    @Override
    public Float previous() {
        return Float.valueOf(this.previousFloat());
    }

    @Override
    public int back(int n) {
        int i = n;
        while (i-- != 0 && this.hasPrevious()) {
            this.previousFloat();
        }
        return n - i - 1;
    }
}

