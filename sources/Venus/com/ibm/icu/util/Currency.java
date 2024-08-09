/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.text.CurrencyDisplayNames;
import com.ibm.icu.text.CurrencyMetaInfo;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.ObjectStreamException;
import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

public class Currency
extends MeasureUnit {
    private static final long serialVersionUID = -5839973855554750484L;
    private static final boolean DEBUG = ICUDebug.enabled("currency");
    private static ICUCache<ULocale, List<TextTrieMap<CurrencyStringInfo>>> CURRENCY_NAME_CACHE = new SimpleCache<ULocale, List<TextTrieMap<CurrencyStringInfo>>>();
    public static final int SYMBOL_NAME = 0;
    public static final int LONG_NAME = 1;
    public static final int PLURAL_LONG_NAME = 2;
    public static final int NARROW_SYMBOL_NAME = 3;
    private static ServiceShim shim;
    private static final CacheBase<String, Currency, Void> regionCurrencyCache;
    private static final ULocale UND;
    private static final String[] EMPTY_STRING_ARRAY;
    private static final int[] POW10;
    private static SoftReference<List<String>> ALL_TENDER_CODES;
    private static SoftReference<Set<String>> ALL_CODES_AS_SET;
    private final String isoCode;

    private static ServiceShim getShim() {
        if (shim == null) {
            try {
                Class<?> clazz = Class.forName("com.ibm.icu.util.CurrencyServiceShim");
                shim = (ServiceShim)clazz.newInstance();
            } catch (Exception exception) {
                if (DEBUG) {
                    exception.printStackTrace();
                }
                throw new RuntimeException(exception.getMessage());
            }
        }
        return shim;
    }

    public static Currency getInstance(Locale locale) {
        return Currency.getInstance(ULocale.forLocale(locale));
    }

    public static Currency getInstance(ULocale uLocale) {
        String string = uLocale.getKeywordValue("currency");
        if (string != null) {
            return Currency.getInstance(string);
        }
        if (shim == null) {
            return Currency.createCurrency(uLocale);
        }
        return shim.createInstance(uLocale);
    }

    public static String[] getAvailableCurrencyCodes(ULocale uLocale, Date date) {
        String string = ULocale.getRegionForSupplementalData(uLocale, false);
        CurrencyMetaInfo.CurrencyFilter currencyFilter = CurrencyMetaInfo.CurrencyFilter.onDate(date).withRegion(string);
        List<String> list = Currency.getTenderCurrencies(currencyFilter);
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }

    public static String[] getAvailableCurrencyCodes(Locale locale, Date date) {
        return Currency.getAvailableCurrencyCodes(ULocale.forLocale(locale), date);
    }

    public static Set<Currency> getAvailableCurrencies() {
        CurrencyMetaInfo currencyMetaInfo = CurrencyMetaInfo.getInstance();
        List<String> list = currencyMetaInfo.currencies(CurrencyMetaInfo.CurrencyFilter.all());
        HashSet<Currency> hashSet = new HashSet<Currency>(list.size());
        for (String string : list) {
            hashSet.add(Currency.getInstance(string));
        }
        return hashSet;
    }

    static Currency createCurrency(ULocale uLocale) {
        String string = ULocale.getRegionForSupplementalData(uLocale, false);
        return regionCurrencyCache.getInstance(string, null);
    }

    private static Currency loadCurrency(String string) {
        String string2 = string;
        CurrencyMetaInfo currencyMetaInfo = CurrencyMetaInfo.getInstance();
        List<String> list = currencyMetaInfo.currencies(CurrencyMetaInfo.CurrencyFilter.onRegion(string2));
        if (!list.isEmpty()) {
            String string3 = list.get(0);
            return Currency.getInstance(string3);
        }
        return null;
    }

    public static Currency getInstance(String string) {
        if (string == null) {
            throw new NullPointerException("The input currency code is null.");
        }
        if (!Currency.isAlpha3Code(string)) {
            throw new IllegalArgumentException("The input currency code is not 3-letter alphabetic code.");
        }
        return (Currency)MeasureUnit.internalGetInstance("currency", string.toUpperCase(Locale.ENGLISH));
    }

    private static boolean isAlpha3Code(String string) {
        if (string.length() != 3) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            char c = string.charAt(i);
            if (c >= 'A' && (c <= 'Z' || c >= 'a') && c <= 'z') continue;
            return true;
        }
        return false;
    }

    public static Currency fromJavaCurrency(java.util.Currency currency) {
        return Currency.getInstance(currency.getCurrencyCode());
    }

    public java.util.Currency toJavaCurrency() {
        return java.util.Currency.getInstance(this.getCurrencyCode());
    }

    public static Object registerInstance(Currency currency, ULocale uLocale) {
        return Currency.getShim().registerInstance(currency, uLocale);
    }

    public static boolean unregister(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("registryKey must not be null");
        }
        if (shim == null) {
            return true;
        }
        return shim.unregister(object);
    }

    public static Locale[] getAvailableLocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return shim.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return shim.getAvailableULocales();
    }

    public static final String[] getKeywordValuesForLocale(String string, ULocale uLocale, boolean bl) {
        if (!"currency".equals(string)) {
            return EMPTY_STRING_ARRAY;
        }
        if (!bl) {
            return Currency.getAllTenderCurrencies().toArray(new String[0]);
        }
        if (UND.equals(uLocale)) {
            return EMPTY_STRING_ARRAY;
        }
        String string2 = ULocale.getRegionForSupplementalData(uLocale, true);
        CurrencyMetaInfo.CurrencyFilter currencyFilter = CurrencyMetaInfo.CurrencyFilter.now().withRegion(string2);
        List<String> list = Currency.getTenderCurrencies(currencyFilter);
        if (list.size() == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return list.toArray(new String[list.size()]);
    }

    public String getCurrencyCode() {
        return this.subType;
    }

    public int getNumericCode() {
        int n = 0;
        try {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            UResourceBundle uResourceBundle2 = uResourceBundle.get("codeMap");
            UResourceBundle uResourceBundle3 = uResourceBundle2.get(this.subType);
            n = uResourceBundle3.getInt();
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return n;
    }

    public String getSymbol() {
        return this.getSymbol(ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getSymbol(Locale locale) {
        return this.getSymbol(ULocale.forLocale(locale));
    }

    public String getSymbol(ULocale uLocale) {
        return this.getName(uLocale, 0, null);
    }

    public String getName(Locale locale, int n, boolean[] blArray) {
        return this.getName(ULocale.forLocale(locale), n, blArray);
    }

    public String getName(ULocale uLocale, int n, boolean[] blArray) {
        if (blArray != null) {
            blArray[0] = false;
        }
        CurrencyDisplayNames currencyDisplayNames = CurrencyDisplayNames.getInstance(uLocale);
        switch (n) {
            case 0: {
                return currencyDisplayNames.getSymbol(this.subType);
            }
            case 3: {
                return currencyDisplayNames.getNarrowSymbol(this.subType);
            }
            case 1: {
                return currencyDisplayNames.getName(this.subType);
            }
        }
        throw new IllegalArgumentException("bad name style: " + n);
    }

    public String getName(Locale locale, int n, String string, boolean[] blArray) {
        return this.getName(ULocale.forLocale(locale), n, string, blArray);
    }

    public String getName(ULocale uLocale, int n, String string, boolean[] blArray) {
        if (n != 2) {
            return this.getName(uLocale, n, blArray);
        }
        if (blArray != null) {
            blArray[0] = false;
        }
        CurrencyDisplayNames currencyDisplayNames = CurrencyDisplayNames.getInstance(uLocale);
        return currencyDisplayNames.getPluralName(this.subType, string);
    }

    public String getDisplayName() {
        return this.getName(Locale.getDefault(), 1, null);
    }

    public String getDisplayName(Locale locale) {
        return this.getName(locale, 1, null);
    }

    @Deprecated
    public static String parse(ULocale uLocale, String string, int n, ParsePosition parsePosition) {
        List<TextTrieMap<CurrencyStringInfo>> list = Currency.getCurrencyTrieVec(uLocale);
        int n2 = 0;
        String string2 = null;
        TextTrieMap<CurrencyStringInfo> textTrieMap = list.get(1);
        CurrencyNameResultHandler currencyNameResultHandler = new CurrencyNameResultHandler(null);
        textTrieMap.find(string, parsePosition.getIndex(), currencyNameResultHandler);
        string2 = currencyNameResultHandler.getBestCurrencyISOCode();
        n2 = currencyNameResultHandler.getBestMatchLength();
        if (n != 1) {
            TextTrieMap<CurrencyStringInfo> textTrieMap2 = list.get(0);
            currencyNameResultHandler = new CurrencyNameResultHandler(null);
            textTrieMap2.find(string, parsePosition.getIndex(), currencyNameResultHandler);
            if (currencyNameResultHandler.getBestMatchLength() > n2) {
                string2 = currencyNameResultHandler.getBestCurrencyISOCode();
                n2 = currencyNameResultHandler.getBestMatchLength();
            }
        }
        int n3 = parsePosition.getIndex();
        parsePosition.setIndex(n3 + n2);
        return string2;
    }

    @Deprecated
    public static TextTrieMap<CurrencyStringInfo> getParsingTrie(ULocale uLocale, int n) {
        List<TextTrieMap<CurrencyStringInfo>> list = Currency.getCurrencyTrieVec(uLocale);
        if (n == 1) {
            return list.get(1);
        }
        return list.get(0);
    }

    private static List<TextTrieMap<CurrencyStringInfo>> getCurrencyTrieVec(ULocale uLocale) {
        List<TextTrieMap<CurrencyStringInfo>> list = CURRENCY_NAME_CACHE.get(uLocale);
        if (list == null) {
            TextTrieMap textTrieMap = new TextTrieMap(true);
            TextTrieMap textTrieMap2 = new TextTrieMap(false);
            list = new ArrayList<TextTrieMap<CurrencyStringInfo>>();
            list.add(textTrieMap2);
            list.add(textTrieMap);
            Currency.setupCurrencyTrieVec(uLocale, list);
            CURRENCY_NAME_CACHE.put(uLocale, list);
        }
        return list;
    }

    private static void setupCurrencyTrieVec(ULocale uLocale, List<TextTrieMap<CurrencyStringInfo>> list) {
        String string;
        String string2;
        TextTrieMap<CurrencyStringInfo> textTrieMap = list.get(0);
        TextTrieMap<CurrencyStringInfo> textTrieMap2 = list.get(1);
        CurrencyDisplayNames currencyDisplayNames = CurrencyDisplayNames.getInstance(uLocale);
        for (Map.Entry<String, String> entry : currencyDisplayNames.symbolMap().entrySet()) {
            string2 = entry.getKey();
            string = entry.getValue();
            StaticUnicodeSets.Key key = StaticUnicodeSets.chooseCurrency(string2);
            CurrencyStringInfo currencyStringInfo = new CurrencyStringInfo(string, string2);
            if (key != null) {
                UnicodeSet unicodeSet = StaticUnicodeSets.get(key);
                for (String string3 : unicodeSet) {
                    textTrieMap.put(string3, currencyStringInfo);
                }
                continue;
            }
            textTrieMap.put(string2, currencyStringInfo);
        }
        for (Map.Entry<String, String> entry : currencyDisplayNames.nameMap().entrySet()) {
            string2 = entry.getKey();
            string = entry.getValue();
            textTrieMap2.put(string2, new CurrencyStringInfo(string, string2));
        }
    }

    public int getDefaultFractionDigits() {
        return this.getDefaultFractionDigits(CurrencyUsage.STANDARD);
    }

    public int getDefaultFractionDigits(CurrencyUsage currencyUsage) {
        CurrencyMetaInfo currencyMetaInfo = CurrencyMetaInfo.getInstance();
        CurrencyMetaInfo.CurrencyDigits currencyDigits = currencyMetaInfo.currencyDigits(this.subType, currencyUsage);
        return currencyDigits.fractionDigits;
    }

    public double getRoundingIncrement() {
        return this.getRoundingIncrement(CurrencyUsage.STANDARD);
    }

    public double getRoundingIncrement(CurrencyUsage currencyUsage) {
        CurrencyMetaInfo currencyMetaInfo = CurrencyMetaInfo.getInstance();
        CurrencyMetaInfo.CurrencyDigits currencyDigits = currencyMetaInfo.currencyDigits(this.subType, currencyUsage);
        int n = currencyDigits.roundingIncrement;
        if (n == 0) {
            return 0.0;
        }
        int n2 = currencyDigits.fractionDigits;
        if (n2 < 0 || n2 >= POW10.length) {
            return 0.0;
        }
        return (double)n / (double)POW10[n2];
    }

    @Override
    public String toString() {
        return this.subType;
    }

    protected Currency(String string) {
        super("currency", string);
        this.isoCode = string;
    }

    private static synchronized List<String> getAllTenderCurrencies() {
        List<String> list;
        List<String> list2 = list = ALL_TENDER_CODES == null ? null : ALL_TENDER_CODES.get();
        if (list == null) {
            CurrencyMetaInfo.CurrencyFilter currencyFilter = CurrencyMetaInfo.CurrencyFilter.all();
            list = Collections.unmodifiableList(Currency.getTenderCurrencies(currencyFilter));
            ALL_TENDER_CODES = new SoftReference<List<String>>(list);
        }
        return list;
    }

    private static synchronized Set<String> getAllCurrenciesAsSet() {
        Set<String> set;
        Set<String> set2 = set = ALL_CODES_AS_SET == null ? null : ALL_CODES_AS_SET.get();
        if (set == null) {
            CurrencyMetaInfo currencyMetaInfo = CurrencyMetaInfo.getInstance();
            set = Collections.unmodifiableSet(new HashSet<String>(currencyMetaInfo.currencies(CurrencyMetaInfo.CurrencyFilter.all())));
            ALL_CODES_AS_SET = new SoftReference<Set<String>>(set);
        }
        return set;
    }

    public static boolean isAvailable(String string, Date date, Date date2) {
        if (!Currency.isAlpha3Code(string)) {
            return true;
        }
        if (date != null && date2 != null && date.after(date2)) {
            throw new IllegalArgumentException("To is before from");
        }
        string = string.toUpperCase(Locale.ENGLISH);
        boolean bl = Currency.getAllCurrenciesAsSet().contains(string);
        if (!bl) {
            return true;
        }
        if (date == null && date2 == null) {
            return false;
        }
        CurrencyMetaInfo currencyMetaInfo = CurrencyMetaInfo.getInstance();
        List<String> list = currencyMetaInfo.currencies(CurrencyMetaInfo.CurrencyFilter.onDateRange(date, date2).withCurrency(string));
        return list.contains(string);
    }

    private static List<String> getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        CurrencyMetaInfo currencyMetaInfo = CurrencyMetaInfo.getInstance();
        return currencyMetaInfo.currencies(currencyFilter.withTender());
    }

    private Object writeReplace() throws ObjectStreamException {
        return new MeasureUnit.MeasureUnitProxy(this.type, this.subType);
    }

    private Object readResolve() throws ObjectStreamException {
        return Currency.getInstance(this.isoCode);
    }

    static Currency access$000(String string) {
        return Currency.loadCurrency(string);
    }

    static {
        regionCurrencyCache = new SoftCache<String, Currency, Void>(){

            @Override
            protected Currency createInstance(String string, Void void_) {
                return Currency.access$000(string);
            }

            @Override
            protected Object createInstance(Object object, Object object2) {
                return this.createInstance((String)object, (Void)object2);
            }
        };
        UND = new ULocale("und");
        EMPTY_STRING_ARRAY = new String[0];
        POW10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    }

    private static class CurrencyNameResultHandler
    implements TextTrieMap.ResultHandler<CurrencyStringInfo> {
        private int bestMatchLength;
        private String bestCurrencyISOCode;

        private CurrencyNameResultHandler() {
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<CurrencyStringInfo> iterator2) {
            if (iterator2.hasNext()) {
                this.bestCurrencyISOCode = iterator2.next().getISOCode();
                this.bestMatchLength = n;
            }
            return false;
        }

        public String getBestCurrencyISOCode() {
            return this.bestCurrencyISOCode;
        }

        public int getBestMatchLength() {
            return this.bestMatchLength;
        }

        CurrencyNameResultHandler(1 var1_1) {
            this();
        }
    }

    @Deprecated
    public static final class CurrencyStringInfo {
        private String isoCode;
        private String currencyString;

        @Deprecated
        public CurrencyStringInfo(String string, String string2) {
            this.isoCode = string;
            this.currencyString = string2;
        }

        @Deprecated
        public String getISOCode() {
            return this.isoCode;
        }

        @Deprecated
        public String getCurrencyString() {
            return this.currencyString;
        }
    }

    static abstract class ServiceShim {
        ServiceShim() {
        }

        abstract ULocale[] getAvailableULocales();

        abstract Locale[] getAvailableLocales();

        abstract Currency createInstance(ULocale var1);

        abstract Object registerInstance(Currency var1, ULocale var2);

        abstract boolean unregister(Object var1);
    }

    public static enum CurrencyUsage {
        STANDARD,
        CASH;

    }
}

