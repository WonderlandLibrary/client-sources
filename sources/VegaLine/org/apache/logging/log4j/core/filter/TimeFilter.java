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

    private TimeFilter(long start, long end, TimeZone tz, Filter.Result onMatch, Filter.Result onMismatch) {
        super(onMatch, onMismatch);
        this.start = start;
        this.end = end;
        this.timezone = tz;
        this.initMidnight(start);
    }

    void initMidnight(long now) {
        Calendar calendar = Calendar.getInstance(this.timezone);
        calendar.setTimeInMillis(now);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        this.midnightToday = calendar.getTimeInMillis();
        calendar.add(5, 1);
        this.midnightTomorrow = calendar.getTimeInMillis();
    }

    Filter.Result filter(long currentTimeMillis) {
        if (currentTimeMillis >= this.midnightTomorrow || currentTimeMillis < this.midnightToday) {
            this.initMidnight(currentTimeMillis);
        }
        return currentTimeMillis >= this.midnightToday + this.start && currentTimeMillis <= this.midnightToday + this.end ? this.onMatch : this.onMismatch;
    }

    @Override
    public Filter.Result filter(LogEvent event) {
        return this.filter(event.getTimeMillis());
    }

    private Filter.Result filter() {
        return this.filter(CLOCK.currentTimeMillis());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object ... params) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return this.filter();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("start=").append(this.start);
        sb.append(", end=").append(this.end);
        sb.append(", timezone=").append(this.timezone.toString());
        return sb.toString();
    }

    @PluginFactory
    public static TimeFilter createFilter(@PluginAttribute(value="start") String start, @PluginAttribute(value="end") String end, @PluginAttribute(value="timezone") String tz, @PluginAttribute(value="onMatch") Filter.Result match, @PluginAttribute(value="onMismatch") Filter.Result mismatch) {
        long s = TimeFilter.parseTimestamp(start, 0L);
        long e = TimeFilter.parseTimestamp(end, Long.MAX_VALUE);
        TimeZone timezone = tz == null ? TimeZone.getDefault() : TimeZone.getTimeZone(tz);
        Filter.Result onMatch = match == null ? Filter.Result.NEUTRAL : match;
        Filter.Result onMismatch = mismatch == null ? Filter.Result.DENY : mismatch;
        return new TimeFilter(s, e, timezone, onMatch, onMismatch);
    }

    private static long parseTimestamp(String timestamp, long defaultValue) {
        if (timestamp == null) {
            return defaultValue;
        }
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        stf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return stf.parse(timestamp).getTime();
        } catch (ParseException e) {
            LOGGER.warn("Error parsing TimeFilter timestamp value {}", (Object)timestamp, (Object)e);
            return defaultValue;
        }
    }
}

