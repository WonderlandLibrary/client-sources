package com.ibm.icu.impl.number.parse;

public class RequireCurrencyValidator extends ValidationMatcher
{
    @Override
    public void postProcess(final ParsedNumber result) {
        if (result.currencyCode == null) {
            result.flags |= 0x100;
        }
    }
    
    @Override
    public String toString() {
        return "<RequireCurrency>";
    }
}
