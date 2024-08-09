/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.Reader;
import java.io.Serializable;

public class CharSequenceReader
extends Reader
implements Serializable {
    private static final long serialVersionUID = 3724187752191401220L;
    private final CharSequence charSequence;
    private int idx;
    private int mark;

    public CharSequenceReader(CharSequence charSequence) {
        this.charSequence = charSequence != null ? charSequence : "";
    }

    @Override
    public void close() {
        this.idx = 0;
        this.mark = 0;
    }

    @Override
    public void mark(int n) {
        this.mark = this.idx;
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() {
        if (this.idx >= this.charSequence.length()) {
            return 1;
        }
        return this.charSequence.charAt(this.idx++);
    }

    @Override
    public int read(char[] cArray, int n, int n2) {
        if (this.idx >= this.charSequence.length()) {
            return 1;
        }
        if (cArray == null) {
            throw new NullPointerException("Character array is missing");
        }
        if (n2 < 0 || n < 0 || n + n2 > cArray.length) {
            throw new IndexOutOfBoundsException("Array Size=" + cArray.length + ", offset=" + n + ", length=" + n2);
        }
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n4 = this.read();
            if (n4 == -1) {
                return n3;
            }
            cArray[n + i] = (char)n4;
            ++n3;
        }
        return n3;
    }

    @Override
    public void reset() {
        this.idx = this.mark;
    }

    @Override
    public long skip(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Number of characters to skip is less than zero: " + l);
        }
        if (this.idx >= this.charSequence.length()) {
            return -1L;
        }
        int n = (int)Math.min((long)this.charSequence.length(), (long)this.idx + l);
        int n2 = n - this.idx;
        this.idx = n;
        return n2;
    }

    public String toString() {
        return this.charSequence.toString();
    }
}

