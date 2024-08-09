/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicDurationFormatter;
import com.ibm.icu.impl.duration.BasicPeriodFormatterService;
import com.ibm.icu.impl.duration.DateFormatter;
import com.ibm.icu.impl.duration.DurationFormatter;
import com.ibm.icu.impl.duration.DurationFormatterFactory;
import com.ibm.icu.impl.duration.PeriodBuilder;
import com.ibm.icu.impl.duration.PeriodFormatter;
import java.util.Locale;
import java.util.TimeZone;

class BasicDurationFormatterFactory
implements DurationFormatterFactory {
    private BasicPeriodFormatterService ps;
    private PeriodFormatter formatter;
    private PeriodBuilder builder;
    private DateFormatter fallback;
    private long fallbackLimit;
    private String localeName;
    private TimeZone timeZone;
    private BasicDurationFormatter f;

    BasicDurationFormatterFactory(BasicPeriodFormatterService basicPeriodFormatterService) {
        this.ps = basicPeriodFormatterService;
        this.localeName = Locale.getDefault().toString();
        this.timeZone = TimeZone.getDefault();
    }

    @Override
    public DurationFormatterFactory setPeriodFormatter(PeriodFormatter periodFormatter) {
        if (periodFormatter != this.formatter) {
            this.formatter = periodFormatter;
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setPeriodBuilder(PeriodBuilder periodBuilder) {
        if (periodBuilder != this.builder) {
            this.builder = periodBuilder;
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setFallback(DateFormatter dateFormatter) {
        boolean bl;
        boolean bl2 = dateFormatter == null ? this.fallback != null : (bl = !dateFormatter.equals(this.fallback));
        if (bl) {
            this.fallback = dateFormatter;
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setFallbackLimit(long l) {
        if (l < 0L) {
            l = 0L;
        }
        if (l != this.fallbackLimit) {
            this.fallbackLimit = l;
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setLocale(String string) {
        if (!string.equals(this.localeName)) {
            this.localeName = string;
            if (this.builder != null) {
                this.builder = this.builder.withLocale(string);
            }
            if (this.formatter != null) {
                this.formatter = this.formatter.withLocale(string);
            }
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setTimeZone(TimeZone timeZone) {
        if (!timeZone.equals(this.timeZone)) {
            this.timeZone = timeZone;
            if (this.builder != null) {
                this.builder = this.builder.withTimeZone(timeZone);
            }
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatter getFormatter() {
        if (this.f == null) {
            if (this.fallback != null) {
                this.fallback = this.fallback.withLocale(this.localeName).withTimeZone(this.timeZone);
            }
            this.formatter = this.getPeriodFormatter();
            this.builder = this.getPeriodBuilder();
            this.f = this.createFormatter();
        }
        return this.f;
    }

    public PeriodFormatter getPeriodFormatter() {
        if (this.formatter == null) {
            this.formatter = this.ps.newPeriodFormatterFactory().setLocale(this.localeName).getFormatter();
        }
        return this.formatter;
    }

    public PeriodBuilder getPeriodBuilder() {
        if (this.builder == null) {
            this.builder = this.ps.newPeriodBuilderFactory().setLocale(this.localeName).setTimeZone(this.timeZone).getSingleUnitBuilder();
        }
        return this.builder;
    }

    public DateFormatter getFallback() {
        return this.fallback;
    }

    public long getFallbackLimit() {
        return this.fallback == null ? 0L : this.fallbackLimit;
    }

    public String getLocaleName() {
        return this.localeName;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    protected BasicDurationFormatter createFormatter() {
        return new BasicDurationFormatter(this.formatter, this.builder, this.fallback, this.fallbackLimit, this.localeName, this.timeZone);
    }

    protected void reset() {
        this.f = null;
    }
}

