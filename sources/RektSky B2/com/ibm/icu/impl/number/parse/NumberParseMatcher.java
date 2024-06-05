package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.*;

public interface NumberParseMatcher
{
    boolean match(final StringSegment p0, final ParsedNumber p1);
    
    boolean smokeTest(final StringSegment p0);
    
    void postProcess(final ParsedNumber p0);
    
    public interface Flexible
    {
    }
}
