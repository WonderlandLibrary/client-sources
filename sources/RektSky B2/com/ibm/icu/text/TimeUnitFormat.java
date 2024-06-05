package com.ibm.icu.text;

import com.ibm.icu.number.*;
import java.text.*;
import com.ibm.icu.impl.*;
import java.util.*;
import java.io.*;
import com.ibm.icu.util.*;

@Deprecated
public class TimeUnitFormat extends MeasureFormat
{
    @Deprecated
    public static final int FULL_NAME = 0;
    @Deprecated
    public static final int ABBREVIATED_NAME = 1;
    private static final int TOTAL_STYLES = 2;
    private static final long serialVersionUID = -3707773153184971529L;
    private NumberFormat format;
    private ULocale locale;
    private int style;
    private transient Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns;
    private transient PluralRules pluralRules;
    private transient boolean isReady;
    private static final String DEFAULT_PATTERN_FOR_SECOND = "{0} s";
    private static final String DEFAULT_PATTERN_FOR_MINUTE = "{0} min";
    private static final String DEFAULT_PATTERN_FOR_HOUR = "{0} h";
    private static final String DEFAULT_PATTERN_FOR_DAY = "{0} d";
    private static final String DEFAULT_PATTERN_FOR_WEEK = "{0} w";
    private static final String DEFAULT_PATTERN_FOR_MONTH = "{0} m";
    private static final String DEFAULT_PATTERN_FOR_YEAR = "{0} y";
    
    @Deprecated
    public TimeUnitFormat() {
        this(ULocale.getDefault(), 0);
    }
    
    @Deprecated
    public TimeUnitFormat(final ULocale locale) {
        this(locale, 0);
    }
    
    @Deprecated
    public TimeUnitFormat(final Locale locale) {
        this(locale, 0);
    }
    
    @Deprecated
    public TimeUnitFormat(final ULocale locale, final int style) {
        super(locale, (style == 0) ? FormatWidth.WIDE : FormatWidth.SHORT);
        this.format = super.getNumberFormatInternal();
        if (style < 0 || style >= 2) {
            throw new IllegalArgumentException("style should be either FULL_NAME or ABBREVIATED_NAME style");
        }
        this.style = style;
        this.isReady = false;
    }
    
    private TimeUnitFormat(final ULocale locale, final int style, final NumberFormat numberFormat) {
        this(locale, style);
        if (numberFormat != null) {
            this.setNumberFormat((NumberFormat)numberFormat.clone());
        }
    }
    
    @Deprecated
    public TimeUnitFormat(final Locale locale, final int style) {
        this(ULocale.forLocale(locale), style);
    }
    
    @Deprecated
    public TimeUnitFormat setLocale(final ULocale locale) {
        this.setLocale(locale, locale);
        this.clearCache();
        return this;
    }
    
    @Deprecated
    public TimeUnitFormat setLocale(final Locale locale) {
        return this.setLocale(ULocale.forLocale(locale));
    }
    
    @Deprecated
    public TimeUnitFormat setNumberFormat(final NumberFormat format) {
        if (format == this.format) {
            return this;
        }
        if (format == null) {
            if (this.locale == null) {
                this.isReady = false;
            }
            else {
                this.format = NumberFormat.getNumberInstance(this.locale);
            }
        }
        else {
            this.format = format;
        }
        this.clearCache();
        return this;
    }
    
    @Deprecated
    @Override
    public NumberFormat getNumberFormat() {
        return (NumberFormat)this.format.clone();
    }
    
    @Override
    NumberFormat getNumberFormatInternal() {
        return this.format;
    }
    
    @Override
    LocalizedNumberFormatter getNumberFormatter() {
        return ((DecimalFormat)this.format).toNumberFormatter();
    }
    
