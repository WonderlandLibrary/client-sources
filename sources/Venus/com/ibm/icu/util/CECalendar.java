/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

abstract class CECalendar
extends Calendar {
    private static final long serialVersionUID = -999547623066414271L;
    private static final int[][] LIMITS = new int[][]{{0, 0, 1, 1}, {1, 1, 5000000, 5000000}, {0, 0, 12, 12}, {1, 1, 52, 53}, new int[0], {1, 1, 5, 30}, {1, 1, 365, 366}, new int[0], {-1, -1, 1, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], new int[0]};

    protected CECalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    protected CECalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    protected CECalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    protected CECalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    protected CECalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    protected CECalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    protected CECalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(n, n2, n3);
    }

    protected CECalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    protected CECalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(n, n2, n3, n4, n5, n6);
    }

    protected abstract int getJDEpochOffset();

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        return CECalendar.ceToJD(n, n2, 0, this.getJDEpochOffset());
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        if ((n2 + 1) % 13 != 0) {
            return 1;
        }
        return n % 4 / 3 + 5;
    }

    public static int ceToJD(long l, int n, int n2, int n3) {
        if (n >= 0) {
            l += (long)(n / 13);
            n %= 13;
        } else {
            l += (long)(++n / 13 - 1);
            n = n % 13 + 12;
        }
        return (int)((long)n3 + 365L * l + CECalendar.floorDivide(l, 4L) + (long)(30 * n) + (long)n2 - 1L);
    }

    public static void jdToCE(int n, int n2, int[] nArray) {
        int[] nArray2 = new int[1];
        int n3 = CECalendar.floorDivide(n - n2, 1461, nArray2);
        nArray[0] = 4 * n3 + (nArray2[0] / 365 - nArray2[0] / 1460);
        int n4 = nArray2[0] == 1460 ? 365 : nArray2[0] % 365;
        nArray[1] = n4 / 30;
        nArray[2] = n4 % 30 + 1;
    }
}

