/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.MeasureFormat;
import com.ibm.icu.util.CurrencyAmount;
import com.ibm.icu.util.Measure;
import com.ibm.icu.util.ULocale;
import java.io.ObjectStreamException;
import java.text.FieldPosition;
import java.text.ParsePosition;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class CurrencyFormat
extends MeasureFormat {
    static final long serialVersionUID = -931679363692504634L;

    public CurrencyFormat(ULocale uLocale) {
        super(uLocale, MeasureFormat.FormatWidth.DEFAULT_CURRENCY);
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (!(object instanceof CurrencyAmount)) {
            throw new IllegalArgumentException("Invalid type: " + object.getClass().getName());
        }
        return super.format(object, stringBuffer, fieldPosition);
    }

    @Override
    public CurrencyAmount parseObject(String string, ParsePosition parsePosition) {
        return this.getNumberFormatInternal().parseCurrency(string, parsePosition);
    }

    private Object writeReplace() throws ObjectStreamException {
        return this.toCurrencyProxy();
    }

    private Object readResolve() throws ObjectStreamException {
        return new CurrencyFormat(this.getLocale(ULocale.ACTUAL_LOCALE));
    }

    @Override
    public Measure parseObject(String string, ParsePosition parsePosition) {
        return this.parseObject(string, parsePosition);
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parseObject(string, parsePosition);
    }
}

