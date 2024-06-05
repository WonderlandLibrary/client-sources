package com.ibm.icu.impl.number;

import com.ibm.icu.util.*;
import com.ibm.icu.text.*;

public class CustomSymbolCurrency extends Currency
{
    private static final long serialVersionUID = 2497493016770137670L;
    private String symbol1;
    private String symbol2;
    
    public static Currency resolve(Currency currency, final ULocale locale, final DecimalFormatSymbols symbols) {
        if (currency == null) {
            currency = symbols.getCurrency();
        }
        if (currency == null) {
            return Currency.getInstance("XXX");
        }
        if (!currency.equals(symbols.getCurrency())) {
            return currency;
        }
        final String currency1Sym = symbols.getCurrencySymbol();
        final String currency2Sym = symbols.getInternationalCurrencySymbol();
        final String currency2 = currency.getName(symbols.getULocale(), 0, null);
        final String currency3 = currency.getCurrencyCode();
        if (!currency2.equals(currency1Sym) || !currency3.equals(currency2Sym)) {
            return new CustomSymbolCurrency(currency3, currency1Sym, currency2Sym);
        }
        return currency;
    }
    
    public CustomSymbolCurrency(final String isoCode, final String currency1Sym, final String currency2Sym) {
        super(isoCode);
        this.symbol1 = currency1Sym;
        this.symbol2 = currency2Sym;
    }
    
    @Override
    public String getName(final ULocale locale, final int nameStyle, final boolean[] isChoiceFormat) {
        if (nameStyle == 0) {
            return this.symbol1;
        }
        return super.getName(locale, nameStyle, isChoiceFormat);
    }
    
    @Override
    public String getName(final ULocale locale, final int nameStyle, final String pluralCount, final boolean[] isChoiceFormat) {
        return super.getName(locale, nameStyle, pluralCount, isChoiceFormat);
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
    public boolean equals(final Object other) {
        return super.equals(other) && ((CustomSymbolCurrency)other).symbol1.equals(this.symbol1) && ((CustomSymbolCurrency)other).symbol2.equals(this.symbol2);
    }
}
