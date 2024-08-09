/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.Assert;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateFormats {
    private static final String ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String ISO_8601_MILLIS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final ThreadLocal<DateFormat> ISO_8601 = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormats.ISO_8601_PATTERN);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return simpleDateFormat;
        }

        @Override
        protected Object initialValue() {
            return this.initialValue();
        }
    };
    private static final ThreadLocal<DateFormat> ISO_8601_MILLIS = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormats.ISO_8601_MILLIS_PATTERN);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return simpleDateFormat;
        }

        @Override
        protected Object initialValue() {
            return this.initialValue();
        }
    };

    private DateFormats() {
    }

    public static String formatIso8601(Date date) {
        return DateFormats.formatIso8601(date, true);
    }

    public static String formatIso8601(Date date, boolean bl) {
        if (bl) {
            return ISO_8601_MILLIS.get().format(date);
        }
        return ISO_8601.get().format(date);
    }

    public static Date parseIso8601Date(String string) throws ParseException {
        Assert.notNull(string, "String argument cannot be null.");
        if (string.lastIndexOf(46) > -1) {
            return ISO_8601_MILLIS.get().parse(string);
        }
        return ISO_8601.get().parse(string);
    }
}

