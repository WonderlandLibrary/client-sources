/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.DateFormatter;
import com.ibm.icu.impl.duration.DurationFormatter;
import com.ibm.icu.impl.duration.Period;
import com.ibm.icu.impl.duration.PeriodBuilder;
import com.ibm.icu.impl.duration.PeriodFormatter;
import java.util.Date;
import java.util.TimeZone;

class BasicDurationFormatter
implements DurationFormatter {
    private PeriodFormatter formatter;
    private PeriodBuilder builder;
    private DateFormatter fallback;
    private long fallbackLimit;
    private String localeName;
    private TimeZone timeZone;

    public BasicDurationFormatter(PeriodFormatter periodFormatter, PeriodBuilder periodBuilder, DateFormatter dateFormatter, long l) {
        this.formatter = periodFormatter;
        this.builder = periodBuilder;
        this.fallback = dateFormatter;
        this.fallbackLimit = l < 0L ? 0L : l;
    }

    protected BasicDurationFormatter(PeriodFormatter periodFormatter, PeriodBuilder periodBuilder, DateFormatter dateFormatter, long l, String string, TimeZone timeZone) {
        this.formatter = periodFormatter;
        this.builder = periodBuilder;
        this.fallback = dateFormatter;
        this.fallbackLimit = l;
        this.localeName = string;
        this.timeZone = timeZone;
    }

    @Override
    public String formatDurationFromNowTo(Date date) {
        long l = System.currentTimeMillis();
        long l2 = date.getTime() - l;
        return this.formatDurationFrom(l2, l);
    }

    @Override
    public String formatDurationFromNow(long l) {
        return this.formatDurationFrom(l, System.currentTimeMillis());
    }

    @Override
    public String formatDurationFrom(long l, long l2) {
        String string = this.doFallback(l, l2);
        if (string == null) {
            Period period = this.doBuild(l, l2);
            string = this.doFormat(period);
        }
        return string;
    }

    @Override
    public DurationFormatter withLocale(String string) {
        if (!string.equals(this.localeName)) {
            PeriodFormatter periodFormatter = this.formatter.withLocale(string);
            PeriodBuilder periodBuilder = this.builder.withLocale(string);
            DateFormatter dateFormatter = this.fallback == null ? null : this.fallback.withLocale(string);
            return new BasicDurationFormatter(periodFormatter, periodBuilder, dateFormatter, this.fallbackLimit, string, this.timeZone);
        }
        return this;
    }

    @Override
    public DurationFormatter withTimeZone(TimeZone timeZone) {
        if (!timeZone.equals(this.timeZone)) {
            PeriodBuilder periodBuilder = this.builder.withTimeZone(timeZone);
            DateFormatter dateFormatter = this.fallback == null ? null : this.fallback.withTimeZone(timeZone);
            return new BasicDurationFormatter(this.formatter, periodBuilder, dateFormatter, this.fallbackLimit, this.localeName, timeZone);
        }
        return this;
    }

    protected String doFallback(long l, long l2) {
        if (this.fallback != null && this.fallbackLimit > 0L && Math.abs(l) >= this.fallbackLimit) {
            return this.fallback.format(l2 + l);
        }
        return null;
    }

    protected Period doBuild(long l, long l2) {
        return this.builder.createWithReferenceDate(l, l2);
    }

    protected String doFormat(Period period) {
        if (!period.isSet()) {
            throw new IllegalArgumentException("period is not set");
        }
        return this.formatter.format(period);
    }
}

