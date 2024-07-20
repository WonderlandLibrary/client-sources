/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;

public abstract class AbstractIntListIterator
extends AbstractIntBidirectionalIterator
implements IntListIterator {
    protected AbstractIntListIterator() {
    }

    @Override
    public void set(Integer ok) {
        this.set((int)ok);
    }

    @Override
    public void add(Integer ok) {
        this.add((int)ok);
    }

    @Override
    public void set(int k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int k) {
        throw new UnsupportedOperationException();
    }
}

