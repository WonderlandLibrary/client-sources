package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import java.util.concurrent.*;
import java.util.regex.*;
import com.ibm.icu.util.*;
import java.io.*;
import java.util.*;

public class TimeZoneNamesImpl extends TimeZoneNames
{
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
    
    public TimeZoneNamesImpl(final ULocale locale) {
        this.initialize(locale);
    }
    
    @Override
    public Set<String> getAvailableMetaZoneIDs() {
        return _getAvailableMetaZoneIDs();
    }
    
    static Set<String> _getAvailableMetaZoneIDs() {
        if (TimeZoneNamesImpl.METAZONE_IDS == null) {
            synchronized (TimeZoneNamesImpl.class) {
                if (TimeZoneNamesImpl.METAZONE_IDS == null) {
                    final UResourceBundle bundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "metaZones");
                    final UResourceBundle mapTimezones = bundle.get("mapTimezones");
                    final Set<String> keys = mapTimezones.keySet();
                    TimeZoneNamesImpl.METAZONE_IDS = Collections.unmodifiableSet((Set<? extends String>)keys);
                }
            }
        }
        return TimeZoneNamesImpl.METAZONE_IDS;
    }
    
    @Override
    public Set<String> getAvailableMetaZoneIDs(final String tzID) {
        return _getAvailableMetaZoneIDs(tzID);
    }
    
    static Set<String> _getAvailableMetaZoneIDs(final String tzID) {
        if (tzID == null || tzID.length() == 0) {
            return Collections.emptySet();
        }
        final List<MZMapEntry> maps = TimeZoneNamesImpl.TZ_TO_MZS_CACHE.getInstance(tzID, tzID);
        if (maps.isEmpty()) {
            return Collections.emptySet();
        }
        final Set<String> mzIDs = new HashSet<String>(maps.size());
        for (final MZMapEntry map : maps) {
            mzIDs.add(map.mzID());
        }
        return Collections.unmodifiableSet((Set<? extends String>)mzIDs);
    }
    
    @Override
    public String getMetaZoneID(final String tzID, final long date) {
        return _getMetaZoneID(tzID, date);
    }
    
    static String _getMetaZoneID(final String tzID, final long date) {
        if (tzID == null || tzID.length() == 0) {
            return null;
        }
        String mzID = null;
        final List<MZMapEntry> maps = TimeZoneNamesImpl.TZ_TO_MZS_CACHE.getInstance(tzID, tzID);
        for (final MZMapEntry map : maps) {
            if (date >= map.from() && date < map.to()) {
                mzID = map.mzID();
                break;
            }
        }
        return mzID;
    }
    
    @Override
    public String getReferenceZoneID(final String mzID, final String region) {
        return _getReferenceZoneID(mzID, region);
    }
    
    static String _getReferenceZoneID(final String mzID, final String region) {
        if (mzID == null || mzID.length() == 0) {
            return null;
        }
        String refID = null;
        final Map<String, String> regionTzMap = TimeZoneNamesImpl.MZ_TO_TZS_CACHE.getInstance(mzID, mzID);
        if (!regionTzMap.isEmpty()) {
            refID = regionTzMap.get(region);
            if (refID == null) {
                refID = regionTzMap.get("001");
            }
        }
        return refID;
    }
    
    @Override
    public String getMetaZoneDisplayName(final String mzID, final NameType type) {
        if (mzID == null || mzID.length() == 0) {
            return null;
        }
        return this.loadMetaZoneNames(mzID).getName(type);
    }
    
    @Override
    public String getTimeZoneDisplayName(final String tzID, final NameType type) {
        if (tzID == null || tzID.length() == 0) {
            return null;
        }
        return this.loadTimeZoneNames(tzID).getName(type);
    }
    
    @Override
    public String getExemplarLocationName(final String tzID) {
        if (tzID == null || tzID.length() == 0) {
            return null;
        }
        final String locName = this.loadTimeZoneNames(tzID).getName(NameType.EXEMPLAR_LOCATION);
        return locName;
    }
    
    @Override
    public synchronized Collection<MatchInfo> find(final CharSequence text, final int start, final EnumSet<NameType> nameTypes) {
        if (text == null || text.length() == 0 || start < 0 || start >= text.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        final NameSearchHandler handler = new NameSearchHandler(nameTypes);
        Collection<MatchInfo> matches = this.doFind(handler, text, start);
        if (matches != null) {
            return matches;
        }
        this.addAllNamesIntoTrie();
        matches = this.doFind(handler, text, start);
        if (matches != null) {
            return matches;
        }
        this.internalLoadAllDisplayNames();
        final Set<String> tzIDs = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null);
        for (final String tzID : tzIDs) {
            if (!this._tzNamesMap.containsKey(tzID)) {
                ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, null, tzID);
            }
        }
        this.addAllNamesIntoTrie();
        this._namesTrieFullyLoaded = true;
        return this.doFind(handler, text, start);
    }
    
    private Collection<MatchInfo> doFind(final NameSearchHandler handler, final CharSequence text, final int start) {
        handler.resetResults();
        this._namesTrie.find(text, start, handler);
        if (handler.getMaxMatchLen() == text.length() - start || this._namesTrieFullyLoaded) {
            return handler.getMatches();
        }
        return null;
    }
    
    @Override
    public synchronized void loadAllDisplayNames() {
        this.internalLoadAllDisplayNames();
    }
    
    @Override
    public void getDisplayNames(final String tzID, final NameType[] types, final long date, final String[] dest, final int destOffset) {
        if (tzID == null || tzID.length() == 0) {
            return;
        }
        final ZNames tzNames = this.loadTimeZoneNames(tzID);
        ZNames mzNames = null;
        for (int i = 0; i < types.length; ++i) {
            final NameType type = types[i];
            String name = tzNames.getName(type);
            if (name == null) {
                if (mzNames == null) {
                    final String mzID = this.getMetaZoneID(tzID, date);
                    if (mzID == null || mzID.length() == 0) {
                        mzNames = ZNames.EMPTY_ZNAMES;
                    }
                    else {
                        mzNames = this.loadMetaZoneNames(mzID);
                    }
                }
                name = mzNames.getName(type);
            }
            dest[destOffset + i] = name;
        }
    }
    
    private void internalLoadAllDisplayNames() {
        if (!this._namesFullyLoaded) {
            this._namesFullyLoaded = true;
            new ZoneStringsLoader().load();
        }
    }
    
    private void addAllNamesIntoTrie() {
        for (final Map.Entry<String, ZNames> entry : this._tzNamesMap.entrySet()) {
            entry.getValue().addAsTimeZoneIntoTrie(entry.getKey(), this._namesTrie);
        }
        for (final Map.Entry<String, ZNames> entry : this._mzNamesMap.entrySet()) {
            entry.getValue().addAsMetaZoneIntoTrie(entry.getKey(), this._namesTrie);
        }
    }
    
    private void initialize(final ULocale locale) {
        final ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/zone", locale);
        this._zoneStrings = (ICUResourceBundle)bundle.get("zoneStrings");
        this._tzNamesMap = new ConcurrentHashMap<String, ZNames>();
        this._mzNamesMap = new ConcurrentHashMap<String, ZNames>();
        this._namesFullyLoaded = false;
        this._namesTrie = new TextTrieMap<NameInfo>(true);
        this._namesTrieFullyLoaded = false;
        final TimeZone tz = TimeZone.getDefault();
        final String tzCanonicalID = ZoneMeta.getCanonicalCLDRID(tz);
        if (tzCanonicalID != null) {
            this.loadStrings(tzCanonicalID);
        }
    }
    
    private synchronized void loadStrings(final String tzCanonicalID) {
        if (tzCanonicalID == null || tzCanonicalID.length() == 0) {
            return;
        }
        this.loadTimeZoneNames(tzCanonicalID);
        final Set<String> mzIDs = this.getAvailableMetaZoneIDs(tzCanonicalID);
        for (final String mzID : mzIDs) {
            this.loadMetaZoneNames(mzID);
        }
    }
    
    private void writeObject(final ObjectOutputStream out) throws IOException {
        final ULocale locale = this._zoneStrings.getULocale();
        out.writeObject(locale);
    }
    
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        final ULocale locale = (ULocale)in.readObject();
        this.initialize(locale);
    }
    
    private synchronized ZNames loadMetaZoneNames(final String mzID) {
        ZNames mznames = this._mzNamesMap.get(mzID);
        if (mznames == null) {
            final ZNamesLoader loader = new ZNamesLoader();
            loader.loadMetaZone(this._zoneStrings, mzID);
            mznames = ZNames.createMetaZoneAndPutInCache(this._mzNamesMap, loader.getNames(), mzID);
        }
        return mznames;
    }
    
    private synchronized ZNames loadTimeZoneNames(final String tzID) {
        ZNames tznames = this._tzNamesMap.get(tzID);
        if (tznames == null) {
            final ZNamesLoader loader = new ZNamesLoader();
            loader.loadTimeZone(this._zoneStrings, tzID);
            tznames = ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, loader.getNames(), tzID);
        }
        return tznames;
    }
    
    public static String getDefaultExemplarLocationName(final String tzID) {
        if (tzID == null || tzID.length() == 0 || TimeZoneNamesImpl.LOC_EXCLUSION_PATTERN.matcher(tzID).matches()) {
            return null;
        }
        String location = null;
        final int sep = tzID.lastIndexOf(47);
        if (sep > 0 && sep + 1 < tzID.length()) {
            location = tzID.substring(sep + 1).replace('_', ' ');
        }
        return location;
    }
    
    static {
        TZ_TO_MZS_CACHE = new TZ2MZsCache();
        MZ_TO_TZS_CACHE = new MZ2TZsCache();
        LOC_EXCLUSION_PATTERN = Pattern.compile("Etc/.*|SystemV/.*|.*/Riyadh8[7-9]");
    }
    
    private final class ZoneStringsLoader extends UResource.Sink
    {
        private static final int INITIAL_NUM_ZONES = 300;
        private HashMap<UResource.Key, ZNamesLoader> keyToLoader;
        private StringBuilder sb;
        
        private ZoneStringsLoader() {
            this.keyToLoader = new HashMap<UResource.Key, ZNamesLoader>(300);
            this.sb = new StringBuilder(32);
        }
        
        void load() {
            TimeZoneNamesImpl.this._zoneStrings.getAllItemsWithFallback("", this);
            for (final Map.Entry<UResource.Key, ZNamesLoader> entry : this.keyToLoader.entrySet()) {
                final ZNamesLoader loader = entry.getValue();
                if (loader == ZNamesLoader.DUMMY_LOADER) {
                    continue;
                }
                final UResource.Key key = entry.getKey();
                if (this.isMetaZone(key)) {
                    final String mzID = this.mzIDFromKey(key);
                    ZNames.createMetaZoneAndPutInCache(TimeZoneNamesImpl.this._mzNamesMap, loader.getNames(), mzID);
                }
                else {
                    final String tzID = this.tzIDFromKey(key);
                    ZNames.createTimeZoneAndPutInCache(TimeZoneNamesImpl.this._tzNamesMap, loader.getNames(), tzID);
                }
            }
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table timeZonesTable = value.getTable();
            for (int j = 0; timeZonesTable.getKeyAndValue(j, key, value); ++j) {
                assert !value.isNoInheritanceMarker();
                if (value.getType() == 2) {
                    this.consumeNamesTable(key, value, noFallback);
                }
            }
        }
        
        private void consumeNamesTable(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            ZNamesLoader loader = this.keyToLoader.get(key);
            if (loader == null) {
                if (this.isMetaZone(key)) {
                    final String mzID = this.mzIDFromKey(key);
                    if (TimeZoneNamesImpl.this._mzNamesMap.containsKey(mzID)) {
                        loader = ZNamesLoader.DUMMY_LOADER;
                    }
                    else {
                        loader = new ZNamesLoader();
                    }
                }
                else {
                    final String tzID = this.tzIDFromKey(key);
                    if (TimeZoneNamesImpl.this._tzNamesMap.containsKey(tzID)) {
                        loader = ZNamesLoader.DUMMY_LOADER;
                    }
                    else {
                        loader = new ZNamesLoader();
                    }
                }
                final UResource.Key newKey = this.createKey(key);
                this.keyToLoader.put(newKey, loader);
            }
            if (loader != ZNamesLoader.DUMMY_LOADER) {
                loader.put(key, value, noFallback);
            }
        }
        
        UResource.Key createKey(final UResource.Key key) {
            return key.clone();
        }
        
        boolean isMetaZone(final UResource.Key key) {
            return key.startsWith("meta:");
        }
        
        private String mzIDFromKey(final UResource.Key key) {
            this.sb.setLength(0);
            for (int i = "meta:".length(); i < key.length(); ++i) {
                this.sb.append(key.charAt(i));
            }
            return this.sb.toString();
        }
        
        private String tzIDFromKey(final UResource.Key key) {
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
    }
    
    private static class NameInfo
    {
        String tzID;
        String mzID;
        NameType type;
    }
    
    private static class NameSearchHandler implements TextTrieMap.ResultHandler<NameInfo>
    {
        private EnumSet<NameType> _nameTypes;
        private Collection<MatchInfo> _matches;
        private int _maxMatchLen;
        
        NameSearchHandler(final EnumSet<NameType> nameTypes) {
            this._nameTypes = nameTypes;
        }
        
        @Override
        public boolean handlePrefixMatch(final int matchLength, final Iterator<NameInfo> values) {
            while (values.hasNext()) {
                final NameInfo ninfo = values.next();
                if (this._nameTypes != null && !this._nameTypes.contains(ninfo.type)) {
                    continue;
                }
                MatchInfo minfo;
                if (ninfo.tzID != null) {
                    minfo = new MatchInfo(ninfo.type, ninfo.tzID, null, matchLength);
                }
                else {
                    assert ninfo.mzID != null;
                    minfo = new MatchInfo(ninfo.type, null, ninfo.mzID, matchLength);
                }
                if (this._matches == null) {
                    this._matches = new LinkedList<MatchInfo>();
                }
                this._matches.add(minfo);
                if (matchLength <= this._maxMatchLen) {
                    continue;
                }
                this._maxMatchLen = matchLength;
            }
            return true;
        }
        
        public Collection<MatchInfo> getMatches() {
            if (this._matches == null) {
                return (Collection<MatchInfo>)Collections.emptyList();
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
    
    private static final class ZNamesLoader extends UResource.Sink
    {
        private String[] names;
        private static ZNamesLoader DUMMY_LOADER;
        
        void loadMetaZone(final ICUResourceBundle zoneStrings, final String mzID) {
            final String key = "meta:" + mzID;
            this.loadNames(zoneStrings, key);
        }
        
        void loadTimeZone(final ICUResourceBundle zoneStrings, final String tzID) {
            final String key = tzID.replace('/', ':');
            this.loadNames(zoneStrings, key);
        }
        
        void loadNames(final ICUResourceBundle zoneStrings, final String key) {
            assert zoneStrings != null;
            assert key != null;
            assert key.length() > 0;
            this.names = null;
            try {
                zoneStrings.getAllItemsWithFallback(key, this);
            }
            catch (MissingResourceException ex) {}
        }
        
        private static ZNames.NameTypeIndex nameTypeIndexFromKey(final UResource.Key key) {
            if (key.length() != 2) {
                return null;
            }
            final char c0 = key.charAt(0);
            final char c2 = key.charAt(1);
            if (c0 == 'l') {
                return (c2 == 'g') ? ZNames.NameTypeIndex.LONG_GENERIC : ((c2 == 's') ? ZNames.NameTypeIndex.LONG_STANDARD : ((c2 == 'd') ? ZNames.NameTypeIndex.LONG_DAYLIGHT : null));
            }
            if (c0 == 's') {
                return (c2 == 'g') ? ZNames.NameTypeIndex.SHORT_GENERIC : ((c2 == 's') ? ZNames.NameTypeIndex.SHORT_STANDARD : ((c2 == 'd') ? ZNames.NameTypeIndex.SHORT_DAYLIGHT : null));
            }
            if (c0 == 'e' && c2 == 'c') {
                return ZNames.NameTypeIndex.EXEMPLAR_LOCATION;
            }
            return null;
        }
        
        private void setNameIfEmpty(final UResource.Key key, final UResource.Value value) {
            if (this.names == null) {
                this.names = new String[7];
            }
            final ZNames.NameTypeIndex index = nameTypeIndexFromKey(key);
            if (index == null) {
                return;
            }
            assert index.ordinal() < 7;
            if (this.names[index.ordinal()] == null) {
                this.names[index.ordinal()] = value.getString();
            }
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table namesTable = value.getTable();
            for (int i = 0; namesTable.getKeyAndValue(i, key, value); ++i) {
                assert value.getType() == 0;
                this.setNameIfEmpty(key, value);
            }
        }
        
        private String[] getNames() {
            if (Utility.sameObjects(this.names, null)) {
                return null;
            }
            int length = 0;
            for (int i = 0; i < 7; ++i) {
                final String name = this.names[i];
                if (name != null) {
                    if (name.equals("\u2205\u2205\u2205")) {
                        this.names[i] = null;
                    }
                    else {
                        length = i + 1;
                    }
                }
            }
            String[] result;
            if (length == 7) {
                result = this.names;
            }
            else if (length == 0) {
                result = null;
            }
            else {
                result = Arrays.copyOfRange(this.names, 0, length);
            }
            return result;
        }
        
        static {
            ZNamesLoader.DUMMY_LOADER = new ZNamesLoader();
        }
    }
    
    private static class ZNames
    {
        public static final int NUM_NAME_TYPES = 7;
        static final ZNames EMPTY_ZNAMES;
        private static final int EX_LOC_INDEX;
        private String[] _names;
        private boolean didAddIntoTrie;
        
        private static int getNameTypeIndex(final NameType type) {
            switch (type) {
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
                default: {
                    throw new AssertionError((Object)("No NameTypeIndex match for " + type));
                }
            }
        }
        
        private static NameType getNameType(final int index) {
            switch (NameTypeIndex.values[index]) {
                case EXEMPLAR_LOCATION: {
                    return NameType.EXEMPLAR_LOCATION;
                }
                case LONG_GENERIC: {
                    return NameType.LONG_GENERIC;
                }
                case LONG_STANDARD: {
                    return NameType.LONG_STANDARD;
                }
                case LONG_DAYLIGHT: {
                    return NameType.LONG_DAYLIGHT;
                }
                case SHORT_GENERIC: {
                    return NameType.SHORT_GENERIC;
                }
                case SHORT_STANDARD: {
                    return NameType.SHORT_STANDARD;
                }
                case SHORT_DAYLIGHT: {
                    return NameType.SHORT_DAYLIGHT;
                }
                default: {
                    throw new AssertionError((Object)("No NameType match for " + index));
                }
            }
        }
        
        protected ZNames(final String[] names) {
            this._names = names;
            this.didAddIntoTrie = (names == null);
        }
        
        public static ZNames createMetaZoneAndPutInCache(final Map<String, ZNames> cache, final String[] names, final String mzID) {
            final String key = mzID.intern();
            ZNames value;
            if (names == null) {
                value = ZNames.EMPTY_ZNAMES;
            }
            else {
                value = new ZNames(names);
            }
            cache.put(key, value);
            return value;
        }
        
        public static ZNames createTimeZoneAndPutInCache(final Map<String, ZNames> cache, String[] names, final String tzID) {
            names = ((names == null) ? new String[ZNames.EX_LOC_INDEX + 1] : names);
            if (names[ZNames.EX_LOC_INDEX] == null) {
                names[ZNames.EX_LOC_INDEX] = TimeZoneNamesImpl.getDefaultExemplarLocationName(tzID);
            }
            final String key = tzID.intern();
            final ZNames value = new ZNames(names);
            cache.put(key, value);
            return value;
        }
        
        public String getName(final NameType type) {
            final int index = getNameTypeIndex(type);
            if (this._names != null && index < this._names.length) {
                return this._names[index];
            }
            return null;
        }
        
        public void addAsMetaZoneIntoTrie(final String mzID, final TextTrieMap<NameInfo> trie) {
            this.addNamesIntoTrie(mzID, null, trie);
        }
        
        public void addAsTimeZoneIntoTrie(final String tzID, final TextTrieMap<NameInfo> trie) {
            this.addNamesIntoTrie(null, tzID, trie);
        }
        
        private void addNamesIntoTrie(final String mzID, final String tzID, final TextTrieMap<NameInfo> trie) {
            if (this._names == null || this.didAddIntoTrie) {
                return;
            }
            this.didAddIntoTrie = true;
            for (int i = 0; i < this._names.length; ++i) {
                final String name = this._names[i];
                if (name != null) {
                    final NameInfo info = new NameInfo();
                    info.mzID = mzID;
                    info.tzID = tzID;
                    info.type = getNameType(i);
                    trie.put(name, info);
                }
            }
        }
        
        static {
            EMPTY_ZNAMES = new ZNames(null);
            EX_LOC_INDEX = NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
        }
        
        private enum NameTypeIndex
        {
            EXEMPLAR_LOCATION, 
            LONG_GENERIC, 
            LONG_STANDARD, 
            LONG_DAYLIGHT, 
            SHORT_GENERIC, 
            SHORT_STANDARD, 
            SHORT_DAYLIGHT;
            
            static final NameTypeIndex[] values;
            
            static {
                values = values();
            }
        }
    }
    
    private static class MZMapEntry
    {
        private String _mzID;
        private long _from;
        private long _to;
        
        MZMapEntry(final String mzID, final long from, final long to) {
            this._mzID = mzID;
            this._from = from;
            this._to = to;
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
    
    private static class TZ2MZsCache extends SoftCache<String, List<MZMapEntry>, String>
    {
        @Override
        protected List<MZMapEntry> createInstance(final String key, final String data) {
            List<MZMapEntry> mzMaps = null;
            final UResourceBundle bundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "metaZones");
            final UResourceBundle metazoneInfoBundle = bundle.get("metazoneInfo");
            final String tzkey = data.replace('/', ':');
            try {
                final UResourceBundle zoneBundle = metazoneInfoBundle.get(tzkey);
                mzMaps = new ArrayList<MZMapEntry>(zoneBundle.getSize());
                for (int idx = 0; idx < zoneBundle.getSize(); ++idx) {
                    final UResourceBundle mz = zoneBundle.get(idx);
                    final String mzid = mz.getString(0);
                    String fromStr = "1970-01-01 00:00";
                    String toStr = "9999-12-31 23:59";
                    if (mz.getSize() == 3) {
                        fromStr = mz.getString(1);
                        toStr = mz.getString(2);
                    }
                    final long from = parseDate(fromStr);
                    final long to = parseDate(toStr);
                    mzMaps.add(new MZMapEntry(mzid, from, to));
                }
            }
            catch (MissingResourceException mre) {
                mzMaps = Collections.emptyList();
            }
            return mzMaps;
        }
        
        private static long parseDate(final String text) {
            int year = 0;
            int month = 0;
            int day = 0;
            int hour = 0;
            int min = 0;
            for (int idx = 0; idx <= 3; ++idx) {
                final int n = text.charAt(idx) - '0';
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad year");
                }
                year = 10 * year + n;
            }
            for (int idx = 5; idx <= 6; ++idx) {
                final int n = text.charAt(idx) - '0';
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad month");
                }
                month = 10 * month + n;
            }
            for (int idx = 8; idx <= 9; ++idx) {
                final int n = text.charAt(idx) - '0';
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad day");
                }
                day = 10 * day + n;
            }
            for (int idx = 11; idx <= 12; ++idx) {
                final int n = text.charAt(idx) - '0';
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad hour");
                }
                hour = 10 * hour + n;
            }
            for (int idx = 14; idx <= 15; ++idx) {
                final int n = text.charAt(idx) - '0';
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad minute");
                }
                min = 10 * min + n;
            }
            final long date = Grego.fieldsToDay(year, month - 1, day) * 86400000L + hour * 3600000L + min * 60000L;
            return date;
        }
    }
    
    private static class MZ2TZsCache extends SoftCache<String, Map<String, String>, String>
    {
        @Override
        protected Map<String, String> createInstance(final String key, final String data) {
            Map<String, String> map = null;
            final UResourceBundle bundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "metaZones");
            final UResourceBundle mapTimezones = bundle.get("mapTimezones");
            try {
                final UResourceBundle regionMap = mapTimezones.get(key);
                final Set<String> regions = regionMap.keySet();
                map = new HashMap<String, String>(regions.size());
                for (final String region : regions) {
                    final String tzID = regionMap.getString(region).intern();
                    map.put(region.intern(), tzID);
                }
            }
            catch (MissingResourceException e) {
                map = Collections.emptyMap();
            }
            return map;
        }
    }
}
