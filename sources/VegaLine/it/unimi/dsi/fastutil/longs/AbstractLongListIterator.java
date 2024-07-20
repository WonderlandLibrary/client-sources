/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongListIterator;

public abstract class AbstractLongListIterator
extends AbstractLongBidirectionalIterator
implements LongListIterator {
    protected AbstractLongListIterator() {
    }

    @Override
    public void set(Long ok) {
        this.set((long)ok);
    }

    @Override
    public void add(Long ok) {
        this.add((long)ok);
    }

    @Override
    public void set(long k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(long k) {
        throw new UnsupportedOperationException();
    }
}

