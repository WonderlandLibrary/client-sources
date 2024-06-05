package com.ibm.icu.impl.number;

import java.math.*;

public class PatternStringParser
{
    public static final int IGNORE_ROUNDING_NEVER = 0;
    public static final int IGNORE_ROUNDING_IF_CURRENCY = 1;
    public static final int IGNORE_ROUNDING_ALWAYS = 2;
    
    public static ParsedPatternInfo parseToPatternInfo(final String patternString) {
        final ParserState state = new ParserState(patternString);
        final ParsedPatternInfo result = new ParsedPatternInfo(patternString);
        consumePattern(state, result);
        return result;
    }
    
    public static DecimalFormatProperties parseToProperties(final String pattern, final int ignoreRounding) {
        final DecimalFormatProperties properties = new DecimalFormatProperties();
        parseToExistingPropertiesImpl(pattern, properties, ignoreRounding);
        return properties;
    }
    
    public static DecimalFormatProperties parseToProperties(final String pattern) {
        return parseToProperties(pattern, 0);
    }
    
    public static void parseToExistingProperties(final String pattern, final DecimalFormatProperties properties, final int ignoreRounding) {
        parseToExistingPropertiesImpl(pattern, properties, ignoreRounding);
    }
    
    public static void parseToExistingProperties(final String pattern, final DecimalFormatProperties properties) {
        parseToExistingProperties(pattern, properties, 0);
    }
    
    private static void consumePattern(final ParserState state, final ParsedPatternInfo result) {
        consumeSubpattern(state, result.positive = new ParsedSubpatternInfo());
        if (state.peek() == 59) {
            state.next();
            if (state.peek() != -1) {
                consumeSubpattern(state, result.negative = new ParsedSubpatternInfo());
            }
        }
        if (state.peek() != -1) {
            throw state.toParseException("Found unquoted special character");
        }
    }
    
    private static void consumeSubpattern(final ParserState state, final ParsedSubpatternInfo result) {
        consumePadding(state, result, Padder.PadPosition.BEFORE_PREFIX);
        result.prefixEndpoints = consumeAffix(state, result);
        consumePadding(state, result, Padder.PadPosition.AFTER_PREFIX);
        consumeFormat(state, result);
        consumeExponent(state, result);
        consumePadding(state, result, Padder.PadPosition.BEFORE_SUFFIX);
        result.suffixEndpoints = consumeAffix(state, result);
        consumePadding(state, result, Padder.PadPosition.AFTER_SUFFIX);
    }
    
    private static void consumePadding(final ParserState state, final ParsedSubpatternInfo result, final Padder.PadPosition paddingLocation) {
        if (state.peek() != 42) {
            return;
        }
        if (result.paddingLocation != null) {
            throw state.toParseException("Cannot have multiple pad specifiers");
        }
        result.paddingLocation = paddingLocation;
        state.next();
        result.paddingEndpoints |= state.offset;
        consumeLiteral(state);
        result.paddingEndpoints |= (long)state.offset << 32;
    }
    
    private static long consumeAffix(final ParserState state, final ParsedSubpatternInfo result) {
        long endpoints = state.offset;
    Label_0196:
        while (true) {
            switch (state.peek()) {
                case -1:
                case 35:
                case 42:
                case 44:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 59:
                case 64: {
                    break Label_0196;
                }
                case 37: {
                    result.hasPercentSign = true;
                    break;
                }
                case 8240: {
                    result.hasPerMilleSign = true;
                    break;
                }
                case 164: {
                    result.hasCurrencySign = true;
                    break;
                }
                case 45: {
                    result.hasMinusSign = true;
                    break;
                }
                case 43: {
                    result.hasPlusSign = true;
                    break;
                }
            }
            consumeLiteral(state);
        }
        endpoints |= (long)state.offset << 32;
        return endpoints;
    }
    
