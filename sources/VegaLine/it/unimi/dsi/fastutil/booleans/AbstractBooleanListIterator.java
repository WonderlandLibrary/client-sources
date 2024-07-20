/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanBidirectionalIterator;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;

public abstract class AbstractBooleanListIterator
extends AbstractBooleanBidirectionalIterator
implements BooleanListIterator {
    protected AbstractBooleanListIterator() {
    }

    @Override
    public void set(Boolean ok) {
        this.set((boolean)ok);
    }

    @Override
    public void add(Boolean ok) {
        this.add((boolean)ok);
    }

    @Override
    public void set(boolean k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(boolean k) {
        throw new UnsupportedOperationException();
    }
}

