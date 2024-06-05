package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;

public class AffixTokenMatcherFactory
{
    public Currency currency;
    public DecimalFormatSymbols symbols;
    public IgnorablesMatcher ignorables;
    public ULocale locale;
    public int parseFlags;
    
    public MinusSignMatcher minusSign() {
        return MinusSignMatcher.getInstance(this.symbols, true);
    }
    
    public PlusSignMatcher plusSign() {
        return PlusSignMatcher.getInstance(this.symbols, true);
    }
    
    public PercentMatcher percent() {
        return PercentMatcher.getInstance(this.symbols);
    }
    
    public PermilleMatcher permille() {
        return PermilleMatcher.getInstance(this.symbols);
    }
    
    public CombinedCurrencyMatcher currency() {
        return CombinedCurrencyMatcher.getInstance(this.currency, this.symbols, this.parseFlags);
    }
    
    public IgnorablesMatcher ignorables() {
        return this.ignorables;
    }
}
