/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.MultiplierProducer;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.number.CurrencyPrecision;
import com.ibm.icu.number.FractionPrecision;
import com.ibm.icu.util.Currency;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public abstract class Precision
implements Cloneable {
    MathContext mathContext = RoundingUtils.DEFAULT_MATH_CONTEXT_UNLIMITED;
    static final InfiniteRounderImpl NONE;
    static final FractionRounderImpl FIXED_FRAC_0;
    static final FractionRounderImpl FIXED_FRAC_2;
    static final FractionRounderImpl DEFAULT_MAX_FRAC_6;
    static final SignificantRounderImpl FIXED_SIG_2;
    static final SignificantRounderImpl FIXED_SIG_3;
    static final SignificantRounderImpl RANGE_SIG_2_3;
    static final FracSigRounderImpl COMPACT_STRATEGY;
    static final IncrementFiveRounderImpl NICKEL;
    static final CurrencyRounderImpl MONETARY_STANDARD;
    static final CurrencyRounderImpl MONETARY_CASH;
    static final PassThroughRounderImpl PASS_THROUGH;
    static final boolean $assertionsDisabled;

    Precision() {
    }

    public static Precision unlimited() {
        return Precision.constructInfinite();
    }

    public static FractionPrecision integer() {
        return Precision.constructFraction(0, 0);
    }

    public static FractionPrecision fixedFraction(int n) {
        if (n >= 0 && n <= 999) {
            return Precision.constructFraction(n, n);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static FractionPrecision minFraction(int n) {
        if (n >= 0 && n <= 999) {
            return Precision.constructFraction(n, -1);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static FractionPrecision maxFraction(int n) {
        if (n >= 0 && n <= 999) {
            return Precision.constructFraction(0, n);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static FractionPrecision minMaxFraction(int n, int n2) {
        if (n >= 0 && n2 <= 999 && n <= n2) {
            return Precision.constructFraction(n, n2);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static Precision fixedSignificantDigits(int n) {
        if (n >= 1 && n <= 999) {
            return Precision.constructSignificant(n, n);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision minSignificantDigits(int n) {
        if (n >= 1 && n <= 999) {
            return Precision.constructSignificant(n, -1);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision maxSignificantDigits(int n) {
        if (n >= 1 && n <= 999) {
            return Precision.constructSignificant(1, n);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision minMaxSignificantDigits(int n, int n2) {
        if (n >= 1 && n2 <= 999 && n <= n2) {
            return Precision.constructSignificant(n, n2);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision increment(BigDecimal bigDecimal) {
        if (bigDecimal != null && bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
            return Precision.constructIncrement(bigDecimal);
        }
        throw new IllegalArgumentException("Rounding increment must be positive and non-null");
    }

    public static CurrencyPrecision currency(Currency.CurrencyUsage currencyUsage) {
        if (currencyUsage != null) {
            return Precision.constructCurrency(currencyUsage);
        }
        throw new IllegalArgumentException("CurrencyUsage must be non-null");
    }

    @Deprecated
    public Precision withMode(MathContext mathContext) {
        if (this.mathContext.equals(mathContext)) {
            return this;
        }
        Precision precision = (Precision)this.clone();
        precision.mathContext = mathContext;
        return precision;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError((Object)cloneNotSupportedException);
        }
    }

    @Deprecated
    public abstract void apply(DecimalQuantity var1);

    static Precision constructInfinite() {
        return NONE;
    }

    static FractionPrecision constructFraction(int n, int n2) {
        if (n == 0 && n2 == 0) {
            return FIXED_FRAC_0;
        }
        if (n == 2 && n2 == 2) {
            return FIXED_FRAC_2;
        }
        if (n == 0 && n2 == 6) {
            return DEFAULT_MAX_FRAC_6;
        }
        return new FractionRounderImpl(n, n2);
    }

    static Precision constructSignificant(int n, int n2) {
        if (n == 2 && n2 == 2) {
            return FIXED_SIG_2;
        }
        if (n == 3 && n2 == 3) {
            return FIXED_SIG_3;
        }
        if (n == 2 && n2 == 3) {
            return RANGE_SIG_2_3;
        }
        return new SignificantRounderImpl(n, n2);
    }

    static Precision constructFractionSignificant(FractionPrecision fractionPrecision, int n, int n2) {
        if (!$assertionsDisabled && !(fractionPrecision instanceof FractionRounderImpl)) {
            throw new AssertionError();
        }
        FractionRounderImpl fractionRounderImpl = (FractionRounderImpl)fractionPrecision;
        FracSigRounderImpl fracSigRounderImpl = fractionRounderImpl.minFrac == 0 && fractionRounderImpl.maxFrac == 0 && n == 2 ? COMPACT_STRATEGY : new FracSigRounderImpl(fractionRounderImpl.minFrac, fractionRounderImpl.maxFrac, n, n2);
        return fracSigRounderImpl.withMode(fractionRounderImpl.mathContext);
    }

    static Precision constructIncrement(BigDecimal bigDecimal) {
        if (bigDecimal.equals(Precision.NICKEL.increment)) {
            return NICKEL;
        }
        BigDecimal bigDecimal2 = bigDecimal.stripTrailingZeros();
        if (bigDecimal2.precision() == 1) {
            int n = bigDecimal.scale();
            int n2 = bigDecimal2.scale();
            BigInteger bigInteger = bigDecimal2.unscaledValue();
            if (bigInteger.intValue() == 1) {
                return new IncrementOneRounderImpl(bigDecimal, n, n2);
            }
            if (bigInteger.intValue() == 5) {
                return new IncrementFiveRounderImpl(bigDecimal, n, n2);
            }
        }
        return new IncrementRounderImpl(bigDecimal);
    }

    static CurrencyPrecision constructCurrency(Currency.CurrencyUsage currencyUsage) {
        if (currencyUsage == Currency.CurrencyUsage.STANDARD) {
            return MONETARY_STANDARD;
        }
        if (currencyUsage == Currency.CurrencyUsage.CASH) {
            return MONETARY_CASH;
        }
        throw new AssertionError();
    }

    static Precision constructFromCurrency(CurrencyPrecision currencyPrecision, Currency currency) {
        Precision precision;
        if (!$assertionsDisabled && !(currencyPrecision instanceof CurrencyRounderImpl)) {
            throw new AssertionError();
        }
        CurrencyRounderImpl currencyRounderImpl = (CurrencyRounderImpl)currencyPrecision;
        double d = currency.getRoundingIncrement(currencyRounderImpl.usage);
        if (d != 0.0) {
            BigDecimal bigDecimal = BigDecimal.valueOf(d);
            precision = Precision.constructIncrement(bigDecimal);
        } else {
            int n = currency.getDefaultFractionDigits(currencyRounderImpl.usage);
            precision = Precision.constructFraction(n, n);
        }
        return precision.withMode(currencyRounderImpl.mathContext);
    }

    static Precision constructPassThrough() {
        return PASS_THROUGH;
    }

    Precision withLocaleData(Currency currency) {
        if (this instanceof CurrencyPrecision) {
            return ((CurrencyPrecision)this).withCurrency(currency);
        }
        return this;
    }

    int chooseMultiplierAndApply(DecimalQuantity decimalQuantity, MultiplierProducer multiplierProducer) {
        if (!$assertionsDisabled && decimalQuantity.isZeroish()) {
            throw new AssertionError();
        }
        int n = decimalQuantity.getMagnitude();
        int n2 = multiplierProducer.getMultiplier(n);
        decimalQuantity.adjustMagnitude(n2);
        this.apply(decimalQuantity);
        if (decimalQuantity.isZeroish()) {
            return n2;
        }
        if (decimalQuantity.getMagnitude() == n + n2) {
            return n2;
        }
        int n3 = multiplierProducer.getMultiplier(n + 1);
        if (n2 == n3) {
            return n2;
        }
        decimalQuantity.adjustMagnitude(n3 - n2);
        this.apply(decimalQuantity);
        return n3;
    }

    private static int getRoundingMagnitudeFraction(int n) {
        if (n == -1) {
            return 1;
        }
        return -n;
    }

    private static int getRoundingMagnitudeSignificant(DecimalQuantity decimalQuantity, int n) {
        if (n == -1) {
            return 1;
        }
        int n2 = decimalQuantity.isZeroish() ? 0 : decimalQuantity.getMagnitude();
        return n2 - n + 1;
    }

    private static int getDisplayMagnitudeFraction(int n) {
        if (n == 0) {
            return 0;
        }
        return -n;
    }

    private static int getDisplayMagnitudeSignificant(DecimalQuantity decimalQuantity, int n) {
        int n2 = decimalQuantity.isZeroish() ? 0 : decimalQuantity.getMagnitude();
        return n2 - n + 1;
    }

    static int access$000(int n) {
        return Precision.getRoundingMagnitudeFraction(n);
    }

    static int access$100(int n) {
        return Precision.getDisplayMagnitudeFraction(n);
    }

    static int access$200(DecimalQuantity decimalQuantity, int n) {
        return Precision.getRoundingMagnitudeSignificant(decimalQuantity, n);
    }

    static int access$300(DecimalQuantity decimalQuantity, int n) {
        return Precision.getDisplayMagnitudeSignificant(decimalQuantity, n);
    }

    static {
        $assertionsDisabled = !Precision.class.desiredAssertionStatus();
        NONE = new InfiniteRounderImpl();
        FIXED_FRAC_0 = new FractionRounderImpl(0, 0);
        FIXED_FRAC_2 = new FractionRounderImpl(2, 2);
        DEFAULT_MAX_FRAC_6 = new FractionRounderImpl(0, 6);
        FIXED_SIG_2 = new SignificantRounderImpl(2, 2);
        FIXED_SIG_3 = new SignificantRounderImpl(3, 3);
        RANGE_SIG_2_3 = new SignificantRounderImpl(2, 3);
        COMPACT_STRATEGY = new FracSigRounderImpl(0, 0, 2, -1);
        NICKEL = new IncrementFiveRounderImpl(new BigDecimal("0.05"), 2, 2);
        MONETARY_STANDARD = new CurrencyRounderImpl(Currency.CurrencyUsage.STANDARD);
        MONETARY_CASH = new CurrencyRounderImpl(Currency.CurrencyUsage.CASH);
        PASS_THROUGH = new PassThroughRounderImpl();
    }

    static class PassThroughRounderImpl
    extends Precision {
        @Override
        public void apply(DecimalQuantity decimalQuantity) {
        }
    }

    static class CurrencyRounderImpl
    extends CurrencyPrecision {
        final Currency.CurrencyUsage usage;

        public CurrencyRounderImpl(Currency.CurrencyUsage currencyUsage) {
            this.usage = currencyUsage;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            throw new AssertionError();
        }
    }

    static class IncrementFiveRounderImpl
    extends IncrementRounderImpl {
        final int minFrac;
        final int maxFrac;

        public IncrementFiveRounderImpl(BigDecimal bigDecimal, int n, int n2) {
            super(bigDecimal);
            this.minFrac = n;
            this.maxFrac = n2;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToNickel(-this.maxFrac, this.mathContext);
            decimalQuantity.setMinFraction(this.minFrac);
        }
    }

    static class IncrementOneRounderImpl
    extends IncrementRounderImpl {
        final int minFrac;
        final int maxFrac;

        public IncrementOneRounderImpl(BigDecimal bigDecimal, int n, int n2) {
            super(bigDecimal);
            this.minFrac = n;
            this.maxFrac = n2;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToMagnitude(-this.maxFrac, this.mathContext);
            decimalQuantity.setMinFraction(this.minFrac);
        }
    }

    static class IncrementRounderImpl
    extends Precision {
        final BigDecimal increment;

        public IncrementRounderImpl(BigDecimal bigDecimal) {
            this.increment = bigDecimal;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToIncrement(this.increment, this.mathContext);
            decimalQuantity.setMinFraction(this.increment.scale());
        }
    }

    static class FracSigRounderImpl
    extends Precision {
        final int minFrac;
        final int maxFrac;
        final int minSig;
        final int maxSig;

        public FracSigRounderImpl(int n, int n2, int n3, int n4) {
            this.minFrac = n;
            this.maxFrac = n2;
            this.minSig = n3;
            this.maxSig = n4;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            int n = Precision.access$100(this.minFrac);
            int n2 = Precision.access$000(this.maxFrac);
            if (this.minSig == -1) {
                int n3 = Precision.access$200(decimalQuantity, this.maxSig);
                n2 = Math.max(n2, n3);
            } else {
                int n4 = Precision.access$300(decimalQuantity, this.minSig);
                n2 = Math.min(n2, n4);
            }
            decimalQuantity.roundToMagnitude(n2, this.mathContext);
            decimalQuantity.setMinFraction(Math.max(0, -n));
        }
    }

    static class SignificantRounderImpl
    extends Precision {
        final int minSig;
        final int maxSig;
        static final boolean $assertionsDisabled = !Precision.class.desiredAssertionStatus();

        public SignificantRounderImpl(int n, int n2) {
            this.minSig = n;
            this.maxSig = n2;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToMagnitude(Precision.access$200(decimalQuantity, this.maxSig), this.mathContext);
            decimalQuantity.setMinFraction(Math.max(0, -Precision.access$300(decimalQuantity, this.minSig)));
            if (decimalQuantity.isZeroish() && this.minSig > 0) {
                decimalQuantity.setMinInteger(1);
            }
        }

        public void apply(DecimalQuantity decimalQuantity, int n) {
            if (!$assertionsDisabled && !decimalQuantity.isZeroish()) {
                throw new AssertionError();
            }
            decimalQuantity.setMinFraction(this.minSig - n);
        }
    }

    static class FractionRounderImpl
    extends FractionPrecision {
        final int minFrac;
        final int maxFrac;

        public FractionRounderImpl(int n, int n2) {
            this.minFrac = n;
            this.maxFrac = n2;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToMagnitude(Precision.access$000(this.maxFrac), this.mathContext);
            decimalQuantity.setMinFraction(Math.max(0, -Precision.access$100(this.minFrac)));
        }
    }

    static class InfiniteRounderImpl
    extends Precision {
        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToInfinity();
            decimalQuantity.setMinFraction(0);
        }
    }
}

