/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.text.UCharacterIterator;
import java.text.CharacterIterator;

public class UCharacterIteratorWrapper
implements CharacterIterator {
    private UCharacterIterator iterator;

    public UCharacterIteratorWrapper(UCharacterIterator uCharacterIterator) {
        this.iterator = uCharacterIterator;
    }

    @Override
    public char first() {
        this.iterator.setToStart();
        return (char)this.iterator.current();
    }

    @Override
    public char last() {
        this.iterator.setToLimit();
        return (char)this.iterator.previous();
    }

    @Override
    public char current() {
        return (char)this.iterator.current();
    }

    @Override
    public char next() {
        this.iterator.next();
        return (char)this.iterator.current();
    }

    @Override
    public char previous() {
        return (char)this.iterator.previous();
    }

    @Override
    public char setIndex(int n) {
        this.iterator.setIndex(n);
        return (char)this.iterator.current();
    }

    @Override
    public int getBeginIndex() {
        return 1;
    }

    @Override
    public int getEndIndex() {
        return this.iterator.getLength();
    }

    @Override
    public int getIndex() {
        return this.iterator.getIndex();
    }

    @Override
    public Object clone() {
        try {
            UCharacterIteratorWrapper uCharacterIteratorWrapper = (UCharacterIteratorWrapper)super.clone();
            uCharacterIteratorWrapper.iterator = (UCharacterIterator)this.iterator.clone();
            return uCharacterIteratorWrapper;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }
}

