/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.math.BigInteger;

@GwtIncompatible
final class DoubleUtils {
    static final long SIGNIFICAND_MASK = 0xFFFFFFFFFFFFFL;
    static final long EXPONENT_MASK = 0x7FF0000000000000L;
    static final long SIGN_MASK = Long.MIN_VALUE;
    static final int SIGNIFICAND_BITS = 52;
    static final int EXPONENT_BIAS = 1023;
    static final long IMPLICIT_BIT = 0x10000000000000L;
    private static final long ONE_BITS = Double.doubleToRawLongBits(1.0);

    private DoubleUtils() {
    }

    static double nextDown(double d) {
        return -Math.nextUp(-d);
    }

    static long getSignificand(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d), "not a normal value");
        int n = Math.getExponent(d);
        long l = Double.doubleToRawLongBits(d);
        return n == -1023 ? l << 1 : (l &= 0xFFFFFFFFFFFFFL) | 0x10000000000000L;
    }

    static boolean isFinite(double d) {
        return Math.getExponent(d) <= 1023;
    }

    static boolean isNormal(double d) {
        return Math.getExponent(d) >= -1022;
    }

    static double scaleNormalize(double d) {
        long l = Double.doubleToRawLongBits(d) & 0xFFFFFFFFFFFFFL;
        return Double.longBitsToDouble(l | ONE_BITS);
    }

    static double bigToDouble(BigInteger bigInteger) {
        BigInteger bigInteger2 = bigInteger.abs();
        int n = bigInteger2.bitLength() - 1;
        if (n < 63) {
            return bigInteger.longValue();
        }
        if (n > 1023) {
            return (double)bigInteger.signum() * Double.POSITIVE_INFINITY;
        }
        int n2 = n - 52 - 1;
        long l = bigInteger2.shiftRight(n2).longValue();
        long l2 = l >> 1;
        boolean bl = (l & 1L) != 0L && (((l2 &= 0xFFFFFFFFFFFFFL) & 1L) != 0L || bigInteger2.getLowestSetBit() < n2);
        long l3 = bl ? l2 + 1L : l2;
        long l4 = (long)(n + 1023) << 52;
        l4 += l3;
        return Double.longBitsToDouble(l4 |= (long)bigInteger.signum() & Long.MIN_VALUE);
    }

    static double ensureNonNegative(double d) {
        Preconditions.checkArgument(!Double.isNaN(d));
        if (d > 0.0) {
            return d;
        }
        return 0.0;
    }
}

