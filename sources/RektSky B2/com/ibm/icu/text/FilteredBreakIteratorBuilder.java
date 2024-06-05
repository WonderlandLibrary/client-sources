package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;

public abstract class FilteredBreakIteratorBuilder
{
    public static final FilteredBreakIteratorBuilder getInstance(final Locale where) {
        return new SimpleFilteredSentenceBreakIterator.Builder(where);
    }
    
    public static final FilteredBreakIteratorBuilder getInstance(final ULocale where) {
        return new SimpleFilteredSentenceBreakIterator.Builder(where);
    }
    
    public static final FilteredBreakIteratorBuilder getEmptyInstance() {
        return new SimpleFilteredSentenceBreakIterator.Builder();
    }
    
    public abstract boolean suppressBreakAfter(final CharSequence p0);
    
    public abstract boolean unsuppressBreakAfter(final CharSequence p0);
    
    public abstract BreakIterator wrapIteratorWithFilter(final BreakIterator p0);
    
    @Deprecated
    protected FilteredBreakIteratorBuilder() {
    }
}
