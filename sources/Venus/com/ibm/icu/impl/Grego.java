/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.Locale;

public class Grego {
    public static final long MIN_MILLIS = -184303902528000000L;
    public static final long MAX_MILLIS = 183882168921600000L;
    public static final int MILLIS_PER_SECOND = 1000;
    public static final int MILLIS_PER_MINUTE = 60000;
    public static final int MILLIS_PER_HOUR = 3600000;
    public static final int MILLIS_PER_DAY = 86400000;
    private static final int JULIAN_1_CE = 1721426;
    private static final int JULIAN_1970_CE = 2440588;
    private static final int[] MONTH_LENGTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] DAYS_BEFORE = new int[]{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};

    public static final boolean isLeapYear(int n) {
        return (n & 3) == 0 && (n % 100 != 0 || n % 400 == 0);
    }

    public static final int monthLength(int n, int n2) {
        return MONTH_LENGTH[n2 + (Grego.isLeapYear(n) ? 12 : 0)];
    }

    public static final int previousMonthLength(int n, int n2) {
        return n2 > 0 ? Grego.monthLength(n, n2 - 1) : 31;
    }

    public static long fieldsToDay(int n, int n2, int n3) {
        int n4 = n - 1;
        long l = (long)(365 * n4) + Grego.floorDivide(n4, 4L) + 1721423L + Grego.floorDivide(n4, 400L) - Grego.floorDivide(n4, 100L) + 2L + (long)DAYS_BEFORE[n2 + (Grego.isLeapYear(n) ? 12 : 0)] + (long)n3;
        return l - 2440588L;
    }

    public static int dayOfWeek(long l) {
        long[] lArray = new long[1];
        Grego.floorDivide(l + 5L, 7L, lArray);
        int n = (int)lArray[0];
        n = n == 0 ? 7 : n;
        return n;
    }

    public static int[] dayToFields(long l, int[] nArray) {
        int n;
        if (nArray == null || nArray.length < 5) {
            nArray = new int[5];
        }
        long[] lArray = new long[1];
        long l2 = Grego.floorDivide(l += 719162L, 146097L, lArray);
        long l3 = Grego.floorDivide(lArray[0], 36524L, lArray);
        long l4 = Grego.floorDivide(lArray[0], 1461L, lArray);
        long l5 = Grego.floorDivide(lArray[0], 365L, lArray);
        int n2 = (int)(400L * l2 + 100L * l3 + 4L * l4 + l5);
        int n3 = (int)lArray[0];
        if (l3 == 4L || l5 == 4L) {
            n3 = 365;
        } else {
            ++n2;
        }
        boolean bl = Grego.isLeapYear(n2);
        int n4 = 0;
        int n5 = n = bl ? 60 : 59;
        if (n3 >= n) {
            n4 = bl ? 1 : 2;
        }
        int n6 = (12 * (n3 + n4) + 6) / 367;
        int n7 = n3 - DAYS_BEFORE[bl ? n6 + 12 : n6] + 1;
        int n8 = (int)((l + 2L) % 7L);
        if (n8 < 1) {
            n8 += 7;
        }
        nArray[0] = n2;
        nArray[1] = n6;
        nArray[2] = n7;
        nArray[3] = n8;
        nArray[4] = ++n3;
        return nArray;
    }

    public static int[] timeToFields(long l, int[] nArray) {
        if (nArray == null || nArray.length < 6) {
            nArray = new int[6];
        }
        long[] lArray = new long[1];
        long l2 = Grego.floorDivide(l, 86400000L, lArray);
        Grego.dayToFields(l2, nArray);
        nArray[5] = (int)lArray[0];
        return nArray;
    }

    public static long floorDivide(long l, long l2) {
        return l >= 0L ? l / l2 : (l + 1L) / l2 - 1L;
    }

    private static long floorDivide(long l, long l2, long[] lArray) {
        if (l >= 0L) {
            lArray[0] = l % l2;
            return l / l2;
        }
        long l3 = (l + 1L) / l2 - 1L;
        lArray[0] = l - l3 * l2;
        return l3;
    }

    public static int getDayOfWeekInMonth(int n, int n2, int n3) {
        int n4 = (n3 + 6) / 7;
        if (n4 == 4) {
            if (n3 + 7 > Grego.monthLength(n, n2)) {
                n4 = -1;
            }
        } else if (n4 == 5) {
            n4 = -1;
        }
        return n4;
    }

    public static String timeToString(long l) {
        int[] nArray = Grego.timeToFields(l, null);
        int n = nArray[5];
        int n2 = n / 3600000;
        int n3 = (n %= 3600000) / 60000;
        int n4 = (n %= 60000) / 1000;
        return String.format((Locale)null, "%04d-%02d-%02dT%02d:%02d:%02d.%03dZ", nArray[0], nArray[1] + 1, nArray[2], n2, n3, n4, n %= 1000);
    }
}

