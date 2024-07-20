/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;

public abstract class AbstractShortListIterator
extends AbstractShortBidirectionalIterator
implements ShortListIterator {
    protected AbstractShortListIterator() {
    }

    @Override
    public void set(Short ok) {
        this.set((short)ok);
    }

    @Override
    public void add(Short ok) {
        this.add((short)ok);
    }

    @Override
    public void set(short k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(short k) {
        throw new UnsupportedOperationException();
    }
}

