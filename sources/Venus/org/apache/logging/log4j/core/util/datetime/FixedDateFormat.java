/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util.datetime;

import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;
import org.apache.logging.log4j.core.util.datetime.FastDateFormat;

public class FixedDateFormat {
    private final FixedFormat fixedFormat;
    private final TimeZone timeZone;
    private final int length;
    private final FastDateFormat fastDateFormat;
    private final char timeSeparatorChar;
    private final char millisSeparatorChar;
    private final int timeSeparatorLength;
    private final int millisSeparatorLength;
    private volatile long midnightToday = 0L;
    private volatile long midnightTomorrow = 0L;
    private char[] cachedDate;
    private int dateLength;

    FixedDateFormat(FixedFormat fixedFormat, TimeZone timeZone) {
        this.fixedFormat = Objects.requireNonNull(fixedFormat);
        this.timeZone = Objects.requireNonNull(timeZone);
        this.timeSeparatorChar = FixedFormat.access$000(fixedFormat);
        this.timeSeparatorLength = FixedFormat.access$100(fixedFormat);
        this.millisSeparatorChar = FixedFormat.access$200(fixedFormat);
        this.millisSeparatorLength = FixedFormat.access$300(fixedFormat);
        this.length = fixedFormat.getLength();
        this.fastDateFormat = fixedFormat.getFastDateFormat(timeZone);
    }

    public static FixedDateFormat createIfSupported(String ... stringArray) {
        TimeZone timeZone;
        if (stringArray == null || stringArray.length == 0 || stringArray[0] == null) {
            return new FixedDateFormat(FixedFormat.DEFAULT, TimeZone.getDefault());
        }
        if (stringArray.length > 1) {
            timeZone = stringArray[5] != null ? TimeZone.getTimeZone(stringArray[5]) : TimeZone.getDefault();
        } else {
            if (stringArray.length > 2) {
                return null;
            }
            timeZone = TimeZone.getDefault();
        }
        FixedFormat fixedFormat = FixedFormat.lookup(stringArray[0]);
        return fixedFormat == null ? null : new FixedDateFormat(fixedFormat, timeZone);
    }

    public static FixedDateFormat create(FixedFormat fixedFormat) {
        return new FixedDateFormat(fixedFormat, TimeZone.getDefault());
    }

    public static FixedDateFormat create(FixedFormat fixedFormat, TimeZone timeZone) {
        return new FixedDateFormat(fixedFormat, timeZone != null ? timeZone : TimeZone.getDefault());
    }

