package com.ibm.icu.impl.number;

import java.math.*;
import com.ibm.icu.text.*;
import com.ibm.icu.number.*;
import com.ibm.icu.impl.*;

public class PatternStringUtils
{
    public static String propertiesToPatternString(final DecimalFormatProperties properties) {
        final StringBuilder sb = new StringBuilder();
        final int dosMax = 100;
        final int groupingSize = Math.min(properties.getSecondaryGroupingSize(), dosMax);
        final int firstGroupingSize = Math.min(properties.getGroupingSize(), dosMax);
        final int paddingWidth = Math.min(properties.getFormatWidth(), dosMax);
        final Padder.PadPosition paddingLocation = properties.getPadPosition();
        final String paddingString = properties.getPadString();
        final int minInt = Math.max(Math.min(properties.getMinimumIntegerDigits(), dosMax), 0);
        final int maxInt = Math.min(properties.getMaximumIntegerDigits(), dosMax);
        final int minFrac = Math.max(Math.min(properties.getMinimumFractionDigits(), dosMax), 0);
        final int maxFrac = Math.min(properties.getMaximumFractionDigits(), dosMax);
        final int minSig = Math.min(properties.getMinimumSignificantDigits(), dosMax);
        final int maxSig = Math.min(properties.getMaximumSignificantDigits(), dosMax);
        final boolean alwaysShowDecimal = properties.getDecimalSeparatorAlwaysShown();
        final int exponentDigits = Math.min(properties.getMinimumExponentDigits(), dosMax);
        final boolean exponentShowPlusSign = properties.getExponentSignAlwaysShown();
        final String pp = properties.getPositivePrefix();
        final String ppp = properties.getPositivePrefixPattern();
        final String ps = properties.getPositiveSuffix();
        final String psp = properties.getPositiveSuffixPattern();
        final String np = properties.getNegativePrefix();
        final String npp = properties.getNegativePrefixPattern();
        final String ns = properties.getNegativeSuffix();
        final String nsp = properties.getNegativeSuffixPattern();
        if (ppp != null) {
            sb.append(ppp);
        }
        AffixUtils.escape(pp, sb);
        int afterPrefixPos = sb.length();
        int grouping;
        int grouping2;
        int grouping3;
        if (groupingSize != Math.min(dosMax, -1) && firstGroupingSize != Math.min(dosMax, -1) && groupingSize != firstGroupingSize) {
            grouping = groupingSize;
            grouping2 = groupingSize;
            grouping3 = firstGroupingSize;
        }
        else if (groupingSize != Math.min(dosMax, -1)) {
            grouping = groupingSize;
            grouping2 = 0;
            grouping3 = groupingSize;
        }
        else if (firstGroupingSize != Math.min(dosMax, -1)) {
            grouping = groupingSize;
            grouping2 = 0;
            grouping3 = firstGroupingSize;
        }
        else {
            grouping = 0;
            grouping2 = 0;
            grouping3 = 0;
        }
        final int groupingLength = grouping2 + grouping3 + 1;
        final BigDecimal roundingInterval = properties.getRoundingIncrement();
        final StringBuilder digitsString = new StringBuilder();
        int digitsStringScale = 0;
        if (maxSig != Math.min(dosMax, -1)) {
            while (digitsString.length() < minSig) {
                digitsString.append('@');
            }
            while (digitsString.length() < maxSig) {
                digitsString.append('#');
            }
        }
        else if (roundingInterval != null) {
            digitsStringScale = -roundingInterval.scale();
            final String str = roundingInterval.scaleByPowerOfTen(roundingInterval.scale()).toPlainString();
            if (str.charAt(0) == '-') {
                digitsString.append(str, 1, str.length());
            }
            else {
                digitsString.append(str);
            }
        }
        while (digitsString.length() + digitsStringScale < minInt) {
            digitsString.insert(0, '0');
        }
        while (-digitsStringScale < minFrac) {
            digitsString.append('0');
            --digitsStringScale;
        }
        int m0 = Math.max(groupingLength, digitsString.length() + digitsStringScale);
        m0 = ((maxInt != dosMax) ? (Math.max(maxInt, m0) - 1) : (m0 - 1));
        for (int mN = (maxFrac != dosMax) ? Math.min(-maxFrac, digitsStringScale) : digitsStringScale, magnitude = m0; magnitude >= mN; --magnitude) {
            final int di = digitsString.length() + digitsStringScale - magnitude - 1;
            if (di < 0 || di >= digitsString.length()) {
                sb.append('#');
            }
            else {
                sb.append(digitsString.charAt(di));
            }
            if (magnitude > grouping3 && grouping > 0 && (magnitude - grouping3) % grouping == 0) {
                sb.append(',');
            }
            else if (magnitude > 0 && magnitude == grouping3) {
                sb.append(',');
            }
            else if (magnitude == 0 && (alwaysShowDecimal || mN < 0)) {
                sb.append('.');
            }
        }
        if (exponentDigits != Math.min(dosMax, -1)) {
            sb.append('E');
            if (exponentShowPlusSign) {
                sb.append('+');
            }
            for (int i = 0; i < exponentDigits; ++i) {
                sb.append('0');
            }
        }
        int beforeSuffixPos = sb.length();
        if (psp != null) {
            sb.append(psp);
        }
        AffixUtils.escape(ps, sb);
        if (paddingWidth != -1) {
            while (paddingWidth - sb.length() > 0) {
                sb.insert(afterPrefixPos, '#');
                ++beforeSuffixPos;
            }
            switch (paddingLocation) {
                case BEFORE_PREFIX: {
                    final int addedLength = escapePaddingString(paddingString, sb, 0);
                    sb.insert(0, '*');
                    afterPrefixPos += addedLength + 1;
                    beforeSuffixPos += addedLength + 1;
                    break;
                }
                case AFTER_PREFIX: {
                    final int addedLength = escapePaddingString(paddingString, sb, afterPrefixPos);
                    sb.insert(afterPrefixPos, '*');
                    afterPrefixPos += addedLength + 1;
                    beforeSuffixPos += addedLength + 1;
                    break;
                }
                case BEFORE_SUFFIX: {
                    escapePaddingString(paddingString, sb, beforeSuffixPos);
                    sb.insert(beforeSuffixPos, '*');
                    break;
                }
                case AFTER_SUFFIX: {
                    sb.append('*');
                    escapePaddingString(paddingString, sb, sb.length());
                    break;
                }
            }
        }
        if (np != null || ns != null || (npp == null && nsp != null) || (npp != null && (npp.length() != 1 || npp.charAt(0) != '-' || nsp.length() != 0))) {
            sb.append(';');
            if (npp != null) {
                sb.append(npp);
            }
            AffixUtils.escape(np, sb);
            sb.append(sb, afterPrefixPos, beforeSuffixPos);
            if (nsp != null) {
                sb.append(nsp);
            }
            AffixUtils.escape(ns, sb);
        }
        return sb.toString();
    }
    
