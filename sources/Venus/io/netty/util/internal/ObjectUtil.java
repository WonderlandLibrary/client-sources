/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import java.util.Collection;

public final class ObjectUtil {
    private ObjectUtil() {
    }

    public static <T> T checkNotNull(T t, String string) {
        if (t == null) {
            throw new NullPointerException(string);
        }
        return t;
    }

    public static int checkPositive(int n, String string) {
        if (n <= 0) {
            throw new IllegalArgumentException(string + ": " + n + " (expected: > 0)");
        }
        return n;
    }

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

    public static long checkPositiveOrZero(long l, String string) {
        if (l < 0L) {
            throw new IllegalArgumentException(string + ": " + l + " (expected: >= 0)");
        }
        return l;
    }

    public static <T> T[] checkNonEmpty(T[] TArray, String string) {
        ObjectUtil.checkNotNull(TArray, string);
        ObjectUtil.checkPositive(TArray.length, string + ".length");
        return TArray;
    }

    public static <T extends Collection<?>> T checkNonEmpty(T t, String string) {
        ObjectUtil.checkNotNull(t, string);
        ObjectUtil.checkPositive(t.size(), string + ".size");
        return t;
    }

    public static int intValue(Integer n, int n2) {
        return n != null ? n : n2;
    }

    public static long longValue(Long l, long l2) {
        return l != null ? l : l2;
    }
}

