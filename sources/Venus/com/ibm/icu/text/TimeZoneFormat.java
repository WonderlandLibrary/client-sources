/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.TZDBTimeZoneNames;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.impl.TimeZoneGenericNames;
import com.ibm.icu.impl.TimeZoneNamesImpl;
import com.ibm.icu.impl.ZoneMeta;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.text.UFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TimeZoneFormat
extends UFormat
implements Freezable<TimeZoneFormat>,
Serializable {
    private static final long serialVersionUID = 2281246852693575022L;
    private static final int ISO_Z_STYLE_FLAG = 128;
    private static final int ISO_LOCAL_STYLE_FLAG = 256;
    private ULocale _locale;
    private TimeZoneNames _tznames;
    private String _gmtPattern;
    private String[] _gmtOffsetPatterns;
    private String[] _gmtOffsetDigits;
    private String _gmtZeroFormat;
    private boolean _parseAllStyles;
    private boolean _parseTZDBNames;
    private volatile transient TimeZoneGenericNames _gnames;
    private transient String _gmtPatternPrefix;
    private transient String _gmtPatternSuffix;
    private transient Object[][] _gmtOffsetPatternItems;
    private transient boolean _abuttingOffsetHoursAndMinutes;
    private transient String _region;
    private volatile transient boolean _frozen;
    private volatile transient TimeZoneNames _tzdbNames;
    private static final String TZID_GMT = "Etc/GMT";
    private static final String[] ALT_GMT_STRINGS;
    private static final String DEFAULT_GMT_PATTERN = "GMT{0}";
    private static final String DEFAULT_GMT_ZERO = "GMT";
    private static final String[] DEFAULT_GMT_DIGITS;
    private static final char DEFAULT_GMT_OFFSET_SEP = ':';
    private static final String ASCII_DIGITS = "0123456789";
    private static final String ISO8601_UTC = "Z";
    private static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
    private static final String UNKNOWN_SHORT_ZONE_ID = "unk";
    private static final String UNKNOWN_LOCATION = "Unknown";
    private static final GMTOffsetPatternType[] PARSE_GMT_OFFSET_TYPES;
    private static final int MILLIS_PER_HOUR = 3600000;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int MILLIS_PER_SECOND = 1000;
    private static final int MAX_OFFSET = 86400000;
    private static final int MAX_OFFSET_HOUR = 23;
    private static final int MAX_OFFSET_MINUTE = 59;
    private static final int MAX_OFFSET_SECOND = 59;
    private static final int UNKNOWN_OFFSET = Integer.MAX_VALUE;
    private static TimeZoneFormatCache _tzfCache;
    private static final EnumSet<TimeZoneNames.NameType> ALL_SIMPLE_NAME_TYPES;
    private static final EnumSet<TimeZoneGenericNames.GenericNameType> ALL_GENERIC_NAME_TYPES;
    private static volatile TextTrieMap<String> ZONE_ID_TRIE;
    private static volatile TextTrieMap<String> SHORT_ZONE_ID_TRIE;
    private static final ObjectStreamField[] serialPersistentFields;
    static final boolean $assertionsDisabled;

    protected TimeZoneFormat(ULocale uLocale) {
        Object object;
        String[] stringArray;
        this._locale = uLocale;
        this._tznames = TimeZoneNames.getInstance(uLocale);
        String string = null;
        String string2 = null;
        this._gmtZeroFormat = DEFAULT_GMT_ZERO;
        try {
            stringArray = (String[])ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/zone", uLocale);
            try {
                string = stringArray.getStringWithFallback("zoneStrings/gmtFormat");
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            try {
                string2 = stringArray.getStringWithFallback("zoneStrings/hourFormat");
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            try {
                this._gmtZeroFormat = stringArray.getStringWithFallback("zoneStrings/gmtZeroFormat");
            } catch (MissingResourceException missingResourceException) {}
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        if (string == null) {
            string = DEFAULT_GMT_PATTERN;
        }
        this.initGMTPattern(string);
        stringArray = new String[GMTOffsetPatternType.values().length];
        if (string2 != null) {
            object = string2.split(";", 2);
            stringArray[GMTOffsetPatternType.POSITIVE_H.ordinal()] = TimeZoneFormat.truncateOffsetPattern((String)((Object)object[0]));
            stringArray[GMTOffsetPatternType.POSITIVE_HM.ordinal()] = object[0];
            stringArray[GMTOffsetPatternType.POSITIVE_HMS.ordinal()] = TimeZoneFormat.expandOffsetPattern((String)object[0]);
            stringArray[GMTOffsetPatternType.NEGATIVE_H.ordinal()] = TimeZoneFormat.truncateOffsetPattern((String)object[5]);
            stringArray[GMTOffsetPatternType.NEGATIVE_HM.ordinal()] = object[5];
            stringArray[GMTOffsetPatternType.NEGATIVE_HMS.ordinal()] = TimeZoneFormat.expandOffsetPattern((String)object[5]);
        } else {
            for (GMTOffsetPatternType gMTOffsetPatternType : GMTOffsetPatternType.values()) {
                stringArray[gMTOffsetPatternType.ordinal()] = GMTOffsetPatternType.access$100(gMTOffsetPatternType);
            }
        }
        this.initGMTOffsetPatterns(stringArray);
        this._gmtOffsetDigits = DEFAULT_GMT_DIGITS;
        object = NumberingSystem.getInstance(uLocale);
        if (!((NumberingSystem)object).isAlgorithmic()) {
            this._gmtOffsetDigits = TimeZoneFormat.toCodePoints(((NumberingSystem)object).getDescription());
        }
    }

    public static TimeZoneFormat getInstance(ULocale uLocale) {
        if (uLocale == null) {
            throw new NullPointerException("locale is null");
        }
        return (TimeZoneFormat)_tzfCache.getInstance(uLocale, uLocale);
    }

    public static TimeZoneFormat getInstance(Locale locale) {
        return TimeZoneFormat.getInstance(ULocale.forLocale(locale));
    }

    public TimeZoneNames getTimeZoneNames() {
        return this._tznames;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private TimeZoneGenericNames getTimeZoneGenericNames() {
        if (this._gnames == null) {
            TimeZoneFormat timeZoneFormat = this;
            synchronized (timeZoneFormat) {
                if (this._gnames == null) {
                    this._gnames = TimeZoneGenericNames.getInstance(this._locale);
                }
            }
        }
        return this._gnames;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private TimeZoneNames getTZDBTimeZoneNames() {
        if (this._tzdbNames == null) {
            TimeZoneFormat timeZoneFormat = this;
            synchronized (timeZoneFormat) {
                if (this._tzdbNames == null) {
                    this._tzdbNames = new TZDBTimeZoneNames(this._locale);
                }
            }
        }
        return this._tzdbNames;
    }

    public TimeZoneFormat setTimeZoneNames(TimeZoneNames timeZoneNames) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        this._tznames = timeZoneNames;
        this._gnames = new TimeZoneGenericNames(this._locale, this._tznames);
        return this;
    }

    public String getGMTPattern() {
        return this._gmtPattern;
    }

    public TimeZoneFormat setGMTPattern(String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        this.initGMTPattern(string);
        return this;
    }

    public String getGMTOffsetPattern(GMTOffsetPatternType gMTOffsetPatternType) {
        return this._gmtOffsetPatterns[gMTOffsetPatternType.ordinal()];
    }

    public TimeZoneFormat setGMTOffsetPattern(GMTOffsetPatternType gMTOffsetPatternType, String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (string == null) {
            throw new NullPointerException("Null GMT offset pattern");
        }
        Object[] objectArray = TimeZoneFormat.parseOffsetPattern(string, GMTOffsetPatternType.access$200(gMTOffsetPatternType));
        this._gmtOffsetPatterns[gMTOffsetPatternType.ordinal()] = string;
        this._gmtOffsetPatternItems[gMTOffsetPatternType.ordinal()] = objectArray;
        this.checkAbuttingHoursAndMinutes();
        return this;
    }

    public String getGMTOffsetDigits() {
        StringBuilder stringBuilder = new StringBuilder(this._gmtOffsetDigits.length);
        for (String string : this._gmtOffsetDigits) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public TimeZoneFormat setGMTOffsetDigits(String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (string == null) {
            throw new NullPointerException("Null GMT offset digits");
        }
        String[] stringArray = TimeZoneFormat.toCodePoints(string);
        if (stringArray.length != 10) {
            throw new IllegalArgumentException("Length of digits must be 10");
        }
        this._gmtOffsetDigits = stringArray;
        return this;
    }

    public String getGMTZeroFormat() {
        return this._gmtZeroFormat;
    }

    public TimeZoneFormat setGMTZeroFormat(String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (string == null) {
            throw new NullPointerException("Null GMT zero format");
        }
        if (string.length() == 0) {
            throw new IllegalArgumentException("Empty GMT zero format");
        }
        this._gmtZeroFormat = string;
        return this;
    }

    public TimeZoneFormat setDefaultParseOptions(EnumSet<ParseOption> enumSet) {
        this._parseAllStyles = enumSet.contains((Object)ParseOption.ALL_STYLES);
        this._parseTZDBNames = enumSet.contains((Object)ParseOption.TZ_DATABASE_ABBREVIATIONS);
        return this;
    }

    public EnumSet<ParseOption> getDefaultParseOptions() {
        if (this._parseAllStyles && this._parseTZDBNames) {
            return EnumSet.of(ParseOption.ALL_STYLES, ParseOption.TZ_DATABASE_ABBREVIATIONS);
        }
        if (this._parseAllStyles) {
            return EnumSet.of(ParseOption.ALL_STYLES);
        }
        if (this._parseTZDBNames) {
            return EnumSet.of(ParseOption.TZ_DATABASE_ABBREVIATIONS);
        }
        return EnumSet.noneOf(ParseOption.class);
    }

    public final String formatOffsetISO8601Basic(int n, boolean bl, boolean bl2, boolean bl3) {
        return this.formatOffsetISO8601(n, true, bl, bl2, bl3);
    }

    public final String formatOffsetISO8601Extended(int n, boolean bl, boolean bl2, boolean bl3) {
        return this.formatOffsetISO8601(n, false, bl, bl2, bl3);
    }

    public String formatOffsetLocalizedGMT(int n) {
        return this.formatOffsetLocalizedGMT(n, false);
    }

    public String formatOffsetShortLocalizedGMT(int n) {
        return this.formatOffsetLocalizedGMT(n, true);
    }

    public final String format(Style style, TimeZone timeZone, long l) {
        return this.format(style, timeZone, l, null);
    }

    public String format(Style style, TimeZone timeZone, long l, Output<TimeType> output) {
        String string = null;
        if (output != null) {
            output.value = TimeType.UNKNOWN;
        }
        boolean bl = false;
        switch (style) {
            case GENERIC_LOCATION: {
                string = this.getTimeZoneGenericNames().getGenericLocationName(ZoneMeta.getCanonicalCLDRID(timeZone));
                break;
            }
            case GENERIC_LONG: {
                string = this.getTimeZoneGenericNames().getDisplayName(timeZone, TimeZoneGenericNames.GenericNameType.LONG, l);
                break;
            }
            case GENERIC_SHORT: {
                string = this.getTimeZoneGenericNames().getDisplayName(timeZone, TimeZoneGenericNames.GenericNameType.SHORT, l);
                break;
            }
            case SPECIFIC_LONG: {
                string = this.formatSpecific(timeZone, TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, l, output);
                break;
            }
            case SPECIFIC_SHORT: {
                string = this.formatSpecific(timeZone, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, l, output);
                break;
            }
            case ZONE_ID: {
                string = timeZone.getID();
                bl = true;
                break;
            }
            case ZONE_ID_SHORT: {
                string = ZoneMeta.getShortID(timeZone);
                if (string == null) {
                    string = UNKNOWN_SHORT_ZONE_ID;
                }
                bl = true;
                break;
            }
            case EXEMPLAR_LOCATION: {
                string = this.formatExemplarLocation(timeZone);
                bl = true;
                break;
            }
        }
        if (string == null && !bl) {
            int[] nArray = new int[]{0, 0};
            timeZone.getOffset(l, false, nArray);
            int n = nArray[0] + nArray[1];
            switch (style) {
                case GENERIC_LOCATION: 
                case GENERIC_LONG: 
                case SPECIFIC_LONG: 
                case LOCALIZED_GMT: {
                    string = this.formatOffsetLocalizedGMT(n);
                    break;
                }
                case GENERIC_SHORT: 
                case SPECIFIC_SHORT: 
                case LOCALIZED_GMT_SHORT: {
                    string = this.formatOffsetShortLocalizedGMT(n);
                    break;
                }
                case ISO_BASIC_SHORT: {
                    string = this.formatOffsetISO8601Basic(n, true, true, false);
                    break;
                }
                case ISO_BASIC_LOCAL_SHORT: {
                    string = this.formatOffsetISO8601Basic(n, false, true, false);
                    break;
                }
                case ISO_BASIC_FIXED: {
                    string = this.formatOffsetISO8601Basic(n, true, false, false);
                    break;
                }
                case ISO_BASIC_LOCAL_FIXED: {
                    string = this.formatOffsetISO8601Basic(n, false, false, false);
                    break;
                }
                case ISO_BASIC_FULL: {
                    string = this.formatOffsetISO8601Basic(n, true, false, true);
                    break;
                }
                case ISO_BASIC_LOCAL_FULL: {
                    string = this.formatOffsetISO8601Basic(n, false, false, true);
                    break;
                }
                case ISO_EXTENDED_FIXED: {
                    string = this.formatOffsetISO8601Extended(n, true, false, false);
                    break;
                }
                case ISO_EXTENDED_LOCAL_FIXED: {
                    string = this.formatOffsetISO8601Extended(n, false, false, false);
                    break;
                }
                case ISO_EXTENDED_FULL: {
                    string = this.formatOffsetISO8601Extended(n, true, false, true);
                    break;
                }
                case ISO_EXTENDED_LOCAL_FULL: {
                    string = this.formatOffsetISO8601Extended(n, false, false, true);
                    break;
                }
                default: {
                    if (!$assertionsDisabled) {
                        throw new AssertionError();
                    }
                    break;
                }
            }
            if (output != null) {
                TimeType timeType = output.value = nArray[1] != 0 ? TimeType.DAYLIGHT : TimeType.STANDARD;
            }
        }
        if (!$assertionsDisabled && string == null) {
            throw new AssertionError();
        }
        return string;
    }

    public final int parseOffsetISO8601(String string, ParsePosition parsePosition) {
        return TimeZoneFormat.parseOffsetISO8601(string, parsePosition, false, null);
    }

    public int parseOffsetLocalizedGMT(String string, ParsePosition parsePosition) {
        return this.parseOffsetLocalizedGMT(string, parsePosition, false, null);
    }

    public int parseOffsetShortLocalizedGMT(String string, ParsePosition parsePosition) {
        return this.parseOffsetLocalizedGMT(string, parsePosition, true, null);
    }

    public TimeZone parse(Style style, String string, ParsePosition parsePosition, EnumSet<ParseOption> enumSet, Output<TimeType> output) {
        boolean bl;
        Object object;
        Object object2;
        Object object3;
        Object object4;
        Object object5;
        int n;
        if (output == null) {
            output = new Output<TimeType>(TimeType.UNKNOWN);
        } else {
            output.value = TimeType.UNKNOWN;
        }
        int n2 = parsePosition.getIndex();
        int n3 = string.length();
        boolean bl2 = style == Style.SPECIFIC_LONG || style == Style.GENERIC_LONG || style == Style.GENERIC_LOCATION;
        boolean bl3 = style == Style.SPECIFIC_SHORT || style == Style.GENERIC_SHORT;
        int n4 = 0;
        ParsePosition parsePosition2 = new ParsePosition(n2);
        int n5 = Integer.MAX_VALUE;
        int n6 = -1;
        if (bl2 || bl3) {
            Output<Boolean> output2 = new Output<Boolean>(false);
            n = this.parseOffsetLocalizedGMT(string, parsePosition2, bl3, output2);
            if (parsePosition2.getErrorIndex() == -1) {
                if (parsePosition2.getIndex() == n3 || ((Boolean)output2.value).booleanValue()) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(n);
                }
                n5 = n;
                n6 = parsePosition2.getIndex();
            }
            n4 |= Style.LOCALIZED_GMT.flag | Style.LOCALIZED_GMT_SHORT.flag;
        }
        boolean bl4 = enumSet == null ? this.getDefaultParseOptions().contains((Object)ParseOption.TZ_DATABASE_ABBREVIATIONS) : enumSet.contains((Object)ParseOption.TZ_DATABASE_ABBREVIATIONS);
        switch (style) {
            case LOCALIZED_GMT: {
                parsePosition2.setIndex(n2);
                parsePosition2.setErrorIndex(-1);
                n = this.parseOffsetLocalizedGMT(string, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(n);
                }
                n4 |= Style.LOCALIZED_GMT_SHORT.flag;
                break;
            }
            case LOCALIZED_GMT_SHORT: {
                parsePosition2.setIndex(n2);
                parsePosition2.setErrorIndex(-1);
                n = this.parseOffsetShortLocalizedGMT(string, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(n);
                }
                n4 |= Style.LOCALIZED_GMT.flag;
                break;
            }
            case ISO_BASIC_SHORT: 
            case ISO_BASIC_FIXED: 
            case ISO_BASIC_FULL: 
            case ISO_EXTENDED_FIXED: 
            case ISO_EXTENDED_FULL: {
                parsePosition2.setIndex(n2);
                parsePosition2.setErrorIndex(-1);
                n = this.parseOffsetISO8601(string, parsePosition2);
                if (parsePosition2.getErrorIndex() != -1) break;
                parsePosition.setIndex(parsePosition2.getIndex());
                return this.getTimeZoneForOffset(n);
            }
            case ISO_BASIC_LOCAL_SHORT: 
            case ISO_BASIC_LOCAL_FIXED: 
            case ISO_BASIC_LOCAL_FULL: 
            case ISO_EXTENDED_LOCAL_FIXED: 
            case ISO_EXTENDED_LOCAL_FULL: {
                parsePosition2.setIndex(n2);
                parsePosition2.setErrorIndex(-1);
                object5 = new Output<Boolean>(false);
                n = TimeZoneFormat.parseOffsetISO8601(string, parsePosition2, false, object5);
                if (parsePosition2.getErrorIndex() != -1 || !((Boolean)((Output)object5).value).booleanValue()) break;
                parsePosition.setIndex(parsePosition2.getIndex());
                return this.getTimeZoneForOffset(n);
            }
            case SPECIFIC_LONG: 
            case SPECIFIC_SHORT: {
                object5 = null;
                if (style == Style.SPECIFIC_LONG) {
                    object5 = EnumSet.of(TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT);
                } else {
                    if (!$assertionsDisabled && style != Style.SPECIFIC_SHORT) {
                        throw new AssertionError();
                    }
                    object5 = EnumSet.of(TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT);
                }
                object4 = this._tznames.find(string, n2, (EnumSet<TimeZoneNames.NameType>)object5);
                if (object4 != null) {
                    object3 = null;
                    object2 = object4.iterator();
                    while (object2.hasNext()) {
                        object = (TimeZoneNames.MatchInfo)object2.next();
                        if (n2 + ((TimeZoneNames.MatchInfo)object).matchLength() <= n6) continue;
                        object3 = object;
                        n6 = n2 + ((TimeZoneNames.MatchInfo)object).matchLength();
                    }
                    if (object3 != null) {
                        output.value = this.getTimeType(((TimeZoneNames.MatchInfo)object3).nameType());
                        parsePosition.setIndex(n6);
                        return TimeZone.getTimeZone(this.getTimeZoneID(((TimeZoneNames.MatchInfo)object3).tzID(), ((TimeZoneNames.MatchInfo)object3).mzID()));
                    }
                }
                if (!bl4 || style != Style.SPECIFIC_SHORT) break;
                if (!$assertionsDisabled && !((AbstractCollection)object5).contains((Object)TimeZoneNames.NameType.SHORT_STANDARD)) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && !((AbstractCollection)object5).contains((Object)TimeZoneNames.NameType.SHORT_DAYLIGHT)) {
                    throw new AssertionError();
                }
                object3 = this.getTZDBTimeZoneNames().find(string, n2, (EnumSet<TimeZoneNames.NameType>)object5);
                if (object3 == null) break;
                object2 = null;
                object = object3.iterator();
                while (object.hasNext()) {
                    TimeZoneNames.MatchInfo matchInfo = (TimeZoneNames.MatchInfo)object.next();
                    if (n2 + matchInfo.matchLength() <= n6) continue;
                    object2 = matchInfo;
                    n6 = n2 + matchInfo.matchLength();
                }
                if (object2 == null) break;
                output.value = this.getTimeType(((TimeZoneNames.MatchInfo)object2).nameType());
                parsePosition.setIndex(n6);
                return TimeZone.getTimeZone(this.getTimeZoneID(((TimeZoneNames.MatchInfo)object2).tzID(), ((TimeZoneNames.MatchInfo)object2).mzID()));
            }
            case GENERIC_LOCATION: 
            case GENERIC_LONG: 
            case GENERIC_SHORT: {
                object5 = null;
                switch (style) {
                    case GENERIC_LOCATION: {
                        object5 = EnumSet.of(TimeZoneGenericNames.GenericNameType.LOCATION);
                        break;
                    }
                    case GENERIC_LONG: {
                        object5 = EnumSet.of(TimeZoneGenericNames.GenericNameType.LONG, TimeZoneGenericNames.GenericNameType.LOCATION);
                        break;
                    }
                    case GENERIC_SHORT: {
                        object5 = EnumSet.of(TimeZoneGenericNames.GenericNameType.SHORT, TimeZoneGenericNames.GenericNameType.LOCATION);
                        break;
                    }
                    default: {
                        if (!$assertionsDisabled) {
                            throw new AssertionError();
                        }
                        break;
                    }
                }
                object4 = this.getTimeZoneGenericNames().findBestMatch(string, n2, (EnumSet<TimeZoneGenericNames.GenericNameType>)object5);
                if (object4 == null || n2 + ((TimeZoneGenericNames.GenericMatchInfo)object4).matchLength() <= n6) break;
                output.value = ((TimeZoneGenericNames.GenericMatchInfo)object4).timeType();
                parsePosition.setIndex(n2 + ((TimeZoneGenericNames.GenericMatchInfo)object4).matchLength());
                return TimeZone.getTimeZone(((TimeZoneGenericNames.GenericMatchInfo)object4).tzID());
            }
            case ZONE_ID: {
                parsePosition2.setIndex(n2);
                parsePosition2.setErrorIndex(-1);
                object5 = TimeZoneFormat.parseZoneID(string, parsePosition2);
                if (parsePosition2.getErrorIndex() != -1) break;
                parsePosition.setIndex(parsePosition2.getIndex());
                return TimeZone.getTimeZone(object5);
            }
            case ZONE_ID_SHORT: {
                parsePosition2.setIndex(n2);
                parsePosition2.setErrorIndex(-1);
                object5 = TimeZoneFormat.parseShortZoneID(string, parsePosition2);
                if (parsePosition2.getErrorIndex() != -1) break;
                parsePosition.setIndex(parsePosition2.getIndex());
                return TimeZone.getTimeZone(object5);
            }
            case EXEMPLAR_LOCATION: {
                parsePosition2.setIndex(n2);
                parsePosition2.setErrorIndex(-1);
                object5 = this.parseExemplarLocation(string, parsePosition2);
                if (parsePosition2.getErrorIndex() != -1) break;
                parsePosition.setIndex(parsePosition2.getIndex());
                return TimeZone.getTimeZone(object5);
            }
        }
        n4 |= style.flag;
        if (n6 > n2) {
            if (!$assertionsDisabled && n5 == Integer.MAX_VALUE) {
                throw new AssertionError();
            }
            parsePosition.setIndex(n6);
            return this.getTimeZoneForOffset(n5);
        }
        object5 = null;
        object4 = TimeType.UNKNOWN;
        if (!$assertionsDisabled && n6 >= 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n5 != Integer.MAX_VALUE) {
            throw new AssertionError();
        }
        if (n6 < n3 && ((n4 & 0x80) == 0 || (n4 & 0x100) == 0)) {
            parsePosition2.setIndex(n2);
            parsePosition2.setErrorIndex(-1);
            object3 = new Output<Boolean>(false);
            n = TimeZoneFormat.parseOffsetISO8601(string, parsePosition2, false, object3);
            if (parsePosition2.getErrorIndex() == -1) {
                if (parsePosition2.getIndex() == n3 || ((Boolean)((Output)object3).value).booleanValue()) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(n);
                }
                if (n6 < parsePosition2.getIndex()) {
                    n5 = n;
                    object5 = null;
                    object4 = TimeType.UNKNOWN;
                    n6 = parsePosition2.getIndex();
                    if (!$assertionsDisabled && n6 != n2 + 1) {
                        throw new AssertionError();
                    }
                }
            }
        }
        if (n6 < n3 && (n4 & Style.LOCALIZED_GMT.flag) == 0) {
            parsePosition2.setIndex(n2);
            parsePosition2.setErrorIndex(-1);
            object3 = new Output<Boolean>(false);
            n = this.parseOffsetLocalizedGMT(string, parsePosition2, false, (Output<Boolean>)object3);
            if (parsePosition2.getErrorIndex() == -1) {
                if (parsePosition2.getIndex() == n3 || ((Boolean)((Output)object3).value).booleanValue()) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(n);
                }
                if (n6 < parsePosition2.getIndex()) {
                    n5 = n;
                    object5 = null;
                    object4 = TimeType.UNKNOWN;
                    n6 = parsePosition2.getIndex();
                }
            }
        }
        if (n6 < n3 && (n4 & Style.LOCALIZED_GMT_SHORT.flag) == 0) {
            parsePosition2.setIndex(n2);
            parsePosition2.setErrorIndex(-1);
            object3 = new Output<Boolean>(false);
            n = this.parseOffsetLocalizedGMT(string, parsePosition2, true, (Output<Boolean>)object3);
            if (parsePosition2.getErrorIndex() == -1) {
                if (parsePosition2.getIndex() == n3 || ((Boolean)((Output)object3).value).booleanValue()) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(n);
                }
                if (n6 < parsePosition2.getIndex()) {
                    n5 = n;
                    object5 = null;
                    object4 = TimeType.UNKNOWN;
                    n6 = parsePosition2.getIndex();
                }
            }
        }
        boolean bl5 = bl = enumSet == null ? this.getDefaultParseOptions().contains((Object)ParseOption.ALL_STYLES) : enumSet.contains((Object)ParseOption.ALL_STYLES);
        if (bl) {
            TimeZoneNames.MatchInfo matchInfo;
            Iterator iterator2;
            if (n6 < n3) {
                object2 = this._tznames.find(string, n2, ALL_SIMPLE_NAME_TYPES);
                object = null;
                int n7 = -1;
                if (object2 != null) {
                    iterator2 = object2.iterator();
                    while (iterator2.hasNext()) {
                        matchInfo = (TimeZoneNames.MatchInfo)iterator2.next();
                        if (n2 + matchInfo.matchLength() <= n7) continue;
                        object = matchInfo;
                        n7 = n2 + matchInfo.matchLength();
                    }
                }
                if (n6 < n7) {
                    n6 = n7;
                    object5 = this.getTimeZoneID(((TimeZoneNames.MatchInfo)object).tzID(), ((TimeZoneNames.MatchInfo)object).mzID());
                    object4 = this.getTimeType(((TimeZoneNames.MatchInfo)object).nameType());
                    n5 = Integer.MAX_VALUE;
                }
            }
            if (bl4 && n6 < n3 && (n4 & Style.SPECIFIC_SHORT.flag) == 0) {
                object2 = this.getTZDBTimeZoneNames().find(string, n2, ALL_SIMPLE_NAME_TYPES);
                object = null;
                int n8 = -1;
                if (object2 != null) {
                    iterator2 = object2.iterator();
                    while (iterator2.hasNext()) {
                        matchInfo = (TimeZoneNames.MatchInfo)iterator2.next();
                        if (n2 + matchInfo.matchLength() <= n8) continue;
                        object = matchInfo;
                        n8 = n2 + matchInfo.matchLength();
                    }
                    if (n6 < n8) {
                        n6 = n8;
                        object5 = this.getTimeZoneID(((TimeZoneNames.MatchInfo)object).tzID(), ((TimeZoneNames.MatchInfo)object).mzID());
                        object4 = this.getTimeType(((TimeZoneNames.MatchInfo)object).nameType());
                        n5 = Integer.MAX_VALUE;
                    }
                }
            }
            if (n6 < n3 && (object2 = this.getTimeZoneGenericNames().findBestMatch(string, n2, ALL_GENERIC_NAME_TYPES)) != null && n6 < n2 + ((TimeZoneGenericNames.GenericMatchInfo)object2).matchLength()) {
                n6 = n2 + ((TimeZoneGenericNames.GenericMatchInfo)object2).matchLength();
                object5 = ((TimeZoneGenericNames.GenericMatchInfo)object2).tzID();
                object4 = ((TimeZoneGenericNames.GenericMatchInfo)object2).timeType();
                n5 = Integer.MAX_VALUE;
            }
            if (n6 < n3 && (n4 & Style.ZONE_ID.flag) == 0) {
                parsePosition2.setIndex(n2);
                parsePosition2.setErrorIndex(-1);
                object2 = TimeZoneFormat.parseZoneID(string, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1 && n6 < parsePosition2.getIndex()) {
                    n6 = parsePosition2.getIndex();
                    object5 = object2;
                    object4 = TimeType.UNKNOWN;
                    n5 = Integer.MAX_VALUE;
                }
            }
            if (n6 < n3 && (n4 & Style.ZONE_ID_SHORT.flag) == 0) {
                parsePosition2.setIndex(n2);
                parsePosition2.setErrorIndex(-1);
                object2 = TimeZoneFormat.parseShortZoneID(string, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1 && n6 < parsePosition2.getIndex()) {
                    n6 = parsePosition2.getIndex();
                    object5 = object2;
                    object4 = TimeType.UNKNOWN;
                    n5 = Integer.MAX_VALUE;
                }
            }
        }
        if (n6 > n2) {
            object2 = null;
            if (object5 != null) {
                object2 = TimeZone.getTimeZone(object5);
            } else {
                if (!$assertionsDisabled && n5 == Integer.MAX_VALUE) {
                    throw new AssertionError();
                }
                object2 = this.getTimeZoneForOffset(n5);
            }
            output.value = object4;
            parsePosition.setIndex(n6);
            return object2;
        }
        parsePosition.setErrorIndex(n2);
        return null;
    }

    public TimeZone parse(Style style, String string, ParsePosition parsePosition, Output<TimeType> output) {
        return this.parse(style, string, parsePosition, null, output);
    }

    public final TimeZone parse(String string, ParsePosition parsePosition) {
        return this.parse(Style.GENERIC_LOCATION, string, parsePosition, EnumSet.of(ParseOption.ALL_STYLES), null);
    }

    public final TimeZone parse(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        TimeZone timeZone = this.parse(string, parsePosition);
        if (parsePosition.getErrorIndex() >= 0) {
            throw new ParseException("Unparseable time zone: \"" + string + "\"", 0);
        }
        if (!$assertionsDisabled && timeZone == null) {
            throw new AssertionError();
        }
        return timeZone;
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        TimeZone timeZone = null;
        long l = System.currentTimeMillis();
        if (object instanceof TimeZone) {
            timeZone = (TimeZone)object;
        } else if (object instanceof Calendar) {
            timeZone = ((Calendar)object).getTimeZone();
            l = ((Calendar)object).getTimeInMillis();
        } else {
            throw new IllegalArgumentException("Cannot format given Object (" + object.getClass().getName() + ") as a time zone");
        }
        if (!$assertionsDisabled && timeZone == null) {
            throw new AssertionError();
        }
        String string = this.formatOffsetLocalizedGMT(timeZone.getOffset(l));
        stringBuffer.append(string);
        if (fieldPosition.getFieldAttribute() == DateFormat.Field.TIME_ZONE || fieldPosition.getField() == 17) {
            fieldPosition.setBeginIndex(0);
            fieldPosition.setEndIndex(string.length());
        }
        return stringBuffer;
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        stringBuffer = this.format(object, stringBuffer, fieldPosition);
        AttributedString attributedString = new AttributedString(stringBuffer.toString());
        attributedString.addAttribute(DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE);
        return attributedString.getIterator();
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parse(string, parsePosition);
    }

    private String formatOffsetLocalizedGMT(int n, boolean bl) {
        if (n == 0) {
            return this._gmtZeroFormat;
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl2 = true;
        if (n < 0) {
            n = -n;
            bl2 = false;
        }
        int n2 = n / 3600000;
        int n3 = (n %= 3600000) / 60000;
        int n4 = (n %= 60000) / 1000;
        if (n2 > 23 || n3 > 59 || n4 > 59) {
            throw new IllegalArgumentException("Offset out of range :" + n);
        }
        Object[] objectArray = bl2 ? (n4 != 0 ? this._gmtOffsetPatternItems[GMTOffsetPatternType.POSITIVE_HMS.ordinal()] : (n3 != 0 || !bl ? this._gmtOffsetPatternItems[GMTOffsetPatternType.POSITIVE_HM.ordinal()] : this._gmtOffsetPatternItems[GMTOffsetPatternType.POSITIVE_H.ordinal()])) : (n4 != 0 ? this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_HMS.ordinal()] : (n3 != 0 || !bl ? this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_HM.ordinal()] : this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_H.ordinal()]));
        stringBuilder.append(this._gmtPatternPrefix);
        block5: for (Object object : objectArray) {
            if (object instanceof String) {
                stringBuilder.append((String)object);
                continue;
            }
            if (!(object instanceof GMTOffsetField)) continue;
            GMTOffsetField gMTOffsetField = (GMTOffsetField)object;
            switch (gMTOffsetField.getType()) {
                case 'H': {
                    this.appendOffsetDigits(stringBuilder, n2, bl ? 1 : 2);
                    continue block5;
                }
                case 'm': {
                    this.appendOffsetDigits(stringBuilder, n3, 2);
                    continue block5;
                }
                case 's': {
                    this.appendOffsetDigits(stringBuilder, n4, 2);
                }
            }
        }
        stringBuilder.append(this._gmtPatternSuffix);
        return stringBuilder.toString();
    }

    private String formatOffsetISO8601(int n, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n2;
        int n3;
        Character c;
        int n4;
        int n5 = n4 = n < 0 ? -n : n;
        if (bl2 && (n4 < 1000 || bl4 && n4 < 60000)) {
            return ISO8601_UTC;
        }
        OffsetFields offsetFields = bl3 ? OffsetFields.H : OffsetFields.HM;
        OffsetFields offsetFields2 = bl4 ? OffsetFields.HM : OffsetFields.HMS;
        Character c2 = c = bl ? null : Character.valueOf(':');
        if (n4 >= 86400000) {
            throw new IllegalArgumentException("Offset out of range :" + n);
        }
        int[] nArray = new int[]{n4 / 3600000, (n4 %= 3600000) / 60000, (n4 %= 60000) / 1000};
        if (!($assertionsDisabled || nArray[0] >= 0 && nArray[0] <= 23)) {
            throw new AssertionError();
        }
        if (!($assertionsDisabled || nArray[1] >= 0 && nArray[1] <= 59)) {
            throw new AssertionError();
        }
        if (!($assertionsDisabled || nArray[2] >= 0 && nArray[2] <= 59)) {
            throw new AssertionError();
        }
        for (n3 = offsetFields2.ordinal(); n3 > offsetFields.ordinal() && nArray[n3] == 0; --n3) {
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n6 = 43;
        if (n < 0) {
            for (n2 = 0; n2 <= n3; ++n2) {
                if (nArray[n2] == 0) continue;
                n6 = 45;
                break;
            }
        }
        stringBuilder.append((char)n6);
        for (n2 = 0; n2 <= n3; ++n2) {
            if (c != null && n2 != 0) {
                stringBuilder.append(c);
            }
            if (nArray[n2] < 10) {
                stringBuilder.append('0');
            }
            stringBuilder.append(nArray[n2]);
        }
        return stringBuilder.toString();
    }

    private String formatSpecific(TimeZone timeZone, TimeZoneNames.NameType nameType, TimeZoneNames.NameType nameType2, long l, Output<TimeType> output) {
        String string;
        if (!$assertionsDisabled && nameType != TimeZoneNames.NameType.LONG_STANDARD && nameType != TimeZoneNames.NameType.SHORT_STANDARD) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && nameType2 != TimeZoneNames.NameType.LONG_DAYLIGHT && nameType2 != TimeZoneNames.NameType.SHORT_DAYLIGHT) {
            throw new AssertionError();
        }
        boolean bl = timeZone.inDaylightTime(new Date(l));
        String string2 = string = bl ? this.getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID(timeZone), nameType2, l) : this.getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID(timeZone), nameType, l);
        if (string != null && output != null) {
            output.value = bl ? TimeType.DAYLIGHT : TimeType.STANDARD;
        }
        return string;
    }

    private String formatExemplarLocation(TimeZone timeZone) {
        String string = this.getTimeZoneNames().getExemplarLocationName(ZoneMeta.getCanonicalCLDRID(timeZone));
        if (string == null && (string = this.getTimeZoneNames().getExemplarLocationName(UNKNOWN_ZONE_ID)) == null) {
            string = UNKNOWN_LOCATION;
        }
        return string;
    }

    private String getTimeZoneID(String string, String string2) {
        String string3 = string;
        if (string3 == null) {
            if (!$assertionsDisabled && string2 == null) {
                throw new AssertionError();
            }
            string3 = this._tznames.getReferenceZoneID(string2, this.getTargetRegion());
            if (string3 == null) {
                throw new IllegalArgumentException("Invalid mzID: " + string2);
            }
        }
        return string3;
    }

    private synchronized String getTargetRegion() {
        if (this._region == null) {
            this._region = this._locale.getCountry();
            if (this._region.length() == 0) {
                ULocale uLocale = ULocale.addLikelySubtags(this._locale);
                this._region = uLocale.getCountry();
                if (this._region.length() == 0) {
                    this._region = "001";
                }
            }
        }
        return this._region;
    }

    private TimeType getTimeType(TimeZoneNames.NameType nameType) {
        switch (nameType) {
            case LONG_STANDARD: 
            case SHORT_STANDARD: {
                return TimeType.STANDARD;
            }
            case LONG_DAYLIGHT: 
            case SHORT_DAYLIGHT: {
                return TimeType.DAYLIGHT;
            }
        }
        return TimeType.UNKNOWN;
    }

    private void initGMTPattern(String string) {
        int n = string.indexOf("{0}");
        if (n < 0) {
            throw new IllegalArgumentException("Bad localized GMT pattern: " + string);
        }
        this._gmtPattern = string;
        this._gmtPatternPrefix = TimeZoneFormat.unquote(string.substring(0, n));
        this._gmtPatternSuffix = TimeZoneFormat.unquote(string.substring(n + 3));
    }

    private static String unquote(String string) {
        if (string.indexOf(39) < 0) {
            return string;
        }
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\'') {
                if (bl) {
                    stringBuilder.append(c);
                    bl = false;
                } else {
                    bl = true;
                }
                bl2 = !bl2;
                continue;
            }
            bl = false;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private void initGMTOffsetPatterns(String[] stringArray) {
        int n = GMTOffsetPatternType.values().length;
        if (stringArray.length < n) {
            throw new IllegalArgumentException("Insufficient number of elements in gmtOffsetPatterns");
        }
        Object[][] objectArrayArray = new Object[n][];
        for (GMTOffsetPatternType gMTOffsetPatternType : GMTOffsetPatternType.values()) {
            int n2 = gMTOffsetPatternType.ordinal();
            Object[] objectArray = TimeZoneFormat.parseOffsetPattern(stringArray[n2], GMTOffsetPatternType.access$200(gMTOffsetPatternType));
            objectArrayArray[n2] = objectArray;
        }
        this._gmtOffsetPatterns = new String[n];
        System.arraycopy(stringArray, 0, this._gmtOffsetPatterns, 0, n);
        this._gmtOffsetPatternItems = objectArrayArray;
        this.checkAbuttingHoursAndMinutes();
    }

    private void checkAbuttingHoursAndMinutes() {
        this._abuttingOffsetHoursAndMinutes = false;
        block0: for (Object[] objectArray : this._gmtOffsetPatternItems) {
            boolean bl = false;
            for (Object object : objectArray) {
                if (object instanceof GMTOffsetField) {
                    GMTOffsetField gMTOffsetField = (GMTOffsetField)object;
                    if (bl) {
                        this._abuttingOffsetHoursAndMinutes = true;
                        continue;
                    }
                    if (gMTOffsetField.getType() != 'H') continue;
                    bl = true;
                    continue;
                }
                if (bl) continue block0;
            }
        }
    }

    private static Object[] parseOffsetPattern(String string, String string2) {
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        char c = '\u0000';
        int n = 1;
        boolean bl3 = false;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        BitSet bitSet = new BitSet(string2.length());
        for (int i = 0; i < string.length(); ++i) {
            char c2 = string.charAt(i);
            if (c2 == '\'') {
                if (bl) {
                    stringBuilder.append('\'');
                    bl = false;
                } else {
                    bl = true;
                    if (c != '\u0000') {
                        if (!GMTOffsetField.isValid(c, n)) {
                            bl3 = true;
                            break;
                        }
                        arrayList.add(new GMTOffsetField(c, n));
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
            int n2 = string2.indexOf(c2);
            if (n2 >= 0) {
                if (c2 == c) {
                    ++n;
                    continue;
                }
                if (c == '\u0000') {
                    if (stringBuilder.length() > 0) {
                        arrayList.add(stringBuilder.toString());
                        stringBuilder.setLength(0);
                    }
                } else if (GMTOffsetField.isValid(c, n)) {
                    arrayList.add(new GMTOffsetField(c, n));
                } else {
                    bl3 = true;
                    break;
                }
                c = c2;
                n = 1;
                bitSet.set(n2);
                continue;
            }
            if (c != '\u0000') {
                if (!GMTOffsetField.isValid(c, n)) {
                    bl3 = true;
                    break;
                }
                arrayList.add(new GMTOffsetField(c, n));
                c = '\u0000';
            }
            stringBuilder.append(c2);
        }
        if (!bl3) {
            if (c == '\u0000') {
                if (stringBuilder.length() > 0) {
                    arrayList.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
            } else if (GMTOffsetField.isValid(c, n)) {
                arrayList.add(new GMTOffsetField(c, n));
            } else {
                bl3 = true;
            }
        }
        if (bl3 || bitSet.cardinality() != string2.length()) {
            throw new IllegalStateException("Bad localized GMT offset pattern: " + string);
        }
        return arrayList.toArray(new Object[arrayList.size()]);
    }

    private static String expandOffsetPattern(String string) {
        int n = string.indexOf("mm");
        if (n < 0) {
            throw new RuntimeException("Bad time zone hour pattern data");
        }
        String string2 = ":";
        int n2 = string.substring(0, n).lastIndexOf("H");
        if (n2 >= 0) {
            string2 = string.substring(n2 + 1, n);
        }
        return string.substring(0, n + 2) + string2 + "ss" + string.substring(n + 2);
    }

    private static String truncateOffsetPattern(String string) {
        int n = string.indexOf("mm");
        if (n < 0) {
            throw new RuntimeException("Bad time zone hour pattern data");
        }
        int n2 = string.substring(0, n).lastIndexOf("HH");
        if (n2 >= 0) {
            return string.substring(0, n2 + 2);
        }
        int n3 = string.substring(0, n).lastIndexOf("H");
        if (n3 >= 0) {
            return string.substring(0, n3 + 1);
        }
        throw new RuntimeException("Bad time zone hour pattern data");
    }

    private void appendOffsetDigits(StringBuilder stringBuilder, int n, int n2) {
        if (!($assertionsDisabled || n >= 0 && n < 60)) {
            throw new AssertionError();
        }
        int n3 = n >= 10 ? 2 : 1;
        for (int i = 0; i < n2 - n3; ++i) {
            stringBuilder.append(this._gmtOffsetDigits[0]);
        }
        if (n3 == 2) {
            stringBuilder.append(this._gmtOffsetDigits[n / 10]);
        }
        stringBuilder.append(this._gmtOffsetDigits[n % 10]);
    }

    private TimeZone getTimeZoneForOffset(int n) {
        if (n == 0) {
            return TimeZone.getTimeZone(TZID_GMT);
        }
        return ZoneMeta.getCustomTimeZone(n);
    }

    private int parseOffsetLocalizedGMT(String string, ParsePosition parsePosition, boolean bl, Output<Boolean> output) {
        int n = parsePosition.getIndex();
        int n2 = 0;
        int[] nArray = new int[]{0};
        if (output != null) {
            output.value = false;
        }
        n2 = this.parseOffsetLocalizedGMTPattern(string, n, bl, nArray);
        if (nArray[0] > 0) {
            if (output != null) {
                output.value = true;
            }
            parsePosition.setIndex(n + nArray[0]);
            return n2;
        }
        n2 = this.parseOffsetDefaultLocalizedGMT(string, n, nArray);
        if (nArray[0] > 0) {
            if (output != null) {
                output.value = true;
            }
            parsePosition.setIndex(n + nArray[0]);
            return n2;
        }
        if (string.regionMatches(true, n, this._gmtZeroFormat, 0, this._gmtZeroFormat.length())) {
            parsePosition.setIndex(n + this._gmtZeroFormat.length());
            return 1;
        }
        for (String string2 : ALT_GMT_STRINGS) {
            if (!string.regionMatches(true, n, string2, 0, string2.length())) continue;
            parsePosition.setIndex(n + string2.length());
            return 1;
        }
        parsePosition.setErrorIndex(n);
        return 1;
    }

    private int parseOffsetLocalizedGMTPattern(String string, int n, boolean bl, int[] nArray) {
        int n2 = n;
        int n3 = 0;
        boolean bl2 = false;
        int n4 = this._gmtPatternPrefix.length();
        if (n4 <= 0 || string.regionMatches(true, n2, this._gmtPatternPrefix, 0, n4)) {
            int[] nArray2 = new int[1];
            n3 = this.parseOffsetFields(string, n2 += n4, false, nArray2);
            if (nArray2[0] != 0 && ((n4 = this._gmtPatternSuffix.length()) <= 0 || string.regionMatches(true, n2 += nArray2[0], this._gmtPatternSuffix, 0, n4))) {
                n2 += n4;
                bl2 = true;
            }
        }
        nArray[0] = bl2 ? n2 - n : 0;
        return n3;
    }

    private int parseOffsetFields(String string, int n, boolean bl, int[] nArray) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 1;
        if (nArray != null && nArray.length >= 1) {
            nArray[0] = 0;
        }
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int[] nArray2 = new int[]{0, 0, 0};
        for (GMTOffsetPatternType gMTOffsetPatternType : PARSE_GMT_OFFSET_TYPES) {
            Object[] objectArray = this._gmtOffsetPatternItems[gMTOffsetPatternType.ordinal()];
            if (!$assertionsDisabled && objectArray == null) {
                throw new AssertionError();
            }
            n2 = this.parseOffsetFieldsWithPattern(string, n, objectArray, false, nArray2);
            if (n2 <= 0) continue;
            n4 = GMTOffsetPatternType.access$300(gMTOffsetPatternType) ? 1 : -1;
            n7 = nArray2[0];
            n6 = nArray2[1];
            n5 = nArray2[2];
            break;
        }
        if (n2 > 0 && this._abuttingOffsetHoursAndMinutes) {
            int n8 = 0;
            int n9 = 1;
            for (GMTOffsetPatternType gMTOffsetPatternType : PARSE_GMT_OFFSET_TYPES) {
                Object[] objectArray = this._gmtOffsetPatternItems[gMTOffsetPatternType.ordinal()];
                if (!$assertionsDisabled && objectArray == null) {
                    throw new AssertionError();
                }
                n8 = this.parseOffsetFieldsWithPattern(string, n, objectArray, true, nArray2);
                if (n8 <= 0) continue;
                n9 = GMTOffsetPatternType.access$300(gMTOffsetPatternType) ? 1 : -1;
                break;
            }
            if (n8 > n2) {
                n2 = n8;
                n4 = n9;
                n7 = nArray2[0];
                n6 = nArray2[1];
                n5 = nArray2[2];
            }
        }
        if (nArray != null && nArray.length >= 1) {
            nArray[0] = n2;
        }
        if (n2 > 0) {
            n3 = ((n7 * 60 + n6) * 60 + n5) * 1000 * n4;
        }
        return n3;
    }

    private int parseOffsetFieldsWithPattern(String string, int n, Object[] objectArray, boolean bl, int[] nArray) {
        if (!($assertionsDisabled || nArray != null && nArray.length >= 3)) {
            throw new AssertionError();
        }
        nArray[2] = 0;
        nArray[1] = 0;
        nArray[0] = 0;
        boolean bl2 = false;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = n;
        int[] nArray2 = new int[]{0};
        for (int i = 0; i < objectArray.length; ++i) {
            int n6;
            int n7;
            Object object;
            if (objectArray[i] instanceof String) {
                object = (String)objectArray[i];
                n7 = ((String)object).length();
                n6 = 0;
                if (i == 0 && n5 < string.length() && !PatternProps.isWhiteSpace(string.codePointAt(n5))) {
                    int n8;
                    while (n7 > 0 && PatternProps.isWhiteSpace(n8 = ((String)object).codePointAt(n6))) {
                        int n9 = Character.charCount(n8);
                        n7 -= n9;
                        n6 += n9;
                    }
                }
                if (!string.regionMatches(true, n5, (String)object, n6, n7)) {
                    bl2 = true;
                    break;
                }
                n5 += n7;
                continue;
            }
            if (!$assertionsDisabled && !(objectArray[i] instanceof GMTOffsetField)) {
                throw new AssertionError();
            }
            object = (GMTOffsetField)objectArray[i];
            n7 = ((GMTOffsetField)object).getType();
            if (n7 == 72) {
                n6 = bl ? 1 : 2;
                n4 = this.parseOffsetFieldWithLocalizedDigits(string, n5, 1, n6, 0, 23, nArray2);
            } else if (n7 == 109) {
                n3 = this.parseOffsetFieldWithLocalizedDigits(string, n5, 2, 2, 0, 59, nArray2);
            } else if (n7 == 115) {
                n2 = this.parseOffsetFieldWithLocalizedDigits(string, n5, 2, 2, 0, 59, nArray2);
            }
            if (nArray2[0] == 0) {
                bl2 = true;
                break;
            }
            n5 += nArray2[0];
        }
        if (bl2) {
            return 1;
        }
        nArray[0] = n4;
        nArray[1] = n3;
        nArray[2] = n2;
        return n5 - n;
    }

    private int parseOffsetDefaultLocalizedGMT(String string, int n, int[] nArray) {
        int n2;
        int n3;
        block8: {
            int n4;
            int n5;
            block10: {
                int n6;
                block9: {
                    n5 = n;
                    n3 = 0;
                    n2 = 0;
                    int n7 = 0;
                    for (String string2 : ALT_GMT_STRINGS) {
                        int n8 = string2.length();
                        if (!string.regionMatches(true, n5, string2, 0, n8)) continue;
                        n7 = n8;
                        break;
                    }
                    if (n7 == 0 || (n5 += n7) + 1 >= string.length()) break block8;
                    n4 = 1;
                    n6 = string.charAt(n5);
                    if (n6 != 43) break block9;
                    n4 = 1;
                    break block10;
                }
                if (n6 != 45) break block8;
                n4 = -1;
            }
            int[] nArray2 = new int[]{0};
            int n9 = this.parseDefaultOffsetFields(string, ++n5, ':', nArray2);
            if (nArray2[0] == string.length() - n5) {
                n3 = n9 * n4;
                n5 += nArray2[0];
            } else {
                int[] nArray3 = new int[]{0};
                int n10 = this.parseAbuttingOffsetFields(string, n5, nArray3);
                if (nArray2[0] > nArray3[0]) {
                    n3 = n9 * n4;
                    n5 += nArray2[0];
                } else {
                    n3 = n10 * n4;
                    n5 += nArray3[0];
                }
            }
            n2 = n5 - n;
        }
        nArray[0] = n2;
        return n3;
    }

    private int parseDefaultOffsetFields(String string, int n, char c, int[] nArray) {
        int n2 = string.length();
        int n3 = n;
        int[] nArray2 = new int[]{0};
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        n4 = this.parseOffsetFieldWithLocalizedDigits(string, n3, 1, 2, 0, 23, nArray2);
        if (nArray2[0] != 0 && (n3 += nArray2[0]) + 1 < n2 && string.charAt(n3) == c) {
            n5 = this.parseOffsetFieldWithLocalizedDigits(string, n3 + 1, 2, 2, 0, 59, nArray2);
            if (nArray2[0] != 0 && (n3 += 1 + nArray2[0]) + 1 < n2 && string.charAt(n3) == c) {
                n6 = this.parseOffsetFieldWithLocalizedDigits(string, n3 + 1, 2, 2, 0, 59, nArray2);
                if (nArray2[0] != 0) {
                    n3 += 1 + nArray2[0];
                }
            }
        }
        if (n3 == n) {
            nArray[0] = 0;
            return 1;
        }
        nArray[0] = n3 - n;
        return n4 * 3600000 + n5 * 60000 + n6 * 1000;
    }

    private int parseAbuttingOffsetFields(String string, int n, int[] nArray) {
        int n2;
        int n3 = 6;
        int[] nArray2 = new int[6];
        int[] nArray3 = new int[6];
        int n4 = n;
        int[] nArray4 = new int[]{0};
        int n5 = 0;
        for (n2 = 0; n2 < 6; ++n2) {
            nArray2[n2] = this.parseSingleLocalizedDigit(string, n4, nArray4);
            if (nArray2[n2] < 0) break;
            nArray3[n2] = (n4 += nArray4[0]) - n;
            ++n5;
        }
        if (n5 == 0) {
            nArray[0] = 0;
            return 1;
        }
        n2 = 0;
        while (n5 > 0) {
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            if (!($assertionsDisabled || n5 > 0 && n5 <= 6)) {
                throw new AssertionError();
            }
            switch (n5) {
                case 1: {
                    n6 = nArray2[0];
                    break;
                }
                case 2: {
                    n6 = nArray2[0] * 10 + nArray2[1];
                    break;
                }
                case 3: {
                    n6 = nArray2[0];
                    n7 = nArray2[1] * 10 + nArray2[2];
                    break;
                }
                case 4: {
                    n6 = nArray2[0] * 10 + nArray2[1];
                    n7 = nArray2[2] * 10 + nArray2[3];
                    break;
                }
                case 5: {
                    n6 = nArray2[0];
                    n7 = nArray2[1] * 10 + nArray2[2];
                    n8 = nArray2[3] * 10 + nArray2[4];
                    break;
                }
                case 6: {
                    n6 = nArray2[0] * 10 + nArray2[1];
                    n7 = nArray2[2] * 10 + nArray2[3];
                    n8 = nArray2[4] * 10 + nArray2[5];
                }
            }
            if (n6 <= 23 && n7 <= 59 && n8 <= 59) {
                n2 = n6 * 3600000 + n7 * 60000 + n8 * 1000;
                nArray[0] = nArray3[n5 - 1];
                break;
            }
            --n5;
        }
        return n2;
    }

    private int parseOffsetFieldWithLocalizedDigits(String string, int n, int n2, int n3, int n4, int n5, int[] nArray) {
        int n6;
        int n7;
        int n8;
        nArray[0] = 0;
        int n9 = 0;
        int n10 = 0;
        int[] nArray2 = new int[]{0};
        for (n8 = n; n8 < string.length() && n10 < n3 && (n7 = this.parseSingleLocalizedDigit(string, n8, nArray2)) >= 0 && (n6 = n9 * 10 + n7) <= n5; ++n10, n8 += nArray2[0]) {
            n9 = n6;
        }
        if (n10 < n2 || n9 < n4) {
            n9 = -1;
            n10 = 0;
        } else {
            nArray[0] = n8 - n;
        }
        return n9;
    }

    private int parseSingleLocalizedDigit(String string, int n, int[] nArray) {
        int n2 = -1;
        nArray[0] = 0;
        if (n < string.length()) {
            int n3 = Character.codePointAt(string, n);
            for (int i = 0; i < this._gmtOffsetDigits.length; ++i) {
                if (n3 != this._gmtOffsetDigits[i].codePointAt(0)) continue;
                n2 = i;
                break;
            }
            if (n2 < 0) {
                n2 = UCharacter.digit(n3);
            }
            if (n2 >= 0) {
                nArray[0] = Character.charCount(n3);
            }
        }
        return n2;
    }

    private static String[] toCodePoints(String string) {
        int n = string.codePointCount(0, string.length());
        String[] stringArray = new String[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = string.codePointAt(n2);
            int n4 = Character.charCount(n3);
            stringArray[i] = string.substring(n2, n2 + n4);
            n2 += n4;
        }
        return stringArray;
    }

    private static int parseOffsetISO8601(String string, ParsePosition parsePosition, boolean bl, Output<Boolean> output) {
        int n;
        int n2;
        if (output != null) {
            output.value = false;
        }
        if ((n2 = parsePosition.getIndex()) >= string.length()) {
            parsePosition.setErrorIndex(n2);
            return 1;
        }
        char c = string.charAt(n2);
        if (Character.toUpperCase(c) == ISO8601_UTC.charAt(0)) {
            parsePosition.setIndex(n2 + 1);
            return 1;
        }
        if (c == '+') {
            n = 1;
        } else if (c == '-') {
            n = -1;
        } else {
            parsePosition.setErrorIndex(n2);
            return 1;
        }
        ParsePosition parsePosition2 = new ParsePosition(n2 + 1);
        int n3 = TimeZoneFormat.parseAsciiOffsetFields(string, parsePosition2, ':', OffsetFields.H, OffsetFields.HMS);
        if (parsePosition2.getErrorIndex() == -1 && !bl && parsePosition2.getIndex() - n2 <= 3) {
            ParsePosition parsePosition3 = new ParsePosition(n2 + 1);
            int n4 = TimeZoneFormat.parseAbuttingAsciiOffsetFields(string, parsePosition3, OffsetFields.H, OffsetFields.HMS, false);
            if (parsePosition3.getErrorIndex() == -1 && parsePosition3.getIndex() > parsePosition2.getIndex()) {
                n3 = n4;
                parsePosition2.setIndex(parsePosition3.getIndex());
            }
        }
        if (parsePosition2.getErrorIndex() != -1) {
            parsePosition.setErrorIndex(n2);
            return 1;
        }
        parsePosition.setIndex(parsePosition2.getIndex());
        if (output != null) {
            output.value = true;
        }
        return n * n3;
    }

    private static int parseAbuttingAsciiOffsetFields(String string, ParsePosition parsePosition, OffsetFields offsetFields, OffsetFields offsetFields2, boolean bl) {
        int n;
        int n2 = parsePosition.getIndex();
        int n3 = 2 * (offsetFields.ordinal() + 1) - (bl ? 0 : 1);
        int n4 = 2 * (offsetFields2.ordinal() + 1);
        int[] nArray = new int[n4];
        int n5 = 0;
        for (int i = n2; n5 < nArray.length && i < string.length() && (n = ASCII_DIGITS.indexOf(string.charAt(i))) >= 0; ++n5, ++i) {
            nArray[n5] = n;
        }
        if (bl && (n5 & 1) != 0) {
            --n5;
        }
        if (n5 < n3) {
            parsePosition.setErrorIndex(n2);
            return 1;
        }
        n = 0;
        int n6 = 0;
        int n7 = 0;
        boolean bl2 = false;
        while (n5 >= n3) {
            switch (n5) {
                case 1: {
                    n = nArray[0];
                    break;
                }
                case 2: {
                    n = nArray[0] * 10 + nArray[1];
                    break;
                }
                case 3: {
                    n = nArray[0];
                    n6 = nArray[1] * 10 + nArray[2];
                    break;
                }
                case 4: {
                    n = nArray[0] * 10 + nArray[1];
                    n6 = nArray[2] * 10 + nArray[3];
                    break;
                }
                case 5: {
                    n = nArray[0];
                    n6 = nArray[1] * 10 + nArray[2];
                    n7 = nArray[3] * 10 + nArray[4];
                    break;
                }
                case 6: {
                    n = nArray[0] * 10 + nArray[1];
                    n6 = nArray[2] * 10 + nArray[3];
                    n7 = nArray[4] * 10 + nArray[5];
                }
            }
            if (n <= 23 && n6 <= 59 && n7 <= 59) {
                bl2 = true;
                break;
            }
            n5 -= bl ? 2 : 1;
            n7 = 0;
            n6 = 0;
            n = 0;
        }
        if (!bl2) {
            parsePosition.setErrorIndex(n2);
            return 1;
        }
        parsePosition.setIndex(n2 + n5);
        return ((n * 60 + n6) * 60 + n7) * 1000;
    }

    private static int parseAsciiOffsetFields(String string, ParsePosition parsePosition, char c, OffsetFields offsetFields, OffsetFields offsetFields2) {
        int n;
        int n2 = parsePosition.getIndex();
        int[] nArray = new int[]{0, 0, 0};
        int[] nArray2 = new int[]{0, -1, -1};
        int n3 = 0;
        for (n = n2; n < string.length() && n3 <= offsetFields2.ordinal(); ++n) {
            int n4;
            char c2 = string.charAt(n);
            if (c2 == c) {
                if (n3 == 0) {
                    if (nArray2[0] == 0) break;
                    ++n3;
                    continue;
                }
                if (nArray2[n3] != -1) break;
                nArray2[n3] = 0;
                continue;
            }
            if (nArray2[n3] == -1 || (n4 = ASCII_DIGITS.indexOf(c2)) < 0) break;
            nArray[n3] = nArray[n3] * 10 + n4;
            int n5 = n3;
            nArray2[n5] = nArray2[n5] + 1;
            if (nArray2[n3] < 2) continue;
            ++n3;
        }
        n = 0;
        n3 = 0;
        Enum enum_ = null;
        if (nArray2[0] != 0) {
            if (nArray[0] > 23) {
                n = nArray[0] / 10 * 3600000;
                enum_ = OffsetFields.H;
                n3 = 1;
            } else {
                n = nArray[0] * 3600000;
                n3 = nArray2[0];
                enum_ = OffsetFields.H;
                if (nArray2[1] == 2 && nArray[1] <= 59) {
                    n += nArray[1] * 60000;
                    n3 += 1 + nArray2[1];
                    enum_ = OffsetFields.HM;
                    if (nArray2[2] == 2 && nArray[2] <= 59) {
                        n += nArray[2] * 1000;
                        n3 += 1 + nArray2[2];
                        enum_ = OffsetFields.HMS;
                    }
                }
            }
        }
        if (enum_ == null || enum_.ordinal() < offsetFields.ordinal()) {
            parsePosition.setErrorIndex(n2);
            return 1;
        }
        parsePosition.setIndex(n2 + n3);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static String parseZoneID(String string, ParsePosition parsePosition) {
        Iterator<String> iterator2;
        Object object;
        String string2 = null;
        if (ZONE_ID_TRIE == null) {
            object = TimeZoneFormat.class;
            // MONITORENTER : com.ibm.icu.text.TimeZoneFormat.class
            if (ZONE_ID_TRIE == null) {
                String[] stringArray;
                iterator2 = new TextTrieMap(true);
                for (String string3 : stringArray = TimeZone.getAvailableIDs()) {
                    ((TextTrieMap)((Object)iterator2)).put(string3, string3);
                }
                ZONE_ID_TRIE = iterator2;
            }
            // MONITOREXIT : object
        }
        object = new TextTrieMap.Output();
        iterator2 = ZONE_ID_TRIE.get(string, parsePosition.getIndex(), (TextTrieMap.Output)object);
        if (iterator2 != null) {
            string2 = (String)iterator2.next();
            parsePosition.setIndex(parsePosition.getIndex() + ((TextTrieMap.Output)object).matchLength);
            return string2;
        }
        parsePosition.setErrorIndex(parsePosition.getIndex());
        return string2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static String parseShortZoneID(String string, ParsePosition parsePosition) {
        Iterator<String> iterator2;
        Object object;
        String string2 = null;
        if (SHORT_ZONE_ID_TRIE == null) {
            object = TimeZoneFormat.class;
            // MONITORENTER : com.ibm.icu.text.TimeZoneFormat.class
            if (SHORT_ZONE_ID_TRIE == null) {
                iterator2 = new TextTrieMap(true);
                Set<String> set = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null);
                for (String string3 : set) {
                    String string4 = ZoneMeta.getShortID(string3);
                    if (string4 == null) continue;
                    ((TextTrieMap)((Object)iterator2)).put(string4, string3);
                }
                ((TextTrieMap)((Object)iterator2)).put(UNKNOWN_SHORT_ZONE_ID, UNKNOWN_ZONE_ID);
                SHORT_ZONE_ID_TRIE = iterator2;
            }
            // MONITOREXIT : object
        }
        object = new TextTrieMap.Output();
        iterator2 = SHORT_ZONE_ID_TRIE.get(string, parsePosition.getIndex(), (TextTrieMap.Output)object);
        if (iterator2 != null) {
            string2 = (String)iterator2.next();
            parsePosition.setIndex(parsePosition.getIndex() + ((TextTrieMap.Output)object).matchLength);
            return string2;
        }
        parsePosition.setErrorIndex(parsePosition.getIndex());
        return string2;
    }

    private String parseExemplarLocation(String string, ParsePosition parsePosition) {
        int n = parsePosition.getIndex();
        int n2 = -1;
        String string2 = null;
        EnumSet<TimeZoneNames.NameType> enumSet = EnumSet.of(TimeZoneNames.NameType.EXEMPLAR_LOCATION);
        Collection<TimeZoneNames.MatchInfo> collection = this._tznames.find(string, n, enumSet);
        if (collection != null) {
            TimeZoneNames.MatchInfo matchInfo = null;
            for (TimeZoneNames.MatchInfo matchInfo2 : collection) {
                if (n + matchInfo2.matchLength() <= n2) continue;
                matchInfo = matchInfo2;
                n2 = n + matchInfo2.matchLength();
            }
            if (matchInfo != null) {
                string2 = this.getTimeZoneID(matchInfo.tzID(), matchInfo.mzID());
                parsePosition.setIndex(n2);
            }
        }
        if (string2 == null) {
            parsePosition.setErrorIndex(n);
        }
        return string2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectOutputStream.PutField putField = objectOutputStream.putFields();
        putField.put("_locale", this._locale);
        putField.put("_tznames", this._tznames);
        putField.put("_gmtPattern", this._gmtPattern);
        putField.put("_gmtOffsetPatterns", this._gmtOffsetPatterns);
        putField.put("_gmtOffsetDigits", this._gmtOffsetDigits);
        putField.put("_gmtZeroFormat", this._gmtZeroFormat);
        putField.put("_parseAllStyles", this._parseAllStyles);
        objectOutputStream.writeFields();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        ObjectInputStream.GetField getField = objectInputStream.readFields();
        this._locale = (ULocale)getField.get("_locale", null);
        if (this._locale == null) {
            throw new InvalidObjectException("Missing field: locale");
        }
        this._tznames = (TimeZoneNames)getField.get("_tznames", null);
        if (this._tznames == null) {
            throw new InvalidObjectException("Missing field: tznames");
        }
        this._gmtPattern = (String)getField.get("_gmtPattern", null);
        if (this._gmtPattern == null) {
            throw new InvalidObjectException("Missing field: gmtPattern");
        }
        String[] stringArray = (String[])getField.get("_gmtOffsetPatterns", null);
        if (stringArray == null) {
            throw new InvalidObjectException("Missing field: gmtOffsetPatterns");
        }
        if (stringArray.length < 4) {
            throw new InvalidObjectException("Incompatible field: gmtOffsetPatterns");
        }
        this._gmtOffsetPatterns = new String[6];
        if (stringArray.length == 4) {
            for (int i = 0; i < 4; ++i) {
                this._gmtOffsetPatterns[i] = stringArray[i];
            }
            this._gmtOffsetPatterns[GMTOffsetPatternType.POSITIVE_H.ordinal()] = TimeZoneFormat.truncateOffsetPattern(this._gmtOffsetPatterns[GMTOffsetPatternType.POSITIVE_HM.ordinal()]);
            this._gmtOffsetPatterns[GMTOffsetPatternType.NEGATIVE_H.ordinal()] = TimeZoneFormat.truncateOffsetPattern(this._gmtOffsetPatterns[GMTOffsetPatternType.NEGATIVE_HM.ordinal()]);
        } else {
            this._gmtOffsetPatterns = stringArray;
        }
        this._gmtOffsetDigits = (String[])getField.get("_gmtOffsetDigits", null);
        if (this._gmtOffsetDigits == null) {
            throw new InvalidObjectException("Missing field: gmtOffsetDigits");
        }
        if (this._gmtOffsetDigits.length != 10) {
            throw new InvalidObjectException("Incompatible field: gmtOffsetDigits");
        }
        this._gmtZeroFormat = (String)getField.get("_gmtZeroFormat", null);
        if (this._gmtZeroFormat == null) {
            throw new InvalidObjectException("Missing field: gmtZeroFormat");
        }
        this._parseAllStyles = getField.get("_parseAllStyles", true);
        if (getField.defaulted("_parseAllStyles")) {
            throw new InvalidObjectException("Missing field: parseAllStyles");
        }
        if (this._tznames instanceof TimeZoneNamesImpl) {
            this._tznames = TimeZoneNames.getInstance(this._locale);
            this._gnames = null;
        } else {
            this._gnames = new TimeZoneGenericNames(this._locale, this._tznames);
        }
        this.initGMTPattern(this._gmtPattern);
        this.initGMTOffsetPatterns(this._gmtOffsetPatterns);
    }

    @Override
    public boolean isFrozen() {
        return this._frozen;
    }

    @Override
    public TimeZoneFormat freeze() {
        this._frozen = true;
        return this;
    }

    @Override
    public TimeZoneFormat cloneAsThawed() {
        TimeZoneFormat timeZoneFormat = (TimeZoneFormat)super.clone();
        timeZoneFormat._frozen = false;
        return timeZoneFormat;
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    static {
        $assertionsDisabled = !TimeZoneFormat.class.desiredAssertionStatus();
        ALT_GMT_STRINGS = new String[]{DEFAULT_GMT_ZERO, "UTC", "UT"};
        DEFAULT_GMT_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        PARSE_GMT_OFFSET_TYPES = new GMTOffsetPatternType[]{GMTOffsetPatternType.POSITIVE_HMS, GMTOffsetPatternType.NEGATIVE_HMS, GMTOffsetPatternType.POSITIVE_HM, GMTOffsetPatternType.NEGATIVE_HM, GMTOffsetPatternType.POSITIVE_H, GMTOffsetPatternType.NEGATIVE_H};
        _tzfCache = new TimeZoneFormatCache(null);
        ALL_SIMPLE_NAME_TYPES = EnumSet.of(TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, TimeZoneNames.NameType.EXEMPLAR_LOCATION);
        ALL_GENERIC_NAME_TYPES = EnumSet.of(TimeZoneGenericNames.GenericNameType.LOCATION, TimeZoneGenericNames.GenericNameType.LONG, TimeZoneGenericNames.GenericNameType.SHORT);
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("_locale", ULocale.class), new ObjectStreamField("_tznames", TimeZoneNames.class), new ObjectStreamField("_gmtPattern", String.class), new ObjectStreamField("_gmtOffsetPatterns", String[].class), new ObjectStreamField("_gmtOffsetDigits", String[].class), new ObjectStreamField("_gmtZeroFormat", String.class), new ObjectStreamField("_parseAllStyles", Boolean.TYPE)};
    }

    private static class TimeZoneFormatCache
    extends SoftCache<ULocale, TimeZoneFormat, ULocale> {
        private TimeZoneFormatCache() {
        }

        @Override
        protected TimeZoneFormat createInstance(ULocale uLocale, ULocale uLocale2) {
            TimeZoneFormat timeZoneFormat = new TimeZoneFormat(uLocale2);
            timeZoneFormat.freeze();
            return timeZoneFormat;
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((ULocale)object, (ULocale)object2);
        }

        TimeZoneFormatCache(1 var1_1) {
            this();
        }
    }

    private static class GMTOffsetField {
        final char _type;
        final int _width;

        GMTOffsetField(char c, int n) {
            this._type = c;
            this._width = n;
        }

        char getType() {
            return this._type;
        }

        int getWidth() {
            return this._width;
        }

        static boolean isValid(char c, int n) {
            return n == 1 || n == 2;
        }
    }

    private static enum OffsetFields {
        H,
        HM,
        HMS;

    }

    public static enum ParseOption {
        ALL_STYLES,
        TZ_DATABASE_ABBREVIATIONS;

    }

    public static enum TimeType {
        UNKNOWN,
        STANDARD,
        DAYLIGHT;

    }

    public static enum GMTOffsetPatternType {
        POSITIVE_HM("+H:mm", "Hm", true),
        POSITIVE_HMS("+H:mm:ss", "Hms", true),
        NEGATIVE_HM("-H:mm", "Hm", false),
        NEGATIVE_HMS("-H:mm:ss", "Hms", false),
        POSITIVE_H("+H", "H", true),
        NEGATIVE_H("-H", "H", false);

        private String _defaultPattern;
        private String _required;
        private boolean _isPositive;

        private GMTOffsetPatternType(String string2, String string3, boolean bl) {
            this._defaultPattern = string2;
            this._required = string3;
            this._isPositive = bl;
        }

        private String defaultPattern() {
            return this._defaultPattern;
        }

        private String required() {
            return this._required;
        }

        private boolean isPositive() {
            return this._isPositive;
        }

        static String access$100(GMTOffsetPatternType gMTOffsetPatternType) {
            return gMTOffsetPatternType.defaultPattern();
        }

        static String access$200(GMTOffsetPatternType gMTOffsetPatternType) {
            return gMTOffsetPatternType.required();
        }

        static boolean access$300(GMTOffsetPatternType gMTOffsetPatternType) {
            return gMTOffsetPatternType.isPositive();
        }
    }

    public static enum Style {
        GENERIC_LOCATION(1),
        GENERIC_LONG(2),
        GENERIC_SHORT(4),
        SPECIFIC_LONG(8),
        SPECIFIC_SHORT(16),
        LOCALIZED_GMT(32),
        LOCALIZED_GMT_SHORT(64),
        ISO_BASIC_SHORT(128),
        ISO_BASIC_LOCAL_SHORT(256),
        ISO_BASIC_FIXED(128),
        ISO_BASIC_LOCAL_FIXED(256),
        ISO_BASIC_FULL(128),
        ISO_BASIC_LOCAL_FULL(256),
        ISO_EXTENDED_FIXED(128),
        ISO_EXTENDED_LOCAL_FIXED(256),
        ISO_EXTENDED_FULL(128),
        ISO_EXTENDED_LOCAL_FULL(256),
        ZONE_ID(512),
        ZONE_ID_SHORT(1024),
        EXEMPLAR_LOCATION(2048);

        final int flag;

        private Style(int n2) {
            this.flag = n2;
        }
    }
}

