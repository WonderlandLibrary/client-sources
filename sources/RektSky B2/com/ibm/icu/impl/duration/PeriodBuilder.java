package com.ibm.icu.impl.duration;

import java.util.*;

public interface PeriodBuilder
{
    Period create(final long p0);
    
    Period createWithReferenceDate(final long p0, final long p1);
    
    PeriodBuilder withLocale(final String p0);
    
    PeriodBuilder withTimeZone(final TimeZone p0);
}
