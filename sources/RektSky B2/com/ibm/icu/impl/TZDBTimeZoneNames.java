package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import java.util.concurrent.*;
import com.ibm.icu.util.*;
import java.util.*;

public class TZDBTimeZoneNames extends TimeZoneNames
{
    private static final long serialVersionUID = 1L;
    private static final ConcurrentHashMap<String, TZDBNames> TZDB_NAMES_MAP;
    private static volatile TextTrieMap<TZDBNameInfo> TZDB_NAMES_TRIE;
    private static final ICUResourceBundle ZONESTRINGS;
    private ULocale _locale;
    private transient volatile String _region;
    
    public TZDBTimeZoneNames(final ULocale loc) {
        this._locale = loc;
    }
    
    @Override
    public Set<String> getAvailableMetaZoneIDs() {
        return TimeZoneNamesImpl._getAvailableMetaZoneIDs();
    }
    
    @Override
    public Set<String> getAvailableMetaZoneIDs(final String tzID) {
        return TimeZoneNamesImpl._getAvailableMetaZoneIDs(tzID);
    }
    
    @Override
    public String getMetaZoneID(final String tzID, final long date) {
        return TimeZoneNamesImpl._getMetaZoneID(tzID, date);
    }
    
    @Override
    public String getReferenceZoneID(final String mzID, final String region) {
        return TimeZoneNamesImpl._getReferenceZoneID(mzID, region);
    }
    
    @Override
    public String getMetaZoneDisplayName(final String mzID, final NameType type) {
        if (mzID == null || mzID.length() == 0 || (type != NameType.SHORT_STANDARD && type != NameType.SHORT_DAYLIGHT)) {
            return null;
        }
        return getMetaZoneNames(mzID).getName(type);
    }
    
    @Override
    public String getTimeZoneDisplayName(final String tzID, final NameType type) {
        return null;
    }
    
