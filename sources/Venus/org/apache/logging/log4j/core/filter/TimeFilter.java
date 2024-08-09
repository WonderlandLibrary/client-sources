/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.ClockFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="TimeFilter", category="Core", elementType="filter", printObject=true)
@PerformanceSensitive(value={"allocation"})
public final class TimeFilter
extends AbstractFilter {
    private static final Clock CLOCK = ClockFactory.getClock();
    private static final long HOUR_MS = 3600000L;
    private static final long MINUTE_MS = 60000L;
    private static final long SECOND_MS = 1000L;
    private final long start;
    private final long end;
    private final TimeZone timezone;
    private long midnightToday;
    private long midnightTomorrow;

    private TimeFilter(long l, long l2, TimeZone timeZone, Filter.Result result, Filter.Result result2) {
        super(result, result2);
        this.start = l;
        this.end = l2;
        this.timezone = timeZone;
        this.initMidnight(l);
    }

    void initMidnight(long l) {
        Calendar calendar = Calendar.getInstance(this.timezone);
        calendar.setTimeInMillis(l);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        this.midnightToday = calendar.getTimeInMillis();
        calendar.add(5, 1);
        this.midnightTomorrow = calendar.getTimeInMillis();
    }

    Filter.Result filter(long l) {
        if (l >= this.midnightTomorrow || l < this.midnightToday) {
            this.initMidnight(l);
        }
        return l >= this.midnightToday + this.start && l <= this.midnightToday + this.end ? this.onMatch : this.onMismatch;
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        return this.filter(logEvent.getTimeMillis());
    }

    private Filter.Result filter() {
        return this.filter(CLOCK.currentTimeMillis());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object object, Throwable throwable) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object ... objectArray) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.filter();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("start=").append(this.start);
        stringBuilder.append(", end=").append(this.end);
        stringBuilder.append(", timezone=").append(this.timezone.toString());
        return stringBuilder.toString();
    }

    @PluginFactory
    public static TimeFilter createFilter(@PluginAttribute(value="start") String string, @PluginAttribute(value="end") String string2, @PluginAttribute(value="timezone") String string3, @PluginAttribute(value="onMatch") Filter.Result result, @PluginAttribute(value="onMismatch") Filter.Result result2) {
        long l = TimeFilter.parseTimestamp(string, 0L);
        long l2 = TimeFilter.parseTimestamp(string2, Long.MAX_VALUE);
        TimeZone timeZone = string3 == null ? TimeZone.getDefault() : TimeZone.getTimeZone(string3);
        Filter.Result result3 = result == null ? Filter.Result.NEUTRAL : result;
        Filter.Result result4 = result2 == null ? Filter.Result.DENY : result2;
        return new TimeFilter(l, l2, timeZone, result3, result4);
    }

    private static long parseTimestamp(String string, long l) {
        if (string == null) {
            return l;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return simpleDateFormat.parse(string).getTime();
        } catch (ParseException parseException) {
            LOGGER.warn("Error parsing TimeFilter timestamp value {}", (Object)string, (Object)parseException);
            return l;
        }
    }
}

