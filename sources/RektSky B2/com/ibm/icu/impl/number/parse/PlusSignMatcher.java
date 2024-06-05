package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public class PlusSignMatcher extends SymbolMatcher
{
    private static final PlusSignMatcher DEFAULT;
    private static final PlusSignMatcher DEFAULT_ALLOW_TRAILING;
    private final boolean allowTrailing;
    
    public static PlusSignMatcher getInstance(final DecimalFormatSymbols symbols, final boolean allowTrailing) {
        final String symbolString = symbols.getPlusSignString();
        if (ParsingUtils.safeContains(PlusSignMatcher.DEFAULT.uniSet, symbolString)) {
            return allowTrailing ? PlusSignMatcher.DEFAULT_ALLOW_TRAILING : PlusSignMatcher.DEFAULT;
        }
        return new PlusSignMatcher(symbolString, allowTrailing);
    }
    
    private PlusSignMatcher(final String symbolString, final boolean allowTrailing) {
        super(symbolString, PlusSignMatcher.DEFAULT.uniSet);
        this.allowTrailing = allowTrailing;
    }
    
    private PlusSignMatcher(final boolean allowTrailing) {
        super(StaticUnicodeSets.Key.PLUS_SIGN);
        this.allowTrailing = allowTrailing;
    }
    
    @Override
    protected boolean isDisabled(final ParsedNumber result) {
        return !this.allowTrailing && result.seenNumber();
    }
    
    @Override
    protected void accept(final StringSegment segment, final ParsedNumber result) {
        result.setCharsConsumed(segment);
    }
    
    @Override
    public String toString() {
        return "<PlusSignMatcher>";
    }
    
    static {
        DEFAULT = new PlusSignMatcher(false);
        DEFAULT_ALLOW_TRAILING = new PlusSignMatcher(true);
    }
}
