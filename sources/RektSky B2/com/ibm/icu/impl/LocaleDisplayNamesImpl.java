package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import com.ibm.icu.lang.*;
import com.ibm.icu.impl.locale.*;
import java.util.*;

public class LocaleDisplayNamesImpl extends LocaleDisplayNames
{
    private final ULocale locale;
    private final DialectHandling dialectHandling;
    private final DisplayContext capitalization;
    private final DisplayContext nameLength;
    private final DisplayContext substituteHandling;
    private final DataTable langData;
    private final DataTable regionData;
    private final String separatorFormat;
    private final String format;
    private final String keyTypeFormat;
    private final char formatOpenParen;
    private final char formatReplaceOpenParen;
    private final char formatCloseParen;
    private final char formatReplaceCloseParen;
    private final CurrencyData.CurrencyDisplayInfo currencyDisplayInfo;
    private static final Cache cache;
    private boolean[] capitalizationUsage;
    private static final Map<String, CapitalizationContextUsage> contextUsageTypeMap;
    private transient BreakIterator capitalizationBrkIter;
    private static final CaseMap.Title TO_TITLE_WHOLE_STRING_NO_LOWERCASE;
    
    private static String toTitleWholeStringNoLowercase(final ULocale locale, final String s) {
        return LocaleDisplayNamesImpl.TO_TITLE_WHOLE_STRING_NO_LOWERCASE.apply(locale.toLocale(), null, s);
    }
    
    public static LocaleDisplayNames getInstance(final ULocale locale, final DialectHandling dialectHandling) {
        synchronized (LocaleDisplayNamesImpl.cache) {
            return LocaleDisplayNamesImpl.cache.get(locale, dialectHandling);
        }
    }
    
    public static LocaleDisplayNames getInstance(final ULocale locale, final DisplayContext... contexts) {
        synchronized (LocaleDisplayNamesImpl.cache) {
            return LocaleDisplayNamesImpl.cache.get(locale, contexts);
        }
    }
    
    public LocaleDisplayNamesImpl(final ULocale locale, final DialectHandling dialectHandling) {
        this(locale, new DisplayContext[] { (dialectHandling == DialectHandling.STANDARD_NAMES) ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES, DisplayContext.CAPITALIZATION_NONE });
    }
    
    public LocaleDisplayNamesImpl(final ULocale locale, final DisplayContext... contexts) {
        this.capitalizationUsage = null;
        this.capitalizationBrkIter = null;
        DialectHandling dialectHandling = DialectHandling.STANDARD_NAMES;
        DisplayContext capitalization = DisplayContext.CAPITALIZATION_NONE;
        DisplayContext nameLength = DisplayContext.LENGTH_FULL;
        DisplayContext substituteHandling = DisplayContext.SUBSTITUTE;
        for (final DisplayContext contextItem : contexts) {
            switch (contextItem.type()) {
                case DIALECT_HANDLING: {
                    dialectHandling = ((contextItem.value() == DisplayContext.STANDARD_NAMES.value()) ? DialectHandling.STANDARD_NAMES : DialectHandling.DIALECT_NAMES);
                    break;
                }
                case CAPITALIZATION: {
                    capitalization = contextItem;
                    break;
                }
                case DISPLAY_LENGTH: {
                    nameLength = contextItem;
                    break;
                }
                case SUBSTITUTE_HANDLING: {
                    substituteHandling = contextItem;
                    break;
                }
            }
        }
        this.dialectHandling = dialectHandling;
        this.capitalization = capitalization;
        this.nameLength = nameLength;
        this.substituteHandling = substituteHandling;
        this.langData = LangDataTables.impl.get(locale, substituteHandling == DisplayContext.NO_SUBSTITUTE);
        this.regionData = RegionDataTables.impl.get(locale, substituteHandling == DisplayContext.NO_SUBSTITUTE);
        this.locale = (ULocale.ROOT.equals(this.langData.getLocale()) ? this.regionData.getLocale() : this.langData.getLocale());
        String sep = this.langData.get("localeDisplayPattern", "separator");
        if (sep == null || "separator".equals(sep)) {
            sep = "{0}, {1}";
        }
        final StringBuilder sb = new StringBuilder();
        this.separatorFormat = SimpleFormatterImpl.compileToStringMinMaxArguments(sep, sb, 2, 2);
        String pattern = this.langData.get("localeDisplayPattern", "pattern");
        if (pattern == null || "pattern".equals(pattern)) {
            pattern = "{0} ({1})";
        }
        this.format = SimpleFormatterImpl.compileToStringMinMaxArguments(pattern, sb, 2, 2);
        if (pattern.contains("\uff08")) {
            this.formatOpenParen = '\uff08';
            this.formatCloseParen = '\uff09';
            this.formatReplaceOpenParen = '\uff3b';
            this.formatReplaceCloseParen = '\uff3d';
        }
        else {
            this.formatOpenParen = '(';
            this.formatCloseParen = ')';
            this.formatReplaceOpenParen = '[';
            this.formatReplaceCloseParen = ']';
        }
        String keyTypePattern = this.langData.get("localeDisplayPattern", "keyTypePattern");
        if (keyTypePattern == null || "keyTypePattern".equals(keyTypePattern)) {
            keyTypePattern = "{0}={1}";
        }
        this.keyTypeFormat = SimpleFormatterImpl.compileToStringMinMaxArguments(keyTypePattern, sb, 2, 2);
        boolean needBrkIter = false;
        if (capitalization == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || capitalization == DisplayContext.CAPITALIZATION_FOR_STANDALONE) {
            this.capitalizationUsage = new boolean[CapitalizationContextUsage.values().length];
            final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", locale);
            final CapitalizationContextSink sink = new CapitalizationContextSink();
            try {
                rb.getAllItemsWithFallback("contextTransforms", sink);
            }
            catch (MissingResourceException ex) {}
            needBrkIter = sink.hasCapitalizationUsage;
        }
        if (needBrkIter || capitalization == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(locale);
        }
        this.currencyDisplayInfo = CurrencyData.provider.getInstance(locale, false);
    }
    
