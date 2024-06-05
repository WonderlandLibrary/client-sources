package com.ibm.icu.util;

import java.lang.ref.*;
import java.text.*;
import com.ibm.icu.text.*;
import java.util.*;
import java.io.*;
import com.ibm.icu.impl.*;

public class Currency extends MeasureUnit
{
    private static final long serialVersionUID = -5839973855554750484L;
    private static final boolean DEBUG;
    private static ICUCache<ULocale, List<TextTrieMap<CurrencyStringInfo>>> CURRENCY_NAME_CACHE;
    public static final int SYMBOL_NAME = 0;
    public static final int LONG_NAME = 1;
    public static final int PLURAL_LONG_NAME = 2;
    public static final int NARROW_SYMBOL_NAME = 3;
    private static ServiceShim shim;
    private static final String EUR_STR = "EUR";
    private static final CacheBase<String, Currency, Void> regionCurrencyCache;
    private static final ULocale UND;
    private static final String[] EMPTY_STRING_ARRAY;
    private static final int[] POW10;
    private static SoftReference<List<String>> ALL_TENDER_CODES;
    private static SoftReference<Set<String>> ALL_CODES_AS_SET;
    private final String isoCode;
    
    private static ServiceShim getShim() {
        if (Currency.shim == null) {
            try {
                final Class<?> cls = Class.forName("com.ibm.icu.util.CurrencyServiceShim");
                Currency.shim = (ServiceShim)cls.newInstance();
            }
            catch (Exception e) {
                if (Currency.DEBUG) {
                    e.printStackTrace();
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Currency.shim;
    }
    
    public static Currency getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }
    
    public static Currency getInstance(final ULocale locale) {
        final String currency = locale.getKeywordValue("currency");
        if (currency != null) {
            return getInstance(currency);
        }
        if (Currency.shim == null) {
            return createCurrency(locale);
        }
        return Currency.shim.createInstance(locale);
    }
    
    public static String[] getAvailableCurrencyCodes(final ULocale loc, final Date d) {
        final String region = ULocale.getRegionForSupplementalData(loc, false);
        final CurrencyMetaInfo.CurrencyFilter filter = CurrencyMetaInfo.CurrencyFilter.onDate(d).withRegion(region);
        final List<String> list = getTenderCurrencies(filter);
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String[] getAvailableCurrencyCodes(final Locale loc, final Date d) {
        return getAvailableCurrencyCodes(ULocale.forLocale(loc), d);
    }
    
    public static Set<Currency> getAvailableCurrencies() {
        final CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
        final List<String> list = info.currencies(CurrencyMetaInfo.CurrencyFilter.all());
        final HashSet<Currency> resultSet = new HashSet<Currency>(list.size());
        for (final String code : list) {
            resultSet.add(getInstance(code));
        }
        return resultSet;
    }
    
    static Currency createCurrency(final ULocale loc) {
        final String variant = loc.getVariant();
        if ("EURO".equals(variant)) {
            return getInstance("EUR");
        }
        String key = ULocale.getRegionForSupplementalData(loc, false);
        if ("PREEURO".equals(variant)) {
            key += '-';
        }
        return Currency.regionCurrencyCache.getInstance(key, null);
    }
    
    private static Currency loadCurrency(final String key) {
        String region;
        boolean isPreEuro;
        if (key.endsWith("-")) {
            region = key.substring(0, key.length() - 1);
            isPreEuro = true;
        }
        else {
            region = key;
            isPreEuro = false;
        }
        final CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
        final List<String> list = info.currencies(CurrencyMetaInfo.CurrencyFilter.onRegion(region));
        if (!list.isEmpty()) {
            String code = list.get(0);
            if (isPreEuro && "EUR".equals(code)) {
                if (list.size() < 2) {
                    return null;
                }
                code = list.get(1);
            }
            return getInstance(code);
        }
        return null;
    }
    
    public static Currency getInstance(final String theISOCode) {
        if (theISOCode == null) {
            throw new NullPointerException("The input currency code is null.");
        }
        if (!isAlpha3Code(theISOCode)) {
            throw new IllegalArgumentException("The input currency code is not 3-letter alphabetic code.");
        }
        return (Currency)MeasureUnit.internalGetInstance("currency", theISOCode.toUpperCase(Locale.ENGLISH));
    }
    
    private static boolean isAlpha3Code(final String code) {
        if (code.length() != 3) {
            return false;
        }
        for (int i = 0; i < 3; ++i) {
            final char ch = code.charAt(i);
            if (ch < 'A' || (ch > 'Z' && ch < 'a') || ch > 'z') {
                return false;
            }
        }
        return true;
    }
    
    public static Currency fromJavaCurrency(final java.util.Currency currency) {
        return getInstance(currency.getCurrencyCode());
    }
    
    public java.util.Currency toJavaCurrency() {
        return java.util.Currency.getInstance(this.getCurrencyCode());
    }
    
    public static Object registerInstance(final Currency currency, final ULocale locale) {
        return getShim().registerInstance(currency, locale);
    }
    
    public static boolean unregister(final Object registryKey) {
        if (registryKey == null) {
            throw new IllegalArgumentException("registryKey must not be null");
        }
        return Currency.shim != null && Currency.shim.unregister(registryKey);
    }
    
    public static Locale[] getAvailableLocales() {
        if (Currency.shim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return Currency.shim.getAvailableLocales();
    }
    
    public static ULocale[] getAvailableULocales() {
        if (Currency.shim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return Currency.shim.getAvailableULocales();
    }
    
    public static final String[] getKeywordValuesForLocale(final String key, final ULocale locale, final boolean commonlyUsed) {
        if (!"currency".equals(key)) {
            return Currency.EMPTY_STRING_ARRAY;
        }
        if (!commonlyUsed) {
            return getAllTenderCurrencies().toArray(new String[0]);
        }
        if (Currency.UND.equals(locale)) {
            return Currency.EMPTY_STRING_ARRAY;
        }
        final String prefRegion = ULocale.getRegionForSupplementalData(locale, true);
        final CurrencyMetaInfo.CurrencyFilter filter = CurrencyMetaInfo.CurrencyFilter.now().withRegion(prefRegion);
        final List<String> result = getTenderCurrencies(filter);
        if (result.size() == 0) {
            return Currency.EMPTY_STRING_ARRAY;
        }
        return result.toArray(new String[result.size()]);
    }
    
    public String getCurrencyCode() {
        return this.subType;
    }
    
    public int getNumericCode() {
        int result = 0;
        try {
            final UResourceBundle bundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            final UResourceBundle codeMap = bundle.get("codeMap");
            final UResourceBundle numCode = codeMap.get(this.subType);
            result = numCode.getInt();
        }
        catch (MissingResourceException ex) {}
        return result;
    }
    
    public String getSymbol() {
        return this.getSymbol(ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public String getSymbol(final Locale loc) {
        return this.getSymbol(ULocale.forLocale(loc));
    }
    
    public String getSymbol(final ULocale uloc) {
        return this.getName(uloc, 0, null);
    }
    
    public String getName(final Locale locale, final int nameStyle, final boolean[] isChoiceFormat) {
        return this.getName(ULocale.forLocale(locale), nameStyle, isChoiceFormat);
    }
    
    public String getName(final ULocale locale, final int nameStyle, final boolean[] isChoiceFormat) {
        if (isChoiceFormat != null) {
            isChoiceFormat[0] = false;
        }
        final CurrencyDisplayNames names = CurrencyDisplayNames.getInstance(locale);
        switch (nameStyle) {
            case 0: {
                return names.getSymbol(this.subType);
            }
            case 3: {
                return names.getNarrowSymbol(this.subType);
            }
            case 1: {
                return names.getName(this.subType);
            }
            default: {
                throw new IllegalArgumentException("bad name style: " + nameStyle);
            }
        }
    }
    
    public String getName(final Locale locale, final int nameStyle, final String pluralCount, final boolean[] isChoiceFormat) {
        return this.getName(ULocale.forLocale(locale), nameStyle, pluralCount, isChoiceFormat);
    }
    
    public String getName(final ULocale locale, final int nameStyle, final String pluralCount, final boolean[] isChoiceFormat) {
        if (nameStyle != 2) {
            return this.getName(locale, nameStyle, isChoiceFormat);
        }
        if (isChoiceFormat != null) {
            isChoiceFormat[0] = false;
        }
        final CurrencyDisplayNames names = CurrencyDisplayNames.getInstance(locale);
        return names.getPluralName(this.subType, pluralCount);
    }
    
    public String getDisplayName() {
        return this.getName(Locale.getDefault(), 1, null);
    }
    
    public String getDisplayName(final Locale locale) {
        return this.getName(locale, 1, null);
    }
    
    @Deprecated
    public static String parse(final ULocale locale, final String text, final int type, final ParsePosition pos) {
        final List<TextTrieMap<CurrencyStringInfo>> currencyTrieVec = getCurrencyTrieVec(locale);
        int maxLength = 0;
        String isoResult = null;
        final TextTrieMap<CurrencyStringInfo> currencyNameTrie = currencyTrieVec.get(1);
        CurrencyNameResultHandler handler = new CurrencyNameResultHandler();
        currencyNameTrie.find(text, pos.getIndex(), handler);
        isoResult = handler.getBestCurrencyISOCode();
        maxLength = handler.getBestMatchLength();
        if (type != 1) {
            final TextTrieMap<CurrencyStringInfo> currencySymbolTrie = currencyTrieVec.get(0);
            handler = new CurrencyNameResultHandler();
            currencySymbolTrie.find(text, pos.getIndex(), handler);
            if (handler.getBestMatchLength() > maxLength) {
                isoResult = handler.getBestCurrencyISOCode();
                maxLength = handler.getBestMatchLength();
            }
        }
        final int start = pos.getIndex();
        pos.setIndex(start + maxLength);
        return isoResult;
    }
    
    @Deprecated
    public static TextTrieMap<CurrencyStringInfo> getParsingTrie(final ULocale locale, final int type) {
        final List<TextTrieMap<CurrencyStringInfo>> currencyTrieVec = getCurrencyTrieVec(locale);
        if (type == 1) {
            return currencyTrieVec.get(1);
        }
        return currencyTrieVec.get(0);
    }
    
    private static List<TextTrieMap<CurrencyStringInfo>> getCurrencyTrieVec(final ULocale locale) {
        List<TextTrieMap<CurrencyStringInfo>> currencyTrieVec = Currency.CURRENCY_NAME_CACHE.get(locale);
        if (currencyTrieVec == null) {
            final TextTrieMap<CurrencyStringInfo> currencyNameTrie = new TextTrieMap<CurrencyStringInfo>(true);
            final TextTrieMap<CurrencyStringInfo> currencySymbolTrie = new TextTrieMap<CurrencyStringInfo>(false);
            currencyTrieVec = new ArrayList<TextTrieMap<CurrencyStringInfo>>();
            currencyTrieVec.add(currencySymbolTrie);
            currencyTrieVec.add(currencyNameTrie);
            setupCurrencyTrieVec(locale, currencyTrieVec);
            Currency.CURRENCY_NAME_CACHE.put(locale, currencyTrieVec);
        }
        return currencyTrieVec;
    }
    
    private static void setupCurrencyTrieVec(final ULocale locale, final List<TextTrieMap<CurrencyStringInfo>> trieVec) {
        final TextTrieMap<CurrencyStringInfo> symTrie = trieVec.get(0);
        final TextTrieMap<CurrencyStringInfo> trie = trieVec.get(1);
        final CurrencyDisplayNames names = CurrencyDisplayNames.getInstance(locale);
        for (final Map.Entry<String, String> e : names.symbolMap().entrySet()) {
            final String symbol = e.getKey();
            final String isoCode = e.getValue();
            final StaticUnicodeSets.Key key = StaticUnicodeSets.chooseCurrency(symbol);
            final CurrencyStringInfo value = new CurrencyStringInfo(isoCode, symbol);
            if (key != null) {
                final UnicodeSet equivalents = StaticUnicodeSets.get(key);
                for (final String equivalentSymbol : equivalents) {
                    symTrie.put(equivalentSymbol, value);
                }
            }
            else {
                symTrie.put(symbol, value);
            }
        }
        for (final Map.Entry<String, String> e : names.nameMap().entrySet()) {
            final String name = e.getKey();
            final String isoCode = e.getValue();
            trie.put(name, new CurrencyStringInfo(isoCode, name));
        }
    }
    
    public int getDefaultFractionDigits() {
        return this.getDefaultFractionDigits(CurrencyUsage.STANDARD);
    }
    
    public int getDefaultFractionDigits(final CurrencyUsage Usage) {
        final CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
        final CurrencyMetaInfo.CurrencyDigits digits = info.currencyDigits(this.subType, Usage);
        return digits.fractionDigits;
    }
    
    public double getRoundingIncrement() {
        return this.getRoundingIncrement(CurrencyUsage.STANDARD);
    }
    
    public double getRoundingIncrement(final CurrencyUsage Usage) {
        final CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
        final CurrencyMetaInfo.CurrencyDigits digits = info.currencyDigits(this.subType, Usage);
        final int data1 = digits.roundingIncrement;
        if (data1 == 0) {
            return 0.0;
        }
        final int data2 = digits.fractionDigits;
        if (data2 < 0 || data2 >= Currency.POW10.length) {
            return 0.0;
        }
        return data1 / (double)Currency.POW10[data2];
    }
    
    @Override
    public String toString() {
        return this.subType;
    }
    
    protected Currency(final String theISOCode) {
        super("currency", theISOCode);
        this.isoCode = theISOCode;
    }
    
    private static synchronized List<String> getAllTenderCurrencies() {
        List<String> all = (Currency.ALL_TENDER_CODES == null) ? null : Currency.ALL_TENDER_CODES.get();
        if (all == null) {
            final CurrencyMetaInfo.CurrencyFilter filter = CurrencyMetaInfo.CurrencyFilter.all();
            all = Collections.unmodifiableList((List<? extends String>)getTenderCurrencies(filter));
            Currency.ALL_TENDER_CODES = new SoftReference<List<String>>(all);
        }
        return all;
    }
    
    private static synchronized Set<String> getAllCurrenciesAsSet() {
        Set<String> all = (Currency.ALL_CODES_AS_SET == null) ? null : Currency.ALL_CODES_AS_SET.get();
        if (all == null) {
            final CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
            all = Collections.unmodifiableSet((Set<? extends String>)new HashSet<String>(info.currencies(CurrencyMetaInfo.CurrencyFilter.all())));
            Currency.ALL_CODES_AS_SET = new SoftReference<Set<String>>(all);
        }
        return all;
    }
    
    public static boolean isAvailable(String code, final Date from, final Date to) {
        if (!isAlpha3Code(code)) {
            return false;
        }
        if (from != null && to != null && from.after(to)) {
            throw new IllegalArgumentException("To is before from");
        }
        code = code.toUpperCase(Locale.ENGLISH);
        final boolean isKnown = getAllCurrenciesAsSet().contains(code);
        if (!isKnown) {
            return false;
        }
        if (from == null && to == null) {
            return true;
        }
        final CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
        final List<String> allActive = info.currencies(CurrencyMetaInfo.CurrencyFilter.onDateRange(from, to).withCurrency(code));
        return allActive.contains(code);
    }
    
    private static List<String> getTenderCurrencies(final CurrencyMetaInfo.CurrencyFilter filter) {
        final CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
        return info.currencies(filter.withTender());
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return new MeasureUnitProxy(this.type, this.subType);
    }
    
    private Object readResolve() throws ObjectStreamException {
        return getInstance(this.isoCode);
    }
    
    static {
        DEBUG = ICUDebug.enabled("currency");
        Currency.CURRENCY_NAME_CACHE = new SimpleCache<ULocale, List<TextTrieMap<CurrencyStringInfo>>>();
        regionCurrencyCache = new SoftCache<String, Currency, Void>() {
            @Override
            protected Currency createInstance(final String key, final Void unused) {
                return loadCurrency(key);
            }
        };
        UND = new ULocale("und");
        EMPTY_STRING_ARRAY = new String[0];
        POW10 = new int[] { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 };
    }
    
    public enum CurrencyUsage
    {
        STANDARD, 
        CASH;
    }
    
    abstract static class ServiceShim
    {
        abstract ULocale[] getAvailableULocales();
        
        abstract Locale[] getAvailableLocales();
        
        abstract Currency createInstance(final ULocale p0);
        
        abstract Object registerInstance(final Currency p0, final ULocale p1);
        
        abstract boolean unregister(final Object p0);
    }
    
    @Deprecated
    public static final class CurrencyStringInfo
    {
        private String isoCode;
        private String currencyString;
        
        @Deprecated
        public CurrencyStringInfo(final String isoCode, final String currencyString) {
            this.isoCode = isoCode;
            this.currencyString = currencyString;
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
    
    private static class CurrencyNameResultHandler implements TextTrieMap.ResultHandler<CurrencyStringInfo>
    {
        private int bestMatchLength;
        private String bestCurrencyISOCode;
        
        @Override
        public boolean handlePrefixMatch(final int matchLength, final Iterator<CurrencyStringInfo> values) {
            if (values.hasNext()) {
                this.bestCurrencyISOCode = values.next().getISOCode();
                this.bestMatchLength = matchLength;
            }
            return true;
        }
        
        public String getBestCurrencyISOCode() {
            return this.bestCurrencyISOCode;
        }
        
        public int getBestMatchLength() {
            return this.bestMatchLength;
        }
    }
}
