/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Date;
import java.util.TimeZone;
import org.apache.http.impl.cookie.DateParseException;

@Deprecated
public final class DateUtils {
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_RFC1036 = "EEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    public static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    public static Date parseDate(String string) throws DateParseException {
        return DateUtils.parseDate(string, null, null);
    }

    public static Date parseDate(String string, String[] stringArray) throws DateParseException {
        return DateUtils.parseDate(string, stringArray, null);
    }

    public static Date parseDate(String string, String[] stringArray, Date date) throws DateParseException {
        Date date2 = org.apache.http.client.utils.DateUtils.parseDate(string, stringArray, date);
        if (date2 == null) {
            throw new DateParseException("Unable to parse the date " + string);
        }
        return date2;
    }

    public static String formatDate(Date date) {
        return org.apache.http.client.utils.DateUtils.formatDate(date);
    }

    public static String formatDate(Date date, String string) {
        return org.apache.http.client.utils.DateUtils.formatDate(date, string);
    }

    private DateUtils() {
    }
}

