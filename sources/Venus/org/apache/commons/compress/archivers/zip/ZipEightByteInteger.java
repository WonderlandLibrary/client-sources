/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;
import java.math.BigInteger;

public final class ZipEightByteInteger
implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int BYTE_1 = 1;
    private static final int BYTE_1_MASK = 65280;
    private static final int BYTE_1_SHIFT = 8;
    private static final int BYTE_2 = 2;
    private static final int BYTE_2_MASK = 0xFF0000;
    private static final int BYTE_2_SHIFT = 16;
    private static final int BYTE_3 = 3;
    private static final long BYTE_3_MASK = 0xFF000000L;
    private static final int BYTE_3_SHIFT = 24;
    private static final int BYTE_4 = 4;
    private static final long BYTE_4_MASK = 0xFF00000000L;
    private static final int BYTE_4_SHIFT = 32;
    private static final int BYTE_5 = 5;
    private static final long BYTE_5_MASK = 0xFF0000000000L;
    private static final int BYTE_5_SHIFT = 40;
    private static final int BYTE_6 = 6;
    private static final long BYTE_6_MASK = 0xFF000000000000L;
    private static final int BYTE_6_SHIFT = 48;
    private static final int BYTE_7 = 7;
    private static final long BYTE_7_MASK = 0x7F00000000000000L;
    private static final int BYTE_7_SHIFT = 56;
    private static final int LEFTMOST_BIT_SHIFT = 63;
    private static final byte LEFTMOST_BIT = -128;
    private final BigInteger value;
    public static final ZipEightByteInteger ZERO = new ZipEightByteInteger(0L);

    public ZipEightByteInteger(long l) {
        this(BigInteger.valueOf(l));
    }

    public ZipEightByteInteger(BigInteger bigInteger) {
        this.value = bigInteger;
    }

    public ZipEightByteInteger(byte[] byArray) {
        this(byArray, 0);
    }

    public ZipEightByteInteger(byte[] byArray, int n) {
        this.value = ZipEightByteInteger.getValue(byArray, n);
    }

    public byte[] getBytes() {
        return ZipEightByteInteger.getBytes(this.value);
    }

    public long getLongValue() {
        return this.value.longValue();
    }

    public BigInteger getValue() {
        return this.value;
    }

    public static byte[] getBytes(long l) {
        return ZipEightByteInteger.getBytes(BigInteger.valueOf(l));
    }

    public static byte[] getBytes(BigInteger bigInteger) {
        byte[] byArray = new byte[8];
        long l = bigInteger.longValue();
        byArray[0] = (byte)(l & 0xFFL);
        byArray[1] = (byte)((l & 0xFF00L) >> 8);
        byArray[2] = (byte)((l & 0xFF0000L) >> 16);
        byArray[3] = (byte)((l & 0xFF000000L) >> 24);
        byArray[4] = (byte)((l & 0xFF00000000L) >> 32);
        byArray[5] = (byte)((l & 0xFF0000000000L) >> 40);
        byArray[6] = (byte)((l & 0xFF000000000000L) >> 48);
        byArray[7] = (byte)((l & 0x7F00000000000000L) >> 56);
        if (bigInteger.testBit(0)) {
            byArray[7] = (byte)(byArray[7] | 0xFFFFFF80);
        }
        return byArray;
    }

    public static long getLongValue(byte[] byArray, int n) {
        return ZipEightByteInteger.getValue(byArray, n).longValue();
    }

    public static BigInteger getValue(byte[] byArray, int n) {
        long l = (long)byArray[n + 7] << 56 & 0x7F00000000000000L;
        l += (long)byArray[n + 6] << 48 & 0xFF000000000000L;
        l += (long)byArray[n + 5] << 40 & 0xFF0000000000L;
        l += (long)byArray[n + 4] << 32 & 0xFF00000000L;
        l += (long)byArray[n + 3] << 24 & 0xFF000000L;
        l += (long)byArray[n + 2] << 16 & 0xFF0000L;
        l += (long)byArray[n + 1] << 8 & 0xFF00L;
        BigInteger bigInteger = BigInteger.valueOf(l += (long)byArray[n] & 0xFFL);
        return (byArray[n + 7] & 0xFFFFFF80) == -128 ? bigInteger.setBit(63) : bigInteger;
    }

    public static long getLongValue(byte[] byArray) {
        return ZipEightByteInteger.getLongValue(byArray, 0);
    }

    public static BigInteger getValue(byte[] byArray) {
        return ZipEightByteInteger.getValue(byArray, 0);
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof ZipEightByteInteger)) {
            return true;
        }
        return this.value.equals(((ZipEightByteInteger)object).getValue());
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return "ZipEightByteInteger value: " + this.value;
    }
}

