/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.CurrencyData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUResourceTableAccess;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.CaseMap;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.LocaleDisplayNames;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

public class LocaleDisplayNamesImpl
extends LocaleDisplayNames {
    private final ULocale locale;
    private final LocaleDisplayNames.DialectHandling dialectHandling;
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
    private static final Cache cache = new Cache(null);
    private boolean[] capitalizationUsage = null;
    private static final Map<String, CapitalizationContextUsage> contextUsageTypeMap = new HashMap<String, CapitalizationContextUsage>();
    private transient BreakIterator capitalizationBrkIter = null;
    private static final CaseMap.Title TO_TITLE_WHOLE_STRING_NO_LOWERCASE;

    private static String toTitleWholeStringNoLowercase(ULocale uLocale, String string) {
        return TO_TITLE_WHOLE_STRING_NO_LOWERCASE.apply(uLocale.toLocale(), null, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static LocaleDisplayNames getInstance(ULocale uLocale, LocaleDisplayNames.DialectHandling dialectHandling) {
        Cache cache = LocaleDisplayNamesImpl.cache;
        synchronized (cache) {
            return LocaleDisplayNamesImpl.cache.get(uLocale, dialectHandling);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static LocaleDisplayNames getInstance(ULocale uLocale, DisplayContext ... displayContextArray) {
        Cache cache = LocaleDisplayNamesImpl.cache;
        synchronized (cache) {
            return LocaleDisplayNamesImpl.cache.get(uLocale, displayContextArray);
        }
    }

    public LocaleDisplayNamesImpl(ULocale uLocale, LocaleDisplayNames.DialectHandling dialectHandling) {
        this(uLocale, dialectHandling == LocaleDisplayNames.DialectHandling.STANDARD_NAMES ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES, DisplayContext.CAPITALIZATION_NONE);
    }

    /*
     * WARNING - void declaration
     */
    public LocaleDisplayNamesImpl(ULocale uLocale, DisplayContext ... displayContextArray) {
        void var7_10;
        Object object2;
        LocaleDisplayNames.DialectHandling dialectHandling = LocaleDisplayNames.DialectHandling.STANDARD_NAMES;
        DisplayContext displayContext = DisplayContext.CAPITALIZATION_NONE;
        DisplayContext displayContext2 = DisplayContext.LENGTH_FULL;
        DisplayContext displayContext3 = DisplayContext.SUBSTITUTE;
        block8: for (Object object2 : displayContextArray) {
            switch (object2.type()) {
                case DIALECT_HANDLING: {
                    dialectHandling = object2.value() == DisplayContext.STANDARD_NAMES.value() ? LocaleDisplayNames.DialectHandling.STANDARD_NAMES : LocaleDisplayNames.DialectHandling.DIALECT_NAMES;
                    continue block8;
                }
                case CAPITALIZATION: {
                    displayContext = object2;
                    continue block8;
                }
                case DISPLAY_LENGTH: {
                    displayContext2 = object2;
                    continue block8;
                }
                case SUBSTITUTE_HANDLING: {
                    displayContext3 = object2;
                    continue block8;
                }
            }
        }
        this.dialectHandling = dialectHandling;
        this.capitalization = displayContext;
        this.nameLength = displayContext2;
        this.substituteHandling = displayContext3;
        this.langData = LangDataTables.impl.get(uLocale, displayContext3 == DisplayContext.NO_SUBSTITUTE);
        this.regionData = RegionDataTables.impl.get(uLocale, displayContext3 == DisplayContext.NO_SUBSTITUTE);
        this.locale = ULocale.ROOT.equals(this.langData.getLocale()) ? this.regionData.getLocale() : this.langData.getLocale();
        String string = this.langData.get("localeDisplayPattern", "separator");
        if (string == null || "separator".equals(string)) {
            String string2 = "{0}, {1}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        this.separatorFormat = SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)var7_10, stringBuilder, 2, 2);
        String string3 = this.langData.get("localeDisplayPattern", "pattern");
        if (string3 == null || "pattern".equals(string3)) {
            string3 = "{0} ({1})";
        }
        this.format = SimpleFormatterImpl.compileToStringMinMaxArguments(string3, stringBuilder, 2, 2);
        if (string3.contains("\uff08")) {
            this.formatOpenParen = (char)65288;
            this.formatCloseParen = (char)65289;
            this.formatReplaceOpenParen = (char)65339;
            this.formatReplaceCloseParen = (char)65341;
        } else {
            this.formatOpenParen = (char)40;
            this.formatCloseParen = (char)41;
            this.formatReplaceOpenParen = (char)91;
            this.formatReplaceCloseParen = (char)93;
        }
        object2 = this.langData.get("localeDisplayPattern", "keyTypePattern");
        if (object2 == null || "keyTypePattern".equals(object2)) {
            object2 = "{0}={1}";
        }
        this.keyTypeFormat = SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)object2, stringBuilder, 2, 2);
        boolean bl = false;
        if (displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE) {
            this.capitalizationUsage = new boolean[CapitalizationContextUsage.values().length];
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
            CapitalizationContextSink capitalizationContextSink = new CapitalizationContextSink(this, null);
            try {
                iCUResourceBundle.getAllItemsWithFallback("contextTransforms", capitalizationContextSink);
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            bl = capitalizationContextSink.hasCapitalizationUsage;
        }
        if (bl || displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(uLocale);
        }
        this.currencyDisplayInfo = CurrencyData.provider.getInstance(uLocale, false);
    }

    @Override
    public ULocale getLocale() {
        return this.locale;
    }

    @Override
    public LocaleDisplayNames.DialectHandling getDialectHandling() {
        return this.dialectHandling;
    }

    @Override
    public DisplayContext getContext(DisplayContext.Type type) {
        DisplayContext displayContext;
        switch (type) {
            case DIALECT_HANDLING: {
                displayContext = this.dialectHandling == LocaleDisplayNames.DialectHandling.STANDARD_NAMES ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES;
                break;
            }
            case CAPITALIZATION: {
                displayContext = this.capitalization;
                break;
            }
            case DISPLAY_LENGTH: {
                displayContext = this.nameLength;
                break;
            }
            case SUBSTITUTE_HANDLING: {
                displayContext = this.substituteHandling;
                break;
            }
            default: {
                displayContext = DisplayContext.STANDARD_NAMES;
            }
        }
        return displayContext;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String adjustForUsageAndContext(CapitalizationContextUsage capitalizationContextUsage, String string) {
        if (string != null && string.length() > 0 && UCharacter.isLowerCase(string.codePointAt(0)) && (this.capitalization == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || this.capitalizationUsage != null && this.capitalizationUsage[capitalizationContextUsage.ordinal()])) {
            LocaleDisplayNamesImpl localeDisplayNamesImpl = this;
            synchronized (localeDisplayNamesImpl) {
                if (this.capitalizationBrkIter == null) {
                    this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
                }
                return UCharacter.toTitleCase(this.locale, string, this.capitalizationBrkIter, 768);
            }
        }
        return string;
    }

    @Override
    public String localeDisplayName(ULocale uLocale) {
        return this.localeDisplayNameInternal(uLocale);
    }

    @Override
    public String localeDisplayName(Locale locale) {
        return this.localeDisplayNameInternal(ULocale.forLocale(locale));
    }

    @Override
    public String localeDisplayName(String string) {
        return this.localeDisplayNameInternal(new ULocale(string));
    }

    private String localeDisplayNameInternal(ULocale uLocale) {
        String string;
        CharSequence charSequence;
        Object object;
        boolean bl;
        Object object2 = null;
        String string2 = uLocale.getLanguage();
        if (string2.isEmpty()) {
            string2 = "und";
        }
        String string3 = uLocale.getScript();
        String string4 = uLocale.getCountry();
        String string5 = uLocale.getVariant();
        boolean bl2 = string3.length() > 0;
        boolean bl3 = string4.length() > 0;
        boolean bl4 = bl = string5.length() > 0;
        if (this.dialectHandling == LocaleDisplayNames.DialectHandling.DIALECT_NAMES) {
            if (bl2 && bl3 && (object = this.localeIdName((String)(charSequence = string2 + '_' + string3 + '_' + string4))) != null && !((String)object).equals(charSequence)) {
                object2 = object;
                bl2 = false;
                bl3 = false;
            } else if (bl2 && (object = this.localeIdName((String)(charSequence = string2 + '_' + string3))) != null && !((String)object).equals(charSequence)) {
                object2 = object;
                bl2 = false;
            } else if (bl3 && (object = this.localeIdName((String)(charSequence = string2 + '_' + string4))) != null && !((String)object).equals(charSequence)) {
                object2 = object;
                bl3 = false;
            }
        }
        if (object2 == null) {
            charSequence = this.localeIdName(string2);
            if (charSequence == null) {
                return null;
            }
            object2 = ((String)charSequence).replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
        }
        charSequence = new StringBuilder();
        if (bl2) {
            object = this.scriptDisplayNameInContext(string3, true);
            if (object == null) {
                return null;
            }
            ((StringBuilder)charSequence).append(((String)object).replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen));
        }
        if (bl3) {
            object = this.regionDisplayName(string4, true);
            if (object == null) {
                return null;
            }
            this.appendWithSep(((String)object).replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), (StringBuilder)charSequence);
        }
        if (bl) {
            object = this.variantDisplayName(string5, true);
            if (object == null) {
                return null;
            }
            this.appendWithSep(((String)object).replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), (StringBuilder)charSequence);
        }
        if ((object = uLocale.getKeywords()) != null) {
            while (object.hasNext()) {
                string = (String)object.next();
                String string6 = uLocale.getKeywordValue(string);
                String string7 = this.keyDisplayName(string, true);
                if (string7 == null) {
                    return null;
                }
                string7 = string7.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
                String string8 = this.keyValueDisplayName(string, string6, true);
                if (string8 == null) {
                    return null;
                }
                if (!(string8 = string8.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen)).equals(string6)) {
                    this.appendWithSep(string8, (StringBuilder)charSequence);
                    continue;
                }
                if (!string.equals(string7)) {
                    String string9 = SimpleFormatterImpl.formatCompiledPattern(this.keyTypeFormat, string7, string8);
                    this.appendWithSep(string9, (StringBuilder)charSequence);
                    continue;
                }
                this.appendWithSep(string7, (StringBuilder)charSequence).append("=").append(string8);
            }
        }
        string = null;
        if (((StringBuilder)charSequence).length() > 0) {
            string = ((StringBuilder)charSequence).toString();
        }
        if (string != null) {
            object2 = SimpleFormatterImpl.formatCompiledPattern(this.format, new CharSequence[]{object2, string});
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, (String)object2);
    }

    private String localeIdName(String string) {
        String string2;
        if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.langData.get("Languages%short", string)) != null && !string2.equals(string)) {
            return string2;
        }
        return this.langData.get("Languages", string);
    }

    @Override
    public String languageDisplayName(String string) {
        String string2;
        if (string.equals("root") || string.indexOf(95) != -1) {
            return this.substituteHandling == DisplayContext.SUBSTITUTE ? string : null;
        }
        if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.langData.get("Languages%short", string)) != null && !string2.equals(string)) {
            return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, string2);
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, this.langData.get("Languages", string));
    }

    @Override
    public String scriptDisplayName(String string) {
        String string2 = this.langData.get("Scripts%stand-alone", string);
        if (string2 == null || string2.equals(string)) {
            if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.langData.get("Scripts%short", string)) != null && !string2.equals(string)) {
                return this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, string2);
            }
            string2 = this.langData.get("Scripts", string);
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, string2);
    }

    private String scriptDisplayNameInContext(String string, boolean bl) {
        String string2;
        if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.langData.get("Scripts%short", string)) != null && !string2.equals(string)) {
            return bl ? string2 : this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, string2);
        }
        string2 = this.langData.get("Scripts", string);
        return bl ? string2 : this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, string2);
    }

    @Override
    public String scriptDisplayNameInContext(String string) {
        return this.scriptDisplayNameInContext(string, false);
    }

    @Override
    public String scriptDisplayName(int n) {
        return this.scriptDisplayName(UScript.getShortName(n));
    }

    private String regionDisplayName(String string, boolean bl) {
        String string2;
        if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.regionData.get("Countries%short", string)) != null && !string2.equals(string)) {
            return bl ? string2 : this.adjustForUsageAndContext(CapitalizationContextUsage.TERRITORY, string2);
        }
        string2 = this.regionData.get("Countries", string);
        return bl ? string2 : this.adjustForUsageAndContext(CapitalizationContextUsage.TERRITORY, string2);
    }

    @Override
    public String regionDisplayName(String string) {
        return this.regionDisplayName(string, false);
    }

    private String variantDisplayName(String string, boolean bl) {
        String string2 = this.langData.get("Variants", string);
        return bl ? string2 : this.adjustForUsageAndContext(CapitalizationContextUsage.VARIANT, string2);
    }

    @Override
    public String variantDisplayName(String string) {
        return this.variantDisplayName(string, false);
    }

    private String keyDisplayName(String string, boolean bl) {
        String string2 = this.langData.get("Keys", string);
        return bl ? string2 : this.adjustForUsageAndContext(CapitalizationContextUsage.KEY, string2);
    }

    @Override
    public String keyDisplayName(String string) {
        return this.keyDisplayName(string, false);
    }

    private String keyValueDisplayName(String string, String string2, boolean bl) {
        String string3 = null;
        if (string.equals("currency")) {
            string3 = this.currencyDisplayInfo.getName(AsciiUtil.toUpperString(string2));
            if (string3 == null) {
                string3 = string2;
            }
        } else {
            String string4;
            if (this.nameLength == DisplayContext.LENGTH_SHORT && (string4 = this.langData.get("Types%short", string, string2)) != null && !string4.equals(string2)) {
                string3 = string4;
            }
            if (string3 == null) {
                string3 = this.langData.get("Types", string, string2);
            }
        }
        return bl ? string3 : this.adjustForUsageAndContext(CapitalizationContextUsage.KEYVALUE, string3);
    }

    @Override
    public String keyValueDisplayName(String string, String string2) {
        return this.keyValueDisplayName(string, string2, false);
    }

    @Override
    public List<LocaleDisplayNames.UiListItem> getUiListCompareWholeItems(Set<ULocale> set, Comparator<LocaleDisplayNames.UiListItem> comparator) {
        Serializable serializable;
        Object object;
        ULocale uLocale;
        DisplayContext displayContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
        ArrayList<LocaleDisplayNames.UiListItem> arrayList = new ArrayList<LocaleDisplayNames.UiListItem>();
        HashMap hashMap = new HashMap();
        ULocale.Builder builder = new ULocale.Builder();
        for (ULocale object2 : set) {
            builder.setLocale(object2);
            uLocale = ULocale.addLikelySubtags(object2);
            object = new ULocale(uLocale.getLanguage());
            serializable = (HashSet<ULocale>)hashMap.get(object);
            if (serializable == null) {
                serializable = new HashSet();
                hashMap.put(object, serializable);
            }
            serializable.add(uLocale);
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            uLocale = (ULocale)entry.getKey();
            object = (Set)entry.getValue();
            if (object.size() == 1) {
                serializable = (ULocale)object.iterator().next();
                arrayList.add(this.newRow(ULocale.minimizeSubtags(serializable, ULocale.Minimize.FAVOR_SCRIPT), displayContext));
                continue;
            }
            serializable = new HashSet<ULocale>();
            HashSet<String> hashSet = new HashSet<String>();
            ULocale uLocale2 = ULocale.addLikelySubtags(uLocale);
            serializable.add(uLocale2.getScript());
            hashSet.add(uLocale2.getCountry());
            Iterator iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                ULocale uLocale3 = (ULocale)iterator2.next();
                serializable.add(uLocale3.getScript());
                hashSet.add(uLocale3.getCountry());
            }
            boolean bl = serializable.size() > 1;
            boolean bl2 = hashSet.size() > 1;
            Iterator iterator3 = object.iterator();
            while (iterator3.hasNext()) {
                ULocale uLocale4 = (ULocale)iterator3.next();
                ULocale.Builder builder2 = builder.setLocale(uLocale4);
                if (!bl) {
                    builder2.setScript("");
                }
                if (!bl2) {
                    builder2.setRegion("");
                }
                arrayList.add(this.newRow(builder2.build(), displayContext));
            }
        }
        Collections.sort(arrayList, comparator);
        return arrayList;
    }

    private LocaleDisplayNames.UiListItem newRow(ULocale uLocale, DisplayContext displayContext) {
        ULocale uLocale2 = ULocale.minimizeSubtags(uLocale, ULocale.Minimize.FAVOR_SCRIPT);
        String string = uLocale.getDisplayName(this.locale);
        boolean bl = displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU;
        String string2 = bl ? LocaleDisplayNamesImpl.toTitleWholeStringNoLowercase(this.locale, string) : string;
        string = uLocale.getDisplayName(uLocale);
        String string3 = displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? LocaleDisplayNamesImpl.toTitleWholeStringNoLowercase(uLocale, string) : string;
        return new LocaleDisplayNames.UiListItem(uLocale2, uLocale, string2, string3);
    }

    public static boolean haveData(DataTableType dataTableType) {
        switch (dataTableType) {
            case LANG: {
                return LangDataTables.impl instanceof ICUDataTables;
            }
            case REGION: {
                return RegionDataTables.impl instanceof ICUDataTables;
            }
        }
        throw new IllegalArgumentException("unknown type: " + (Object)((Object)dataTableType));
    }

    private StringBuilder appendWithSep(String string, StringBuilder stringBuilder) {
        if (stringBuilder.length() == 0) {
            stringBuilder.append(string);
        } else {
            SimpleFormatterImpl.formatAndReplace(this.separatorFormat, stringBuilder, null, stringBuilder, string);
        }
        return stringBuilder;
    }

    static Map access$100() {
        return contextUsageTypeMap;
    }

    static DisplayContext access$200(LocaleDisplayNamesImpl localeDisplayNamesImpl) {
        return localeDisplayNamesImpl.capitalization;
    }

    static boolean[] access$300(LocaleDisplayNamesImpl localeDisplayNamesImpl) {
        return localeDisplayNamesImpl.capitalizationUsage;
    }

    static {
        contextUsageTypeMap.put("languages", CapitalizationContextUsage.LANGUAGE);
        contextUsageTypeMap.put("script", CapitalizationContextUsage.SCRIPT);
        contextUsageTypeMap.put("territory", CapitalizationContextUsage.TERRITORY);
        contextUsageTypeMap.put("variant", CapitalizationContextUsage.VARIANT);
        contextUsageTypeMap.put("key", CapitalizationContextUsage.KEY);
        contextUsageTypeMap.put("keyValue", CapitalizationContextUsage.KEYVALUE);
        TO_TITLE_WHOLE_STRING_NO_LOWERCASE = CaseMap.toTitle().wholeString().noLowercase();
    }

    private static class Cache {
        private ULocale locale;
        private LocaleDisplayNames.DialectHandling dialectHandling;
        private DisplayContext capitalization;
        private DisplayContext nameLength;
        private DisplayContext substituteHandling;
        private LocaleDisplayNames cache;

        private Cache() {
        }

        public LocaleDisplayNames get(ULocale uLocale, LocaleDisplayNames.DialectHandling dialectHandling) {
            if (dialectHandling != this.dialectHandling || DisplayContext.CAPITALIZATION_NONE != this.capitalization || DisplayContext.LENGTH_FULL != this.nameLength || DisplayContext.SUBSTITUTE != this.substituteHandling || !uLocale.equals(this.locale)) {
                this.locale = uLocale;
                this.dialectHandling = dialectHandling;
                this.capitalization = DisplayContext.CAPITALIZATION_NONE;
                this.nameLength = DisplayContext.LENGTH_FULL;
                this.substituteHandling = DisplayContext.SUBSTITUTE;
                this.cache = new LocaleDisplayNamesImpl(uLocale, dialectHandling);
            }
            return this.cache;
        }

        public LocaleDisplayNames get(ULocale uLocale, DisplayContext ... displayContextArray) {
            LocaleDisplayNames.DialectHandling dialectHandling = LocaleDisplayNames.DialectHandling.STANDARD_NAMES;
            DisplayContext displayContext = DisplayContext.CAPITALIZATION_NONE;
            DisplayContext displayContext2 = DisplayContext.LENGTH_FULL;
            DisplayContext displayContext3 = DisplayContext.SUBSTITUTE;
            block6: for (DisplayContext displayContext4 : displayContextArray) {
                switch (displayContext4.type()) {
                    case DIALECT_HANDLING: {
                        dialectHandling = displayContext4.value() == DisplayContext.STANDARD_NAMES.value() ? LocaleDisplayNames.DialectHandling.STANDARD_NAMES : LocaleDisplayNames.DialectHandling.DIALECT_NAMES;
                        continue block6;
                    }
                    case CAPITALIZATION: {
                        displayContext = displayContext4;
                        continue block6;
                    }
                    case DISPLAY_LENGTH: {
                        displayContext2 = displayContext4;
                        continue block6;
                    }
                    case SUBSTITUTE_HANDLING: {
                        displayContext3 = displayContext4;
                        continue block6;
                    }
                }
            }
            if (dialectHandling != this.dialectHandling || displayContext != this.capitalization || displayContext2 != this.nameLength || displayContext3 != this.substituteHandling || !uLocale.equals(this.locale)) {
                this.locale = uLocale;
                this.dialectHandling = dialectHandling;
                this.capitalization = displayContext;
                this.nameLength = displayContext2;
                this.substituteHandling = displayContext3;
                this.cache = new LocaleDisplayNamesImpl(uLocale, displayContextArray);
            }
            return this.cache;
        }

        Cache(1 var1_1) {
            this();
        }
    }

    public static enum DataTableType {
        LANG,
        REGION;

    }

    static class RegionDataTables {
        static final DataTables impl = DataTables.load("com.ibm.icu.impl.ICURegionDataTables");

        RegionDataTables() {
        }
    }

    static class LangDataTables {
        static final DataTables impl = DataTables.load("com.ibm.icu.impl.ICULangDataTables");

        LangDataTables() {
        }
    }

    static abstract class ICUDataTables
    extends DataTables {
        private final String path;

        protected ICUDataTables(String string) {
            this.path = string;
        }

        @Override
        public DataTable get(ULocale uLocale, boolean bl) {
            return new ICUDataTable(this.path, uLocale, bl);
        }
    }

    static abstract class DataTables {
        DataTables() {
        }

        public abstract DataTable get(ULocale var1, boolean var2);

        public static DataTables load(String string) {
            try {
                return (DataTables)Class.forName(string).newInstance();
            } catch (Throwable throwable) {
                return new DataTables(){

                    @Override
                    public DataTable get(ULocale uLocale, boolean bl) {
                        return new DataTable(bl);
                    }
                };
            }
        }
    }

    static class ICUDataTable
    extends DataTable {
        private final ICUResourceBundle bundle;

        public ICUDataTable(String string, ULocale uLocale, boolean bl) {
            super(bl);
            this.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(string, uLocale.getBaseName());
        }

        @Override
        public ULocale getLocale() {
            return this.bundle.getULocale();
        }

        @Override
        public String get(String string, String string2, String string3) {
            return ICUResourceTableAccess.getTableString(this.bundle, string, string2, string3, this.nullIfNotFound ? null : string3);
        }
    }

    public static class DataTable {
        final boolean nullIfNotFound;

        DataTable(boolean bl) {
            this.nullIfNotFound = bl;
        }

        ULocale getLocale() {
            return ULocale.ROOT;
        }

        String get(String string, String string2) {
            return this.get(string, null, string2);
        }

        String get(String string, String string2, String string3) {
            return this.nullIfNotFound ? null : string3;
        }
    }

    private final class CapitalizationContextSink
    extends UResource.Sink {
        boolean hasCapitalizationUsage;
        final LocaleDisplayNamesImpl this$0;

        private CapitalizationContextSink(LocaleDisplayNamesImpl localeDisplayNamesImpl) {
            this.this$0 = localeDisplayNamesImpl;
            this.hasCapitalizationUsage = false;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int[] nArray;
                CapitalizationContextUsage capitalizationContextUsage = (CapitalizationContextUsage)((Object)LocaleDisplayNamesImpl.access$100().get(key.toString()));
                if (capitalizationContextUsage != null && (nArray = value.getIntVector()).length >= 2) {
                    int n2;
                    int n3 = n2 = LocaleDisplayNamesImpl.access$200(this.this$0) == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? nArray[0] : nArray[1];
                    if (n2 != 0) {
                        LocaleDisplayNamesImpl.access$300((LocaleDisplayNamesImpl)this.this$0)[capitalizationContextUsage.ordinal()] = true;
                        this.hasCapitalizationUsage = true;
                    }
                }
                ++n;
            }
        }

        CapitalizationContextSink(LocaleDisplayNamesImpl localeDisplayNamesImpl, 1 var2_2) {
            this(localeDisplayNamesImpl);
        }
    }

    private static enum CapitalizationContextUsage {
        LANGUAGE,
        SCRIPT,
        TERRITORY,
        VARIANT,
        KEY,
        KEYVALUE;

    }
}

