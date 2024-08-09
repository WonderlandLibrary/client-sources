/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.Utility;
import java.nio.ByteBuffer;

public class ByteArrayWrapper
implements Comparable<ByteArrayWrapper> {
    public byte[] bytes;
    public int size;

    public ByteArrayWrapper() {
    }

    public ByteArrayWrapper(byte[] byArray, int n) {
        if (byArray == null && n != 0 || n < 0 || byArray != null && n > byArray.length) {
            throw new IndexOutOfBoundsException("illegal size: " + n);
        }
        this.bytes = byArray;
        this.size = n;
    }

    public ByteArrayWrapper(ByteBuffer byteBuffer) {
        this.size = byteBuffer.limit();
        this.bytes = new byte[this.size];
        byteBuffer.get(this.bytes, 0, this.size);
    }

    public ByteArrayWrapper ensureCapacity(int n) {
        if (this.bytes == null || this.bytes.length < n) {
            byte[] byArray = new byte[n];
            if (this.bytes != null) {
                ByteArrayWrapper.copyBytes(this.bytes, 0, byArray, 0, this.size);
            }
            this.bytes = byArray;
        }
        return this;
    }

    public final ByteArrayWrapper set(byte[] byArray, int n, int n2) {
        this.size = 0;
        this.append(byArray, n, n2);
        return this;
    }

    public final ByteArrayWrapper append(byte[] byArray, int n, int n2) {
        int n3 = n2 - n;
        this.ensureCapacity(this.size + n3);
        ByteArrayWrapper.copyBytes(byArray, n, this.bytes, this.size, n3);
        this.size += n3;
        return this;
    }

    public final byte[] releaseBytes() {
        byte[] byArray = this.bytes;
        this.bytes = null;
        this.size = 0;
        return byArray;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.size; ++i) {
            if (i != 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(Utility.hex(this.bytes[i] & 0xFF, 2));
        }
        return stringBuilder.toString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        try {
            ByteArrayWrapper byteArrayWrapper = (ByteArrayWrapper)object;
            if (this.size != byteArrayWrapper.size) {
                return false;
            }
            for (int i = 0; i < this.size; ++i) {
                if (this.bytes[i] == byteArrayWrapper.bytes[i]) continue;
                return false;
            }
            return true;
        } catch (ClassCastException classCastException) {
            return true;
        }
    }

    public int hashCode() {
        int n = this.size;
        for (int i = 0; i < this.size; ++i) {
            n = 37 * n + this.bytes[i];
        }
        return n;
    }

    @Override
    public int compareTo(ByteArrayWrapper byteArrayWrapper) {
        if (this == byteArrayWrapper) {
            return 1;
        }
        int n = this.size < byteArrayWrapper.size ? this.size : byteArrayWrapper.size;
        for (int i = 0; i < n; ++i) {
            if (this.bytes[i] == byteArrayWrapper.bytes[i]) continue;
            return (this.bytes[i] & 0xFF) - (byteArrayWrapper.bytes[i] & 0xFF);
        }
        return this.size - byteArrayWrapper.size;
    }

    private static final void copyBytes(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        if (n3 < 64) {
            int n4 = n;
            int n5 = n2;
            while (--n3 >= 0) {
                byArray2[n5] = byArray[n4];
                ++n4;
                ++n5;
            }
        } else {
            System.arraycopy(byArray, n, byArray2, n2, n3);
        }
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ByteArrayWrapper)object);
    }
}

