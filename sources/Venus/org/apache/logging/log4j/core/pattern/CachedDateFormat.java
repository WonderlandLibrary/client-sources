/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.TimeZone;

final class CachedDateFormat
extends DateFormat {
    public static final int NO_MILLISECONDS = -2;
    public static final int UNRECOGNIZED_MILLISECONDS = -1;
    private static final long serialVersionUID = -1253877934598423628L;
    private static final String DIGITS = "0123456789";
    private static final int MAGIC1 = 654;
    private static final String MAGICSTRING1 = "654";
    private static final int MAGIC2 = 987;
    private static final String MAGICSTRING2 = "987";
    private static final String ZERO_STRING = "000";
    private static final int BUF_SIZE = 50;
    private static final int DEFAULT_VALIDITY = 1000;
    private static final int THREE_DIGITS = 100;
    private static final int TWO_DIGITS = 10;
    private static final long SLOTS = 1000L;
    private final DateFormat formatter;
    private int millisecondStart;
    private long slotBegin;
    private final StringBuffer cache = new StringBuffer(50);
    private final int expiration;
    private long previousTime;
    private final Date tmpDate = new Date(0L);

    public CachedDateFormat(DateFormat dateFormat, int n) {
        if (dateFormat == null) {
            throw new IllegalArgumentException("dateFormat cannot be null");
        }
        if (n < 0) {
            throw new IllegalArgumentException("expiration must be non-negative");
        }
        this.formatter = dateFormat;
        this.expiration = n;
        this.millisecondStart = 0;
        this.previousTime = Long.MIN_VALUE;
        this.slotBegin = Long.MIN_VALUE;
    }

    public static int findMillisecondStart(long l, String string, DateFormat dateFormat) {
        String string2;
        long l2 = l / 1000L * 1000L;
        if (l2 > l) {
            l2 -= 1000L;
        }
        int n = (int)(l - l2);
        int n2 = 654;
        String string3 = MAGICSTRING1;
        if (n == 654) {
            n2 = 987;
            string3 = MAGICSTRING2;
        }
        if ((string2 = dateFormat.format(new Date(l2 + (long)n2))).length() != string.length()) {
            return 1;
        }
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == string2.charAt(i)) continue;
            StringBuffer stringBuffer = new StringBuffer("ABC");
            CachedDateFormat.millisecondFormat(n, stringBuffer, 0);
            String string4 = dateFormat.format(new Date(l2));
            if (string4.length() == string.length() && string3.regionMatches(0, string2, i, string3.length()) && stringBuffer.toString().regionMatches(0, string, i, string3.length()) && ZERO_STRING.regionMatches(0, string4, i, 0)) {
                return i;
            }
            return 1;
        }
        return 1;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        this.format(date.getTime(), stringBuffer);
        return stringBuffer;
    }

    public StringBuffer format(long l, StringBuffer stringBuffer) {
        if (l == this.previousTime) {
            stringBuffer.append(this.cache);
            return stringBuffer;
        }
        if (this.millisecondStart != -1 && l < this.slotBegin + (long)this.expiration && l >= this.slotBegin && l < this.slotBegin + 1000L) {
            if (this.millisecondStart >= 0) {
                CachedDateFormat.millisecondFormat((int)(l - this.slotBegin), this.cache, this.millisecondStart);
            }
            this.previousTime = l;
            stringBuffer.append(this.cache);
            return stringBuffer;
        }
        this.cache.setLength(0);
        this.tmpDate.setTime(l);
        this.cache.append(this.formatter.format(this.tmpDate));
        stringBuffer.append(this.cache);
        this.previousTime = l;
        this.slotBegin = this.previousTime / 1000L * 1000L;
        if (this.slotBegin > this.previousTime) {
            this.slotBegin -= 1000L;
        }
        if (this.millisecondStart >= 0) {
            this.millisecondStart = CachedDateFormat.findMillisecondStart(l, this.cache.toString(), this.formatter);
        }
        return stringBuffer;
    }

    private static void millisecondFormat(int n, StringBuffer stringBuffer, int n2) {
        stringBuffer.setCharAt(n2, DIGITS.charAt(n / 100));
        stringBuffer.setCharAt(n2 + 1, DIGITS.charAt(n / 10 % 10));
        stringBuffer.setCharAt(n2 + 2, DIGITS.charAt(n % 10));
    }

    @Override
    public void setTimeZone(TimeZone timeZone) {
        this.formatter.setTimeZone(timeZone);
        this.previousTime = Long.MIN_VALUE;
        this.slotBegin = Long.MIN_VALUE;
    }

    @Override
    public Date parse(String string, ParsePosition parsePosition) {
        return this.formatter.parse(string, parsePosition);
    }

    @Override
    public NumberFormat getNumberFormat() {
        return this.formatter.getNumberFormat();
    }

    public static int getMaximumCacheValidity(String string) {
        int n = string.indexOf(83);
        if (n >= 0 && n != string.lastIndexOf("SSS")) {
            return 0;
        }
        return 1;
    }
}

