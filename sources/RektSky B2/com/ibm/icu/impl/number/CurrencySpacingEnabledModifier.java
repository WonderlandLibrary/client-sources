package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;

public class CurrencySpacingEnabledModifier extends ConstantMultiFieldModifier
{
    private static final UnicodeSet UNISET_DIGIT;
    private static final UnicodeSet UNISET_NOTS;
    static final byte PREFIX = 0;
    static final byte SUFFIX = 1;
    static final short IN_CURRENCY = 0;
    static final short IN_NUMBER = 1;
    private final UnicodeSet afterPrefixUnicodeSet;
    private final String afterPrefixInsert;
    private final UnicodeSet beforeSuffixUnicodeSet;
    private final String beforeSuffixInsert;
    
    public CurrencySpacingEnabledModifier(final NumberStringBuilder prefix, final NumberStringBuilder suffix, final boolean overwrite, final boolean strong, final DecimalFormatSymbols symbols) {
        super(prefix, suffix, overwrite, strong);
        if (prefix.length() > 0 && prefix.fieldAt(prefix.length() - 1) == NumberFormat.Field.CURRENCY) {
            final int prefixCp = prefix.getLastCodePoint();
            final UnicodeSet prefixUnicodeSet = getUnicodeSet(symbols, (short)0, (byte)0);
            if (prefixUnicodeSet.contains(prefixCp)) {
                (this.afterPrefixUnicodeSet = getUnicodeSet(symbols, (short)1, (byte)0)).freeze();
                this.afterPrefixInsert = getInsertString(symbols, (byte)0);
            }
            else {
                this.afterPrefixUnicodeSet = null;
                this.afterPrefixInsert = null;
            }
        }
        else {
            this.afterPrefixUnicodeSet = null;
            this.afterPrefixInsert = null;
        }
        if (suffix.length() > 0 && suffix.fieldAt(0) == NumberFormat.Field.CURRENCY) {
            final int suffixCp = suffix.getLastCodePoint();
            final UnicodeSet suffixUnicodeSet = getUnicodeSet(symbols, (short)0, (byte)1);
            if (suffixUnicodeSet.contains(suffixCp)) {
                (this.beforeSuffixUnicodeSet = getUnicodeSet(symbols, (short)1, (byte)1)).freeze();
                this.beforeSuffixInsert = getInsertString(symbols, (byte)1);
            }
            else {
                this.beforeSuffixUnicodeSet = null;
                this.beforeSuffixInsert = null;
            }
        }
        else {
            this.beforeSuffixUnicodeSet = null;
            this.beforeSuffixInsert = null;
        }
    }
    
    @Override
    public int apply(final NumberStringBuilder output, final int leftIndex, final int rightIndex) {
        int length = 0;
        if (rightIndex - leftIndex > 0 && this.afterPrefixUnicodeSet != null && this.afterPrefixUnicodeSet.contains(output.codePointAt(leftIndex))) {
            length += output.insert(leftIndex, this.afterPrefixInsert, null);
        }
        if (rightIndex - leftIndex > 0 && this.beforeSuffixUnicodeSet != null && this.beforeSuffixUnicodeSet.contains(output.codePointBefore(rightIndex))) {
            length += output.insert(rightIndex + length, this.beforeSuffixInsert, null);
        }
        length += super.apply(output, leftIndex, rightIndex + length);
        return length;
    }
    
    public static int applyCurrencySpacing(final NumberStringBuilder output, final int prefixStart, final int prefixLen, final int suffixStart, final int suffixLen, final DecimalFormatSymbols symbols) {
        int length = 0;
        final boolean hasPrefix = prefixLen > 0;
        final boolean hasSuffix = suffixLen > 0;
        final boolean hasNumber = suffixStart - prefixStart - prefixLen > 0;
        if (hasPrefix && hasNumber) {
            length += applyCurrencySpacingAffix(output, prefixStart + prefixLen, (byte)0, symbols);
        }
        if (hasSuffix && hasNumber) {
            length += applyCurrencySpacingAffix(output, suffixStart + length, (byte)1, symbols);
        }
        return length;
    }
    
    private static int applyCurrencySpacingAffix(final NumberStringBuilder output, final int index, final byte affix, final DecimalFormatSymbols symbols) {
        final NumberFormat.Field affixField = (affix == 0) ? output.fieldAt(index - 1) : output.fieldAt(index);
        if (affixField != NumberFormat.Field.CURRENCY) {
            return 0;
        }
        final int affixCp = (affix == 0) ? output.codePointBefore(index) : output.codePointAt(index);
        final UnicodeSet affixUniset = getUnicodeSet(symbols, (short)0, affix);
        if (!affixUniset.contains(affixCp)) {
            return 0;
        }
        final int numberCp = (affix == 0) ? output.codePointAt(index) : output.codePointBefore(index);
        final UnicodeSet numberUniset = getUnicodeSet(symbols, (short)1, affix);
        if (!numberUniset.contains(numberCp)) {
            return 0;
        }
        final String spacingString = getInsertString(symbols, affix);
        return output.insert(index, spacingString, null);
    }
    
    private static UnicodeSet getUnicodeSet(final DecimalFormatSymbols symbols, final short position, final byte affix) {
        final String pattern = symbols.getPatternForCurrencySpacing((position != 0) ? 1 : 0, affix == 1);
        if (pattern.equals("[:digit:]")) {
            return CurrencySpacingEnabledModifier.UNISET_DIGIT;
        }
        if (pattern.equals("[:^S:]")) {
            return CurrencySpacingEnabledModifier.UNISET_NOTS;
        }
        return new UnicodeSet(pattern);
    }
    
    private static String getInsertString(final DecimalFormatSymbols symbols, final byte affix) {
        return symbols.getPatternForCurrencySpacing(2, affix == 1);
    }
    
    static {
        UNISET_DIGIT = new UnicodeSet("[:digit:]").freeze();
        UNISET_NOTS = new UnicodeSet("[:^S:]").freeze();
    }
}
