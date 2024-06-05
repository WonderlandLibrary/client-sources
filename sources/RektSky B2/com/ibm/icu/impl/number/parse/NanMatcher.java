package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public class NanMatcher extends SymbolMatcher
{
    private static final NanMatcher DEFAULT;
    
    public static NanMatcher getInstance(final DecimalFormatSymbols symbols, final int parseFlags) {
        final String symbolString = symbols.getNaN();
        if (NanMatcher.DEFAULT.string.equals(symbolString)) {
            return NanMatcher.DEFAULT;
        }
        return new NanMatcher(symbolString);
    }
    
    private NanMatcher(final String symbolString) {
        super(symbolString, UnicodeSet.EMPTY);
    }
    
    @Override
    protected boolean isDisabled(final ParsedNumber result) {
        return result.seenNumber();
    }
    
    @Override
    protected void accept(final StringSegment segment, final ParsedNumber result) {
        result.flags |= 0x40;
        result.setCharsConsumed(segment);
    }
    
    @Override
    public String toString() {
        return "<NanMatcher>";
    }
    
    static {
        DEFAULT = new NanMatcher("NaN");
    }
}
