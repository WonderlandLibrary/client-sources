/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;

public abstract class AbstractDoubleListIterator
extends AbstractDoubleBidirectionalIterator
implements DoubleListIterator {
    protected AbstractDoubleListIterator() {
    }

    @Override
    public void set(Double ok) {
        this.set((double)ok);
    }

    @Override
    public void add(Double ok) {
        this.add((double)ok);
    }

    @Override
    public void set(double k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(double k) {
        throw new UnsupportedOperationException();
    }
}

