/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;

class AbsoluteValueSubstitution
extends NFSubstitution {
    AbsoluteValueSubstitution(int n, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
    }

    @Override
    public long transformNumber(long l) {
        return Math.abs(l);
    }

    @Override
    public double transformNumber(double d) {
        return Math.abs(d);
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return -d;
    }

    @Override
    public double calcUpperBound(double d) {
        return Double.MAX_VALUE;
    }

    @Override
    char tokenChar() {
        return '\u0001';
    }
}

