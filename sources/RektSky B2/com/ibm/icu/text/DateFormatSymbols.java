package com.ibm.icu.text;

import java.io.*;
import com.ibm.icu.impl.*;
import java.util.*;
import com.ibm.icu.util.*;

public class DateFormatSymbols implements Serializable, Cloneable
{
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
    String[] eras;
    String[] eraNames;
    String[] narrowEras;
    String[] months;
    String[] shortMonths;
    String[] narrowMonths;
    String[] standaloneMonths;
    String[] standaloneShortMonths;
    String[] standaloneNarrowMonths;
    String[] weekdays;
    String[] shortWeekdays;
    String[] shorterWeekdays;
    String[] narrowWeekdays;
    String[] standaloneWeekdays;
    String[] standaloneShortWeekdays;
    String[] standaloneShorterWeekdays;
    String[] standaloneNarrowWeekdays;
    String[] ampms;
    String[] ampmsNarrow;
    private String timeSeparator;
    String[] shortQuarters;
    String[] quarters;
    String[] standaloneShortQuarters;
    String[] standaloneQuarters;
    String[] leapMonthPatterns;
    String[] shortYearNames;
    String[] shortZodiacNames;
    private String[][] zoneStrings;
    static final String patternChars = "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB";
    String localPatternChars;
    String[] abbreviatedDayPeriods;
    String[] wideDayPeriods;
    String[] narrowDayPeriods;
    String[] standaloneAbbreviatedDayPeriods;
    String[] standaloneWideDayPeriods;
    String[] standaloneNarrowDayPeriods;
    private static final long serialVersionUID = -5987973545549424702L;
    private static final String[][] CALENDAR_CLASSES;
    private static final Map<String, CapitalizationContextUsage> contextUsageTypeMap;
    Map<CapitalizationContextUsage, boolean[]> capitalization;
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
    
    public DateFormatSymbols(final Locale locale) {
        this(ULocale.forLocale(locale));
    }
    
    public DateFormatSymbols(final ULocale locale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        this.initializeData(locale, CalendarUtil.getCalendarType(locale));
    }
    
    public static DateFormatSymbols getInstance() {
        return new DateFormatSymbols();
    }
    
    public static DateFormatSymbols getInstance(final Locale locale) {
        return new DateFormatSymbols(locale);
    }
    
