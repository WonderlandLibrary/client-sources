/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

import java.io.Serializable;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public final class ByteArrayBuffer
implements Serializable {
    private static final long serialVersionUID = 4359112959524048036L;
    private byte[] buffer;
    private int len;

    public ByteArrayBuffer(int n) {
        Args.notNegative(n, "Buffer capacity");
        this.buffer = new byte[n];
    }

    private void expand(int n) {
        byte[] byArray = new byte[Math.max(this.buffer.length << 1, n)];
        System.arraycopy(this.buffer, 0, byArray, 0, this.len);
        this.buffer = byArray;
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
        int n3 = this.len + n2;
        if (n3 > this.buffer.length) {
            this.expand(n3);
        }
        System.arraycopy(byArray, n, this.buffer, this.len, n2);
        this.len = n3;
    }

    public void append(int n) {
        int n2 = this.len + 1;
        if (n2 > this.buffer.length) {
            this.expand(n2);
        }
        this.buffer[this.len] = (byte)n;
        this.len = n2;
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
        int n3 = this.len;
        int n4 = n3 + n2;
        if (n4 > this.buffer.length) {
            this.expand(n4);
        }
        int n5 = n;
        for (int i = n3; i < n4; ++i) {
            char c = cArray[n5];
            this.buffer[i] = c >= ' ' && c <= '~' || c >= '\u00a0' && c <= '\u00ff' || c == '\t' ? (int)c : 63;
            ++n5;
        }
        this.len = n4;
    }

    public void append(CharArrayBuffer charArrayBuffer, int n, int n2) {
        if (charArrayBuffer == null) {
            return;
        }
        this.append(charArrayBuffer.buffer(), n, n2);
    }

    public void clear() {
        this.len = 0;
    }

    public byte[] toByteArray() {
        byte[] byArray = new byte[this.len];
        if (this.len > 0) {
            System.arraycopy(this.buffer, 0, byArray, 0, this.len);
        }
        return byArray;
    }

    public int byteAt(int n) {
        return this.buffer[n];
    }

    public int capacity() {
        return this.buffer.length;
    }

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

    public byte[] buffer() {
        return this.buffer;
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

    public int indexOf(byte by, int n, int n2) {
        int n3;
        int n4 = n;
        if (n4 < 0) {
            n4 = 0;
        }
        if ((n3 = n2) > this.len) {
            n3 = this.len;
        }
        if (n4 > n3) {
            return 1;
        }
        for (int i = n4; i < n3; ++i) {
            if (this.buffer[i] != by) continue;
            return i;
        }
        return 1;
    }

    public int indexOf(byte by) {
        return this.indexOf(by, 0, this.len);
    }
}

