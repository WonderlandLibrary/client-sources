/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;

public abstract class AbstractFloatListIterator
extends AbstractFloatBidirectionalIterator
implements FloatListIterator {
    protected AbstractFloatListIterator() {
    }

    @Override
    public void set(Float ok) {
        this.set(ok.floatValue());
    }

    @Override
    public void add(Float ok) {
        this.add(ok.floatValue());
    }

    @Override
    public void set(float k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(float k) {
        throw new UnsupportedOperationException();
    }
}

