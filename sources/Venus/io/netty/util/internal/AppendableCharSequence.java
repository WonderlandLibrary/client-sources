/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import java.io.IOException;
import java.util.Arrays;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class AppendableCharSequence
implements CharSequence,
Appendable {
    private char[] chars;
    private int pos;

    public AppendableCharSequence(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("length: " + n + " (length: >= 1)");
        }
        this.chars = new char[n];
    }

    private AppendableCharSequence(char[] cArray) {
        if (cArray.length < 1) {
            throw new IllegalArgumentException("length: " + cArray.length + " (length: >= 1)");
        }
        this.chars = cArray;
        this.pos = cArray.length;
    }

    @Override
    public int length() {
        return this.pos;
    }

    @Override
    public char charAt(int n) {
        if (n > this.pos) {
            throw new IndexOutOfBoundsException();
        }
        return this.chars[n];
    }

    public char charAtUnsafe(int n) {
        return this.chars[n];
    }

    @Override
    public AppendableCharSequence subSequence(int n, int n2) {
        return new AppendableCharSequence(Arrays.copyOfRange(this.chars, n, n2));
    }

    @Override
    public AppendableCharSequence append(char c) {
        if (this.pos == this.chars.length) {
            char[] cArray = this.chars;
            this.chars = new char[cArray.length << 1];
            System.arraycopy(cArray, 0, this.chars, 0, cArray.length);
        }
        this.chars[this.pos++] = c;
        return this;
    }

    @Override
    public AppendableCharSequence append(CharSequence charSequence) {
        return this.append(charSequence, 0, charSequence.length());
    }

    @Override
    public AppendableCharSequence append(CharSequence charSequence, int n, int n2) {
        if (charSequence.length() < n2) {
            throw new IndexOutOfBoundsException();
        }
        int n3 = n2 - n;
        if (n3 > this.chars.length - this.pos) {
            this.chars = AppendableCharSequence.expand(this.chars, this.pos + n3, this.pos);
        }
        if (charSequence instanceof AppendableCharSequence) {
            AppendableCharSequence appendableCharSequence = (AppendableCharSequence)charSequence;
            char[] cArray = appendableCharSequence.chars;
            System.arraycopy(cArray, n, this.chars, this.pos, n3);
            this.pos += n3;
            return this;
        }
        for (int i = n; i < n2; ++i) {
            this.chars[this.pos++] = charSequence.charAt(i);
        }
        return this;
    }

    public void reset() {
        this.pos = 0;
    }

    @Override
    public String toString() {
        return new String(this.chars, 0, this.pos);
    }

    public String substring(int n, int n2) {
        int n3 = n2 - n;
        if (n > this.pos || n3 > this.pos) {
            throw new IndexOutOfBoundsException();
        }
        return new String(this.chars, n, n3);
    }

    public String subStringUnsafe(int n, int n2) {
        return new String(this.chars, n, n2 - n);
    }

    private static char[] expand(char[] cArray, int n, int n2) {
        int n3 = cArray.length;
        do {
            if ((n3 <<= 1) >= 0) continue;
            throw new IllegalStateException();
        } while (n > n3);
        char[] cArray2 = new char[n3];
        System.arraycopy(cArray, 0, cArray2, 0, n2);
        return cArray2;
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return this.subSequence(n, n2);
    }

    @Override
    public Appendable append(char c) throws IOException {
        return this.append(c);
    }

    @Override
    public Appendable append(CharSequence charSequence, int n, int n2) throws IOException {
        return this.append(charSequence, n, n2);
    }

    @Override
    public Appendable append(CharSequence charSequence) throws IOException {
        return this.append(charSequence);
    }
}

