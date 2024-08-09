/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.text.UCharacterIterator;
import java.text.CharacterIterator;

public class CharacterIteratorWrapper
extends UCharacterIterator {
    private CharacterIterator iterator;

    public CharacterIteratorWrapper(CharacterIterator characterIterator) {
        if (characterIterator == null) {
            throw new IllegalArgumentException();
        }
        this.iterator = characterIterator;
    }

    @Override
    public int current() {
        char c = this.iterator.current();
        if (c == '\uffff') {
            return 1;
        }
        return c;
    }

    @Override
    public int getLength() {
        return this.iterator.getEndIndex() - this.iterator.getBeginIndex();
    }

    @Override
    public int getIndex() {
        return this.iterator.getIndex();
    }

    @Override
    public int next() {
        char c = this.iterator.current();
        this.iterator.next();
        if (c == '\uffff') {
            return 1;
        }
        return c;
    }

    @Override
    public int previous() {
        char c = this.iterator.previous();
        if (c == '\uffff') {
            return 1;
        }
        return c;
    }

    @Override
    public void setIndex(int n) {
        try {
            this.iterator.setIndex(n);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void setToLimit() {
        this.iterator.setIndex(this.iterator.getEndIndex());
    }

    @Override
    public int getText(char[] cArray, int n) {
        int n2 = this.iterator.getEndIndex() - this.iterator.getBeginIndex();
        int n3 = this.iterator.getIndex();
        if (n < 0 || n + n2 > cArray.length) {
            throw new IndexOutOfBoundsException(Integer.toString(n2));
        }
        char c = this.iterator.first();
        while (c != '\uffff') {
            cArray[n++] = c;
            c = this.iterator.next();
        }
        this.iterator.setIndex(n3);
        return n2;
    }

    @Override
    public Object clone() {
        try {
            CharacterIteratorWrapper characterIteratorWrapper = (CharacterIteratorWrapper)super.clone();
            characterIteratorWrapper.iterator = (CharacterIterator)this.iterator.clone();
            return characterIteratorWrapper;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public int moveIndex(int n) {
        int n2 = this.iterator.getEndIndex() - this.iterator.getBeginIndex();
        int n3 = this.iterator.getIndex() + n;
        if (n3 < 0) {
            n3 = 0;
        } else if (n3 > n2) {
            n3 = n2;
        }
        return this.iterator.setIndex(n3);
    }

    @Override
    public CharacterIterator getCharacterIterator() {
        return (CharacterIterator)this.iterator.clone();
    }
}

