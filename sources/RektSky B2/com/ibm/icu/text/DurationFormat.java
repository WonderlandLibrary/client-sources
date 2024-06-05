package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.duration.*;
import java.text.*;
import java.util.*;

@Deprecated
public abstract class DurationFormat extends UFormat
{
    private static final long serialVersionUID = -2076961954727774282L;
    
    @Deprecated
    public static DurationFormat getInstance(final ULocale locale) {
        return BasicDurationFormat.getInstance(locale);
    }
    
    @Deprecated
    protected DurationFormat() {
    }
    
    @Deprecated
    protected DurationFormat(final ULocale locale) {
        this.setLocale(locale, locale);
    }
    
    @Deprecated
    @Override
    public abstract StringBuffer format(final Object p0, final StringBuffer p1, final FieldPosition p2);
    
    @Deprecated
    @Override
    public Object parseObject(final String source, final ParsePosition pos) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public abstract String formatDurationFromNowTo(final Date p0);
    
    @Deprecated
    public abstract String formatDurationFromNow(final long p0);
    
    @Deprecated
    public abstract String formatDurationFrom(final long p0, final long p1);
}
