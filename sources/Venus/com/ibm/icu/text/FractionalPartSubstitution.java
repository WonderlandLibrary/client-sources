/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;
import java.text.ParsePosition;

class FractionalPartSubstitution
extends NFSubstitution {
    private final boolean byDigits;
    private final boolean useSpaces;

    FractionalPartSubstitution(int n, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
        if (string.equals(">>") || string.equals(">>>") || nFRuleSet == this.ruleSet) {
            this.byDigits = true;
            this.useSpaces = !string.equals(">>>");
        } else {
            this.byDigits = false;
            this.useSpaces = true;
            this.ruleSet.makeIntoFractionRuleSet();
        }
    }

    @Override
    public void doSubstitution(double d, StringBuilder stringBuilder, int n, int n2) {
        if (!this.byDigits) {
            super.doSubstitution(d, stringBuilder, n, n2);
        } else {
            DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(d);
            decimalQuantity_DualStorageBCD.roundToInfinity();
            boolean bl = false;
            int n3 = decimalQuantity_DualStorageBCD.getLowerDisplayMagnitude();
            while (n3 < 0) {
                if (bl && this.useSpaces) {
                    stringBuilder.insert(n + this.pos, ' ');
                } else {
                    bl = true;
                }
                this.ruleSet.format(decimalQuantity_DualStorageBCD.getDigit(n3++), stringBuilder, n + this.pos, n2);
            }
        }
    }

    @Override
    public long transformNumber(long l) {
        return 0L;
    }

    @Override
    public double transformNumber(double d) {
        return d - Math.floor(d);
    }

    @Override
    public Number doParse(String string, ParsePosition parsePosition, double d, double d2, boolean bl, int n) {
        if (!this.byDigits) {
            return super.doParse(string, parsePosition, d, 0.0, bl, n);
        }
        String string2 = string;
        ParsePosition parsePosition2 = new ParsePosition(1);
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD();
        int n2 = 0;
        while (string2.length() > 0 && parsePosition2.getIndex() != 0) {
            Number number;
            parsePosition2.setIndex(0);
            int n3 = this.ruleSet.parse(string2, parsePosition2, 10.0, n).intValue();
            if (bl && parsePosition2.getIndex() == 0 && (number = this.ruleSet.owner.getDecimalFormat().parse(string2, parsePosition2)) != null) {
                n3 = number.intValue();
            }
            if (parsePosition2.getIndex() == 0) continue;
            decimalQuantity_DualStorageBCD.appendDigit((byte)n3, 0, false);
            ++n2;
            parsePosition.setIndex(parsePosition.getIndex() + parsePosition2.getIndex());
            string2 = string2.substring(parsePosition2.getIndex());
            while (string2.length() > 0 && string2.charAt(0) == ' ') {
                string2 = string2.substring(1);
                parsePosition.setIndex(parsePosition.getIndex() + 1);
            }
        }
        decimalQuantity_DualStorageBCD.adjustMagnitude(-n2);
        double d3 = decimalQuantity_DualStorageBCD.toDouble();
        d3 = this.composeRuleValue(d3, d);
        return new Double(d3);
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d + d2;
    }

    @Override
    public double calcUpperBound(double d) {
        return 0.0;
    }

    @Override
    char tokenChar() {
        return '\u0001';
    }
}