    public String getFormat() {
        return this.fixedFormat.getPattern();
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public long millisSinceMidnight(long l) {
        if (l >= this.midnightTomorrow || l < this.midnightToday) {
            this.updateMidnightMillis(l);
        }
        return l - this.midnightToday;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateMidnightMillis(long l) {
        if (l >= this.midnightTomorrow || l < this.midnightToday) {
            FixedDateFormat fixedDateFormat = this;
            synchronized (fixedDateFormat) {
                this.updateCachedDate(l);
                this.midnightToday = this.calcMidnightMillis(l, 0);
                this.midnightTomorrow = this.calcMidnightMillis(l, 1);
            }
        }
    }

    private long calcMidnightMillis(long l, int n) {
        Calendar calendar = Calendar.getInstance(this.timeZone);
        calendar.setTimeInMillis(l);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(5, n);
        return calendar.getTimeInMillis();
    }

    private void updateCachedDate(long l) {
        if (this.fastDateFormat != null) {
            StringBuilder stringBuilder = this.fastDateFormat.format(l, new StringBuilder());
            this.cachedDate = stringBuilder.toString().toCharArray();
            this.dateLength = stringBuilder.length();
        }
    }

    public String format(long l) {
        char[] cArray = new char[this.length << 1];
        int n = this.format(l, cArray, 0);
        return new String(cArray, 0, n);
    }

    public int format(long l, char[] cArray, int n) {
        int n2 = (int)this.millisSinceMidnight(l);
        this.writeDate(cArray, n);
        return this.writeTime(n2, cArray, n + this.dateLength) - n;
    }

    private void writeDate(char[] cArray, int n) {
        if (this.cachedDate != null) {
            System.arraycopy(this.cachedDate, 0, cArray, n, this.dateLength);
        }
    }

    private int writeTime(int n, char[] cArray, int n2) {
        int n3 = n / 3600000;
        int n4 = (n -= 3600000 * n3) / 60000;
        int n5 = (n -= 60000 * n4) / 1000;
        n -= 1000 * n5;
        int n6 = n3 / 10;
        cArray[n2++] = (char)(n6 + 48);
        cArray[n2++] = (char)(n3 - 10 * n6 + 48);
        cArray[n2] = this.timeSeparatorChar;
        n2 += this.timeSeparatorLength;
        n6 = n4 / 10;
        cArray[n2++] = (char)(n6 + 48);
        cArray[n2++] = (char)(n4 - 10 * n6 + 48);
        cArray[n2] = this.timeSeparatorChar;
        n2 += this.timeSeparatorLength;
        n6 = n5 / 10;
        cArray[n2++] = (char)(n6 + 48);
        cArray[n2++] = (char)(n5 - 10 * n6 + 48);
        cArray[n2] = this.millisSeparatorChar;
        n2 += this.millisSeparatorLength;
        n6 = n / 100;
        cArray[n2++] = (char)(n6 + 48);
        n -= 100 * n6;
        n6 = n / 10;
        cArray[n2++] = (char)(n6 + 48);
        cArray[n2++] = (char)((n -= 10 * n6) + 48);
        return n2;
    }

    public static enum FixedFormat {
        ABSOLUTE("HH:mm:ss,SSS", null, 0, ':', 1, ',', 1),
        ABSOLUTE_PERIOD("HH:mm:ss.SSS", null, 0, ':', 1, '.', 1),
        COMPACT("yyyyMMddHHmmssSSS", "yyyyMMdd", 0, ' ', 0, ' ', 0),
        DATE("dd MMM yyyy HH:mm:ss,SSS", "dd MMM yyyy ", 0, ':', 1, ',', 1),
        DATE_PERIOD("dd MMM yyyy HH:mm:ss.SSS", "dd MMM yyyy ", 0, ':', 1, '.', 1),
        DEFAULT("yyyy-MM-dd HH:mm:ss,SSS", "yyyy-MM-dd ", 0, ':', 1, ',', 1),
        DEFAULT_PERIOD("yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd ", 0, ':', 1, '.', 1),
        ISO8601_BASIC("yyyyMMdd'T'HHmmss,SSS", "yyyyMMdd'T'", 2, ' ', 0, ',', 1),
        ISO8601_BASIC_PERIOD("yyyyMMdd'T'HHmmss.SSS", "yyyyMMdd'T'", 2, ' ', 0, '.', 1),
        ISO8601("yyyy-MM-dd'T'HH:mm:ss,SSS", "yyyy-MM-dd'T'", 2, ':', 1, ',', 1),
        ISO8601_PERIOD("yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'", 2, ':', 1, '.', 1);

        private final String pattern;
        private final String datePattern;
        private final int escapeCount;
        private final char timeSeparatorChar;
        private final int timeSeparatorLength;
        private final char millisSeparatorChar;
        private final int millisSeparatorLength;

        private FixedFormat(String string2, String string3, int n2, char c, int n3, char c2, int n4) {
            this.timeSeparatorChar = c;
            this.timeSeparatorLength = n3;
            this.millisSeparatorChar = c2;
            this.millisSeparatorLength = n4;
            this.pattern = Objects.requireNonNull(string2);
            this.datePattern = string3;
            this.escapeCount = n2;
        }

        public String getPattern() {
            return this.pattern;
        }

        public String getDatePattern() {
            return this.datePattern;
        }

        public static FixedFormat lookup(String string) {
            for (FixedFormat fixedFormat : FixedFormat.values()) {
                if (!fixedFormat.name().equals(string) && !fixedFormat.getPattern().equals(string)) continue;
                return fixedFormat;
            }
            return null;
        }

        public int getLength() {
            return this.pattern.length() - this.escapeCount;
        }

        public int getDatePatternLength() {
            return this.getDatePattern() == null ? 0 : this.getDatePattern().length() - this.escapeCount;
        }

        public FastDateFormat getFastDateFormat() {
            return this.getFastDateFormat(null);
        }

        public FastDateFormat getFastDateFormat(TimeZone timeZone) {
            return this.getDatePattern() == null ? null : FastDateFormat.getInstance(this.getDatePattern(), timeZone);
        }

        static char access$000(FixedFormat fixedFormat) {
            return fixedFormat.timeSeparatorChar;
        }

        static int access$100(FixedFormat fixedFormat) {
            return fixedFormat.timeSeparatorLength;
        }

        static char access$200(FixedFormat fixedFormat) {
            return fixedFormat.millisSeparatorChar;
        }

        static int access$300(FixedFormat fixedFormat) {
            return fixedFormat.millisSeparatorLength;
        }
    }
}

