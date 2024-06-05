package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public class PercentMatcher extends SymbolMatcher
{
    private static final PercentMatcher DEFAULT;
    
    public static PercentMatcher getInstance(final DecimalFormatSymbols symbols) {
        final String symbolString = symbols.getPercentString();
        if (PercentMatcher.DEFAULT.uniSet.contains(symbolString)) {
            return PercentMatcher.DEFAULT;
        }
        return new PercentMatcher(symbolString);
    }
    
    private PercentMatcher(final String symbolString) {
        super(symbolString, PercentMatcher.DEFAULT.uniSet);
    }
    
    private PercentMatcher() {
        super(StaticUnicodeSets.Key.PERCENT_SIGN);
    }
    
    @Override
    protected boolean isDisabled(final ParsedNumber result) {
        return 0x0 != (result.flags & 0x2);
    }
    
    @Override
    protected void accept(final StringSegment segment, final ParsedNumber result) {
        result.flags |= 0x2;
        result.setCharsConsumed(segment);
    }
    
    @Override
    public String toString() {
        return "<PercentMatcher>";
    }
    
    static {
        DEFAULT = new PercentMatcher();
    }
}
