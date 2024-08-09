/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternTokenizer;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.Region;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DateTimePatternGenerator
implements Freezable<DateTimePatternGenerator>,
Cloneable {
    private static final boolean DEBUG = false;
    private static final String[] LAST_RESORT_ALLOWED_HOUR_FORMAT = new String[]{"H"};
    static final Map<String, String[]> LOCALE_TO_ALLOWED_HOUR;
    public static final int ERA = 0;
    public static final int YEAR = 1;
    public static final int QUARTER = 2;
    public static final int MONTH = 3;
    public static final int WEEK_OF_YEAR = 4;
    public static final int WEEK_OF_MONTH = 5;
    public static final int WEEKDAY = 6;
    public static final int DAY = 7;
    public static final int DAY_OF_YEAR = 8;
    public static final int DAY_OF_WEEK_IN_MONTH = 9;
    public static final int DAYPERIOD = 10;
    public static final int HOUR = 11;
    public static final int MINUTE = 12;
    public static final int SECOND = 13;
    public static final int FRACTIONAL_SECOND = 14;
    public static final int ZONE = 15;
    @Deprecated
    public static final int TYPE_LIMIT = 16;
    private static final DisplayWidth APPENDITEM_WIDTH;
    private static final int APPENDITEM_WIDTH_INT;
    private static final DisplayWidth[] CLDR_FIELD_WIDTH;
    public static final int MATCH_NO_OPTIONS = 0;
    public static final int MATCH_HOUR_FIELD_LENGTH = 2048;
    @Deprecated
    public static final int MATCH_MINUTE_FIELD_LENGTH = 4096;
    @Deprecated
    public static final int MATCH_SECOND_FIELD_LENGTH = 8192;
    public static final int MATCH_ALL_FIELDS_LENGTH = 65535;
    private TreeMap<DateTimeMatcher, PatternWithSkeletonFlag> skeleton2pattern = new TreeMap();
    private TreeMap<String, PatternWithSkeletonFlag> basePattern_pattern = new TreeMap();
    private String decimal = "?";
    private String dateTimeFormat = "{1} {0}";
    private String[] appendItemFormats = new String[16];
    private String[][] fieldDisplayNames = new String[16][DisplayWidth.access$100()];
    private char defaultHourFormatChar = (char)72;
    private volatile boolean frozen = false;
    private transient DateTimeMatcher current = new DateTimeMatcher(null);
    private transient FormatParser fp = new FormatParser();
    private transient DistanceInfo _distanceInfo = new DistanceInfo(null);
    private String[] allowedHourFormats;
    private static final int FRACTIONAL_MASK = 16384;
    private static final int SECOND_AND_FRACTIONAL_MASK = 24576;
    private static ICUCache<String, DateTimePatternGenerator> DTPNG_CACHE;
    private static final String[] CLDR_FIELD_APPEND;
    private static final String[] CLDR_FIELD_NAME;
    private static final String[] FIELD_NAME;
    private static final String[] CANONICAL_ITEMS;
    private static final Set<String> CANONICAL_SET;
    private Set<String> cldrAvailableFormatKeys = new HashSet<String>(20);
    private static final int DATE_MASK = 1023;
    private static final int TIME_MASK = 64512;
    private static final int DELTA = 16;
    private static final int NUMERIC = 256;
    private static final int NONE = 0;
    private static final int NARROW = -257;
    private static final int SHORTER = -258;
    private static final int SHORT = -259;
    private static final int LONG = -260;
    private static final int EXTRA_FIELD = 65536;
    private static final int MISSING_FIELD = 4096;
    private static final int[][] types;

    public static DateTimePatternGenerator getEmptyInstance() {
        DateTimePatternGenerator dateTimePatternGenerator = new DateTimePatternGenerator();
        dateTimePatternGenerator.addCanonicalItems();
        dateTimePatternGenerator.fillInMissing();
        return dateTimePatternGenerator;
    }

    protected DateTimePatternGenerator() {
    }

    public static DateTimePatternGenerator getInstance() {
        return DateTimePatternGenerator.getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static DateTimePatternGenerator getInstance(ULocale uLocale) {
        return DateTimePatternGenerator.getFrozenInstance(uLocale).cloneAsThawed();
    }

    public static DateTimePatternGenerator getInstance(Locale locale) {
        return DateTimePatternGenerator.getInstance(ULocale.forLocale(locale));
    }

    @Deprecated
    public static DateTimePatternGenerator getFrozenInstance(ULocale uLocale) {
        String string = uLocale.toString();
        DateTimePatternGenerator dateTimePatternGenerator = DTPNG_CACHE.get(string);
        if (dateTimePatternGenerator != null) {
            return dateTimePatternGenerator;
        }
        dateTimePatternGenerator = new DateTimePatternGenerator();
        dateTimePatternGenerator.initData(uLocale);
        dateTimePatternGenerator.freeze();
        DTPNG_CACHE.put(string, dateTimePatternGenerator);
        return dateTimePatternGenerator;
    }

    private void initData(ULocale uLocale) {
        PatternInfo patternInfo = new PatternInfo();
        this.addCanonicalItems();
        this.addICUPatterns(patternInfo, uLocale);
        this.addCLDRData(patternInfo, uLocale);
        this.setDateTimeFromCalendar(uLocale);
        this.setDecimalSymbols(uLocale);
        this.getAllowedHourFormats(uLocale);
        this.fillInMissing();
    }

    private void addICUPatterns(PatternInfo patternInfo, ULocale uLocale) {
        for (int i = 0; i <= 3; ++i) {
            SimpleDateFormat simpleDateFormat = (SimpleDateFormat)DateFormat.getDateInstance(i, uLocale);
            this.addPattern(simpleDateFormat.toPattern(), false, patternInfo);
            simpleDateFormat = (SimpleDateFormat)DateFormat.getTimeInstance(i, uLocale);
            this.addPattern(simpleDateFormat.toPattern(), false, patternInfo);
            if (i != 3) continue;
            this.consumeShortTimePattern(simpleDateFormat.toPattern(), patternInfo);
        }
    }

    private String getCalendarTypeToUse(ULocale uLocale) {
        String string = uLocale.getKeywordValue("calendar");
        if (string == null) {
            String[] stringArray = Calendar.getKeywordValuesForLocale("calendar", uLocale, true);
            string = stringArray[0];
        }
        if (string == null) {
            string = "gregorian";
        }
        return string;
    }

    private void consumeShortTimePattern(String string, PatternInfo patternInfo) {
        this.hackTimes(patternInfo, string);
    }

    private void fillInMissing() {
        for (int i = 0; i < 16; ++i) {
            if (this.getAppendItemFormat(i) == null) {
                this.setAppendItemFormat(i, "{0} \u251c{2}: {1}\u2524");
            }
            if (this.getFieldDisplayName(i, DisplayWidth.WIDE) == null) {
                this.setFieldDisplayName(i, DisplayWidth.WIDE, "F" + i);
            }
            if (this.getFieldDisplayName(i, DisplayWidth.ABBREVIATED) == null) {
                this.setFieldDisplayName(i, DisplayWidth.ABBREVIATED, this.getFieldDisplayName(i, DisplayWidth.WIDE));
            }
            if (this.getFieldDisplayName(i, DisplayWidth.NARROW) != null) continue;
            this.setFieldDisplayName(i, DisplayWidth.NARROW, this.getFieldDisplayName(i, DisplayWidth.ABBREVIATED));
        }
    }

    private void addCLDRData(PatternInfo patternInfo, ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        String string = this.getCalendarTypeToUse(uLocale);
        AppendItemFormatsSink appendItemFormatsSink = new AppendItemFormatsSink(this, null);
        try {
            iCUResourceBundle.getAllItemsWithFallback("calendar/" + string + "/appendItems", appendItemFormatsSink);
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        AppendItemNamesSink appendItemNamesSink = new AppendItemNamesSink(this, null);
        try {
            iCUResourceBundle.getAllItemsWithFallback("fields", appendItemNamesSink);
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        AvailableFormatsSink availableFormatsSink = new AvailableFormatsSink(this, patternInfo);
        try {
            iCUResourceBundle.getAllItemsWithFallback("calendar/" + string + "/availableFormats", availableFormatsSink);
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
    }

    private void setDateTimeFromCalendar(ULocale uLocale) {
        String string = Calendar.getDateTimePattern(Calendar.getInstance(uLocale), uLocale, 2);
        this.setDateTimeFormat(string);
    }

    private void setDecimalSymbols(ULocale uLocale) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(uLocale);
        this.setDecimal(String.valueOf(decimalFormatSymbols.getDecimalSeparator()));
    }

    private String[] getAllowedHourFormatsLangCountry(String string, String string2) {
        String string3 = string + "_" + string2;
        String[] stringArray = LOCALE_TO_ALLOWED_HOUR.get(string3);
        if (stringArray == null) {
            stringArray = LOCALE_TO_ALLOWED_HOUR.get(string2);
        }
        return stringArray;
    }

    private void getAllowedHourFormats(ULocale uLocale) {
        String[] stringArray;
        String string = uLocale.getLanguage();
        String string2 = uLocale.getCountry();
        if (string.isEmpty() || string2.isEmpty()) {
            stringArray = ULocale.addLikelySubtags(uLocale);
            string = stringArray.getLanguage();
            string2 = stringArray.getCountry();
        }
        if (string.isEmpty()) {
            string = "und";
        }
        if (string2.isEmpty()) {
            string2 = "001";
        }
        if ((stringArray = this.getAllowedHourFormatsLangCountry(string, string2)) == null) {
            try {
                Region region = Region.getInstance(string2);
                string2 = region.toString();
                stringArray = this.getAllowedHourFormatsLangCountry(string, string2);
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        if (stringArray != null) {
            this.defaultHourFormatChar = stringArray[0].charAt(0);
            this.allowedHourFormats = Arrays.copyOfRange(stringArray, 1, stringArray.length - 1);
        } else {
            this.allowedHourFormats = LAST_RESORT_ALLOWED_HOUR_FORMAT;
            this.defaultHourFormatChar = this.allowedHourFormats[0].charAt(0);
        }
    }

    @Deprecated
    public char getDefaultHourFormatChar() {
        return this.defaultHourFormatChar;
    }

    @Deprecated
    public void setDefaultHourFormatChar(char c) {
        this.defaultHourFormatChar = c;
    }

    private void hackTimes(PatternInfo patternInfo, String string) {
        int n;
        Object object;
        this.fp.set(string);
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        for (int i = 0; i < FormatParser.access$1000(this.fp).size(); ++i) {
            object = FormatParser.access$1000(this.fp).get(i);
            if (object instanceof String) {
                if (!bl) continue;
                stringBuilder.append(this.fp.quoteLiteral(object.toString()));
                continue;
            }
            n = object.toString().charAt(0);
            if (n == 109) {
                bl = true;
                stringBuilder.append(object);
                continue;
            }
            if (n == 115) {
                if (!bl) break;
                stringBuilder.append(object);
                this.addPattern(stringBuilder.toString(), false, patternInfo);
                break;
            }
            if (bl || n == 122 || n == 90 || n == 118 || n == 86) break;
        }
        BitSet bitSet = new BitSet();
        object = new BitSet();
        for (n = 0; n < FormatParser.access$1000(this.fp).size(); ++n) {
            Object e = FormatParser.access$1000(this.fp).get(n);
            if (!(e instanceof VariableField)) continue;
            bitSet.set(n);
            char c = e.toString().charAt(0);
            if (c != 's' && c != 'S') continue;
            ((BitSet)object).set(n);
            for (int i = n - 1; i >= 0 && !bitSet.get(i); ++i) {
                ((BitSet)object).set(n);
            }
        }
        String string2 = DateTimePatternGenerator.getFilteredPattern(this.fp, (BitSet)object);
        this.addPattern(string2, false, patternInfo);
    }

    private static String getFilteredPattern(FormatParser formatParser, BitSet bitSet) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < FormatParser.access$1000(formatParser).size(); ++i) {
            if (bitSet.get(i)) continue;
            Object e = FormatParser.access$1000(formatParser).get(i);
            if (e instanceof String) {
                stringBuilder.append(formatParser.quoteLiteral(e.toString()));
                continue;
            }
            stringBuilder.append(e.toString());
        }
        return stringBuilder.toString();
    }

    @Deprecated
    public static int getAppendFormatNumber(UResource.Key key) {
        for (int i = 0; i < CLDR_FIELD_APPEND.length; ++i) {
            if (!key.contentEquals(CLDR_FIELD_APPEND[i])) continue;
            return i;
        }
        return 1;
    }

    @Deprecated
    public static int getAppendFormatNumber(String string) {
        for (int i = 0; i < CLDR_FIELD_APPEND.length; ++i) {
            if (!CLDR_FIELD_APPEND[i].equals(string)) continue;
            return i;
        }
        return 1;
    }

    private static int getCLDRFieldAndWidthNumber(UResource.Key key) {
        for (int i = 0; i < CLDR_FIELD_NAME.length; ++i) {
            for (int j = 0; j < DisplayWidth.access$100(); ++j) {
                String string = CLDR_FIELD_NAME[i].concat(DisplayWidth.access$1100(CLDR_FIELD_WIDTH[j]));
                if (!key.contentEquals(string)) continue;
                return i * DisplayWidth.access$100() + j;
            }
        }
        return 1;
    }

    public String getBestPattern(String string) {
        return this.getBestPattern(string, null, 0);
    }

    public String getBestPattern(String string, int n) {
        return this.getBestPattern(string, null, n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getBestPattern(String string, DateTimeMatcher dateTimeMatcher, int n) {
        String string2;
        String string3;
        EnumSet<DTPGflags> enumSet = EnumSet.noneOf(DTPGflags.class);
        String string4 = this.mapSkeletonMetacharacters(string, enumSet);
        DateTimePatternGenerator dateTimePatternGenerator = this;
        synchronized (dateTimePatternGenerator) {
            this.current.set(string4, this.fp, true);
            PatternWithMatcher patternWithMatcher = this.getBestRaw(this.current, -1, this._distanceInfo, dateTimeMatcher);
            if (this._distanceInfo.missingFieldMask == 0 && this._distanceInfo.extraFieldMask == 0) {
                return this.adjustFieldTypes(patternWithMatcher, this.current, enumSet, n);
            }
            int n2 = this.current.getFieldMask();
            string3 = this.getBestAppending(this.current, n2 & 0x3FF, this._distanceInfo, dateTimeMatcher, enumSet, n);
            string2 = this.getBestAppending(this.current, n2 & 0xFC00, this._distanceInfo, dateTimeMatcher, enumSet, n);
        }
        if (string3 == null) {
            return string2 == null ? "" : string2;
        }
        if (string2 == null) {
            return string3;
        }
        return SimpleFormatterImpl.formatRawPattern(this.getDateTimeFormat(), 2, 2, string2, string3);
    }

    private String mapSkeletonMetacharacters(String string, EnumSet<DTPGflags> enumSet) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\'') {
                bl = !bl;
                continue;
            }
            if (bl) continue;
            if (c == 'j' || c == 'C') {
                int n = 0;
                while (i + 1 < string.length() && string.charAt(i + 1) == c) {
                    ++n;
                    ++i;
                }
                int n2 = 1 + (n & 1);
                int n3 = n < 2 ? 1 : 3 + (n >> 1);
                char c2 = 'h';
                char c3 = 'a';
                if (c == 'j') {
                    c2 = this.defaultHourFormatChar;
                } else {
                    String string2 = this.allowedHourFormats[0];
                    c2 = string2.charAt(0);
                    char c4 = string2.charAt(string2.length() - 1);
                    if (c4 == 'b' || c4 == 'B') {
                        c3 = c4;
                    }
                }
                if (c2 == 'H' || c2 == 'k') {
                    n3 = 0;
                }
                while (n3-- > 0) {
                    stringBuilder.append(c3);
                }
                while (n2-- > 0) {
                    stringBuilder.append(c2);
                }
                continue;
            }
            if (c == 'J') {
                stringBuilder.append('H');
                enumSet.add(DTPGflags.SKELETON_USES_CAP_J);
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public DateTimePatternGenerator addPattern(String string, boolean bl, PatternInfo patternInfo) {
        return this.addPatternWithSkeleton(string, null, bl, patternInfo);
    }

    @Deprecated
    public DateTimePatternGenerator addPatternWithSkeleton(String string, String string2, boolean bl, PatternInfo patternInfo) {
        PatternWithSkeletonFlag patternWithSkeletonFlag;
        this.checkFrozen();
        DateTimeMatcher dateTimeMatcher = string2 == null ? new DateTimeMatcher(null).set(string, this.fp, true) : new DateTimeMatcher(null).set(string2, this.fp, true);
        String string3 = dateTimeMatcher.getBasePattern();
        PatternWithSkeletonFlag patternWithSkeletonFlag2 = this.basePattern_pattern.get(string3);
        if (patternWithSkeletonFlag2 != null && (!patternWithSkeletonFlag2.skeletonWasSpecified || string2 != null && !bl)) {
            patternInfo.status = 1;
            patternInfo.conflictingPattern = patternWithSkeletonFlag2.pattern;
            if (!bl) {
                return this;
            }
        }
        if ((patternWithSkeletonFlag = this.skeleton2pattern.get(dateTimeMatcher)) != null) {
            patternInfo.status = 2;
            patternInfo.conflictingPattern = patternWithSkeletonFlag.pattern;
            if (!bl || string2 != null && patternWithSkeletonFlag.skeletonWasSpecified) {
                return this;
            }
        }
        patternInfo.status = 0;
        patternInfo.conflictingPattern = "";
        PatternWithSkeletonFlag patternWithSkeletonFlag3 = new PatternWithSkeletonFlag(string, string2 != null);
        this.skeleton2pattern.put(dateTimeMatcher, patternWithSkeletonFlag3);
        this.basePattern_pattern.put(string3, patternWithSkeletonFlag3);
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getSkeleton(String string) {
        DateTimePatternGenerator dateTimePatternGenerator = this;
        synchronized (dateTimePatternGenerator) {
            this.current.set(string, this.fp, true);
            return this.current.toString();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public String getSkeletonAllowingDuplicates(String string) {
        DateTimePatternGenerator dateTimePatternGenerator = this;
        synchronized (dateTimePatternGenerator) {
            this.current.set(string, this.fp, false);
            return this.current.toString();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public String getCanonicalSkeletonAllowingDuplicates(String string) {
        DateTimePatternGenerator dateTimePatternGenerator = this;
        synchronized (dateTimePatternGenerator) {
            this.current.set(string, this.fp, false);
            return this.current.toCanonicalString();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getBaseSkeleton(String string) {
        DateTimePatternGenerator dateTimePatternGenerator = this;
        synchronized (dateTimePatternGenerator) {
            this.current.set(string, this.fp, true);
            return this.current.getBasePattern();
        }
    }

    public Map<String, String> getSkeletons(Map<String, String> map) {
        if (map == null) {
            map = new LinkedHashMap<String, String>();
        }
        for (DateTimeMatcher dateTimeMatcher : this.skeleton2pattern.keySet()) {
            PatternWithSkeletonFlag patternWithSkeletonFlag = this.skeleton2pattern.get(dateTimeMatcher);
            String string = patternWithSkeletonFlag.pattern;
            if (CANONICAL_SET.contains(string)) continue;
            map.put(dateTimeMatcher.toString(), string);
        }
        return map;
    }

    public Set<String> getBaseSkeletons(Set<String> set) {
        if (set == null) {
            set = new HashSet<String>();
        }
        set.addAll(this.basePattern_pattern.keySet());
        return set;
    }

    public String replaceFieldTypes(String string, String string2) {
        return this.replaceFieldTypes(string, string2, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String replaceFieldTypes(String string, String string2, int n) {
        DateTimePatternGenerator dateTimePatternGenerator = this;
        synchronized (dateTimePatternGenerator) {
            PatternWithMatcher patternWithMatcher = new PatternWithMatcher(string, null);
            return this.adjustFieldTypes(patternWithMatcher, this.current.set(string2, this.fp, true), EnumSet.noneOf(DTPGflags.class), n);
        }
    }

    public void setDateTimeFormat(String string) {
        this.checkFrozen();
        this.dateTimeFormat = string;
    }

    public String getDateTimeFormat() {
        return this.dateTimeFormat;
    }

    public void setDecimal(String string) {
        this.checkFrozen();
        this.decimal = string;
    }

    public String getDecimal() {
        return this.decimal;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public Collection<String> getRedundants(Collection<String> collection) {
        DateTimePatternGenerator dateTimePatternGenerator = this;
        synchronized (dateTimePatternGenerator) {
            if (collection == null) {
                collection = new LinkedHashSet<String>();
            }
            for (DateTimeMatcher dateTimeMatcher : this.skeleton2pattern.keySet()) {
                String string;
                PatternWithSkeletonFlag patternWithSkeletonFlag = this.skeleton2pattern.get(dateTimeMatcher);
                String string2 = patternWithSkeletonFlag.pattern;
                if (CANONICAL_SET.contains(string2) || !(string = this.getBestPattern(dateTimeMatcher.toString(), dateTimeMatcher, 0)).equals(string2)) continue;
                collection.add(string2);
            }
            return collection;
        }
    }

    public void setAppendItemFormat(int n, String string) {
        this.checkFrozen();
        this.appendItemFormats[n] = string;
    }

    public String getAppendItemFormat(int n) {
        return this.appendItemFormats[n];
    }

    public void setAppendItemName(int n, String string) {
        this.setFieldDisplayName(n, APPENDITEM_WIDTH, string);
    }

    public String getAppendItemName(int n) {
        return this.getFieldDisplayName(n, APPENDITEM_WIDTH);
    }

    @Deprecated
    private void setFieldDisplayName(int n, DisplayWidth displayWidth, String string) {
        this.checkFrozen();
        if (n < 16 && n >= 0) {
            this.fieldDisplayNames[n][displayWidth.ordinal()] = string;
        }
    }

    public String getFieldDisplayName(int n, DisplayWidth displayWidth) {
        if (n >= 16 || n < 0) {
            return "";
        }
        return this.fieldDisplayNames[n][displayWidth.ordinal()];
    }

    @Deprecated
    public static boolean isSingleField(String string) {
        char c = string.charAt(0);
        for (int i = 1; i < string.length(); ++i) {
            if (string.charAt(i) == c) continue;
            return true;
        }
        return false;
    }

    private void setAvailableFormat(String string) {
        this.checkFrozen();
        this.cldrAvailableFormatKeys.add(string);
    }

    private boolean isAvailableFormatSet(String string) {
        return this.cldrAvailableFormatKeys.contains(string);
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override
    public DateTimePatternGenerator freeze() {
        this.frozen = true;
        return this;
    }

    @Override
    public DateTimePatternGenerator cloneAsThawed() {
        DateTimePatternGenerator dateTimePatternGenerator = (DateTimePatternGenerator)this.clone();
        this.frozen = false;
        return dateTimePatternGenerator;
    }

    public Object clone() {
        try {
            DateTimePatternGenerator dateTimePatternGenerator = (DateTimePatternGenerator)super.clone();
            dateTimePatternGenerator.skeleton2pattern = (TreeMap)this.skeleton2pattern.clone();
            dateTimePatternGenerator.basePattern_pattern = (TreeMap)this.basePattern_pattern.clone();
            dateTimePatternGenerator.appendItemFormats = (String[])this.appendItemFormats.clone();
            dateTimePatternGenerator.fieldDisplayNames = (String[][])this.fieldDisplayNames.clone();
            dateTimePatternGenerator.current = new DateTimeMatcher(null);
            dateTimePatternGenerator.fp = new FormatParser();
            dateTimePatternGenerator._distanceInfo = new DistanceInfo(null);
            dateTimePatternGenerator.frozen = false;
            return dateTimePatternGenerator;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException("Internal Error", cloneNotSupportedException);
        }
    }

    @Deprecated
    public boolean skeletonsAreSimilar(String string, String string2) {
        if (string.equals(string2)) {
            return false;
        }
        TreeSet<String> treeSet = this.getSet(string);
        TreeSet<String> treeSet2 = this.getSet(string2);
        if (treeSet.size() != treeSet2.size()) {
            return true;
        }
        Iterator<String> iterator2 = treeSet2.iterator();
        for (String string3 : treeSet) {
            String string4;
            int n;
            int n2 = DateTimePatternGenerator.getCanonicalIndex(string3, false);
            if (types[n2][1] == types[n = DateTimePatternGenerator.getCanonicalIndex(string4 = iterator2.next(), false)][1]) continue;
            return true;
        }
        return false;
    }

    private TreeSet<String> getSet(String string) {
        List<Object> list = this.fp.set(string).getItems();
        TreeSet<String> treeSet = new TreeSet<String>();
        for (Object object : list) {
            String string2 = object.toString();
            if (string2.startsWith("G") || string2.startsWith("a")) continue;
            treeSet.add(string2);
        }
        return treeSet;
    }

    private void checkFrozen() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
    }

    private String getBestAppending(DateTimeMatcher dateTimeMatcher, int n, DistanceInfo distanceInfo, DateTimeMatcher dateTimeMatcher2, EnumSet<DTPGflags> enumSet, int n2) {
        String string = null;
        if (n != 0) {
            PatternWithMatcher patternWithMatcher = this.getBestRaw(dateTimeMatcher, n, distanceInfo, dateTimeMatcher2);
            string = this.adjustFieldTypes(patternWithMatcher, dateTimeMatcher, enumSet, n2);
            while (distanceInfo.missingFieldMask != 0) {
                if ((distanceInfo.missingFieldMask & 0x6000) == 16384 && (n & 0x6000) == 24576) {
                    patternWithMatcher.pattern = string;
                    enumSet = EnumSet.copyOf(enumSet);
                    enumSet.add(DTPGflags.FIX_FRACTIONAL_SECONDS);
                    string = this.adjustFieldTypes(patternWithMatcher, dateTimeMatcher, enumSet, n2);
                    distanceInfo.missingFieldMask &= 0xFFFFBFFF;
                    continue;
                }
                int n3 = distanceInfo.missingFieldMask;
                PatternWithMatcher patternWithMatcher2 = this.getBestRaw(dateTimeMatcher, distanceInfo.missingFieldMask, distanceInfo, dateTimeMatcher2);
                String string2 = this.adjustFieldTypes(patternWithMatcher2, dateTimeMatcher, enumSet, n2);
                int n4 = n3 & ~distanceInfo.missingFieldMask;
                int n5 = this.getTopBitNumber(n4);
                string = SimpleFormatterImpl.formatRawPattern(this.getAppendFormat(n5), 2, 3, string, string2, this.getAppendName(n5));
            }
        }
        return string;
    }

    private String getAppendName(int n) {
        return "'" + this.fieldDisplayNames[n][APPENDITEM_WIDTH_INT] + "'";
    }

    private String getAppendFormat(int n) {
        return this.appendItemFormats[n];
    }

    private int getTopBitNumber(int n) {
        int n2 = 0;
        while (n != 0) {
            n >>>= 1;
            ++n2;
        }
        return n2 - 1;
    }

    private void addCanonicalItems() {
        PatternInfo patternInfo = new PatternInfo();
        for (int i = 0; i < CANONICAL_ITEMS.length; ++i) {
            this.addPattern(String.valueOf(CANONICAL_ITEMS[i]), false, patternInfo);
        }
    }

    private PatternWithMatcher getBestRaw(DateTimeMatcher dateTimeMatcher, int n, DistanceInfo distanceInfo, DateTimeMatcher dateTimeMatcher2) {
        int n2 = Integer.MAX_VALUE;
        PatternWithMatcher patternWithMatcher = new PatternWithMatcher("", null);
        DistanceInfo distanceInfo2 = new DistanceInfo(null);
        for (DateTimeMatcher dateTimeMatcher3 : this.skeleton2pattern.keySet()) {
            int n3;
            if (dateTimeMatcher3.equals(dateTimeMatcher2) || (n3 = dateTimeMatcher.getDistance(dateTimeMatcher3, n, distanceInfo2)) >= n2) continue;
            n2 = n3;
            PatternWithSkeletonFlag patternWithSkeletonFlag = this.skeleton2pattern.get(dateTimeMatcher3);
            patternWithMatcher.pattern = patternWithSkeletonFlag.pattern;
            patternWithMatcher.matcherWithSkeleton = patternWithSkeletonFlag.skeletonWasSpecified ? dateTimeMatcher3 : null;
            distanceInfo.setTo(distanceInfo2);
            if (n3 != 0) continue;
            break;
        }
        return patternWithMatcher;
    }

    private String adjustFieldTypes(PatternWithMatcher patternWithMatcher, DateTimeMatcher dateTimeMatcher, EnumSet<DTPGflags> enumSet, int n) {
        this.fp.set(patternWithMatcher.pattern);
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : this.fp.getItems()) {
            if (object instanceof String) {
                stringBuilder.append(this.fp.quoteLiteral((String)object));
                continue;
            }
            VariableField variableField = (VariableField)object;
            StringBuilder stringBuilder2 = new StringBuilder(variableField.toString());
            int n2 = variableField.getType();
            if (enumSet.contains((Object)DTPGflags.FIX_FRACTIONAL_SECONDS) && n2 == 13) {
                stringBuilder2.append(this.decimal);
                DateTimeMatcher.access$1700(dateTimeMatcher).appendFieldTo(14, stringBuilder2);
            } else if (DateTimeMatcher.access$1800(dateTimeMatcher)[n2] != 0) {
                int n3;
                char c;
                char c2 = DateTimeMatcher.access$1700(dateTimeMatcher).getFieldChar(n2);
                int n4 = DateTimeMatcher.access$1700(dateTimeMatcher).getFieldLength(n2);
                if (c2 == 'E' && n4 < 3) {
                    n4 = 3;
                }
                int n5 = n4;
                DateTimeMatcher dateTimeMatcher2 = patternWithMatcher.matcherWithSkeleton;
                if (n2 == 11 && (n & 0x800) == 0 || n2 == 12 && (n & 0x1000) == 0 || n2 == 13 && (n & 0x2000) == 0) {
                    n5 = stringBuilder2.length();
                } else if (dateTimeMatcher2 != null) {
                    c = DateTimeMatcher.access$1700(dateTimeMatcher2).getFieldLength(n2);
                    n3 = variableField.isNumeric();
                    boolean bl = dateTimeMatcher2.fieldIsNumeric(n2);
                    if (c == n4 || n3 != 0 && !bl || bl && n3 == 0) {
                        n5 = stringBuilder2.length();
                    }
                }
                c = n2 != 11 && n2 != 3 && n2 != 6 && (n2 != 1 || c2 == 'Y') ? c2 : stringBuilder2.charAt(0);
                if (n2 == 11 && enumSet.contains((Object)DTPGflags.SKELETON_USES_CAP_J)) {
                    c = this.defaultHourFormatChar;
                }
                stringBuilder2 = new StringBuilder();
                for (n3 = n5; n3 > 0; --n3) {
                    stringBuilder2.append(c);
                }
            }
            stringBuilder.append((CharSequence)stringBuilder2);
        }
        return stringBuilder.toString();
    }

    @Deprecated
    public String getFields(String string) {
        this.fp.set(string);
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : this.fp.getItems()) {
            if (object instanceof String) {
                stringBuilder.append(this.fp.quoteLiteral((String)object));
                continue;
            }
            stringBuilder.append("{" + DateTimePatternGenerator.getName(object.toString()) + "}");
        }
        return stringBuilder.toString();
    }

    private static String showMask(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; ++i) {
            if ((n & 1 << i) == 0) continue;
            if (stringBuilder.length() != 0) {
                stringBuilder.append(" | ");
            }
            stringBuilder.append(FIELD_NAME[i]);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private static String getName(String string) {
        int n = DateTimePatternGenerator.getCanonicalIndex(string, true);
        String string2 = FIELD_NAME[types[n][1]];
        string2 = types[n][2] < 0 ? string2 + ":S" : string2 + ":N";
        return string2;
    }

    private static int getCanonicalIndex(String string, boolean bl) {
        int n;
        int n2 = string.length();
        if (n2 == 0) {
            return 1;
        }
        char c = string.charAt(0);
        for (n = 1; n < n2; ++n) {
            if (string.charAt(n) == c) continue;
            return 1;
        }
        n = -1;
        for (int i = 0; i < types.length; ++i) {
            int[] nArray = types[i];
            if (nArray[0] != c) continue;
            n = i;
            if (nArray[3] > n2 || nArray[nArray.length - 1] < n2) continue;
            return i;
        }
        return bl ? -1 : n;
    }

    private static char getCanonicalChar(int n, char c) {
        if (c == 'h' || c == 'K') {
            return '\u0001';
        }
        for (int i = 0; i < types.length; ++i) {
            int[] nArray = types[i];
            if (nArray[1] != n) continue;
            return (char)nArray[0];
        }
        throw new IllegalArgumentException("Could not find field " + n);
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    static int access$000(UResource.Key key) {
        return DateTimePatternGenerator.getCLDRFieldAndWidthNumber(key);
    }

    static DisplayWidth[] access$200() {
        return CLDR_FIELD_WIDTH;
    }

    static void access$300(DateTimePatternGenerator dateTimePatternGenerator, int n, DisplayWidth displayWidth, String string) {
        dateTimePatternGenerator.setFieldDisplayName(n, displayWidth, string);
    }

    static boolean access$400(DateTimePatternGenerator dateTimePatternGenerator, String string) {
        return dateTimePatternGenerator.isAvailableFormatSet(string);
    }

    static void access$500(DateTimePatternGenerator dateTimePatternGenerator, String string) {
        dateTimePatternGenerator.setAvailableFormat(string);
    }

    static String[] access$800() {
        return LAST_RESORT_ALLOWED_HOUR_FORMAT;
    }

    static int access$1400(String string, boolean bl) {
        return DateTimePatternGenerator.getCanonicalIndex(string, bl);
    }

    static int[][] access$1500() {
        return types;
    }

    static String[] access$1600() {
        return CANONICAL_ITEMS;
    }

    static char access$1900(int n, char c) {
        return DateTimePatternGenerator.getCanonicalChar(n, c);
    }

    static String access$2200(int n) {
        return DateTimePatternGenerator.showMask(n);
    }

    static {
        HashMap hashMap = new HashMap();
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        DayPeriodAllowedHoursSink dayPeriodAllowedHoursSink = new DayPeriodAllowedHoursSink(hashMap, null);
        iCUResourceBundle.getAllItemsWithFallback("timeData", dayPeriodAllowedHoursSink);
        LOCALE_TO_ALLOWED_HOUR = Collections.unmodifiableMap(hashMap);
        APPENDITEM_WIDTH = DisplayWidth.WIDE;
        APPENDITEM_WIDTH_INT = APPENDITEM_WIDTH.ordinal();
        CLDR_FIELD_WIDTH = DisplayWidth.values();
        DTPNG_CACHE = new SimpleCache<String, DateTimePatternGenerator>();
        CLDR_FIELD_APPEND = new String[]{"Era", "Year", "Quarter", "Month", "Week", "*", "Day-Of-Week", "Day", "*", "*", "*", "Hour", "Minute", "Second", "*", "Timezone"};
        CLDR_FIELD_NAME = new String[]{"era", "year", "quarter", "month", "week", "weekOfMonth", "weekday", "day", "dayOfYear", "weekdayOfMonth", "dayperiod", "hour", "minute", "second", "*", "zone"};
        FIELD_NAME = new String[]{"Era", "Year", "Quarter", "Month", "Week_in_Year", "Week_in_Month", "Weekday", "Day", "Day_Of_Year", "Day_of_Week_in_Month", "Dayperiod", "Hour", "Minute", "Second", "Fractional_Second", "Zone"};
        CANONICAL_ITEMS = new String[]{"G", "y", "Q", "M", "w", "W", "E", "d", "D", "F", "a", "H", "m", "s", "S", "v"};
        CANONICAL_SET = new HashSet<String>(Arrays.asList(CANONICAL_ITEMS));
        types = new int[][]{{71, 0, -259, 1, 3}, {71, 0, -260, 4}, {71, 0, -257, 5}, {121, 1, 256, 1, 20}, {89, 1, 272, 1, 20}, {117, 1, 288, 1, 20}, {114, 1, 304, 1, 20}, {85, 1, -259, 1, 3}, {85, 1, -260, 4}, {85, 1, -257, 5}, {81, 2, 256, 1, 2}, {81, 2, -259, 3}, {81, 2, -260, 4}, {81, 2, -257, 5}, {113, 2, 272, 1, 2}, {113, 2, -275, 3}, {113, 2, -276, 4}, {113, 2, -273, 5}, {77, 3, 256, 1, 2}, {77, 3, -259, 3}, {77, 3, -260, 4}, {77, 3, -257, 5}, {76, 3, 272, 1, 2}, {76, 3, -275, 3}, {76, 3, -276, 4}, {76, 3, -273, 5}, {108, 3, 272, 1, 1}, {119, 4, 256, 1, 2}, {87, 5, 256, 1}, {69, 6, -259, 1, 3}, {69, 6, -260, 4}, {69, 6, -257, 5}, {69, 6, -258, 6}, {99, 6, 288, 1, 2}, {99, 6, -291, 3}, {99, 6, -292, 4}, {99, 6, -289, 5}, {99, 6, -290, 6}, {101, 6, 272, 1, 2}, {101, 6, -275, 3}, {101, 6, -276, 4}, {101, 6, -273, 5}, {101, 6, -274, 6}, {100, 7, 256, 1, 2}, {103, 7, 272, 1, 20}, {68, 8, 256, 1, 3}, {70, 9, 256, 1}, {97, 10, -259, 1, 3}, {97, 10, -260, 4}, {97, 10, -257, 5}, {98, 10, -275, 1, 3}, {98, 10, -276, 4}, {98, 10, -273, 5}, {66, 10, -307, 1, 3}, {66, 10, -308, 4}, {66, 10, -305, 5}, {72, 11, 416, 1, 2}, {107, 11, 432, 1, 2}, {104, 11, 256, 1, 2}, {75, 11, 272, 1, 2}, {109, 12, 256, 1, 2}, {115, 13, 256, 1, 2}, {65, 13, 272, 1, 1000}, {83, 14, 256, 1, 1000}, {118, 15, -291, 1}, {118, 15, -292, 4}, {122, 15, -259, 1, 3}, {122, 15, -260, 4}, {90, 15, -273, 1, 3}, {90, 15, -276, 4}, {90, 15, -275, 5}, {79, 15, -275, 1}, {79, 15, -276, 4}, {86, 15, -275, 1}, {86, 15, -276, 2}, {86, 15, -277, 3}, {86, 15, -278, 4}, {88, 15, -273, 1}, {88, 15, -275, 2}, {88, 15, -276, 4}, {120, 15, -273, 1}, {120, 15, -275, 2}, {120, 15, -276, 4}};
    }

    private static class DistanceInfo {
        int missingFieldMask;
        int extraFieldMask;

        private DistanceInfo() {
        }

        void clear() {
            this.extraFieldMask = 0;
            this.missingFieldMask = 0;
        }

        void setTo(DistanceInfo distanceInfo) {
            this.missingFieldMask = distanceInfo.missingFieldMask;
            this.extraFieldMask = distanceInfo.extraFieldMask;
        }

        void addMissing(int n) {
            this.missingFieldMask |= 1 << n;
        }

        void addExtra(int n) {
            this.extraFieldMask |= 1 << n;
        }

        public String toString() {
            return "missingFieldMask: " + DateTimePatternGenerator.access$2200(this.missingFieldMask) + ", extraFieldMask: " + DateTimePatternGenerator.access$2200(this.extraFieldMask);
        }

        DistanceInfo(1 var1_1) {
            this();
        }
    }

    private static class DateTimeMatcher
    implements Comparable<DateTimeMatcher> {
        private int[] type = new int[16];
        private SkeletonFields original = new SkeletonFields(null);
        private SkeletonFields baseOriginal = new SkeletonFields(null);
        private boolean addedDefaultDayPeriod = false;

        private DateTimeMatcher() {
        }

        public boolean fieldIsNumeric(int n) {
            return this.type[n] > 0;
        }

        public String toString() {
            return this.original.toString(this.addedDefaultDayPeriod);
        }

        public String toCanonicalString() {
            return this.original.toCanonicalString(this.addedDefaultDayPeriod);
        }

        String getBasePattern() {
            return this.baseOriginal.toString(this.addedDefaultDayPeriod);
        }

        DateTimeMatcher set(String string, FormatParser formatParser, boolean bl) {
            Arrays.fill(this.type, 0);
            this.original.clear();
            this.baseOriginal.clear();
            this.addedDefaultDayPeriod = false;
            formatParser.set(string);
            for (Object object : formatParser.getItems()) {
                int n;
                char c;
                if (!(object instanceof VariableField)) continue;
                VariableField variableField = (VariableField)object;
                String string2 = variableField.toString();
                int n2 = VariableField.access$2100(variableField);
                int[] nArray = DateTimePatternGenerator.access$1500()[n2];
                int n3 = nArray[1];
                if (!this.original.isFieldEmpty(n3)) {
                    c = this.original.getFieldChar(n3);
                    n = string2.charAt(0);
                    if (bl || c == 'r' && n == 85 || c == 'U' && n == 114) continue;
                    throw new IllegalArgumentException("Conflicting fields:\t" + c + ", " + string2 + "\t in " + string);
                }
                this.original.populate(n3, string2);
                c = (char)nArray[0];
                n = nArray[3];
                if ("GEzvQ".indexOf(c) >= 0) {
                    n = 1;
                }
                this.baseOriginal.populate(n3, c, n);
                int n4 = nArray[2];
                if (n4 > 0) {
                    n4 += string2.length();
                }
                this.type[n3] = n4;
            }
            if (!this.original.isFieldEmpty(0)) {
                if (this.original.getFieldChar(11) == 'h' || this.original.getFieldChar(11) == 'K') {
                    if (this.original.isFieldEmpty(1)) {
                        for (int i = 0; i < DateTimePatternGenerator.access$1500().length; ++i) {
                            Object object;
                            object = DateTimePatternGenerator.access$1500()[i];
                            if (object[1] != 10) continue;
                            this.original.populate(10, (char)object[0], (int)object[3]);
                            this.baseOriginal.populate(10, (char)object[0], (int)object[3]);
                            this.type[10] = (int)object[2];
                            this.addedDefaultDayPeriod = true;
                            break;
                        }
                    }
                } else if (!this.original.isFieldEmpty(1)) {
                    this.original.clearField(10);
                    this.baseOriginal.clearField(10);
                    this.type[10] = 0;
                }
            }
            return this;
        }

        int getFieldMask() {
            int n = 0;
            for (int i = 0; i < this.type.length; ++i) {
                if (this.type[i] == 0) continue;
                n |= 1 << i;
            }
            return n;
        }

        void extractFrom(DateTimeMatcher dateTimeMatcher, int n) {
            for (int i = 0; i < this.type.length; ++i) {
                if ((n & 1 << i) != 0) {
                    this.type[i] = dateTimeMatcher.type[i];
                    this.original.copyFieldFrom(dateTimeMatcher.original, i);
                    continue;
                }
                this.type[i] = 0;
                this.original.clearField(i);
            }
        }

        int getDistance(DateTimeMatcher dateTimeMatcher, int n, DistanceInfo distanceInfo) {
            int n2 = 0;
            distanceInfo.clear();
            for (int i = 0; i < 16; ++i) {
                int n3;
                int n4 = (n & 1 << i) == 0 ? 0 : this.type[i];
                if (n4 == (n3 = dateTimeMatcher.type[i])) continue;
                if (n4 == 0) {
                    n2 += 65536;
                    distanceInfo.addExtra(i);
                    continue;
                }
                if (n3 == 0) {
                    n2 += 4096;
                    distanceInfo.addMissing(i);
                    continue;
                }
                n2 += Math.abs(n4 - n3);
            }
            return n2;
        }

        @Override
        public int compareTo(DateTimeMatcher dateTimeMatcher) {
            int n = this.original.compareTo(dateTimeMatcher.original);
            return n > 0 ? -1 : (n < 0 ? 1 : 0);
        }

        public boolean equals(Object object) {
            return this == object || object != null && object instanceof DateTimeMatcher && this.original.equals(((DateTimeMatcher)object).original);
        }

        public int hashCode() {
            return this.original.hashCode();
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((DateTimeMatcher)object);
        }

        DateTimeMatcher(1 var1_1) {
            this();
        }

        static SkeletonFields access$1700(DateTimeMatcher dateTimeMatcher) {
            return dateTimeMatcher.original;
        }

        static int[] access$1800(DateTimeMatcher dateTimeMatcher) {
            return dateTimeMatcher.type;
        }
    }

    private static class SkeletonFields {
        private byte[] chars = new byte[16];
        private byte[] lengths = new byte[16];
        private static final byte DEFAULT_CHAR = 0;
        private static final byte DEFAULT_LENGTH = 0;
        static final boolean $assertionsDisabled = !DateTimePatternGenerator.class.desiredAssertionStatus();

        private SkeletonFields() {
        }

        public void clear() {
            Arrays.fill(this.chars, (byte)0);
            Arrays.fill(this.lengths, (byte)0);
        }

        void copyFieldFrom(SkeletonFields skeletonFields, int n) {
            this.chars[n] = skeletonFields.chars[n];
            this.lengths[n] = skeletonFields.lengths[n];
        }

        void clearField(int n) {
            this.chars[n] = 0;
            this.lengths[n] = 0;
        }

        char getFieldChar(int n) {
            return (char)this.chars[n];
        }

        int getFieldLength(int n) {
            return this.lengths[n];
        }

        void populate(int n, String string) {
            for (char c : string.toCharArray()) {
                if (!$assertionsDisabled && c != string.charAt(0)) {
                    throw new AssertionError();
                }
            }
            this.populate(n, string.charAt(0), string.length());
        }

        void populate(int n, char c, int n2) {
            if (!$assertionsDisabled && c > '\u007f') {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && n2 > 127) {
                throw new AssertionError();
            }
            this.chars[n] = (byte)c;
            this.lengths[n] = (byte)n2;
        }

        public boolean isFieldEmpty(int n) {
            return this.lengths[n] == 0;
        }

        public String toString() {
            return this.appendTo(new StringBuilder(), false, false).toString();
        }

        public String toString(boolean bl) {
            return this.appendTo(new StringBuilder(), false, bl).toString();
        }

        public String toCanonicalString() {
            return this.appendTo(new StringBuilder(), true, false).toString();
        }

        public String toCanonicalString(boolean bl) {
            return this.appendTo(new StringBuilder(), true, bl).toString();
        }

        public StringBuilder appendTo(StringBuilder stringBuilder) {
            return this.appendTo(stringBuilder, false, false);
        }

        private StringBuilder appendTo(StringBuilder stringBuilder, boolean bl, boolean bl2) {
            for (int i = 0; i < 16; ++i) {
                if (bl2 && i == 10) continue;
                this.appendFieldTo(i, stringBuilder, bl);
            }
            return stringBuilder;
        }

        public StringBuilder appendFieldTo(int n, StringBuilder stringBuilder) {
            return this.appendFieldTo(n, stringBuilder, false);
        }

        private StringBuilder appendFieldTo(int n, StringBuilder stringBuilder, boolean bl) {
            char c = (char)this.chars[n];
            int n2 = this.lengths[n];
            if (bl) {
                c = DateTimePatternGenerator.access$1900(n, c);
            }
            for (int i = 0; i < n2; ++i) {
                stringBuilder.append(c);
            }
            return stringBuilder;
        }

        public int compareTo(SkeletonFields skeletonFields) {
            for (int i = 0; i < 16; ++i) {
                int n = this.chars[i] - skeletonFields.chars[i];
                if (n != 0) {
                    return n;
                }
                int n2 = this.lengths[i] - skeletonFields.lengths[i];
                if (n2 == 0) continue;
                return n2;
            }
            return 1;
        }

        public boolean equals(Object object) {
            return this == object || object != null && object instanceof SkeletonFields && this.compareTo((SkeletonFields)object) == 0;
        }

        public int hashCode() {
            return Arrays.hashCode(this.chars) ^ Arrays.hashCode(this.lengths);
        }

        SkeletonFields(1 var1_1) {
            this();
        }
    }

    private static enum DTPGflags {
        FIX_FRACTIONAL_SECONDS,
        SKELETON_USES_CAP_J;

    }

    private static class PatternWithSkeletonFlag {
        public String pattern;
        public boolean skeletonWasSpecified;

        public PatternWithSkeletonFlag(String string, boolean bl) {
            this.pattern = string;
            this.skeletonWasSpecified = bl;
        }

        public String toString() {
            return this.pattern + "," + this.skeletonWasSpecified;
        }
    }

    private static class PatternWithMatcher {
        public String pattern;
        public DateTimeMatcher matcherWithSkeleton;

        public PatternWithMatcher(String string, DateTimeMatcher dateTimeMatcher) {
            this.pattern = string;
            this.matcherWithSkeleton = dateTimeMatcher;
        }
    }

    @Deprecated
    public static class FormatParser {
        private static final UnicodeSet SYNTAX_CHARS = new UnicodeSet("[a-zA-Z]").freeze();
        private static final UnicodeSet QUOTING_CHARS = new UnicodeSet("[[[:script=Latn:][:script=Cyrl:]]&[[:L:][:M:]]]").freeze();
        private transient PatternTokenizer tokenizer = new PatternTokenizer().setSyntaxCharacters(SYNTAX_CHARS).setExtraQuotingCharacters(QUOTING_CHARS).setUsingQuote(false);
        private List<Object> items = new ArrayList<Object>();

        @Deprecated
        public FormatParser() {
        }

        @Deprecated
        public final FormatParser set(String string) {
            return this.set(string, true);
        }

        @Deprecated
        public FormatParser set(String string, boolean bl) {
            this.items.clear();
            if (string.length() == 0) {
                return this;
            }
            this.tokenizer.setPattern(string);
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer2 = new StringBuffer();
            while (true) {
                stringBuffer.setLength(0);
                int n = this.tokenizer.next(stringBuffer);
                if (n == 0) break;
                if (n == 1) {
                    if (stringBuffer2.length() != 0 && stringBuffer.charAt(0) != stringBuffer2.charAt(0)) {
                        this.addVariable(stringBuffer2, false);
                    }
                    stringBuffer2.append(stringBuffer);
                    continue;
                }
                this.addVariable(stringBuffer2, false);
                this.items.add(stringBuffer.toString());
            }
            this.addVariable(stringBuffer2, false);
            return this;
        }

        private void addVariable(StringBuffer stringBuffer, boolean bl) {
            if (stringBuffer.length() != 0) {
                this.items.add(new VariableField(stringBuffer.toString(), bl));
                stringBuffer.setLength(0);
            }
        }

        @Deprecated
        public List<Object> getItems() {
            return this.items;
        }

        @Deprecated
        public String toString() {
            return this.toString(0, this.items.size());
        }

        @Deprecated
        public String toString(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = n; i < n2; ++i) {
                Object object = this.items.get(i);
                if (object instanceof String) {
                    String string = (String)object;
                    stringBuilder.append(this.tokenizer.quoteLiteral(string));
                    continue;
                }
                stringBuilder.append(this.items.get(i).toString());
            }
            return stringBuilder.toString();
        }

        @Deprecated
        public boolean hasDateAndTimeFields() {
            int n = 0;
            for (Object object : this.items) {
                if (!(object instanceof VariableField)) continue;
                int n2 = ((VariableField)object).getType();
                n |= 1 << n2;
            }
            boolean bl = (n & 0x3FF) != 0;
            boolean bl2 = (n & 0xFC00) != 0;
            return bl && bl2;
        }

        @Deprecated
        public Object quoteLiteral(String string) {
            return this.tokenizer.quoteLiteral(string);
        }

        static List access$1000(FormatParser formatParser) {
            return formatParser.items;
        }
    }

    @Deprecated
    public static class VariableField {
        private final String string;
        private final int canonicalIndex;

        @Deprecated
        public VariableField(String string) {
            this(string, false);
        }

        @Deprecated
        public VariableField(String string, boolean bl) {
            this.canonicalIndex = DateTimePatternGenerator.access$1400(string, bl);
            if (this.canonicalIndex < 0) {
                throw new IllegalArgumentException("Illegal datetime field:\t" + string);
            }
            this.string = string;
        }

        @Deprecated
        public int getType() {
            return DateTimePatternGenerator.access$1500()[this.canonicalIndex][1];
        }

        @Deprecated
        public static String getCanonicalCode(int n) {
            try {
                return DateTimePatternGenerator.access$1600()[n];
            } catch (Exception exception) {
                return String.valueOf(n);
            }
        }

        @Deprecated
        public boolean isNumeric() {
            return DateTimePatternGenerator.access$1500()[this.canonicalIndex][2] > 0;
        }

        private int getCanonicalIndex() {
            return this.canonicalIndex;
        }

        @Deprecated
        public String toString() {
            return this.string;
        }

        static int access$2100(VariableField variableField) {
            return variableField.getCanonicalIndex();
        }
    }

    public static enum DisplayWidth {
        WIDE(""),
        ABBREVIATED("-short"),
        NARROW("-narrow");

        @Deprecated
        private static int COUNT;
        private final String cldrKey;

        private DisplayWidth(String string2) {
            this.cldrKey = string2;
        }

        private String cldrKey() {
            return this.cldrKey;
        }

        static int access$100() {
            return COUNT;
        }

        static String access$1100(DisplayWidth displayWidth) {
            return displayWidth.cldrKey();
        }

        static {
            COUNT = DisplayWidth.values().length;
        }
    }

    public static final class PatternInfo {
        public static final int OK = 0;
        public static final int BASE_CONFLICT = 1;
        public static final int CONFLICT = 2;
        public int status;
        public String conflictingPattern;
    }

    private static class DayPeriodAllowedHoursSink
    extends UResource.Sink {
        HashMap<String, String[]> tempMap;

        private DayPeriodAllowedHoursSink(HashMap<String, String[]> hashMap) {
            this.tempMap = hashMap;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string = key.toString();
                UResource.Table table2 = value.getTable();
                String[] stringArray = null;
                String string2 = null;
                int n2 = 0;
                while (table2.getKeyAndValue(n2, key, value)) {
                    if (key.contentEquals("allowed")) {
                        stringArray = value.getStringArrayOrStringAsArray();
                    } else if (key.contentEquals("preferred")) {
                        string2 = value.getString();
                    }
                    ++n2;
                }
                String[] stringArray2 = null;
                if (stringArray != null && stringArray.length > 0) {
                    stringArray2 = new String[stringArray.length + 1];
                    stringArray2[0] = string2 != null ? string2 : stringArray[0];
                    System.arraycopy(stringArray, 0, stringArray2, 1, stringArray.length);
                } else {
                    stringArray2 = new String[]{string2 != null ? string2 : DateTimePatternGenerator.access$800()[0], stringArray2[0]};
                }
                this.tempMap.put(string, stringArray2);
                ++n;
            }
        }

        DayPeriodAllowedHoursSink(HashMap hashMap, 1 var2_2) {
            this(hashMap);
        }
    }

    private class AvailableFormatsSink
    extends UResource.Sink {
        PatternInfo returnInfo;
        final DateTimePatternGenerator this$0;

        public AvailableFormatsSink(DateTimePatternGenerator dateTimePatternGenerator, PatternInfo patternInfo) {
            this.this$0 = dateTimePatternGenerator;
            this.returnInfo = patternInfo;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string = key.toString();
                if (!DateTimePatternGenerator.access$400(this.this$0, string)) {
                    DateTimePatternGenerator.access$500(this.this$0, string);
                    String string2 = value.toString();
                    this.this$0.addPatternWithSkeleton(string2, string, !bl, this.returnInfo);
                }
                ++n;
            }
        }
    }

    private class AppendItemNamesSink
    extends UResource.Sink {
        final DateTimePatternGenerator this$0;

        private AppendItemNamesSink(DateTimePatternGenerator dateTimePatternGenerator) {
            this.this$0 = dateTimePatternGenerator;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2;
                if (value.getType() == 2 && (n2 = DateTimePatternGenerator.access$000(key)) != -1) {
                    int n3 = n2 / DisplayWidth.access$100();
                    DisplayWidth displayWidth = DateTimePatternGenerator.access$200()[n2 % DisplayWidth.access$100()];
                    UResource.Table table2 = value.getTable();
                    int n4 = 0;
                    while (table2.getKeyAndValue(n4, key, value)) {
                        if (key.contentEquals("dn")) {
                            if (this.this$0.getFieldDisplayName(n3, displayWidth) != null) break;
                            DateTimePatternGenerator.access$300(this.this$0, n3, displayWidth, value.toString());
                            break;
                        }
                        ++n4;
                    }
                }
                ++n;
            }
        }

        AppendItemNamesSink(DateTimePatternGenerator dateTimePatternGenerator, 1 var2_2) {
            this(dateTimePatternGenerator);
        }
    }

    private class AppendItemFormatsSink
    extends UResource.Sink {
        static final boolean $assertionsDisabled = !DateTimePatternGenerator.class.desiredAssertionStatus();
        final DateTimePatternGenerator this$0;

        private AppendItemFormatsSink(DateTimePatternGenerator dateTimePatternGenerator) {
            this.this$0 = dateTimePatternGenerator;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2 = DateTimePatternGenerator.getAppendFormatNumber(key);
                if (!$assertionsDisabled && n2 == -1) {
                    throw new AssertionError();
                }
                if (this.this$0.getAppendItemFormat(n2) == null) {
                    this.this$0.setAppendItemFormat(n2, value.toString());
                }
                ++n;
            }
        }

        AppendItemFormatsSink(DateTimePatternGenerator dateTimePatternGenerator, 1 var2_2) {
            this(dateTimePatternGenerator);
        }
    }
}