    private static int escapePaddingString(CharSequence input, final StringBuilder output, final int startIndex) {
        if (input == null || input.length() == 0) {
            input = " ";
        }
        final int startLength = output.length();
        if (input.length() == 1) {
            if (input.equals("'")) {
                output.insert(startIndex, "''");
            }
            else {
                output.insert(startIndex, input);
            }
        }
        else {
            output.insert(startIndex, '\'');
            int offset = 1;
            for (int i = 0; i < input.length(); ++i) {
                final char ch = input.charAt(i);
                if (ch == '\'') {
                    output.insert(startIndex + offset, "''");
                    offset += 2;
                }
                else {
                    output.insert(startIndex + offset, ch);
                    ++offset;
                }
            }
            output.insert(startIndex + offset, '\'');
        }
        return output.length() - startLength;
    }
    
    public static String convertLocalized(final String input, final DecimalFormatSymbols symbols, final boolean toLocalized) {
        if (input == null) {
            return null;
        }
        final String[][] table = new String[21][2];
        final int standIdx = toLocalized ? 0 : 1;
        final int localIdx = toLocalized ? 1 : 0;
        table[0][standIdx] = "%";
        table[0][localIdx] = symbols.getPercentString();
        table[1][standIdx] = "\u2030";
        table[1][localIdx] = symbols.getPerMillString();
        table[2][standIdx] = ".";
        table[2][localIdx] = symbols.getDecimalSeparatorString();
        table[3][standIdx] = ",";
        table[3][localIdx] = symbols.getGroupingSeparatorString();
        table[4][standIdx] = "-";
        table[4][localIdx] = symbols.getMinusSignString();
        table[5][standIdx] = "+";
        table[5][localIdx] = symbols.getPlusSignString();
        table[6][standIdx] = ";";
        table[6][localIdx] = Character.toString(symbols.getPatternSeparator());
        table[7][standIdx] = "@";
        table[7][localIdx] = Character.toString(symbols.getSignificantDigit());
        table[8][standIdx] = "E";
        table[8][localIdx] = symbols.getExponentSeparator();
        table[9][standIdx] = "*";
        table[9][localIdx] = Character.toString(symbols.getPadEscape());
        table[10][standIdx] = "#";
        table[10][localIdx] = Character.toString(symbols.getDigit());
        for (int i = 0; i < 10; ++i) {
            table[11 + i][standIdx] = Character.toString((char)(48 + i));
            table[11 + i][localIdx] = symbols.getDigitStringsLocal()[i];
        }
        for (int i = 0; i < table.length; ++i) {
            table[i][localIdx] = table[i][localIdx].replace('\'', '\u2019');
        }
        final StringBuilder result = new StringBuilder();
        int state = 0;
    Label_0793:
        for (int offset = 0; offset < input.length(); ++offset) {
            final char ch = input.charAt(offset);
            if (ch == '\'') {
                if (state == 0) {
                    result.append('\'');
                    state = 1;
                }
                else if (state == 1) {
                    result.append('\'');
                    state = 0;
                }
                else if (state == 2) {
                    state = 3;
                }
                else if (state == 3) {
                    result.append('\'');
                    result.append('\'');
                    state = 1;
                }
                else if (state == 4) {
                    state = 5;
                }
                else {
                    assert state == 5;
                    result.append('\'');
                    result.append('\'');
                    state = 4;
                }
            }
            else if (state == 0 || state == 3 || state == 4) {
                for (final String[] pair : table) {
                    if (input.regionMatches(offset, pair[0], 0, pair[0].length())) {
                        offset += pair[0].length() - 1;
                        if (state == 3 || state == 4) {
                            result.append('\'');
                            state = 0;
                        }
                        result.append(pair[1]);
                        continue Label_0793;
                    }
                }
                for (final String[] pair : table) {
                    if (input.regionMatches(offset, pair[1], 0, pair[1].length())) {
                        if (state == 0) {
                            result.append('\'');
                            state = 4;
                        }
                        result.append(ch);
                        continue Label_0793;
                    }
                }
                if (state == 3 || state == 4) {
                    result.append('\'');
                    state = 0;
                }
                result.append(ch);
            }
            else {
                assert state == 5;
                result.append(ch);
                state = 2;
            }
        }
        if (state == 3 || state == 4) {
            result.append('\'');
            state = 0;
        }
        if (state != 0) {
            throw new IllegalArgumentException("Malformed localized pattern: unterminated quote");
        }
        return result.toString();
    }
    
