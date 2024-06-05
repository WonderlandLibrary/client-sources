package com.ibm.icu.text;

import java.text.*;
import java.io.*;
import com.ibm.icu.util.*;

class CurrencyFormat extends MeasureFormat
{
    static final long serialVersionUID = -931679363692504634L;
    
    public CurrencyFormat(final ULocale locale) {
        super(locale, FormatWidth.DEFAULT_CURRENCY);
    }
    
    @Override
    public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition pos) {
        if (!(obj instanceof CurrencyAmount)) {
            throw new IllegalArgumentException("Invalid type: " + obj.getClass().getName());
        }
        return super.format(obj, toAppendTo, pos);
    }
    
    @Override
    public CurrencyAmount parseObject(final String source, final ParsePosition pos) {
        return this.getNumberFormatInternal().parseCurrency(source, pos);
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return this.toCurrencyProxy();
    }
    
    private Object readResolve() throws ObjectStreamException {
        return new CurrencyFormat(this.getLocale(ULocale.ACTUAL_LOCALE));
    }
}
