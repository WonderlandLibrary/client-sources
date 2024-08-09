/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;
import com.ibm.icu.text.PluralFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.text.RbnfLenientScanner;
import com.ibm.icu.text.RuleBasedNumberFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.List;
import java.util.Objects;

final class NFRule {
    static final int NEGATIVE_NUMBER_RULE = -1;
    static final int IMPROPER_FRACTION_RULE = -2;
    static final int PROPER_FRACTION_RULE = -3;
    static final int MASTER_RULE = -4;
    static final int INFINITY_RULE = -5;
    static final int NAN_RULE = -6;
    static final Long ZERO;
    private long baseValue;
    private int radix = 10;
    private short exponent = 0;
    private char decimalPoint = '\u0000';
    private String ruleText = null;
    private PluralFormat rulePatternFormat = null;
    private NFSubstitution sub1 = null;
    private NFSubstitution sub2 = null;
    private final RuleBasedNumberFormat formatter;
    private static final String[] RULE_PREFIXES;
    static final boolean $assertionsDisabled;

    public static void makeRules(String string, NFRuleSet nFRuleSet, NFRule nFRule, RuleBasedNumberFormat ruleBasedNumberFormat, List<NFRule> list) {
        int n;
        NFRule nFRule2 = new NFRule(ruleBasedNumberFormat, string);
        string = nFRule2.ruleText;
        int n2 = string.indexOf(91);
        int n3 = n = n2 < 0 ? -1 : string.indexOf(93);
        if (n < 0 || n2 > n || nFRule2.baseValue == -3L || nFRule2.baseValue == -1L || nFRule2.baseValue == -5L || nFRule2.baseValue == -6L) {
            nFRule2.extractSubstitutions(nFRuleSet, string, nFRule);
        } else {
            NFRule nFRule3 = null;
            StringBuilder stringBuilder = new StringBuilder();
            if (nFRule2.baseValue > 0L && nFRule2.baseValue % NFRule.power(nFRule2.radix, nFRule2.exponent) == 0L || nFRule2.baseValue == -2L || nFRule2.baseValue == -4L) {
                nFRule3 = new NFRule(ruleBasedNumberFormat, null);
                if (nFRule2.baseValue >= 0L) {
                    nFRule3.baseValue = nFRule2.baseValue++;
                    if (!nFRuleSet.isFractionSet()) {
                        // empty if block
                    }
                } else if (nFRule2.baseValue == -2L) {
                    nFRule3.baseValue = -3L;
                } else if (nFRule2.baseValue == -4L) {
                    nFRule3.baseValue = nFRule2.baseValue;
                    nFRule2.baseValue = -2L;
                }
                nFRule3.radix = nFRule2.radix;
                nFRule3.exponent = nFRule2.exponent;
                stringBuilder.append(string.substring(0, n2));
                if (n + 1 < string.length()) {
                    stringBuilder.append(string.substring(n + 1));
                }
                nFRule3.extractSubstitutions(nFRuleSet, stringBuilder.toString(), nFRule);
            }
            stringBuilder.setLength(0);
            stringBuilder.append(string.substring(0, n2));
            stringBuilder.append(string.substring(n2 + 1, n));
            if (n + 1 < string.length()) {
                stringBuilder.append(string.substring(n + 1));
            }
            nFRule2.extractSubstitutions(nFRuleSet, stringBuilder.toString(), nFRule);
            if (nFRule3 != null) {
                if (nFRule3.baseValue >= 0L) {
                    list.add(nFRule3);
                } else {
                    nFRuleSet.setNonNumericalRule(nFRule3);
                }
            }
        }
        if (nFRule2.baseValue >= 0L) {
            list.add(nFRule2);
        } else {
            nFRuleSet.setNonNumericalRule(nFRule2);
        }
    }

    public NFRule(RuleBasedNumberFormat ruleBasedNumberFormat, String string) {
        this.formatter = ruleBasedNumberFormat;
        this.ruleText = string == null ? null : this.parseRuleDescriptor(string);
    }

