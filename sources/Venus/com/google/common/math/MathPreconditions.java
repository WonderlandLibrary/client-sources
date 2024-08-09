/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import javax.annotation.Nullable;

@GwtCompatible
@CanIgnoreReturnValue
final class MathPreconditions {
    static int checkPositive(@Nullable String string, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(string + " (" + n + ") must be > 0");
        }
        return n;
    }

    static long checkPositive(@Nullable String string, long l) {
        if (l <= 0L) {
            throw new IllegalArgumentException(string + " (" + l + ") must be > 0");
        }
        return l;
    }

    static BigInteger checkPositive(@Nullable String string, BigInteger bigInteger) {
        if (bigInteger.signum() <= 0) {
            throw new IllegalArgumentException(string + " (" + bigInteger + ") must be > 0");
        }
        return bigInteger;
    }

    static int checkNonNegative(@Nullable String string, int n) {
        if (n < 0) {
            throw new IllegalArgumentException(string + " (" + n + ") must be >= 0");
        }
        return n;
    }

    static long checkNonNegative(@Nullable String string, long l) {
        if (l < 0L) {
            throw new IllegalArgumentException(string + " (" + l + ") must be >= 0");
        }
        return l;
    }

    static BigInteger checkNonNegative(@Nullable String string, BigInteger bigInteger) {
        if (bigInteger.signum() < 0) {
            throw new IllegalArgumentException(string + " (" + bigInteger + ") must be >= 0");
        }
        return bigInteger;
    }

    static double checkNonNegative(@Nullable String string, double d) {
        if (!(d >= 0.0)) {
            throw new IllegalArgumentException(string + " (" + d + ") must be >= 0");
        }
        return d;
    }

    static void checkRoundingUnnecessary(boolean bl) {
        if (!bl) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }

    static void checkInRange(boolean bl) {
        if (!bl) {
            throw new ArithmeticException("not in range");
        }
    }

    static void checkNoOverflow(boolean bl) {
        if (!bl) {
            throw new ArithmeticException("overflow");
        }
    }

    private MathPreconditions() {
    }
}

