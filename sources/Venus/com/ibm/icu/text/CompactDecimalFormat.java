/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.CurrencyAmount;
import com.ibm.icu.util.ULocale;
import java.text.ParsePosition;
import java.util.Locale;

public class CompactDecimalFormat
extends DecimalFormat {
    private static final long serialVersionUID = 4716293295276629682L;

    public static CompactDecimalFormat getInstance(ULocale uLocale, CompactStyle compactStyle) {
        return new CompactDecimalFormat(uLocale, compactStyle);
    }

    public static CompactDecimalFormat getInstance(Locale locale, CompactStyle compactStyle) {
        return new CompactDecimalFormat(ULocale.forLocale(locale), compactStyle);
    }

    CompactDecimalFormat(ULocale uLocale, CompactStyle compactStyle) {
        this.symbols = DecimalFormatSymbols.getInstance(uLocale);
        this.properties = new DecimalFormatProperties();
        this.properties.setCompactStyle(compactStyle);
        this.properties.setGroupingSize(-2);
        this.properties.setMinimumGroupingDigits(2);
        this.exportedProperties = new DecimalFormatProperties();
        this.refreshFormatter();
    }

    @Override
    public Number parse(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CurrencyAmount parseCurrency(CharSequence charSequence, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    public static enum CompactStyle {
        SHORT,
        LONG;

    }
}

