package com.ibm.icu.impl.duration;

import java.util.*;

public interface DurationFormatterFactory
{
    DurationFormatterFactory setPeriodFormatter(final PeriodFormatter p0);
    
    DurationFormatterFactory setPeriodBuilder(final PeriodBuilder p0);
    
    DurationFormatterFactory setFallback(final DateFormatter p0);
    
    DurationFormatterFactory setFallbackLimit(final long p0);
    
    DurationFormatterFactory setLocale(final String p0);
    
    DurationFormatterFactory setTimeZone(final TimeZone p0);
    
    DurationFormatter getFormatter();
}
