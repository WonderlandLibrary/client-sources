package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.*;

public class CodePointMatcher implements NumberParseMatcher
{
    private final int cp;
    
    public static CodePointMatcher getInstance(final int cp) {
        return new CodePointMatcher(cp);
    }
    
    private CodePointMatcher(final int cp) {
        this.cp = cp;
    }
    
    @Override
    public boolean match(final StringSegment segment, final ParsedNumber result) {
        if (segment.startsWith(this.cp)) {
            segment.adjustOffsetByCodePoint();
            result.setCharsConsumed(segment);
        }
        return false;
    }
    
    @Override
    public boolean smokeTest(final StringSegment segment) {
        return segment.startsWith(this.cp);
    }
    
    @Override
    public void postProcess(final ParsedNumber result) {
    }
    
    @Override
    public String toString() {
        return "<CodePointMatcher U+" + Integer.toHexString(this.cp) + ">";
    }
}
