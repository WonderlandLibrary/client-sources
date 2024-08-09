/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.text.CurrencyDisplayNames;
import com.ibm.icu.util.ULocale;
import java.util.Collections;
import java.util.Map;

public class CurrencyData {
    public static final CurrencyDisplayInfoProvider provider;

    private CurrencyData() {
    }

    static {
        CurrencyDisplayInfoProvider currencyDisplayInfoProvider = null;
        try {
            Class<?> clazz = Class.forName("com.ibm.icu.impl.ICUCurrencyDisplayInfoProvider");
            currencyDisplayInfoProvider = (CurrencyDisplayInfoProvider)clazz.newInstance();
        } catch (Throwable throwable) {
            currencyDisplayInfoProvider = new CurrencyDisplayInfoProvider(){

                @Override
                public CurrencyDisplayInfo getInstance(ULocale uLocale, boolean bl) {
                    return DefaultInfo.getWithFallback(bl);
                }

                @Override
                public boolean hasData() {
                    return true;
                }
            };
        }
        provider = currencyDisplayInfoProvider;
    }

    public static class DefaultInfo
    extends CurrencyDisplayInfo {
        private final boolean fallback;
        private static final CurrencyDisplayInfo FALLBACK_INSTANCE = new DefaultInfo(true);
        private static final CurrencyDisplayInfo NO_FALLBACK_INSTANCE = new DefaultInfo(false);

        private DefaultInfo(boolean bl) {
            this.fallback = bl;
        }

        public static final CurrencyDisplayInfo getWithFallback(boolean bl) {
            return bl ? FALLBACK_INSTANCE : NO_FALLBACK_INSTANCE;
        }

        @Override
        public String getName(String string) {
            return this.fallback ? string : null;
        }

        @Override
        public String getPluralName(String string, String string2) {
            return this.fallback ? string : null;
        }

        @Override
        public String getSymbol(String string) {
            return this.fallback ? string : null;
        }

        @Override
        public String getNarrowSymbol(String string) {
            return this.fallback ? string : null;
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
        public CurrencyFormatInfo getFormatInfo(String string) {
            return null;
        }

        @Override
        public CurrencySpacingInfo getSpacingInfo() {
            return this.fallback ? CurrencySpacingInfo.DEFAULT : null;
        }
    }

    public static final class CurrencySpacingInfo {
        private final String[][] symbols = new String[SpacingType.COUNT.ordinal()][SpacingPattern.COUNT.ordinal()];
        public boolean hasBeforeCurrency = false;
        public boolean hasAfterCurrency = false;
        private static final String DEFAULT_CUR_MATCH = "[:letter:]";
        private static final String DEFAULT_CTX_MATCH = "[:digit:]";
        private static final String DEFAULT_INSERT = " ";
        public static final CurrencySpacingInfo DEFAULT;
        static final boolean $assertionsDisabled;

        public CurrencySpacingInfo() {
        }

        public CurrencySpacingInfo(String ... stringArray) {
            if (!$assertionsDisabled && stringArray.length != 6) {
                throw new AssertionError();
            }
            int n = 0;
            for (int i = 0; i < SpacingType.COUNT.ordinal(); ++i) {
                for (int j = 0; j < SpacingPattern.COUNT.ordinal(); ++j) {
                    this.symbols[i][j] = stringArray[n];
                    ++n;
                }
            }
        }

        public void setSymbolIfNull(SpacingType spacingType, SpacingPattern spacingPattern, String string) {
            int n;
            int n2 = spacingType.ordinal();
            if (this.symbols[n2][n = spacingPattern.ordinal()] == null) {
                this.symbols[n2][n] = string;
            }
        }

        public String[] getBeforeSymbols() {
            return this.symbols[SpacingType.BEFORE.ordinal()];
        }

        public String[] getAfterSymbols() {
            return this.symbols[SpacingType.AFTER.ordinal()];
        }

        static {
            $assertionsDisabled = !CurrencyData.class.desiredAssertionStatus();
            DEFAULT = new CurrencySpacingInfo(DEFAULT_CUR_MATCH, DEFAULT_CTX_MATCH, DEFAULT_INSERT, DEFAULT_CUR_MATCH, DEFAULT_CTX_MATCH, DEFAULT_INSERT);
        }

        public static final class SpacingPattern
        extends Enum<SpacingPattern> {
            public static final /* enum */ SpacingPattern CURRENCY_MATCH;
            public static final /* enum */ SpacingPattern SURROUNDING_MATCH;
            public static final /* enum */ SpacingPattern INSERT_BETWEEN;
            public static final /* enum */ SpacingPattern COUNT;
            private static final SpacingPattern[] $VALUES;
            static final boolean $assertionsDisabled;

            public static SpacingPattern[] values() {
                return (SpacingPattern[])$VALUES.clone();
            }

            public static SpacingPattern valueOf(String string) {
                return Enum.valueOf(SpacingPattern.class, string);
            }

            private SpacingPattern() {
            }

            private SpacingPattern(int n2) {
                if (!$assertionsDisabled && n2 != this.ordinal()) {
                    throw new AssertionError();
                }
            }

            static {
                $assertionsDisabled = !CurrencyData.class.desiredAssertionStatus();
                CURRENCY_MATCH = new SpacingPattern(0);
                SURROUNDING_MATCH = new SpacingPattern(1);
                INSERT_BETWEEN = new SpacingPattern(2);
                COUNT = new SpacingPattern();
                $VALUES = new SpacingPattern[]{CURRENCY_MATCH, SURROUNDING_MATCH, INSERT_BETWEEN, COUNT};
            }
        }

        public static enum SpacingType {
            BEFORE,
            AFTER,
            COUNT;

        }
    }

    public static final class CurrencyFormatInfo {
        public final String isoCode;
        public final String currencyPattern;
        public final String monetaryDecimalSeparator;
        public final String monetaryGroupingSeparator;

        public CurrencyFormatInfo(String string, String string2, String string3, String string4) {
            this.isoCode = string;
            this.currencyPattern = string2;
            this.monetaryDecimalSeparator = string3;
            this.monetaryGroupingSeparator = string4;
        }
    }

    public static abstract class CurrencyDisplayInfo
    extends CurrencyDisplayNames {
        public abstract Map<String, String> getUnitPatterns();

        public abstract CurrencyFormatInfo getFormatInfo(String var1);

        public abstract CurrencySpacingInfo getSpacingInfo();
    }

    public static interface CurrencyDisplayInfoProvider {
        public CurrencyDisplayInfo getInstance(ULocale var1, boolean var2);

        public boolean hasData();
    }
}