    @Deprecated
    @Override
    public TimeUnitAmount parseObject(final String source, final ParsePosition pos) {
        if (!this.isReady) {
            this.setup();
        }
        Number resultNumber = null;
        TimeUnit resultTimeUnit = null;
        final int oldPos = pos.getIndex();
        int newPos = -1;
        int longestParseDistance = 0;
        String countOfLongestMatch = null;
        for (final TimeUnit timeUnit : this.timeUnitToCountToPatterns.keySet()) {
            final Map<String, Object[]> countToPattern = this.timeUnitToCountToPatterns.get(timeUnit);
            for (final Map.Entry<String, Object[]> patternEntry : countToPattern.entrySet()) {
                final String count = patternEntry.getKey();
                for (int styl = 0; styl < 2; ++styl) {
                    final MessageFormat pattern = (MessageFormat)patternEntry.getValue()[styl];
                    pos.setErrorIndex(-1);
                    pos.setIndex(oldPos);
                    final Object parsed = pattern.parseObject(source, pos);
                    if (pos.getErrorIndex() == -1) {
                        if (pos.getIndex() != oldPos) {
                            Number temp = null;
                            if (((Object[])parsed).length != 0) {
                                final Object tempObj = ((Object[])parsed)[0];
                                if (tempObj instanceof Number) {
                                    temp = (Number)tempObj;
                                }
                                else {
                                    try {
                                        temp = this.format.parse(tempObj.toString());
                                    }
                                    catch (ParseException e) {
                                        continue;
                                    }
                                }
                            }
                            final int parseDistance = pos.getIndex() - oldPos;
                            if (parseDistance > longestParseDistance) {
                                resultNumber = temp;
                                resultTimeUnit = timeUnit;
                                newPos = pos.getIndex();
                                longestParseDistance = parseDistance;
                                countOfLongestMatch = count;
                            }
                        }
                    }
                }
            }
        }
        if (resultNumber == null && longestParseDistance != 0) {
            if (countOfLongestMatch.equals("zero")) {
                resultNumber = 0;
            }
            else if (countOfLongestMatch.equals("one")) {
                resultNumber = 1;
            }
            else if (countOfLongestMatch.equals("two")) {
                resultNumber = 2;
            }
            else {
                resultNumber = 3;
            }
        }
        if (longestParseDistance == 0) {
            pos.setIndex(oldPos);
            pos.setErrorIndex(0);
            return null;
        }
        pos.setIndex(newPos);
        pos.setErrorIndex(-1);
        return new TimeUnitAmount(resultNumber, resultTimeUnit);
    }
    
    private void setup() {
        if (this.locale == null) {
            if (this.format != null) {
                this.locale = this.format.getLocale(null);
            }
            else {
                this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
            }
            this.setLocale(this.locale, this.locale);
        }
        if (this.format == null) {
            this.format = NumberFormat.getNumberInstance(this.locale);
        }
        this.pluralRules = PluralRules.forLocale(this.locale);
        this.timeUnitToCountToPatterns = new HashMap<TimeUnit, Map<String, Object[]>>();
        final Set<String> pluralKeywords = this.pluralRules.getKeywords();
        this.setup("units/duration", this.timeUnitToCountToPatterns, 0, pluralKeywords);
        this.setup("unitsShort/duration", this.timeUnitToCountToPatterns, 1, pluralKeywords);
        this.isReady = true;
    }
    
    private void setup(final String resourceKey, final Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns, final int style, final Set<String> pluralKeywords) {
        try {
            final ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/unit", this.locale);
            final TimeUnitFormatSetupSink sink = new TimeUnitFormatSetupSink(timeUnitToCountToPatterns, style, pluralKeywords, this.locale);
            resource.getAllItemsWithFallback(resourceKey, sink);
        }
        catch (MissingResourceException ex) {}
        final TimeUnit[] timeUnits = TimeUnit.values();
        final Set<String> keywords = this.pluralRules.getKeywords();
        for (int i = 0; i < timeUnits.length; ++i) {
            final TimeUnit timeUnit = timeUnits[i];
            Map<String, Object[]> countToPatterns = timeUnitToCountToPatterns.get(timeUnit);
            if (countToPatterns == null) {
                countToPatterns = new TreeMap<String, Object[]>();
                timeUnitToCountToPatterns.put(timeUnit, countToPatterns);
            }
            for (final String pluralCount : keywords) {
                if (countToPatterns.get(pluralCount) == null || countToPatterns.get(pluralCount)[style] == null) {
                    this.searchInTree(resourceKey, style, timeUnit, pluralCount, pluralCount, countToPatterns);
                }
            }
        }
    }
    