    public static DateFormatSymbols getInstance(final ULocale locale) {
        return new DateFormatSymbols(locale);
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
    
    public void setEras(final String[] newEras) {
        this.eras = this.duplicate(newEras);
    }
    
    public String[] getEraNames() {
        return this.duplicate(this.eraNames);
    }
    
    public void setEraNames(final String[] newEraNames) {
        this.eraNames = this.duplicate(newEraNames);
    }
    
    public String[] getMonths() {
        return this.duplicate(this.months);
    }
    
    public String[] getMonths(final int context, final int width) {
        String[] returnValue = null;
        Label_0137: {
            switch (context) {
                case 0: {
                    switch (width) {
                        case 1: {
                            returnValue = this.months;
                            break;
                        }
                        case 0:
                        case 3: {
                            returnValue = this.shortMonths;
                            break;
                        }
                        case 2: {
                            returnValue = this.narrowMonths;
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (width) {
                        case 1: {
                            returnValue = this.standaloneMonths;
                            break Label_0137;
                        }
                        case 0:
                        case 3: {
                            returnValue = this.standaloneShortMonths;
                            break Label_0137;
                        }
                        case 2: {
                            returnValue = this.standaloneNarrowMonths;
                            break Label_0137;
                        }
                    }
                    break;
                }
            }
        }
        if (returnValue == null) {
            throw new IllegalArgumentException("Bad context or width argument");
        }
        return this.duplicate(returnValue);
    }
    
    public void setMonths(final String[] newMonths) {
        this.months = this.duplicate(newMonths);
    }
    
    public void setMonths(final String[] newMonths, final int context, final int width) {
        Label_0160: {
            switch (context) {
                case 0: {
                    switch (width) {
                        case 1: {
                            this.months = this.duplicate(newMonths);
                            break Label_0160;
                        }
                        case 0: {
                            this.shortMonths = this.duplicate(newMonths);
                            break Label_0160;
                        }
                        case 2: {
                            this.narrowMonths = this.duplicate(newMonths);
                            break Label_0160;
                        }
                        default: {
                            break Label_0160;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (width) {
                        case 1: {
                            this.standaloneMonths = this.duplicate(newMonths);
                            break Label_0160;
                        }
                        case 0: {
                            this.standaloneShortMonths = this.duplicate(newMonths);
                            break Label_0160;
                        }
                        case 2: {
                            this.standaloneNarrowMonths = this.duplicate(newMonths);
                            break Label_0160;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public String[] getShortMonths() {
        return this.duplicate(this.shortMonths);
    }
    
    public void setShortMonths(final String[] newShortMonths) {
        this.shortMonths = this.duplicate(newShortMonths);
    }
    
    public String[] getWeekdays() {
        return this.duplicate(this.weekdays);
    }
    
    public String[] getWeekdays(final int context, final int width) {
        String[] returnValue = null;
        Label_0179: {
            switch (context) {
                case 0: {
                    switch (width) {
                        case 1: {
                            returnValue = this.weekdays;
                            break;
                        }
                        case 0: {
                            returnValue = this.shortWeekdays;
                            break;
                        }
                        case 3: {
                            returnValue = ((this.shorterWeekdays != null) ? this.shorterWeekdays : this.shortWeekdays);
                            break;
                        }
                        case 2: {
                            returnValue = this.narrowWeekdays;
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (width) {
                        case 1: {
                            returnValue = this.standaloneWeekdays;
                            break Label_0179;
                        }
                        case 0: {
                            returnValue = this.standaloneShortWeekdays;
                            break Label_0179;
                        }
                        case 3: {
                            returnValue = ((this.standaloneShorterWeekdays != null) ? this.standaloneShorterWeekdays : this.standaloneShortWeekdays);
                            break Label_0179;
                        }
                        case 2: {
                            returnValue = this.standaloneNarrowWeekdays;
                            break Label_0179;
                        }
                    }
                    break;
                }
            }
        }
        if (returnValue == null) {
            throw new IllegalArgumentException("Bad context or width argument");
        }
        return this.duplicate(returnValue);
    }
    
    public void setWeekdays(final String[] newWeekdays, final int context, final int width) {
        Label_0185: {
            switch (context) {
                case 0: {
                    switch (width) {
                        case 1: {
                            this.weekdays = this.duplicate(newWeekdays);
                            break;
                        }
                        case 0: {
                            this.shortWeekdays = this.duplicate(newWeekdays);
                            break;
                        }
                        case 3: {
                            this.shorterWeekdays = this.duplicate(newWeekdays);
                            break;
                        }
                        case 2: {
                            this.narrowWeekdays = this.duplicate(newWeekdays);
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (width) {
                        case 1: {
                            this.standaloneWeekdays = this.duplicate(newWeekdays);
                            break Label_0185;
                        }
                        case 0: {
                            this.standaloneShortWeekdays = this.duplicate(newWeekdays);
                            break Label_0185;
                        }
                        case 3: {
                            this.standaloneShorterWeekdays = this.duplicate(newWeekdays);
                            break Label_0185;
                        }
                        case 2: {
                            this.standaloneNarrowWeekdays = this.duplicate(newWeekdays);
                            break Label_0185;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public void setWeekdays(final String[] newWeekdays) {
        this.weekdays = this.duplicate(newWeekdays);
    }
    
    public String[] getShortWeekdays() {
        return this.duplicate(this.shortWeekdays);
    }
    
    public void setShortWeekdays(final String[] newAbbrevWeekdays) {
        this.shortWeekdays = this.duplicate(newAbbrevWeekdays);
    }
    
    public String[] getQuarters(final int context, final int width) {
        String[] returnValue = null;
        Label_0130: {
            switch (context) {
                case 0: {
                    switch (width) {
                        case 1: {
                            returnValue = this.quarters;
                            break;
                        }
                        case 0:
                        case 3: {
                            returnValue = this.shortQuarters;
                            break;
                        }
                        case 2: {
                            returnValue = null;
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (width) {
                        case 1: {
                            returnValue = this.standaloneQuarters;
                            break Label_0130;
                        }
                        case 0:
                        case 3: {
                            returnValue = this.standaloneShortQuarters;
                            break Label_0130;
                        }
                        case 2: {
                            returnValue = null;
                            break Label_0130;
                        }
                    }
                    break;
                }
            }
        }
        if (returnValue == null) {
            throw new IllegalArgumentException("Bad context or width argument");
        }
        return this.duplicate(returnValue);
    }
    
    public void setQuarters(final String[] newQuarters, final int context, final int width) {
        Label_0139: {
            switch (context) {
                case 0: {
                    switch (width) {
                        case 1: {
                            this.quarters = this.duplicate(newQuarters);
                            break Label_0139;
                        }
                        case 0: {
                            this.shortQuarters = this.duplicate(newQuarters);
                            break Label_0139;
                        }
                        case 2: {
                            break Label_0139;
                        }
                        default: {
                            break Label_0139;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (width) {
                        case 1: {
                            this.standaloneQuarters = this.duplicate(newQuarters);
                            break Label_0139;
                        }
                        case 0: {
                            this.standaloneShortQuarters = this.duplicate(newQuarters);
                        }
                        case 2: {
                            break Label_0139;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public String[] getYearNames(final int context, final int width) {
        if (this.shortYearNames != null) {
            return this.duplicate(this.shortYearNames);
        }
        return null;
    }
    
    public void setYearNames(final String[] yearNames, final int context, final int width) {
        if (context == 0 && width == 0) {
            this.shortYearNames = this.duplicate(yearNames);
        }
    }
    
    public String[] getZodiacNames(final int context, final int width) {
        if (this.shortZodiacNames != null) {
            return this.duplicate(this.shortZodiacNames);
        }
        return null;
    }
    
    public void setZodiacNames(final String[] zodiacNames, final int context, final int width) {
        if (context == 0 && width == 0) {
            this.shortZodiacNames = this.duplicate(zodiacNames);
        }
    }
    
    @Deprecated
    public String getLeapMonthPattern(final int context, final int width) {
        if (this.leapMonthPatterns == null) {
            return null;
        }
        int leapMonthPatternIndex = -1;
        switch (context) {
            case 0: {
                switch (width) {
                    case 1: {
                        leapMonthPatternIndex = 0;
                        break;
                    }
                    case 0:
                    case 3: {
                        leapMonthPatternIndex = 1;
                        break;
                    }
                    case 2: {
                        leapMonthPatternIndex = 2;
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (width) {
                    case 1: {
                        leapMonthPatternIndex = 3;
                        break;
                    }
                    case 0:
                    case 3: {
                        leapMonthPatternIndex = 1;
                        break;
                    }
                    case 2: {
                        leapMonthPatternIndex = 5;
                        break;
                    }
                }
                break;
            }
            case 2: {
                leapMonthPatternIndex = 6;
                break;
            }
        }
        if (leapMonthPatternIndex < 0) {
            throw new IllegalArgumentException("Bad context or width argument");
        }
        return this.leapMonthPatterns[leapMonthPatternIndex];
    }
    
    @Deprecated
    public void setLeapMonthPattern(final String leapMonthPattern, final int context, final int width) {
        if (this.leapMonthPatterns != null) {
            int leapMonthPatternIndex = -1;
            Label_0140: {
                switch (context) {
                    case 0: {
                        switch (width) {
                            case 1: {
                                leapMonthPatternIndex = 0;
                                break Label_0140;
                            }
                            case 0: {
                                leapMonthPatternIndex = 1;
                                break Label_0140;
                            }
                            case 2: {
                                leapMonthPatternIndex = 2;
                                break Label_0140;
                            }
                            default: {
                                break Label_0140;
                            }
                        }
                        break;
                    }
                    case 1: {
                        switch (width) {
                            case 1: {
                                leapMonthPatternIndex = 3;
                                break Label_0140;
                            }
                            case 0: {
                                leapMonthPatternIndex = 1;
                                break Label_0140;
                            }
                            case 2: {
                                leapMonthPatternIndex = 5;
                                break Label_0140;
                            }
                            default: {
                                break Label_0140;
                            }
                        }
                        break;
                    }
                    case 2: {
                        leapMonthPatternIndex = 6;
                        break;
                    }
                }
            }
            if (leapMonthPatternIndex >= 0) {
                this.leapMonthPatterns[leapMonthPatternIndex] = leapMonthPattern;
            }
        }
    }
    
    public String[] getAmPmStrings() {
        return this.duplicate(this.ampms);
    }
    
    public void setAmPmStrings(final String[] newAmpms) {
        this.ampms = this.duplicate(newAmpms);
    }
    
    @Deprecated
    public String getTimeSeparatorString() {
        return this.timeSeparator;
    }
    
    @Deprecated
    public void setTimeSeparatorString(final String newTimeSeparator) {
        this.timeSeparator = newTimeSeparator;
    }
    
    public String[][] getZoneStrings() {
        if (this.zoneStrings != null) {
            return this.duplicate(this.zoneStrings);
        }
        final String[] tzIDs = TimeZone.getAvailableIDs();
        final TimeZoneNames tznames = TimeZoneNames.getInstance(this.validLocale);
        tznames.loadAllDisplayNames();
        final TimeZoneNames.NameType[] types = { TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_DAYLIGHT };
        final long now = System.currentTimeMillis();
        final String[][] array = new String[tzIDs.length][5];
        for (int i = 0; i < tzIDs.length; ++i) {
            String canonicalID = TimeZone.getCanonicalID(tzIDs[i]);
            if (canonicalID == null) {
                canonicalID = tzIDs[i];
            }
            array[i][0] = tzIDs[i];
            tznames.getDisplayNames(canonicalID, types, now, array[i], 1);
        }
        return this.zoneStrings = array;
    }
    
    public void setZoneStrings(final String[][] newZoneStrings) {
        this.zoneStrings = this.duplicate(newZoneStrings);
    }
    
    public String getLocalPatternChars() {
        return this.localPatternChars;
    }
    
    public void setLocalPatternChars(final String newLocalPatternChars) {
        this.localPatternChars = newLocalPatternChars;
    }
    
    public Object clone() {
        try {
            final DateFormatSymbols other = (DateFormatSymbols)super.clone();
            return other;
        }
        catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }
    
    @Override
    public int hashCode() {
        return this.requestedLocale.toString().hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final DateFormatSymbols that = (DateFormatSymbols)obj;
        return Utility.arrayEquals(this.eras, that.eras) && Utility.arrayEquals(this.eraNames, that.eraNames) && Utility.arrayEquals(this.months, that.months) && Utility.arrayEquals(this.shortMonths, that.shortMonths) && Utility.arrayEquals(this.narrowMonths, that.narrowMonths) && Utility.arrayEquals(this.standaloneMonths, that.standaloneMonths) && Utility.arrayEquals(this.standaloneShortMonths, that.standaloneShortMonths) && Utility.arrayEquals(this.standaloneNarrowMonths, that.standaloneNarrowMonths) && Utility.arrayEquals(this.weekdays, that.weekdays) && Utility.arrayEquals(this.shortWeekdays, that.shortWeekdays) && Utility.arrayEquals(this.shorterWeekdays, that.shorterWeekdays) && Utility.arrayEquals(this.narrowWeekdays, that.narrowWeekdays) && Utility.arrayEquals(this.standaloneWeekdays, that.standaloneWeekdays) && Utility.arrayEquals(this.standaloneShortWeekdays, that.standaloneShortWeekdays) && Utility.arrayEquals(this.standaloneShorterWeekdays, that.standaloneShorterWeekdays) && Utility.arrayEquals(this.standaloneNarrowWeekdays, that.standaloneNarrowWeekdays) && Utility.arrayEquals(this.ampms, that.ampms) && Utility.arrayEquals(this.ampmsNarrow, that.ampmsNarrow) && Utility.arrayEquals(this.abbreviatedDayPeriods, that.abbreviatedDayPeriods) && Utility.arrayEquals(this.wideDayPeriods, that.wideDayPeriods) && Utility.arrayEquals(this.narrowDayPeriods, that.narrowDayPeriods) && Utility.arrayEquals(this.standaloneAbbreviatedDayPeriods, that.standaloneAbbreviatedDayPeriods) && Utility.arrayEquals(this.standaloneWideDayPeriods, that.standaloneWideDayPeriods) && Utility.arrayEquals(this.standaloneNarrowDayPeriods, that.standaloneNarrowDayPeriods) && Utility.arrayEquals(this.timeSeparator, that.timeSeparator) && arrayOfArrayEquals(this.zoneStrings, that.zoneStrings) && this.requestedLocale.getDisplayName().equals(that.requestedLocale.getDisplayName()) && Utility.arrayEquals(this.localPatternChars, that.localPatternChars);
    }
    
    protected void initializeData(final ULocale desiredLocale, final String type) {
        String key = desiredLocale.getBaseName() + '+' + type;
        final String ns = desiredLocale.getKeywordValue("numbers");
        if (ns != null && ns.length() > 0) {
            key = key + '+' + ns;
        }
        final DateFormatSymbols dfs = DateFormatSymbols.DFSCACHE.getInstance(key, desiredLocale);
        this.initializeData(dfs);
    }
    
    void initializeData(final DateFormatSymbols dfs) {
        this.eras = dfs.eras;
        this.eraNames = dfs.eraNames;
        this.narrowEras = dfs.narrowEras;
        this.months = dfs.months;
        this.shortMonths = dfs.shortMonths;
        this.narrowMonths = dfs.narrowMonths;
        this.standaloneMonths = dfs.standaloneMonths;
        this.standaloneShortMonths = dfs.standaloneShortMonths;
        this.standaloneNarrowMonths = dfs.standaloneNarrowMonths;
        this.weekdays = dfs.weekdays;
        this.shortWeekdays = dfs.shortWeekdays;
        this.shorterWeekdays = dfs.shorterWeekdays;
        this.narrowWeekdays = dfs.narrowWeekdays;
        this.standaloneWeekdays = dfs.standaloneWeekdays;
        this.standaloneShortWeekdays = dfs.standaloneShortWeekdays;
        this.standaloneShorterWeekdays = dfs.standaloneShorterWeekdays;
        this.standaloneNarrowWeekdays = dfs.standaloneNarrowWeekdays;
        this.ampms = dfs.ampms;
        this.ampmsNarrow = dfs.ampmsNarrow;
        this.timeSeparator = dfs.timeSeparator;
        this.shortQuarters = dfs.shortQuarters;
        this.quarters = dfs.quarters;
        this.standaloneShortQuarters = dfs.standaloneShortQuarters;
        this.standaloneQuarters = dfs.standaloneQuarters;
        this.leapMonthPatterns = dfs.leapMonthPatterns;
        this.shortYearNames = dfs.shortYearNames;
        this.shortZodiacNames = dfs.shortZodiacNames;
        this.abbreviatedDayPeriods = dfs.abbreviatedDayPeriods;
        this.wideDayPeriods = dfs.wideDayPeriods;
        this.narrowDayPeriods = dfs.narrowDayPeriods;
        this.standaloneAbbreviatedDayPeriods = dfs.standaloneAbbreviatedDayPeriods;
        this.standaloneWideDayPeriods = dfs.standaloneWideDayPeriods;
        this.standaloneNarrowDayPeriods = dfs.standaloneNarrowDayPeriods;
        this.zoneStrings = dfs.zoneStrings;
        this.localPatternChars = dfs.localPatternChars;
        this.capitalization = dfs.capitalization;
        this.actualLocale = dfs.actualLocale;
        this.validLocale = dfs.validLocale;
        this.requestedLocale = dfs.requestedLocale;
    }
    
    private DateFormatSymbols(final ULocale desiredLocale, final ICUResourceBundle b, final String calendarType) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        this.initializeData(desiredLocale, b, calendarType);
    }
    
    @Deprecated
    protected void initializeData(final ULocale desiredLocale, ICUResourceBundle b, String calendarType) {
        final CalendarDataSink calendarSink = new CalendarDataSink();
        if (b == null) {
            b = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", desiredLocale);
        }
        while (calendarType != null) {
            final ICUResourceBundle dataForType = b.findWithFallback("calendar/" + calendarType);
            if (dataForType == null) {
                if ("gregorian".equals(calendarType)) {
                    throw new MissingResourceException("The 'gregorian' calendar type wasn't found for the locale: " + desiredLocale.getBaseName(), this.getClass().getName(), "gregorian");
                }
                calendarType = "gregorian";
                calendarSink.visitAllResources();
            }
            else {
                calendarSink.preEnumerate(calendarType);
                dataForType.getAllItemsWithFallback("", calendarSink);
                if (calendarType.equals("gregorian")) {
                    break;
                }
                calendarType = calendarSink.nextCalendarType;
                if (calendarType != null) {
                    continue;
                }
                calendarType = "gregorian";
                calendarSink.visitAllResources();
            }
        }
        final Map<String, String[]> arrays = calendarSink.arrays;
        final Map<String, Map<String, String>> maps = calendarSink.maps;
        this.eras = arrays.get("eras/abbreviated");
        this.eraNames = arrays.get("eras/wide");
        this.narrowEras = arrays.get("eras/narrow");
        this.months = arrays.get("monthNames/format/wide");
        this.shortMonths = arrays.get("monthNames/format/abbreviated");
        this.narrowMonths = arrays.get("monthNames/format/narrow");
        this.standaloneMonths = arrays.get("monthNames/stand-alone/wide");
        this.standaloneShortMonths = arrays.get("monthNames/stand-alone/abbreviated");
        this.standaloneNarrowMonths = arrays.get("monthNames/stand-alone/narrow");
        final String[] lWeekdays = arrays.get("dayNames/format/wide");
        (this.weekdays = new String[8])[0] = "";
        System.arraycopy(lWeekdays, 0, this.weekdays, 1, lWeekdays.length);
        final String[] aWeekdays = arrays.get("dayNames/format/abbreviated");
        (this.shortWeekdays = new String[8])[0] = "";
        System.arraycopy(aWeekdays, 0, this.shortWeekdays, 1, aWeekdays.length);
        final String[] sWeekdays = arrays.get("dayNames/format/short");
        (this.shorterWeekdays = new String[8])[0] = "";
        System.arraycopy(sWeekdays, 0, this.shorterWeekdays, 1, sWeekdays.length);
        String[] nWeekdays = arrays.get("dayNames/format/narrow");
        if (nWeekdays == null) {
            nWeekdays = arrays.get("dayNames/stand-alone/narrow");
            if (nWeekdays == null) {
                nWeekdays = arrays.get("dayNames/format/abbreviated");
                if (nWeekdays == null) {
                    throw new MissingResourceException("Resource not found", this.getClass().getName(), "dayNames/format/abbreviated");
                }
            }
        }
        (this.narrowWeekdays = new String[8])[0] = "";
        System.arraycopy(nWeekdays, 0, this.narrowWeekdays, 1, nWeekdays.length);
        String[] swWeekdays = null;
        swWeekdays = arrays.get("dayNames/stand-alone/wide");
        (this.standaloneWeekdays = new String[8])[0] = "";
        System.arraycopy(swWeekdays, 0, this.standaloneWeekdays, 1, swWeekdays.length);
        String[] saWeekdays = null;
        saWeekdays = arrays.get("dayNames/stand-alone/abbreviated");
        (this.standaloneShortWeekdays = new String[8])[0] = "";
        System.arraycopy(saWeekdays, 0, this.standaloneShortWeekdays, 1, saWeekdays.length);
        String[] ssWeekdays = null;
        ssWeekdays = arrays.get("dayNames/stand-alone/short");
        (this.standaloneShorterWeekdays = new String[8])[0] = "";
        System.arraycopy(ssWeekdays, 0, this.standaloneShorterWeekdays, 1, ssWeekdays.length);
        String[] snWeekdays = null;
        snWeekdays = arrays.get("dayNames/stand-alone/narrow");
        (this.standaloneNarrowWeekdays = new String[8])[0] = "";
        System.arraycopy(snWeekdays, 0, this.standaloneNarrowWeekdays, 1, snWeekdays.length);
        this.ampms = arrays.get("AmPmMarkers");
        this.ampmsNarrow = arrays.get("AmPmMarkersNarrow");
        this.quarters = arrays.get("quarters/format/wide");
        this.shortQuarters = arrays.get("quarters/format/abbreviated");
        this.standaloneQuarters = arrays.get("quarters/stand-alone/wide");
        this.standaloneShortQuarters = arrays.get("quarters/stand-alone/abbreviated");
        this.abbreviatedDayPeriods = this.loadDayPeriodStrings(maps.get("dayPeriod/format/abbreviated"));
        this.wideDayPeriods = this.loadDayPeriodStrings(maps.get("dayPeriod/format/wide"));
        this.narrowDayPeriods = this.loadDayPeriodStrings(maps.get("dayPeriod/format/narrow"));
        this.standaloneAbbreviatedDayPeriods = this.loadDayPeriodStrings(maps.get("dayPeriod/stand-alone/abbreviated"));
        this.standaloneWideDayPeriods = this.loadDayPeriodStrings(maps.get("dayPeriod/stand-alone/wide"));
        this.standaloneNarrowDayPeriods = this.loadDayPeriodStrings(maps.get("dayPeriod/stand-alone/narrow"));
        for (int i = 0; i < 7; ++i) {
            final String monthPatternPath = DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[i];
            if (monthPatternPath != null) {
                final Map<String, String> monthPatternMap = maps.get(monthPatternPath);
                if (monthPatternMap != null) {
                    final String leapMonthPattern = monthPatternMap.get("leap");
                    if (leapMonthPattern != null) {
                        if (this.leapMonthPatterns == null) {
                            this.leapMonthPatterns = new String[7];
                        }
                        this.leapMonthPatterns[i] = leapMonthPattern;
                    }
                }
            }
        }
        this.shortYearNames = arrays.get("cyclicNameSets/years/format/abbreviated");
        this.shortZodiacNames = arrays.get("cyclicNameSets/zodiacs/format/abbreviated");
        this.requestedLocale = desiredLocale;
        final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", desiredLocale);
        this.localPatternChars = "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB";
        final ULocale uloc = rb.getULocale();
        this.setLocale(uloc, uloc);
        this.capitalization = new HashMap<CapitalizationContextUsage, boolean[]>();
        final boolean[] noTransforms = { false, false };
        final CapitalizationContextUsage[] values;
        final CapitalizationContextUsage[] allUsages = values = CapitalizationContextUsage.values();
        for (final CapitalizationContextUsage usage : values) {
            this.capitalization.put(usage, noTransforms);
        }
        UResourceBundle contextTransformsBundle = null;
        try {
            contextTransformsBundle = rb.getWithFallback("contextTransforms");
        }
        catch (MissingResourceException e) {
            contextTransformsBundle = null;
        }
        if (contextTransformsBundle != null) {
            final UResourceBundleIterator ctIterator = contextTransformsBundle.getIterator();
            while (ctIterator.hasNext()) {
                final UResourceBundle contextTransformUsage = ctIterator.next();
                final int[] intVector = contextTransformUsage.getIntVector();
                if (intVector.length >= 2) {
                    final String usageKey = contextTransformUsage.getKey();
                    final CapitalizationContextUsage usage2 = DateFormatSymbols.contextUsageTypeMap.get(usageKey);
                    if (usage2 == null) {
                        continue;
                    }
                    final boolean[] transforms = { intVector[0] != 0, intVector[1] != 0 };
                    this.capitalization.put(usage2, transforms);
                }
            }
        }
        final NumberingSystem ns = NumberingSystem.getInstance(desiredLocale);
        final String nsName = (ns == null) ? "latn" : ns.getName();
        final String tsPath = "NumberElements/" + nsName + "/symbols/timeSeparator";
        try {
            this.setTimeSeparatorString(rb.getStringWithFallback(tsPath));
        }
        catch (MissingResourceException e2) {
            this.setTimeSeparatorString(":");
        }
    }
    
    private static final boolean arrayOfArrayEquals(final Object[][] aa1, final Object[][] aa2) {
        if (aa1 == aa2) {
            return true;
        }
        if (aa1 == null || aa2 == null) {
            return false;
        }
        if (aa1.length != aa2.length) {
            return false;
        }
        boolean equal = true;
        for (int i = 0; i < aa1.length; ++i) {
            equal = Utility.arrayEquals(aa1[i], aa2[i]);
            if (!equal) {
                break;
            }
        }
        return equal;
    }
    
    private String[] loadDayPeriodStrings(final Map<String, String> resourceMap) {
        final String[] strings = new String[DateFormatSymbols.DAY_PERIOD_KEYS.length];
        if (resourceMap != null) {
            for (int i = 0; i < DateFormatSymbols.DAY_PERIOD_KEYS.length; ++i) {
                strings[i] = resourceMap.get(DateFormatSymbols.DAY_PERIOD_KEYS[i]);
            }
        }
        return strings;
    }
    
    private final String[] duplicate(final String[] srcArray) {
        return srcArray.clone();
    }
    
    private final String[][] duplicate(final String[][] srcArray) {
        final String[][] aCopy = new String[srcArray.length][];
        for (int i = 0; i < srcArray.length; ++i) {
            aCopy[i] = this.duplicate(srcArray[i]);
        }
        return aCopy;
    }
    
    public DateFormatSymbols(final Calendar cal, final Locale locale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        this.initializeData(ULocale.forLocale(locale), cal.getType());
    }
    
    public DateFormatSymbols(final Calendar cal, final ULocale locale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        this.initializeData(locale, cal.getType());
    }
    
    public DateFormatSymbols(final Class<? extends Calendar> calendarClass, final Locale locale) {
        this(calendarClass, ULocale.forLocale(locale));
    }
    
    public DateFormatSymbols(final Class<? extends Calendar> calendarClass, final ULocale locale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        final String fullName = calendarClass.getName();
        final int lastDot = fullName.lastIndexOf(46);
        final String className = fullName.substring(lastDot + 1);
        String calType = null;
        for (final String[] calClassInfo : DateFormatSymbols.CALENDAR_CLASSES) {
            if (calClassInfo[0].equals(className)) {
                calType = calClassInfo[1];
                break;
            }
        }
        if (calType == null) {
            calType = className.replaceAll("Calendar", "").toLowerCase(Locale.ENGLISH);
        }
        this.initializeData(locale, calType);
    }
    
    public DateFormatSymbols(final ResourceBundle bundle, final Locale locale) {
        this(bundle, ULocale.forLocale(locale));
    }
    
    public DateFormatSymbols(final ResourceBundle bundle, final ULocale locale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        this.initializeData(locale, (ICUResourceBundle)bundle, CalendarUtil.getCalendarType(locale));
    }
    
    @Deprecated
    public static ResourceBundle getDateFormatBundle(final Class<? extends Calendar> calendarClass, final Locale locale) throws MissingResourceException {
        return null;
    }
    
    @Deprecated
    public static ResourceBundle getDateFormatBundle(final Class<? extends Calendar> calendarClass, final ULocale locale) throws MissingResourceException {
        return null;
    }
    
    @Deprecated
    public static ResourceBundle getDateFormatBundle(final Calendar cal, final Locale locale) throws MissingResourceException {
        return null;
    }
    
    @Deprecated
    public static ResourceBundle getDateFormatBundle(final Calendar cal, final ULocale locale) throws MissingResourceException {
        return null;
    }
    
    public final ULocale getLocale(final ULocale.Type type) {
        return (type == ULocale.ACTUAL_LOCALE) ? this.actualLocale : this.validLocale;
    }
    
    final void setLocale(final ULocale valid, final ULocale actual) {
        if (valid == null != (actual == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = valid;
        this.actualLocale = actual;
    }
    
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
    
    static {
        CALENDAR_CLASSES = new String[][] { { "GregorianCalendar", "gregorian" }, { "JapaneseCalendar", "japanese" }, { "BuddhistCalendar", "buddhist" }, { "TaiwanCalendar", "roc" }, { "PersianCalendar", "persian" }, { "IslamicCalendar", "islamic" }, { "HebrewCalendar", "hebrew" }, { "ChineseCalendar", "chinese" }, { "IndianCalendar", "indian" }, { "CopticCalendar", "coptic" }, { "EthiopicCalendar", "ethiopic" } };
        (contextUsageTypeMap = new HashMap<String, CapitalizationContextUsage>()).put("month-format-except-narrow", CapitalizationContextUsage.MONTH_FORMAT);
        DateFormatSymbols.contextUsageTypeMap.put("month-standalone-except-narrow", CapitalizationContextUsage.MONTH_STANDALONE);
        DateFormatSymbols.contextUsageTypeMap.put("month-narrow", CapitalizationContextUsage.MONTH_NARROW);
        DateFormatSymbols.contextUsageTypeMap.put("day-format-except-narrow", CapitalizationContextUsage.DAY_FORMAT);
        DateFormatSymbols.contextUsageTypeMap.put("day-standalone-except-narrow", CapitalizationContextUsage.DAY_STANDALONE);
        DateFormatSymbols.contextUsageTypeMap.put("day-narrow", CapitalizationContextUsage.DAY_NARROW);
        DateFormatSymbols.contextUsageTypeMap.put("era-name", CapitalizationContextUsage.ERA_WIDE);
        DateFormatSymbols.contextUsageTypeMap.put("era-abbr", CapitalizationContextUsage.ERA_ABBREV);
        DateFormatSymbols.contextUsageTypeMap.put("era-narrow", CapitalizationContextUsage.ERA_NARROW);
        DateFormatSymbols.contextUsageTypeMap.put("zone-long", CapitalizationContextUsage.ZONE_LONG);
        DateFormatSymbols.contextUsageTypeMap.put("zone-short", CapitalizationContextUsage.ZONE_SHORT);
        DateFormatSymbols.contextUsageTypeMap.put("metazone-long", CapitalizationContextUsage.METAZONE_LONG);
        DateFormatSymbols.contextUsageTypeMap.put("metazone-short", CapitalizationContextUsage.METAZONE_SHORT);
        DateFormatSymbols.DFSCACHE = new SoftCache<String, DateFormatSymbols, ULocale>() {
            @Override
            protected DateFormatSymbols createInstance(final String key, final ULocale locale) {
                final int typeStart = key.indexOf(43) + 1;
                int typeLimit = key.indexOf(43, typeStart);
                if (typeLimit < 0) {
                    typeLimit = key.length();
                }
                final String type = key.substring(typeStart, typeLimit);
                return new DateFormatSymbols(locale, null, type, null);
            }
        };
        (LEAP_MONTH_PATTERNS_PATHS = new String[7])[0] = "monthPatterns/format/wide";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[1] = "monthPatterns/format/abbreviated";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[2] = "monthPatterns/format/narrow";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[3] = "monthPatterns/stand-alone/wide";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[4] = "monthPatterns/stand-alone/abbreviated";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[5] = "monthPatterns/stand-alone/narrow";
        DateFormatSymbols.LEAP_MONTH_PATTERNS_PATHS[6] = "monthPatterns/numeric/all";
        DAY_PERIOD_KEYS = new String[] { "midnight", "noon", "morning1", "afternoon1", "evening1", "night1", "morning2", "afternoon2", "evening2", "night2" };
    }
    
    enum CapitalizationContextUsage
    {
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
    
    private static final class CalendarDataSink extends UResource.Sink
    {
        Map<String, String[]> arrays;
        Map<String, Map<String, String>> maps;
        List<String> aliasPathPairs;
        String currentCalendarType;
        String nextCalendarType;
        private Set<String> resourcesToVisit;
        private String aliasRelativePath;
        private static final String CALENDAR_ALIAS_PREFIX = "/LOCALE/calendar/";
        
        CalendarDataSink() {
            this.arrays = new TreeMap<String, String[]>();
            this.maps = new TreeMap<String, Map<String, String>>();
            this.aliasPathPairs = new ArrayList<String>();
            this.currentCalendarType = null;
            this.nextCalendarType = null;
        }
        
        void visitAllResources() {
            this.resourcesToVisit = null;
        }
        
        void preEnumerate(final String calendarType) {
            this.currentCalendarType = calendarType;
            this.nextCalendarType = null;
            this.aliasPathPairs.clear();
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            assert this.currentCalendarType != null && !this.currentCalendarType.isEmpty();
            Set<String> resourcesToVisitNext = null;
            final UResource.Table calendarData = value.getTable();
            for (int i = 0; calendarData.getKeyAndValue(i, key, value); ++i) {
                final String keyString = key.toString();
                final AliasType aliasType = this.processAliasFromValue(keyString, value);
                if (aliasType != AliasType.GREGORIAN) {
                    if (aliasType == AliasType.DIFFERENT_CALENDAR) {
                        if (resourcesToVisitNext == null) {
                            resourcesToVisitNext = new HashSet<String>();
                        }
                        resourcesToVisitNext.add(this.aliasRelativePath);
                    }
                    else if (aliasType == AliasType.SAME_CALENDAR) {
                        if (!this.arrays.containsKey(keyString) && !this.maps.containsKey(keyString)) {
                            this.aliasPathPairs.add(this.aliasRelativePath);
                            this.aliasPathPairs.add(keyString);
                        }
                    }
                    else if (this.resourcesToVisit == null || this.resourcesToVisit.isEmpty() || this.resourcesToVisit.contains(keyString) || keyString.equals("AmPmMarkersAbbr")) {
                        if (keyString.startsWith("AmPmMarkers")) {
                            if (!keyString.endsWith("%variant") && !this.arrays.containsKey(keyString)) {
                                final String[] dataArray = value.getStringArray();
                                this.arrays.put(keyString, dataArray);
                            }
                        }
                        else if (keyString.equals("eras") || keyString.equals("dayNames") || keyString.equals("monthNames") || keyString.equals("quarters") || keyString.equals("dayPeriod") || keyString.equals("monthPatterns") || keyString.equals("cyclicNameSets")) {
                            this.processResource(keyString, key, value);
                        }
                    }
                }
            }
            boolean modified;
            do {
                modified = false;
                int j = 0;
                while (j < this.aliasPathPairs.size()) {
                    boolean mod = false;
                    final String alias = this.aliasPathPairs.get(j);
                    if (this.arrays.containsKey(alias)) {
                        this.arrays.put(this.aliasPathPairs.get(j + 1), this.arrays.get(alias));
                        mod = true;
                    }
                    else if (this.maps.containsKey(alias)) {
                        this.maps.put(this.aliasPathPairs.get(j + 1), this.maps.get(alias));
                        mod = true;
                    }
                    if (mod) {
                        this.aliasPathPairs.remove(j + 1);
                        this.aliasPathPairs.remove(j);
                        modified = true;
                    }
                    else {
                        j += 2;
                    }
                }
            } while (modified && !this.aliasPathPairs.isEmpty());
            if (resourcesToVisitNext != null) {
                this.resourcesToVisit = resourcesToVisitNext;
            }
        }
        
        protected void processResource(final String path, final UResource.Key key, final UResource.Value value) {
            final UResource.Table table = value.getTable();
            Map<String, String> stringMap = null;
            for (int i = 0; table.getKeyAndValue(i, key, value); ++i) {
                if (!key.endsWith("%variant")) {
                    final String keyString = key.toString();
                    if (value.getType() == 0) {
                        if (i == 0) {
                            stringMap = new HashMap<String, String>();
                            this.maps.put(path, stringMap);
                        }
                        assert stringMap != null;
                        stringMap.put(keyString, value.getString());
                    }
                    else {
                        assert stringMap == null;
                        final String currentPath = path + "/" + keyString;
                        if (!currentPath.startsWith("cyclicNameSets") || "cyclicNameSets/years/format/abbreviated".startsWith(currentPath) || "cyclicNameSets/zodiacs/format/abbreviated".startsWith(currentPath) || "cyclicNameSets/dayParts/format/abbreviated".startsWith(currentPath)) {
                            if (!this.arrays.containsKey(currentPath)) {
                                if (!this.maps.containsKey(currentPath)) {
                                    final AliasType aliasType = this.processAliasFromValue(currentPath, value);
                                    if (aliasType == AliasType.SAME_CALENDAR) {
                                        this.aliasPathPairs.add(this.aliasRelativePath);
                                        this.aliasPathPairs.add(currentPath);
                                    }
                                    else {
                                        assert aliasType == AliasType.NONE;
                                        if (value.getType() == 8) {
                                            final String[] dataArray = value.getStringArray();
                                            this.arrays.put(currentPath, dataArray);
                                        }
                                        else if (value.getType() == 2) {
                                            this.processResource(currentPath, key, value);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        private AliasType processAliasFromValue(final String currentRelativePath, final UResource.Value value) {
            if (value.getType() == 3) {
                final String aliasPath = value.getAliasString();
                if (aliasPath.startsWith("/LOCALE/calendar/") && aliasPath.length() > "/LOCALE/calendar/".length()) {
                    final int typeLimit = aliasPath.indexOf(47, "/LOCALE/calendar/".length());
                    if (typeLimit > "/LOCALE/calendar/".length()) {
                        final String aliasCalendarType = aliasPath.substring("/LOCALE/calendar/".length(), typeLimit);
                        this.aliasRelativePath = aliasPath.substring(typeLimit + 1);
                        if (this.currentCalendarType.equals(aliasCalendarType) && !currentRelativePath.equals(this.aliasRelativePath)) {
                            return AliasType.SAME_CALENDAR;
                        }
                        if (!this.currentCalendarType.equals(aliasCalendarType) && currentRelativePath.equals(this.aliasRelativePath)) {
                            if (aliasCalendarType.equals("gregorian")) {
                                return AliasType.GREGORIAN;
                            }
                            if (this.nextCalendarType == null || this.nextCalendarType.equals(aliasCalendarType)) {
                                this.nextCalendarType = aliasCalendarType;
                                return AliasType.DIFFERENT_CALENDAR;
                            }
                        }
                    }
                }
                throw new ICUException("Malformed 'calendar' alias. Path: " + aliasPath);
            }
            return AliasType.NONE;
        }
        
        private enum AliasType
        {
            SAME_CALENDAR, 
            DIFFERENT_CALENDAR, 
            GREGORIAN, 
            NONE;
        }
    }
}
