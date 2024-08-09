/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

@Deprecated
public class PersianCalendar
extends Calendar {
    private static final long serialVersionUID = -6727306982975111643L;
    private static final int[][] MONTH_COUNT = new int[][]{{31, 31, 0}, {31, 31, 31}, {31, 31, 62}, {31, 31, 93}, {31, 31, 124}, {31, 31, 155}, {30, 30, 186}, {30, 30, 216}, {30, 30, 246}, {30, 30, 276}, {30, 30, 306}, {29, 30, 336}};
    private static final int PERSIAN_EPOCH = 1948320;
    private static final int[][] LIMITS = new int[][]{{0, 0, 0, 0}, {-5000000, -5000000, 5000000, 5000000}, {0, 0, 11, 11}, {1, 1, 52, 53}, new int[0], {1, 1, 29, 31}, {1, 1, 365, 366}, new int[0], {-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], new int[0]};

    @Deprecated
    public PersianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    @Deprecated
    public PersianCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    @Deprecated
    public PersianCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    @Deprecated
    public PersianCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    @Deprecated
    public PersianCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    @Deprecated
    public PersianCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    @Deprecated
    public PersianCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    @Deprecated
    public PersianCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    @Deprecated
    public PersianCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    @Override
    @Deprecated
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    private static final boolean isLeapYear(int n) {
        int[] nArray = new int[1];
        PersianCalendar.floorDivide(25 * n + 11, 33, nArray);
        return nArray[0] < 8;
    }

    @Override
    @Deprecated
    protected int handleGetMonthLength(int n, int n2) {
        if (n2 < 0 || n2 > 11) {
            int[] nArray = new int[1];
            n += PersianCalendar.floorDivide(n2, 12, nArray);
            n2 = nArray[0];
        }
        return MONTH_COUNT[n2][PersianCalendar.isLeapYear(n) ? 1 : 0];
    }

    @Override
    @Deprecated
    protected int handleGetYearLength(int n) {
        return PersianCalendar.isLeapYear(n) ? 366 : 365;
    }

    @Override
    @Deprecated
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        if (n2 < 0 || n2 > 11) {
            int[] nArray = new int[1];
            n += PersianCalendar.floorDivide(n2, 12, nArray);
            n2 = nArray[0];
        }
        int n3 = 1948319 + 365 * (n - 1) + PersianCalendar.floorDivide(8 * n + 21, 33);
        if (n2 != 0) {
            n3 += MONTH_COUNT[n2][2];
        }
        return n3;
    }

    @Override
    @Deprecated
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : this.internalGet(1, 1);
        return n;
    }

    @Override
    @Deprecated
    protected void handleComputeFields(int n) {
        long l = n - 1948320;
        int n2 = 1 + (int)PersianCalendar.floorDivide(33L * l + 3L, 12053L);
        long l2 = 365L * ((long)n2 - 1L) + PersianCalendar.floorDivide(8L * (long)n2 + 21L, 33L);
        int n3 = (int)(l - l2);
        int n4 = n3 < 216 ? n3 / 31 : (n3 - 6) / 30;
        int n5 = n3 - MONTH_COUNT[n4][2] + 1;
        ++n3;
        this.internalSet(0, 0);
        this.internalSet(1, n2);
        this.internalSet(19, n2);
        this.internalSet(2, n4);
        this.internalSet(5, n5);
        this.internalSet(6, n3);
    }

    @Override
    @Deprecated
    public String getType() {
        return "persian";
    }
}

