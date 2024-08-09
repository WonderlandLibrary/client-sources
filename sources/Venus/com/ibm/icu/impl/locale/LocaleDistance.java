/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.locale.LSR;
import com.ibm.icu.impl.locale.XLikelySubtags;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.LocaleMatcher;
import com.ibm.icu.util.ULocale;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;

public class LocaleDistance {
    public static final int END_OF_SUBTAG = 128;
    public static final int DISTANCE_SKIP_SCRIPT = 128;
    private static final int DISTANCE_IS_FINAL = 256;
    private static final int DISTANCE_IS_FINAL_OR_SKIP_SCRIPT = 384;
    public static final int IX_DEF_LANG_DISTANCE = 0;
    public static final int IX_DEF_SCRIPT_DISTANCE = 1;
    public static final int IX_DEF_REGION_DISTANCE = 2;
    public static final int IX_MIN_REGION_DISTANCE = 3;
    public static final int IX_LIMIT = 4;
    private static final int ABOVE_THRESHOLD = 100;
    private static final boolean DEBUG_OUTPUT = false;
    private final BytesTrie trie;
    private final byte[] regionToPartitionsIndex;
    private final String[] partitionArrays;
    private final Set<LSR> paradigmLSRs;
    private final int defaultLanguageDistance;
    private final int defaultScriptDistance;
    private final int defaultRegionDistance;
    private final int minRegionDistance;
    private final int defaultDemotionPerDesiredLocale;
    public static final LocaleDistance INSTANCE;
    static final boolean $assertionsDisabled;

    private LocaleDistance(Data data) {
        this.trie = new BytesTrie(data.trie, 0);
        this.regionToPartitionsIndex = data.regionToPartitionsIndex;
        this.partitionArrays = data.partitionArrays;
        this.paradigmLSRs = data.paradigmLSRs;
        this.defaultLanguageDistance = data.distances[0];
        this.defaultScriptDistance = data.distances[1];
        this.defaultRegionDistance = data.distances[2];
        this.minRegionDistance = data.distances[3];
        LSR lSR = new LSR("en", "Latn", "US");
        LSR lSR2 = new LSR("en", "Latn", "GB");
        this.defaultDemotionPerDesiredLocale = this.getBestIndexAndDistance(lSR, new LSR[]{lSR2}, 50, LocaleMatcher.FavorSubtag.LANGUAGE) & 0xFF;
    }

    public int testOnlyDistance(ULocale uLocale, ULocale uLocale2, int n, LocaleMatcher.FavorSubtag favorSubtag) {
        LSR lSR = XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(uLocale2);
        LSR lSR2 = XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(uLocale);
        return this.getBestIndexAndDistance(lSR2, new LSR[]{lSR}, n, favorSubtag) & 0xFF;
    }

    public int getBestIndexAndDistance(LSR lSR, LSR[] lSRArray, int n, LocaleMatcher.FavorSubtag favorSubtag) {
        BytesTrie bytesTrie = new BytesTrie(this.trie);
        int n2 = LocaleDistance.trieNext(bytesTrie, lSR.language, false);
        long l = n2 >= 0 && lSRArray.length > 1 ? bytesTrie.getState64() : 0L;
        int n3 = -1;
        for (int i = 0; i < lSRArray.length; ++i) {
            int n4;
            int n5;
            LSR lSR2 = lSRArray[i];
            boolean bl = false;
            int n6 = n2;
            if (n6 >= 0) {
                if (!$assertionsDisabled && (n6 & 0x100) != 0) {
                    throw new AssertionError();
                }
                if (i != 0) {
                    bytesTrie.resetToState64(l);
                }
                n6 = LocaleDistance.trieNext(bytesTrie, lSR2.language, true);
            }
            if (n6 >= 0) {
                n5 = n6 & 0x180;
                n6 &= 0xFFFFFE7F;
            } else {
                n6 = lSR.language.equals(lSR2.language) ? 0 : this.defaultLanguageDistance;
                n5 = 0;
                bl = true;
            }
            if (!($assertionsDisabled || 0 <= n6 && n6 <= 100)) {
                throw new AssertionError();
            }
            if (favorSubtag == LocaleMatcher.FavorSubtag.SCRIPT) {
                n6 >>= 2;
            }
            if (n6 >= n) continue;
            if (bl || n5 != 0) {
                n4 = lSR.script.equals(lSR2.script) ? 0 : this.defaultScriptDistance;
            } else {
                n4 = LocaleDistance.getDesSuppScriptDistance(bytesTrie, bytesTrie.getState64(), lSR.script, lSR2.script);
                n5 = n4 & 0x100;
                n4 &= 0xFFFFFEFF;
            }
            if ((n6 += n4) >= n) continue;
            if (!lSR.region.equals(lSR2.region)) {
                if (bl || (n5 & 0x100) != 0) {
                    n6 += this.defaultRegionDistance;
                } else {
                    int n7 = n - n6;
                    if (this.minRegionDistance >= n7) continue;
                    n6 += LocaleDistance.getRegionPartitionsDistance(bytesTrie, bytesTrie.getState64(), this.partitionsForRegion(lSR), this.partitionsForRegion(lSR2), n7);
                }
            }
            if (n6 >= n) continue;
            if (n6 == 0) {
                return i << 8;
            }
            n3 = i;
            n = n6;
        }
        return n3 >= 0 ? n3 << 8 | n : -156;
    }

