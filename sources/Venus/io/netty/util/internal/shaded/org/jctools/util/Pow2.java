/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.util;

public final class Pow2 {
    public static final int MAX_POW2 = 0x40000000;

    public static int roundToPowerOfTwo(int n) {
        if (n > 0x40000000) {
            throw new IllegalArgumentException("There is no larger power of 2 int for value:" + n + " since it exceeds 2^31.");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Given value:" + n + ". Expecting value >= 0.");
        }
        int n2 = 1 << 32 - Integer.numberOfLeadingZeros(n - 1);
        return n2;
    }

    public static boolean isPowerOfTwo(int n) {
        return (n & n - 1) == 0;
    }

    public static long align(long l, int n) {
        if (!Pow2.isPowerOfTwo(n)) {
            throw new IllegalArgumentException("alignment must be a power of 2:" + n);
        }
        return l + (long)(n - 1) & (long)(~(n - 1));
    }
}

