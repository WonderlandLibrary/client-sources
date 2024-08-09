/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.locale.LSR;
import com.ibm.icu.impl.locale.LocaleDistance;
import com.ibm.icu.impl.locale.XLikelySubtags;
import com.ibm.icu.util.LocalePriorityList;
import com.ibm.icu.util.ULocale;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class LocaleMatcher {
    private static final LSR UND_LSR;
    private static final ULocale UND_ULOCALE;
    private static final Locale UND_LOCALE;
    private static final Locale EMPTY_LOCALE;
    private static final boolean TRACE_MATCHER = false;
    private final int thresholdDistance;
    private final int demotionPerDesiredLocale;
    private final FavorSubtag favorSubtag;
    private final ULocale[] supportedULocales;
    private final Locale[] supportedLocales;
    private final Map<LSR, Integer> supportedLsrToIndex;
    private final LSR[] supportedLSRs;
    private final int[] supportedIndexes;
    private final ULocale defaultULocale;
    private final Locale defaultLocale;
    private final int defaultLocaleIndex;
    static final boolean $assertionsDisabled;

    public static Builder builder() {
        return new Builder(null);
    }

    public LocaleMatcher(LocalePriorityList localePriorityList) {
        this(LocaleMatcher.builder().setSupportedULocales(localePriorityList.getULocales()));
    }

    public LocaleMatcher(String string) {
        this(LocaleMatcher.builder().setSupportedLocales(string));
    }

    private LocaleMatcher(Builder builder) {
        Object object;
        this.thresholdDistance = Builder.access$200(builder) < 0 ? LocaleDistance.INSTANCE.getDefaultScriptDistance() : Builder.access$200(builder);
        int n = Builder.access$300(builder) != null ? Builder.access$300(builder).size() : 0;
        ULocale uLocale = Builder.access$400(builder);
        Locale locale = null;
        int n2 = -1;
        this.supportedULocales = new ULocale[n];
        this.supportedLocales = new Locale[n];
        LSR[] lSRArray = new LSR[n];
        LSR lSR = null;
        if (uLocale != null) {
            locale = uLocale.toLocale();
            lSR = LocaleMatcher.getMaximalLsrOrUnd(uLocale);
        }
        int n3 = 0;
        if (n > 0) {
            object = Builder.access$300(builder).iterator();
            while (object.hasNext()) {
                ULocale[] uLocaleArray;
                this.supportedULocales[n3] = uLocaleArray = (ULocale[])object.next();
                this.supportedLocales[n3] = uLocaleArray.toLocale();
                LSR lSR2 = lSRArray[n3] = LocaleMatcher.getMaximalLsrOrUnd((ULocale)uLocaleArray);
                if (n2 < 0 && lSR != null && lSR2.equals(lSR)) {
                    n2 = n3;
                }
                ++n3;
            }
        }
        this.supportedLsrToIndex = new LinkedHashMap<LSR, Integer>(n);
        object = null;
        if (n2 >= 0) {
            this.supportedLsrToIndex.put(lSR, n2);
        }
        n3 = 0;
        for (ULocale uLocale2 : this.supportedULocales) {
            if (n3 == n2) {
                ++n3;
                continue;
            }
            LSR lSR3 = lSRArray[n3];
            if (lSR == null) {
                if (!$assertionsDisabled && n3 != 0) {
                    throw new AssertionError();
                }
                uLocale = uLocale2;
                locale = this.supportedLocales[0];
                lSR = lSR3;
                n2 = 0;
                this.supportedLsrToIndex.put(lSR3, 0);
            } else if (n2 < 0 || !lSR3.equals(lSR)) {
                if (LocaleDistance.INSTANCE.isParadigmLSR(lSR3)) {
                    LocaleMatcher.putIfAbsent(this.supportedLsrToIndex, lSR3, n3);
                } else {
                    if (object == null) {
                        object = new LinkedHashMap(n);
                    }
                    LocaleMatcher.putIfAbsent(object, lSR3, n3);
                }
            }
            ++n3;
        }
        if (object != null) {
            this.supportedLsrToIndex.putAll((Map<LSR, Integer>)object);
        }
        int n4 = this.supportedLsrToIndex.size();
        this.supportedLSRs = new LSR[n4];
        this.supportedIndexes = new int[n4];
        n3 = 0;
        for (Map.Entry<LSR, Integer> entry : this.supportedLsrToIndex.entrySet()) {
            this.supportedLSRs[n3] = entry.getKey();
            this.supportedIndexes[n3++] = entry.getValue();
        }
        this.defaultULocale = uLocale;
        this.defaultLocale = locale;
        this.defaultLocaleIndex = n2;
        this.demotionPerDesiredLocale = Builder.access$500(builder) == Demotion.NONE ? 0 : LocaleDistance.INSTANCE.getDefaultDemotionPerDesiredLocale();
        this.favorSubtag = Builder.access$600(builder);
    }

    private static final void putIfAbsent(Map<LSR, Integer> map, LSR lSR, int n) {
        Integer n2 = map.get(lSR);
        if (n2 == null) {
            map.put(lSR, n);
        }
    }

    private static final LSR getMaximalLsrOrUnd(ULocale uLocale) {
        if (uLocale.equals(UND_ULOCALE)) {
            return UND_LSR;
        }
        return XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(uLocale);
    }

    private static final LSR getMaximalLsrOrUnd(Locale locale) {
        if (locale.equals(UND_LOCALE) || locale.equals(EMPTY_LOCALE)) {
            return UND_LSR;
        }
        return XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(locale);
    }

    public ULocale getBestMatch(ULocale uLocale) {
        LSR lSR = LocaleMatcher.getMaximalLsrOrUnd(uLocale);
        int n = this.getBestSuppIndex(lSR, null);
        return n >= 0 ? this.supportedULocales[n] : this.defaultULocale;
    }

    public ULocale getBestMatch(Iterable<ULocale> iterable) {
        Iterator<ULocale> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) {
            return this.defaultULocale;
        }
        ULocaleLsrIterator uLocaleLsrIterator = new ULocaleLsrIterator(iterator2);
        LSR lSR = uLocaleLsrIterator.next();
        int n = this.getBestSuppIndex(lSR, uLocaleLsrIterator);
        return n >= 0 ? this.supportedULocales[n] : this.defaultULocale;
    }

    public ULocale getBestMatch(String string) {
        return this.getBestMatch(LocalePriorityList.add(string).build());
    }

    public Locale getBestLocale(Locale locale) {
        LSR lSR = LocaleMatcher.getMaximalLsrOrUnd(locale);
        int n = this.getBestSuppIndex(lSR, null);
        return n >= 0 ? this.supportedLocales[n] : this.defaultLocale;
    }

    public Locale getBestLocale(Iterable<Locale> iterable) {
        Iterator<Locale> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) {
            return this.defaultLocale;
        }
        LocaleLsrIterator localeLsrIterator = new LocaleLsrIterator(iterator2);
        LSR lSR = localeLsrIterator.next();
        int n = this.getBestSuppIndex(lSR, localeLsrIterator);
        return n >= 0 ? this.supportedLocales[n] : this.defaultLocale;
    }

    private Result defaultResult() {
        return new Result(null, this.defaultULocale, null, this.defaultLocale, -1, this.defaultLocaleIndex, null);
    }

    private Result makeResult(ULocale uLocale, ULocaleLsrIterator uLocaleLsrIterator, int n) {
        if (n < 0) {
            return this.defaultResult();
        }
        if (uLocale != null) {
            return new Result(uLocale, this.supportedULocales[n], null, this.supportedLocales[n], 0, n, null);
        }
        return new Result(ULocaleLsrIterator.access$1100(uLocaleLsrIterator), this.supportedULocales[n], null, this.supportedLocales[n], uLocaleLsrIterator.bestDesiredIndex, n, null);
    }

    private Result makeResult(Locale locale, LocaleLsrIterator localeLsrIterator, int n) {
        if (n < 0) {
            return this.defaultResult();
        }
        if (locale != null) {
            return new Result(null, this.supportedULocales[n], locale, this.supportedLocales[n], 0, n, null);
        }
        return new Result(null, this.supportedULocales[n], LocaleLsrIterator.access$1200(localeLsrIterator), this.supportedLocales[n], localeLsrIterator.bestDesiredIndex, n, null);
    }

    public Result getBestMatchResult(ULocale uLocale) {
        LSR lSR = LocaleMatcher.getMaximalLsrOrUnd(uLocale);
        int n = this.getBestSuppIndex(lSR, null);
        return this.makeResult(uLocale, null, n);
    }

    public Result getBestMatchResult(Iterable<ULocale> iterable) {
        Iterator<ULocale> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) {
            return this.defaultResult();
        }
        ULocaleLsrIterator uLocaleLsrIterator = new ULocaleLsrIterator(iterator2);
        LSR lSR = uLocaleLsrIterator.next();
        int n = this.getBestSuppIndex(lSR, uLocaleLsrIterator);
        return this.makeResult(null, uLocaleLsrIterator, n);
    }

    public Result getBestLocaleResult(Locale locale) {
        LSR lSR = LocaleMatcher.getMaximalLsrOrUnd(locale);
        int n = this.getBestSuppIndex(lSR, null);
        return this.makeResult(locale, null, n);
    }

    public Result getBestLocaleResult(Iterable<Locale> iterable) {
        Iterator<Locale> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) {
            return this.defaultResult();
        }
        LocaleLsrIterator localeLsrIterator = new LocaleLsrIterator(iterator2);
        LSR lSR = localeLsrIterator.next();
        int n = this.getBestSuppIndex(lSR, localeLsrIterator);
        return this.makeResult(null, localeLsrIterator, n);
    }

    private int getBestSuppIndex(LSR lSR, LsrIterator lsrIterator) {
        int n = 0;
        int n2 = -1;
        int n3 = this.thresholdDistance;
        while (true) {
            int n4;
            Integer n5;
            if ((n5 = this.supportedLsrToIndex.get(lSR)) != null) {
                n4 = n5;
                if (lsrIterator != null) {
                    lsrIterator.rememberCurrent(n);
                }
                return n4;
            }
            n4 = LocaleDistance.INSTANCE.getBestIndexAndDistance(lSR, this.supportedLSRs, n3, this.favorSubtag);
            if (n4 >= 0) {
                n3 = n4 & 0xFF;
                if (lsrIterator != null) {
                    lsrIterator.rememberCurrent(n);
                }
                n2 = n4 >> 8;
            }
            if ((n3 -= this.demotionPerDesiredLocale) <= 0 || lsrIterator == null || !lsrIterator.hasNext()) break;
            lSR = (LSR)lsrIterator.next();
            ++n;
        }
        if (n2 < 0) {
            return 1;
        }
        n3 = this.supportedIndexes[n2];
        return n3;
    }

    @Deprecated
    public double match(ULocale uLocale, ULocale uLocale2, ULocale uLocale3, ULocale uLocale4) {
        int n = LocaleDistance.INSTANCE.getBestIndexAndDistance(LocaleMatcher.getMaximalLsrOrUnd(uLocale), new LSR[]{LocaleMatcher.getMaximalLsrOrUnd(uLocale3)}, this.thresholdDistance, this.favorSubtag) & 0xFF;
        return (double)(100 - n) / 100.0;
    }

    public ULocale canonicalize(ULocale uLocale) {
        return XLikelySubtags.INSTANCE.canonicalize(uLocale);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append("{LocaleMatcher");
        if (this.supportedULocales.length > 0) {
            stringBuilder.append(" supported={").append(this.supportedULocales[0].toString());
            for (int i = 1; i < this.supportedULocales.length; ++i) {
                stringBuilder.append(", ").append(this.supportedULocales[i].toString());
            }
            stringBuilder.append('}');
        }
        stringBuilder.append(" default=").append(Objects.toString(this.defaultULocale));
        if (this.favorSubtag != null) {
            stringBuilder.append(" distance=").append(this.favorSubtag.toString());
        }
        if (this.thresholdDistance >= 0) {
            stringBuilder.append(String.format(" threshold=%d", this.thresholdDistance));
        }
        stringBuilder.append(String.format(" demotion=%d", this.demotionPerDesiredLocale));
        return stringBuilder.append('}').toString();
    }

    LocaleMatcher(Builder builder, 1 var2_2) {
        this(builder);
    }

    static LSR access$800(ULocale uLocale) {
        return LocaleMatcher.getMaximalLsrOrUnd(uLocale);
    }

    static LSR access$900(Locale locale) {
        return LocaleMatcher.getMaximalLsrOrUnd(locale);
    }

    static {
        $assertionsDisabled = !LocaleMatcher.class.desiredAssertionStatus();
        UND_LSR = new LSR("und", "", "");
        UND_ULOCALE = new ULocale("und");
        UND_LOCALE = new Locale("und");
        EMPTY_LOCALE = new Locale("");
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class LocaleLsrIterator
    extends LsrIterator {
        private Iterator<Locale> locales;
        private Locale current;
        private Locale remembered;

        LocaleLsrIterator(Iterator<Locale> iterator2) {
            super(null);
            this.locales = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.locales.hasNext();
        }

        @Override
        public LSR next() {
            this.current = this.locales.next();
            return LocaleMatcher.access$900(this.current);
        }

        @Override
        public void rememberCurrent(int n) {
            this.bestDesiredIndex = n;
            this.remembered = this.current;
        }

        @Override
        public Object next() {
            return this.next();
        }

        static Locale access$1200(LocaleLsrIterator localeLsrIterator) {
            return localeLsrIterator.remembered;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class ULocaleLsrIterator
    extends LsrIterator {
        private Iterator<ULocale> locales;
        private ULocale current;
        private ULocale remembered;

        ULocaleLsrIterator(Iterator<ULocale> iterator2) {
            super(null);
            this.locales = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.locales.hasNext();
        }

        @Override
        public LSR next() {
            this.current = this.locales.next();
            return LocaleMatcher.access$800(this.current);
        }

        @Override
        public void rememberCurrent(int n) {
            this.bestDesiredIndex = n;
            this.remembered = this.current;
        }

        @Override
        public Object next() {
            return this.next();
        }

        static ULocale access$1100(ULocaleLsrIterator uLocaleLsrIterator) {
            return uLocaleLsrIterator.remembered;
        }
    }

    public static final class Builder {
        private List<ULocale> supportedLocales;
        private int thresholdDistance = -1;
        private Demotion demotion;
        private ULocale defaultLocale;
        private FavorSubtag favor;

        private Builder() {
        }

        public Builder setSupportedLocales(String string) {
            return this.setSupportedULocales(LocalePriorityList.add(string).build().getULocales());
        }

        public Builder setSupportedULocales(Collection<ULocale> collection) {
            this.supportedLocales = new ArrayList<ULocale>(collection);
            return this;
        }

        public Builder setSupportedLocales(Collection<Locale> collection) {
            this.supportedLocales = new ArrayList<ULocale>(collection.size());
            for (Locale locale : collection) {
                this.supportedLocales.add(ULocale.forLocale(locale));
            }
            return this;
        }

        public Builder addSupportedULocale(ULocale uLocale) {
            if (this.supportedLocales == null) {
                this.supportedLocales = new ArrayList<ULocale>();
            }
            this.supportedLocales.add(uLocale);
            return this;
        }

        public Builder addSupportedLocale(Locale locale) {
            return this.addSupportedULocale(ULocale.forLocale(locale));
        }

        public Builder setDefaultULocale(ULocale uLocale) {
            this.defaultLocale = uLocale;
            return this;
        }

        public Builder setDefaultLocale(Locale locale) {
            this.defaultLocale = ULocale.forLocale(locale);
            return this;
        }

        public Builder setFavorSubtag(FavorSubtag favorSubtag) {
            this.favor = favorSubtag;
            return this;
        }

        public Builder setDemotionPerDesiredLocale(Demotion demotion) {
            this.demotion = demotion;
            return this;
        }

        @Deprecated
        public Builder internalSetThresholdDistance(int n) {
            if (n > 100) {
                n = 100;
            }
            this.thresholdDistance = n;
            return this;
        }

        public LocaleMatcher build() {
            return new LocaleMatcher(this, null);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder().append("{LocaleMatcher.Builder");
            if (this.supportedLocales != null && !this.supportedLocales.isEmpty()) {
                stringBuilder.append(" supported={").append(this.supportedLocales.toString()).append('}');
            }
            if (this.defaultLocale != null) {
                stringBuilder.append(" default=").append(this.defaultLocale.toString());
            }
            if (this.favor != null) {
                stringBuilder.append(" distance=").append(this.favor.toString());
            }
            if (this.thresholdDistance >= 0) {
                stringBuilder.append(String.format(" threshold=%d", this.thresholdDistance));
            }
            if (this.demotion != null) {
                stringBuilder.append(" demotion=").append(this.demotion.toString());
            }
            return stringBuilder.append('}').toString();
        }

        Builder(1 var1_1) {
            this();
        }

        static int access$200(Builder builder) {
            return builder.thresholdDistance;
        }

        static List access$300(Builder builder) {
            return builder.supportedLocales;
        }

        static ULocale access$400(Builder builder) {
            return builder.defaultLocale;
        }

        static Demotion access$500(Builder builder) {
            return builder.demotion;
        }

        static FavorSubtag access$600(Builder builder) {
            return builder.favor;
        }
    }

    public static final class Result {
        private final ULocale desiredULocale;
        private final ULocale supportedULocale;
        private final Locale desiredLocale;
        private final Locale supportedLocale;
        private final int desiredIndex;
        private final int supportedIndex;

        private Result(ULocale uLocale, ULocale uLocale2, Locale locale, Locale locale2, int n, int n2) {
            this.desiredULocale = uLocale;
            this.supportedULocale = uLocale2;
            this.desiredLocale = locale;
            this.supportedLocale = locale2;
            this.desiredIndex = n;
            this.supportedIndex = n2;
        }

        public ULocale getDesiredULocale() {
            return this.desiredULocale == null && this.desiredLocale != null ? ULocale.forLocale(this.desiredLocale) : this.desiredULocale;
        }

        public Locale getDesiredLocale() {
            return this.desiredLocale == null && this.desiredULocale != null ? this.desiredULocale.toLocale() : this.desiredLocale;
        }

        public ULocale getSupportedULocale() {
            return this.supportedULocale;
        }

        public Locale getSupportedLocale() {
            return this.supportedLocale;
        }

        public int getDesiredIndex() {
            return this.desiredIndex;
        }

        public int getSupportedIndex() {
            return this.supportedIndex;
        }

        public ULocale makeResolvedULocale() {
            String string;
            ULocale uLocale = this.getDesiredULocale();
            if (this.supportedULocale == null || uLocale == null || this.supportedULocale.equals(uLocale)) {
                return this.supportedULocale;
            }
            ULocale.Builder builder = new ULocale.Builder().setLocale(this.supportedULocale);
            String string2 = uLocale.getCountry();
            if (!string2.isEmpty()) {
                builder.setRegion(string2);
            }
            if (!(string = uLocale.getVariant()).isEmpty()) {
                builder.setVariant(string);
            }
            for (char c : uLocale.getExtensionKeys()) {
                builder.setExtension(c, uLocale.getExtension(c));
            }
            return builder.build();
        }

        public Locale makeResolvedLocale() {
            ULocale uLocale = this.makeResolvedULocale();
            return uLocale != null ? uLocale.toLocale() : null;
        }

        Result(ULocale uLocale, ULocale uLocale2, Locale locale, Locale locale2, int n, int n2, 1 var7_7) {
            this(uLocale, uLocale2, locale, locale2, n, n2);
        }
    }

    public static enum Demotion {
        NONE,
        REGION;

    }

    public static enum FavorSubtag {
        LANGUAGE,
        SCRIPT;

    }

    private static abstract class LsrIterator
    implements Iterator<LSR> {
        int bestDesiredIndex = -1;

        private LsrIterator() {
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public abstract void rememberCurrent(int var1);

        LsrIterator(1 var1_1) {
            this();
        }
    }
}

