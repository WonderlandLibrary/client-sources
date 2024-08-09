/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.AffixUtils;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.Padder;

public class PatternStringParser {
    public static final int IGNORE_ROUNDING_NEVER = 0;
    public static final int IGNORE_ROUNDING_IF_CURRENCY = 1;
    public static final int IGNORE_ROUNDING_ALWAYS = 2;
    static final boolean $assertionsDisabled = !PatternStringParser.class.desiredAssertionStatus();

    public static ParsedPatternInfo parseToPatternInfo(String string) {
        ParserState parserState = new ParserState(string);
        ParsedPatternInfo parsedPatternInfo = new ParsedPatternInfo(string, null);
        PatternStringParser.consumePattern(parserState, parsedPatternInfo);
        return parsedPatternInfo;
    }

    public static DecimalFormatProperties parseToProperties(String string, int n) {
        DecimalFormatProperties decimalFormatProperties = new DecimalFormatProperties();
        PatternStringParser.parseToExistingPropertiesImpl(string, decimalFormatProperties, n);
        return decimalFormatProperties;
    }

    public static DecimalFormatProperties parseToProperties(String string) {
        return PatternStringParser.parseToProperties(string, 0);
    }

    public static void parseToExistingProperties(String string, DecimalFormatProperties decimalFormatProperties, int n) {
        PatternStringParser.parseToExistingPropertiesImpl(string, decimalFormatProperties, n);
    }

    public static void parseToExistingProperties(String string, DecimalFormatProperties decimalFormatProperties) {
        PatternStringParser.parseToExistingProperties(string, decimalFormatProperties, 0);
    }

    private static void consumePattern(ParserState parserState, ParsedPatternInfo parsedPatternInfo) {
        parsedPatternInfo.positive = new ParsedSubpatternInfo();
        PatternStringParser.consumeSubpattern(parserState, parsedPatternInfo.positive);
        if (parserState.peek() == 59) {
            parserState.next();
            if (parserState.peek() != -1) {
                parsedPatternInfo.negative = new ParsedSubpatternInfo();
                PatternStringParser.consumeSubpattern(parserState, parsedPatternInfo.negative);
            }
        }
        if (parserState.peek() != -1) {
            throw parserState.toParseException("Found unquoted special character");
        }
    }

    private static void consumeSubpattern(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        PatternStringParser.consumePadding(parserState, parsedSubpatternInfo, Padder.PadPosition.BEFORE_PREFIX);
        parsedSubpatternInfo.prefixEndpoints = PatternStringParser.consumeAffix(parserState, parsedSubpatternInfo);
        PatternStringParser.consumePadding(parserState, parsedSubpatternInfo, Padder.PadPosition.AFTER_PREFIX);
        PatternStringParser.consumeFormat(parserState, parsedSubpatternInfo);
        PatternStringParser.consumeExponent(parserState, parsedSubpatternInfo);
        PatternStringParser.consumePadding(parserState, parsedSubpatternInfo, Padder.PadPosition.BEFORE_SUFFIX);
        parsedSubpatternInfo.suffixEndpoints = PatternStringParser.consumeAffix(parserState, parsedSubpatternInfo);
        PatternStringParser.consumePadding(parserState, parsedSubpatternInfo, Padder.PadPosition.AFTER_SUFFIX);
    }

    private static void consumePadding(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo, Padder.PadPosition padPosition) {
        if (parserState.peek() != 42) {
            return;
        }
        if (parsedSubpatternInfo.paddingLocation != null) {
            throw parserState.toParseException("Cannot have multiple pad specifiers");
        }
        parsedSubpatternInfo.paddingLocation = padPosition;
        parserState.next();
        parsedSubpatternInfo.paddingEndpoints |= (long)parserState.offset;
        PatternStringParser.consumeLiteral(parserState);
        parsedSubpatternInfo.paddingEndpoints |= (long)parserState.offset << 32;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static long consumeAffix(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        long l = parserState.offset;
        while (true) {
            switch (parserState.peek()) {
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
                    return l |= (long)parserState.offset << 32;
                }
                case 37: {
                    parsedSubpatternInfo.hasPercentSign = true;
                    break;
                }
                case 8240: {
                    parsedSubpatternInfo.hasPerMilleSign = true;
                    break;
                }
                case 164: {
                    parsedSubpatternInfo.hasCurrencySign = true;
                    break;
                }
                case 45: {
                    parsedSubpatternInfo.hasMinusSign = true;
                    break;
                }
                case 43: {
                    parsedSubpatternInfo.hasPlusSign = true;
                }
            }
            PatternStringParser.consumeLiteral(parserState);
        }
    }

