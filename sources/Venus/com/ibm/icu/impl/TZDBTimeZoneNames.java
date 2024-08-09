/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.impl.TimeZoneNamesImpl;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TZDBTimeZoneNames
extends TimeZoneNames {
    private static final long serialVersionUID = 1L;
    private static final ConcurrentHashMap<String, TZDBNames> TZDB_NAMES_MAP = new ConcurrentHashMap();
    private static volatile TextTrieMap<TZDBNameInfo> TZDB_NAMES_TRIE = null;
    private static final ICUResourceBundle ZONESTRINGS;
    private ULocale _locale;
    private volatile transient String _region;

    public TZDBTimeZoneNames(ULocale uLocale) {
        this._locale = uLocale;
    }

    @Override
    public Set<String> getAvailableMetaZoneIDs() {
        return TimeZoneNamesImpl._getAvailableMetaZoneIDs();
    }

    @Override
    public Set<String> getAvailableMetaZoneIDs(String string) {
        return TimeZoneNamesImpl._getAvailableMetaZoneIDs(string);
    }

    @Override
    public String getMetaZoneID(String string, long l) {
        return TimeZoneNamesImpl._getMetaZoneID(string, l);
    }

    @Override
    public String getReferenceZoneID(String string, String string2) {
        return TimeZoneNamesImpl._getReferenceZoneID(string, string2);
    }

    @Override
    public String getMetaZoneDisplayName(String string, TimeZoneNames.NameType nameType) {
        if (string == null || string.length() == 0 || nameType != TimeZoneNames.NameType.SHORT_STANDARD && nameType != TimeZoneNames.NameType.SHORT_DAYLIGHT) {
            return null;
        }
        return TZDBTimeZoneNames.getMetaZoneNames(string).getName(nameType);
    }

    @Override
    public String getTimeZoneDisplayName(String string, TimeZoneNames.NameType nameType) {
        return null;
    }

    @Override
    public Collection<TimeZoneNames.MatchInfo> find(CharSequence charSequence, int n, EnumSet<TimeZoneNames.NameType> enumSet) {
        if (charSequence == null || charSequence.length() == 0 || n < 0 || n >= charSequence.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        TZDBTimeZoneNames.prepareFind();
        TZDBNameSearchHandler tZDBNameSearchHandler = new TZDBNameSearchHandler(enumSet, this.getTargetRegion());
        TZDB_NAMES_TRIE.find(charSequence, n, tZDBNameSearchHandler);
        return tZDBNameSearchHandler.getMatches();
    }

    private static TZDBNames getMetaZoneNames(String string) {
        TZDBNames tZDBNames = TZDB_NAMES_MAP.get(string);
        if (tZDBNames == null) {
            tZDBNames = TZDBNames.getInstance(ZONESTRINGS, "meta:" + string);
            TZDBNames tZDBNames2 = TZDB_NAMES_MAP.putIfAbsent(string = string.intern(), tZDBNames);
            tZDBNames = tZDBNames2 == null ? tZDBNames : tZDBNames2;
        }
        return tZDBNames;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void prepareFind() {
        if (TZDB_NAMES_TRIE != null) return;
        Class<TZDBTimeZoneNames> clazz = TZDBTimeZoneNames.class;
        synchronized (TZDBTimeZoneNames.class) {
            if (TZDB_NAMES_TRIE != null) return;
            TextTrieMap<TZDBNameInfo> textTrieMap = new TextTrieMap<TZDBNameInfo>(true);
            Set<String> set = TimeZoneNamesImpl._getAvailableMetaZoneIDs();
            for (String string : set) {
                TZDBNameInfo tZDBNameInfo;
                boolean bl;
                TZDBNames tZDBNames = TZDBTimeZoneNames.getMetaZoneNames(string);
                String string2 = tZDBNames.getName(TimeZoneNames.NameType.SHORT_STANDARD);
                String string3 = tZDBNames.getName(TimeZoneNames.NameType.SHORT_DAYLIGHT);
                if (string2 == null && string3 == null) continue;
                String[] stringArray = tZDBNames.getParseRegions();
                string = string.intern();
                boolean bl2 = bl = string2 != null && string3 != null && string2.equals(string3);
                if (string2 != null) {
                    tZDBNameInfo = new TZDBNameInfo(string, TimeZoneNames.NameType.SHORT_STANDARD, bl, stringArray);
                    textTrieMap.put(string2, tZDBNameInfo);
                }
                if (string3 == null) continue;
                tZDBNameInfo = new TZDBNameInfo(string, TimeZoneNames.NameType.SHORT_DAYLIGHT, bl, stringArray);
                textTrieMap.put(string3, tZDBNameInfo);
            }
            TZDB_NAMES_TRIE = textTrieMap;
            // ** MonitorExit[var0] (shouldn't be in output)
            return;
        }
    }

    private String getTargetRegion() {
        if (this._region == null) {
            ULocale uLocale;
            String string = this._locale.getCountry();
            if (string.length() == 0 && (string = (uLocale = ULocale.addLikelySubtags(this._locale)).getCountry()).length() == 0) {
                string = "001";
            }
            this._region = string;
        }
        return this._region;
    }

    static {
        UResourceBundle uResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/zone", "tzdbNames");
        ZONESTRINGS = (ICUResourceBundle)uResourceBundle.get("zoneStrings");
    }

    private static class TZDBNameSearchHandler
    implements TextTrieMap.ResultHandler<TZDBNameInfo> {
        private EnumSet<TimeZoneNames.NameType> _nameTypes;
        private Collection<TimeZoneNames.MatchInfo> _matches;
        private String _region;
        static final boolean $assertionsDisabled = !TZDBTimeZoneNames.class.desiredAssertionStatus();

        TZDBNameSearchHandler(EnumSet<TimeZoneNames.NameType> enumSet, String string) {
            this._nameTypes = enumSet;
            if (!$assertionsDisabled && string == null) {
                throw new AssertionError();
            }
            this._region = string;
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<TZDBNameInfo> iterator2) {
            Object object;
            Object object2 = null;
            Object object3 = null;
            while (iterator2.hasNext()) {
                object = iterator2.next();
                if (this._nameTypes != null && !this._nameTypes.contains((Object)object.type)) continue;
                if (object.parseRegions == null) {
                    if (object3 != null) continue;
                    object2 = object3 = object;
                    continue;
                }
                boolean bl = false;
                for (String string : object.parseRegions) {
                    if (!this._region.equals(string)) continue;
                    object2 = object;
                    bl = true;
                    break;
                }
                if (bl) break;
                if (object2 != null) continue;
                object2 = object;
            }
            if (object2 != null) {
                object = object2.type;
                if (object2.ambiguousType && (object == TimeZoneNames.NameType.SHORT_STANDARD || object == TimeZoneNames.NameType.SHORT_DAYLIGHT) && this._nameTypes.contains((Object)TimeZoneNames.NameType.SHORT_STANDARD) && this._nameTypes.contains((Object)TimeZoneNames.NameType.SHORT_DAYLIGHT)) {
                    object = TimeZoneNames.NameType.SHORT_GENERIC;
                }
                TimeZoneNames.MatchInfo matchInfo = new TimeZoneNames.MatchInfo((TimeZoneNames.NameType)((Object)object), null, object2.mzID, n);
                if (this._matches == null) {
                    this._matches = new LinkedList<TimeZoneNames.MatchInfo>();
                }
                this._matches.add(matchInfo);
            }
            return false;
        }

        public Collection<TimeZoneNames.MatchInfo> getMatches() {
            if (this._matches == null) {
                return Collections.emptyList();
            }
            return this._matches;
        }
    }

    private static class TZDBNameInfo {
        final String mzID;
        final TimeZoneNames.NameType type;
        final boolean ambiguousType;
        final String[] parseRegions;

        TZDBNameInfo(String string, TimeZoneNames.NameType nameType, boolean bl, String[] stringArray) {
            this.mzID = string;
            this.type = nameType;
            this.ambiguousType = bl;
            this.parseRegions = stringArray;
        }
    }

    private static class TZDBNames {
        public static final TZDBNames EMPTY_TZDBNAMES = new TZDBNames(null, null);
        private String[] _names;
        private String[] _parseRegions;
        private static final String[] KEYS = new String[]{"ss", "sd"};

        private TZDBNames(String[] stringArray, String[] stringArray2) {
            this._names = stringArray;
            this._parseRegions = stringArray2;
        }

        static TZDBNames getInstance(ICUResourceBundle iCUResourceBundle, String string) {
            if (iCUResourceBundle == null || string == null || string.length() == 0) {
                return EMPTY_TZDBNAMES;
            }
            ICUResourceBundle iCUResourceBundle2 = null;
            try {
                iCUResourceBundle2 = (ICUResourceBundle)iCUResourceBundle.get(string);
            } catch (MissingResourceException missingResourceException) {
                return EMPTY_TZDBNAMES;
            }
            boolean bl = true;
            String[] stringArray = new String[KEYS.length];
            for (int i = 0; i < stringArray.length; ++i) {
                try {
                    stringArray[i] = iCUResourceBundle2.getString(KEYS[i]);
                    bl = false;
                    continue;
                } catch (MissingResourceException missingResourceException) {
                    stringArray[i] = null;
                }
            }
            if (bl) {
                return EMPTY_TZDBNAMES;
            }
            String[] stringArray2 = null;
            try {
                ICUResourceBundle iCUResourceBundle3 = (ICUResourceBundle)iCUResourceBundle2.get("parseRegions");
                if (iCUResourceBundle3.getType() == 0) {
                    stringArray2 = new String[]{iCUResourceBundle3.getString()};
                } else if (iCUResourceBundle3.getType() == 8) {
                    stringArray2 = iCUResourceBundle3.getStringArray();
                }
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            return new TZDBNames(stringArray, stringArray2);
        }

        String getName(TimeZoneNames.NameType nameType) {
            if (this._names == null) {
                return null;
            }
            String string = null;
            switch (1.$SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[nameType.ordinal()]) {
                case 1: {
                    string = this._names[0];
                    break;
                }
                case 2: {
                    string = this._names[1];
                    break;
                }
            }
            return string;
        }

        String[] getParseRegions() {
            return this._parseRegions;
        }
    }
}

