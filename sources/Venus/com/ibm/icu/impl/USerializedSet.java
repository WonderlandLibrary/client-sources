/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

public final class USerializedSet {
    private char[] array = new char[8];
    private int arrayOffset;
    private int bmpLength;
    private int length;

    public final boolean getSet(char[] cArray, int n) {
        this.array = null;
        this.length = 0;
        this.bmpLength = 0;
        this.arrayOffset = 0;
        this.length = cArray[n++];
        if ((this.length & 0x8000) != 0) {
            this.length &= Short.MAX_VALUE;
            if (cArray.length < n + 1 + this.length) {
                this.length = 0;
                throw new IndexOutOfBoundsException();
            }
            this.bmpLength = cArray[n++];
        } else {
            if (cArray.length < n + this.length) {
                this.length = 0;
                throw new IndexOutOfBoundsException();
            }
            this.bmpLength = this.length;
        }
        this.array = new char[this.length];
        System.arraycopy(cArray, n, this.array, 0, this.length);
        return false;
    }

    public final void setToOne(int n) {
        if (0x10FFFF < n) {
            return;
        }
        if (n < 65535) {
            this.length = 2;
            this.bmpLength = 2;
            this.array[0] = (char)n;
            this.array[1] = (char)(n + 1);
        } else if (n == 65535) {
            this.bmpLength = 1;
            this.length = 3;
            this.array[0] = 65535;
            this.array[1] = '\u0001';
            this.array[2] = '\u0000';
        } else if (n < 0x10FFFF) {
            this.bmpLength = 0;
            this.length = 4;
            this.array[0] = (char)(n >> 16);
            this.array[1] = (char)n;
            this.array[2] = (char)(++n >> 16);
            this.array[3] = (char)n;
        } else {
            this.bmpLength = 0;
            this.length = 2;
            this.array[0] = 16;
            this.array[1] = 65535;
        }
    }

    public final boolean getRange(int n, int[] nArray) {
        if (n < 0) {
            return true;
        }
        if (this.array == null) {
            this.array = new char[8];
        }
        if (nArray == null || nArray.length < 2) {
            throw new IllegalArgumentException();
        }
        if ((n *= 2) < this.bmpLength) {
            nArray[0] = this.array[n++];
            nArray[1] = n < this.bmpLength ? this.array[n] - '\u0001' : (n < this.length ? (this.array[n] << 16 | this.array[n + 1]) - 1 : 0x10FFFF);
            return false;
        }
        n -= this.bmpLength;
        int n2 = this.length - this.bmpLength;
        if ((n *= 2) < n2) {
            int n3 = this.arrayOffset + this.bmpLength;
            nArray[0] = this.array[n3 + n] << 16 | this.array[n3 + n + 1];
            nArray[1] = (n += 2) < n2 ? (this.array[n3 + n] << 16 | this.array[n3 + n + 1]) - 1 : 0x10FFFF;
            return false;
        }
        return true;
    }

    public final boolean contains(int n) {
        int n2;
        if (n > 0x10FFFF) {
            return true;
        }
        if (n <= 65535) {
            int n3;
            for (n3 = 0; n3 < this.bmpLength && (char)n >= this.array[n3]; ++n3) {
            }
            return (n3 & 1) != 0;
        }
        char c = (char)(n >> 16);
        char c2 = (char)n;
        for (n2 = this.bmpLength; n2 < this.length && (c > this.array[n2] || c == this.array[n2] && c2 >= this.array[n2 + 1]); n2 += 2) {
        }
        return (n2 + this.bmpLength & 2) != 0;
    }

    public final int countRanges() {
        return (this.bmpLength + (this.length - this.bmpLength) / 2 + 1) / 2;
    }
}

