/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import java.io.Reader;
import java.io.Serializable;
import java.util.Objects;

public class CharSequenceReader
extends Reader
implements Serializable {
    private static final long serialVersionUID = 3724187752191401220L;
    private final CharSequence charSequence;
    private int idx;
    private int mark;
    private final int start;
    private final Integer end;

    public CharSequenceReader(CharSequence charSequence) {
        this(charSequence, 0);
    }

    public CharSequenceReader(CharSequence charSequence, int n) {
        this(charSequence, n, Integer.MAX_VALUE);
    }

    public CharSequenceReader(CharSequence charSequence, int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("Start index is less than zero: " + n);
        }
        if (n2 < n) {
            throw new IllegalArgumentException("End index is less than start " + n + ": " + n2);
        }
        this.charSequence = charSequence != null ? charSequence : "";
        this.start = n;
        this.end = n2;
        this.idx = n;
        this.mark = n;
    }

    @Override
    public void close() {
        this.idx = this.start;
        this.mark = this.start;
    }

    private int end() {
        return Math.min(this.charSequence.length(), this.end == null ? Integer.MAX_VALUE : this.end);
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
        if (this.idx >= this.end()) {
            return 1;
        }
        return this.charSequence.charAt(this.idx++);
    }

    @Override
    public int read(char[] cArray, int n, int n2) {
        if (this.idx >= this.end()) {
            return 1;
        }
        Objects.requireNonNull(cArray, "array");
        if (n2 < 0 || n < 0 || n + n2 > cArray.length) {
            throw new IndexOutOfBoundsException("Array Size=" + cArray.length + ", offset=" + n + ", length=" + n2);
        }
        if (this.charSequence instanceof String) {
            int n3 = Math.min(n2, this.end() - this.idx);
            ((String)this.charSequence).getChars(this.idx, this.idx + n3, cArray, n);
            this.idx += n3;
            return n3;
        }
        if (this.charSequence instanceof StringBuilder) {
            int n4 = Math.min(n2, this.end() - this.idx);
            ((StringBuilder)this.charSequence).getChars(this.idx, this.idx + n4, cArray, n);
            this.idx += n4;
            return n4;
        }
        if (this.charSequence instanceof StringBuffer) {
            int n5 = Math.min(n2, this.end() - this.idx);
            ((StringBuffer)this.charSequence).getChars(this.idx, this.idx + n5, cArray, n);
            this.idx += n5;
            return n5;
        }
        int n6 = 0;
        for (int i = 0; i < n2; ++i) {
            int n7 = this.read();
            if (n7 == -1) {
                return n6;
            }
            cArray[n + i] = (char)n7;
            ++n6;
        }
        return n6;
    }

    @Override
    public boolean ready() {
        return this.idx < this.end();
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
        if (this.idx >= this.end()) {
            return 0L;
        }
        int n = (int)Math.min((long)this.end(), (long)this.idx + l);
        int n2 = n - this.idx;
        this.idx = n;
        return n2;
    }

    private int start() {
        return Math.min(this.charSequence.length(), this.start);
    }

    public String toString() {
        CharSequence charSequence = this.charSequence.subSequence(this.start(), this.end());
        return charSequence.toString();
    }
}

