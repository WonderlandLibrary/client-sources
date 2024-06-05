package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public class InfinityMatcher extends SymbolMatcher
{
    private static final InfinityMatcher DEFAULT;
    
    public static InfinityMatcher getInstance(final DecimalFormatSymbols symbols) {
        final String symbolString = symbols.getInfinity();
        if (ParsingUtils.safeContains(InfinityMatcher.DEFAULT.uniSet, symbolString)) {
            return InfinityMatcher.DEFAULT;
        }
        return new InfinityMatcher(symbolString);
    }
    
    private InfinityMatcher(final String symbolString) {
        super(symbolString, InfinityMatcher.DEFAULT.uniSet);
    }
    
    private InfinityMatcher() {
        super(StaticUnicodeSets.Key.INFINITY);
    }
    
    @Override
    protected boolean isDisabled(final ParsedNumber result) {
        return 0x0 != (result.flags & 0x80);
    }
    
    @Override
    protected void accept(final StringSegment segment, final ParsedNumber result) {
        result.flags |= 0x80;
        result.setCharsConsumed(segment);
    }
    
    @Override
    public String toString() {
        return "<InfinityMatcher>";
    }
    
    static {
        DEFAULT = new InfinityMatcher();
    }
}