    private static final int getDesSuppScriptDistance(BytesTrie bytesTrie, long l, String string, String string2) {
        int n = LocaleDistance.trieNext(bytesTrie, string, false);
        if (n >= 0) {
            n = LocaleDistance.trieNext(bytesTrie, string2, true);
        }
        if (n < 0) {
            BytesTrie.Result result = bytesTrie.resetToState64(l).next(42);
            if (!$assertionsDisabled && !result.hasValue()) {
                throw new AssertionError();
            }
            if (string.equals(string2)) {
                n = 0;
            } else {
                n = bytesTrie.getValue();
                if (!$assertionsDisabled && n < 0) {
                    throw new AssertionError();
                }
            }
            if (result == BytesTrie.Result.FINAL_VALUE) {
                n |= 0x100;
            }
        }
        return n;
    }

    private static final int getRegionPartitionsDistance(BytesTrie bytesTrie, long l, String string, String string2, int n) {
        int n2 = string.length();
        int n3 = string2.length();
        if (n2 == 1 && n3 == 1) {
            BytesTrie.Result result = bytesTrie.next(string.charAt(0) | 0x80);
            if (result.hasNext() && (result = bytesTrie.next(string2.charAt(0) | 0x80)).hasValue()) {
                return bytesTrie.getValue();
            }
            return LocaleDistance.getFallbackRegionDistance(bytesTrie, l);
        }
        int n4 = 0;
        boolean bl = false;
        int n5 = 0;
        while (true) {
            BytesTrie.Result result;
            if ((result = bytesTrie.next(string.charAt(n5++) | 0x80)).hasNext()) {
                long l2 = n3 > 1 ? bytesTrie.getState64() : 0L;
                int n6 = 0;
                while (true) {
                    int n7;
                    if ((result = bytesTrie.next(string2.charAt(n6++) | 0x80)).hasValue()) {
                        n7 = bytesTrie.getValue();
                    } else if (bl) {
                        n7 = 0;
                    } else {
                        n7 = LocaleDistance.getFallbackRegionDistance(bytesTrie, l);
                        bl = true;
                    }
                    if (n7 >= n) {
                        return n7;
                    }
                    if (n4 < n7) {
                        n4 = n7;
                    }
                    if (n6 < n3) {
                        bytesTrie.resetToState64(l2);
                        continue;
                    }
                    break;
                }
            } else if (!bl) {
                int n8 = LocaleDistance.getFallbackRegionDistance(bytesTrie, l);
                if (n8 >= n) {
                    return n8;
                }
                if (n4 < n8) {
                    n4 = n8;
                }
                bl = true;
            }
            if (n5 >= n2) break;
            bytesTrie.resetToState64(l);
        }
        return n4;
    }

    private static final int getFallbackRegionDistance(BytesTrie bytesTrie, long l) {
        BytesTrie.Result result = bytesTrie.resetToState64(l).next(42);
        if (!$assertionsDisabled && !result.hasValue()) {
            throw new AssertionError();
        }
        int n = bytesTrie.getValue();
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        return n;
    }

    private static final int trieNext(BytesTrie bytesTrie, String string, boolean bl) {
        if (string.isEmpty()) {
            return 1;
        }
        int n = 0;
        int n2 = string.length() - 1;
        while (true) {
            char c = string.charAt(n);
            if (n < n2) {
                if (!bytesTrie.next(c).hasNext()) {
                    return 1;
                }
            } else {
                BytesTrie.Result result = bytesTrie.next(c | 0x80);
                if (bl) {
                    if (result.hasValue()) {
                        int n3 = bytesTrie.getValue();
                        if (result == BytesTrie.Result.FINAL_VALUE) {
                            n3 |= 0x100;
                        }
                        return n3;
                    }
                } else if (result.hasNext()) {
                    return 1;
                }
                return 1;
            }
            ++n;
        }
    }

    public String toString() {
        return this.testOnlyGetDistanceTable().toString();
    }

    private String partitionsForRegion(LSR lSR) {
        byte by = this.regionToPartitionsIndex[lSR.regionIndex];
        return this.partitionArrays[by];
    }

