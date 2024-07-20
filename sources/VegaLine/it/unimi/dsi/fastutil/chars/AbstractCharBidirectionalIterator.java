/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;

public abstract class AbstractCharBidirectionalIterator
extends AbstractCharIterator
implements CharBidirectionalIterator {
    protected AbstractCharBidirectionalIterator() {
    }

    @Override
    public char previousChar() {
        return this.previous().charValue();
    }

    @Override
    public Character previous() {
        return Character.valueOf(this.previousChar());
    }

    @Override
    public int back(int n) {
        int i = n;
        while (i-- != 0 && this.hasPrevious()) {
            this.previousChar();
        }
        return n - i - 1;
    }
}

