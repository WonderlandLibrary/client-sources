/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.CalendarAstronomer;
import com.ibm.icu.impl.CalendarCache;
import com.ibm.icu.impl.CalendarUtil;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Locale;

public class IslamicCalendar
extends Calendar {
    private static final long serialVersionUID = -6253365474073869325L;
    public static final int MUHARRAM = 0;
    public static final int SAFAR = 1;
    public static final int RABI_1 = 2;
    public static final int RABI_2 = 3;
    public static final int JUMADA_1 = 4;
    public static final int JUMADA_2 = 5;
    public static final int RAJAB = 6;
    public static final int SHABAN = 7;
    public static final int RAMADAN = 8;
    public static final int SHAWWAL = 9;
    public static final int DHU_AL_QIDAH = 10;
    public static final int DHU_AL_HIJJAH = 11;
    private static final long HIJRA_MILLIS = -42521587200000L;
    private static final long CIVIL_EPOC = 1948440L;
    private static final long ASTRONOMICAL_EPOC = 1948439L;
    private static final int[][] LIMITS = new int[][]{{0, 0, 0, 0}, {1, 1, 5000000, 5000000}, {0, 0, 11, 11}, {1, 1, 50, 51}, new int[0], {1, 1, 29, 30}, {1, 1, 354, 355}, new int[0], {-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], {1, 1, 5000000, 5000000}, new int[0], {1, 1, 5000000, 5000000}, new int[0], new int[0]};
    private static final int[] UMALQURA_MONTHLENGTH = new int[]{2730, 3412, 3785, 1748, 1770, 876, 2733, 1365, 1705, 1938, 2985, 1492, 2778, 1372, 3373, 1685, 1866, 2900, 2922, 1453, 1198, 2639, 1303, 1675, 1701, 2773, 726, 2395, 1181, 2637, 3366, 3477, 1452, 2486, 698, 2651, 1323, 2709, 1738, 2793, 756, 2422, 694, 2390, 2762, 2980, 3026, 1497, 732, 2413, 1357, 2725, 2898, 2981, 1460, 2486, 1367, 663, 1355, 1699, 1874, 2917, 1386, 2731, 1323, 3221, 3402, 3493, 1482, 2774, 2391, 1195, 2379, 2725, 2898, 2922, 1397, 630, 2231, 1115, 1365, 1449, 1460, 2522, 1245, 622, 2358, 2730, 3412, 3506, 1493, 730, 2395, 1195, 2645, 2889, 2916, 2929, 1460, 2741, 2645, 3365, 3730, 3785, 1748, 2793, 2411, 1195, 2707, 3401, 3492, 3506, 2745, 1210, 2651, 1323, 2709, 2858, 2901, 1372, 1213, 573, 2333, 2709, 2890, 2906, 1389, 694, 2363, 1179, 1621, 1705, 1876, 2922, 1388, 2733, 1365, 2857, 2962, 2985, 1492, 2778, 1370, 2731, 1429, 1865, 1892, 2986, 1461, 694, 2646, 3661, 2853, 2898, 2922, 1453, 686, 2351, 1175, 1611, 1701, 1708, 2774, 1373, 1181, 2637, 3350, 3477, 1450, 1461, 730, 2395, 1197, 1429, 1738, 1764, 2794, 1269, 694, 2390, 2730, 2900, 3026, 1497, 746, 2413, 1197, 2709, 2890, 2981, 1458, 2485, 1238, 2711, 1351, 1683, 1865, 2901, 1386, 2667, 1323, 2699, 3398, 3491, 1482, 2774, 1243, 619, 2379, 2725, 2898, 2921, 1397, 374, 2231, 603, 1323, 1381, 1460, 2522, 1261, 365, 2230, 2726, 3410, 3497, 1492, 2778, 2395, 1195, 1619, 1833, 1890, 2985, 1458, 2741, 1365, 2853, 3474, 3785, 1746, 2793, 1387, 1195, 2645, 3369, 3412, 3498, 2485, 1210, 2619, 1179, 2637, 2730, 2773, 730, 2397, 1118, 2606, 3226, 3413, 1714, 1721, 1210, 2653, 1325, 2709, 2898, 2984, 2996, 1465, 730, 2394, 2890, 3492, 3793, 1768, 2922, 1389, 1333, 1685, 3402, 3496, 3540, 1754, 1371, 669, 1579, 2837, 2890, 2965, 1450, 2734, 2350, 3215, 1319, 1685, 1706, 2774, 1373, 669};
    private static final int UMALQURA_YEAR_START = 1300;
    private static final int UMALQURA_YEAR_END = 1600;
    private static final byte[] UMALQURA_YEAR_START_ESTIMATE_FIX = new byte[]{0, 0, -1, 0, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, -1, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 1, 1, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, -1, 0, 1, 0, 0, 0, -1, 0, 1, 0, 1, 0, 0, 0, -1, 0, 0, 0, 0, -1, -1, 0, -1, 0, 1, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, -1, 0, 0, 0, 1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, -1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, -1, 0, 1, 0, 0, -1, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 1, 1, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, 0, 0, -1, 0, -1, 0, 1, 0, 0, 0, -1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, 0, -1, -1, 0, -1, 0, 1, 0, 0, -1, -1, 0, 0, 1, 1, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    private static CalendarAstronomer astro = new CalendarAstronomer();
    private static CalendarCache cache = new CalendarCache();
    private boolean civil = true;
    private CalculationType cType = CalculationType.ISLAMIC_CIVIL;

    public IslamicCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IslamicCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IslamicCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    public IslamicCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    public IslamicCalendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale));
    }

    public IslamicCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setCalcTypeForLocale(uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public IslamicCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    public IslamicCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public IslamicCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    public void setCivil(boolean bl) {
        this.civil = bl;
        if (bl && this.cType != CalculationType.ISLAMIC_CIVIL) {
            long l = this.getTimeInMillis();
            this.cType = CalculationType.ISLAMIC_CIVIL;
            this.clear();
            this.setTimeInMillis(l);
        } else if (!bl && this.cType != CalculationType.ISLAMIC) {
            long l = this.getTimeInMillis();
            this.cType = CalculationType.ISLAMIC;
            this.clear();
            this.setTimeInMillis(l);
        }
    }

    public boolean isCivil() {
        return this.cType != CalculationType.ISLAMIC_CIVIL;
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    private static final boolean civilLeapYear(int n) {
        return (14 + 11 * n) % 30 < 11;
    }

    private long yearStart(int n) {
        long l = 0L;
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA || this.cType == CalculationType.ISLAMIC_UMALQURA && (n < 1300 || n > 1600)) {
            l = (long)((n - 1) * 354) + (long)Math.floor((double)(3 + 11 * n) / 30.0);
        } else if (this.cType == CalculationType.ISLAMIC) {
            l = IslamicCalendar.trueMonthStart(12 * (n - 1));
        } else if (this.cType == CalculationType.ISLAMIC_UMALQURA) {
            int n2 = (int)(354.3672 * (double)(n -= 1300) + 460322.05 + 0.5);
            l = n2 + UMALQURA_YEAR_START_ESTIMATE_FIX[n];
        }
        return l;
    }

    private long monthStart(int n, int n2) {
        int n3 = n + n2 / 12;
        int n4 = n2 % 12;
        long l = 0L;
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA || this.cType == CalculationType.ISLAMIC_UMALQURA && n < 1300) {
            l = (long)Math.ceil(29.5 * (double)n4) + (long)((n3 - 1) * 354) + (long)Math.floor((double)(3 + 11 * n3) / 30.0);
        } else if (this.cType == CalculationType.ISLAMIC) {
            l = IslamicCalendar.trueMonthStart(12 * (n3 - 1) + n4);
        } else if (this.cType == CalculationType.ISLAMIC_UMALQURA) {
            l = this.yearStart(n);
            for (int i = 0; i < n2; ++i) {
                l += (long)this.handleGetMonthLength(n, i);
            }
        }
        return l;
    }

    private static final long trueMonthStart(long l) {
        long l2 = cache.get(l);
        if (l2 == CalendarCache.EMPTY) {
            long l3 = -42521587200000L + (long)Math.floor((double)l * 29.530588853) * 86400000L;
            double d = IslamicCalendar.moonAge(l3);
            if (IslamicCalendar.moonAge(l3) >= 0.0) {
                while ((d = IslamicCalendar.moonAge(l3 -= 86400000L)) >= 0.0) {
                }
            } else {
                while ((d = IslamicCalendar.moonAge(l3 += 86400000L)) < 0.0) {
                }
            }
            l2 = (l3 - -42521587200000L) / 86400000L + 1L;
            cache.put(l, l2);
        }
        return l2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final double moonAge(long l) {
        double d = 0.0;
        CalendarAstronomer calendarAstronomer = astro;
        synchronized (calendarAstronomer) {
            astro.setTime(l);
            d = astro.getMoonAge();
        }
        d = d * 180.0 / Math.PI;
        if (d > 180.0) {
            d -= 360.0;
        }
        return d;
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        int n3;
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA || this.cType == CalculationType.ISLAMIC_UMALQURA && (n < 1300 || n > 1600)) {
            n3 = 29 + (n2 + 1) % 2;
            if (n2 == 11 && IslamicCalendar.civilLeapYear(n)) {
                ++n3;
            }
        } else if (this.cType == CalculationType.ISLAMIC) {
            n2 = 12 * (n - 1) + n2;
            n3 = (int)(IslamicCalendar.trueMonthStart(n2 + 1) - IslamicCalendar.trueMonthStart(n2));
        } else {
            int n4 = n - 1300;
            int n5 = 1 << 11 - n2;
            n3 = (UMALQURA_MONTHLENGTH[n4] & n5) == 0 ? 29 : 30;
        }
        return n3;
    }

    @Override
    protected int handleGetYearLength(int n) {
        int n2 = 0;
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA || this.cType == CalculationType.ISLAMIC_UMALQURA && (n < 1300 || n > 1600)) {
            n2 = 354 + (IslamicCalendar.civilLeapYear(n) ? 1 : 0);
        } else if (this.cType == CalculationType.ISLAMIC) {
            int n3 = 12 * (n - 1);
            n2 = (int)(IslamicCalendar.trueMonthStart(n3 + 12) - IslamicCalendar.trueMonthStart(n3));
        } else if (this.cType == CalculationType.ISLAMIC_UMALQURA) {
            for (int i = 0; i < 12; ++i) {
                n2 += this.handleGetMonthLength(n, i);
            }
        }
        return n2;
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        return (int)(this.monthStart(n, n2) + (this.cType == CalculationType.ISLAMIC_TBLA ? 1948439L : 1948440L) - 1L);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : this.internalGet(1, 1);
        return n;
    }

    @Override
    protected void handleComputeFields(int n) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        long l = (long)n - 1948440L;
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA) {
            if (this.cType == CalculationType.ISLAMIC_TBLA) {
                l = (long)n - 1948439L;
            }
            n2 = (int)Math.floor((double)(30L * l + 10646L) / 10631.0);
            n3 = (int)Math.ceil((double)(l - 29L - this.yearStart(n2)) / 29.5);
            n3 = Math.min(n3, 11);
        } else if (this.cType == CalculationType.ISLAMIC) {
            int n6 = (int)Math.floor((double)l / 29.530588853);
            long l2 = (long)Math.floor((double)n6 * 29.530588853 - 1.0);
            if (l - l2 >= 25L && IslamicCalendar.moonAge(this.internalGetTimeInMillis()) > 0.0) {
                ++n6;
            }
            while ((l2 = IslamicCalendar.trueMonthStart(n6)) > l) {
                --n6;
            }
            n2 = n6 / 12 + 1;
            n3 = n6 % 12;
        } else if (this.cType == CalculationType.ISLAMIC_UMALQURA) {
            long l3 = this.yearStart(1300);
            if (l < l3) {
                n2 = (int)Math.floor((double)(30L * l + 10646L) / 10631.0);
                n3 = (int)Math.ceil((double)(l - 29L - this.yearStart(n2)) / 29.5);
                n3 = Math.min(n3, 11);
            } else {
                int n7 = 1299;
                int n8 = 0;
                long l4 = 1L;
                while (l4 > 0L) {
                    if ((l4 = l - this.yearStart(++n7) + 1L) == (long)this.handleGetYearLength(n7)) {
                        n8 = 11;
                        break;
                    }
                    if (l4 >= (long)this.handleGetYearLength(n7)) continue;
                    int n9 = this.handleGetMonthLength(n7, n8);
                    n8 = 0;
                    while (l4 > (long)n9) {
                        l4 -= (long)n9;
                        n9 = this.handleGetMonthLength(n7, ++n8);
                    }
                    break block1;
                }
                n2 = n7;
                n3 = n8;
            }
        }
        n4 = (int)(l - this.monthStart(n2, n3)) + 1;
        n5 = (int)(l - this.monthStart(n2, 0) + 1L);
        this.internalSet(0, 0);
        this.internalSet(1, n2);
        this.internalSet(19, n2);
        this.internalSet(2, n3);
        this.internalSet(5, n4);
        this.internalSet(6, n5);
    }

    public void setCalculationType(CalculationType calculationType) {
        this.cType = calculationType;
        this.civil = this.cType == CalculationType.ISLAMIC_CIVIL;
    }

    public CalculationType getCalculationType() {
        return this.cType;
    }

    private void setCalcTypeForLocale(ULocale uLocale) {
        String string = CalendarUtil.getCalendarType(uLocale);
        if ("islamic-civil".equals(string)) {
            this.setCalculationType(CalculationType.ISLAMIC_CIVIL);
        } else if ("islamic-umalqura".equals(string)) {
            this.setCalculationType(CalculationType.ISLAMIC_UMALQURA);
        } else if ("islamic-tbla".equals(string)) {
            this.setCalculationType(CalculationType.ISLAMIC_TBLA);
        } else if (string.startsWith("islamic")) {
            this.setCalculationType(CalculationType.ISLAMIC);
        } else {
            this.setCalculationType(CalculationType.ISLAMIC_CIVIL);
        }
    }

    @Override
    public String getType() {
        if (this.cType == null) {
            return "islamic";
        }
        return this.cType.bcpType();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.cType == null) {
            this.cType = this.civil ? CalculationType.ISLAMIC_CIVIL : CalculationType.ISLAMIC;
        } else {
            this.civil = this.cType == CalculationType.ISLAMIC_CIVIL;
        }
    }

    public static enum CalculationType {
        ISLAMIC("islamic"),
        ISLAMIC_CIVIL("islamic-civil"),
        ISLAMIC_UMALQURA("islamic-umalqura"),
        ISLAMIC_TBLA("islamic-tbla");

        private String bcpType;

        private CalculationType(String string2) {
            this.bcpType = string2;
        }

        String bcpType() {
            return this.bcpType;
        }
    }
}

