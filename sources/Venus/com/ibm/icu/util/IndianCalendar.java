/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class IndianCalendar
extends Calendar {
    private static final long serialVersionUID = 3617859668165014834L;
    public static final int CHAITRA = 0;
    public static final int VAISAKHA = 1;
    public static final int JYAISTHA = 2;
    public static final int ASADHA = 3;
    public static final int SRAVANA = 4;
    public static final int BHADRA = 5;
    public static final int ASVINA = 6;
    public static final int KARTIKA = 7;
    public static final int AGRAHAYANA = 8;
    public static final int PAUSA = 9;
    public static final int MAGHA = 10;
    public static final int PHALGUNA = 11;
    public static final int IE = 0;
    private static final int INDIAN_ERA_START = 78;
    private static final int INDIAN_YEAR_START = 80;
    private static final int[][] LIMITS = new int[][]{{0, 0, 0, 0}, {-5000000, -5000000, 5000000, 5000000}, {0, 0, 11, 11}, {1, 1, 52, 53}, new int[0], {1, 1, 30, 31}, {1, 1, 365, 366}, new int[0], {-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], new int[0]};

    public IndianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IndianCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IndianCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    public IndianCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    public IndianCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public IndianCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public IndianCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    public IndianCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public IndianCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : this.internalGet(1, 1);
        return n;
    }

    @Override
    protected int handleGetYearLength(int n) {
        return super.handleGetYearLength(n);
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        if (n2 < 0 || n2 > 11) {
            int[] nArray = new int[1];
            n += IndianCalendar.floorDivide(n2, 12, nArray);
            n2 = nArray[0];
        }
        if (IndianCalendar.isGregorianLeap(n + 78) && n2 == 0) {
            return 0;
        }
        if (n2 >= 1 && n2 <= 5) {
            return 0;
        }
        return 1;
    }

    @Override
    protected void handleComputeFields(int n) {
        int n2;
        int n3;
        int n4;
        int[] nArray = IndianCalendar.jdToGregorian(n);
        int n5 = nArray[0] - 78;
        double d = IndianCalendar.gregorianToJD(nArray[0], 1, 1);
        int n6 = (int)((double)n - d);
        if (n6 < 80) {
            --n5;
            n4 = IndianCalendar.isGregorianLeap(nArray[0] - 1) ? 31 : 30;
            n6 += n4 + 155 + 90 + 10;
        } else {
            n4 = IndianCalendar.isGregorianLeap(nArray[0]) ? 31 : 30;
            n6 -= 80;
        }
        if (n6 < n4) {
            n3 = 0;
            n2 = n6 + 1;
        } else {
            int n7 = n6 - n4;
            if (n7 < 155) {
                n3 = n7 / 31 + 1;
                n2 = n7 % 31 + 1;
            } else {
                n3 = (n7 -= 155) / 30 + 6;
                n2 = n7 % 30 + 1;
            }
        }
        this.internalSet(0, 0);
        this.internalSet(19, n5);
        this.internalSet(1, n5);
        this.internalSet(2, n3);
        this.internalSet(5, n2);
        this.internalSet(6, n6 + 1);
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        if (n2 < 0 || n2 > 11) {
            n += n2 / 12;
            n2 %= 12;
        }
        int n3 = n2 + 1;
        double d = IndianCalendar.IndianToJD(n, n3, 1);
        return (int)d;
    }

    private static double IndianToJD(int n, int n2, int n3) {
        double d;
        double d2;
        int n4;
        int n5 = n + 78;
        if (IndianCalendar.isGregorianLeap(n5)) {
            n4 = 31;
            d2 = IndianCalendar.gregorianToJD(n5, 3, 21);
        } else {
            n4 = 30;
            d2 = IndianCalendar.gregorianToJD(n5, 3, 22);
        }
        if (n2 == 1) {
            d = d2 + (double)(n3 - 1);
        } else {
            d = d2 + (double)n4;
            int n6 = n2 - 2;
            n6 = Math.min(n6, 5);
            d += (double)(n6 * 31);
            if (n2 >= 8) {
                n6 = n2 - 7;
                d += (double)(n6 * 30);
            }
            d += (double)(n3 - 1);
        }
        return d;
    }

    private static double gregorianToJD(int n, int n2, int n3) {
        double d = 1721425.5;
        int n4 = n - 1;
        int n5 = 365 * n4 + n4 / 4 - n4 / 100 + n4 / 400 + (367 * n2 - 362) / 12 + (n2 <= 2 ? 0 : (IndianCalendar.isGregorianLeap(n) ? -1 : -2)) + n3;
        return (double)(n5 - 1) + d;
    }

    private static int[] jdToGregorian(double d) {
        double d2 = 1721425.5;
        double d3 = Math.floor(d - 0.5) + 0.5;
        double d4 = d3 - d2;
        double d5 = Math.floor(d4 / 146097.0);
        double d6 = d4 % 146097.0;
        double d7 = Math.floor(d6 / 36524.0);
        double d8 = d6 % 36524.0;
        double d9 = Math.floor(d8 / 1461.0);
        double d10 = d8 % 1461.0;
        double d11 = Math.floor(d10 / 365.0);
        int n = (int)(d5 * 400.0 + d7 * 100.0 + d9 * 4.0 + d11);
        if (d7 != 4.0 && d11 != 4.0) {
            ++n;
        }
        double d12 = d3 - IndianCalendar.gregorianToJD(n, 1, 1);
        double d13 = d3 < IndianCalendar.gregorianToJD(n, 3, 1) ? 0 : (IndianCalendar.isGregorianLeap(n) ? 1 : 2);
        int n2 = (int)Math.floor(((d12 + d13) * 12.0 + 373.0) / 367.0);
        int n3 = (int)(d3 - IndianCalendar.gregorianToJD(n, n2, 1)) + 1;
        int[] nArray = new int[]{n, n2, n3};
        return nArray;
    }

    private static boolean isGregorianLeap(int n) {
        return n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
    }

    @Override
    public String getType() {
        return "indian";
    }
}

