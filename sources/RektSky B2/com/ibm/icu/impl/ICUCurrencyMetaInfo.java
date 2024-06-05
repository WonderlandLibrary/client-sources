package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import java.util.*;

public class ICUCurrencyMetaInfo extends CurrencyMetaInfo
{
    private ICUResourceBundle regionInfo;
    private ICUResourceBundle digitInfo;
    private static final long MASK = 4294967295L;
    private static final int Region = 1;
    private static final int Currency = 2;
    private static final int Date = 4;
    private static final int Tender = 8;
    private static final int Everything = Integer.MAX_VALUE;
    
    public ICUCurrencyMetaInfo() {
        final ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/curr", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        this.regionInfo = bundle.findTopLevel("CurrencyMap");
        this.digitInfo = bundle.findTopLevel("CurrencyMeta");
    }
    
    @Override
    public List<CurrencyInfo> currencyInfo(final CurrencyFilter filter) {
        return this.collect((Collector<CurrencyInfo>)new InfoCollector(), filter);
    }
    
    @Override
    public List<String> currencies(final CurrencyFilter filter) {
        return this.collect((Collector<String>)new CurrencyCollector(), filter);
    }
    
    @Override
    public List<String> regions(final CurrencyFilter filter) {
        return this.collect((Collector<String>)new RegionCollector(), filter);
    }
    
    @Override
    public CurrencyDigits currencyDigits(final String isoCode) {
        return this.currencyDigits(isoCode, com.ibm.icu.util.Currency.CurrencyUsage.STANDARD);
    }
    
    @Override
    public CurrencyDigits currencyDigits(final String isoCode, final Currency.CurrencyUsage currencyPurpose) {
        ICUResourceBundle b = this.digitInfo.findWithFallback(isoCode);
        if (b == null) {
            b = this.digitInfo.findWithFallback("DEFAULT");
        }
        final int[] data = b.getIntVector();
        if (currencyPurpose == com.ibm.icu.util.Currency.CurrencyUsage.CASH) {
            return new CurrencyDigits(data[2], data[3]);
        }
        if (currencyPurpose == com.ibm.icu.util.Currency.CurrencyUsage.STANDARD) {
            return new CurrencyDigits(data[0], data[1]);
        }
        return new CurrencyDigits(data[0], data[1]);
    }
    
    private <T> List<T> collect(final Collector<T> collector, CurrencyFilter filter) {
        if (filter == null) {
            filter = CurrencyFilter.all();
        }
        int needed = collector.collects();
        if (filter.region != null) {
            needed |= 0x1;
        }
        if (filter.currency != null) {
            needed |= 0x2;
        }
        if (filter.from != Long.MIN_VALUE || filter.to != Long.MAX_VALUE) {
            needed |= 0x4;
        }
        if (filter.tenderOnly) {
            needed |= 0x8;
        }
        if (needed != 0) {
            if (filter.region != null) {
                final ICUResourceBundle b = this.regionInfo.findWithFallback(filter.region);
                if (b != null) {
                    this.collectRegion(collector, filter, needed, b);
                }
            }
            else {
                for (int i = 0; i < this.regionInfo.getSize(); ++i) {
                    this.collectRegion(collector, filter, needed, this.regionInfo.at(i));
                }
            }
        }
        return collector.getList();
    }
    
    private <T> void collectRegion(final Collector<T> collector, final CurrencyFilter filter, final int needed, final ICUResourceBundle b) {
        final String region = b.getKey();
        if (needed == 1) {
            collector.collect(b.getKey(), null, 0L, 0L, -1, false);
            return;
        }
        for (int i = 0; i < b.getSize(); ++i) {
            final ICUResourceBundle r = b.at(i);
            if (r.getSize() != 0) {
                String currency = null;
                long from = Long.MIN_VALUE;
                long to = Long.MAX_VALUE;
                boolean tender = true;
                if ((needed & 0x2) != 0x0) {
                    final ICUResourceBundle currBundle = r.at("id");
                    currency = currBundle.getString();
                    if (filter.currency != null && !filter.currency.equals(currency)) {
                        continue;
                    }
                }
                if ((needed & 0x4) != 0x0) {
                    from = this.getDate(r.at("from"), Long.MIN_VALUE, false);
                    to = this.getDate(r.at("to"), Long.MAX_VALUE, true);
                    if (filter.from > to) {
                        continue;
                    }
                    if (filter.to < from) {
                        continue;
                    }
                }
                if ((needed & 0x8) != 0x0) {
                    final ICUResourceBundle tenderBundle = r.at("tender");
                    tender = (tenderBundle == null || "true".equals(tenderBundle.getString()));
                    if (filter.tenderOnly && !tender) {
                        continue;
                    }
                }
                collector.collect(region, currency, from, to, i, tender);
            }
        }
    }
    
    private long getDate(final ICUResourceBundle b, final long defaultValue, final boolean endOfDay) {
        if (b == null) {
            return defaultValue;
        }
        final int[] values = b.getIntVector();
        return (long)values[0] << 32 | ((long)values[1] & 0xFFFFFFFFL);
    }
    
    private static class UniqueList<T>
    {
        private Set<T> seen;
        private List<T> list;
        
        private UniqueList() {
            this.seen = new HashSet<T>();
            this.list = new ArrayList<T>();
        }
        
        private static <T> UniqueList<T> create() {
            return new UniqueList<T>();
        }
        
        void add(final T value) {
            if (!this.seen.contains(value)) {
                this.list.add(value);
                this.seen.add(value);
            }
        }
        
        List<T> list() {
            return Collections.unmodifiableList((List<? extends T>)this.list);
        }
    }
    
    private static class InfoCollector implements Collector<CurrencyInfo>
    {
        private List<CurrencyInfo> result;
        
        private InfoCollector() {
            this.result = new ArrayList<CurrencyInfo>();
        }
        
        @Override
        public void collect(final String region, final String currency, final long from, final long to, final int priority, final boolean tender) {
            this.result.add(new CurrencyInfo(region, currency, from, to, priority, tender));
        }
        
        @Override
        public List<CurrencyInfo> getList() {
            return Collections.unmodifiableList((List<? extends CurrencyInfo>)this.result);
        }
        
        @Override
        public int collects() {
            return Integer.MAX_VALUE;
        }
    }
    
    private static class RegionCollector implements Collector<String>
    {
        private final UniqueList<String> result;
        
        private RegionCollector() {
            this.result = (UniqueList<String>)create();
        }
        
        @Override
        public void collect(final String region, final String currency, final long from, final long to, final int priority, final boolean tender) {
            this.result.add(region);
        }
        
        @Override
        public int collects() {
            return 1;
        }
        
        @Override
        public List<String> getList() {
            return this.result.list();
        }
    }
    
    private static class CurrencyCollector implements Collector<String>
    {
        private final UniqueList<String> result;
        
        private CurrencyCollector() {
            this.result = (UniqueList<String>)create();
        }
        
        @Override
        public void collect(final String region, final String currency, final long from, final long to, final int priority, final boolean tender) {
            this.result.add(currency);
        }
        
        @Override
        public int collects() {
            return 2;
        }
        
        @Override
        public List<String> getList() {
            return this.result.list();
        }
    }
    
    private interface Collector<T>
    {
        int collects();
        
        void collect(final String p0, final String p1, final long p2, final long p3, final int p4, final boolean p5);
        
        List<T> getList();
    }
}