    private static void consumeLiteral(ParserState parserState) {
        if (parserState.peek() == -1) {
            throw parserState.toParseException("Expected unquoted literal but found EOL");
        }
        if (parserState.peek() == 39) {
            parserState.next();
            while (parserState.peek() != 39) {
                if (parserState.peek() == -1) {
                    throw parserState.toParseException("Expected quoted literal but found EOL");
                }
                parserState.next();
            }
            parserState.next();
        } else {
            parserState.next();
        }
    }

    private static void consumeFormat(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        PatternStringParser.consumeIntegerFormat(parserState, parsedSubpatternInfo);
        if (parserState.peek() == 46) {
            parserState.next();
            parsedSubpatternInfo.hasDecimal = true;
            ++parsedSubpatternInfo.widthExceptAffixes;
            PatternStringParser.consumeFractionFormat(parserState, parsedSubpatternInfo);
        }
    }

    private static void consumeIntegerFormat(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        block6: while (true) {
            switch (parserState.peek()) {
                case 44: {
                    ++parsedSubpatternInfo.widthExceptAffixes;
                    parsedSubpatternInfo.groupingSizes <<= 16;
                    break;
                }
                case 35: {
                    if (parsedSubpatternInfo.integerNumerals > 0) {
                        throw parserState.toParseException("# cannot follow 0 before decimal point");
                    }
                    ++parsedSubpatternInfo.widthExceptAffixes;
                    ++parsedSubpatternInfo.groupingSizes;
                    if (parsedSubpatternInfo.integerAtSigns > 0) {
                        ++parsedSubpatternInfo.integerTrailingHashSigns;
                    } else {
                        ++parsedSubpatternInfo.integerLeadingHashSigns;
                    }
                    ++parsedSubpatternInfo.integerTotal;
                    break;
                }
                case 64: {
                    if (parsedSubpatternInfo.integerNumerals > 0) {
                        throw parserState.toParseException("Cannot mix 0 and @");
                    }
                    if (parsedSubpatternInfo.integerTrailingHashSigns > 0) {
                        throw parserState.toParseException("Cannot nest # inside of a run of @");
                    }
                    ++parsedSubpatternInfo.widthExceptAffixes;
                    ++parsedSubpatternInfo.groupingSizes;
                    ++parsedSubpatternInfo.integerAtSigns;
                    ++parsedSubpatternInfo.integerTotal;
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
                    if (parsedSubpatternInfo.integerAtSigns > 0) {
                        throw parserState.toParseException("Cannot mix @ and 0");
                    }
                    ++parsedSubpatternInfo.widthExceptAffixes;
                    ++parsedSubpatternInfo.groupingSizes;
                    ++parsedSubpatternInfo.integerNumerals;
                    ++parsedSubpatternInfo.integerTotal;
                    if (parserState.peek() != 48 && parsedSubpatternInfo.rounding == null) {
                        parsedSubpatternInfo.rounding = new DecimalQuantity_DualStorageBCD();
                    }
                    if (parsedSubpatternInfo.rounding == null) break;
                    parsedSubpatternInfo.rounding.appendDigit((byte)(parserState.peek() - 48), 0, false);
                    break;
                }
                default: {
                    break block6;
                }
            }
            parserState.next();
        }
        short s = (short)(parsedSubpatternInfo.groupingSizes & 0xFFFFL);
        short s2 = (short)(parsedSubpatternInfo.groupingSizes >>> 16 & 0xFFFFL);
        short s3 = (short)(parsedSubpatternInfo.groupingSizes >>> 32 & 0xFFFFL);
        if (s == 0 && s2 != -1) {
            throw parserState.toParseException("Trailing grouping separator is invalid");
        }
        if (s2 == 0 && s3 != -1) {
            throw parserState.toParseException("Grouping width of zero is invalid");
        }
    }

