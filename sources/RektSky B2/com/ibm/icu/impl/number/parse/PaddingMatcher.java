package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public class PaddingMatcher extends SymbolMatcher implements NumberParseMatcher.Flexible
{
    public static PaddingMatcher getInstance(final String padString) {
        return new PaddingMatcher(padString);
    }
    
    private PaddingMatcher(final String symbolString) {
        super(symbolString, UnicodeSet.EMPTY);
    }
    
    @Override
    protected boolean isDisabled(final ParsedNumber result) {
        return false;
    }
    
    @Override
    protected void accept(final StringSegment segment, final ParsedNumber result) {
    }
    
    @Override
    public String toString() {
        return "<PaddingMatcher>";
    }
}
