package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;

public class AffixUtils
{
    private static final int STATE_BASE = 0;
    private static final int STATE_FIRST_QUOTE = 1;
    private static final int STATE_INSIDE_QUOTE = 2;
    private static final int STATE_AFTER_QUOTE = 3;
    private static final int STATE_FIRST_CURR = 4;
    private static final int STATE_SECOND_CURR = 5;
    private static final int STATE_THIRD_CURR = 6;
    private static final int STATE_FOURTH_CURR = 7;
    private static final int STATE_FIFTH_CURR = 8;
    private static final int STATE_OVERFLOW_CURR = 9;
    private static final int TYPE_CODEPOINT = 0;
    public static final int TYPE_MINUS_SIGN = -1;
    public static final int TYPE_PLUS_SIGN = -2;
    public static final int TYPE_PERCENT = -3;
    public static final int TYPE_PERMILLE = -4;
    public static final int TYPE_CURRENCY_SINGLE = -5;
    public static final int TYPE_CURRENCY_DOUBLE = -6;
    public static final int TYPE_CURRENCY_TRIPLE = -7;
    public static final int TYPE_CURRENCY_QUAD = -8;
    public static final int TYPE_CURRENCY_QUINT = -9;
    public static final int TYPE_CURRENCY_OVERFLOW = -15;
    
