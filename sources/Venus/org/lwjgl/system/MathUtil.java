/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

public final class MathUtil {
    private MathUtil() {
    }

    public static boolean mathIsPoT(int n) {
        return Integer.bitCount(n) == 1;
    }

    public static int mathRoundPoT(int n) {
        return 1 << 32 - Integer.numberOfLeadingZeros(n - 1);
    }

    public static boolean mathHasZeroByte(int n) {
        return (n - 0x1010101 & ~n & 0x80808080) != 0;
    }

    public static boolean mathHasZeroByte(long l) {
        return (l - 0x101010101010101L & (l ^ 0xFFFFFFFFFFFFFFFFL) & 0x8080808080808080L) != 0L;
    }

    public static boolean mathHasZeroShort(int n) {
        return (n & 0xFFFF) == 0 || n >>> 16 == 0;
    }

    public static boolean mathHasZeroShort(long l) {
        return (l - 0x1000100010001L & (l ^ 0xFFFFFFFFFFFFFFFFL) & 0x8000800080008000L) != 0L;
    }

    public static long mathMultiplyHighU64(long l, long l2) {
        long l3 = l & 0xFFFFFFFFL;
        long l4 = l >>> 32;
        long l5 = l2 & 0xFFFFFFFFL;
        long l6 = l2 >>> 32;
        long l7 = l4 * l5 + (l3 * l5 >>> 32);
        return l4 * l6 + (l7 >>> 32) + ((l7 & 0xFFFFFFFFL) + l3 * l6 >>> 32);
    }

    public static long mathMultiplyHighS64(long l, long l2) {
        long l3 = l & 0xFFFFFFFFL;
        long l4 = l >> 32;
        long l5 = l2 & 0xFFFFFFFFL;
        long l6 = l2 >> 32;
        long l7 = l4 * l5 + (l3 * l5 >>> 32);
        return l4 * l6 + (l7 >> 32) + ((l7 & 0xFFFFFFFFL) + l3 * l6 >> 32);
    }

    public static long mathDivideUnsigned(long l, long l2) {
        if (0L <= l2) {
            return 0L <= l ? l / l2 : MathUtil.udivdi3(l, l2);
        }
        return Long.compareUnsigned(l, l2) < 0 ? 0L : 1L;
    }

    public static long mathRemainderUnsigned(long l, long l2) {
        if (0L < l && 0L < l2) {
            return l % l2;
        }
        return Long.compareUnsigned(l, l2) < 0 ? l : l - l2 * MathUtil.udivdi3(l, l2);
    }

    private static long udivdi3(long l, long l2) {
        if (l2 >>> 32 == 0L) {
            if (l >>> 32 < l2) {
                long l3 = (l >>> 1) / l2 << Long.numberOfLeadingZeros(l2) >>> 31;
                if (l - l3 * l2 >= l2) {
                    ++l3;
                }
                return l3;
            }
            long l4 = l >>> 32;
            long l5 = l4 / l2;
            long l6 = (l4 - l5 * l2 << 32 | l & 0xFFFFFFFFL) / l2;
            return l5 << 32 | l6;
        }
        int n = Long.numberOfLeadingZeros(l2);
        long l7 = (l >>> 1) / (l2 << n >>> 32) << n >>> 31;
        if (l7 != 0L) {
            --l7;
        }
        if (Long.compareUnsigned(l - l7 * l2, l2) >= 0) {
            ++l7;
        }
        return l7;
    }
}

