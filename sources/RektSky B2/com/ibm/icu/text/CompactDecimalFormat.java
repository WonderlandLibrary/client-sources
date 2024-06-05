package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.impl.number.*;
import java.text.*;
import com.ibm.icu.util.*;

public class CompactDecimalFormat extends DecimalFormat
{
    private static final long serialVersionUID = 4716293295276629682L;
    
    public static CompactDecimalFormat getInstance(final ULocale locale, final CompactStyle style) {
        return new CompactDecimalFormat(locale, style);
    }
    
    public static CompactDecimalFormat getInstance(final Locale locale, final CompactStyle style) {
        return new CompactDecimalFormat(ULocale.forLocale(locale), style);
    }
    
    CompactDecimalFormat(final ULocale locale, final CompactStyle style) {
        this.symbols = DecimalFormatSymbols.getInstance(locale);
        (this.properties = new DecimalFormatProperties()).setCompactStyle(style);
        this.properties.setGroupingSize(-2);
        this.properties.setMinimumGroupingDigits(2);
        this.exportedProperties = new DecimalFormatProperties();
        this.refreshFormatter();
    }
    
    @Override
    public Number parse(final String text, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public CurrencyAmount parseCurrency(final CharSequence text, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
    
    public enum CompactStyle
    {
        SHORT, 
        LONG;
    }
}
