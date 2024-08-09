/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.Period;
import java.util.TimeZone;

public interface PeriodBuilder {
    public Period create(long var1);

    public Period createWithReferenceDate(long var1, long var3);

    public PeriodBuilder withLocale(String var1);

    public PeriodBuilder withTimeZone(TimeZone var1);
}

