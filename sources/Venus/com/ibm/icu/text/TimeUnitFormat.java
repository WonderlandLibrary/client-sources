/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.MeasureFormat;
import com.ibm.icu.text.MessageFormat;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Measure;
import com.ibm.icu.util.TimeUnit;
import com.ibm.icu.util.TimeUnitAmount;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.ObjectStreamException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public class TimeUnitFormat
extends MeasureFormat {
    @Deprecated
    public static final int FULL_NAME = 0;
    @Deprecated
    public static final int ABBREVIATED_NAME = 1;
    private static final int TOTAL_STYLES = 2;
    private static final long serialVersionUID = -3707773153184971529L;
    private NumberFormat format = super.getNumberFormatInternal();
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
    public TimeUnitFormat(ULocale uLocale) {
        this(uLocale, 0);
    }

    @Deprecated
    public TimeUnitFormat(Locale locale) {
        this(locale, 0);
    }

    @Deprecated
    public TimeUnitFormat(ULocale uLocale, int n) {
        super(uLocale, n == 0 ? MeasureFormat.FormatWidth.WIDE : MeasureFormat.FormatWidth.SHORT);
        if (n < 0 || n >= 2) {
            throw new IllegalArgumentException("style should be either FULL_NAME or ABBREVIATED_NAME style");
        }
        this.style = n;
        this.isReady = false;
    }

    private TimeUnitFormat(ULocale uLocale, int n, NumberFormat numberFormat) {
        this(uLocale, n);
        if (numberFormat != null) {
            this.setNumberFormat((NumberFormat)numberFormat.clone());
        }
    }

    @Deprecated
    public TimeUnitFormat(Locale locale, int n) {
        this(ULocale.forLocale(locale), n);
    }

    @Deprecated
    public TimeUnitFormat setLocale(ULocale uLocale) {
        this.setLocale(uLocale, uLocale);
        this.clearCache();
        return this;
    }

    @Deprecated
    public TimeUnitFormat setLocale(Locale locale) {
        return this.setLocale(ULocale.forLocale(locale));
    }

    @Deprecated
    public TimeUnitFormat setNumberFormat(NumberFormat numberFormat) {
        if (numberFormat == this.format) {
            return this;
        }
        if (numberFormat == null) {
            if (this.locale == null) {
                this.isReady = false;
            } else {
                this.format = NumberFormat.getNumberInstance(this.locale);
            }
        } else {
            this.format = numberFormat;
        }
        this.clearCache();
        return this;
    }

    @Override
    @Deprecated
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

    @Override
    @Deprecated
    public TimeUnitAmount parseObject(String string, ParsePosition parsePosition) {
        if (!this.isReady) {
            this.setup();
        }
        Integer n = null;
        TimeUnit timeUnit = null;
        int n2 = parsePosition.getIndex();
        int n3 = -1;
        int n4 = 0;
        String string2 = null;
        for (TimeUnit timeUnit2 : this.timeUnitToCountToPatterns.keySet()) {
            Map<String, Object[]> map = this.timeUnitToCountToPatterns.get(timeUnit2);
            for (Map.Entry<String, Object[]> entry : map.entrySet()) {
                String string3 = entry.getKey();
                for (int i = 0; i < 2; ++i) {
                    int n5;
                    MessageFormat messageFormat = (MessageFormat)entry.getValue()[i];
                    parsePosition.setErrorIndex(-1);
                    parsePosition.setIndex(n2);
                    Object object = messageFormat.parseObject(string, parsePosition);
                    if (parsePosition.getErrorIndex() != -1 || parsePosition.getIndex() == n2) continue;
                    Number number = null;
                    if (((Object[])object).length != 0) {
                        Object object2 = ((Object[])object)[0];
                        if (object2 instanceof Number) {
                            number = (Number)object2;
                        } else {
                            try {
                                number = this.format.parse(object2.toString());
                            } catch (ParseException parseException) {
                                continue;
                            }
                        }
                    }
                    if ((n5 = parsePosition.getIndex() - n2) <= n4) continue;
                    n = number;
                    timeUnit = timeUnit2;
                    n3 = parsePosition.getIndex();
                    n4 = n5;
                    string2 = string3;
                }
            }
        }
        if (n == null && n4 != 0) {
            n = string2.equals("zero") ? Integer.valueOf(0) : (string2.equals("one") ? Integer.valueOf(1) : (string2.equals("two") ? Integer.valueOf(2) : Integer.valueOf(3)));
        }
        if (n4 == 0) {
            parsePosition.setIndex(n2);
            parsePosition.setErrorIndex(0);
            return null;
        }
        parsePosition.setIndex(n3);
        parsePosition.setErrorIndex(-1);
        return new TimeUnitAmount((Number)n, timeUnit);
    }

    private void setup() {
        if (this.locale == null) {
            this.locale = this.format != null ? this.format.getLocale(null) : ULocale.getDefault(ULocale.Category.FORMAT);
            this.setLocale(this.locale, this.locale);
        }
        if (this.format == null) {
            this.format = NumberFormat.getNumberInstance(this.locale);
        }
        this.pluralRules = PluralRules.forLocale(this.locale);
        this.timeUnitToCountToPatterns = new HashMap<TimeUnit, Map<String, Object[]>>();
        Set<String> set = this.pluralRules.getKeywords();
        this.setup("units/duration", this.timeUnitToCountToPatterns, 0, set);
        this.setup("unitsShort/duration", this.timeUnitToCountToPatterns, 1, set);
        this.isReady = true;
    }

    private void setup(String string, Map<TimeUnit, Map<String, Object[]>> map, int n, Set<String> set) {
        Object object;
        Object object2;
        try {
            object2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/unit", this.locale);
            object = new TimeUnitFormatSetupSink(map, n, set, this.locale);
            ((ICUResourceBundle)object2).getAllItemsWithFallback(string, (UResource.Sink)object);
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        object2 = TimeUnit.values();
        object = this.pluralRules.getKeywords();
        for (int i = 0; i < ((TimeUnit[])object2).length; ++i) {
            TimeUnit timeUnit = object2[i];
            Map<String, Object[]> map2 = map.get(timeUnit);
            if (map2 == null) {
                map2 = new TreeMap<String, Object[]>();
                map.put(timeUnit, map2);
            }
            Iterator iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                String string2 = (String)iterator2.next();
                if (map2.get(string2) != null && map2.get(string2)[n] != null) continue;
                this.searchInTree(string, n, timeUnit, string2, string2, map2);
            }
        }
    }

    private void searchInTree(String string, int n, TimeUnit timeUnit, String string2, String string3, Map<String, Object[]> map) {
        ULocale uLocale;
        String string4 = timeUnit.toString();
        for (uLocale = this.locale; uLocale != null; uLocale = uLocale.getFallback()) {
            try {
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/unit", uLocale);
                iCUResourceBundle = iCUResourceBundle.getWithFallback(string);
                ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.getWithFallback(string4);
                String string5 = iCUResourceBundle2.getStringWithFallback(string3);
                MessageFormat messageFormat = new MessageFormat(string5, this.locale);
                Object[] objectArray = map.get(string2);
                if (objectArray == null) {
                    objectArray = new Object[2];
                    map.put(string2, objectArray);
                }
                objectArray[n] = messageFormat;
                return;
            } catch (MissingResourceException missingResourceException) {
                continue;
            }
        }
        if (uLocale == null && string.equals("unitsShort")) {
            this.searchInTree("units", n, timeUnit, string2, string3, map);
            if (map.get(string2) != null && map.get(string2)[n] != null) {
                return;
            }
        }
        if (string3.equals("other")) {
            MessageFormat messageFormat = null;
            if (timeUnit == TimeUnit.SECOND) {
                messageFormat = new MessageFormat(DEFAULT_PATTERN_FOR_SECOND, this.locale);
            } else if (timeUnit == TimeUnit.MINUTE) {
                messageFormat = new MessageFormat(DEFAULT_PATTERN_FOR_MINUTE, this.locale);
            } else if (timeUnit == TimeUnit.HOUR) {
                messageFormat = new MessageFormat(DEFAULT_PATTERN_FOR_HOUR, this.locale);
            } else if (timeUnit == TimeUnit.WEEK) {
                messageFormat = new MessageFormat(DEFAULT_PATTERN_FOR_WEEK, this.locale);
            } else if (timeUnit == TimeUnit.DAY) {
                messageFormat = new MessageFormat(DEFAULT_PATTERN_FOR_DAY, this.locale);
            } else if (timeUnit == TimeUnit.MONTH) {
                messageFormat = new MessageFormat(DEFAULT_PATTERN_FOR_MONTH, this.locale);
            } else if (timeUnit == TimeUnit.YEAR) {
                messageFormat = new MessageFormat(DEFAULT_PATTERN_FOR_YEAR, this.locale);
            }
            Object[] objectArray = map.get(string2);
            if (objectArray == null) {
                objectArray = new Object[2];
                map.put(string2, objectArray);
            }
            objectArray[n] = messageFormat;
        } else {
            this.searchInTree(string, n, timeUnit, string2, "other", map);
        }
    }

    @Override
    @Deprecated
    public Object clone() {
        TimeUnitFormat timeUnitFormat = (TimeUnitFormat)super.clone();
        timeUnitFormat.format = (NumberFormat)this.format.clone();
        return timeUnitFormat;
    }

    private Object writeReplace() throws ObjectStreamException {
        return super.toTimeUnitProxy();
    }

    private Object readResolve() throws ObjectStreamException {
        return new TimeUnitFormat(this.locale, this.style, this.format);
    }

    @Override
    @Deprecated
    public Measure parseObject(String string, ParsePosition parsePosition) {
        return this.parseObject(string, parsePosition);
    }

    @Override
    @Deprecated
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parseObject(string, parsePosition);
    }

    private static final class TimeUnitFormatSetupSink
    extends UResource.Sink {
        Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns;
        int style;
        Set<String> pluralKeywords;
        ULocale locale;
        boolean beenHere;

        TimeUnitFormatSetupSink(Map<TimeUnit, Map<String, Object[]>> map, int n, Set<String> set, ULocale uLocale) {
            this.timeUnitToCountToPatterns = map;
            this.style = n;
            this.pluralKeywords = set;
            this.locale = uLocale;
            this.beenHere = false;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            if (this.beenHere) {
                return;
            }
            this.beenHere = true;
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                block17: {
                    TimeUnit timeUnit;
                    block11: {
                        String string;
                        block16: {
                            block15: {
                                block14: {
                                    block13: {
                                        block12: {
                                            block10: {
                                                string = key.toString();
                                                timeUnit = null;
                                                if (!string.equals("year")) break block10;
                                                timeUnit = TimeUnit.YEAR;
                                                break block11;
                                            }
                                            if (!string.equals("month")) break block12;
                                            timeUnit = TimeUnit.MONTH;
                                            break block11;
                                        }
                                        if (!string.equals("day")) break block13;
                                        timeUnit = TimeUnit.DAY;
                                        break block11;
                                    }
                                    if (!string.equals("hour")) break block14;
                                    timeUnit = TimeUnit.HOUR;
                                    break block11;
                                }
                                if (!string.equals("minute")) break block15;
                                timeUnit = TimeUnit.MINUTE;
                                break block11;
                            }
                            if (!string.equals("second")) break block16;
                            timeUnit = TimeUnit.SECOND;
                            break block11;
                        }
                        if (!string.equals("week")) break block17;
                        timeUnit = TimeUnit.WEEK;
                    }
                    Map<String, Object[]> map = this.timeUnitToCountToPatterns.get(timeUnit);
                    if (map == null) {
                        map = new TreeMap<String, Object[]>();
                        this.timeUnitToCountToPatterns.put(timeUnit, map);
                    }
                    UResource.Table table2 = value.getTable();
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        String string = key.toString();
                        if (this.pluralKeywords.contains(string)) {
                            Object[] objectArray = map.get(string);
                            if (objectArray == null) {
                                objectArray = new Object[2];
                                map.put(string, objectArray);
                            }
                            if (objectArray[this.style] == null) {
                                String string2 = value.getString();
                                MessageFormat messageFormat = new MessageFormat(string2, this.locale);
                                objectArray[this.style] = messageFormat;
                            }
                        }
                        ++n2;
                    }
                }
                ++n;
            }
        }
    }
}

