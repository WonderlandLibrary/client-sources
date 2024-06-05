package com.ibm.icu.number;

import com.ibm.icu.util.*;

public abstract class CurrencyPrecision extends Precision
{
    CurrencyPrecision() {
    }
    
    public Precision withCurrency(final Currency currency) {
        if (currency != null) {
            return Precision.constructFromCurrency(this, currency);
        }
        throw new IllegalArgumentException("Currency must not be null");
    }
}
