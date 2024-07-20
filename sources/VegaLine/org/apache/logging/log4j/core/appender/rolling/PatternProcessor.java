/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rolling.RolloverFrequency;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.pattern.ArrayPatternConverter;
import org.apache.logging.log4j.core.pattern.DatePatternConverter;
import org.apache.logging.log4j.core.pattern.FormattingInfo;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.status.StatusLogger;

public class PatternProcessor {
    protected static final Logger LOGGER = StatusLogger.getLogger();
    private static final String KEY = "FileConverter";
    private static final char YEAR_CHAR = 'y';
    private static final char MONTH_CHAR = 'M';
    private static final char[] WEEK_CHARS = new char[]{'w', 'W'};
    private static final char[] DAY_CHARS = new char[]{'D', 'd', 'F', 'E'};
    private static final char[] HOUR_CHARS = new char[]{'H', 'K', 'h', 'k'};
    private static final char MINUTE_CHAR = 'm';
    private static final char SECOND_CHAR = 's';
    private static final char MILLIS_CHAR = 'S';
    private final ArrayPatternConverter[] patternConverters;
    private final FormattingInfo[] patternFields;
    private long prevFileTime = 0L;
    private long nextFileTime = 0L;
    private long currentFileTime = 0L;
    private RolloverFrequency frequency = null;
    private final String pattern;

    public String getPattern() {
        return this.pattern;
    }

    public String toString() {
        return this.pattern;
    }

    public PatternProcessor(String pattern) {
        this.pattern = pattern;
        PatternParser parser = this.createPatternParser();
        ArrayList<PatternConverter> converters = new ArrayList<PatternConverter>();
        ArrayList<FormattingInfo> fields = new ArrayList<FormattingInfo>();
        parser.parse(pattern, converters, fields, false, false, false);
        FormattingInfo[] infoArray = new FormattingInfo[fields.size()];
        this.patternFields = fields.toArray(infoArray);
        ArrayPatternConverter[] converterArray = new ArrayPatternConverter[converters.size()];
        for (ArrayPatternConverter converter : this.patternConverters = converters.toArray(converterArray)) {
            if (!(converter instanceof DatePatternConverter)) continue;
            DatePatternConverter dateConverter = (DatePatternConverter)converter;
            this.frequency = this.calculateFrequency(dateConverter.getPattern());
        }
    }

    public long getCurrentFileTime() {
        return this.currentFileTime;
    }

    public void setCurrentFileTime(long currentFileTime) {
        this.currentFileTime = currentFileTime;
    }

    public long getPrevFileTime() {
        return this.prevFileTime;
    }

    public void setPrevFileTime(long prevFileTime) {
        LOGGER.debug("Setting prev file time to {}", (Object)new Date(prevFileTime));
        this.prevFileTime = prevFileTime;
    }

    public long getNextTime(long currentMillis, int increment, boolean modulus) {
        this.prevFileTime = this.nextFileTime;
        if (this.frequency == null) {
            throw new IllegalStateException("Pattern does not contain a date");
        }
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTimeInMillis(currentMillis);
        Calendar cal = Calendar.getInstance();
        currentCal.setMinimalDaysInFirstWeek(7);
        cal.setMinimalDaysInFirstWeek(7);
        cal.set(currentCal.get(1), 0, 1, 0, 0, 0);
        cal.set(14, 0);
        if (this.frequency == RolloverFrequency.ANNUALLY) {
            this.increment(cal, 1, increment, modulus);
            long nextTime = cal.getTimeInMillis();
            cal.add(1, -1);
            this.nextFileTime = cal.getTimeInMillis();
            return this.debugGetNextTime(nextTime);
        }
        cal.set(2, currentCal.get(2));
        if (this.frequency == RolloverFrequency.MONTHLY) {
            this.increment(cal, 2, increment, modulus);
            long nextTime = cal.getTimeInMillis();
            cal.add(2, -1);
            this.nextFileTime = cal.getTimeInMillis();
            return this.debugGetNextTime(nextTime);
        }
        if (this.frequency == RolloverFrequency.WEEKLY) {
            cal.set(3, currentCal.get(3));
            this.increment(cal, 3, increment, modulus);
            cal.set(7, currentCal.getFirstDayOfWeek());
            long nextTime = cal.getTimeInMillis();
            cal.add(3, -1);
            this.nextFileTime = cal.getTimeInMillis();
            return this.debugGetNextTime(nextTime);
        }
        cal.set(6, currentCal.get(6));
        if (this.frequency == RolloverFrequency.DAILY) {
            this.increment(cal, 6, increment, modulus);
            long nextTime = cal.getTimeInMillis();
            cal.add(6, -1);
            this.nextFileTime = cal.getTimeInMillis();
            return this.debugGetNextTime(nextTime);
        }
        cal.set(11, currentCal.get(11));
        if (this.frequency == RolloverFrequency.HOURLY) {
            this.increment(cal, 11, increment, modulus);
            long nextTime = cal.getTimeInMillis();
            cal.add(11, -1);
            this.nextFileTime = cal.getTimeInMillis();
            return this.debugGetNextTime(nextTime);
        }
        cal.set(12, currentCal.get(12));
        if (this.frequency == RolloverFrequency.EVERY_MINUTE) {
            this.increment(cal, 12, increment, modulus);
            long nextTime = cal.getTimeInMillis();
            cal.add(12, -1);
            this.nextFileTime = cal.getTimeInMillis();
            return this.debugGetNextTime(nextTime);
        }
        cal.set(13, currentCal.get(13));
        if (this.frequency == RolloverFrequency.EVERY_SECOND) {
            this.increment(cal, 13, increment, modulus);
            long nextTime = cal.getTimeInMillis();
            cal.add(13, -1);
            this.nextFileTime = cal.getTimeInMillis();
            return this.debugGetNextTime(nextTime);
        }
        cal.set(14, currentCal.get(14));
        this.increment(cal, 14, increment, modulus);
        long nextTime = cal.getTimeInMillis();
        cal.add(14, -1);
        this.nextFileTime = cal.getTimeInMillis();
        return this.debugGetNextTime(nextTime);
    }

