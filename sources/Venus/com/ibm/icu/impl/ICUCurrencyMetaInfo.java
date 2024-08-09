/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.text.CurrencyMetaInfo;
import com.ibm.icu.util.Currency;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ICUCurrencyMetaInfo
extends CurrencyMetaInfo {
    private ICUResourceBundle regionInfo;
    private ICUResourceBundle digitInfo;
    private static final long MASK = 0xFFFFFFFFL;
    private static final int Region = 1;
    private static final int Currency = 2;
    private static final int Date = 4;
    private static final int Tender = 8;
    private static final int Everything = Integer.MAX_VALUE;

    public ICUCurrencyMetaInfo() {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/curr", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        this.regionInfo = iCUResourceBundle.findTopLevel("CurrencyMap");
        this.digitInfo = iCUResourceBundle.findTopLevel("CurrencyMeta");
    }

    @Override
    public List<CurrencyMetaInfo.CurrencyInfo> currencyInfo(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return this.collect(new InfoCollector(null), currencyFilter);
    }

    @Override
    public List<String> currencies(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return this.collect(new CurrencyCollector(null), currencyFilter);
    }

    @Override
    public List<String> regions(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return this.collect(new RegionCollector(null), currencyFilter);
    }

    @Override
    public CurrencyMetaInfo.CurrencyDigits currencyDigits(String string) {
        return this.currencyDigits(string, Currency.CurrencyUsage.STANDARD);
    }

    @Override
    public CurrencyMetaInfo.CurrencyDigits currencyDigits(String string, Currency.CurrencyUsage currencyUsage) {
        ICUResourceBundle iCUResourceBundle = this.digitInfo.findWithFallback(string);
        if (iCUResourceBundle == null) {
            iCUResourceBundle = this.digitInfo.findWithFallback("DEFAULT");
        }
        int[] nArray = iCUResourceBundle.getIntVector();
        if (currencyUsage == Currency.CurrencyUsage.CASH) {
            return new CurrencyMetaInfo.CurrencyDigits(nArray[2], nArray[3]);
        }
        if (currencyUsage == Currency.CurrencyUsage.STANDARD) {
            return new CurrencyMetaInfo.CurrencyDigits(nArray[0], nArray[1]);
        }
        return new CurrencyMetaInfo.CurrencyDigits(nArray[0], nArray[1]);
    }

    private <T> List<T> collect(Collector<T> collector, CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        if (currencyFilter == null) {
            currencyFilter = CurrencyMetaInfo.CurrencyFilter.all();
        }
        int n = collector.collects();
        if (currencyFilter.region != null) {
            n |= 1;
        }
        if (currencyFilter.currency != null) {
            n |= 2;
        }
        if (currencyFilter.from != Long.MIN_VALUE || currencyFilter.to != Long.MAX_VALUE) {
            n |= 4;
        }
        if (currencyFilter.tenderOnly) {
            n |= 8;
        }
        if (n != 0) {
            if (currencyFilter.region != null) {
                ICUResourceBundle iCUResourceBundle = this.regionInfo.findWithFallback(currencyFilter.region);
                if (iCUResourceBundle != null) {
                    this.collectRegion(collector, currencyFilter, n, iCUResourceBundle);
                }
            } else {
                for (int i = 0; i < this.regionInfo.getSize(); ++i) {
                    this.collectRegion(collector, currencyFilter, n, this.regionInfo.at(i));
                }
            }
        }
        return collector.getList();
    }

    private <T> void collectRegion(Collector<T> collector, CurrencyMetaInfo.CurrencyFilter currencyFilter, int n, ICUResourceBundle iCUResourceBundle) {
        String string = iCUResourceBundle.getKey();
        if (n == 1) {
            collector.collect(iCUResourceBundle.getKey(), null, 0L, 0L, -1, false);
            return;
        }
        for (int i = 0; i < iCUResourceBundle.getSize(); ++i) {
            ICUResourceBundle iCUResourceBundle2;
            ICUResourceBundle iCUResourceBundle3 = iCUResourceBundle.at(i);
            if (iCUResourceBundle3.getSize() == 0) continue;
            String string2 = null;
            long l = Long.MIN_VALUE;
            long l2 = Long.MAX_VALUE;
            boolean bl = true;
            if ((n & 2) != 0) {
                iCUResourceBundle2 = iCUResourceBundle3.at("id");
                string2 = iCUResourceBundle2.getString();
                if (currencyFilter.currency != null && !currencyFilter.currency.equals(string2)) continue;
            }
            if ((n & 4) != 0) {
                l = this.getDate(iCUResourceBundle3.at("from"), Long.MIN_VALUE, false);
                l2 = this.getDate(iCUResourceBundle3.at("to"), Long.MAX_VALUE, true);
                if (currencyFilter.from > l2 || currencyFilter.to < l) continue;
            }
            if ((n & 8) != 0) {
                iCUResourceBundle2 = iCUResourceBundle3.at("tender");
                boolean bl2 = bl = iCUResourceBundle2 == null || "true".equals(iCUResourceBundle2.getString());
                if (currencyFilter.tenderOnly && !bl) continue;
            }
            collector.collect(string, string2, l, l2, i, bl);
        }
    }

    private long getDate(ICUResourceBundle iCUResourceBundle, long l, boolean bl) {
        if (iCUResourceBundle == null) {
            return l;
        }
        int[] nArray = iCUResourceBundle.getIntVector();
        return (long)nArray[0] << 32 | (long)nArray[1] & 0xFFFFFFFFL;
    }

    private static interface Collector<T> {
        public int collects();

        public void collect(String var1, String var2, long var3, long var5, int var7, boolean var8);

        public List<T> getList();
    }

    private static class CurrencyCollector
    implements Collector<String> {
        private final UniqueList<String> result = UniqueList.access$300();

        private CurrencyCollector() {
        }

        @Override
        public void collect(String string, String string2, long l, long l2, int n, boolean bl) {
            this.result.add(string2);
        }

        @Override
        public int collects() {
            return 1;
        }

        @Override
        public List<String> getList() {
            return this.result.list();
        }

        CurrencyCollector(1 var1_1) {
            this();
        }
    }

    private static class RegionCollector
    implements Collector<String> {
        private final UniqueList<String> result = UniqueList.access$300();

        private RegionCollector() {
        }

        @Override
        public void collect(String string, String string2, long l, long l2, int n, boolean bl) {
            this.result.add(string);
        }

        @Override
        public int collects() {
            return 0;
        }

        @Override
        public List<String> getList() {
            return this.result.list();
        }

        RegionCollector(1 var1_1) {
            this();
        }
    }

    private static class InfoCollector
    implements Collector<CurrencyMetaInfo.CurrencyInfo> {
        private List<CurrencyMetaInfo.CurrencyInfo> result = new ArrayList<CurrencyMetaInfo.CurrencyInfo>();

        private InfoCollector() {
        }

        @Override
        public void collect(String string, String string2, long l, long l2, int n, boolean bl) {
            this.result.add(new CurrencyMetaInfo.CurrencyInfo(string, string2, l, l2, n, bl));
        }

        @Override
        public List<CurrencyMetaInfo.CurrencyInfo> getList() {
            return Collections.unmodifiableList(this.result);
        }

        @Override
        public int collects() {
            return 0;
        }

        InfoCollector(1 var1_1) {
            this();
        }
    }

    private static class UniqueList<T> {
        private Set<T> seen = new HashSet<T>();
        private List<T> list = new ArrayList<T>();

        private UniqueList() {
        }

        private static <T> UniqueList<T> create() {
            return new UniqueList<T>();
        }

        void add(T t) {
            if (!this.seen.contains(t)) {
                this.list.add(t);
                this.seen.add(t);
            }
        }

        List<T> list() {
            return Collections.unmodifiableList(this.list);
        }

        static UniqueList access$300() {
            return UniqueList.create();
        }
    }
}

