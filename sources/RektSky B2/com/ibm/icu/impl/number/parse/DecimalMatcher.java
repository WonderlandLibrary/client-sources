package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.lang.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.impl.number.*;

public class DecimalMatcher implements NumberParseMatcher
{
    private final boolean requireGroupingMatch;
    private final boolean groupingDisabled;
    private final boolean integerOnly;
    private final int grouping1;
    private final int grouping2;
    private final String groupingSeparator;
    private final String decimalSeparator;
    private final UnicodeSet groupingUniSet;
    private final UnicodeSet decimalUniSet;
    private final UnicodeSet separatorSet;
    private final UnicodeSet leadSet;
    private final String[] digitStrings;
    static final /* synthetic */ boolean $assertionsDisabled;
    
    public static DecimalMatcher getInstance(final DecimalFormatSymbols symbols, final Grouper grouper, final int parseFlags) {
        return new DecimalMatcher(symbols, grouper, parseFlags);
    }
    
    private DecimalMatcher(final DecimalFormatSymbols symbols, final Grouper grouper, final int parseFlags) {
        if (0x0 != (parseFlags & 0x2)) {
            this.groupingSeparator = symbols.getMonetaryGroupingSeparatorString();
            this.decimalSeparator = symbols.getMonetaryDecimalSeparatorString();
        }
        else {
            this.groupingSeparator = symbols.getGroupingSeparatorString();
            this.decimalSeparator = symbols.getDecimalSeparatorString();
        }
        final boolean strictSeparators = 0x0 != (parseFlags & 0x4);
        final StaticUnicodeSets.Key groupingKey = strictSeparators ? StaticUnicodeSets.Key.STRICT_ALL_SEPARATORS : StaticUnicodeSets.Key.ALL_SEPARATORS;
        this.groupingUniSet = StaticUnicodeSets.get(groupingKey);
        final StaticUnicodeSets.Key decimalKey = StaticUnicodeSets.chooseFrom(this.decimalSeparator, strictSeparators ? StaticUnicodeSets.Key.STRICT_COMMA : StaticUnicodeSets.Key.COMMA, strictSeparators ? StaticUnicodeSets.Key.STRICT_PERIOD : StaticUnicodeSets.Key.PERIOD);
        if (decimalKey != null) {
            this.decimalUniSet = StaticUnicodeSets.get(decimalKey);
        }
        else if (!this.decimalSeparator.isEmpty()) {
            this.decimalUniSet = new UnicodeSet().add(this.decimalSeparator.codePointAt(0)).freeze();
        }
        else {
            this.decimalUniSet = UnicodeSet.EMPTY;
        }
        if (groupingKey != null && decimalKey != null) {
            this.separatorSet = this.groupingUniSet;
            this.leadSet = StaticUnicodeSets.get(strictSeparators ? StaticUnicodeSets.Key.DIGITS_OR_ALL_SEPARATORS : StaticUnicodeSets.Key.DIGITS_OR_STRICT_ALL_SEPARATORS);
        }
        else {
            this.separatorSet = new UnicodeSet().addAll(this.groupingUniSet).addAll(this.decimalUniSet).freeze();
            this.leadSet = null;
        }
        final int cpZero = symbols.getCodePointZero();
        if (cpZero == -1 || !UCharacter.isDigit(cpZero) || UCharacter.digit(cpZero) != 0) {
            this.digitStrings = symbols.getDigitStringsLocal();
        }
        else {
            this.digitStrings = null;
        }
        this.requireGroupingMatch = (0x0 != (parseFlags & 0x8));
        this.groupingDisabled = (0x0 != (parseFlags & 0x20));
        this.integerOnly = (0x0 != (parseFlags & 0x10));
        this.grouping1 = grouper.getPrimary();
        this.grouping2 = grouper.getSecondary();
    }
    
    @Override
    public boolean match(final StringSegment segment, final ParsedNumber result) {
        return this.match(segment, result, 0);
    }
    
