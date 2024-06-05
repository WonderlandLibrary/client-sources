package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.impl.*;
import java.text.*;

final class NFRuleSet
{
    private final String name;
    private NFRule[] rules;
    final NFRule[] nonNumericalRules;
    LinkedList<NFRule> fractionRules;
    static final int NEGATIVE_RULE_INDEX = 0;
    static final int IMPROPER_FRACTION_RULE_INDEX = 1;
    static final int PROPER_FRACTION_RULE_INDEX = 2;
    static final int MASTER_RULE_INDEX = 3;
    static final int INFINITY_RULE_INDEX = 4;
    static final int NAN_RULE_INDEX = 5;
    final RuleBasedNumberFormat owner;
    private boolean isFractionRuleSet;
    private final boolean isParseable;
    private static final int RECURSION_LIMIT = 64;
    
    public NFRuleSet(final RuleBasedNumberFormat owner, final String[] descriptions, final int index) throws IllegalArgumentException {
        this.nonNumericalRules = new NFRule[6];
        this.isFractionRuleSet = false;
        this.owner = owner;
        String description = descriptions[index];
        if (description.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
        if (description.charAt(0) == '%') {
            int pos = description.indexOf(58);
            if (pos == -1) {
                throw new IllegalArgumentException("Rule set name doesn't end in colon");
            }
            String name = description.substring(0, pos);
            if (!(this.isParseable = !name.endsWith("@noparse"))) {
                name = name.substring(0, name.length() - 8);
            }
            this.name = name;
            while (pos < description.length() && PatternProps.isWhiteSpace(description.charAt(++pos))) {}
            description = description.substring(pos);
            descriptions[index] = description;
        }
        else {
            this.name = "%default";
            this.isParseable = true;
        }
        if (description.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
    }
    
    public void parseRules(final String description) {
        final List<NFRule> tempRules = new ArrayList<NFRule>();
        NFRule predecessor = null;
        int oldP = 0;
        final int descriptionLen = description.length();
        do {
            int p = description.indexOf(59, oldP);
            if (p < 0) {
                p = descriptionLen;
            }
            NFRule.makeRules(description.substring(oldP, p), this, predecessor, this.owner, tempRules);
            if (!tempRules.isEmpty()) {
                predecessor = tempRules.get(tempRules.size() - 1);
            }
            oldP = p + 1;
        } while (oldP < descriptionLen);
        long defaultBaseValue = 0L;
        for (final NFRule rule : tempRules) {
            final long baseValue = rule.getBaseValue();
            if (baseValue == 0L) {
                rule.setBaseValue(defaultBaseValue);
            }
            else {
                if (baseValue < defaultBaseValue) {
                    throw new IllegalArgumentException("Rules are not in order, base: " + baseValue + " < " + defaultBaseValue);
                }
                defaultBaseValue = baseValue;
            }
            if (!this.isFractionRuleSet) {
                ++defaultBaseValue;
            }
        }
        tempRules.toArray(this.rules = new NFRule[tempRules.size()]);
    }
    
    void setNonNumericalRule(final NFRule rule) {
        final long baseValue = rule.getBaseValue();
        if (baseValue == -1L) {
            this.nonNumericalRules[0] = rule;
        }
        else if (baseValue == -2L) {
            this.setBestFractionRule(1, rule, true);
        }
        else if (baseValue == -3L) {
            this.setBestFractionRule(2, rule, true);
        }
        else if (baseValue == -4L) {
            this.setBestFractionRule(3, rule, true);
        }
        else if (baseValue == -5L) {
            this.nonNumericalRules[4] = rule;
        }
        else if (baseValue == -6L) {
            this.nonNumericalRules[5] = rule;
        }
    }
    
    private void setBestFractionRule(final int originalIndex, final NFRule newRule, final boolean rememberRule) {
        if (rememberRule) {
            if (this.fractionRules == null) {
                this.fractionRules = new LinkedList<NFRule>();
            }
            this.fractionRules.add(newRule);
        }
        final NFRule bestResult = this.nonNumericalRules[originalIndex];
        if (bestResult == null) {
            this.nonNumericalRules[originalIndex] = newRule;
        }
        else {
            final DecimalFormatSymbols decimalFormatSymbols = this.owner.getDecimalFormatSymbols();
            if (decimalFormatSymbols.getDecimalSeparator() == newRule.getDecimalPoint()) {
                this.nonNumericalRules[originalIndex] = newRule;
            }
        }
    }
    
    public void makeIntoFractionRuleSet() {
        this.isFractionRuleSet = true;
    }
    
    @Override
    public boolean equals(final Object that) {
        if (!(that instanceof NFRuleSet)) {
            return false;
        }
        final NFRuleSet that2 = (NFRuleSet)that;
        if (!this.name.equals(that2.name) || this.rules.length != that2.rules.length || this.isFractionRuleSet != that2.isFractionRuleSet) {
            return false;
        }
        for (int i = 0; i < this.nonNumericalRules.length; ++i) {
            if (!Utility.objectEquals(this.nonNumericalRules[i], that2.nonNumericalRules[i])) {
                return false;
            }
        }
        for (int i = 0; i < this.rules.length; ++i) {
            if (!this.rules[i].equals(that2.rules[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(this.name).append(":\n");
        for (final NFRule rule : this.rules) {
            result.append(rule.toString()).append("\n");
        }
        for (final NFRule rule : this.nonNumericalRules) {
            if (rule != null) {
                if (rule.getBaseValue() == -2L || rule.getBaseValue() == -3L || rule.getBaseValue() == -4L) {
                    for (final NFRule fractionRule : this.fractionRules) {
                        if (fractionRule.getBaseValue() == rule.getBaseValue()) {
                            result.append(fractionRule.toString()).append("\n");
                        }
                    }
                }
                else {
                    result.append(rule.toString()).append("\n");
                }
            }
        }
        return result.toString();
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
    
    public void format(final long number, final StringBuilder toInsertInto, final int pos, int recursionCount) {
        if (recursionCount >= 64) {
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        final NFRule applicableRule = this.findNormalRule(number);
        applicableRule.doFormat(number, toInsertInto, pos, ++recursionCount);
    }
    
    public void format(final double number, final StringBuilder toInsertInto, final int pos, int recursionCount) {
        if (recursionCount >= 64) {
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        final NFRule applicableRule = this.findRule(number);
        applicableRule.doFormat(number, toInsertInto, pos, ++recursionCount);
    }
    
    NFRule findRule(double number) {
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule(number);
        }
        if (Double.isNaN(number)) {
            NFRule rule = this.nonNumericalRules[5];
            if (rule == null) {
                rule = this.owner.getDefaultNaNRule();
            }
            return rule;
        }
        if (number < 0.0) {
            if (this.nonNumericalRules[0] != null) {
                return this.nonNumericalRules[0];
            }
            number = -number;
        }
        if (Double.isInfinite(number)) {
            NFRule rule = this.nonNumericalRules[4];
            if (rule == null) {
                rule = this.owner.getDefaultInfinityRule();
            }
            return rule;
        }
        if (number != Math.floor(number)) {
            if (number < 1.0 && this.nonNumericalRules[2] != null) {
                return this.nonNumericalRules[2];
            }
            if (this.nonNumericalRules[1] != null) {
                return this.nonNumericalRules[1];
            }
        }
        if (this.nonNumericalRules[3] != null) {
            return this.nonNumericalRules[3];
        }
        return this.findNormalRule(Math.round(number));
    }
    
    private NFRule findNormalRule(long number) {
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule((double)number);
        }
        if (number < 0L) {
            if (this.nonNumericalRules[0] != null) {
                return this.nonNumericalRules[0];
            }
            number = -number;
        }
        int lo = 0;
        int hi = this.rules.length;
        if (hi <= 0) {
            return this.nonNumericalRules[3];
        }
        while (lo < hi) {
            final int mid = lo + hi >>> 1;
            final long ruleBaseValue = this.rules[mid].getBaseValue();
            if (ruleBaseValue == number) {
                return this.rules[mid];
            }
            if (ruleBaseValue > number) {
                hi = mid;
            }
            else {
                lo = mid + 1;
            }
        }
        if (hi == 0) {
            throw new IllegalStateException("The rule set " + this.name + " cannot format the value " + number);
        }
        NFRule result = this.rules[hi - 1];
        if (result.shouldRollBack(number)) {
            if (hi == 1) {
                throw new IllegalStateException("The rule set " + this.name + " cannot roll back from the rule '" + result + "'");
            }
            result = this.rules[hi - 2];
        }
        return result;
    }
    
    private NFRule findFractionRuleSetRule(final double number) {
        long leastCommonMultiple = this.rules[0].getBaseValue();
        for (int i = 1; i < this.rules.length; ++i) {
            leastCommonMultiple = lcm(leastCommonMultiple, this.rules[i].getBaseValue());
        }
        final long numerator = Math.round(number * leastCommonMultiple);
        long difference = Long.MAX_VALUE;
        int winner = 0;
        for (int j = 0; j < this.rules.length; ++j) {
            long tempDifference = numerator * this.rules[j].getBaseValue() % leastCommonMultiple;
            if (leastCommonMultiple - tempDifference < tempDifference) {
                tempDifference = leastCommonMultiple - tempDifference;
            }
            if (tempDifference < difference) {
                difference = tempDifference;
                winner = j;
                if (difference == 0L) {
                    break;
                }
            }
        }
        if (winner + 1 < this.rules.length && this.rules[winner + 1].getBaseValue() == this.rules[winner].getBaseValue() && (Math.round(number * this.rules[winner].getBaseValue()) < 1L || Math.round(number * this.rules[winner].getBaseValue()) >= 2L)) {
            ++winner;
        }
        return this.rules[winner];
    }
    
    private static long lcm(final long x, final long y) {
        long x2 = x;
        long y2 = y;
        int p2 = 0;
        while ((x2 & 0x1L) == 0x0L && (y2 & 0x1L) == 0x0L) {
            ++p2;
            x2 >>= 1;
            y2 >>= 1;
        }
        long t;
        if ((x2 & 0x1L) == 0x1L) {
            t = -y2;
        }
        else {
            t = x2;
        }
        while (t != 0L) {
            while ((t & 0x1L) == 0x0L) {
                t >>= 1;
            }
            if (t > 0L) {
                x2 = t;
            }
            else {
                y2 = -t;
            }
            t = x2 - y2;
        }
        final long gcd = x2 << p2;
        return x / gcd * y;
    }
    
    public Number parse(final String text, final ParsePosition parsePosition, final double upperBound, int nonNumericalExecutedRuleMask) {
        final ParsePosition highWaterMark = new ParsePosition(0);
        Number result = NFRule.ZERO;
        if (text.length() == 0) {
            return result;
        }
        for (int nonNumericalRuleIdx = 0; nonNumericalRuleIdx < this.nonNumericalRules.length; ++nonNumericalRuleIdx) {
            final NFRule nonNumericalRule = this.nonNumericalRules[nonNumericalRuleIdx];
            if (nonNumericalRule != null && (nonNumericalExecutedRuleMask >> nonNumericalRuleIdx & 0x1) == 0x0) {
                nonNumericalExecutedRuleMask |= 1 << nonNumericalRuleIdx;
                final Number tempResult = nonNumericalRule.doParse(text, parsePosition, false, upperBound, nonNumericalExecutedRuleMask);
                if (parsePosition.getIndex() > highWaterMark.getIndex()) {
                    result = tempResult;
                    highWaterMark.setIndex(parsePosition.getIndex());
                }
                parsePosition.setIndex(0);
            }
        }
        for (int i = this.rules.length - 1; i >= 0 && highWaterMark.getIndex() < text.length(); --i) {
            if (this.isFractionRuleSet || this.rules[i].getBaseValue() < upperBound) {
                final Number tempResult = this.rules[i].doParse(text, parsePosition, this.isFractionRuleSet, upperBound, nonNumericalExecutedRuleMask);
                if (parsePosition.getIndex() > highWaterMark.getIndex()) {
                    result = tempResult;
                    highWaterMark.setIndex(parsePosition.getIndex());
                }
                parsePosition.setIndex(0);
            }
        }
        parsePosition.setIndex(highWaterMark.getIndex());
        return result;
    }
    
    public void setDecimalFormatSymbols(final DecimalFormatSymbols newSymbols) {
        for (final NFRule rule : this.rules) {
            rule.setDecimalFormatSymbols(newSymbols);
        }
        if (this.fractionRules != null) {
            for (int nonNumericalIdx = 1; nonNumericalIdx <= 3; ++nonNumericalIdx) {
                if (this.nonNumericalRules[nonNumericalIdx] != null) {
                    for (final NFRule rule2 : this.fractionRules) {
                        if (this.nonNumericalRules[nonNumericalIdx].getBaseValue() == rule2.getBaseValue()) {
                            this.setBestFractionRule(nonNumericalIdx, rule2, false);
                        }
                    }
                }
            }
        }
        for (final NFRule rule : this.nonNumericalRules) {
            if (rule != null) {
                rule.setDecimalFormatSymbols(newSymbols);
            }
        }
    }
}