    private static void consumeFractionFormat(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        int n = 0;
        while (true) {
            switch (parserState.peek()) {
                case 35: {
                    ++parsedSubpatternInfo.widthExceptAffixes;
                    ++parsedSubpatternInfo.fractionHashSigns;
                    ++parsedSubpatternInfo.fractionTotal;
                    ++n;
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
                    if (parsedSubpatternInfo.fractionHashSigns > 0) {
                        throw parserState.toParseException("0 cannot follow # after decimal point");
                    }
                    ++parsedSubpatternInfo.widthExceptAffixes;
                    ++parsedSubpatternInfo.fractionNumerals;
                    ++parsedSubpatternInfo.fractionTotal;
                    if (parserState.peek() == 48) {
                        ++n;
                        break;
                    }
                    if (parsedSubpatternInfo.rounding == null) {
                        parsedSubpatternInfo.rounding = new DecimalQuantity_DualStorageBCD();
                    }
                    parsedSubpatternInfo.rounding.appendDigit((byte)(parserState.peek() - 48), n, true);
                    n = 0;
                    break;
                }
                default: {
                    return;
                }
            }
            parserState.next();
        }
    }

    private static void consumeExponent(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        if (parserState.peek() != 69) {
            return;
        }
        if ((parsedSubpatternInfo.groupingSizes & 0xFFFF0000L) != 0xFFFF0000L) {
            throw parserState.toParseException("Cannot have grouping separator in scientific notation");
        }
        parserState.next();
        ++parsedSubpatternInfo.widthExceptAffixes;
        if (parserState.peek() == 43) {
            parserState.next();
            parsedSubpatternInfo.exponentHasPlusSign = true;
            ++parsedSubpatternInfo.widthExceptAffixes;
        }
        while (parserState.peek() == 48) {
            parserState.next();
            ++parsedSubpatternInfo.exponentZeros;
            ++parsedSubpatternInfo.widthExceptAffixes;
        }
    }

    private static void parseToExistingPropertiesImpl(String string, DecimalFormatProperties decimalFormatProperties, int n) {
        if (string == null || string.length() == 0) {
            decimalFormatProperties.clear();
            return;
        }
        ParsedPatternInfo parsedPatternInfo = PatternStringParser.parseToPatternInfo(string);
        PatternStringParser.patternInfoToProperties(decimalFormatProperties, parsedPatternInfo, n);
    }

