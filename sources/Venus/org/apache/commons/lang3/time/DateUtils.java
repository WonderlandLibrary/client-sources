/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.time;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.FastDateParser;

public class DateUtils {
    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MILLIS_PER_DAY = 86400000L;
    public static final int SEMI_MONTH = 1001;
    private static final int[][] fields = new int[][]{{14}, {13}, {12}, {11, 10}, {5, 5, 9}, {2, 1001}, {1}, {0}};
    public static final int RANGE_WEEK_SUNDAY = 1;
    public static final int RANGE_WEEK_MONDAY = 2;
    public static final int RANGE_WEEK_RELATIVE = 3;
    public static final int RANGE_WEEK_CENTER = 4;
    public static final int RANGE_MONTH_SUNDAY = 5;
    public static final int RANGE_MONTH_MONDAY = 6;

    public static boolean isSameDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return DateUtils.isSameDay(calendar, calendar2);
    }

    public static boolean isSameDay(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(0) == calendar2.get(0) && calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6);
    }

    public static boolean isSameInstant(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date.getTime() == date2.getTime();
    }

    public static boolean isSameInstant(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.getTime().getTime() == calendar2.getTime().getTime();
    }

    public static boolean isSameLocalTime(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(14) == calendar2.get(14) && calendar.get(13) == calendar2.get(13) && calendar.get(12) == calendar2.get(12) && calendar.get(11) == calendar2.get(11) && calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1) && calendar.get(0) == calendar2.get(0) && calendar.getClass() == calendar2.getClass();
    }

    public static Date parseDate(String string, String ... stringArray) throws ParseException {
        return DateUtils.parseDate(string, null, stringArray);
    }

    public static Date parseDate(String string, Locale locale, String ... stringArray) throws ParseException {
        return DateUtils.parseDateWithLeniency(string, locale, stringArray, true);
    }

    public static Date parseDateStrictly(String string, String ... stringArray) throws ParseException {
        return DateUtils.parseDateStrictly(string, null, stringArray);
    }

    public static Date parseDateStrictly(String string, Locale locale, String ... stringArray) throws ParseException {
        return DateUtils.parseDateWithLeniency(string, locale, stringArray, false);
    }

    private static Date parseDateWithLeniency(String string, Locale locale, String[] stringArray, boolean bl) throws ParseException {
        if (string == null || stringArray == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
        TimeZone timeZone = TimeZone.getDefault();
        Locale locale2 = locale == null ? Locale.getDefault() : locale;
        ParsePosition parsePosition = new ParsePosition(0);
        Calendar calendar = Calendar.getInstance(timeZone, locale2);
        calendar.setLenient(bl);
        for (String string2 : stringArray) {
            FastDateParser fastDateParser = new FastDateParser(string2, timeZone, locale2);
            calendar.clear();
            try {
                if (fastDateParser.parse(string, parsePosition, calendar) && parsePosition.getIndex() == string.length()) {
                    return calendar.getTime();
                }
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            parsePosition.setIndex(0);
        }
        throw new ParseException("Unable to parse the date: " + string, -1);
    }

    public static Date addYears(Date date, int n) {
        return DateUtils.add(date, 1, n);
    }

    public static Date addMonths(Date date, int n) {
        return DateUtils.add(date, 2, n);
    }

    public static Date addWeeks(Date date, int n) {
        return DateUtils.add(date, 3, n);
    }

    public static Date addDays(Date date, int n) {
        return DateUtils.add(date, 5, n);
    }

    public static Date addHours(Date date, int n) {
        return DateUtils.add(date, 11, n);
    }

    public static Date addMinutes(Date date, int n) {
        return DateUtils.add(date, 12, n);
    }

    public static Date addSeconds(Date date, int n) {
        return DateUtils.add(date, 13, n);
    }

    public static Date addMilliseconds(Date date, int n) {
        return DateUtils.add(date, 14, n);
    }

    private static Date add(Date date, int n, int n2) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(n, n2);
        return calendar.getTime();
    }

    public static Date setYears(Date date, int n) {
        return DateUtils.set(date, 1, n);
    }

    public static Date setMonths(Date date, int n) {
        return DateUtils.set(date, 2, n);
    }

    public static Date setDays(Date date, int n) {
        return DateUtils.set(date, 5, n);
    }

    public static Date setHours(Date date, int n) {
        return DateUtils.set(date, 11, n);
    }

    public static Date setMinutes(Date date, int n) {
        return DateUtils.set(date, 12, n);
    }

    public static Date setSeconds(Date date, int n) {
        return DateUtils.set(date, 13, n);
    }

    public static Date setMilliseconds(Date date, int n) {
        return DateUtils.set(date, 14, n);
    }

    private static Date set(Date date, int n, int n2) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(true);
        calendar.setTime(date);
        calendar.set(n, n2);
        return calendar.getTime();
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar toCalendar(Date date, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(date);
        return calendar;
    }

    public static Date round(Date date, int n) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateUtils.modify(calendar, n, ModifyType.ROUND);
        return calendar.getTime();
    }

    public static Calendar round(Calendar calendar, int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar2 = (Calendar)calendar.clone();
        DateUtils.modify(calendar2, n, ModifyType.ROUND);
        return calendar2;
    }

    public static Date round(Object object, int n) {
        if (object == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (object instanceof Date) {
            return DateUtils.round((Date)object, n);
        }
        if (object instanceof Calendar) {
            return DateUtils.round((Calendar)object, n).getTime();
        }
        throw new ClassCastException("Could not round " + object);
    }

    public static Date truncate(Date date, int n) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateUtils.modify(calendar, n, ModifyType.TRUNCATE);
        return calendar.getTime();
    }

    public static Calendar truncate(Calendar calendar, int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar2 = (Calendar)calendar.clone();
        DateUtils.modify(calendar2, n, ModifyType.TRUNCATE);
        return calendar2;
    }

    public static Date truncate(Object object, int n) {
        if (object == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (object instanceof Date) {
            return DateUtils.truncate((Date)object, n);
        }
        if (object instanceof Calendar) {
            return DateUtils.truncate((Calendar)object, n).getTime();
        }
        throw new ClassCastException("Could not truncate " + object);
    }

    public static Date ceiling(Date date, int n) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateUtils.modify(calendar, n, ModifyType.CEILING);
        return calendar.getTime();
    }

    public static Calendar ceiling(Calendar calendar, int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar2 = (Calendar)calendar.clone();
        DateUtils.modify(calendar2, n, ModifyType.CEILING);
        return calendar2;
    }

    public static Date ceiling(Object object, int n) {
        if (object == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (object instanceof Date) {
            return DateUtils.ceiling((Date)object, n);
        }
        if (object instanceof Calendar) {
            return DateUtils.ceiling((Calendar)object, n).getTime();
        }
        throw new ClassCastException("Could not find ceiling of for type: " + object.getClass());
    }

    private static void modify(Calendar calendar, int n, ModifyType modifyType) {
        if (calendar.get(1) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        }
        if (n == 14) {
            return;
        }
        Date date = calendar.getTime();
        long l = date.getTime();
        boolean bl = false;
        int n2 = calendar.get(14);
        if (ModifyType.TRUNCATE == modifyType || n2 < 500) {
            l -= (long)n2;
        }
        if (n == 13) {
            bl = true;
        }
        int n3 = calendar.get(13);
        if (!(bl || ModifyType.TRUNCATE != modifyType && n3 >= 30)) {
            l -= (long)n3 * 1000L;
        }
        if (n == 12) {
            bl = true;
        }
        int n4 = calendar.get(12);
        if (!(bl || ModifyType.TRUNCATE != modifyType && n4 >= 30)) {
            l -= (long)n4 * 60000L;
        }
        if (date.getTime() != l) {
            date.setTime(l);
            calendar.setTime(date);
        }
        boolean bl2 = false;
        int[][] nArray = fields;
        int n5 = nArray.length;
        for (int i = 0; i < n5; ++i) {
            int[] nArray2;
            for (int n6 : nArray2 = nArray[i]) {
                if (n6 != n) continue;
                if (modifyType == ModifyType.CEILING || modifyType == ModifyType.ROUND && bl2) {
                    if (n == 1001) {
                        if (calendar.get(5) == 1) {
                            calendar.add(5, 15);
                        } else {
                            calendar.add(5, -15);
                            calendar.add(2, 1);
                        }
                    } else if (n == 9) {
                        if (calendar.get(11) == 0) {
                            calendar.add(11, 12);
                        } else {
                            calendar.add(11, -12);
                            calendar.add(5, 1);
                        }
                    } else {
                        calendar.add(nArray2[0], 1);
                    }
                }
                return;
            }
            int n7 = 0;
            int n8 = 0;
            switch (n) {
                case 1001: {
                    if (nArray2[0] != 5) break;
                    n7 = calendar.get(5) - 1;
                    if (n7 >= 15) {
                        n7 -= 15;
                    }
                    bl2 = n7 > 7;
                    n8 = 1;
                    break;
                }
                case 9: {
                    if (nArray2[0] != 11) break;
                    n7 = calendar.get(11);
                    if (n7 >= 12) {
                        n7 -= 12;
                    }
                    bl2 = n7 >= 6;
                    n8 = 1;
                    break;
                }
            }
            if (n8 == 0) {
                int n6;
                int n9 = calendar.getActualMinimum(nArray2[0]);
                n6 = calendar.getActualMaximum(nArray2[0]);
                n7 = calendar.get(nArray2[0]) - n9;
                boolean bl3 = bl2 = n7 > (n6 - n9) / 2;
            }
            if (n7 == 0) continue;
            calendar.set(nArray2[0], calendar.get(nArray2[0]) - n7);
        }
        throw new IllegalArgumentException("The field " + n + " is not supported");
    }

    public static Iterator<Calendar> iterator(Date date, int n) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DateUtils.iterator(calendar, n);
    }

    public static Iterator<Calendar> iterator(Calendar calendar, int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar2 = null;
        Calendar calendar3 = null;
        int n2 = 1;
        int n3 = 7;
        block0 : switch (n) {
            case 5: 
            case 6: {
                calendar2 = DateUtils.truncate(calendar, 2);
                calendar3 = (Calendar)calendar2.clone();
                calendar3.add(2, 1);
                calendar3.add(5, -1);
                if (n != 6) break;
                n2 = 2;
                n3 = 1;
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                calendar2 = DateUtils.truncate(calendar, 5);
                calendar3 = DateUtils.truncate(calendar, 5);
                switch (n) {
                    case 1: {
                        break block0;
                    }
                    case 2: {
                        n2 = 2;
                        n3 = 1;
                        break block0;
                    }
                    case 3: {
                        n2 = calendar.get(7);
                        n3 = n2 - 1;
                        break block0;
                    }
                    case 4: {
                        n2 = calendar.get(7) - 3;
                        n3 = calendar.get(7) + 3;
                        break block0;
                    }
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("The range style " + n + " is not valid.");
            }
        }
        if (n2 < 1) {
            n2 += 7;
        }
        if (n2 > 7) {
            n2 -= 7;
        }
        if (n3 < 1) {
            n3 += 7;
        }
        if (n3 > 7) {
            n3 -= 7;
        }
        while (calendar2.get(7) != n2) {
            calendar2.add(5, -1);
        }
        while (calendar3.get(7) != n3) {
            calendar3.add(5, 1);
        }
        return new DateIterator(calendar2, calendar3);
    }

    public static Iterator<?> iterator(Object object, int n) {
        if (object == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (object instanceof Date) {
            return DateUtils.iterator((Date)object, n);
        }
        if (object instanceof Calendar) {
            return DateUtils.iterator((Calendar)object, n);
        }
        throw new ClassCastException("Could not iterate based on " + object);
    }

    public static long getFragmentInMilliseconds(Date date, int n) {
        return DateUtils.getFragment(date, n, TimeUnit.MILLISECONDS);
    }

    public static long getFragmentInSeconds(Date date, int n) {
        return DateUtils.getFragment(date, n, TimeUnit.SECONDS);
    }

    public static long getFragmentInMinutes(Date date, int n) {
        return DateUtils.getFragment(date, n, TimeUnit.MINUTES);
    }

    public static long getFragmentInHours(Date date, int n) {
        return DateUtils.getFragment(date, n, TimeUnit.HOURS);
    }

    public static long getFragmentInDays(Date date, int n) {
        return DateUtils.getFragment(date, n, TimeUnit.DAYS);
    }

    public static long getFragmentInMilliseconds(Calendar calendar, int n) {
        return DateUtils.getFragment(calendar, n, TimeUnit.MILLISECONDS);
    }

    public static long getFragmentInSeconds(Calendar calendar, int n) {
        return DateUtils.getFragment(calendar, n, TimeUnit.SECONDS);
    }

    public static long getFragmentInMinutes(Calendar calendar, int n) {
        return DateUtils.getFragment(calendar, n, TimeUnit.MINUTES);
    }

    public static long getFragmentInHours(Calendar calendar, int n) {
        return DateUtils.getFragment(calendar, n, TimeUnit.HOURS);
    }

    public static long getFragmentInDays(Calendar calendar, int n) {
        return DateUtils.getFragment(calendar, n, TimeUnit.DAYS);
    }

    private static long getFragment(Date date, int n, TimeUnit timeUnit) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DateUtils.getFragment(calendar, n, timeUnit);
    }

    private static long getFragment(Calendar calendar, int n, TimeUnit timeUnit) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        long l = 0L;
        int n2 = timeUnit == TimeUnit.DAYS ? 0 : 1;
        switch (n) {
            case 1: {
                l += timeUnit.convert(calendar.get(6) - n2, TimeUnit.DAYS);
                break;
            }
            case 2: {
                l += timeUnit.convert(calendar.get(5) - n2, TimeUnit.DAYS);
                break;
            }
        }
        switch (n) {
            case 1: 
            case 2: 
            case 5: 
            case 6: {
                l += timeUnit.convert(calendar.get(11), TimeUnit.HOURS);
            }
            case 11: {
                l += timeUnit.convert(calendar.get(12), TimeUnit.MINUTES);
            }
            case 12: {
                l += timeUnit.convert(calendar.get(13), TimeUnit.SECONDS);
            }
            case 13: {
                l += timeUnit.convert(calendar.get(14), TimeUnit.MILLISECONDS);
                break;
            }
            case 14: {
                break;
            }
            default: {
                throw new IllegalArgumentException("The fragment " + n + " is not supported");
            }
        }
        return l;
    }

    public static boolean truncatedEquals(Calendar calendar, Calendar calendar2, int n) {
        return DateUtils.truncatedCompareTo(calendar, calendar2, n) == 0;
    }

    public static boolean truncatedEquals(Date date, Date date2, int n) {
        return DateUtils.truncatedCompareTo(date, date2, n) == 0;
    }

    public static int truncatedCompareTo(Calendar calendar, Calendar calendar2, int n) {
        Calendar calendar3 = DateUtils.truncate(calendar, n);
        Calendar calendar4 = DateUtils.truncate(calendar2, n);
        return calendar3.compareTo(calendar4);
    }

    public static int truncatedCompareTo(Date date, Date date2, int n) {
        Date date3 = DateUtils.truncate(date, n);
        Date date4 = DateUtils.truncate(date2, n);
        return date3.compareTo(date4);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static class DateIterator
    implements Iterator<Calendar> {
        private final Calendar endFinal;
        private final Calendar spot;

        DateIterator(Calendar calendar, Calendar calendar2) {
            this.endFinal = calendar2;
            this.spot = calendar;
            this.spot.add(5, -1);
        }

        @Override
        public boolean hasNext() {
            return this.spot.before(this.endFinal);
        }

        @Override
        public Calendar next() {
            if (this.spot.equals(this.endFinal)) {
                throw new NoSuchElementException();
            }
            this.spot.add(5, 1);
            return (Calendar)this.spot.clone();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object next() {
            return this.next();
        }
    }

    private static enum ModifyType {
        TRUNCATE,
        ROUND,
        CEILING;

    }
}

