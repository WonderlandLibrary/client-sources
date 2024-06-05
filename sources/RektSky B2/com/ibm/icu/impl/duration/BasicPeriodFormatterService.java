package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.impl.*;
import java.util.*;

public class BasicPeriodFormatterService implements PeriodFormatterService
{
    private static BasicPeriodFormatterService instance;
    private PeriodFormatterDataService ds;
    
    public static BasicPeriodFormatterService getInstance() {
        if (BasicPeriodFormatterService.instance == null) {
            final PeriodFormatterDataService ds = ResourceBasedPeriodFormatterDataService.getInstance();
            BasicPeriodFormatterService.instance = new BasicPeriodFormatterService(ds);
        }
        return BasicPeriodFormatterService.instance;
    }
    
    public BasicPeriodFormatterService(final PeriodFormatterDataService ds) {
        this.ds = ds;
    }
    
    @Override
    public DurationFormatterFactory newDurationFormatterFactory() {
        return new BasicDurationFormatterFactory(this);
    }
    
    @Override
    public PeriodFormatterFactory newPeriodFormatterFactory() {
        return new BasicPeriodFormatterFactory(this.ds);
    }
    
    @Override
    public PeriodBuilderFactory newPeriodBuilderFactory() {
        return new BasicPeriodBuilderFactory(this.ds);
    }
    
    @Override
    public Collection<String> getAvailableLocaleNames() {
        return this.ds.getAvailableLocales();
    }
}
