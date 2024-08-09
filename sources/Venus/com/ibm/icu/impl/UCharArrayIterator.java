/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.text.UCharacterIterator;

public final class UCharArrayIterator
extends UCharacterIterator {
    private final char[] text;
    private final int start;
    private final int limit;
    private int pos;

    public UCharArrayIterator(char[] cArray, int n, int n2) {
        if (n < 0 || n2 > cArray.length || n > n2) {
            throw new IllegalArgumentException("start: " + n + " or limit: " + n2 + " out of range [0, " + cArray.length + ")");
        }
        this.text = cArray;
        this.start = n;
        this.limit = n2;
        this.pos = n;
    }

    @Override
    public int current() {
        return this.pos < this.limit ? this.text[this.pos] : -1;
    }

    @Override
    public int getLength() {
        return this.limit - this.start;
    }

    @Override
    public int getIndex() {
        return this.pos - this.start;
    }

    @Override
    public int next() {
        return this.pos < this.limit ? this.text[this.pos++] : -1;
    }

    @Override
    public int previous() {
        return this.pos > this.start ? this.text[--this.pos] : -1;
    }

    @Override
    public void setIndex(int n) {
        if (n < 0 || n > this.limit - this.start) {
            throw new IndexOutOfBoundsException("index: " + n + " out of range [0, " + (this.limit - this.start) + ")");
        }
        this.pos = this.start + n;
    }

    @Override
    public int getText(char[] cArray, int n) {
        int n2 = this.limit - this.start;
        System.arraycopy(this.text, this.start, cArray, n, n2);
        return n2;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }
}

