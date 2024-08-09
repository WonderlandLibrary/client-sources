/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.impl.ZoneMeta;
import com.ibm.icu.text.LocaleDisplayNames;
import com.ibm.icu.text.TimeZoneFormat;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneTransition;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TimeZoneGenericNames
implements Serializable,
Freezable<TimeZoneGenericNames> {
    private static final long serialVersionUID = 2729910342063468417L;
    private final ULocale _locale;
    private TimeZoneNames _tznames;
    private volatile transient boolean _frozen;
    private transient String _region;
    private transient WeakReference<LocaleDisplayNames> _localeDisplayNamesRef;
    private transient MessageFormat[] _patternFormatters;
    private transient ConcurrentHashMap<String, String> _genericLocationNamesMap;
    private transient ConcurrentHashMap<String, String> _genericPartialLocationNamesMap;
    private transient TextTrieMap<NameInfo> _gnamesTrie;
    private transient boolean _gnamesTrieFullyLoaded;
    private static Cache GENERIC_NAMES_CACHE;
    private static final long DST_CHECK_RANGE = 15897600000L;
    private static final TimeZoneNames.NameType[] GENERIC_NON_LOCATION_TYPES;
    static final boolean $assertionsDisabled;

    public TimeZoneGenericNames(ULocale uLocale, TimeZoneNames timeZoneNames) {
        this._locale = uLocale;
        this._tznames = timeZoneNames;
        this.init();
    }

    private void init() {
        if (this._tznames == null) {
            this._tznames = TimeZoneNames.getInstance(this._locale);
        }
        this._genericLocationNamesMap = new ConcurrentHashMap();
        this._genericPartialLocationNamesMap = new ConcurrentHashMap();
        this._gnamesTrie = new TextTrieMap(true);
        this._gnamesTrieFullyLoaded = false;
        TimeZone timeZone = TimeZone.getDefault();
        String string = ZoneMeta.getCanonicalCLDRID(timeZone);
        if (string != null) {
            this.loadStrings(string);
        }
    }

    private TimeZoneGenericNames(ULocale uLocale) {
        this(uLocale, (TimeZoneNames)null);
    }

    public static TimeZoneGenericNames getInstance(ULocale uLocale) {
        String string = uLocale.getBaseName();
        return (TimeZoneGenericNames)GENERIC_NAMES_CACHE.getInstance(string, uLocale);
    }

    public String getDisplayName(TimeZone timeZone, GenericNameType genericNameType, long l) {
        String string = null;
        String string2 = null;
        switch (genericNameType) {
            case LOCATION: {
                string2 = ZoneMeta.getCanonicalCLDRID(timeZone);
                if (string2 == null) break;
                string = this.getGenericLocationName(string2);
                break;
            }
            case LONG: 
            case SHORT: {
                string = this.formatGenericNonLocationName(timeZone, genericNameType, l);
                if (string != null || (string2 = ZoneMeta.getCanonicalCLDRID(timeZone)) == null) break;
                string = this.getGenericLocationName(string2);
            }
        }
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getGenericLocationName(String string) {
        Object object;
        if (string == null || string.length() == 0) {
            return null;
        }
        String string2 = this._genericLocationNamesMap.get(string);
        if (string2 != null) {
            if (string2.length() == 0) {
                return null;
            }
            return string2;
        }
        Output<Boolean> output = new Output<Boolean>();
        String string3 = ZoneMeta.getCanonicalCountry(string, output);
        if (string3 != null) {
            if (((Boolean)output.value).booleanValue()) {
                object = this.getLocaleDisplayNames().regionDisplayName(string3);
                string2 = this.formatPattern(Pattern.REGION_FORMAT, new String[]{object});
            } else {
                object = this._tznames.getExemplarLocationName(string);
                string2 = this.formatPattern(Pattern.REGION_FORMAT, new String[]{object});
            }
        }
        if (string2 == null) {
            this._genericLocationNamesMap.putIfAbsent(string.intern(), "");
        } else {
            object = this;
            synchronized (object) {
                string = string.intern();
                String string4 = this._genericLocationNamesMap.putIfAbsent(string, string2.intern());
                if (string4 == null) {
                    NameInfo nameInfo = new NameInfo(string, GenericNameType.LOCATION);
                    this._gnamesTrie.put(string2, nameInfo);
                } else {
                    string2 = string4;
                }
            }
        }
        return string2;
    }

    public TimeZoneGenericNames setFormatPattern(Pattern pattern, String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (!this._genericLocationNamesMap.isEmpty()) {
            this._genericLocationNamesMap = new ConcurrentHashMap();
        }
        if (!this._genericPartialLocationNamesMap.isEmpty()) {
            this._genericPartialLocationNamesMap = new ConcurrentHashMap();
        }
        this._gnamesTrie = null;
        this._gnamesTrieFullyLoaded = false;
        if (this._patternFormatters == null) {
            this._patternFormatters = new MessageFormat[Pattern.values().length];
        }
        this._patternFormatters[pattern.ordinal()] = new MessageFormat(string);
        return this;
    }

    private String formatGenericNonLocationName(TimeZone timeZone, GenericNameType genericNameType, long l) {
        if (!$assertionsDisabled && genericNameType != GenericNameType.LONG && genericNameType != GenericNameType.SHORT) {
            throw new AssertionError();
        }
        String string = ZoneMeta.getCanonicalCLDRID(timeZone);
        if (string == null) {
            return null;
        }
        TimeZoneNames.NameType nameType = genericNameType == GenericNameType.LONG ? TimeZoneNames.NameType.LONG_GENERIC : TimeZoneNames.NameType.SHORT_GENERIC;
        Object object = this._tznames.getTimeZoneDisplayName(string, nameType);
        if (object != null) {
            return object;
        }
        String string2 = this._tznames.getMetaZoneID(string, l);
        if (string2 != null) {
            Object object2;
            Object object3;
            Object object4;
            boolean bl = false;
            int[] nArray = new int[]{0, 0};
            timeZone.getOffset(l, false, nArray);
            if (nArray[1] == 0) {
                bl = true;
                if (timeZone instanceof BasicTimeZone) {
                    object4 = (BasicTimeZone)timeZone;
                    object3 = ((BasicTimeZone)object4).getPreviousTransition(l, false);
                    if (object3 != null && l - ((TimeZoneTransition)object3).getTime() < 15897600000L && ((TimeZoneTransition)object3).getFrom().getDSTSavings() != 0) {
                        bl = false;
                    } else {
                        object2 = ((BasicTimeZone)object4).getNextTransition(l, true);
                        if (object2 != null && ((TimeZoneTransition)object2).getTime() - l < 15897600000L && ((TimeZoneTransition)object2).getTo().getDSTSavings() != 0) {
                            bl = false;
                        }
                    }
                } else {
                    object4 = new int[2];
                    timeZone.getOffset(l - 15897600000L, false, (int[])object4);
                    if (object4[1] != false) {
                        bl = false;
                    } else {
                        timeZone.getOffset(l + 15897600000L, false, (int[])object4);
                        if (object4[1] != false) {
                            bl = false;
                        }
                    }
                }
            }
            if (bl && (object3 = this._tznames.getDisplayName(string, (TimeZoneNames.NameType)((Object)(object4 = nameType == TimeZoneNames.NameType.LONG_GENERIC ? TimeZoneNames.NameType.LONG_STANDARD : TimeZoneNames.NameType.SHORT_STANDARD)), l)) != null) {
                object = object3;
                object2 = this._tznames.getMetaZoneDisplayName(string2, nameType);
                if (((String)object3).equalsIgnoreCase((String)object2)) {
                    object = null;
                }
            }
            if (object == null && (object4 = this._tznames.getMetaZoneDisplayName(string2, nameType)) != null) {
                object3 = this._tznames.getReferenceZoneID(string2, this.getTargetRegion());
                if (object3 != null && !((String)object3).equals(string)) {
                    object2 = TimeZone.getFrozenTimeZone((String)object3);
                    int[] nArray2 = new int[]{0, 0};
                    ((TimeZone)object2).getOffset(l + (long)nArray[0] + (long)nArray[1], true, nArray2);
                    object = nArray[0] != nArray2[0] || nArray[1] != nArray2[1] ? this.getPartialLocationName(string, string2, nameType == TimeZoneNames.NameType.LONG_GENERIC, (String)object4) : object4;
                } else {
                    object = object4;
                }
            }
        }
        return object;
    }

    private synchronized String formatPattern(Pattern pattern, String ... stringArray) {
        int n;
        if (this._patternFormatters == null) {
            this._patternFormatters = new MessageFormat[Pattern.values().length];
        }
        if (this._patternFormatters[n = pattern.ordinal()] == null) {
            String string;
            try {
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/zone", this._locale);
                string = iCUResourceBundle.getStringWithFallback("zoneStrings/" + pattern.key());
            } catch (MissingResourceException missingResourceException) {
                string = pattern.defaultValue();
            }
            this._patternFormatters[n] = new MessageFormat(string);
        }
        return this._patternFormatters[n].format(stringArray);
    }

    private synchronized LocaleDisplayNames getLocaleDisplayNames() {
        LocaleDisplayNames localeDisplayNames = null;
        if (this._localeDisplayNamesRef != null) {
            localeDisplayNames = (LocaleDisplayNames)this._localeDisplayNamesRef.get();
        }
        if (localeDisplayNames == null) {
            localeDisplayNames = LocaleDisplayNames.getInstance(this._locale);
            this._localeDisplayNamesRef = new WeakReference<LocaleDisplayNames>(localeDisplayNames);
        }
        return localeDisplayNames;
    }

    private synchronized void loadStrings(String string) {
        if (string == null || string.length() == 0) {
            return;
        }
        this.getGenericLocationName(string);
        Set<String> set = this._tznames.getAvailableMetaZoneIDs(string);
        for (String string2 : set) {
            String string3 = this._tznames.getReferenceZoneID(string2, this.getTargetRegion());
            if (string.equals(string3)) continue;
            for (TimeZoneNames.NameType nameType : GENERIC_NON_LOCATION_TYPES) {
                String string4 = this._tznames.getMetaZoneDisplayName(string2, nameType);
                if (string4 == null) continue;
                this.getPartialLocationName(string, string2, nameType == TimeZoneNames.NameType.LONG_GENERIC, string4);
            }
        }
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getPartialLocationName(String string, String string2, boolean bl, String string3) {
        Object object;
        String string4 = bl ? "L" : "S";
        String string5 = string + "&" + string2 + "#" + string4;
        String string6 = this._genericPartialLocationNamesMap.get(string5);
        if (string6 != null) {
            return string6;
        }
        String string7 = null;
        String string8 = ZoneMeta.getCanonicalCountry(string);
        if (string8 != null) {
            object = this._tznames.getReferenceZoneID(string2, string8);
            string7 = string.equals(object) ? this.getLocaleDisplayNames().regionDisplayName(string8) : this._tznames.getExemplarLocationName(string);
        } else {
            string7 = this._tznames.getExemplarLocationName(string);
            if (string7 == null) {
                string7 = string;
            }
        }
        string6 = this.formatPattern(Pattern.FALLBACK_FORMAT, string7, string3);
        object = this;
        synchronized (object) {
            String string9 = this._genericPartialLocationNamesMap.putIfAbsent(string5.intern(), string6.intern());
            if (string9 == null) {
                NameInfo nameInfo = new NameInfo(string.intern(), bl ? GenericNameType.LONG : GenericNameType.SHORT);
                this._gnamesTrie.put(string6, nameInfo);
            } else {
                string6 = string9;
            }
        }
        return string6;
    }

    public GenericMatchInfo findBestMatch(String string, int n, EnumSet<GenericNameType> enumSet) {
        Object object;
        if (string == null || string.length() == 0 || n < 0 || n >= string.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        GenericMatchInfo object2 = null;
        Collection<TimeZoneNames.MatchInfo> collection = this.findTimeZoneNames(string, n, enumSet);
        if (collection != null) {
            object = null;
            for (TimeZoneNames.MatchInfo object3 : collection) {
                if (object != null && object3.matchLength() <= ((TimeZoneNames.MatchInfo)object).matchLength()) continue;
                object = object3;
            }
            if (object != null && (object2 = this.createGenericMatchInfo((TimeZoneNames.MatchInfo)object)).matchLength() == string.length() - n && object2.timeType != TimeZoneFormat.TimeType.STANDARD) {
                return object2;
            }
        }
        if ((object = this.findLocal(string, n, enumSet)) != null) {
            Iterator<Object> iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                GenericMatchInfo genericMatchInfo = (GenericMatchInfo)iterator2.next();
                if (object2 != null && genericMatchInfo.matchLength() < object2.matchLength()) continue;
                object2 = genericMatchInfo;
            }
        }
        return object2;
    }

    public Collection<GenericMatchInfo> find(String string, int n, EnumSet<GenericNameType> enumSet) {
        if (string == null || string.length() == 0 || n < 0 || n >= string.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        Collection<GenericMatchInfo> collection = this.findLocal(string, n, enumSet);
        Collection<TimeZoneNames.MatchInfo> collection2 = this.findTimeZoneNames(string, n, enumSet);
        if (collection2 != null) {
            for (TimeZoneNames.MatchInfo matchInfo : collection2) {
                if (collection == null) {
                    collection = new LinkedList<GenericMatchInfo>();
                }
                collection.add(this.createGenericMatchInfo(matchInfo));
            }
        }
        return collection;
    }

    private GenericMatchInfo createGenericMatchInfo(TimeZoneNames.MatchInfo matchInfo) {
        Object object;
        GenericNameType genericNameType = null;
        TimeZoneFormat.TimeType timeType = TimeZoneFormat.TimeType.UNKNOWN;
        switch (matchInfo.nameType()) {
            case LONG_STANDARD: {
                genericNameType = GenericNameType.LONG;
                timeType = TimeZoneFormat.TimeType.STANDARD;
                break;
            }
            case LONG_GENERIC: {
                genericNameType = GenericNameType.LONG;
                break;
            }
            case SHORT_STANDARD: {
                genericNameType = GenericNameType.SHORT;
                timeType = TimeZoneFormat.TimeType.STANDARD;
                break;
            }
            case SHORT_GENERIC: {
                genericNameType = GenericNameType.SHORT;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unexpected MatchInfo name type - " + (Object)((Object)matchInfo.nameType()));
            }
        }
        String string = matchInfo.tzID();
        if (string == null) {
            object = matchInfo.mzID();
            if (!$assertionsDisabled && object == null) {
                throw new AssertionError();
            }
            string = this._tznames.getReferenceZoneID((String)object, this.getTargetRegion());
        }
        if (!$assertionsDisabled && string == null) {
            throw new AssertionError();
        }
        object = new GenericMatchInfo(genericNameType, string, matchInfo.matchLength(), timeType, null);
        return object;
    }

    private Collection<TimeZoneNames.MatchInfo> findTimeZoneNames(String string, int n, EnumSet<GenericNameType> enumSet) {
        Collection<TimeZoneNames.MatchInfo> collection = null;
        EnumSet<TimeZoneNames.NameType> enumSet2 = EnumSet.noneOf(TimeZoneNames.NameType.class);
        if (enumSet.contains((Object)GenericNameType.LONG)) {
            enumSet2.add(TimeZoneNames.NameType.LONG_GENERIC);
            enumSet2.add(TimeZoneNames.NameType.LONG_STANDARD);
        }
        if (enumSet.contains((Object)GenericNameType.SHORT)) {
            enumSet2.add(TimeZoneNames.NameType.SHORT_GENERIC);
            enumSet2.add(TimeZoneNames.NameType.SHORT_STANDARD);
        }
        if (!enumSet2.isEmpty()) {
            collection = this._tznames.find(string, n, enumSet2);
        }
        return collection;
    }

    private synchronized Collection<GenericMatchInfo> findLocal(String string, int n, EnumSet<GenericNameType> enumSet) {
        GenericNameSearchHandler genericNameSearchHandler = new GenericNameSearchHandler(enumSet);
        this._gnamesTrie.find(string, n, genericNameSearchHandler);
        if (genericNameSearchHandler.getMaxMatchLen() == string.length() - n || this._gnamesTrieFullyLoaded) {
            return genericNameSearchHandler.getMatches();
        }
        Set<String> set = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null);
        for (String string2 : set) {
            this.loadStrings(string2);
        }
        this._gnamesTrieFullyLoaded = true;
        genericNameSearchHandler.resetResults();
        this._gnamesTrie.find(string, n, genericNameSearchHandler);
        return genericNameSearchHandler.getMatches();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init();
    }

    @Override
    public boolean isFrozen() {
        return this._frozen;
    }

    @Override
    public TimeZoneGenericNames freeze() {
        this._frozen = true;
        return this;
    }

    @Override
    public TimeZoneGenericNames cloneAsThawed() {
        TimeZoneGenericNames timeZoneGenericNames = null;
        try {
            timeZoneGenericNames = (TimeZoneGenericNames)super.clone();
            timeZoneGenericNames._frozen = false;
        } catch (Throwable throwable) {
            // empty catch block
        }
        return timeZoneGenericNames;
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    TimeZoneGenericNames(ULocale uLocale, 1 var2_2) {
        this(uLocale);
    }

    static {
        $assertionsDisabled = !TimeZoneGenericNames.class.desiredAssertionStatus();
        GENERIC_NAMES_CACHE = new Cache(null);
        GENERIC_NON_LOCATION_TYPES = new TimeZoneNames.NameType[]{TimeZoneNames.NameType.LONG_GENERIC, TimeZoneNames.NameType.SHORT_GENERIC};
    }

    private static class Cache
    extends SoftCache<String, TimeZoneGenericNames, ULocale> {
        private Cache() {
        }

        @Override
        protected TimeZoneGenericNames createInstance(String string, ULocale uLocale) {
            return new TimeZoneGenericNames(uLocale, null).freeze();
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (ULocale)object2);
        }

        Cache(1 var1_1) {
            this();
        }
    }

    private static class GenericNameSearchHandler
    implements TextTrieMap.ResultHandler<NameInfo> {
        private EnumSet<GenericNameType> _types;
        private Collection<GenericMatchInfo> _matches;
        private int _maxMatchLen;

        GenericNameSearchHandler(EnumSet<GenericNameType> enumSet) {
            this._types = enumSet;
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<NameInfo> iterator2) {
            while (iterator2.hasNext()) {
                NameInfo nameInfo = iterator2.next();
                if (this._types != null && !this._types.contains((Object)nameInfo.type)) continue;
                GenericMatchInfo genericMatchInfo = new GenericMatchInfo(nameInfo.type, nameInfo.tzID, n, null);
                if (this._matches == null) {
                    this._matches = new LinkedList<GenericMatchInfo>();
                }
                this._matches.add(genericMatchInfo);
                if (n <= this._maxMatchLen) continue;
                this._maxMatchLen = n;
            }
            return false;
        }

        public Collection<GenericMatchInfo> getMatches() {
            return this._matches;
        }

        public int getMaxMatchLen() {
            return this._maxMatchLen;
        }

        public void resetResults() {
            this._matches = null;
            this._maxMatchLen = 0;
        }
    }

    public static class GenericMatchInfo {
        final GenericNameType nameType;
        final String tzID;
        final int matchLength;
        final TimeZoneFormat.TimeType timeType;

        private GenericMatchInfo(GenericNameType genericNameType, String string, int n) {
            this(genericNameType, string, n, TimeZoneFormat.TimeType.UNKNOWN);
        }

        private GenericMatchInfo(GenericNameType genericNameType, String string, int n, TimeZoneFormat.TimeType timeType) {
            this.nameType = genericNameType;
            this.tzID = string;
            this.matchLength = n;
            this.timeType = timeType;
        }

        public GenericNameType nameType() {
            return this.nameType;
        }

        public String tzID() {
            return this.tzID;
        }

        public TimeZoneFormat.TimeType timeType() {
            return this.timeType;
        }

        public int matchLength() {
            return this.matchLength;
        }

        GenericMatchInfo(GenericNameType genericNameType, String string, int n, 1 var4_4) {
            this(genericNameType, string, n);
        }

        GenericMatchInfo(GenericNameType genericNameType, String string, int n, TimeZoneFormat.TimeType timeType, 1 var5_5) {
            this(genericNameType, string, n, timeType);
        }
    }

    private static class NameInfo {
        final String tzID;
        final GenericNameType type;

        NameInfo(String string, GenericNameType genericNameType) {
            this.tzID = string;
            this.type = genericNameType;
        }
    }

    public static enum Pattern {
        REGION_FORMAT("regionFormat", "({0})"),
        FALLBACK_FORMAT("fallbackFormat", "{1} ({0})");

        String _key;
        String _defaultVal;

        private Pattern(String string2, String string3) {
            this._key = string2;
            this._defaultVal = string3;
        }

        String key() {
            return this._key;
        }

        String defaultValue() {
            return this._defaultVal;
        }
    }

    public static enum GenericNameType {
        LOCATION("LONG", "SHORT"),
        LONG(new String[0]),
        SHORT(new String[0]);

        String[] _fallbackTypeOf;

        private GenericNameType(String ... stringArray) {
            this._fallbackTypeOf = stringArray;
        }

        public boolean isFallbackTypeOf(GenericNameType genericNameType) {
            String string = genericNameType.toString();
            for (String string2 : this._fallbackTypeOf) {
                if (!string2.equals(string)) continue;
                return false;
            }
            return true;
        }
    }
}

