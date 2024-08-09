/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.util;

public final class RangeUtil {
    public static long checkPositive(long l, String string) {
        if (l <= 0L) {
            throw new IllegalArgumentException(string + ": " + l + " (expected: > 0)");
        }
        return l;
    }

    public static int checkPositiveOrZero(int n, String string) {
        if (n < 0) {
            throw new IllegalArgumentException(string + ": " + n + " (expected: >= 0)");
        }
        return n;
    }

    public static int checkLessThan(int n, int n2, String string) {
        if (n >= n2) {
            throw new IllegalArgumentException(string + ": " + n + " (expected: < " + n2 + ')');
        }
        return n;
    }

    public static int checkLessThanOrEqual(int n, long l, String string) {
        if ((long)n > l) {
            throw new IllegalArgumentException(string + ": " + n + " (expected: <= " + l + ')');
        }
        return n;
    }

    public static int checkGreaterThanOrEqual(int n, int n2, String string) {
        if (n < n2) {
            throw new IllegalArgumentException(string + ": " + n + " (expected: >= " + n2 + ')');
        }
        return n;
    }
}