    private String parseRuleDescriptor(String string) {
        int n = string.indexOf(":");
        if (n != -1) {
            String string2 = string.substring(0, n);
            ++n;
            while (n < string.length() && PatternProps.isWhiteSpace(string.charAt(n))) {
                ++n;
            }
            string = string.substring(n);
            int n2 = string2.length();
            char c = string2.charAt(0);
            char c2 = string2.charAt(n2 - 1);
            if (c >= '0' && c <= '9' && c2 != 'x') {
                long l = 0L;
                char c3 = '\u0000';
                for (n = 0; n < n2; ++n) {
                    c3 = string2.charAt(n);
                    if (c3 >= '0' && c3 <= '9') {
                        l = l * 10L + (long)(c3 - 48);
                        continue;
                    }
                    if (c3 == '/' || c3 == '>') break;
                    if (PatternProps.isWhiteSpace(c3) || c3 == ',' || c3 == '.') continue;
                    throw new IllegalArgumentException("Illegal character " + c3 + " in rule descriptor");
                }
                this.setBaseValue(l);
                if (c3 == '/') {
                    l = 0L;
                    ++n;
                    while (n < n2) {
                        c3 = string2.charAt(n);
                        if (c3 >= '0' && c3 <= '9') {
                            l = l * 10L + (long)(c3 - 48);
                        } else {
                            if (c3 == '>') break;
                            if (!PatternProps.isWhiteSpace(c3) && c3 != ',' && c3 != '.') {
                                throw new IllegalArgumentException("Illegal character " + c3 + " in rule descriptor");
                            }
                        }
                        ++n;
                    }
                    this.radix = (int)l;
                    if (this.radix == 0) {
                        throw new IllegalArgumentException("Rule can't have radix of 0");
                    }
                    this.exponent = this.expectedExponent();
                }
                if (c3 == '>') {
                    while (n < n2) {
                        c3 = string2.charAt(n);
                        if (c3 != '>' || this.exponent <= 0) {
                            throw new IllegalArgumentException("Illegal character in rule descriptor");
                        }
                        this.exponent = (short)(this.exponent - 1);
                        ++n;
                    }
                }
            } else if (string2.equals("-x")) {
                this.setBaseValue(-1L);
            } else if (n2 == 3) {
                if (c == '0' && c2 == 'x') {
                    this.setBaseValue(-3L);
                    this.decimalPoint = string2.charAt(1);
                } else if (c == 'x' && c2 == 'x') {
                    this.setBaseValue(-2L);
                    this.decimalPoint = string2.charAt(1);
                } else if (c == 'x' && c2 == '0') {
                    this.setBaseValue(-4L);
                    this.decimalPoint = string2.charAt(1);
                } else if (string2.equals("NaN")) {
                    this.setBaseValue(-6L);
                } else if (string2.equals("Inf")) {
                    this.setBaseValue(-5L);
                }
            }
        }
        if (string.length() > 0 && string.charAt(0) == '\'') {
            string = string.substring(1);
        }
        return string;
    }

    private void extractSubstitutions(NFRuleSet nFRuleSet, String string, NFRule nFRule) {
        int n;
        this.ruleText = string;
        this.sub1 = this.extractSubstitution(nFRuleSet, nFRule);
        this.sub2 = this.sub1 == null ? null : this.extractSubstitution(nFRuleSet, nFRule);
        string = this.ruleText;
        int n2 = string.indexOf("$(");
        int n3 = n = n2 >= 0 ? string.indexOf(")$", n2) : -1;
        if (n >= 0) {
            PluralRules.PluralType pluralType;
            int n4 = string.indexOf(44, n2);
            if (n4 < 0) {
                throw new IllegalArgumentException("Rule \"" + string + "\" does not have a defined type");
            }
            String string2 = this.ruleText.substring(n2 + 2, n4);
            if ("cardinal".equals(string2)) {
                pluralType = PluralRules.PluralType.CARDINAL;
            } else if ("ordinal".equals(string2)) {
                pluralType = PluralRules.PluralType.ORDINAL;
            } else {
                throw new IllegalArgumentException(string2 + " is an unknown type");
            }
            this.rulePatternFormat = this.formatter.createPluralFormat(pluralType, string.substring(n4 + 1, n));
        }
    }

    private NFSubstitution extractSubstitution(NFRuleSet nFRuleSet, NFRule nFRule) {
        int n;
        int n2 = NFRule.indexOfAnyRulePrefix(this.ruleText);
        if (n2 == -1) {
            return null;
        }
        if (this.ruleText.startsWith(">>>", n2)) {
            n = n2 + 2;
        } else {
            char c = this.ruleText.charAt(n2);
            n = this.ruleText.indexOf(c, n2 + 1);
            if (c == '<' && n != -1 && n < this.ruleText.length() - 1 && this.ruleText.charAt(n + 1) == c) {
                ++n;
            }
        }
        if (n == -1) {
            return null;
        }
        NFSubstitution nFSubstitution = NFSubstitution.makeSubstitution(n2, this, nFRule, nFRuleSet, this.formatter, this.ruleText.substring(n2, n + 1));
        this.ruleText = this.ruleText.substring(0, n2) + this.ruleText.substring(n + 1);
        return nFSubstitution;
    }

