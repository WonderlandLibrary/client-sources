package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public class IgnorablesMatcher extends SymbolMatcher implements NumberParseMatcher.Flexible
{
    public static final IgnorablesMatcher DEFAULT;
    public static final IgnorablesMatcher STRICT;
    
    public static IgnorablesMatcher getInstance(final UnicodeSet ignorables) {
        assert ignorables.isFrozen();
        return new IgnorablesMatcher(ignorables);
    }
    
    private IgnorablesMatcher(final UnicodeSet ignorables) {
        super("", ignorables);
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
        return "<IgnorablesMatcher>";
    }
    
    static {
        DEFAULT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.DEFAULT_IGNORABLES));
        STRICT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.STRICT_IGNORABLES));
    }
}
