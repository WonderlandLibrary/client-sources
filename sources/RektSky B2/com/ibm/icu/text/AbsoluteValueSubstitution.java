package com.ibm.icu.text;

class AbsoluteValueSubstitution extends NFSubstitution
{
    AbsoluteValueSubstitution(final int pos, final NFRuleSet ruleSet, final String description) {
        super(pos, ruleSet, description);
    }
    
    @Override
    public long transformNumber(final long number) {
        return Math.abs(number);
    }
    
    @Override
    public double transformNumber(final double number) {
        return Math.abs(number);
    }
    
    @Override
    public double composeRuleValue(final double newRuleValue, final double oldRuleValue) {
        return -newRuleValue;
    }
    
    @Override
    public double calcUpperBound(final double oldUpperBound) {
        return Double.MAX_VALUE;
    }
    
    @Override
    char tokenChar() {
        return '>';
    }
}
