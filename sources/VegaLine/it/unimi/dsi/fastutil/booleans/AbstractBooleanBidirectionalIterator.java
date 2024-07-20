/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator;

public abstract class AbstractBooleanBidirectionalIterator
extends AbstractBooleanIterator
implements BooleanBidirectionalIterator {
    protected AbstractBooleanBidirectionalIterator() {
    }

    @Override
    public boolean previousBoolean() {
        return this.previous();
    }

    @Override
    public Boolean previous() {
        return this.previousBoolean();
    }

    @Override
    public int back(int n) {
        int i = n;
        while (i-- != 0 && this.hasPrevious()) {
            this.previousBoolean();
        }
        return n - i - 1;
    }
}

