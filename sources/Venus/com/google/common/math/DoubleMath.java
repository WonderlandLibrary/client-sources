/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.math.DoubleUtils;
import com.google.common.math.LongMath;
import com.google.common.math.MathPreconditions;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;

@GwtCompatible(emulated=true)
public final class DoubleMath {
    private static final double MIN_INT_AS_DOUBLE = -2.147483648E9;
    private static final double MAX_INT_AS_DOUBLE = 2.147483647E9;
    private static final double MIN_LONG_AS_DOUBLE = -9.223372036854776E18;
    private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 9.223372036854776E18;
    private static final double LN_2 = Math.log(2.0);
    @VisibleForTesting
    static final int MAX_FACTORIAL = 170;
    @VisibleForTesting
    static final double[] everySixteenthFactorial = new double[]{1.0, 2.0922789888E13, 2.631308369336935E35, 1.2413915592536073E61, 1.2688693218588417E89, 7.156945704626381E118, 9.916779348709496E149, 1.974506857221074E182, 3.856204823625804E215, 5.5502938327393044E249, 4.7147236359920616E284};

    @GwtIncompatible
    static double roundIntermediate(double d, RoundingMode roundingMode) {
        if (!DoubleUtils.isFinite(d)) {
            throw new ArithmeticException("input is infinite or NaN");
        }
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(DoubleMath.isMathematicalInteger(d));
                return d;
            }
            case 2: {
                if (d >= 0.0 || DoubleMath.isMathematicalInteger(d)) {
                    return d;
                }
                return (long)d - 1L;
            }
            case 3: {
                if (d <= 0.0 || DoubleMath.isMathematicalInteger(d)) {
                    return d;
                }
                return (long)d + 1L;
            }
            case 4: {
                return d;
            }
            case 5: {
                if (DoubleMath.isMathematicalInteger(d)) {
                    return d;
                }
                return (long)d + (long)(d > 0.0 ? 1 : -1);
            }
            case 6: {
                return Math.rint(d);
            }
            case 7: {
                double d2 = Math.rint(d);
                if (Math.abs(d - d2) == 0.5) {
                    return d + Math.copySign(0.5, d);
                }
                return d2;
            }
            case 8: {
                double d3 = Math.rint(d);
                if (Math.abs(d - d3) == 0.5) {
                    return d;
                }
                return d3;
            }
        }
        throw new AssertionError();
    }

    @GwtIncompatible
    public static int roundToInt(double d, RoundingMode roundingMode) {
        double d2 = DoubleMath.roundIntermediate(d, roundingMode);
        MathPreconditions.checkInRange(d2 > -2.147483649E9 & d2 < 2.147483648E9);
        return (int)d2;
    }

    @GwtIncompatible
    public static long roundToLong(double d, RoundingMode roundingMode) {
        double d2 = DoubleMath.roundIntermediate(d, roundingMode);
        MathPreconditions.checkInRange(-9.223372036854776E18 - d2 < 1.0 & d2 < 9.223372036854776E18);
        return (long)d2;
    }

    @GwtIncompatible
    public static BigInteger roundToBigInteger(double d, RoundingMode roundingMode) {
        if (-9.223372036854776E18 - (d = DoubleMath.roundIntermediate(d, roundingMode)) < 1.0 & d < 9.223372036854776E18) {
            return BigInteger.valueOf((long)d);
        }
        int n = Math.getExponent(d);
        long l = DoubleUtils.getSignificand(d);
        BigInteger bigInteger = BigInteger.valueOf(l).shiftLeft(n - 52);
        return d < 0.0 ? bigInteger.negate() : bigInteger;
    }

    @GwtIncompatible
    public static boolean isPowerOfTwo(double d) {
        return d > 0.0 && DoubleUtils.isFinite(d) && LongMath.isPowerOfTwo(DoubleUtils.getSignificand(d));
    }

    public static double log2(double d) {
        return Math.log(d) / LN_2;
    }

    @GwtIncompatible
    public static int log2(double d, RoundingMode roundingMode) {
        boolean bl;
        Preconditions.checkArgument(d > 0.0 && DoubleUtils.isFinite(d), "x must be positive and finite");
        int n = Math.getExponent(d);
        if (!DoubleUtils.isNormal(d)) {
            return DoubleMath.log2(d * 4.503599627370496E15, roundingMode) - 52;
        }
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(DoubleMath.isPowerOfTwo(d));
            }
            case 2: {
                bl = false;
                break;
            }
            case 3: {
                bl = !DoubleMath.isPowerOfTwo(d);
                break;
            }
            case 4: {
                bl = n < 0 & !DoubleMath.isPowerOfTwo(d);
                break;
            }
            case 5: {
                bl = n >= 0 & !DoubleMath.isPowerOfTwo(d);
                break;
            }
            case 6: 
            case 7: 
            case 8: {
                double d2 = DoubleUtils.scaleNormalize(d);
                bl = d2 * d2 > 2.0;
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return bl ? n + 1 : n;
    }

    @GwtIncompatible
    public static boolean isMathematicalInteger(double d) {
        return DoubleUtils.isFinite(d) && (d == 0.0 || 52 - Long.numberOfTrailingZeros(DoubleUtils.getSignificand(d)) <= Math.getExponent(d));
    }

    public static double factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        if (n > 170) {
            return Double.POSITIVE_INFINITY;
        }
        double d = 1.0;
        for (int i = 1 + (n & 0xFFFFFFF0); i <= n; ++i) {
            d *= (double)i;
        }
        return d * everySixteenthFactorial[n >> 4];
    }

    public static boolean fuzzyEquals(double d, double d2, double d3) {
        MathPreconditions.checkNonNegative("tolerance", d3);
        return Math.copySign(d - d2, 1.0) <= d3 || d == d2 || Double.isNaN(d) && Double.isNaN(d2);
    }

    public static int fuzzyCompare(double d, double d2, double d3) {
        if (DoubleMath.fuzzyEquals(d, d2, d3)) {
            return 1;
        }
        if (d < d2) {
            return 1;
        }
        if (d > d2) {
            return 0;
        }
        return Booleans.compare(Double.isNaN(d), Double.isNaN(d2));
    }

    @Deprecated
    @GwtIncompatible
    public static double mean(double ... dArray) {
        Preconditions.checkArgument(dArray.length > 0, "Cannot take mean of 0 values");
        long l = 1L;
        double d = DoubleMath.checkFinite(dArray[0]);
        for (int i = 1; i < dArray.length; ++i) {
            DoubleMath.checkFinite(dArray[i]);
            d += (dArray[i] - d) / (double)(++l);
        }
        return d;
    }

    @Deprecated
    public static double mean(int ... nArray) {
        Preconditions.checkArgument(nArray.length > 0, "Cannot take mean of 0 values");
        long l = 0L;
        for (int i = 0; i < nArray.length; ++i) {
            l += (long)nArray[i];
        }
        return (double)l / (double)nArray.length;
    }

    @Deprecated
    public static double mean(long ... lArray) {
        Preconditions.checkArgument(lArray.length > 0, "Cannot take mean of 0 values");
        long l = 1L;
        double d = lArray[0];
        for (int i = 1; i < lArray.length; ++i) {
            d += ((double)lArray[i] - d) / (double)(++l);
        }
        return d;
    }

    @Deprecated
    @GwtIncompatible
    public static double mean(Iterable<? extends Number> iterable) {
        return DoubleMath.mean(iterable.iterator());
    }

    @Deprecated
    @GwtIncompatible
    public static double mean(Iterator<? extends Number> iterator2) {
        Preconditions.checkArgument(iterator2.hasNext(), "Cannot take mean of 0 values");
        long l = 1L;
        double d = DoubleMath.checkFinite(iterator2.next().doubleValue());
        while (iterator2.hasNext()) {
            double d2 = DoubleMath.checkFinite(iterator2.next().doubleValue());
            d += (d2 - d) / (double)(++l);
        }
        return d;
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    private static double checkFinite(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        return d;
    }

    private DoubleMath() {
    }
}

