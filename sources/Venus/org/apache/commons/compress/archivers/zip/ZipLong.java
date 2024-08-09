/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;

public final class ZipLong
implements Cloneable,
Serializable {
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
    private final long value;
    public static final ZipLong CFH_SIG = new ZipLong(33639248L);
    public static final ZipLong LFH_SIG = new ZipLong(67324752L);
    public static final ZipLong DD_SIG = new ZipLong(134695760L);
    static final ZipLong ZIP64_MAGIC = new ZipLong(0xFFFFFFFFL);
    public static final ZipLong SINGLE_SEGMENT_SPLIT_MARKER = new ZipLong(808471376L);
    public static final ZipLong AED_SIG = new ZipLong(134630224L);

    public ZipLong(long l) {
        this.value = l;
    }

    public ZipLong(byte[] byArray) {
        this(byArray, 0);
    }

    public ZipLong(byte[] byArray, int n) {
        this.value = ZipLong.getValue(byArray, n);
    }

    public byte[] getBytes() {
        return ZipLong.getBytes(this.value);
    }

    public long getValue() {
        return this.value;
    }

    public static byte[] getBytes(long l) {
        byte[] byArray = new byte[]{(byte)(l & 0xFFL), (byte)((l & 0xFF00L) >> 8), (byte)((l & 0xFF0000L) >> 16), (byte)((l & 0xFF000000L) >> 24)};
        return byArray;
    }

    public static long getValue(byte[] byArray, int n) {
        long l = (long)(byArray[n + 3] << 24) & 0xFF000000L;
        l += (long)(byArray[n + 2] << 16 & 0xFF0000);
        l += (long)(byArray[n + 1] << 8 & 0xFF00);
        return l += (long)(byArray[n] & 0xFF);
    }

    public static long getValue(byte[] byArray) {
        return ZipLong.getValue(byArray, 0);
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof ZipLong)) {
            return true;
        }
        return this.value == ((ZipLong)object).getValue();
    }

    public int hashCode() {
        return (int)this.value;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

    public String toString() {
        return "ZipLong value: " + this.value;
    }
}