    public boolean isParadigmLSR(LSR lSR) {
        return this.paradigmLSRs.contains(lSR);
    }

    public int getDefaultScriptDistance() {
        return this.defaultScriptDistance;
    }

    int getDefaultRegionDistance() {
        return this.defaultRegionDistance;
    }

    public int getDefaultDemotionPerDesiredLocale() {
        return this.defaultDemotionPerDesiredLocale;
    }

    public Map<String, Integer> testOnlyGetDistanceTable() {
        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
        StringBuilder stringBuilder = new StringBuilder();
        for (BytesTrie.Entry entry : this.trie) {
            stringBuilder.setLength(0);
            int n = entry.bytesLength();
            for (int i = 0; i < n; ++i) {
                byte by = entry.byteAt(i);
                if (by == 42) {
                    stringBuilder.append("*-*-");
                    continue;
                }
                if (by >= 0) {
                    stringBuilder.append((char)by);
                    continue;
                }
                stringBuilder.append((char)(by & 0x7F)).append('-');
            }
            if (!($assertionsDisabled || stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '-')) {
                throw new AssertionError();
            }
            stringBuilder.setLength(stringBuilder.length() - 1);
            treeMap.put(stringBuilder.toString(), entry.value);
        }
        return treeMap;
    }

    public void testOnlyPrintDistanceTable() {
        for (Map.Entry<String, Integer> entry : this.testOnlyGetDistanceTable().entrySet()) {
            String string = "";
            int n = entry.getValue();
            if ((n & 0x80) != 0) {
                n &= 0xFFFFFF7F;
                string = " skip script";
            }
            System.out.println(entry.getKey() + '=' + n + string);
        }
    }

    static {
        $assertionsDisabled = !LocaleDistance.class.desiredAssertionStatus();
        INSTANCE = new LocaleDistance(Data.load());
    }

    public static final class Data {
        public byte[] trie;
        public byte[] regionToPartitionsIndex;
        public String[] partitionArrays;
        public Set<LSR> paradigmLSRs;
        public int[] distances;

        public Data(byte[] byArray, byte[] byArray2, String[] stringArray, Set<LSR> set, int[] nArray) {
            this.trie = byArray;
            this.regionToPartitionsIndex = byArray2;
            this.partitionArrays = stringArray;
            this.paradigmLSRs = set;
            this.distances = nArray;
        }

        private static UResource.Value getValue(UResource.Table table, String string, UResource.Value value) {
            if (!table.findValue(string, value)) {
                throw new MissingResourceException("langInfo.res missing data", "", "match/" + string);
            }
            return value;
        }

        public static Data load() throws MissingResourceException {
            Set<LSR> set;
            Object[] objectArray;
            ICUResourceBundle iCUResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "langInfo", ICUResourceBundle.ICU_DATA_CLASS_LOADER, ICUResourceBundle.OpenType.DIRECT);
            UResource.Value value = iCUResourceBundle.getValueWithFallback("match");
            UResource.Table table = value.getTable();
            ByteBuffer byteBuffer = Data.getValue(table, "trie", value).getBinary();
            byte[] byArray = new byte[byteBuffer.remaining()];
            byteBuffer.get(byArray);
            byteBuffer = Data.getValue(table, "regionToPartitions", value).getBinary();
            byte[] byArray2 = new byte[byteBuffer.remaining()];
            byteBuffer.get(byArray2);
            if (byArray2.length < 1677) {
                throw new MissingResourceException("langInfo.res binary data too short", "", "match/regionToPartitions");
            }
            String[] stringArray = Data.getValue(table, "partitions", value).getStringArray();
            if (table.findValue("paradigms", value)) {
                objectArray = value.getStringArray();
                set = new HashSet(objectArray.length / 3);
                for (int i = 0; i < objectArray.length; i += 3) {
                    set.add(new LSR(objectArray[i], objectArray[i + 1], objectArray[i + 2]));
                }
            } else {
                set = Collections.emptySet();
            }
            if ((objectArray = (Object[])Data.getValue(table, "distances", value).getIntVector()).length < 4) {
                throw new MissingResourceException("langInfo.res intvector too short", "", "match/distances");
            }
            return new Data(byArray, byArray2, stringArray, set, (int[])objectArray);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!this.getClass().equals(object.getClass())) {
                return true;
            }
            Data data = (Data)object;
            return Arrays.equals(this.trie, data.trie) && Arrays.equals(this.regionToPartitionsIndex, data.regionToPartitionsIndex) && Arrays.equals(this.partitionArrays, data.partitionArrays) && this.paradigmLSRs.equals(data.paradigmLSRs) && Arrays.equals(this.distances, data.distances);
        }
    }
}

