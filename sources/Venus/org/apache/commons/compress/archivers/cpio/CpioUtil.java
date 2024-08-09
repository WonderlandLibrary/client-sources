/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.cpio;

class CpioUtil {
    CpioUtil() {
    }

    static long fileType(long l) {
        return l & 0xF000L;
    }

    static long byteArray2long(byte[] byArray, boolean bl) {
        if (byArray.length % 2 != 0) {
            throw new UnsupportedOperationException();
        }
        long l = 0L;
        int n = 0;
        byte[] byArray2 = new byte[byArray.length];
        System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        if (!bl) {
            byte by = 0;
            for (n = 0; n < byArray2.length; ++n) {
                by = byArray2[n];
                byArray2[n++] = byArray2[n];
                byArray2[n] = by;
            }
        }
        l = byArray2[0] & 0xFF;
        for (n = 1; n < byArray2.length; ++n) {
            l <<= 8;
            l |= (long)(byArray2[n] & 0xFF);
        }
        return l;
    }

    static byte[] long2byteArray(long l, int n, boolean bl) {
        byte[] byArray = new byte[n];
        int n2 = 0;
        long l2 = 0L;
        if (n % 2 != 0 || n < 2) {
            throw new UnsupportedOperationException();
        }
        l2 = l;
        for (n2 = n - 1; n2 >= 0; --n2) {
            byArray[n2] = (byte)(l2 & 0xFFL);
            l2 >>= 8;
        }
        if (!bl) {
            byte by = 0;
            for (n2 = 0; n2 < n; ++n2) {
                by = byArray[n2];
                byArray[n2++] = byArray[n2];
                byArray[n2] = by;
            }
        }
        return byArray;
    }
}

