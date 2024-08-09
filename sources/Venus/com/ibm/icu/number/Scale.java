/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.RoundingUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Scale {
    private static final Scale DEFAULT = new Scale(0, null);
    private static final Scale HUNDRED = new Scale(2, null);
    private static final Scale THOUSAND = new Scale(3, null);
    private static final BigDecimal BIG_DECIMAL_100 = BigDecimal.valueOf(100L);
    private static final BigDecimal BIG_DECIMAL_1000 = BigDecimal.valueOf(1000L);
    final int magnitude;
    final BigDecimal arbitrary;
    final BigDecimal reciprocal;
    final MathContext mc;

    private Scale(int n, BigDecimal bigDecimal) {
        this(n, bigDecimal, RoundingUtils.DEFAULT_MATH_CONTEXT_34_DIGITS);
    }

    private Scale(int n, BigDecimal bigDecimal, MathContext mathContext) {
        if (bigDecimal != null) {
            BigDecimal bigDecimal2 = bigDecimal = bigDecimal.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : bigDecimal.stripTrailingZeros();
            if (bigDecimal.precision() == 1 && bigDecimal.unscaledValue().equals(BigInteger.ONE)) {
                n -= bigDecimal.scale();
                bigDecimal = null;
            }
        }
        this.magnitude = n;
        this.arbitrary = bigDecimal;
        this.mc = mathContext;
        this.reciprocal = bigDecimal != null && BigDecimal.ZERO.compareTo(bigDecimal) != 0 ? BigDecimal.ONE.divide(bigDecimal, mathContext) : null;
    }

    public static Scale none() {
        return DEFAULT;
    }

    public static Scale powerOfTen(int n) {
        if (n == 0) {
            return DEFAULT;
        }
        if (n == 2) {
            return HUNDRED;
        }
        if (n == 3) {
            return THOUSAND;
        }
        return new Scale(n, null);
    }

    public static Scale byBigDecimal(BigDecimal bigDecimal) {
        if (bigDecimal.compareTo(BigDecimal.ONE) == 0) {
            return DEFAULT;
        }
        if (bigDecimal.compareTo(BIG_DECIMAL_100) == 0) {
            return HUNDRED;
        }
        if (bigDecimal.compareTo(BIG_DECIMAL_1000) == 0) {
            return THOUSAND;
        }
        return new Scale(0, bigDecimal);
    }

    public static Scale byDouble(double d) {
        if (d == 1.0) {
            return DEFAULT;
        }
        if (d == 100.0) {
            return HUNDRED;
        }
        if (d == 1000.0) {
            return THOUSAND;
        }
        return new Scale(0, BigDecimal.valueOf(d));
    }

    public static Scale byDoubleAndPowerOfTen(double d, int n) {
        return new Scale(n, BigDecimal.valueOf(d));
    }

    boolean isValid() {
        return this.magnitude != 0 || this.arbitrary != null;
    }

    @Deprecated
    public Scale withMathContext(MathContext mathContext) {
        if (this.mc.equals(mathContext)) {
            return this;
        }
        return new Scale(this.magnitude, this.arbitrary, mathContext);
    }

    @Deprecated
    public void applyTo(DecimalQuantity decimalQuantity) {
        decimalQuantity.adjustMagnitude(this.magnitude);
        if (this.arbitrary != null) {
            decimalQuantity.multiplyBy(this.arbitrary);
        }
    }

    @Deprecated
    public void applyReciprocalTo(DecimalQuantity decimalQuantity) {
        decimalQuantity.adjustMagnitude(-this.magnitude);
        if (this.reciprocal != null) {
            decimalQuantity.multiplyBy(this.reciprocal);
            decimalQuantity.roundToMagnitude(decimalQuantity.getMagnitude() - this.mc.getPrecision(), this.mc);
        }
    }
}

