/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NFRule;
import com.ibm.icu.text.RuleBasedNumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

final class NFRuleSet {
    private final String name;
    private NFRule[] rules;
    final NFRule[] nonNumericalRules = new NFRule[6];
    LinkedList<NFRule> fractionRules;
    static final int NEGATIVE_RULE_INDEX = 0;
    static final int IMPROPER_FRACTION_RULE_INDEX = 1;
    static final int PROPER_FRACTION_RULE_INDEX = 2;
    static final int MASTER_RULE_INDEX = 3;
    static final int INFINITY_RULE_INDEX = 4;
    static final int NAN_RULE_INDEX = 5;
    final RuleBasedNumberFormat owner;
    private boolean isFractionRuleSet = false;
    private final boolean isParseable;
    private static final int RECURSION_LIMIT = 64;
    static final boolean $assertionsDisabled = !NFRuleSet.class.desiredAssertionStatus();

    public NFRuleSet(RuleBasedNumberFormat ruleBasedNumberFormat, String[] stringArray, int n) throws IllegalArgumentException {
        this.owner = ruleBasedNumberFormat;
        String string = stringArray[n];
        if (string.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
        if (string.charAt(0) == '%') {
            int n2 = string.indexOf(58);
            if (n2 == -1) {
                throw new IllegalArgumentException("Rule set name doesn't end in colon");
            }
            String string2 = string.substring(0, n2);
            boolean bl = this.isParseable = !string2.endsWith("@noparse");
            if (!this.isParseable) {
                string2 = string2.substring(0, string2.length() - 8);
            }
            this.name = string2;
            while (n2 < string.length() && PatternProps.isWhiteSpace(string.charAt(++n2))) {
            }
            stringArray[n] = string = string.substring(n2);
        } else {
            this.name = "%default";
            this.isParseable = true;
        }
        if (string.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
    }

    public void parseRules(String string) {
        int n;
        ArrayList<NFRule> arrayList = new ArrayList<NFRule>();
        NFRule nFRule = null;
        int n2 = 0;
        int n3 = string.length();
        do {
            if ((n = string.indexOf(59, n2)) < 0) {
                n = n3;
            }
            NFRule.makeRules(string.substring(n2, n), this, nFRule, this.owner, arrayList);
            if (arrayList.isEmpty()) continue;
            nFRule = (NFRule)arrayList.get(arrayList.size() - 1);
        } while ((n2 = n + 1) < n3);
        long l = 0L;
        for (NFRule nFRule2 : arrayList) {
            long l2 = nFRule2.getBaseValue();
            if (l2 == 0L) {
                nFRule2.setBaseValue(l);
            } else {
                if (l2 < l) {
                    throw new IllegalArgumentException("Rules are not in order, base: " + l2 + " < " + l);
                }
                l = l2;
            }
            if (this.isFractionRuleSet) continue;
            ++l;
        }
        this.rules = new NFRule[arrayList.size()];
        arrayList.toArray(this.rules);
    }

    void setNonNumericalRule(NFRule nFRule) {
        long l = nFRule.getBaseValue();
        if (l == -1L) {
            this.nonNumericalRules[0] = nFRule;
        } else if (l == -2L) {
            this.setBestFractionRule(1, nFRule, true);
        } else if (l == -3L) {
            this.setBestFractionRule(2, nFRule, true);
        } else if (l == -4L) {
            this.setBestFractionRule(3, nFRule, true);
        } else if (l == -5L) {
            this.nonNumericalRules[4] = nFRule;
        } else if (l == -6L) {
            this.nonNumericalRules[5] = nFRule;
        }
    }

    private void setBestFractionRule(int n, NFRule nFRule, boolean bl) {
        NFRule nFRule2;
        if (bl) {
            if (this.fractionRules == null) {
                this.fractionRules = new LinkedList();
            }
            this.fractionRules.add(nFRule);
        }
        if ((nFRule2 = this.nonNumericalRules[n]) == null) {
            this.nonNumericalRules[n] = nFRule;
        } else {
            DecimalFormatSymbols decimalFormatSymbols = this.owner.getDecimalFormatSymbols();
            if (decimalFormatSymbols.getDecimalSeparator() == nFRule.getDecimalPoint()) {
                this.nonNumericalRules[n] = nFRule;
            }
        }
    }

    public void makeIntoFractionRuleSet() {
        this.isFractionRuleSet = true;
    }

    public boolean equals(Object object) {
        int n;
        if (!(object instanceof NFRuleSet)) {
            return true;
        }
        NFRuleSet nFRuleSet = (NFRuleSet)object;
        if (!this.name.equals(nFRuleSet.name) || this.rules.length != nFRuleSet.rules.length || this.isFractionRuleSet != nFRuleSet.isFractionRuleSet) {
            return true;
        }
        for (n = 0; n < this.nonNumericalRules.length; ++n) {
            if (Objects.equals(this.nonNumericalRules[n], nFRuleSet.nonNumericalRules[n])) continue;
            return true;
        }
        for (n = 0; n < this.rules.length; ++n) {
            if (this.rules[n].equals(nFRuleSet.rules[n])) continue;
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (!$assertionsDisabled) {
            throw new AssertionError((Object)"hashCode not designed");
        }
        return 1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name).append(":\n");
        for (NFRule nFRule : this.rules) {
            stringBuilder.append(nFRule.toString()).append("\n");
        }
        for (NFRule nFRule : this.nonNumericalRules) {
            if (nFRule == null) continue;
            if (nFRule.getBaseValue() == -2L || nFRule.getBaseValue() == -3L || nFRule.getBaseValue() == -4L) {
                for (NFRule nFRule2 : this.fractionRules) {
                    if (nFRule2.getBaseValue() != nFRule.getBaseValue()) continue;
                    stringBuilder.append(nFRule2.toString()).append("\n");
                }
                continue;
            }
            stringBuilder.append(nFRule.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean isFractionSet() {
        return this.isFractionRuleSet;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPublic() {
        return !this.name.startsWith("%%");
    }

    public boolean isParseable() {
        return this.isParseable;
    }

    public void format(long l, StringBuilder stringBuilder, int n, int n2) {
        if (n2 >= 64) {
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        NFRule nFRule = this.findNormalRule(l);
        nFRule.doFormat(l, stringBuilder, n, ++n2);
    }

    public void format(double d, StringBuilder stringBuilder, int n, int n2) {
        if (n2 >= 64) {
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        NFRule nFRule = this.findRule(d);
        nFRule.doFormat(d, stringBuilder, n, ++n2);
    }

    NFRule findRule(double d) {
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule(d);
        }
        if (Double.isNaN(d)) {
            NFRule nFRule = this.nonNumericalRules[5];
            if (nFRule == null) {
                nFRule = this.owner.getDefaultNaNRule();
            }
            return nFRule;
        }
        if (d < 0.0) {
            if (this.nonNumericalRules[0] != null) {
                return this.nonNumericalRules[0];
            }
            d = -d;
        }
        if (Double.isInfinite(d)) {
            NFRule nFRule = this.nonNumericalRules[4];
            if (nFRule == null) {
                nFRule = this.owner.getDefaultInfinityRule();
            }
            return nFRule;
        }
        if (d != Math.floor(d)) {
            if (d < 1.0 && this.nonNumericalRules[2] != null) {
                return this.nonNumericalRules[2];
            }
            if (this.nonNumericalRules[1] != null) {
                return this.nonNumericalRules[1];
            }
        }
        if (this.nonNumericalRules[3] != null) {
            return this.nonNumericalRules[3];
        }
        return this.findNormalRule(Math.round(d));
    }

    private NFRule findNormalRule(long l) {
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule(l);
        }
        if (l < 0L) {
            if (this.nonNumericalRules[0] != null) {
                return this.nonNumericalRules[0];
            }
            l = -l;
        }
        int n = 0;
        int n2 = this.rules.length;
        if (n2 > 0) {
            while (n < n2) {
                int n3 = n + n2 >>> 1;
                long l2 = this.rules[n3].getBaseValue();
                if (l2 == l) {
                    return this.rules[n3];
                }
                if (l2 > l) {
                    n2 = n3;
                    continue;
                }
                n = n3 + 1;
            }
            if (n2 == 0) {
                throw new IllegalStateException("The rule set " + this.name + " cannot format the value " + l);
            }
            NFRule nFRule = this.rules[n2 - 1];
            if (nFRule.shouldRollBack(l)) {
                if (n2 == 1) {
                    throw new IllegalStateException("The rule set " + this.name + " cannot roll back from the rule '" + nFRule + "'");
                }
                nFRule = this.rules[n2 - 2];
            }
            return nFRule;
        }
        return this.nonNumericalRules[3];
    }

    private NFRule findFractionRuleSetRule(double d) {
        long l = this.rules[0].getBaseValue();
        for (int i = 1; i < this.rules.length; ++i) {
            l = NFRuleSet.lcm(l, this.rules[i].getBaseValue());
        }
        long l2 = Math.round(d * (double)l);
        long l3 = Long.MAX_VALUE;
        int n = 0;
        for (int i = 0; i < this.rules.length; ++i) {
            long l4 = l2 * this.rules[i].getBaseValue() % l;
            if (l - l4 < l4) {
                l4 = l - l4;
            }
            if (l4 >= l3) continue;
            l3 = l4;
            n = i;
            if (l3 == 0L) break;
        }
        if (n + 1 < this.rules.length && this.rules[n + 1].getBaseValue() == this.rules[n].getBaseValue() && (Math.round(d * (double)this.rules[n].getBaseValue()) < 1L || Math.round(d * (double)this.rules[n].getBaseValue()) >= 2L)) {
            ++n;
        }
        return this.rules[n];
    }

    private static long lcm(long l, long l2) {
        long l3 = l;
        long l4 = l2;
        int n = 0;
        while ((l3 & 1L) == 0L && (l4 & 1L) == 0L) {
            ++n;
            l3 >>= 1;
            l4 >>= 1;
        }
        long l5 = (l3 & 1L) == 1L ? -l4 : l3;
        while (l5 != 0L) {
            while ((l5 & 1L) == 0L) {
                l5 >>= 1;
            }
            if (l5 > 0L) {
                l3 = l5;
            } else {
                l4 = -l5;
            }
            l5 = l3 - l4;
        }
        long l6 = l3 << n;
        return l / l6 * l2;
    }

    public Number parse(String string, ParsePosition parsePosition, double d, int n) {
        Number number;
        int n2;
        ParsePosition parsePosition2 = new ParsePosition(0);
        Number number2 = NFRule.ZERO;
        if (string.length() == 0) {
            return number2;
        }
        for (n2 = 0; n2 < this.nonNumericalRules.length; ++n2) {
            NFRule nFRule = this.nonNumericalRules[n2];
            if (nFRule == null || (n >> n2 & 1) != 0) continue;
            number = nFRule.doParse(string, parsePosition, false, d, n |= 1 << n2);
            if (parsePosition.getIndex() > parsePosition2.getIndex()) {
                number2 = number;
                parsePosition2.setIndex(parsePosition.getIndex());
            }
            parsePosition.setIndex(0);
        }
        for (n2 = this.rules.length - 1; n2 >= 0 && parsePosition2.getIndex() < string.length(); --n2) {
            if (!this.isFractionRuleSet && (double)this.rules[n2].getBaseValue() >= d) continue;
            number = this.rules[n2].doParse(string, parsePosition, this.isFractionRuleSet, d, n);
            if (parsePosition.getIndex() > parsePosition2.getIndex()) {
                number2 = number;
                parsePosition2.setIndex(parsePosition.getIndex());
            }
            parsePosition.setIndex(0);
        }
        parsePosition.setIndex(parsePosition2.getIndex());
        return number2;
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        for (NFRule nFRule : this.rules) {
            nFRule.setDecimalFormatSymbols(decimalFormatSymbols);
        }
        if (this.fractionRules != null) {
            for (int i = 1; i <= 3; ++i) {
                if (this.nonNumericalRules[i] == null) continue;
                for (NFRule nFRule : this.fractionRules) {
                    if (this.nonNumericalRules[i].getBaseValue() != nFRule.getBaseValue()) continue;
                    this.setBestFractionRule(i, nFRule, false);
                }
            }
        }
        for (NFRule nFRule : this.nonNumericalRules) {
            if (nFRule == null) continue;
            nFRule.setDecimalFormatSymbols(decimalFormatSymbols);
        }
    }
}

