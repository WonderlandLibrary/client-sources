package com.ibm.icu.number;

import java.math.*;
import com.ibm.icu.impl.number.*;

public class Scale
{
    private static final Scale DEFAULT;
    private static final Scale HUNDRED;
    private static final Scale THOUSAND;
    private static final BigDecimal BIG_DECIMAL_100;
    private static final BigDecimal BIG_DECIMAL_1000;
    final int magnitude;
    final BigDecimal arbitrary;
    final BigDecimal reciprocal;
    final MathContext mc;
    
    private Scale(final int magnitude, final BigDecimal arbitrary) {
        this(magnitude, arbitrary, RoundingUtils.DEFAULT_MATH_CONTEXT_34_DIGITS);
    }
    
    private Scale(int magnitude, BigDecimal arbitrary, final MathContext mc) {
        if (arbitrary != null) {
            arbitrary = arbitrary.stripTrailingZeros();
            if (arbitrary.precision() == 1 && arbitrary.unscaledValue().equals(BigInteger.ONE)) {
                magnitude -= arbitrary.scale();
                arbitrary = null;
            }
        }
        this.magnitude = magnitude;
        this.arbitrary = arbitrary;
        this.mc = mc;
        if (arbitrary != null && BigDecimal.ZERO.compareTo(arbitrary) != 0) {
            this.reciprocal = BigDecimal.ONE.divide(arbitrary, mc);
        }
        else {
            this.reciprocal = null;
        }
    }
    
    public static Scale none() {
        return Scale.DEFAULT;
    }
    
    public static Scale powerOfTen(final int power) {
        if (power == 0) {
            return Scale.DEFAULT;
        }
        if (power == 2) {
            return Scale.HUNDRED;
        }
        if (power == 3) {
            return Scale.THOUSAND;
        }
        return new Scale(power, null);
    }
    
    public static Scale byBigDecimal(final BigDecimal multiplicand) {
        if (multiplicand.compareTo(BigDecimal.ONE) == 0) {
            return Scale.DEFAULT;
        }
        if (multiplicand.compareTo(Scale.BIG_DECIMAL_100) == 0) {
            return Scale.HUNDRED;
        }
        if (multiplicand.compareTo(Scale.BIG_DECIMAL_1000) == 0) {
            return Scale.THOUSAND;
        }
        return new Scale(0, multiplicand);
    }
    
    public static Scale byDouble(final double multiplicand) {
        if (multiplicand == 1.0) {
            return Scale.DEFAULT;
        }
        if (multiplicand == 100.0) {
            return Scale.HUNDRED;
        }
        if (multiplicand == 1000.0) {
            return Scale.THOUSAND;
        }
        return new Scale(0, BigDecimal.valueOf(multiplicand));
    }
    
    public static Scale byDoubleAndPowerOfTen(final double multiplicand, final int power) {
        return new Scale(power, BigDecimal.valueOf(multiplicand));
    }
    
    boolean isValid() {
        return this.magnitude != 0 || this.arbitrary != null;
    }
    
    @Deprecated
    public Scale withMathContext(final MathContext mc) {
        if (this.mc.equals(mc)) {
            return this;
        }
        return new Scale(this.magnitude, this.arbitrary, mc);
    }
    
    @Deprecated
    public void applyTo(final DecimalQuantity quantity) {
        quantity.adjustMagnitude(this.magnitude);
        if (this.arbitrary != null) {
            quantity.multiplyBy(this.arbitrary);
        }
    }
    
    @Deprecated
    public void applyReciprocalTo(final DecimalQuantity quantity) {
        quantity.adjustMagnitude(-this.magnitude);
        if (this.reciprocal != null) {
            quantity.multiplyBy(this.reciprocal);
            quantity.roundToMagnitude(quantity.getMagnitude() - this.mc.getPrecision(), this.mc);
        }
    }
    
    static {
        DEFAULT = new Scale(0, null);
        HUNDRED = new Scale(2, null);
        THOUSAND = new Scale(3, null);
        BIG_DECIMAL_100 = BigDecimal.valueOf(100L);
        BIG_DECIMAL_1000 = BigDecimal.valueOf(1000L);
    }
}
