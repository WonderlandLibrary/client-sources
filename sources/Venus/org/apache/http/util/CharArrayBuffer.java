/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

import java.io.Serializable;
import java.nio.CharBuffer;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.ByteArrayBuffer;

public final class CharArrayBuffer
implements CharSequence,
Serializable {
    private static final long serialVersionUID = -6208952725094867135L;
    private char[] buffer;
    private int len;

    public CharArrayBuffer(int n) {
        Args.notNegative(n, "Buffer capacity");
        this.buffer = new char[n];
    }

    private void expand(int n) {
        char[] cArray = new char[Math.max(this.buffer.length << 1, n)];
        System.arraycopy(this.buffer, 0, cArray, 0, this.len);
        this.buffer = cArray;
    }

    public void append(char[] cArray, int n, int n2) {
        if (cArray == null) {
            return;
        }
        if (n < 0 || n > cArray.length || n2 < 0 || n + n2 < 0 || n + n2 > cArray.length) {
            throw new IndexOutOfBoundsException("off: " + n + " len: " + n2 + " b.length: " + cArray.length);
        }
        if (n2 == 0) {
            return;
        }
        int n3 = this.len + n2;
        if (n3 > this.buffer.length) {
            this.expand(n3);
        }
        System.arraycopy(cArray, n, this.buffer, this.len, n2);
        this.len = n3;
    }

    public void append(String string) {
        String string2 = string != null ? string : "null";
        int n = string2.length();
        int n2 = this.len + n;
        if (n2 > this.buffer.length) {
            this.expand(n2);
        }
        string2.getChars(0, n, this.buffer, this.len);
        this.len = n2;
    }

    public void append(CharArrayBuffer charArrayBuffer, int n, int n2) {
        if (charArrayBuffer == null) {
            return;
        }
        this.append(charArrayBuffer.buffer, n, n2);
    }

    public void append(CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer == null) {
            return;
        }
        this.append(charArrayBuffer.buffer, 0, charArrayBuffer.len);
    }

    public void append(char c) {
        int n = this.len + 1;
        if (n > this.buffer.length) {
            this.expand(n);
        }
        this.buffer[this.len] = c;
        this.len = n;
    }

    public void append(byte[] byArray, int n, int n2) {
        if (byArray == null) {
            return;
        }
        if (n < 0 || n > byArray.length || n2 < 0 || n + n2 < 0 || n + n2 > byArray.length) {
            throw new IndexOutOfBoundsException("off: " + n + " len: " + n2 + " b.length: " + byArray.length);
        }
        if (n2 == 0) {
            return;
        }
        int n3 = this.len;
        int n4 = n3 + n2;
        if (n4 > this.buffer.length) {
            this.expand(n4);
        }
        int n5 = n;
        for (int i = n3; i < n4; ++i) {
            this.buffer[i] = (char)(byArray[n5] & 0xFF);
            ++n5;
        }
        this.len = n4;
    }

    public void append(ByteArrayBuffer byteArrayBuffer, int n, int n2) {
        if (byteArrayBuffer == null) {
            return;
        }
        this.append(byteArrayBuffer.buffer(), n, n2);
    }

    public void append(Object object) {
        this.append(String.valueOf(object));
    }

    public void clear() {
        this.len = 0;
    }

    public char[] toCharArray() {
        char[] cArray = new char[this.len];
        if (this.len > 0) {
            System.arraycopy(this.buffer, 0, cArray, 0, this.len);
        }
        return cArray;
    }

    @Override
    public char charAt(int n) {
        return this.buffer[n];
    }

    public char[] buffer() {
        return this.buffer;
    }

    public int capacity() {
        return this.buffer.length;
    }

    @Override
    public int length() {
        return this.len;
    }

    public void ensureCapacity(int n) {
        if (n <= 0) {
            return;
        }
        int n2 = this.buffer.length - this.len;
        if (n > n2) {
            this.expand(this.len + n);
        }
    }

    public void setLength(int n) {
        if (n < 0 || n > this.buffer.length) {
            throw new IndexOutOfBoundsException("len: " + n + " < 0 or > buffer len: " + this.buffer.length);
        }
        this.len = n;
    }

    public boolean isEmpty() {
        return this.len == 0;
    }

    public boolean isFull() {
        return this.len == this.buffer.length;
    }

    public int indexOf(int n, int n2, int n3) {
        int n4;
        int n5 = n2;
        if (n5 < 0) {
            n5 = 0;
        }
        if ((n4 = n3) > this.len) {
            n4 = this.len;
        }
        if (n5 > n4) {
            return 1;
        }
        for (int i = n5; i < n4; ++i) {
            if (this.buffer[i] != n) continue;
            return i;
        }
        return 1;
    }

    public int indexOf(int n) {
        return this.indexOf(n, 0, this.len);
    }

    public String substring(int n, int n2) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Negative beginIndex: " + n);
        }
        if (n2 > this.len) {
            throw new IndexOutOfBoundsException("endIndex: " + n2 + " > length: " + this.len);
        }
        if (n > n2) {
            throw new IndexOutOfBoundsException("beginIndex: " + n + " > endIndex: " + n2);
        }
        return new String(this.buffer, n, n2 - n);
    }

    public String substringTrimmed(int n, int n2) {
        int n3;
        if (n < 0) {
            throw new IndexOutOfBoundsException("Negative beginIndex: " + n);
        }
        if (n2 > this.len) {
            throw new IndexOutOfBoundsException("endIndex: " + n2 + " > length: " + this.len);
        }
        if (n > n2) {
            throw new IndexOutOfBoundsException("beginIndex: " + n + " > endIndex: " + n2);
        }
        int n4 = n2;
        for (n3 = n; n3 < n2 && HTTP.isWhitespace(this.buffer[n3]); ++n3) {
        }
        while (n4 > n3 && HTTP.isWhitespace(this.buffer[n4 - 1])) {
            --n4;
        }
        return new String(this.buffer, n3, n4 - n3);
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Negative beginIndex: " + n);
        }
        if (n2 > this.len) {
            throw new IndexOutOfBoundsException("endIndex: " + n2 + " > length: " + this.len);
        }
        if (n > n2) {
            throw new IndexOutOfBoundsException("beginIndex: " + n + " > endIndex: " + n2);
        }
        return CharBuffer.wrap(this.buffer, n, n2);
    }

    @Override
    public String toString() {
        return new String(this.buffer, 0, this.len);
    }
}