    final void setBaseValue(long l) {
        this.baseValue = l;
        this.radix = 10;
        if (this.baseValue >= 1L) {
            this.exponent = this.expectedExponent();
            if (this.sub1 != null) {
                this.sub1.setDivisor(this.radix, this.exponent);
            }
            if (this.sub2 != null) {
                this.sub2.setDivisor(this.radix, this.exponent);
            }
        } else {
            this.exponent = 0;
        }
    }

    private short expectedExponent() {
        if (this.radix == 0 || this.baseValue < 1L) {
            return 1;
        }
        short s = (short)(Math.log(this.baseValue) / Math.log(this.radix));
        if (NFRule.power(this.radix, (short)(s + 1)) <= this.baseValue) {
            return (short)(s + 1);
        }
        return s;
    }

    private static int indexOfAnyRulePrefix(String string) {
        int n = -1;
        if (string.length() > 0) {
            for (String string2 : RULE_PREFIXES) {
                int n2 = string.indexOf(string2);
                if (n2 == -1 || n != -1 && n2 >= n) continue;
                n = n2;
            }
        }
        return n;
    }

    public boolean equals(Object object) {
        if (object instanceof NFRule) {
            NFRule nFRule = (NFRule)object;
            return this.baseValue == nFRule.baseValue && this.radix == nFRule.radix && this.exponent == nFRule.exponent && this.ruleText.equals(nFRule.ruleText) && Objects.equals(this.sub1, nFRule.sub1) && Objects.equals(this.sub2, nFRule.sub2);
        }
        return true;
    }

