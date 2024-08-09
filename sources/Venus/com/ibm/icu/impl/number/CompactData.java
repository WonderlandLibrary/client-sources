/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.number.MultiplierProducer;
import com.ibm.icu.text.CompactDecimalFormat;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class CompactData
implements MultiplierProducer {
    private static final String USE_FALLBACK = "<USE FALLBACK>";
    private final String[] patterns = new String[16 * StandardPlural.COUNT];
    private final byte[] multipliers = new byte[16];
    private byte largestMagnitude = 0;
    private boolean isEmpty = true;
    private static final int COMPACT_MAX_DIGITS = 15;
    static final boolean $assertionsDisabled = !CompactData.class.desiredAssertionStatus();

    public void populate(ULocale uLocale, String string, CompactDecimalFormat.CompactStyle compactStyle, CompactType compactType) {
        if (!$assertionsDisabled && !this.isEmpty) {
            throw new AssertionError();
        }
        CompactDataSink compactDataSink = new CompactDataSink(this);
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        boolean bl = string.equals("latn");
        boolean bl2 = compactStyle == CompactDecimalFormat.CompactStyle.SHORT;
        StringBuilder stringBuilder = new StringBuilder();
        CompactData.getResourceBundleKey(string, compactStyle, compactType, stringBuilder);
        iCUResourceBundle.getAllItemsWithFallbackNoFail(stringBuilder.toString(), compactDataSink);
        if (this.isEmpty && !bl) {
            CompactData.getResourceBundleKey("latn", compactStyle, compactType, stringBuilder);
            iCUResourceBundle.getAllItemsWithFallbackNoFail(stringBuilder.toString(), compactDataSink);
        }
        if (this.isEmpty && !bl2) {
            CompactData.getResourceBundleKey(string, CompactDecimalFormat.CompactStyle.SHORT, compactType, stringBuilder);
            iCUResourceBundle.getAllItemsWithFallbackNoFail(stringBuilder.toString(), compactDataSink);
        }
        if (this.isEmpty && !bl && !bl2) {
            CompactData.getResourceBundleKey("latn", CompactDecimalFormat.CompactStyle.SHORT, compactType, stringBuilder);
            iCUResourceBundle.getAllItemsWithFallbackNoFail(stringBuilder.toString(), compactDataSink);
        }
        if (this.isEmpty) {
            throw new ICUException("Could not load compact decimal data for locale " + uLocale);
        }
    }

    private static void getResourceBundleKey(String string, CompactDecimalFormat.CompactStyle compactStyle, CompactType compactType, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        stringBuilder.append("NumberElements/");
        stringBuilder.append(string);
        stringBuilder.append(compactStyle == CompactDecimalFormat.CompactStyle.SHORT ? "/patternsShort" : "/patternsLong");
        stringBuilder.append(compactType == CompactType.DECIMAL ? "/decimalFormat" : "/currencyFormat");
    }

    public void populate(Map<String, Map<String, String>> map) {
        if (!$assertionsDisabled && !this.isEmpty) {
            throw new AssertionError();
        }
        for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
            byte by = (byte)(entry.getKey().length() - 1);
            for (Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {
                String string;
                StandardPlural standardPlural = StandardPlural.fromString(entry2.getKey().toString());
                this.patterns[CompactData.getIndex((int)by, (StandardPlural)standardPlural)] = string = entry2.getValue().toString();
                int n = CompactData.countZeros(string);
                if (n <= 0) continue;
                this.multipliers[by] = (byte)(n - by - 1);
                if (by > this.largestMagnitude) {
                    this.largestMagnitude = by;
                }
                this.isEmpty = false;
            }
        }
    }

    @Override
    public int getMultiplier(int n) {
        if (n < 0) {
            return 1;
        }
        if (n > this.largestMagnitude) {
            n = this.largestMagnitude;
        }
        return this.multipliers[n];
    }

    public String getPattern(int n, StandardPlural standardPlural) {
        String string;
        if (n < 0) {
            return null;
        }
        if (n > this.largestMagnitude) {
            n = this.largestMagnitude;
        }
        if ((string = this.patterns[CompactData.getIndex(n, standardPlural)]) == null && standardPlural != StandardPlural.OTHER) {
            string = this.patterns[CompactData.getIndex(n, StandardPlural.OTHER)];
        }
        if (string == USE_FALLBACK) {
            string = null;
        }
        return string;
    }

    public void getUniquePatterns(Set<String> set) {
        if (!$assertionsDisabled && !set.isEmpty()) {
            throw new AssertionError();
        }
        set.addAll(Arrays.asList(this.patterns));
        set.remove(USE_FALLBACK);
        set.remove(null);
    }

    private static final int getIndex(int n, StandardPlural standardPlural) {
        return n * StandardPlural.COUNT + standardPlural.ordinal();
    }

    private static final int countZeros(String string) {
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == '0') {
                ++n;
                continue;
            }
            if (n > 0) break;
        }
        return n;
    }

    static byte[] access$000(CompactData compactData) {
        return compactData.multipliers;
    }

    static String[] access$100(CompactData compactData) {
        return compactData.patterns;
    }

    static int access$200(int n, StandardPlural standardPlural) {
        return CompactData.getIndex(n, standardPlural);
    }

    static int access$300(String string) {
        return CompactData.countZeros(string);
    }

    static byte access$400(CompactData compactData) {
        return compactData.largestMagnitude;
    }

    static byte access$402(CompactData compactData, byte by) {
        compactData.largestMagnitude = by;
        return compactData.largestMagnitude;
    }

    static boolean access$502(CompactData compactData, boolean bl) {
        compactData.isEmpty = bl;
        return compactData.isEmpty;
    }

    private static final class CompactDataSink
    extends UResource.Sink {
        CompactData data;
        static final boolean $assertionsDisabled = !CompactData.class.desiredAssertionStatus();

        public CompactDataSink(CompactData compactData) {
            this.data = compactData;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                byte by = (byte)(key.length() - 1);
                byte by2 = CompactData.access$000(this.data)[by];
                if (!$assertionsDisabled && by >= 15) {
                    throw new AssertionError();
                }
                UResource.Table table2 = value.getTable();
                int n2 = 0;
                while (table2.getKeyAndValue(n2, key, value)) {
                    StandardPlural standardPlural = StandardPlural.fromString(key.toString());
                    if (CompactData.access$100(this.data)[CompactData.access$200(by, standardPlural)] == null) {
                        int n3;
                        String string = value.toString();
                        if (string.equals("0")) {
                            string = CompactData.USE_FALLBACK;
                        }
                        CompactData.access$100((CompactData)this.data)[CompactData.access$200((int)by, (StandardPlural)standardPlural)] = string;
                        if (by2 == 0 && (n3 = CompactData.access$300(string)) > 0) {
                            by2 = (byte)(n3 - by - 1);
                        }
                    }
                    ++n2;
                }
                if (CompactData.access$000(this.data)[by] == 0) {
                    CompactData.access$000((CompactData)this.data)[by] = by2;
                    if (by > CompactData.access$400(this.data)) {
                        CompactData.access$402(this.data, by);
                    }
                    CompactData.access$502(this.data, false);
                } else if (!$assertionsDisabled && CompactData.access$000(this.data)[by] != by2) {
                    throw new AssertionError();
                }
                ++n;
            }
        }
    }

    public static enum CompactType {
        DECIMAL,
        CURRENCY;

    }
}

