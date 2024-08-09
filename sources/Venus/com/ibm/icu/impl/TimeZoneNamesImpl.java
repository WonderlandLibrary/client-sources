/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.ZoneMeta;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class TimeZoneNamesImpl
extends TimeZoneNames {
    private static final long serialVersionUID = -2179814848495897472L;
    private static final String ZONE_STRINGS_BUNDLE = "zoneStrings";
    private static final String MZ_PREFIX = "meta:";
    private static volatile Set<String> METAZONE_IDS;
    private static final TZ2MZsCache TZ_TO_MZS_CACHE;
    private static final MZ2TZsCache MZ_TO_TZS_CACHE;
    private transient ICUResourceBundle _zoneStrings;
    private transient ConcurrentHashMap<String, ZNames> _mzNamesMap;
    private transient ConcurrentHashMap<String, ZNames> _tzNamesMap;
    private transient boolean _namesFullyLoaded;
    private transient TextTrieMap<NameInfo> _namesTrie;
    private transient boolean _namesTrieFullyLoaded;
    private static final Pattern LOC_EXCLUSION_PATTERN;

    public TimeZoneNamesImpl(ULocale uLocale) {
        this.initialize(uLocale);
    }

    @Override
    public Set<String> getAvailableMetaZoneIDs() {
        return TimeZoneNamesImpl._getAvailableMetaZoneIDs();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Set<String> _getAvailableMetaZoneIDs() {
        if (METAZONE_IDS != null) return METAZONE_IDS;
        Class<TimeZoneNamesImpl> clazz = TimeZoneNamesImpl.class;
        synchronized (TimeZoneNamesImpl.class) {
            if (METAZONE_IDS != null) return METAZONE_IDS;
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "metaZones");
            UResourceBundle uResourceBundle2 = uResourceBundle.get("mapTimezones");
            Set<String> set = uResourceBundle2.keySet();
            METAZONE_IDS = Collections.unmodifiableSet(set);
            // ** MonitorExit[var0] (shouldn't be in output)
            return METAZONE_IDS;
        }
    }

    @Override
    public Set<String> getAvailableMetaZoneIDs(String string) {
        return TimeZoneNamesImpl._getAvailableMetaZoneIDs(string);
    }

    static Set<String> _getAvailableMetaZoneIDs(String string) {
        if (string == null || string.length() == 0) {
            return Collections.emptySet();
        }
        List list = (List)TZ_TO_MZS_CACHE.getInstance(string, string);
        if (list.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<String> hashSet = new HashSet<String>(list.size());
        for (MZMapEntry mZMapEntry : list) {
            hashSet.add(mZMapEntry.mzID());
        }
        return Collections.unmodifiableSet(hashSet);
    }

    @Override
    public String getMetaZoneID(String string, long l) {
        return TimeZoneNamesImpl._getMetaZoneID(string, l);
    }

    static String _getMetaZoneID(String string, long l) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String string2 = null;
        List list = (List)TZ_TO_MZS_CACHE.getInstance(string, string);
        for (MZMapEntry mZMapEntry : list) {
            if (l < mZMapEntry.from() || l >= mZMapEntry.to()) continue;
            string2 = mZMapEntry.mzID();
            break;
        }
        return string2;
    }

    @Override
    public String getReferenceZoneID(String string, String string2) {
        return TimeZoneNamesImpl._getReferenceZoneID(string, string2);
    }

    static String _getReferenceZoneID(String string, String string2) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String string3 = null;
        Map map = (Map)MZ_TO_TZS_CACHE.getInstance(string, string);
        if (!map.isEmpty() && (string3 = (String)map.get(string2)) == null) {
            string3 = (String)map.get("001");
        }
        return string3;
    }

    @Override
    public String getMetaZoneDisplayName(String string, TimeZoneNames.NameType nameType) {
        if (string == null || string.length() == 0) {
            return null;
        }
        return this.loadMetaZoneNames(string).getName(nameType);
    }

    @Override
    public String getTimeZoneDisplayName(String string, TimeZoneNames.NameType nameType) {
        if (string == null || string.length() == 0) {
            return null;
        }
        return this.loadTimeZoneNames(string).getName(nameType);
    }

    @Override
    public String getExemplarLocationName(String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String string2 = this.loadTimeZoneNames(string).getName(TimeZoneNames.NameType.EXEMPLAR_LOCATION);
        return string2;
    }

    @Override
    public synchronized Collection<TimeZoneNames.MatchInfo> find(CharSequence charSequence, int n, EnumSet<TimeZoneNames.NameType> enumSet) {
        if (charSequence == null || charSequence.length() == 0 || n < 0 || n >= charSequence.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        NameSearchHandler nameSearchHandler = new NameSearchHandler(enumSet);
        Collection<TimeZoneNames.MatchInfo> collection = this.doFind(nameSearchHandler, charSequence, n);
        if (collection != null) {
            return collection;
        }
        this.addAllNamesIntoTrie();
        collection = this.doFind(nameSearchHandler, charSequence, n);
        if (collection != null) {
            return collection;
        }
        this.internalLoadAllDisplayNames();
        Set<String> set = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null);
        for (String string : set) {
            if (this._tzNamesMap.containsKey(string)) continue;
            ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, null, string);
        }
        this.addAllNamesIntoTrie();
        this._namesTrieFullyLoaded = true;
        return this.doFind(nameSearchHandler, charSequence, n);
    }

    private Collection<TimeZoneNames.MatchInfo> doFind(NameSearchHandler nameSearchHandler, CharSequence charSequence, int n) {
        nameSearchHandler.resetResults();
        this._namesTrie.find(charSequence, n, nameSearchHandler);
        if (nameSearchHandler.getMaxMatchLen() == charSequence.length() - n || this._namesTrieFullyLoaded) {
            return nameSearchHandler.getMatches();
        }
        return null;
    }

    @Override
    public synchronized void loadAllDisplayNames() {
        this.internalLoadAllDisplayNames();
    }

    @Override
    public void getDisplayNames(String string, TimeZoneNames.NameType[] nameTypeArray, long l, String[] stringArray, int n) {
        if (string == null || string.length() == 0) {
            return;
        }
        ZNames zNames = this.loadTimeZoneNames(string);
        ZNames zNames2 = null;
        for (int i = 0; i < nameTypeArray.length; ++i) {
            TimeZoneNames.NameType nameType = nameTypeArray[i];
            String string2 = zNames.getName(nameType);
            if (string2 == null) {
                if (zNames2 == null) {
                    String string3 = this.getMetaZoneID(string, l);
                    zNames2 = string3 == null || string3.length() == 0 ? ZNames.EMPTY_ZNAMES : this.loadMetaZoneNames(string3);
                }
                string2 = zNames2.getName(nameType);
            }
            stringArray[n + i] = string2;
        }
    }

    private void internalLoadAllDisplayNames() {
        if (!this._namesFullyLoaded) {
            this._namesFullyLoaded = true;
            new ZoneStringsLoader(this, null).load();
        }
    }

    private void addAllNamesIntoTrie() {
        for (Map.Entry<String, ZNames> entry : this._tzNamesMap.entrySet()) {
            entry.getValue().addAsTimeZoneIntoTrie(entry.getKey(), this._namesTrie);
        }
        for (Map.Entry<String, ZNames> entry : this._mzNamesMap.entrySet()) {
            entry.getValue().addAsMetaZoneIntoTrie(entry.getKey(), this._namesTrie);
        }
    }

    private void initialize(ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/zone", uLocale);
        this._zoneStrings = (ICUResourceBundle)iCUResourceBundle.get(ZONE_STRINGS_BUNDLE);
        this._tzNamesMap = new ConcurrentHashMap();
        this._mzNamesMap = new ConcurrentHashMap();
        this._namesFullyLoaded = false;
        this._namesTrie = new TextTrieMap(true);
        this._namesTrieFullyLoaded = false;
        TimeZone timeZone = TimeZone.getDefault();
        String string = ZoneMeta.getCanonicalCLDRID(timeZone);
        if (string != null) {
            this.loadStrings(string);
        }
    }

    private synchronized void loadStrings(String string) {
        if (string == null || string.length() == 0) {
            return;
        }
        this.loadTimeZoneNames(string);
        Set<String> set = this.getAvailableMetaZoneIDs(string);
        for (String string2 : set) {
            this.loadMetaZoneNames(string2);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ULocale uLocale = this._zoneStrings.getULocale();
        objectOutputStream.writeObject(uLocale);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        ULocale uLocale = (ULocale)objectInputStream.readObject();
        this.initialize(uLocale);
    }

    private synchronized ZNames loadMetaZoneNames(String string) {
        ZNames zNames = this._mzNamesMap.get(string);
        if (zNames == null) {
            ZNamesLoader zNamesLoader = new ZNamesLoader(null);
            zNamesLoader.loadMetaZone(this._zoneStrings, string);
            zNames = ZNames.createMetaZoneAndPutInCache(this._mzNamesMap, ZNamesLoader.access$600(zNamesLoader), string);
        }
        return zNames;
    }

    private synchronized ZNames loadTimeZoneNames(String string) {
        ZNames zNames = this._tzNamesMap.get(string);
        if (zNames == null) {
            ZNamesLoader zNamesLoader = new ZNamesLoader(null);
            zNamesLoader.loadTimeZone(this._zoneStrings, string);
            zNames = ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, ZNamesLoader.access$600(zNamesLoader), string);
        }
        return zNames;
    }

    public static String getDefaultExemplarLocationName(String string) {
        if (string == null || string.length() == 0 || LOC_EXCLUSION_PATTERN.matcher(string).matches()) {
            return null;
        }
        String string2 = null;
        int n = string.lastIndexOf(47);
        if (n > 0 && n + 1 < string.length()) {
            string2 = string.substring(n + 1).replace('_', ' ');
        }
        return string2;
    }

    static ICUResourceBundle access$300(TimeZoneNamesImpl timeZoneNamesImpl) {
        return timeZoneNamesImpl._zoneStrings;
    }

    static ConcurrentHashMap access$500(TimeZoneNamesImpl timeZoneNamesImpl) {
        return timeZoneNamesImpl._mzNamesMap;
    }

    static ConcurrentHashMap access$700(TimeZoneNamesImpl timeZoneNamesImpl) {
        return timeZoneNamesImpl._tzNamesMap;
    }

    static {
        TZ_TO_MZS_CACHE = new TZ2MZsCache(null);
        MZ_TO_TZS_CACHE = new MZ2TZsCache(null);
        LOC_EXCLUSION_PATTERN = Pattern.compile("Etc/.*|SystemV/.*|.*/Riyadh8[7-9]");
    }

    private static class MZ2TZsCache
    extends SoftCache<String, Map<String, String>, String> {
        private MZ2TZsCache() {
        }

        @Override
        protected Map<String, String> createInstance(String string, String string2) {
            Map<String, String> map = null;
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "metaZones");
            UResourceBundle uResourceBundle2 = uResourceBundle.get("mapTimezones");
            try {
                UResourceBundle uResourceBundle3 = uResourceBundle2.get(string);
                Set<String> set = uResourceBundle3.keySet();
                map = new HashMap<String, String>(set.size());
                for (String string3 : set) {
                    String string4 = uResourceBundle3.getString(string3).intern();
                    map.put(string3.intern(), string4);
                }
            } catch (MissingResourceException missingResourceException) {
                map = Collections.emptyMap();
            }
            return map;
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (String)object2);
        }

        MZ2TZsCache(1 var1_1) {
            this();
        }
    }

    private static class TZ2MZsCache
    extends SoftCache<String, List<MZMapEntry>, String> {
        private TZ2MZsCache() {
        }

        @Override
        protected List<MZMapEntry> createInstance(String string, String string2) {
            List<MZMapEntry> list = null;
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "metaZones");
            UResourceBundle uResourceBundle2 = uResourceBundle.get("metazoneInfo");
            String string3 = string2.replace('/', ':');
            try {
                UResourceBundle uResourceBundle3 = uResourceBundle2.get(string3);
                list = new ArrayList<MZMapEntry>(uResourceBundle3.getSize());
                for (int i = 0; i < uResourceBundle3.getSize(); ++i) {
                    UResourceBundle uResourceBundle4 = uResourceBundle3.get(i);
                    String string4 = uResourceBundle4.getString(0);
                    String string5 = "1970-01-01 00:00";
                    String string6 = "9999-12-31 23:59";
                    if (uResourceBundle4.getSize() == 3) {
                        string5 = uResourceBundle4.getString(1);
                        string6 = uResourceBundle4.getString(2);
                    }
                    long l = TZ2MZsCache.parseDate(string5);
                    long l2 = TZ2MZsCache.parseDate(string6);
                    list.add(new MZMapEntry(string4, l, l2));
                }
            } catch (MissingResourceException missingResourceException) {
                list = Collections.emptyList();
            }
            return list;
        }

        private static long parseDate(String string) {
            int n;
            int n2;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            for (n2 = 0; n2 <= 3; ++n2) {
                n = string.charAt(n2) - 48;
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad year");
                }
                n3 = 10 * n3 + n;
            }
            for (n2 = 5; n2 <= 6; ++n2) {
                n = string.charAt(n2) - 48;
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad month");
                }
                n4 = 10 * n4 + n;
            }
            for (n2 = 8; n2 <= 9; ++n2) {
                n = string.charAt(n2) - 48;
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad day");
                }
                n5 = 10 * n5 + n;
            }
            for (n2 = 11; n2 <= 12; ++n2) {
                n = string.charAt(n2) - 48;
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad hour");
                }
                n6 = 10 * n6 + n;
            }
            for (n2 = 14; n2 <= 15; ++n2) {
                n = string.charAt(n2) - 48;
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad minute");
                }
                n7 = 10 * n7 + n;
            }
            long l = Grego.fieldsToDay(n3, n4 - 1, n5) * 86400000L + (long)n6 * 3600000L + (long)n7 * 60000L;
            return l;
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (String)object2);
        }

        TZ2MZsCache(1 var1_1) {
            this();
        }
    }

    private static class MZMapEntry {
        private String _mzID;
        private long _from;
        private long _to;

        MZMapEntry(String string, long l, long l2) {
            this._mzID = string;
            this._from = l;
            this._to = l2;
        }

        String mzID() {
            return this._mzID;
        }

        long from() {
            return this._from;
        }

        long to() {
            return this._to;
        }
    }

    private static class ZNames {
        public static final int NUM_NAME_TYPES = 7;
        static final ZNames EMPTY_ZNAMES = new ZNames(null);
        private static final int EX_LOC_INDEX = NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
        private String[] _names;
        private boolean didAddIntoTrie;

        private static int getNameTypeIndex(TimeZoneNames.NameType nameType) {
            switch (nameType) {
                case EXEMPLAR_LOCATION: {
                    return NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
                }
                case LONG_GENERIC: {
                    return NameTypeIndex.LONG_GENERIC.ordinal();
                }
                case LONG_STANDARD: {
                    return NameTypeIndex.LONG_STANDARD.ordinal();
                }
                case LONG_DAYLIGHT: {
                    return NameTypeIndex.LONG_DAYLIGHT.ordinal();
                }
                case SHORT_GENERIC: {
                    return NameTypeIndex.SHORT_GENERIC.ordinal();
                }
                case SHORT_STANDARD: {
                    return NameTypeIndex.SHORT_STANDARD.ordinal();
                }
                case SHORT_DAYLIGHT: {
                    return NameTypeIndex.SHORT_DAYLIGHT.ordinal();
                }
            }
            throw new AssertionError((Object)("No NameTypeIndex match for " + (Object)((Object)nameType)));
        }

        private static TimeZoneNames.NameType getNameType(int n) {
            switch (NameTypeIndex.values[n]) {
                case EXEMPLAR_LOCATION: {
                    return TimeZoneNames.NameType.EXEMPLAR_LOCATION;
                }
                case LONG_GENERIC: {
                    return TimeZoneNames.NameType.LONG_GENERIC;
                }
                case LONG_STANDARD: {
                    return TimeZoneNames.NameType.LONG_STANDARD;
                }
                case LONG_DAYLIGHT: {
                    return TimeZoneNames.NameType.LONG_DAYLIGHT;
                }
                case SHORT_GENERIC: {
                    return TimeZoneNames.NameType.SHORT_GENERIC;
                }
                case SHORT_STANDARD: {
                    return TimeZoneNames.NameType.SHORT_STANDARD;
                }
                case SHORT_DAYLIGHT: {
                    return TimeZoneNames.NameType.SHORT_DAYLIGHT;
                }
            }
            throw new AssertionError((Object)("No NameType match for " + n));
        }

        protected ZNames(String[] stringArray) {
            this._names = stringArray;
            this.didAddIntoTrie = stringArray == null;
        }

        public static ZNames createMetaZoneAndPutInCache(Map<String, ZNames> map, String[] stringArray, String string) {
            String string2 = string.intern();
            ZNames zNames = stringArray == null ? EMPTY_ZNAMES : new ZNames(stringArray);
            map.put(string2, zNames);
            return zNames;
        }

        public static ZNames createTimeZoneAndPutInCache(Map<String, ZNames> map, String[] stringArray, String string) {
            String[] stringArray2 = stringArray = stringArray == null ? new String[EX_LOC_INDEX + 1] : stringArray;
            if (stringArray[EX_LOC_INDEX] == null) {
                stringArray[ZNames.EX_LOC_INDEX] = TimeZoneNamesImpl.getDefaultExemplarLocationName(string);
            }
            String string2 = string.intern();
            ZNames zNames = new ZNames(stringArray);
            map.put(string2, zNames);
            return zNames;
        }

        public String getName(TimeZoneNames.NameType nameType) {
            int n = ZNames.getNameTypeIndex(nameType);
            if (this._names != null && n < this._names.length) {
                return this._names[n];
            }
            return null;
        }

        public void addAsMetaZoneIntoTrie(String string, TextTrieMap<NameInfo> textTrieMap) {
            this.addNamesIntoTrie(string, null, textTrieMap);
        }

        public void addAsTimeZoneIntoTrie(String string, TextTrieMap<NameInfo> textTrieMap) {
            this.addNamesIntoTrie(null, string, textTrieMap);
        }

        private void addNamesIntoTrie(String string, String string2, TextTrieMap<NameInfo> textTrieMap) {
            if (this._names == null || this.didAddIntoTrie) {
                return;
            }
            this.didAddIntoTrie = true;
            for (int i = 0; i < this._names.length; ++i) {
                String string3 = this._names[i];
                if (string3 == null) continue;
                NameInfo nameInfo = new NameInfo(null);
                nameInfo.mzID = string;
                nameInfo.tzID = string2;
                nameInfo.type = ZNames.getNameType(i);
                textTrieMap.put(string3, nameInfo);
            }
        }

        private static enum NameTypeIndex {
            EXEMPLAR_LOCATION,
            LONG_GENERIC,
            LONG_STANDARD,
            LONG_DAYLIGHT,
            SHORT_GENERIC,
            SHORT_STANDARD,
            SHORT_DAYLIGHT;

            static final NameTypeIndex[] values;

            static {
                values = NameTypeIndex.values();
            }
        }
    }

    private static final class ZNamesLoader
    extends UResource.Sink {
        private String[] names;
        private static ZNamesLoader DUMMY_LOADER;
        static final boolean $assertionsDisabled;

        private ZNamesLoader() {
        }

        void loadMetaZone(ICUResourceBundle iCUResourceBundle, String string) {
            String string2 = TimeZoneNamesImpl.MZ_PREFIX + string;
            this.loadNames(iCUResourceBundle, string2);
        }

        void loadTimeZone(ICUResourceBundle iCUResourceBundle, String string) {
            String string2 = string.replace('/', ':');
            this.loadNames(iCUResourceBundle, string2);
        }

        void loadNames(ICUResourceBundle iCUResourceBundle, String string) {
            if (!$assertionsDisabled && iCUResourceBundle == null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && string == null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && string.length() <= 0) {
                throw new AssertionError();
            }
            this.names = null;
            try {
                iCUResourceBundle.getAllItemsWithFallback(string, this);
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }

        private static ZNames.NameTypeIndex nameTypeIndexFromKey(UResource.Key key) {
            if (key.length() != 2) {
                return null;
            }
            char c = key.charAt(0);
            char c2 = key.charAt(1);
            if (c == 'l') {
                return c2 == 'g' ? ZNames.NameTypeIndex.LONG_GENERIC : (c2 == 's' ? ZNames.NameTypeIndex.LONG_STANDARD : (c2 == 'd' ? ZNames.NameTypeIndex.LONG_DAYLIGHT : null));
            }
            if (c == 's') {
                return c2 == 'g' ? ZNames.NameTypeIndex.SHORT_GENERIC : (c2 == 's' ? ZNames.NameTypeIndex.SHORT_STANDARD : (c2 == 'd' ? ZNames.NameTypeIndex.SHORT_DAYLIGHT : null));
            }
            if (c == 'e' && c2 == 'c') {
                return ZNames.NameTypeIndex.EXEMPLAR_LOCATION;
            }
            return null;
        }

        private void setNameIfEmpty(UResource.Key key, UResource.Value value) {
            ZNames.NameTypeIndex nameTypeIndex;
            if (this.names == null) {
                this.names = new String[7];
            }
            if ((nameTypeIndex = ZNamesLoader.nameTypeIndexFromKey(key)) == null) {
                return;
            }
            if (!$assertionsDisabled && nameTypeIndex.ordinal() >= 7) {
                throw new AssertionError();
            }
            if (this.names[nameTypeIndex.ordinal()] == null) {
                this.names[nameTypeIndex.ordinal()] = value.getString();
            }
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (!$assertionsDisabled && value.getType() != 0) {
                    throw new AssertionError();
                }
                this.setNameIfEmpty(key, value);
                ++n;
            }
        }

        private String[] getNames() {
            if (Utility.sameObjects(this.names, null)) {
                return null;
            }
            int n = 0;
            for (int i = 0; i < 7; ++i) {
                String string = this.names[i];
                if (string == null) continue;
                if (string.equals("\u2205\u2205\u2205")) {
                    this.names[i] = null;
                    continue;
                }
                n = i + 1;
            }
            Object object = n == 7 ? this.names : (n == 0 ? null : Arrays.copyOfRange(this.names, 0, n));
            return object;
        }

        static ZNamesLoader access$400() {
            return DUMMY_LOADER;
        }

        static String[] access$600(ZNamesLoader zNamesLoader) {
            return zNamesLoader.getNames();
        }

        ZNamesLoader(1 var1_1) {
            this();
        }

        static {
            $assertionsDisabled = !TimeZoneNamesImpl.class.desiredAssertionStatus();
            DUMMY_LOADER = new ZNamesLoader();
        }
    }

    private static class NameSearchHandler
    implements TextTrieMap.ResultHandler<NameInfo> {
        private EnumSet<TimeZoneNames.NameType> _nameTypes;
        private Collection<TimeZoneNames.MatchInfo> _matches;
        private int _maxMatchLen;
        static final boolean $assertionsDisabled = !TimeZoneNamesImpl.class.desiredAssertionStatus();

        NameSearchHandler(EnumSet<TimeZoneNames.NameType> enumSet) {
            this._nameTypes = enumSet;
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<NameInfo> iterator2) {
            while (iterator2.hasNext()) {
                TimeZoneNames.MatchInfo matchInfo;
                NameInfo nameInfo = iterator2.next();
                if (this._nameTypes != null && !this._nameTypes.contains((Object)nameInfo.type)) continue;
                if (nameInfo.tzID != null) {
                    matchInfo = new TimeZoneNames.MatchInfo(nameInfo.type, nameInfo.tzID, null, n);
                } else {
                    if (!$assertionsDisabled && nameInfo.mzID == null) {
                        throw new AssertionError();
                    }
                    matchInfo = new TimeZoneNames.MatchInfo(nameInfo.type, null, nameInfo.mzID, n);
                }
                if (this._matches == null) {
                    this._matches = new LinkedList<TimeZoneNames.MatchInfo>();
                }
                this._matches.add(matchInfo);
                if (n <= this._maxMatchLen) continue;
                this._maxMatchLen = n;
            }
            return false;
        }

        public Collection<TimeZoneNames.MatchInfo> getMatches() {
            if (this._matches == null) {
                return Collections.emptyList();
            }
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

    private static class NameInfo {
        String tzID;
        String mzID;
        TimeZoneNames.NameType type;

        private NameInfo() {
        }

        NameInfo(1 var1_1) {
            this();
        }
    }

    private final class ZoneStringsLoader
    extends UResource.Sink {
        private static final int INITIAL_NUM_ZONES = 300;
        private HashMap<UResource.Key, ZNamesLoader> keyToLoader;
        private StringBuilder sb;
        static final boolean $assertionsDisabled = !TimeZoneNamesImpl.class.desiredAssertionStatus();
        final TimeZoneNamesImpl this$0;

        private ZoneStringsLoader(TimeZoneNamesImpl timeZoneNamesImpl) {
            this.this$0 = timeZoneNamesImpl;
            this.keyToLoader = new HashMap(300);
            this.sb = new StringBuilder(32);
        }

        void load() {
            TimeZoneNamesImpl.access$300(this.this$0).getAllItemsWithFallback("", this);
            for (Map.Entry<UResource.Key, ZNamesLoader> entry : this.keyToLoader.entrySet()) {
                String string;
                ZNamesLoader zNamesLoader = entry.getValue();
                if (zNamesLoader == ZNamesLoader.access$400()) continue;
                UResource.Key key = entry.getKey();
                if (this.isMetaZone(key)) {
                    string = this.mzIDFromKey(key);
                    ZNames.createMetaZoneAndPutInCache(TimeZoneNamesImpl.access$500(this.this$0), ZNamesLoader.access$600(zNamesLoader), string);
                    continue;
                }
                string = this.tzIDFromKey(key);
                ZNames.createTimeZoneAndPutInCache(TimeZoneNamesImpl.access$700(this.this$0), ZNamesLoader.access$600(zNamesLoader), string);
            }
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (!$assertionsDisabled && value.isNoInheritanceMarker()) {
                    throw new AssertionError();
                }
                if (value.getType() == 2) {
                    this.consumeNamesTable(key, value, bl);
                }
                ++n;
            }
        }

        private void consumeNamesTable(UResource.Key key, UResource.Value value, boolean bl) {
            ZNamesLoader zNamesLoader = this.keyToLoader.get(key);
            if (zNamesLoader == null) {
                CharSequence charSequence;
                if (this.isMetaZone(key)) {
                    charSequence = this.mzIDFromKey(key);
                    zNamesLoader = TimeZoneNamesImpl.access$500(this.this$0).containsKey(charSequence) ? ZNamesLoader.access$400() : new ZNamesLoader(null);
                } else {
                    charSequence = this.tzIDFromKey(key);
                    zNamesLoader = TimeZoneNamesImpl.access$700(this.this$0).containsKey(charSequence) ? ZNamesLoader.access$400() : new ZNamesLoader(null);
                }
                charSequence = this.createKey(key);
                this.keyToLoader.put((UResource.Key)charSequence, zNamesLoader);
            }
            if (zNamesLoader != ZNamesLoader.access$400()) {
                zNamesLoader.put(key, value, bl);
            }
        }

        UResource.Key createKey(UResource.Key key) {
            return key.clone();
        }

        boolean isMetaZone(UResource.Key key) {
            return key.startsWith(TimeZoneNamesImpl.MZ_PREFIX);
        }

        private String mzIDFromKey(UResource.Key key) {
            this.sb.setLength(0);
            for (int i = 5; i < key.length(); ++i) {
                this.sb.append(key.charAt(i));
            }
            return this.sb.toString();
        }

        private String tzIDFromKey(UResource.Key key) {
            this.sb.setLength(0);
            for (int i = 0; i < key.length(); ++i) {
                char c = key.charAt(i);
                if (c == ':') {
                    c = '/';
                }
                this.sb.append(c);
            }
            return this.sb.toString();
        }

        ZoneStringsLoader(TimeZoneNamesImpl timeZoneNamesImpl, 1 var2_2) {
            this(timeZoneNamesImpl);
        }
    }
}

