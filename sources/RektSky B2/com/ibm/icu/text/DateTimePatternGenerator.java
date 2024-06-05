package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.util.*;

public class DateTimePatternGenerator implements Freezable<DateTimePatternGenerator>, Cloneable
{
    private static final boolean DEBUG = false;
    private static final String[] LAST_RESORT_ALLOWED_HOUR_FORMAT;
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
    private TreeMap<DateTimeMatcher, PatternWithSkeletonFlag> skeleton2pattern;
    private TreeMap<String, PatternWithSkeletonFlag> basePattern_pattern;
    private String decimal;
    private String dateTimeFormat;
    private String[] appendItemFormats;
    private String[][] fieldDisplayNames;
    private char defaultHourFormatChar;
    private volatile boolean frozen;
    private transient DateTimeMatcher current;
    private transient FormatParser fp;
    private transient DistanceInfo _distanceInfo;
    private String[] allowedHourFormats;
    private static final int FRACTIONAL_MASK = 16384;
    private static final int SECOND_AND_FRACTIONAL_MASK = 24576;
    private static ICUCache<String, DateTimePatternGenerator> DTPNG_CACHE;
    private static final String[] CLDR_FIELD_APPEND;
    private static final String[] CLDR_FIELD_NAME;
    private static final String[] FIELD_NAME;
    private static final String[] CANONICAL_ITEMS;
    private static final Set<String> CANONICAL_SET;
    private Set<String> cldrAvailableFormatKeys;
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
        final DateTimePatternGenerator instance = new DateTimePatternGenerator();
        instance.addCanonicalItems();
        instance.fillInMissing();
        return instance;
    }
    
    protected DateTimePatternGenerator() {
        this.skeleton2pattern = new TreeMap<DateTimeMatcher, PatternWithSkeletonFlag>();
        this.basePattern_pattern = new TreeMap<String, PatternWithSkeletonFlag>();
        this.decimal = "?";
        this.dateTimeFormat = "{1} {0}";
        this.appendItemFormats = new String[16];
        this.fieldDisplayNames = new String[16][DisplayWidth.COUNT];
        this.defaultHourFormatChar = 'H';
        this.frozen = false;
        this.current = new DateTimeMatcher();
        this.fp = new FormatParser();
        this._distanceInfo = new DistanceInfo();
        this.cldrAvailableFormatKeys = new HashSet<String>(20);
    }
    
    public static DateTimePatternGenerator getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static DateTimePatternGenerator getInstance(final ULocale uLocale) {
        return getFrozenInstance(uLocale).cloneAsThawed();
    }
    
    public static DateTimePatternGenerator getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }
    
    @Deprecated
    public static DateTimePatternGenerator getFrozenInstance(final ULocale uLocale) {
        final String localeKey = uLocale.toString();
        DateTimePatternGenerator result = DateTimePatternGenerator.DTPNG_CACHE.get(localeKey);
        if (result != null) {
            return result;
        }
        result = new DateTimePatternGenerator();
        result.initData(uLocale);
        result.freeze();
        DateTimePatternGenerator.DTPNG_CACHE.put(localeKey, result);
        return result;
    }
    
    private void initData(final ULocale uLocale) {
        final PatternInfo returnInfo = new PatternInfo();
        this.addCanonicalItems();
        this.addICUPatterns(returnInfo, uLocale);
        this.addCLDRData(returnInfo, uLocale);
        this.setDateTimeFromCalendar(uLocale);
        this.setDecimalSymbols(uLocale);
        this.getAllowedHourFormats(uLocale);
        this.fillInMissing();
    }
    
    private void addICUPatterns(final PatternInfo returnInfo, final ULocale uLocale) {
        for (int i = 0; i <= 3; ++i) {
            SimpleDateFormat df = (SimpleDateFormat)DateFormat.getDateInstance(i, uLocale);
            this.addPattern(df.toPattern(), false, returnInfo);
            df = (SimpleDateFormat)DateFormat.getTimeInstance(i, uLocale);
            this.addPattern(df.toPattern(), false, returnInfo);
            if (i == 3) {
                this.consumeShortTimePattern(df.toPattern(), returnInfo);
            }
        }
    }
    
    private String getCalendarTypeToUse(final ULocale uLocale) {
        String calendarTypeToUse = uLocale.getKeywordValue("calendar");
        if (calendarTypeToUse == null) {
            final String[] preferredCalendarTypes = Calendar.getKeywordValuesForLocale("calendar", uLocale, true);
            calendarTypeToUse = preferredCalendarTypes[0];
        }
        if (calendarTypeToUse == null) {
            calendarTypeToUse = "gregorian";
        }
        return calendarTypeToUse;
    }
    
    private void consumeShortTimePattern(final String shortTimePattern, final PatternInfo returnInfo) {
        final FormatParser fp = new FormatParser();
        fp.set(shortTimePattern);
        final List<Object> items = fp.getItems();
        for (int idx = 0; idx < items.size(); ++idx) {
            final Object item = items.get(idx);
            if (item instanceof VariableField) {
                final VariableField fld = (VariableField)item;
                if (fld.getType() == 11) {
                    this.defaultHourFormatChar = fld.toString().charAt(0);
                    break;
                }
            }
        }
        this.hackTimes(returnInfo, shortTimePattern);
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
            if (this.getFieldDisplayName(i, DisplayWidth.NARROW) == null) {
                this.setFieldDisplayName(i, DisplayWidth.NARROW, this.getFieldDisplayName(i, DisplayWidth.ABBREVIATED));
            }
        }
    }
    
    private void addCLDRData(final PatternInfo returnInfo, final ULocale uLocale) {
        final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", uLocale);
        final String calendarTypeToUse = this.getCalendarTypeToUse(uLocale);
        final AppendItemFormatsSink appendItemFormatsSink = new AppendItemFormatsSink();
        try {
            rb.getAllItemsWithFallback("calendar/" + calendarTypeToUse + "/appendItems", appendItemFormatsSink);
        }
        catch (MissingResourceException ex) {}
        final AppendItemNamesSink appendItemNamesSink = new AppendItemNamesSink();
        try {
            rb.getAllItemsWithFallback("fields", appendItemNamesSink);
        }
        catch (MissingResourceException ex2) {}
        final AvailableFormatsSink availableFormatsSink = new AvailableFormatsSink(returnInfo);
        try {
            rb.getAllItemsWithFallback("calendar/" + calendarTypeToUse + "/availableFormats", availableFormatsSink);
        }
        catch (MissingResourceException ex3) {}
    }
    
    private void setDateTimeFromCalendar(final ULocale uLocale) {
        final String dateTimeFormat = Calendar.getDateTimePattern(Calendar.getInstance(uLocale), uLocale, 2);
        this.setDateTimeFormat(dateTimeFormat);
    }
    
    private void setDecimalSymbols(final ULocale uLocale) {
        final DecimalFormatSymbols dfs = new DecimalFormatSymbols(uLocale);
        this.setDecimal(String.valueOf(dfs.getDecimalSeparator()));
    }
    
    private void getAllowedHourFormats(final ULocale uLocale) {
        final ULocale max = ULocale.addLikelySubtags(uLocale);
        String country = max.getCountry();
        if (country.isEmpty()) {
            country = "001";
        }
        final String langCountry = max.getLanguage() + "_" + country;
        String[] list = DateTimePatternGenerator.LOCALE_TO_ALLOWED_HOUR.get(langCountry);
        if (list == null) {
            list = DateTimePatternGenerator.LOCALE_TO_ALLOWED_HOUR.get(country);
            if (list == null) {
                list = DateTimePatternGenerator.LAST_RESORT_ALLOWED_HOUR_FORMAT;
            }
        }
        this.allowedHourFormats = list;
    }
    
    @Deprecated
    public char getDefaultHourFormatChar() {
        return this.defaultHourFormatChar;
    }
    
    @Deprecated
    public void setDefaultHourFormatChar(final char defaultHourFormatChar) {
        this.defaultHourFormatChar = defaultHourFormatChar;
    }
    
    private void hackTimes(final PatternInfo returnInfo, final String shortTimePattern) {
        this.fp.set(shortTimePattern);
        final StringBuilder mmss = new StringBuilder();
        boolean gotMm = false;
        for (int i = 0; i < this.fp.items.size(); ++i) {
            final Object item = this.fp.items.get(i);
            if (item instanceof String) {
                if (gotMm) {
                    mmss.append(this.fp.quoteLiteral(item.toString()));
                }
            }
            else {
                final char ch = item.toString().charAt(0);
                if (ch == 'm') {
                    gotMm = true;
                    mmss.append(item);
                }
                else if (ch == 's') {
                    if (!gotMm) {
                        break;
                    }
                    mmss.append(item);
                    this.addPattern(mmss.toString(), false, returnInfo);
                    break;
                }
                else {
                    if (gotMm || ch == 'z' || ch == 'Z' || ch == 'v') {
                        break;
                    }
                    if (ch == 'V') {
                        break;
                    }
                }
            }
        }
        final BitSet variables = new BitSet();
        final BitSet nuke = new BitSet();
        for (int j = 0; j < this.fp.items.size(); ++j) {
            final Object item2 = this.fp.items.get(j);
            if (item2 instanceof VariableField) {
                variables.set(j);
                final char ch2 = item2.toString().charAt(0);
                if (ch2 == 's' || ch2 == 'S') {
                    nuke.set(j);
                    for (int k = j - 1; k >= 0; ++k) {
                        if (variables.get(k)) {
                            break;
                        }
                        nuke.set(j);
                    }
                }
            }
        }
        final String hhmm = getFilteredPattern(this.fp, nuke);
        this.addPattern(hhmm, false, returnInfo);
    }
    
    private static String getFilteredPattern(final FormatParser fp, final BitSet nuke) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < fp.items.size(); ++i) {
            if (!nuke.get(i)) {
                final Object item = fp.items.get(i);
                if (item instanceof String) {
                    result.append(fp.quoteLiteral(item.toString()));
                }
                else {
                    result.append(item.toString());
                }
            }
        }
        return result.toString();
    }
    
    @Deprecated
    public static int getAppendFormatNumber(final UResource.Key key) {
        for (int i = 0; i < DateTimePatternGenerator.CLDR_FIELD_APPEND.length; ++i) {
            if (key.contentEquals(DateTimePatternGenerator.CLDR_FIELD_APPEND[i])) {
                return i;
            }
        }
        return -1;
    }
    
    @Deprecated
    public static int getAppendFormatNumber(final String string) {
        for (int i = 0; i < DateTimePatternGenerator.CLDR_FIELD_APPEND.length; ++i) {
            if (DateTimePatternGenerator.CLDR_FIELD_APPEND[i].equals(string)) {
                return i;
            }
        }
        return -1;
    }
    
    private static int getCLDRFieldAndWidthNumber(final UResource.Key key) {
        for (int i = 0; i < DateTimePatternGenerator.CLDR_FIELD_NAME.length; ++i) {
            for (int j = 0; j < DisplayWidth.COUNT; ++j) {
                final String fullKey = DateTimePatternGenerator.CLDR_FIELD_NAME[i].concat(DateTimePatternGenerator.CLDR_FIELD_WIDTH[j].cldrKey());
                if (key.contentEquals(fullKey)) {
                    return i * DisplayWidth.COUNT + j;
                }
            }
        }
        return -1;
    }
    
    public String getBestPattern(final String skeleton) {
        return this.getBestPattern(skeleton, null, 0);
    }
    
    public String getBestPattern(final String skeleton, final int options) {
        return this.getBestPattern(skeleton, null, options);
    }
    
    private String getBestPattern(final String skeleton, final DateTimeMatcher skipMatcher, final int options) {
        final EnumSet<DTPGflags> flags = EnumSet.noneOf(DTPGflags.class);
        final String skeletonMapped = this.mapSkeletonMetacharacters(skeleton, flags);
        final String datePattern;
        final String timePattern;
        synchronized (this) {
            this.current.set(skeletonMapped, this.fp, false);
            final PatternWithMatcher bestWithMatcher = this.getBestRaw(this.current, -1, this._distanceInfo, skipMatcher);
            if (this._distanceInfo.missingFieldMask == 0 && this._distanceInfo.extraFieldMask == 0) {
                return this.adjustFieldTypes(bestWithMatcher, this.current, flags, options);
            }
            final int neededFields = this.current.getFieldMask();
            datePattern = this.getBestAppending(this.current, neededFields & 0x3FF, this._distanceInfo, skipMatcher, flags, options);
            timePattern = this.getBestAppending(this.current, neededFields & 0xFC00, this._distanceInfo, skipMatcher, flags, options);
        }
        if (datePattern == null) {
            return (timePattern == null) ? "" : timePattern;
        }
        if (timePattern == null) {
            return datePattern;
        }
        return SimpleFormatterImpl.formatRawPattern(this.getDateTimeFormat(), 2, 2, timePattern, datePattern);
    }
    
    private String mapSkeletonMetacharacters(final String skeleton, final EnumSet<DTPGflags> flags) {
        final StringBuilder skeletonCopy = new StringBuilder();
        boolean inQuoted = false;
        for (int patPos = 0; patPos < skeleton.length(); ++patPos) {
            final char patChr = skeleton.charAt(patPos);
            if (patChr == '\'') {
                inQuoted = !inQuoted;
            }
            else if (!inQuoted) {
                if (patChr == 'j' || patChr == 'C') {
                    int extraLen = 0;
                    while (patPos + 1 < skeleton.length() && skeleton.charAt(patPos + 1) == patChr) {
                        ++extraLen;
                        ++patPos;
                    }
                    int hourLen = 1 + (extraLen & 0x1);
                    int dayPeriodLen = (extraLen < 2) ? 1 : (3 + (extraLen >> 1));
                    char hourChar = 'h';
                    char dayPeriodChar = 'a';
                    if (patChr == 'j') {
                        hourChar = this.defaultHourFormatChar;
                    }
                    else {
                        final String preferred = this.allowedHourFormats[0];
                        hourChar = preferred.charAt(0);
                        final char last = preferred.charAt(preferred.length() - 1);
                        if (last == 'b' || last == 'B') {
                            dayPeriodChar = last;
                        }
                    }
                    if (hourChar == 'H' || hourChar == 'k') {
                        dayPeriodLen = 0;
                    }
                    while (dayPeriodLen-- > 0) {
                        skeletonCopy.append(dayPeriodChar);
                    }
                    while (hourLen-- > 0) {
                        skeletonCopy.append(hourChar);
                    }
                }
                else if (patChr == 'J') {
                    skeletonCopy.append('H');
                    flags.add(DTPGflags.SKELETON_USES_CAP_J);
                }
                else {
                    skeletonCopy.append(patChr);
                }
            }
        }
        return skeletonCopy.toString();
    }
    
    public DateTimePatternGenerator addPattern(final String pattern, final boolean override, final PatternInfo returnInfo) {
        return this.addPatternWithSkeleton(pattern, null, override, returnInfo);
    }
    
    @Deprecated
    public DateTimePatternGenerator addPatternWithSkeleton(final String pattern, final String skeletonToUse, final boolean override, final PatternInfo returnInfo) {
        this.checkFrozen();
        DateTimeMatcher matcher;
        if (skeletonToUse == null) {
            matcher = new DateTimeMatcher().set(pattern, this.fp, false);
        }
        else {
            matcher = new DateTimeMatcher().set(skeletonToUse, this.fp, false);
        }
        final String basePattern = matcher.getBasePattern();
        final PatternWithSkeletonFlag previousPatternWithSameBase = this.basePattern_pattern.get(basePattern);
        if (previousPatternWithSameBase != null && (!previousPatternWithSameBase.skeletonWasSpecified || (skeletonToUse != null && !override))) {
            returnInfo.status = 1;
            returnInfo.conflictingPattern = previousPatternWithSameBase.pattern;
            if (!override) {
                return this;
            }
        }
        final PatternWithSkeletonFlag previousValue = this.skeleton2pattern.get(matcher);
        if (previousValue != null) {
            returnInfo.status = 2;
            returnInfo.conflictingPattern = previousValue.pattern;
            if (!override || (skeletonToUse != null && previousValue.skeletonWasSpecified)) {
                return this;
            }
        }
        returnInfo.status = 0;
        returnInfo.conflictingPattern = "";
        final PatternWithSkeletonFlag patWithSkelFlag = new PatternWithSkeletonFlag(pattern, skeletonToUse != null);
        this.skeleton2pattern.put(matcher, patWithSkelFlag);
        this.basePattern_pattern.put(basePattern, patWithSkelFlag);
        return this;
    }
    
    public String getSkeleton(final String pattern) {
        synchronized (this) {
            this.current.set(pattern, this.fp, false);
            return this.current.toString();
        }
    }
    
    @Deprecated
    public String getSkeletonAllowingDuplicates(final String pattern) {
        synchronized (this) {
            this.current.set(pattern, this.fp, true);
            return this.current.toString();
        }
    }
    
    @Deprecated
    public String getCanonicalSkeletonAllowingDuplicates(final String pattern) {
        synchronized (this) {
            this.current.set(pattern, this.fp, true);
            return this.current.toCanonicalString();
        }
    }
    
    public String getBaseSkeleton(final String pattern) {
        synchronized (this) {
            this.current.set(pattern, this.fp, false);
            return this.current.getBasePattern();
        }
    }
    
    public Map<String, String> getSkeletons(Map<String, String> result) {
        if (result == null) {
            result = new LinkedHashMap<String, String>();
        }
        for (final DateTimeMatcher item : this.skeleton2pattern.keySet()) {
            final PatternWithSkeletonFlag patternWithSkelFlag = this.skeleton2pattern.get(item);
            final String pattern = patternWithSkelFlag.pattern;
            if (DateTimePatternGenerator.CANONICAL_SET.contains(pattern)) {
                continue;
            }
            result.put(item.toString(), pattern);
        }
        return result;
    }
    
    public Set<String> getBaseSkeletons(Set<String> result) {
        if (result == null) {
            result = new HashSet<String>();
        }
        result.addAll(this.basePattern_pattern.keySet());
        return result;
    }
    
    public String replaceFieldTypes(final String pattern, final String skeleton) {
        return this.replaceFieldTypes(pattern, skeleton, 0);
    }
    
    public String replaceFieldTypes(final String pattern, final String skeleton, final int options) {
        synchronized (this) {
            final PatternWithMatcher patternNoMatcher = new PatternWithMatcher(pattern, null);
            return this.adjustFieldTypes(patternNoMatcher, this.current.set(skeleton, this.fp, false), EnumSet.noneOf(DTPGflags.class), options);
        }
    }
    
    public void setDateTimeFormat(final String dateTimeFormat) {
        this.checkFrozen();
        this.dateTimeFormat = dateTimeFormat;
    }
    
    public String getDateTimeFormat() {
        return this.dateTimeFormat;
    }
    
    public void setDecimal(final String decimal) {
        this.checkFrozen();
        this.decimal = decimal;
    }
    
    public String getDecimal() {
        return this.decimal;
    }
    
    @Deprecated
    public Collection<String> getRedundants(Collection<String> output) {
        synchronized (this) {
            if (output == null) {
                output = new LinkedHashSet<String>();
            }
            for (final DateTimeMatcher cur : this.skeleton2pattern.keySet()) {
                final PatternWithSkeletonFlag patternWithSkelFlag = this.skeleton2pattern.get(cur);
                final String pattern = patternWithSkelFlag.pattern;
                if (DateTimePatternGenerator.CANONICAL_SET.contains(pattern)) {
                    continue;
                }
                final String trial = this.getBestPattern(cur.toString(), cur, 0);
                if (!trial.equals(pattern)) {
                    continue;
                }
                output.add(pattern);
            }
            return output;
        }
    }
    
    public void setAppendItemFormat(final int field, final String value) {
        this.checkFrozen();
        this.appendItemFormats[field] = value;
    }
    
    public String getAppendItemFormat(final int field) {
        return this.appendItemFormats[field];
    }
    
    public void setAppendItemName(final int field, final String value) {
        this.setFieldDisplayName(field, DateTimePatternGenerator.APPENDITEM_WIDTH, value);
    }
    
    public String getAppendItemName(final int field) {
        return this.getFieldDisplayName(field, DateTimePatternGenerator.APPENDITEM_WIDTH);
    }
    
    @Deprecated
    private void setFieldDisplayName(final int field, final DisplayWidth width, final String value) {
        this.checkFrozen();
        if (field < 16 && field >= 0) {
            this.fieldDisplayNames[field][width.ordinal()] = value;
        }
    }
    
    public String getFieldDisplayName(final int field, final DisplayWidth width) {
        if (field >= 16 || field < 0) {
            return "";
        }
        return this.fieldDisplayNames[field][width.ordinal()];
    }
    
    @Deprecated
    public static boolean isSingleField(final String skeleton) {
        final char first = skeleton.charAt(0);
        for (int i = 1; i < skeleton.length(); ++i) {
            if (skeleton.charAt(i) != first) {
                return false;
            }
        }
        return true;
    }
    
    private void setAvailableFormat(final String key) {
        this.checkFrozen();
        this.cldrAvailableFormatKeys.add(key);
    }
    
    private boolean isAvailableFormatSet(final String key) {
        return this.cldrAvailableFormatKeys.contains(key);
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
        final DateTimePatternGenerator result = (DateTimePatternGenerator)this.clone();
        this.frozen = false;
        return result;
    }
    
    public Object clone() {
        try {
            final DateTimePatternGenerator result = (DateTimePatternGenerator)super.clone();
            result.skeleton2pattern = (TreeMap<DateTimeMatcher, PatternWithSkeletonFlag>)this.skeleton2pattern.clone();
            result.basePattern_pattern = (TreeMap<String, PatternWithSkeletonFlag>)this.basePattern_pattern.clone();
            result.appendItemFormats = this.appendItemFormats.clone();
            result.fieldDisplayNames = this.fieldDisplayNames.clone();
            result.current = new DateTimeMatcher();
            result.fp = new FormatParser();
            result._distanceInfo = new DistanceInfo();
            result.frozen = false;
            return result;
        }
        catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException("Internal Error", e);
        }
    }
    
    @Deprecated
    public boolean skeletonsAreSimilar(final String id, final String skeleton) {
        if (id.equals(skeleton)) {
            return true;
        }
        final TreeSet<String> parser1 = this.getSet(id);
        final TreeSet<String> parser2 = this.getSet(skeleton);
        if (parser1.size() != parser2.size()) {
            return false;
        }
        final Iterator<String> it2 = parser2.iterator();
        for (final String item : parser1) {
            final int index1 = getCanonicalIndex(item, false);
            final String item2 = it2.next();
            final int index2 = getCanonicalIndex(item2, false);
            if (DateTimePatternGenerator.types[index1][1] != DateTimePatternGenerator.types[index2][1]) {
                return false;
            }
        }
        return true;
    }
    
    private TreeSet<String> getSet(final String id) {
        final List<Object> items = this.fp.set(id).getItems();
        final TreeSet<String> result = new TreeSet<String>();
        for (final Object obj : items) {
            final String item = obj.toString();
            if (!item.startsWith("G")) {
                if (item.startsWith("a")) {
                    continue;
                }
                result.add(item);
            }
        }
        return result;
    }
    
    private void checkFrozen() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
    }
    
    private String getBestAppending(final DateTimeMatcher source, final int missingFields, final DistanceInfo distInfo, final DateTimeMatcher skipMatcher, EnumSet<DTPGflags> flags, final int options) {
        String resultPattern = null;
        if (missingFields != 0) {
            final PatternWithMatcher resultPatternWithMatcher = this.getBestRaw(source, missingFields, distInfo, skipMatcher);
            resultPattern = this.adjustFieldTypes(resultPatternWithMatcher, source, flags, options);
            while (distInfo.missingFieldMask != 0) {
                if ((distInfo.missingFieldMask & 0x6000) == 0x4000 && (missingFields & 0x6000) == 0x6000) {
                    resultPatternWithMatcher.pattern = resultPattern;
                    flags = EnumSet.copyOf(flags);
                    flags.add(DTPGflags.FIX_FRACTIONAL_SECONDS);
                    resultPattern = this.adjustFieldTypes(resultPatternWithMatcher, source, flags, options);
                    distInfo.missingFieldMask &= 0xFFFFBFFF;
                }
                else {
                    final int startingMask = distInfo.missingFieldMask;
                    final PatternWithMatcher tempWithMatcher = this.getBestRaw(source, distInfo.missingFieldMask, distInfo, skipMatcher);
                    final String temp = this.adjustFieldTypes(tempWithMatcher, source, flags, options);
                    final int foundMask = startingMask & ~distInfo.missingFieldMask;
                    final int topField = this.getTopBitNumber(foundMask);
                    resultPattern = SimpleFormatterImpl.formatRawPattern(this.getAppendFormat(topField), 2, 3, resultPattern, temp, this.getAppendName(topField));
                }
            }
        }
        return resultPattern;
    }
    
    private String getAppendName(final int foundMask) {
        return "'" + this.fieldDisplayNames[foundMask][DateTimePatternGenerator.APPENDITEM_WIDTH_INT] + "'";
    }
    
    private String getAppendFormat(final int foundMask) {
        return this.appendItemFormats[foundMask];
    }
    
    private int getTopBitNumber(int foundMask) {
        int i;
        for (i = 0; foundMask != 0; foundMask >>>= 1, ++i) {}
        return i - 1;
    }
    
    private void addCanonicalItems() {
        final PatternInfo patternInfo = new PatternInfo();
        for (int i = 0; i < DateTimePatternGenerator.CANONICAL_ITEMS.length; ++i) {
            this.addPattern(String.valueOf(DateTimePatternGenerator.CANONICAL_ITEMS[i]), false, patternInfo);
        }
    }
    
    private PatternWithMatcher getBestRaw(final DateTimeMatcher source, final int includeMask, final DistanceInfo missingFields, final DateTimeMatcher skipMatcher) {
        int bestDistance = Integer.MAX_VALUE;
        final PatternWithMatcher bestPatternWithMatcher = new PatternWithMatcher("", null);
        final DistanceInfo tempInfo = new DistanceInfo();
        for (final DateTimeMatcher trial : this.skeleton2pattern.keySet()) {
            if (trial.equals(skipMatcher)) {
                continue;
            }
            final int distance = source.getDistance(trial, includeMask, tempInfo);
            if (distance >= bestDistance) {
                continue;
            }
            bestDistance = distance;
            final PatternWithSkeletonFlag patternWithSkelFlag = this.skeleton2pattern.get(trial);
            bestPatternWithMatcher.pattern = patternWithSkelFlag.pattern;
            if (patternWithSkelFlag.skeletonWasSpecified) {
                bestPatternWithMatcher.matcherWithSkeleton = trial;
            }
            else {
                bestPatternWithMatcher.matcherWithSkeleton = null;
            }
            missingFields.setTo(tempInfo);
            if (distance == 0) {
                break;
            }
        }
        return bestPatternWithMatcher;
    }
    
    private String adjustFieldTypes(final PatternWithMatcher patternWithMatcher, final DateTimeMatcher inputRequest, final EnumSet<DTPGflags> flags, final int options) {
        this.fp.set(patternWithMatcher.pattern);
        final StringBuilder newPattern = new StringBuilder();
        for (final Object item : this.fp.getItems()) {
            if (item instanceof String) {
                newPattern.append(this.fp.quoteLiteral((String)item));
            }
            else {
                final VariableField variableField = (VariableField)item;
                StringBuilder fieldBuilder = new StringBuilder(variableField.toString());
                final int type = variableField.getType();
                if (flags.contains(DTPGflags.FIX_FRACTIONAL_SECONDS) && type == 13) {
                    fieldBuilder.append(this.decimal);
                    inputRequest.original.appendFieldTo(14, fieldBuilder);
                }
                else if (inputRequest.type[type] != 0) {
                    final char reqFieldChar = inputRequest.original.getFieldChar(type);
                    int reqFieldLen = inputRequest.original.getFieldLength(type);
                    if (reqFieldChar == 'E' && reqFieldLen < 3) {
                        reqFieldLen = 3;
                    }
                    int adjFieldLen = reqFieldLen;
                    final DateTimeMatcher matcherWithSkeleton = patternWithMatcher.matcherWithSkeleton;
                    if ((type == 11 && (options & 0x800) == 0x0) || (type == 12 && (options & 0x1000) == 0x0) || (type == 13 && (options & 0x2000) == 0x0)) {
                        adjFieldLen = fieldBuilder.length();
                    }
                    else if (matcherWithSkeleton != null) {
                        final int skelFieldLen = matcherWithSkeleton.original.getFieldLength(type);
                        final boolean patFieldIsNumeric = variableField.isNumeric();
                        final boolean skelFieldIsNumeric = matcherWithSkeleton.fieldIsNumeric(type);
                        if (skelFieldLen == reqFieldLen || (patFieldIsNumeric && !skelFieldIsNumeric) || (skelFieldIsNumeric && !patFieldIsNumeric)) {
                            adjFieldLen = fieldBuilder.length();
                        }
                    }
                    char c = (type != 11 && type != 3 && type != 6 && (type != 1 || reqFieldChar == 'Y')) ? reqFieldChar : fieldBuilder.charAt(0);
                    if (type == 11 && flags.contains(DTPGflags.SKELETON_USES_CAP_J)) {
                        c = this.defaultHourFormatChar;
                    }
                    fieldBuilder = new StringBuilder();
                    for (int i = adjFieldLen; i > 0; --i) {
                        fieldBuilder.append(c);
                    }
                }
                newPattern.append((CharSequence)fieldBuilder);
            }
        }
        return newPattern.toString();
    }
    
    @Deprecated
    public String getFields(final String pattern) {
        this.fp.set(pattern);
        final StringBuilder newPattern = new StringBuilder();
        for (final Object item : this.fp.getItems()) {
            if (item instanceof String) {
                newPattern.append(this.fp.quoteLiteral((String)item));
            }
            else {
                newPattern.append("{" + getName(item.toString()) + "}");
            }
        }
        return newPattern.toString();
    }
    
    private static String showMask(final int mask) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; ++i) {
            if ((mask & 1 << i) != 0x0) {
                if (result.length() != 0) {
                    result.append(" | ");
                }
                result.append(DateTimePatternGenerator.FIELD_NAME[i]);
                result.append(" ");
            }
        }
        return result.toString();
    }
    
    private static String getName(final String s) {
        final int i = getCanonicalIndex(s, true);
        String name = DateTimePatternGenerator.FIELD_NAME[DateTimePatternGenerator.types[i][1]];
        if (DateTimePatternGenerator.types[i][2] < 0) {
            name += ":S";
        }
        else {
            name += ":N";
        }
        return name;
    }
    
    private static int getCanonicalIndex(final String s, final boolean strict) {
        final int len = s.length();
        if (len == 0) {
            return -1;
        }
        final int ch = s.charAt(0);
        for (int i = 1; i < len; ++i) {
            if (s.charAt(i) != ch) {
                return -1;
            }
        }
        int bestRow = -1;
        for (int j = 0; j < DateTimePatternGenerator.types.length; ++j) {
            final int[] row = DateTimePatternGenerator.types[j];
            if (row[0] == ch) {
                bestRow = j;
                if (row[3] <= len) {
                    if (row[row.length - 1] >= len) {
                        return j;
                    }
                }
            }
        }
        return strict ? -1 : bestRow;
    }
    
    private static char getCanonicalChar(final int field, final char reference) {
        if (reference == 'h' || reference == 'K') {
            return 'h';
        }
        for (int i = 0; i < DateTimePatternGenerator.types.length; ++i) {
            final int[] row = DateTimePatternGenerator.types[i];
            if (row[1] == field) {
                return (char)row[0];
            }
        }
        throw new IllegalArgumentException("Could not find field " + field);
    }
    
    static {
        LAST_RESORT_ALLOWED_HOUR_FORMAT = new String[] { "H" };
        final HashMap<String, String[]> temp = new HashMap<String, String[]>();
        final ICUResourceBundle suppData = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        final DayPeriodAllowedHoursSink allowedHoursSink = new DayPeriodAllowedHoursSink((HashMap)temp);
        suppData.getAllItemsWithFallback("timeData", allowedHoursSink);
        LOCALE_TO_ALLOWED_HOUR = Collections.unmodifiableMap((Map<? extends String, ? extends String[]>)temp);
        APPENDITEM_WIDTH = DisplayWidth.WIDE;
        APPENDITEM_WIDTH_INT = DateTimePatternGenerator.APPENDITEM_WIDTH.ordinal();
        CLDR_FIELD_WIDTH = DisplayWidth.values();
        DateTimePatternGenerator.DTPNG_CACHE = new SimpleCache<String, DateTimePatternGenerator>();
        CLDR_FIELD_APPEND = new String[] { "Era", "Year", "Quarter", "Month", "Week", "*", "Day-Of-Week", "Day", "*", "*", "*", "Hour", "Minute", "Second", "*", "Timezone" };
        CLDR_FIELD_NAME = new String[] { "era", "year", "quarter", "month", "week", "weekOfMonth", "weekday", "day", "dayOfYear", "weekdayOfMonth", "dayperiod", "hour", "minute", "second", "*", "zone" };
        FIELD_NAME = new String[] { "Era", "Year", "Quarter", "Month", "Week_in_Year", "Week_in_Month", "Weekday", "Day", "Day_Of_Year", "Day_of_Week_in_Month", "Dayperiod", "Hour", "Minute", "Second", "Fractional_Second", "Zone" };
        CANONICAL_ITEMS = new String[] { "G", "y", "Q", "M", "w", "W", "E", "d", "D", "F", "a", "H", "m", "s", "S", "v" };
        CANONICAL_SET = new HashSet<String>(Arrays.asList(DateTimePatternGenerator.CANONICAL_ITEMS));
        types = new int[][] { { 71, 0, -259, 1, 3 }, { 71, 0, -260, 4 }, { 71, 0, -257, 5 }, { 121, 1, 256, 1, 20 }, { 89, 1, 272, 1, 20 }, { 117, 1, 288, 1, 20 }, { 114, 1, 304, 1, 20 }, { 85, 1, -259, 1, 3 }, { 85, 1, -260, 4 }, { 85, 1, -257, 5 }, { 81, 2, 256, 1, 2 }, { 81, 2, -259, 3 }, { 81, 2, -260, 4 }, { 81, 2, -257, 5 }, { 113, 2, 272, 1, 2 }, { 113, 2, -275, 3 }, { 113, 2, -276, 4 }, { 113, 2, -273, 5 }, { 77, 3, 256, 1, 2 }, { 77, 3, -259, 3 }, { 77, 3, -260, 4 }, { 77, 3, -257, 5 }, { 76, 3, 272, 1, 2 }, { 76, 3, -275, 3 }, { 76, 3, -276, 4 }, { 76, 3, -273, 5 }, { 108, 3, 272, 1, 1 }, { 119, 4, 256, 1, 2 }, { 87, 5, 256, 1 }, { 69, 6, -259, 1, 3 }, { 69, 6, -260, 4 }, { 69, 6, -257, 5 }, { 69, 6, -258, 6 }, { 99, 6, 288, 1, 2 }, { 99, 6, -291, 3 }, { 99, 6, -292, 4 }, { 99, 6, -289, 5 }, { 99, 6, -290, 6 }, { 101, 6, 272, 1, 2 }, { 101, 6, -275, 3 }, { 101, 6, -276, 4 }, { 101, 6, -273, 5 }, { 101, 6, -274, 6 }, { 100, 7, 256, 1, 2 }, { 103, 7, 272, 1, 20 }, { 68, 8, 256, 1, 3 }, { 70, 9, 256, 1 }, { 97, 10, -259, 1, 3 }, { 97, 10, -260, 4 }, { 97, 10, -257, 5 }, { 98, 10, -275, 1, 3 }, { 98, 10, -276, 4 }, { 98, 10, -273, 5 }, { 66, 10, -307, 1, 3 }, { 66, 10, -308, 4 }, { 66, 10, -305, 5 }, { 72, 11, 416, 1, 2 }, { 107, 11, 432, 1, 2 }, { 104, 11, 256, 1, 2 }, { 75, 11, 272, 1, 2 }, { 109, 12, 256, 1, 2 }, { 115, 13, 256, 1, 2 }, { 65, 13, 272, 1, 1000 }, { 83, 14, 256, 1, 1000 }, { 118, 15, -291, 1 }, { 118, 15, -292, 4 }, { 122, 15, -259, 1, 3 }, { 122, 15, -260, 4 }, { 90, 15, -273, 1, 3 }, { 90, 15, -276, 4 }, { 90, 15, -275, 5 }, { 79, 15, -275, 1 }, { 79, 15, -276, 4 }, { 86, 15, -275, 1 }, { 86, 15, -276, 2 }, { 86, 15, -277, 3 }, { 86, 15, -278, 4 }, { 88, 15, -273, 1 }, { 88, 15, -275, 2 }, { 88, 15, -276, 4 }, { 120, 15, -273, 1 }, { 120, 15, -275, 2 }, { 120, 15, -276, 4 } };
    }
    
    private class AppendItemFormatsSink extends UResource.Sink
    {
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table itemsTable = value.getTable();
            for (int i = 0; itemsTable.getKeyAndValue(i, key, value); ++i) {
                final int field = DateTimePatternGenerator.getAppendFormatNumber(key);
                assert field != -1;
                if (DateTimePatternGenerator.this.getAppendItemFormat(field) == null) {
                    DateTimePatternGenerator.this.setAppendItemFormat(field, value.toString());
                }
            }
        }
    }
    
    private class AppendItemNamesSink extends UResource.Sink
    {
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table itemsTable = value.getTable();
            for (int i = 0; itemsTable.getKeyAndValue(i, key, value); ++i) {
                if (value.getType() == 2) {
                    final int fieldAndWidth = getCLDRFieldAndWidthNumber(key);
                    if (fieldAndWidth != -1) {
                        final int field = fieldAndWidth / DisplayWidth.COUNT;
                        final DisplayWidth width = DateTimePatternGenerator.CLDR_FIELD_WIDTH[fieldAndWidth % DisplayWidth.COUNT];
                        final UResource.Table detailsTable = value.getTable();
                        int j = 0;
                        while (detailsTable.getKeyAndValue(j, key, value)) {
                            if (!key.contentEquals("dn")) {
                                ++j;
                            }
                            else {
                                if (DateTimePatternGenerator.this.getFieldDisplayName(field, width) == null) {
                                    DateTimePatternGenerator.this.setFieldDisplayName(field, width, value.toString());
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    private class AvailableFormatsSink extends UResource.Sink
    {
        PatternInfo returnInfo;
        
        public AvailableFormatsSink(final PatternInfo returnInfo) {
            this.returnInfo = returnInfo;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean isRoot) {
            final UResource.Table formatsTable = value.getTable();
            for (int i = 0; formatsTable.getKeyAndValue(i, key, value); ++i) {
                final String formatKey = key.toString();
                if (!DateTimePatternGenerator.this.isAvailableFormatSet(formatKey)) {
                    DateTimePatternGenerator.this.setAvailableFormat(formatKey);
                    final String formatValue = value.toString();
                    DateTimePatternGenerator.this.addPatternWithSkeleton(formatValue, formatKey, !isRoot, this.returnInfo);
                }
            }
        }
    }
    
    private static class DayPeriodAllowedHoursSink extends UResource.Sink
    {
        HashMap<String, String[]> tempMap;
        
        private DayPeriodAllowedHoursSink(final HashMap<String, String[]> tempMap) {
            this.tempMap = tempMap;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table timeData = value.getTable();
            for (int i = 0; timeData.getKeyAndValue(i, key, value); ++i) {
                final String regionOrLocale = key.toString();
                final UResource.Table formatList = value.getTable();
                for (int j = 0; formatList.getKeyAndValue(j, key, value); ++j) {
                    if (key.contentEquals("allowed")) {
                        this.tempMap.put(regionOrLocale, value.getStringArrayOrStringAsArray());
                    }
                }
            }
        }
    }
    
    public static final class PatternInfo
    {
        public static final int OK = 0;
        public static final int BASE_CONFLICT = 1;
        public static final int CONFLICT = 2;
        public int status;
        public String conflictingPattern;
    }
    
    public enum DisplayWidth
    {
        WIDE(""), 
        ABBREVIATED("-short"), 
        NARROW("-narrow");
        
        @Deprecated
        private static int COUNT;
        private final String cldrKey;
        
        private DisplayWidth(final String cldrKey) {
            this.cldrKey = cldrKey;
        }
        
        private String cldrKey() {
            return this.cldrKey;
        }
        
        static {
            DisplayWidth.COUNT = values().length;
        }
    }
    
    @Deprecated
    public static class VariableField
    {
        private final String string;
        private final int canonicalIndex;
        
        @Deprecated
        public VariableField(final String string) {
            this(string, false);
        }
        
        @Deprecated
        public VariableField(final String string, final boolean strict) {
            this.canonicalIndex = getCanonicalIndex(string, strict);
            if (this.canonicalIndex < 0) {
                throw new IllegalArgumentException("Illegal datetime field:\t" + string);
            }
            this.string = string;
        }
        
        @Deprecated
        public int getType() {
            return DateTimePatternGenerator.types[this.canonicalIndex][1];
        }
        
        @Deprecated
        public static String getCanonicalCode(final int type) {
            try {
                return DateTimePatternGenerator.CANONICAL_ITEMS[type];
            }
            catch (Exception e) {
                return String.valueOf(type);
            }
        }
        
        @Deprecated
        public boolean isNumeric() {
            return DateTimePatternGenerator.types[this.canonicalIndex][2] > 0;
        }
        
        private int getCanonicalIndex() {
            return this.canonicalIndex;
        }
        
        @Deprecated
        @Override
        public String toString() {
            return this.string;
        }
    }
    
    @Deprecated
    public static class FormatParser
    {
        private static final UnicodeSet SYNTAX_CHARS;
        private static final UnicodeSet QUOTING_CHARS;
        private transient PatternTokenizer tokenizer;
        private List<Object> items;
        
        @Deprecated
        public FormatParser() {
            this.tokenizer = new PatternTokenizer().setSyntaxCharacters(FormatParser.SYNTAX_CHARS).setExtraQuotingCharacters(FormatParser.QUOTING_CHARS).setUsingQuote(true);
            this.items = new ArrayList<Object>();
        }
        
        @Deprecated
        public final FormatParser set(final String string) {
            return this.set(string, false);
        }
        
        @Deprecated
        public FormatParser set(final String string, final boolean strict) {
            this.items.clear();
            if (string.length() == 0) {
                return this;
            }
            this.tokenizer.setPattern(string);
            final StringBuffer buffer = new StringBuffer();
            final StringBuffer variable = new StringBuffer();
            while (true) {
                buffer.setLength(0);
                final int status = this.tokenizer.next(buffer);
                if (status == 0) {
                    break;
                }
                if (status == 1) {
                    if (variable.length() != 0 && buffer.charAt(0) != variable.charAt(0)) {
                        this.addVariable(variable, false);
                    }
                    variable.append(buffer);
                }
                else {
                    this.addVariable(variable, false);
                    this.items.add(buffer.toString());
                }
            }
            this.addVariable(variable, false);
            return this;
        }
        
        private void addVariable(final StringBuffer variable, final boolean strict) {
            if (variable.length() != 0) {
                this.items.add(new VariableField(variable.toString(), strict));
                variable.setLength(0);
            }
        }
        
        @Deprecated
        public List<Object> getItems() {
            return this.items;
        }
        
        @Deprecated
        @Override
        public String toString() {
            return this.toString(0, this.items.size());
        }
        
        @Deprecated
        public String toString(final int start, final int limit) {
            final StringBuilder result = new StringBuilder();
            for (int i = start; i < limit; ++i) {
                final Object item = this.items.get(i);
                if (item instanceof String) {
                    final String itemString = (String)item;
                    result.append(this.tokenizer.quoteLiteral(itemString));
                }
                else {
                    result.append(this.items.get(i).toString());
                }
            }
            return result.toString();
        }
        
        @Deprecated
        public boolean hasDateAndTimeFields() {
            int foundMask = 0;
            for (final Object item : this.items) {
                if (item instanceof VariableField) {
                    final int type = ((VariableField)item).getType();
                    foundMask |= 1 << type;
                }
            }
            final boolean isDate = (foundMask & 0x3FF) != 0x0;
            final boolean isTime = (foundMask & 0xFC00) != 0x0;
            return isDate && isTime;
        }
        
        @Deprecated
        public Object quoteLiteral(final String string) {
            return this.tokenizer.quoteLiteral(string);
        }
        
        static {
            SYNTAX_CHARS = new UnicodeSet("[a-zA-Z]").freeze();
            QUOTING_CHARS = new UnicodeSet("[[[:script=Latn:][:script=Cyrl:]]&[[:L:][:M:]]]").freeze();
        }
    }
    
    private static class PatternWithMatcher
    {
        public String pattern;
        public DateTimeMatcher matcherWithSkeleton;
        
        public PatternWithMatcher(final String pat, final DateTimeMatcher matcher) {
            this.pattern = pat;
            this.matcherWithSkeleton = matcher;
        }
    }
    
    private static class PatternWithSkeletonFlag
    {
        public String pattern;
        public boolean skeletonWasSpecified;
        
        public PatternWithSkeletonFlag(final String pat, final boolean skelSpecified) {
            this.pattern = pat;
            this.skeletonWasSpecified = skelSpecified;
        }
        
        @Override
        public String toString() {
            return this.pattern + "," + this.skeletonWasSpecified;
        }
    }
    
    private enum DTPGflags
    {
        FIX_FRACTIONAL_SECONDS, 
        SKELETON_USES_CAP_J;
    }
    
    private static class SkeletonFields
    {
        private byte[] chars;
        private byte[] lengths;
        private static final byte DEFAULT_CHAR = 0;
        private static final byte DEFAULT_LENGTH = 0;
        
        private SkeletonFields() {
            this.chars = new byte[16];
            this.lengths = new byte[16];
        }
        
        public void clear() {
            Arrays.fill(this.chars, (byte)0);
            Arrays.fill(this.lengths, (byte)0);
        }
        
        void copyFieldFrom(final SkeletonFields other, final int field) {
            this.chars[field] = other.chars[field];
            this.lengths[field] = other.lengths[field];
        }
        
        void clearField(final int field) {
            this.chars[field] = 0;
            this.lengths[field] = 0;
        }
        
        char getFieldChar(final int field) {
            return (char)this.chars[field];
        }
        
        int getFieldLength(final int field) {
            return this.lengths[field];
        }
        
        void populate(final int field, final String value) {
            for (final char ch : value.toCharArray()) {
                assert ch == value.charAt(0);
            }
            this.populate(field, value.charAt(0), value.length());
        }
        
        void populate(final int field, final char ch, final int length) {
            assert ch <= '\u007f';
            assert length <= 127;
            this.chars[field] = (byte)ch;
            this.lengths[field] = (byte)length;
        }
        
        public boolean isFieldEmpty(final int field) {
            return this.lengths[field] == 0;
        }
        
        @Override
        public String toString() {
            return this.appendTo(new StringBuilder(), false, false).toString();
        }
        
        public String toString(final boolean skipDayPeriod) {
            return this.appendTo(new StringBuilder(), false, skipDayPeriod).toString();
        }
        
        public String toCanonicalString() {
            return this.appendTo(new StringBuilder(), true, false).toString();
        }
        
        public String toCanonicalString(final boolean skipDayPeriod) {
            return this.appendTo(new StringBuilder(), true, skipDayPeriod).toString();
        }
        
        public StringBuilder appendTo(final StringBuilder sb) {
            return this.appendTo(sb, false, false);
        }
        
        private StringBuilder appendTo(final StringBuilder sb, final boolean canonical, final boolean skipDayPeriod) {
            for (int i = 0; i < 16; ++i) {
                if (!skipDayPeriod || i != 10) {
                    this.appendFieldTo(i, sb, canonical);
                }
            }
            return sb;
        }
        
        public StringBuilder appendFieldTo(final int field, final StringBuilder sb) {
            return this.appendFieldTo(field, sb, false);
        }
        
        private StringBuilder appendFieldTo(final int field, final StringBuilder sb, final boolean canonical) {
            char ch = (char)this.chars[field];
            final int length = this.lengths[field];
            if (canonical) {
                ch = getCanonicalChar(field, ch);
            }
            for (int i = 0; i < length; ++i) {
                sb.append(ch);
            }
            return sb;
        }
        
        public int compareTo(final SkeletonFields other) {
            for (int i = 0; i < 16; ++i) {
                final int charDiff = this.chars[i] - other.chars[i];
                if (charDiff != 0) {
                    return charDiff;
                }
                final int lengthDiff = this.lengths[i] - other.lengths[i];
                if (lengthDiff != 0) {
                    return lengthDiff;
                }
            }
            return 0;
        }
        
        @Override
        public boolean equals(final Object other) {
            return this == other || (other != null && other instanceof SkeletonFields && this.compareTo((SkeletonFields)other) == 0);
        }
        
        @Override
        public int hashCode() {
            return Arrays.hashCode(this.chars) ^ Arrays.hashCode(this.lengths);
        }
    }
    
    private static class DateTimeMatcher implements Comparable<DateTimeMatcher>
    {
        private int[] type;
        private SkeletonFields original;
        private SkeletonFields baseOriginal;
        private boolean addedDefaultDayPeriod;
        
        private DateTimeMatcher() {
            this.type = new int[16];
            this.original = new SkeletonFields();
            this.baseOriginal = new SkeletonFields();
            this.addedDefaultDayPeriod = false;
        }
        
        public boolean fieldIsNumeric(final int field) {
            return this.type[field] > 0;
        }
        
        @Override
        public String toString() {
            return this.original.toString(this.addedDefaultDayPeriod);
        }
        
        public String toCanonicalString() {
            return this.original.toCanonicalString(this.addedDefaultDayPeriod);
        }
        
        String getBasePattern() {
            return this.baseOriginal.toString(this.addedDefaultDayPeriod);
        }
        
        DateTimeMatcher set(final String pattern, final FormatParser fp, final boolean allowDuplicateFields) {
            Arrays.fill(this.type, 0);
            this.original.clear();
            this.baseOriginal.clear();
            this.addedDefaultDayPeriod = false;
            fp.set(pattern);
            for (final Object obj : fp.getItems()) {
                if (!(obj instanceof VariableField)) {
                    continue;
                }
                final VariableField item = (VariableField)obj;
                final String value = item.toString();
                final int canonicalIndex = item.getCanonicalIndex();
                final int[] row = DateTimePatternGenerator.types[canonicalIndex];
                final int field = row[1];
                if (!this.original.isFieldEmpty(field)) {
                    final char ch1 = this.original.getFieldChar(field);
                    final char ch2 = value.charAt(0);
                    if (allowDuplicateFields || (ch1 == 'r' && ch2 == 'U')) {
                        continue;
                    }
                    if (ch1 == 'U' && ch2 == 'r') {
                        continue;
                    }
                    throw new IllegalArgumentException("Conflicting fields:\t" + ch1 + ", " + value + "\t in " + pattern);
                }
                else {
                    this.original.populate(field, value);
                    final char repeatChar = (char)row[0];
                    int repeatCount = row[3];
                    if ("GEzvQ".indexOf(repeatChar) >= 0) {
                        repeatCount = 1;
                    }
                    this.baseOriginal.populate(field, repeatChar, repeatCount);
                    int subField = row[2];
                    if (subField > 0) {
                        subField += value.length();
                    }
                    this.type[field] = subField;
                }
            }
            if (!this.original.isFieldEmpty(11)) {
                if (this.original.getFieldChar(11) == 'h' || this.original.getFieldChar(11) == 'K') {
                    if (this.original.isFieldEmpty(10)) {
                        for (int i = 0; i < DateTimePatternGenerator.types.length; ++i) {
                            final int[] row2 = DateTimePatternGenerator.types[i];
                            if (row2[1] == 10) {
                                this.original.populate(10, (char)row2[0], row2[3]);
                                this.baseOriginal.populate(10, (char)row2[0], row2[3]);
                                this.type[10] = row2[2];
                                this.addedDefaultDayPeriod = true;
                                break;
                            }
                        }
                    }
                }
                else if (!this.original.isFieldEmpty(10)) {
                    this.original.clearField(10);
                    this.baseOriginal.clearField(10);
                    this.type[10] = 0;
                }
            }
            return this;
        }
        
        int getFieldMask() {
            int result = 0;
            for (int i = 0; i < this.type.length; ++i) {
                if (this.type[i] != 0) {
                    result |= 1 << i;
                }
            }
            return result;
        }
        
        void extractFrom(final DateTimeMatcher source, final int fieldMask) {
            for (int i = 0; i < this.type.length; ++i) {
                if ((fieldMask & 1 << i) != 0x0) {
                    this.type[i] = source.type[i];
                    this.original.copyFieldFrom(source.original, i);
                }
                else {
                    this.type[i] = 0;
                    this.original.clearField(i);
                }
            }
        }
        
        int getDistance(final DateTimeMatcher other, final int includeMask, final DistanceInfo distanceInfo) {
            int result = 0;
            distanceInfo.clear();
            for (int i = 0; i < 16; ++i) {
                final int myType = ((includeMask & 1 << i) == 0x0) ? 0 : this.type[i];
                final int otherType = other.type[i];
                if (myType != otherType) {
                    if (myType == 0) {
                        result += 65536;
                        distanceInfo.addExtra(i);
                    }
                    else if (otherType == 0) {
                        result += 4096;
                        distanceInfo.addMissing(i);
                    }
                    else {
                        result += Math.abs(myType - otherType);
                    }
                }
            }
            return result;
        }
        
        @Override
        public int compareTo(final DateTimeMatcher that) {
            final int result = this.original.compareTo(that.original);
            return (result > 0) ? -1 : ((result < 0) ? 1 : 0);
        }
        
        @Override
        public boolean equals(final Object other) {
            return this == other || (other != null && other instanceof DateTimeMatcher && this.original.equals(((DateTimeMatcher)other).original));
        }
        
        @Override
        public int hashCode() {
            return this.original.hashCode();
        }
    }
    
    private static class DistanceInfo
    {
        int missingFieldMask;
        int extraFieldMask;
        
        void clear() {
            final int n = 0;
            this.extraFieldMask = n;
            this.missingFieldMask = n;
        }
        
        void setTo(final DistanceInfo other) {
            this.missingFieldMask = other.missingFieldMask;
            this.extraFieldMask = other.extraFieldMask;
        }
        
        void addMissing(final int field) {
            this.missingFieldMask |= 1 << field;
        }
        
        void addExtra(final int field) {
            this.extraFieldMask |= 1 << field;
        }
        
        @Override
        public String toString() {
            return "missingFieldMask: " + showMask(this.missingFieldMask) + ", extraFieldMask: " + showMask(this.extraFieldMask);
        }
    }
}
