/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharListIterator;

public abstract class AbstractCharListIterator
extends AbstractCharBidirectionalIterator
implements CharListIterator {
    protected AbstractCharListIterator() {
    }

    @Override
    public void set(Character ok) {
        this.set(ok.charValue());
    }

    @Override
    public void add(Character ok) {
        this.add(ok.charValue());
    }

    @Override
    public void set(char k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(char k) {
        throw new UnsupportedOperationException();
    }
}

