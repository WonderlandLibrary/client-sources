/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class GregorianCalendar
extends Calendar {
    private static final long serialVersionUID = 9199388694351062137L;
    public static final int BC = 0;
    public static final int AD = 1;
    private static final int EPOCH_YEAR = 1970;
    private static final int[][] MONTH_COUNT = new int[][]{{31, 31, 0, 0}, {28, 29, 31, 31}, {31, 31, 59, 60}, {30, 30, 90, 91}, {31, 31, 120, 121}, {30, 30, 151, 152}, {31, 31, 181, 182}, {31, 31, 212, 213}, {30, 30, 243, 244}, {31, 31, 273, 274}, {30, 30, 304, 305}, {31, 31, 334, 335}};
    private static final int[][] LIMITS = new int[][]{{0, 0, 1, 1}, {1, 1, 5828963, 5838270}, {0, 0, 11, 11}, {1, 1, 52, 53}, new int[0], {1, 1, 28, 31}, {1, 1, 365, 366}, new int[0], {-1, -1, 4, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], {-5838270, -5838270, 5828964, 5838271}, new int[0], {-5838269, -5838269, 5828963, 5838270}, new int[0], new int[0], new int[0]};
    private long gregorianCutover = -12219292800000L;
    private transient int cutoverJulianDay = 2299161;
    private transient int gregorianCutoverYear = 1582;
    protected transient boolean isGregorian;
    protected transient boolean invertGregorian;

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    public GregorianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public GregorianCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public GregorianCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    public GregorianCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    public GregorianCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public GregorianCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public GregorianCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(0, 1);
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public GregorianCalendar(int n, int n2, int n3, int n4, int n5) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(0, 1);
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
    }

    public GregorianCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(0, 1);
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    public void setGregorianChange(Date date) {
        this.gregorianCutover = date.getTime();
        if (this.gregorianCutover <= -184303902528000000L) {
            this.cutoverJulianDay = Integer.MIN_VALUE;
            this.gregorianCutoverYear = Integer.MIN_VALUE;
        } else if (this.gregorianCutover >= 183882168921600000L) {
            this.cutoverJulianDay = Integer.MAX_VALUE;
            this.gregorianCutoverYear = Integer.MAX_VALUE;
        } else {
            this.cutoverJulianDay = (int)GregorianCalendar.floorDivide(this.gregorianCutover, 86400000L);
            GregorianCalendar gregorianCalendar = new GregorianCalendar(this.getTimeZone());
            gregorianCalendar.setTime(date);
            this.gregorianCutoverYear = gregorianCalendar.get(19);
        }
    }

    public final Date getGregorianChange() {
        return new Date(this.gregorianCutover);
    }

    public boolean isLeapYear(int n) {
        return n >= this.gregorianCutoverYear ? n % 4 == 0 && (n % 100 != 0 || n % 400 == 0) : n % 4 == 0;
    }

    @Override
    public boolean isEquivalentTo(Calendar calendar) {
        return super.isEquivalentTo(calendar) && this.gregorianCutover == ((GregorianCalendar)calendar).gregorianCutover;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)this.gregorianCutover;
    }

    @Override
    public void roll(int n, int n2) {
        switch (n) {
            case 3: {
                int n3 = this.get(3);
                int n4 = this.get(17);
                int n5 = this.internalGet(6);
                if (this.internalGet(2) == 0) {
                    if (n3 >= 52) {
                        n5 += this.handleGetYearLength(n4);
                    }
                } else if (n3 == 1) {
                    n5 -= this.handleGetYearLength(n4 - 1);
                }
                if ((n3 += n2) < 1 || n3 > 52) {
                    int n6 = this.handleGetYearLength(n4);
                    int n7 = (n6 - n5 + this.internalGet(7) - this.getFirstDayOfWeek()) % 7;
                    if (n7 < 0) {
                        n7 += 7;
                    }
                    if (6 - n7 >= this.getMinimalDaysInFirstWeek()) {
                        n6 -= 7;
                    }
                    int n8 = this.weekNumber(n6, n7 + 1);
                    n3 = (n3 + n8 - 1) % n8 + 1;
                }
                this.set(3, n3);
                this.set(1, n4);
                return;
            }
        }
        super.roll(n, n2);
    }

    @Override
    public int getActualMinimum(int n) {
        return this.getMinimum(n);
    }

    @Override
    public int getActualMaximum(int n) {
        switch (n) {
            case 1: {
                Calendar calendar = (Calendar)this.clone();
                calendar.setLenient(false);
                int n2 = calendar.get(0);
                Date date = calendar.getTime();
                int n3 = LIMITS[1][1];
                int n4 = LIMITS[1][2] + 1;
                while (n3 + 1 < n4) {
                    int n5 = (n3 + n4) / 2;
                    calendar.set(1, n5);
                    if (calendar.get(1) == n5 && calendar.get(0) == n2) {
                        n3 = n5;
                        continue;
                    }
                    n4 = n5;
                    calendar.setTime(date);
                }
                return n3;
            }
        }
        return super.getActualMaximum(n);
    }

    boolean inDaylightTime() {
        if (!this.getTimeZone().useDaylightTime()) {
            return true;
        }
        this.complete();
        return this.internalGet(16) != 0;
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        if (n2 < 0 || n2 > 11) {
            int[] nArray = new int[1];
            n += GregorianCalendar.floorDivide(n2, 12, nArray);
            n2 = nArray[0];
        }
        return MONTH_COUNT[n2][this.isLeapYear(n) ? 1 : 0];
    }

    @Override
    protected int handleGetYearLength(int n) {
        return this.isLeapYear(n) ? 366 : 365;
    }

    @Override
    protected void handleComputeFields(int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        if (n >= this.cutoverJulianDay) {
            n5 = this.getGregorianMonth();
            n4 = this.getGregorianDayOfMonth();
            n3 = this.getGregorianDayOfYear();
            n2 = this.getGregorianYear();
        } else {
            int n6;
            long l = n - 1721424;
            n2 = (int)GregorianCalendar.floorDivide(4L * l + 1464L, 1461L);
            long l2 = 365L * ((long)n2 - 1L) + GregorianCalendar.floorDivide((long)n2 - 1L, 4L);
            n3 = (int)(l - l2);
            boolean bl = (n2 & 3) == 0;
            int n7 = 0;
            int n8 = n6 = bl ? 60 : 59;
            if (n3 >= n6) {
                n7 = bl ? 1 : 2;
            }
            n5 = (12 * (n3 + n7) + 6) / 367;
            n4 = n3 - MONTH_COUNT[n5][bl ? 3 : 2] + 1;
            ++n3;
        }
        this.internalSet(2, n5);
        this.internalSet(5, n4);
        this.internalSet(6, n3);
        this.internalSet(19, n2);
        int n9 = 1;
        if (n2 < 1) {
            n9 = 0;
            n2 = 1 - n2;
        }
        this.internalSet(0, n9);
        this.internalSet(1, n2);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n;
        int n2 = this.newerField(19, 1) == 19 ? this.internalGet(19, 1970) : ((n = this.internalGet(0, 1)) == 0 ? 1 - this.internalGet(1, 1) : this.internalGet(1, 1970));
        return n2;
    }

    @Override
    protected int handleComputeJulianDay(int n) {
        this.invertGregorian = false;
        int n2 = super.handleComputeJulianDay(n);
        if (this.isGregorian != n2 >= this.cutoverJulianDay) {
            this.invertGregorian = true;
            n2 = super.handleComputeJulianDay(n);
        }
        return n2;
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        if (n2 < 0 || n2 > 11) {
            int[] nArray = new int[1];
            n += GregorianCalendar.floorDivide(n2, 12, nArray);
            n2 = nArray[0];
        }
        boolean bl2 = n % 4 == 0;
        int n3 = n - 1;
        int n4 = 365 * n3 + GregorianCalendar.floorDivide(n3, 4) + 1721423;
        boolean bl3 = this.isGregorian = n >= this.gregorianCutoverYear;
        if (this.invertGregorian) {
            boolean bl4 = this.isGregorian = !this.isGregorian;
        }
        if (this.isGregorian) {
            bl2 = bl2 && (n % 100 != 0 || n % 400 == 0);
            n4 += GregorianCalendar.floorDivide(n3, 400) - GregorianCalendar.floorDivide(n3, 100) + 2;
        }
        if (n2 != 0) {
            n4 += MONTH_COUNT[n2][bl2 ? 3 : 2];
        }
        return n4;
    }

    @Override
    public String getType() {
        return "gregorian";
    }
}

