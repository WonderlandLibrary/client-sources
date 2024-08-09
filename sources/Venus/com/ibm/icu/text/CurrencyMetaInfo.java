/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.util.Currency;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CurrencyMetaInfo {
    private static final CurrencyMetaInfo impl;
    private static final boolean hasData;
    @Deprecated
    protected static final CurrencyDigits defaultDigits;

    public static CurrencyMetaInfo getInstance() {
        return impl;
    }

    public static CurrencyMetaInfo getInstance(boolean bl) {
        return hasData ? impl : null;
    }

    @Deprecated
    public static boolean hasData() {
        return hasData;
    }

    @Deprecated
    protected CurrencyMetaInfo() {
    }

    public List<CurrencyInfo> currencyInfo(CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }

    public List<String> currencies(CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }

    public List<String> regions(CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }

    public CurrencyDigits currencyDigits(String string) {
        return this.currencyDigits(string, Currency.CurrencyUsage.STANDARD);
    }

    public CurrencyDigits currencyDigits(String string, Currency.CurrencyUsage currencyUsage) {
        return defaultDigits;
    }

    private static String dateString(long l) {
        if (l == Long.MAX_VALUE || l == Long.MIN_VALUE) {
            return null;
        }
        return Grego.timeToString(l);
    }

    private static String debugString(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (Field field : object.getClass().getFields()) {
                String string;
                Object object2 = field.get(object);
                if (object2 == null || (string = object2 instanceof Date ? CurrencyMetaInfo.dateString(((Date)object2).getTime()) : (object2 instanceof Long ? CurrencyMetaInfo.dateString((Long)object2) : String.valueOf(object2))) == null) continue;
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(field.getName()).append("='").append(string).append("'");
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        stringBuilder.insert(0, object.getClass().getSimpleName() + "(");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    static String access$000(Object object) {
        return CurrencyMetaInfo.debugString(object);
    }

    static {
        defaultDigits = new CurrencyDigits(2, 0);
        CurrencyMetaInfo currencyMetaInfo = null;
        boolean bl = false;
        try {
            Class<?> clazz = Class.forName("com.ibm.icu.impl.ICUCurrencyMetaInfo");
            currencyMetaInfo = (CurrencyMetaInfo)clazz.newInstance();
            bl = true;
        } catch (Throwable throwable) {
            currencyMetaInfo = new CurrencyMetaInfo();
        }
        impl = currencyMetaInfo;
        hasData = bl;
    }

    public static final class CurrencyInfo {
        public final String region;
        public final String code;
        public final long from;
        public final long to;
        public final int priority;
        private final boolean tender;

        @Deprecated
        public CurrencyInfo(String string, String string2, long l, long l2, int n) {
            this(string, string2, l, l2, n, true);
        }

        @Deprecated
        public CurrencyInfo(String string, String string2, long l, long l2, int n, boolean bl) {
            this.region = string;
            this.code = string2;
            this.from = l;
            this.to = l2;
            this.priority = n;
            this.tender = bl;
        }

        public String toString() {
            return CurrencyMetaInfo.access$000(this);
        }

        public boolean isTender() {
            return this.tender;
        }
    }

    public static final class CurrencyDigits {
        public final int fractionDigits;
        public final int roundingIncrement;

        public CurrencyDigits(int n, int n2) {
            this.fractionDigits = n;
            this.roundingIncrement = n2;
        }

        public String toString() {
            return CurrencyMetaInfo.access$000(this);
        }
    }

    public static final class CurrencyFilter {
        public final String region;
        public final String currency;
        public final long from;
        public final long to;
        @Deprecated
        public final boolean tenderOnly;
        private static final CurrencyFilter ALL = new CurrencyFilter(null, null, Long.MIN_VALUE, Long.MAX_VALUE, false);

        private CurrencyFilter(String string, String string2, long l, long l2, boolean bl) {
            this.region = string;
            this.currency = string2;
            this.from = l;
            this.to = l2;
            this.tenderOnly = bl;
        }

        public static CurrencyFilter all() {
            return ALL;
        }

        public static CurrencyFilter now() {
            return ALL.withDate(new Date());
        }

        public static CurrencyFilter onRegion(String string) {
            return ALL.withRegion(string);
        }

        public static CurrencyFilter onCurrency(String string) {
            return ALL.withCurrency(string);
        }

        public static CurrencyFilter onDate(Date date) {
            return ALL.withDate(date);
        }

        public static CurrencyFilter onDateRange(Date date, Date date2) {
            return ALL.withDateRange(date, date2);
        }

        public static CurrencyFilter onDate(long l) {
            return ALL.withDate(l);
        }

        public static CurrencyFilter onDateRange(long l, long l2) {
            return ALL.withDateRange(l, l2);
        }

        public static CurrencyFilter onTender() {
            return ALL.withTender();
        }

        public CurrencyFilter withRegion(String string) {
            return new CurrencyFilter(string, this.currency, this.from, this.to, this.tenderOnly);
        }

        public CurrencyFilter withCurrency(String string) {
            return new CurrencyFilter(this.region, string, this.from, this.to, this.tenderOnly);
        }

        public CurrencyFilter withDate(Date date) {
            return new CurrencyFilter(this.region, this.currency, date.getTime(), date.getTime(), this.tenderOnly);
        }

        public CurrencyFilter withDateRange(Date date, Date date2) {
            long l = date == null ? Long.MIN_VALUE : date.getTime();
            long l2 = date2 == null ? Long.MAX_VALUE : date2.getTime();
            return new CurrencyFilter(this.region, this.currency, l, l2, this.tenderOnly);
        }

        public CurrencyFilter withDate(long l) {
            return new CurrencyFilter(this.region, this.currency, l, l, this.tenderOnly);
        }

        public CurrencyFilter withDateRange(long l, long l2) {
            return new CurrencyFilter(this.region, this.currency, l, l2, this.tenderOnly);
        }

        public CurrencyFilter withTender() {
            return new CurrencyFilter(this.region, this.currency, this.from, this.to, true);
        }

        public boolean equals(Object object) {
            return object instanceof CurrencyFilter && this.equals((CurrencyFilter)object);
        }

        public boolean equals(CurrencyFilter currencyFilter) {
            return Utility.sameObjects(this, currencyFilter) || currencyFilter != null && CurrencyFilter.equals(this.region, currencyFilter.region) && CurrencyFilter.equals(this.currency, currencyFilter.currency) && this.from == currencyFilter.from && this.to == currencyFilter.to && this.tenderOnly == currencyFilter.tenderOnly;
        }

        public int hashCode() {
            int n = 0;
            if (this.region != null) {
                n = this.region.hashCode();
            }
            if (this.currency != null) {
                n = n * 31 + this.currency.hashCode();
            }
            n = n * 31 + (int)this.from;
            n = n * 31 + (int)(this.from >>> 32);
            n = n * 31 + (int)this.to;
            n = n * 31 + (int)(this.to >>> 32);
            n = n * 31 + (this.tenderOnly ? 1 : 0);
            return n;
        }

        public String toString() {
            return CurrencyMetaInfo.access$000(this);
        }

        private static boolean equals(String string, String string2) {
            return Utility.sameObjects(string, string2) || string != null && string.equals(string2);
        }
    }
}

