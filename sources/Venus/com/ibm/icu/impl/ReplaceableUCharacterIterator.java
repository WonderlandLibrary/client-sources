/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.ReplaceableString;
import com.ibm.icu.text.UCharacterIterator;
import com.ibm.icu.text.UTF16;

public class ReplaceableUCharacterIterator
extends UCharacterIterator {
    private Replaceable replaceable;
    private int currentIndex;

    public ReplaceableUCharacterIterator(Replaceable replaceable) {
        if (replaceable == null) {
            throw new IllegalArgumentException();
        }
        this.replaceable = replaceable;
        this.currentIndex = 0;
    }

    public ReplaceableUCharacterIterator(String string) {
        if (string == null) {
            throw new IllegalArgumentException();
        }
        this.replaceable = new ReplaceableString(string);
        this.currentIndex = 0;
    }

    public ReplaceableUCharacterIterator(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            throw new IllegalArgumentException();
        }
        this.replaceable = new ReplaceableString(stringBuffer);
        this.currentIndex = 0;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public int current() {
        if (this.currentIndex < this.replaceable.length()) {
            return this.replaceable.charAt(this.currentIndex);
        }
        return 1;
    }

    @Override
    public int currentCodePoint() {
        int n = this.current();
        if (UTF16.isLeadSurrogate((char)n)) {
            this.next();
            int n2 = this.current();
            this.previous();
            if (UTF16.isTrailSurrogate((char)n2)) {
                return Character.toCodePoint((char)n, (char)n2);
            }
        }
        return n;
    }

    @Override
    public int getLength() {
        return this.replaceable.length();
    }

    @Override
    public int getIndex() {
        return this.currentIndex;
    }

    @Override
    public int next() {
        if (this.currentIndex < this.replaceable.length()) {
            return this.replaceable.charAt(this.currentIndex++);
        }
        return 1;
    }

    @Override
    public int previous() {
        if (this.currentIndex > 0) {
            return this.replaceable.charAt(--this.currentIndex);
        }
        return 1;
    }

    @Override
    public void setIndex(int n) throws IndexOutOfBoundsException {
        if (n < 0 || n > this.replaceable.length()) {
            throw new IndexOutOfBoundsException();
        }
        this.currentIndex = n;
    }

    @Override
    public int getText(char[] cArray, int n) {
        int n2 = this.replaceable.length();
        if (n < 0 || n + n2 > cArray.length) {
            throw new IndexOutOfBoundsException(Integer.toString(n2));
        }
        this.replaceable.getChars(0, n2, cArray, n);
        return n2;
    }
}