    public void updateTime() {
        this.prevFileTime = this.nextFileTime;
    }

    private long debugGetNextTime(long nextTime) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("PatternProcessor.getNextTime returning {}, nextFileTime={}, prevFileTime={}, current={}, freq={}", (Object)this.format(nextTime), (Object)this.format(this.nextFileTime), (Object)this.format(this.prevFileTime), (Object)this.format(System.currentTimeMillis()), (Object)this.frequency);
        }
        return nextTime;
    }

    private String format(long time) {
        return new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SSS").format(new Date(time));
    }

    private void increment(Calendar cal, int type2, int increment, boolean modulate) {
        int interval = modulate ? increment - cal.get(type2) % increment : increment;
        cal.add(type2, interval);
    }

    public final void formatFileName(StringBuilder buf, boolean useCurrentTime, Object obj) {
        long time;
        long l = time = useCurrentTime ? this.currentFileTime : this.prevFileTime;
        if (time == 0L) {
            time = System.currentTimeMillis();
        }
        this.formatFileName(buf, new Date(time), obj);
    }

    public final void formatFileName(StrSubstitutor subst, StringBuilder buf, Object obj) {
        this.formatFileName(subst, buf, false, obj);
    }

    public final void formatFileName(StrSubstitutor subst, StringBuilder buf, boolean useCurrentTime, Object obj) {
        long time = useCurrentTime && this.currentFileTime != 0L ? this.currentFileTime : (this.prevFileTime != 0L ? this.prevFileTime : System.currentTimeMillis());
        this.formatFileName(buf, new Date(time), obj);
        Log4jLogEvent event = new Log4jLogEvent.Builder().setTimeMillis(time).build();
        String fileName = subst.replace((LogEvent)event, buf);
        buf.setLength(0);
        buf.append(fileName);
    }

    protected final void formatFileName(StringBuilder buf, Object ... objects) {
        for (int i = 0; i < this.patternConverters.length; ++i) {
            int fieldStart = buf.length();
            this.patternConverters[i].format(buf, objects);
            if (this.patternFields[i] == null) continue;
            this.patternFields[i].format(fieldStart, buf);
        }
    }

    private RolloverFrequency calculateFrequency(String pattern) {
        if (this.patternContains(pattern, 'S')) {
            return RolloverFrequency.EVERY_MILLISECOND;
        }
        if (this.patternContains(pattern, 's')) {
            return RolloverFrequency.EVERY_SECOND;
        }
        if (this.patternContains(pattern, 'm')) {
            return RolloverFrequency.EVERY_MINUTE;
        }
        if (this.patternContains(pattern, HOUR_CHARS)) {
            return RolloverFrequency.HOURLY;
        }
        if (this.patternContains(pattern, DAY_CHARS)) {
            return RolloverFrequency.DAILY;
        }
        if (this.patternContains(pattern, WEEK_CHARS)) {
            return RolloverFrequency.WEEKLY;
        }
        if (this.patternContains(pattern, 'M')) {
            return RolloverFrequency.MONTHLY;
        }
        if (this.patternContains(pattern, 'y')) {
            return RolloverFrequency.ANNUALLY;
        }
        return null;
    }

    private PatternParser createPatternParser() {
        return new PatternParser(null, KEY, null);
    }

    private boolean patternContains(String pattern, char ... chars) {
        for (char character : chars) {
            if (!this.patternContains(pattern, character)) continue;
            return true;
        }
        return false;
    }

    private boolean patternContains(String pattern, char character) {
        return pattern.indexOf(character) >= 0;
    }

    public RolloverFrequency getFrequency() {
        return this.frequency;
    }

    public long getNextFileTime() {
        return this.nextFileTime;
    }
}

