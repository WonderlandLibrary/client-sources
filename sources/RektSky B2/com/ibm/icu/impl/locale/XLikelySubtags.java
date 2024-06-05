package com.ibm.icu.impl.locale;

import java.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;

public class XLikelySubtags
{
    private static final XLikelySubtags DEFAULT;
    final Map<String, Map<String, Map<String, LSR>>> langTable;
    
    public static final XLikelySubtags getDefault() {
        return XLikelySubtags.DEFAULT;
    }
    
    public XLikelySubtags() {
        this(getDefaultRawData(), true);
    }
    
    private static Map<String, String> getDefaultRawData() {
        final Map<String, String> rawData = new TreeMap<String, String>();
        final UResourceBundle bundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "likelySubtags");
        final Enumeration<String> enumer = bundle.getKeys();
        while (enumer.hasMoreElements()) {
            final String key = enumer.nextElement();
            rawData.put(key, bundle.getString(key));
        }
        return rawData;
    }
    
    public XLikelySubtags(final Map<String, String> rawData, final boolean skipNoncanonical) {
        this.langTable = this.init(rawData, skipNoncanonical);
    }
    
    private Map<String, Map<String, Map<String, LSR>>> init(final Map<String, String> rawData, final boolean skipNoncanonical) {
        final Maker maker = Maker.TREEMAP;
        final Map<String, Map<String, Map<String, LSR>>> result = maker.make();
        final Map<LSR, LSR> internCache = new HashMap<LSR, LSR>();
        for (final Map.Entry<String, String> sourceTarget : rawData.entrySet()) {
            LSR ltp = LSR.from(sourceTarget.getKey());
            final String language = ltp.language;
            final String script = ltp.script;
            final String region = ltp.region;
            ltp = LSR.from(sourceTarget.getValue());
            final String languageTarget = ltp.language;
            final String scriptTarget = ltp.script;
            final String regionTarget = ltp.region;
            this.set(result, language, script, region, languageTarget, scriptTarget, regionTarget, internCache);
            final Collection<String> languageAliases = LSR.LANGUAGE_ALIASES.getAliases(language);
            final Collection<String> regionAliases = LSR.REGION_ALIASES.getAliases(region);
            for (final String languageAlias : languageAliases) {
                for (final String regionAlias : regionAliases) {
                    if (languageAlias.equals(language) && regionAlias.equals(region)) {
                        continue;
                    }
                    this.set(result, languageAlias, script, regionAlias, languageTarget, scriptTarget, regionTarget, internCache);
                }
            }
        }
        this.set(result, "und", "Latn", "", "en", "Latn", "US", internCache);
        final Map<String, Map<String, LSR>> undScriptMap = result.get("und");
        final Map<String, LSR> undEmptyRegionMap = undScriptMap.get("");
        for (final Map.Entry<String, LSR> regionEntry : undEmptyRegionMap.entrySet()) {
            final LSR value = regionEntry.getValue();
            this.set(result, "und", value.script, value.region, value);
        }
        if (!result.containsKey("und")) {
            throw new IllegalArgumentException("failure: base");
        }
        for (final Map.Entry<String, Map<String, Map<String, LSR>>> langEntry : result.entrySet()) {
            final String lang = langEntry.getKey();
            final Map<String, Map<String, LSR>> scriptMap = langEntry.getValue();
            if (!scriptMap.containsKey("")) {
                throw new IllegalArgumentException("failure: " + lang);
            }
            for (final Map.Entry<String, Map<String, LSR>> scriptEntry : scriptMap.entrySet()) {
                final String script2 = scriptEntry.getKey();
                final Map<String, LSR> regionMap = scriptEntry.getValue();
                if (!regionMap.containsKey("")) {
                    throw new IllegalArgumentException("failure: " + lang + "-" + script2);
                }
            }
        }
        return result;
    }
    
    private void set(final Map<String, Map<String, Map<String, LSR>>> langTable, final String language, final String script, final String region, final String languageTarget, final String scriptTarget, final String regionTarget, final Map<LSR, LSR> internCache) {
        final LSR newValue = new LSR(languageTarget, scriptTarget, regionTarget);
        LSR oldValue = internCache.get(newValue);
        if (oldValue == null) {
            internCache.put(newValue, newValue);
            oldValue = newValue;
        }
        this.set(langTable, language, script, region, oldValue);
    }
    
    private void set(final Map<String, Map<String, Map<String, LSR>>> langTable, final String language, final String script, final String region, final LSR newValue) {
        final Map<String, Map<String, LSR>> scriptTable = Maker.TREEMAP.getSubtable(langTable, language);
        final Map<String, LSR> regionTable = Maker.TREEMAP.getSubtable(scriptTable, script);
        regionTable.put(region, newValue);
    }
    
    public LSR maximize(final String source) {
        return this.maximize(ULocale.forLanguageTag(source));
    }
    
    public LSR maximize(final ULocale source) {
        return this.maximize(source.getLanguage(), source.getScript(), source.getCountry());
    }
    
    public LSR maximize(final LSR source) {
        return this.maximize(source.language, source.script, source.region);
    }
    
    public LSR maximize(final String language, String script, String region) {
        int retainOldMask = 0;
        Map<String, Map<String, LSR>> scriptTable = this.langTable.get(language);
        if (scriptTable == null) {
            retainOldMask |= 0x4;
            scriptTable = this.langTable.get("und");
        }
        else if (!language.equals("und")) {
            retainOldMask |= 0x4;
        }
        if (script.equals("Zzzz")) {
            script = "";
        }
        Map<String, LSR> regionTable = scriptTable.get(script);
        if (regionTable == null) {
            retainOldMask |= 0x2;
            regionTable = scriptTable.get("");
        }
        else if (!script.isEmpty()) {
            retainOldMask |= 0x2;
        }
        if (region.equals("ZZ")) {
            region = "";
        }
        LSR result = regionTable.get(region);
        if (result == null) {
            retainOldMask |= 0x1;
            result = regionTable.get("");
            if (result == null) {
                return null;
            }
        }
        else if (!region.isEmpty()) {
            retainOldMask |= 0x1;
        }
        switch (retainOldMask) {
            default: {
                return result;
            }
            case 1: {
                return result.replace(null, null, region);
            }
            case 2: {
                return result.replace(null, script, null);
            }
            case 3: {
                return result.replace(null, script, region);
            }
            case 4: {
                return result.replace(language, null, null);
            }
            case 5: {
                return result.replace(language, null, region);
            }
            case 6: {
                return result.replace(language, script, null);
            }
            case 7: {
                return result.replace(language, script, region);
            }
        }
    }
    
    private LSR minimizeSubtags(final String languageIn, final String scriptIn, final String regionIn, final ULocale.Minimize fieldToFavor) {
        final LSR result = this.maximize(languageIn, scriptIn, regionIn);
        final Map<String, Map<String, LSR>> scriptTable = this.langTable.get(result.language);
        final Map<String, LSR> regionTable0 = scriptTable.get("");
        final LSR value00 = regionTable0.get("");
        boolean favorRegionOk = false;
        if (result.script.equals(value00.script)) {
            if (result.region.equals(value00.region)) {
                return result.replace(null, "", "");
            }
            if (fieldToFavor == ULocale.Minimize.FAVOR_REGION) {
                return result.replace(null, "", null);
            }
            favorRegionOk = true;
        }
        final LSR result2 = this.maximize(languageIn, scriptIn, "");
        if (result2.equals(result)) {
            return result.replace(null, null, "");
        }
        if (favorRegionOk) {
            return result.replace(null, "", null);
        }
        return result;
    }
    
    private static StringBuilder show(final Map<?, ?> map, final String indent, final StringBuilder output) {
        String first = indent.isEmpty() ? "" : "\t";
        for (final Map.Entry<?, ?> e : map.entrySet()) {
            final String key = e.getKey().toString();
            final Object value = e.getValue();
            output.append(first + (key.isEmpty() ? "\u2205" : key));
            if (value instanceof Map) {
                show((Map<?, ?>)value, indent + "\t", output);
            }
            else {
                output.append("\t" + Utility.toString(value)).append("\n");
            }
            first = indent;
        }
        return output;
    }
    
    @Override
    public String toString() {
        return show(this.langTable, "", new StringBuilder()).toString();
    }
    
    static {
        DEFAULT = new XLikelySubtags();
    }
    
    abstract static class Maker
    {
        static final Maker HASHMAP;
        static final Maker TREEMAP;
        
        abstract <V> V make();
        
        public <K, V> V getSubtable(final Map<K, V> langTable, final K language) {
            V scriptTable = langTable.get(language);
            if (scriptTable == null) {
                langTable.put(language, scriptTable = this.make());
            }
            return scriptTable;
        }
        
        static {
            HASHMAP = new Maker() {
                public Map<Object, Object> make() {
                    return new HashMap<Object, Object>();
                }
            };
            TREEMAP = new Maker() {
                public Map<Object, Object> make() {
                    return new TreeMap<Object, Object>();
                }
            };
        }
    }
    
    public static class Aliases
    {
        final Map<String, String> toCanonical;
        final XCldrStub.Multimap<String, String> toAliases;
        
        public String getCanonical(final String alias) {
            final String canonical = this.toCanonical.get(alias);
            return (canonical == null) ? alias : canonical;
        }
        
        public Set<String> getAliases(final String canonical) {
            final Set<String> aliases = this.toAliases.get(canonical);
            return (aliases == null) ? Collections.singleton(canonical) : aliases;
        }
        
        public Aliases(final String key) {
            final UResourceBundle metadata = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            final UResourceBundle metadataAlias = metadata.get("alias");
            final UResourceBundle territoryAlias = metadataAlias.get(key);
            final Map<String, String> toCanonical1 = new HashMap<String, String>();
            for (int i = 0; i < territoryAlias.getSize(); ++i) {
                final UResourceBundle res = territoryAlias.get(i);
                final String aliasFrom = res.getKey();
                if (!aliasFrom.contains("_")) {
                    final String aliasReason = res.get("reason").getString();
                    if (!aliasReason.equals("overlong")) {
                        final String aliasTo = res.get("replacement").getString();
                        final int spacePos = aliasTo.indexOf(32);
                        final String aliasFirst = (spacePos < 0) ? aliasTo : aliasTo.substring(0, spacePos);
                        if (!aliasFirst.contains("_")) {
                            toCanonical1.put(aliasFrom, aliasFirst);
                        }
                    }
                }
            }
            if (key.equals("language")) {
                toCanonical1.put("mo", "ro");
            }
            this.toCanonical = Collections.unmodifiableMap((Map<? extends String, ? extends String>)toCanonical1);
            this.toAliases = XCldrStub.Multimaps.invertFrom(toCanonical1, (XCldrStub.Multimap<String, String>)XCldrStub.HashMultimap.create());
        }
    }
    
    public static class LSR
    {
        public final String language;
        public final String script;
        public final String region;
        public static Aliases LANGUAGE_ALIASES;
        public static Aliases REGION_ALIASES;
        
        public static LSR from(final String language, final String script, final String region) {
            return new LSR(language, script, region);
        }
        
        static LSR from(final String languageIdentifier) {
            final String[] parts = languageIdentifier.split("[-_]");
            if (parts.length < 1 || parts.length > 3) {
                throw new ICUException("too many subtags");
            }
            final String lang = parts[0].toLowerCase();
            final String p2 = (parts.length < 2) ? "" : parts[1];
            final String p3 = (parts.length < 3) ? "" : parts[2];
            return (p2.length() < 4) ? new LSR(lang, "", p2) : new LSR(lang, p2, p3);
        }
        
        public static LSR from(final ULocale locale) {
            return new LSR(locale.getLanguage(), locale.getScript(), locale.getCountry());
        }
        
        public static LSR fromMaximalized(final ULocale locale) {
            return fromMaximalized(locale.getLanguage(), locale.getScript(), locale.getCountry());
        }
        
        public static LSR fromMaximalized(final String language, final String script, final String region) {
            final String canonicalLanguage = LSR.LANGUAGE_ALIASES.getCanonical(language);
            final String canonicalRegion = LSR.REGION_ALIASES.getCanonical(region);
            return XLikelySubtags.DEFAULT.maximize(canonicalLanguage, script, canonicalRegion);
        }
        
        public LSR(final String language, final String script, final String region) {
            this.language = language;
            this.script = script;
            this.region = region;
        }
        
        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder(this.language);
            if (!this.script.isEmpty()) {
                result.append('-').append(this.script);
            }
            if (!this.region.isEmpty()) {
                result.append('-').append(this.region);
            }
            return result.toString();
        }
        
        public LSR replace(final String language2, final String script2, final String region2) {
            if (language2 == null && script2 == null && region2 == null) {
                return this;
            }
            return new LSR((language2 == null) ? this.language : language2, (script2 == null) ? this.script : script2, (region2 == null) ? this.region : region2);
        }
        
        @Override
        public boolean equals(final Object obj) {
            final LSR other;
            return this == obj || (obj != null && obj.getClass() == this.getClass() && this.language.equals((other = (LSR)obj).language) && this.script.equals(other.script) && this.region.equals(other.region));
        }
        
        @Override
        public int hashCode() {
            return Utility.hash(this.language, this.script, this.region);
        }
        
        static {
            LSR.LANGUAGE_ALIASES = new Aliases("language");
            LSR.REGION_ALIASES = new Aliases("territory");
        }
    }
}
