/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.CalendarCache;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class HebrewCalendar
extends Calendar {
    private static final long serialVersionUID = -1952524560588825816L;
    public static final int TISHRI = 0;
    public static final int HESHVAN = 1;
    public static final int KISLEV = 2;
    public static final int TEVET = 3;
    public static final int SHEVAT = 4;
    public static final int ADAR_1 = 5;
    public static final int ADAR = 6;
    public static final int NISAN = 7;
    public static final int IYAR = 8;
    public static final int SIVAN = 9;
    public static final int TAMUZ = 10;
    public static final int AV = 11;
    public static final int ELUL = 12;
    private static final int[][] LIMITS = new int[][]{{0, 0, 0, 0}, {-5000000, -5000000, 5000000, 5000000}, {0, 0, 12, 12}, {1, 1, 51, 56}, new int[0], {1, 1, 29, 30}, {1, 1, 353, 385}, new int[0], {-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], new int[0]};
    private static final int[][] MONTH_LENGTH = new int[][]{{30, 30, 30}, {29, 29, 30}, {29, 30, 30}, {29, 29, 29}, {30, 30, 30}, {30, 30, 30}, {29, 29, 29}, {30, 30, 30}, {29, 29, 29}, {30, 30, 30}, {29, 29, 29}, {30, 30, 30}, {29, 29, 29}};
    private static final int[][] MONTH_START = new int[][]{{0, 0, 0}, {30, 30, 30}, {59, 59, 60}, {88, 89, 90}, {117, 118, 119}, {147, 148, 149}, {147, 148, 149}, {176, 177, 178}, {206, 207, 208}, {235, 236, 237}, {265, 266, 267}, {294, 295, 296}, {324, 325, 326}, {353, 354, 355}};
    private static final int[][] LEAP_MONTH_START = new int[][]{{0, 0, 0}, {30, 30, 30}, {59, 59, 60}, {88, 89, 90}, {117, 118, 119}, {147, 148, 149}, {177, 178, 179}, {206, 207, 208}, {236, 237, 238}, {265, 266, 267}, {295, 296, 297}, {324, 325, 326}, {354, 355, 356}, {383, 384, 385}};
    private static CalendarCache cache = new CalendarCache();
    private static final long HOUR_PARTS = 1080L;
    private static final long DAY_PARTS = 25920L;
    private static final int MONTH_DAYS = 29;
    private static final long MONTH_FRACT = 13753L;
    private static final long MONTH_PARTS = 765433L;
    private static final long BAHARAD = 12084L;

    public HebrewCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public HebrewCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public HebrewCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    public HebrewCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    public HebrewCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public HebrewCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public HebrewCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public HebrewCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    public HebrewCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    @Override
    public void add(int n, int n2) {
        switch (n) {
            case 2: {
                int n3 = this.get(2);
                int n4 = this.get(1);
                if (n2 > 0) {
                    boolean bl = n3 < 5;
                    n3 += n2;
                    while (true) {
                        if (bl && n3 >= 5 && !HebrewCalendar.isLeapYear(n4)) {
                            ++n3;
                        }
                        if (n3 > 12) {
                            n3 -= 13;
                            ++n4;
                            bl = true;
                            continue;
                        }
                        break;
                    }
                } else {
                    boolean bl = n3 > 5;
                    n3 += n2;
                    while (true) {
                        if (bl && n3 <= 5 && !HebrewCalendar.isLeapYear(n4)) {
                            --n3;
                        }
                        if (n3 >= 0) break;
                        n3 += 13;
                        --n4;
                        bl = true;
                    }
                }
                this.set(2, n3);
                this.set(1, n4);
                this.pinField(5);
                break;
            }
            default: {
                super.add(n, n2);
            }
        }
    }

    @Override
    public void roll(int n, int n2) {
        switch (n) {
            case 2: {
                int n3 = this.get(2);
                int n4 = this.get(1);
                boolean bl = HebrewCalendar.isLeapYear(n4);
                int n5 = HebrewCalendar.monthsInYear(n4);
                int n6 = n3 + n2 % n5;
                if (!bl) {
                    if (n2 > 0 && n3 < 5 && n6 >= 5) {
                        ++n6;
                    } else if (n2 < 0 && n3 > 5 && n6 <= 5) {
                        --n6;
                    }
                }
                this.set(2, (n6 + 13) % 13);
                this.pinField(5);
                return;
            }
        }
        super.roll(n, n2);
    }

    private static long startOfYear(int n) {
        long l = cache.get(n);
        if (l == CalendarCache.EMPTY) {
            int n2 = (235 * n - 234) / 19;
            long l2 = (long)n2 * 13753L + 12084L;
            l = (long)(n2 * 29) + l2 / 25920L;
            l2 %= 25920L;
            int n3 = (int)(l % 7L);
            if (n3 == 2 || n3 == 4 || n3 == 6) {
                n3 = (int)(++l % 7L);
            }
            if (n3 == 1 && l2 > 16404L && !HebrewCalendar.isLeapYear(n)) {
                l += 2L;
            } else if (n3 == 0 && l2 > 23269L && HebrewCalendar.isLeapYear(n - 1)) {
                ++l;
            }
            cache.put(n, l);
        }
        return l;
    }

    private final int yearType(int n) {
        int n2 = this.handleGetYearLength(n);
        if (n2 > 380) {
            n2 -= 30;
        }
        int n3 = 0;
        switch (n2) {
            case 353: {
                n3 = 0;
                break;
            }
            case 354: {
                n3 = 1;
                break;
            }
            case 355: {
                n3 = 2;
                break;
            }
            default: {
                throw new IllegalArgumentException("Illegal year length " + n2 + " in year " + n);
            }
        }
        return n3;
    }

    @Deprecated
    public static boolean isLeapYear(int n) {
        int n2;
        return n2 >= ((n2 = (n * 12 + 17) % 19) < 0 ? -7 : 12);
    }

    private static int monthsInYear(int n) {
        return HebrewCalendar.isLeapYear(n) ? 13 : 12;
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        while (n2 < 0) {
            n2 += HebrewCalendar.monthsInYear(--n);
        }
        while (n2 > 12) {
            n2 -= HebrewCalendar.monthsInYear(n++);
        }
        switch (n2) {
            case 1: 
            case 2: {
                return MONTH_LENGTH[n2][this.yearType(n)];
            }
        }
        return MONTH_LENGTH[n2][0];
    }

    @Override
    protected int handleGetYearLength(int n) {
        return (int)(HebrewCalendar.startOfYear(n + 1) - HebrewCalendar.startOfYear(n));
    }

    @Override
    @Deprecated
    protected void validateField(int n) {
        if (n == 2 && !HebrewCalendar.isLeapYear(this.handleGetExtendedYear()) && this.internalGet(2) == 5) {
            throw new IllegalArgumentException("MONTH cannot be ADAR_1(5) except leap years");
        }
        super.validateField(n);
    }

    @Override
    protected void handleComputeFields(int n) {
        long l = n - 347997;
        long l2 = l * 25920L / 765433L;
        int n2 = (int)((19L * l2 + 234L) / 235L) + 1;
        long l3 = HebrewCalendar.startOfYear(n2);
        int n3 = (int)(l - l3);
        while (n3 < 1) {
            l3 = HebrewCalendar.startOfYear(--n2);
            n3 = (int)(l - l3);
        }
        int n4 = this.yearType(n2);
        int[][] nArray = HebrewCalendar.isLeapYear(n2) ? LEAP_MONTH_START : MONTH_START;
        int n5 = 0;
        while (n3 > nArray[n5][n4]) {
            ++n5;
        }
        int n6 = n3 - nArray[--n5][n4];
        this.internalSet(0, 0);
        this.internalSet(1, n2);
        this.internalSet(19, n2);
        this.internalSet(2, n5);
        this.internalSet(5, n6);
        this.internalSet(6, n3);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : this.internalGet(1, 1);
        return n;
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        while (n2 < 0) {
            n2 += HebrewCalendar.monthsInYear(--n);
        }
        while (n2 > 12) {
            n2 -= HebrewCalendar.monthsInYear(n++);
        }
        long l = HebrewCalendar.startOfYear(n);
        if (n2 != 0) {
            l = HebrewCalendar.isLeapYear(n) ? (l += (long)LEAP_MONTH_START[n2][this.yearType(n)]) : (l += (long)MONTH_START[n2][this.yearType(n)]);
        }
        return (int)(l + 347997L);
    }

    @Override
    public String getType() {
        return "hebrew";
    }
}

