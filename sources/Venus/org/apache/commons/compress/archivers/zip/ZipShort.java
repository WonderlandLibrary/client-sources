/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;

public final class ZipShort
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private static final int BYTE_1_MASK = 65280;
    private static final int BYTE_1_SHIFT = 8;
    private final int value;

    public ZipShort(int n) {
        this.value = n;
    }

    public ZipShort(byte[] byArray) {
        this(byArray, 0);
    }

    public ZipShort(byte[] byArray, int n) {
        this.value = ZipShort.getValue(byArray, n);
    }

    public byte[] getBytes() {
        byte[] byArray = new byte[]{(byte)(this.value & 0xFF), (byte)((this.value & 0xFF00) >> 8)};
        return byArray;
    }

    public int getValue() {
        return this.value;
    }

    public static byte[] getBytes(int n) {
        byte[] byArray = new byte[]{(byte)(n & 0xFF), (byte)((n & 0xFF00) >> 8)};
        return byArray;
    }

    public static int getValue(byte[] byArray, int n) {
        int n2 = byArray[n + 1] << 8 & 0xFF00;
        return n2 += byArray[n] & 0xFF;
    }

    public static int getValue(byte[] byArray) {
        return ZipShort.getValue(byArray, 0);
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof ZipShort)) {
            return true;
        }
        return this.value == ((ZipShort)object).getValue();
    }

    public int hashCode() {
        return this.value;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

    public String toString() {
        return "ZipShort value: " + this.value;
    }
}

