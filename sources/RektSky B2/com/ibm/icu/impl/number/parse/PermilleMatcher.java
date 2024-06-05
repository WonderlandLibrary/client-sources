package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public class PermilleMatcher extends SymbolMatcher
{
    private static final PermilleMatcher DEFAULT;
    
    public static PermilleMatcher getInstance(final DecimalFormatSymbols symbols) {
        final String symbolString = symbols.getPerMillString();
        if (PermilleMatcher.DEFAULT.uniSet.contains(symbolString)) {
            return PermilleMatcher.DEFAULT;
        }
        return new PermilleMatcher(symbolString);
    }
    
    private PermilleMatcher(final String symbolString) {
        super(symbolString, PermilleMatcher.DEFAULT.uniSet);
    }
    
    private PermilleMatcher() {
        super(StaticUnicodeSets.Key.PERMILLE_SIGN);
    }
    
    @Override
    protected boolean isDisabled(final ParsedNumber result) {
        return 0x0 != (result.flags & 0x4);
    }
    
    @Override
    protected void accept(final StringSegment segment, final ParsedNumber result) {
        result.flags |= 0x4;
        result.setCharsConsumed(segment);
    }
    
    @Override
    public String toString() {
        return "<PermilleMatcher>";
    }
    
    static {
        DEFAULT = new PermilleMatcher();
    }
}