    public int hashCode() {
        if (!$assertionsDisabled) {
            throw new AssertionError((Object)"hashCode not designed");
        }
        return 1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.baseValue == -1L) {
            stringBuilder.append("-x: ");
        } else if (this.baseValue == -2L) {
            stringBuilder.append('x').append(this.decimalPoint == '\u0000' ? (char)'.' : (char)this.decimalPoint).append("x: ");
        } else if (this.baseValue == -3L) {
            stringBuilder.append('0').append(this.decimalPoint == '\u0000' ? (char)'.' : (char)this.decimalPoint).append("x: ");
        } else if (this.baseValue == -4L) {
            stringBuilder.append('x').append(this.decimalPoint == '\u0000' ? (char)'.' : (char)this.decimalPoint).append("0: ");
        } else if (this.baseValue == -5L) {
            stringBuilder.append("Inf: ");
        } else if (this.baseValue == -6L) {
            stringBuilder.append("NaN: ");
        } else {
            stringBuilder.append(String.valueOf(this.baseValue));
            if (this.radix != 10) {
                stringBuilder.append('/').append(this.radix);
            }
            int n = this.expectedExponent() - this.exponent;
            for (int i = 0; i < n; ++i) {
                stringBuilder.append('>');
            }
            stringBuilder.append(": ");
        }
        if (this.ruleText.startsWith(" ") && (this.sub1 == null || this.sub1.getPos() != 0)) {
            stringBuilder.append('\'');
        }
        StringBuilder stringBuilder2 = new StringBuilder(this.ruleText);
        if (this.sub2 != null) {
            stringBuilder2.insert(this.sub2.getPos(), this.sub2.toString());
        }
        if (this.sub1 != null) {
            stringBuilder2.insert(this.sub1.getPos(), this.sub1.toString());
        }
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(';');
        return stringBuilder.toString();
    }

    public final char getDecimalPoint() {
        return this.decimalPoint;
    }

    public final long getBaseValue() {
        return this.baseValue;
    }

    public long getDivisor() {
        return NFRule.power(this.radix, this.exponent);
    }

    public void doFormat(long l, StringBuilder stringBuilder, int n, int n2) {
        int n3 = this.ruleText.length();
        int n4 = 0;
        if (this.rulePatternFormat == null) {
            stringBuilder.insert(n, this.ruleText);
        } else {
            n3 = this.ruleText.indexOf("$(");
            int n5 = this.ruleText.indexOf(")$", n3);
            int n6 = stringBuilder.length();
            if (n5 < this.ruleText.length() - 1) {
                stringBuilder.insert(n, this.ruleText.substring(n5 + 2));
            }
            stringBuilder.insert(n, this.rulePatternFormat.format(l / NFRule.power(this.radix, this.exponent)));
            if (n3 > 0) {
                stringBuilder.insert(n, this.ruleText.substring(0, n3));
            }
            n4 = this.ruleText.length() - (stringBuilder.length() - n6);
        }
        if (this.sub2 != null) {
            this.sub2.doSubstitution(l, stringBuilder, n - (this.sub2.getPos() > n3 ? n4 : 0), n2);
        }
        if (this.sub1 != null) {
            this.sub1.doSubstitution(l, stringBuilder, n - (this.sub1.getPos() > n3 ? n4 : 0), n2);
        }
    }

    public void doFormat(double d, StringBuilder stringBuilder, int n, int n2) {
        int n3 = this.ruleText.length();
        int n4 = 0;
        if (this.rulePatternFormat == null) {
            stringBuilder.insert(n, this.ruleText);
        } else {
            double d2;
            n3 = this.ruleText.indexOf("$(");
            int n5 = this.ruleText.indexOf(")$", n3);
            int n6 = stringBuilder.length();
            if (n5 < this.ruleText.length() - 1) {
                stringBuilder.insert(n, this.ruleText.substring(n5 + 2));
            }
            d2 = 0.0 <= (d2 = d) && d2 < 1.0 ? (double)Math.round(d2 * (double)NFRule.power(this.radix, this.exponent)) : (d2 /= (double)NFRule.power(this.radix, this.exponent));
            stringBuilder.insert(n, this.rulePatternFormat.format((long)d2));
            if (n3 > 0) {
                stringBuilder.insert(n, this.ruleText.substring(0, n3));
            }
            n4 = this.ruleText.length() - (stringBuilder.length() - n6);
        }
        if (this.sub2 != null) {
            this.sub2.doSubstitution(d, stringBuilder, n - (this.sub2.getPos() > n3 ? n4 : 0), n2);
        }
        if (this.sub1 != null) {
            this.sub1.doSubstitution(d, stringBuilder, n - (this.sub1.getPos() > n3 ? n4 : 0), n2);
        }
    }

    static long power(long l, short s) {
        if (s < 0) {
            throw new IllegalArgumentException("Exponent can not be negative");
        }
        if (l < 0L) {
            throw new IllegalArgumentException("Base can not be negative");
        }
        long l2 = 1L;
        while (s > 0) {
            if ((s & 1) == 1) {
                l2 *= l;
            }
            l *= l;
            s = (short)(s >> 1);
        }
        return l2;
    }

    public boolean shouldRollBack(long l) {
        if (!(this.sub1 != null && this.sub1.isModulusSubstitution() || this.sub2 != null && this.sub2.isModulusSubstitution())) {
            return true;
        }
        long l2 = NFRule.power(this.radix, this.exponent);
        return l % l2 == 0L && this.baseValue % l2 != 0L;
    }

    public Number doParse(String string, ParsePosition parsePosition, boolean bl, double d, int n) {
        ParsePosition parsePosition2 = new ParsePosition(0);
        int n2 = this.sub1 != null ? this.sub1.getPos() : this.ruleText.length();
        int n3 = this.sub2 != null ? this.sub2.getPos() : this.ruleText.length();
        String string2 = this.stripPrefix(string, this.ruleText.substring(0, n2), parsePosition2);
        int n4 = string.length() - string2.length();
        if (parsePosition2.getIndex() == 0 && n2 != 0) {
            return ZERO;
        }
        if (this.baseValue == -5L) {
            parsePosition.setIndex(parsePosition2.getIndex());
            return Double.POSITIVE_INFINITY;
        }
        if (this.baseValue == -6L) {
            parsePosition.setIndex(parsePosition2.getIndex());
            return Double.NaN;
        }
        int n5 = 0;
        double d2 = 0.0;
        int n6 = 0;
        double d3 = Math.max(0L, this.baseValue);
        do {
            parsePosition2.setIndex(0);
            double d4 = this.matchToDelimiter(string2, n6, d3, this.ruleText.substring(n2, n3), this.rulePatternFormat, parsePosition2, this.sub1, d, n).doubleValue();
            if (parsePosition2.getIndex() == 0 && this.sub1 != null) continue;
            n6 = parsePosition2.getIndex();
            String string3 = string2.substring(parsePosition2.getIndex());
            ParsePosition parsePosition3 = new ParsePosition(0);
            d4 = this.matchToDelimiter(string3, 0, d4, this.ruleText.substring(n3), this.rulePatternFormat, parsePosition3, this.sub2, d, n).doubleValue();
            if (parsePosition3.getIndex() == 0 && this.sub2 != null || n4 + parsePosition2.getIndex() + parsePosition3.getIndex() <= n5) continue;
            n5 = n4 + parsePosition2.getIndex() + parsePosition3.getIndex();
            d2 = d4;
        } while (n2 != n3 && parsePosition2.getIndex() > 0 && parsePosition2.getIndex() < string2.length() && parsePosition2.getIndex() != n6);
        parsePosition.setIndex(n5);
        if (bl && n5 > 0 && this.sub1 == null) {
            d2 = 1.0 / d2;
        }
        if (d2 == (double)((long)d2)) {
            return (long)d2;
        }
        return new Double(d2);
    }

    private String stripPrefix(String string, String string2, ParsePosition parsePosition) {
        if (string2.length() == 0) {
            return string;
        }
        int n = this.prefixLength(string, string2);
        if (n != 0) {
            parsePosition.setIndex(parsePosition.getIndex() + n);
            return string.substring(n);
        }
        return string;
    }

    private Number matchToDelimiter(String string, int n, double d, String string2, PluralFormat pluralFormat, ParsePosition parsePosition, NFSubstitution nFSubstitution, double d2, int n2) {
        if (!this.allIgnorable(string2)) {
            ParsePosition parsePosition2 = new ParsePosition(0);
            int[] nArray = this.findText(string, string2, pluralFormat, n);
            int n3 = nArray[0];
            int n4 = nArray[1];
            while (n3 >= 0) {
                String string3 = string.substring(0, n3);
                if (string3.length() > 0) {
                    Number number = nFSubstitution.doParse(string3, parsePosition2, d, d2, this.formatter.lenientParseEnabled(), n2);
                    if (parsePosition2.getIndex() == n3) {
                        parsePosition.setIndex(n3 + n4);
                        return number;
                    }
                }
                parsePosition2.setIndex(0);
                nArray = this.findText(string, string2, pluralFormat, n3 + n4);
                n3 = nArray[0];
                n4 = nArray[1];
            }
            parsePosition.setIndex(0);
            return ZERO;
        }
        if (nFSubstitution == null) {
            return d;
        }
        ParsePosition parsePosition3 = new ParsePosition(0);
        Number number = ZERO;
        Number number2 = nFSubstitution.doParse(string, parsePosition3, d, d2, this.formatter.lenientParseEnabled(), n2);
        if (parsePosition3.getIndex() != 0) {
            parsePosition.setIndex(parsePosition3.getIndex());
            if (number2 != null) {
                number = number2;
            }
        }
        return number;
    }

    private int prefixLength(String string, String string2) {
        if (string2.length() == 0) {
            return 1;
        }
        RbnfLenientScanner rbnfLenientScanner = this.formatter.getLenientScanner();
        if (rbnfLenientScanner != null) {
            return rbnfLenientScanner.prefixLength(string, string2);
        }
        if (string.startsWith(string2)) {
            return string2.length();
        }
        return 1;
    }

    private int[] findText(String string, String string2, PluralFormat pluralFormat, int n) {
        RbnfLenientScanner rbnfLenientScanner = this.formatter.getLenientScanner();
        if (pluralFormat != null) {
            FieldPosition fieldPosition = new FieldPosition(0);
            fieldPosition.setBeginIndex(n);
            pluralFormat.parseType(string, rbnfLenientScanner, fieldPosition);
            int n2 = fieldPosition.getBeginIndex();
            if (n2 >= 0) {
                int n3 = this.ruleText.indexOf("$(");
                int n4 = this.ruleText.indexOf(")$", n3) + 2;
                int n5 = fieldPosition.getEndIndex() - n2;
                String string3 = this.ruleText.substring(0, n3);
                String string4 = this.ruleText.substring(n4);
                if (string.regionMatches(n2 - string3.length(), string3, 0, string3.length()) && string.regionMatches(n2 + n5, string4, 0, string4.length())) {
                    return new int[]{n2 - string3.length(), n5 + string3.length() + string4.length()};
                }
            }
            return new int[]{-1, 0};
        }
        if (rbnfLenientScanner != null) {
            return rbnfLenientScanner.findText(string, string2, n);
        }
        return new int[]{string.indexOf(string2, n), string2.length()};
    }

    private boolean allIgnorable(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }
        RbnfLenientScanner rbnfLenientScanner = this.formatter.getLenientScanner();
        return rbnfLenientScanner != null && rbnfLenientScanner.allIgnorable(string);
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        if (this.sub1 != null) {
            this.sub1.setDecimalFormatSymbols(decimalFormatSymbols);
        }
        if (this.sub2 != null) {
            this.sub2.setDecimalFormatSymbols(decimalFormatSymbols);
        }
    }

    static {
        $assertionsDisabled = !NFRule.class.desiredAssertionStatus();
        ZERO = 0L;
        RULE_PREFIXES = new String[]{"<<", "<%", "<#", "<0", ">>", ">%", ">#", ">0", "=%", "=#", "=0"};
    }
}

