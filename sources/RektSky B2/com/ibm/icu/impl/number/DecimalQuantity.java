package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;
import java.math.*;
import com.ibm.icu.impl.*;
import java.text.*;

public interface DecimalQuantity extends PluralRules.IFixedDecimal
{
    void setIntegerLength(final int p0, final int p1);
    
    void setFractionLength(final int p0, final int p1);
    
    void roundToIncrement(final BigDecimal p0, final MathContext p1);
    
    void roundToMagnitude(final int p0, final MathContext p1);
    
    void roundToInfinity();
    
    void multiplyBy(final BigDecimal p0);
    
    void negate();
    
    void adjustMagnitude(final int p0);
    
    int getMagnitude() throws ArithmeticException;
    
    boolean isZero();
    
    boolean isNegative();
    
    int signum();
    
    boolean isInfinite();
    
    boolean isNaN();
    
    double toDouble();
    
    BigDecimal toBigDecimal();
    
    void setToBigDecimal(final BigDecimal p0);
    
    int maxRepresentableDigits();
    
    StandardPlural getStandardPlural(final PluralRules p0);
    
    byte getDigit(final int p0);
    
    int getUpperDisplayMagnitude();
    
    int getLowerDisplayMagnitude();
    
    String toPlainString();
    
    DecimalQuantity createCopy();
    
    void copyFrom(final DecimalQuantity p0);
    
    long getPositionFingerprint();
    
    void populateUFieldPosition(final FieldPosition p0);
}
