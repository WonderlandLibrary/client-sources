package com.ibm.icu.impl.number;

import java.math.*;
import com.ibm.icu.number.*;

public class RoundingUtils
{
    public static final int SECTION_LOWER = 1;
    public static final int SECTION_MIDPOINT = 2;
    public static final int SECTION_UPPER = 3;
    public static final RoundingMode DEFAULT_ROUNDING_MODE;
    public static final int MAX_INT_FRAC_SIG = 999;
    private static final MathContext[] MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED;
    private static final MathContext[] MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS;
    public static final MathContext DEFAULT_MATH_CONTEXT_UNLIMITED;
    public static final MathContext DEFAULT_MATH_CONTEXT_34_DIGITS;
    
    public static boolean getRoundingDirection(final boolean isEven, final boolean isNegative, final int section, final int roundingMode, final Object reference) {
        Label_0166: {
            switch (roundingMode) {
                case 0: {
                    return false;
                }
                case 1: {
                    return true;
                }
                case 2: {
                    return isNegative;
                }
                case 3: {
                    return !isNegative;
                }
                case 4: {
                    switch (section) {
                        case 2: {
                            return false;
                        }
                        case 1: {
                            return true;
                        }
                        case 3: {
                            return false;
                        }
                        default: {
                            break Label_0166;
                        }
                    }
                    break;
                }
                case 5: {
                    switch (section) {
                        case 2: {
                            return true;
                        }
                        case 1: {
                            return true;
                        }
                        case 3: {
                            return false;
                        }
                        default: {
                            break Label_0166;
                        }
                    }
                    break;
                }
                case 6: {
                    switch (section) {
                        case 2: {
                            return isEven;
                        }
                        case 1: {
                            return true;
                        }
                        case 3: {
                            return false;
                        }
                        default: {
                            break Label_0166;
                        }
                    }
                    break;
                }
            }
        }
        throw new ArithmeticException("Rounding is required on " + reference.toString());
    }
    
    public static boolean roundsAtMidpoint(final int roundingMode) {
        switch (roundingMode) {
            case 0:
            case 1:
            case 2:
            case 3: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public static MathContext getMathContextOrUnlimited(final DecimalFormatProperties properties) {
        MathContext mathContext = properties.getMathContext();
        if (mathContext == null) {
            RoundingMode roundingMode = properties.getRoundingMode();
            if (roundingMode == null) {
                roundingMode = RoundingMode.HALF_EVEN;
            }
            mathContext = RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[roundingMode.ordinal()];
        }
        return mathContext;
    }
    
    public static MathContext getMathContextOr34Digits(final DecimalFormatProperties properties) {
        MathContext mathContext = properties.getMathContext();
        if (mathContext == null) {
            RoundingMode roundingMode = properties.getRoundingMode();
            if (roundingMode == null) {
                roundingMode = RoundingMode.HALF_EVEN;
            }
            mathContext = RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS[roundingMode.ordinal()];
        }
        return mathContext;
    }
    
    public static MathContext mathContextUnlimited(final RoundingMode roundingMode) {
        return RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[roundingMode.ordinal()];
    }
    
    public static Scale scaleFromProperties(final DecimalFormatProperties properties) {
        final MathContext mc = getMathContextOr34Digits(properties);
        if (properties.getMagnitudeMultiplier() != 0) {
            return Scale.powerOfTen(properties.getMagnitudeMultiplier()).withMathContext(mc);
        }
        if (properties.getMultiplier() != null) {
            return Scale.byBigDecimal(properties.getMultiplier()).withMathContext(mc);
        }
        return null;
    }
    
    static {
        DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
        MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED = new MathContext[RoundingMode.values().length];
        MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS = new MathContext[RoundingMode.values().length];
        for (int i = 0; i < RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS.length; ++i) {
            RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[i] = new MathContext(0, RoundingMode.valueOf(i));
            RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS[i] = new MathContext(34);
        }
        DEFAULT_MATH_CONTEXT_UNLIMITED = RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[RoundingUtils.DEFAULT_ROUNDING_MODE.ordinal()];
        DEFAULT_MATH_CONTEXT_34_DIGITS = RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS[RoundingUtils.DEFAULT_ROUNDING_MODE.ordinal()];
    }
}
