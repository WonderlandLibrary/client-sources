package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public class MinusSignMatcher extends SymbolMatcher
{
    private static final MinusSignMatcher DEFAULT;
    private static final MinusSignMatcher DEFAULT_ALLOW_TRAILING;
    private final boolean allowTrailing;
    
    public static MinusSignMatcher getInstance(final DecimalFormatSymbols symbols, final boolean allowTrailing) {
        final String symbolString = symbols.getMinusSignString();
        if (ParsingUtils.safeContains(MinusSignMatcher.DEFAULT.uniSet, symbolString)) {
            return allowTrailing ? MinusSignMatcher.DEFAULT_ALLOW_TRAILING : MinusSignMatcher.DEFAULT;
        }
        return new MinusSignMatcher(symbolString, allowTrailing);
    }
    
    private MinusSignMatcher(final String symbolString, final boolean allowTrailing) {
        super(symbolString, MinusSignMatcher.DEFAULT.uniSet);
        this.allowTrailing = allowTrailing;
    }
    
    private MinusSignMatcher(final boolean allowTrailing) {
        super(StaticUnicodeSets.Key.MINUS_SIGN);
        this.allowTrailing = allowTrailing;
    }
    
    @Override
    protected boolean isDisabled(final ParsedNumber result) {
        return !this.allowTrailing && result.seenNumber();
    }
    
    @Override
    protected void accept(final StringSegment segment, final ParsedNumber result) {
        result.flags |= 0x1;
        result.setCharsConsumed(segment);
    }
    
    @Override
    public String toString() {
        return "<MinusSignMatcher>";
    }
    
    static {
        DEFAULT = new MinusSignMatcher(false);
        DEFAULT_ALLOW_TRAILING = new MinusSignMatcher(true);
    }
}
