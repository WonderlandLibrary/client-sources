package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.text.*;
import java.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.lang.*;

public final class ScientificNumberFormatter
{
    private final String preExponent;
    private final DecimalFormat fmt;
    private final Style style;
    private static final Style SUPER_SCRIPT;
    
    public static ScientificNumberFormatter getSuperscriptInstance(final ULocale locale) {
        return getInstanceForLocale(locale, ScientificNumberFormatter.SUPER_SCRIPT);
    }
    
    public static ScientificNumberFormatter getSuperscriptInstance(final DecimalFormat df) {
        return getInstance(df, ScientificNumberFormatter.SUPER_SCRIPT);
    }
    
    public static ScientificNumberFormatter getMarkupInstance(final ULocale locale, final String beginMarkup, final String endMarkup) {
        return getInstanceForLocale(locale, new MarkupStyle(beginMarkup, endMarkup));
    }
    
    public static ScientificNumberFormatter getMarkupInstance(final DecimalFormat df, final String beginMarkup, final String endMarkup) {
        return getInstance(df, new MarkupStyle(beginMarkup, endMarkup));
    }
    
    public String format(final Object number) {
        synchronized (this.fmt) {
            return this.style.format(this.fmt.formatToCharacterIterator(number), this.preExponent);
        }
    }
    
    private static String getPreExponent(final DecimalFormatSymbols dfs) {
        final StringBuilder preExponent = new StringBuilder();
        preExponent.append(dfs.getExponentMultiplicationSign());
        final char[] digits = dfs.getDigits();
        preExponent.append(digits[1]).append(digits[0]);
        return preExponent.toString();
    }
    
    private static ScientificNumberFormatter getInstance(final DecimalFormat decimalFormat, final Style style) {
        final DecimalFormatSymbols dfs = decimalFormat.getDecimalFormatSymbols();
        return new ScientificNumberFormatter((DecimalFormat)decimalFormat.clone(), getPreExponent(dfs), style);
    }
    
    private static ScientificNumberFormatter getInstanceForLocale(final ULocale locale, final Style style) {
        final DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getScientificInstance(locale);
        return new ScientificNumberFormatter(decimalFormat, getPreExponent(decimalFormat.getDecimalFormatSymbols()), style);
    }
    
    private ScientificNumberFormatter(final DecimalFormat decimalFormat, final String preExponent, final Style style) {
        this.fmt = decimalFormat;
        this.preExponent = preExponent;
        this.style = style;
    }
    
    static {
        SUPER_SCRIPT = new SuperscriptStyle();
    }
    
    private abstract static class Style
    {
        abstract String format(final AttributedCharacterIterator p0, final String p1);
        
        static void append(final AttributedCharacterIterator iterator, final int start, final int limit, final StringBuilder result) {
            final int oldIndex = iterator.getIndex();
            iterator.setIndex(start);
            for (int i = start; i < limit; ++i) {
                result.append(iterator.current());
                iterator.next();
            }
            iterator.setIndex(oldIndex);
        }
    }
    
    private static class MarkupStyle extends Style
    {
        private final String beginMarkup;
        private final String endMarkup;
        
        MarkupStyle(final String beginMarkup, final String endMarkup) {
            this.beginMarkup = beginMarkup;
            this.endMarkup = endMarkup;
        }
        
        @Override
        String format(final AttributedCharacterIterator iterator, final String preExponent) {
            int copyFromOffset = 0;
            final StringBuilder result = new StringBuilder();
            iterator.first();
            while (iterator.current() != '\uffff') {
                final Map<AttributedCharacterIterator.Attribute, Object> attributeSet = iterator.getAttributes();
                if (attributeSet.containsKey(NumberFormat.Field.EXPONENT_SYMBOL)) {
                    Style.append(iterator, copyFromOffset, iterator.getRunStart(NumberFormat.Field.EXPONENT_SYMBOL), result);
                    copyFromOffset = iterator.getRunLimit(NumberFormat.Field.EXPONENT_SYMBOL);
                    iterator.setIndex(copyFromOffset);
                    result.append(preExponent);
                    result.append(this.beginMarkup);
                }
                else if (attributeSet.containsKey(NumberFormat.Field.EXPONENT)) {
                    final int limit = iterator.getRunLimit(NumberFormat.Field.EXPONENT);
                    Style.append(iterator, copyFromOffset, limit, result);
                    copyFromOffset = limit;
                    iterator.setIndex(copyFromOffset);
                    result.append(this.endMarkup);
                }
                else {
                    iterator.next();
                }
            }
            Style.append(iterator, copyFromOffset, iterator.getEndIndex(), result);
            return result.toString();
        }
    }
    
