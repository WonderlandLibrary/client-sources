/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharBigListIterator;

public abstract class AbstractCharBigListIterator
extends AbstractCharBidirectionalIterator
implements CharBigListIterator {
    protected AbstractCharBigListIterator() {
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

    @Override
    public long skip(long n) {
        long i = n;
        while (i-- != 0L && this.hasNext()) {
            this.nextChar();
        }
        return n - i - 1L;
    }

    public long back(long n) {
        long i = n;
        while (i-- != 0L && this.hasPrevious()) {
            this.previousChar();
        }
        return n - i - 1L;
    }
}

