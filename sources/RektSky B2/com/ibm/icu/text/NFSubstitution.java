package com.ibm.icu.text;

import java.text.*;

abstract class NFSubstitution
{
    final int pos;
    final NFRuleSet ruleSet;
    final DecimalFormat numberFormat;
    private static final long MAX_INT64_IN_DOUBLE = 9007199254740991L;
    
    public static NFSubstitution makeSubstitution(final int pos, final NFRule rule, final NFRule rulePredecessor, final NFRuleSet ruleSet, final RuleBasedNumberFormat formatter, final String description) {
        if (description.length() == 0) {
            return null;
        }
        switch (description.charAt(0)) {
            case '<': {
                if (rule.getBaseValue() == -1L) {
                    throw new IllegalArgumentException("<< not allowed in negative-number rule");
                }
                if (rule.getBaseValue() == -2L || rule.getBaseValue() == -3L || rule.getBaseValue() == -4L) {
                    return new IntegralPartSubstitution(pos, ruleSet, description);
                }
                if (ruleSet.isFractionSet()) {
                    return new NumeratorSubstitution(pos, (double)rule.getBaseValue(), formatter.getDefaultRuleSet(), description);
                }
                return new MultiplierSubstitution(pos, rule, ruleSet, description);
            }
            case '>': {
                if (rule.getBaseValue() == -1L) {
                    return new AbsoluteValueSubstitution(pos, ruleSet, description);
                }
                if (rule.getBaseValue() == -2L || rule.getBaseValue() == -3L || rule.getBaseValue() == -4L) {
                    return new FractionalPartSubstitution(pos, ruleSet, description);
                }
                if (ruleSet.isFractionSet()) {
                    throw new IllegalArgumentException(">> not allowed in fraction rule set");
                }
                return new ModulusSubstitution(pos, rule, rulePredecessor, ruleSet, description);
            }
            case '=': {
                return new SameValueSubstitution(pos, ruleSet, description);
            }
            default: {
                throw new IllegalArgumentException("Illegal substitution character");
            }
        }
    }
    
    NFSubstitution(final int pos, final NFRuleSet ruleSet, String description) {
        this.pos = pos;
        final int descriptionLen = description.length();
        if (descriptionLen >= 2 && description.charAt(0) == description.charAt(descriptionLen - 1)) {
            description = description.substring(1, descriptionLen - 1);
        }
        else if (descriptionLen != 0) {
            throw new IllegalArgumentException("Illegal substitution syntax");
        }
        if (description.length() == 0) {
            this.ruleSet = ruleSet;
            this.numberFormat = null;
        }
        else if (description.charAt(0) == '%') {
            this.ruleSet = ruleSet.owner.findRuleSet(description);
            this.numberFormat = null;
        }
        else if (description.charAt(0) == '#' || description.charAt(0) == '0') {
            this.ruleSet = null;
            (this.numberFormat = (DecimalFormat)ruleSet.owner.getDecimalFormat().clone()).applyPattern(description);
        }
        else {
            if (description.charAt(0) != '>') {
                throw new IllegalArgumentException("Illegal substitution syntax");
            }
            this.ruleSet = ruleSet;
            this.numberFormat = null;
        }
    }
    
    public void setDivisor(final int radix, final short exponent) {
    }
    
    @Override
    public boolean equals(final Object that) {
        if (that == null) {
            return false;
        }
        if (this == that) {
            return true;
        }
        if (this.getClass() == that.getClass()) {
            final NFSubstitution that2 = (NFSubstitution)that;
            return this.pos == that2.pos && (this.ruleSet != null || that2.ruleSet == null) && ((this.numberFormat != null) ? this.numberFormat.equals(that2.numberFormat) : (that2.numberFormat == null));
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public String toString() {
        if (this.ruleSet != null) {
            return this.tokenChar() + this.ruleSet.getName() + this.tokenChar();
        }
        return this.tokenChar() + this.numberFormat.toPattern() + this.tokenChar();
    }
    
    public void doSubstitution(final long number, final StringBuilder toInsertInto, final int position, final int recursionCount) {
        if (this.ruleSet != null) {
            final long numberToFormat = this.transformNumber(number);
            this.ruleSet.format(numberToFormat, toInsertInto, position + this.pos, recursionCount);
        }
        else if (number <= 9007199254740991L) {
            double numberToFormat2 = this.transformNumber((double)number);
            if (this.numberFormat.getMaximumFractionDigits() == 0) {
                numberToFormat2 = Math.floor(numberToFormat2);
            }
            toInsertInto.insert(position + this.pos, this.numberFormat.format(numberToFormat2));
        }
        else {
            final long numberToFormat = this.transformNumber(number);
            toInsertInto.insert(position + this.pos, this.numberFormat.format(numberToFormat));
        }
    }
    
    public void doSubstitution(final double number, final StringBuilder toInsertInto, final int position, final int recursionCount) {
        final double numberToFormat = this.transformNumber(number);
        if (Double.isInfinite(numberToFormat)) {
            final NFRule infiniteRule = this.ruleSet.findRule(Double.POSITIVE_INFINITY);
            infiniteRule.doFormat(numberToFormat, toInsertInto, position + this.pos, recursionCount);
            return;
        }
        if (numberToFormat == Math.floor(numberToFormat) && this.ruleSet != null) {
            this.ruleSet.format((long)numberToFormat, toInsertInto, position + this.pos, recursionCount);
        }
        else if (this.ruleSet != null) {
            this.ruleSet.format(numberToFormat, toInsertInto, position + this.pos, recursionCount);
        }
        else {
            toInsertInto.insert(position + this.pos, this.numberFormat.format(numberToFormat));
        }
    }
    
    public abstract long transformNumber(final long p0);
    
    public abstract double transformNumber(final double p0);
    
    public Number doParse(final String text, final ParsePosition parsePosition, final double baseValue, double upperBound, final boolean lenientParse, final int nonNumericalExecutedRuleMask) {
        upperBound = this.calcUpperBound(upperBound);
        Number tempResult;
        if (this.ruleSet != null) {
            tempResult = this.ruleSet.parse(text, parsePosition, upperBound, nonNumericalExecutedRuleMask);
            if (lenientParse && !this.ruleSet.isFractionSet() && parsePosition.getIndex() == 0) {
                tempResult = this.ruleSet.owner.getDecimalFormat().parse(text, parsePosition);
            }
        }
        else {
            tempResult = this.numberFormat.parse(text, parsePosition);
        }
        if (parsePosition.getIndex() == 0) {
            return tempResult;
        }
        double result = tempResult.doubleValue();
        result = this.composeRuleValue(result, baseValue);
        if (result == (long)result) {
            return (long)result;
        }
        return new Double(result);
    }
    
    public abstract double composeRuleValue(final double p0, final double p1);
    
    public abstract double calcUpperBound(final double p0);
    
    public final int getPos() {
        return this.pos;
    }
    
    abstract char tokenChar();
    
    public boolean isModulusSubstitution() {
        return false;
    }
    
    public void setDecimalFormatSymbols(final DecimalFormatSymbols newSymbols) {
        if (this.numberFormat != null) {
            this.numberFormat.setDecimalFormatSymbols(newSymbols);
        }
    }
}
