/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;

class SameValueSubstitution
extends NFSubstitution {
    SameValueSubstitution(int n, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
        if (string.equals("==")) {
            throw new IllegalArgumentException("== is not a legal token");
        }
    }

    @Override
    public long transformNumber(long l) {
        return l;
    }

    @Override
    public double transformNumber(double d) {
        return d;
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d;
    }

    @Override
    public double calcUpperBound(double d) {
        return d;
    }

    @Override
    char tokenChar() {
        return '\u0000';
    }
}