    private static void patternInfoToProperties(DecimalFormatProperties decimalFormatProperties, ParsedPatternInfo parsedPatternInfo, int n) {
        int n2;
        int n3;
        boolean bl;
        ParsedSubpatternInfo parsedSubpatternInfo = parsedPatternInfo.positive;
        if (n == 0) {
            bl = false;
        } else if (n == 1) {
            bl = parsedSubpatternInfo.hasCurrencySign;
        } else {
            if (!$assertionsDisabled && n != 2) {
                throw new AssertionError();
            }
            bl = true;
        }
        short s = (short)(parsedSubpatternInfo.groupingSizes & 0xFFFFL);
        short s2 = (short)(parsedSubpatternInfo.groupingSizes >>> 16 & 0xFFFFL);
        short s3 = (short)(parsedSubpatternInfo.groupingSizes >>> 32 & 0xFFFFL);
        if (s2 != -1) {
            decimalFormatProperties.setGroupingSize(s);
            decimalFormatProperties.setGroupingUsed(false);
        } else {
            decimalFormatProperties.setGroupingSize(-1);
            decimalFormatProperties.setGroupingUsed(true);
        }
        if (s3 != -1) {
            decimalFormatProperties.setSecondaryGroupingSize(s2);
        } else {
            decimalFormatProperties.setSecondaryGroupingSize(-1);
        }
        if (parsedSubpatternInfo.integerTotal == 0 && parsedSubpatternInfo.fractionTotal > 0) {
            n3 = 0;
            n2 = Math.max(1, parsedSubpatternInfo.fractionNumerals);
        } else if (parsedSubpatternInfo.integerNumerals == 0 && parsedSubpatternInfo.fractionNumerals == 0) {
            n3 = 1;
            n2 = 0;
        } else {
            n3 = parsedSubpatternInfo.integerNumerals;
            n2 = parsedSubpatternInfo.fractionNumerals;
        }
        if (parsedSubpatternInfo.integerAtSigns > 0) {
            decimalFormatProperties.setMinimumFractionDigits(-1);
            decimalFormatProperties.setMaximumFractionDigits(-1);
            decimalFormatProperties.setRoundingIncrement(null);
            decimalFormatProperties.setMinimumSignificantDigits(parsedSubpatternInfo.integerAtSigns);
            decimalFormatProperties.setMaximumSignificantDigits(parsedSubpatternInfo.integerAtSigns + parsedSubpatternInfo.integerTrailingHashSigns);
        } else if (parsedSubpatternInfo.rounding != null) {
            if (!bl) {
                decimalFormatProperties.setMinimumFractionDigits(n2);
                decimalFormatProperties.setMaximumFractionDigits(parsedSubpatternInfo.fractionTotal);
                decimalFormatProperties.setRoundingIncrement(parsedSubpatternInfo.rounding.toBigDecimal().setScale(parsedSubpatternInfo.fractionNumerals));
            } else {
                decimalFormatProperties.setMinimumFractionDigits(-1);
                decimalFormatProperties.setMaximumFractionDigits(-1);
                decimalFormatProperties.setRoundingIncrement(null);
            }
            decimalFormatProperties.setMinimumSignificantDigits(-1);
            decimalFormatProperties.setMaximumSignificantDigits(-1);
        } else {
            if (!bl) {
                decimalFormatProperties.setMinimumFractionDigits(n2);
                decimalFormatProperties.setMaximumFractionDigits(parsedSubpatternInfo.fractionTotal);
                decimalFormatProperties.setRoundingIncrement(null);
            } else {
                decimalFormatProperties.setMinimumFractionDigits(-1);
                decimalFormatProperties.setMaximumFractionDigits(-1);
                decimalFormatProperties.setRoundingIncrement(null);
            }
            decimalFormatProperties.setMinimumSignificantDigits(-1);
            decimalFormatProperties.setMaximumSignificantDigits(-1);
        }
        if (parsedSubpatternInfo.hasDecimal && parsedSubpatternInfo.fractionTotal == 0) {
            decimalFormatProperties.setDecimalSeparatorAlwaysShown(false);
        } else {
            decimalFormatProperties.setDecimalSeparatorAlwaysShown(true);
        }
        if (parsedSubpatternInfo.exponentZeros > 0) {
            decimalFormatProperties.setExponentSignAlwaysShown(parsedSubpatternInfo.exponentHasPlusSign);
            decimalFormatProperties.setMinimumExponentDigits(parsedSubpatternInfo.exponentZeros);
            if (parsedSubpatternInfo.integerAtSigns == 0) {
                decimalFormatProperties.setMinimumIntegerDigits(parsedSubpatternInfo.integerNumerals);
                decimalFormatProperties.setMaximumIntegerDigits(parsedSubpatternInfo.integerTotal);
            } else {
                decimalFormatProperties.setMinimumIntegerDigits(1);
                decimalFormatProperties.setMaximumIntegerDigits(-1);
            }
        } else {
            decimalFormatProperties.setExponentSignAlwaysShown(true);
            decimalFormatProperties.setMinimumExponentDigits(-1);
            decimalFormatProperties.setMinimumIntegerDigits(n3);
            decimalFormatProperties.setMaximumIntegerDigits(-1);
        }
        String string = parsedPatternInfo.getString(256);
        String string2 = parsedPatternInfo.getString(0);
        if (parsedSubpatternInfo.paddingLocation != null) {
            int n4 = parsedSubpatternInfo.widthExceptAffixes + AffixUtils.estimateLength(string) + AffixUtils.estimateLength(string2);
            decimalFormatProperties.setFormatWidth(n4);
            String string3 = parsedPatternInfo.getString(1024);
            if (string3.length() == 1) {
                decimalFormatProperties.setPadString(string3);
            } else if (string3.length() == 2) {
                if (string3.charAt(0) == '\'') {
                    decimalFormatProperties.setPadString("'");
                } else {
                    decimalFormatProperties.setPadString(string3);
                }
            } else {
                decimalFormatProperties.setPadString(string3.substring(1, string3.length() - 1));
            }
            if (!$assertionsDisabled && parsedSubpatternInfo.paddingLocation == null) {
                throw new AssertionError();
            }
            decimalFormatProperties.setPadPosition(parsedSubpatternInfo.paddingLocation);
        } else {
            decimalFormatProperties.setFormatWidth(-1);
            decimalFormatProperties.setPadString(null);
            decimalFormatProperties.setPadPosition(null);
        }
        decimalFormatProperties.setPositivePrefixPattern(string);
        decimalFormatProperties.setPositiveSuffixPattern(string2);
        if (parsedPatternInfo.negative != null) {
            decimalFormatProperties.setNegativePrefixPattern(parsedPatternInfo.getString(768));
            decimalFormatProperties.setNegativeSuffixPattern(parsedPatternInfo.getString(512));
        } else {
            decimalFormatProperties.setNegativePrefixPattern(null);
            decimalFormatProperties.setNegativeSuffixPattern(null);
        }
        if (parsedSubpatternInfo.hasPercentSign) {
            decimalFormatProperties.setMagnitudeMultiplier(2);
        } else if (parsedSubpatternInfo.hasPerMilleSign) {
            decimalFormatProperties.setMagnitudeMultiplier(3);
        } else {
            decimalFormatProperties.setMagnitudeMultiplier(0);
        }
    }