    @Override
    public ULocale getLocale() {
        return this.locale;
    }
    
    @Override
    public DialectHandling getDialectHandling() {
        return this.dialectHandling;
    }
    
    @Override
    public DisplayContext getContext(final DisplayContext.Type type) {
        DisplayContext result = null;
        switch (type) {
            case DIALECT_HANDLING: {
                result = ((this.dialectHandling == DialectHandling.STANDARD_NAMES) ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES);
                break;
            }
            case CAPITALIZATION: {
                result = this.capitalization;
                break;
            }
            case DISPLAY_LENGTH: {
                result = this.nameLength;
                break;
            }
            case SUBSTITUTE_HANDLING: {
                result = this.substituteHandling;
                break;
            }
            default: {
                result = DisplayContext.STANDARD_NAMES;
                break;
            }
        }
        return result;
    }
    
    private String adjustForUsageAndContext(final CapitalizationContextUsage usage, final String name) {
        if (name != null && name.length() > 0 && UCharacter.isLowerCase(name.codePointAt(0)) && (this.capitalization == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || (this.capitalizationUsage != null && this.capitalizationUsage[usage.ordinal()]))) {
            synchronized (this) {
                if (this.capitalizationBrkIter == null) {
                    this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
                }
                return UCharacter.toTitleCase(this.locale, name, this.capitalizationBrkIter, 768);
            }
        }
        return name;
    }
    
    @Override
    public String localeDisplayName(final ULocale locale) {
        return this.localeDisplayNameInternal(locale);
    }
    
    @Override
    public String localeDisplayName(final Locale locale) {
        return this.localeDisplayNameInternal(ULocale.forLocale(locale));
    }
    
    @Override
    public String localeDisplayName(final String localeId) {
        return this.localeDisplayNameInternal(new ULocale(localeId));
    }
    
