/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.math.DoubleMath;
import com.google.common.math.DoubleUtils;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import com.google.common.math.MathPreconditions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@GwtCompatible(emulated=true)
public final class BigIntegerMath {
    @VisibleForTesting
    static final int SQRT2_PRECOMPUTE_THRESHOLD = 256;
    @VisibleForTesting
    static final BigInteger SQRT2_PRECOMPUTED_BITS = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
    private static final double LN_10 = Math.log(10.0);
    private static final double LN_2 = Math.log(2.0);

    @Beta
    public static BigInteger ceilingPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(BigIntegerMath.log2(bigInteger, RoundingMode.CEILING));
    }

    @Beta
    public static BigInteger floorPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(BigIntegerMath.log2(bigInteger, RoundingMode.FLOOR));
    }

    public static boolean isPowerOfTwo(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        return bigInteger.signum() > 0 && bigInteger.getLowestSetBit() == bigInteger.bitLength() - 1;
    }

    public static int log2(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", Preconditions.checkNotNull(bigInteger));
        int n = bigInteger.bitLength() - 1;
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(BigIntegerMath.isPowerOfTwo(bigInteger));
            }
            case 2: 
            case 3: {
                return n;
            }
            case 4: 
            case 5: {
                return BigIntegerMath.isPowerOfTwo(bigInteger) ? n : n + 1;
            }
            case 6: 
            case 7: 
            case 8: {
                if (n < 256) {
                    BigInteger bigInteger2 = SQRT2_PRECOMPUTED_BITS.shiftRight(256 - n);
                    if (bigInteger.compareTo(bigInteger2) <= 0) {
                        return n;
                    }
                    return n + 1;
                }
                BigInteger bigInteger3 = bigInteger.pow(2);
                int n2 = bigInteger3.bitLength() - 1;
                return n2 < 2 * n + 1 ? n : n + 1;
            }
        }
        throw new AssertionError();
    }

    @GwtIncompatible
    public static int log10(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", bigInteger);
        if (BigIntegerMath.fitsInLong(bigInteger)) {
            return LongMath.log10(bigInteger.longValue(), roundingMode);
        }
        int n = (int)((double)BigIntegerMath.log2(bigInteger, RoundingMode.FLOOR) * LN_2 / LN_10);
        BigInteger bigInteger2 = BigInteger.TEN.pow(n);
        int n2 = bigInteger2.compareTo(bigInteger);
        if (n2 > 0) {
            do {
                --n;
            } while ((n2 = (bigInteger2 = bigInteger2.divide(BigInteger.TEN)).compareTo(bigInteger)) > 0);
        } else {
            BigInteger bigInteger3 = BigInteger.TEN.multiply(bigInteger2);
            int n3 = bigInteger3.compareTo(bigInteger);
            while (n3 <= 0) {
                ++n;
                bigInteger2 = bigInteger3;
                n2 = n3;
                bigInteger3 = BigInteger.TEN.multiply(bigInteger2);
                n3 = bigInteger3.compareTo(bigInteger);
            }
        }
        int n4 = n;
        BigInteger bigInteger4 = bigInteger2;
        int n5 = n2;
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(n5 == 0);
            }
            case 2: 
            case 3: {
                return n4;
            }
            case 4: 
            case 5: {
                return bigInteger4.equals(bigInteger) ? n4 : n4 + 1;
            }
            case 6: 
            case 7: 
            case 8: {
                BigInteger bigInteger5 = bigInteger.pow(2);
                BigInteger bigInteger6 = bigInteger4.pow(2).multiply(BigInteger.TEN);
                return bigInteger5.compareTo(bigInteger6) <= 0 ? n4 : n4 + 1;
            }
        }
        throw new AssertionError();
    }

    @GwtIncompatible
    public static BigInteger sqrt(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", bigInteger);
        if (BigIntegerMath.fitsInLong(bigInteger)) {
            return BigInteger.valueOf(LongMath.sqrt(bigInteger.longValue(), roundingMode));
        }
        BigInteger bigInteger2 = BigIntegerMath.sqrtFloor(bigInteger);
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(bigInteger2.pow(2).equals(bigInteger));
            }
            case 2: 
            case 3: {
                return bigInteger2;
            }
            case 4: 
            case 5: {
                int n = bigInteger2.intValue();
                boolean bl = n * n == bigInteger.intValue() && bigInteger2.pow(2).equals(bigInteger);
                return bl ? bigInteger2 : bigInteger2.add(BigInteger.ONE);
            }
            case 6: 
            case 7: 
            case 8: {
                BigInteger bigInteger3 = bigInteger2.pow(2).add(bigInteger2);
                return bigInteger3.compareTo(bigInteger) >= 0 ? bigInteger2 : bigInteger2.add(BigInteger.ONE);
            }
        }
        throw new AssertionError();
    }

    @GwtIncompatible
    private static BigInteger sqrtFloor(BigInteger bigInteger) {
        BigInteger bigInteger2;
        int n = BigIntegerMath.log2(bigInteger, RoundingMode.FLOOR);
        if (n < 1023) {
            bigInteger2 = BigIntegerMath.sqrtApproxWithDoubles(bigInteger);
        } else {
            int n2 = n - 52 & 0xFFFFFFFE;
            bigInteger2 = BigIntegerMath.sqrtApproxWithDoubles(bigInteger.shiftRight(n2)).shiftLeft(n2 >> 1);
        }
        BigInteger bigInteger3 = bigInteger2.add(bigInteger.divide(bigInteger2)).shiftRight(1);
        if (bigInteger2.equals(bigInteger3)) {
            return bigInteger2;
        }
        while ((bigInteger3 = (bigInteger2 = bigInteger3).add(bigInteger.divide(bigInteger2)).shiftRight(1)).compareTo(bigInteger2) < 0) {
        }
        return bigInteger2;
    }

    @GwtIncompatible
    private static BigInteger sqrtApproxWithDoubles(BigInteger bigInteger) {
        return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.bigToDouble(bigInteger)), RoundingMode.HALF_EVEN);
    }

    @GwtIncompatible
    public static BigInteger divide(BigInteger bigInteger, BigInteger bigInteger2, RoundingMode roundingMode) {
        BigDecimal bigDecimal = new BigDecimal(bigInteger);
        BigDecimal bigDecimal2 = new BigDecimal(bigInteger2);
        return bigDecimal.divide(bigDecimal2, 0, roundingMode).toBigIntegerExact();
    }

    public static BigInteger factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        if (n < LongMath.factorials.length) {
            return BigInteger.valueOf(LongMath.factorials[n]);
        }
        int n2 = IntMath.divide(n * IntMath.log2(n, RoundingMode.CEILING), 64, RoundingMode.CEILING);
        ArrayList<BigInteger> arrayList = new ArrayList<BigInteger>(n2);
        int n3 = LongMath.factorials.length;
        long l = LongMath.factorials[n3 - 1];
        int n4 = Long.numberOfTrailingZeros(l);
        int n5 = LongMath.log2(l >>= n4, RoundingMode.FLOOR) + 1;
        int n6 = LongMath.log2(n3, RoundingMode.FLOOR) + 1;
        int n7 = 1 << n6 - 1;
        for (long i = (long)n3; i <= (long)n; ++i) {
            if ((i & (long)n7) != 0L) {
                n7 <<= 1;
                ++n6;
            }
            int n8 = Long.numberOfTrailingZeros(i);
            long l2 = i >> n8;
            n4 += n8;
            int n9 = n6 - n8;
            if (n9 + n5 >= 64) {
                arrayList.add(BigInteger.valueOf(l));
                l = 1L;
                n5 = 0;
            }
            n5 = LongMath.log2(l *= l2, RoundingMode.FLOOR) + 1;
        }
        if (l > 1L) {
            arrayList.add(BigInteger.valueOf(l));
        }
        return BigIntegerMath.listProduct(arrayList).shiftLeft(n4);
    }

    static BigInteger listProduct(List<BigInteger> list) {
        return BigIntegerMath.listProduct(list, 0, list.size());
    }

    static BigInteger listProduct(List<BigInteger> list, int n, int n2) {
        switch (n2 - n) {
            case 0: {
                return BigInteger.ONE;
            }
            case 1: {
                return list.get(n);
            }
            case 2: {
                return list.get(n).multiply(list.get(n + 1));
            }
            case 3: {
                return list.get(n).multiply(list.get(n + 1)).multiply(list.get(n + 2));
            }
        }
        int n3 = n2 + n >>> 1;
        return BigIntegerMath.listProduct(list, n, n3).multiply(BigIntegerMath.listProduct(list, n3, n2));
    }

    public static BigInteger binomial(int n, int n2) {
        int n3;
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", n2);
        Preconditions.checkArgument(n2 <= n, "k (%s) > n (%s)", n2, n);
        if (n2 > n >> 1) {
            n2 = n - n2;
        }
        if (n2 < LongMath.biggestBinomials.length && n <= LongMath.biggestBinomials[n2]) {
            return BigInteger.valueOf(LongMath.binomial(n, n2));
        }
        BigInteger bigInteger = BigInteger.ONE;
        long l = n;
        long l2 = 1L;
        int n4 = n3 = LongMath.log2(n, RoundingMode.CEILING);
        for (int i = 1; i < n2; ++i) {
            int n5 = n - i;
            int n6 = i + 1;
            if (n4 + n3 >= 63) {
                bigInteger = bigInteger.multiply(BigInteger.valueOf(l)).divide(BigInteger.valueOf(l2));
                l = n5;
                l2 = n6;
                n4 = n3;
                continue;
            }
            l *= (long)n5;
            l2 *= (long)n6;
            n4 += n3;
        }
        return bigInteger.multiply(BigInteger.valueOf(l)).divide(BigInteger.valueOf(l2));
    }

    @GwtIncompatible
    static boolean fitsInLong(BigInteger bigInteger) {
        return bigInteger.bitLength() <= 63;
    }

    private BigIntegerMath() {
    }
}

