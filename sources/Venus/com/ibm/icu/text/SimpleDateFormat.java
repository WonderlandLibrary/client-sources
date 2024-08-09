/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.DateNumberFormat;
import com.ibm.icu.impl.DayPeriodRules;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.MessageFormat;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.text.TimeZoneFormat;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.HebrewCalendar;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneTransition;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.UUID;

public class SimpleDateFormat
extends DateFormat {
    private static final long serialVersionUID = 4774881970558875024L;
    static final int currentSerialVersion = 2;
    static boolean DelayedHebrewMonthCheck;
    private static final int[] CALENDAR_FIELD_TO_LEVEL;
    private static final int[] PATTERN_CHAR_TO_LEVEL;
    private static final boolean[] PATTERN_CHAR_IS_SYNTAX;
    private static final int HEBREW_CAL_CUR_MILLENIUM_START_YEAR = 5000;
    private static final int HEBREW_CAL_CUR_MILLENIUM_END_YEAR = 6000;
    private int serialVersionOnStream = 2;
    private String pattern;
    private String override;
    private HashMap<String, NumberFormat> numberFormatters;
    private HashMap<Character, String> overrideMap;
    private DateFormatSymbols formatData;
    private transient ULocale locale;
    private Date defaultCenturyStart;
    private transient int defaultCenturyStartYear;
    private transient long defaultCenturyBase;
    private static final int millisPerHour = 3600000;
    private static final int ISOSpecialEra = -32000;
    private static final String SUPPRESS_NEGATIVE_PREFIX = "\uab00";
    private transient boolean useFastFormat;
    private volatile TimeZoneFormat tzFormat;
    private transient BreakIterator capitalizationBrkIter = null;
    private transient boolean hasMinute;
    private transient boolean hasSecond;
    private transient boolean hasHanYearChar;
    private static ULocale cachedDefaultLocale;
    private static String cachedDefaultPattern;
    private static final String FALLBACKPATTERN = "yy/MM/dd HH:mm";
    private static final int[] PATTERN_CHAR_TO_INDEX;
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD;
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD;
    private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE;
    private static ICUCache<String, Object[]> PARSED_PATTERN_CACHE;
    private transient Object[] patternItems;
    private transient boolean useLocalZeroPaddingNumberFormat;
    private transient char[] decDigits;
    private transient char[] decimalBuf;
    private static final int DECIMAL_BUF_SIZE = 10;
    private static final String NUMERIC_FORMAT_CHARS = "ADdFgHhKkmrSsuWwYy";
    private static final String NUMERIC_FORMAT_CHARS2 = "ceLMQq";
    static final UnicodeSet DATE_PATTERN_TYPE;
    static final boolean $assertionsDisabled;

    private static int getLevelFromChar(char c) {
        return c < PATTERN_CHAR_TO_LEVEL.length ? PATTERN_CHAR_TO_LEVEL[c & 0xFF] : -1;
    }

    private static boolean isSyntaxChar(char c) {
        return c < PATTERN_CHAR_IS_SYNTAX.length ? PATTERN_CHAR_IS_SYNTAX[c & 0xFF] : false;
    }

    public SimpleDateFormat() {
        this(SimpleDateFormat.getDefaultPattern(), null, null, null, null, true, null);
    }

    public SimpleDateFormat(String string) {
        this(string, null, null, null, null, true, null);
    }

    public SimpleDateFormat(String string, Locale locale) {
        this(string, null, null, null, ULocale.forLocale(locale), true, null);
    }

    public SimpleDateFormat(String string, ULocale uLocale) {
        this(string, null, null, null, uLocale, true, null);
    }

    public SimpleDateFormat(String string, String string2, ULocale uLocale) {
        this(string, null, null, null, uLocale, false, string2);
    }

    public SimpleDateFormat(String string, DateFormatSymbols dateFormatSymbols) {
        this(string, (DateFormatSymbols)dateFormatSymbols.clone(), null, null, null, true, null);
    }

    @Deprecated
    public SimpleDateFormat(String string, DateFormatSymbols dateFormatSymbols, ULocale uLocale) {
        this(string, (DateFormatSymbols)dateFormatSymbols.clone(), null, null, uLocale, true, null);
    }

    SimpleDateFormat(String string, DateFormatSymbols dateFormatSymbols, Calendar calendar, ULocale uLocale, boolean bl, String string2) {
        this(string, (DateFormatSymbols)dateFormatSymbols.clone(), (Calendar)calendar.clone(), null, uLocale, bl, string2);
    }

    private SimpleDateFormat(String string, DateFormatSymbols dateFormatSymbols, Calendar calendar, NumberFormat numberFormat, ULocale uLocale, boolean bl, String string2) {
        this.pattern = string;
        this.formatData = dateFormatSymbols;
        this.calendar = calendar;
        this.numberFormat = numberFormat;
        this.locale = uLocale;
        this.useFastFormat = bl;
        this.override = string2;
        this.initialize();
    }

    @Deprecated
    public static SimpleDateFormat getInstance(Calendar.FormatConfiguration formatConfiguration) {
        String string = formatConfiguration.getOverrideString();
        boolean bl = string != null && string.length() > 0;
        return new SimpleDateFormat(formatConfiguration.getPatternString(), formatConfiguration.getDateFormatSymbols(), formatConfiguration.getCalendar(), null, formatConfiguration.getLocale(), bl, formatConfiguration.getOverrideString());
    }

    private void initialize() {
        if (this.locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        if (this.formatData == null) {
            this.formatData = new DateFormatSymbols(this.locale);
        }
        if (this.calendar == null) {
            this.calendar = Calendar.getInstance(this.locale);
        }
        if (this.numberFormat == null) {
            NumberingSystem numberingSystem = NumberingSystem.getInstance(this.locale);
            String string = numberingSystem.getDescription();
            if (numberingSystem.isAlgorithmic() || string.length() != 10) {
                this.numberFormat = NumberFormat.getInstance(this.locale);
            } else {
                String string2 = numberingSystem.getName();
                this.numberFormat = new DateNumberFormat(this.locale, string, string2);
            }
        }
        if (this.numberFormat instanceof DecimalFormat) {
            SimpleDateFormat.fixNumberFormatForDates(this.numberFormat);
        }
        this.defaultCenturyBase = System.currentTimeMillis();
        this.setLocale(this.calendar.getLocale(ULocale.VALID_LOCALE), this.calendar.getLocale(ULocale.ACTUAL_LOCALE));
        this.initLocalZeroPaddingNumberFormat();
        this.parsePattern();
        if (this.override == null && this.hasHanYearChar && this.calendar != null && this.calendar.getType().equals("japanese") && this.locale != null && this.locale.getLanguage().equals("ja")) {
            this.override = "y=jpanyear";
        }
        if (this.override != null) {
            this.initNumberFormatters(this.locale);
        }
    }

    private synchronized void initializeTimeZoneFormat(boolean bl) {
        if (bl || this.tzFormat == null) {
            this.tzFormat = TimeZoneFormat.getInstance(this.locale);
            String string = null;
            if (this.numberFormat instanceof DecimalFormat) {
                DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols();
                String[] stringArray = decimalFormatSymbols.getDigitStringsLocal();
                StringBuilder stringBuilder = new StringBuilder();
                for (String string2 : stringArray) {
                    stringBuilder.append(string2);
                }
                string = stringBuilder.toString();
            } else if (this.numberFormat instanceof DateNumberFormat) {
                string = new String(((DateNumberFormat)this.numberFormat).getDigits());
            }
            if (string != null && !this.tzFormat.getGMTOffsetDigits().equals(string)) {
                if (this.tzFormat.isFrozen()) {
                    this.tzFormat = this.tzFormat.cloneAsThawed();
                }
                this.tzFormat.setGMTOffsetDigits(string);
            }
        }
    }

    private TimeZoneFormat tzFormat() {
        if (this.tzFormat == null) {
            this.initializeTimeZoneFormat(false);
        }
        return this.tzFormat;
    }

    private static synchronized String getDefaultPattern() {
        ULocale uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        if (!uLocale.equals(cachedDefaultLocale)) {
            cachedDefaultLocale = uLocale;
            Calendar calendar = Calendar.getInstance(cachedDefaultLocale);
            try {
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", cachedDefaultLocale);
                String string = "calendar/" + calendar.getType() + "/DateTimePatterns";
                ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.findWithFallback(string);
                if (iCUResourceBundle2 == null) {
                    iCUResourceBundle2 = iCUResourceBundle.findWithFallback("calendar/gregorian/DateTimePatterns");
                }
                if (iCUResourceBundle2 == null || iCUResourceBundle2.getSize() < 9) {
                    cachedDefaultPattern = FALLBACKPATTERN;
                } else {
                    int n = 8;
                    if (iCUResourceBundle2.getSize() >= 13) {
                        n += 4;
                    }
                    String string2 = iCUResourceBundle2.getString(n);
                    cachedDefaultPattern = SimpleFormatterImpl.formatRawPattern(string2, 2, 2, iCUResourceBundle2.getString(3), iCUResourceBundle2.getString(7));
                }
            } catch (MissingResourceException missingResourceException) {
                cachedDefaultPattern = FALLBACKPATTERN;
            }
        }
        return cachedDefaultPattern;
    }

    private void parseAmbiguousDatesAsAfter(Date date) {
        this.defaultCenturyStart = date;
        this.calendar.setTime(date);
        this.defaultCenturyStartYear = this.calendar.get(1);
    }

    private void initializeDefaultCenturyStart(long l) {
        this.defaultCenturyBase = l;
        Calendar calendar = (Calendar)this.calendar.clone();
        calendar.setTimeInMillis(l);
        calendar.add(1, -80);
        this.defaultCenturyStart = calendar.getTime();
        this.defaultCenturyStartYear = calendar.get(1);
    }

    private Date getDefaultCenturyStart() {
        if (this.defaultCenturyStart == null) {
            this.initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStart;
    }

    private int getDefaultCenturyStartYear() {
        if (this.defaultCenturyStart == null) {
            this.initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStartYear;
    }

    public void set2DigitYearStart(Date date) {
        this.parseAmbiguousDatesAsAfter(date);
    }

    public Date get2DigitYearStart() {
        return this.getDefaultCenturyStart();
    }

    @Override
    public void setContext(DisplayContext displayContext) {
        super.setContext(displayContext);
        if (this.capitalizationBrkIter == null && (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
        }
    }

    @Override
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format(calendar, stringBuffer, fieldPosition, null);
    }

    StringBuffer format(Calendar calendar, StringBuffer stringBuffer, FieldPosition fieldPosition, List<FieldPosition> list) {
        TimeZone timeZone = null;
        if (calendar != this.calendar && !calendar.getType().equals(this.calendar.getType())) {
            this.calendar.setTimeInMillis(calendar.getTimeInMillis());
            timeZone = this.calendar.getTimeZone();
            this.calendar.setTimeZone(calendar.getTimeZone());
            calendar = this.calendar;
        }
        StringBuffer stringBuffer2 = this.format(calendar, this.getContext(DisplayContext.Type.CAPITALIZATION), stringBuffer, fieldPosition, list);
        if (timeZone != null) {
            this.calendar.setTimeZone(timeZone);
        }
        return stringBuffer2;
    }

    private StringBuffer format(Calendar calendar, DisplayContext displayContext, StringBuffer stringBuffer, FieldPosition fieldPosition, List<FieldPosition> list) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        Object[] objectArray = this.getPatternItems();
        for (int i = 0; i < objectArray.length; ++i) {
            int n;
            if (objectArray[i] instanceof String) {
                stringBuffer.append((String)objectArray[i]);
                continue;
            }
            PatternItem patternItem = (PatternItem)objectArray[i];
            int n2 = 0;
            if (list != null) {
                n2 = stringBuffer.length();
            }
            if (this.useFastFormat) {
                this.subFormat(stringBuffer, patternItem.type, patternItem.length, stringBuffer.length(), i, displayContext, fieldPosition, calendar);
            } else {
                stringBuffer.append(this.subFormat(patternItem.type, patternItem.length, stringBuffer.length(), i, displayContext, fieldPosition, calendar));
            }
            if (list == null || (n = stringBuffer.length()) - n2 <= 0) continue;
            DateFormat.Field field = this.patternCharToDateFormatField(patternItem.type);
            FieldPosition fieldPosition2 = new FieldPosition(field);
            fieldPosition2.setBeginIndex(n2);
            fieldPosition2.setEndIndex(n);
            list.add(fieldPosition2);
        }
        return stringBuffer;
    }

    private static int getIndexFromChar(char c) {
        return c < PATTERN_CHAR_TO_INDEX.length ? PATTERN_CHAR_TO_INDEX[c & 0xFF] : -1;
    }

    protected DateFormat.Field patternCharToDateFormatField(char c) {
        int n = SimpleDateFormat.getIndexFromChar(c);
        if (n != -1) {
            return PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[n];
        }
        return null;
    }

    protected String subFormat(char c, int n, int n2, FieldPosition fieldPosition, DateFormatSymbols dateFormatSymbols, Calendar calendar) throws IllegalArgumentException {
        return this.subFormat(c, n, n2, 0, DisplayContext.CAPITALIZATION_NONE, fieldPosition, calendar);
    }

    @Deprecated
    protected String subFormat(char c, int n, int n2, int n3, DisplayContext displayContext, FieldPosition fieldPosition, Calendar calendar) {
        StringBuffer stringBuffer = new StringBuffer();
        this.subFormat(stringBuffer, c, n, n2, n3, displayContext, fieldPosition, calendar);
        return stringBuffer.toString();
    }

    @Deprecated
    protected void subFormat(StringBuffer stringBuffer, char c, int n, int n2, int n3, DisplayContext displayContext, FieldPosition fieldPosition, Calendar calendar) {
        int n4;
        int n5 = Integer.MAX_VALUE;
        int n6 = stringBuffer.length();
        TimeZone timeZone = calendar.getTimeZone();
        long l = calendar.getTimeInMillis();
        String string = null;
        int n7 = SimpleDateFormat.getIndexFromChar(c);
        if (n7 == -1) {
            if (c == 'l') {
                return;
            }
            throw new IllegalArgumentException("Illegal pattern character '" + c + "' in \"" + this.pattern + '\"');
        }
        int n8 = PATTERN_INDEX_TO_CALENDAR_FIELD[n7];
        int n9 = 0;
        if (n8 >= 0) {
            n9 = n7 != 34 ? calendar.get(n8) : calendar.getRelatedYear();
        }
        NumberFormat numberFormat = this.getNumberFormat(c);
        DateFormatSymbols.CapitalizationContextUsage capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.OTHER;
        switch (n7) {
            case 0: {
                if (calendar.getType().equals("chinese") || calendar.getType().equals("dangi")) {
                    this.zeroPaddingNumber(numberFormat, stringBuffer, n9, 1, 9);
                    break;
                }
                if (n == 5) {
                    SimpleDateFormat.safeAppend(this.formatData.narrowEras, n9, stringBuffer);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.ERA_NARROW;
                    break;
                }
                if (n == 4) {
                    SimpleDateFormat.safeAppend(this.formatData.eraNames, n9, stringBuffer);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.ERA_WIDE;
                    break;
                }
                SimpleDateFormat.safeAppend(this.formatData.eras, n9, stringBuffer);
                capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.ERA_ABBREV;
                break;
            }
            case 30: {
                if (this.formatData.shortYearNames != null && n9 <= this.formatData.shortYearNames.length) {
                    SimpleDateFormat.safeAppend(this.formatData.shortYearNames, n9 - 1, stringBuffer);
                    break;
                }
            }
            case 1: 
            case 18: {
                if (this.override != null && (this.override.compareTo("hebr") == 0 || this.override.indexOf("y=hebr") >= 0) && n9 > 5000 && n9 < 6000) {
                    n9 -= 5000;
                }
                if (n == 2) {
                    this.zeroPaddingNumber(numberFormat, stringBuffer, n9, 2, 2);
                    break;
                }
                this.zeroPaddingNumber(numberFormat, stringBuffer, n9, n, Integer.MAX_VALUE);
                break;
            }
            case 2: 
            case 26: {
                if (calendar.getType().equals("hebrew")) {
                    n4 = HebrewCalendar.isLeapYear(calendar.get(1));
                    if (n4 != 0 && n9 == 6 && n >= 3) {
                        n9 = 13;
                    }
                    if (n4 == 0 && n9 >= 6 && n < 3) {
                        --n9;
                    }
                }
                int n10 = n4 = this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7 ? calendar.get(22) : 0;
                if (n == 5) {
                    if (n7 == 2) {
                        SimpleDateFormat.safeAppendWithMonthPattern(this.formatData.narrowMonths, n9, stringBuffer, n4 != 0 ? this.formatData.leapMonthPatterns[2] : null);
                    } else {
                        SimpleDateFormat.safeAppendWithMonthPattern(this.formatData.standaloneNarrowMonths, n9, stringBuffer, n4 != 0 ? this.formatData.leapMonthPatterns[5] : null);
                    }
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_NARROW;
                    break;
                }
                if (n == 4) {
                    if (n7 == 2) {
                        SimpleDateFormat.safeAppendWithMonthPattern(this.formatData.months, n9, stringBuffer, n4 != 0 ? this.formatData.leapMonthPatterns[0] : null);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                        break;
                    }
                    SimpleDateFormat.safeAppendWithMonthPattern(this.formatData.standaloneMonths, n9, stringBuffer, n4 != 0 ? this.formatData.leapMonthPatterns[3] : null);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                    break;
                }
                if (n == 3) {
                    if (n7 == 2) {
                        SimpleDateFormat.safeAppendWithMonthPattern(this.formatData.shortMonths, n9, stringBuffer, n4 != 0 ? this.formatData.leapMonthPatterns[1] : null);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                        break;
                    }
                    SimpleDateFormat.safeAppendWithMonthPattern(this.formatData.standaloneShortMonths, n9, stringBuffer, n4 != 0 ? this.formatData.leapMonthPatterns[4] : null);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                    break;
                }
                StringBuffer stringBuffer2 = new StringBuffer();
                this.zeroPaddingNumber(numberFormat, stringBuffer2, n9 + 1, n, Integer.MAX_VALUE);
                String[] stringArray = new String[]{stringBuffer2.toString()};
                SimpleDateFormat.safeAppendWithMonthPattern(stringArray, 0, stringBuffer, n4 != 0 ? this.formatData.leapMonthPatterns[6] : null);
                break;
            }
            case 4: {
                if (n9 == 0) {
                    this.zeroPaddingNumber(numberFormat, stringBuffer, calendar.getMaximum(11) + 1, n, Integer.MAX_VALUE);
                    break;
                }
                this.zeroPaddingNumber(numberFormat, stringBuffer, n9, n, Integer.MAX_VALUE);
                break;
            }
            case 8: {
                this.numberFormat.setMinimumIntegerDigits(Math.min(3, n));
                this.numberFormat.setMaximumIntegerDigits(Integer.MAX_VALUE);
                if (n == 1) {
                    n9 /= 100;
                } else if (n == 2) {
                    n9 /= 10;
                }
                FieldPosition fieldPosition2 = new FieldPosition(-1);
                this.numberFormat.format(n9, stringBuffer, fieldPosition2);
                if (n <= 3) break;
                this.numberFormat.setMinimumIntegerDigits(n - 3);
                this.numberFormat.format(0L, stringBuffer, fieldPosition2);
                break;
            }
            case 19: {
                if (n < 3) {
                    this.zeroPaddingNumber(numberFormat, stringBuffer, n9, n, Integer.MAX_VALUE);
                    break;
                }
                n9 = calendar.get(7);
            }
            case 9: {
                if (n == 5) {
                    SimpleDateFormat.safeAppend(this.formatData.narrowWeekdays, n9, stringBuffer);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
                    break;
                }
                if (n == 4) {
                    SimpleDateFormat.safeAppend(this.formatData.weekdays, n9, stringBuffer);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                    break;
                }
                if (n == 6 && this.formatData.shorterWeekdays != null) {
                    SimpleDateFormat.safeAppend(this.formatData.shorterWeekdays, n9, stringBuffer);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                    break;
                }
                SimpleDateFormat.safeAppend(this.formatData.shortWeekdays, n9, stringBuffer);
                capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                break;
            }
            case 14: {
                if (n < 5 || this.formatData.ampmsNarrow == null) {
                    SimpleDateFormat.safeAppend(this.formatData.ampms, n9, stringBuffer);
                    break;
                }
                SimpleDateFormat.safeAppend(this.formatData.ampmsNarrow, n9, stringBuffer);
                break;
            }
            case 15: {
                if (n9 == 0) {
                    this.zeroPaddingNumber(numberFormat, stringBuffer, calendar.getLeastMaximum(10) + 1, n, Integer.MAX_VALUE);
                    break;
                }
                this.zeroPaddingNumber(numberFormat, stringBuffer, n9, n, Integer.MAX_VALUE);
                break;
            }
            case 17: {
                if (n < 4) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.SPECIFIC_SHORT, timeZone, l);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
                } else {
                    string = this.tzFormat().format(TimeZoneFormat.Style.SPECIFIC_LONG, timeZone, l);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
                }
                stringBuffer.append(string);
                break;
            }
            case 23: {
                string = n < 4 ? this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, timeZone, l) : (n == 5 ? this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, timeZone, l) : this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, timeZone, l));
                stringBuffer.append(string);
                break;
            }
            case 24: {
                if (n == 1) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_SHORT, timeZone, l);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
                } else if (n == 4) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_LONG, timeZone, l);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
                }
                stringBuffer.append(string);
                break;
            }
            case 29: {
                if (n == 1) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ZONE_ID_SHORT, timeZone, l);
                } else if (n == 2) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ZONE_ID, timeZone, l);
                } else if (n == 3) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.EXEMPLAR_LOCATION, timeZone, l);
                } else if (n == 4) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_LOCATION, timeZone, l);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.ZONE_LONG;
                }
                stringBuffer.append(string);
                break;
            }
            case 31: {
                if (n == 1) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT_SHORT, timeZone, l);
                } else if (n == 4) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, timeZone, l);
                }
                stringBuffer.append(string);
                break;
            }
            case 32: {
                if (n == 1) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_SHORT, timeZone, l);
                } else if (n == 2) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FIXED, timeZone, l);
                } else if (n == 3) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FIXED, timeZone, l);
                } else if (n == 4) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FULL, timeZone, l);
                } else if (n == 5) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, timeZone, l);
                }
                stringBuffer.append(string);
                break;
            }
            case 33: {
                if (n == 1) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT, timeZone, l);
                } else if (n == 2) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED, timeZone, l);
                } else if (n == 3) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED, timeZone, l);
                } else if (n == 4) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, timeZone, l);
                } else if (n == 5) {
                    string = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL, timeZone, l);
                }
                stringBuffer.append(string);
                break;
            }
            case 25: {
                if (n < 3) {
                    this.zeroPaddingNumber(numberFormat, stringBuffer, n9, 1, Integer.MAX_VALUE);
                    break;
                }
                n9 = calendar.get(7);
                if (n == 5) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneNarrowWeekdays, n9, stringBuffer);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
                    break;
                }
                if (n == 4) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneWeekdays, n9, stringBuffer);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                    break;
                }
                if (n == 6 && this.formatData.standaloneShorterWeekdays != null) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneShorterWeekdays, n9, stringBuffer);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                    break;
                }
                SimpleDateFormat.safeAppend(this.formatData.standaloneShortWeekdays, n9, stringBuffer);
                capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                break;
            }
            case 27: {
                if (n >= 4) {
                    SimpleDateFormat.safeAppend(this.formatData.quarters, n9 / 3, stringBuffer);
                    break;
                }
                if (n == 3) {
                    SimpleDateFormat.safeAppend(this.formatData.shortQuarters, n9 / 3, stringBuffer);
                    break;
                }
                this.zeroPaddingNumber(numberFormat, stringBuffer, n9 / 3 + 1, n, Integer.MAX_VALUE);
                break;
            }
            case 28: {
                if (n >= 4) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneQuarters, n9 / 3, stringBuffer);
                    break;
                }
                if (n == 3) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneShortQuarters, n9 / 3, stringBuffer);
                    break;
                }
                this.zeroPaddingNumber(numberFormat, stringBuffer, n9 / 3 + 1, n, Integer.MAX_VALUE);
                break;
            }
            case 35: {
                int n11 = calendar.get(11);
                String string2 = null;
                if (!(n11 != 12 || this.hasMinute && calendar.get(12) != 0 || this.hasSecond && calendar.get(13) != 0)) {
                    n9 = calendar.get(9);
                    string2 = n <= 3 ? this.formatData.abbreviatedDayPeriods[n9] : (n == 4 || n > 5 ? this.formatData.wideDayPeriods[n9] : this.formatData.narrowDayPeriods[n9]);
                }
                if (string2 == null) {
                    this.subFormat(stringBuffer, 'a', n, n2, n3, displayContext, fieldPosition, calendar);
                    break;
                }
                stringBuffer.append(string2);
                break;
            }
            case 36: {
                int n12;
                DayPeriodRules dayPeriodRules = DayPeriodRules.getInstance(this.getLocale());
                if (dayPeriodRules == null) {
                    this.subFormat(stringBuffer, 'a', n, n2, n3, displayContext, fieldPosition, calendar);
                    break;
                }
                int n13 = calendar.get(11);
                int n14 = 0;
                int n15 = 0;
                if (this.hasMinute) {
                    n14 = calendar.get(12);
                }
                if (this.hasSecond) {
                    n15 = calendar.get(13);
                }
                DayPeriodRules.DayPeriod dayPeriod = n13 == 0 && n14 == 0 && n15 == 0 && dayPeriodRules.hasMidnight() ? DayPeriodRules.DayPeriod.MIDNIGHT : (n13 == 12 && n14 == 0 && n15 == 0 && dayPeriodRules.hasNoon() ? DayPeriodRules.DayPeriod.NOON : dayPeriodRules.getDayPeriodForHour(n13));
                if (!$assertionsDisabled && dayPeriod == null) {
                    throw new AssertionError();
                }
                String string3 = null;
                if (dayPeriod != DayPeriodRules.DayPeriod.AM && dayPeriod != DayPeriodRules.DayPeriod.PM && dayPeriod != DayPeriodRules.DayPeriod.MIDNIGHT) {
                    n12 = dayPeriod.ordinal();
                    string3 = n <= 3 ? this.formatData.abbreviatedDayPeriods[n12] : (n == 4 || n > 5 ? this.formatData.wideDayPeriods[n12] : this.formatData.narrowDayPeriods[n12]);
                }
                if (string3 == null && (dayPeriod == DayPeriodRules.DayPeriod.MIDNIGHT || dayPeriod == DayPeriodRules.DayPeriod.NOON)) {
                    dayPeriod = dayPeriodRules.getDayPeriodForHour(n13);
                    n12 = dayPeriod.ordinal();
                    string3 = n <= 3 ? this.formatData.abbreviatedDayPeriods[n12] : (n == 4 || n > 5 ? this.formatData.wideDayPeriods[n12] : this.formatData.narrowDayPeriods[n12]);
                }
                if (dayPeriod == DayPeriodRules.DayPeriod.AM || dayPeriod == DayPeriodRules.DayPeriod.PM || string3 == null) {
                    this.subFormat(stringBuffer, 'a', n, n2, n3, displayContext, fieldPosition, calendar);
                    break;
                }
                stringBuffer.append(string3);
                break;
            }
            case 37: {
                stringBuffer.append(this.formatData.getTimeSeparatorString());
                break;
            }
            default: {
                this.zeroPaddingNumber(numberFormat, stringBuffer, n9, n, Integer.MAX_VALUE);
            }
        }
        if (n3 == 0 && displayContext != null && stringBuffer.length() > n6 && UCharacter.isLowerCase(stringBuffer.codePointAt(n6))) {
            n4 = 0;
            switch (1.$SwitchMap$com$ibm$icu$text$DisplayContext[displayContext.ordinal()]) {
                case 1: {
                    n4 = 1;
                    break;
                }
                case 2: 
                case 3: {
                    if (this.formatData.capitalization == null) break;
                    boolean[] blArray = this.formatData.capitalization.get((Object)capitalizationContextUsage);
                    n4 = displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? blArray[0] : blArray[1];
                    break;
                }
            }
            if (n4 != 0) {
                if (this.capitalizationBrkIter == null) {
                    this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
                }
                BreakIterator breakIterator = (BreakIterator)this.capitalizationBrkIter.clone();
                String string4 = stringBuffer.substring(n6);
                String string5 = UCharacter.toTitleCase(this.locale, string4, breakIterator, 768);
                stringBuffer.replace(n6, stringBuffer.length(), string5);
            }
        }
        if (fieldPosition.getBeginIndex() == fieldPosition.getEndIndex()) {
            if (fieldPosition.getField() == PATTERN_INDEX_TO_DATE_FORMAT_FIELD[n7]) {
                fieldPosition.setBeginIndex(n2);
                fieldPosition.setEndIndex(n2 + stringBuffer.length() - n6);
            } else if (fieldPosition.getFieldAttribute() == PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[n7]) {
                fieldPosition.setBeginIndex(n2);
                fieldPosition.setEndIndex(n2 + stringBuffer.length() - n6);
            }
        }
    }

    private static void safeAppend(String[] stringArray, int n, StringBuffer stringBuffer) {
        if (stringArray != null && n >= 0 && n < stringArray.length) {
            stringBuffer.append(stringArray[n]);
        }
    }

    private static void safeAppendWithMonthPattern(String[] stringArray, int n, StringBuffer stringBuffer, String string) {
        if (stringArray != null && n >= 0 && n < stringArray.length) {
            if (string == null) {
                stringBuffer.append(stringArray[n]);
            } else {
                String string2 = SimpleFormatterImpl.formatRawPattern(string, 1, 1, stringArray[n]);
                stringBuffer.append(string2);
            }
        }
    }

    private Object[] getPatternItems() {
        if (this.patternItems != null) {
            return this.patternItems;
        }
        this.patternItems = PARSED_PATTERN_CACHE.get(this.pattern);
        if (this.patternItems != null) {
            return this.patternItems;
        }
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        char c = '\u0000';
        int n = 1;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int i = 0; i < this.pattern.length(); ++i) {
            char c2 = this.pattern.charAt(i);
            if (c2 == '\'') {
                if (bl) {
                    stringBuilder.append('\'');
                    bl = false;
                } else {
                    bl = true;
                    if (c != '\u0000') {
                        arrayList.add(new PatternItem(c, n));
                        c = '\u0000';
                    }
                }
                bl2 = !bl2;
                continue;
            }
            bl = false;
            if (bl2) {
                stringBuilder.append(c2);
                continue;
            }
            if (SimpleDateFormat.isSyntaxChar(c2)) {
                if (c2 == c) {
                    ++n;
                    continue;
                }
                if (c == '\u0000') {
                    if (stringBuilder.length() > 0) {
                        arrayList.add(stringBuilder.toString());
                        stringBuilder.setLength(0);
                    }
                } else {
                    arrayList.add(new PatternItem(c, n));
                }
                c = c2;
                n = 1;
                continue;
            }
            if (c != '\u0000') {
                arrayList.add(new PatternItem(c, n));
                c = '\u0000';
            }
            stringBuilder.append(c2);
        }
        if (c == '\u0000') {
            if (stringBuilder.length() > 0) {
                arrayList.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        } else {
            arrayList.add(new PatternItem(c, n));
        }
        this.patternItems = arrayList.toArray(new Object[arrayList.size()]);
        PARSED_PATTERN_CACHE.put(this.pattern, this.patternItems);
        return this.patternItems;
    }

    @Deprecated
    protected void zeroPaddingNumber(NumberFormat numberFormat, StringBuffer stringBuffer, int n, int n2, int n3) {
        if (this.useLocalZeroPaddingNumberFormat && n >= 0) {
            this.fastZeroPaddingNumber(stringBuffer, n, n2, n3);
        } else {
            numberFormat.setMinimumIntegerDigits(n2);
            numberFormat.setMaximumIntegerDigits(n3);
            numberFormat.format(n, stringBuffer, new FieldPosition(-1));
        }
    }

    @Override
    public void setNumberFormat(NumberFormat numberFormat) {
        super.setNumberFormat(numberFormat);
        this.initLocalZeroPaddingNumberFormat();
        this.initializeTimeZoneFormat(true);
        if (this.numberFormatters != null) {
            this.numberFormatters = null;
        }
        if (this.overrideMap != null) {
            this.overrideMap = null;
        }
    }

    private void initLocalZeroPaddingNumberFormat() {
        if (this.numberFormat instanceof DecimalFormat) {
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols();
            String[] stringArray = decimalFormatSymbols.getDigitStringsLocal();
            this.useLocalZeroPaddingNumberFormat = true;
            this.decDigits = new char[10];
            for (int i = 0; i < 10; ++i) {
                if (stringArray[i].length() > 1) {
                    this.useLocalZeroPaddingNumberFormat = false;
                    break;
                }
                this.decDigits[i] = stringArray[i].charAt(0);
            }
        } else if (this.numberFormat instanceof DateNumberFormat) {
            this.decDigits = ((DateNumberFormat)this.numberFormat).getDigits();
            this.useLocalZeroPaddingNumberFormat = true;
        } else {
            this.useLocalZeroPaddingNumberFormat = false;
        }
        if (this.useLocalZeroPaddingNumberFormat) {
            this.decimalBuf = new char[10];
        }
    }

    private void fastZeroPaddingNumber(StringBuffer stringBuffer, int n, int n2, int n3) {
        int n4;
        int n5 = this.decimalBuf.length < n3 ? this.decimalBuf.length : n3;
        int n6 = n5 - 1;
        while (true) {
            this.decimalBuf[n6] = this.decDigits[n % 10];
            if (n6 == 0 || (n /= 10) == 0) break;
            --n6;
        }
        for (n4 = n2 - (n5 - n6); n4 > 0 && n6 > 0; --n4) {
            this.decimalBuf[--n6] = this.decDigits[0];
        }
        while (n4 > 0) {
            stringBuffer.append(this.decDigits[0]);
            --n4;
        }
        stringBuffer.append(this.decimalBuf, n6, n5 - n6);
    }

    protected String zeroPaddingNumber(long l, int n, int n2) {
        this.numberFormat.setMinimumIntegerDigits(n);
        this.numberFormat.setMaximumIntegerDigits(n2);
        return this.numberFormat.format(l);
    }

    private static final boolean isNumeric(char c, int n) {
        return NUMERIC_FORMAT_CHARS.indexOf(c) >= 0 || n <= 2 && NUMERIC_FORMAT_CHARS2.indexOf(c) >= 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void parse(String string, Calendar calendar, ParsePosition parsePosition) {
        Calendar calendar2;
        TimeZone timeZone;
        block53: {
            Object object;
            char c;
            int n;
            int n2;
            int n3;
            timeZone = null;
            calendar2 = null;
            if (calendar != this.calendar && !calendar.getType().equals(this.calendar.getType())) {
                this.calendar.setTimeInMillis(calendar.getTimeInMillis());
                timeZone = this.calendar.getTimeZone();
                this.calendar.setTimeZone(calendar.getTimeZone());
                calendar2 = calendar;
                calendar = this.calendar;
            }
            if ((n3 = parsePosition.getIndex()) < 0) {
                parsePosition.setErrorIndex(0);
                return;
            }
            int n4 = n3;
            Output<Object> output = new Output<Object>(null);
            Output<TimeZoneFormat.TimeType> output2 = new Output<TimeZoneFormat.TimeType>(TimeZoneFormat.TimeType.UNKNOWN);
            boolean[] blArray = new boolean[]{false};
            int n5 = -1;
            int n6 = 0;
            int n7 = 0;
            MessageFormat messageFormat = null;
            if (this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7) {
                messageFormat = new MessageFormat(this.formatData.leapMonthPatterns[6], this.locale);
            }
            Object[] objectArray = this.getPatternItems();
            int n8 = 0;
            while (n8 < objectArray.length) {
                block57: {
                    Object object2;
                    block55: {
                        int n9;
                        block58: {
                            block56: {
                                if (!(objectArray[n8] instanceof PatternItem)) break block55;
                                object2 = (PatternItem)objectArray[n8];
                                if (((PatternItem)object2).isNumeric && n5 == -1 && n8 + 1 < objectArray.length && objectArray[n8 + 1] instanceof PatternItem && ((PatternItem)objectArray[n8 + 1]).isNumeric) {
                                    n5 = n8;
                                    n6 = ((PatternItem)object2).length;
                                    n7 = n3;
                                }
                                if (n5 == -1) break block56;
                                n9 = ((PatternItem)object2).length;
                                if (n5 == n8) {
                                    n9 = n6;
                                }
                                if ((n3 = this.subParse(string, n3, ((PatternItem)object2).type, n9, true, false, blArray, calendar, messageFormat, output2)) < 0) {
                                    if (--n6 == 0) {
                                        parsePosition.setIndex(n4);
                                        parsePosition.setErrorIndex(n3);
                                        if (timeZone != null) {
                                            this.calendar.setTimeZone(timeZone);
                                        }
                                        return;
                                    }
                                    n8 = n5;
                                    n3 = n7;
                                    continue;
                                }
                                break block57;
                            }
                            if (((PatternItem)object2).type == 'l') break block57;
                            n5 = -1;
                            n9 = n3;
                            if ((n3 = this.subParse(string, n3, ((PatternItem)object2).type, ((PatternItem)object2).length, false, true, blArray, calendar, messageFormat, output2, output)) >= 0) break block57;
                            if (n3 != -32000) break block58;
                            n3 = n9;
                            if (n8 + 1 < objectArray.length) {
                                char c2;
                                String string2 = null;
                                try {
                                    string2 = (String)objectArray[n8 + 1];
                                } catch (ClassCastException classCastException) {
                                    parsePosition.setIndex(n4);
                                    parsePosition.setErrorIndex(n9);
                                    if (timeZone != null) {
                                        this.calendar.setTimeZone(timeZone);
                                    }
                                    return;
                                }
                                if (string2 == null) {
                                    string2 = (String)objectArray[n8 + 1];
                                }
                                n2 = string2.length();
                                for (n = 0; n < n2 && PatternProps.isWhiteSpace(c2 = string2.charAt(n)); ++n) {
                                }
                                if (n == n2) {
                                    ++n8;
                                }
                            }
                            break block57;
                        }
                        parsePosition.setIndex(n4);
                        parsePosition.setErrorIndex(n9);
                        if (timeZone != null) {
                            this.calendar.setTimeZone(timeZone);
                        }
                        return;
                    }
                    n5 = -1;
                    object2 = new boolean[1];
                    n3 = this.matchLiteral(string, n3, objectArray, n8, (boolean[])object2);
                    if (object2[0] == false) {
                        parsePosition.setIndex(n4);
                        parsePosition.setErrorIndex(n3);
                        if (timeZone != null) {
                            this.calendar.setTimeZone(timeZone);
                        }
                        return;
                    }
                }
                ++n8;
            }
            if (n3 < string.length() && (c = string.charAt(n3)) == '.' && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE) && objectArray.length != 0 && (object = objectArray[objectArray.length - 1]) instanceof PatternItem && !((PatternItem)object).isNumeric) {
                ++n3;
            }
            if (output.value != null) {
                DayPeriodRules dayPeriodRules = DayPeriodRules.getInstance(this.getLocale());
                if (!calendar.isSet(1) && !calendar.isSet(0)) {
                    double d = dayPeriodRules.getMidPointForDayPeriod((DayPeriodRules.DayPeriod)((Object)output.value));
                    n = d - (double)(n2 = (int)d) > 0.0 ? 30 : 0;
                    calendar.set(11, n2);
                    calendar.set(12, n);
                } else {
                    int n10;
                    if (calendar.isSet(0)) {
                        n10 = calendar.get(11);
                    } else {
                        n10 = calendar.get(10);
                        if (n10 == 0) {
                            n10 = 12;
                        }
                    }
                    if (!($assertionsDisabled || 0 <= n10 && n10 <= 23)) {
                        throw new AssertionError();
                    }
                    if (n10 == 0 || 13 <= n10 && n10 <= 23) {
                        calendar.set(11, n10);
                    } else {
                        double d;
                        double d2;
                        double d3;
                        if (n10 == 12) {
                            n10 = 0;
                        }
                        if (-6.0 <= (d3 = (d2 = (double)n10 + (double)calendar.get(12) / 60.0) - (d = dayPeriodRules.getMidPointForDayPeriod((DayPeriodRules.DayPeriod)((Object)output.value)))) && d3 < 6.0) {
                            calendar.set(9, 0);
                        } else {
                            calendar.set(9, 1);
                        }
                    }
                }
            }
            parsePosition.setIndex(n3);
            try {
                Calendar calendar3;
                Date date;
                TimeZoneFormat.TimeType timeType = (TimeZoneFormat.TimeType)((Object)output2.value);
                if (!blArray[0] && timeType == TimeZoneFormat.TimeType.UNKNOWN) break block53;
                if (blArray[0] && (date = (calendar3 = (Calendar)calendar.clone()).getTime()).before(this.getDefaultCenturyStart())) {
                    calendar.set(1, this.getDefaultCenturyStartYear() + 100);
                }
                if (timeType == TimeZoneFormat.TimeType.UNKNOWN) break block53;
                Calendar calendar4 = (Calendar)calendar.clone();
                TimeZone timeZone2 = calendar4.getTimeZone();
                BasicTimeZone basicTimeZone = null;
                if (timeZone2 instanceof BasicTimeZone) {
                    basicTimeZone = (BasicTimeZone)timeZone2;
                }
                calendar4.set(15, 0);
                calendar4.set(16, 0);
                long l = calendar4.getTimeInMillis();
                int[] nArray = new int[2];
                if (basicTimeZone != null) {
                    if (timeType == TimeZoneFormat.TimeType.STANDARD) {
                        basicTimeZone.getOffsetFromLocal(l, 1, 1, nArray);
                    } else {
                        basicTimeZone.getOffsetFromLocal(l, 3, 3, nArray);
                    }
                } else {
                    timeZone2.getOffset(l, true, nArray);
                    if (timeType == TimeZoneFormat.TimeType.STANDARD && nArray[1] != 0 || timeType == TimeZoneFormat.TimeType.DAYLIGHT && nArray[1] == 0) {
                        timeZone2.getOffset(l - 86400000L, true, nArray);
                    }
                }
                int n11 = nArray[1];
                if (timeType == TimeZoneFormat.TimeType.STANDARD) {
                    if (nArray[1] != 0) {
                        n11 = 0;
                    }
                } else if (nArray[1] == 0) {
                    if (basicTimeZone == null) {
                        n11 = timeZone2.getDSTSavings();
                    } else {
                        TimeZoneTransition timeZoneTransition;
                        TimeZoneTransition timeZoneTransition2;
                        long l2;
                        long l3 = l2 = l + (long)nArray[0];
                        long l4 = l2;
                        int n12 = 0;
                        int n13 = 0;
                        while ((timeZoneTransition2 = basicTimeZone.getPreviousTransition(l3, false)) != null) {
                            l3 = timeZoneTransition2.getTime() - 1L;
                            n12 = timeZoneTransition2.getFrom().getDSTSavings();
                            if (n12 == 0) continue;
                        }
                        while ((timeZoneTransition = basicTimeZone.getNextTransition(l4, true)) != null) {
                            l4 = timeZoneTransition.getTime();
                            n13 = timeZoneTransition.getTo().getDSTSavings();
                            if (n13 == 0) continue;
                        }
                        n11 = timeZoneTransition2 != null && timeZoneTransition != null ? (l2 - l3 > l4 - l2 ? n13 : n12) : (timeZoneTransition2 != null && n12 != 0 ? n12 : (timeZoneTransition != null && n13 != 0 ? n13 : basicTimeZone.getDSTSavings()));
                    }
                    if (n11 == 0) {
                        n11 = 3600000;
                    }
                }
                calendar.set(15, nArray[0]);
                calendar.set(16, n11);
            } catch (IllegalArgumentException illegalArgumentException) {
                parsePosition.setErrorIndex(n3);
                parsePosition.setIndex(n4);
                if (timeZone != null) {
                    this.calendar.setTimeZone(timeZone);
                }
                return;
            }
        }
        if (calendar2 != null) {
            calendar2.setTimeZone(calendar.getTimeZone());
            calendar2.setTimeInMillis(calendar.getTimeInMillis());
        }
        if (timeZone != null) {
            this.calendar.setTimeZone(timeZone);
        }
    }

    private int matchLiteral(String string, int n, Object[] objectArray, int n2, boolean[] blArray) {
        char c;
        int n3 = n;
        String string2 = (String)objectArray[n2];
        int n4 = string2.length();
        int n5 = string.length();
        int n6 = 0;
        while (n6 < n4 && n < n5) {
            char c2 = string2.charAt(n6);
            char c3 = string.charAt(n);
            if (PatternProps.isWhiteSpace(c2) && PatternProps.isWhiteSpace(c3)) {
                while (n6 + 1 < n4 && PatternProps.isWhiteSpace(string2.charAt(n6 + 1))) {
                    ++n6;
                }
                while (n + 1 < n5 && PatternProps.isWhiteSpace(string.charAt(n + 1))) {
                    ++n;
                }
            } else if (c2 != c3) {
                if (c3 == '.' && n == n3 && 0 < n2 && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                    Object object = objectArray[n2 - 1];
                    if (!(object instanceof PatternItem) || (c = ((PatternItem)object).isNumeric) != '\u0000') break;
                    ++n;
                    continue;
                }
                if ((c2 == ' ' || c2 == '.') && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                    ++n6;
                    continue;
                }
                if (n == n3 || !this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH)) break;
                ++n6;
                continue;
            }
            ++n6;
            ++n;
        }
        boolean bl = blArray[0] = n6 == n4;
        if (!blArray[0] && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE) && 0 < n2 && n2 < objectArray.length - 1 && n3 < n5) {
            Object object = objectArray[n2 - 1];
            Object object2 = objectArray[n2 + 1];
            if (object instanceof PatternItem && object2 instanceof PatternItem) {
                char c4 = ((PatternItem)object).type;
                c = ((PatternItem)object2).type;
                if (DATE_PATTERN_TYPE.contains(c4) != DATE_PATTERN_TYPE.contains(c)) {
                    char c5;
                    int n7;
                    for (n7 = n3; n7 < n5 && PatternProps.isWhiteSpace(c5 = string.charAt(n7)); ++n7) {
                    }
                    blArray[0] = n7 > n3;
                    n = n7;
                }
            }
        }
        return n;
    }

    protected int matchString(String string, int n, int n2, String[] stringArray, Calendar calendar) {
        return this.matchString(string, n, n2, stringArray, null, calendar);
    }

    @Deprecated
    private int matchString(String string, int n, int n2, String[] stringArray, String string2, Calendar calendar) {
        int n3 = 0;
        int n4 = stringArray.length;
        if (n2 == 7) {
            n3 = 1;
        }
        int n5 = 0;
        int n6 = -1;
        int n7 = 0;
        int n8 = 0;
        while (n3 < n4) {
            String string3;
            int n9 = stringArray[n3].length();
            if (n9 > n5 && (n8 = this.regionMatchesWithOptionalDot(string, n, stringArray[n3], n9)) >= 0) {
                n6 = n3;
                n5 = n8;
                n7 = 0;
            }
            if (string2 != null && (n9 = (string3 = SimpleFormatterImpl.formatRawPattern(string2, 1, 1, stringArray[n3])).length()) > n5 && (n8 = this.regionMatchesWithOptionalDot(string, n, string3, n9)) >= 0) {
                n6 = n3;
                n5 = n8;
                n7 = 1;
            }
            ++n3;
        }
        if (n6 >= 0) {
            if (n2 >= 0) {
                if (n2 == 1) {
                    ++n6;
                }
                calendar.set(n2, n6);
                if (string2 != null) {
                    calendar.set(22, n7);
                }
            }
            return n + n5;
        }
        return ~n;
    }

    private int regionMatchesWithOptionalDot(String string, int n, String string2, int n2) {
        boolean bl = string.regionMatches(true, n, string2, 0, n2);
        if (bl) {
            return n2;
        }
        if (string2.length() > 0 && string2.charAt(string2.length() - 1) == '.' && string.regionMatches(true, n, string2, 0, n2 - 1)) {
            return n2 - 1;
        }
        return 1;
    }

    protected int matchQuarterString(String string, int n, int n2, String[] stringArray, Calendar calendar) {
        int n3 = stringArray.length;
        int n4 = 0;
        int n5 = -1;
        int n6 = 0;
        for (int i = 0; i < n3; ++i) {
            int n7 = stringArray[i].length();
            if (n7 <= n4 || (n6 = this.regionMatchesWithOptionalDot(string, n, stringArray[i], n7)) < 0) continue;
            n5 = i;
            n4 = n6;
        }
        if (n5 >= 0) {
            calendar.set(n2, n5 * 3);
            return n + n4;
        }
        return -n;
    }

    private int matchDayPeriodString(String string, int n, String[] stringArray, int n2, Output<DayPeriodRules.DayPeriod> output) {
        int n3 = 0;
        int n4 = -1;
        int n5 = 0;
        for (int i = 0; i < n2; ++i) {
            int n6;
            if (stringArray[i] == null || (n6 = stringArray[i].length()) <= n3 || (n5 = this.regionMatchesWithOptionalDot(string, n, stringArray[i], n6)) < 0) continue;
            n4 = i;
            n3 = n5;
        }
        if (n4 >= 0) {
            output.value = DayPeriodRules.DayPeriod.VALUES[n4];
            return n + n3;
        }
        return -n;
    }

    protected int subParse(String string, int n, char c, int n2, boolean bl, boolean bl2, boolean[] blArray, Calendar calendar) {
        return this.subParse(string, n, c, n2, bl, bl2, blArray, calendar, null, null);
    }

    private int subParse(String string, int n, char c, int n2, boolean bl, boolean bl2, boolean[] blArray, Calendar calendar, MessageFormat messageFormat, Output<TimeZoneFormat.TimeType> output) {
        return this.subParse(string, n, c, n2, bl, bl2, blArray, calendar, null, null, null);
    }

    @Deprecated
    private int subParse(String string, int n, char c, int n2, boolean bl, boolean bl2, boolean[] blArray, Calendar calendar, MessageFormat messageFormat, Output<TimeZoneFormat.TimeType> output, Output<DayPeriodRules.DayPeriod> output2) {
        Object object;
        int n3;
        boolean bl3;
        Number number = null;
        NumberFormat numberFormat = null;
        int n4 = 0;
        ParsePosition parsePosition = new ParsePosition(0);
        int n5 = SimpleDateFormat.getIndexFromChar(c);
        if (n5 == -1) {
            return ~n;
        }
        numberFormat = this.getNumberFormat(c);
        int n6 = PATTERN_INDEX_TO_CALENDAR_FIELD[n5];
        if (messageFormat != null) {
            messageFormat.setFormatByArgumentIndex(0, numberFormat);
        }
        boolean bl4 = bl3 = calendar.getType().equals("chinese") || calendar.getType().equals("dangi");
        while (true) {
            if (n >= string.length()) {
                return ~n;
            }
            n3 = UTF16.charAt(string, n);
            if (!UCharacter.isUWhiteSpace(n3) || !PatternProps.isWhiteSpace(n3)) break;
            n += UTF16.getCharCount(n3);
        }
        parsePosition.setIndex(n);
        if (n5 == 4 || n5 == 15 || n5 == 2 && n2 <= 2 || n5 == 26 || n5 == 19 || n5 == 25 || n5 == 1 || n5 == 18 || n5 == 30 || n5 == 0 && bl3 || n5 == 27 || n5 == 28 || n5 == 8) {
            n3 = 0;
            if (messageFormat != null && (n5 == 2 || n5 == 26)) {
                object = messageFormat.parse(string, parsePosition);
                if (object != null && parsePosition.getIndex() > n && object[0] instanceof Number) {
                    n3 = 1;
                    number = (Number)object[0];
                    calendar.set(22, 1);
                } else {
                    parsePosition.setIndex(n);
                    calendar.set(22, 0);
                }
            }
            if (n3 == 0) {
                if (bl) {
                    if (n + n2 > string.length()) {
                        return ~n;
                    }
                    number = this.parseInt(string, n2, parsePosition, bl2, numberFormat);
                } else {
                    number = this.parseInt(string, parsePosition, bl2, numberFormat);
                }
                if (number == null && !this.allowNumericFallback(n5)) {
                    return ~n;
                }
            }
            if (number != null) {
                n4 = number.intValue();
            }
        }
        switch (n5) {
            case 0: {
                if (bl3) {
                    calendar.set(0, n4);
                    return parsePosition.getIndex();
                }
                n3 = 0;
                n3 = n2 == 5 ? this.matchString(string, n, 0, this.formatData.narrowEras, null, calendar) : (n2 == 4 ? this.matchString(string, n, 0, this.formatData.eraNames, null, calendar) : this.matchString(string, n, 0, this.formatData.eras, null, calendar));
                if (n3 == ~n) {
                    n3 = -32000;
                }
                return n3;
            }
            case 1: 
            case 18: {
                if (this.override != null && (this.override.compareTo("hebr") == 0 || this.override.indexOf("y=hebr") >= 0) && n4 < 1000) {
                    n4 += 5000;
                } else if (n2 == 2 && SimpleDateFormat.countDigits(string, n, parsePosition.getIndex()) == 2 && calendar.haveDefaultCentury()) {
                    int n7 = this.getDefaultCenturyStartYear() % 100;
                    blArray[0] = n4 == n7;
                    n4 += this.getDefaultCenturyStartYear() / 100 * 100 + (n4 < n7 ? 100 : 0);
                }
                calendar.set(n6, n4);
                if (DelayedHebrewMonthCheck) {
                    if (!HebrewCalendar.isLeapYear(n4)) {
                        calendar.add(2, 1);
                    }
                    DelayedHebrewMonthCheck = false;
                }
                return parsePosition.getIndex();
            }
            case 30: {
                int n8;
                if (this.formatData.shortYearNames != null && (n8 = this.matchString(string, n, 1, this.formatData.shortYearNames, null, calendar)) > 0) {
                    return n8;
                }
                if (number != null && (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) || this.formatData.shortYearNames == null || n4 > this.formatData.shortYearNames.length)) {
                    calendar.set(1, n4);
                    return parsePosition.getIndex();
                }
                return ~n;
            }
            case 2: 
            case 26: {
                if (n2 <= 2 || number != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC)) {
                    calendar.set(2, n4 - 1);
                    if (calendar.getType().equals("hebrew") && n4 >= 6) {
                        if (calendar.isSet(0)) {
                            if (!HebrewCalendar.isLeapYear(calendar.get(1))) {
                                calendar.set(2, n4);
                            }
                        } else {
                            DelayedHebrewMonthCheck = true;
                        }
                    }
                    return parsePosition.getIndex();
                }
                boolean bl5 = this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7;
                int n9 = 0;
                if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 4) {
                    int n10 = n5 == 2 ? this.matchString(string, n, 2, this.formatData.months, bl5 ? this.formatData.leapMonthPatterns[0] : null, calendar) : (n9 = this.matchString(string, n, 2, this.formatData.standaloneMonths, bl5 ? this.formatData.leapMonthPatterns[3] : null, calendar));
                    if (n9 > 0) {
                        return n9;
                    }
                }
                if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 3) {
                    return n5 == 2 ? this.matchString(string, n, 2, this.formatData.shortMonths, bl5 ? this.formatData.leapMonthPatterns[1] : null, calendar) : this.matchString(string, n, 2, this.formatData.standaloneShortMonths, bl5 ? this.formatData.leapMonthPatterns[4] : null, calendar);
                }
                return n9;
            }
            case 4: {
                if (n4 == calendar.getMaximum(11) + 1) {
                    n4 = 0;
                }
                calendar.set(11, n4);
                return parsePosition.getIndex();
            }
            case 8: {
                int n11;
                if (n11 < 3) {
                    for (n11 = SimpleDateFormat.countDigits(string, n, parsePosition.getIndex()); n11 < 3; ++n11) {
                        n4 *= 10;
                    }
                } else {
                    int n12 = 1;
                    while (n11 > 3) {
                        n12 *= 10;
                        --n11;
                    }
                    n4 /= n12;
                }
                calendar.set(14, n4);
                return parsePosition.getIndex();
            }
            case 19: {
                if (n2 <= 2 || number != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC)) {
                    calendar.set(n6, n4);
                    return parsePosition.getIndex();
                }
            }
            case 9: {
                int n13 = 0;
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 4) && (n13 = this.matchString(string, n, 7, this.formatData.weekdays, null, calendar)) > 0) {
                    return n13;
                }
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 3) && (n13 = this.matchString(string, n, 7, this.formatData.shortWeekdays, null, calendar)) > 0) {
                    return n13;
                }
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 6) && this.formatData.shorterWeekdays != null && (n13 = this.matchString(string, n, 7, this.formatData.shorterWeekdays, null, calendar)) > 0) {
                    return n13;
                }
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 5) && this.formatData.narrowWeekdays != null && (n13 = this.matchString(string, n, 7, this.formatData.narrowWeekdays, null, calendar)) > 0) {
                    return n13;
                }
                return n13;
            }
            case 25: {
                if (n2 == 1 || number != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC)) {
                    calendar.set(n6, n4);
                    return parsePosition.getIndex();
                }
                int n14 = 0;
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 4) && (n14 = this.matchString(string, n, 7, this.formatData.standaloneWeekdays, null, calendar)) > 0) {
                    return n14;
                }
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 3) && (n14 = this.matchString(string, n, 7, this.formatData.standaloneShortWeekdays, null, calendar)) > 0) {
                    return n14;
                }
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 6) && this.formatData.standaloneShorterWeekdays != null) {
                    return this.matchString(string, n, 7, this.formatData.standaloneShorterWeekdays, null, calendar);
                }
                return n14;
            }
            case 14: {
                int n15 = 0;
                if ((this.formatData.ampmsNarrow == null || n2 < 5 || this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)) && (n15 = this.matchString(string, n, 9, this.formatData.ampms, null, calendar)) > 0) {
                    return n15;
                }
                if (this.formatData.ampmsNarrow != null && (n2 >= 5 || this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)) && (n15 = this.matchString(string, n, 9, this.formatData.ampmsNarrow, null, calendar)) > 0) {
                    return n15;
                }
                return ~n;
            }
            case 15: {
                if (n4 == calendar.getLeastMaximum(10) + 1) {
                    n4 = 0;
                }
                calendar.set(10, n4);
                return parsePosition.getIndex();
            }
            case 17: {
                object = n2 < 4 ? TimeZoneFormat.Style.SPECIFIC_SHORT : TimeZoneFormat.Style.SPECIFIC_LONG;
                TimeZone timeZone = this.tzFormat().parse((TimeZoneFormat.Style)((Object)object), string, parsePosition, output);
                if (timeZone != null) {
                    calendar.setTimeZone(timeZone);
                    return parsePosition.getIndex();
                }
                return ~n;
            }
            case 23: {
                object = n2 < 4 ? TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL : (n2 == 5 ? TimeZoneFormat.Style.ISO_EXTENDED_FULL : TimeZoneFormat.Style.LOCALIZED_GMT);
                TimeZone timeZone = this.tzFormat().parse((TimeZoneFormat.Style)((Object)object), string, parsePosition, output);
                if (timeZone != null) {
                    calendar.setTimeZone(timeZone);
                    return parsePosition.getIndex();
                }
                return ~n;
            }
            case 24: {
                object = n2 < 4 ? TimeZoneFormat.Style.GENERIC_SHORT : TimeZoneFormat.Style.GENERIC_LONG;
                TimeZone timeZone = this.tzFormat().parse((TimeZoneFormat.Style)((Object)object), string, parsePosition, output);
                if (timeZone != null) {
                    calendar.setTimeZone(timeZone);
                    return parsePosition.getIndex();
                }
                return ~n;
            }
            case 29: {
                object = null;
                switch (n2) {
                    case 1: {
                        object = TimeZoneFormat.Style.ZONE_ID_SHORT;
                        break;
                    }
                    case 2: {
                        object = TimeZoneFormat.Style.ZONE_ID;
                        break;
                    }
                    case 3: {
                        object = TimeZoneFormat.Style.EXEMPLAR_LOCATION;
                        break;
                    }
                    default: {
                        object = TimeZoneFormat.Style.GENERIC_LOCATION;
                    }
                }
                TimeZone timeZone = this.tzFormat().parse((TimeZoneFormat.Style)((Object)object), string, parsePosition, output);
                if (timeZone != null) {
                    calendar.setTimeZone(timeZone);
                    return parsePosition.getIndex();
                }
                return ~n;
            }
            case 31: {
                object = n2 < 4 ? TimeZoneFormat.Style.LOCALIZED_GMT_SHORT : TimeZoneFormat.Style.LOCALIZED_GMT;
                TimeZone timeZone = this.tzFormat().parse((TimeZoneFormat.Style)((Object)object), string, parsePosition, output);
                if (timeZone != null) {
                    calendar.setTimeZone(timeZone);
                    return parsePosition.getIndex();
                }
                return ~n;
            }
            case 32: {
                switch (n2) {
                    case 1: {
                        object = TimeZoneFormat.Style.ISO_BASIC_SHORT;
                        break;
                    }
                    case 2: {
                        object = TimeZoneFormat.Style.ISO_BASIC_FIXED;
                        break;
                    }
                    case 3: {
                        object = TimeZoneFormat.Style.ISO_EXTENDED_FIXED;
                        break;
                    }
                    case 4: {
                        object = TimeZoneFormat.Style.ISO_BASIC_FULL;
                        break;
                    }
                    default: {
                        object = TimeZoneFormat.Style.ISO_EXTENDED_FULL;
                    }
                }
                TimeZone timeZone = this.tzFormat().parse((TimeZoneFormat.Style)((Object)object), string, parsePosition, output);
                if (timeZone != null) {
                    calendar.setTimeZone(timeZone);
                    return parsePosition.getIndex();
                }
                return ~n;
            }
            case 33: {
                switch (n2) {
                    case 1: {
                        object = TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT;
                        break;
                    }
                    case 2: {
                        object = TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED;
                        break;
                    }
                    case 3: {
                        object = TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED;
                        break;
                    }
                    case 4: {
                        object = TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL;
                        break;
                    }
                    default: {
                        object = TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL;
                    }
                }
                TimeZone timeZone = this.tzFormat().parse((TimeZoneFormat.Style)((Object)object), string, parsePosition, output);
                if (timeZone != null) {
                    calendar.setTimeZone(timeZone);
                    return parsePosition.getIndex();
                }
                return ~n;
            }
            case 27: {
                if (n2 <= 2 || number != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC)) {
                    calendar.set(2, (n4 - 1) * 3);
                    return parsePosition.getIndex();
                }
                int n16 = 0;
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 4) && (n16 = this.matchQuarterString(string, n, 2, this.formatData.quarters, calendar)) > 0) {
                    return n16;
                }
                if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 3) {
                    return this.matchQuarterString(string, n, 2, this.formatData.shortQuarters, calendar);
                }
                return n16;
            }
            case 28: {
                if (n2 <= 2 || number != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC)) {
                    calendar.set(2, (n4 - 1) * 3);
                    return parsePosition.getIndex();
                }
                int n17 = 0;
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 4) && (n17 = this.matchQuarterString(string, n, 2, this.formatData.standaloneQuarters, calendar)) > 0) {
                    return n17;
                }
                if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 3) {
                    return this.matchQuarterString(string, n, 2, this.formatData.standaloneShortQuarters, calendar);
                }
                return n17;
            }
            case 37: {
                object = new ArrayList(3);
                ((ArrayList)object).add(this.formatData.getTimeSeparatorString());
                if (!this.formatData.getTimeSeparatorString().equals(":")) {
                    ((ArrayList)object).add(":");
                }
                if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH) && !this.formatData.getTimeSeparatorString().equals(".")) {
                    ((ArrayList)object).add(".");
                }
                return this.matchString(string, n, -1, ((ArrayList)object).toArray(new String[0]), calendar);
            }
            case 35: {
                int n18 = this.subParse(string, n, 'a', n2, bl, bl2, blArray, calendar, messageFormat, output, output2);
                if (n18 > 0) {
                    return n18;
                }
                int n19 = 0;
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 3) && (n19 = this.matchDayPeriodString(string, n, this.formatData.abbreviatedDayPeriods, 2, output2)) > 0) {
                    return n19;
                }
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 4) && (n19 = this.matchDayPeriodString(string, n, this.formatData.wideDayPeriods, 2, output2)) > 0) {
                    return n19;
                }
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 4) && (n19 = this.matchDayPeriodString(string, n, this.formatData.narrowDayPeriods, 2, output2)) > 0) {
                    return n19;
                }
                return n19;
            }
            case 36: {
                int n20 = 0;
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 3) && (n20 = this.matchDayPeriodString(string, n, this.formatData.abbreviatedDayPeriods, this.formatData.abbreviatedDayPeriods.length, output2)) > 0) {
                    return n20;
                }
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 4) && (n20 = this.matchDayPeriodString(string, n, this.formatData.wideDayPeriods, this.formatData.wideDayPeriods.length, output2)) > 0) {
                    return n20;
                }
                if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || n2 == 4) && (n20 = this.matchDayPeriodString(string, n, this.formatData.narrowDayPeriods, this.formatData.narrowDayPeriods.length, output2)) > 0) {
                    return n20;
                }
                return n20;
            }
        }
        if (bl) {
            if (n + n2 > string.length()) {
                return -n;
            }
            number = this.parseInt(string, n2, parsePosition, bl2, numberFormat);
        } else {
            number = this.parseInt(string, parsePosition, bl2, numberFormat);
        }
        if (number != null) {
            if (n5 != 34) {
                calendar.set(n6, number.intValue());
            } else {
                calendar.setRelatedYear(number.intValue());
            }
            return parsePosition.getIndex();
        }
        return ~n;
    }

    private boolean allowNumericFallback(int n) {
        return n != 26 && n != 19 && n != 25 && n != 30 && n != 27 && n != 28;
    }

    private Number parseInt(String string, ParsePosition parsePosition, boolean bl, NumberFormat numberFormat) {
        return this.parseInt(string, -1, parsePosition, bl, numberFormat);
    }

    private Number parseInt(String string, int n, ParsePosition parsePosition, boolean bl, NumberFormat numberFormat) {
        int n2;
        Number number;
        int n3 = parsePosition.getIndex();
        if (bl) {
            number = numberFormat.parse(string, parsePosition);
        } else if (numberFormat instanceof DecimalFormat) {
            String string2 = ((DecimalFormat)numberFormat).getNegativePrefix();
            ((DecimalFormat)numberFormat).setNegativePrefix(SUPPRESS_NEGATIVE_PREFIX);
            number = numberFormat.parse(string, parsePosition);
            ((DecimalFormat)numberFormat).setNegativePrefix(string2);
        } else {
            boolean bl2 = numberFormat instanceof DateNumberFormat;
            if (bl2) {
                ((DateNumberFormat)numberFormat).setParsePositiveOnly(false);
            }
            number = numberFormat.parse(string, parsePosition);
            if (bl2) {
                ((DateNumberFormat)numberFormat).setParsePositiveOnly(true);
            }
        }
        if (n > 0 && (n2 = parsePosition.getIndex() - n3) > n) {
            double d = number.doubleValue();
            n2 -= n;
            while (n2 > 0) {
                d /= 10.0;
                --n2;
            }
            parsePosition.setIndex(n3 + n);
            number = (int)d;
        }
        return number;
    }

    private static int countDigits(String string, int n, int n2) {
        int n3;
        int n4 = 0;
        for (int i = n; i < n2; i += UCharacter.charCount(n3)) {
            n3 = string.codePointAt(i);
            if (!UCharacter.isDigit(n3)) continue;
            ++n4;
        }
        return n4;
    }

    private String translatePattern(String string, String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        for (int i = 0; i < string.length(); ++i) {
            int n;
            char c = string.charAt(i);
            if (bl) {
                if (c == '\'') {
                    bl = false;
                }
            } else if (c == '\'') {
                bl = true;
            } else if (SimpleDateFormat.isSyntaxChar(c) && (n = string2.indexOf(c)) != -1) {
                c = string3.charAt(n);
            }
            stringBuilder.append(c);
        }
        if (bl) {
            throw new IllegalArgumentException("Unfinished quote in pattern");
        }
        return stringBuilder.toString();
    }

    public String toPattern() {
        return this.pattern;
    }

    public String toLocalizedPattern() {
        return this.translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB", this.formatData.localPatternChars);
    }

    public void applyPattern(String string) {
        this.pattern = string;
        this.parsePattern();
        this.setLocale(null, null);
        this.patternItems = null;
        if (this.calendar != null && this.calendar.getType().equals("japanese") && this.locale != null && this.locale.getLanguage().equals("ja")) {
            if (this.override != null && this.override.equals("y=jpanyear") && !this.hasHanYearChar) {
                this.numberFormatters = null;
                this.overrideMap = null;
                this.override = null;
            } else if (this.override == null && this.hasHanYearChar) {
                this.numberFormatters = new HashMap();
                this.overrideMap = new HashMap();
                this.overrideMap.put(Character.valueOf('y'), "jpanyear");
                ULocale uLocale = new ULocale(this.locale.getBaseName() + "@numbers=jpanyear");
                NumberFormat numberFormat = NumberFormat.createInstance(uLocale, 0);
                numberFormat.setGroupingUsed(true);
                this.useLocalZeroPaddingNumberFormat = false;
                this.numberFormatters.put("jpanyear", numberFormat);
                this.override = "y=jpanyear";
            }
        }
    }

    public void applyLocalizedPattern(String string) {
        this.pattern = this.translatePattern(string, this.formatData.localPatternChars, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB");
        this.setLocale(null, null);
    }

    public DateFormatSymbols getDateFormatSymbols() {
        return (DateFormatSymbols)this.formatData.clone();
    }

    public void setDateFormatSymbols(DateFormatSymbols dateFormatSymbols) {
        this.formatData = (DateFormatSymbols)dateFormatSymbols.clone();
    }

    protected DateFormatSymbols getSymbols() {
        return this.formatData;
    }

    public TimeZoneFormat getTimeZoneFormat() {
        return this.tzFormat().freeze();
    }

    public void setTimeZoneFormat(TimeZoneFormat timeZoneFormat) {
        this.tzFormat = timeZoneFormat.isFrozen() ? timeZoneFormat : timeZoneFormat.cloneAsThawed().freeze();
    }

    @Override
    public Object clone() {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)super.clone();
        simpleDateFormat.formatData = (DateFormatSymbols)this.formatData.clone();
        if (this.decimalBuf != null) {
            simpleDateFormat.decimalBuf = new char[10];
        }
        return simpleDateFormat;
    }

    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return true;
        }
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)object;
        return this.pattern.equals(simpleDateFormat.pattern) && this.formatData.equals(simpleDateFormat.formatData);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.defaultCenturyStart == null) {
            this.initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        this.initializeTimeZoneFormat(false);
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.getContext(DisplayContext.Type.CAPITALIZATION).value());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int n;
        objectInputStream.defaultReadObject();
        int n2 = n = this.serialVersionOnStream > 1 ? objectInputStream.readInt() : -1;
        if (this.serialVersionOnStream < 1) {
            this.defaultCenturyBase = System.currentTimeMillis();
        } else {
            this.parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
        }
        this.serialVersionOnStream = 2;
        this.locale = this.getLocale(ULocale.VALID_LOCALE);
        if (this.locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        this.initLocalZeroPaddingNumberFormat();
        this.setContext(DisplayContext.CAPITALIZATION_NONE);
        if (n >= 0) {
            for (DisplayContext displayContext : DisplayContext.values()) {
                if (displayContext.value() != n) continue;
                this.setContext(displayContext);
                break;
            }
        }
        if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_MATCH)) {
            this.setBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH, true);
        }
        this.parsePattern();
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        Calendar calendar = this.calendar;
        if (object instanceof Calendar) {
            calendar = (Calendar)object;
        } else if (object instanceof Date) {
            this.calendar.setTime((Date)object);
        } else if (object instanceof Number) {
            this.calendar.setTimeInMillis(((Number)object).longValue());
        } else {
            throw new IllegalArgumentException("Cannot format given Object as a Date");
        }
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        ArrayList<FieldPosition> arrayList = new ArrayList<FieldPosition>();
        this.format(calendar, this.getContext(DisplayContext.Type.CAPITALIZATION), stringBuffer, fieldPosition, arrayList);
        AttributedString attributedString = new AttributedString(stringBuffer.toString());
        for (int i = 0; i < arrayList.size(); ++i) {
            FieldPosition fieldPosition2 = (FieldPosition)arrayList.get(i);
            Format.Field field = fieldPosition2.getFieldAttribute();
            attributedString.addAttribute(field, field, fieldPosition2.getBeginIndex(), fieldPosition2.getEndIndex());
        }
        return attributedString.getIterator();
    }

    ULocale getLocale() {
        return this.locale;
    }

    boolean isFieldUnitIgnored(int n) {
        return SimpleDateFormat.isFieldUnitIgnored(this.pattern, n);
    }

    static boolean isFieldUnitIgnored(String string, int n) {
        int n2;
        int n3 = CALENDAR_FIELD_TO_LEVEL[n];
        boolean bl = false;
        char c = '\u0000';
        int n4 = 0;
        for (int i = 0; i < string.length(); ++i) {
            char c2 = string.charAt(i);
            if (c2 != c && n4 > 0) {
                n2 = SimpleDateFormat.getLevelFromChar(c);
                if (n3 <= n2) {
                    return true;
                }
                n4 = 0;
            }
            if (c2 == '\'') {
                if (i + 1 < string.length() && string.charAt(i + 1) == '\'') {
                    ++i;
                    continue;
                }
                bl = !bl;
                continue;
            }
            if (bl || !SimpleDateFormat.isSyntaxChar(c2)) continue;
            c = c2;
            ++n4;
        }
        return n4 > 0 && n3 <= (n2 = SimpleDateFormat.getLevelFromChar(c));
    }

    @Deprecated
    public final StringBuffer intervalFormatByAlgorithm(Calendar calendar, Calendar calendar2, StringBuffer stringBuffer, FieldPosition fieldPosition) throws IllegalArgumentException {
        int n;
        int n2;
        int n3;
        if (!calendar.isEquivalentTo(calendar2)) {
            throw new IllegalArgumentException("can not format on two different calendars");
        }
        Object[] objectArray = this.getPatternItems();
        int n4 = -1;
        int n5 = -1;
        try {
            for (n3 = 0; n3 < objectArray.length; ++n3) {
                if (!this.diffCalFieldValue(calendar, calendar2, objectArray, n3)) continue;
                n4 = n3;
                break;
            }
            if (n4 == -1) {
                return this.format(calendar, stringBuffer, fieldPosition);
            }
            for (n3 = objectArray.length - 1; n3 >= n4; --n3) {
                if (!this.diffCalFieldValue(calendar, calendar2, objectArray, n3)) continue;
                n5 = n3;
                break;
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException(illegalArgumentException.toString());
        }
        if (n4 == 0 && n5 == objectArray.length - 1) {
            this.format(calendar, stringBuffer, fieldPosition);
            stringBuffer.append(" \u2013 ");
            this.format(calendar2, stringBuffer, fieldPosition);
            return stringBuffer;
        }
        n3 = 1000;
        for (n2 = n4; n2 <= n5; ++n2) {
            if (objectArray[n2] instanceof String) continue;
            PatternItem patternItem = (PatternItem)objectArray[n2];
            char c = patternItem.type;
            int n6 = SimpleDateFormat.getIndexFromChar(c);
            if (n6 == -1) {
                throw new IllegalArgumentException("Illegal pattern character '" + c + "' in \"" + this.pattern + '\"');
            }
            if (n6 >= n3) continue;
            n3 = n6;
        }
        try {
            for (n2 = 0; n2 < n4; ++n2) {
                if (!this.lowerLevel(objectArray, n2, n3)) continue;
                n4 = n2;
                break;
            }
            for (n2 = objectArray.length - 1; n2 > n5; --n2) {
                if (!this.lowerLevel(objectArray, n2, n3)) continue;
                n5 = n2;
                break;
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException(illegalArgumentException.toString());
        }
        if (n4 == 0 && n5 == objectArray.length - 1) {
            this.format(calendar, stringBuffer, fieldPosition);
            stringBuffer.append(" \u2013 ");
            this.format(calendar2, stringBuffer, fieldPosition);
            return stringBuffer;
        }
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        DisplayContext displayContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
        for (n = 0; n <= n5; ++n) {
            if (objectArray[n] instanceof String) {
                stringBuffer.append((String)objectArray[n]);
                continue;
            }
            PatternItem patternItem = (PatternItem)objectArray[n];
            if (this.useFastFormat) {
                this.subFormat(stringBuffer, patternItem.type, patternItem.length, stringBuffer.length(), n, displayContext, fieldPosition, calendar);
                continue;
            }
            stringBuffer.append(this.subFormat(patternItem.type, patternItem.length, stringBuffer.length(), n, displayContext, fieldPosition, calendar));
        }
        stringBuffer.append(" \u2013 ");
        for (n = n4; n < objectArray.length; ++n) {
            if (objectArray[n] instanceof String) {
                stringBuffer.append((String)objectArray[n]);
                continue;
            }
            PatternItem patternItem = (PatternItem)objectArray[n];
            if (this.useFastFormat) {
                this.subFormat(stringBuffer, patternItem.type, patternItem.length, stringBuffer.length(), n, displayContext, fieldPosition, calendar2);
                continue;
            }
            stringBuffer.append(this.subFormat(patternItem.type, patternItem.length, stringBuffer.length(), n, displayContext, fieldPosition, calendar2));
        }
        return stringBuffer;
    }

    private boolean diffCalFieldValue(Calendar calendar, Calendar calendar2, Object[] objectArray, int n) throws IllegalArgumentException {
        int n2;
        int n3;
        if (objectArray[n] instanceof String) {
            return true;
        }
        PatternItem patternItem = (PatternItem)objectArray[n];
        char c = patternItem.type;
        int n4 = SimpleDateFormat.getIndexFromChar(c);
        if (n4 == -1) {
            throw new IllegalArgumentException("Illegal pattern character '" + c + "' in \"" + this.pattern + '\"');
        }
        int n5 = PATTERN_INDEX_TO_CALENDAR_FIELD[n4];
        return n5 < 0 || (n3 = calendar.get(n5)) == (n2 = calendar2.get(n5));
    }

    private boolean lowerLevel(Object[] objectArray, int n, int n2) throws IllegalArgumentException {
        if (objectArray[n] instanceof String) {
            return true;
        }
        PatternItem patternItem = (PatternItem)objectArray[n];
        char c = patternItem.type;
        int n3 = SimpleDateFormat.getLevelFromChar(c);
        if (n3 == -1) {
            throw new IllegalArgumentException("Illegal pattern character '" + c + "' in \"" + this.pattern + '\"');
        }
        return n3 < n2;
    }

    public void setNumberFormat(String string, NumberFormat numberFormat) {
        numberFormat.setGroupingUsed(true);
        String string2 = "$" + UUID.randomUUID().toString();
        if (this.numberFormatters == null) {
            this.numberFormatters = new HashMap();
        }
        if (this.overrideMap == null) {
            this.overrideMap = new HashMap();
        }
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if ("GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB".indexOf(c) == -1) {
                throw new IllegalArgumentException("Illegal field character '" + c + "' in setNumberFormat.");
            }
            this.overrideMap.put(Character.valueOf(c), string2);
            this.numberFormatters.put(string2, numberFormat);
        }
        this.useLocalZeroPaddingNumberFormat = false;
    }

    public NumberFormat getNumberFormat(char c) {
        Character c2 = Character.valueOf(c);
        if (this.overrideMap != null && this.overrideMap.containsKey(c2)) {
            String string = this.overrideMap.get(c2).toString();
            NumberFormat numberFormat = this.numberFormatters.get(string);
            return numberFormat;
        }
        return this.numberFormat;
    }

    private void initNumberFormatters(ULocale uLocale) {
        this.numberFormatters = new HashMap();
        this.overrideMap = new HashMap();
        this.processOverrideString(uLocale, this.override);
    }

    private void processOverrideString(ULocale uLocale, String string) {
        if (string == null || string.length() == 0) {
            return;
        }
        int n = 0;
        boolean bl = true;
        while (bl) {
            boolean bl2;
            String string2;
            int n2;
            int n3 = string.indexOf(";", n);
            if (n3 == -1) {
                bl = false;
                n2 = string.length();
            } else {
                n2 = n3;
            }
            String string3 = string.substring(n, n2);
            int n4 = string3.indexOf("=");
            if (n4 == -1) {
                string2 = string3;
                bl2 = true;
            } else {
                string2 = string3.substring(n4 + 1);
                Character c = Character.valueOf(string3.charAt(0));
                this.overrideMap.put(c, string2);
                bl2 = false;
            }
            ULocale uLocale2 = new ULocale(uLocale.getBaseName() + "@numbers=" + string2);
            NumberFormat numberFormat = NumberFormat.createInstance(uLocale2, 0);
            numberFormat.setGroupingUsed(true);
            if (bl2) {
                this.setNumberFormat(numberFormat);
            } else {
                this.useLocalZeroPaddingNumberFormat = false;
            }
            if (!bl2 && !this.numberFormatters.containsKey(string2)) {
                this.numberFormatters.put(string2, numberFormat);
            }
            n = n3 + 1;
        }
    }

    private void parsePattern() {
        this.hasMinute = false;
        this.hasSecond = false;
        this.hasHanYearChar = false;
        boolean bl = false;
        for (int i = 0; i < this.pattern.length(); ++i) {
            char c = this.pattern.charAt(i);
            if (c == '\'') {
                boolean bl2 = bl = !bl;
            }
            if (c == '\u5e74') {
                this.hasHanYearChar = true;
            }
            if (bl) continue;
            if (c == 'm') {
                this.hasMinute = true;
            }
            if (c != 's') continue;
            this.hasSecond = true;
        }
    }

    static boolean access$000(char c, int n) {
        return SimpleDateFormat.isNumeric(c, n);
    }

    static {
        $assertionsDisabled = !SimpleDateFormat.class.desiredAssertionStatus();
        DelayedHebrewMonthCheck = false;
        CALENDAR_FIELD_TO_LEVEL = new int[]{0, 10, 20, 20, 30, 30, 20, 30, 30, 40, 50, 50, 60, 70, 80, 0, 0, 10, 30, 10, 0, 40, 0, 0};
        PATTERN_CHAR_TO_LEVEL = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, 20, 30, 30, 0, 50, -1, -1, 50, 20, 20, -1, 0, -1, 20, -1, 80, -1, 10, 0, 30, 0, 10, 0, -1, -1, -1, -1, -1, -1, 40, -1, 30, 30, 30, -1, 0, 50, -1, -1, 50, -1, 60, -1, -1, -1, 20, 10, 70, -1, 10, 0, 20, 0, 10, 0, -1, -1, -1, -1, -1};
        PATTERN_CHAR_IS_SYNTAX = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
        cachedDefaultLocale = null;
        cachedDefaultPattern = null;
        PATTERN_CHAR_TO_INDEX = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22, 36, -1, 10, 9, 11, 0, 5, -1, -1, 16, 26, 2, -1, 31, -1, 27, -1, 8, -1, 30, 29, 13, 32, 18, 23, -1, -1, -1, -1, -1, -1, 14, 35, 25, 3, 19, -1, 21, 15, -1, -1, 4, -1, 6, -1, -1, -1, 28, 34, 7, -1, 20, 24, 12, 33, 1, 17, -1, -1, -1, -1, -1};
        PATTERN_INDEX_TO_CALENDAR_FIELD = new int[]{0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 17, 18, 19, 20, 21, 15, 15, 18, 2, 2, 2, 15, 1, 15, 15, 15, 19, -1, -2};
        PATTERN_INDEX_TO_DATE_FORMAT_FIELD = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37};
        PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE = new DateFormat.Field[]{DateFormat.Field.ERA, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.HOUR_OF_DAY1, DateFormat.Field.HOUR_OF_DAY0, DateFormat.Field.MINUTE, DateFormat.Field.SECOND, DateFormat.Field.MILLISECOND, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_YEAR, DateFormat.Field.DAY_OF_WEEK_IN_MONTH, DateFormat.Field.WEEK_OF_YEAR, DateFormat.Field.WEEK_OF_MONTH, DateFormat.Field.AM_PM, DateFormat.Field.HOUR1, DateFormat.Field.HOUR0, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR_WOY, DateFormat.Field.DOW_LOCAL, DateFormat.Field.EXTENDED_YEAR, DateFormat.Field.JULIAN_DAY, DateFormat.Field.MILLISECONDS_IN_DAY, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.MONTH, DateFormat.Field.QUARTER, DateFormat.Field.QUARTER, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.RELATED_YEAR, DateFormat.Field.AM_PM_MIDNIGHT_NOON, DateFormat.Field.FLEXIBLE_DAY_PERIOD, DateFormat.Field.TIME_SEPARATOR};
        PARSED_PATTERN_CACHE = new SimpleCache<String, Object[]>();
        DATE_PATTERN_TYPE = new UnicodeSet("[GyYuUQqMLlwWd]").freeze();
    }

    private static class PatternItem {
        final char type;
        final int length;
        final boolean isNumeric;

        PatternItem(char c, int n) {
            this.type = c;
            this.length = n;
            this.isNumeric = SimpleDateFormat.access$000(c, n);
        }
    }

    private static enum ContextValue {
        UNKNOWN,
        CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE,
        CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE,
        CAPITALIZATION_FOR_UI_LIST_OR_MENU,
        CAPITALIZATION_FOR_STANDALONE;

    }
}

