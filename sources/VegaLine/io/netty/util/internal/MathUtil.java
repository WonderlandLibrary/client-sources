/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

public final class MathUtil {
    private MathUtil() {
    }

    public static int findNextPositivePowerOfTwo(int value) {
        assert (value > Integer.MIN_VALUE && value < 0x40000000);
        return 1 << 32 - Integer.numberOfLeadingZeros(value - 1);
    }

    public static int safeFindNextPositivePowerOfTwo(int value) {
        return value <= 0 ? 1 : (value >= 0x40000000 ? 0x40000000 : MathUtil.findNextPositivePowerOfTwo(value));
    }

    public static boolean isOutOfBounds(int index, int length, int capacity) {
        return (index | length | index + length | capacity - (index + length)) < 0;
    }

    public static int compare(int x, int y) {
        return x < y ? -1 : (x > y ? 1 : 0);
    }

    public static int compare(long x, long y) {
        return x < y ? -1 : (x > y ? 1 : 0);
    }
}

