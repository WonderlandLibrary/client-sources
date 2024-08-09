/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;
import java.text.ParsePosition;

class NumeratorSubstitution
extends NFSubstitution {
    private final double denominator;
    private final boolean withZeros;

    NumeratorSubstitution(int n, double d, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, NumeratorSubstitution.fixdesc(string));
        this.denominator = d;
        this.withZeros = string.endsWith("<<");
    }

    static String fixdesc(String string) {
        return string.endsWith("<<") ? string.substring(0, string.length() - 1) : string;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NumeratorSubstitution numeratorSubstitution = (NumeratorSubstitution)object;
            return this.denominator == numeratorSubstitution.denominator && this.withZeros == numeratorSubstitution.withZeros;
        }
        return true;
    }

    @Override
    public void doSubstitution(double d, StringBuilder stringBuilder, int n, int n2) {
        double d2 = this.transformNumber(d);
        if (this.withZeros && this.ruleSet != null) {
            long l = (long)d2;
            int n3 = stringBuilder.length();
            while (true) {
                long l2;
                l *= 10L;
                if (!((double)l2 < this.denominator)) break;
                stringBuilder.insert(n + this.pos, ' ');
                this.ruleSet.format(0L, stringBuilder, n + this.pos, n2);
            }
            n += stringBuilder.length() - n3;
        }
        if (d2 == Math.floor(d2) && this.ruleSet != null) {
            this.ruleSet.format((long)d2, stringBuilder, n + this.pos, n2);
        } else if (this.ruleSet != null) {
            this.ruleSet.format(d2, stringBuilder, n + this.pos, n2);
        } else {
            stringBuilder.insert(n + this.pos, this.numberFormat.format(d2));
        }
    }

    @Override
    public long transformNumber(long l) {
        return Math.round((double)l * this.denominator);
    }

    @Override
    public double transformNumber(double d) {
        return Math.round(d * this.denominator);
    }

    @Override
    public Number doParse(String string, ParsePosition parsePosition, double d, double d2, boolean bl, int n) {
        Object object;
        int n2 = 0;
        if (this.withZeros) {
            object = string;
            ParsePosition parsePosition2 = new ParsePosition(1);
            while (((String)object).length() > 0 && parsePosition2.getIndex() != 0) {
                parsePosition2.setIndex(0);
                this.ruleSet.parse((String)object, parsePosition2, 1.0, n).intValue();
                if (parsePosition2.getIndex() == 0) break;
                ++n2;
                parsePosition.setIndex(parsePosition.getIndex() + parsePosition2.getIndex());
                object = ((String)object).substring(parsePosition2.getIndex());
                while (((String)object).length() > 0 && ((String)object).charAt(0) == ' ') {
                    object = ((String)object).substring(1);
                    parsePosition.setIndex(parsePosition.getIndex() + 1);
                }
            }
            string = string.substring(parsePosition.getIndex());
            parsePosition.setIndex(0);
        }
        object = super.doParse(string, parsePosition, this.withZeros ? 1.0 : d, d2, false, n);
        if (this.withZeros) {
            long l;
            long l2 = ((Number)object).longValue();
            for (l = 1L; l <= l2; l *= 10L) {
            }
            while (n2 > 0) {
                l *= 10L;
                --n2;
            }
            object = new Double((double)l2 / (double)l);
        }
        return object;
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d / d2;
    }

    @Override
    public double calcUpperBound(double d) {
        return this.denominator;
    }

    @Override
    char tokenChar() {
        return '\u0001';
    }
}

