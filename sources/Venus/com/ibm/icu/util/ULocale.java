/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUResourceTableAccess;
import com.ibm.icu.impl.LocaleIDParser;
import com.ibm.icu.impl.LocaleIDs;
import com.ibm.icu.impl.LocaleUtility;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.locale.Extension;
import com.ibm.icu.impl.locale.InternalLocaleBuilder;
import com.ibm.icu.impl.locale.KeyTypeData;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.impl.locale.LocaleExtensions;
import com.ibm.icu.impl.locale.LocaleSyntaxException;
import com.ibm.icu.impl.locale.ParseStatus;
import com.ibm.icu.impl.locale.UnicodeLocaleExtension;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.LocaleDisplayNames;
import com.ibm.icu.util.IllformedLocaleException;
import com.ibm.icu.util.UResourceBundle;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

public final class ULocale
implements Serializable,
Comparable<ULocale> {
    private static final long serialVersionUID = 3715177670352309217L;
    private static final Pattern UND_PATTERN = Pattern.compile("^und(?=$|[_-])", 2);
    private static CacheBase<String, String, Void> nameCache = new SoftCache<String, String, Void>(){

        @Override
        protected String createInstance(String string, Void void_) {
            return new LocaleIDParser(string).getName();
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (Void)object2);
        }
    };
    public static final ULocale ENGLISH = new ULocale("en", Locale.ENGLISH);
    public static final ULocale FRENCH = new ULocale("fr", Locale.FRENCH);
    public static final ULocale GERMAN = new ULocale("de", Locale.GERMAN);
    public static final ULocale ITALIAN = new ULocale("it", Locale.ITALIAN);
    public static final ULocale JAPANESE = new ULocale("ja", Locale.JAPANESE);
    public static final ULocale KOREAN = new ULocale("ko", Locale.KOREAN);
    public static final ULocale CHINESE = new ULocale("zh", Locale.CHINESE);
    public static final ULocale SIMPLIFIED_CHINESE = new ULocale("zh_Hans");
    public static final ULocale TRADITIONAL_CHINESE = new ULocale("zh_Hant");
    public static final ULocale FRANCE = new ULocale("fr_FR", Locale.FRANCE);
    public static final ULocale GERMANY = new ULocale("de_DE", Locale.GERMANY);
    public static final ULocale ITALY = new ULocale("it_IT", Locale.ITALY);
    public static final ULocale JAPAN = new ULocale("ja_JP", Locale.JAPAN);
    public static final ULocale KOREA = new ULocale("ko_KR", Locale.KOREA);
    public static final ULocale CHINA;
    public static final ULocale PRC;
    public static final ULocale TAIWAN;
    public static final ULocale UK;
    public static final ULocale US;
    public static final ULocale CANADA;
    public static final ULocale CANADA_FRENCH;
    private static final String EMPTY_STRING = "";
    private static final char UNDERSCORE = '_';
    private static final Locale EMPTY_LOCALE;
    private static final String LOCALE_ATTRIBUTE_KEY = "attribute";
    public static final ULocale ROOT;
    private static final SoftCache<Locale, ULocale, Void> CACHE;
    private volatile transient Locale locale;
    private String localeID;
    private volatile transient BaseLocale baseLocale;
    private volatile transient LocaleExtensions extensions;
    private static String[][] CANONICALIZE_MAP;
    private static Locale defaultLocale;
    private static ULocale defaultULocale;
    private static Locale[] defaultCategoryLocales;
    private static ULocale[] defaultCategoryULocales;
    private static final String LANG_DIR_STRING = "root-en-es-pt-zh-ja-ko-de-fr-it-ar+he+fa+ru-nl-pl-th-tr-";
    public static Type ACTUAL_LOCALE;
    public static Type VALID_LOCALE;
    private static final String UNDEFINED_LANGUAGE = "und";
    private static final String UNDEFINED_SCRIPT = "Zzzz";
    private static final String UNDEFINED_REGION = "ZZ";
    public static final char PRIVATE_USE_EXTENSION = 'x';
    public static final char UNICODE_LOCALE_EXTENSION = 'u';

    private ULocale(String string, Locale locale) {
        this.localeID = string;
        this.locale = locale;
    }

    private ULocale(Locale locale) {
        this.localeID = ULocale.getName(ULocale.forLocale(locale).toString());
        this.locale = locale;
    }

    public static ULocale forLocale(Locale locale) {
        if (locale == null) {
            return null;
        }
        return CACHE.getInstance(locale, null);
    }

    public ULocale(String string) {
        this.localeID = ULocale.getName(string);
    }

    public ULocale(String string, String string2) {
        this(string, string2, null);
    }

    public ULocale(String string, String string2, String string3) {
        this.localeID = ULocale.getName(ULocale.lscvToID(string, string2, string3, EMPTY_STRING));
    }

    public static ULocale createCanonical(String string) {
        return new ULocale(ULocale.canonicalize(string), (Locale)null);
    }

    private static String lscvToID(String string, String string2, String string3, String string4) {
        StringBuilder stringBuilder = new StringBuilder();
        if (string != null && string.length() > 0) {
            stringBuilder.append(string);
        }
        if (string2 != null && string2.length() > 0) {
            stringBuilder.append('_');
            stringBuilder.append(string2);
        }
        if (string3 != null && string3.length() > 0) {
            stringBuilder.append('_');
            stringBuilder.append(string3);
        }
        if (string4 != null && string4.length() > 0) {
            if (string3 == null || string3.length() == 0) {
                stringBuilder.append('_');
            }
            stringBuilder.append('_');
            stringBuilder.append(string4);
        }
        return stringBuilder.toString();
    }

    public Locale toLocale() {
        if (this.locale == null) {
            this.locale = JDKLocaleHelper.toLocale(this);
        }
        return this.locale;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ULocale getDefault() {
        Class<ULocale> clazz = ULocale.class;
        synchronized (ULocale.class) {
            if (defaultULocale == null) {
                // ** MonitorExit[var0] (shouldn't be in output)
                return ROOT;
            }
            Locale locale = Locale.getDefault();
            if (!defaultLocale.equals(locale)) {
                defaultLocale = locale;
                defaultULocale = ULocale.forLocale(locale);
                if (!JDKLocaleHelper.hasLocaleCategories()) {
                    for (Category category : Category.values()) {
                        int n = category.ordinal();
                        ULocale.defaultCategoryLocales[n] = locale;
                        ULocale.defaultCategoryULocales[n] = ULocale.forLocale(locale);
                    }
                }
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return defaultULocale;
        }
    }

    public static synchronized void setDefault(ULocale uLocale) {
        defaultLocale = uLocale.toLocale();
        Locale.setDefault(defaultLocale);
        defaultULocale = uLocale;
        for (Category category : Category.values()) {
            ULocale.setDefault(category, uLocale);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ULocale getDefault(Category category) {
        Class<ULocale> clazz = ULocale.class;
        synchronized (ULocale.class) {
            int n = category.ordinal();
            if (defaultCategoryULocales[n] == null) {
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return ROOT;
            }
            if (JDKLocaleHelper.hasLocaleCategories()) {
                Locale locale = JDKLocaleHelper.getDefault(category);
                if (!defaultCategoryLocales[n].equals(locale)) {
                    ULocale.defaultCategoryLocales[n] = locale;
                    ULocale.defaultCategoryULocales[n] = ULocale.forLocale(locale);
                }
            } else {
                Locale locale = Locale.getDefault();
                if (!defaultLocale.equals(locale)) {
                    defaultLocale = locale;
                    defaultULocale = ULocale.forLocale(locale);
                    for (Category category2 : Category.values()) {
                        int n2 = category2.ordinal();
                        ULocale.defaultCategoryLocales[n2] = locale;
                        ULocale.defaultCategoryULocales[n2] = ULocale.forLocale(locale);
                    }
                }
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return defaultCategoryULocales[n];
        }
    }

    public static synchronized void setDefault(Category category, ULocale uLocale) {
        Locale locale = uLocale.toLocale();
        int n = category.ordinal();
        ULocale.defaultCategoryULocales[n] = uLocale;
        ULocale.defaultCategoryLocales[n] = locale;
        JDKLocaleHelper.setDefault(category, locale);
    }

    public Object clone() {
        return this;
    }

    public int hashCode() {
        return this.localeID.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof ULocale) {
            return this.localeID.equals(((ULocale)object).localeID);
        }
        return true;
    }

    @Override
    public int compareTo(ULocale uLocale) {
        if (this == uLocale) {
            return 1;
        }
        int n = 0;
        n = this.getLanguage().compareTo(uLocale.getLanguage());
        if (n == 0 && (n = this.getScript().compareTo(uLocale.getScript())) == 0 && (n = this.getCountry().compareTo(uLocale.getCountry())) == 0 && (n = this.getVariant().compareTo(uLocale.getVariant())) == 0) {
            Iterator<String> iterator2 = this.getKeywords();
            Iterator<String> iterator3 = uLocale.getKeywords();
            if (iterator2 == null) {
                n = iterator3 == null ? 0 : -1;
            } else if (iterator3 == null) {
                n = 1;
            } else {
                while (n == 0 && iterator2.hasNext()) {
                    String string;
                    if (!iterator3.hasNext()) {
                        n = 1;
                        break;
                    }
                    String string2 = iterator2.next();
                    n = string2.compareTo(string = iterator3.next());
                    if (n != 0) continue;
                    String string3 = this.getKeywordValue(string2);
                    String string4 = uLocale.getKeywordValue(string);
                    if (string3 == null) {
                        n = string4 == null ? 0 : -1;
                        continue;
                    }
                    if (string4 == null) {
                        n = 1;
                        continue;
                    }
                    n = string3.compareTo(string4);
                }
                if (n == 0 && iterator3.hasNext()) {
                    n = -1;
                }
            }
        }
        return n < 0 ? -1 : (n > 0 ? 1 : 0);
    }

    public static ULocale[] getAvailableLocales() {
        return (ULocale[])ICUResourceBundle.getAvailableULocales().clone();
    }

    public static Collection<ULocale> getAvailableLocalesByType(AvailableType availableType) {
        List<Object> list;
        if (availableType == null) {
            throw new IllegalArgumentException();
        }
        if (availableType == AvailableType.WITH_LEGACY_ALIASES) {
            list = new ArrayList();
            Collections.addAll(list, ICUResourceBundle.getAvailableULocales(AvailableType.DEFAULT));
            Collections.addAll(list, ICUResourceBundle.getAvailableULocales(AvailableType.ONLY_LEGACY_ALIASES));
        } else {
            list = Arrays.asList(ICUResourceBundle.getAvailableULocales(availableType));
        }
        return Collections.unmodifiableList(list);
    }

    public static String[] getISOCountries() {
        return LocaleIDs.getISOCountries();
    }

    public static String[] getISOLanguages() {
        return LocaleIDs.getISOLanguages();
    }

    public String getLanguage() {
        return this.base().getLanguage();
    }

    public static String getLanguage(String string) {
        return new LocaleIDParser(string).getLanguage();
    }

    public String getScript() {
        return this.base().getScript();
    }

    public static String getScript(String string) {
        return new LocaleIDParser(string).getScript();
    }

    public String getCountry() {
        return this.base().getRegion();
    }

    public static String getCountry(String string) {
        return new LocaleIDParser(string).getCountry();
    }

    @Deprecated
    public static String getRegionForSupplementalData(ULocale uLocale, boolean bl) {
        Object object;
        String string = uLocale.getKeywordValue("rg");
        if (string != null && string.length() == 6 && ((String)(object = AsciiUtil.toUpperString(string))).endsWith("ZZZZ")) {
            return ((String)object).substring(0, 2);
        }
        string = uLocale.getCountry();
        if (string.length() == 0 && bl) {
            object = ULocale.addLikelySubtags(uLocale);
            string = ((ULocale)object).getCountry();
        }
        return string;
    }

    public String getVariant() {
        return this.base().getVariant();
    }

    public static String getVariant(String string) {
        return new LocaleIDParser(string).getVariant();
    }

    public static String getFallback(String string) {
        return ULocale.getFallbackString(ULocale.getName(string));
    }

    public ULocale getFallback() {
        if (this.localeID.length() == 0 || this.localeID.charAt(0) == '@') {
            return null;
        }
        return new ULocale(ULocale.getFallbackString(this.localeID), (Locale)null);
    }

    private static String getFallbackString(String string) {
        int n;
        int n2 = string.indexOf(64);
        if (n2 == -1) {
            n2 = string.length();
        }
        if ((n = string.lastIndexOf(95, n2)) == -1) {
            n = 0;
        } else {
            while (n > 0 && string.charAt(n - 1) == '_') {
                --n;
            }
        }
        return string.substring(0, n) + string.substring(n2);
    }

    public String getBaseName() {
        return ULocale.getBaseName(this.localeID);
    }

    public static String getBaseName(String string) {
        if (string.indexOf(64) == -1) {
            return string;
        }
        return new LocaleIDParser(string).getBaseName();
    }

    public String getName() {
        return this.localeID;
    }

    private static int getShortestSubtagLength(String string) {
        int n;
        int n2 = n = string.length();
        boolean bl = true;
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            if (string.charAt(i) != '_' && string.charAt(i) != '-') {
                if (bl) {
                    bl = false;
                    n3 = 0;
                }
                ++n3;
                continue;
            }
            if (n3 != 0 && n3 < n2) {
                n2 = n3;
            }
            bl = true;
        }
        return n2;
    }

    public static String getName(String string) {
        String string2;
        if (string != null && !string.contains("@") && ULocale.getShortestSubtagLength(string) == 1) {
            string2 = ULocale.forLanguageTag(string).getName();
            if (string2.length() == 0) {
                string2 = string;
            }
        } else {
            string2 = "root".equalsIgnoreCase(string) ? EMPTY_STRING : UND_PATTERN.matcher(string).replaceFirst(EMPTY_STRING);
        }
        return nameCache.getInstance(string2, null);
    }

    public String toString() {
        return this.localeID;
    }

    public Iterator<String> getKeywords() {
        return ULocale.getKeywords(this.localeID);
    }

    public static Iterator<String> getKeywords(String string) {
        return new LocaleIDParser(string).getKeywords();
    }

    public String getKeywordValue(String string) {
        return ULocale.getKeywordValue(this.localeID, string);
    }

    public static String getKeywordValue(String string, String string2) {
        return new LocaleIDParser(string).getKeywordValue(string2);
    }

    public static String canonicalize(String string) {
        LocaleIDParser localeIDParser = new LocaleIDParser(string, true);
        String string2 = localeIDParser.getBaseName();
        boolean bl = false;
        if (string.equals(EMPTY_STRING)) {
            return EMPTY_STRING;
        }
        for (int i = 0; i < CANONICALIZE_MAP.length; ++i) {
            String[] stringArray = CANONICALIZE_MAP[i];
            if (!stringArray[0].equals(string2)) continue;
            bl = true;
            localeIDParser.setBaseName(stringArray[5]);
            break;
        }
        if (!bl && localeIDParser.getLanguage().equals("nb") && localeIDParser.getVariant().equals("NY")) {
            localeIDParser.setBaseName(ULocale.lscvToID("nn", localeIDParser.getScript(), localeIDParser.getCountry(), null));
        }
        return localeIDParser.getName();
    }

    public ULocale setKeywordValue(String string, String string2) {
        return new ULocale(ULocale.setKeywordValue(this.localeID, string, string2), (Locale)null);
    }

    public static String setKeywordValue(String string, String string2, String string3) {
        LocaleIDParser localeIDParser = new LocaleIDParser(string);
        localeIDParser.setKeywordValue(string2, string3);
        return localeIDParser.getName();
    }

    public String getISO3Language() {
        return ULocale.getISO3Language(this.localeID);
    }

    public static String getISO3Language(String string) {
        return LocaleIDs.getISO3Language(ULocale.getLanguage(string));
    }

    public String getISO3Country() {
        return ULocale.getISO3Country(this.localeID);
    }

    public static String getISO3Country(String string) {
        return LocaleIDs.getISO3Country(ULocale.getCountry(string));
    }

    public boolean isRightToLeft() {
        String string = this.getScript();
        if (string.length() == 0) {
            ULocale uLocale;
            int n;
            String string2 = this.getLanguage();
            if (!string2.isEmpty() && (n = LANG_DIR_STRING.indexOf(string2)) >= 0) {
                switch (LANG_DIR_STRING.charAt(n + string2.length())) {
                    case '-': {
                        return true;
                    }
                    case '+': {
                        return false;
                    }
                }
            }
            if ((string = (uLocale = ULocale.addLikelySubtags(this)).getScript()).length() == 0) {
                return true;
            }
        }
        int n = UScript.getCodeFromName(string);
        return UScript.isRightToLeft(n);
    }

    public String getDisplayLanguage() {
        return ULocale.getDisplayLanguageInternal(this, ULocale.getDefault(Category.DISPLAY), false);
    }

    public String getDisplayLanguage(ULocale uLocale) {
        return ULocale.getDisplayLanguageInternal(this, uLocale, false);
    }

    public static String getDisplayLanguage(String string, String string2) {
        return ULocale.getDisplayLanguageInternal(new ULocale(string), new ULocale(string2), false);
    }

    public static String getDisplayLanguage(String string, ULocale uLocale) {
        return ULocale.getDisplayLanguageInternal(new ULocale(string), uLocale, false);
    }

    public String getDisplayLanguageWithDialect() {
        return ULocale.getDisplayLanguageInternal(this, ULocale.getDefault(Category.DISPLAY), true);
    }

    public String getDisplayLanguageWithDialect(ULocale uLocale) {
        return ULocale.getDisplayLanguageInternal(this, uLocale, true);
    }

    public static String getDisplayLanguageWithDialect(String string, String string2) {
        return ULocale.getDisplayLanguageInternal(new ULocale(string), new ULocale(string2), true);
    }

    public static String getDisplayLanguageWithDialect(String string, ULocale uLocale) {
        return ULocale.getDisplayLanguageInternal(new ULocale(string), uLocale, true);
    }

    private static String getDisplayLanguageInternal(ULocale uLocale, ULocale uLocale2, boolean bl) {
        String string = bl ? uLocale.getBaseName() : uLocale.getLanguage();
        return LocaleDisplayNames.getInstance(uLocale2).languageDisplayName(string);
    }

    public String getDisplayScript() {
        return ULocale.getDisplayScriptInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    @Deprecated
    public String getDisplayScriptInContext() {
        return ULocale.getDisplayScriptInContextInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayScript(ULocale uLocale) {
        return ULocale.getDisplayScriptInternal(this, uLocale);
    }

    @Deprecated
    public String getDisplayScriptInContext(ULocale uLocale) {
        return ULocale.getDisplayScriptInContextInternal(this, uLocale);
    }

    public static String getDisplayScript(String string, String string2) {
        return ULocale.getDisplayScriptInternal(new ULocale(string), new ULocale(string2));
    }

    @Deprecated
    public static String getDisplayScriptInContext(String string, String string2) {
        return ULocale.getDisplayScriptInContextInternal(new ULocale(string), new ULocale(string2));
    }

    public static String getDisplayScript(String string, ULocale uLocale) {
        return ULocale.getDisplayScriptInternal(new ULocale(string), uLocale);
    }

    @Deprecated
    public static String getDisplayScriptInContext(String string, ULocale uLocale) {
        return ULocale.getDisplayScriptInContextInternal(new ULocale(string), uLocale);
    }

    private static String getDisplayScriptInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).scriptDisplayName(uLocale.getScript());
    }

    private static String getDisplayScriptInContextInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).scriptDisplayNameInContext(uLocale.getScript());
    }

    public String getDisplayCountry() {
        return ULocale.getDisplayCountryInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayCountry(ULocale uLocale) {
        return ULocale.getDisplayCountryInternal(this, uLocale);
    }

    public static String getDisplayCountry(String string, String string2) {
        return ULocale.getDisplayCountryInternal(new ULocale(string), new ULocale(string2));
    }

    public static String getDisplayCountry(String string, ULocale uLocale) {
        return ULocale.getDisplayCountryInternal(new ULocale(string), uLocale);
    }

    private static String getDisplayCountryInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).regionDisplayName(uLocale.getCountry());
    }

    public String getDisplayVariant() {
        return ULocale.getDisplayVariantInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayVariant(ULocale uLocale) {
        return ULocale.getDisplayVariantInternal(this, uLocale);
    }

    public static String getDisplayVariant(String string, String string2) {
        return ULocale.getDisplayVariantInternal(new ULocale(string), new ULocale(string2));
    }

    public static String getDisplayVariant(String string, ULocale uLocale) {
        return ULocale.getDisplayVariantInternal(new ULocale(string), uLocale);
    }

    private static String getDisplayVariantInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).variantDisplayName(uLocale.getVariant());
    }

    public static String getDisplayKeyword(String string) {
        return ULocale.getDisplayKeywordInternal(string, ULocale.getDefault(Category.DISPLAY));
    }

    public static String getDisplayKeyword(String string, String string2) {
        return ULocale.getDisplayKeywordInternal(string, new ULocale(string2));
    }

    public static String getDisplayKeyword(String string, ULocale uLocale) {
        return ULocale.getDisplayKeywordInternal(string, uLocale);
    }

    private static String getDisplayKeywordInternal(String string, ULocale uLocale) {
        return LocaleDisplayNames.getInstance(uLocale).keyDisplayName(string);
    }

    public String getDisplayKeywordValue(String string) {
        return ULocale.getDisplayKeywordValueInternal(this, string, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayKeywordValue(String string, ULocale uLocale) {
        return ULocale.getDisplayKeywordValueInternal(this, string, uLocale);
    }

    public static String getDisplayKeywordValue(String string, String string2, String string3) {
        return ULocale.getDisplayKeywordValueInternal(new ULocale(string), string2, new ULocale(string3));
    }

    public static String getDisplayKeywordValue(String string, String string2, ULocale uLocale) {
        return ULocale.getDisplayKeywordValueInternal(new ULocale(string), string2, uLocale);
    }

    private static String getDisplayKeywordValueInternal(ULocale uLocale, String string, ULocale uLocale2) {
        string = AsciiUtil.toLowerString(string.trim());
        String string2 = uLocale.getKeywordValue(string);
        return LocaleDisplayNames.getInstance(uLocale2).keyValueDisplayName(string, string2);
    }

    public String getDisplayName() {
        return ULocale.getDisplayNameInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayName(ULocale uLocale) {
        return ULocale.getDisplayNameInternal(this, uLocale);
    }

    public static String getDisplayName(String string, String string2) {
        return ULocale.getDisplayNameInternal(new ULocale(string), new ULocale(string2));
    }

    public static String getDisplayName(String string, ULocale uLocale) {
        return ULocale.getDisplayNameInternal(new ULocale(string), uLocale);
    }

    private static String getDisplayNameInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).localeDisplayName(uLocale);
    }

    public String getDisplayNameWithDialect() {
        return ULocale.getDisplayNameWithDialectInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayNameWithDialect(ULocale uLocale) {
        return ULocale.getDisplayNameWithDialectInternal(this, uLocale);
    }

    public static String getDisplayNameWithDialect(String string, String string2) {
        return ULocale.getDisplayNameWithDialectInternal(new ULocale(string), new ULocale(string2));
    }

    public static String getDisplayNameWithDialect(String string, ULocale uLocale) {
        return ULocale.getDisplayNameWithDialectInternal(new ULocale(string), uLocale);
    }

    private static String getDisplayNameWithDialectInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2, LocaleDisplayNames.DialectHandling.DIALECT_NAMES).localeDisplayName(uLocale);
    }

    public String getCharacterOrientation() {
        return ICUResourceTableAccess.getTableString("com/ibm/icu/impl/data/icudt66b", this, "layout", "characters", "characters");
    }

    public String getLineOrientation() {
        return ICUResourceTableAccess.getTableString("com/ibm/icu/impl/data/icudt66b", this, "layout", "lines", "lines");
    }

    public static ULocale acceptLanguage(String string, ULocale[] uLocaleArray, boolean[] blArray) {
        if (string == null) {
            throw new NullPointerException();
        }
        ULocale[] uLocaleArray2 = null;
        try {
            uLocaleArray2 = ULocale.parseAcceptLanguage(string, true);
        } catch (ParseException parseException) {
            uLocaleArray2 = null;
        }
        if (uLocaleArray2 == null) {
            return null;
        }
        return ULocale.acceptLanguage(uLocaleArray2, uLocaleArray, blArray);
    }

    public static ULocale acceptLanguage(ULocale[] uLocaleArray, ULocale[] uLocaleArray2, boolean[] blArray) {
        if (blArray != null) {
            blArray[0] = true;
        }
        for (int i = 0; i < uLocaleArray.length; ++i) {
            ULocale uLocale = uLocaleArray[i];
            boolean[] blArray2 = blArray;
            do {
                Serializable serializable;
                for (int j = 0; j < uLocaleArray2.length; ++j) {
                    if (uLocaleArray2[j].equals(uLocale)) {
                        if (blArray2 != null) {
                            blArray2[0] = false;
                        }
                        return uLocaleArray2[j];
                    }
                    if (uLocale.getScript().length() != 0 || uLocaleArray2[j].getScript().length() <= 0 || !uLocaleArray2[j].getLanguage().equals(uLocale.getLanguage()) || !uLocaleArray2[j].getCountry().equals(uLocale.getCountry()) || !uLocaleArray2[j].getVariant().equals(uLocale.getVariant()) || ((ULocale)(serializable = ULocale.minimizeSubtags(uLocaleArray2[j]))).getScript().length() != 0) continue;
                    if (blArray2 != null) {
                        blArray2[0] = false;
                    }
                    return uLocale;
                }
                serializable = uLocale.toLocale();
                Locale locale = LocaleUtility.fallback((Locale)serializable);
                uLocale = locale != null ? new ULocale(locale) : null;
                blArray2 = null;
            } while (uLocale != null);
        }
        return null;
    }

    public static ULocale acceptLanguage(String string, boolean[] blArray) {
        return ULocale.acceptLanguage(string, ULocale.getAvailableLocales(), blArray);
    }

    public static ULocale acceptLanguage(ULocale[] uLocaleArray, boolean[] blArray) {
        return ULocale.acceptLanguage(uLocaleArray, ULocale.getAvailableLocales(), blArray);
    }

    static ULocale[] parseAcceptLanguage(String string, boolean bl) throws ParseException {
        int n;
        class ULocaleAcceptLanguageQ
        implements Comparable<ULocaleAcceptLanguageQ> {
            private double q;
            private double serial;

            public ULocaleAcceptLanguageQ(double d, int n) {
                this.q = d;
                this.serial = n;
            }

            @Override
            public int compareTo(ULocaleAcceptLanguageQ uLocaleAcceptLanguageQ) {
                if (this.q > uLocaleAcceptLanguageQ.q) {
                    return 1;
                }
                if (this.q < uLocaleAcceptLanguageQ.q) {
                    return 0;
                }
                if (this.serial < uLocaleAcceptLanguageQ.serial) {
                    return 1;
                }
                if (this.serial > uLocaleAcceptLanguageQ.serial) {
                    return 0;
                }
                return 1;
            }

            @Override
            public int compareTo(Object object) {
                return this.compareTo((ULocaleAcceptLanguageQ)object);
            }
        }
        TreeMap<ULocaleAcceptLanguageQ, ULocale> treeMap = new TreeMap<ULocaleAcceptLanguageQ, ULocale>();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        int n2 = 0;
        string = string + ",";
        boolean bl2 = false;
        boolean bl3 = false;
        for (n = 0; n < string.length(); ++n) {
            boolean bl4 = false;
            char c = string.charAt(n);
            switch (n2) {
                case 0: {
                    if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z') {
                        stringBuilder.append(c);
                        n2 = 1;
                        bl2 = false;
                        break;
                    }
                    if (c == '*') {
                        stringBuilder.append(c);
                        n2 = 2;
                        break;
                    }
                    if (c == ' ' || c == '\t') break;
                    n2 = -1;
                    break;
                }
                case 1: {
                    if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z') {
                        stringBuilder.append(c);
                        break;
                    }
                    if (c == '-') {
                        bl2 = true;
                        stringBuilder.append(c);
                        break;
                    }
                    if (c == '_') {
                        if (bl) {
                            bl2 = true;
                            stringBuilder.append(c);
                            break;
                        }
                        n2 = -1;
                        break;
                    }
                    if ('0' <= c && c <= '9') {
                        if (bl2) {
                            stringBuilder.append(c);
                            break;
                        }
                        n2 = -1;
                        break;
                    }
                    if (c == ',') {
                        bl4 = true;
                        break;
                    }
                    if (c == ' ' || c == '\t') {
                        n2 = 3;
                        break;
                    }
                    if (c == ';') {
                        n2 = 4;
                        break;
                    }
                    n2 = -1;
                    break;
                }
                case 2: {
                    if (c == ',') {
                        bl4 = true;
                        break;
                    }
                    if (c == ' ' || c == '\t') {
                        n2 = 3;
                        break;
                    }
                    if (c == ';') {
                        n2 = 4;
                        break;
                    }
                    n2 = -1;
                    break;
                }
                case 3: {
                    if (c == ',') {
                        bl4 = true;
                        break;
                    }
                    if (c == ';') {
                        n2 = 4;
                        break;
                    }
                    if (c == ' ' || c == '\t') break;
                    n2 = -1;
                    break;
                }
                case 4: {
                    if (c == 'q') {
                        n2 = 5;
                        break;
                    }
                    if (c == ' ' || c == '\t') break;
                    n2 = -1;
                    break;
                }
                case 5: {
                    if (c == '=') {
                        n2 = 6;
                        break;
                    }
                    if (c == ' ' || c == '\t') break;
                    n2 = -1;
                    break;
                }
                case 6: {
                    if (c == '0') {
                        bl3 = false;
                        stringBuilder2.append(c);
                        n2 = 7;
                        break;
                    }
                    if (c == '1') {
                        stringBuilder2.append(c);
                        n2 = 7;
                        break;
                    }
                    if (c == '.') {
                        if (bl) {
                            stringBuilder2.append(c);
                            n2 = 8;
                            break;
                        }
                        n2 = -1;
                        break;
                    }
                    if (c == ' ' || c == '\t') break;
                    n2 = -1;
                    break;
                }
                case 7: {
                    if (c == '.') {
                        stringBuilder2.append(c);
                        n2 = 8;
                        break;
                    }
                    if (c == ',') {
                        bl4 = true;
                        break;
                    }
                    if (c == ' ' || c == '\t') {
                        n2 = 10;
                        break;
                    }
                    n2 = -1;
                    break;
                }
                case 8: {
                    if ('0' <= c && c <= '9') {
                        if (bl3 && c != '0' && !bl) {
                            n2 = -1;
                            break;
                        }
                        stringBuilder2.append(c);
                        n2 = 9;
                        break;
                    }
                    n2 = -1;
                    break;
                }
                case 9: {
                    if ('0' <= c && c <= '9') {
                        if (bl3 && c != '0') {
                            n2 = -1;
                            break;
                        }
                        stringBuilder2.append(c);
                        break;
                    }
                    if (c == ',') {
                        bl4 = true;
                        break;
                    }
                    if (c == ' ' || c == '\t') {
                        n2 = 10;
                        break;
                    }
                    n2 = -1;
                    break;
                }
                case 10: {
                    if (c == ',') {
                        bl4 = true;
                        break;
                    }
                    if (c == ' ' || c == '\t') break;
                    n2 = -1;
                }
            }
            if (n2 == -1) {
                throw new ParseException("Invalid Accept-Language", n);
            }
            if (!bl4) continue;
            double d = 1.0;
            if (stringBuilder2.length() != 0) {
                try {
                    d = Double.parseDouble(stringBuilder2.toString());
                } catch (NumberFormatException numberFormatException) {
                    d = 1.0;
                }
                if (d > 1.0) {
                    d = 1.0;
                }
            }
            if (stringBuilder.charAt(0) != '*') {
                int n3 = treeMap.size();
                ULocaleAcceptLanguageQ uLocaleAcceptLanguageQ = new ULocaleAcceptLanguageQ(d, n3);
                treeMap.put(uLocaleAcceptLanguageQ, new ULocale(ULocale.canonicalize(stringBuilder.toString())));
            }
            stringBuilder.setLength(0);
            stringBuilder2.setLength(0);
            n2 = 0;
        }
        if (n2 != 0) {
            throw new ParseException("Invalid AcceptlLanguage", n);
        }
        ULocale[] uLocaleArray = treeMap.values().toArray(new ULocale[treeMap.size()]);
        return uLocaleArray;
    }

    public static ULocale addLikelySubtags(ULocale uLocale) {
        String string;
        String[] stringArray = new String[3];
        String string2 = null;
        int n = ULocale.parseTagString(uLocale.localeID, stringArray);
        if (n < uLocale.localeID.length()) {
            string2 = uLocale.localeID.substring(n);
        }
        return (string = ULocale.createLikelySubtagsString(stringArray[0], stringArray[5], stringArray[5], string2)) == null ? uLocale : new ULocale(string);
    }

    public static ULocale minimizeSubtags(ULocale uLocale) {
        return ULocale.minimizeSubtags(uLocale, Minimize.FAVOR_REGION);
    }

    @Deprecated
    public static ULocale minimizeSubtags(ULocale uLocale, Minimize minimize) {
        String string;
        String[] stringArray = new String[3];
        int n = ULocale.parseTagString(uLocale.localeID, stringArray);
        String string2 = stringArray[0];
        String string3 = stringArray[5];
        String string4 = stringArray[5];
        String string5 = null;
        if (n < uLocale.localeID.length()) {
            string5 = uLocale.localeID.substring(n);
        }
        if (ULocale.isEmptyString(string = ULocale.createLikelySubtagsString(string2, string3, string4, null))) {
            return uLocale;
        }
        String string6 = ULocale.createLikelySubtagsString(string2, null, null, null);
        if (string6.equals(string)) {
            String string7 = ULocale.createTagString(string2, null, null, string5);
            return new ULocale(string7);
        }
        if (minimize == Minimize.FAVOR_REGION) {
            if (string4.length() != 0 && (string6 = ULocale.createLikelySubtagsString(string2, null, string4, null)).equals(string)) {
                String string8 = ULocale.createTagString(string2, null, string4, string5);
                return new ULocale(string8);
            }
            if (string3.length() != 0 && (string6 = ULocale.createLikelySubtagsString(string2, string3, null, null)).equals(string)) {
                String string9 = ULocale.createTagString(string2, string3, null, string5);
                return new ULocale(string9);
            }
        } else {
            if (string3.length() != 0 && (string6 = ULocale.createLikelySubtagsString(string2, string3, null, null)).equals(string)) {
                String string10 = ULocale.createTagString(string2, string3, null, string5);
                return new ULocale(string10);
            }
            if (string4.length() != 0 && (string6 = ULocale.createLikelySubtagsString(string2, null, string4, null)).equals(string)) {
                String string11 = ULocale.createTagString(string2, null, string4, string5);
                return new ULocale(string11);
            }
        }
        return uLocale;
    }

    private static boolean isEmptyString(String string) {
        return string == null || string.length() == 0;
    }

    private static void appendTag(String string, StringBuilder stringBuilder) {
        if (stringBuilder.length() != 0) {
            stringBuilder.append('_');
        }
        stringBuilder.append(string);
    }

    private static String createTagString(String string, String string2, String string3, String string4, String string5) {
        String string6;
        LocaleIDParser localeIDParser = null;
        boolean bl = false;
        StringBuilder stringBuilder = new StringBuilder();
        if (!ULocale.isEmptyString(string)) {
            ULocale.appendTag(string, stringBuilder);
        } else if (ULocale.isEmptyString(string5)) {
            ULocale.appendTag(UNDEFINED_LANGUAGE, stringBuilder);
        } else {
            localeIDParser = new LocaleIDParser(string5);
            string6 = localeIDParser.getLanguage();
            ULocale.appendTag(!ULocale.isEmptyString(string6) ? string6 : UNDEFINED_LANGUAGE, stringBuilder);
        }
        if (!ULocale.isEmptyString(string2)) {
            ULocale.appendTag(string2, stringBuilder);
        } else if (!ULocale.isEmptyString(string5)) {
            if (localeIDParser == null) {
                localeIDParser = new LocaleIDParser(string5);
            }
            if (!ULocale.isEmptyString(string6 = localeIDParser.getScript())) {
                ULocale.appendTag(string6, stringBuilder);
            }
        }
        if (!ULocale.isEmptyString(string3)) {
            ULocale.appendTag(string3, stringBuilder);
            bl = true;
        } else if (!ULocale.isEmptyString(string5)) {
            if (localeIDParser == null) {
                localeIDParser = new LocaleIDParser(string5);
            }
            if (!ULocale.isEmptyString(string6 = localeIDParser.getCountry())) {
                ULocale.appendTag(string6, stringBuilder);
                bl = true;
            }
        }
        if (string4 != null && string4.length() > 1) {
            int n = 0;
            if (string4.charAt(0) == '_') {
                if (string4.charAt(1) == '_') {
                    n = 2;
                }
            } else {
                n = 1;
            }
            if (bl) {
                if (n == 2) {
                    stringBuilder.append(string4.substring(1));
                } else {
                    stringBuilder.append(string4);
                }
            } else {
                if (n == 1) {
                    stringBuilder.append('_');
                }
                stringBuilder.append(string4);
            }
        }
        return stringBuilder.toString();
    }

    static String createTagString(String string, String string2, String string3, String string4) {
        return ULocale.createTagString(string, string2, string3, string4, null);
    }

    private static int parseTagString(String string, String[] stringArray) {
        LocaleIDParser localeIDParser = new LocaleIDParser(string);
        String string2 = localeIDParser.getLanguage();
        String string3 = localeIDParser.getScript();
        String string4 = localeIDParser.getCountry();
        stringArray[0] = ULocale.isEmptyString(string2) ? UNDEFINED_LANGUAGE : string2;
        stringArray[1] = string3.equals(UNDEFINED_SCRIPT) ? EMPTY_STRING : string3;
        stringArray[2] = string4.equals(UNDEFINED_REGION) ? EMPTY_STRING : string4;
        String string5 = localeIDParser.getVariant();
        if (!ULocale.isEmptyString(string5)) {
            int n = string.indexOf(string5);
            return n > 0 ? n - 1 : n;
        }
        int n = string.indexOf(64);
        return n == -1 ? string.length() : n;
    }

    private static String lookupLikelySubtags(String string) {
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "likelySubtags");
        try {
            return uResourceBundle.getString(string);
        } catch (MissingResourceException missingResourceException) {
            return null;
        }
    }

    private static String createLikelySubtagsString(String string, String string2, String string3, String string4) {
        String string5;
        String string6;
        if (!ULocale.isEmptyString(string2) && !ULocale.isEmptyString(string3) && (string6 = ULocale.lookupLikelySubtags(string5 = ULocale.createTagString(string, string2, string3, null))) != null) {
            return ULocale.createTagString(null, null, null, string4, string6);
        }
        if (!ULocale.isEmptyString(string2) && (string6 = ULocale.lookupLikelySubtags(string5 = ULocale.createTagString(string, string2, null, null))) != null) {
            return ULocale.createTagString(null, null, string3, string4, string6);
        }
        if (!ULocale.isEmptyString(string3) && (string6 = ULocale.lookupLikelySubtags(string5 = ULocale.createTagString(string, null, string3, null))) != null) {
            return ULocale.createTagString(null, string2, null, string4, string6);
        }
        string5 = ULocale.createTagString(string, null, null, null);
        string6 = ULocale.lookupLikelySubtags(string5);
        if (string6 != null) {
            return ULocale.createTagString(null, string2, string3, string4, string6);
        }
        return null;
    }

    public String getExtension(char c) {
        if (!LocaleExtensions.isValidKey(c)) {
            throw new IllegalArgumentException("Invalid extension key: " + c);
        }
        return this.extensions().getExtensionValue(Character.valueOf(c));
    }

    public Set<Character> getExtensionKeys() {
        return this.extensions().getKeys();
    }

    public Set<String> getUnicodeLocaleAttributes() {
        return this.extensions().getUnicodeLocaleAttributes();
    }

    public String getUnicodeLocaleType(String string) {
        if (!LocaleExtensions.isValidUnicodeLocaleKey(string)) {
            throw new IllegalArgumentException("Invalid Unicode locale key: " + string);
        }
        return this.extensions().getUnicodeLocaleType(string);
    }

    public Set<String> getUnicodeLocaleKeys() {
        return this.extensions().getUnicodeLocaleKeys();
    }

    public String toLanguageTag() {
        Object object;
        BaseLocale baseLocale = this.base();
        LocaleExtensions localeExtensions = this.extensions();
        if (baseLocale.getVariant().equalsIgnoreCase("POSIX")) {
            baseLocale = BaseLocale.getInstance(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), EMPTY_STRING);
            if (localeExtensions.getUnicodeLocaleType("va") == null) {
                object = new InternalLocaleBuilder();
                try {
                    ((InternalLocaleBuilder)object).setLocale(BaseLocale.ROOT, localeExtensions);
                    ((InternalLocaleBuilder)object).setUnicodeLocaleKeyword("va", "posix");
                    localeExtensions = ((InternalLocaleBuilder)object).getLocaleExtensions();
                } catch (LocaleSyntaxException localeSyntaxException) {
                    throw new RuntimeException(localeSyntaxException);
                }
            }
        }
        object = LanguageTag.parseLocale(baseLocale, localeExtensions);
        StringBuilder stringBuilder = new StringBuilder();
        String string = ((LanguageTag)object).getLanguage();
        if (string.length() > 0) {
            stringBuilder.append(LanguageTag.canonicalizeLanguage(string));
        }
        if ((string = ((LanguageTag)object).getScript()).length() > 0) {
            stringBuilder.append("-");
            stringBuilder.append(LanguageTag.canonicalizeScript(string));
        }
        if ((string = ((LanguageTag)object).getRegion()).length() > 0) {
            stringBuilder.append("-");
            stringBuilder.append(LanguageTag.canonicalizeRegion(string));
        }
        List<String> list = ((LanguageTag)object).getVariants();
        for (String string2 : list) {
            stringBuilder.append("-");
            stringBuilder.append(LanguageTag.canonicalizeVariant(string2));
        }
        list = ((LanguageTag)object).getExtensions();
        for (String string2 : list) {
            stringBuilder.append("-");
            stringBuilder.append(LanguageTag.canonicalizeExtension(string2));
        }
        string = ((LanguageTag)object).getPrivateuse();
        if (string.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("-");
            }
            stringBuilder.append("x").append("-");
            stringBuilder.append(LanguageTag.canonicalizePrivateuse(string));
        }
        return stringBuilder.toString();
    }

    public static ULocale forLanguageTag(String string) {
        LanguageTag languageTag = LanguageTag.parse(string, null);
        InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
        internalLocaleBuilder.setLanguageTag(languageTag);
        return ULocale.getInstance(internalLocaleBuilder.getBaseLocale(), internalLocaleBuilder.getLocaleExtensions());
    }

    public static String toUnicodeLocaleKey(String string) {
        String string2 = KeyTypeData.toBcpKey(string);
        if (string2 == null && UnicodeLocaleExtension.isKey(string)) {
            string2 = AsciiUtil.toLowerString(string);
        }
        return string2;
    }

    public static String toUnicodeLocaleType(String string, String string2) {
        String string3 = KeyTypeData.toBcpType(string, string2, null, null);
        if (string3 == null && UnicodeLocaleExtension.isType(string2)) {
            string3 = AsciiUtil.toLowerString(string2);
        }
        return string3;
    }

    public static String toLegacyKey(String string) {
        String string2 = KeyTypeData.toLegacyKey(string);
        if (string2 == null && string.matches("[0-9a-zA-Z]+")) {
            string2 = AsciiUtil.toLowerString(string);
        }
        return string2;
    }

    public static String toLegacyType(String string, String string2) {
        String string3 = KeyTypeData.toLegacyType(string, string2, null, null);
        if (string3 == null && string2.matches("[0-9a-zA-Z]+([_/\\-][0-9a-zA-Z]+)*")) {
            string3 = AsciiUtil.toLowerString(string2);
        }
        return string3;
    }

    private static ULocale getInstance(BaseLocale baseLocale, LocaleExtensions localeExtensions) {
        String string = ULocale.lscvToID(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), baseLocale.getVariant());
        Set<Character> set = localeExtensions.getKeys();
        if (!set.isEmpty()) {
            TreeMap<String, String> treeMap = new TreeMap<String, String>();
            for (Character object3 : set) {
                Extension bl = localeExtensions.getExtension(object3);
                if (bl instanceof UnicodeLocaleExtension) {
                    CharSequence charSequence;
                    UnicodeLocaleExtension unicodeLocaleExtension = (UnicodeLocaleExtension)bl;
                    Object object = unicodeLocaleExtension.getUnicodeLocaleKeys();
                    Set<String> set2 = object.iterator();
                    while (set2.hasNext()) {
                        charSequence = set2.next();
                        String string2 = unicodeLocaleExtension.getUnicodeLocaleType((String)charSequence);
                        String string3 = ULocale.toLegacyKey((String)charSequence);
                        String string4 = ULocale.toLegacyType((String)charSequence, (String)(string2.length() == 0 ? "yes" : string2));
                        if (string3.equals("va") && string4.equals("posix") && baseLocale.getVariant().length() == 0) {
                            string = string + "_POSIX";
                            continue;
                        }
                        treeMap.put(string3, string4);
                    }
                    set2 = unicodeLocaleExtension.getUnicodeLocaleAttributes();
                    if (set2.size() <= 0) continue;
                    charSequence = new StringBuilder();
                    for (String string3 : set2) {
                        if (((StringBuilder)charSequence).length() > 0) {
                            ((StringBuilder)charSequence).append('-');
                        }
                        ((StringBuilder)charSequence).append(string3);
                    }
                    treeMap.put(LOCALE_ATTRIBUTE_KEY, ((StringBuilder)charSequence).toString());
                    continue;
                }
                treeMap.put(String.valueOf(object3), bl.getValue());
            }
            if (!treeMap.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder(string);
                stringBuilder.append("@");
                Set set3 = treeMap.entrySet();
                boolean bl = false;
                for (Object object : set3) {
                    if (bl) {
                        stringBuilder.append(";");
                    } else {
                        bl = true;
                    }
                    stringBuilder.append((String)object.getKey());
                    stringBuilder.append("=");
                    stringBuilder.append((String)object.getValue());
                }
                string = stringBuilder.toString();
            }
        }
        return new ULocale(string);
    }

    private BaseLocale base() {
        if (this.baseLocale == null) {
            String string = EMPTY_STRING;
            String string2 = EMPTY_STRING;
            String string3 = EMPTY_STRING;
            String string4 = EMPTY_STRING;
            if (!this.equals(ROOT)) {
                LocaleIDParser localeIDParser = new LocaleIDParser(this.localeID);
                string4 = localeIDParser.getLanguage();
                string3 = localeIDParser.getScript();
                string2 = localeIDParser.getCountry();
                string = localeIDParser.getVariant();
            }
            this.baseLocale = BaseLocale.getInstance(string4, string3, string2, string);
        }
        return this.baseLocale;
    }

    private LocaleExtensions extensions() {
        if (this.extensions == null) {
            Iterator<String> iterator2 = this.getKeywords();
            if (iterator2 == null) {
                this.extensions = LocaleExtensions.EMPTY_EXTENSIONS;
            } else {
                InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
                while (iterator2.hasNext()) {
                    Object object;
                    String string = iterator2.next();
                    if (string.equals(LOCALE_ATTRIBUTE_KEY)) {
                        for (String string2 : object = this.getKeywordValue(string).split("[-_]")) {
                            try {
                                internalLocaleBuilder.addUnicodeLocaleAttribute(string2);
                            } catch (LocaleSyntaxException localeSyntaxException) {
                                // empty catch block
                            }
                        }
                        continue;
                    }
                    if (string.length() >= 2) {
                        object = ULocale.toUnicodeLocaleKey(string);
                        String string3 = ULocale.toUnicodeLocaleType(string, this.getKeywordValue(string));
                        if (object == null || string3 == null) continue;
                        try {
                            internalLocaleBuilder.setUnicodeLocaleKeyword((String)object, string3);
                        } catch (LocaleSyntaxException localeSyntaxException) {}
                        continue;
                    }
                    if (string.length() != 1 || string.charAt(0) == 'u') continue;
                    try {
                        internalLocaleBuilder.setExtension(string.charAt(0), this.getKeywordValue(string).replace("_", "-"));
                    } catch (LocaleSyntaxException localeSyntaxException) {
                    }
                }
                this.extensions = internalLocaleBuilder.getLocaleExtensions();
            }
        }
        return this.extensions;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ULocale)object);
    }

    static BaseLocale access$100(ULocale uLocale) {
        return uLocale.base();
    }

    static LocaleExtensions access$200(ULocale uLocale) {
        return uLocale.extensions();
    }

    static ULocale access$300(BaseLocale baseLocale, LocaleExtensions localeExtensions) {
        return ULocale.getInstance(baseLocale, localeExtensions);
    }

    ULocale(String string, Locale locale, 1 var3_3) {
        this(string, locale);
    }

    static {
        PRC = CHINA = new ULocale("zh_Hans_CN");
        TAIWAN = new ULocale("zh_Hant_TW");
        UK = new ULocale("en_GB", Locale.UK);
        US = new ULocale("en_US", Locale.US);
        CANADA = new ULocale("en_CA", Locale.CANADA);
        CANADA_FRENCH = new ULocale("fr_CA", Locale.CANADA_FRENCH);
        EMPTY_LOCALE = new Locale(EMPTY_STRING, EMPTY_STRING);
        ROOT = new ULocale(EMPTY_STRING, EMPTY_LOCALE);
        CACHE = new SoftCache<Locale, ULocale, Void>(){

            @Override
            protected ULocale createInstance(Locale locale, Void void_) {
                return JDKLocaleHelper.toULocale(locale);
            }

            @Override
            protected Object createInstance(Object object, Object object2) {
                return this.createInstance((Locale)object, (Void)object2);
            }
        };
        CANONICALIZE_MAP = new String[][]{{"art_LOJBAN", "jbo"}, {"cel_GAULISH", "cel__GAULISH"}, {"de_1901", "de__1901"}, {"de_1906", "de__1906"}, {"en_BOONT", "en__BOONT"}, {"en_SCOUSE", "en__SCOUSE"}, {"hy__AREVELA", "hy", null, null}, {"hy__AREVMDA", "hyw", null, null}, {"sl_ROZAJ", "sl__ROZAJ"}, {"zh_GAN", "zh__GAN"}, {"zh_GUOYU", "zh"}, {"zh_HAKKA", "zh__HAKKA"}, {"zh_MIN", "zh__MIN"}, {"zh_MIN_NAN", "zh__MINNAN"}, {"zh_WUU", "zh__WUU"}, {"zh_XIANG", "zh__XIANG"}, {"zh_YUE", "zh__YUE"}};
        defaultLocale = Locale.getDefault();
        defaultCategoryLocales = new Locale[Category.values().length];
        defaultCategoryULocales = new ULocale[Category.values().length];
        defaultULocale = ULocale.forLocale(defaultLocale);
        if (JDKLocaleHelper.hasLocaleCategories()) {
            for (Category category : Category.values()) {
                int n = category.ordinal();
                ULocale.defaultCategoryLocales[n] = JDKLocaleHelper.getDefault(category);
                ULocale.defaultCategoryULocales[n] = ULocale.forLocale(defaultCategoryLocales[n]);
            }
        } else {
            for (Category category : Category.values()) {
                int n = category.ordinal();
                ULocale.defaultCategoryLocales[n] = defaultLocale;
                ULocale.defaultCategoryULocales[n] = defaultULocale;
            }
        }
        ACTUAL_LOCALE = new Type(null);
        VALID_LOCALE = new Type(null);
    }

    private static final class JDKLocaleHelper {
        private static boolean hasLocaleCategories = false;
        private static Method mGetDefault;
        private static Method mSetDefault;
        private static Object eDISPLAY;
        private static Object eFORMAT;

        private JDKLocaleHelper() {
        }

        public static boolean hasLocaleCategories() {
            return hasLocaleCategories;
        }

        public static ULocale toULocale(Locale locale) {
            Object object;
            String string2 = locale.getLanguage();
            String string3 = ULocale.EMPTY_STRING;
            String string4 = locale.getCountry();
            String string5 = locale.getVariant();
            TreeSet<Object> treeSet = null;
            TreeMap treeMap = null;
            string3 = locale.getScript();
            Set<Character> set = locale.getExtensionKeys();
            if (!set.isEmpty()) {
                for (Character serializable : set) {
                    Object object2;
                    if (serializable.charValue() == 'u') {
                        object2 = locale.getUnicodeLocaleAttributes();
                        if (!object2.isEmpty()) {
                            treeSet = new TreeSet<Object>();
                            Iterator iterator2 = object2.iterator();
                            while (iterator2.hasNext()) {
                                object = (String)iterator2.next();
                                treeSet.add(object);
                            }
                        }
                        Set<String> set2 = locale.getUnicodeLocaleKeys();
                        for (String string : set2) {
                            String string6 = locale.getUnicodeLocaleType(string);
                            if (string6 == null) continue;
                            if (string.equals("va")) {
                                string5 = string5.length() == 0 ? string6 : string6 + "_" + string5;
                                continue;
                            }
                            if (treeMap == null) {
                                treeMap = new TreeMap();
                            }
                            treeMap.put(string, string6);
                        }
                        continue;
                    }
                    object2 = locale.getExtension(serializable.charValue());
                    if (object2 == null) continue;
                    if (treeMap == null) {
                        treeMap = new TreeMap();
                    }
                    treeMap.put(String.valueOf(serializable), object2);
                }
            }
            if (string2.equals("no") && string4.equals("NO") && string5.equals("NY")) {
                string2 = "nn";
                string5 = ULocale.EMPTY_STRING;
            }
            StringBuilder stringBuilder = new StringBuilder(string2);
            if (string3.length() > 0) {
                stringBuilder.append('_');
                stringBuilder.append(string3);
            }
            if (string4.length() > 0) {
                stringBuilder.append('_');
                stringBuilder.append(string4);
            }
            if (string5.length() > 0) {
                if (string4.length() == 0) {
                    stringBuilder.append('_');
                }
                stringBuilder.append('_');
                stringBuilder.append(string5);
            }
            if (treeSet != null) {
                StringBuilder bl = new StringBuilder();
                for (String string : treeSet) {
                    if (bl.length() != 0) {
                        bl.append('-');
                    }
                    bl.append(string);
                }
                if (treeMap == null) {
                    treeMap = new TreeMap();
                }
                treeMap.put(ULocale.LOCALE_ATTRIBUTE_KEY, bl.toString());
            }
            if (treeMap != null) {
                stringBuilder.append('@');
                boolean bl = false;
                for (Map.Entry entry : treeMap.entrySet()) {
                    boolean bl2;
                    String string;
                    object = (String)entry.getKey();
                    string = (String)entry.getValue();
                    if (((String)object).length() != 1) {
                        object = ULocale.toLegacyKey((String)object);
                        string = ULocale.toLegacyType((String)object, string.length() == 0 ? "yes" : string);
                    }
                    if (bl2) {
                        stringBuilder.append(';');
                    } else {
                        bl2 = true;
                    }
                    stringBuilder.append((String)object);
                    stringBuilder.append('=');
                    stringBuilder.append(string);
                }
            }
            return new ULocale(ULocale.getName(stringBuilder.toString()), locale, null);
        }

        public static Locale toLocale(ULocale uLocale) {
            Locale locale = null;
            String string = uLocale.getName();
            if (uLocale.getScript().length() > 0 || string.contains("@")) {
                String string2 = uLocale.toLanguageTag();
                string2 = AsciiUtil.toUpperString(string2);
                locale = Locale.forLanguageTag(string2);
            }
            if (locale == null) {
                locale = new Locale(uLocale.getLanguage(), uLocale.getCountry(), uLocale.getVariant());
            }
            return locale;
        }

        public static Locale getDefault(Category category) {
            if (hasLocaleCategories) {
                Object object = null;
                switch (3.$SwitchMap$com$ibm$icu$util$ULocale$Category[category.ordinal()]) {
                    case 1: {
                        object = eDISPLAY;
                        break;
                    }
                    case 2: {
                        object = eFORMAT;
                    }
                }
                if (object != null) {
                    try {
                        return (Locale)mGetDefault.invoke(null, object);
                    } catch (InvocationTargetException invocationTargetException) {
                    } catch (IllegalArgumentException illegalArgumentException) {
                    } catch (IllegalAccessException illegalAccessException) {
                        // empty catch block
                    }
                }
            }
            return Locale.getDefault();
        }

        public static void setDefault(Category category, Locale locale) {
            if (hasLocaleCategories) {
                Object object = null;
                switch (3.$SwitchMap$com$ibm$icu$util$ULocale$Category[category.ordinal()]) {
                    case 1: {
                        object = eDISPLAY;
                        break;
                    }
                    case 2: {
                        object = eFORMAT;
                    }
                }
                if (object != null) {
                    try {
                        mSetDefault.invoke(null, object, locale);
                    } catch (InvocationTargetException invocationTargetException) {
                    } catch (IllegalArgumentException illegalArgumentException) {
                    } catch (IllegalAccessException illegalAccessException) {
                        // empty catch block
                    }
                }
            }
        }

        static {
            try {
                Class<?> clazz = null;
                Class<?>[] classArray = Locale.class.getDeclaredClasses();
                for (Class<?> clazz2 : classArray) {
                    if (!clazz2.getName().equals("java.util.Locale$Category")) continue;
                    clazz = clazz2;
                    break;
                }
                if (clazz != null) {
                    ?[] objArray;
                    mGetDefault = Locale.class.getDeclaredMethod("getDefault", clazz);
                    mSetDefault = Locale.class.getDeclaredMethod("setDefault", clazz, Locale.class);
                    Method method = clazz.getMethod("name", null);
                    for (Object obj : objArray = clazz.getEnumConstants()) {
                        String string = (String)method.invoke(obj, null);
                        if (string.equals("DISPLAY")) {
                            eDISPLAY = obj;
                            continue;
                        }
                        if (!string.equals("FORMAT")) continue;
                        eFORMAT = obj;
                    }
                    if (eDISPLAY != null && eFORMAT != null) {
                        hasLocaleCategories = true;
                    }
                }
            } catch (NoSuchMethodException noSuchMethodException) {
            } catch (IllegalArgumentException illegalArgumentException) {
            } catch (IllegalAccessException illegalAccessException) {
            } catch (InvocationTargetException invocationTargetException) {
            } catch (SecurityException securityException) {
                // empty catch block
            }
        }
    }

    public static final class Builder {
        private final InternalLocaleBuilder _locbld = new InternalLocaleBuilder();

        public Builder setLocale(ULocale uLocale) {
            try {
                this._locbld.setLocale(ULocale.access$100(uLocale), ULocale.access$200(uLocale));
            } catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
            return this;
        }

        public Builder setLanguageTag(String string) {
            ParseStatus parseStatus = new ParseStatus();
            LanguageTag languageTag = LanguageTag.parse(string, parseStatus);
            if (parseStatus.isError()) {
                throw new IllformedLocaleException(parseStatus.getErrorMessage(), parseStatus.getErrorIndex());
            }
            this._locbld.setLanguageTag(languageTag);
            return this;
        }

        public Builder setLanguage(String string) {
            try {
                this._locbld.setLanguage(string);
            } catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
            return this;
        }

        public Builder setScript(String string) {
            try {
                this._locbld.setScript(string);
            } catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
            return this;
        }

        public Builder setRegion(String string) {
            try {
                this._locbld.setRegion(string);
            } catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
            return this;
        }

        public Builder setVariant(String string) {
            try {
                this._locbld.setVariant(string);
            } catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
            return this;
        }

        public Builder setExtension(char c, String string) {
            try {
                this._locbld.setExtension(c, string);
            } catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
            return this;
        }

        public Builder setUnicodeLocaleKeyword(String string, String string2) {
            try {
                this._locbld.setUnicodeLocaleKeyword(string, string2);
            } catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
            return this;
        }

        public Builder addUnicodeLocaleAttribute(String string) {
            try {
                this._locbld.addUnicodeLocaleAttribute(string);
            } catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
            return this;
        }

        public Builder removeUnicodeLocaleAttribute(String string) {
            try {
                this._locbld.removeUnicodeLocaleAttribute(string);
            } catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
            return this;
        }

        public Builder clear() {
            this._locbld.clear();
            return this;
        }

        public Builder clearExtensions() {
            this._locbld.clearExtensions();
            return this;
        }

        public ULocale build() {
            return ULocale.access$300(this._locbld.getBaseLocale(), this._locbld.getLocaleExtensions());
        }
    }

    @Deprecated
    public static enum Minimize {
        FAVOR_SCRIPT,
        FAVOR_REGION;

    }

    public static final class Type {
        private Type() {
        }

        Type(1 var1_1) {
            this();
        }
    }

    public static enum Category {
        DISPLAY,
        FORMAT;

    }

    public static enum AvailableType {
        DEFAULT,
        ONLY_LEGACY_ALIASES,
        WITH_LEGACY_ALIASES;

    }
}

