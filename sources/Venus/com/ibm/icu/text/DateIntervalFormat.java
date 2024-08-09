/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.FormattedValueFieldPositionIteratorImpl;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.ConstrainedFieldPosition;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DateIntervalInfo;
import com.ibm.icu.text.DateTimePatternGenerator;
import com.ibm.icu.text.FormattedValue;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.text.UFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.DateInterval;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DateIntervalFormat
extends UFormat {
    private static final long serialVersionUID = 1L;
    private static ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> LOCAL_PATTERN_CACHE = new SimpleCache<String, Map<String, DateIntervalInfo.PatternInfo>>();
    private DateIntervalInfo fInfo;
    private SimpleDateFormat fDateFormat;
    private Calendar fFromCalendar;
    private Calendar fToCalendar;
    private String fSkeleton = null;
    private boolean isDateIntervalInfoDefault;
    private transient Map<String, DateIntervalInfo.PatternInfo> fIntervalPatterns = null;
    private String fDatePattern = null;
    private String fTimePattern = null;
    private String fDateTimeFormat = null;

    private DateIntervalFormat() {
    }

    @Deprecated
    public DateIntervalFormat(String string, DateIntervalInfo dateIntervalInfo, SimpleDateFormat simpleDateFormat) {
        this.fDateFormat = simpleDateFormat;
        dateIntervalInfo.freeze();
        this.fSkeleton = string;
        this.fInfo = dateIntervalInfo;
        this.isDateIntervalInfoDefault = false;
        this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.initializePattern(null);
    }

    private DateIntervalFormat(String string, ULocale uLocale, SimpleDateFormat simpleDateFormat) {
        this.fDateFormat = simpleDateFormat;
        this.fSkeleton = string;
        this.fInfo = new DateIntervalInfo(uLocale).freeze();
        this.isDateIntervalInfoDefault = true;
        this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.initializePattern(LOCAL_PATTERN_CACHE);
    }

    public static final DateIntervalFormat getInstance(String string) {
        return DateIntervalFormat.getInstance(string, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final DateIntervalFormat getInstance(String string, Locale locale) {
        return DateIntervalFormat.getInstance(string, ULocale.forLocale(locale));
    }

    public static final DateIntervalFormat getInstance(String string, ULocale uLocale) {
        DateTimePatternGenerator dateTimePatternGenerator = DateTimePatternGenerator.getInstance(uLocale);
        return new DateIntervalFormat(string, uLocale, new SimpleDateFormat(dateTimePatternGenerator.getBestPattern(string), uLocale));
    }

    public static final DateIntervalFormat getInstance(String string, DateIntervalInfo dateIntervalInfo) {
        return DateIntervalFormat.getInstance(string, ULocale.getDefault(ULocale.Category.FORMAT), dateIntervalInfo);
    }

    public static final DateIntervalFormat getInstance(String string, Locale locale, DateIntervalInfo dateIntervalInfo) {
        return DateIntervalFormat.getInstance(string, ULocale.forLocale(locale), dateIntervalInfo);
    }

    public static final DateIntervalFormat getInstance(String string, ULocale uLocale, DateIntervalInfo dateIntervalInfo) {
        dateIntervalInfo = (DateIntervalInfo)dateIntervalInfo.clone();
        DateTimePatternGenerator dateTimePatternGenerator = DateTimePatternGenerator.getInstance(uLocale);
        return new DateIntervalFormat(string, dateIntervalInfo, new SimpleDateFormat(dateTimePatternGenerator.getBestPattern(string), uLocale));
    }

    @Override
    public synchronized Object clone() {
        DateIntervalFormat dateIntervalFormat = (DateIntervalFormat)super.clone();
        dateIntervalFormat.fDateFormat = (SimpleDateFormat)this.fDateFormat.clone();
        dateIntervalFormat.fInfo = (DateIntervalInfo)this.fInfo.clone();
        dateIntervalFormat.fFromCalendar = (Calendar)this.fFromCalendar.clone();
        dateIntervalFormat.fToCalendar = (Calendar)this.fToCalendar.clone();
        dateIntervalFormat.fDatePattern = this.fDatePattern;
        dateIntervalFormat.fTimePattern = this.fTimePattern;
        dateIntervalFormat.fDateTimeFormat = this.fDateTimeFormat;
        return dateIntervalFormat;
    }

    @Override
    public final StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (object instanceof DateInterval) {
            return this.format((DateInterval)object, stringBuffer, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object (" + object.getClass().getName() + ") as a DateInterval");
    }

    public final StringBuffer format(DateInterval dateInterval, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.formatIntervalImpl(dateInterval, stringBuffer, fieldPosition, null, null);
    }

    public FormattedDateInterval formatToValue(DateInterval dateInterval) {
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        FormatOutput formatOutput = new FormatOutput(null);
        ArrayList<FieldPosition> arrayList = new ArrayList<FieldPosition>();
        this.formatIntervalImpl(dateInterval, stringBuffer, fieldPosition, formatOutput, arrayList);
        if (formatOutput.firstIndex != -1) {
            FormattedValueFieldPositionIteratorImpl.addOverlapSpans(arrayList, SpanField.DATE_INTERVAL_SPAN, formatOutput.firstIndex);
            FormattedValueFieldPositionIteratorImpl.sort(arrayList);
        }
        return new FormattedDateInterval(stringBuffer, arrayList);
    }

    private synchronized StringBuffer formatIntervalImpl(DateInterval dateInterval, StringBuffer stringBuffer, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list) {
        this.fFromCalendar.setTimeInMillis(dateInterval.getFromDate());
        this.fToCalendar.setTimeInMillis(dateInterval.getToDate());
        return this.formatImpl(this.fFromCalendar, this.fToCalendar, stringBuffer, fieldPosition, formatOutput, list);
    }

    @Deprecated
    public String getPatterns(Calendar calendar, Calendar calendar2, Output<String> output) {
        int n;
        if (calendar.get(0) != calendar2.get(0)) {
            n = 0;
        } else if (calendar.get(1) != calendar2.get(1)) {
            n = 1;
        } else if (calendar.get(2) != calendar2.get(2)) {
            n = 2;
        } else if (calendar.get(5) != calendar2.get(5)) {
            n = 5;
        } else if (calendar.get(9) != calendar2.get(9)) {
            n = 9;
        } else if (calendar.get(10) != calendar2.get(10)) {
            n = 10;
        } else if (calendar.get(12) != calendar2.get(12)) {
            n = 12;
        } else if (calendar.get(13) != calendar2.get(13)) {
            n = 13;
        } else {
            return null;
        }
        DateIntervalInfo.PatternInfo patternInfo = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]);
        output.value = patternInfo.getSecondPart();
        return patternInfo.getFirstPart();
    }

    public final StringBuffer format(Calendar calendar, Calendar calendar2, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.formatImpl(calendar, calendar2, stringBuffer, fieldPosition, null, null);
    }

    public FormattedDateInterval formatToValue(Calendar calendar, Calendar calendar2) {
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        FormatOutput formatOutput = new FormatOutput(null);
        ArrayList<FieldPosition> arrayList = new ArrayList<FieldPosition>();
        this.formatImpl(calendar, calendar2, stringBuffer, fieldPosition, formatOutput, arrayList);
        if (formatOutput.firstIndex != -1) {
            FormattedValueFieldPositionIteratorImpl.addOverlapSpans(arrayList, SpanField.DATE_INTERVAL_SPAN, formatOutput.firstIndex);
            FormattedValueFieldPositionIteratorImpl.sort(arrayList);
        }
        return new FormattedDateInterval(stringBuffer, arrayList);
    }

    private synchronized StringBuffer formatImpl(Calendar calendar, Calendar calendar2, StringBuffer stringBuffer, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list) {
        Calendar calendar3;
        Calendar calendar4;
        if (!calendar.isEquivalentTo(calendar2)) {
            throw new IllegalArgumentException("can not format on two different calendars");
        }
        int n = -1;
        if (calendar.get(0) != calendar2.get(0)) {
            n = 0;
        } else if (calendar.get(1) != calendar2.get(1)) {
            n = 1;
        } else if (calendar.get(2) != calendar2.get(2)) {
            n = 2;
        } else if (calendar.get(5) != calendar2.get(5)) {
            n = 5;
        } else if (calendar.get(9) != calendar2.get(9)) {
            n = 9;
        } else if (calendar.get(10) != calendar2.get(10)) {
            n = 10;
        } else if (calendar.get(12) != calendar2.get(12)) {
            n = 12;
        } else if (calendar.get(13) != calendar2.get(13)) {
            n = 13;
        } else {
            return this.fDateFormat.format(calendar, stringBuffer, fieldPosition, list);
        }
        boolean bl = n == 9 || n == 10 || n == 12 || n == 13;
        DateIntervalInfo.PatternInfo patternInfo = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]);
        if (patternInfo == null) {
            if (this.fDateFormat.isFieldUnitIgnored(n)) {
                return this.fDateFormat.format(calendar, stringBuffer, fieldPosition, list);
            }
            return this.fallbackFormat(calendar, calendar2, bl, stringBuffer, fieldPosition, formatOutput, list);
        }
        if (patternInfo.getFirstPart() == null) {
            return this.fallbackFormat(calendar, calendar2, bl, stringBuffer, fieldPosition, formatOutput, list, patternInfo.getSecondPart());
        }
        if (patternInfo.firstDateInPtnIsLaterDate()) {
            if (formatOutput != null) {
                formatOutput.register(1);
            }
            calendar4 = calendar2;
            calendar3 = calendar;
        } else {
            if (formatOutput != null) {
                formatOutput.register(0);
            }
            calendar4 = calendar;
            calendar3 = calendar2;
        }
        String string = this.fDateFormat.toPattern();
        this.fDateFormat.applyPattern(patternInfo.getFirstPart());
        this.fDateFormat.format(calendar4, stringBuffer, fieldPosition, list);
        if (fieldPosition.getEndIndex() > 0) {
            fieldPosition = new FieldPosition(0);
        }
        if (patternInfo.getSecondPart() != null) {
            this.fDateFormat.applyPattern(patternInfo.getSecondPart());
            this.fDateFormat.format(calendar3, stringBuffer, fieldPosition, list);
        }
        this.fDateFormat.applyPattern(string);
        return stringBuffer;
    }

    private final void fallbackFormatRange(Calendar calendar, Calendar calendar2, StringBuffer stringBuffer, StringBuilder stringBuilder, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list) {
        String string = SimpleFormatterImpl.compileToStringMinMaxArguments(this.fInfo.getFallbackIntervalPattern(), stringBuilder, 2, 2);
        long l = 0L;
        while ((l = SimpleFormatterImpl.Int64Iterator.step(string, l, stringBuffer)) != -1L) {
            if (SimpleFormatterImpl.Int64Iterator.getArgIndex(l) == 0) {
                if (formatOutput != null) {
                    formatOutput.register(0);
                }
                this.fDateFormat.format(calendar, stringBuffer, fieldPosition, list);
            } else {
                if (formatOutput != null) {
                    formatOutput.register(1);
                }
                this.fDateFormat.format(calendar2, stringBuffer, fieldPosition, list);
            }
            if (fieldPosition.getEndIndex() <= 0) continue;
            fieldPosition = new FieldPosition(0);
        }
    }

    private final StringBuffer fallbackFormat(Calendar calendar, Calendar calendar2, boolean bl, StringBuffer stringBuffer, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list) {
        boolean bl2;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = bl2 = bl && this.fDatePattern != null && this.fTimePattern != null;
        if (bl2) {
            String string = SimpleFormatterImpl.compileToStringMinMaxArguments(this.fDateTimeFormat, stringBuilder, 2, 2);
            String string2 = this.fDateFormat.toPattern();
            long l = 0L;
            while ((l = SimpleFormatterImpl.Int64Iterator.step(string, l, stringBuffer)) != -1L) {
                if (SimpleFormatterImpl.Int64Iterator.getArgIndex(l) == 0) {
                    this.fDateFormat.applyPattern(this.fTimePattern);
                    this.fallbackFormatRange(calendar, calendar2, stringBuffer, stringBuilder, fieldPosition, formatOutput, list);
                } else {
                    this.fDateFormat.applyPattern(this.fDatePattern);
                    this.fDateFormat.format(calendar, stringBuffer, fieldPosition, list);
                }
                if (fieldPosition.getEndIndex() <= 0) continue;
                fieldPosition = new FieldPosition(0);
            }
            this.fDateFormat.applyPattern(string2);
        } else {
            this.fallbackFormatRange(calendar, calendar2, stringBuffer, stringBuilder, fieldPosition, formatOutput, list);
        }
        return stringBuffer;
    }

    private final StringBuffer fallbackFormat(Calendar calendar, Calendar calendar2, boolean bl, StringBuffer stringBuffer, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list, String string) {
        String string2 = this.fDateFormat.toPattern();
        this.fDateFormat.applyPattern(string);
        this.fallbackFormat(calendar, calendar2, bl, stringBuffer, fieldPosition, formatOutput, list);
        this.fDateFormat.applyPattern(string2);
        return stringBuffer;
    }

    @Override
    @Deprecated
    public Object parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException("parsing is not supported");
    }

    public DateIntervalInfo getDateIntervalInfo() {
        return (DateIntervalInfo)this.fInfo.clone();
    }

    public void setDateIntervalInfo(DateIntervalInfo dateIntervalInfo) {
        this.fInfo = (DateIntervalInfo)dateIntervalInfo.clone();
        this.isDateIntervalInfoDefault = false;
        this.fInfo.freeze();
        if (this.fDateFormat != null) {
            this.initializePattern(null);
        }
    }

    public TimeZone getTimeZone() {
        if (this.fDateFormat != null) {
            return (TimeZone)this.fDateFormat.getTimeZone().clone();
        }
        return TimeZone.getDefault();
    }

    public void setTimeZone(TimeZone timeZone) {
        TimeZone timeZone2 = (TimeZone)timeZone.clone();
        if (this.fDateFormat != null) {
            this.fDateFormat.setTimeZone(timeZone2);
        }
        if (this.fFromCalendar != null) {
            this.fFromCalendar.setTimeZone(timeZone2);
        }
        if (this.fToCalendar != null) {
            this.fToCalendar.setTimeZone(timeZone2);
        }
    }

    public synchronized DateFormat getDateFormat() {
        return (DateFormat)this.fDateFormat.clone();
    }

    private void initializePattern(ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> iCUCache) {
        String string = this.fDateFormat.toPattern();
        ULocale uLocale = this.fDateFormat.getLocale();
        String string2 = null;
        Map<String, DateIntervalInfo.PatternInfo> map = null;
        if (iCUCache != null) {
            string2 = this.fSkeleton != null ? uLocale.toString() + "+" + string + "+" + this.fSkeleton : uLocale.toString() + "+" + string;
            map = iCUCache.get(string2);
        }
        if (map == null) {
            Map<String, DateIntervalInfo.PatternInfo> map2 = this.initializeIntervalPattern(string, uLocale);
            map = Collections.unmodifiableMap(map2);
            if (iCUCache != null) {
                iCUCache.put(string2, map);
            }
        }
        this.fIntervalPatterns = map;
    }

    private Map<String, DateIntervalInfo.PatternInfo> initializeIntervalPattern(String string, ULocale uLocale) {
        boolean bl;
        DateTimePatternGenerator dateTimePatternGenerator = DateTimePatternGenerator.getInstance(uLocale);
        if (this.fSkeleton == null) {
            this.fSkeleton = dateTimePatternGenerator.getSkeleton(string);
        }
        String string2 = this.fSkeleton;
        HashMap<String, DateIntervalInfo.PatternInfo> hashMap = new HashMap<String, DateIntervalInfo.PatternInfo>();
        StringBuilder stringBuilder = new StringBuilder(string2.length());
        StringBuilder stringBuilder2 = new StringBuilder(string2.length());
        StringBuilder stringBuilder3 = new StringBuilder(string2.length());
        StringBuilder stringBuilder4 = new StringBuilder(string2.length());
        DateIntervalFormat.getDateTimeSkeleton(string2, stringBuilder, stringBuilder2, stringBuilder3, stringBuilder4);
        String string3 = stringBuilder.toString();
        String string4 = stringBuilder3.toString();
        String string5 = stringBuilder2.toString();
        String string6 = stringBuilder4.toString();
        if (stringBuilder3.length() != 0 && stringBuilder.length() != 0) {
            this.fDateTimeFormat = this.getConcatenationPattern(uLocale);
        }
        if (!(bl = this.genSeparateDateTimePtn(string5, string6, hashMap, dateTimePatternGenerator))) {
            if (stringBuilder3.length() != 0 && stringBuilder.length() == 0) {
                string4 = "yMd" + string4;
                String string7 = dateTimePatternGenerator.getBestPattern(string4);
                DateIntervalInfo.PatternInfo patternInfo = new DateIntervalInfo.PatternInfo(null, string7, this.fInfo.getDefaultOrder());
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], patternInfo);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], patternInfo);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], patternInfo);
            }
            return hashMap;
        }
        if (stringBuilder3.length() != 0) {
            if (stringBuilder.length() == 0) {
                string4 = "yMd" + string4;
                String string8 = dateTimePatternGenerator.getBestPattern(string4);
                DateIntervalInfo.PatternInfo patternInfo = new DateIntervalInfo.PatternInfo(null, string8, this.fInfo.getDefaultOrder());
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], patternInfo);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], patternInfo);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], patternInfo);
            } else {
                if (!DateIntervalFormat.fieldExistsInSkeleton(5, string3)) {
                    string2 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5] + string2;
                    this.genFallbackPattern(5, string2, hashMap, dateTimePatternGenerator);
                }
                if (!DateIntervalFormat.fieldExistsInSkeleton(2, string3)) {
                    string2 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2] + string2;
                    this.genFallbackPattern(2, string2, hashMap, dateTimePatternGenerator);
                }
                if (!DateIntervalFormat.fieldExistsInSkeleton(1, string3)) {
                    string2 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1] + string2;
                    this.genFallbackPattern(1, string2, hashMap, dateTimePatternGenerator);
                }
                if (this.fDateTimeFormat == null) {
                    this.fDateTimeFormat = "{1} {0}";
                }
                String string9 = dateTimePatternGenerator.getBestPattern(string3);
                this.concatSingleDate2TimeInterval(this.fDateTimeFormat, string9, 9, hashMap);
                this.concatSingleDate2TimeInterval(this.fDateTimeFormat, string9, 10, hashMap);
                this.concatSingleDate2TimeInterval(this.fDateTimeFormat, string9, 12, hashMap);
            }
        }
        return hashMap;
    }

    private String getConcatenationPattern(ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.getWithFallback("calendar/gregorian/DateTimePatterns");
        ICUResourceBundle iCUResourceBundle3 = (ICUResourceBundle)iCUResourceBundle2.get(8);
        if (iCUResourceBundle3.getType() == 0) {
            return iCUResourceBundle3.getString();
        }
        return iCUResourceBundle3.getString(0);
    }

    private void genFallbackPattern(int n, String string, Map<String, DateIntervalInfo.PatternInfo> map, DateTimePatternGenerator dateTimePatternGenerator) {
        String string2 = dateTimePatternGenerator.getBestPattern(string);
        DateIntervalInfo.PatternInfo patternInfo = new DateIntervalInfo.PatternInfo(null, string2, this.fInfo.getDefaultOrder());
        map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], patternInfo);
    }

    private static void getDateTimeSkeleton(String string, StringBuilder stringBuilder, StringBuilder stringBuilder2, StringBuilder stringBuilder3, StringBuilder stringBuilder4) {
        int n;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        block14: for (n = 0; n < string.length(); ++n) {
            char c = string.charAt(n);
            switch (c) {
                case 'E': {
                    stringBuilder.append(c);
                    ++n2;
                    continue block14;
                }
                case 'd': {
                    stringBuilder.append(c);
                    ++n3;
                    continue block14;
                }
                case 'M': {
                    stringBuilder.append(c);
                    ++n4;
                    continue block14;
                }
                case 'y': {
                    stringBuilder.append(c);
                    ++n5;
                    continue block14;
                }
                case 'D': 
                case 'F': 
                case 'G': 
                case 'L': 
                case 'Q': 
                case 'U': 
                case 'W': 
                case 'Y': 
                case 'c': 
                case 'e': 
                case 'g': 
                case 'l': 
                case 'q': 
                case 'r': 
                case 'u': 
                case 'w': {
                    stringBuilder2.append(c);
                    stringBuilder.append(c);
                    continue block14;
                }
                case 'a': {
                    stringBuilder3.append(c);
                    continue block14;
                }
                case 'h': {
                    stringBuilder3.append(c);
                    ++n6;
                    continue block14;
                }
                case 'H': {
                    stringBuilder3.append(c);
                    ++n7;
                    continue block14;
                }
                case 'm': {
                    stringBuilder3.append(c);
                    ++n8;
                    continue block14;
                }
                case 'z': {
                    ++n10;
                    stringBuilder3.append(c);
                    continue block14;
                }
                case 'v': {
                    ++n9;
                    stringBuilder3.append(c);
                    continue block14;
                }
                case 'A': 
                case 'K': 
                case 'S': 
                case 'V': 
                case 'Z': 
                case 'j': 
                case 'k': 
                case 's': {
                    stringBuilder3.append(c);
                    stringBuilder4.append(c);
                }
            }
        }
        if (n5 != 0) {
            for (n = 0; n < n5; ++n) {
                stringBuilder2.append('y');
            }
        }
        if (n4 != 0) {
            if (n4 < 3) {
                stringBuilder2.append('M');
            } else {
                for (n = 0; n < n4 && n < 5; ++n) {
                    stringBuilder2.append('M');
                }
            }
        }
        if (n2 != 0) {
            if (n2 <= 3) {
                stringBuilder2.append('E');
            } else {
                for (n = 0; n < n2 && n < 5; ++n) {
                    stringBuilder2.append('E');
                }
            }
        }
        if (n3 != 0) {
            stringBuilder2.append('d');
        }
        if (n7 != 0) {
            stringBuilder4.append('H');
        } else if (n6 != 0) {
            stringBuilder4.append('h');
        }
        if (n8 != 0) {
            stringBuilder4.append('m');
        }
        if (n10 != 0) {
            stringBuilder4.append('z');
        }
        if (n9 != 0) {
            stringBuilder4.append('v');
        }
    }

    private boolean genSeparateDateTimePtn(String string, String string2, Map<String, DateIntervalInfo.PatternInfo> map, DateTimePatternGenerator dateTimePatternGenerator) {
        String string3 = string2.length() != 0 ? string2 : string;
        BestMatchInfo bestMatchInfo = this.fInfo.getBestSkeleton(string3);
        String string4 = bestMatchInfo.bestMatchSkeleton;
        int n = bestMatchInfo.bestMatchDistanceInfo;
        if (string.length() != 0) {
            this.fDatePattern = dateTimePatternGenerator.getBestPattern(string);
        }
        if (string2.length() != 0) {
            this.fTimePattern = dateTimePatternGenerator.getBestPattern(string2);
        }
        if (n == -1) {
            return true;
        }
        if (string2.length() == 0) {
            this.genIntervalPattern(5, string3, string4, n, map);
            SkeletonAndItsBestMatch skeletonAndItsBestMatch = this.genIntervalPattern(2, string3, string4, n, map);
            if (skeletonAndItsBestMatch != null) {
                string4 = skeletonAndItsBestMatch.skeleton;
                string3 = skeletonAndItsBestMatch.bestMatchSkeleton;
            }
            this.genIntervalPattern(1, string3, string4, n, map);
            this.genIntervalPattern(0, string3, string4, n, map);
        } else {
            this.genIntervalPattern(12, string3, string4, n, map);
            this.genIntervalPattern(10, string3, string4, n, map);
            this.genIntervalPattern(9, string3, string4, n, map);
        }
        return false;
    }

    private SkeletonAndItsBestMatch genIntervalPattern(int n, String string, String string2, int n2, Map<String, DateIntervalInfo.PatternInfo> map) {
        Object object;
        String string3;
        SkeletonAndItsBestMatch skeletonAndItsBestMatch = null;
        DateIntervalInfo.PatternInfo patternInfo = this.fInfo.getIntervalPattern(string2, n);
        if (patternInfo == null) {
            if (SimpleDateFormat.isFieldUnitIgnored(string2, n)) {
                DateIntervalInfo.PatternInfo patternInfo2 = new DateIntervalInfo.PatternInfo(this.fDateFormat.toPattern(), null, this.fInfo.getDefaultOrder());
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], patternInfo2);
                return null;
            }
            if (n == 9) {
                patternInfo = this.fInfo.getIntervalPattern(string2, 10);
                if (patternInfo != null) {
                    map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], patternInfo);
                }
                return null;
            }
            string3 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n];
            string2 = string3 + string2;
            string = string3 + string;
            patternInfo = this.fInfo.getIntervalPattern(string2, n);
            if (patternInfo == null && n2 == 0) {
                object = this.fInfo.getBestSkeleton(string);
                String string4 = ((BestMatchInfo)object).bestMatchSkeleton;
                n2 = ((BestMatchInfo)object).bestMatchDistanceInfo;
                if (string4.length() != 0 && n2 != -1) {
                    patternInfo = this.fInfo.getIntervalPattern(string4, n);
                    string2 = string4;
                }
            }
            if (patternInfo != null) {
                skeletonAndItsBestMatch = new SkeletonAndItsBestMatch(string, string2);
            }
        }
        if (patternInfo != null) {
            if (n2 != 0) {
                string3 = DateIntervalFormat.adjustFieldWidth(string, string2, patternInfo.getFirstPart(), n2);
                object = DateIntervalFormat.adjustFieldWidth(string, string2, patternInfo.getSecondPart(), n2);
                patternInfo = new DateIntervalInfo.PatternInfo(string3, (String)object, patternInfo.firstDateInPtnIsLaterDate());
            }
            map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], patternInfo);
        }
        return skeletonAndItsBestMatch;
    }

    private static String adjustFieldWidth(String string, String string2, String string3, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        if (string3 == null) {
            return null;
        }
        int[] nArray = new int[58];
        int[] nArray2 = new int[58];
        DateIntervalInfo.parseSkeleton(string, nArray);
        DateIntervalInfo.parseSkeleton(string2, nArray2);
        if (n == 2) {
            string3 = string3.replace('v', 'z');
        }
        StringBuilder stringBuilder = new StringBuilder(string3);
        boolean bl = false;
        int n6 = 0;
        int n7 = 0;
        int n8 = 65;
        int n9 = stringBuilder.length();
        for (n5 = 0; n5 < n9; ++n5) {
            n4 = stringBuilder.charAt(n5);
            if (n4 != n6 && n7 > 0) {
                n3 = n6;
                if (n3 == 76) {
                    n3 = 77;
                }
                n2 = nArray2[n3 - n8];
                int n10 = nArray[n3 - n8];
                if (n2 == n7 && n10 > n2) {
                    n7 = n10 - n2;
                    for (int i = 0; i < n7; ++i) {
                        stringBuilder.insert(n5, (char)n6);
                    }
                    n5 += n7;
                    n9 += n7;
                }
                n7 = 0;
            }
            if (n4 == 39) {
                if (n5 + 1 < stringBuilder.length() && stringBuilder.charAt(n5 + 1) == '\'') {
                    ++n5;
                    continue;
                }
                bl = !bl;
                continue;
            }
            if (bl || (n4 < 97 || n4 > 122) && (n4 < 65 || n4 > 90)) continue;
            n6 = n4;
            ++n7;
        }
        if (n7 > 0) {
            n5 = n6;
            if (n5 == 76) {
                n5 = 77;
            }
            n4 = nArray2[n5 - n8];
            n3 = nArray[n5 - n8];
            if (n4 == n7 && n3 > n4) {
                n7 = n3 - n4;
                for (n2 = 0; n2 < n7; ++n2) {
                    stringBuilder.append((char)n6);
                }
            }
        }
        return stringBuilder.toString();
    }

    private void concatSingleDate2TimeInterval(String string, String string2, int n, Map<String, DateIntervalInfo.PatternInfo> map) {
        DateIntervalInfo.PatternInfo patternInfo = map.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]);
        if (patternInfo != null) {
            String string3 = patternInfo.getFirstPart() + patternInfo.getSecondPart();
            String string4 = SimpleFormatterImpl.formatRawPattern(string, 2, 2, string3, string2);
            patternInfo = DateIntervalInfo.genPatternInfo(string4, patternInfo.firstDateInPtnIsLaterDate());
            map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], patternInfo);
        }
    }

    private static boolean fieldExistsInSkeleton(int n, String string) {
        String string2 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n];
        return string.indexOf(string2) != -1;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.initializePattern(this.isDateIntervalInfoDefault ? LOCAL_PATTERN_CACHE : null);
    }

    @Deprecated
    public Map<String, DateIntervalInfo.PatternInfo> getRawPatterns() {
        return this.fIntervalPatterns;
    }

    private static final class FormatOutput {
        int firstIndex = -1;

        private FormatOutput() {
        }

        public void register(int n) {
            if (this.firstIndex == -1) {
                this.firstIndex = n;
            }
        }

        FormatOutput(1 var1_1) {
            this();
        }
    }

    private static final class SkeletonAndItsBestMatch {
        final String skeleton;
        final String bestMatchSkeleton;

        SkeletonAndItsBestMatch(String string, String string2) {
            this.skeleton = string;
            this.bestMatchSkeleton = string2;
        }
    }

    static final class BestMatchInfo {
        final String bestMatchSkeleton;
        final int bestMatchDistanceInfo;

        BestMatchInfo(String string, int n) {
            this.bestMatchSkeleton = string;
            this.bestMatchDistanceInfo = n;
        }
    }

    public static final class SpanField
    extends UFormat.SpanField {
        private static final long serialVersionUID = -6330879259553618133L;
        public static final SpanField DATE_INTERVAL_SPAN = new SpanField("date-interval-span");

        private SpanField(String string) {
            super(string);
        }

        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getName().equals(DATE_INTERVAL_SPAN.getName())) {
                return DATE_INTERVAL_SPAN;
            }
            throw new InvalidObjectException("An invalid object.");
        }
    }

    public static final class FormattedDateInterval
    implements FormattedValue {
        private final String string;
        private final List<FieldPosition> attributes;

        FormattedDateInterval(CharSequence charSequence, List<FieldPosition> list) {
            this.string = charSequence.toString();
            this.attributes = Collections.unmodifiableList(list);
        }

        @Override
        public String toString() {
            return this.string;
        }

        @Override
        public int length() {
            return this.string.length();
        }

        @Override
        public char charAt(int n) {
            return this.string.charAt(n);
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            return this.string.subSequence(n, n2);
        }

        @Override
        public <A extends Appendable> A appendTo(A a) {
            return Utility.appendTo(this.string, a);
        }

        @Override
        public boolean nextPosition(ConstrainedFieldPosition constrainedFieldPosition) {
            return FormattedValueFieldPositionIteratorImpl.nextPosition(this.attributes, constrainedFieldPosition);
        }

        @Override
        public AttributedCharacterIterator toCharacterIterator() {
            return FormattedValueFieldPositionIteratorImpl.toCharacterIterator(this.string, this.attributes);
        }
    }
}

