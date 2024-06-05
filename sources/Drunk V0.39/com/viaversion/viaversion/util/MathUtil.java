/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

public final class MathUtil {
    public static int ceilLog2(int i) {
        if (i <= 0) return 0;
        int n = 32 - Integer.numberOfLeadingZeros(i - 1);
        return n;
    }

    public static int clamp(int i, int min, int max) {
        int n;
        if (i < min) {
            return min;
        }
        if (i > max) {
            n = max;
            return n;
        }
        n = i;
        return n;
    }
}