    private String localeDisplayNameInternal(final ULocale locale) {
        String resultName = null;
        String lang = locale.getLanguage();
        if (locale.getBaseName().length() == 0) {
            lang = "root";
        }
        final String script = locale.getScript();
        final String country = locale.getCountry();
        final String variant = locale.getVariant();
        boolean hasScript = script.length() > 0;
        boolean hasCountry = country.length() > 0;
        final boolean hasVariant = variant.length() > 0;
        Label_0300: {
            if (this.dialectHandling == DialectHandling.DIALECT_NAMES) {
                if (hasScript && hasCountry) {
                    final String langScriptCountry = lang + '_' + script + '_' + country;
                    final String result = this.localeIdName(langScriptCountry);
                    if (result != null && !result.equals(langScriptCountry)) {
                        resultName = result;
                        hasScript = false;
                        hasCountry = false;
                        break Label_0300;
                    }
                }
                if (hasScript) {
                    final String langScript = lang + '_' + script;
                    final String result = this.localeIdName(langScript);
                    if (result != null && !result.equals(langScript)) {
                        resultName = result;
                        hasScript = false;
                        break Label_0300;
                    }
                }
                if (hasCountry) {
                    final String langCountry = lang + '_' + country;
                    final String result = this.localeIdName(langCountry);
                    if (result != null && !result.equals(langCountry)) {
                        resultName = result;
                        hasCountry = false;
                    }
                }
            }
        }
        if (resultName == null) {
            final String result2 = this.localeIdName(lang);
            if (result2 == null) {
                return null;
            }
            resultName = result2.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
        }
        final StringBuilder buf = new StringBuilder();
        if (hasScript) {
            final String result = this.scriptDisplayNameInContext(script, true);
            if (result == null) {
                return null;
            }
            buf.append(result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen));
        }
        if (hasCountry) {
            final String result = this.regionDisplayName(country, true);
            if (result == null) {
                return null;
            }
            this.appendWithSep(result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), buf);
        }
        if (hasVariant) {
            final String result = this.variantDisplayName(variant, true);
            if (result == null) {
                return null;
            }
            this.appendWithSep(result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), buf);
        }
        final Iterator<String> keys = locale.getKeywords();
        if (keys != null) {
            while (keys.hasNext()) {
                final String key = keys.next();
                final String value = locale.getKeywordValue(key);
                String keyDisplayName = this.keyDisplayName(key, true);
                if (keyDisplayName == null) {
                    return null;
                }
                keyDisplayName = keyDisplayName.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
                String valueDisplayName = this.keyValueDisplayName(key, value, true);
                if (valueDisplayName == null) {
                    return null;
                }
                valueDisplayName = valueDisplayName.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
                if (!valueDisplayName.equals(value)) {
                    this.appendWithSep(valueDisplayName, buf);
                }
                else if (!key.equals(keyDisplayName)) {
                    final String keyValue = SimpleFormatterImpl.formatCompiledPattern(this.keyTypeFormat, keyDisplayName, valueDisplayName);
                    this.appendWithSep(keyValue, buf);
                }
                else {
                    this.appendWithSep(keyDisplayName, buf).append("=").append(valueDisplayName);
                }
            }
        }
        String resultRemainder = null;
        if (buf.length() > 0) {
            resultRemainder = buf.toString();
        }
        if (resultRemainder != null) {
            resultName = SimpleFormatterImpl.formatCompiledPattern(this.format, resultName, resultRemainder);
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, resultName);
    }
    
    private String localeIdName(final String localeId) {
        if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            final String locIdName = this.langData.get("Languages%short", localeId);
            if (locIdName != null && !locIdName.equals(localeId)) {
                return locIdName;
            }
        }
        return this.langData.get("Languages", localeId);
    }
    
    @Override
    public String languageDisplayName(final String lang) {
        if (lang.equals("root") || lang.indexOf(95) != -1) {
            return (this.substituteHandling == DisplayContext.SUBSTITUTE) ? lang : null;
        }
        if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            final String langName = this.langData.get("Languages%short", lang);
            if (langName != null && !langName.equals(lang)) {
                return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, langName);
            }
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, this.langData.get("Languages", lang));
    }
    
    @Override
    public String scriptDisplayName(final String script) {
        String str = this.langData.get("Scripts%stand-alone", script);
        if (str == null || str.equals(script)) {
            if (this.nameLength == DisplayContext.LENGTH_SHORT) {
                str = this.langData.get("Scripts%short", script);
                if (str != null && !str.equals(script)) {
                    return this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, str);
                }
            }
            str = this.langData.get("Scripts", script);
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, str);
    }
    
    private String scriptDisplayNameInContext(final String script, final boolean skipAdjust) {
        if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            final String scriptName = this.langData.get("Scripts%short", script);
            if (scriptName != null && !scriptName.equals(script)) {
                return skipAdjust ? scriptName : this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, scriptName);
            }
        }
        final String scriptName = this.langData.get("Scripts", script);
        return skipAdjust ? scriptName : this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, scriptName);
    }
    
    @Override
    public String scriptDisplayNameInContext(final String script) {
        return this.scriptDisplayNameInContext(script, false);
    }
    
    @Override
    public String scriptDisplayName(final int scriptCode) {
        return this.scriptDisplayName(UScript.getShortName(scriptCode));
    }
    
    private String regionDisplayName(final String region, final boolean skipAdjust) {
        if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            final String regionName = this.regionData.get("Countries%short", region);
            if (regionName != null && !regionName.equals(region)) {
                return skipAdjust ? regionName : this.adjustForUsageAndContext(CapitalizationContextUsage.TERRITORY, regionName);
            }
        }
        final String regionName = this.regionData.get("Countries", region);
        return skipAdjust ? regionName : this.adjustForUsageAndContext(CapitalizationContextUsage.TERRITORY, regionName);
    }
    
    @Override
    public String regionDisplayName(final String region) {
        return this.regionDisplayName(region, false);
    }
    
    private String variantDisplayName(final String variant, final boolean skipAdjust) {
        final String variantName = this.langData.get("Variants", variant);
        return skipAdjust ? variantName : this.adjustForUsageAndContext(CapitalizationContextUsage.VARIANT, variantName);
    }
    
    @Override
    public String variantDisplayName(final String variant) {
        return this.variantDisplayName(variant, false);
    }
    
    private String keyDisplayName(final String key, final boolean skipAdjust) {
        final String keyName = this.langData.get("Keys", key);
        return skipAdjust ? keyName : this.adjustForUsageAndContext(CapitalizationContextUsage.KEY, keyName);
    }
    
    @Override
    public String keyDisplayName(final String key) {
        return this.keyDisplayName(key, false);
    }
    
    private String keyValueDisplayName(final String key, final String value, final boolean skipAdjust) {
        String keyValueName = null;
        if (key.equals("currency")) {
            keyValueName = this.currencyDisplayInfo.getName(AsciiUtil.toUpperString(value));
            if (keyValueName == null) {
                keyValueName = value;
            }
        }
        else {
            if (this.nameLength == DisplayContext.LENGTH_SHORT) {
                final String tmp = this.langData.get("Types%short", key, value);
                if (tmp != null && !tmp.equals(value)) {
                    keyValueName = tmp;
                }
            }
            if (keyValueName == null) {
                keyValueName = this.langData.get("Types", key, value);
            }
        }
        return skipAdjust ? keyValueName : this.adjustForUsageAndContext(CapitalizationContextUsage.KEYVALUE, keyValueName);
    }
    
    @Override
    public String keyValueDisplayName(final String key, final String value) {
        return this.keyValueDisplayName(key, value, false);
    }
    
    @Override
    public List<UiListItem> getUiListCompareWholeItems(final Set<ULocale> localeSet, final Comparator<UiListItem> comparator) {
        final DisplayContext capContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
        final List<UiListItem> result = new ArrayList<UiListItem>();
        final Map<ULocale, Set<ULocale>> baseToLocales = new HashMap<ULocale, Set<ULocale>>();
        final ULocale.Builder builder = new ULocale.Builder();
        for (final ULocale locOriginal : localeSet) {
            builder.setLocale(locOriginal);
            final ULocale loc = ULocale.addLikelySubtags(locOriginal);
            final ULocale base = new ULocale(loc.getLanguage());
            Set<ULocale> locales = baseToLocales.get(base);
            if (locales == null) {
                baseToLocales.put(base, locales = new HashSet<ULocale>());
            }
            locales.add(loc);
        }
        for (final Map.Entry<ULocale, Set<ULocale>> entry : baseToLocales.entrySet()) {
            final ULocale base2 = entry.getKey();
            final Set<ULocale> values = entry.getValue();
            if (values.size() == 1) {
                final ULocale locale = values.iterator().next();
                result.add(this.newRow(ULocale.minimizeSubtags(locale, ULocale.Minimize.FAVOR_SCRIPT), capContext));
            }
            else {
                final Set<String> scripts = new HashSet<String>();
                final Set<String> regions = new HashSet<String>();
                final ULocale maxBase = ULocale.addLikelySubtags(base2);
                scripts.add(maxBase.getScript());
                regions.add(maxBase.getCountry());
                for (final ULocale locale2 : values) {
                    scripts.add(locale2.getScript());
                    regions.add(locale2.getCountry());
                }
                final boolean hasScripts = scripts.size() > 1;
                final boolean hasRegions = regions.size() > 1;
                for (final ULocale locale3 : values) {
                    final ULocale.Builder modified = builder.setLocale(locale3);
                    if (!hasScripts) {
                        modified.setScript("");
                    }
                    if (!hasRegions) {
                        modified.setRegion("");
                    }
                    result.add(this.newRow(modified.build(), capContext));
                }
            }
        }
        Collections.sort(result, comparator);
        return result;
    }
    
    private UiListItem newRow(final ULocale modified, final DisplayContext capContext) {
        final ULocale minimized = ULocale.minimizeSubtags(modified, ULocale.Minimize.FAVOR_SCRIPT);
        String tempName = modified.getDisplayName(this.locale);
        final boolean titlecase = capContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU;
        final String nameInDisplayLocale = titlecase ? toTitleWholeStringNoLowercase(this.locale, tempName) : tempName;
        tempName = modified.getDisplayName(modified);
        final String nameInSelf = (capContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU) ? toTitleWholeStringNoLowercase(modified, tempName) : tempName;
        return new UiListItem(minimized, modified, nameInDisplayLocale, nameInSelf);
    }
    
    public static boolean haveData(final DataTableType type) {
        switch (type) {
            case LANG: {
                return LangDataTables.impl instanceof ICUDataTables;
            }
            case REGION: {
                return RegionDataTables.impl instanceof ICUDataTables;
            }
            default: {
                throw new IllegalArgumentException("unknown type: " + type);
            }
        }
    }
    
    private StringBuilder appendWithSep(final String s, final StringBuilder b) {
        if (b.length() == 0) {
            b.append(s);
        }
        else {
            SimpleFormatterImpl.formatAndReplace(this.separatorFormat, b, null, b, s);
        }
        return b;
    }
    
    static {
        cache = new Cache();
        (contextUsageTypeMap = new HashMap<String, CapitalizationContextUsage>()).put("languages", CapitalizationContextUsage.LANGUAGE);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("script", CapitalizationContextUsage.SCRIPT);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("territory", CapitalizationContextUsage.TERRITORY);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("variant", CapitalizationContextUsage.VARIANT);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("key", CapitalizationContextUsage.KEY);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("keyValue", CapitalizationContextUsage.KEYVALUE);
        TO_TITLE_WHOLE_STRING_NO_LOWERCASE = CaseMap.toTitle().wholeString().noLowercase();
    }
    
    private enum CapitalizationContextUsage
    {
        LANGUAGE, 
        SCRIPT, 
        TERRITORY, 
        VARIANT, 
        KEY, 
        KEYVALUE;
    }
    
    private final class CapitalizationContextSink extends UResource.Sink
    {
        boolean hasCapitalizationUsage;
        
        private CapitalizationContextSink() {
            this.hasCapitalizationUsage = false;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table contextsTable = value.getTable();
            for (int i = 0; contextsTable.getKeyAndValue(i, key, value); ++i) {
                final CapitalizationContextUsage usage = LocaleDisplayNamesImpl.contextUsageTypeMap.get(key.toString());
                if (usage != null) {
                    final int[] intVector = value.getIntVector();
                    if (intVector.length >= 2) {
                        final int titlecaseInt = (LocaleDisplayNamesImpl.this.capitalization == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU) ? intVector[0] : intVector[1];
                        if (titlecaseInt != 0) {
                            LocaleDisplayNamesImpl.this.capitalizationUsage[usage.ordinal()] = true;
                            this.hasCapitalizationUsage = true;
                        }
                    }
                }
            }
        }
    }
    
    public static class DataTable
    {
        final boolean nullIfNotFound;
        
        DataTable(final boolean nullIfNotFound) {
            this.nullIfNotFound = nullIfNotFound;
        }
        
        ULocale getLocale() {
            return ULocale.ROOT;
        }
        
        String get(final String tableName, final String code) {
            return this.get(tableName, null, code);
        }
        
        String get(final String tableName, final String subTableName, final String code) {
            return this.nullIfNotFound ? null : code;
        }
    }
    
    static class ICUDataTable extends DataTable
    {
        private final ICUResourceBundle bundle;
        
        public ICUDataTable(final String path, final ULocale locale, final boolean nullIfNotFound) {
            super(nullIfNotFound);
            this.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(path, locale.getBaseName());
        }
        
        public ULocale getLocale() {
            return this.bundle.getULocale();
        }
        
        public String get(final String tableName, final String subTableName, final String code) {
            return ICUResourceTableAccess.getTableString(this.bundle, tableName, subTableName, code, this.nullIfNotFound ? null : code);
        }
    }
    
    abstract static class DataTables
    {
        public abstract DataTable get(final ULocale p0, final boolean p1);
        
        public static DataTables load(final String className) {
            try {
                return (DataTables)Class.forName(className).newInstance();
            }
            catch (Throwable t) {
                return new DataTables() {
                    @Override
                    public DataTable get(final ULocale locale, final boolean nullIfNotFound) {
                        return new DataTable(nullIfNotFound);
                    }
                };
            }
        }
    }
    
    abstract static class ICUDataTables extends DataTables
    {
        private final String path;
        
        protected ICUDataTables(final String path) {
            this.path = path;
        }
        
        @Override
        public DataTable get(final ULocale locale, final boolean nullIfNotFound) {
            return new ICUDataTable(this.path, locale, nullIfNotFound);
        }
    }
    
    static class LangDataTables
    {
        static final DataTables impl;
        
        static {
            impl = DataTables.load("com.ibm.icu.impl.ICULangDataTables");
        }
    }
    
    static class RegionDataTables
    {
        static final DataTables impl;
        
        static {
            impl = DataTables.load("com.ibm.icu.impl.ICURegionDataTables");
        }
    }
    
    public enum DataTableType
    {
        LANG, 
        REGION;
    }
    
    private static class Cache
    {
        private ULocale locale;
        private DialectHandling dialectHandling;
        private DisplayContext capitalization;
        private DisplayContext nameLength;
        private DisplayContext substituteHandling;
        private LocaleDisplayNames cache;
        
        public LocaleDisplayNames get(final ULocale locale, final DialectHandling dialectHandling) {
            if (dialectHandling != this.dialectHandling || DisplayContext.CAPITALIZATION_NONE != this.capitalization || DisplayContext.LENGTH_FULL != this.nameLength || DisplayContext.SUBSTITUTE != this.substituteHandling || !locale.equals(this.locale)) {
                this.locale = locale;
                this.dialectHandling = dialectHandling;
                this.capitalization = DisplayContext.CAPITALIZATION_NONE;
                this.nameLength = DisplayContext.LENGTH_FULL;
                this.substituteHandling = DisplayContext.SUBSTITUTE;
                this.cache = new LocaleDisplayNamesImpl(locale, dialectHandling);
            }
            return this.cache;
        }
        
        public LocaleDisplayNames get(final ULocale locale, final DisplayContext... contexts) {
            DialectHandling dialectHandlingIn = DialectHandling.STANDARD_NAMES;
            DisplayContext capitalizationIn = DisplayContext.CAPITALIZATION_NONE;
            DisplayContext nameLengthIn = DisplayContext.LENGTH_FULL;
            DisplayContext substituteHandling = DisplayContext.SUBSTITUTE;
            for (final DisplayContext contextItem : contexts) {
                switch (contextItem.type()) {
                    case DIALECT_HANDLING: {
                        dialectHandlingIn = ((contextItem.value() == DisplayContext.STANDARD_NAMES.value()) ? DialectHandling.STANDARD_NAMES : DialectHandling.DIALECT_NAMES);
                        break;
                    }
                    case CAPITALIZATION: {
                        capitalizationIn = contextItem;
                        break;
                    }
                    case DISPLAY_LENGTH: {
                        nameLengthIn = contextItem;
                        break;
                    }
                    case SUBSTITUTE_HANDLING: {
                        substituteHandling = contextItem;
                        break;
                    }
                }
            }
            if (dialectHandlingIn != this.dialectHandling || capitalizationIn != this.capitalization || nameLengthIn != this.nameLength || substituteHandling != this.substituteHandling || !locale.equals(this.locale)) {
                this.locale = locale;
                this.dialectHandling = dialectHandlingIn;
                this.capitalization = capitalizationIn;
                this.nameLength = nameLengthIn;
                this.substituteHandling = substituteHandling;
                this.cache = new LocaleDisplayNamesImpl(locale, contexts);
            }
            return this.cache;
        }
    }
}