    private static class ParserState {
        final String pattern;
        int offset;

        ParserState(String string) {
            this.pattern = string;
            this.offset = 0;
        }

        int peek() {
            if (this.offset == this.pattern.length()) {
                return 1;
            }
            return this.pattern.codePointAt(this.offset);
        }

        int next() {
            int n = this.peek();
            this.offset += Character.charCount(n);
            return n;
        }

        IllegalArgumentException toParseException(String string) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Malformed pattern for ICU DecimalFormat: \"");
            stringBuilder.append(this.pattern);
            stringBuilder.append("\": ");
            stringBuilder.append(string);
            stringBuilder.append(" at position ");
            stringBuilder.append(this.offset);
            return new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static class ParsedSubpatternInfo {
        public long groupingSizes = 0xFFFFFFFF0000L;
        public int integerLeadingHashSigns = 0;
        public int integerTrailingHashSigns = 0;
        public int integerNumerals = 0;
        public int integerAtSigns = 0;
        public int integerTotal = 0;
        public int fractionNumerals = 0;
        public int fractionHashSigns = 0;
        public int fractionTotal = 0;
        public boolean hasDecimal = false;
        public int widthExceptAffixes = 0;
        public Padder.PadPosition paddingLocation = null;
        public DecimalQuantity_DualStorageBCD rounding = null;
        public boolean exponentHasPlusSign = false;
        public int exponentZeros = 0;
        public boolean hasPercentSign = false;
        public boolean hasPerMilleSign = false;
        public boolean hasCurrencySign = false;
        public boolean hasMinusSign = false;
        public boolean hasPlusSign = false;
        public long prefixEndpoints = 0L;
        public long suffixEndpoints = 0L;
        public long paddingEndpoints = 0L;
    }

    public static class ParsedPatternInfo
    implements AffixPatternProvider {
        public String pattern;
        public ParsedSubpatternInfo positive;
        public ParsedSubpatternInfo negative;

        private ParsedPatternInfo(String string) {
            this.pattern = string;
        }

        @Override
        public char charAt(int n, int n2) {
            long l = this.getEndpoints(n);
            int n3 = (int)(l & 0xFFFFFFFFFFFFFFFFL);
            int n4 = (int)(l >>> 32);
            if (n2 < 0 || n2 >= n4 - n3) {
                throw new IndexOutOfBoundsException();
            }
            return this.pattern.charAt(n3 + n2);
        }

        @Override
        public int length(int n) {
            return ParsedPatternInfo.getLengthFromEndpoints(this.getEndpoints(n));
        }

        public static int getLengthFromEndpoints(long l) {
            int n = (int)(l & 0xFFFFFFFFFFFFFFFFL);
            int n2 = (int)(l >>> 32);
            return n2 - n;
        }

        @Override
        public String getString(int n) {
            int n2;
            long l = this.getEndpoints(n);
            int n3 = (int)(l & 0xFFFFFFFFFFFFFFFFL);
            if (n3 == (n2 = (int)(l >>> 32))) {
                return "";
            }
            return this.pattern.substring(n3, n2);
        }

        private long getEndpoints(int n) {
            boolean bl;
            boolean bl2 = (n & 0x100) != 0;
            boolean bl3 = (n & 0x200) != 0;
            boolean bl4 = bl = (n & 0x400) != 0;
            if (bl3 && bl) {
                return this.negative.paddingEndpoints;
            }
            if (bl) {
                return this.positive.paddingEndpoints;
            }
            if (bl2 && bl3) {
                return this.negative.prefixEndpoints;
            }
            if (bl2) {
                return this.positive.prefixEndpoints;
            }
            if (bl3) {
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
            return this.positive.hasCurrencySign || this.negative != null && this.negative.hasCurrencySign;
        }

        @Override
        public boolean containsSymbolType(int n) {
            return AffixUtils.containsType(this.pattern, n);
        }

        @Override
        public boolean hasBody() {
            return this.positive.integerTotal > 0;
        }

        ParsedPatternInfo(String string, 1 var2_2) {
            this(string);
        }
    }
}

