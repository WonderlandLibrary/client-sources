package com.ibm.icu.impl.duration;

import java.util.*;

public interface DateFormatter
{
    String format(final Date p0);
    
    String format(final long p0);
    
    DateFormatter withLocale(final String p0);
    
    DateFormatter withTimeZone(final TimeZone p0);
}
