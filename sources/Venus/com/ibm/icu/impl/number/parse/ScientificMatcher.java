/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.Grouper;
import com.ibm.icu.impl.number.parse.DecimalMatcher;
import com.ibm.icu.impl.number.parse.IgnorablesMatcher;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.ParsingUtils;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.UnicodeSet;

public class ScientificMatcher
implements NumberParseMatcher {
    private final String exponentSeparatorString;
    private final DecimalMatcher exponentMatcher;
    private final IgnorablesMatcher ignorablesMatcher;
    private final String customMinusSign;
    private final String customPlusSign;

    public static ScientificMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper) {
        return new ScientificMatcher(decimalFormatSymbols, grouper);
    }

    private ScientificMatcher(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper) {
        this.exponentSeparatorString = decimalFormatSymbols.getExponentSeparator();
        this.exponentMatcher = DecimalMatcher.getInstance(decimalFormatSymbols, grouper, 48);
        this.ignorablesMatcher = IgnorablesMatcher.getInstance(32768);
        String string = decimalFormatSymbols.getMinusSignString();
        this.customMinusSign = ParsingUtils.safeContains(ScientificMatcher.minusSignSet(), string) ? null : string;
        String string2 = decimalFormatSymbols.getPlusSignString();
        this.customPlusSign = ParsingUtils.safeContains(ScientificMatcher.plusSignSet(), string2) ? null : string2;
    }

    private static UnicodeSet minusSignSet() {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.MINUS_SIGN);
    }

    private static UnicodeSet plusSignSet() {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.PLUS_SIGN);
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (!parsedNumber.seenNumber()) {
            return true;
        }
        if (0 != (parsedNumber.flags & 8)) {
            return true;
        }
        int n = stringSegment.getOffset();
        int n2 = stringSegment.getCommonPrefixLength(this.exponentSeparatorString);
        if (n2 == this.exponentSeparatorString.length()) {
            boolean bl;
            if (stringSegment.length() == n2) {
                return false;
            }
            stringSegment.adjustOffset(n2);
            this.ignorablesMatcher.match(stringSegment, null);
            if (stringSegment.length() == 0) {
                stringSegment.setOffset(n);
                return false;
            }
            int n3 = 1;
            if (stringSegment.startsWith(ScientificMatcher.minusSignSet())) {
                n3 = -1;
                stringSegment.adjustOffsetByCodePoint();
            } else if (stringSegment.startsWith(ScientificMatcher.plusSignSet())) {
                stringSegment.adjustOffsetByCodePoint();
            } else if (stringSegment.startsWith(this.customMinusSign)) {
                n2 = stringSegment.getCommonPrefixLength(this.customMinusSign);
                if (n2 != this.customMinusSign.length()) {
                    stringSegment.setOffset(n);
                    return false;
                }
                n3 = -1;
                stringSegment.adjustOffset(n2);
            } else if (stringSegment.startsWith(this.customPlusSign)) {
                n2 = stringSegment.getCommonPrefixLength(this.customPlusSign);
                if (n2 != this.customPlusSign.length()) {
                    stringSegment.setOffset(n);
                    return false;
                }
                stringSegment.adjustOffset(n2);
            }
            if (stringSegment.length() == 0) {
                stringSegment.setOffset(n);
                return false;
            }
            this.ignorablesMatcher.match(stringSegment, null);
            if (stringSegment.length() == 0) {
                stringSegment.setOffset(n);
                return false;
            }
            boolean bl2 = bl = parsedNumber.quantity == null;
            if (bl) {
                parsedNumber.quantity = new DecimalQuantity_DualStorageBCD();
            }
            int n4 = stringSegment.getOffset();
            boolean bl3 = this.exponentMatcher.match(stringSegment, parsedNumber, n3);
            if (bl) {
                parsedNumber.quantity = null;
            }
            if (stringSegment.getOffset() != n4) {
                parsedNumber.flags |= 8;
            } else {
                stringSegment.setOffset(n);
            }
            return bl3;
        }
        return n2 != stringSegment.length();
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return stringSegment.startsWith(this.exponentSeparatorString);
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    public String toString() {
        return "<ScientificMatcher " + this.exponentSeparatorString + ">";
    }
}

