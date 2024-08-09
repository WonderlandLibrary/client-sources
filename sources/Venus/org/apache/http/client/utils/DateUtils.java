/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.utils;

import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.apache.http.util.Args;

public final class DateUtils {
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_RFC1036 = "EEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    private static final String[] DEFAULT_PATTERNS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"};
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
    public static final TimeZone GMT;

    public static Date parseDate(String string) {
        return DateUtils.parseDate(string, null, null);
    }

    public static Date parseDate(String string, String[] stringArray) {
        return DateUtils.parseDate(string, stringArray, null);
    }

    public static Date parseDate(String string, String[] stringArray, Date date) {
        Args.notNull(string, "Date value");
        String[] stringArray2 = stringArray != null ? stringArray : DEFAULT_PATTERNS;
        Date date2 = date != null ? date : DEFAULT_TWO_DIGIT_YEAR_START;
        String string2 = string;
        if (string2.length() > 1 && string2.startsWith("'") && string2.endsWith("'")) {
            string2 = string2.substring(1, string2.length() - 1);
        }
        for (String string3 : stringArray2) {
            SimpleDateFormat simpleDateFormat = DateFormatHolder.formatFor(string3);
            simpleDateFormat.set2DigitYearStart(date2);
            ParsePosition parsePosition = new ParsePosition(0);
            Date date3 = simpleDateFormat.parse(string2, parsePosition);
            if (parsePosition.getIndex() == 0) continue;
            return date3;
        }
        return null;
    }

    public static String formatDate(Date date) {
        return DateUtils.formatDate(date, PATTERN_RFC1123);
    }

    public static String formatDate(Date date, String string) {
        Args.notNull(date, "Date");
        Args.notNull(string, "Pattern");
        SimpleDateFormat simpleDateFormat = DateFormatHolder.formatFor(string);
        return simpleDateFormat.format(date);
    }

    public static void clearThreadLocal() {
        DateFormatHolder.clearThreadLocal();
    }

    private DateUtils() {
    }

    static {
        GMT = TimeZone.getTimeZone("GMT");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(GMT);
        calendar.set(2000, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
    }

    static final class DateFormatHolder {
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal();

        DateFormatHolder() {
        }

        public static SimpleDateFormat formatFor(String string) {
            SimpleDateFormat simpleDateFormat;
            Map<String, SimpleDateFormat> map;
            SoftReference<Map<String, SimpleDateFormat>> softReference = THREADLOCAL_FORMATS.get();
            Map<String, SimpleDateFormat> map2 = map = softReference == null ? null : softReference.get();
            if (map == null) {
                map = new HashMap<String, SimpleDateFormat>();
                THREADLOCAL_FORMATS.set(new SoftReference<Map<String, SimpleDateFormat>>(map));
            }
            if ((simpleDateFormat = map.get(string)) == null) {
                simpleDateFormat = new SimpleDateFormat(string, Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                map.put(string, simpleDateFormat);
            }
            return simpleDateFormat;
        }

        public static void clearThreadLocal() {
            THREADLOCAL_FORMATS.remove();
        }
    }
}