    @Override
    public Collection<MatchInfo> find(final CharSequence text, final int start, final EnumSet<NameType> nameTypes) {
        if (text == null || text.length() == 0 || start < 0 || start >= text.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        prepareFind();
        final TZDBNameSearchHandler handler = new TZDBNameSearchHandler(nameTypes, this.getTargetRegion());
        TZDBTimeZoneNames.TZDB_NAMES_TRIE.find(text, start, handler);
        return handler.getMatches();
    }
    
    private static TZDBNames getMetaZoneNames(String mzID) {
        TZDBNames names = TZDBTimeZoneNames.TZDB_NAMES_MAP.get(mzID);
        if (names == null) {
            names = TZDBNames.getInstance(TZDBTimeZoneNames.ZONESTRINGS, "meta:" + mzID);
            mzID = mzID.intern();
            final TZDBNames tmpNames = TZDBTimeZoneNames.TZDB_NAMES_MAP.putIfAbsent(mzID, names);
            names = ((tmpNames == null) ? names : tmpNames);
        }
        return names;
    }
    
    private static void prepareFind() {
        if (TZDBTimeZoneNames.TZDB_NAMES_TRIE == null) {
            synchronized (TZDBTimeZoneNames.class) {
                if (TZDBTimeZoneNames.TZDB_NAMES_TRIE == null) {
                    final TextTrieMap<TZDBNameInfo> trie = new TextTrieMap<TZDBNameInfo>(true);
                    final Set<String> mzIDs = TimeZoneNamesImpl._getAvailableMetaZoneIDs();
                    for (String mzID : mzIDs) {
                        final TZDBNames names = getMetaZoneNames(mzID);
                        final String std = names.getName(NameType.SHORT_STANDARD);
                        final String dst = names.getName(NameType.SHORT_DAYLIGHT);
                        if (std == null && dst == null) {
                            continue;
                        }
                        final String[] parseRegions = names.getParseRegions();
                        mzID = mzID.intern();
                        final boolean ambiguousType = std != null && dst != null && std.equals(dst);
                        if (std != null) {
                            final TZDBNameInfo stdInf = new TZDBNameInfo(mzID, NameType.SHORT_STANDARD, ambiguousType, parseRegions);
                            trie.put(std, stdInf);
                        }
                        if (dst == null) {
                            continue;
                        }
                        final TZDBNameInfo dstInf = new TZDBNameInfo(mzID, NameType.SHORT_DAYLIGHT, ambiguousType, parseRegions);
                        trie.put(dst, dstInf);
                    }
                    TZDBTimeZoneNames.TZDB_NAMES_TRIE = trie;
                }
            }
        }
    }
    
    private String getTargetRegion() {
        if (this._region == null) {
            String region = this._locale.getCountry();
            if (region.length() == 0) {
                final ULocale tmp = ULocale.addLikelySubtags(this._locale);
                region = tmp.getCountry();
                if (region.length() == 0) {
                    region = "001";
                }
            }
            this._region = region;
        }
        return this._region;
    }
    
    static {
        TZDB_NAMES_MAP = new ConcurrentHashMap<String, TZDBNames>();
        TZDBTimeZoneNames.TZDB_NAMES_TRIE = null;
        final UResourceBundle bundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/zone", "tzdbNames");
        ZONESTRINGS = (ICUResourceBundle)bundle.get("zoneStrings");
    }
    
    private static class TZDBNames
    {
        public static final TZDBNames EMPTY_TZDBNAMES;
        private String[] _names;
        private String[] _parseRegions;
        private static final String[] KEYS;
        
        private TZDBNames(final String[] names, final String[] parseRegions) {
            this._names = names;
            this._parseRegions = parseRegions;
        }
        
        static TZDBNames getInstance(final ICUResourceBundle zoneStrings, final String key) {
            if (zoneStrings == null || key == null || key.length() == 0) {
                return TZDBNames.EMPTY_TZDBNAMES;
            }
            ICUResourceBundle table = null;
            try {
                table = (ICUResourceBundle)zoneStrings.get(key);
            }
            catch (MissingResourceException e) {
                return TZDBNames.EMPTY_TZDBNAMES;
            }
            boolean isEmpty = true;
            final String[] names = new String[TZDBNames.KEYS.length];
            for (int i = 0; i < names.length; ++i) {
                try {
                    names[i] = table.getString(TZDBNames.KEYS[i]);
                    isEmpty = false;
                }
                catch (MissingResourceException e2) {
                    names[i] = null;
                }
            }
            if (isEmpty) {
                return TZDBNames.EMPTY_TZDBNAMES;
            }
            String[] parseRegions = null;
            try {
                final ICUResourceBundle regionsRes = (ICUResourceBundle)table.get("parseRegions");
                if (regionsRes.getType() == 0) {
                    parseRegions = new String[] { regionsRes.getString() };
                }
                else if (regionsRes.getType() == 8) {
                    parseRegions = regionsRes.getStringArray();
                }
            }
            catch (MissingResourceException ex) {}
            return new TZDBNames(names, parseRegions);
        }
        
        String getName(final NameType type) {
            if (this._names == null) {
                return null;
            }
            String name = null;
            switch (type) {
                case SHORT_STANDARD: {
                    name = this._names[0];
                    break;
                }
                case SHORT_DAYLIGHT: {
                    name = this._names[1];
                    break;
                }
            }
            return name;
        }
        
        String[] getParseRegions() {
            return this._parseRegions;
        }
        
        static {
            EMPTY_TZDBNAMES = new TZDBNames(null, null);
            KEYS = new String[] { "ss", "sd" };
        }
    }
    
    private static class TZDBNameInfo
    {
        final String mzID;
        final NameType type;
        final boolean ambiguousType;
        final String[] parseRegions;
        
        TZDBNameInfo(final String mzID, final NameType type, final boolean ambiguousType, final String[] parseRegions) {
            this.mzID = mzID;
            this.type = type;
            this.ambiguousType = ambiguousType;
            this.parseRegions = parseRegions;
        }
    }
    
    private static class TZDBNameSearchHandler implements TextTrieMap.ResultHandler<TZDBNameInfo>
    {
        private EnumSet<NameType> _nameTypes;
        private Collection<MatchInfo> _matches;
        private String _region;
        
        TZDBNameSearchHandler(final EnumSet<NameType> nameTypes, final String region) {
            this._nameTypes = nameTypes;
            assert region != null;
            this._region = region;
        }
        
        @Override
        public boolean handlePrefixMatch(final int matchLength, final Iterator<TZDBNameInfo> values) {
            TZDBNameInfo match = null;
            TZDBNameInfo defaultRegionMatch = null;
            while (values.hasNext()) {
                final TZDBNameInfo ninfo = values.next();
                if (this._nameTypes != null && !this._nameTypes.contains(ninfo.type)) {
                    continue;
                }
                if (ninfo.parseRegions == null) {
                    if (defaultRegionMatch != null) {
                        continue;
                    }
                    defaultRegionMatch = (match = ninfo);
                }
                else {
                    boolean matchRegion = false;
                    for (final String region : ninfo.parseRegions) {
                        if (this._region.equals(region)) {
                            match = ninfo;
                            matchRegion = true;
                            break;
                        }
                    }
                    if (matchRegion) {
                        break;
                    }
                    if (match != null) {
                        continue;
                    }
                    match = ninfo;
                }
            }
            if (match != null) {
                NameType ntype = match.type;
                if (match.ambiguousType && (ntype == NameType.SHORT_STANDARD || ntype == NameType.SHORT_DAYLIGHT) && this._nameTypes.contains(NameType.SHORT_STANDARD) && this._nameTypes.contains(NameType.SHORT_DAYLIGHT)) {
                    ntype = NameType.SHORT_GENERIC;
                }
                final MatchInfo minfo = new MatchInfo(ntype, null, match.mzID, matchLength);
                if (this._matches == null) {
                    this._matches = new LinkedList<MatchInfo>();
                }
                this._matches.add(minfo);
            }
            return true;
        }
        
        public Collection<MatchInfo> getMatches() {
            if (this._matches == null) {
                return (Collection<MatchInfo>)Collections.emptyList();
            }
            return this._matches;
        }
    }
}
