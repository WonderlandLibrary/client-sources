package com.ibm.icu.impl.duration.impl;

import java.util.*;

public abstract class PeriodFormatterDataService
{
    public abstract PeriodFormatterData get(final String p0);
    
    public abstract Collection<String> getAvailableLocales();
}