    private void searchInTree(final String resourceKey, final int styl, final TimeUnit timeUnit, final String srcPluralCount, final String searchPluralCount, final Map<String, Object[]> countToPatterns) {
        ULocale parentLocale = this.locale;
        final String srcTimeUnitName = timeUnit.toString();
        while (parentLocale != null) {
            try {
                ICUResourceBundle unitsRes = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/unit", parentLocale);
                unitsRes = unitsRes.getWithFallback(resourceKey);
                final ICUResourceBundle oneUnitRes = unitsRes.getWithFallback(srcTimeUnitName);
                final String pattern = oneUnitRes.getStringWithFallback(searchPluralCount);
                final MessageFormat messageFormat = new MessageFormat(pattern, this.locale);
                Object[] pair = countToPatterns.get(srcPluralCount);
                if (pair == null) {
                    pair = new Object[2];
                    countToPatterns.put(srcPluralCount, pair);
                }
                pair[styl] = messageFormat;
                return;
            }
            catch (MissingResourceException ex) {
                parentLocale = parentLocale.getFallback();
                continue;
            }
            break;
        }
        if (parentLocale == null && resourceKey.equals("unitsShort")) {
            this.searchInTree("units", styl, timeUnit, srcPluralCount, searchPluralCount, countToPatterns);
            if (countToPatterns.get(srcPluralCount) != null && countToPatterns.get(srcPluralCount)[styl] != null) {
                return;
            }
        }
        if (searchPluralCount.equals("other")) {
            MessageFormat messageFormat2 = null;
            if (timeUnit == TimeUnit.SECOND) {
                messageFormat2 = new MessageFormat("{0} s", this.locale);
            }
            else if (timeUnit == TimeUnit.MINUTE) {
                messageFormat2 = new MessageFormat("{0} min", this.locale);
            }
            else if (timeUnit == TimeUnit.HOUR) {
                messageFormat2 = new MessageFormat("{0} h", this.locale);
            }
            else if (timeUnit == TimeUnit.WEEK) {
                messageFormat2 = new MessageFormat("{0} w", this.locale);
            }
            else if (timeUnit == TimeUnit.DAY) {
                messageFormat2 = new MessageFormat("{0} d", this.locale);
            }
            else if (timeUnit == TimeUnit.MONTH) {
                messageFormat2 = new MessageFormat("{0} m", this.locale);
            }
            else if (timeUnit == TimeUnit.YEAR) {
                messageFormat2 = new MessageFormat("{0} y", this.locale);
            }
            Object[] pair2 = countToPatterns.get(srcPluralCount);
            if (pair2 == null) {
                pair2 = new Object[2];
                countToPatterns.put(srcPluralCount, pair2);
            }
            pair2[styl] = messageFormat2;
        }
        else {
            this.searchInTree(resourceKey, styl, timeUnit, srcPluralCount, "other", countToPatterns);
        }
    }
    
    @Deprecated
    @Override
    public Object clone() {
        final TimeUnitFormat result = (TimeUnitFormat)super.clone();
        result.format = (NumberFormat)this.format.clone();
        return result;
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return super.toTimeUnitProxy();
    }
    
    private Object readResolve() throws ObjectStreamException {
        return new TimeUnitFormat(this.locale, this.style, this.format);
    }
    
    private static final class TimeUnitFormatSetupSink extends UResource.Sink
    {
        Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns;
        int style;
        Set<String> pluralKeywords;
        ULocale locale;
        boolean beenHere;
        
        TimeUnitFormatSetupSink(final Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns, final int style, final Set<String> pluralKeywords, final ULocale locale) {
            this.timeUnitToCountToPatterns = timeUnitToCountToPatterns;
            this.style = style;
            this.pluralKeywords = pluralKeywords;
            this.locale = locale;
            this.beenHere = false;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            if (this.beenHere) {
                return;
            }
            this.beenHere = true;
            final UResource.Table units = value.getTable();
            for (int i = 0; units.getKeyAndValue(i, key, value); ++i) {
                final String timeUnitName = key.toString();
                TimeUnit timeUnit = null;
                if (timeUnitName.equals("year")) {
                    timeUnit = TimeUnit.YEAR;
                }
                else if (timeUnitName.equals("month")) {
                    timeUnit = TimeUnit.MONTH;
                }
                else if (timeUnitName.equals("day")) {
                    timeUnit = TimeUnit.DAY;
                }
                else if (timeUnitName.equals("hour")) {
                    timeUnit = TimeUnit.HOUR;
                }
                else if (timeUnitName.equals("minute")) {
                    timeUnit = TimeUnit.MINUTE;
                }
                else if (timeUnitName.equals("second")) {
                    timeUnit = TimeUnit.SECOND;
                }
                else {
                    if (!timeUnitName.equals("week")) {
                        continue;
                    }
                    timeUnit = TimeUnit.WEEK;
                }
                Map<String, Object[]> countToPatterns = this.timeUnitToCountToPatterns.get(timeUnit);
                if (countToPatterns == null) {
                    countToPatterns = new TreeMap<String, Object[]>();
                    this.timeUnitToCountToPatterns.put(timeUnit, countToPatterns);
                }
                final UResource.Table countsToPatternTable = value.getTable();
                for (int j = 0; countsToPatternTable.getKeyAndValue(j, key, value); ++j) {
                    final String pluralCount = key.toString();
                    if (this.pluralKeywords.contains(pluralCount)) {
                        Object[] pair = countToPatterns.get(pluralCount);
                        if (pair == null) {
                            pair = new Object[2];
                            countToPatterns.put(pluralCount, pair);
                        }
                        if (pair[this.style] == null) {
                            final String pattern = value.getString();
                            final MessageFormat messageFormat = new MessageFormat(pattern, this.locale);
                            pair[this.style] = messageFormat;
                        }
                    }
                }
            }
        }
    }
}
