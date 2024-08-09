/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.number.ConstantMultiFieldModifier;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.UnicodeSet;
import java.text.Format;

public class CurrencySpacingEnabledModifier
extends ConstantMultiFieldModifier {
    private static final UnicodeSet UNISET_DIGIT = new UnicodeSet("[:digit:]").freeze();
    private static final UnicodeSet UNISET_NOTS = new UnicodeSet("[:^S:]").freeze();
    static final byte PREFIX = 0;
    static final byte SUFFIX = 1;
    static final short IN_CURRENCY = 0;
    static final short IN_NUMBER = 1;
    private final UnicodeSet afterPrefixUnicodeSet;
    private final String afterPrefixInsert;
    private final UnicodeSet beforeSuffixUnicodeSet;
    private final String beforeSuffixInsert;

    public CurrencySpacingEnabledModifier(FormattedStringBuilder formattedStringBuilder, FormattedStringBuilder formattedStringBuilder2, boolean bl, boolean bl2, DecimalFormatSymbols decimalFormatSymbols) {
        super(formattedStringBuilder, formattedStringBuilder2, bl, bl2);
        UnicodeSet unicodeSet;
        int n;
        if (formattedStringBuilder.length() > 0 && formattedStringBuilder.fieldAt(formattedStringBuilder.length() - 1) == NumberFormat.Field.CURRENCY) {
            n = formattedStringBuilder.getLastCodePoint();
            unicodeSet = CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)0, (byte)0);
            if (unicodeSet.contains(n)) {
                this.afterPrefixUnicodeSet = CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)1, (byte)0);
                this.afterPrefixUnicodeSet.freeze();
                this.afterPrefixInsert = CurrencySpacingEnabledModifier.getInsertString(decimalFormatSymbols, (byte)0);
            } else {
                this.afterPrefixUnicodeSet = null;
                this.afterPrefixInsert = null;
            }
        } else {
            this.afterPrefixUnicodeSet = null;
            this.afterPrefixInsert = null;
        }
        if (formattedStringBuilder2.length() > 0 && formattedStringBuilder2.fieldAt(0) == NumberFormat.Field.CURRENCY) {
            n = formattedStringBuilder2.getLastCodePoint();
            unicodeSet = CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)0, (byte)1);
            if (unicodeSet.contains(n)) {
                this.beforeSuffixUnicodeSet = CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)1, (byte)1);
                this.beforeSuffixUnicodeSet.freeze();
                this.beforeSuffixInsert = CurrencySpacingEnabledModifier.getInsertString(decimalFormatSymbols, (byte)1);
            } else {
                this.beforeSuffixUnicodeSet = null;
                this.beforeSuffixInsert = null;
            }
        } else {
            this.beforeSuffixUnicodeSet = null;
            this.beforeSuffixInsert = null;
        }
    }

    @Override
    public int apply(FormattedStringBuilder formattedStringBuilder, int n, int n2) {
        int n3 = 0;
        if (n2 - n > 0 && this.afterPrefixUnicodeSet != null && this.afterPrefixUnicodeSet.contains(formattedStringBuilder.codePointAt(n))) {
            n3 += formattedStringBuilder.insert(n, this.afterPrefixInsert, null);
        }
        if (n2 - n > 0 && this.beforeSuffixUnicodeSet != null && this.beforeSuffixUnicodeSet.contains(formattedStringBuilder.codePointBefore(n2))) {
            n3 += formattedStringBuilder.insert(n2 + n3, this.beforeSuffixInsert, null);
        }
        n3 += super.apply(formattedStringBuilder, n, n2 + n3);
        return n3;
    }

    public static int applyCurrencySpacing(FormattedStringBuilder formattedStringBuilder, int n, int n2, int n3, int n4, DecimalFormatSymbols decimalFormatSymbols) {
        boolean bl;
        int n5 = 0;
        boolean bl2 = n2 > 0;
        boolean bl3 = n4 > 0;
        boolean bl4 = bl = n3 - n - n2 > 0;
        if (bl2 && bl) {
            n5 += CurrencySpacingEnabledModifier.applyCurrencySpacingAffix(formattedStringBuilder, n + n2, (byte)0, decimalFormatSymbols);
        }
        if (bl3 && bl) {
            n5 += CurrencySpacingEnabledModifier.applyCurrencySpacingAffix(formattedStringBuilder, n3 + n5, (byte)1, decimalFormatSymbols);
        }
        return n5;
    }

    private static int applyCurrencySpacingAffix(FormattedStringBuilder formattedStringBuilder, int n, byte by, DecimalFormatSymbols decimalFormatSymbols) {
        Format.Field field;
        Format.Field field2 = field = by == 0 ? formattedStringBuilder.fieldAt(n - 1) : formattedStringBuilder.fieldAt(n);
        if (field != NumberFormat.Field.CURRENCY) {
            return 1;
        }
        int n2 = by == 0 ? formattedStringBuilder.codePointBefore(n) : formattedStringBuilder.codePointAt(n);
        UnicodeSet unicodeSet = CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)0, by);
        if (!unicodeSet.contains(n2)) {
            return 1;
        }
        int n3 = by == 0 ? formattedStringBuilder.codePointAt(n) : formattedStringBuilder.codePointBefore(n);
        UnicodeSet unicodeSet2 = CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)1, by);
        if (!unicodeSet2.contains(n3)) {
            return 1;
        }
        String string = CurrencySpacingEnabledModifier.getInsertString(decimalFormatSymbols, by);
        return formattedStringBuilder.insert(n, string, null);
    }

    private static UnicodeSet getUnicodeSet(DecimalFormatSymbols decimalFormatSymbols, short s, byte by) {
        String string = decimalFormatSymbols.getPatternForCurrencySpacing(s == 0 ? 0 : 1, by == 1);
        if (string.equals("[:digit:]")) {
            return UNISET_DIGIT;
        }
        if (string.equals("[:^S:]")) {
            return UNISET_NOTS;
        }
        return new UnicodeSet(string);
    }

    private static String getInsertString(DecimalFormatSymbols decimalFormatSymbols, byte by) {
        return decimalFormatSymbols.getPatternForCurrencySpacing(2, by == 1);
    }
}

