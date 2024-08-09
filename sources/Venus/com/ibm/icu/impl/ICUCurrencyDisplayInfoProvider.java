/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.CurrencyData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class ICUCurrencyDisplayInfoProvider
implements CurrencyData.CurrencyDisplayInfoProvider {
    private volatile ICUCurrencyDisplayInfo currencyDisplayInfoCache = null;

    @Override
    public CurrencyData.CurrencyDisplayInfo getInstance(ULocale uLocale, boolean bl) {
        ICUCurrencyDisplayInfo iCUCurrencyDisplayInfo;
        if (uLocale == null) {
            uLocale = ULocale.ROOT;
        }
        if ((iCUCurrencyDisplayInfo = this.currencyDisplayInfoCache) == null || !iCUCurrencyDisplayInfo.locale.equals(uLocale) || iCUCurrencyDisplayInfo.fallback != bl) {
            ICUResourceBundle iCUResourceBundle;
            if (bl) {
                iCUResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/curr", uLocale, ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT);
            } else {
                try {
                    iCUResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/curr", uLocale, ICUResourceBundle.OpenType.LOCALE_ONLY);
                } catch (MissingResourceException missingResourceException) {
                    return null;
                }
            }
            this.currencyDisplayInfoCache = iCUCurrencyDisplayInfo = new ICUCurrencyDisplayInfo(uLocale, iCUResourceBundle, bl);
        }
        return iCUCurrencyDisplayInfo;
    }

    @Override
    public boolean hasData() {
        return false;
    }

    static class ICUCurrencyDisplayInfo
    extends CurrencyData.CurrencyDisplayInfo {
        final ULocale locale;
        final boolean fallback;
        private final ICUResourceBundle rb;
        private volatile FormattingData formattingDataCache = null;
        private volatile NarrowSymbol narrowSymbolCache = null;
        private volatile String[] pluralsDataCache = null;
        private volatile SoftReference<ParsingData> parsingDataCache = new SoftReference<Object>(null);
        private volatile Map<String, String> unitPatternsCache = null;
        private volatile CurrencyData.CurrencySpacingInfo spacingInfoCache = null;

        public ICUCurrencyDisplayInfo(ULocale uLocale, ICUResourceBundle iCUResourceBundle, boolean bl) {
            this.locale = uLocale;
            this.fallback = bl;
            this.rb = iCUResourceBundle;
        }

        @Override
        public ULocale getULocale() {
            return this.rb.getULocale();
        }

        @Override
        public String getName(String string) {
            FormattingData formattingData = this.fetchFormattingData(string);
            if (formattingData.displayName == null && this.fallback) {
                return string;
            }
            return formattingData.displayName;
        }

        @Override
        public String getSymbol(String string) {
            FormattingData formattingData = this.fetchFormattingData(string);
            if (formattingData.symbol == null && this.fallback) {
                return string;
            }
            return formattingData.symbol;
        }

        @Override
        public String getNarrowSymbol(String string) {
            NarrowSymbol narrowSymbol = this.fetchNarrowSymbol(string);
            if (narrowSymbol.narrowSymbol == null && this.fallback) {
                return this.getSymbol(string);
            }
            return narrowSymbol.narrowSymbol;
        }

        @Override
        public String getPluralName(String string, String string2) {
            StandardPlural standardPlural = StandardPlural.orNullFromString(string2);
            String[] stringArray = this.fetchPluralsData(string);
            String string3 = null;
            if (standardPlural != null) {
                string3 = stringArray[1 + standardPlural.ordinal()];
            }
            if (string3 == null && this.fallback) {
                string3 = stringArray[1 + StandardPlural.OTHER.ordinal()];
            }
            if (string3 == null && this.fallback) {
                FormattingData formattingData = this.fetchFormattingData(string);
                string3 = formattingData.displayName;
            }
            if (string3 == null && this.fallback) {
                string3 = string;
            }
            return string3;
        }

        @Override
        public Map<String, String> symbolMap() {
            ParsingData parsingData = this.fetchParsingData();
            return parsingData.symbolToIsoCode;
        }

        @Override
        public Map<String, String> nameMap() {
            ParsingData parsingData = this.fetchParsingData();
            return parsingData.nameToIsoCode;
        }

        @Override
        public Map<String, String> getUnitPatterns() {
            Map<String, String> map = this.fetchUnitPatterns();
            return map;
        }

        @Override
        public CurrencyData.CurrencyFormatInfo getFormatInfo(String string) {
            FormattingData formattingData = this.fetchFormattingData(string);
            return formattingData.formatInfo;
        }

        @Override
        public CurrencyData.CurrencySpacingInfo getSpacingInfo() {
            CurrencyData.CurrencySpacingInfo currencySpacingInfo = this.fetchSpacingInfo();
            if (!(currencySpacingInfo.hasBeforeCurrency && currencySpacingInfo.hasAfterCurrency || !this.fallback)) {
                return CurrencyData.CurrencySpacingInfo.DEFAULT;
            }
            return currencySpacingInfo;
        }

        FormattingData fetchFormattingData(String string) {
            FormattingData formattingData = this.formattingDataCache;
            if (formattingData == null || !formattingData.isoCode.equals(string)) {
                formattingData = new FormattingData(string);
                CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCIES);
                currencySink.formattingData = formattingData;
                this.rb.getAllItemsWithFallbackNoFail("Currencies/" + string, currencySink);
                this.formattingDataCache = formattingData;
            }
            return formattingData;
        }

        NarrowSymbol fetchNarrowSymbol(String string) {
            NarrowSymbol narrowSymbol = this.narrowSymbolCache;
            if (narrowSymbol == null || !narrowSymbol.isoCode.equals(string)) {
                narrowSymbol = new NarrowSymbol(string);
                CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_NARROW);
                currencySink.narrowSymbol = narrowSymbol;
                this.rb.getAllItemsWithFallbackNoFail("Currencies%narrow/" + string, currencySink);
                this.narrowSymbolCache = narrowSymbol;
            }
            return narrowSymbol;
        }

        String[] fetchPluralsData(String string) {
            String[] stringArray = this.pluralsDataCache;
            if (stringArray == null || !stringArray[0].equals(string)) {
                stringArray = new String[1 + StandardPlural.COUNT];
                stringArray[0] = string;
                CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_PLURALS);
                currencySink.pluralsData = stringArray;
                this.rb.getAllItemsWithFallbackNoFail("CurrencyPlurals/" + string, currencySink);
                this.pluralsDataCache = stringArray;
            }
            return stringArray;
        }

        ParsingData fetchParsingData() {
            ParsingData parsingData = this.parsingDataCache.get();
            if (parsingData == null) {
                parsingData = new ParsingData();
                CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.TOP);
                currencySink.parsingData = parsingData;
                this.rb.getAllItemsWithFallback("", currencySink);
                this.parsingDataCache = new SoftReference<ParsingData>(parsingData);
            }
            return parsingData;
        }

        Map<String, String> fetchUnitPatterns() {
            Map<String, String> map = this.unitPatternsCache;
            if (map == null) {
                map = new HashMap<String, String>();
                CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_UNIT_PATTERNS);
                currencySink.unitPatterns = map;
                this.rb.getAllItemsWithFallback("CurrencyUnitPatterns", currencySink);
                this.unitPatternsCache = map;
            }
            return map;
        }

        CurrencyData.CurrencySpacingInfo fetchSpacingInfo() {
            CurrencyData.CurrencySpacingInfo currencySpacingInfo = this.spacingInfoCache;
            if (currencySpacingInfo == null) {
                currencySpacingInfo = new CurrencyData.CurrencySpacingInfo();
                CurrencySink currencySink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_SPACING);
                currencySink.spacingInfo = currencySpacingInfo;
                this.rb.getAllItemsWithFallback("currencySpacing", currencySink);
                this.spacingInfoCache = currencySpacingInfo;
            }
            return currencySpacingInfo;
        }

        private static final class CurrencySink
        extends UResource.Sink {
            final boolean noRoot;
            final EntrypointTable entrypointTable;
            FormattingData formattingData = null;
            String[] pluralsData = null;
            ParsingData parsingData = null;
            Map<String, String> unitPatterns = null;
            CurrencyData.CurrencySpacingInfo spacingInfo = null;
            NarrowSymbol narrowSymbol = null;
            static final boolean $assertionsDisabled = !ICUCurrencyDisplayInfoProvider.class.desiredAssertionStatus();

            CurrencySink(boolean bl, EntrypointTable entrypointTable) {
                this.noRoot = bl;
                this.entrypointTable = entrypointTable;
            }

            @Override
            public void put(UResource.Key key, UResource.Value value, boolean bl) {
                if (this.noRoot && bl) {
                    return;
                }
                switch (1.$SwitchMap$com$ibm$icu$impl$ICUCurrencyDisplayInfoProvider$ICUCurrencyDisplayInfo$CurrencySink$EntrypointTable[this.entrypointTable.ordinal()]) {
                    case 1: {
                        this.consumeTopTable(key, value);
                        break;
                    }
                    case 2: {
                        this.consumeCurrenciesEntry(key, value);
                        break;
                    }
                    case 3: {
                        this.consumeCurrencyPluralsEntry(key, value);
                        break;
                    }
                    case 4: {
                        this.consumeCurrenciesNarrowEntry(key, value);
                        break;
                    }
                    case 5: {
                        this.consumeCurrencySpacingTable(key, value);
                        break;
                    }
                    case 6: {
                        this.consumeCurrencyUnitPatternsTable(key, value);
                    }
                }
            }

            private void consumeTopTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    if (key.contentEquals("Currencies")) {
                        this.consumeCurrenciesTable(key, value);
                    } else if (key.contentEquals("Currencies%variant")) {
                        this.consumeCurrenciesVariantTable(key, value);
                    } else if (key.contentEquals("CurrencyPlurals")) {
                        this.consumeCurrencyPluralsTable(key, value);
                    }
                    ++n;
                }
            }

            void consumeCurrenciesTable(UResource.Key key, UResource.Value value) {
                if (!$assertionsDisabled && this.parsingData == null) {
                    throw new AssertionError();
                }
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    String string = key.toString();
                    if (value.getType() != 8) {
                        throw new ICUException("Unexpected data type in Currencies table for " + string);
                    }
                    UResource.Array array = value.getArray();
                    this.parsingData.symbolToIsoCode.put(string, string);
                    array.getValue(0, value);
                    this.parsingData.symbolToIsoCode.put(value.getString(), string);
                    array.getValue(1, value);
                    this.parsingData.nameToIsoCode.put(value.getString(), string);
                    ++n;
                }
            }

            void consumeCurrenciesEntry(UResource.Key key, UResource.Value value) {
                if (!$assertionsDisabled && this.formattingData == null) {
                    throw new AssertionError();
                }
                String string = key.toString();
                if (value.getType() != 8) {
                    throw new ICUException("Unexpected data type in Currencies table for " + string);
                }
                UResource.Array array = value.getArray();
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
                    UResource.Array array2 = value.getArray();
                    array2.getValue(0, value);
                    String string2 = value.getString();
                    array2.getValue(1, value);
                    String string3 = value.getString();
                    array2.getValue(2, value);
                    String string4 = value.getString();
                    this.formattingData.formatInfo = new CurrencyData.CurrencyFormatInfo(string, string2, string3, string4);
                }
            }

            void consumeCurrenciesNarrowEntry(UResource.Key key, UResource.Value value) {
                if (!$assertionsDisabled && this.narrowSymbol == null) {
                    throw new AssertionError();
                }
                if (this.narrowSymbol.narrowSymbol == null) {
                    this.narrowSymbol.narrowSymbol = value.getString();
                }
            }

            void consumeCurrenciesVariantTable(UResource.Key key, UResource.Value value) {
                if (!$assertionsDisabled && this.parsingData == null) {
                    throw new AssertionError();
                }
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    String string = key.toString();
                    this.parsingData.symbolToIsoCode.put(value.getString(), string);
                    ++n;
                }
            }

            void consumeCurrencyPluralsTable(UResource.Key key, UResource.Value value) {
                if (!$assertionsDisabled && this.parsingData == null) {
                    throw new AssertionError();
                }
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    String string = key.toString();
                    UResource.Table table2 = value.getTable();
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        StandardPlural standardPlural = StandardPlural.orNullFromString(key.toString());
                        if (standardPlural == null) {
                            throw new ICUException("Could not make StandardPlural from keyword " + key);
                        }
                        this.parsingData.nameToIsoCode.put(value.getString(), string);
                        ++n2;
                    }
                    ++n;
                }
            }

            void consumeCurrencyPluralsEntry(UResource.Key key, UResource.Value value) {
                if (!$assertionsDisabled && this.pluralsData == null) {
                    throw new AssertionError();
                }
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    StandardPlural standardPlural = StandardPlural.orNullFromString(key.toString());
                    if (standardPlural == null) {
                        throw new ICUException("Could not make StandardPlural from keyword " + key);
                    }
                    if (this.pluralsData[1 + standardPlural.ordinal()] == null) {
                        this.pluralsData[1 + standardPlural.ordinal()] = value.getString();
                    }
                    ++n;
                }
            }

            void consumeCurrencySpacingTable(UResource.Key key, UResource.Value value) {
                if (!$assertionsDisabled && this.spacingInfo == null) {
                    throw new AssertionError();
                }
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    block8: {
                        CurrencyData.CurrencySpacingInfo.SpacingType spacingType;
                        block7: {
                            block6: {
                                if (!key.contentEquals("beforeCurrency")) break block6;
                                spacingType = CurrencyData.CurrencySpacingInfo.SpacingType.BEFORE;
                                this.spacingInfo.hasBeforeCurrency = true;
                                break block7;
                            }
                            if (!key.contentEquals("afterCurrency")) break block8;
                            spacingType = CurrencyData.CurrencySpacingInfo.SpacingType.AFTER;
                            this.spacingInfo.hasAfterCurrency = true;
                        }
                        UResource.Table table2 = value.getTable();
                        int n2 = 0;
                        while (table2.getKeyAndValue(n2, key, value)) {
                            block12: {
                                CurrencyData.CurrencySpacingInfo.SpacingPattern spacingPattern;
                                block10: {
                                    block11: {
                                        block9: {
                                            if (!key.contentEquals("currencyMatch")) break block9;
                                            spacingPattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.CURRENCY_MATCH;
                                            break block10;
                                        }
                                        if (!key.contentEquals("surroundingMatch")) break block11;
                                        spacingPattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.SURROUNDING_MATCH;
                                        break block10;
                                    }
                                    if (!key.contentEquals("insertBetween")) break block12;
                                    spacingPattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.INSERT_BETWEEN;
                                }
                                this.spacingInfo.setSymbolIfNull(spacingType, spacingPattern, value.getString());
                            }
                            ++n2;
                        }
                    }
                    ++n;
                }
            }

            void consumeCurrencyUnitPatternsTable(UResource.Key key, UResource.Value value) {
                if (!$assertionsDisabled && this.unitPatterns == null) {
                    throw new AssertionError();
                }
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    String string = key.toString();
                    if (this.unitPatterns.get(string) == null) {
                        this.unitPatterns.put(string, value.getString());
                    }
                    ++n;
                }
            }

            static enum EntrypointTable {
                TOP,
                CURRENCIES,
                CURRENCY_PLURALS,
                CURRENCY_NARROW,
                CURRENCY_SPACING,
                CURRENCY_UNIT_PATTERNS;

            }
        }

        static class ParsingData {
            Map<String, String> symbolToIsoCode = new HashMap<String, String>();
            Map<String, String> nameToIsoCode = new HashMap<String, String>();

            ParsingData() {
            }
        }

        static class NarrowSymbol {
            final String isoCode;
            String narrowSymbol = null;

            NarrowSymbol(String string) {
                this.isoCode = string;
            }
        }

        static class FormattingData {
            final String isoCode;
            String displayName = null;
            String symbol = null;
            CurrencyData.CurrencyFormatInfo formatInfo = null;

            FormattingData(String string) {
                this.isoCode = string;
            }
        }
    }
}

