/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;

public abstract class AbstractIntBidirectionalIterator
extends AbstractIntIterator
implements IntBidirectionalIterator {
    protected AbstractIntBidirectionalIterator() {
    }

    @Override
    public int previousInt() {
        return this.previous();
    }

    @Override
    public Integer previous() {
        return this.previousInt();
    }

    @Override
    public int back(int n) {
        int i = n;
        while (i-- != 0 && this.hasPrevious()) {
            this.previousInt();
        }
        return n - i - 1;
    }
}

