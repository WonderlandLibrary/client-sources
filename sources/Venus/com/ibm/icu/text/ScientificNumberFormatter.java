/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.ULocale;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public final class ScientificNumberFormatter {
    private final String preExponent;
    private final DecimalFormat fmt;
    private final Style style;
    private static final Style SUPER_SCRIPT = new SuperscriptStyle(null);

    public static ScientificNumberFormatter getSuperscriptInstance(ULocale uLocale) {
        return ScientificNumberFormatter.getInstanceForLocale(uLocale, SUPER_SCRIPT);
    }

    public static ScientificNumberFormatter getSuperscriptInstance(DecimalFormat decimalFormat) {
        return ScientificNumberFormatter.getInstance(decimalFormat, SUPER_SCRIPT);
    }

    public static ScientificNumberFormatter getMarkupInstance(ULocale uLocale, String string, String string2) {
        return ScientificNumberFormatter.getInstanceForLocale(uLocale, new MarkupStyle(string, string2));
    }

    public static ScientificNumberFormatter getMarkupInstance(DecimalFormat decimalFormat, String string, String string2) {
        return ScientificNumberFormatter.getInstance(decimalFormat, new MarkupStyle(string, string2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String format(Object object) {
        DecimalFormat decimalFormat = this.fmt;
        synchronized (decimalFormat) {
            return this.style.format(this.fmt.formatToCharacterIterator(object), this.preExponent);
        }
    }

    private static String getPreExponent(DecimalFormatSymbols decimalFormatSymbols) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decimalFormatSymbols.getExponentMultiplicationSign());
        char[] cArray = decimalFormatSymbols.getDigits();
        stringBuilder.append(cArray[1]).append(cArray[0]);
        return stringBuilder.toString();
    }

    private static ScientificNumberFormatter getInstance(DecimalFormat decimalFormat, Style style) {
        DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
        return new ScientificNumberFormatter((DecimalFormat)decimalFormat.clone(), ScientificNumberFormatter.getPreExponent(decimalFormatSymbols), style);
    }

    private static ScientificNumberFormatter getInstanceForLocale(ULocale uLocale, Style style) {
        DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getScientificInstance(uLocale);
        return new ScientificNumberFormatter(decimalFormat, ScientificNumberFormatter.getPreExponent(decimalFormat.getDecimalFormatSymbols()), style);
    }

    private ScientificNumberFormatter(DecimalFormat decimalFormat, String string, Style style) {
        this.fmt = decimalFormat;
        this.preExponent = string;
        this.style = style;
    }

    private static class SuperscriptStyle
    extends Style {
        private static final char[] SUPERSCRIPT_DIGITS = new char[]{'\u2070', '\u00b9', '\u00b2', '\u00b3', '\u2074', '\u2075', '\u2076', '\u2077', '\u2078', '\u2079'};
        private static final char SUPERSCRIPT_PLUS_SIGN = '\u207a';
        private static final char SUPERSCRIPT_MINUS_SIGN = '\u207b';

        private SuperscriptStyle() {
            super(null);
        }

        @Override
        String format(AttributedCharacterIterator attributedCharacterIterator, String string) {
            int n = 0;
            StringBuilder stringBuilder = new StringBuilder();
            attributedCharacterIterator.first();
            while (attributedCharacterIterator.current() != '\uffff') {
                int n2;
                int n3;
                Map<AttributedCharacterIterator.Attribute, Object> map = attributedCharacterIterator.getAttributes();
                if (map.containsKey(NumberFormat.Field.EXPONENT_SYMBOL)) {
                    SuperscriptStyle.append(attributedCharacterIterator, n, attributedCharacterIterator.getRunStart(NumberFormat.Field.EXPONENT_SYMBOL), stringBuilder);
                    n = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT_SYMBOL);
                    attributedCharacterIterator.setIndex(n);
                    stringBuilder.append(string);
                    continue;
                }
                if (map.containsKey(NumberFormat.Field.EXPONENT_SIGN)) {
                    n3 = attributedCharacterIterator.getRunStart(NumberFormat.Field.EXPONENT_SIGN);
                    n2 = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT_SIGN);
                    int n4 = SuperscriptStyle.char32AtAndAdvance(attributedCharacterIterator);
                    if (StaticUnicodeSets.get(StaticUnicodeSets.Key.MINUS_SIGN).contains(n4)) {
                        SuperscriptStyle.append(attributedCharacterIterator, n, n3, stringBuilder);
                        stringBuilder.append('\u207b');
                    } else if (StaticUnicodeSets.get(StaticUnicodeSets.Key.PLUS_SIGN).contains(n4)) {
                        SuperscriptStyle.append(attributedCharacterIterator, n, n3, stringBuilder);
                        stringBuilder.append('\u207a');
                    } else {
                        throw new IllegalArgumentException();
                    }
                    n = n2;
                    attributedCharacterIterator.setIndex(n);
                    continue;
                }
                if (map.containsKey(NumberFormat.Field.EXPONENT)) {
                    n3 = attributedCharacterIterator.getRunStart(NumberFormat.Field.EXPONENT);
                    n2 = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT);
                    SuperscriptStyle.append(attributedCharacterIterator, n, n3, stringBuilder);
                    SuperscriptStyle.copyAsSuperscript(attributedCharacterIterator, n3, n2, stringBuilder);
                    n = n2;
                    attributedCharacterIterator.setIndex(n);
                    continue;
                }
                attributedCharacterIterator.next();
            }
            SuperscriptStyle.append(attributedCharacterIterator, n, attributedCharacterIterator.getEndIndex(), stringBuilder);
            return stringBuilder.toString();
        }

        private static void copyAsSuperscript(AttributedCharacterIterator attributedCharacterIterator, int n, int n2, StringBuilder stringBuilder) {
            int n3 = attributedCharacterIterator.getIndex();
            attributedCharacterIterator.setIndex(n);
            while (attributedCharacterIterator.getIndex() < n2) {
                int n4 = SuperscriptStyle.char32AtAndAdvance(attributedCharacterIterator);
                int n5 = UCharacter.digit(n4);
                if (n5 < 0) {
                    throw new IllegalArgumentException();
                }
                stringBuilder.append(SUPERSCRIPT_DIGITS[n5]);
            }
            attributedCharacterIterator.setIndex(n3);
        }

        private static int char32AtAndAdvance(AttributedCharacterIterator attributedCharacterIterator) {
            char c = attributedCharacterIterator.current();
            char c2 = attributedCharacterIterator.next();
            if (UCharacter.isHighSurrogate(c) && UCharacter.isLowSurrogate(c2)) {
                attributedCharacterIterator.next();
                return UCharacter.toCodePoint(c, c2);
            }
            return c;
        }

        SuperscriptStyle(1 var1_1) {
            this();
        }
    }

    private static class MarkupStyle
    extends Style {
        private final String beginMarkup;
        private final String endMarkup;

        MarkupStyle(String string, String string2) {
            super(null);
            this.beginMarkup = string;
            this.endMarkup = string2;
        }

        @Override
        String format(AttributedCharacterIterator attributedCharacterIterator, String string) {
            int n = 0;
            StringBuilder stringBuilder = new StringBuilder();
            attributedCharacterIterator.first();
            while (attributedCharacterIterator.current() != '\uffff') {
                Map<AttributedCharacterIterator.Attribute, Object> map = attributedCharacterIterator.getAttributes();
                if (map.containsKey(NumberFormat.Field.EXPONENT_SYMBOL)) {
                    MarkupStyle.append(attributedCharacterIterator, n, attributedCharacterIterator.getRunStart(NumberFormat.Field.EXPONENT_SYMBOL), stringBuilder);
                    n = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT_SYMBOL);
                    attributedCharacterIterator.setIndex(n);
                    stringBuilder.append(string);
                    stringBuilder.append(this.beginMarkup);
                    continue;
                }
                if (map.containsKey(NumberFormat.Field.EXPONENT)) {
                    int n2 = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT);
                    MarkupStyle.append(attributedCharacterIterator, n, n2, stringBuilder);
                    n = n2;
                    attributedCharacterIterator.setIndex(n);
                    stringBuilder.append(this.endMarkup);
                    continue;
                }
                attributedCharacterIterator.next();
            }
            MarkupStyle.append(attributedCharacterIterator, n, attributedCharacterIterator.getEndIndex(), stringBuilder);
            return stringBuilder.toString();
        }
    }

    private static abstract class Style {
        private Style() {
        }

        abstract String format(AttributedCharacterIterator var1, String var2);

        static void append(AttributedCharacterIterator attributedCharacterIterator, int n, int n2, StringBuilder stringBuilder) {
            int n3 = attributedCharacterIterator.getIndex();
            attributedCharacterIterator.setIndex(n);
            for (int i = n; i < n2; ++i) {
                stringBuilder.append(attributedCharacterIterator.current());
                attributedCharacterIterator.next();
            }
            attributedCharacterIterator.setIndex(n3);
        }

        Style(1 var1_1) {
            this();
        }
    }
}

