/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.Serializable;

public class ByteOrderMark
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final ByteOrderMark UTF_8 = new ByteOrderMark("UTF-8", 239, 187, 191);
    public static final ByteOrderMark UTF_16BE = new ByteOrderMark("UTF-16BE", 254, 255);
    public static final ByteOrderMark UTF_16LE = new ByteOrderMark("UTF-16LE", 255, 254);
    public static final ByteOrderMark UTF_32BE = new ByteOrderMark("UTF-32BE", 0, 0, 254, 255);
    public static final ByteOrderMark UTF_32LE = new ByteOrderMark("UTF-32LE", 255, 254, 0, 0);
    public static final char UTF_BOM = '\ufeff';
    private final String charsetName;
    private final int[] bytes;

    public ByteOrderMark(String string, int ... nArray) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("No charsetName specified");
        }
        if (nArray == null || nArray.length == 0) {
            throw new IllegalArgumentException("No bytes specified");
        }
        this.charsetName = string;
        this.bytes = new int[nArray.length];
        System.arraycopy(nArray, 0, this.bytes, 0, nArray.length);
    }

    public String getCharsetName() {
        return this.charsetName;
    }

    public int length() {
        return this.bytes.length;
    }

    public int get(int n) {
        return this.bytes[n];
    }

    public byte[] getBytes() {
        byte[] byArray = new byte[this.bytes.length];
        for (int i = 0; i < this.bytes.length; ++i) {
            byArray[i] = (byte)this.bytes[i];
        }
        return byArray;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ByteOrderMark)) {
            return true;
        }
        ByteOrderMark byteOrderMark = (ByteOrderMark)object;
        if (this.bytes.length != byteOrderMark.length()) {
            return true;
        }
        for (int i = 0; i < this.bytes.length; ++i) {
            if (this.bytes[i] == byteOrderMark.get(i)) continue;
            return true;
        }
        return false;
    }

    public int hashCode() {
        int n = this.getClass().hashCode();
        for (int n2 : this.bytes) {
            n += n2;
        }
        return n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append('[');
        stringBuilder.append(this.charsetName);
        stringBuilder.append(": ");
        for (int i = 0; i < this.bytes.length; ++i) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(0xFF & this.bytes[i]).toUpperCase());
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

