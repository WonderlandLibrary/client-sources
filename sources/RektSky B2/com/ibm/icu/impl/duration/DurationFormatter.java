package com.ibm.icu.impl.duration;

import java.util.*;

public interface DurationFormatter
{
    String formatDurationFromNowTo(final Date p0);
    
    String formatDurationFromNow(final long p0);
    
    String formatDurationFrom(final long p0, final long p1);
    
    DurationFormatter withLocale(final String p0);
    
    DurationFormatter withTimeZone(final TimeZone p0);
}
