/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.number.Precision;
import com.ibm.icu.number.Scale;
import com.ibm.icu.text.PluralRules;
import java.math.MathContext;
import java.math.RoundingMode;

public class RoundingUtils {
    public static final int SECTION_LOWER = 1;
    public static final int SECTION_MIDPOINT = 2;
    public static final int SECTION_UPPER = 3;
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    public static final int MAX_INT_FRAC_SIG = 999;
    private static final MathContext[] MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED = new MathContext[RoundingMode.values().length];
    private static final MathContext[] MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS = new MathContext[RoundingMode.values().length];
    public static final MathContext DEFAULT_MATH_CONTEXT_UNLIMITED;
    public static final MathContext DEFAULT_MATH_CONTEXT_34_DIGITS;

    public static boolean getRoundingDirection(boolean bl, boolean bl2, int n, int n2, Object object) {
        switch (n2) {
            case 0: {
                return true;
            }
            case 1: {
                return false;
            }
            case 2: {
                return bl2;
            }
            case 3: {
                return !bl2;
            }
            case 4: {
                switch (n) {
                    case 2: {
                        return true;
                    }
                    case 1: {
                        return false;
                    }
                    case 3: {
                        return true;
                    }
                }
                break;
            }
            case 5: {
                switch (n) {
                    case 2: {
                        return false;
                    }
                    case 1: {
                        return false;
                    }
                    case 3: {
                        return true;
                    }
                }
                break;
            }
            case 6: {
                switch (n) {
                    case 2: {
                        return bl;
                    }
                    case 1: {
                        return false;
                    }
                    case 3: {
                        return true;
                    }
                }
            }
        }
        throw new ArithmeticException("Rounding is required on " + object.toString());
    }

    public static boolean roundsAtMidpoint(int n) {
        switch (n) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                return true;
            }
        }
        return false;
    }

    public static MathContext getMathContextOrUnlimited(DecimalFormatProperties decimalFormatProperties) {
        MathContext mathContext = decimalFormatProperties.getMathContext();
        if (mathContext == null) {
            RoundingMode roundingMode = decimalFormatProperties.getRoundingMode();
            if (roundingMode == null) {
                roundingMode = RoundingMode.HALF_EVEN;
            }
            mathContext = MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[roundingMode.ordinal()];
        }
        return mathContext;
    }

    public static MathContext getMathContextOr34Digits(DecimalFormatProperties decimalFormatProperties) {
        MathContext mathContext = decimalFormatProperties.getMathContext();
        if (mathContext == null) {
            RoundingMode roundingMode = decimalFormatProperties.getRoundingMode();
            if (roundingMode == null) {
                roundingMode = RoundingMode.HALF_EVEN;
            }
            mathContext = MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS[roundingMode.ordinal()];
        }
        return mathContext;
    }

    public static MathContext mathContextUnlimited(RoundingMode roundingMode) {
        return MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[roundingMode.ordinal()];
    }

    public static Scale scaleFromProperties(DecimalFormatProperties decimalFormatProperties) {
        MathContext mathContext = RoundingUtils.getMathContextOr34Digits(decimalFormatProperties);
        if (decimalFormatProperties.getMagnitudeMultiplier() != 0) {
            return Scale.powerOfTen(decimalFormatProperties.getMagnitudeMultiplier()).withMathContext(mathContext);
        }
        if (decimalFormatProperties.getMultiplier() != null) {
            return Scale.byBigDecimal(decimalFormatProperties.getMultiplier()).withMathContext(mathContext);
        }
        return null;
    }

    public static StandardPlural getPluralSafe(Precision precision, PluralRules pluralRules, DecimalQuantity decimalQuantity) {
        DecimalQuantity decimalQuantity2 = decimalQuantity.createCopy();
        precision.apply(decimalQuantity2);
        return decimalQuantity2.getStandardPlural(pluralRules);
    }

    static {
        for (int i = 0; i < MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS.length; ++i) {
            RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[i] = new MathContext(0, RoundingMode.valueOf(i));
            RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS[i] = new MathContext(34);
        }
        DEFAULT_MATH_CONTEXT_UNLIMITED = MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[DEFAULT_ROUNDING_MODE.ordinal()];
        DEFAULT_MATH_CONTEXT_34_DIGITS = MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS[DEFAULT_ROUNDING_MODE.ordinal()];
    }
}

