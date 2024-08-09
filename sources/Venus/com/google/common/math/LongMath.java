/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.math.MathPreconditions;
import com.google.common.primitives.UnsignedLongs;
import java.math.RoundingMode;

@GwtCompatible(emulated=true)
public final class LongMath {
    @VisibleForTesting
    static final long MAX_SIGNED_POWER_OF_TWO = 0x4000000000000000L;
    @VisibleForTesting
    static final long MAX_POWER_OF_SQRT2_UNSIGNED = -5402926248376769404L;
    @VisibleForTesting
    static final byte[] maxLog10ForLeadingZeros = new byte[]{19, 18, 18, 18, 18, 17, 17, 17, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 13, 13, 13, 12, 12, 12, 12, 11, 11, 11, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0};
    @GwtIncompatible
    @VisibleForTesting
    static final long[] powersOf10 = new long[]{1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};
    @GwtIncompatible
    @VisibleForTesting
    static final long[] halfPowersOf10 = new long[]{3L, 31L, 316L, 3162L, 31622L, 316227L, 3162277L, 31622776L, 316227766L, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
    @VisibleForTesting
    static final long FLOOR_SQRT_MAX_LONG = 3037000499L;
    static final long[] factorials = new long[]{1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
    static final int[] biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, 206, 169, 143, 125, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};
    @VisibleForTesting
    static final int[] biggestSimpleBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 2642246, 86251, 11724, 3218, 1313, 684, 419, 287, 214, 169, 139, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
    private static final int SIEVE_30 = -545925251;
    private static final long[][] millerRabinBaseSets = new long[][]{{291830L, 126401071349994536L}, {885594168L, 725270293939359937L, 3569819667048198375L}, {273919523040L, 15L, 7363882082L, 992620450144556L}, {47636622961200L, 2L, 2570940L, 211991001L, 3749873356L}, {7999252175582850L, 2L, 4130806001517L, 149795463772692060L, 186635894390467037L, 3967304179347715805L}, {585226005592931976L, 2L, 123635709730000L, 9233062284813009L, 43835965440333360L, 761179012939631437L, 1263739024124850375L}, {Long.MAX_VALUE, 2L, 325L, 9375L, 28178L, 450775L, 9780504L, 1795265022L}};

    @Beta
    public static long ceilingPowerOfTwo(long l) {
        MathPreconditions.checkPositive("x", l);
        if (l > 0x4000000000000000L) {
            throw new ArithmeticException("ceilingPowerOfTwo(" + l + ") is not representable as a long");
        }
        return 1L << -Long.numberOfLeadingZeros(l - 1L);
    }

    @Beta
    public static long floorPowerOfTwo(long l) {
        MathPreconditions.checkPositive("x", l);
        return 1L << 63 - Long.numberOfLeadingZeros(l);
    }

    public static boolean isPowerOfTwo(long l) {
        return l > 0L & (l & l - 1L) == 0L;
    }

    @VisibleForTesting
    static int lessThanBranchFree(long l, long l2) {
        return (int)((l - l2 ^ 0xFFFFFFFFFFFFFFFFL ^ 0xFFFFFFFFFFFFFFFFL) >>> 63);
    }

    public static int log2(long l, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", l);
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(LongMath.isPowerOfTwo(l));
            }
            case 2: 
            case 3: {
                return 63 - Long.numberOfLeadingZeros(l);
            }
            case 4: 
            case 5: {
                return 64 - Long.numberOfLeadingZeros(l - 1L);
            }
            case 6: 
            case 7: 
            case 8: {
                int n = Long.numberOfLeadingZeros(l);
                long l2 = -5402926248376769404L >>> n;
                int n2 = 63 - n;
                return n2 + LongMath.lessThanBranchFree(l2, l);
            }
        }
        throw new AssertionError((Object)"impossible");
    }

    @GwtIncompatible
    public static int log10(long l, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", l);
        int n = LongMath.log10Floor(l);
        long l2 = powersOf10[n];
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(l == l2);
            }
            case 2: 
            case 3: {
                return n;
            }
            case 4: 
            case 5: {
                return n + LongMath.lessThanBranchFree(l2, l);
            }
            case 6: 
            case 7: 
            case 8: {
                return n + LongMath.lessThanBranchFree(halfPowersOf10[n], l);
            }
        }
        throw new AssertionError();
    }

    @GwtIncompatible
    static int log10Floor(long l) {
        byte by = maxLog10ForLeadingZeros[Long.numberOfLeadingZeros(l)];
        return by - LongMath.lessThanBranchFree(l, powersOf10[by]);
    }

    @GwtIncompatible
    public static long pow(long l, int n) {
        MathPreconditions.checkNonNegative("exponent", n);
        if (-2L <= l && l <= 2L) {
            switch ((int)l) {
                case 0: {
                    return n == 0 ? 1L : 0L;
                }
                case 1: {
                    return 1L;
                }
                case -1: {
                    return (n & 1) == 0 ? 1L : -1L;
                }
                case 2: {
                    return n < 64 ? 1L << n : 0L;
                }
                case -2: {
                    if (n < 64) {
                        return (n & 1) == 0 ? 1L << n : -(1L << n);
                    }
                    return 0L;
                }
            }
            throw new AssertionError();
        }
        long l2 = 1L;
        while (true) {
            switch (n) {
                case 0: {
                    return l2;
                }
                case 1: {
                    return l2 * l;
                }
            }
            l2 *= (n & 1) == 0 ? 1L : l;
            l *= l;
            n >>= 1;
        }
    }

    @GwtIncompatible
    public static long sqrt(long l, RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", l);
        if (LongMath.fitsInInt(l)) {
            return IntMath.sqrt((int)l, roundingMode);
        }
        long l2 = (long)Math.sqrt(l);
        long l3 = l2 * l2;
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(l3 == l);
                return l2;
            }
            case 2: 
            case 3: {
                if (l < l3) {
                    return l2 - 1L;
                }
                return l2;
            }
            case 4: 
            case 5: {
                if (l > l3) {
                    return l2 + 1L;
                }
                return l2;
            }
            case 6: 
            case 7: 
            case 8: {
                long l4 = l2 - (long)(l < l3 ? 1 : 0);
                long l5 = l4 * l4 + l4;
                return l4 + (long)LongMath.lessThanBranchFree(l5, l);
            }
        }
        throw new AssertionError();
    }

    @GwtIncompatible
    public static long divide(long l, long l2, RoundingMode roundingMode) {
        boolean bl;
        Preconditions.checkNotNull(roundingMode);
        long l3 = l / l2;
        long l4 = l - l2 * l3;
        if (l4 == 0L) {
            return l3;
        }
        int n = 1 | (int)((l ^ l2) >> 63);
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(l4 == 0L);
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
                bl = n > 0;
                break;
            }
            case 3: {
                bl = n < 0;
                break;
            }
            case 6: 
            case 7: 
            case 8: {
                long l5 = Math.abs(l4);
                long l6 = l5 - (Math.abs(l2) - l5);
                if (l6 == 0L) {
                    bl = roundingMode == RoundingMode.HALF_UP | roundingMode == RoundingMode.HALF_EVEN & (l3 & 1L) != 0L;
                    break;
                }
                bl = l6 > 0L;
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return bl ? l3 + (long)n : l3;
    }

    @GwtIncompatible
    public static int mod(long l, int n) {
        return (int)LongMath.mod(l, (long)n);
    }

    @GwtIncompatible
    public static long mod(long l, long l2) {
        if (l2 <= 0L) {
            throw new ArithmeticException("Modulus must be positive");
        }
        long l3 = l % l2;
        return l3 >= 0L ? l3 : l3 + l2;
    }

    public static long gcd(long l, long l2) {
        MathPreconditions.checkNonNegative("a", l);
        MathPreconditions.checkNonNegative("b", l2);
        if (l == 0L) {
            return l2;
        }
        if (l2 == 0L) {
            return l;
        }
        int n = Long.numberOfTrailingZeros(l);
        l >>= n;
        int n2 = Long.numberOfTrailingZeros(l2);
        l2 >>= n2;
        while (l != l2) {
            long l3 = l - l2;
            long l4 = l3 & l3 >> 63;
            l = l3 - l4 - l4;
            l2 += l4;
            l >>= Long.numberOfTrailingZeros(l);
        }
        return l << Math.min(n, n2);
    }

    @GwtIncompatible
    public static long checkedAdd(long l, long l2) {
        long l3 = l + l2;
        MathPreconditions.checkNoOverflow((l ^ l2) < 0L | (l ^ l3) >= 0L);
        return l3;
    }

    @GwtIncompatible
    public static long checkedSubtract(long l, long l2) {
        long l3 = l - l2;
        MathPreconditions.checkNoOverflow((l ^ l2) >= 0L | (l ^ l3) >= 0L);
        return l3;
    }

    @GwtIncompatible
    public static long checkedMultiply(long l, long l2) {
        int n = Long.numberOfLeadingZeros(l) + Long.numberOfLeadingZeros(l ^ 0xFFFFFFFFFFFFFFFFL) + Long.numberOfLeadingZeros(l2) + Long.numberOfLeadingZeros(l2 ^ 0xFFFFFFFFFFFFFFFFL);
        if (n > 65) {
            return l * l2;
        }
        MathPreconditions.checkNoOverflow(n >= 64);
        MathPreconditions.checkNoOverflow(l >= 0L | l2 != Long.MIN_VALUE);
        long l3 = l * l2;
        MathPreconditions.checkNoOverflow(l == 0L || l3 / l == l2);
        return l3;
    }

    @GwtIncompatible
    public static long checkedPow(long l, int n) {
        MathPreconditions.checkNonNegative("exponent", n);
        if (l >= -2L & l <= 2L) {
            switch ((int)l) {
                case 0: {
                    return n == 0 ? 1L : 0L;
                }
                case 1: {
                    return 1L;
                }
                case -1: {
                    return (n & 1) == 0 ? 1L : -1L;
                }
                case 2: {
                    MathPreconditions.checkNoOverflow(n < 63);
                    return 1L << n;
                }
                case -2: {
                    MathPreconditions.checkNoOverflow(n < 64);
                    return (n & 1) == 0 ? 1L << n : -1L << n;
                }
            }
            throw new AssertionError();
        }
        long l2 = 1L;
        while (true) {
            switch (n) {
                case 0: {
                    return l2;
                }
                case 1: {
                    return LongMath.checkedMultiply(l2, l);
                }
            }
            if ((n & 1) != 0) {
                l2 = LongMath.checkedMultiply(l2, l);
            }
            if ((n >>= 1) <= 0) continue;
            MathPreconditions.checkNoOverflow(-3037000499L <= l && l <= 3037000499L);
            l *= l;
        }
    }

    @Beta
    public static long saturatedAdd(long l, long l2) {
        long l3;
        if ((l ^ l2) < 0L | (l ^ (l3 = l + l2)) >= 0L) {
            return l3;
        }
        return Long.MAX_VALUE + (l3 >>> 63 ^ 1L);
    }

    @Beta
    public static long saturatedSubtract(long l, long l2) {
        long l3;
        if ((l ^ l2) >= 0L | (l ^ (l3 = l - l2)) >= 0L) {
            return l3;
        }
        return Long.MAX_VALUE + (l3 >>> 63 ^ 1L);
    }

    @Beta
    public static long saturatedMultiply(long l, long l2) {
        int n = Long.numberOfLeadingZeros(l) + Long.numberOfLeadingZeros(l ^ 0xFFFFFFFFFFFFFFFFL) + Long.numberOfLeadingZeros(l2) + Long.numberOfLeadingZeros(l2 ^ 0xFFFFFFFFFFFFFFFFL);
        if (n > 65) {
            return l * l2;
        }
        long l3 = Long.MAX_VALUE + ((l ^ l2) >>> 63);
        if (n < 64 | l < 0L & l2 == Long.MIN_VALUE) {
            return l3;
        }
        long l4 = l * l2;
        if (l == 0L || l4 / l == l2) {
            return l4;
        }
        return l3;
    }

    @Beta
    public static long saturatedPow(long l, int n) {
        MathPreconditions.checkNonNegative("exponent", n);
        if (l >= -2L & l <= 2L) {
            switch ((int)l) {
                case 0: {
                    return n == 0 ? 1L : 0L;
                }
                case 1: {
                    return 1L;
                }
                case -1: {
                    return (n & 1) == 0 ? 1L : -1L;
                }
                case 2: {
                    if (n >= 63) {
                        return Long.MAX_VALUE;
                    }
                    return 1L << n;
                }
                case -2: {
                    if (n >= 64) {
                        return Long.MAX_VALUE + (long)(n & 1);
                    }
                    return (n & 1) == 0 ? 1L << n : -1L << n;
                }
            }
            throw new AssertionError();
        }
        long l2 = 1L;
        long l3 = Long.MAX_VALUE + (l >>> 63 & (long)(n & 1));
        while (true) {
            switch (n) {
                case 0: {
                    return l2;
                }
                case 1: {
                    return LongMath.saturatedMultiply(l2, l);
                }
            }
            if ((n & 1) != 0) {
                l2 = LongMath.saturatedMultiply(l2, l);
            }
            if ((n >>= 1) <= 0) continue;
            if (-3037000499L > l | l > 3037000499L) {
                return l3;
            }
            l *= l;
        }
    }

    @GwtIncompatible
    public static long factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        return n < factorials.length ? factorials[n] : Long.MAX_VALUE;
    }

    public static long binomial(int n, int n2) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", n2);
        Preconditions.checkArgument(n2 <= n, "k (%s) > n (%s)", n2, n);
        if (n2 > n >> 1) {
            n2 = n - n2;
        }
        switch (n2) {
            case 0: {
                return 1L;
            }
            case 1: {
                return n;
            }
        }
        if (n < factorials.length) {
            return factorials[n] / (factorials[n2] * factorials[n - n2]);
        }
        if (n2 >= biggestBinomials.length || n > biggestBinomials[n2]) {
            return Long.MAX_VALUE;
        }
        if (n2 < biggestSimpleBinomials.length && n <= biggestSimpleBinomials[n2]) {
            long l = n--;
            for (int i = 2; i <= n2; ++i) {
                l *= (long)n;
                l /= (long)i;
                --n;
            }
            return l;
        }
        int n3 = LongMath.log2(n, RoundingMode.CEILING);
        long l = 1L;
        long l2 = n--;
        long l3 = 1L;
        int n4 = n3;
        int n5 = 2;
        while (n5 <= n2) {
            if (n4 + n3 < 63) {
                l2 *= (long)n;
                l3 *= (long)n5;
                n4 += n3;
            } else {
                l = LongMath.multiplyFraction(l, l2, l3);
                l2 = n;
                l3 = n5;
                n4 = n3;
            }
            ++n5;
            --n;
        }
        return LongMath.multiplyFraction(l, l2, l3);
    }

    static long multiplyFraction(long l, long l2, long l3) {
        if (l == 1L) {
            return l2 / l3;
        }
        long l4 = LongMath.gcd(l, l3);
        return (l /= l4) * (l2 / (l3 /= l4));
    }

    static boolean fitsInInt(long l) {
        return (long)((int)l) == l;
    }

    public static long mean(long l, long l2) {
        return (l & l2) + ((l ^ l2) >> 1);
    }

    @GwtIncompatible
    @Beta
    public static boolean isPrime(long l) {
        if (l < 2L) {
            MathPreconditions.checkNonNegative("n", l);
            return true;
        }
        if (l == 2L || l == 3L || l == 5L || l == 7L || l == 11L || l == 13L) {
            return false;
        }
        if ((0xDF75D77D & 1 << (int)(l % 30L)) != 0) {
            return true;
        }
        if (l % 7L == 0L || l % 11L == 0L || l % 13L == 0L) {
            return true;
        }
        if (l < 289L) {
            return false;
        }
        for (long[] lArray : millerRabinBaseSets) {
            if (l > lArray[0]) continue;
            for (int i = 1; i < lArray.length; ++i) {
                if (MillerRabinTester.test(lArray[i], l)) continue;
                return true;
            }
            return false;
        }
        throw new AssertionError();
    }

    private LongMath() {
    }

    private static enum MillerRabinTester {
        SMALL{

            @Override
            long mulMod(long l, long l2, long l3) {
                return l * l2 % l3;
            }

            @Override
            long squareMod(long l, long l2) {
                return l * l % l2;
            }
        }
        ,
        LARGE{

            private long plusMod(long l, long l2, long l3) {
                return l >= l3 - l2 ? l + l2 - l3 : l + l2;
            }

            private long times2ToThe32Mod(long l, long l2) {
                int n;
                int n2 = 32;
                do {
                    n = Math.min(n2, Long.numberOfLeadingZeros(l));
                    l = UnsignedLongs.remainder(l << n, l2);
                } while ((n2 -= n) > 0);
                return l;
            }

            @Override
            long mulMod(long l, long l2, long l3) {
                long l4 = l >>> 32;
                long l5 = l2 >>> 32;
                long l6 = l & 0xFFFFFFFFL;
                long l7 = l2 & 0xFFFFFFFFL;
                long l8 = this.times2ToThe32Mod(l4 * l5, l3);
                if ((l8 += l4 * l7) < 0L) {
                    l8 = UnsignedLongs.remainder(l8, l3);
                }
                l8 += l6 * l5;
                l8 = this.times2ToThe32Mod(l8, l3);
                return this.plusMod(l8, UnsignedLongs.remainder(l6 * l7, l3), l3);
            }

            @Override
            long squareMod(long l, long l2) {
                long l3 = l >>> 32;
                long l4 = l & 0xFFFFFFFFL;
                long l5 = this.times2ToThe32Mod(l3 * l3, l2);
                long l6 = l3 * l4 * 2L;
                if (l6 < 0L) {
                    l6 = UnsignedLongs.remainder(l6, l2);
                }
                l5 += l6;
                l5 = this.times2ToThe32Mod(l5, l2);
                return this.plusMod(l5, UnsignedLongs.remainder(l4 * l4, l2), l2);
            }
        };


        private MillerRabinTester() {
        }

        static boolean test(long l, long l2) {
            return (l2 <= 3037000499L ? SMALL : LARGE).testWitness(l, l2);
        }

        abstract long mulMod(long var1, long var3, long var5);

        abstract long squareMod(long var1, long var3);

        private long powMod(long l, long l2, long l3) {
            long l4 = 1L;
            while (l2 != 0L) {
                if ((l2 & 1L) != 0L) {
                    l4 = this.mulMod(l4, l, l3);
                }
                l = this.squareMod(l, l3);
                l2 >>= 1;
            }
            return l4;
        }

        private boolean testWitness(long l, long l2) {
            int n = Long.numberOfTrailingZeros(l2 - 1L);
            long l3 = l2 - 1L >> n;
            if ((l %= l2) == 0L) {
                return false;
            }
            long l4 = this.powMod(l, l3, l2);
            if (l4 == 1L) {
                return false;
            }
            int n2 = 0;
            while (l4 != l2 - 1L) {
                if (++n2 == n) {
                    return true;
                }
                l4 = this.squareMod(l4, l2);
            }
            return false;
        }

        MillerRabinTester(1 var3_3) {
            this();
        }
    }
}

