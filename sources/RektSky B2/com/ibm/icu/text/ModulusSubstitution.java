package com.ibm.icu.text;

import java.text.*;

class ModulusSubstitution extends NFSubstitution
{
    long divisor;
    private final NFRule ruleToUse;
    
    ModulusSubstitution(final int pos, final NFRule rule, final NFRule rulePredecessor, final NFRuleSet ruleSet, final String description) {
        super(pos, ruleSet, description);
        this.divisor = rule.getDivisor();
        if (this.divisor == 0L) {
            throw new IllegalStateException("Substitution with bad divisor (" + this.divisor + ") " + description.substring(0, pos) + " | " + description.substring(pos));
        }
        if (description.equals(">>>")) {
            this.ruleToUse = rulePredecessor;
        }
        else {
            this.ruleToUse = null;
        }
    }
    
    @Override
    public void setDivisor(final int radix, final short exponent) {
        this.divisor = NFRule.power(radix, exponent);
        if (this.divisor == 0L) {
            throw new IllegalStateException("Substitution with bad divisor");
        }
    }
    
    @Override
    public boolean equals(final Object that) {
        if (super.equals(that)) {
            final ModulusSubstitution that2 = (ModulusSubstitution)that;
            return this.divisor == that2.divisor;
        }
        return false;
    }
    
    @Override
    public void doSubstitution(final long number, final StringBuilder toInsertInto, final int position, final int recursionCount) {
        if (this.ruleToUse == null) {
            super.doSubstitution(number, toInsertInto, position, recursionCount);
        }
        else {
            final long numberToFormat = this.transformNumber(number);
            this.ruleToUse.doFormat(numberToFormat, toInsertInto, position + this.pos, recursionCount);
        }
    }
    
    @Override
    public void doSubstitution(final double number, final StringBuilder toInsertInto, final int position, final int recursionCount) {
        if (this.ruleToUse == null) {
            super.doSubstitution(number, toInsertInto, position, recursionCount);
        }
        else {
            final double numberToFormat = this.transformNumber(number);
            this.ruleToUse.doFormat(numberToFormat, toInsertInto, position + this.pos, recursionCount);
        }
    }
    
    @Override
    public long transformNumber(final long number) {
        return number % this.divisor;
    }
    
    @Override
    public double transformNumber(final double number) {
        return Math.floor(number % this.divisor);
    }
    
    @Override
    public Number doParse(final String text, final ParsePosition parsePosition, final double baseValue, final double upperBound, final boolean lenientParse, final int nonNumericalExecutedRuleMask) {
        if (this.ruleToUse == null) {
            return super.doParse(text, parsePosition, baseValue, upperBound, lenientParse, nonNumericalExecutedRuleMask);
        }
        final Number tempResult = this.ruleToUse.doParse(text, parsePosition, false, upperBound, nonNumericalExecutedRuleMask);
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
    
    @Override
    public double composeRuleValue(final double newRuleValue, final double oldRuleValue) {
        return oldRuleValue - oldRuleValue % this.divisor + newRuleValue;
    }
    
    @Override
    public double calcUpperBound(final double oldUpperBound) {
        return (double)this.divisor;
    }
    
    @Override
    public boolean isModulusSubstitution() {
        return true;
    }
    
    @Override
    char tokenChar() {
        return '>';
    }
}
