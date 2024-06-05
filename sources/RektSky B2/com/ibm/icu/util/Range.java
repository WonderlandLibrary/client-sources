package com.ibm.icu.util;

import java.util.*;

class Range
{
    public Date start;
    public DateRule rule;
    
    public Range(final Date start, final DateRule rule) {
        this.start = start;
        this.rule = rule;
    }
}
