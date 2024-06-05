package com.ibm.icu.impl;

import java.lang.ref.*;
import java.util.*;
import com.ibm.icu.util.*;

public class ICUCurrencyDisplayInfoProvider implements CurrencyData.CurrencyDisplayInfoProvider
{
    private volatile ICUCurrencyDisplayInfo currencyDisplayInfoCache;
    
    public ICUCurrencyDisplayInfoProvider() {
        this.currencyDisplayInfoCache = null;
    }
    
    @Override
    public CurrencyData.CurrencyDisplayInfo getInstance(ULocale locale, final boolean withFallback) {
        if (locale == null) {
            locale = ULocale.ROOT;
        }
        ICUCurrencyDisplayInfo instance = this.currencyDisplayInfoCache;
        if (instance == null || !instance.locale.equals(locale) || instance.fallback != withFallback) {
            ICUResourceBundle rb;
            if (withFallback) {
                rb = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/curr", locale, ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT);
            }
            else {
                try {
                    rb = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/curr", locale, ICUResourceBundle.OpenType.LOCALE_ONLY);
                }
                catch (MissingResourceException e) {
                    return null;
                }
            }
            instance = new ICUCurrencyDisplayInfo(locale, rb, withFallback);
            this.currencyDisplayInfoCache = instance;
        }
        return instance;
    }
    
    @Override
    public boolean hasData() {
        return true;
    }
    
    static class ICUCurrencyDisplayInfo extends CurrencyData.CurrencyDisplayInfo
    {
        final ULocale locale;
        final boolean fallback;
        private final ICUResourceBundle rb;
        private volatile FormattingData formattingDataCache;
        private volatile NarrowSymbol narrowSymbolCache;
        private volatile String[] pluralsDataCache;
        private volatile SoftReference<ParsingData> parsingDataCache;
        private volatile Map<String, String> unitPatternsCache;
        private volatile CurrencyData.CurrencySpacingInfo spacingInfoCache;
        
        public ICUCurrencyDisplayInfo(final ULocale locale, final ICUResourceBundle rb, final boolean fallback) {
            this.formattingDataCache = null;
            this.narrowSymbolCache = null;
            this.pluralsDataCache = null;
            this.parsingDataCache = new SoftReference<ParsingData>(null);
            this.unitPatternsCache = null;
            this.spacingInfoCache = null;
            this.locale = locale;
            this.fallback = fallback;
            this.rb = rb;
        }
        
        @Override
        public ULocale getULocale() {
            return this.rb.getULocale();
        }
        
        @Override
        public String getName(final String isoCode) {
            final FormattingData formattingData = this.fetchFormattingData(isoCode);
            if (formattingData.displayName == null && this.fallback) {
                return isoCode;
            }
            return formattingData.displayName;
        }
        
        @Override
        public String getSymbol(final String isoCode) {
            final FormattingData formattingData = this.fetchFormattingData(isoCode);
            if (formattingData.symbol == null && this.fallback) {
                return isoCode;
            }
            return formattingData.symbol;
        }
        
        @Override
        public String getNarrowSymbol(final String isoCode) {
            final NarrowSymbol narrowSymbol = this.fetchNarrowSymbol(isoCode);
            if (narrowSymbol.narrowSymbol == null && this.fallback) {
                return isoCode;
            }
            return narrowSymbol.narrowSymbol;
        }
        
        @Override
        public String getPluralName(final String isoCode, final String pluralKey) {
            final StandardPlural plural = StandardPlural.orNullFromString(pluralKey);
            final String[] pluralsData = this.fetchPluralsData(isoCode);
            String result = null;
            if (plural != null) {
                result = pluralsData[1 + plural.ordinal()];
            }
            if (result == null && this.fallback) {
                result = pluralsData[1 + StandardPlural.OTHER.ordinal()];
            }
            if (result == null && this.fallback) {
                final FormattingData formattingData = this.fetchFormattingData(isoCode);
                result = formattingData.displayName;
            }
            if (result == null && this.fallback) {
                result = isoCode;
            }
            return result;
        }
        
        @Override
        public Map<String, String> symbolMap() {
            final ParsingData parsingData = this.fetchParsingData();
            return parsingData.symbolToIsoCode;
        }
        
