package com.ibm.icu.impl.duration;

import java.util.*;

public interface PeriodFormatterService
{
    DurationFormatterFactory newDurationFormatterFactory();
    
    PeriodFormatterFactory newPeriodFormatterFactory();
    
    PeriodBuilderFactory newPeriodBuilderFactory();
    
    Collection<String> getAvailableLocaleNames();
}
