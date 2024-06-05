/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil;

public final class SafeMath {
    private SafeMath() {
    }

    public static char safeIntToChar(int value) {
        if (value < 0) throw new IllegalArgumentException(value + " can't be represented as char");
        if (65535 >= value) return (char)value;
        throw new IllegalArgumentException(value + " can't be represented as char");
    }

    public static byte safeIntToByte(int value) {
        if (value < -128) throw new IllegalArgumentException(value + " can't be represented as byte (out of range)");
        if (127 >= value) return (byte)value;
        throw new IllegalArgumentException(value + " can't be represented as byte (out of range)");
    }

    public static short safeIntToShort(int value) {
        if (value < Short.MIN_VALUE) throw new IllegalArgumentException(value + " can't be represented as short (out of range)");
        if (Short.MAX_VALUE >= value) return (short)value;
        throw new IllegalArgumentException(value + " can't be represented as short (out of range)");
    }

    public static char safeLongToChar(long value) {
        if (value < 0L) throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
        if (65535L >= value) return (char)value;
        throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
    }

    public static byte safeLongToByte(long value) {
        if (value < -128L) throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
        if (127L >= value) return (byte)value;
        throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
    }

    public static short safeLongToShort(long value) {
        if (value < -32768L) throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
        if (32767L >= value) return (short)value;
        throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
    }

    public static int safeLongToInt(long value) {
        if (value < Integer.MIN_VALUE) throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
        if (Integer.MAX_VALUE >= value) return (int)value;
        throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
    }

    public static float safeDoubleToFloat(double value) {
        if (Double.isNaN(value)) {
            return Float.NaN;
        }
        if (Double.isInfinite(value)) {
            if (!(value < 0.0)) return Float.POSITIVE_INFINITY;
            return Float.NEGATIVE_INFINITY;
        }
        if (value < -3.4028234663852886E38) throw new IllegalArgumentException(value + " can't be represented as float (out of range)");
        if (3.4028234663852886E38 < value) {
            throw new IllegalArgumentException(value + " can't be represented as float (out of range)");
        }
        float floatValue = (float)value;
        if ((double)floatValue == value) return floatValue;
        throw new IllegalArgumentException(value + " can't be represented as float (imprecise)");
    }
}

