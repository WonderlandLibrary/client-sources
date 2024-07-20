/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;

public abstract class AbstractShortBidirectionalIterator
extends AbstractShortIterator
implements ShortBidirectionalIterator {
    protected AbstractShortBidirectionalIterator() {
    }

    @Override
    public short previousShort() {
        return this.previous();
    }

    @Override
    public Short previous() {
        return this.previousShort();
    }

    @Override
    public int back(int n) {
        int i = n;
        while (i-- != 0 && this.hasPrevious()) {
            this.previousShort();
        }
        return n - i - 1;
    }
}

