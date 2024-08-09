/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodFormatterService;
import com.ibm.icu.impl.duration.DurationFormatter;
import com.ibm.icu.impl.duration.Period;
import com.ibm.icu.impl.duration.PeriodFormatter;
import com.ibm.icu.impl.duration.PeriodFormatterService;
import com.ibm.icu.impl.duration.TimeUnit;
import com.ibm.icu.text.DurationFormat;
import com.ibm.icu.util.ULocale;
import java.text.FieldPosition;
import java.util.Date;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;

public class BasicDurationFormat
extends DurationFormat {
    private static final long serialVersionUID = -3146984141909457700L;
    transient DurationFormatter formatter;
    transient PeriodFormatter pformatter;
    transient PeriodFormatterService pfs = BasicPeriodFormatterService.getInstance();

    public static BasicDurationFormat getInstance(ULocale uLocale) {
        return new BasicDurationFormat(uLocale);
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (object instanceof Long) {
            String string = this.formatDurationFromNow((Long)object);
            return stringBuffer.append(string);
        }
        if (object instanceof Date) {
            String string = this.formatDurationFromNowTo((Date)object);
            return stringBuffer.append(string);
        }
        if (object instanceof Duration) {
            String string = this.formatDuration(object);
            return stringBuffer.append(string);
        }
        throw new IllegalArgumentException("Cannot format given Object as a Duration");
    }

    public BasicDurationFormat() {
        this.formatter = this.pfs.newDurationFormatterFactory().getFormatter();
        this.pformatter = this.pfs.newPeriodFormatterFactory().setDisplayPastFuture(false).getFormatter();
    }

    public BasicDurationFormat(ULocale uLocale) {
        super(uLocale);
        this.formatter = this.pfs.newDurationFormatterFactory().setLocale(uLocale.getName()).getFormatter();
        this.pformatter = this.pfs.newPeriodFormatterFactory().setDisplayPastFuture(false).setLocale(uLocale.getName()).getFormatter();
    }

    @Override
    public String formatDurationFrom(long l, long l2) {
        return this.formatter.formatDurationFrom(l, l2);
    }

    @Override
    public String formatDurationFromNow(long l) {
        return this.formatter.formatDurationFromNow(l);
    }

    @Override
    public String formatDurationFromNowTo(Date date) {
        return this.formatter.formatDurationFromNowTo(date);
    }

    public String formatDuration(Object object) {
        DatatypeConstants.Field[] fieldArray = new DatatypeConstants.Field[]{DatatypeConstants.YEARS, DatatypeConstants.MONTHS, DatatypeConstants.DAYS, DatatypeConstants.HOURS, DatatypeConstants.MINUTES, DatatypeConstants.SECONDS};
        TimeUnit[] timeUnitArray = new TimeUnit[]{TimeUnit.YEAR, TimeUnit.MONTH, TimeUnit.DAY, TimeUnit.HOUR, TimeUnit.MINUTE, TimeUnit.SECOND};
        Duration duration = (Duration)object;
        Period period = null;
        Duration duration2 = duration;
        boolean bl = false;
        if (duration.getSign() < 0) {
            duration2 = duration.negate();
            bl = true;
        }
        boolean bl2 = false;
        for (int i = 0; i < fieldArray.length; ++i) {
            double d;
            double d2;
            double d3;
            Number number;
            if (!duration2.isSet(fieldArray[i]) || (number = duration2.getField(fieldArray[i])).intValue() == 0 && !bl2) continue;
            bl2 = true;
            float f = number.floatValue();
            TimeUnit timeUnit = null;
            float f2 = 0.0f;
            if (timeUnitArray[i] == TimeUnit.SECOND && (d3 = ((d2 = (double)f) - (d = Math.floor(f))) * 1000.0) > 0.0) {
                timeUnit = TimeUnit.MILLISECOND;
                f2 = (float)d3;
                f = (float)d;
            }
            period = period == null ? Period.at(f, timeUnitArray[i]) : period.and(f, timeUnitArray[i]);
            if (timeUnit == null) continue;
            period = period.and(f2, timeUnit);
        }
        if (period == null) {
            return this.formatDurationFromNow(0L);
        }
        period = bl ? period.inPast() : period.inFuture();
        return this.pformatter.format(period);
    }
}

