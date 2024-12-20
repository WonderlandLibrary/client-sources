/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

public final class SafeMath {
    private SafeMath() {
    }

    public static char safeIntToChar(int n) {
        if (n < 0 || 65535 < n) {
            throw new IllegalArgumentException(n + " can't be represented as char");
        }
        return (char)n;
    }

    public static byte safeIntToByte(int n) {
        if (n < -128 || 127 < n) {
            throw new IllegalArgumentException(n + " can't be represented as byte (out of range)");
        }
        return (byte)n;
    }

    public static short safeIntToShort(int n) {
        if (n < Short.MIN_VALUE || Short.MAX_VALUE < n) {
            throw new IllegalArgumentException(n + " can't be represented as short (out of range)");
        }
        return (short)n;
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || Integer.MAX_VALUE < l) {
            throw new IllegalArgumentException(l + " can't be represented as int (out of range)");
        }
        return (int)l;
    }

    public static float safeDoubleToFloat(double d) {
        if (Double.isNaN(d)) {
            return Float.NaN;
        }
        if (Double.isInfinite(d)) {
            return d < 0.0 ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
        }
        if (d < (double)1.4E-45f || 3.4028234663852886E38 < d) {
            throw new IllegalArgumentException(d + " can't be represented as float (out of range)");
        }
        float f = (float)d;
        if ((double)f != d) {
            throw new IllegalArgumentException(d + " can't be represented as float (imprecise)");
        }
        return f;
    }
}