    public boolean match(final StringSegment segment, final ParsedNumber result, final int exponentSign) {
        if (result.seenNumber() && exponentSign == 0) {
            return false;
        }
        if (exponentSign != 0 && !DecimalMatcher.$assertionsDisabled && result.quantity == null) {
            throw new AssertionError();
        }
        final int initialOffset = segment.getOffset();
        boolean maybeMore = false;
        DecimalQuantity_DualStorageBCD digitsConsumed = null;
        int digitsAfterDecimalPlace = 0;
        String actualGroupingString = null;
        String actualDecimalString = null;
        int currGroupOffset = 0;
        int currGroupSepType = 0;
        int currGroupCount = 0;
        int prevGroupOffset = -1;
        int prevGroupSepType = -1;
        int prevGroupCount = -1;
        while (segment.length() > 0) {
            maybeMore = false;
            byte digit = -1;
            final int cp = segment.getCodePoint();
            if (UCharacter.isDigit(cp)) {
                segment.adjustOffset(Character.charCount(cp));
                digit = (byte)UCharacter.digit(cp);
            }
            if (digit == -1 && this.digitStrings != null) {
                for (int i = 0; i < this.digitStrings.length; ++i) {
                    final String str = this.digitStrings[i];
                    if (!str.isEmpty()) {
                        final int overlap = segment.getCommonPrefixLength(str);
                        if (overlap == str.length()) {
                            segment.adjustOffset(overlap);
                            digit = (byte)i;
                            break;
                        }
                        maybeMore = (maybeMore || overlap == segment.length());
                    }
                }
            }
            if (digit >= 0) {
                if (digitsConsumed == null) {
                    digitsConsumed = new DecimalQuantity_DualStorageBCD();
                }
                digitsConsumed.appendDigit(digit, 0, true);
                ++currGroupCount;
                if (actualDecimalString == null) {
                    continue;
                }
                ++digitsAfterDecimalPlace;
            }
            else {
                boolean isDecimal = false;
                boolean isGrouping = false;
                if (actualDecimalString == null && !this.decimalSeparator.isEmpty()) {
                    final int overlap = segment.getCommonPrefixLength(this.decimalSeparator);
                    maybeMore = (maybeMore || overlap == segment.length());
                    if (overlap == this.decimalSeparator.length()) {
                        isDecimal = true;
                        actualDecimalString = this.decimalSeparator;
                    }
                }
                if (actualGroupingString != null) {
                    final int overlap = segment.getCommonPrefixLength(actualGroupingString);
                    maybeMore = (maybeMore || overlap == segment.length());
                    if (overlap == actualGroupingString.length()) {
                        isGrouping = true;
                    }
                }
                if (!this.groupingDisabled && actualGroupingString == null && actualDecimalString == null && !this.groupingSeparator.isEmpty()) {
                    final int overlap = segment.getCommonPrefixLength(this.groupingSeparator);
                    maybeMore = (maybeMore || overlap == segment.length());
                    if (overlap == this.groupingSeparator.length()) {
                        isGrouping = true;
                        actualGroupingString = this.groupingSeparator;
                    }
                }
                if (!isGrouping && actualDecimalString == null && this.decimalUniSet.contains(cp)) {
                    isDecimal = true;
                    actualDecimalString = UCharacter.toString(cp);
                }
                if (!this.groupingDisabled && actualGroupingString == null && actualDecimalString == null && this.groupingUniSet.contains(cp)) {
                    isGrouping = true;
                    actualGroupingString = UCharacter.toString(cp);
                }
                if (!isDecimal && !isGrouping) {
                    break;
                }
                if (isDecimal && this.integerOnly) {
                    break;
                }
                if (currGroupSepType == 2 && isGrouping) {
                    break;
                }
                final boolean prevValidSecondary = this.validateGroup(prevGroupSepType, prevGroupCount, false);
                final boolean currValidPrimary = this.validateGroup(currGroupSepType, currGroupCount, true);
                if (!prevValidSecondary || (isDecimal && !currValidPrimary)) {
                    if (isGrouping && currGroupCount == 0) {
                        assert currGroupSepType == 1;
                        break;
                    }
                    else {
                        if (this.requireGroupingMatch) {
                            digitsConsumed = null;
                            break;
                        }
                        break;
                    }
                }
                else {
                    if (this.requireGroupingMatch && currGroupCount == 0 && currGroupSepType == 1) {
                        break;
                    }
                    prevGroupOffset = currGroupOffset;
                    prevGroupCount = currGroupCount;
                    if (isDecimal) {
                        prevGroupSepType = -1;
                    }
                    else {
                        prevGroupSepType = currGroupSepType;
                    }
                    if (currGroupCount != 0) {
                        currGroupOffset = segment.getOffset();
                    }
                    currGroupSepType = (isGrouping ? 1 : 2);
                    currGroupCount = 0;
                    if (isGrouping) {
                        segment.adjustOffset(actualGroupingString.length());
                    }
                    else {
                        segment.adjustOffset(actualDecimalString.length());
                    }
                }
            }
        }
        if (currGroupSepType != 2 && currGroupCount == 0) {
            maybeMore = true;
            segment.setOffset(currGroupOffset);
            currGroupOffset = prevGroupOffset;
            currGroupSepType = prevGroupSepType;
            currGroupCount = prevGroupCount;
            prevGroupOffset = -1;
            prevGroupSepType = 0;
            prevGroupCount = 1;
        }
        boolean prevValidSecondary2 = this.validateGroup(prevGroupSepType, prevGroupCount, false);
        boolean currValidPrimary2 = this.validateGroup(currGroupSepType, currGroupCount, true);
        if (!this.requireGroupingMatch) {
            int digitsToRemove = 0;
            if (!prevValidSecondary2) {
                segment.setOffset(prevGroupOffset);
                digitsToRemove += prevGroupCount;
                digitsToRemove += currGroupCount;
            }
            else if (!currValidPrimary2 && (prevGroupSepType != 0 || prevGroupCount != 0)) {
                maybeMore = true;
                segment.setOffset(currGroupOffset);
                digitsToRemove += currGroupCount;
            }
            if (digitsToRemove != 0) {
                digitsConsumed.adjustMagnitude(-digitsToRemove);
                digitsConsumed.truncate();
            }
            prevValidSecondary2 = true;
            currValidPrimary2 = true;
        }
        if (currGroupSepType != 2 && (!prevValidSecondary2 || !currValidPrimary2)) {
            digitsConsumed = null;
        }
        if (digitsConsumed == null) {
            maybeMore = (maybeMore || segment.length() == 0);
            segment.setOffset(initialOffset);
            return maybeMore;
        }
        digitsConsumed.adjustMagnitude(-digitsAfterDecimalPlace);
        if (exponentSign != 0 && segment.getOffset() != initialOffset) {
            boolean overflow = false;
            if (digitsConsumed.fitsInLong()) {
                final long exponentLong = digitsConsumed.toLong(false);
                assert exponentLong >= 0L;
                if (exponentLong <= 2147483647L) {
                    final int exponentInt = (int)exponentLong;
                    try {
                        result.quantity.adjustMagnitude(exponentSign * exponentInt);
                    }
                    catch (ArithmeticException e) {
                        overflow = true;
                    }
                }
                else {
                    overflow = true;
                }
            }
            else {
                overflow = true;
            }
            if (overflow) {
                if (exponentSign == -1) {
                    result.quantity.clear();
                }
                else {
                    result.quantity = null;
                    result.flags |= 0x80;
                }
            }
        }
        else {
            result.quantity = digitsConsumed;
        }
        if (actualDecimalString != null) {
            result.flags |= 0x20;
        }
        result.setCharsConsumed(segment);
        return segment.length() == 0 || maybeMore;
    }
    
    private boolean validateGroup(final int sepType, final int count, final boolean isPrimary) {
        if (!this.requireGroupingMatch) {
            return sepType != 1 || count != 1;
        }
        if (sepType == -1) {
            return true;
        }
        if (sepType == 0) {
            return isPrimary || (count != 0 && count <= this.grouping2);
        }
        if (sepType == 1) {
            if (isPrimary) {
                return count == this.grouping1;
            }
            return count == this.grouping2;
        }
        else {
            assert sepType == 2;
            return true;
        }
    }
    
    @Override
    public boolean smokeTest(final StringSegment segment) {
        if (this.digitStrings == null && this.leadSet != null) {
            return segment.startsWith(this.leadSet);
        }
        if (segment.startsWith(this.separatorSet) || UCharacter.isDigit(segment.getCodePoint())) {
            return true;
        }
        if (this.digitStrings == null) {
            return false;
        }
        for (int i = 0; i < this.digitStrings.length; ++i) {
            if (segment.startsWith(this.digitStrings[i])) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void postProcess(final ParsedNumber result) {
    }
    
    @Override
    public String toString() {
        return "<DecimalMatcher>";
    }
}
