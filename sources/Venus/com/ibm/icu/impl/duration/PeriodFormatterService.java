/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.DurationFormatterFactory;
import com.ibm.icu.impl.duration.PeriodBuilderFactory;
import com.ibm.icu.impl.duration.PeriodFormatterFactory;
import java.util.Collection;

public interface PeriodFormatterService {
    public DurationFormatterFactory newDurationFormatterFactory();

    public PeriodFormatterFactory newPeriodFormatterFactory();

    public PeriodBuilderFactory newPeriodBuilderFactory();

    public Collection<String> getAvailableLocaleNames();
}

