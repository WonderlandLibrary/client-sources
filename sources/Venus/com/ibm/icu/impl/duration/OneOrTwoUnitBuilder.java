/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodBuilderFactory;
import com.ibm.icu.impl.duration.Period;
import com.ibm.icu.impl.duration.PeriodBuilder;
import com.ibm.icu.impl.duration.PeriodBuilderImpl;
import com.ibm.icu.impl.duration.TimeUnit;

class OneOrTwoUnitBuilder
extends PeriodBuilderImpl {
    OneOrTwoUnitBuilder(BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
    }

    public static OneOrTwoUnitBuilder get(BasicPeriodBuilderFactory.Settings settings) {
        if (settings == null) {
            return null;
        }
        return new OneOrTwoUnitBuilder(settings);
    }

    @Override
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return OneOrTwoUnitBuilder.get(settings);
    }

    @Override
    protected Period handleCreate(long l, long l2, boolean bl) {
        Period period = null;
        short s = this.settings.effectiveSet();
        for (int i = 0; i < TimeUnit.units.length; ++i) {
            TimeUnit timeUnit;
            long l3;
            if (0 == (s & 1 << i) || l < (l3 = this.approximateDurationOf(timeUnit = TimeUnit.units[i])) && period == null) continue;
            double d = (double)l / (double)l3;
            if (period == null) {
                if (d >= 2.0) {
                    period = Period.at((float)d, timeUnit);
                    break;
                }
                period = Period.at(1.0f, timeUnit).inPast(bl);
                l -= l3;
                continue;
            }
            if (!(d >= 1.0)) break;
            period = period.and((float)d, timeUnit);
            break;
        }
        return period;
    }
}

