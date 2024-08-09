/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

public final class MathUtil {
    public static int ceilLog2(int n) {
        return n > 0 ? 32 - Integer.numberOfLeadingZeros(n - 1) : 0;
    }

    public static int clamp(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        return n > n3 ? n3 : n;
    }
}

