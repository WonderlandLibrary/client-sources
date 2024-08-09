/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodBuilderFactory;
import com.ibm.icu.impl.duration.Period;
import com.ibm.icu.impl.duration.PeriodBuilder;
import com.ibm.icu.impl.duration.PeriodBuilderImpl;
import com.ibm.icu.impl.duration.TimeUnit;

class SingleUnitBuilder
extends PeriodBuilderImpl {
    SingleUnitBuilder(BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
    }

    public static SingleUnitBuilder get(BasicPeriodBuilderFactory.Settings settings) {
        if (settings == null) {
            return null;
        }
        return new SingleUnitBuilder(settings);
    }

    @Override
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return SingleUnitBuilder.get(settings);
    }

    @Override
    protected Period handleCreate(long l, long l2, boolean bl) {
        short s = this.settings.effectiveSet();
        for (int i = 0; i < TimeUnit.units.length; ++i) {
            TimeUnit timeUnit;
            long l3;
            if (0 == (s & 1 << i) || l < (l3 = this.approximateDurationOf(timeUnit = TimeUnit.units[i]))) continue;
            return Period.at((float)((double)l / (double)l3), timeUnit).inPast(bl);
        }
        return null;
    }
}

