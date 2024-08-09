/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.FixedUnitBuilder;
import com.ibm.icu.impl.duration.MultiUnitBuilder;
import com.ibm.icu.impl.duration.OneOrTwoUnitBuilder;
import com.ibm.icu.impl.duration.Period;
import com.ibm.icu.impl.duration.PeriodBuilder;
import com.ibm.icu.impl.duration.PeriodBuilderFactory;
import com.ibm.icu.impl.duration.SingleUnitBuilder;
import com.ibm.icu.impl.duration.TimeUnit;
import com.ibm.icu.impl.duration.impl.PeriodFormatterData;
import com.ibm.icu.impl.duration.impl.PeriodFormatterDataService;
import java.util.TimeZone;

class BasicPeriodBuilderFactory
implements PeriodBuilderFactory {
    private PeriodFormatterDataService ds;
    private Settings settings;
    private static final short allBits = 255;

    BasicPeriodBuilderFactory(PeriodFormatterDataService periodFormatterDataService) {
        this.ds = periodFormatterDataService;
        this.settings = new Settings(this);
    }

    static long approximateDurationOf(TimeUnit timeUnit) {
        return TimeUnit.approxDurations[timeUnit.ordinal];
    }

    @Override
    public PeriodBuilderFactory setAvailableUnitRange(TimeUnit timeUnit, TimeUnit timeUnit2) {
        int n = 0;
        for (int i = timeUnit2.ordinal; i <= timeUnit.ordinal; ++i) {
            n |= 1 << i;
        }
        if (n == 0) {
            throw new IllegalArgumentException("range " + timeUnit + " to " + timeUnit2 + " is empty");
        }
        this.settings = this.settings.setUnits(n);
        return this;
    }

    @Override
    public PeriodBuilderFactory setUnitIsAvailable(TimeUnit timeUnit, boolean bl) {
        int n = this.settings.uset;
        n = bl ? (n |= 1 << timeUnit.ordinal) : (n &= ~(1 << timeUnit.ordinal));
        this.settings = this.settings.setUnits(n);
        return this;
    }

    @Override
    public PeriodBuilderFactory setMaxLimit(float f) {
        this.settings = this.settings.setMaxLimit(f);
        return this;
    }

    @Override
    public PeriodBuilderFactory setMinLimit(float f) {
        this.settings = this.settings.setMinLimit(f);
        return this;
    }

    @Override
    public PeriodBuilderFactory setAllowZero(boolean bl) {
        this.settings = this.settings.setAllowZero(bl);
        return this;
    }

    @Override
    public PeriodBuilderFactory setWeeksAloneOnly(boolean bl) {
        this.settings = this.settings.setWeeksAloneOnly(bl);
        return this;
    }

    @Override
    public PeriodBuilderFactory setAllowMilliseconds(boolean bl) {
        this.settings = this.settings.setAllowMilliseconds(bl);
        return this;
    }

    @Override
    public PeriodBuilderFactory setLocale(String string) {
        this.settings = this.settings.setLocale(string);
        return this;
    }

    @Override
    public PeriodBuilderFactory setTimeZone(TimeZone timeZone) {
        return this;
    }

    private Settings getSettings() {
        if (this.settings.effectiveSet() == 0) {
            return null;
        }
        return this.settings.setInUse();
    }

    @Override
    public PeriodBuilder getFixedUnitBuilder(TimeUnit timeUnit) {
        return FixedUnitBuilder.get(timeUnit, this.getSettings());
    }

    @Override
    public PeriodBuilder getSingleUnitBuilder() {
        return SingleUnitBuilder.get(this.getSettings());
    }

    @Override
    public PeriodBuilder getOneOrTwoUnitBuilder() {
        return OneOrTwoUnitBuilder.get(this.getSettings());
    }

    @Override
    public PeriodBuilder getMultiUnitBuilder(int n) {
        return MultiUnitBuilder.get(n, this.getSettings());
    }

    static PeriodFormatterDataService access$000(BasicPeriodBuilderFactory basicPeriodBuilderFactory) {
        return basicPeriodBuilderFactory.ds;
    }

    class Settings {
        boolean inUse;
        short uset;
        TimeUnit maxUnit;
        TimeUnit minUnit;
        int maxLimit;
        int minLimit;
        boolean allowZero;
        boolean weeksAloneOnly;
        boolean allowMillis;
        final BasicPeriodBuilderFactory this$0;

        Settings(BasicPeriodBuilderFactory basicPeriodBuilderFactory) {
            this.this$0 = basicPeriodBuilderFactory;
            this.uset = (short)255;
            this.maxUnit = TimeUnit.YEAR;
            this.minUnit = TimeUnit.MILLISECOND;
            this.allowZero = true;
            this.allowMillis = true;
        }

        Settings setUnits(int n) {
            if (this.uset == n) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.uset = (short)n;
            if ((n & 0xFF) == 255) {
                settings.uset = (short)255;
                settings.maxUnit = TimeUnit.YEAR;
                settings.minUnit = TimeUnit.MILLISECOND;
            } else {
                int n2 = -1;
                for (int i = 0; i < TimeUnit.units.length; ++i) {
                    if (0 == (n & 1 << i)) continue;
                    if (n2 == -1) {
                        settings.maxUnit = TimeUnit.units[i];
                    }
                    n2 = i;
                }
                if (n2 == -1) {
                    settings.maxUnit = null;
                    settings.minUnit = null;
                } else {
                    settings.minUnit = TimeUnit.units[n2];
                }
            }
            return settings;
        }

        short effectiveSet() {
            if (this.allowMillis) {
                return this.uset;
            }
            return (short)(this.uset & ~(1 << TimeUnit.MILLISECOND.ordinal));
        }

        TimeUnit effectiveMinUnit() {
            if (this.allowMillis || this.minUnit != TimeUnit.MILLISECOND) {
                return this.minUnit;
            }
            int n = TimeUnit.units.length - 1;
            while (--n >= 0) {
                if (0 == (this.uset & 1 << n)) continue;
                return TimeUnit.units[n];
            }
            return TimeUnit.SECOND;
        }

        Settings setMaxLimit(float f) {
            int n;
            int n2 = n = f <= 0.0f ? 0 : (int)(f * 1000.0f);
            if (f == (float)n) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.maxLimit = n;
            return settings;
        }

        Settings setMinLimit(float f) {
            int n;
            int n2 = n = f <= 0.0f ? 0 : (int)(f * 1000.0f);
            if (f == (float)n) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.minLimit = n;
            return settings;
        }

        Settings setAllowZero(boolean bl) {
            if (this.allowZero == bl) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.allowZero = bl;
            return settings;
        }

        Settings setWeeksAloneOnly(boolean bl) {
            if (this.weeksAloneOnly == bl) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.weeksAloneOnly = bl;
            return settings;
        }

        Settings setAllowMilliseconds(boolean bl) {
            if (this.allowMillis == bl) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.allowMillis = bl;
            return settings;
        }

        Settings setLocale(String string) {
            PeriodFormatterData periodFormatterData = BasicPeriodBuilderFactory.access$000(this.this$0).get(string);
            return this.setAllowZero(periodFormatterData.allowZero()).setWeeksAloneOnly(periodFormatterData.weeksAloneOnly()).setAllowMilliseconds(periodFormatterData.useMilliseconds() != 1);
        }

        Settings setInUse() {
            this.inUse = true;
            return this;
        }

        Period createLimited(long l, boolean bl) {
            long l2;
            if (this.maxLimit > 0 && l * 1000L > (long)this.maxLimit * (l2 = BasicPeriodBuilderFactory.approximateDurationOf(this.maxUnit))) {
                return Period.moreThan((float)this.maxLimit / 1000.0f, this.maxUnit).inPast(bl);
            }
            if (this.minLimit > 0) {
                long l3;
                TimeUnit timeUnit = this.effectiveMinUnit();
                long l4 = BasicPeriodBuilderFactory.approximateDurationOf(timeUnit);
                long l5 = l3 = timeUnit == this.minUnit ? (long)this.minLimit : Math.max(1000L, BasicPeriodBuilderFactory.approximateDurationOf(this.minUnit) * (long)this.minLimit / l4);
                if (l * 1000L < l3 * l4) {
                    return Period.lessThan((float)l3 / 1000.0f, timeUnit).inPast(bl);
                }
            }
            return null;
        }

        public Settings copy() {
            Settings settings = new Settings(this.this$0);
            settings.inUse = this.inUse;
            settings.uset = this.uset;
            settings.maxUnit = this.maxUnit;
            settings.minUnit = this.minUnit;
            settings.maxLimit = this.maxLimit;
            settings.minLimit = this.minLimit;
            settings.allowZero = this.allowZero;
            settings.weeksAloneOnly = this.weeksAloneOnly;
            settings.allowMillis = this.allowMillis;
            return settings;
        }
    }
}

