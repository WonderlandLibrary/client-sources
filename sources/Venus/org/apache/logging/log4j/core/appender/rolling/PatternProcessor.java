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

    public PatternProcessor(String string) {
        this.pattern = string;
        PatternParser patternParser = this.createPatternParser();
        ArrayList<PatternConverter> arrayList = new ArrayList<PatternConverter>();
        ArrayList<FormattingInfo> arrayList2 = new ArrayList<FormattingInfo>();
        patternParser.parse(string, arrayList, arrayList2, false, false, true);
        FormattingInfo[] formattingInfoArray = new FormattingInfo[arrayList2.size()];
        this.patternFields = arrayList2.toArray(formattingInfoArray);
        ArrayPatternConverter[] arrayPatternConverterArray = new ArrayPatternConverter[arrayList.size()];
        for (ArrayPatternConverter arrayPatternConverter : this.patternConverters = arrayList.toArray(arrayPatternConverterArray)) {
            if (!(arrayPatternConverter instanceof DatePatternConverter)) continue;
            DatePatternConverter datePatternConverter = (DatePatternConverter)arrayPatternConverter;
            this.frequency = this.calculateFrequency(datePatternConverter.getPattern());
        }
    }

    public long getCurrentFileTime() {
        return this.currentFileTime;
    }

    public void setCurrentFileTime(long l) {
        this.currentFileTime = l;
    }

    public long getPrevFileTime() {
        return this.prevFileTime;
    }

    public void setPrevFileTime(long l) {
        LOGGER.debug("Setting prev file time to {}", (Object)new Date(l));
        this.prevFileTime = l;
    }

    public long getNextTime(long l, int n, boolean bl) {
        this.prevFileTime = this.nextFileTime;
        if (this.frequency == null) {
            throw new IllegalStateException("Pattern does not contain a date");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        Calendar calendar2 = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar2.setMinimalDaysInFirstWeek(7);
        calendar2.set(calendar.get(1), 0, 1, 0, 0, 0);
        calendar2.set(14, 0);
        if (this.frequency == RolloverFrequency.ANNUALLY) {
            this.increment(calendar2, 1, n, bl);
            long l2 = calendar2.getTimeInMillis();
            calendar2.add(1, -1);
            this.nextFileTime = calendar2.getTimeInMillis();
            return this.debugGetNextTime(l2);
        }
        calendar2.set(2, calendar.get(2));
        if (this.frequency == RolloverFrequency.MONTHLY) {
            this.increment(calendar2, 2, n, bl);
            long l3 = calendar2.getTimeInMillis();
            calendar2.add(2, -1);
            this.nextFileTime = calendar2.getTimeInMillis();
            return this.debugGetNextTime(l3);
        }
        if (this.frequency == RolloverFrequency.WEEKLY) {
            calendar2.set(3, calendar.get(3));
            this.increment(calendar2, 3, n, bl);
            calendar2.set(7, calendar.getFirstDayOfWeek());
            long l4 = calendar2.getTimeInMillis();
            calendar2.add(3, -1);
            this.nextFileTime = calendar2.getTimeInMillis();
            return this.debugGetNextTime(l4);
        }
        calendar2.set(6, calendar.get(6));
        if (this.frequency == RolloverFrequency.DAILY) {
            this.increment(calendar2, 6, n, bl);
            long l5 = calendar2.getTimeInMillis();
            calendar2.add(6, -1);
            this.nextFileTime = calendar2.getTimeInMillis();
            return this.debugGetNextTime(l5);
        }
        calendar2.set(11, calendar.get(11));
        if (this.frequency == RolloverFrequency.HOURLY) {
            this.increment(calendar2, 11, n, bl);
            long l6 = calendar2.getTimeInMillis();
            calendar2.add(11, -1);
            this.nextFileTime = calendar2.getTimeInMillis();
            return this.debugGetNextTime(l6);
        }
        calendar2.set(12, calendar.get(12));
        if (this.frequency == RolloverFrequency.EVERY_MINUTE) {
            this.increment(calendar2, 12, n, bl);
            long l7 = calendar2.getTimeInMillis();
            calendar2.add(12, -1);
            this.nextFileTime = calendar2.getTimeInMillis();
            return this.debugGetNextTime(l7);
        }
        calendar2.set(13, calendar.get(13));
        if (this.frequency == RolloverFrequency.EVERY_SECOND) {
            this.increment(calendar2, 13, n, bl);
            long l8 = calendar2.getTimeInMillis();
            calendar2.add(13, -1);
            this.nextFileTime = calendar2.getTimeInMillis();
            return this.debugGetNextTime(l8);
        }
        calendar2.set(14, calendar.get(14));
        this.increment(calendar2, 14, n, bl);
        long l9 = calendar2.getTimeInMillis();
        calendar2.add(14, -1);
        this.nextFileTime = calendar2.getTimeInMillis();
        return this.debugGetNextTime(l9);
    }

    public void updateTime() {
        this.prevFileTime = this.nextFileTime;
    }

    private long debugGetNextTime(long l) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("PatternProcessor.getNextTime returning {}, nextFileTime={}, prevFileTime={}, current={}, freq={}", (Object)this.format(l), (Object)this.format(this.nextFileTime), (Object)this.format(this.prevFileTime), (Object)this.format(System.currentTimeMillis()), (Object)this.frequency);
        }
        return l;
    }

    private String format(long l) {
        return new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SSS").format(new Date(l));
    }

    private void increment(Calendar calendar, int n, int n2, boolean bl) {
        int n3 = bl ? n2 - calendar.get(n) % n2 : n2;
        calendar.add(n, n3);
    }

    public final void formatFileName(StringBuilder stringBuilder, boolean bl, Object object) {
        long l;
        long l2 = l = bl ? this.currentFileTime : this.prevFileTime;
        if (l == 0L) {
            l = System.currentTimeMillis();
        }
        this.formatFileName(stringBuilder, new Date(l), object);
    }

    public final void formatFileName(StrSubstitutor strSubstitutor, StringBuilder stringBuilder, Object object) {
        this.formatFileName(strSubstitutor, stringBuilder, false, object);
    }

    public final void formatFileName(StrSubstitutor strSubstitutor, StringBuilder stringBuilder, boolean bl, Object object) {
        long l = bl && this.currentFileTime != 0L ? this.currentFileTime : (this.prevFileTime != 0L ? this.prevFileTime : System.currentTimeMillis());
        this.formatFileName(stringBuilder, new Date(l), object);
        Log4jLogEvent log4jLogEvent = new Log4jLogEvent.Builder().setTimeMillis(l).build();
        String string = strSubstitutor.replace((LogEvent)log4jLogEvent, stringBuilder);
        stringBuilder.setLength(0);
        stringBuilder.append(string);
    }

    protected final void formatFileName(StringBuilder stringBuilder, Object ... objectArray) {
        for (int i = 0; i < this.patternConverters.length; ++i) {
            int n = stringBuilder.length();
            this.patternConverters[i].format(stringBuilder, objectArray);
            if (this.patternFields[i] == null) continue;
            this.patternFields[i].format(n, stringBuilder);
        }
    }

    private RolloverFrequency calculateFrequency(String string) {
        if (this.patternContains(string, 'S')) {
            return RolloverFrequency.EVERY_MILLISECOND;
        }
        if (this.patternContains(string, 's')) {
            return RolloverFrequency.EVERY_SECOND;
        }
        if (this.patternContains(string, 'm')) {
            return RolloverFrequency.EVERY_MINUTE;
        }
        if (this.patternContains(string, HOUR_CHARS)) {
            return RolloverFrequency.HOURLY;
        }
        if (this.patternContains(string, DAY_CHARS)) {
            return RolloverFrequency.DAILY;
        }
        if (this.patternContains(string, WEEK_CHARS)) {
            return RolloverFrequency.WEEKLY;
        }
        if (this.patternContains(string, 'M')) {
            return RolloverFrequency.MONTHLY;
        }
        if (this.patternContains(string, 'y')) {
            return RolloverFrequency.ANNUALLY;
        }
        return null;
    }

    private PatternParser createPatternParser() {
        return new PatternParser(null, KEY, null);
    }

    private boolean patternContains(String string, char ... cArray) {
        for (char c : cArray) {
            if (!this.patternContains(string, c)) continue;
            return false;
        }
        return true;
    }

    private boolean patternContains(String string, char c) {
        return string.indexOf(c) >= 0;
    }

    public RolloverFrequency getFrequency() {
        return this.frequency;
    }

    public long getNextFileTime() {
        return this.nextFileTime;
    }
}

