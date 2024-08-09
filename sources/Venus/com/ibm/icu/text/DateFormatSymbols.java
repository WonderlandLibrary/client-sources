/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.CalendarUtil;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class DateFormatSymbols
implements Serializable,
Cloneable {
    public static final int FORMAT = 0;
    public static final int STANDALONE = 1;
    @Deprecated
    public static final int NUMERIC = 2;
    @Deprecated
    public static final int DT_CONTEXT_COUNT = 3;
    public static final int ABBREVIATED = 0;
    public static final int WIDE = 1;
    public static final int NARROW = 2;
    public static final int SHORT = 3;
    @Deprecated
    public static final int DT_WIDTH_COUNT = 4;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_WIDE = 0;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_ABBREV = 1;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_NARROW = 2;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_WIDE = 3;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_ABBREV = 4;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_NARROW = 5;
    static final int DT_LEAP_MONTH_PATTERN_NUMERIC = 6;
    static final int DT_MONTH_PATTERN_COUNT = 7;
    static final String DEFAULT_TIME_SEPARATOR = ":";
    static final String ALTERNATE_TIME_SEPARATOR = ".";
    String[] eras = null;
    String[] eraNames = null;
    String[] narrowEras = null;
    String[] months = null;
    String[] shortMonths = null;
    String[] narrowMonths = null;
    String[] standaloneMonths = null;
    String[] standaloneShortMonths = null;
    String[] standaloneNarrowMonths = null;
    String[] weekdays = null;
    String[] shortWeekdays = null;
    String[] shorterWeekdays = null;
    String[] narrowWeekdays = null;
    String[] standaloneWeekdays = null;
    String[] standaloneShortWeekdays = null;
    String[] standaloneShorterWeekdays = null;
    String[] standaloneNarrowWeekdays = null;
    String[] ampms = null;
    String[] ampmsNarrow = null;
    private String timeSeparator = null;
    String[] shortQuarters = null;
    String[] quarters = null;
    String[] standaloneShortQuarters = null;
    String[] standaloneQuarters = null;
    String[] leapMonthPatterns = null;
    String[] shortYearNames = null;
    String[] shortZodiacNames = null;
    private String[][] zoneStrings = null;
    static final String patternChars = "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB";
    String localPatternChars = null;
    String[] abbreviatedDayPeriods = null;
    String[] wideDayPeriods = null;
    String[] narrowDayPeriods = null;
    String[] standaloneAbbreviatedDayPeriods = null;
    String[] standaloneWideDayPeriods = null;
    String[] standaloneNarrowDayPeriods = null;
    private static final long serialVersionUID = -5987973545549424702L;
    private static final String[][] CALENDAR_CLASSES = new String[][]{{"GregorianCalendar", "gregorian"}, {"JapaneseCalendar", "japanese"}, {"BuddhistCalendar", "buddhist"}, {"TaiwanCalendar", "roc"}, {"PersianCalendar", "persian"}, {"IslamicCalendar", "islamic"}, {"HebrewCalendar", "hebrew"}, {"ChineseCalendar", "chinese"}, {"IndianCalendar", "indian"}, {"CopticCalendar", "coptic"}, {"EthiopicCalendar", "ethiopic"}};
    private static final Map<String, CapitalizationContextUsage> contextUsageTypeMap = new HashMap<String, CapitalizationContextUsage>();
    Map<CapitalizationContextUsage, boolean[]> capitalization = null;
    static final int millisPerHour = 3600000;
    private static CacheBase<String, DateFormatSymbols, ULocale> DFSCACHE;
    private static final String[] LEAP_MONTH_PATTERNS_PATHS;
    private static final String[] DAY_PERIOD_KEYS;
    private ULocale requestedLocale;
    private ULocale validLocale;
    private ULocale actualLocale;

    public DateFormatSymbols() {
        this(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public DateFormatSymbols(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    public DateFormatSymbols(ULocale uLocale) {
        this.initializeData(uLocale, CalendarUtil.getCalendarType(uLocale));
    }

    public static DateFormatSymbols getInstance() {
        return new DateFormatSymbols();
    }

    public static DateFormatSymbols getInstance(Locale locale) {
        return new DateFormatSymbols(locale);
    }

    public static DateFormatSymbols getInstance(ULocale uLocale) {
        return new DateFormatSymbols(uLocale);
    }

    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    public String[] getEras() {
        return this.duplicate(this.eras);
    }

    public void setEras(String[] stringArray) {
        this.eras = this.duplicate(stringArray);
    }

    public String[] getEraNames() {
        return this.duplicate(this.eraNames);
    }

    public void setEraNames(String[] stringArray) {
        this.eraNames = this.duplicate(stringArray);
    }

    public String[] getNarrowEras() {
        return this.duplicate(this.narrowEras);
    }

    public void setNarrowEras(String[] stringArray) {
        this.narrowEras = this.duplicate(stringArray);
    }

    public String[] getMonths() {
        return this.duplicate(this.months);
    }

    public String[] getMonths(int n, int n2) {
        String[] stringArray = null;
        block0 : switch (n) {
            case 0: {
                switch (n2) {
                    case 1: {
                        stringArray = this.months;
                        break;
                    }
                    case 0: 
                    case 3: {
                        stringArray = this.shortMonths;
                        break;
                    }
                    case 2: {
                        stringArray = this.narrowMonths;
                    }
                }
                break;
            }
            case 1: {
                switch (n2) {
                    case 1: {
                        stringArray = this.standaloneMonths;
                        break block0;
                    }
                    case 0: 
                    case 3: {
                        stringArray = this.standaloneShortMonths;
                        break block0;
                    }
                    case 2: {
                        stringArray = this.standaloneNarrowMonths;
                    }
                }
            }
        }
        if (stringArray == null) {
            throw new IllegalArgumentException("Bad context or width argument");
        }
        return this.duplicate(stringArray);
    }

    public void setMonths(String[] stringArray) {
        this.months = this.duplicate(stringArray);
    }

    public void setMonths(String[] stringArray, int n, int n2) {
        block0 : switch (n) {
            case 0: {
                switch (n2) {
                    case 1: {
                        this.months = this.duplicate(stringArray);
                        break block0;
                    }
                    case 0: {
                        this.shortMonths = this.duplicate(stringArray);
                        break block0;
                    }
                    case 2: {
                        this.narrowMonths = this.duplicate(stringArray);
                        break block0;
                    }
                }
                break;
            }
            case 1: {
                switch (n2) {
                    case 1: {
                        this.standaloneMonths = this.duplicate(stringArray);
                        break block0;
                    }
                    case 0: {
                        this.standaloneShortMonths = this.duplicate(stringArray);
                        break block0;
                    }
                    case 2: {
                        this.standaloneNarrowMonths = this.duplicate(stringArray);
                        break block0;
                    }
                }
            }
        }
    }

    public String[] getShortMonths() {
        return this.duplicate(this.shortMonths);
    }

    public void setShortMonths(String[] stringArray) {
        this.shortMonths = this.duplicate(stringArray);
    }

    public String[] getWeekdays() {
        return this.duplicate(this.weekdays);
    }

    public String[] getWeekdays(int n, int n2) {
        String[] stringArray = null;
        block0 : switch (n) {
            case 0: {
                switch (n2) {
                    case 1: {
                        stringArray = this.weekdays;
                        break;
                    }
                    case 0: {
                        stringArray = this.shortWeekdays;
                        break;
                    }
                    case 3: {
                        stringArray = this.shorterWeekdays != null ? this.shorterWeekdays : this.shortWeekdays;
                        break;
                    }
                    case 2: {
                        stringArray = this.narrowWeekdays;
                    }
                }
                break;
            }
            case 1: {
                switch (n2) {
                    case 1: {
                        stringArray = this.standaloneWeekdays;
                        break block0;
                    }
                    case 0: {
                        stringArray = this.standaloneShortWeekdays;
                        break block0;
                    }
                    case 3: {
                        stringArray = this.standaloneShorterWeekdays != null ? this.standaloneShorterWeekdays : this.standaloneShortWeekdays;
                        break block0;
                    }
                    case 2: {
                        stringArray = this.standaloneNarrowWeekdays;
                    }
                }
            }
        }
        if (stringArray == null) {
            throw new IllegalArgumentException("Bad context or width argument");
        }
        return this.duplicate(stringArray);
    }

    public void setWeekdays(String[] stringArray, int n, int n2) {
        block0 : switch (n) {
            case 0: {
                switch (n2) {
                    case 1: {
                        this.weekdays = this.duplicate(stringArray);
                        break;
                    }
                    case 0: {
                        this.shortWeekdays = this.duplicate(stringArray);
                        break;
                    }
                    case 3: {
                        this.shorterWeekdays = this.duplicate(stringArray);
                        break;
                    }
                    case 2: {
                        this.narrowWeekdays = this.duplicate(stringArray);
                    }
                }
                break;
            }
            case 1: {
                switch (n2) {
                    case 1: {
                        this.standaloneWeekdays = this.duplicate(stringArray);
                        break block0;
                    }
                    case 0: {
                        this.standaloneShortWeekdays = this.duplicate(stringArray);
                        break block0;
                    }
                    case 3: {
                        this.standaloneShorterWeekdays = this.duplicate(stringArray);
                        break block0;
                    }
                    case 2: {
                        this.standaloneNarrowWeekdays = this.duplicate(stringArray);
                    }
                }
            }
        }
    }

    public void setWeekdays(String[] stringArray) {
        this.weekdays = this.duplicate(stringArray);
    }

    public String[] getShortWeekdays() {
        return this.duplicate(this.shortWeekdays);
    }

    public void setShortWeekdays(String[] stringArray) {
        this.shortWeekdays = this.duplicate(stringArray);
    }

    public String[] getQuarters(int n, int n2) {
        String[] stringArray = null;
        block0 : switch (n) {
            case 0: {
                switch (n2) {
                    case 1: {
                        stringArray = this.quarters;
                        break;
                    }
                    case 0: 
                    case 3: {
                        stringArray = this.shortQuarters;
                        break;
                    }
                    case 2: {
                        stringArray = null;
                    }
                }
                break;
            }
            case 1: {
                switch (n2) {
                    case 1: {
                        stringArray = this.standaloneQuarters;
                        break block0;
                    }
                    case 0: 
                    case 3: {
                        stringArray = this.standaloneShortQuarters;
                        break block0;
                    }
                    case 2: {
                        stringArray = null;
                    }
                }
            }
        }
        if (stringArray == null) {
            throw new IllegalArgumentException("Bad context or width argument");
        }
        return this.duplicate(stringArray);
    }

    public void setQuarters(String[] stringArray, int n, int n2) {
        block0 : switch (n) {
            case 0: {
                switch (n2) {
                    case 1: {
                        this.quarters = this.duplicate(stringArray);
                        break block0;
                    }
                    case 0: {
                        this.shortQuarters = this.duplicate(stringArray);
                        break block0;
                    }
                    case 2: {
                        break block0;
                    }
                }
                break;
            }
            case 1: {
                switch (n2) {
                    case 1: {
                        this.standaloneQuarters = this.duplicate(stringArray);
                        break block0;
                    }
                    case 0: {
                        this.standaloneShortQuarters = this.duplicate(stringArray);
                        break block0;
                    }
                    case 2: {
                        break block0;
                    }
                }
            }
        }
    }

    public String[] getYearNames(int n, int n2) {
        if (this.shortYearNames != null) {
            return this.duplicate(this.shortYearNames);
        }
        return null;
    }

    public void setYearNames(String[] stringArray, int n, int n2) {
        if (n == 0 && n2 == 0) {
            this.shortYearNames = this.duplicate(stringArray);
        }
    }

    public String[] getZodiacNames(int n, int n2) {
        if (this.shortZodiacNames != null) {
            return this.duplicate(this.shortZodiacNames);
        }
        return null;
    }

    public void setZodiacNames(String[] stringArray, int n, int n2) {
        if (n == 0 && n2 == 0) {
            this.shortZodiacNames = this.duplicate(stringArray);
        }
    }

    @Deprecated
    public String getLeapMonthPattern(int n, int n2) {
        if (this.leapMonthPatterns != null) {
            int n3 = -1;
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 1: {
                            n3 = 0;
                            break;
                        }
                        case 0: 
                        case 3: {
                            n3 = 1;
                            break;
                        }
                        case 2: {
                            n3 = 2;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 1: {
                            n3 = 3;
                            break;
                        }
                        case 0: 
                        case 3: {
                            n3 = 1;
                            break;
                        }
                        case 2: {
                            n3 = 5;
                        }
                    }
                    break;
                }
                case 2: {
                    n3 = 6;
                }
            }
            if (n3 < 0) {
                throw new IllegalArgumentException("Bad context or width argument");
            }
            return this.leapMonthPatterns[n3];
        }
        return null;
    }

    @Deprecated
    public void setLeapMonthPattern(String string, int n, int n2) {
        if (this.leapMonthPatterns != null) {
            int n3 = -1;
            block0 : switch (n) {
                case 0: {
                    switch (n2) {
                        case 1: {
                            n3 = 0;
                            break block0;
                        }
                        case 0: {
                            n3 = 1;
                            break block0;
                        }
                        case 2: {
                            n3 = 2;
                            break block0;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 1: {
                            n3 = 3;
                            break block0;
                        }
                        case 0: {
                            n3 = 1;
                            break block0;
                        }
                        case 2: {
                            n3 = 5;
                            break block0;
                        }
                    }
                    break;
                }
                case 2: {
                    n3 = 6;
                    break;
                }
            }
            if (n3 >= 0) {
                this.leapMonthPatterns[n3] = string;
            }
        }
    }

    public String[] getAmPmStrings() {
        return this.duplicate(this.ampms);
    }

    public void setAmPmStrings(String[] stringArray) {
        this.ampms = this.duplicate(stringArray);
    }

    @Deprecated
    public String getTimeSeparatorString() {
        return this.timeSeparator;
    }

    @Deprecated
    public void setTimeSeparatorString(String string) {
        this.timeSeparator = string;
    }

    public String[][] getZoneStrings() {
        if (this.zoneStrings != null) {
            return this.duplicate(this.zoneStrings);
        }
        String[] stringArray = TimeZone.getAvailableIDs();
        TimeZoneNames timeZoneNames = TimeZoneNames.getInstance(this.validLocale);
        timeZoneNames.loadAllDisplayNames();
        TimeZoneNames.NameType[] nameTypeArray = new TimeZoneNames.NameType[]{TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_DAYLIGHT};
        long l = System.currentTimeMillis();
        String[][] stringArray2 = new String[stringArray.length][5];
        for (int i = 0; i < stringArray.length; ++i) {
            String string = TimeZone.getCanonicalID(stringArray[i]);
            if (string == null) {
                string = stringArray[i];
            }
            stringArray2[i][0] = stringArray[i];
            timeZoneNames.getDisplayNames(string, nameTypeArray, l, stringArray2[i], 0);
        }
        this.zoneStrings = stringArray2;
        return this.zoneStrings;
    }

    public void setZoneStrings(String[][] stringArray) {
        this.zoneStrings = this.duplicate(stringArray);
    }

    public String getLocalPatternChars() {
        return this.localPatternChars;
    }

    public void setLocalPatternChars(String string) {
        this.localPatternChars = string;
    }

    public Object clone() {
        try {
            DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)super.clone();
            return dateFormatSymbols;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public int hashCode() {
        return this.requestedLocale.toString().hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)object;
        return Utility.arrayEquals(this.eras, (Object)dateFormatSymbols.eras) && Utility.arrayEquals(this.eraNames, (Object)dateFormatSymbols.eraNames) && Utility.arrayEquals(this.narrowEras, (Object)dateFormatSymbols.narrowEras) && Utility.arrayEquals(this.months, (Object)dateFormatSymbols.months) && Utility.arrayEquals(this.shortMonths, (Object)dateFormatSymbols.shortMonths) && Utility.arrayEquals(this.narrowMonths, (Object)dateFormatSymbols.narrowMonths) && Utility.arrayEquals(this.standaloneMonths, (Object)dateFormatSymbols.standaloneMonths) && Utility.arrayEquals(this.standaloneShortMonths, (Object)dateFormatSymbols.standaloneShortMonths) && Utility.arrayEquals(this.standaloneNarrowMonths, (Object)dateFormatSymbols.standaloneNarrowMonths) && Utility.arrayEquals(this.weekdays, (Object)dateFormatSymbols.weekdays) && Utility.arrayEquals(this.shortWeekdays, (Object)dateFormatSymbols.shortWeekdays) && Utility.arrayEquals(this.shorterWeekdays, (Object)dateFormatSymbols.shorterWeekdays) && Utility.arrayEquals(this.narrowWeekdays, (Object)dateFormatSymbols.narrowWeekdays) && Utility.arrayEquals(this.standaloneWeekdays, (Object)dateFormatSymbols.standaloneWeekdays) && Utility.arrayEquals(this.standaloneShortWeekdays, (Object)dateFormatSymbols.standaloneShortWeekdays) && Utility.arrayEquals(this.standaloneShorterWeekdays, (Object)dateFormatSymbols.standaloneShorterWeekdays) && Utility.arrayEquals(this.standaloneNarrowWeekdays, (Object)dateFormatSymbols.standaloneNarrowWeekdays) && Utility.arrayEquals(this.ampms, (Object)dateFormatSymbols.ampms) && Utility.arrayEquals(this.ampmsNarrow, (Object)dateFormatSymbols.ampmsNarrow) && Utility.arrayEquals(this.abbreviatedDayPeriods, (Object)dateFormatSymbols.abbreviatedDayPeriods) && Utility.arrayEquals(this.wideDayPeriods, (Object)dateFormatSymbols.wideDayPeriods) && Utility.arrayEquals(this.narrowDayPeriods, (Object)dateFormatSymbols.narrowDayPeriods) && Utility.arrayEquals(this.standaloneAbbreviatedDayPeriods, (Object)dateFormatSymbols.standaloneAbbreviatedDayPeriods) && Utility.arrayEquals(this.standaloneWideDayPeriods, (Object)dateFormatSymbols.standaloneWideDayPeriods) && Utility.arrayEquals(this.standaloneNarrowDayPeriods, (Object)dateFormatSymbols.standaloneNarrowDayPeriods) && Utility.arrayEquals(this.timeSeparator, (Object)dateFormatSymbols.timeSeparator) && DateFormatSymbols.arrayOfArrayEquals(this.zoneStrings, dateFormatSymbols.zoneStrings) && this.requestedLocale.getDisplayName().equals(dateFormatSymbols.requestedLocale.getDisplayName()) && Utility.arrayEquals(this.localPatternChars, (Object)dateFormatSymbols.localPatternChars);
    }

    protected void initializeData(ULocale uLocale, String string) {
        String string2 = uLocale.getBaseName() + '+' + string;
        String string3 = uLocale.getKeywordValue("numbers");
        if (string3 != null && string3.length() > 0) {
            string2 = string2 + '+' + string3;
        }
        DateFormatSymbols dateFormatSymbols = DFSCACHE.getInstance(string2, uLocale);
        this.initializeData(dateFormatSymbols);
    }

    void initializeData(DateFormatSymbols dateFormatSymbols) {
        this.eras = dateFormatSymbols.eras;
        this.eraNames = dateFormatSymbols.eraNames;
        this.narrowEras = dateFormatSymbols.narrowEras;
        this.months = dateFormatSymbols.months;
        this.shortMonths = dateFormatSymbols.shortMonths;
        this.narrowMonths = dateFormatSymbols.narrowMonths;
        this.standaloneMonths = dateFormatSymbols.standaloneMonths;
        this.standaloneShortMonths = dateFormatSymbols.standaloneShortMonths;
        this.standaloneNarrowMonths = dateFormatSymbols.standaloneNarrowMonths;
        this.weekdays = dateFormatSymbols.weekdays;
        this.shortWeekdays = dateFormatSymbols.shortWeekdays;
        this.shorterWeekdays = dateFormatSymbols.shorterWeekdays;
        this.narrowWeekdays = dateFormatSymbols.narrowWeekdays;
        this.standaloneWeekdays = dateFormatSymbols.standaloneWeekdays;
        this.standaloneShortWeekdays = dateFormatSymbols.standaloneShortWeekdays;
        this.standaloneShorterWeekdays = dateFormatSymbols.standaloneShorterWeekdays;
        this.standaloneNarrowWeekdays = dateFormatSymbols.standaloneNarrowWeekdays;
        this.ampms = dateFormatSymbols.ampms;
        this.ampmsNarrow = dateFormatSymbols.ampmsNarrow;
        this.timeSeparator = dateFormatSymbols.timeSeparator;
        this.shortQuarters = dateFormatSymbols.shortQuarters;
        this.quarters = dateFormatSymbols.quarters;
        this.standaloneShortQuarters = dateFormatSymbols.standaloneShortQuarters;
        this.standaloneQuarters = dateFormatSymbols.standaloneQuarters;
        this.leapMonthPatterns = dateFormatSymbols.leapMonthPatterns;
        this.shortYearNames = dateFormatSymbols.shortYearNames;
        this.shortZodiacNames = dateFormatSymbols.shortZodiacNames;
        this.abbreviatedDayPeriods = dateFormatSymbols.abbreviatedDayPeriods;
        this.wideDayPeriods = dateFormatSymbols.wideDayPeriods;
        this.narrowDayPeriods = dateFormatSymbols.narrowDayPeriods;
        this.standaloneAbbreviatedDayPeriods = dateFormatSymbols.standaloneAbbreviatedDayPeriods;
        this.standaloneWideDayPeriods = dateFormatSymbols.standaloneWideDayPeriods;
        this.standaloneNarrowDayPeriods = dateFormatSymbols.standaloneNarrowDayPeriods;
        this.zoneStrings = dateFormatSymbols.zoneStrings;
        this.localPatternChars = dateFormatSymbols.localPatternChars;
        this.capitalization = dateFormatSymbols.capitalization;
        this.actualLocale = dateFormatSymbols.actualLocale;
        this.validLocale = dateFormatSymbols.validLocale;
        this.requestedLocale = dateFormatSymbols.requestedLocale;
    }

    private DateFormatSymbols(ULocale uLocale, ICUResourceBundle iCUResourceBundle, String string) {
        this.initializeData(uLocale, iCUResourceBundle, string);
    }

    @Deprecated
    protected void initializeData(ULocale uLocale, ICUResourceBundle iCUResourceBundle, String string) {
        NumberingSystem numberingSystem;
        Object object5;
        CapitalizationContextUsage[] capitalizationContextUsageArray;
        Object object2;
        Object object3;
        Object object4;
        CalendarDataSink calendarDataSink = new CalendarDataSink();
        if (iCUResourceBundle == null) {
            iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        }
        while (string != null) {
            object4 = iCUResourceBundle.findWithFallback("calendar/" + string);
            if (object4 == null) {
                if (!"gregorian".equals(string)) {
                    string = "gregorian";
                    calendarDataSink.visitAllResources();
                    continue;
                }
                throw new MissingResourceException("The 'gregorian' calendar type wasn't found for the locale: " + uLocale.getBaseName(), this.getClass().getName(), "gregorian");
            }
            calendarDataSink.preEnumerate(string);
            ((ICUResourceBundle)object4).getAllItemsWithFallback("", calendarDataSink);
            if (string.equals("gregorian")) break;
            string = calendarDataSink.nextCalendarType;
            if (string != null) continue;
            string = "gregorian";
            calendarDataSink.visitAllResources();
        }
        object4 = calendarDataSink.arrays;
        Map<String, Map<String, String>> map = calendarDataSink.maps;
        this.eras = object4.get("eras/abbreviated");
        this.eraNames = (String[])object4.get("eras/wide");
        this.narrowEras = (String[])object4.get("eras/narrow");
        this.months = (String[])object4.get("monthNames/format/wide");
        this.shortMonths = (String[])object4.get("monthNames/format/abbreviated");
        this.narrowMonths = (String[])object4.get("monthNames/format/narrow");
        this.standaloneMonths = (String[])object4.get("monthNames/stand-alone/wide");
        this.standaloneShortMonths = (String[])object4.get("monthNames/stand-alone/abbreviated");
        this.standaloneNarrowMonths = (String[])object4.get("monthNames/stand-alone/narrow");
        String[] stringArray = (String[])object4.get("dayNames/format/wide");
        this.weekdays = new String[8];
        this.weekdays[0] = "";
        System.arraycopy(stringArray, 0, this.weekdays, 1, stringArray.length);
        String[] stringArray2 = (String[])object4.get("dayNames/format/abbreviated");
        this.shortWeekdays = new String[8];
        this.shortWeekdays[0] = "";
        System.arraycopy(stringArray2, 0, this.shortWeekdays, 1, stringArray2.length);
        String[] stringArray3 = (String[])object4.get("dayNames/format/short");
        this.shorterWeekdays = new String[8];
        this.shorterWeekdays[0] = "";
        System.arraycopy(stringArray3, 0, this.shorterWeekdays, 1, stringArray3.length);
        String[] stringArray4 = (String[])object4.get("dayNames/format/narrow");
        if (stringArray4 == null && (stringArray4 = (String[])object4.get("dayNames/stand-alone/narrow")) == null && (stringArray4 = (String[])object4.get("dayNames/format/abbreviated")) == null) {
            throw new MissingResourceException("Resource not found", this.getClass().getName(), "dayNames/format/abbreviated");
        }
        this.narrowWeekdays = new String[8];
        this.narrowWeekdays[0] = "";
        System.arraycopy(stringArray4, 0, this.narrowWeekdays, 1, stringArray4.length);
        String[] stringArray5 = null;
        stringArray5 = (String[])object4.get("dayNames/stand-alone/wide");
        this.standaloneWeekdays = new String[8];
        this.standaloneWeekdays[0] = "";
        System.arraycopy(stringArray5, 0, this.standaloneWeekdays, 1, stringArray5.length);
        String[] stringArray6 = null;
        stringArray6 = (String[])object4.get("dayNames/stand-alone/abbreviated");
        this.standaloneShortWeekdays = new String[8];
        this.standaloneShortWeekdays[0] = "";
        System.arraycopy(stringArray6, 0, this.standaloneShortWeekdays, 1, stringArray6.length);
        String[] stringArray7 = null;
        stringArray7 = (String[])object4.get("dayNames/stand-alone/short");
        this.standaloneShorterWeekdays = new String[8];
        this.standaloneShorterWeekdays[0] = "";
        System.arraycopy(stringArray7, 0, this.standaloneShorterWeekdays, 1, stringArray7.length);
        String[] stringArray8 = null;
        stringArray8 = (String[])object4.get("dayNames/stand-alone/narrow");
        this.standaloneNarrowWeekdays = new String[8];
        this.standaloneNarrowWeekdays[0] = "";
        System.arraycopy(stringArray8, 0, this.standaloneNarrowWeekdays, 1, stringArray8.length);
        this.ampms = (String[])object4.get("AmPmMarkers");
        this.ampmsNarrow = (String[])object4.get("AmPmMarkersNarrow");
        this.quarters = (String[])object4.get("quarters/format/wide");
        this.shortQuarters = (String[])object4.get("quarters/format/abbreviated");
        this.standaloneQuarters = (String[])object4.get("quarters/stand-alone/wide");
        this.standaloneShortQuarters = (String[])object4.get("quarters/stand-alone/abbreviated");
        this.abbreviatedDayPeriods = this.loadDayPeriodStrings(map.get("dayPeriod/format/abbreviated"));
        this.wideDayPeriods = this.loadDayPeriodStrings(map.get("dayPeriod/format/wide"));
        this.narrowDayPeriods = this.loadDayPeriodStrings(map.get("dayPeriod/format/narrow"));
        this.standaloneAbbreviatedDayPeriods = this.loadDayPeriodStrings(map.get("dayPeriod/stand-alone/abbreviated"));
        this.standaloneWideDayPeriods = this.loadDayPeriodStrings(map.get("dayPeriod/stand-alone/wide"));
        this.standaloneNarrowDayPeriods = this.loadDayPeriodStrings(map.get("dayPeriod/stand-alone/narrow"));
        for (int i = 0; i < 7; ++i) {
            object3 = LEAP_MONTH_PATTERNS_PATHS[i];
            if (object3 == null || (object2 = map.get(object3)) == null || (capitalizationContextUsageArray = object2.get("leap")) == null) continue;
            if (this.leapMonthPatterns == null) {
                this.leapMonthPatterns = new String[7];
            }
            this.leapMonthPatterns[i] = capitalizationContextUsageArray;
        }
        this.shortYearNames = (String[])object4.get("cyclicNameSets/years/format/abbreviated");
        this.shortZodiacNames = (String[])object4.get("cyclicNameSets/zodiacs/format/abbreviated");
        this.requestedLocale = uLocale;
        ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        this.localPatternChars = patternChars;
        object3 = iCUResourceBundle2.getULocale();
        this.setLocale((ULocale)object3, (ULocale)object3);
        this.capitalization = new HashMap<CapitalizationContextUsage, boolean[]>();
        object2 = new boolean[2];
        object2[0] = false;
        object2[1] = false;
        capitalizationContextUsageArray = CapitalizationContextUsage.values();
        for (Object object5 : capitalizationContextUsageArray) {
            this.capitalization.put((CapitalizationContextUsage)((Object)object5), (boolean[])object2);
        }
        Object object6 = null;
        try {
            object6 = iCUResourceBundle2.getWithFallback("contextTransforms");
        } catch (MissingResourceException missingResourceException) {
            object6 = null;
        }
        if (object6 != null) {
            UResourceBundleIterator uResourceBundleIterator = ((UResourceBundle)object6).getIterator();
            while (uResourceBundleIterator.hasNext()) {
                String string2;
                CapitalizationContextUsage capitalizationContextUsage;
                UResourceBundle uResourceBundle = uResourceBundleIterator.next();
                object5 = uResourceBundle.getIntVector();
                if (((CapitalizationContextUsage)object5).length < 2 || (capitalizationContextUsage = contextUsageTypeMap.get(string2 = uResourceBundle.getKey())) == null) continue;
                boolean[] blArray = new boolean[]{object5[0] != false, object5[1] != false};
                this.capitalization.put(capitalizationContextUsage, blArray);
            }
        }
        String string3 = (numberingSystem = NumberingSystem.getInstance(uLocale)) == null ? "latn" : numberingSystem.getName();
        object5 = "NumberElements/" + string3 + "/symbols/timeSeparator";
        try {
            this.setTimeSeparatorString(iCUResourceBundle2.getStringWithFallback((String)object5));
        } catch (MissingResourceException missingResourceException) {
            this.setTimeSeparatorString(DEFAULT_TIME_SEPARATOR);
        }
    }

    private static final boolean arrayOfArrayEquals(Object[][] objectArray, Object[][] objectArray2) {
        if (objectArray == objectArray2) {
            return false;
        }
        if (objectArray == null || objectArray2 == null) {
            return true;
        }
        if (objectArray.length != objectArray2.length) {
            return true;
        }
        boolean bl = true;
        for (int i = 0; i < objectArray.length && (bl = Utility.arrayEquals(objectArray[i], (Object)objectArray2[i])); ++i) {
        }
        return bl;
    }

    private String[] loadDayPeriodStrings(Map<String, String> map) {
        String[] stringArray = new String[DAY_PERIOD_KEYS.length];
        if (map != null) {
            for (int i = 0; i < DAY_PERIOD_KEYS.length; ++i) {
                stringArray[i] = map.get(DAY_PERIOD_KEYS[i]);
            }
        }
        return stringArray;
    }

    private final String[] duplicate(String[] stringArray) {
        return (String[])stringArray.clone();
    }

    private final String[][] duplicate(String[][] stringArray) {
        String[][] stringArray2 = new String[stringArray.length][];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray2[i] = this.duplicate(stringArray[i]);
        }
        return stringArray2;
    }

    public DateFormatSymbols(Calendar calendar, Locale locale) {
        this.initializeData(ULocale.forLocale(locale), calendar.getType());
    }

    public DateFormatSymbols(Calendar calendar, ULocale uLocale) {
        this.initializeData(uLocale, calendar.getType());
    }

    public DateFormatSymbols(Class<? extends Calendar> clazz, Locale locale) {
        this(clazz, ULocale.forLocale(locale));
    }

    public DateFormatSymbols(Class<? extends Calendar> clazz, ULocale uLocale) {
        String string = clazz.getName();
        int n = string.lastIndexOf(46);
        String string2 = string.substring(n + 1);
        String string3 = null;
        for (String[] stringArray : CALENDAR_CLASSES) {
            if (!stringArray[0].equals(string2)) continue;
            string3 = stringArray[5];
            break;
        }
        if (string3 == null) {
            string3 = string2.replaceAll("Calendar", "").toLowerCase(Locale.ENGLISH);
        }
        this.initializeData(uLocale, string3);
    }

    public DateFormatSymbols(ResourceBundle resourceBundle, Locale locale) {
        this(resourceBundle, ULocale.forLocale(locale));
    }

    public DateFormatSymbols(ResourceBundle resourceBundle, ULocale uLocale) {
        this.initializeData(uLocale, (ICUResourceBundle)resourceBundle, CalendarUtil.getCalendarType(uLocale));
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Class<? extends Calendar> clazz, Locale locale) throws MissingResourceException {
        return null;
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Class<? extends Calendar> clazz, ULocale uLocale) throws MissingResourceException {
        return null;
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Calendar calendar, Locale locale) throws MissingResourceException {
        return null;
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Calendar calendar, ULocale uLocale) throws MissingResourceException {
        return null;
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

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
    }

    DateFormatSymbols(ULocale uLocale, ICUResourceBundle iCUResourceBundle, String string, 1 var4_4) {
        this(uLocale, iCUResourceBundle, string);
    }

    static {
        contextUsageTypeMap.put("month-format-except-narrow", CapitalizationContextUsage.MONTH_FORMAT);
        contextUsageTypeMap.put("month-standalone-except-narrow", CapitalizationContextUsage.MONTH_STANDALONE);
        contextUsageTypeMap.put("month-narrow", CapitalizationContextUsage.MONTH_NARROW);
        contextUsageTypeMap.put("day-format-except-narrow", CapitalizationContextUsage.DAY_FORMAT);
        contextUsageTypeMap.put("day-standalone-except-narrow", CapitalizationContextUsage.DAY_STANDALONE);
        contextUsageTypeMap.put("day-narrow", CapitalizationContextUsage.DAY_NARROW);
        contextUsageTypeMap.put("era-name", CapitalizationContextUsage.ERA_WIDE);
        contextUsageTypeMap.put("era-abbr", CapitalizationContextUsage.ERA_ABBREV);
        contextUsageTypeMap.put("era-narrow", CapitalizationContextUsage.ERA_NARROW);
        contextUsageTypeMap.put("zone-long", CapitalizationContextUsage.ZONE_LONG);
        contextUsageTypeMap.put("zone-short", CapitalizationContextUsage.ZONE_SHORT);
        contextUsageTypeMap.put("metazone-long", CapitalizationContextUsage.METAZONE_LONG);
        contextUsageTypeMap.put("metazone-short", CapitalizationContextUsage.METAZONE_SHORT);
        DFSCACHE = new SoftCache<String, DateFormatSymbols, ULocale>(){

            @Override
            protected DateFormatSymbols createInstance(String string, ULocale uLocale) {
                int n = string.indexOf(43) + 1;
                int n2 = string.indexOf(43, n);
                if (n2 < 0) {
                    n2 = string.length();
                }
                String string2 = string.substring(n, n2);
                return new DateFormatSymbols(uLocale, null, string2, null);
            }

            @Override
            protected Object createInstance(Object object, Object object2) {
                return this.createInstance((String)object, (ULocale)object2);
            }
        };
        LEAP_MONTH_PATTERNS_PATHS = new String[7];
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[0] = "monthPatterns/format/wide";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[1] = "monthPatterns/format/abbreviated";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[2] = "monthPatterns/format/narrow";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[3] = "monthPatterns/stand-alone/wide";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[4] = "monthPatterns/stand-alone/abbreviated";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[5] = "monthPatterns/stand-alone/narrow";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[6] = "monthPatterns/numeric/all";
        DAY_PERIOD_KEYS = new String[]{"midnight", "noon", "morning1", "afternoon1", "evening1", "night1", "morning2", "afternoon2", "evening2", "night2"};
    }

    private static final class CalendarDataSink
    extends UResource.Sink {
        Map<String, String[]> arrays = new TreeMap<String, String[]>();
        Map<String, Map<String, String>> maps = new TreeMap<String, Map<String, String>>();
        List<String> aliasPathPairs = new ArrayList<String>();
        String currentCalendarType = null;
        String nextCalendarType = null;
        private Set<String> resourcesToVisit;
        private String aliasRelativePath;
        private static final String CALENDAR_ALIAS_PREFIX = "/LOCALE/calendar/";
        static final boolean $assertionsDisabled = !DateFormatSymbols.class.desiredAssertionStatus();

        CalendarDataSink() {
        }

        void visitAllResources() {
            this.resourcesToVisit = null;
        }

        void preEnumerate(String string) {
            this.currentCalendarType = string;
            this.nextCalendarType = null;
            this.aliasPathPairs.clear();
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            String[] stringArray;
            if (!$assertionsDisabled && (this.currentCalendarType == null || this.currentCalendarType.isEmpty())) {
                throw new AssertionError();
            }
            HashSet<String> hashSet = null;
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string = key.toString();
                AliasType aliasType = this.processAliasFromValue(string, value);
                if (aliasType != AliasType.GREGORIAN) {
                    if (aliasType == AliasType.DIFFERENT_CALENDAR) {
                        if (hashSet == null) {
                            hashSet = new HashSet<String>();
                        }
                        hashSet.add(this.aliasRelativePath);
                    } else if (aliasType == AliasType.SAME_CALENDAR) {
                        if (!this.arrays.containsKey(string) && !this.maps.containsKey(string)) {
                            this.aliasPathPairs.add(this.aliasRelativePath);
                            this.aliasPathPairs.add(string);
                        }
                    } else if (this.resourcesToVisit == null || this.resourcesToVisit.isEmpty() || this.resourcesToVisit.contains(string) || string.equals("AmPmMarkersAbbr")) {
                        if (string.startsWith("AmPmMarkers")) {
                            if (!string.endsWith("%variant") && !this.arrays.containsKey(string)) {
                                stringArray = value.getStringArray();
                                this.arrays.put(string, stringArray);
                            }
                        } else if (string.equals("eras") || string.equals("dayNames") || string.equals("monthNames") || string.equals("quarters") || string.equals("dayPeriod") || string.equals("monthPatterns") || string.equals("cyclicNameSets")) {
                            this.processResource(string, key, value);
                        }
                    }
                }
                ++n;
            }
            do {
                n = 0;
                int n2 = 0;
                while (n2 < this.aliasPathPairs.size()) {
                    boolean bl2 = false;
                    stringArray = this.aliasPathPairs.get(n2);
                    if (this.arrays.containsKey(stringArray)) {
                        this.arrays.put(this.aliasPathPairs.get(n2 + 1), this.arrays.get(stringArray));
                        bl2 = true;
                    } else if (this.maps.containsKey(stringArray)) {
                        this.maps.put(this.aliasPathPairs.get(n2 + 1), this.maps.get(stringArray));
                        bl2 = true;
                    }
                    if (bl2) {
                        this.aliasPathPairs.remove(n2 + 1);
                        this.aliasPathPairs.remove(n2);
                        n = 1;
                        continue;
                    }
                    n2 += 2;
                }
            } while (n != 0 && !this.aliasPathPairs.isEmpty());
            if (hashSet != null) {
                this.resourcesToVisit = hashSet;
            }
        }

        protected void processResource(String string, UResource.Key key, UResource.Value value) {
            UResource.Table table = value.getTable();
            HashMap<String, String> hashMap = null;
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (!key.endsWith("%variant")) {
                    String string2 = key.toString();
                    if (value.getType() == 0) {
                        if (n == 0) {
                            hashMap = new HashMap<String, String>();
                            this.maps.put(string, hashMap);
                        }
                        if (!$assertionsDisabled && hashMap == null) {
                            throw new AssertionError();
                        }
                        hashMap.put(string2, value.getString());
                    } else {
                        if (!$assertionsDisabled && hashMap != null) {
                            throw new AssertionError();
                        }
                        String string3 = string + "/" + string2;
                        if ((!string3.startsWith("cyclicNameSets") || "cyclicNameSets/years/format/abbreviated".startsWith(string3) || "cyclicNameSets/zodiacs/format/abbreviated".startsWith(string3) || "cyclicNameSets/dayParts/format/abbreviated".startsWith(string3)) && !this.arrays.containsKey(string3) && !this.maps.containsKey(string3)) {
                            AliasType aliasType = this.processAliasFromValue(string3, value);
                            if (aliasType == AliasType.SAME_CALENDAR) {
                                this.aliasPathPairs.add(this.aliasRelativePath);
                                this.aliasPathPairs.add(string3);
                            } else {
                                if (!$assertionsDisabled && aliasType != AliasType.NONE) {
                                    throw new AssertionError();
                                }
                                if (value.getType() == 8) {
                                    String[] stringArray = value.getStringArray();
                                    this.arrays.put(string3, stringArray);
                                } else if (value.getType() == 2) {
                                    this.processResource(string3, key, value);
                                }
                            }
                        }
                    }
                }
                ++n;
            }
        }

        private AliasType processAliasFromValue(String string, UResource.Value value) {
            if (value.getType() == 3) {
                int n;
                String string2 = value.getAliasString();
                if (string2.startsWith(CALENDAR_ALIAS_PREFIX) && string2.length() > 17 && (n = string2.indexOf(47, 17)) > 17) {
                    String string3 = string2.substring(17, n);
                    this.aliasRelativePath = string2.substring(n + 1);
                    if (this.currentCalendarType.equals(string3) && !string.equals(this.aliasRelativePath)) {
                        return AliasType.SAME_CALENDAR;
                    }
                    if (!this.currentCalendarType.equals(string3) && string.equals(this.aliasRelativePath)) {
                        if (string3.equals("gregorian")) {
                            return AliasType.GREGORIAN;
                        }
                        if (this.nextCalendarType == null || this.nextCalendarType.equals(string3)) {
                            this.nextCalendarType = string3;
                            return AliasType.DIFFERENT_CALENDAR;
                        }
                    }
                }
                throw new ICUException("Malformed 'calendar' alias. Path: " + string2);
            }
            return AliasType.NONE;
        }

        private static enum AliasType {
            SAME_CALENDAR,
            DIFFERENT_CALENDAR,
            GREGORIAN,
            NONE;

        }
    }

    static enum CapitalizationContextUsage {
        OTHER,
        MONTH_FORMAT,
        MONTH_STANDALONE,
        MONTH_NARROW,
        DAY_FORMAT,
        DAY_STANDALONE,
        DAY_NARROW,
        ERA_WIDE,
        ERA_ABBREV,
        ERA_NARROW,
        ZONE_LONG,
        ZONE_SHORT,
        METAZONE_LONG,
        METAZONE_SHORT;

    }
}

