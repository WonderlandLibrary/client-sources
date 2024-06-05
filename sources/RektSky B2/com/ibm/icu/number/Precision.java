package com.ibm.icu.number;

import com.ibm.icu.util.*;
import java.math.*;
import com.ibm.icu.impl.number.*;

public abstract class Precision implements Cloneable
{
    MathContext mathContext;
    static final InfiniteRounderImpl NONE;
    static final FractionRounderImpl FIXED_FRAC_0;
    static final FractionRounderImpl FIXED_FRAC_2;
    static final FractionRounderImpl DEFAULT_MAX_FRAC_6;
    static final SignificantRounderImpl FIXED_SIG_2;
    static final SignificantRounderImpl FIXED_SIG_3;
    static final SignificantRounderImpl RANGE_SIG_2_3;
    static final FracSigRounderImpl COMPACT_STRATEGY;
    static final IncrementRounderImpl NICKEL;
    static final CurrencyRounderImpl MONETARY_STANDARD;
    static final CurrencyRounderImpl MONETARY_CASH;
    static final PassThroughRounderImpl PASS_THROUGH;
    
    Precision() {
        this.mathContext = RoundingUtils.DEFAULT_MATH_CONTEXT_UNLIMITED;
    }
    
    public static Precision unlimited() {
        return constructInfinite();
    }
    
    public static FractionPrecision integer() {
        return constructFraction(0, 0);
    }
    
