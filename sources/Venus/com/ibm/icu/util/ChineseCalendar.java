/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.CalendarAstronomer;
import com.ibm.icu.impl.CalendarCache;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.SimpleTimeZone;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Locale;

public class ChineseCalendar
extends Calendar {
    private static final long serialVersionUID = 7312110751940929420L;
    private int epochYear;
    private TimeZone zoneAstro;
    private transient CalendarAstronomer astro = new CalendarAstronomer();
    private transient CalendarCache winterSolsticeCache = new CalendarCache();
    private transient CalendarCache newYearCache = new CalendarCache();
    private transient boolean isLeapYear;
    private static final int[][] LIMITS = new int[][]{{1, 1, 83333, 83333}, {1, 1, 60, 60}, {0, 0, 11, 11}, {1, 1, 50, 55}, new int[0], {1, 1, 29, 30}, {1, 1, 353, 385}, new int[0], {-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], new int[0], {0, 0, 1, 1}};
    static final int[][][] CHINESE_DATE_PRECEDENCE = new int[][][]{new int[][]{{5}, {3, 7}, {4, 7}, {8, 7}, {3, 18}, {4, 18}, {8, 18}, {6}, {37, 22}}, new int[][]{{3}, {4}, {8}, {40, 7}, {40, 18}}};
    private static final int CHINESE_EPOCH_YEAR = -2636;
    private static final TimeZone CHINA_ZONE = new SimpleTimeZone(28800000, "CHINA_ZONE").freeze();
    private static final int SYNODIC_GAP = 25;

    public ChineseCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
    }

    public ChineseCalendar(Date date) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
        this.setTime(date);
    }

    public ChineseCalendar(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, 0, 0, 0);
    }

    public ChineseCalendar(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
        this.set(14, 0);
        this.set(1, n);
        this.set(2, n2);
        this.set(22, n3);
        this.set(5, n4);
        this.set(11, n5);
        this.set(12, n6);
        this.set(13, n7);
    }

    public ChineseCalendar(int n, int n2, int n3, int n4, int n5) {
        this(n, n2, n3, n4, n5, 0, 0, 0);
    }

    public ChineseCalendar(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
        this.set(14, 0);
        this.set(0, n);
        this.set(1, n2);
        this.set(2, n3);
        this.set(22, n4);
        this.set(5, n5);
        this.set(11, n6);
        this.set(12, n7);
        this.set(13, n8);
    }

    public ChineseCalendar(Locale locale) {
        this(TimeZone.getDefault(), ULocale.forLocale(locale), -2636, CHINA_ZONE);
    }

    public ChineseCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
    }

    public ChineseCalendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale), -2636, CHINA_ZONE);
    }

    public ChineseCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale, -2636, CHINA_ZONE);
    }

    public ChineseCalendar(TimeZone timeZone, ULocale uLocale) {
        this(timeZone, uLocale, -2636, CHINA_ZONE);
    }

    @Deprecated
    protected ChineseCalendar(TimeZone timeZone, ULocale uLocale, int n, TimeZone timeZone2) {
        super(timeZone, uLocale);
        this.epochYear = n;
        this.zoneAstro = timeZone2;
        this.setTimeInMillis(System.currentTimeMillis());
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    @Override
    protected int handleGetExtendedYear() {
        int n;
        if (this.newestStamp(0, 1, 0) <= this.getStamp(19)) {
            n = this.internalGet(19, 1);
        } else {
            int n2 = this.internalGet(0, 1) - 1;
            n = n2 * 60 + this.internalGet(1, 1) - (this.epochYear - -2636);
        }
        return n;
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        int n3 = this.handleComputeMonthStart(n, n2, false) - 2440588 + 1;
        int n4 = this.newMoonNear(n3 + 25, true);
        return n4 - n3;
    }

    @Override
    protected DateFormat handleGetDateFormat(String string, String string2, ULocale uLocale) {
        return super.handleGetDateFormat(string, string2, uLocale);
    }

    @Override
    protected int[][][] getFieldResolutionTable() {
        return CHINESE_DATE_PRECEDENCE;
    }

    private void offsetMonth(int n, int n2, int n3) {
        n += (int)(29.530588853 * ((double)n3 - 0.5));
        n = this.newMoonNear(n, true);
        int n4 = n + 2440588 - 1 + n2;
        if (n2 > 29) {
            this.set(20, n4 - 1);
            this.complete();
            if (this.getActualMaximum(5) >= n2) {
                this.set(20, n4);
            }
        } else {
            this.set(20, n4);
        }
    }

    @Override
    public void add(int n, int n2) {
        switch (n) {
            case 2: {
                if (n2 == 0) break;
                int n3 = this.get(5);
                int n4 = this.get(20) - 2440588;
                int n5 = n4 - n3 + 1;
                this.offsetMonth(n5, n3, n2);
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
                int n3;
                int n4;
                if (n2 == 0) break;
                int n5 = this.get(5);
                int n6 = this.get(20) - 2440588;
                int n7 = n6 - n5 + 1;
                int n8 = this.get(2);
                if (this.isLeapYear) {
                    if (this.get(22) == 1) {
                        ++n8;
                    } else {
                        n4 = n7 - (int)(29.530588853 * ((double)n8 - 0.5));
                        if (this.isLeapMonthBetween(n4 = this.newMoonNear(n4, true), n7)) {
                            ++n8;
                        }
                    }
                }
                if ((n3 = (n8 + n2) % (n4 = this.isLeapYear ? 13 : 12)) < 0) {
                    n3 += n4;
                }
                if (n3 == n8) break;
                this.offsetMonth(n7, n5, n3 - n8);
                break;
            }
            default: {
                super.roll(n, n2);
            }
        }
    }

    private final long daysToMillis(int n) {
        long l = (long)n * 86400000L;
        return l - (long)this.zoneAstro.getOffset(l);
    }

    private final int millisToDays(long l) {
        return (int)ChineseCalendar.floorDivide(l + (long)this.zoneAstro.getOffset(l), 86400000L);
    }

    private int winterSolstice(int n) {
        long l = this.winterSolsticeCache.get(n);
        if (l == CalendarCache.EMPTY) {
            long l2 = this.daysToMillis(this.computeGregorianMonthStart(n, 11) + 1 - 2440588);
            this.astro.setTime(l2);
            long l3 = this.astro.getSunTime(CalendarAstronomer.WINTER_SOLSTICE, false);
            l = this.millisToDays(l3);
            this.winterSolsticeCache.put(n, l);
        }
        return (int)l;
    }

    private int newMoonNear(int n, boolean bl) {
        this.astro.setTime(this.daysToMillis(n));
        long l = this.astro.getMoonTime(CalendarAstronomer.NEW_MOON, bl);
        return this.millisToDays(l);
    }

    private int synodicMonthsBetween(int n, int n2) {
        return (int)Math.round((double)(n2 - n) / 29.530588853);
    }

    private int majorSolarTerm(int n) {
        this.astro.setTime(this.daysToMillis(n));
        int n2 = ((int)Math.floor(6.0 * this.astro.getSunLongitude() / Math.PI) + 2) % 12;
        if (n2 < 1) {
            n2 += 12;
        }
        return n2;
    }

    private boolean hasNoMajorSolarTerm(int n) {
        int n2;
        int n3;
        int n4 = this.majorSolarTerm(n);
        return n4 == (n3 = this.majorSolarTerm(n2 = this.newMoonNear(n + 25, true)));
    }

    private boolean isLeapMonthBetween(int n, int n2) {
        if (this.synodicMonthsBetween(n, n2) >= 50) {
            throw new IllegalArgumentException("isLeapMonthBetween(" + n + ", " + n2 + "): Invalid parameters");
        }
        return n2 >= n && (this.isLeapMonthBetween(n, this.newMoonNear(n2 - 25, false)) || this.hasNoMajorSolarTerm(n2));
    }

    @Override
    protected void handleComputeFields(int n) {
        this.computeChineseFields(n - 2440588, this.getGregorianYear(), this.getGregorianMonth(), true);
    }

    private void computeChineseFields(int n, int n2, int n3, boolean bl) {
        int n4;
        int n5 = this.winterSolstice(n2);
        if (n < n5) {
            n4 = this.winterSolstice(n2 - 1);
        } else {
            n4 = n5;
            n5 = this.winterSolstice(n2 + 1);
        }
        int n6 = this.newMoonNear(n4 + 1, true);
        int n7 = this.newMoonNear(n5 + 1, false);
        int n8 = this.newMoonNear(n + 1, false);
        this.isLeapYear = this.synodicMonthsBetween(n6, n7) == 12;
        int n9 = this.synodicMonthsBetween(n6, n8);
        if (this.isLeapYear && this.isLeapMonthBetween(n6, n8)) {
            --n9;
        }
        if (n9 < 1) {
            n9 += 12;
        }
        boolean bl2 = this.isLeapYear && this.hasNoMajorSolarTerm(n8) && !this.isLeapMonthBetween(n6, this.newMoonNear(n8 - 25, false));
        this.internalSet(2, n9 - 1);
        this.internalSet(22, bl2 ? 1 : 0);
        if (bl) {
            int n10 = n2 - this.epochYear;
            int n11 = n2 - -2636;
            if (n9 < 11 || n3 >= 6) {
                ++n10;
                ++n11;
            }
            int n12 = n - n8 + 1;
            this.internalSet(19, n10);
            int[] nArray = new int[1];
            int n13 = ChineseCalendar.floorDivide(n11 - 1, 60, nArray);
            this.internalSet(0, n13 + 1);
            this.internalSet(1, nArray[0] + 1);
            this.internalSet(5, n12);
            int n14 = this.newYear(n2);
            if (n < n14) {
                n14 = this.newYear(n2 - 1);
            }
            this.internalSet(6, n - n14 + 1);
        }
    }

    private int newYear(int n) {
        long l = this.newYearCache.get(n);
        if (l == CalendarCache.EMPTY) {
            int n2 = this.winterSolstice(n - 1);
            int n3 = this.winterSolstice(n);
            int n4 = this.newMoonNear(n2 + 1, true);
            int n5 = this.newMoonNear(n4 + 25, true);
            int n6 = this.newMoonNear(n3 + 1, false);
            l = this.synodicMonthsBetween(n4, n6) == 12 && (this.hasNoMajorSolarTerm(n4) || this.hasNoMajorSolarTerm(n5)) ? (long)this.newMoonNear(n5 + 25, true) : (long)n5;
            this.newYearCache.put(n, l);
        }
        return (int)l;
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        if (n2 < 0 || n2 > 11) {
            int[] nArray = new int[1];
            n += ChineseCalendar.floorDivide(n2, 12, nArray);
            n2 = nArray[0];
        }
        int n3 = n + this.epochYear - 1;
        int n4 = this.newYear(n3);
        int n5 = this.newMoonNear(n4 + n2 * 29, true);
        int n6 = n5 + 2440588;
        int n7 = this.internalGet(2);
        int n8 = this.internalGet(22);
        int n9 = bl ? n8 : 0;
        this.computeGregorianFields(n6);
        this.computeChineseFields(n5, this.getGregorianYear(), this.getGregorianMonth(), false);
        if (n2 != this.internalGet(2) || n9 != this.internalGet(22)) {
            n5 = this.newMoonNear(n5 + 25, true);
            n6 = n5 + 2440588;
        }
        this.internalSet(2, n7);
        this.internalSet(22, n8);
        return n6 - 1;
    }

    @Override
    public String getType() {
        return "chinese";
    }

    @Override
    @Deprecated
    public boolean haveDefaultCentury() {
        return true;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.epochYear = -2636;
        this.zoneAstro = CHINA_ZONE;
        objectInputStream.defaultReadObject();
        this.astro = new CalendarAstronomer();
        this.winterSolsticeCache = new CalendarCache();
        this.newYearCache = new CalendarCache();
    }
}

