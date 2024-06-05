package com.ibm.icu.util;

public class CurrencyAmount extends Measure
{
    public CurrencyAmount(final Number number, final Currency currency) {
        super(number, currency);
    }
    
    public CurrencyAmount(final double number, final Currency currency) {
        super(new Double(number), currency);
    }
    
    public CurrencyAmount(final Number number, final java.util.Currency currency) {
        this(number, Currency.fromJavaCurrency(currency));
    }
    
    public CurrencyAmount(final double number, final java.util.Currency currency) {
        this(number, Currency.fromJavaCurrency(currency));
    }
    
    public Currency getCurrency() {
        return (Currency)this.getUnit();
    }
}
