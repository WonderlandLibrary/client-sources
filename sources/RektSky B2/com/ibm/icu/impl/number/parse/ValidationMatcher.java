package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.*;

public abstract class ValidationMatcher implements NumberParseMatcher
{
    @Override
    public boolean match(final StringSegment segment, final ParsedNumber result) {
        return false;
    }
    
    @Override
    public boolean smokeTest(final StringSegment segment) {
        return false;
    }
}
