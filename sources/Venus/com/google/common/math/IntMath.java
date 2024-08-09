/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.math.MathPreconditions;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;

@GwtCompatible(emulated=true)
public final class IntMath {
    @VisibleForTesting
    static final int MAX_SIGNED_POWER_OF_TWO = 0x40000000;
    @VisibleForTesting
    static final int MAX_POWER_OF_SQRT2_UNSIGNED = -1257966797;
    @VisibleForTesting
    static final byte[] maxLog10ForLeadingZeros = new byte[]{9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};
    @VisibleForTesting
    static final int[] powersOf10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    @VisibleForTesting
    static final int[] halfPowersOf10 = new int[]{3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
    @VisibleForTesting
    static final int FLOOR_SQRT_MAX_INT = 46340;
    private static final int[] factorials = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};
    @VisibleForTesting
    static int[] biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};

    @Beta
    public static int ceilingPowerOfTwo(int n) {
        MathPreconditions.checkPositive("x", n);
        if (n > 0x40000000) {
            throw new ArithmeticException("ceilingPowerOfTwo(" + n + ") not representable as an int");
        }
        return 1 << -Integer.numberOfLeadingZeros(n - 1);
    }

    @Beta
    public static int floorPowerOfTwo(int n) {
        MathPreconditions.checkPositive("x", n);
        return Integer.highestOneBit(n);
    }

    public static boolean isPowerOfTwo(int n) {
        return n > 0 & (n & n - 1) == 0;
    }

    @VisibleForTesting
    static int lessThanBranchFree(int n, int n2) {
        return ~(~(n - n2)) >>> 31;
    }

    public static int log2(int n, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", n);
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(IntMath.isPowerOfTwo(n));
            }
            case 2: 
            case 3: {
                return 31 - Integer.numberOfLeadingZeros(n);
            }
            case 4: 
            case 5: {
                return 32 - Integer.numberOfLeadingZeros(n - 1);
            }
            case 6: 
            case 7: 
            case 8: {
                int n2 = Integer.numberOfLeadingZeros(n);
                int n3 = -1257966797 >>> n2;
                int n4 = 31 - n2;
                return n4 + IntMath.lessThanBranchFree(n3, n);
            }
        }
        throw new AssertionError();
    }

    @GwtIncompatible
    public static int log10(int n, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", n);
        int n2 = IntMath.log10Floor(n);
        int n3 = powersOf10[n2];
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(n == n3);
            }
            case 2: 
            case 3: {
                return n2;
            }
            case 4: 
            case 5: {
                return n2 + IntMath.lessThanBranchFree(n3, n);
            }
            case 6: 
            case 7: 
            case 8: {
                return n2 + IntMath.lessThanBranchFree(halfPowersOf10[n2], n);
            }
        }
        throw new AssertionError();
    }

    private static int log10Floor(int n) {
        byte by = maxLog10ForLeadingZeros[Integer.numberOfLeadingZeros(n)];
        return by - IntMath.lessThanBranchFree(n, powersOf10[by]);
    }

    @GwtIncompatible
    public static int pow(int n, int n2) {
        MathPreconditions.checkNonNegative("exponent", n2);
        switch (n) {
            case 0: {
                return n2 == 0 ? 1 : 0;
            }
            case 1: {
                return 0;
            }
            case -1: {
                return (n2 & 1) == 0 ? 1 : -1;
            }
            case 2: {
                return n2 < 32 ? 1 << n2 : 0;
            }
            case -2: {
                if (n2 < 32) {
                    return (n2 & 1) == 0 ? 1 << n2 : -(1 << n2);
                }
                return 1;
            }
        }
        int n3 = 1;
        while (true) {
            switch (n2) {
                case 0: {
                    return n3;
                }
                case 1: {
                    return n * n3;
                }
            }
            n3 *= (n2 & 1) == 0 ? 1 : n;
            n *= n;
            n2 >>= 1;
        }
    }

    @GwtIncompatible
    public static int sqrt(int n, RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", n);
        int n2 = IntMath.sqrtFloor(n);
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(n2 * n2 == n);
            }
            case 2: 
            case 3: {
                return n2;
            }
            case 4: 
            case 5: {
                return n2 + IntMath.lessThanBranchFree(n2 * n2, n);
            }
            case 6: 
            case 7: 
            case 8: {
                int n3 = n2 * n2 + n2;
                return n2 + IntMath.lessThanBranchFree(n3, n);
            }
        }
        throw new AssertionError();
    }

    private static int sqrtFloor(int n) {
        return (int)Math.sqrt(n);
    }

    public static int divide(int n, int n2, RoundingMode roundingMode) {
        boolean bl;
        Preconditions.checkNotNull(roundingMode);
        if (n2 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        int n3 = n / n2;
        int n4 = n - n2 * n3;
        if (n4 == 0) {
            return n3;
        }
        int n5 = 1 | (n ^ n2) >> 31;
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(n4 == 0);
            }
            case 2: {
                bl = false;
                break;
            }
            case 4: {
                bl = true;
                break;
            }
            case 5: {
                bl = n5 > 0;
                break;
            }
            case 3: {
                bl = n5 < 0;
                break;
            }
            case 6: 
            case 7: 
            case 8: {
                int n6 = Math.abs(n4);
                int n7 = n6 - (Math.abs(n2) - n6);
                if (n7 == 0) {
                    bl = roundingMode == RoundingMode.HALF_UP || roundingMode == RoundingMode.HALF_EVEN & (n3 & 1) != 0;
                    break;
                }
                bl = n7 > 0;
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return bl ? n3 + n5 : n3;
    }

    public static int mod(int n, int n2) {
        if (n2 <= 0) {
            throw new ArithmeticException("Modulus " + n2 + " must be > 0");
        }
        int n3 = n % n2;
        return n3 >= 0 ? n3 : n3 + n2;
    }

    public static int gcd(int n, int n2) {
        MathPreconditions.checkNonNegative("a", n);
        MathPreconditions.checkNonNegative("b", n2);
        if (n == 0) {
            return n2;
        }
        if (n2 == 0) {
            return n;
        }
        int n3 = Integer.numberOfTrailingZeros(n);
        n >>= n3;
        int n4 = Integer.numberOfTrailingZeros(n2);
        n2 >>= n4;
        while (n != n2) {
            int n5 = n - n2;
            int n6 = n5 & n5 >> 31;
            n = n5 - n6 - n6;
            n2 += n6;
            n >>= Integer.numberOfTrailingZeros(n);
        }
        return n << Math.min(n3, n4);
    }

    public static int checkedAdd(int n, int n2) {
        long l = (long)n + (long)n2;
        MathPreconditions.checkNoOverflow(l == (long)((int)l));
        return (int)l;
    }

    public static int checkedSubtract(int n, int n2) {
        long l = (long)n - (long)n2;
        MathPreconditions.checkNoOverflow(l == (long)((int)l));
        return (int)l;
    }

    public static int checkedMultiply(int n, int n2) {
        long l = (long)n * (long)n2;
        MathPreconditions.checkNoOverflow(l == (long)((int)l));
        return (int)l;
    }

    public static int checkedPow(int n, int n2) {
        MathPreconditions.checkNonNegative("exponent", n2);
        switch (n) {
            case 0: {
                return n2 == 0 ? 1 : 0;
            }
            case 1: {
                return 0;
            }
            case -1: {
                return (n2 & 1) == 0 ? 1 : -1;
            }
            case 2: {
                MathPreconditions.checkNoOverflow(n2 < 31);
                return 1 << n2;
            }
            case -2: {
                MathPreconditions.checkNoOverflow(n2 < 32);
                return (n2 & 1) == 0 ? 1 << n2 : -1 << n2;
            }
        }
        int n3 = 1;
        while (true) {
            switch (n2) {
                case 0: {
                    return n3;
                }
                case 1: {
                    return IntMath.checkedMultiply(n3, n);
                }
            }
            if ((n2 & 1) != 0) {
                n3 = IntMath.checkedMultiply(n3, n);
            }
            if ((n2 >>= 1) <= 0) continue;
            MathPreconditions.checkNoOverflow(-46340 <= n & n <= 46340);
            n *= n;
        }
    }

    @Beta
    public static int saturatedAdd(int n, int n2) {
        return Ints.saturatedCast((long)n + (long)n2);
    }

    @Beta
    public static int saturatedSubtract(int n, int n2) {
        return Ints.saturatedCast((long)n - (long)n2);
    }

    @Beta
    public static int saturatedMultiply(int n, int n2) {
        return Ints.saturatedCast((long)n * (long)n2);
    }

    @Beta
    public static int saturatedPow(int n, int n2) {
        MathPreconditions.checkNonNegative("exponent", n2);
        switch (n) {
            case 0: {
                return n2 == 0 ? 1 : 0;
            }
            case 1: {
                return 0;
            }
            case -1: {
                return (n2 & 1) == 0 ? 1 : -1;
            }
            case 2: {
                if (n2 >= 31) {
                    return 0;
                }
                return 1 << n2;
            }
            case -2: {
                if (n2 >= 32) {
                    return Integer.MAX_VALUE + (n2 & 1);
                }
                return (n2 & 1) == 0 ? 1 << n2 : -1 << n2;
            }
        }
        int n3 = 1;
        int n4 = Integer.MAX_VALUE + (n >>> 31 & (n2 & 1));
        while (true) {
            switch (n2) {
                case 0: {
                    return n3;
                }
                case 1: {
                    return IntMath.saturatedMultiply(n3, n);
                }
            }
            if ((n2 & 1) != 0) {
                n3 = IntMath.saturatedMultiply(n3, n);
            }
            if ((n2 >>= 1) <= 0) continue;
            if (-46340 > n | n > 46340) {
                return n4;
            }
            n *= n;
        }
    }

    public static int factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        return n < factorials.length ? factorials[n] : Integer.MAX_VALUE;
    }

    @GwtIncompatible
    public static int binomial(int n, int n2) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", n2);
        Preconditions.checkArgument(n2 <= n, "k (%s) > n (%s)", n2, n);
        if (n2 > n >> 1) {
            n2 = n - n2;
        }
        if (n2 >= biggestBinomials.length || n > biggestBinomials[n2]) {
            return 0;
        }
        switch (n2) {
            case 0: {
                return 0;
            }
            case 1: {
                return n;
            }
        }
        long l = 1L;
        for (int i = 0; i < n2; ++i) {
            l *= (long)(n - i);
            l /= (long)(i + 1);
        }
        return (int)l;
    }

    public static int mean(int n, int n2) {
        return (n & n2) + ((n ^ n2) >> 1);
    }

    @GwtIncompatible
    @Beta
    public static boolean isPrime(int n) {
        return LongMath.isPrime(n);
    }

    private IntMath() {
    }
}