        @Override
        public Map<String, String> nameMap() {
            final ParsingData parsingData = this.fetchParsingData();
            return parsingData.nameToIsoCode;
        }
        
        @Override
        public Map<String, String> getUnitPatterns() {
            final Map<String, String> unitPatterns = this.fetchUnitPatterns();
            return unitPatterns;
        }
        
        @Override
        public CurrencyData.CurrencyFormatInfo getFormatInfo(final String isoCode) {
            final FormattingData formattingData = this.fetchFormattingData(isoCode);
            return formattingData.formatInfo;
        }
        
        @Override
        public CurrencyData.CurrencySpacingInfo getSpacingInfo() {
            final CurrencyData.CurrencySpacingInfo spacingInfo = this.fetchSpacingInfo();
            if ((!spacingInfo.hasBeforeCurrency || !spacingInfo.hasAfterCurrency) && this.fallback) {
                return CurrencyData.CurrencySpacingInfo.DEFAULT;
            }
            return spacingInfo;
        }
        
        FormattingData fetchFormattingData(final String isoCode) {
            FormattingData result = this.formattingDataCache;
            if (result == null || !result.isoCode.equals(isoCode)) {
                result = new FormattingData(isoCode);
                final CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCIES);
                sink.formattingData = result;
                this.rb.getAllItemsWithFallbackNoFail("Currencies/" + isoCode, sink);
                this.formattingDataCache = result;
            }
            return result;
        }
        
