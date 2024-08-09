/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.text.CharacterIterator;

public class CSCharacterIterator
implements CharacterIterator {
    private int index;
    private CharSequence seq;

    public CSCharacterIterator(CharSequence charSequence) {
        if (charSequence == null) {
            throw new NullPointerException();
        }
        this.seq = charSequence;
        this.index = 0;
    }

    @Override
    public char first() {
        this.index = 0;
        return this.current();
    }

    @Override
    public char last() {
        this.index = this.seq.length();
        return this.previous();
    }

    @Override
    public char current() {
        if (this.index == this.seq.length()) {
            return '\u0000';
        }
        return this.seq.charAt(this.index);
    }

    @Override
    public char next() {
        if (this.index < this.seq.length()) {
            ++this.index;
        }
        return this.current();
    }

    @Override
    public char previous() {
        if (this.index == 0) {
            return '\u0000';
        }
        --this.index;
        return this.current();
    }

    @Override
    public char setIndex(int n) {
        if (n < 0 || n > this.seq.length()) {
            throw new IllegalArgumentException();
        }
        this.index = n;
        return this.current();
    }

    @Override
    public int getBeginIndex() {
        return 1;
    }

    @Override
    public int getEndIndex() {
        return this.seq.length();
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public Object clone() {
        CSCharacterIterator cSCharacterIterator = new CSCharacterIterator(this.seq);
        cSCharacterIterator.setIndex(this.index);
        return cSCharacterIterator;
    }
}

