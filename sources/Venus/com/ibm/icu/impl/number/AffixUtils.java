/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.UnicodeSet;

public class AffixUtils {
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
    static final boolean $assertionsDisabled = !AffixUtils.class.desiredAssertionStatus();

    public static int estimateLength(CharSequence charSequence) {
        int n;
        if (charSequence == null) {
            return 1;
        }
        int n2 = 0;
        int n3 = 0;
        block9: for (int i = 0; i < charSequence.length(); i += Character.charCount(n)) {
            n = Character.codePointAt(charSequence, i);
            switch (n2) {
                case 0: {
                    if (n == 39) {
                        n2 = 1;
                        continue block9;
                    }
                    ++n3;
                    continue block9;
                }
                case 1: {
                    if (n == 39) {
                        ++n3;
                        n2 = 0;
                        continue block9;
                    }
                    ++n3;
                    n2 = 2;
                    continue block9;
                }
                case 2: {
                    if (n == 39) {
                        n2 = 3;
                        continue block9;
                    }
                    ++n3;
                    continue block9;
                }
                case 3: {
                    if (n == 39) {
                        ++n3;
                        n2 = 2;
                        continue block9;
                    }
                    ++n3;
                    continue block9;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        switch (n2) {
            case 1: 
            case 2: {
                throw new IllegalArgumentException("Unterminated quote: \"" + charSequence + "\"");
            }
        }
        return n3;
    }

    public static int escape(CharSequence charSequence, StringBuilder stringBuilder) {
        int n;
        if (charSequence == null) {
            return 1;
        }
        int n2 = 0;
        int n3 = stringBuilder.length();
        block4: for (int i = 0; i < charSequence.length(); i += Character.charCount(n)) {
            n = Character.codePointAt(charSequence, i);
            switch (n) {
                case 39: {
                    stringBuilder.append("''");
                    continue block4;
                }
                case 37: 
                case 43: 
                case 45: 
                case 164: 
                case 8240: {
                    if (n2 == 0) {
                        stringBuilder.append('\'');
                        stringBuilder.appendCodePoint(n);
                        n2 = 2;
                        continue block4;
                    }
                    stringBuilder.appendCodePoint(n);
                    continue block4;
                }
                default: {
                    if (n2 == 2) {
                        stringBuilder.append('\'');
                        stringBuilder.appendCodePoint(n);
                        n2 = 0;
                        continue block4;
                    }
                    stringBuilder.appendCodePoint(n);
                }
            }
        }
        if (n2 == 2) {
            stringBuilder.append('\'');
        }
        return stringBuilder.length() - n3;
    }

    public static String escape(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        AffixUtils.escape(charSequence, stringBuilder);
        return stringBuilder.toString();
    }

    public static final NumberFormat.Field getFieldForType(int n) {
        switch (n) {
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
        }
        throw new AssertionError();
    }

    public static int unescape(CharSequence charSequence, FormattedStringBuilder formattedStringBuilder, int n, SymbolProvider symbolProvider, NumberFormat.Field field) {
        if (!$assertionsDisabled && charSequence == null) {
            throw new AssertionError();
        }
        int n2 = 0;
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            int n3 = AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence));
            if (n3 == -15) {
                n2 += formattedStringBuilder.insertCodePoint(n + n2, 65533, NumberFormat.Field.CURRENCY);
                continue;
            }
            if (n3 < 0) {
                n2 += formattedStringBuilder.insert(n + n2, symbolProvider.getSymbol(n3), AffixUtils.getFieldForType(n3));
                continue;
            }
            n2 += formattedStringBuilder.insertCodePoint(n + n2, n3, field);
        }
        return n2;
    }