        NarrowSymbol fetchNarrowSymbol(final String isoCode) {
            NarrowSymbol result = this.narrowSymbolCache;
            if (result == null || !result.isoCode.equals(isoCode)) {
                result = new NarrowSymbol(isoCode);
                final CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_NARROW);
                sink.narrowSymbol = result;
                this.rb.getAllItemsWithFallbackNoFail("Currencies%narrow/" + isoCode, sink);
                this.narrowSymbolCache = result;
            }
            return result;
        }
        
        String[] fetchPluralsData(final String isoCode) {
            String[] result = this.pluralsDataCache;
            if (result == null || !result[0].equals(isoCode)) {
                result = new String[1 + StandardPlural.COUNT];
                result[0] = isoCode;
                final CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_PLURALS);
                sink.pluralsData = result;
                this.rb.getAllItemsWithFallbackNoFail("CurrencyPlurals/" + isoCode, sink);
                this.pluralsDataCache = result;
            }
            return result;
        }
        
        ParsingData fetchParsingData() {
            ParsingData result = this.parsingDataCache.get();
            if (result == null) {
                result = new ParsingData();
                final CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.TOP);
                sink.parsingData = result;
                this.rb.getAllItemsWithFallback("", sink);
                this.parsingDataCache = new SoftReference<ParsingData>(result);
            }
            return result;
        }
        
        Map<String, String> fetchUnitPatterns() {
            Map<String, String> result = this.unitPatternsCache;
            if (result == null) {
                result = new HashMap<String, String>();
                final CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_UNIT_PATTERNS);
                sink.unitPatterns = result;
                this.rb.getAllItemsWithFallback("CurrencyUnitPatterns", sink);
                this.unitPatternsCache = result;
            }
            return result;
        }
        
        CurrencyData.CurrencySpacingInfo fetchSpacingInfo() {
            CurrencyData.CurrencySpacingInfo result = this.spacingInfoCache;
            if (result == null) {
                result = new CurrencyData.CurrencySpacingInfo();
                final CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_SPACING);
                sink.spacingInfo = result;
                this.rb.getAllItemsWithFallback("currencySpacing", sink);
                this.spacingInfoCache = result;
            }
            return result;
        }
        
        static class FormattingData
        {
            final String isoCode;
            String displayName;
            String symbol;
            CurrencyData.CurrencyFormatInfo formatInfo;
            
            FormattingData(final String isoCode) {
                this.displayName = null;
                this.symbol = null;
                this.formatInfo = null;
                this.isoCode = isoCode;
            }
        }
        
        static class NarrowSymbol
        {
            final String isoCode;
            String narrowSymbol;
            
            NarrowSymbol(final String isoCode) {
                this.narrowSymbol = null;
                this.isoCode = isoCode;
            }
        }
        
        static class ParsingData
        {
            Map<String, String> symbolToIsoCode;
            Map<String, String> nameToIsoCode;
            
            ParsingData() {
                this.symbolToIsoCode = new HashMap<String, String>();
                this.nameToIsoCode = new HashMap<String, String>();
            }
        }
        
        private static final class CurrencySink extends UResource.Sink
        {
            final boolean noRoot;
            final EntrypointTable entrypointTable;
            FormattingData formattingData;
            String[] pluralsData;
            ParsingData parsingData;
            Map<String, String> unitPatterns;
            CurrencyData.CurrencySpacingInfo spacingInfo;
            NarrowSymbol narrowSymbol;
            
            CurrencySink(final boolean noRoot, final EntrypointTable entrypointTable) {
                this.formattingData = null;
                this.pluralsData = null;
                this.parsingData = null;
                this.unitPatterns = null;
                this.spacingInfo = null;
                this.narrowSymbol = null;
                this.noRoot = noRoot;
                this.entrypointTable = entrypointTable;
            }
            
            @Override
            public void put(final UResource.Key key, final UResource.Value value, final boolean isRoot) {
                if (this.noRoot && isRoot) {
                    return;
                }
                switch (this.entrypointTable) {
                    case TOP: {
                        this.consumeTopTable(key, value);
                        break;
                    }
                    case CURRENCIES: {
                        this.consumeCurrenciesEntry(key, value);
                        break;
                    }
                    case CURRENCY_PLURALS: {
                        this.consumeCurrencyPluralsEntry(key, value);
                        break;
                    }
                    case CURRENCY_NARROW: {
                        this.consumeCurrenciesNarrowEntry(key, value);
                        break;
                    }
                    case CURRENCY_SPACING: {
                        this.consumeCurrencySpacingTable(key, value);
                        break;
                    }
                    case CURRENCY_UNIT_PATTERNS: {
                        this.consumeCurrencyUnitPatternsTable(key, value);
                        break;
                    }
                }
            }
            
            private void consumeTopTable(final UResource.Key key, final UResource.Value value) {
                final UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); ++i) {
                    if (key.contentEquals("Currencies")) {
                        this.consumeCurrenciesTable(key, value);
                    }
                    else if (key.contentEquals("Currencies%variant")) {
                        this.consumeCurrenciesVariantTable(key, value);
                    }
                    else if (key.contentEquals("CurrencyPlurals")) {
                        this.consumeCurrencyPluralsTable(key, value);
                    }
                }
            }
            
            void consumeCurrenciesTable(final UResource.Key key, final UResource.Value value) {
                assert this.parsingData != null;
                final UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); ++i) {
                    final String isoCode = key.toString();
                    if (value.getType() != 8) {
                        throw new ICUException("Unexpected data type in Currencies table for " + isoCode);
                    }
                    final UResource.Array array = value.getArray();
                    this.parsingData.symbolToIsoCode.put(isoCode, isoCode);
                    array.getValue(0, value);
                    this.parsingData.symbolToIsoCode.put(value.getString(), isoCode);
                    array.getValue(1, value);
                    this.parsingData.nameToIsoCode.put(value.getString(), isoCode);
                }
            }
            
            void consumeCurrenciesEntry(final UResource.Key key, final UResource.Value value) {
                assert this.formattingData != null;
                final String isoCode = key.toString();
                if (value.getType() != 8) {
                    throw new ICUException("Unexpected data type in Currencies table for " + isoCode);
                }
                final UResource.Array array = value.getArray();
                if (this.formattingData.symbol == null) {
                    array.getValue(0, value);
                    this.formattingData.symbol = value.getString();
                }
                if (this.formattingData.displayName == null) {
                    array.getValue(1, value);
                    this.formattingData.displayName = value.getString();
                }
                if (array.getSize() > 2 && this.formattingData.formatInfo == null) {
                    array.getValue(2, value);
                    final UResource.Array formatArray = value.getArray();
                    formatArray.getValue(0, value);
                    final String formatPattern = value.getString();
                    formatArray.getValue(1, value);
                    final String decimalSeparator = value.getString();
                    formatArray.getValue(2, value);
                    final String groupingSeparator = value.getString();
                    this.formattingData.formatInfo = new CurrencyData.CurrencyFormatInfo(isoCode, formatPattern, decimalSeparator, groupingSeparator);
                }
            }
            
            void consumeCurrenciesNarrowEntry(final UResource.Key key, final UResource.Value value) {
                assert this.narrowSymbol != null;
                if (this.narrowSymbol.narrowSymbol == null) {
                    this.narrowSymbol.narrowSymbol = value.getString();
                }
            }
            
            void consumeCurrenciesVariantTable(final UResource.Key key, final UResource.Value value) {
                assert this.parsingData != null;
                final UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); ++i) {
                    final String isoCode = key.toString();
                    this.parsingData.symbolToIsoCode.put(value.getString(), isoCode);
                }
            }
            
            void consumeCurrencyPluralsTable(final UResource.Key key, final UResource.Value value) {
                assert this.parsingData != null;
                final UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); ++i) {
                    final String isoCode = key.toString();
                    final UResource.Table pluralsTable = value.getTable();
                    for (int j = 0; pluralsTable.getKeyAndValue(j, key, value); ++j) {
                        final StandardPlural plural = StandardPlural.orNullFromString(key.toString());
                        if (plural == null) {
                            throw new ICUException("Could not make StandardPlural from keyword " + (Object)key);
                        }
                        this.parsingData.nameToIsoCode.put(value.getString(), isoCode);
                    }
                }
            }
            
            void consumeCurrencyPluralsEntry(final UResource.Key key, final UResource.Value value) {
                assert this.pluralsData != null;
                final UResource.Table pluralsTable = value.getTable();
                for (int j = 0; pluralsTable.getKeyAndValue(j, key, value); ++j) {
                    final StandardPlural plural = StandardPlural.orNullFromString(key.toString());
                    if (plural == null) {
                        throw new ICUException("Could not make StandardPlural from keyword " + (Object)key);
                    }
                    if (this.pluralsData[1 + plural.ordinal()] == null) {
                        this.pluralsData[1 + plural.ordinal()] = value.getString();
                    }
                }
            }
            
            void consumeCurrencySpacingTable(final UResource.Key key, final UResource.Value value) {
                assert this.spacingInfo != null;
                final UResource.Table spacingTypesTable = value.getTable();
                for (int i = 0; spacingTypesTable.getKeyAndValue(i, key, value); ++i) {
                    CurrencyData.CurrencySpacingInfo.SpacingType type;
                    if (key.contentEquals("beforeCurrency")) {
                        type = CurrencyData.CurrencySpacingInfo.SpacingType.BEFORE;
                        this.spacingInfo.hasBeforeCurrency = true;
                    }
                    else {
                        if (!key.contentEquals("afterCurrency")) {
                            continue;
                        }
                        type = CurrencyData.CurrencySpacingInfo.SpacingType.AFTER;
                        this.spacingInfo.hasAfterCurrency = true;
                    }
                    final UResource.Table patternsTable = value.getTable();
                    for (int j = 0; patternsTable.getKeyAndValue(j, key, value); ++j) {
                        CurrencyData.CurrencySpacingInfo.SpacingPattern pattern;
                        if (key.contentEquals("currencyMatch")) {
                            pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.CURRENCY_MATCH;
                        }
                        else if (key.contentEquals("surroundingMatch")) {
                            pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.SURROUNDING_MATCH;
                        }
                        else {
                            if (!key.contentEquals("insertBetween")) {
                                continue;
                            }
                            pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.INSERT_BETWEEN;
                        }
                        this.spacingInfo.setSymbolIfNull(type, pattern, value.getString());
                    }
                }
            }
            
            void consumeCurrencyUnitPatternsTable(final UResource.Key key, final UResource.Value value) {
                assert this.unitPatterns != null;
                final UResource.Table table = value.getTable();
                for (int i = 0; table.getKeyAndValue(i, key, value); ++i) {
                    final String pluralKeyword = key.toString();
                    if (this.unitPatterns.get(pluralKeyword) == null) {
                        this.unitPatterns.put(pluralKeyword, value.getString());
                    }
                }
            }
            
            enum EntrypointTable
            {
                TOP, 
                CURRENCIES, 
                CURRENCY_PLURALS, 
                CURRENCY_NARROW, 
                CURRENCY_SPACING, 
                CURRENCY_UNIT_PATTERNS;
            }
        }
    }
}
