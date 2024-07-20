/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;

public abstract class AbstractLongBidirectionalIterator
extends AbstractLongIterator
implements LongBidirectionalIterator {
    protected AbstractLongBidirectionalIterator() {
    }

    @Override
    public long previousLong() {
        return this.previous();
    }

    @Override
    public Long previous() {
        return this.previousLong();
    }

    @Override
    public int back(int n) {
        int i = n;
        while (i-- != 0 && this.hasPrevious()) {
            this.previousLong();
        }
        return n - i - 1;
    }
}

