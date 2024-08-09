/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.CalType;
import com.ibm.icu.impl.CalendarUtil;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.BuddhistCalendar;
import com.ibm.icu.util.ChineseCalendar;
import com.ibm.icu.util.CopticCalendar;
import com.ibm.icu.util.DangiCalendar;
import com.ibm.icu.util.EthiopicCalendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.HebrewCalendar;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.IndianCalendar;
import com.ibm.icu.util.IslamicCalendar;
import com.ibm.icu.util.JapaneseCalendar;
import com.ibm.icu.util.PersianCalendar;
import com.ibm.icu.util.TaiwanCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneTransition;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;

public abstract class Calendar
implements Serializable,
Cloneable,
Comparable<Calendar> {
    public static final int ERA = 0;
    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int WEEK_OF_YEAR = 3;
    public static final int WEEK_OF_MONTH = 4;
    public static final int DATE = 5;
    public static final int DAY_OF_MONTH = 5;
    public static final int DAY_OF_YEAR = 6;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_WEEK_IN_MONTH = 8;
    public static final int AM_PM = 9;
    public static final int HOUR = 10;
    public static final int HOUR_OF_DAY = 11;
    public static final int MINUTE = 12;
    public static final int SECOND = 13;
    public static final int MILLISECOND = 14;
    public static final int ZONE_OFFSET = 15;
    public static final int DST_OFFSET = 16;
    public static final int YEAR_WOY = 17;
    public static final int DOW_LOCAL = 18;
    public static final int EXTENDED_YEAR = 19;
    public static final int JULIAN_DAY = 20;
    public static final int MILLISECONDS_IN_DAY = 21;
    public static final int IS_LEAP_MONTH = 22;
    @Deprecated
    protected static final int BASE_FIELD_COUNT = 23;
    @Deprecated
    protected static final int MAX_FIELD_COUNT = 32;
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    public static final int JANUARY = 0;
    public static final int FEBRUARY = 1;
    public static final int MARCH = 2;
    public static final int APRIL = 3;
    public static final int MAY = 4;
    public static final int JUNE = 5;
    public static final int JULY = 6;
    public static final int AUGUST = 7;
    public static final int SEPTEMBER = 8;
    public static final int OCTOBER = 9;
    public static final int NOVEMBER = 10;
    public static final int DECEMBER = 11;
    public static final int UNDECIMBER = 12;
    public static final int AM = 0;
    public static final int PM = 1;
    @Deprecated
    public static final int WEEKDAY = 0;
    @Deprecated
    public static final int WEEKEND = 1;
    @Deprecated
    public static final int WEEKEND_ONSET = 2;
    @Deprecated
    public static final int WEEKEND_CEASE = 3;
    public static final int WALLTIME_LAST = 0;
    public static final int WALLTIME_FIRST = 1;
    public static final int WALLTIME_NEXT_VALID = 2;
    protected static final int ONE_SECOND = 1000;
    protected static final int ONE_MINUTE = 60000;
    protected static final int ONE_HOUR = 3600000;
    protected static final long ONE_DAY = 86400000L;
    protected static final long ONE_WEEK = 604800000L;
    protected static final int JAN_1_1_JULIAN_DAY = 1721426;
    protected static final int EPOCH_JULIAN_DAY = 2440588;
    protected static final int MIN_JULIAN = -2130706432;
    protected static final long MIN_MILLIS = -184303902528000000L;
    protected static final Date MIN_DATE;
    protected static final int MAX_JULIAN = 0x7F000000;
    protected static final long MAX_MILLIS = 183882168921600000L;
    protected static final Date MAX_DATE;
    private static final int MAX_HOURS = 548;
    private transient int[] fields;
    private transient int[] stamp;
    private long time;
    private transient boolean isTimeSet;
    private transient boolean areFieldsSet;
    private transient boolean areAllFieldsSet;
    private transient boolean areFieldsVirtuallySet;
    private boolean lenient = true;
    private TimeZone zone;
    private int firstDayOfWeek;
    private int minimalDaysInFirstWeek;
    private int weekendOnset;
    private int weekendOnsetMillis;
    private int weekendCease;
    private int weekendCeaseMillis;
    private int repeatedWallTime = 0;
    private int skippedWallTime = 0;
    protected static final int UNSET = 0;
    protected static final int INTERNALLY_SET = 1;
    protected static final int MINIMUM_USER_STAMP = 2;
    private transient int nextStamp = 2;
    private static int STAMP_MAX;
    private static final long serialVersionUID = 6222646104888790989L;
    private transient int internalSetMask;
    private transient int gregorianYear;
    private transient int gregorianMonth;
    private transient int gregorianDayOfYear;
    private transient int gregorianDayOfMonth;
    private static final ICUCache<String, PatternData> PATTERN_CACHE;
    private static final String[] DEFAULT_PATTERNS;
    private static final char QUOTE = '\'';
    private static final int FIELD_DIFF_MAX_INT = Integer.MAX_VALUE;
    private static final int[][] LIMITS;
    protected static final int MINIMUM = 0;
    protected static final int GREATEST_MINIMUM = 1;
    protected static final int LEAST_MAXIMUM = 2;
    protected static final int MAXIMUM = 3;
    private static final WeekDataCache WEEK_DATA_CACHE;
    protected static final int RESOLVE_REMAP = 32;
    static final int[][][] DATE_PRECEDENCE;
    static final int[][][] DOW_PRECEDENCE;
    private static final int[] FIND_ZONE_TRANSITION_TIME_UNITS;
    private static final int[][] GREGORIAN_MONTH_COUNT;
    private static final String[] FIELD_NAME;
    private ULocale validLocale;
    private ULocale actualLocale;
    static final boolean $assertionsDisabled;

    protected Calendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    protected Calendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale));
    }

    protected Calendar(TimeZone timeZone, ULocale uLocale) {
        this.zone = timeZone;
        this.setWeekData(Calendar.getRegionForCalendar(uLocale));
        this.setCalendarLocale(uLocale);
        this.initInternal();
    }

    private void setCalendarLocale(ULocale uLocale) {
        ULocale uLocale2 = uLocale;
        if (uLocale.getVariant().length() != 0 || uLocale.getKeywords() != null) {
            String string;
            String string2;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(uLocale.getLanguage());
            String string3 = uLocale.getScript();
            if (string3.length() > 0) {
                stringBuilder.append("_").append(string3);
            }
            if ((string2 = uLocale.getCountry()).length() > 0) {
                stringBuilder.append("_").append(string2);
            }
            if ((string = uLocale.getKeywordValue("calendar")) != null) {
                stringBuilder.append("@calendar=").append(string);
            }
            uLocale2 = new ULocale(stringBuilder.toString());
        }
        this.setLocale(uLocale2, uLocale2);
    }

    private void recalculateStamp() {
        this.nextStamp = 1;
        for (int i = 0; i < this.stamp.length; ++i) {
            int n = STAMP_MAX;
            int n2 = -1;
            for (int j = 0; j < this.stamp.length; ++j) {
                if (this.stamp[j] <= this.nextStamp || this.stamp[j] >= n) continue;
                n = this.stamp[j];
                n2 = j;
            }
            if (n2 < 0) break;
            this.stamp[n2] = ++this.nextStamp;
        }
        ++this.nextStamp;
    }

    private void initInternal() {
        this.fields = this.handleCreateFields();
        if (this.fields == null || this.fields.length < 23 || this.fields.length > 32) {
            throw new IllegalStateException("Invalid fields[]");
        }
        this.stamp = new int[this.fields.length];
        int n = 4718695;
        for (int i = 23; i < this.fields.length; ++i) {
            n |= 1 << i;
        }
        this.internalSetMask = n;
    }

    public static Calendar getInstance() {
        return Calendar.getInstanceInternal(null, null);
    }

    public static Calendar getInstance(TimeZone timeZone) {
        return Calendar.getInstanceInternal(timeZone, null);
    }

    public static Calendar getInstance(Locale locale) {
        return Calendar.getInstanceInternal(null, ULocale.forLocale(locale));
    }

    public static Calendar getInstance(ULocale uLocale) {
        return Calendar.getInstanceInternal(null, uLocale);
    }

    public static Calendar getInstance(TimeZone timeZone, Locale locale) {
        return Calendar.getInstanceInternal(timeZone, ULocale.forLocale(locale));
    }

    public static Calendar getInstance(TimeZone timeZone, ULocale uLocale) {
        return Calendar.getInstanceInternal(timeZone, uLocale);
    }

    private static Calendar getInstanceInternal(TimeZone timeZone, ULocale uLocale) {
        if (uLocale == null) {
            uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        Calendar calendar = Calendar.createInstance(uLocale);
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar;
    }

    private static String getRegionForCalendar(ULocale uLocale) {
        String string = ULocale.getRegionForSupplementalData(uLocale, true);
        if (string.length() == 0) {
            string = "001";
        }
        return string;
    }

    private static CalType getCalendarTypeForLocale(ULocale uLocale) {
        String string = CalendarUtil.getCalendarType(uLocale);
        if (string != null) {
            string = string.toLowerCase(Locale.ENGLISH);
            for (CalType calType : CalType.values()) {
                if (!string.equals(calType.getId())) continue;
                return calType;
            }
        }
        return CalType.UNKNOWN;
    }

    private static Calendar createInstance(ULocale uLocale) {
        Calendar calendar = null;
        TimeZone timeZone = TimeZone.getDefault();
        CalType calType = Calendar.getCalendarTypeForLocale(uLocale);
        if (calType == CalType.UNKNOWN) {
            calType = CalType.GREGORIAN;
        }
        switch (1.$SwitchMap$com$ibm$icu$impl$CalType[calType.ordinal()]) {
            case 1: {
                calendar = new GregorianCalendar(timeZone, uLocale);
                break;
            }
            case 2: {
                calendar = new GregorianCalendar(timeZone, uLocale);
                calendar.setFirstDayOfWeek(2);
                calendar.setMinimalDaysInFirstWeek(4);
                break;
            }
            case 3: {
                calendar = new BuddhistCalendar(timeZone, uLocale);
                break;
            }
            case 4: {
                calendar = new ChineseCalendar(timeZone, uLocale);
                break;
            }
            case 5: {
                calendar = new CopticCalendar(timeZone, uLocale);
                break;
            }
            case 6: {
                calendar = new DangiCalendar(timeZone, uLocale);
                break;
            }
            case 7: {
                calendar = new EthiopicCalendar(timeZone, uLocale);
                break;
            }
            case 8: {
                calendar = new EthiopicCalendar(timeZone, uLocale);
                ((EthiopicCalendar)calendar).setAmeteAlemEra(false);
                break;
            }
            case 9: {
                calendar = new HebrewCalendar(timeZone, uLocale);
                break;
            }
            case 10: {
                calendar = new IndianCalendar(timeZone, uLocale);
                break;
            }
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: {
                calendar = new IslamicCalendar(timeZone, uLocale);
                break;
            }
            case 16: {
                calendar = new JapaneseCalendar(timeZone, uLocale);
                break;
            }
            case 17: {
                calendar = new PersianCalendar(timeZone, uLocale);
                break;
            }
            case 18: {
                calendar = new TaiwanCalendar(timeZone, uLocale);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown calendar type");
            }
        }
        return calendar;
    }

    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    public static final String[] getKeywordValuesForLocale(String string, ULocale uLocale, boolean bl) {
        String string2 = ULocale.getRegionForSupplementalData(uLocale, true);
        ArrayList<String> arrayList = new ArrayList<String>();
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        UResourceBundle uResourceBundle2 = uResourceBundle.get("calendarPreferenceData");
        UResourceBundle uResourceBundle3 = null;
        try {
            uResourceBundle3 = uResourceBundle2.get(string2);
        } catch (MissingResourceException missingResourceException) {
            uResourceBundle3 = uResourceBundle2.get("001");
        }
        String[] stringArray = uResourceBundle3.getStringArray();
        if (bl) {
            return stringArray;
        }
        for (int i = 0; i < stringArray.length; ++i) {
            arrayList.add(stringArray[i]);
        }
        for (CalType calType : CalType.values()) {
            if (arrayList.contains(calType.getId())) continue;
            arrayList.add(calType.getId());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public final Date getTime() {
        return new Date(this.getTimeInMillis());
    }

    public final void setTime(Date date) {
        this.setTimeInMillis(date.getTime());
    }

    public long getTimeInMillis() {
        if (!this.isTimeSet) {
            this.updateTime();
        }
        return this.time;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setTimeInMillis(long l) {
        if (l > 183882168921600000L) {
            if (!this.isLenient()) throw new IllegalArgumentException("millis value greater than upper bounds for a Calendar : " + l);
            l = 183882168921600000L;
        } else if (l < -184303902528000000L) {
            if (!this.isLenient()) throw new IllegalArgumentException("millis value less than lower bounds for a Calendar : " + l);
            l = -184303902528000000L;
        }
        this.time = l;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.areFieldsVirtuallySet = true;
        this.isTimeSet = true;
        for (int i = 0; i < this.fields.length; ++i) {
            this.stamp[i] = 0;
            this.fields[i] = 0;
        }
    }

    public final int get(int n) {
        this.complete();
        return this.fields[n];
    }

    protected final int internalGet(int n) {
        return this.fields[n];
    }

    protected final int internalGet(int n, int n2) {
        return this.stamp[n] > 0 ? this.fields[n] : n2;
    }

    public final void set(int n, int n2) {
        if (this.areFieldsVirtuallySet) {
            this.computeFields();
        }
        this.fields[n] = n2;
        if (this.nextStamp == STAMP_MAX) {
            this.recalculateStamp();
        }
        ++this.nextStamp;
        this.areFieldsVirtuallySet = false;
        this.areFieldsSet = false;
        this.isTimeSet = false;
    }

    public final void set(int n, int n2, int n3) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public final void set(int n, int n2, int n3, int n4, int n5) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
    }

    public final void set(int n, int n2, int n3, int n4, int n5, int n6) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    private static int gregoYearFromIslamicStart(int n) {
        int n2 = 0;
        if (n >= 1397) {
            int n3 = (n - 1397) / 67;
            int n4 = (n - 1397) % 67;
            n2 = 2 * n3 + (n4 >= 33 ? 1 : 0);
        } else {
            int n5 = (n - 1396) / 67 - 1;
            int n6 = -(n - 1396) % 67;
            n2 = 2 * n5 + (n6 <= 33 ? 1 : 0);
        }
        return n + 579 - n2;
    }

    @Deprecated
    public final int getRelatedYear() {
        int n = this.get(19);
        CalType calType = CalType.GREGORIAN;
        String string = this.getType();
        for (CalType calType2 : CalType.values()) {
            if (!string.equals(calType2.getId())) continue;
            calType = calType2;
            break;
        }
        switch (1.$SwitchMap$com$ibm$icu$impl$CalType[calType.ordinal()]) {
            case 17: {
                n += 622;
                break;
            }
            case 9: {
                n -= 3760;
                break;
            }
            case 4: {
                n -= 2637;
                break;
            }
            case 10: {
                n += 79;
                break;
            }
            case 5: {
                n += 284;
                break;
            }
            case 7: {
                n += 8;
                break;
            }
            case 8: {
                n -= 5492;
                break;
            }
            case 6: {
                n -= 2333;
                break;
            }
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: {
                n = Calendar.gregoYearFromIslamicStart(n);
                break;
            }
        }
        return n;
    }

    private static int firstIslamicStartYearFromGrego(int n) {
        int n2 = 0;
        if (n >= 1977) {
            int n3 = (n - 1977) / 65;
            int n4 = (n - 1977) % 65;
            n2 = 2 * n3 + (n4 >= 32 ? 1 : 0);
        } else {
            int n5 = (n - 1976) / 65 - 1;
            int n6 = -(n - 1976) % 65;
            n2 = 2 * n5 + (n6 <= 32 ? 1 : 0);
        }
        return n - 579 + n2;
    }

    @Deprecated
    public final void setRelatedYear(int n) {
        CalType calType = CalType.GREGORIAN;
        String string = this.getType();
        for (CalType calType2 : CalType.values()) {
            if (!string.equals(calType2.getId())) continue;
            calType = calType2;
            break;
        }
        switch (1.$SwitchMap$com$ibm$icu$impl$CalType[calType.ordinal()]) {
            case 17: {
                n -= 622;
                break;
            }
            case 9: {
                n += 3760;
                break;
            }
            case 4: {
                n += 2637;
                break;
            }
            case 10: {
                n -= 79;
                break;
            }
            case 5: {
                n -= 284;
                break;
            }
            case 7: {
                n -= 8;
                break;
            }
            case 8: {
                n += 5492;
                break;
            }
            case 6: {
                n += 2333;
                break;
            }
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: {
                n = Calendar.firstIslamicStartYearFromGrego(n);
                break;
            }
        }
        this.set(19, n);
    }

    public final void clear() {
        for (int i = 0; i < this.fields.length; ++i) {
            this.stamp[i] = 0;
            this.fields[i] = 0;
        }
        this.areFieldsVirtuallySet = false;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.isTimeSet = false;
    }

    public final void clear(int n) {
        if (this.areFieldsVirtuallySet) {
            this.computeFields();
        }
        this.fields[n] = 0;
        this.stamp[n] = 0;
        this.areFieldsVirtuallySet = false;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.isTimeSet = false;
    }

    public final boolean isSet(int n) {
        return this.areFieldsVirtuallySet || this.stamp[n] != 0;
    }

    protected void complete() {
        if (!this.isTimeSet) {
            this.updateTime();
        }
        if (!this.areFieldsSet) {
            this.computeFields();
            this.areFieldsSet = true;
            this.areAllFieldsSet = true;
        }
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        Calendar calendar = (Calendar)object;
        return this.isEquivalentTo(calendar) && this.getTimeInMillis() == calendar.getTime().getTime();
    }

    public boolean isEquivalentTo(Calendar calendar) {
        return this.getClass() == calendar.getClass() && this.isLenient() == calendar.isLenient() && this.getFirstDayOfWeek() == calendar.getFirstDayOfWeek() && this.getMinimalDaysInFirstWeek() == calendar.getMinimalDaysInFirstWeek() && this.getTimeZone().equals(calendar.getTimeZone()) && this.getRepeatedWallTimeOption() == calendar.getRepeatedWallTimeOption() && this.getSkippedWallTimeOption() == calendar.getSkippedWallTimeOption();
    }

    public int hashCode() {
        return (this.lenient ? 1 : 0) | this.firstDayOfWeek << 1 | this.minimalDaysInFirstWeek << 4 | this.repeatedWallTime << 7 | this.skippedWallTime << 9 | this.zone.hashCode() << 11;
    }

    private long compare(Object object) {
        long l;
        if (object instanceof Calendar) {
            l = ((Calendar)object).getTimeInMillis();
        } else if (object instanceof Date) {
            l = ((Date)object).getTime();
        } else {
            throw new IllegalArgumentException(object + "is not a Calendar or Date");
        }
        return this.getTimeInMillis() - l;
    }

    public boolean before(Object object) {
        return this.compare(object) < 0L;
    }

    public boolean after(Object object) {
        return this.compare(object) > 0L;
    }

    public int getActualMaximum(int n) {
        int n2;
        switch (n) {
            case 5: {
                Calendar calendar = (Calendar)this.clone();
                calendar.setLenient(false);
                calendar.prepareGetActual(n, true);
                n2 = this.handleGetMonthLength(calendar.get(19), calendar.get(2));
                break;
            }
            case 6: {
                Calendar calendar = (Calendar)this.clone();
                calendar.setLenient(false);
                calendar.prepareGetActual(n, true);
                n2 = this.handleGetYearLength(calendar.get(19));
                break;
            }
            case 0: 
            case 7: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 18: 
            case 20: 
            case 21: {
                n2 = this.getMaximum(n);
                break;
            }
            default: {
                n2 = this.getActualHelper(n, this.getLeastMaximum(n), this.getMaximum(n));
            }
        }
        return n2;
    }

    public int getActualMinimum(int n) {
        int n2;
        switch (n) {
            case 7: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 18: 
            case 20: 
            case 21: {
                n2 = this.getMinimum(n);
                break;
            }
            default: {
                n2 = this.getActualHelper(n, this.getGreatestMinimum(n), this.getMinimum(n));
            }
        }
        return n2;
    }

    protected void prepareGetActual(int n, boolean bl) {
        this.set(21, 0);
        switch (n) {
            case 1: 
            case 19: {
                this.set(6, this.getGreatestMinimum(6));
                break;
            }
            case 17: {
                this.set(3, this.getGreatestMinimum(3));
                break;
            }
            case 2: {
                this.set(5, this.getGreatestMinimum(5));
                break;
            }
            case 8: {
                this.set(5, 1);
                this.set(7, this.get(7));
                break;
            }
            case 3: 
            case 4: {
                int n2 = this.firstDayOfWeek;
                if (bl && (n2 = (n2 + 6) % 7) < 1) {
                    n2 += 7;
                }
                this.set(7, n2);
            }
        }
        this.set(n, this.getGreatestMinimum(n));
    }

    private int getActualHelper(int n, int n2, int n3) {
        if (n2 == n3) {
            return n2;
        }
        int n4 = n3 > n2 ? 1 : -1;
        Calendar calendar = (Calendar)this.clone();
        calendar.complete();
        calendar.setLenient(false);
        calendar.prepareGetActual(n, n4 < 0);
        calendar.set(n, n2);
        if (calendar.get(n) != n2 && n != 4 && n4 > 0) {
            return n2;
        }
        int n5 = n2;
        do {
            calendar.add(n, n4);
            if (calendar.get(n) != (n2 += n4)) break;
            n5 = n2;
        } while (n2 != n3);
        return n5;
    }

    public final void roll(int n, boolean bl) {
        this.roll(n, bl ? 1 : -1);
    }

    public void roll(int n, int n2) {
        if (n2 == 0) {
            return;
        }
        this.complete();
        switch (n) {
            case 0: 
            case 5: 
            case 9: 
            case 12: 
            case 13: 
            case 14: 
            case 21: {
                int n3 = this.getActualMinimum(n);
                int n4 = this.getActualMaximum(n);
                int n5 = n4 - n3 + 1;
                int n6 = this.internalGet(n) + n2;
                n6 = (n6 - n3) % n5;
                if (n6 < 0) {
                    n6 += n5;
                }
                this.set(n, n6 += n3);
                return;
            }
            case 10: 
            case 11: {
                long l = this.getTimeInMillis();
                int n7 = this.internalGet(n);
                int n8 = this.getMaximum(n);
                int n9 = (n7 + n2) % (n8 + 1);
                if (n9 < 0) {
                    n9 += n8 + 1;
                }
                this.setTimeInMillis(l + 3600000L * ((long)n9 - (long)n7));
                return;
            }
            case 2: {
                int n10 = this.getActualMaximum(2);
                int n11 = (this.internalGet(2) + n2) % (n10 + 1);
                if (n11 < 0) {
                    n11 += n10 + 1;
                }
                this.set(2, n11);
                this.pinField(5);
                return;
            }
            case 1: 
            case 17: {
                String string;
                boolean bl = false;
                int n12 = this.get(0);
                if (n12 == 0 && ((string = this.getType()).equals("gregorian") || string.equals("roc") || string.equals("coptic"))) {
                    n2 = -n2;
                    bl = true;
                }
                int n13 = this.internalGet(n) + n2;
                if (n12 > 0 || n13 >= 1) {
                    int n14 = this.getActualMaximum(n);
                    if (n14 < 32768) {
                        if (n13 < 1) {
                            n13 = n14 - -n13 % n14;
                        } else if (n13 > n14) {
                            n13 = (n13 - 1) % n14 + 1;
                        }
                    } else if (n13 < 1) {
                        n13 = 1;
                    }
                } else if (bl) {
                    n13 = 1;
                }
                this.set(n, n13);
                this.pinField(2);
                this.pinField(5);
                return;
            }
            case 19: {
                this.set(n, this.internalGet(n) + n2);
                this.pinField(2);
                this.pinField(5);
                return;
            }
            case 4: {
                int n15;
                int n16 = this.internalGet(7) - this.getFirstDayOfWeek();
                if (n16 < 0) {
                    n16 += 7;
                }
                if ((n15 = (n16 - this.internalGet(5) + 1) % 7) < 0) {
                    n15 += 7;
                }
                int n17 = 7 - n15 < this.getMinimalDaysInFirstWeek() ? 8 - n15 : 1 - n15;
                int n18 = this.getActualMaximum(5);
                int n19 = (n18 - this.internalGet(5) + n16) % 7;
                int n20 = n18 + 7 - n19;
                int n21 = n20 - n17;
                int n22 = (this.internalGet(5) + n2 * 7 - n17) % n21;
                if (n22 < 0) {
                    n22 += n21;
                }
                if ((n22 += n17) < 1) {
                    n22 = 1;
                }
                if (n22 > n18) {
                    n22 = n18;
                }
                this.set(5, n22);
                return;
            }
            case 3: {
                int n23;
                int n24 = this.internalGet(7) - this.getFirstDayOfWeek();
                if (n24 < 0) {
                    n24 += 7;
                }
                if ((n23 = (n24 - this.internalGet(6) + 1) % 7) < 0) {
                    n23 += 7;
                }
                int n25 = 7 - n23 < this.getMinimalDaysInFirstWeek() ? 8 - n23 : 1 - n23;
                int n26 = this.getActualMaximum(6);
                int n27 = (n26 - this.internalGet(6) + n24) % 7;
                int n28 = n26 + 7 - n27;
                int n29 = n28 - n25;
                int n30 = (this.internalGet(6) + n2 * 7 - n25) % n29;
                if (n30 < 0) {
                    n30 += n29;
                }
                if ((n30 += n25) < 1) {
                    n30 = 1;
                }
                if (n30 > n26) {
                    n30 = n26;
                }
                this.set(6, n30);
                this.clear(2);
                return;
            }
            case 6: {
                long l = (long)n2 * 86400000L;
                long l2 = this.time - (long)(this.internalGet(6) - 1) * 86400000L;
                int n31 = this.getActualMaximum(6);
                this.time = (this.time + l - l2) % ((long)n31 * 86400000L);
                if (this.time < 0L) {
                    this.time += (long)n31 * 86400000L;
                }
                this.setTimeInMillis(this.time + l2);
                return;
            }
            case 7: 
            case 18: {
                long l = (long)n2 * 86400000L;
                int n32 = this.internalGet(n);
                if ((n32 -= n == 7 ? this.getFirstDayOfWeek() : 1) < 0) {
                    n32 += 7;
                }
                long l3 = this.time - (long)n32 * 86400000L;
                this.time = (this.time + l - l3) % 604800000L;
                if (this.time < 0L) {
                    this.time += 604800000L;
                }
                this.setTimeInMillis(this.time + l3);
                return;
            }
            case 8: {
                long l = (long)n2 * 604800000L;
                int n33 = (this.internalGet(5) - 1) / 7;
                int n34 = (this.getActualMaximum(5) - this.internalGet(5)) / 7;
                long l4 = this.time - (long)n33 * 604800000L;
                long l5 = 604800000L * (long)(n33 + n34 + 1);
                this.time = (this.time + l - l4) % l5;
                if (this.time < 0L) {
                    this.time += l5;
                }
                this.setTimeInMillis(this.time + l4);
                return;
            }
            case 20: {
                this.set(n, this.internalGet(n) + n2);
                return;
            }
        }
        throw new IllegalArgumentException("Calendar.roll(" + this.fieldName(n) + ") not supported");
    }

    public void add(int n, int n2) {
        int n3;
        int n4;
        if (n2 == 0) {
            return;
        }
        long l = n2;
        boolean bl = true;
        switch (n) {
            case 0: {
                this.set(n, this.get(n) + n2);
                this.pinField(0);
                return;
            }
            case 1: 
            case 17: {
                String string;
                int bl2 = this.get(0);
                if (bl2 == 0 && ((string = this.getType()).equals("gregorian") || string.equals("roc") || string.equals("coptic"))) {
                    n2 = -n2;
                }
            }
            case 2: 
            case 19: {
                boolean n42 = this.isLenient();
                this.setLenient(false);
                this.set(n, this.get(n) + n2);
                this.pinField(5);
                if (!n42) {
                    this.complete();
                    this.setLenient(n42);
                }
                return;
            }
            case 3: 
            case 4: 
            case 8: {
                l *= 604800000L;
                break;
            }
            case 9: {
                l *= 43200000L;
                break;
            }
            case 5: 
            case 6: 
            case 7: 
            case 18: 
            case 20: {
                l *= 86400000L;
                break;
            }
            case 10: 
            case 11: {
                l *= 3600000L;
                bl = false;
                break;
            }
            case 12: {
                l *= 60000L;
                bl = false;
                break;
            }
            case 13: {
                l *= 1000L;
                bl = false;
                break;
            }
            case 14: 
            case 21: {
                bl = false;
                break;
            }
            default: {
                throw new IllegalArgumentException("Calendar.add(" + this.fieldName(n) + ") not supported");
            }
        }
        boolean bl2 = false;
        int n5 = 0;
        if (bl) {
            n4 = this.get(16) + this.get(15);
            n5 = this.get(21);
        }
        this.setTimeInMillis(this.getTimeInMillis() + l);
        if (bl && (n3 = this.get(21)) != n5) {
            long l2 = this.internalGetTimeInMillis();
            int n6 = this.get(16) + this.get(15);
            if (n6 != n4) {
                long l3 = (long)(n4 - n6) % 86400000L;
                if (l3 != 0L) {
                    this.setTimeInMillis(l2 + l3);
                    n3 = this.get(21);
                }
                if (n3 != n5) {
                    switch (this.skippedWallTime) {
                        case 1: {
                            if (l3 <= 0L) break;
                            this.setTimeInMillis(l2);
                            break;
                        }
                        case 0: {
                            if (l3 >= 0L) break;
                            this.setTimeInMillis(l2);
                            break;
                        }
                        case 2: {
                            long l4 = l3 > 0L ? this.internalGetTimeInMillis() : l2;
                            Long l5 = this.getImmediatePreviousZoneTransition(l4);
                            if (l5 != null) {
                                this.setTimeInMillis(l5);
                                break;
                            }
                            throw new RuntimeException("Could not locate a time zone transition before " + l4);
                        }
                    }
                }
            }
        }
    }

    public String getDisplayName(Locale locale) {
        return this.getClass().getName();
    }

    public String getDisplayName(ULocale uLocale) {
        return this.getClass().getName();
    }

    @Override
    public int compareTo(Calendar calendar) {
        long l = this.getTimeInMillis() - calendar.getTimeInMillis();
        return l < 0L ? -1 : (l > 0L ? 1 : 0);
    }

    public DateFormat getDateTimeFormat(int n, int n2, Locale locale) {
        return Calendar.formatHelper(this, ULocale.forLocale(locale), n, n2);
    }

    public DateFormat getDateTimeFormat(int n, int n2, ULocale uLocale) {
        return Calendar.formatHelper(this, uLocale, n, n2);
    }

    protected DateFormat handleGetDateFormat(String string, Locale locale) {
        return this.handleGetDateFormat(string, null, ULocale.forLocale(locale));
    }

    protected DateFormat handleGetDateFormat(String string, String string2, Locale locale) {
        return this.handleGetDateFormat(string, string2, ULocale.forLocale(locale));
    }

    protected DateFormat handleGetDateFormat(String string, ULocale uLocale) {
        return this.handleGetDateFormat(string, null, uLocale);
    }

    protected DateFormat handleGetDateFormat(String string, String string2, ULocale uLocale) {
        FormatConfiguration formatConfiguration = new FormatConfiguration(null);
        FormatConfiguration.access$102(formatConfiguration, string);
        FormatConfiguration.access$202(formatConfiguration, string2);
        FormatConfiguration.access$302(formatConfiguration, new DateFormatSymbols(this, uLocale));
        FormatConfiguration.access$402(formatConfiguration, uLocale);
        FormatConfiguration.access$502(formatConfiguration, this);
        return SimpleDateFormat.getInstance(formatConfiguration);
    }

    private static DateFormat formatHelper(Calendar calendar, ULocale uLocale, int n, int n2) {
        Object object;
        if (n2 < -1 || n2 > 3) {
            throw new IllegalArgumentException("Illegal time style " + n2);
        }
        if (n < -1 || n > 3) {
            throw new IllegalArgumentException("Illegal date style " + n);
        }
        PatternData patternData = PatternData.access$600(calendar, uLocale);
        String string = null;
        String string2 = null;
        if (n2 >= 0 && n >= 0) {
            string2 = SimpleFormatterImpl.formatRawPattern(PatternData.access$700(patternData, n), 2, 2, PatternData.access$800(patternData)[n2], PatternData.access$800(patternData)[n + 4]);
            if (PatternData.access$900(patternData) != null) {
                object = PatternData.access$900(patternData)[n + 4];
                String string3 = PatternData.access$900(patternData)[n2];
                string = Calendar.mergeOverrideStrings(PatternData.access$800(patternData)[n + 4], PatternData.access$800(patternData)[n2], (String)object, string3);
            }
        } else if (n2 >= 0) {
            string2 = PatternData.access$800(patternData)[n2];
            if (PatternData.access$900(patternData) != null) {
                string = PatternData.access$900(patternData)[n2];
            }
        } else if (n >= 0) {
            string2 = PatternData.access$800(patternData)[n + 4];
            if (PatternData.access$900(patternData) != null) {
                string = PatternData.access$900(patternData)[n + 4];
            }
        } else {
            throw new IllegalArgumentException("No date or time style specified");
        }
        object = calendar.handleGetDateFormat(string2, string, uLocale);
        ((DateFormat)object).setCalendar(calendar);
        return object;
    }

    private static PatternData getPatternData(ULocale uLocale, String string) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.findWithFallback("calendar/" + string + "/DateTimePatterns");
        if (iCUResourceBundle2 == null) {
            iCUResourceBundle2 = iCUResourceBundle.getWithFallback("calendar/gregorian/DateTimePatterns");
        }
        int n = iCUResourceBundle2.getSize();
        String[] stringArray = new String[n];
        String[] stringArray2 = new String[n];
        block4: for (int i = 0; i < n; ++i) {
            ICUResourceBundle iCUResourceBundle3 = (ICUResourceBundle)iCUResourceBundle2.get(i);
            switch (iCUResourceBundle3.getType()) {
                case 0: {
                    stringArray[i] = iCUResourceBundle3.getString();
                    continue block4;
                }
                case 8: {
                    stringArray[i] = iCUResourceBundle3.getString(0);
                    stringArray2[i] = iCUResourceBundle3.getString(1);
                }
            }
        }
        return new PatternData(stringArray, stringArray2);
    }

    @Deprecated
    public static String getDateTimePattern(Calendar calendar, ULocale uLocale, int n) {
        PatternData patternData = PatternData.access$600(calendar, uLocale);
        return PatternData.access$700(patternData, n);
    }

    private static String mergeOverrideStrings(String string, String string2, String string3, String string4) {
        if (string3 == null && string4 == null) {
            return null;
        }
        if (string3 == null) {
            return Calendar.expandOverride(string2, string4);
        }
        if (string4 == null) {
            return Calendar.expandOverride(string, string3);
        }
        if (string3.equals(string4)) {
            return string3;
        }
        return Calendar.expandOverride(string, string3) + ";" + Calendar.expandOverride(string2, string4);
    }

    private static String expandOverride(String string, String string2) {
        if (string2.indexOf(61) >= 0) {
            return string2;
        }
        boolean bl = false;
        char c = ' ';
        StringBuilder stringBuilder = new StringBuilder();
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(string);
        char c2 = stringCharacterIterator.first();
        while (c2 != '\uffff') {
            if (c2 == '\'') {
                bl = !bl;
                c = c2;
            } else {
                if (!bl && c2 != c) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(";");
                    }
                    stringBuilder.append(c2);
                    stringBuilder.append("=");
                    stringBuilder.append(string2);
                }
                c = c2;
            }
            c2 = stringCharacterIterator.next();
        }
        return stringBuilder.toString();
    }

    protected void pinField(int n) {
        int n2 = this.getActualMaximum(n);
        int n3 = this.getActualMinimum(n);
        if (this.fields[n] > n2) {
            this.set(n, n2);
        } else if (this.fields[n] < n3) {
            this.set(n, n3);
        }
    }

    protected int weekNumber(int n, int n2, int n3) {
        int n4 = (n3 - this.getFirstDayOfWeek() - n2 + 1) % 7;
        if (n4 < 0) {
            n4 += 7;
        }
        int n5 = (n + n4 - 1) / 7;
        if (7 - n4 >= this.getMinimalDaysInFirstWeek()) {
            ++n5;
        }
        return n5;
    }

    protected final int weekNumber(int n, int n2) {
        return this.weekNumber(n, n, n2);
    }

    public int fieldDifference(Date date, int n) {
        long l;
        int n2 = 0;
        long l2 = this.getTimeInMillis();
        if (l2 < (l = date.getTime())) {
            int n3;
            block13: {
                n3 = 1;
                while (true) {
                    this.setTimeInMillis(l2);
                    this.add(n, n3);
                    long l3 = this.getTimeInMillis();
                    if (l3 == l) {
                        return n3;
                    }
                    if (l3 > l) break block13;
                    if (n3 >= Integer.MAX_VALUE) break;
                    n2 = n3;
                    if ((n3 <<= 1) >= 0) continue;
                    n3 = Integer.MAX_VALUE;
                }
                throw new RuntimeException();
            }
            while (n3 - n2 > 1) {
                int n4 = n2 + (n3 - n2) / 2;
                this.setTimeInMillis(l2);
                this.add(n, n4);
                long l4 = this.getTimeInMillis();
                if (l4 == l) {
                    return n4;
                }
                if (l4 > l) {
                    n3 = n4;
                    continue;
                }
                n2 = n4;
            }
        } else if (l2 > l) {
            int n5;
            block14: {
                n5 = -1;
                do {
                    this.setTimeInMillis(l2);
                    this.add(n, n5);
                    long l5 = this.getTimeInMillis();
                    if (l5 == l) {
                        return n5;
                    }
                    if (l5 < l) break block14;
                    n2 = n5;
                } while ((n5 <<= 1) != 0);
                throw new RuntimeException();
            }
            while (n2 - n5 > 1) {
                int n6 = n2 + (n5 - n2) / 2;
                this.setTimeInMillis(l2);
                this.add(n, n6);
                long l6 = this.getTimeInMillis();
                if (l6 == l) {
                    return n6;
                }
                if (l6 < l) {
                    n5 = n6;
                    continue;
                }
                n2 = n6;
            }
        }
        this.setTimeInMillis(l2);
        this.add(n, n2);
        return n2;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.zone = timeZone;
        this.areFieldsSet = false;
    }

    public TimeZone getTimeZone() {
        return this.zone;
    }

    public void setLenient(boolean bl) {
        this.lenient = bl;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public void setRepeatedWallTimeOption(int n) {
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("Illegal repeated wall time option - " + n);
        }
        this.repeatedWallTime = n;
    }

    public int getRepeatedWallTimeOption() {
        return this.repeatedWallTime;
    }

    public void setSkippedWallTimeOption(int n) {
        if (n != 0 && n != 1 && n != 2) {
            throw new IllegalArgumentException("Illegal skipped wall time option - " + n);
        }
        this.skippedWallTime = n;
    }

    public int getSkippedWallTimeOption() {
        return this.skippedWallTime;
    }

    public void setFirstDayOfWeek(int n) {
        if (this.firstDayOfWeek != n) {
            if (n < 1 || n > 7) {
                throw new IllegalArgumentException("Invalid day of week");
            }
            this.firstDayOfWeek = n;
            this.areFieldsSet = false;
        }
    }

    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public void setMinimalDaysInFirstWeek(int n) {
        if (n < 1) {
            n = 1;
        } else if (n > 7) {
            n = 7;
        }
        if (this.minimalDaysInFirstWeek != n) {
            this.minimalDaysInFirstWeek = n;
            this.areFieldsSet = false;
        }
    }

    public int getMinimalDaysInFirstWeek() {
        return this.minimalDaysInFirstWeek;
    }

    protected abstract int handleGetLimit(int var1, int var2);

    protected int getLimit(int n, int n2) {
        switch (n) {
            case 7: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 18: 
            case 20: 
            case 21: 
            case 22: {
                return LIMITS[n][n2];
            }
            case 4: {
                int n3;
                if (n2 == 0) {
                    n3 = this.getMinimalDaysInFirstWeek() == 1 ? 1 : 0;
                } else if (n2 == 1) {
                    n3 = 1;
                } else {
                    int n4 = this.getMinimalDaysInFirstWeek();
                    int n5 = this.handleGetLimit(5, n2);
                    n3 = n2 == 2 ? (n5 + (7 - n4)) / 7 : (n5 + 6 + (7 - n4)) / 7;
                }
                return n3;
            }
        }
        return this.handleGetLimit(n, n2);
    }

    public final int getMinimum(int n) {
        return this.getLimit(n, 0);
    }

    public final int getMaximum(int n) {
        return this.getLimit(n, 3);
    }

    public final int getGreatestMinimum(int n) {
        return this.getLimit(n, 1);
    }

    public final int getLeastMaximum(int n) {
        return this.getLimit(n, 2);
    }

    @Deprecated
    public int getDayOfWeekType(int n) {
        if (n < 1 || n > 7) {
            throw new IllegalArgumentException("Invalid day of week");
        }
        if (this.weekendOnset == this.weekendCease) {
            if (n != this.weekendOnset) {
                return 1;
            }
            return this.weekendOnsetMillis == 0 ? 1 : 2;
        }
        if (this.weekendOnset < this.weekendCease ? n < this.weekendOnset || n > this.weekendCease : n > this.weekendCease && n < this.weekendOnset) {
            return 1;
        }
        if (n == this.weekendOnset) {
            return this.weekendOnsetMillis == 0 ? 1 : 2;
        }
        if (n == this.weekendCease) {
            return this.weekendCeaseMillis >= 86400000 ? 1 : 3;
        }
        return 0;
    }

    @Deprecated
    public int getWeekendTransition(int n) {
        if (n == this.weekendOnset) {
            return this.weekendOnsetMillis;
        }
        if (n == this.weekendCease) {
            return this.weekendCeaseMillis;
        }
        throw new IllegalArgumentException("Not weekend transition day");
    }

    public boolean isWeekend(Date date) {
        this.setTime(date);
        return this.isWeekend();
    }

    public boolean isWeekend() {
        int n = this.get(7);
        int n2 = this.getDayOfWeekType(n);
        switch (n2) {
            case 0: {
                return true;
            }
            case 1: {
                return false;
            }
        }
        int n3 = this.internalGet(14) + 1000 * (this.internalGet(13) + 60 * (this.internalGet(12) + 60 * this.internalGet(11)));
        int n4 = this.getWeekendTransition(n);
        return n2 == 2 ? n3 >= n4 : n3 < n4;
    }

    public Object clone() {
        try {
            Calendar calendar = (Calendar)super.clone();
            calendar.fields = new int[this.fields.length];
            calendar.stamp = new int[this.fields.length];
            System.arraycopy(this.fields, 0, calendar.fields, 0, this.fields.length);
            System.arraycopy(this.stamp, 0, calendar.stamp, 0, this.fields.length);
            calendar.zone = (TimeZone)this.zone.clone();
            return calendar;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[time=");
        stringBuilder.append(this.isTimeSet ? String.valueOf(this.time) : "?");
        stringBuilder.append(",areFieldsSet=");
        stringBuilder.append(this.areFieldsSet);
        stringBuilder.append(",areAllFieldsSet=");
        stringBuilder.append(this.areAllFieldsSet);
        stringBuilder.append(",lenient=");
        stringBuilder.append(this.lenient);
        stringBuilder.append(",zone=");
        stringBuilder.append(this.zone);
        stringBuilder.append(",firstDayOfWeek=");
        stringBuilder.append(this.firstDayOfWeek);
        stringBuilder.append(",minimalDaysInFirstWeek=");
        stringBuilder.append(this.minimalDaysInFirstWeek);
        stringBuilder.append(",repeatedWallTime=");
        stringBuilder.append(this.repeatedWallTime);
        stringBuilder.append(",skippedWallTime=");
        stringBuilder.append(this.skippedWallTime);
        for (int i = 0; i < this.fields.length; ++i) {
            stringBuilder.append(',').append(this.fieldName(i)).append('=');
            stringBuilder.append(this.isSet(i) ? String.valueOf(this.fields[i]) : "?");
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public static WeekData getWeekDataForRegion(String string) {
        return WEEK_DATA_CACHE.createInstance(string, string);
    }

    public WeekData getWeekData() {
        return new WeekData(this.firstDayOfWeek, this.minimalDaysInFirstWeek, this.weekendOnset, this.weekendOnsetMillis, this.weekendCease, this.weekendCeaseMillis);
    }

    public Calendar setWeekData(WeekData weekData) {
        this.setFirstDayOfWeek(weekData.firstDayOfWeek);
        this.setMinimalDaysInFirstWeek(weekData.minimalDaysInFirstWeek);
        this.weekendOnset = weekData.weekendOnset;
        this.weekendOnsetMillis = weekData.weekendOnsetMillis;
        this.weekendCease = weekData.weekendCease;
        this.weekendCeaseMillis = weekData.weekendCeaseMillis;
        return this;
    }

    private static WeekData getWeekDataForRegionInternal(String string) {
        if (string == null) {
            string = "001";
        }
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        UResourceBundle uResourceBundle2 = uResourceBundle.get("weekData");
        UResourceBundle uResourceBundle3 = null;
        try {
            uResourceBundle3 = uResourceBundle2.get(string);
        } catch (MissingResourceException missingResourceException) {
            if (!string.equals("001")) {
                uResourceBundle3 = uResourceBundle2.get("001");
            }
            throw missingResourceException;
        }
        int[] nArray = uResourceBundle3.getIntVector();
        return new WeekData(nArray[0], nArray[1], nArray[2], nArray[3], nArray[4], nArray[5]);
    }

    private void setWeekData(String string) {
        if (string == null) {
            string = "001";
        }
        WeekData weekData = (WeekData)WEEK_DATA_CACHE.getInstance(string, string);
        this.setWeekData(weekData);
    }

    private void updateTime() {
        this.computeTime();
        if (this.isLenient() || !this.areAllFieldsSet) {
            this.areFieldsSet = false;
        }
        this.isTimeSet = true;
        this.areFieldsVirtuallySet = false;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (!this.isTimeSet) {
            try {
                this.updateTime();
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.initInternal();
        this.isTimeSet = true;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.areFieldsVirtuallySet = true;
        this.nextStamp = 2;
    }

    protected void computeFields() {
        int n;
        int[] nArray = new int[2];
        this.getTimeZone().getOffset(this.time, false, nArray);
        long l = this.time + (long)nArray[0] + (long)nArray[1];
        int n2 = this.internalSetMask;
        for (int i = 0; i < this.fields.length; ++i) {
            this.stamp[i] = (n2 & 1) == 0 ? 1 : 0;
            n2 >>= 1;
        }
        long l2 = Calendar.floorDivide(l, 86400000L);
        this.fields[20] = (int)l2 + 2440588;
        this.computeGregorianAndDOWFields(this.fields[20]);
        this.handleComputeFields(this.fields[20]);
        this.computeWeekFields();
        this.fields[21] = n = (int)(l - l2 * 86400000L);
        this.fields[14] = n % 1000;
        this.fields[13] = (n /= 1000) % 60;
        this.fields[12] = (n /= 60) % 60;
        this.fields[11] = n /= 60;
        this.fields[9] = n / 12;
        this.fields[10] = n % 12;
        this.fields[15] = nArray[0];
        this.fields[16] = nArray[1];
    }

    private final void computeGregorianAndDOWFields(int n) {
        this.computeGregorianFields(n);
        int n2 = this.fields[7] = Calendar.julianDayToDayOfWeek(n);
        int n3 = n2 - this.getFirstDayOfWeek() + 1;
        if (n3 < 1) {
            n3 += 7;
        }
        this.fields[18] = n3;
    }

    protected final void computeGregorianFields(int n) {
        int n2;
        long l = n - 1721426;
        int[] nArray = new int[1];
        int n3 = Calendar.floorDivide(l, 146097, nArray);
        int n4 = Calendar.floorDivide(nArray[0], 36524, nArray);
        int n5 = Calendar.floorDivide(nArray[0], 1461, nArray);
        int n6 = Calendar.floorDivide(nArray[0], 365, nArray);
        int n7 = 400 * n3 + 100 * n4 + 4 * n5 + n6;
        int n8 = nArray[0];
        if (n4 == 4 || n6 == 4) {
            n8 = 365;
        } else {
            ++n7;
        }
        boolean bl = (n7 & 3) == 0 && (n7 % 100 != 0 || n7 % 400 == 0);
        int n9 = 0;
        int n10 = n2 = bl ? 60 : 59;
        if (n8 >= n2) {
            n9 = bl ? 1 : 2;
        }
        int n11 = (12 * (n8 + n9) + 6) / 367;
        int n12 = n8 - GREGORIAN_MONTH_COUNT[n11][bl ? 3 : 2] + 1;
        this.gregorianYear = n7;
        this.gregorianMonth = n11;
        this.gregorianDayOfMonth = n12;
        this.gregorianDayOfYear = n8 + 1;
    }

    private final void computeWeekFields() {
        int n;
        int n2 = this.fields[19];
        int n3 = this.fields[7];
        int n4 = this.fields[6];
        int n5 = n2;
        int n6 = (n3 + 7 - this.getFirstDayOfWeek()) % 7;
        int n7 = (n3 - n4 + 7001 - this.getFirstDayOfWeek()) % 7;
        int n8 = (n4 - 1 + n7) / 7;
        if (7 - n7 >= this.getMinimalDaysInFirstWeek()) {
            ++n8;
        }
        if (n8 == 0) {
            n = n4 + this.handleGetYearLength(n2 - 1);
            n8 = this.weekNumber(n, n3);
        } else {
            n = this.handleGetYearLength(n2);
            if (n4 >= n - 5) {
                int n9 = (n6 + n - n4) % 7;
                if (n9 < 0) {
                    n9 += 7;
                }
                if (6 - n9 >= this.getMinimalDaysInFirstWeek() && n4 + 7 - n6 > n) {
                    n8 = 1;
                    ++n5;
                }
            }
        }
        this.fields[3] = n8;
        this.fields[17] = --n5;
        n = this.fields[5];
        this.fields[4] = this.weekNumber(n, n3);
        this.fields[8] = (n - 1) / 7 + 1;
    }

    protected int resolveFields(int[][][] nArray) {
        int n = -1;
        for (int i = 0; i < nArray.length && n < 0; ++i) {
            int[][] nArray2 = nArray[i];
            int n2 = 0;
            block1: for (int j = 0; j < nArray2.length; ++j) {
                int n3;
                int[] nArray3 = nArray2[j];
                int n4 = 0;
                int n5 = n3 = nArray3[0] >= 32 ? 1 : 0;
                while (n3 < nArray3.length) {
                    int n6 = this.stamp[nArray3[n3]];
                    if (n6 == 0) continue block1;
                    n4 = Math.max(n4, n6);
                    ++n3;
                }
                if (n4 <= n2) continue;
                int n7 = nArray3[0];
                if (n7 >= 32) {
                    if ((n7 &= 0x1F) != 5 || this.stamp[4] < this.stamp[n7]) {
                        n = n7;
                    }
                } else {
                    n = n7;
                }
                if (n != n7) continue;
                n2 = n4;
            }
        }
        return n >= 32 ? n & 0x1F : n;
    }

    protected int newestStamp(int n, int n2, int n3) {
        int n4 = n3;
        for (int i = n; i <= n2; ++i) {
            if (this.stamp[i] <= n4) continue;
            n4 = this.stamp[i];
        }
        return n4;
    }

    protected final int getStamp(int n) {
        return this.stamp[n];
    }

    protected int newerField(int n, int n2) {
        if (this.stamp[n2] > this.stamp[n]) {
            return n2;
        }
        return n;
    }

    protected void validateFields() {
        for (int i = 0; i < this.fields.length; ++i) {
            if (this.stamp[i] < 2) continue;
            this.validateField(i);
        }
    }

    protected void validateField(int n) {
        switch (n) {
            case 5: {
                int n2 = this.handleGetExtendedYear();
                this.validateField(n, 1, this.handleGetMonthLength(n2, this.internalGet(2)));
                break;
            }
            case 6: {
                int n3 = this.handleGetExtendedYear();
                this.validateField(n, 1, this.handleGetYearLength(n3));
                break;
            }
            case 8: {
                if (this.internalGet(n) == 0) {
                    throw new IllegalArgumentException("DAY_OF_WEEK_IN_MONTH cannot be zero");
                }
                this.validateField(n, this.getMinimum(n), this.getMaximum(n));
                break;
            }
            default: {
                this.validateField(n, this.getMinimum(n), this.getMaximum(n));
            }
        }
    }

    protected final void validateField(int n, int n2, int n3) {
        int n4 = this.fields[n];
        if (n4 < n2 || n4 > n3) {
            throw new IllegalArgumentException(this.fieldName(n) + '=' + n4 + ", valid range=" + n2 + ".." + n3);
        }
    }

    protected void computeTime() {
        int n;
        long l;
        if (!this.isLenient()) {
            this.validateFields();
        }
        int n2 = this.computeJulianDay();
        long l2 = Calendar.julianDayToMillis(n2);
        if (this.stamp[21] >= 2 && this.newestStamp(9, 14, 0) <= this.stamp[21]) {
            l = this.internalGet(21);
        } else {
            n = Math.abs(this.internalGet(11));
            l = (n = Math.max(n, Math.abs(this.internalGet(10)))) > 548 ? this.computeMillisInDayLong() : (long)this.computeMillisInDay();
        }
        if (this.stamp[15] >= 2 || this.stamp[16] >= 2) {
            this.time = l2 + l - (long)(this.internalGet(15) + this.internalGet(16));
        } else if (!this.lenient || this.skippedWallTime == 2) {
            long l3;
            int n3;
            n = this.computeZoneOffset(l2, l);
            if (n != (n3 = this.zone.getOffset(l3 = l2 + l - (long)n))) {
                if (!this.lenient) {
                    throw new IllegalArgumentException("The specified wall time does not exist due to time zone offset transition.");
                }
                if (!$assertionsDisabled && this.skippedWallTime != 2) {
                    throw new AssertionError(this.skippedWallTime);
                }
                Long l4 = this.getImmediatePreviousZoneTransition(l3);
                if (l4 == null) {
                    throw new RuntimeException("Could not locate a time zone transition before " + l3);
                }
                this.time = l4;
            } else {
                this.time = l3;
            }
        } else {
            this.time = l2 + l - (long)this.computeZoneOffset(l2, l);
        }
    }

    private Long getImmediatePreviousZoneTransition(long l) {
        Long l2 = null;
        if (this.zone instanceof BasicTimeZone) {
            TimeZoneTransition timeZoneTransition = ((BasicTimeZone)this.zone).getPreviousTransition(l, false);
            if (timeZoneTransition != null) {
                l2 = timeZoneTransition.getTime();
            }
        } else {
            l2 = Calendar.getPreviousZoneTransitionTime(this.zone, l, 0x6DDD00L);
            if (l2 == null) {
                l2 = Calendar.getPreviousZoneTransitionTime(this.zone, l, 108000000L);
            }
        }
        return l2;
    }

    private static Long getPreviousZoneTransitionTime(TimeZone timeZone, long l, long l2) {
        int n;
        if (!$assertionsDisabled && l2 <= 0L) {
            throw new AssertionError();
        }
        long l3 = l;
        long l4 = l - l2 - 1L;
        int n2 = timeZone.getOffset(l3);
        if (n2 == (n = timeZone.getOffset(l4))) {
            return null;
        }
        return Calendar.findPreviousZoneTransitionTime(timeZone, n2, l3, l4);
    }

    private static Long findPreviousZoneTransitionTime(TimeZone timeZone, int n, long l, long l2) {
        boolean bl = false;
        long l3 = 0L;
        for (int n2 : FIND_ZONE_TRANSITION_TIME_UNITS) {
            long l4 = l / (long)n2;
            long l5 = l2 / (long)n2;
            if (l4 <= l5) continue;
            l3 = (l5 + l4 + 1L >>> 1) * (long)n2;
            bl = true;
            break;
        }
        if (!bl) {
            l3 = l + l2 >>> 1;
        }
        if (bl) {
            if (l3 != l) {
                int n3 = timeZone.getOffset(l3);
                if (n3 != n) {
                    return Calendar.findPreviousZoneTransitionTime(timeZone, n, l, l3);
                }
                l = l3;
            }
            --l3;
        } else {
            l3 = l + l2 >>> 1;
        }
        if (l3 == l2) {
            return l;
        }
        int n4 = timeZone.getOffset(l3);
        if (n4 != n) {
            if (bl) {
                return l;
            }
            return Calendar.findPreviousZoneTransitionTime(timeZone, n, l, l3);
        }
        return Calendar.findPreviousZoneTransitionTime(timeZone, n, l3, l2);
    }

    @Deprecated
    protected int computeMillisInDay() {
        int n;
        int n2 = 0;
        int n3 = this.stamp[11];
        int n4 = Math.max(this.stamp[10], this.stamp[9]);
        int n5 = n = n4 > n3 ? n4 : n3;
        if (n != 0) {
            if (n == n3) {
                n2 += this.internalGet(11);
            } else {
                n2 += this.internalGet(10);
                n2 += 12 * this.internalGet(9);
            }
        }
        n2 *= 60;
        n2 += this.internalGet(12);
        n2 *= 60;
        n2 += this.internalGet(13);
        n2 *= 1000;
        return n2 += this.internalGet(14);
    }

    @Deprecated
    protected long computeMillisInDayLong() {
        int n;
        long l = 0L;
        int n2 = this.stamp[11];
        int n3 = Math.max(this.stamp[10], this.stamp[9]);
        int n4 = n = n3 > n2 ? n3 : n2;
        if (n != 0) {
            if (n == n2) {
                l += (long)this.internalGet(11);
            } else {
                l += (long)this.internalGet(10);
                l += (long)(12 * this.internalGet(9));
            }
        }
        l *= 60L;
        l += (long)this.internalGet(12);
        l *= 60L;
        l += (long)this.internalGet(13);
        l *= 1000L;
        return l += (long)this.internalGet(14);
    }

    @Deprecated
    protected int computeZoneOffset(long l, int n) {
        int[] nArray = new int[2];
        long l2 = l + (long)n;
        if (this.zone instanceof BasicTimeZone) {
            int n2 = this.repeatedWallTime == 1 ? 4 : 12;
            int n3 = this.skippedWallTime == 1 ? 12 : 4;
            ((BasicTimeZone)this.zone).getOffsetFromLocal(l2, n3, n2, nArray);
        } else {
            long l3;
            this.zone.getOffset(l2, true, nArray);
            boolean bl = false;
            if (this.repeatedWallTime == 1) {
                l3 = l2 - (long)(nArray[0] + nArray[1]);
                int n4 = this.zone.getOffset(l3 - 21600000L);
                int n5 = nArray[0] + nArray[1] - n4;
                if (!$assertionsDisabled && n5 <= -21600000) {
                    throw new AssertionError(n5);
                }
                if (n5 < 0) {
                    bl = true;
                    this.zone.getOffset(l2 + (long)n5, true, nArray);
                }
            }
            if (!bl && this.skippedWallTime == 1) {
                l3 = l2 - (long)(nArray[0] + nArray[1]);
                this.zone.getOffset(l3, false, nArray);
            }
        }
        return nArray[0] + nArray[1];
    }

    @Deprecated
    protected int computeZoneOffset(long l, long l2) {
        int[] nArray = new int[2];
        long l3 = l + l2;
        if (this.zone instanceof BasicTimeZone) {
            int n = this.repeatedWallTime == 1 ? 4 : 12;
            int n2 = this.skippedWallTime == 1 ? 12 : 4;
            ((BasicTimeZone)this.zone).getOffsetFromLocal(l3, n2, n, nArray);
        } else {
            long l4;
            this.zone.getOffset(l3, true, nArray);
            boolean bl = false;
            if (this.repeatedWallTime == 1) {
                l4 = l3 - (long)(nArray[0] + nArray[1]);
                int n = this.zone.getOffset(l4 - 21600000L);
                int n3 = nArray[0] + nArray[1] - n;
                if (!$assertionsDisabled && n3 <= -21600000) {
                    throw new AssertionError(n3);
                }
                if (n3 < 0) {
                    bl = true;
                    this.zone.getOffset(l3 + (long)n3, true, nArray);
                }
            }
            if (!bl && this.skippedWallTime == 1) {
                l4 = l3 - (long)(nArray[0] + nArray[1]);
                this.zone.getOffset(l4, false, nArray);
            }
        }
        return nArray[0] + nArray[1];
    }

    protected int computeJulianDay() {
        int n;
        if (this.stamp[20] >= 2) {
            n = this.newestStamp(0, 8, 0);
            if ((n = this.newestStamp(17, 19, n)) <= this.stamp[20]) {
                return this.internalGet(20);
            }
        }
        if ((n = this.resolveFields(this.getFieldResolutionTable())) < 0) {
            n = 5;
        }
        return this.handleComputeJulianDay(n);
    }

    protected int[][][] getFieldResolutionTable() {
        return DATE_PRECEDENCE;
    }

    protected abstract int handleComputeMonthStart(int var1, int var2, boolean var3);

    protected abstract int handleGetExtendedYear();

    protected int handleGetMonthLength(int n, int n2) {
        return this.handleComputeMonthStart(n, n2 + 1, false) - this.handleComputeMonthStart(n, n2, false);
    }

    protected int handleGetYearLength(int n) {
        return this.handleComputeMonthStart(n + 1, 0, true) - this.handleComputeMonthStart(n, 0, true);
    }

    protected int[] handleCreateFields() {
        return new int[23];
    }

    protected int getDefaultMonthInYear(int n) {
        return 1;
    }

    protected int getDefaultDayInMonth(int n, int n2) {
        return 0;
    }

    protected int handleComputeJulianDay(int n) {
        boolean bl = n == 5 || n == 4 || n == 8;
        int n2 = n == 3 && this.newerField(17, 1) == 17 ? this.internalGet(17) : this.handleGetExtendedYear();
        this.internalSet(19, n2);
        int n3 = bl ? this.internalGet(2, this.getDefaultMonthInYear(n2)) : 0;
        int n4 = this.handleComputeMonthStart(n2, n3, bl);
        if (n == 5) {
            if (this.isSet(0)) {
                return n4 + this.internalGet(5, this.getDefaultDayInMonth(n2, n3));
            }
            return n4 + this.getDefaultDayInMonth(n2, n3);
        }
        if (n == 6) {
            return n4 + this.internalGet(6);
        }
        int n5 = this.getFirstDayOfWeek();
        int n6 = Calendar.julianDayToDayOfWeek(n4 + 1) - n5;
        if (n6 < 0) {
            n6 += 7;
        }
        int n7 = 0;
        switch (this.resolveFields(DOW_PRECEDENCE)) {
            case 7: {
                n7 = this.internalGet(7) - n5;
                break;
            }
            case 18: {
                n7 = this.internalGet(18) - 1;
            }
        }
        if ((n7 %= 7) < 0) {
            n7 += 7;
        }
        int n8 = 1 - n6 + n7;
        if (n == 8) {
            int n9;
            if (n8 < 1) {
                n8 += 7;
            }
            if ((n9 = this.internalGet(8, 1)) >= 0) {
                n8 += 7 * (n9 - 1);
            } else {
                int n10 = this.internalGet(2, 0);
                int n11 = this.handleGetMonthLength(n2, n10);
                n8 += ((n11 - n8) / 7 + n9 + 1) * 7;
            }
        } else {
            if (7 - n6 < this.getMinimalDaysInFirstWeek()) {
                n8 += 7;
            }
            n8 += 7 * (this.internalGet(n) - 1);
        }
        return n4 + n8;
    }

    protected int computeGregorianMonthStart(int n, int n2) {
        if (n2 < 0 || n2 > 11) {
            int[] nArray = new int[1];
            n += Calendar.floorDivide(n2, 12, nArray);
            n2 = nArray[0];
        }
        boolean bl = n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
        int n3 = n - 1;
        int n4 = 365 * n3 + Calendar.floorDivide(n3, 4) - Calendar.floorDivide(n3, 100) + Calendar.floorDivide(n3, 400) + 1721426 - 1;
        if (n2 != 0) {
            n4 += GREGORIAN_MONTH_COUNT[n2][bl ? 3 : 2];
        }
        return n4;
    }

    protected void handleComputeFields(int n) {
        this.internalSet(2, this.getGregorianMonth());
        this.internalSet(5, this.getGregorianDayOfMonth());
        this.internalSet(6, this.getGregorianDayOfYear());
        int n2 = this.getGregorianYear();
        this.internalSet(19, n2);
        int n3 = 1;
        if (n2 < 1) {
            n3 = 0;
            n2 = 1 - n2;
        }
        this.internalSet(0, n3);
        this.internalSet(1, n2);
    }

    protected final int getGregorianYear() {
        return this.gregorianYear;
    }

    protected final int getGregorianMonth() {
        return this.gregorianMonth;
    }

    protected final int getGregorianDayOfYear() {
        return this.gregorianDayOfYear;
    }

    protected final int getGregorianDayOfMonth() {
        return this.gregorianDayOfMonth;
    }

    public final int getFieldCount() {
        return this.fields.length;
    }

    protected final void internalSet(int n, int n2) {
        if ((1 << n & this.internalSetMask) == 0) {
            throw new IllegalStateException("Subclass cannot set " + this.fieldName(n));
        }
        this.fields[n] = n2;
        this.stamp[n] = 1;
    }

    protected static final boolean isGregorianLeapYear(int n) {
        return n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
    }

    protected static final int gregorianMonthLength(int n, int n2) {
        return GREGORIAN_MONTH_COUNT[n2][Calendar.isGregorianLeapYear(n) ? 1 : 0];
    }

    protected static final int gregorianPreviousMonthLength(int n, int n2) {
        return n2 > 0 ? Calendar.gregorianMonthLength(n, n2 - 1) : 31;
    }

    protected static final long floorDivide(long l, long l2) {
        return l >= 0L ? l / l2 : (l + 1L) / l2 - 1L;
    }

    protected static final int floorDivide(int n, int n2) {
        return n >= 0 ? n / n2 : (n + 1) / n2 - 1;
    }

    protected static final int floorDivide(int n, int n2, int[] nArray) {
        if (n >= 0) {
            nArray[0] = n % n2;
            return n / n2;
        }
        int n3 = (n + 1) / n2 - 1;
        nArray[0] = n - n3 * n2;
        return n3;
    }

    protected static final int floorDivide(long l, int n, int[] nArray) {
        if (l >= 0L) {
            nArray[0] = (int)(l % (long)n);
            return (int)(l / (long)n);
        }
        int n2 = (int)((l + 1L) / (long)n - 1L);
        nArray[0] = (int)(l - (long)n2 * (long)n);
        return n2;
    }

    protected String fieldName(int n) {
        try {
            return FIELD_NAME[n];
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return "Field " + n;
        }
    }

    protected static final int millisToJulianDay(long l) {
        return (int)(2440588L + Calendar.floorDivide(l, 86400000L));
    }

    protected static final long julianDayToMillis(int n) {
        return (long)(n - 2440588) * 86400000L;
    }

    protected static final int julianDayToDayOfWeek(int n) {
        int n2 = (n + 2) % 7;
        if (n2 < 1) {
            n2 += 7;
        }
        return n2;
    }

    protected final long internalGetTimeInMillis() {
        return this.time;
    }

    public String getType() {
        return "unknown";
    }

    @Deprecated
    public boolean haveDefaultCentury() {
        return false;
    }

    public final ULocale getLocale(ULocale.Type type) {
        return type == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
    }

    final void setLocale(ULocale uLocale, ULocale uLocale2) {
        if (uLocale == null != (uLocale2 == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = uLocale;
        this.actualLocale = uLocale2;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Calendar)object);
    }

    static ICUCache access$1000() {
        return PATTERN_CACHE;
    }

    static PatternData access$1100(ULocale uLocale, String string) {
        return Calendar.getPatternData(uLocale, string);
    }

    static String[] access$1200() {
        return DEFAULT_PATTERNS;
    }

    static WeekData access$1300(String string) {
        return Calendar.getWeekDataForRegionInternal(string);
    }

    static {
        $assertionsDisabled = !Calendar.class.desiredAssertionStatus();
        MIN_DATE = new Date(-184303902528000000L);
        MAX_DATE = new Date(183882168921600000L);
        STAMP_MAX = 10000;
        PATTERN_CACHE = new SimpleCache<String, PatternData>();
        DEFAULT_PATTERNS = new String[]{"HH:mm:ss z", "HH:mm:ss z", "HH:mm:ss", "HH:mm", "EEEE, yyyy MMMM dd", "yyyy MMMM d", "yyyy MMM d", "yy/MM/dd", "{1} {0}", "{1} {0}", "{1} {0}", "{1} {0}", "{1} {0}"};
        LIMITS = new int[][]{new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], {1, 1, 7, 7}, new int[0], {0, 0, 1, 1}, {0, 0, 11, 11}, {0, 0, 23, 23}, {0, 0, 59, 59}, {0, 0, 59, 59}, {0, 0, 999, 999}, {-43200000, -43200000, 43200000, 43200000}, {0, 0, 3600000, 3600000}, new int[0], {1, 1, 7, 7}, new int[0], {-2130706432, -2130706432, 0x7F000000, 0x7F000000}, {0, 0, 86399999, 86399999}, {0, 0, 1, 1}};
        WEEK_DATA_CACHE = new WeekDataCache(null);
        DATE_PRECEDENCE = new int[][][]{new int[][]{{5}, {3, 7}, {4, 7}, {8, 7}, {3, 18}, {4, 18}, {8, 18}, {6}, {37, 1}, {35, 17}}, new int[][]{{3}, {4}, {8}, {40, 7}, {40, 18}}};
        DOW_PRECEDENCE = new int[][][]{new int[][]{{7}, {18}}};
        FIND_ZONE_TRANSITION_TIME_UNITS = new int[]{3600000, 1800000, 60000, 1000};
        GREGORIAN_MONTH_COUNT = new int[][]{{31, 31, 0, 0}, {28, 29, 31, 31}, {31, 31, 59, 60}, {30, 30, 90, 91}, {31, 31, 120, 121}, {30, 30, 151, 152}, {31, 31, 181, 182}, {31, 31, 212, 213}, {30, 30, 243, 244}, {31, 31, 273, 274}, {30, 30, 304, 305}, {31, 31, 334, 335}};
        FIELD_NAME = new String[]{"ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH", "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR", "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET", "DST_OFFSET", "YEAR_WOY", "DOW_LOCAL", "EXTENDED_YEAR", "JULIAN_DAY", "MILLISECONDS_IN_DAY"};
    }

    private static class WeekDataCache
    extends SoftCache<String, WeekData, String> {
        private WeekDataCache() {
        }

        @Override
        protected WeekData createInstance(String string, String string2) {
            return Calendar.access$1300(string);
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (String)object2);
        }

        WeekDataCache(1 var1_1) {
            this();
        }
    }

    public static final class WeekData {
        public final int firstDayOfWeek;
        public final int minimalDaysInFirstWeek;
        public final int weekendOnset;
        public final int weekendOnsetMillis;
        public final int weekendCease;
        public final int weekendCeaseMillis;

        public WeekData(int n, int n2, int n3, int n4, int n5, int n6) {
            this.firstDayOfWeek = n;
            this.minimalDaysInFirstWeek = n2;
            this.weekendOnset = n3;
            this.weekendOnsetMillis = n4;
            this.weekendCease = n5;
            this.weekendCeaseMillis = n6;
        }

        public int hashCode() {
            return ((((this.firstDayOfWeek * 37 + this.minimalDaysInFirstWeek) * 37 + this.weekendOnset) * 37 + this.weekendOnsetMillis) * 37 + this.weekendCease) * 37 + this.weekendCeaseMillis;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof WeekData)) {
                return true;
            }
            WeekData weekData = (WeekData)object;
            return this.firstDayOfWeek == weekData.firstDayOfWeek && this.minimalDaysInFirstWeek == weekData.minimalDaysInFirstWeek && this.weekendOnset == weekData.weekendOnset && this.weekendOnsetMillis == weekData.weekendOnsetMillis && this.weekendCease == weekData.weekendCease && this.weekendCeaseMillis == weekData.weekendCeaseMillis;
        }

        public String toString() {
            return "{" + this.firstDayOfWeek + ", " + this.minimalDaysInFirstWeek + ", " + this.weekendOnset + ", " + this.weekendOnsetMillis + ", " + this.weekendCease + ", " + this.weekendCeaseMillis + "}";
        }
    }

    @Deprecated
    public static class FormatConfiguration {
        private String pattern;
        private String override;
        private DateFormatSymbols formatData;
        private Calendar cal;
        private ULocale loc;

        private FormatConfiguration() {
        }

        @Deprecated
        public String getPatternString() {
            return this.pattern;
        }

        @Deprecated
        public String getOverrideString() {
            return this.override;
        }

        @Deprecated
        public Calendar getCalendar() {
            return this.cal;
        }

        @Deprecated
        public ULocale getLocale() {
            return this.loc;
        }

        @Deprecated
        public DateFormatSymbols getDateFormatSymbols() {
            return this.formatData;
        }

        FormatConfiguration(1 var1_1) {
            this();
        }

        static String access$102(FormatConfiguration formatConfiguration, String string) {
            formatConfiguration.pattern = string;
            return formatConfiguration.pattern;
        }

        static String access$202(FormatConfiguration formatConfiguration, String string) {
            formatConfiguration.override = string;
            return formatConfiguration.override;
        }

        static DateFormatSymbols access$302(FormatConfiguration formatConfiguration, DateFormatSymbols dateFormatSymbols) {
            formatConfiguration.formatData = dateFormatSymbols;
            return formatConfiguration.formatData;
        }

        static ULocale access$402(FormatConfiguration formatConfiguration, ULocale uLocale) {
            formatConfiguration.loc = uLocale;
            return formatConfiguration.loc;
        }

        static Calendar access$502(FormatConfiguration formatConfiguration, Calendar calendar) {
            formatConfiguration.cal = calendar;
            return formatConfiguration.cal;
        }
    }

    static class PatternData {
        private String[] patterns;
        private String[] overrides;

        public PatternData(String[] stringArray, String[] stringArray2) {
            this.patterns = stringArray;
            this.overrides = stringArray2;
        }

        private String getDateTimePattern(int n) {
            int n2 = 8;
            if (this.patterns.length >= 13) {
                n2 += n + 1;
            }
            String string = this.patterns[n2];
            return string;
        }

        private static PatternData make(Calendar calendar, ULocale uLocale) {
            String string = calendar.getType();
            String string2 = uLocale.getBaseName() + "+" + string;
            PatternData patternData = (PatternData)Calendar.access$1000().get(string2);
            if (patternData == null) {
                try {
                    patternData = Calendar.access$1100(uLocale, string);
                } catch (MissingResourceException missingResourceException) {
                    patternData = new PatternData(Calendar.access$1200(), null);
                }
                Calendar.access$1000().put(string2, patternData);
            }
            return patternData;
        }

        static PatternData access$600(Calendar calendar, ULocale uLocale) {
            return PatternData.make(calendar, uLocale);
        }

        static String access$700(PatternData patternData, int n) {
            return patternData.getDateTimePattern(n);
        }

        static String[] access$800(PatternData patternData) {
            return patternData.patterns;
        }

        static String[] access$900(PatternData patternData) {
            return patternData.overrides;
        }
    }
}

