/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

public final class MathUtil {
    static final boolean $assertionsDisabled = !MathUtil.class.desiredAssertionStatus();

    private MathUtil() {
    }

    public static int findNextPositivePowerOfTwo(int n) {
        if (!($assertionsDisabled || n > Integer.MIN_VALUE && n < 0x40000000)) {
            throw new AssertionError();
        }
        return 1 << 32 - Integer.numberOfLeadingZeros(n - 1);
    }

    public static int safeFindNextPositivePowerOfTwo(int n) {
        return n <= 0 ? 1 : (n >= 0x40000000 ? 0x40000000 : MathUtil.findNextPositivePowerOfTwo(n));
    }

    public static boolean isOutOfBounds(int n, int n2, int n3) {
        return (n | n2 | n + n2 | n3 - (n + n2)) < 0;
    }

    public static int compare(int n, int n2) {
        return n < n2 ? -1 : (n > n2 ? 1 : 0);
    }

    public static int compare(long l, long l2) {
        return l < l2 ? -1 : (l > l2 ? 1 : 0);
    }
}