    public static FractionPrecision fixedFraction(final int minMaxFractionPlaces) {
        if (minMaxFractionPlaces >= 0 && minMaxFractionPlaces <= 999) {
            return constructFraction(minMaxFractionPlaces, minMaxFractionPlaces);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }
    
    public static FractionPrecision minFraction(final int minFractionPlaces) {
        if (minFractionPlaces >= 0 && minFractionPlaces <= 999) {
            return constructFraction(minFractionPlaces, -1);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }
    
    public static FractionPrecision maxFraction(final int maxFractionPlaces) {
        if (maxFractionPlaces >= 0 && maxFractionPlaces <= 999) {
            return constructFraction(0, maxFractionPlaces);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }
    
    public static FractionPrecision minMaxFraction(final int minFractionPlaces, final int maxFractionPlaces) {
        if (minFractionPlaces >= 0 && maxFractionPlaces <= 999 && minFractionPlaces <= maxFractionPlaces) {
            return constructFraction(minFractionPlaces, maxFractionPlaces);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }
    
    public static Precision fixedSignificantDigits(final int minMaxSignificantDigits) {
        if (minMaxSignificantDigits >= 1 && minMaxSignificantDigits <= 999) {
            return constructSignificant(minMaxSignificantDigits, minMaxSignificantDigits);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }
    
    public static Precision minSignificantDigits(final int minSignificantDigits) {
        if (minSignificantDigits >= 1 && minSignificantDigits <= 999) {
            return constructSignificant(minSignificantDigits, -1);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }
    
    public static Precision maxSignificantDigits(final int maxSignificantDigits) {
        if (maxSignificantDigits >= 1 && maxSignificantDigits <= 999) {
            return constructSignificant(1, maxSignificantDigits);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }
    
    public static Precision minMaxSignificantDigits(final int minSignificantDigits, final int maxSignificantDigits) {
        if (minSignificantDigits >= 1 && maxSignificantDigits <= 999 && minSignificantDigits <= maxSignificantDigits) {
            return constructSignificant(minSignificantDigits, maxSignificantDigits);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }
    
    @Deprecated
    public static Precision fixedDigits(final int a) {
        return fixedSignificantDigits(a);
    }
    
    @Deprecated
    public static Precision minDigits(final int a) {
        return minSignificantDigits(a);
    }
    
    @Deprecated
    public static Precision maxDigits(final int a) {
        return maxSignificantDigits(a);
    }
    
    @Deprecated
    public static Precision minMaxDigits(final int a, final int b) {
        return minMaxSignificantDigits(a, b);
    }
    
    public static Precision increment(final BigDecimal roundingIncrement) {
        if (roundingIncrement != null && roundingIncrement.compareTo(BigDecimal.ZERO) > 0) {
            return constructIncrement(roundingIncrement);
        }
        throw new IllegalArgumentException("Rounding increment must be positive and non-null");
    }
    
    public static CurrencyPrecision currency(final Currency.CurrencyUsage currencyUsage) {
        if (currencyUsage != null) {
            return constructCurrency(currencyUsage);
        }
        throw new IllegalArgumentException("CurrencyUsage must be non-null");
    }
    
    @Deprecated
    public Precision withMode(final RoundingMode roundingMode) {
        return this.withMode(RoundingUtils.mathContextUnlimited(roundingMode));
    }
    
    @Deprecated
    public Precision withMode(final MathContext mathContext) {
        if (this.mathContext.equals(mathContext)) {
            return this;
        }
        final Precision other = (Precision)this.clone();
        other.mathContext = mathContext;
        return other;
    }
    
    @Deprecated
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError((Object)e);
        }
    }
    
    @Deprecated
    public abstract void apply(final DecimalQuantity p0);
    
    static Precision constructInfinite() {
        return Precision.NONE;
    }
    
    static FractionPrecision constructFraction(final int minFrac, final int maxFrac) {
        if (minFrac == 0 && maxFrac == 0) {
            return Precision.FIXED_FRAC_0;
        }
        if (minFrac == 2 && maxFrac == 2) {
            return Precision.FIXED_FRAC_2;
        }
        if (minFrac == 0 && maxFrac == 6) {
            return Precision.DEFAULT_MAX_FRAC_6;
        }
        return new FractionRounderImpl(minFrac, maxFrac);
    }
    
    static Precision constructSignificant(final int minSig, final int maxSig) {
        if (minSig == 2 && maxSig == 2) {
            return Precision.FIXED_SIG_2;
        }
        if (minSig == 3 && maxSig == 3) {
            return Precision.FIXED_SIG_3;
        }
        if (minSig == 2 && maxSig == 3) {
            return Precision.RANGE_SIG_2_3;
        }
        return new SignificantRounderImpl(minSig, maxSig);
    }
    
    static Precision constructFractionSignificant(final FractionPrecision base_, final int minSig, final int maxSig) {
        assert base_ instanceof FractionRounderImpl;
        final FractionRounderImpl base = (FractionRounderImpl)base_;
        if (base.minFrac == 0 && base.maxFrac == 0 && minSig == 2) {
            return Precision.COMPACT_STRATEGY;
        }
        return new FracSigRounderImpl(base.minFrac, base.maxFrac, minSig, maxSig);
    }
    
    static Precision constructIncrement(final BigDecimal increment) {
        if (increment.equals(Precision.NICKEL.increment)) {
            return Precision.NICKEL;
        }
        return new IncrementRounderImpl(increment);
    }
    
    static CurrencyPrecision constructCurrency(final Currency.CurrencyUsage usage) {
        if (usage == Currency.CurrencyUsage.STANDARD) {
            return Precision.MONETARY_STANDARD;
        }
        if (usage == Currency.CurrencyUsage.CASH) {
            return Precision.MONETARY_CASH;
        }
        throw new AssertionError();
    }
    
    static Precision constructFromCurrency(final CurrencyPrecision base_, final Currency currency) {
        assert base_ instanceof CurrencyRounderImpl;
        final CurrencyRounderImpl base = (CurrencyRounderImpl)base_;
        final double incrementDouble = currency.getRoundingIncrement(base.usage);
        if (incrementDouble != 0.0) {
            final BigDecimal increment = BigDecimal.valueOf(incrementDouble);
            return constructIncrement(increment);
        }
        final int minMaxFrac = currency.getDefaultFractionDigits(base.usage);
        return constructFraction(minMaxFrac, minMaxFrac);
    }
    
    static Precision constructPassThrough() {
        return Precision.PASS_THROUGH;
    }
    
    Precision withLocaleData(final Currency currency) {
        if (this instanceof CurrencyPrecision) {
            return ((CurrencyPrecision)this).withCurrency(currency);
        }
        return this;
    }
    
    int chooseMultiplierAndApply(final DecimalQuantity input, final MultiplierProducer producer) {
        assert !input.isZero();
        final int magnitude = input.getMagnitude();
        final int multiplier = producer.getMultiplier(magnitude);
        input.adjustMagnitude(multiplier);
        this.apply(input);
        if (input.isZero()) {
            return multiplier;
        }
        if (input.getMagnitude() == magnitude + multiplier) {
            return multiplier;
        }
        final int _multiplier = producer.getMultiplier(magnitude + 1);
        if (multiplier == _multiplier) {
            return multiplier;
        }
        input.adjustMagnitude(_multiplier - multiplier);
        this.apply(input);
        return _multiplier;
    }
    
    private static int getRoundingMagnitudeFraction(final int maxFrac) {
        if (maxFrac == -1) {
            return Integer.MIN_VALUE;
        }
        return -maxFrac;
    }
    
    private static int getRoundingMagnitudeSignificant(final DecimalQuantity value, final int maxSig) {
        if (maxSig == -1) {
            return Integer.MIN_VALUE;
        }
        final int magnitude = value.isZero() ? 0 : value.getMagnitude();
        return magnitude - maxSig + 1;
    }
    
    private static int getDisplayMagnitudeFraction(final int minFrac) {
        if (minFrac == 0) {
            return Integer.MAX_VALUE;
        }
        return -minFrac;
    }
    
    private static int getDisplayMagnitudeSignificant(final DecimalQuantity value, final int minSig) {
        final int magnitude = value.isZero() ? 0 : value.getMagnitude();
        return magnitude - minSig + 1;
    }
    
    static {
        NONE = new InfiniteRounderImpl();
        FIXED_FRAC_0 = new FractionRounderImpl(0, 0);
        FIXED_FRAC_2 = new FractionRounderImpl(2, 2);
        DEFAULT_MAX_FRAC_6 = new FractionRounderImpl(0, 6);
        FIXED_SIG_2 = new SignificantRounderImpl(2, 2);
        FIXED_SIG_3 = new SignificantRounderImpl(3, 3);
        RANGE_SIG_2_3 = new SignificantRounderImpl(2, 3);
        COMPACT_STRATEGY = new FracSigRounderImpl(0, 0, 2, -1);
        NICKEL = new IncrementRounderImpl(BigDecimal.valueOf(0.05));
        MONETARY_STANDARD = new CurrencyRounderImpl(Currency.CurrencyUsage.STANDARD);
        MONETARY_CASH = new CurrencyRounderImpl(Currency.CurrencyUsage.CASH);
        PASS_THROUGH = new PassThroughRounderImpl();
    }
    
    static class InfiniteRounderImpl extends Precision
    {
        public InfiniteRounderImpl() {
        }
        
        @Override
        public void apply(final DecimalQuantity value) {
            value.roundToInfinity();
            value.setFractionLength(0, Integer.MAX_VALUE);
        }
    }
    
    static class FractionRounderImpl extends FractionPrecision
    {
        final int minFrac;
        final int maxFrac;
        
        public FractionRounderImpl(final int minFrac, final int maxFrac) {
            this.minFrac = minFrac;
            this.maxFrac = maxFrac;
        }
        
        @Override
        public void apply(final DecimalQuantity value) {
            value.roundToMagnitude(getRoundingMagnitudeFraction(this.maxFrac), this.mathContext);
            value.setFractionLength(Math.max(0, -getDisplayMagnitudeFraction(this.minFrac)), Integer.MAX_VALUE);
        }
    }
    
    static class SignificantRounderImpl extends Precision
    {
        final int minSig;
        final int maxSig;
        
        public SignificantRounderImpl(final int minSig, final int maxSig) {
            this.minSig = minSig;
            this.maxSig = maxSig;
        }
        
        @Override
        public void apply(final DecimalQuantity value) {
            value.roundToMagnitude(getRoundingMagnitudeSignificant(value, this.maxSig), this.mathContext);
            value.setFractionLength(Math.max(0, -getDisplayMagnitudeSignificant(value, this.minSig)), Integer.MAX_VALUE);
            if (value.isZero() && this.minSig > 0) {
                value.setIntegerLength(1, Integer.MAX_VALUE);
            }
        }
        
        public void apply(final DecimalQuantity quantity, final int minInt) {
            assert quantity.isZero();
            quantity.setFractionLength(this.minSig - minInt, Integer.MAX_VALUE);
        }
    }
    
    static class FracSigRounderImpl extends Precision
    {
        final int minFrac;
        final int maxFrac;
        final int minSig;
        final int maxSig;
        
        public FracSigRounderImpl(final int minFrac, final int maxFrac, final int minSig, final int maxSig) {
            this.minFrac = minFrac;
            this.maxFrac = maxFrac;
            this.minSig = minSig;
            this.maxSig = maxSig;
        }
        
        @Override
        public void apply(final DecimalQuantity value) {
            final int displayMag = getDisplayMagnitudeFraction(this.minFrac);
            int roundingMag = getRoundingMagnitudeFraction(this.maxFrac);
            if (this.minSig == -1) {
                final int candidate = getRoundingMagnitudeSignificant(value, this.maxSig);
                roundingMag = Math.max(roundingMag, candidate);
            }
            else {
                final int candidate = getDisplayMagnitudeSignificant(value, this.minSig);
                roundingMag = Math.min(roundingMag, candidate);
            }
            value.roundToMagnitude(roundingMag, this.mathContext);
            value.setFractionLength(Math.max(0, -displayMag), Integer.MAX_VALUE);
        }
    }
    
    static class IncrementRounderImpl extends Precision
    {
        final BigDecimal increment;
        
        public IncrementRounderImpl(final BigDecimal increment) {
            this.increment = increment;
        }
        
        @Override
        public void apply(final DecimalQuantity value) {
            value.roundToIncrement(this.increment, this.mathContext);
            value.setFractionLength(this.increment.scale(), this.increment.scale());
        }
    }
    
    static class CurrencyRounderImpl extends CurrencyPrecision
    {
        final Currency.CurrencyUsage usage;
        
        public CurrencyRounderImpl(final Currency.CurrencyUsage usage) {
            this.usage = usage;
        }
        
        @Override
        public void apply(final DecimalQuantity value) {
            throw new AssertionError();
        }
    }
    
    static class PassThroughRounderImpl extends Precision
    {
        public PassThroughRounderImpl() {
        }
        
        @Override
        public void apply(final DecimalQuantity value) {
        }
    }
}