    private static void consumeLiteral(final ParserState state) {
        if (state.peek() == -1) {
            throw state.toParseException("Expected unquoted literal but found EOL");
        }
        if (state.peek() == 39) {
            state.next();
            while (state.peek() != 39) {
                if (state.peek() == -1) {
                    throw state.toParseException("Expected quoted literal but found EOL");
                }
                state.next();
            }
            state.next();
        }
        else {
            state.next();
        }
    }
    
    private static void consumeFormat(final ParserState state, final ParsedSubpatternInfo result) {
        consumeIntegerFormat(state, result);
        if (state.peek() == 46) {
            state.next();
            result.hasDecimal = true;
            ++result.widthExceptAffixes;
            consumeFractionFormat(state, result);
        }
    }
    
    private static void consumeIntegerFormat(final ParserState state, final ParsedSubpatternInfo result) {
        while (true) {
            switch (state.peek()) {
                case 44: {
                    ++result.widthExceptAffixes;
                    result.groupingSizes <<= 16;
                    break;
                }
                case 35: {
                    if (result.integerNumerals > 0) {
                        throw state.toParseException("# cannot follow 0 before decimal point");
                    }
                    ++result.widthExceptAffixes;
                    ++result.groupingSizes;
                    if (result.integerAtSigns > 0) {
                        ++result.integerTrailingHashSigns;
                    }
                    else {
                        ++result.integerLeadingHashSigns;
                    }
                    ++result.integerTotal;
                    break;
                }
                case 64: {
                    if (result.integerNumerals > 0) {
                        throw state.toParseException("Cannot mix 0 and @");
                    }
                    if (result.integerTrailingHashSigns > 0) {
                        throw state.toParseException("Cannot nest # inside of a run of @");
                    }
                    ++result.widthExceptAffixes;
                    ++result.groupingSizes;
                    ++result.integerAtSigns;
                    ++result.integerTotal;
                    break;
                }
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    if (result.integerAtSigns > 0) {
                        throw state.toParseException("Cannot mix @ and 0");
                    }
                    ++result.widthExceptAffixes;
                    ++result.groupingSizes;
                    ++result.integerNumerals;
                    ++result.integerTotal;
                    if (state.peek() != 48 && result.rounding == null) {
                        result.rounding = new DecimalQuantity_DualStorageBCD();
                    }
                    if (result.rounding != null) {
                        result.rounding.appendDigit((byte)(state.peek() - 48), 0, true);
                        break;
                    }
                    break;
                }
                default: {
                    final short grouping1 = (short)(result.groupingSizes & 0xFFFFL);
                    final short grouping2 = (short)(result.groupingSizes >>> 16 & 0xFFFFL);
                    final short grouping3 = (short)(result.groupingSizes >>> 32 & 0xFFFFL);
                    if (grouping1 == 0 && grouping2 != -1) {
                        throw state.toParseException("Trailing grouping separator is invalid");
                    }
                    if (grouping2 == 0 && grouping3 != -1) {
                        throw state.toParseException("Grouping width of zero is invalid");
                    }
                    return;
                }
            }
            state.next();
        }
    }
    
    private static void consumeFractionFormat(final ParserState state, final ParsedSubpatternInfo result) {
        int zeroCounter = 0;
        while (true) {
            switch (state.peek()) {
                case 35: {
                    ++result.widthExceptAffixes;
                    ++result.fractionHashSigns;
                    ++result.fractionTotal;
                    ++zeroCounter;
                    break;
                }
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    if (result.fractionHashSigns > 0) {
                        throw state.toParseException("0 cannot follow # after decimal point");
                    }
                    ++result.widthExceptAffixes;
                    ++result.fractionNumerals;
                    ++result.fractionTotal;
                    if (state.peek() == 48) {
                        ++zeroCounter;
                        break;
                    }
                    if (result.rounding == null) {
                        result.rounding = new DecimalQuantity_DualStorageBCD();
                    }
                    result.rounding.appendDigit((byte)(state.peek() - 48), zeroCounter, false);
                    zeroCounter = 0;
                    break;
                }
                default: {
                    return;
                }
            }
            state.next();
        }
    }
    
    private static void consumeExponent(final ParserState state, final ParsedSubpatternInfo result) {
        if (state.peek() != 69) {
            return;
        }
        if ((result.groupingSizes & 0xFFFF0000L) != 0xFFFF0000L) {
            throw state.toParseException("Cannot have grouping separator in scientific notation");
        }
        state.next();
        ++result.widthExceptAffixes;
        if (state.peek() == 43) {
            state.next();
            result.exponentHasPlusSign = true;
            ++result.widthExceptAffixes;
        }
        while (state.peek() == 48) {
            state.next();
            ++result.exponentZeros;
            ++result.widthExceptAffixes;
        }
    }
    
    private static void parseToExistingPropertiesImpl(final String pattern, final DecimalFormatProperties properties, final int ignoreRounding) {
        if (pattern == null || pattern.length() == 0) {
            properties.clear();
            return;
        }
        final ParsedPatternInfo patternInfo = parseToPatternInfo(pattern);
        patternInfoToProperties(properties, patternInfo, ignoreRounding);
    }
    
    private static void patternInfoToProperties(final DecimalFormatProperties properties, final ParsedPatternInfo patternInfo, final int _ignoreRounding) {
        final ParsedSubpatternInfo positive = patternInfo.positive;
        boolean ignoreRounding;
        if (_ignoreRounding == 0) {
            ignoreRounding = false;
        }
        else if (_ignoreRounding == 1) {
            ignoreRounding = positive.hasCurrencySign;
        }
        else {
            assert _ignoreRounding == 2;
            ignoreRounding = true;
        }
        final short grouping1 = (short)(positive.groupingSizes & 0xFFFFL);
        final short grouping2 = (short)(positive.groupingSizes >>> 16 & 0xFFFFL);
        final short grouping3 = (short)(positive.groupingSizes >>> 32 & 0xFFFFL);
        if (grouping2 != -1) {
            properties.setGroupingSize(grouping1);
            properties.setGroupingUsed(true);
        }
        else {
            properties.setGroupingSize(-1);
            properties.setGroupingUsed(false);
        }
        if (grouping3 != -1) {
            properties.setSecondaryGroupingSize(grouping2);
        }
        else {
            properties.setSecondaryGroupingSize(-1);
        }
        int minInt;
        int minFrac;
        if (positive.integerTotal == 0 && positive.fractionTotal > 0) {
            minInt = 0;
            minFrac = Math.max(1, positive.fractionNumerals);
        }
        else if (positive.integerNumerals == 0 && positive.fractionNumerals == 0) {
            minInt = 1;
            minFrac = 0;
        }
        else {
            minInt = positive.integerNumerals;
            minFrac = positive.fractionNumerals;
        }
        if (positive.integerAtSigns > 0) {
            properties.setMinimumFractionDigits(-1);
            properties.setMaximumFractionDigits(-1);
            properties.setRoundingIncrement(null);
            properties.setMinimumSignificantDigits(positive.integerAtSigns);
            properties.setMaximumSignificantDigits(positive.integerAtSigns + positive.integerTrailingHashSigns);
        }
        else if (positive.rounding != null) {
            if (!ignoreRounding) {
                properties.setMinimumFractionDigits(minFrac);
                properties.setMaximumFractionDigits(positive.fractionTotal);
                properties.setRoundingIncrement(positive.rounding.toBigDecimal().setScale(positive.fractionNumerals));
            }
            else {
                properties.setMinimumFractionDigits(-1);
                properties.setMaximumFractionDigits(-1);
                properties.setRoundingIncrement(null);
            }
            properties.setMinimumSignificantDigits(-1);
            properties.setMaximumSignificantDigits(-1);
        }
        else {
            if (!ignoreRounding) {
                properties.setMinimumFractionDigits(minFrac);
                properties.setMaximumFractionDigits(positive.fractionTotal);
                properties.setRoundingIncrement(null);
            }
            else {
                properties.setMinimumFractionDigits(-1);
                properties.setMaximumFractionDigits(-1);
                properties.setRoundingIncrement(null);
            }
            properties.setMinimumSignificantDigits(-1);
            properties.setMaximumSignificantDigits(-1);
        }
        if (positive.hasDecimal && positive.fractionTotal == 0) {
            properties.setDecimalSeparatorAlwaysShown(true);
        }
        else {
            properties.setDecimalSeparatorAlwaysShown(false);
        }
        if (positive.exponentZeros > 0) {
            properties.setExponentSignAlwaysShown(positive.exponentHasPlusSign);
            properties.setMinimumExponentDigits(positive.exponentZeros);
            if (positive.integerAtSigns == 0) {
                properties.setMinimumIntegerDigits(positive.integerNumerals);
                properties.setMaximumIntegerDigits(positive.integerTotal);
            }
            else {
                properties.setMinimumIntegerDigits(1);
                properties.setMaximumIntegerDigits(-1);
            }
        }
        else {
            properties.setExponentSignAlwaysShown(false);
            properties.setMinimumExponentDigits(-1);
            properties.setMinimumIntegerDigits(minInt);
            properties.setMaximumIntegerDigits(-1);
        }
        final String posPrefix = patternInfo.getString(256);
        final String posSuffix = patternInfo.getString(0);
        if (positive.paddingLocation != null) {
            final int paddingWidth = positive.widthExceptAffixes + AffixUtils.estimateLength(posPrefix) + AffixUtils.estimateLength(posSuffix);
            properties.setFormatWidth(paddingWidth);
            final String rawPaddingString = patternInfo.getString(1024);
            if (rawPaddingString.length() == 1) {
                properties.setPadString(rawPaddingString);
            }
            else if (rawPaddingString.length() == 2) {
                if (rawPaddingString.charAt(0) == '\'') {
                    properties.setPadString("'");
                }
                else {
                    properties.setPadString(rawPaddingString);
                }
            }
            else {
                properties.setPadString(rawPaddingString.substring(1, rawPaddingString.length() - 1));
            }
            assert positive.paddingLocation != null;
            properties.setPadPosition(positive.paddingLocation);
        }
        else {
            properties.setFormatWidth(-1);
            properties.setPadString(null);
            properties.setPadPosition(null);
        }
        properties.setPositivePrefixPattern(posPrefix);
        properties.setPositiveSuffixPattern(posSuffix);
        if (patternInfo.negative != null) {
            properties.setNegativePrefixPattern(patternInfo.getString(768));
            properties.setNegativeSuffixPattern(patternInfo.getString(512));
        }
        else {
            properties.setNegativePrefixPattern(null);
            properties.setNegativeSuffixPattern(null);
        }
        if (positive.hasPercentSign) {
            properties.setMagnitudeMultiplier(2);
        }
        else if (positive.hasPerMilleSign) {
            properties.setMagnitudeMultiplier(3);
        }
        else {
            properties.setMagnitudeMultiplier(0);
        }
    }
    
    public static class ParsedPatternInfo implements AffixPatternProvider
    {
        public String pattern;
        public ParsedSubpatternInfo positive;
        public ParsedSubpatternInfo negative;
        
        private ParsedPatternInfo(final String pattern) {
            this.pattern = pattern;
        }
        
        @Override
        public char charAt(final int flags, final int index) {
            final long endpoints = this.getEndpoints(flags);
            final int left = (int)(endpoints & -1L);
            final int right = (int)(endpoints >>> 32);
            if (index < 0 || index >= right - left) {
                throw new IndexOutOfBoundsException();
            }
            return this.pattern.charAt(left + index);
        }
        
        @Override
        public int length(final int flags) {
            return getLengthFromEndpoints(this.getEndpoints(flags));
        }
        
        public static int getLengthFromEndpoints(final long endpoints) {
            final int left = (int)(endpoints & -1L);
            final int right = (int)(endpoints >>> 32);
            return right - left;
        }
        
        @Override
        public String getString(final int flags) {
            final long endpoints = this.getEndpoints(flags);
            final int left = (int)(endpoints & -1L);
            final int right = (int)(endpoints >>> 32);
            if (left == right) {
                return "";
            }
            return this.pattern.substring(left, right);
        }
        
        private long getEndpoints(final int flags) {
            final boolean prefix = (flags & 0x100) != 0x0;
            final boolean isNegative = (flags & 0x200) != 0x0;
            final boolean padding = (flags & 0x400) != 0x0;
            if (isNegative && padding) {
                return this.negative.paddingEndpoints;
            }
            if (padding) {
                return this.positive.paddingEndpoints;
            }
            if (prefix && isNegative) {
                return this.negative.prefixEndpoints;
            }
            if (prefix) {
                return this.positive.prefixEndpoints;
            }
            if (isNegative) {
                return this.negative.suffixEndpoints;
            }
            return this.positive.suffixEndpoints;
        }
        
        @Override
        public boolean positiveHasPlusSign() {
            return this.positive.hasPlusSign;
        }
        
        @Override
        public boolean hasNegativeSubpattern() {
            return this.negative != null;
        }
        
        @Override
        public boolean negativeHasMinusSign() {
            return this.negative.hasMinusSign;
        }
        
        @Override
        public boolean hasCurrencySign() {
            return this.positive.hasCurrencySign || (this.negative != null && this.negative.hasCurrencySign);
        }
        
        @Override
        public boolean containsSymbolType(final int type) {
            return AffixUtils.containsType(this.pattern, type);
        }
        
        @Override
        public boolean hasBody() {
            return this.positive.integerTotal > 0;
        }
    }
    
    public static class ParsedSubpatternInfo
    {
        public long groupingSizes;
        public int integerLeadingHashSigns;
        public int integerTrailingHashSigns;
        public int integerNumerals;
        public int integerAtSigns;
        public int integerTotal;
        public int fractionNumerals;
        public int fractionHashSigns;
        public int fractionTotal;
        public boolean hasDecimal;
        public int widthExceptAffixes;
        public Padder.PadPosition paddingLocation;
        public DecimalQuantity_DualStorageBCD rounding;
        public boolean exponentHasPlusSign;
        public int exponentZeros;
        public boolean hasPercentSign;
        public boolean hasPerMilleSign;
        public boolean hasCurrencySign;
        public boolean hasMinusSign;
        public boolean hasPlusSign;
        public long prefixEndpoints;
        public long suffixEndpoints;
        public long paddingEndpoints;
        
        public ParsedSubpatternInfo() {
            this.groupingSizes = 281474976645120L;
            this.integerLeadingHashSigns = 0;
            this.integerTrailingHashSigns = 0;
            this.integerNumerals = 0;
            this.integerAtSigns = 0;
            this.integerTotal = 0;
            this.fractionNumerals = 0;
            this.fractionHashSigns = 0;
            this.fractionTotal = 0;
            this.hasDecimal = false;
            this.widthExceptAffixes = 0;
            this.paddingLocation = null;
            this.rounding = null;
            this.exponentHasPlusSign = false;
            this.exponentZeros = 0;
            this.hasPercentSign = false;
            this.hasPerMilleSign = false;
            this.hasCurrencySign = false;
            this.hasMinusSign = false;
            this.hasPlusSign = false;
            this.prefixEndpoints = 0L;
            this.suffixEndpoints = 0L;
            this.paddingEndpoints = 0L;
        }
    }
    
    private static class ParserState
    {
        final String pattern;
        int offset;
        
        ParserState(final String pattern) {
            this.pattern = pattern;
            this.offset = 0;
        }
        
        int peek() {
            if (this.offset == this.pattern.length()) {
                return -1;
            }
            return this.pattern.codePointAt(this.offset);
        }
        
        int next() {
            final int codePoint = this.peek();
            this.offset += Character.charCount(codePoint);
            return codePoint;
        }
        
        IllegalArgumentException toParseException(final String message) {
            final StringBuilder sb = new StringBuilder();
            sb.append("Malformed pattern for ICU DecimalFormat: \"");
            sb.append(this.pattern);
            sb.append("\": ");
            sb.append(message);
            sb.append(" at position ");
            sb.append(this.offset);
            return new IllegalArgumentException(sb.toString());
        }
    }
}
