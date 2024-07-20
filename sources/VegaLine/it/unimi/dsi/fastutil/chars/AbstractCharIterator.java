/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharIterator;

public abstract class AbstractCharIterator
implements CharIterator {
    protected AbstractCharIterator() {
    }

    @Override
    public char nextChar() {
        return this.next().charValue();
    }

    @Override
    @Deprecated
    public Character next() {
        return Character.valueOf(this.nextChar());
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int skip(int n) {
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.nextChar();
        }
        return n - i - 1;
    }
}

