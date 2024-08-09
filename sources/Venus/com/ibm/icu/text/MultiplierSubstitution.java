/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.NFRule;
import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;

class MultiplierSubstitution
extends NFSubstitution {
    long divisor;

    MultiplierSubstitution(int n, NFRule nFRule, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
        this.divisor = nFRule.getDivisor();
        if (this.divisor == 0L) {
            throw new IllegalStateException("Substitution with divisor 0 " + string.substring(0, n) + " | " + string.substring(n));
        }
    }

    @Override
    public void setDivisor(int n, short s) {
        this.divisor = NFRule.power(n, s);
        if (this.divisor == 0L) {
            throw new IllegalStateException("Substitution with divisor 0");
        }
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) && this.divisor == ((MultiplierSubstitution)object).divisor;
    }

    @Override
    public long transformNumber(long l) {
        return (long)Math.floor(l / this.divisor);
    }

    @Override
    public double transformNumber(double d) {
        if (this.ruleSet == null) {
            return d / (double)this.divisor;
        }
        return Math.floor(d / (double)this.divisor);
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d * (double)this.divisor;
    }

    @Override
    public double calcUpperBound(double d) {
        return this.divisor;
    }

    @Override
    char tokenChar() {
        return '\u0001';
    }
}

