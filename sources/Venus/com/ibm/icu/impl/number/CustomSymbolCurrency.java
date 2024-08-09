/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ULocale;

public class CustomSymbolCurrency
extends Currency {
    private static final long serialVersionUID = 2497493016770137670L;
    private String symbol1;
    private String symbol2;

    public static Currency resolve(Currency currency, ULocale uLocale, DecimalFormatSymbols decimalFormatSymbols) {
        if (currency == null) {
            currency = decimalFormatSymbols.getCurrency();
        }
        if (currency == null) {
            return Currency.getInstance("XXX");
        }
        if (!currency.equals(decimalFormatSymbols.getCurrency())) {
            return currency;
        }
        String string = decimalFormatSymbols.getCurrencySymbol();
        String string2 = decimalFormatSymbols.getInternationalCurrencySymbol();
        String string3 = currency.getName(decimalFormatSymbols.getULocale(), 0, null);
        String string4 = currency.getCurrencyCode();
        if (!string3.equals(string) || !string4.equals(string2)) {
            return new CustomSymbolCurrency(string4, string, string2);
        }
        return currency;
    }

    public CustomSymbolCurrency(String string, String string2, String string3) {
        super(string);
        this.symbol1 = string2;
        this.symbol2 = string3;
    }

    @Override
    public String getName(ULocale uLocale, int n, boolean[] blArray) {
        if (n == 0) {
            if (blArray != null) {
                blArray[0] = false;
            }
            return this.symbol1;
        }
        return super.getName(uLocale, n, blArray);
    }

    @Override
    public String getName(ULocale uLocale, int n, String string, boolean[] blArray) {
        return super.getName(uLocale, n, string, blArray);
    }

    @Override
    public String getCurrencyCode() {
        return this.symbol2;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.symbol1.hashCode() ^ this.symbol2.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) && ((CustomSymbolCurrency)object).symbol1.equals(this.symbol1) && ((CustomSymbolCurrency)object).symbol2.equals(this.symbol2);
    }
}

