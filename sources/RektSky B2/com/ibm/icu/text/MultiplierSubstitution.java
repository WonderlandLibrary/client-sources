package com.ibm.icu.text;

class MultiplierSubstitution extends NFSubstitution
{
    long divisor;
    
    MultiplierSubstitution(final int pos, final NFRule rule, final NFRuleSet ruleSet, final String description) {
        super(pos, ruleSet, description);
        this.divisor = rule.getDivisor();
        if (this.divisor == 0L) {
            throw new IllegalStateException("Substitution with divisor 0 " + description.substring(0, pos) + " | " + description.substring(pos));
        }
    }
    
    @Override
    public void setDivisor(final int radix, final short exponent) {
        this.divisor = NFRule.power(radix, exponent);
        if (this.divisor == 0L) {
            throw new IllegalStateException("Substitution with divisor 0");
        }
    }
    
    @Override
    public boolean equals(final Object that) {
        return super.equals(that) && this.divisor == ((MultiplierSubstitution)that).divisor;
    }
    
    @Override
    public long transformNumber(final long number) {
        return (long)Math.floor((double)(number / this.divisor));
    }
    
    @Override
    public double transformNumber(final double number) {
        if (this.ruleSet == null) {
            return number / this.divisor;
        }
        return Math.floor(number / this.divisor);
    }
    
    @Override
    public double composeRuleValue(final double newRuleValue, final double oldRuleValue) {
        return newRuleValue * this.divisor;
    }
    
    @Override
    public double calcUpperBound(final double oldUpperBound) {
        return (double)this.divisor;
    }
    
    @Override
    char tokenChar() {
        return '<';
    }
}
