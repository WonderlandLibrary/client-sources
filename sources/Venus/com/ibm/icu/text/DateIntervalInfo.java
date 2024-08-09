/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.text.DateIntervalFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DateIntervalInfo
implements Cloneable,
Freezable<DateIntervalInfo>,
Serializable {
    static final int currentSerialVersion = 1;
    static final String[] CALENDAR_FIELD_TO_PATTERN_LETTER = new String[]{"G", "y", "M", "w", "W", "d", "D", "E", "F", "a", "h", "H", "m", "s", "S", "z", " ", "Y", "e", "u", "g", "A", " ", " "};
    private static final long serialVersionUID = 1L;
    private static final int MINIMUM_SUPPORTED_CALENDAR_FIELD = 13;
    private static String CALENDAR_KEY = "calendar";
    private static String INTERVAL_FORMATS_KEY = "intervalFormats";
    private static String FALLBACK_STRING = "fallback";
    private static String LATEST_FIRST_PREFIX = "latestFirst:";
    private static String EARLIEST_FIRST_PREFIX = "earliestFirst:";
    private static final ICUCache<String, DateIntervalInfo> DIICACHE = new SimpleCache<String, DateIntervalInfo>();
    private String fFallbackIntervalPattern;
    private boolean fFirstDateInPtnIsLaterDate = false;
    private Map<String, Map<String, PatternInfo>> fIntervalPatterns = null;
    private volatile transient boolean frozen = false;
    private transient boolean fIntervalPatternsReadOnly = false;

    @Deprecated
    public DateIntervalInfo() {
        this.fIntervalPatterns = new HashMap<String, Map<String, PatternInfo>>();
        this.fFallbackIntervalPattern = "{0} \u2013 {1}";
    }

    public DateIntervalInfo(ULocale uLocale) {
        this.initializeData(uLocale);
    }

    public DateIntervalInfo(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    private void initializeData(ULocale uLocale) {
        String string = uLocale.toString();
        DateIntervalInfo dateIntervalInfo = DIICACHE.get(string);
        if (dateIntervalInfo == null) {
            this.setup(uLocale);
            this.fIntervalPatternsReadOnly = true;
            DIICACHE.put(string, ((DateIntervalInfo)this.clone()).freeze());
        } else {
            this.initializeFromReadOnlyPatterns(dateIntervalInfo);
        }
    }

    private void initializeFromReadOnlyPatterns(DateIntervalInfo dateIntervalInfo) {
        this.fFallbackIntervalPattern = dateIntervalInfo.fFallbackIntervalPattern;
        this.fFirstDateInPtnIsLaterDate = dateIntervalInfo.fFirstDateInPtnIsLaterDate;
        this.fIntervalPatterns = dateIntervalInfo.fIntervalPatterns;
        this.fIntervalPatternsReadOnly = true;
    }

    private void setup(ULocale uLocale) {
        int n = 19;
        this.fIntervalPatterns = new HashMap<String, Map<String, PatternInfo>>(n);
        this.fFallbackIntervalPattern = "{0} \u2013 {1}";
        try {
            Object object;
            String string = uLocale.getKeywordValue("calendar");
            if (string == null) {
                object = Calendar.getKeywordValuesForLocale("calendar", uLocale, true);
                string = object[0];
            }
            if (string == null) {
                string = "gregorian";
            }
            object = new DateIntervalSink(this);
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
            String string2 = iCUResourceBundle.getStringWithFallback(CALENDAR_KEY + "/" + string + "/" + INTERVAL_FORMATS_KEY + "/" + FALLBACK_STRING);
            this.setFallbackIntervalPattern(string2);
            HashSet<String> hashSet = new HashSet<String>();
            while (string != null) {
                if (hashSet.contains(string)) {
                    throw new ICUException("Loop in calendar type fallback: " + string);
                }
                hashSet.add(string);
                String string3 = CALENDAR_KEY + "/" + string;
                iCUResourceBundle.getAllItemsWithFallback(string3, (UResource.Sink)object);
                string = ((DateIntervalSink)object).getAndResetNextCalendarType();
            }
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
    }

    private static int splitPatternInto2Part(String string) {
        int n;
        boolean bl = false;
        char c = '\u0000';
        int n2 = 0;
        int[] nArray = new int[58];
        int n3 = 65;
        boolean bl2 = false;
        for (n = 0; n < string.length(); ++n) {
            char c2 = string.charAt(n);
            if (c2 != c && n2 > 0) {
                int n4 = nArray[c - n3];
                if (n4 != 0) {
                    bl2 = true;
                    break;
                }
                nArray[c - n3] = 1;
                n2 = 0;
            }
            if (c2 == '\'') {
                if (n + 1 < string.length() && string.charAt(n + 1) == '\'') {
                    ++n;
                    continue;
                }
                bl = !bl;
                continue;
            }
            if (bl || (c2 < 'a' || c2 > 'z') && (c2 < 'A' || c2 > 'Z')) continue;
            c = c2;
            ++n2;
        }
        if (n2 > 0 && !bl2 && nArray[c - n3] == 0) {
            n2 = 0;
        }
        return n - n2;
    }

    public void setIntervalPattern(String string, int n, String string2) {
        if (this.frozen) {
            throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
        }
        if (n > 13) {
            throw new IllegalArgumentException("calendar field is larger than MINIMUM_SUPPORTED_CALENDAR_FIELD");
        }
        if (this.fIntervalPatternsReadOnly) {
            this.fIntervalPatterns = DateIntervalInfo.cloneIntervalPatterns(this.fIntervalPatterns);
            this.fIntervalPatternsReadOnly = false;
        }
        PatternInfo patternInfo = this.setIntervalPatternInternally(string, CALENDAR_FIELD_TO_PATTERN_LETTER[n], string2);
        if (n == 11) {
            this.setIntervalPattern(string, CALENDAR_FIELD_TO_PATTERN_LETTER[9], patternInfo);
            this.setIntervalPattern(string, CALENDAR_FIELD_TO_PATTERN_LETTER[10], patternInfo);
        } else if (n == 5 || n == 7) {
            this.setIntervalPattern(string, CALENDAR_FIELD_TO_PATTERN_LETTER[5], patternInfo);
        }
    }

    private PatternInfo setIntervalPatternInternally(String string, String string2, String string3) {
        int n;
        Map<String, PatternInfo> map = this.fIntervalPatterns.get(string);
        boolean bl = false;
        if (map == null) {
            map = new HashMap<String, PatternInfo>();
            bl = true;
        }
        boolean bl2 = this.fFirstDateInPtnIsLaterDate;
        if (string3.startsWith(LATEST_FIRST_PREFIX)) {
            bl2 = true;
            n = LATEST_FIRST_PREFIX.length();
            string3 = string3.substring(n, string3.length());
        } else if (string3.startsWith(EARLIEST_FIRST_PREFIX)) {
            bl2 = false;
            n = EARLIEST_FIRST_PREFIX.length();
            string3 = string3.substring(n, string3.length());
        }
        PatternInfo patternInfo = DateIntervalInfo.genPatternInfo(string3, bl2);
        map.put(string2, patternInfo);
        if (bl) {
            this.fIntervalPatterns.put(string, map);
        }
        return patternInfo;
    }

    private void setIntervalPattern(String string, String string2, PatternInfo patternInfo) {
        Map<String, PatternInfo> map = this.fIntervalPatterns.get(string);
        map.put(string2, patternInfo);
    }

    @Deprecated
    public static PatternInfo genPatternInfo(String string, boolean bl) {
        int n = DateIntervalInfo.splitPatternInto2Part(string);
        String string2 = string.substring(0, n);
        String string3 = null;
        if (n < string.length()) {
            string3 = string.substring(n, string.length());
        }
        return new PatternInfo(string2, string3, bl);
    }

    public PatternInfo getIntervalPattern(String string, int n) {
        PatternInfo patternInfo;
        if (n > 13) {
            throw new IllegalArgumentException("no support for field less than SECOND");
        }
        Map<String, PatternInfo> map = this.fIntervalPatterns.get(string);
        if (map != null && (patternInfo = map.get(CALENDAR_FIELD_TO_PATTERN_LETTER[n])) != null) {
            return patternInfo;
        }
        return null;
    }

    public String getFallbackIntervalPattern() {
        return this.fFallbackIntervalPattern;
    }

    public void setFallbackIntervalPattern(String string) {
        if (this.frozen) {
            throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
        }
        int n = string.indexOf("{0}");
        int n2 = string.indexOf("{1}");
        if (n == -1 || n2 == -1) {
            throw new IllegalArgumentException("no pattern {0} or pattern {1} in fallbackPattern");
        }
        if (n > n2) {
            this.fFirstDateInPtnIsLaterDate = true;
        }
        this.fFallbackIntervalPattern = string;
    }

    public boolean getDefaultOrder() {
        return this.fFirstDateInPtnIsLaterDate;
    }

    public Object clone() {
        if (this.frozen) {
            return this;
        }
        return this.cloneUnfrozenDII();
    }

    private Object cloneUnfrozenDII() {
        try {
            DateIntervalInfo dateIntervalInfo = (DateIntervalInfo)super.clone();
            dateIntervalInfo.fFallbackIntervalPattern = this.fFallbackIntervalPattern;
            dateIntervalInfo.fFirstDateInPtnIsLaterDate = this.fFirstDateInPtnIsLaterDate;
            if (this.fIntervalPatternsReadOnly) {
                dateIntervalInfo.fIntervalPatterns = this.fIntervalPatterns;
                dateIntervalInfo.fIntervalPatternsReadOnly = true;
            } else {
                dateIntervalInfo.fIntervalPatterns = DateIntervalInfo.cloneIntervalPatterns(this.fIntervalPatterns);
                dateIntervalInfo.fIntervalPatternsReadOnly = false;
            }
            dateIntervalInfo.frozen = false;
            return dateIntervalInfo;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException("clone is not supported", cloneNotSupportedException);
        }
    }

    private static Map<String, Map<String, PatternInfo>> cloneIntervalPatterns(Map<String, Map<String, PatternInfo>> map) {
        HashMap<String, Map<String, PatternInfo>> hashMap = new HashMap<String, Map<String, PatternInfo>>();
        for (Map.Entry<String, Map<String, PatternInfo>> entry : map.entrySet()) {
            String string = entry.getKey();
            Map<String, PatternInfo> map2 = entry.getValue();
            HashMap<String, PatternInfo> hashMap2 = new HashMap<String, PatternInfo>();
            for (Map.Entry<String, PatternInfo> entry2 : map2.entrySet()) {
                String string2 = entry2.getKey();
                PatternInfo patternInfo = entry2.getValue();
                hashMap2.put(string2, patternInfo);
            }
            hashMap.put(string, hashMap2);
        }
        return hashMap;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override
    public DateIntervalInfo freeze() {
        this.fIntervalPatternsReadOnly = true;
        this.frozen = true;
        return this;
    }

    @Override
    public DateIntervalInfo cloneAsThawed() {
        DateIntervalInfo dateIntervalInfo = (DateIntervalInfo)this.cloneUnfrozenDII();
        return dateIntervalInfo;
    }

    static void parseSkeleton(String string, int[] nArray) {
        int n = 65;
        for (int i = 0; i < string.length(); ++i) {
            int n2 = string.charAt(i) - n;
            nArray[n2] = nArray[n2] + 1;
        }
    }

    private static boolean stringNumeric(int n, int n2, char c) {
        return c != 'M' || (n > 2 || n2 <= 2) && (n <= 2 || n2 > 2);
    }

    DateIntervalFormat.BestMatchInfo getBestSkeleton(String string) {
        String string2 = string;
        int[] nArray = new int[58];
        int[] nArray2 = new int[58];
        int n = 4096;
        int n2 = 256;
        int n3 = 65;
        boolean bl = false;
        if (string.indexOf(122) != -1) {
            string = string.replace('z', 'v');
            bl = true;
        }
        DateIntervalInfo.parseSkeleton(string, nArray);
        int n4 = Integer.MAX_VALUE;
        int n5 = 0;
        for (String string3 : this.fIntervalPatterns.keySet()) {
            int n6;
            for (n6 = 0; n6 < nArray2.length; ++n6) {
                nArray2[n6] = 0;
            }
            DateIntervalInfo.parseSkeleton(string3, nArray2);
            n6 = 0;
            int n7 = 1;
            for (int i = 0; i < nArray.length; ++i) {
                int n8 = nArray[i];
                int n9 = nArray2[i];
                if (n8 == n9) continue;
                if (n8 == 0) {
                    n7 = -1;
                    n6 += 4096;
                    continue;
                }
                if (n9 == 0) {
                    n7 = -1;
                    n6 += 4096;
                    continue;
                }
                if (DateIntervalInfo.stringNumeric(n8, n9, (char)(i + 65))) {
                    n6 += 256;
                    continue;
                }
                n6 += Math.abs(n8 - n9);
            }
            if (n6 < n4) {
                string2 = string3;
                n4 = n6;
                n5 = n7;
            }
            if (n6 != 0) continue;
            n5 = 0;
            break;
        }
        if (bl && n5 != -1) {
            n5 = 2;
        }
        return new DateIntervalFormat.BestMatchInfo(string2, n5);
    }

    public boolean equals(Object object) {
        if (object instanceof DateIntervalInfo) {
            DateIntervalInfo dateIntervalInfo = (DateIntervalInfo)object;
            return this.fIntervalPatterns.equals(dateIntervalInfo.fIntervalPatterns);
        }
        return true;
    }

    public int hashCode() {
        return this.fIntervalPatterns.hashCode();
    }

    @Deprecated
    public Map<String, Set<String>> getPatterns() {
        LinkedHashMap<String, Set<String>> linkedHashMap = new LinkedHashMap<String, Set<String>>();
        for (Map.Entry<String, Map<String, PatternInfo>> entry : this.fIntervalPatterns.entrySet()) {
            linkedHashMap.put(entry.getKey(), new LinkedHashSet<String>(entry.getValue().keySet()));
        }
        return linkedHashMap;
    }

    @Deprecated
    public Map<String, Map<String, PatternInfo>> getRawPatterns() {
        LinkedHashMap<String, Map<String, PatternInfo>> linkedHashMap = new LinkedHashMap<String, Map<String, PatternInfo>>();
        for (Map.Entry<String, Map<String, PatternInfo>> entry : this.fIntervalPatterns.entrySet()) {
            linkedHashMap.put(entry.getKey(), new LinkedHashMap<String, PatternInfo>(entry.getValue()));
        }
        return linkedHashMap;
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    static String access$000() {
        return INTERVAL_FORMATS_KEY;
    }

    static String access$100() {
        return CALENDAR_KEY;
    }

    static Map access$200(DateIntervalInfo dateIntervalInfo) {
        return dateIntervalInfo.fIntervalPatterns;
    }

    static PatternInfo access$300(DateIntervalInfo dateIntervalInfo, String string, String string2, String string3) {
        return dateIntervalInfo.setIntervalPatternInternally(string, string2, string3);
    }

    private static final class DateIntervalSink
    extends UResource.Sink {
        private static final String ACCEPTED_PATTERN_LETTERS = "GyMdahHms";
        DateIntervalInfo dateIntervalInfo;
        String nextCalendarType;
        private static final String DATE_INTERVAL_PATH_PREFIX = "/LOCALE/" + DateIntervalInfo.access$100() + "/";
        private static final String DATE_INTERVAL_PATH_SUFFIX = "/" + DateIntervalInfo.access$000();

        public DateIntervalSink(DateIntervalInfo dateIntervalInfo) {
            this.dateIntervalInfo = dateIntervalInfo;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (key.contentEquals(DateIntervalInfo.access$000())) {
                    if (value.getType() == 3) {
                        this.nextCalendarType = this.getCalendarTypeFromPath(value.getAliasString());
                        break;
                    }
                    if (value.getType() == 2) {
                        UResource.Table table2 = value.getTable();
                        int n2 = 0;
                        while (table2.getKeyAndValue(n2, key, value)) {
                            if (value.getType() == 2) {
                                this.processSkeletonTable(key, value);
                            }
                            ++n2;
                        }
                        break;
                    }
                }
                ++n;
            }
        }

        public void processSkeletonTable(UResource.Key key, UResource.Value value) {
            String string = key.toString();
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                CharSequence charSequence;
                if (value.getType() == 0 && (charSequence = this.validateAndProcessPatternLetter(key)) != null) {
                    String string2 = charSequence.toString();
                    this.setIntervalPatternIfAbsent(string, string2, value);
                }
                ++n;
            }
        }

        public String getAndResetNextCalendarType() {
            String string = this.nextCalendarType;
            this.nextCalendarType = null;
            return string;
        }

        private String getCalendarTypeFromPath(String string) {
            if (string.startsWith(DATE_INTERVAL_PATH_PREFIX) && string.endsWith(DATE_INTERVAL_PATH_SUFFIX)) {
                return string.substring(DATE_INTERVAL_PATH_PREFIX.length(), string.length() - DATE_INTERVAL_PATH_SUFFIX.length());
            }
            throw new ICUException("Malformed 'intervalFormat' alias path: " + string);
        }

        private CharSequence validateAndProcessPatternLetter(CharSequence charSequence) {
            if (charSequence.length() != 1) {
                return null;
            }
            char c = charSequence.charAt(0);
            if (ACCEPTED_PATTERN_LETTERS.indexOf(c) < 0) {
                return null;
            }
            if (c == CALENDAR_FIELD_TO_PATTERN_LETTER[11].charAt(0)) {
                charSequence = CALENDAR_FIELD_TO_PATTERN_LETTER[10];
            }
            return charSequence;
        }

        private void setIntervalPatternIfAbsent(String string, String string2, UResource.Value value) {
            Map map = (Map)DateIntervalInfo.access$200(this.dateIntervalInfo).get(string);
            if (map == null || !map.containsKey(string2)) {
                DateIntervalInfo.access$300(this.dateIntervalInfo, string, string2, value.toString());
            }
        }
    }

    public static final class PatternInfo
    implements Cloneable,
    Serializable {
        static final int currentSerialVersion = 1;
        private static final long serialVersionUID = 1L;
        private final String fIntervalPatternFirstPart;
        private final String fIntervalPatternSecondPart;
        private final boolean fFirstDateInPtnIsLaterDate;

        public PatternInfo(String string, String string2, boolean bl) {
            this.fIntervalPatternFirstPart = string;
            this.fIntervalPatternSecondPart = string2;
            this.fFirstDateInPtnIsLaterDate = bl;
        }

        public String getFirstPart() {
            return this.fIntervalPatternFirstPart;
        }

        public String getSecondPart() {
            return this.fIntervalPatternSecondPart;
        }

        public boolean firstDateInPtnIsLaterDate() {
            return this.fFirstDateInPtnIsLaterDate;
        }

        public boolean equals(Object object) {
            if (object instanceof PatternInfo) {
                PatternInfo patternInfo = (PatternInfo)object;
                return Objects.equals(this.fIntervalPatternFirstPart, patternInfo.fIntervalPatternFirstPart) && Objects.equals(this.fIntervalPatternSecondPart, patternInfo.fIntervalPatternSecondPart) && this.fFirstDateInPtnIsLaterDate == patternInfo.fFirstDateInPtnIsLaterDate;
            }
            return true;
        }

        public int hashCode() {
            int n;
            int n2 = n = this.fIntervalPatternFirstPart != null ? this.fIntervalPatternFirstPart.hashCode() : 0;
            if (this.fIntervalPatternSecondPart != null) {
                n ^= this.fIntervalPatternSecondPart.hashCode();
            }
            if (this.fFirstDateInPtnIsLaterDate) {
                n ^= 0xFFFFFFFF;
            }
            return n;
        }

        public String toString() {
            return "{first=\u00ab" + this.fIntervalPatternFirstPart + "\u00bb, second=\u00ab" + this.fIntervalPatternSecondPart + "\u00bb, reversed:" + this.fFirstDateInPtnIsLaterDate + "}";
        }
    }
}