    public static void patternInfoToStringBuilder(final AffixPatternProvider patternInfo, final boolean isPrefix, final int signum, final NumberFormatter.SignDisplay signDisplay, final StandardPlural plural, final boolean perMilleReplacesPercent, final StringBuilder output) {
        final boolean plusReplacesMinusSign = signum != -1 && (signDisplay == NumberFormatter.SignDisplay.ALWAYS || signDisplay == NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS || (signum == 1 && (signDisplay == NumberFormatter.SignDisplay.EXCEPT_ZERO || signDisplay == NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO))) && !patternInfo.positiveHasPlusSign();
        final boolean useNegativeAffixPattern = patternInfo.hasNegativeSubpattern() && (signum == -1 || (patternInfo.negativeHasMinusSign() && plusReplacesMinusSign));
        int flags = 0;
        if (useNegativeAffixPattern) {
            flags |= 0x200;
        }
        if (isPrefix) {
            flags |= 0x100;
        }
        if (plural != null) {
            assert plural.ordinal() == (0xFF & plural.ordinal());
            flags |= plural.ordinal();
        }
        boolean prependSign;
        if (!isPrefix || useNegativeAffixPattern) {
            prependSign = false;
        }
        else if (signum == -1) {
            prependSign = (signDisplay != NumberFormatter.SignDisplay.NEVER);
        }
        else {
            prependSign = plusReplacesMinusSign;
        }
        final int length = patternInfo.length(flags) + (prependSign ? 1 : 0);
        output.setLength(0);
        for (int index = 0; index < length; ++index) {
            char candidate;
            if (prependSign && index == 0) {
                candidate = '-';
            }
            else if (prependSign) {
                candidate = patternInfo.charAt(flags, index - 1);
            }
            else {
                candidate = patternInfo.charAt(flags, index);
            }
            if (plusReplacesMinusSign && candidate == '-') {
                candidate = '+';
            }
            if (perMilleReplacesPercent && candidate == '%') {
                candidate = '\u2030';
            }
            output.append(candidate);
        }
    }
}
