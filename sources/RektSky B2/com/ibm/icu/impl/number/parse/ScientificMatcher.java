package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.impl.number.*;

public class ScientificMatcher implements NumberParseMatcher
{
    private final String exponentSeparatorString;
    private final DecimalMatcher exponentMatcher;
    private final String customMinusSign;
    private final String customPlusSign;
    
    public static ScientificMatcher getInstance(final DecimalFormatSymbols symbols, final Grouper grouper) {
        return new ScientificMatcher(symbols, grouper);
    }
    
    private ScientificMatcher(final DecimalFormatSymbols symbols, final Grouper grouper) {
        this.exponentSeparatorString = symbols.getExponentSeparator();
        this.exponentMatcher = DecimalMatcher.getInstance(symbols, grouper, 48);
        final String minusSign = symbols.getMinusSignString();
        this.customMinusSign = (ParsingUtils.safeContains(minusSignSet(), minusSign) ? null : minusSign);
        final String plusSign = symbols.getPlusSignString();
        this.customPlusSign = (ParsingUtils.safeContains(plusSignSet(), plusSign) ? null : plusSign);
    }
    
    private static UnicodeSet minusSignSet() {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.MINUS_SIGN);
    }
    
    private static UnicodeSet plusSignSet() {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.PLUS_SIGN);
    }
    
    @Override
    public boolean match(final StringSegment segment, final ParsedNumber result) {
        if (!result.seenNumber()) {
            return false;
        }
        final int overlap1 = segment.getCommonPrefixLength(this.exponentSeparatorString);
        if (overlap1 != this.exponentSeparatorString.length()) {
            return overlap1 == segment.length();
        }
        if (segment.length() == overlap1) {
            return true;
        }
        segment.adjustOffset(overlap1);
        int exponentSign = 1;
        if (segment.startsWith(minusSignSet())) {
            exponentSign = -1;
            segment.adjustOffsetByCodePoint();
        }
        else if (segment.startsWith(plusSignSet())) {
            segment.adjustOffsetByCodePoint();
        }
        else if (segment.startsWith(this.customMinusSign)) {
            final int overlap2 = segment.getCommonPrefixLength(this.customMinusSign);
            if (overlap2 != this.customMinusSign.length()) {
                segment.adjustOffset(-overlap1);
                return true;
            }
            exponentSign = -1;
            segment.adjustOffset(overlap2);
        }
        else if (segment.startsWith(this.customPlusSign)) {
            final int overlap2 = segment.getCommonPrefixLength(this.customPlusSign);
            if (overlap2 != this.customPlusSign.length()) {
                segment.adjustOffset(-overlap1);
                return true;
            }
            segment.adjustOffset(overlap2);
        }
        final boolean wasNull = result.quantity == null;
        if (wasNull) {
            result.quantity = new DecimalQuantity_DualStorageBCD();
        }
        final int digitsOffset = segment.getOffset();
        final boolean digitsReturnValue = this.exponentMatcher.match(segment, result, exponentSign);
        if (wasNull) {
            result.quantity = null;
        }
        if (segment.getOffset() != digitsOffset) {
            result.flags |= 0x8;
        }
        else {
            segment.adjustOffset(-overlap1);
        }
        return digitsReturnValue;
    }
    
    @Override
    public boolean smokeTest(final StringSegment segment) {
        return segment.startsWith(this.exponentSeparatorString);
    }
    
    @Override
    public void postProcess(final ParsedNumber result) {
    }
    
    @Override
    public String toString() {
        return "<ScientificMatcher " + this.exponentSeparatorString + ">";
    }
}