    private static class SuperscriptStyle extends Style
    {
        private static final char[] SUPERSCRIPT_DIGITS;
        private static final char SUPERSCRIPT_PLUS_SIGN = '\u207a';
        private static final char SUPERSCRIPT_MINUS_SIGN = '\u207b';
        
        @Override
        String format(final AttributedCharacterIterator iterator, final String preExponent) {
            int copyFromOffset = 0;
            final StringBuilder result = new StringBuilder();
            iterator.first();
            while (iterator.current() != '\uffff') {
                final Map<AttributedCharacterIterator.Attribute, Object> attributeSet = iterator.getAttributes();
                if (attributeSet.containsKey(NumberFormat.Field.EXPONENT_SYMBOL)) {
                    Style.append(iterator, copyFromOffset, iterator.getRunStart(NumberFormat.Field.EXPONENT_SYMBOL), result);
                    copyFromOffset = iterator.getRunLimit(NumberFormat.Field.EXPONENT_SYMBOL);
                    iterator.setIndex(copyFromOffset);
                    result.append(preExponent);
                }
                else if (attributeSet.containsKey(NumberFormat.Field.EXPONENT_SIGN)) {
                    final int start = iterator.getRunStart(NumberFormat.Field.EXPONENT_SIGN);
                    final int limit = iterator.getRunLimit(NumberFormat.Field.EXPONENT_SIGN);
                    final int aChar = char32AtAndAdvance(iterator);
                    if (StaticUnicodeSets.get(StaticUnicodeSets.Key.MINUS_SIGN).contains(aChar)) {
                        Style.append(iterator, copyFromOffset, start, result);
                        result.append('\u207b');
                    }
                    else {
                        if (!StaticUnicodeSets.get(StaticUnicodeSets.Key.PLUS_SIGN).contains(aChar)) {
                            throw new IllegalArgumentException();
                        }
                        Style.append(iterator, copyFromOffset, start, result);
                        result.append('\u207a');
                    }
                    copyFromOffset = limit;
                    iterator.setIndex(copyFromOffset);
                }
                else if (attributeSet.containsKey(NumberFormat.Field.EXPONENT)) {
                    final int start = iterator.getRunStart(NumberFormat.Field.EXPONENT);
                    final int limit = iterator.getRunLimit(NumberFormat.Field.EXPONENT);
                    Style.append(iterator, copyFromOffset, start, result);
                    copyAsSuperscript(iterator, start, limit, result);
                    copyFromOffset = limit;
                    iterator.setIndex(copyFromOffset);
                }
                else {
                    iterator.next();
                }
            }
            Style.append(iterator, copyFromOffset, iterator.getEndIndex(), result);
            return result.toString();
        }
        
        private static void copyAsSuperscript(final AttributedCharacterIterator iterator, final int start, final int limit, final StringBuilder result) {
            final int oldIndex = iterator.getIndex();
            iterator.setIndex(start);
            while (iterator.getIndex() < limit) {
                final int aChar = char32AtAndAdvance(iterator);
                final int digit = UCharacter.digit(aChar);
                if (digit < 0) {
                    throw new IllegalArgumentException();
                }
                result.append(SuperscriptStyle.SUPERSCRIPT_DIGITS[digit]);
            }
            iterator.setIndex(oldIndex);
        }
        
        private static int char32AtAndAdvance(final AttributedCharacterIterator iterator) {
            final char c1 = iterator.current();
            final char c2 = iterator.next();
            if (UCharacter.isHighSurrogate(c1) && UCharacter.isLowSurrogate(c2)) {
                iterator.next();
                return UCharacter.toCodePoint(c1, c2);
            }
            return c1;
        }
        
        static {
            SUPERSCRIPT_DIGITS = new char[] { '\u2070', '¹', '²', '³', '\u2074', '\u2075', '\u2076', '\u2077', '\u2078', '\u2079' };
        }
    }
}