    public static int unescapedCount(CharSequence charSequence, boolean bl, SymbolProvider symbolProvider) {
        int n = 0;
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            int n2 = AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence));
            if (n2 == -15) {
                ++n;
                continue;
            }
            if (n2 < 0) {
                CharSequence charSequence2 = symbolProvider.getSymbol(n2);
                n += bl ? charSequence2.length() : Character.codePointCount(charSequence2, 0, charSequence2.length());
                continue;
            }
            n += bl ? Character.charCount(n2) : 1;
        }
        return n;
    }

    public static boolean containsType(CharSequence charSequence, int n) {
        if (charSequence == null || charSequence.length() == 0) {
            return true;
        }
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            if (AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence)) != n) continue;
            return false;
        }
        return true;
    }

    public static boolean hasCurrencySymbols(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return true;
        }
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            int n = AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence));
            if (n >= 0 || AffixUtils.getFieldForType(n) != NumberFormat.Field.CURRENCY) continue;
            return false;
        }
        return true;
    }

    public static String replaceType(CharSequence charSequence, int n, char c) {
        if (charSequence == null || charSequence.length() == 0) {
            return "";
        }
        char[] cArray = charSequence.toString().toCharArray();
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            if (AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence)) != n) continue;
            int n2 = AffixUtils.getOffset(l);
            cArray[n2 - 1] = c;
        }
        return new String(cArray);
    }

    public static boolean containsOnlySymbolsAndIgnorables(CharSequence charSequence, UnicodeSet unicodeSet) {
        if (charSequence == null) {
            return false;
        }
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            int n = AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence));
            if (n < 0 || unicodeSet.contains(n)) continue;
            return true;
        }
        return false;
    }

    public static void iterateWithConsumer(CharSequence charSequence, TokenConsumer tokenConsumer) {
        if (!$assertionsDisabled && charSequence == null) {
            throw new AssertionError();
        }
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            l = AffixUtils.nextToken(l, charSequence);
            int n = AffixUtils.getTypeOrCp(l);
            tokenConsumer.consumeToken(n);
        }
    }

    private static long nextToken(long l, CharSequence charSequence) {
        int n = AffixUtils.getOffset(l);
        int n2 = AffixUtils.getState(l);
        block31: while (n < charSequence.length()) {
            int n3 = Character.codePointAt(charSequence, n);
            int n4 = Character.charCount(n3);
            switch (n2) {
                case 0: {
                    switch (n3) {
                        case 39: {
                            n2 = 1;
                            n += n4;
                            continue block31;
                        }
                        case 45: {
                            return AffixUtils.makeTag(n + n4, -1, 0, 0);
                        }
                        case 43: {
                            return AffixUtils.makeTag(n + n4, -2, 0, 0);
                        }
                        case 37: {
                            return AffixUtils.makeTag(n + n4, -3, 0, 0);
                        }
                        case 8240: {
                            return AffixUtils.makeTag(n + n4, -4, 0, 0);
                        }
                        case 164: {
                            n2 = 4;
                            n += n4;
                            continue block31;
                        }
                    }
                    return AffixUtils.makeTag(n + n4, 0, 0, n3);
                }
                case 1: {
                    if (n3 == 39) {
                        return AffixUtils.makeTag(n + n4, 0, 0, n3);
                    }
                    return AffixUtils.makeTag(n + n4, 0, 2, n3);
                }
                case 2: {
                    if (n3 == 39) {
                        n2 = 3;
                        n += n4;
                        continue block31;
                    }
                    return AffixUtils.makeTag(n + n4, 0, 2, n3);
                }
                case 3: {
                    if (n3 == 39) {
                        return AffixUtils.makeTag(n + n4, 0, 2, n3);
                    }
                    n2 = 0;
                    continue block31;
                }
                case 4: {
                    if (n3 == 164) {
                        n2 = 5;
                        n += n4;
                        continue block31;
                    }
                    return AffixUtils.makeTag(n, -5, 0, 0);
                }
                case 5: {
                    if (n3 == 164) {
                        n2 = 6;
                        n += n4;
                        continue block31;
                    }
                    return AffixUtils.makeTag(n, -6, 0, 0);
                }
                case 6: {
                    if (n3 == 164) {
                        n2 = 7;
                        n += n4;
                        continue block31;
                    }
                    return AffixUtils.makeTag(n, -7, 0, 0);
                }
                case 7: {
                    if (n3 == 164) {
                        n2 = 8;
                        n += n4;
                        continue block31;
                    }
                    return AffixUtils.makeTag(n, -8, 0, 0);
                }
                case 8: {
                    if (n3 == 164) {
                        n2 = 9;
                        n += n4;
                        continue block31;
                    }
                    return AffixUtils.makeTag(n, -9, 0, 0);
                }
                case 9: {
                    if (n3 == 164) {
                        n += n4;
                        continue block31;
                    }
                    return AffixUtils.makeTag(n, -15, 0, 0);
                }
            }
            throw new AssertionError();
        }
        switch (n2) {
            case 0: {
                return -1L;
            }
            case 1: 
            case 2: {
                throw new IllegalArgumentException("Unterminated quote in pattern affix: \"" + charSequence + "\"");
            }
            case 3: {
                return -1L;
            }
            case 4: {
                return AffixUtils.makeTag(n, -5, 0, 0);
            }
            case 5: {
                return AffixUtils.makeTag(n, -6, 0, 0);
            }
            case 6: {
                return AffixUtils.makeTag(n, -7, 0, 0);
            }
            case 7: {
                return AffixUtils.makeTag(n, -8, 0, 0);
            }
            case 8: {
                return AffixUtils.makeTag(n, -9, 0, 0);
            }
            case 9: {
                return AffixUtils.makeTag(n, -15, 0, 0);
            }
        }
        throw new AssertionError();
    }

    private static boolean hasNext(long l, CharSequence charSequence) {
        if (!$assertionsDisabled && l < 0L) {
            throw new AssertionError();
        }
        int n = AffixUtils.getState(l);
        int n2 = AffixUtils.getOffset(l);
        if (n == 2 && n2 == charSequence.length() - 1 && charSequence.charAt(n2) == '\'') {
            return true;
        }
        if (n != 0) {
            return false;
        }
        return n2 < charSequence.length();
    }

    private static int getTypeOrCp(long l) {
        if (!$assertionsDisabled && l < 0L) {
            throw new AssertionError();
        }
        int n = AffixUtils.getType(l);
        return n == 0 ? AffixUtils.getCodePoint(l) : -n;
    }

    private static long makeTag(int n, int n2, int n3, int n4) {
        long l = 0L;
        l |= (long)n;
        l |= -((long)n2) << 32;
        l |= (long)n3 << 36;
        if (!$assertionsDisabled && (l |= (long)n4 << 40) < 0L) {
            throw new AssertionError();
        }
        return l;
    }

    private static int getOffset(long l) {
        return (int)(l & 0xFFFFFFFFFFFFFFFFL);
    }

    private static int getType(long l) {
        return (int)(l >>> 32 & 0xFL);
    }

    private static int getState(long l) {
        return (int)(l >>> 36 & 0xFL);
    }

    private static int getCodePoint(long l) {
        return (int)(l >>> 40);
    }

    public static interface TokenConsumer {
        public void consumeToken(int var1);
    }

    public static interface SymbolProvider {
        public CharSequence getSymbol(int var1);
    }
}

