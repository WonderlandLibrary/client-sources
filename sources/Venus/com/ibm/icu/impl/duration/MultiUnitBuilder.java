/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodBuilderFactory;
import com.ibm.icu.impl.duration.Period;
import com.ibm.icu.impl.duration.PeriodBuilder;
import com.ibm.icu.impl.duration.PeriodBuilderImpl;
import com.ibm.icu.impl.duration.TimeUnit;

class MultiUnitBuilder
extends PeriodBuilderImpl {
    private int nPeriods;

    MultiUnitBuilder(int n, BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
        this.nPeriods = n;
    }

    public static MultiUnitBuilder get(int n, BasicPeriodBuilderFactory.Settings settings) {
        if (n > 0 && settings != null) {
            return new MultiUnitBuilder(n, settings);
        }
        return null;
    }

    @Override
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return MultiUnitBuilder.get(this.nPeriods, settings);
    }

    @Override
    protected Period handleCreate(long l, long l2, boolean bl) {
        Period period = null;
        int n = 0;
        short s = this.settings.effectiveSet();
        for (int i = 0; i < TimeUnit.units.length; ++i) {
            if (0 == (s & 1 << i)) continue;
            TimeUnit timeUnit = TimeUnit.units[i];
            if (n == this.nPeriods) break;
            long l3 = this.approximateDurationOf(timeUnit);
            if (l < l3 && n <= 0) continue;
            double d = (double)l / (double)l3;
            if (++n < this.nPeriods) {
                d = Math.floor(d);
                l -= (long)(d * (double)l3);
            }
            period = period == null ? Period.at((float)d, timeUnit).inPast(bl) : period.and((float)d, timeUnit);
        }
        return period;
    }
}