    public static int estimateLength(final CharSequence patternString) {
        if (patternString == null) {
            return 0;
        }
        int state = 0;
        int offset = 0;
        int length = 0;
        while (offset < patternString.length()) {
            final int cp = Character.codePointAt(patternString, offset);
            switch (state) {
                case 0: {
                    if (cp == 39) {
                        state = 1;
                        break;
                    }
                    ++length;
                    break;
                }
                case 1: {
                    if (cp == 39) {
                        ++length;
                        state = 0;
                        break;
                    }
                    ++length;
                    state = 2;
                    break;
                }
                case 2: {
                    if (cp == 39) {
                        state = 3;
                        break;
                    }
                    ++length;
                    break;
                }
                case 3: {
                    if (cp == 39) {
                        ++length;
                        state = 2;
                        break;
                    }
                    ++length;
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
            offset += Character.charCount(cp);
        }
        switch (state) {
            case 1:
            case 2: {
                throw new IllegalArgumentException("Unterminated quote: \"" + (Object)patternString + "\"");
            }
            default: {
                return length;
            }
        }
    }
    
    public static int escape(final CharSequence input, final StringBuilder output) {
        if (input == null) {
            return 0;
        }
        int state = 0;
        int offset = 0;
        final int startLength = output.length();
        while (offset < input.length()) {
            final int cp = Character.codePointAt(input, offset);
            switch (cp) {
                case 39: {
                    output.append("''");
                    break;
                }
                case 37:
                case 43:
                case 45:
                case 164:
                case 8240: {
                    if (state == 0) {
                        output.append('\'');
                        output.appendCodePoint(cp);
                        state = 2;
                        break;
                    }
                    output.appendCodePoint(cp);
                    break;
                }
                default: {
                    if (state == 2) {
                        output.append('\'');
                        output.appendCodePoint(cp);
                        state = 0;
                        break;
                    }
                    output.appendCodePoint(cp);
                    break;
                }
            }
            offset += Character.charCount(cp);
        }
        if (state == 2) {
            output.append('\'');
        }
        return output.length() - startLength;
    }
    
    public static String escape(final CharSequence input) {
        if (input == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        escape(input, sb);
        return sb.toString();
    }
    
    public static final NumberFormat.Field getFieldForType(final int type) {
        switch (type) {
            case -1: {
                return NumberFormat.Field.SIGN;
            }
            case -2: {
                return NumberFormat.Field.SIGN;
            }
            case -3: {
                return NumberFormat.Field.PERCENT;
            }
            case -4: {
                return NumberFormat.Field.PERMILLE;
            }
            case -5: {
                return NumberFormat.Field.CURRENCY;
            }
            case -6: {
                return NumberFormat.Field.CURRENCY;
            }
            case -7: {
                return NumberFormat.Field.CURRENCY;
            }
            case -8: {
                return NumberFormat.Field.CURRENCY;
            }
            case -9: {
                return NumberFormat.Field.CURRENCY;
            }
            case -15: {
                return NumberFormat.Field.CURRENCY;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public static int unescape(final CharSequence affixPattern, final NumberStringBuilder output, final int position, final SymbolProvider provider) {
        assert affixPattern != null;
        int length = 0;
        long tag = 0L;
        while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            final int typeOrCp = getTypeOrCp(tag);
            if (typeOrCp == -15) {
                length += output.insertCodePoint(position + length, 65533, NumberFormat.Field.CURRENCY);
            }
            else if (typeOrCp < 0) {
                length += output.insert(position + length, provider.getSymbol(typeOrCp), getFieldForType(typeOrCp));
            }
            else {
                length += output.insertCodePoint(position + length, typeOrCp, null);
            }
        }
        return length;
    }
    
    public static int unescapedCount(final CharSequence affixPattern, final boolean lengthOrCount, final SymbolProvider provider) {
        int length = 0;
        long tag = 0L;
        while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            final int typeOrCp = getTypeOrCp(tag);
            if (typeOrCp == -15) {
                ++length;
            }
            else if (typeOrCp < 0) {
                final CharSequence symbol = provider.getSymbol(typeOrCp);
                length += (lengthOrCount ? symbol.length() : Character.codePointCount(symbol, 0, symbol.length()));
            }
            else {
                length += (lengthOrCount ? Character.charCount(typeOrCp) : 1);
            }
        }
        return length;
    }
    
    public static boolean containsType(final CharSequence affixPattern, final int type) {
        if (affixPattern == null || affixPattern.length() == 0) {
            return false;
        }
        long tag = 0L;
        while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            if (getTypeOrCp(tag) == type) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasCurrencySymbols(final CharSequence affixPattern) {
        if (affixPattern == null || affixPattern.length() == 0) {
            return false;
        }
        long tag = 0L;
        while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            final int typeOrCp = getTypeOrCp(tag);
            if (typeOrCp < 0 && getFieldForType(typeOrCp) == NumberFormat.Field.CURRENCY) {
                return true;
            }
        }
        return false;
    }
    
    public static String replaceType(final CharSequence affixPattern, final int type, final char replacementChar) {
        if (affixPattern == null || affixPattern.length() == 0) {
            return "";
        }
        final char[] chars = affixPattern.toString().toCharArray();
        long tag = 0L;
        while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            if (getTypeOrCp(tag) == type) {
                final int offset = getOffset(tag);
                chars[offset - 1] = replacementChar;
            }
        }
        return new String(chars);
    }
    
    public static boolean containsOnlySymbolsAndIgnorables(final CharSequence affixPattern, final UnicodeSet ignorables) {
        if (affixPattern == null) {
            return true;
        }
        long tag = 0L;
        while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            final int typeOrCp = getTypeOrCp(tag);
            if (typeOrCp >= 0 && !ignorables.contains(typeOrCp)) {
                return false;
            }
        }
        return true;
    }
    
    public static void iterateWithConsumer(final CharSequence affixPattern, final TokenConsumer consumer) {
        assert affixPattern != null;
        long tag = 0L;
        while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            final int typeOrCp = getTypeOrCp(tag);
            consumer.consumeToken(typeOrCp);
        }
    }
    
    private static long nextToken(final long tag, final CharSequence patternString) {
        int offset = getOffset(tag);
        int state = getState(tag);
        while (offset < patternString.length()) {
            final int cp = Character.codePointAt(patternString, offset);
            final int count = Character.charCount(cp);
            switch (state) {
                case 0: {
                    switch (cp) {
                        case 39: {
                            state = 1;
                            offset += count;
                            continue;
                        }
                        case 45: {
                            return makeTag(offset + count, -1, 0, 0);
                        }
                        case 43: {
                            return makeTag(offset + count, -2, 0, 0);
                        }
                        case 37: {
                            return makeTag(offset + count, -3, 0, 0);
                        }
                        case 8240: {
                            return makeTag(offset + count, -4, 0, 0);
                        }
                        case 164: {
                            state = 4;
                            offset += count;
                            continue;
                        }
                        default: {
                            return makeTag(offset + count, 0, 0, cp);
                        }
                    }
                    break;
                }
                case 1: {
                    if (cp == 39) {
                        return makeTag(offset + count, 0, 0, cp);
                    }
                    return makeTag(offset + count, 0, 2, cp);
                }
                case 2: {
                    if (cp == 39) {
                        state = 3;
                        offset += count;
                        continue;
                    }
                    return makeTag(offset + count, 0, 2, cp);
                }
                case 3: {
                    if (cp == 39) {
                        return makeTag(offset + count, 0, 2, cp);
                    }
                    state = 0;
                    continue;
                }
                case 4: {
                    if (cp == 164) {
                        state = 5;
                        offset += count;
                        continue;
                    }
                    return makeTag(offset, -5, 0, 0);
                }
                case 5: {
                    if (cp == 164) {
                        state = 6;
                        offset += count;
                        continue;
                    }
                    return makeTag(offset, -6, 0, 0);
                }
                case 6: {
                    if (cp == 164) {
                        state = 7;
                        offset += count;
                        continue;
                    }
                    return makeTag(offset, -7, 0, 0);
                }
                case 7: {
                    if (cp == 164) {
                        state = 8;
                        offset += count;
                        continue;
                    }
                    return makeTag(offset, -8, 0, 0);
                }
                case 8: {
                    if (cp == 164) {
                        state = 9;
                        offset += count;
                        continue;
                    }
                    return makeTag(offset, -9, 0, 0);
                }
                case 9: {
                    if (cp == 164) {
                        offset += count;
                        continue;
                    }
                    return makeTag(offset, -15, 0, 0);
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        switch (state) {
            case 0: {
                return -1L;
            }
            case 1:
            case 2: {
                throw new IllegalArgumentException("Unterminated quote in pattern affix: \"" + (Object)patternString + "\"");
            }
            case 3: {
                return -1L;
            }
            case 4: {
                return makeTag(offset, -5, 0, 0);
            }
            case 5: {
                return makeTag(offset, -6, 0, 0);
            }
            case 6: {
                return makeTag(offset, -7, 0, 0);
            }
            case 7: {
                return makeTag(offset, -8, 0, 0);
            }
            case 8: {
                return makeTag(offset, -9, 0, 0);
            }
            case 9: {
                return makeTag(offset, -15, 0, 0);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    private static boolean hasNext(final long tag, final CharSequence string) {
        assert tag >= 0L;
        final int state = getState(tag);
        final int offset = getOffset(tag);
        return (state != 2 || offset != string.length() - 1 || string.charAt(offset) != '\'') && (state != 0 || offset < string.length());
    }
    
    private static int getTypeOrCp(final long tag) {
        assert tag >= 0L;
        final int type = getType(tag);
        return (type == 0) ? getCodePoint(tag) : (-type);
    }
    
    private static long makeTag(final int offset, final int type, final int state, final int cp) {
        long tag = 0L;
        tag |= offset;
        tag |= -type << 32;
        tag |= (long)state << 36;
        tag |= (long)cp << 40;
        assert tag >= 0L;
        return tag;
    }
    
    private static int getOffset(final long tag) {
        return (int)(tag & -1L);
    }
    
    private static int getType(final long tag) {
        return (int)(tag >>> 32 & 0xFL);
    }
    
    private static int getState(final long tag) {
        return (int)(tag >>> 36 & 0xFL);
    }
    
    private static int getCodePoint(final long tag) {
        return (int)(tag >>> 40);
    }
    
    public interface TokenConsumer
    {
        void consumeToken(final int p0);
    }
    
    public interface SymbolProvider
    {
        CharSequence getSymbol(final int p0);
    }
}
