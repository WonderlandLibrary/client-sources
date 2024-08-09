/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.NFRule;
import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;
import java.text.ParsePosition;

class ModulusSubstitution
extends NFSubstitution {
    long divisor;
    private final NFRule ruleToUse;

    ModulusSubstitution(int n, NFRule nFRule, NFRule nFRule2, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
        this.divisor = nFRule.getDivisor();
        if (this.divisor == 0L) {
            throw new IllegalStateException("Substitution with bad divisor (" + this.divisor + ") " + string.substring(0, n) + " | " + string.substring(n));
        }
        this.ruleToUse = string.equals(">>>") ? nFRule2 : null;
    }

    @Override
    public void setDivisor(int n, short s) {
        this.divisor = NFRule.power(n, s);
        if (this.divisor == 0L) {
            throw new IllegalStateException("Substitution with bad divisor");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            ModulusSubstitution modulusSubstitution = (ModulusSubstitution)object;
            return this.divisor == modulusSubstitution.divisor;
        }
        return true;
    }

    @Override
    public void doSubstitution(long l, StringBuilder stringBuilder, int n, int n2) {
        if (this.ruleToUse == null) {
            super.doSubstitution(l, stringBuilder, n, n2);
        } else {
            long l2 = this.transformNumber(l);
            this.ruleToUse.doFormat(l2, stringBuilder, n + this.pos, n2);
        }
    }

    @Override
    public void doSubstitution(double d, StringBuilder stringBuilder, int n, int n2) {
        if (this.ruleToUse == null) {
            super.doSubstitution(d, stringBuilder, n, n2);
        } else {
            double d2 = this.transformNumber(d);
            this.ruleToUse.doFormat(d2, stringBuilder, n + this.pos, n2);
        }
    }

    @Override
    public long transformNumber(long l) {
        return l % this.divisor;
    }

    @Override
    public double transformNumber(double d) {
        return Math.floor(d % (double)this.divisor);
    }

    @Override
    public Number doParse(String string, ParsePosition parsePosition, double d, double d2, boolean bl, int n) {
        if (this.ruleToUse == null) {
            return super.doParse(string, parsePosition, d, d2, bl, n);
        }
        Number number = this.ruleToUse.doParse(string, parsePosition, false, d2, n);
        if (parsePosition.getIndex() != 0) {
            double d3 = number.doubleValue();
            if ((d3 = this.composeRuleValue(d3, d)) == (double)((long)d3)) {
                return (long)d3;
            }
            return new Double(d3);
        }
        return number;
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d2 - d2 % (double)this.divisor + d;
    }

    @Override
    public double calcUpperBound(double d) {
        return this.divisor;
    }

    @Override
    public boolean isModulusSubstitution() {
        return false;
    }

    @Override
    char tokenChar() {
        return '\u0001';
    }
}

