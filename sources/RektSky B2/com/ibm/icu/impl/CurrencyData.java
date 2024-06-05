package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import com.ibm.icu.text.*;
import java.util.*;

public class CurrencyData
{
    public static final CurrencyDisplayInfoProvider provider;
    
    private CurrencyData() {
    }
    
    static {
        CurrencyDisplayInfoProvider temp = null;
        try {
            final Class<?> clzz = Class.forName("com.ibm.icu.impl.ICUCurrencyDisplayInfoProvider");
            temp = (CurrencyDisplayInfoProvider)clzz.newInstance();
        }
        catch (Throwable t) {
            temp = new CurrencyDisplayInfoProvider() {
                @Override
                public CurrencyDisplayInfo getInstance(final ULocale locale, final boolean withFallback) {
                    return DefaultInfo.getWithFallback(withFallback);
                }
                
                @Override
                public boolean hasData() {
                    return false;
                }
            };
        }
        provider = temp;
    }
    
    public abstract static class CurrencyDisplayInfo extends CurrencyDisplayNames
    {
        public abstract Map<String, String> getUnitPatterns();
        
        public abstract CurrencyFormatInfo getFormatInfo(final String p0);
        
        public abstract CurrencySpacingInfo getSpacingInfo();
    }
    
    public static final class CurrencyFormatInfo
    {
        public final String isoCode;
        public final String currencyPattern;
        public final String monetaryDecimalSeparator;
        public final String monetaryGroupingSeparator;
        
        public CurrencyFormatInfo(final String isoCode, final String currencyPattern, final String monetarySeparator, final String monetaryGroupingSeparator) {
            this.isoCode = isoCode;
            this.currencyPattern = currencyPattern;
            this.monetaryDecimalSeparator = monetarySeparator;
            this.monetaryGroupingSeparator = monetaryGroupingSeparator;
        }
    }
    
    public static final class CurrencySpacingInfo
    {
        private final String[][] symbols;
        public boolean hasBeforeCurrency;
        public boolean hasAfterCurrency;
        private static final String DEFAULT_CUR_MATCH = "[:letter:]";
        private static final String DEFAULT_CTX_MATCH = "[:digit:]";
        private static final String DEFAULT_INSERT = " ";
        public static final CurrencySpacingInfo DEFAULT;
        
        public CurrencySpacingInfo() {
            this.symbols = new String[SpacingType.COUNT.ordinal()][SpacingPattern.COUNT.ordinal()];
            this.hasBeforeCurrency = false;
            this.hasAfterCurrency = false;
        }
        
        public CurrencySpacingInfo(final String... strings) {
            this.symbols = new String[SpacingType.COUNT.ordinal()][SpacingPattern.COUNT.ordinal()];
            this.hasBeforeCurrency = false;
            this.hasAfterCurrency = false;
            assert strings.length == 6;
            int k = 0;
            for (int i = 0; i < SpacingType.COUNT.ordinal(); ++i) {
                for (int j = 0; j < SpacingPattern.COUNT.ordinal(); ++j) {
                    this.symbols[i][j] = strings[k];
                    ++k;
                }
            }
        }
        
        public void setSymbolIfNull(final SpacingType type, final SpacingPattern pattern, final String value) {
            final int i = type.ordinal();
            final int j = pattern.ordinal();
            if (this.symbols[i][j] == null) {
                this.symbols[i][j] = value;
            }
        }
        
        public String[] getBeforeSymbols() {
            return this.symbols[SpacingType.BEFORE.ordinal()];
        }
        
        public String[] getAfterSymbols() {
            return this.symbols[SpacingType.AFTER.ordinal()];
        }
        
        static {
            DEFAULT = new CurrencySpacingInfo(new String[] { "[:letter:]", "[:digit:]", " ", "[:letter:]", "[:digit:]", " " });
        }
        
        public enum SpacingType
        {
            BEFORE, 
            AFTER, 
            COUNT;
        }
        
        public enum SpacingPattern
        {
            CURRENCY_MATCH(0), 
            SURROUNDING_MATCH(1), 
            INSERT_BETWEEN(2), 
            COUNT;
            
            private SpacingPattern(final int value) {
                assert value == this.ordinal();
            }
        }
    }
    
    public static class DefaultInfo extends CurrencyDisplayInfo
    {
        private final boolean fallback;
        private static final CurrencyDisplayInfo FALLBACK_INSTANCE;
        private static final CurrencyDisplayInfo NO_FALLBACK_INSTANCE;
        
        private DefaultInfo(final boolean fallback) {
            this.fallback = fallback;
        }
        
        public static final CurrencyDisplayInfo getWithFallback(final boolean fallback) {
            return fallback ? DefaultInfo.FALLBACK_INSTANCE : DefaultInfo.NO_FALLBACK_INSTANCE;
        }
        
        @Override
        public String getName(final String isoCode) {
            return this.fallback ? isoCode : null;
        }
        
        @Override
        public String getPluralName(final String isoCode, final String pluralType) {
            return this.fallback ? isoCode : null;
        }
        
        @Override
        public String getSymbol(final String isoCode) {
            return this.fallback ? isoCode : null;
        }
        
        @Override
        public String getNarrowSymbol(final String isoCode) {
            return this.fallback ? isoCode : null;
        }
        
        @Override
        public Map<String, String> symbolMap() {
            return Collections.emptyMap();
        }
        
        @Override
        public Map<String, String> nameMap() {
            return Collections.emptyMap();
        }
        
        @Override
        public ULocale getULocale() {
            return ULocale.ROOT;
        }
        
        @Override
        public Map<String, String> getUnitPatterns() {
            if (this.fallback) {
                return Collections.emptyMap();
            }
            return null;
        }
        
        @Override
        public CurrencyFormatInfo getFormatInfo(final String isoCode) {
            return null;
        }
        
        @Override
        public CurrencySpacingInfo getSpacingInfo() {
            return this.fallback ? CurrencySpacingInfo.DEFAULT : null;
        }
        
        static {
            FALLBACK_INSTANCE = new DefaultInfo(true);
            NO_FALLBACK_INSTANCE = new DefaultInfo(false);
        }
    }
    
    public interface CurrencyDisplayInfoProvider
    {
        CurrencyDisplayInfo getInstance(final ULocale p0, final boolean p1);
        
        boolean hasData();
    }
}
