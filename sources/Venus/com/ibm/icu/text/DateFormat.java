/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.RelativeDateFormat;
import com.ibm.icu.text.DateTimePatternGenerator;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.text.UFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

public abstract class DateFormat
extends UFormat {
    protected Calendar calendar;
    protected NumberFormat numberFormat;
    public static final int ERA_FIELD = 0;
    public static final int YEAR_FIELD = 1;
    public static final int MONTH_FIELD = 2;
    public static final int DATE_FIELD = 3;
    public static final int HOUR_OF_DAY1_FIELD = 4;
    public static final int HOUR_OF_DAY0_FIELD = 5;
    public static final int MINUTE_FIELD = 6;
    public static final int SECOND_FIELD = 7;
    public static final int FRACTIONAL_SECOND_FIELD = 8;
    public static final int MILLISECOND_FIELD = 8;
    public static final int DAY_OF_WEEK_FIELD = 9;
    public static final int DAY_OF_YEAR_FIELD = 10;
    public static final int DAY_OF_WEEK_IN_MONTH_FIELD = 11;
    public static final int WEEK_OF_YEAR_FIELD = 12;
    public static final int WEEK_OF_MONTH_FIELD = 13;
    public static final int AM_PM_FIELD = 14;
    public static final int HOUR1_FIELD = 15;
    public static final int HOUR0_FIELD = 16;
    public static final int TIMEZONE_FIELD = 17;
    public static final int YEAR_WOY_FIELD = 18;
    public static final int DOW_LOCAL_FIELD = 19;
    public static final int EXTENDED_YEAR_FIELD = 20;
    public static final int JULIAN_DAY_FIELD = 21;
    public static final int MILLISECONDS_IN_DAY_FIELD = 22;
    public static final int TIMEZONE_RFC_FIELD = 23;
    public static final int TIMEZONE_GENERIC_FIELD = 24;
    public static final int STANDALONE_DAY_FIELD = 25;
    public static final int STANDALONE_MONTH_FIELD = 26;
    public static final int QUARTER_FIELD = 27;
    public static final int STANDALONE_QUARTER_FIELD = 28;
    public static final int TIMEZONE_SPECIAL_FIELD = 29;
    public static final int YEAR_NAME_FIELD = 30;
    public static final int TIMEZONE_LOCALIZED_GMT_OFFSET_FIELD = 31;
    public static final int TIMEZONE_ISO_FIELD = 32;
    public static final int TIMEZONE_ISO_LOCAL_FIELD = 33;
    @Deprecated
    static final int RELATED_YEAR = 34;
    public static final int AM_PM_MIDNIGHT_NOON_FIELD = 35;
    public static final int FLEXIBLE_DAY_PERIOD_FIELD = 36;
    @Deprecated
    public static final int TIME_SEPARATOR = 37;
    @Deprecated
    public static final int FIELD_COUNT = 38;
    private EnumSet<BooleanAttribute> booleanAttributes = EnumSet.allOf(BooleanAttribute.class);
    private DisplayContext capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
    static final int currentSerialVersion = 1;
    private int serialVersionOnStream = 1;
    private static final long serialVersionUID = 7218322306649953788L;
    public static final int NONE = -1;
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    public static final int DEFAULT = 2;
    public static final int RELATIVE = 128;
    public static final int RELATIVE_FULL = 128;
    public static final int RELATIVE_LONG = 129;
    public static final int RELATIVE_MEDIUM = 130;
    public static final int RELATIVE_SHORT = 131;
    public static final int RELATIVE_DEFAULT = 130;
    public static final String YEAR = "y";
    public static final String QUARTER = "QQQQ";
    public static final String ABBR_QUARTER = "QQQ";
    public static final String YEAR_QUARTER = "yQQQQ";
    public static final String YEAR_ABBR_QUARTER = "yQQQ";
    public static final String MONTH = "MMMM";
    public static final String ABBR_MONTH = "MMM";
    public static final String NUM_MONTH = "M";
    public static final String YEAR_MONTH = "yMMMM";
    public static final String YEAR_ABBR_MONTH = "yMMM";
    public static final String YEAR_NUM_MONTH = "yM";
    public static final String DAY = "d";
    public static final String YEAR_MONTH_DAY = "yMMMMd";
    public static final String YEAR_ABBR_MONTH_DAY = "yMMMd";
    public static final String YEAR_NUM_MONTH_DAY = "yMd";
    public static final String WEEKDAY = "EEEE";
    public static final String ABBR_WEEKDAY = "E";
    public static final String YEAR_MONTH_WEEKDAY_DAY = "yMMMMEEEEd";
    public static final String YEAR_ABBR_MONTH_WEEKDAY_DAY = "yMMMEd";
    public static final String YEAR_NUM_MONTH_WEEKDAY_DAY = "yMEd";
    public static final String MONTH_DAY = "MMMMd";
    public static final String ABBR_MONTH_DAY = "MMMd";
    public static final String NUM_MONTH_DAY = "Md";
    public static final String MONTH_WEEKDAY_DAY = "MMMMEEEEd";
    public static final String ABBR_MONTH_WEEKDAY_DAY = "MMMEd";
    public static final String NUM_MONTH_WEEKDAY_DAY = "MEd";
    @Deprecated
    public static final List<String> DATE_SKELETONS = Arrays.asList("y", "QQQQ", "QQQ", "yQQQQ", "yQQQ", "MMMM", "MMM", "M", "yMMMM", "yMMM", "yM", "d", "yMMMMd", "yMMMd", "yMd", "EEEE", "E", "yMMMMEEEEd", "yMMMEd", "yMEd", "MMMMd", "MMMd", "Md", "MMMMEEEEd", "MMMEd", "MEd");
    public static final String HOUR = "j";
    public static final String HOUR24 = "H";
    public static final String MINUTE = "m";
    public static final String HOUR_MINUTE = "jm";
    public static final String HOUR24_MINUTE = "Hm";
    public static final String SECOND = "s";
    public static final String HOUR_MINUTE_SECOND = "jms";
    public static final String HOUR24_MINUTE_SECOND = "Hms";
    public static final String MINUTE_SECOND = "ms";
    @Deprecated
    public static final List<String> TIME_SKELETONS = Arrays.asList("j", "H", "m", "jm", "Hm", "s", "jms", "Hms", "ms");
    public static final String LOCATION_TZ = "VVVV";
    public static final String GENERIC_TZ = "vvvv";
    public static final String ABBR_GENERIC_TZ = "v";
    public static final String SPECIFIC_TZ = "zzzz";
    public static final String ABBR_SPECIFIC_TZ = "z";
    public static final String ABBR_UTC_TZ = "ZZZZ";
    @Deprecated
    public static final List<String> ZONE_SKELETONS = Arrays.asList("VVVV", "vvvv", "v", "zzzz", "z", "ZZZZ");
    @Deprecated
    public static final String STANDALONE_MONTH = "LLLL";
    @Deprecated
    public static final String ABBR_STANDALONE_MONTH = "LLL";
    @Deprecated
    public static final String HOUR_MINUTE_GENERIC_TZ = "jmv";
    @Deprecated
    public static final String HOUR_MINUTE_TZ = "jmz";
    @Deprecated
    public static final String HOUR_GENERIC_TZ = "jv";
    @Deprecated
    public static final String HOUR_TZ = "jz";
    @Deprecated
    public static final String JP_ERA_2019_ROOT = "Reiwa";
    @Deprecated
    public static final String JP_ERA_2019_JA = "\u4ee4\u548c";
    @Deprecated
    public static final String JP_ERA_2019_NARROW = "R";

    @Override
    public final StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (object instanceof Calendar) {
            return this.format((Calendar)object, stringBuffer, fieldPosition);
        }
        if (object instanceof Date) {
            return this.format((Date)object, stringBuffer, fieldPosition);
        }
        if (object instanceof Number) {
            return this.format(new Date(((Number)object).longValue()), stringBuffer, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object (" + object.getClass().getName() + ") as a Date");
    }

    public abstract StringBuffer format(Calendar var1, StringBuffer var2, FieldPosition var3);

    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        this.calendar.setTime(date);
        return this.format(this.calendar, stringBuffer, fieldPosition);
    }

    public final String format(Date date) {
        return this.format(date, new StringBuffer(64), new FieldPosition(0)).toString();
    }

    public Date parse(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Date date = this.parse(string, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw new ParseException("Unparseable date: \"" + string + "\"", parsePosition.getErrorIndex());
        }
        return date;
    }

    public abstract void parse(String var1, Calendar var2, ParsePosition var3);

    public Date parse(String string, ParsePosition parsePosition) {
        Date date = null;
        int n = parsePosition.getIndex();
        TimeZone timeZone = this.calendar.getTimeZone();
        this.calendar.clear();
        this.parse(string, this.calendar, parsePosition);
        if (parsePosition.getIndex() != n) {
            try {
                date = this.calendar.getTime();
            } catch (IllegalArgumentException illegalArgumentException) {
                parsePosition.setIndex(n);
                parsePosition.setErrorIndex(n);
            }
        }
        this.calendar.setTimeZone(timeZone);
        return date;
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parse(string, parsePosition);
    }

    public static final DateFormat getTimeInstance() {
        return DateFormat.get(-1, 2, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public static final DateFormat getTimeInstance(int n) {
        return DateFormat.get(-1, n, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public static final DateFormat getTimeInstance(int n, Locale locale) {
        return DateFormat.get(-1, n, ULocale.forLocale(locale), null);
    }

    public static final DateFormat getTimeInstance(int n, ULocale uLocale) {
        return DateFormat.get(-1, n, uLocale, null);
    }

    public static final DateFormat getDateInstance() {
        return DateFormat.get(2, -1, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public static final DateFormat getDateInstance(int n) {
        return DateFormat.get(n, -1, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public static final DateFormat getDateInstance(int n, Locale locale) {
        return DateFormat.get(n, -1, ULocale.forLocale(locale), null);
    }

    public static final DateFormat getDateInstance(int n, ULocale uLocale) {
        return DateFormat.get(n, -1, uLocale, null);
    }

    public static final DateFormat getDateTimeInstance() {
        return DateFormat.get(2, 2, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public static final DateFormat getDateTimeInstance(int n, int n2) {
        return DateFormat.get(n, n2, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public static final DateFormat getDateTimeInstance(int n, int n2, Locale locale) {
        return DateFormat.get(n, n2, ULocale.forLocale(locale), null);
    }

    public static final DateFormat getDateTimeInstance(int n, int n2, ULocale uLocale) {
        return DateFormat.get(n, n2, uLocale, null);
    }

    public static final DateFormat getInstance() {
        return DateFormat.getDateTimeInstance(3, 3);
    }

    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = (NumberFormat)numberFormat.clone();
        DateFormat.fixNumberFormatForDates(this.numberFormat);
    }

    static void fixNumberFormatForDates(NumberFormat numberFormat) {
        numberFormat.setGroupingUsed(true);
        if (numberFormat instanceof DecimalFormat) {
            ((DecimalFormat)numberFormat).setDecimalSeparatorAlwaysShown(true);
        }
        numberFormat.setParseIntegerOnly(false);
        numberFormat.setMinimumFractionDigits(0);
    }

    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.calendar.setTimeZone(timeZone);
    }

    public TimeZone getTimeZone() {
        return this.calendar.getTimeZone();
    }

    public void setLenient(boolean bl) {
        this.calendar.setLenient(bl);
        this.setBooleanAttribute(BooleanAttribute.PARSE_ALLOW_NUMERIC, bl);
        this.setBooleanAttribute(BooleanAttribute.PARSE_ALLOW_WHITESPACE, bl);
    }

    public boolean isLenient() {
        return this.calendar.isLenient() && this.getBooleanAttribute(BooleanAttribute.PARSE_ALLOW_NUMERIC) && this.getBooleanAttribute(BooleanAttribute.PARSE_ALLOW_WHITESPACE);
    }

    public void setCalendarLenient(boolean bl) {
        this.calendar.setLenient(bl);
    }

    public boolean isCalendarLenient() {
        return this.calendar.isLenient();
    }

    public DateFormat setBooleanAttribute(BooleanAttribute booleanAttribute, boolean bl) {
        if (booleanAttribute.equals((Object)BooleanAttribute.PARSE_PARTIAL_MATCH)) {
            booleanAttribute = BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH;
        }
        if (bl) {
            this.booleanAttributes.add(booleanAttribute);
        } else {
            this.booleanAttributes.remove((Object)booleanAttribute);
        }
        return this;
    }

    public boolean getBooleanAttribute(BooleanAttribute booleanAttribute) {
        if (booleanAttribute == BooleanAttribute.PARSE_PARTIAL_MATCH) {
            booleanAttribute = BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH;
        }
        return this.booleanAttributes.contains((Object)booleanAttribute);
    }

    public void setContext(DisplayContext displayContext) {
        if (displayContext.type() == DisplayContext.Type.CAPITALIZATION) {
            this.capitalizationSetting = displayContext;
        }
    }

    public DisplayContext getContext(DisplayContext.Type type) {
        return type == DisplayContext.Type.CAPITALIZATION && this.capitalizationSetting != null ? this.capitalizationSetting : DisplayContext.CAPITALIZATION_NONE;
    }

    public int hashCode() {
        return this.numberFormat.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        DateFormat dateFormat = (DateFormat)object;
        return (this.calendar == null && dateFormat.calendar == null || this.calendar != null && dateFormat.calendar != null && this.calendar.isEquivalentTo(dateFormat.calendar)) && (this.numberFormat == null && dateFormat.numberFormat == null || this.numberFormat != null && dateFormat.numberFormat != null && this.numberFormat.equals(dateFormat.numberFormat)) && this.capitalizationSetting == dateFormat.capitalizationSetting;
    }

    @Override
    public Object clone() {
        DateFormat dateFormat = (DateFormat)super.clone();
        dateFormat.calendar = (Calendar)this.calendar.clone();
        if (this.numberFormat != null) {
            dateFormat.numberFormat = (NumberFormat)this.numberFormat.clone();
        }
        return dateFormat;
    }

    private static DateFormat get(int n, int n2, ULocale uLocale, Calendar calendar) {
        if (n2 != -1 && (n2 & 0x80) > 0 || n != -1 && (n & 0x80) > 0) {
            RelativeDateFormat relativeDateFormat = new RelativeDateFormat(n2, n, uLocale, calendar);
            return relativeDateFormat;
        }
        if (n2 < -1 || n2 > 3) {
            throw new IllegalArgumentException("Illegal time style " + n2);
        }
        if (n < -1 || n > 3) {
            throw new IllegalArgumentException("Illegal date style " + n);
        }
        if (calendar == null) {
            calendar = Calendar.getInstance(uLocale);
        }
        try {
            DateFormat dateFormat = calendar.getDateTimeFormat(n, n2, uLocale);
            dateFormat.setLocale(calendar.getLocale(ULocale.VALID_LOCALE), calendar.getLocale(ULocale.ACTUAL_LOCALE));
            return dateFormat;
        } catch (MissingResourceException missingResourceException) {
            return new SimpleDateFormat("M/d/yy h:mm a");
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
        }
        if (this.booleanAttributes == null) {
            this.booleanAttributes = EnumSet.allOf(BooleanAttribute.class);
        }
        this.serialVersionOnStream = 1;
    }

    protected DateFormat() {
    }

    public static final DateFormat getDateInstance(Calendar calendar, int n, Locale locale) {
        return DateFormat.getDateTimeInstance(calendar, n, -1, ULocale.forLocale(locale));
    }

    public static final DateFormat getDateInstance(Calendar calendar, int n, ULocale uLocale) {
        return DateFormat.getDateTimeInstance(calendar, n, -1, uLocale);
    }

    public static final DateFormat getTimeInstance(Calendar calendar, int n, Locale locale) {
        return DateFormat.getDateTimeInstance(calendar, -1, n, ULocale.forLocale(locale));
    }

    public static final DateFormat getTimeInstance(Calendar calendar, int n, ULocale uLocale) {
        return DateFormat.getDateTimeInstance(calendar, -1, n, uLocale);
    }

    public static final DateFormat getDateTimeInstance(Calendar calendar, int n, int n2, Locale locale) {
        return DateFormat.getDateTimeInstance(calendar, n, n2, ULocale.forLocale(locale));
    }

    public static final DateFormat getDateTimeInstance(Calendar calendar, int n, int n2, ULocale uLocale) {
        if (calendar == null) {
            throw new IllegalArgumentException("Calendar must be supplied");
        }
        return DateFormat.get(n, n2, uLocale, calendar);
    }

    public static final DateFormat getInstance(Calendar calendar, Locale locale) {
        return DateFormat.getDateTimeInstance(calendar, 3, 3, ULocale.forLocale(locale));
    }

    public static final DateFormat getInstance(Calendar calendar, ULocale uLocale) {
        return DateFormat.getDateTimeInstance(calendar, 3, 3, uLocale);
    }

    public static final DateFormat getInstance(Calendar calendar) {
        return DateFormat.getInstance(calendar, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final DateFormat getDateInstance(Calendar calendar, int n) {
        return DateFormat.getDateInstance(calendar, n, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final DateFormat getTimeInstance(Calendar calendar, int n) {
        return DateFormat.getTimeInstance(calendar, n, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final DateFormat getDateTimeInstance(Calendar calendar, int n, int n2) {
        return DateFormat.getDateTimeInstance(calendar, n, n2, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final DateFormat getInstanceForSkeleton(String string) {
        return DateFormat.getPatternInstance(string, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final DateFormat getInstanceForSkeleton(String string, Locale locale) {
        return DateFormat.getPatternInstance(string, ULocale.forLocale(locale));
    }

    public static final DateFormat getInstanceForSkeleton(String string, ULocale uLocale) {
        DateTimePatternGenerator dateTimePatternGenerator = DateTimePatternGenerator.getInstance(uLocale);
        String string2 = dateTimePatternGenerator.getBestPattern(string);
        return new SimpleDateFormat(string2, uLocale);
    }

    public static final DateFormat getInstanceForSkeleton(Calendar calendar, String string, Locale locale) {
        return DateFormat.getPatternInstance(calendar, string, ULocale.forLocale(locale));
    }

    public static final DateFormat getInstanceForSkeleton(Calendar calendar, String string, ULocale uLocale) {
        if (calendar != null) {
            uLocale = uLocale.setKeywordValue("calendar", calendar.getType());
        }
        DateTimePatternGenerator dateTimePatternGenerator = DateTimePatternGenerator.getInstance(uLocale);
        String string2 = dateTimePatternGenerator.getBestPattern(string);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(string2, uLocale);
        simpleDateFormat.setCalendar(calendar);
        return simpleDateFormat;
    }

    public static final DateFormat getPatternInstance(String string) {
        return DateFormat.getInstanceForSkeleton(string);
    }

    public static final DateFormat getPatternInstance(String string, Locale locale) {
        return DateFormat.getInstanceForSkeleton(string, locale);
    }

    public static final DateFormat getPatternInstance(String string, ULocale uLocale) {
        return DateFormat.getInstanceForSkeleton(string, uLocale);
    }

    public static final DateFormat getPatternInstance(Calendar calendar, String string, Locale locale) {
        return DateFormat.getInstanceForSkeleton(calendar, string, locale);
    }

    public static final DateFormat getPatternInstance(Calendar calendar, String string, ULocale uLocale) {
        return DateFormat.getInstanceForSkeleton(calendar, string, uLocale);
    }

    public static class Field
    extends Format.Field {
        private static final long serialVersionUID = -3627456821000730829L;
        private static final int CAL_FIELD_COUNT;
        private static final Field[] CAL_FIELDS;
        private static final Map<String, Field> FIELD_NAME_MAP;
        public static final Field AM_PM;
        public static final Field DAY_OF_MONTH;
        public static final Field DAY_OF_WEEK;
        public static final Field DAY_OF_WEEK_IN_MONTH;
        public static final Field DAY_OF_YEAR;
        public static final Field ERA;
        public static final Field HOUR_OF_DAY0;
        public static final Field HOUR_OF_DAY1;
        public static final Field HOUR0;
        public static final Field HOUR1;
        public static final Field MILLISECOND;
        public static final Field MINUTE;
        public static final Field MONTH;
        public static final Field SECOND;
        public static final Field TIME_ZONE;
        public static final Field WEEK_OF_MONTH;
        public static final Field WEEK_OF_YEAR;
        public static final Field YEAR;
        public static final Field DOW_LOCAL;
        public static final Field EXTENDED_YEAR;
        public static final Field JULIAN_DAY;
        public static final Field MILLISECONDS_IN_DAY;
        public static final Field YEAR_WOY;
        public static final Field QUARTER;
        @Deprecated
        public static final Field RELATED_YEAR;
        public static final Field AM_PM_MIDNIGHT_NOON;
        public static final Field FLEXIBLE_DAY_PERIOD;
        @Deprecated
        public static final Field TIME_SEPARATOR;
        private final int calendarField;

        protected Field(String string, int n) {
            super(string);
            this.calendarField = n;
            if (this.getClass() == Field.class) {
                FIELD_NAME_MAP.put(string, this);
                if (n >= 0 && n < CAL_FIELD_COUNT) {
                    Field.CAL_FIELDS[n] = this;
                }
            }
        }

        public static Field ofCalendarField(int n) {
            if (n < 0 || n >= CAL_FIELD_COUNT) {
                throw new IllegalArgumentException("Calendar field number is out of range");
            }
            return CAL_FIELDS[n];
        }

        public int getCalendarField() {
            return this.calendarField;
        }

        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of DateFormat.Field must implement readResolve.");
            }
            Field field = FIELD_NAME_MAP.get(this.getName());
            if (field == null) {
                throw new InvalidObjectException("Unknown attribute name.");
            }
            return field;
        }

        static {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            CAL_FIELD_COUNT = gregorianCalendar.getFieldCount();
            CAL_FIELDS = new Field[CAL_FIELD_COUNT];
            FIELD_NAME_MAP = new HashMap<String, Field>(CAL_FIELD_COUNT);
            AM_PM = new Field("am pm", 9);
            DAY_OF_MONTH = new Field("day of month", 5);
            DAY_OF_WEEK = new Field("day of week", 7);
            DAY_OF_WEEK_IN_MONTH = new Field("day of week in month", 8);
            DAY_OF_YEAR = new Field("day of year", 6);
            ERA = new Field("era", 0);
            HOUR_OF_DAY0 = new Field("hour of day", 11);
            HOUR_OF_DAY1 = new Field("hour of day 1", -1);
            HOUR0 = new Field("hour", 10);
            HOUR1 = new Field("hour 1", -1);
            MILLISECOND = new Field("millisecond", 14);
            MINUTE = new Field("minute", 12);
            MONTH = new Field("month", 2);
            SECOND = new Field("second", 13);
            TIME_ZONE = new Field("time zone", -1);
            WEEK_OF_MONTH = new Field("week of month", 4);
            WEEK_OF_YEAR = new Field("week of year", 3);
            YEAR = new Field("year", 1);
            DOW_LOCAL = new Field("local day of week", 18);
            EXTENDED_YEAR = new Field("extended year", 19);
            JULIAN_DAY = new Field("Julian day", 20);
            MILLISECONDS_IN_DAY = new Field("milliseconds in day", 21);
            YEAR_WOY = new Field("year for week of year", 17);
            QUARTER = new Field("quarter", -1);
            RELATED_YEAR = new Field("related year", -1);
            AM_PM_MIDNIGHT_NOON = new Field("am/pm/midnight/noon", -1);
            FLEXIBLE_DAY_PERIOD = new Field("flexible day period", -1);
            TIME_SEPARATOR = new Field("time separator", -1);
        }
    }

    public static enum BooleanAttribute {
        PARSE_ALLOW_WHITESPACE,
        PARSE_ALLOW_NUMERIC,
        PARSE_MULTIPLE_PATTERNS_FOR_MATCH,
        PARSE_PARTIAL_LITERAL_MATCH,
        PARSE_PARTIAL_MATCH;

    }
}

