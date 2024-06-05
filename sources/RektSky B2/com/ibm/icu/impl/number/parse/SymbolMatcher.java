package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public abstract class SymbolMatcher implements NumberParseMatcher
{
    protected final String string;
    protected final UnicodeSet uniSet;
    
    protected SymbolMatcher(final String symbolString, final UnicodeSet symbolUniSet) {
        this.string = symbolString;
        this.uniSet = symbolUniSet;
    }
    
    protected SymbolMatcher(final StaticUnicodeSets.Key key) {
        this.string = "";
        this.uniSet = StaticUnicodeSets.get(key);
    }
    
    public UnicodeSet getSet() {
        return this.uniSet;
    }
    
    @Override
    public boolean match(final StringSegment segment, final ParsedNumber result) {
        if (this.isDisabled(result)) {
            return false;
        }
        int overlap = 0;
        if (!this.string.isEmpty()) {
            overlap = segment.getCommonPrefixLength(this.string);
            if (overlap == this.string.length()) {
                segment.adjustOffset(this.string.length());
                this.accept(segment, result);
                return false;
            }
        }
        if (segment.startsWith(this.uniSet)) {
            segment.adjustOffsetByCodePoint();
            this.accept(segment, result);
            return false;
        }
        return overlap == segment.length();
    }
    
    @Override
    public boolean smokeTest(final StringSegment segment) {
        return segment.startsWith(this.uniSet) || segment.startsWith(this.string);
    }
    
    @Override
    public void postProcess(final ParsedNumber result) {
    }
    
    protected abstract boolean isDisabled(final ParsedNumber p0);
    
    protected abstract void accept(final StringSegment p0, final ParsedNumber p1);
}
