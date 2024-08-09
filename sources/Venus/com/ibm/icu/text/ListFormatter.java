/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

public final class ListFormatter {
    private final String two;
    private final String start;
    private final String middle;
    private final String end;
    private final ULocale locale;
    static Cache cache = new Cache(null);

    @Deprecated
    public ListFormatter(String string, String string2, String string3, String string4) {
        this(ListFormatter.compilePattern(string, new StringBuilder()), ListFormatter.compilePattern(string2, new StringBuilder()), ListFormatter.compilePattern(string3, new StringBuilder()), ListFormatter.compilePattern(string4, new StringBuilder()), null);
    }

    private ListFormatter(String string, String string2, String string3, String string4, ULocale uLocale) {
        this.two = string;
        this.start = string2;
        this.middle = string3;
        this.end = string4;
        this.locale = uLocale;
    }

    private static String compilePattern(String string, StringBuilder stringBuilder) {
        return SimpleFormatterImpl.compileToStringMinMaxArguments(string, stringBuilder, 2, 2);
    }

    public static ListFormatter getInstance(ULocale uLocale) {
        return ListFormatter.getInstance(uLocale, Style.STANDARD);
    }

    public static ListFormatter getInstance(Locale locale) {
        return ListFormatter.getInstance(ULocale.forLocale(locale), Style.STANDARD);
    }

    @Deprecated
    public static ListFormatter getInstance(ULocale uLocale, Style style) {
        return cache.get(uLocale, style.getName());
    }

    public static ListFormatter getInstance() {
        return ListFormatter.getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public String format(Object ... objectArray) {
        return this.format(Arrays.asList(objectArray));
    }

    public String format(Collection<?> collection) {
        return this.format(collection, -1).toString();
    }

    FormattedListBuilder format(Collection<?> collection, int n) {
        Iterator<?> iterator2 = collection.iterator();
        int n2 = collection.size();
        switch (n2) {
            case 0: {
                return new FormattedListBuilder("", false);
            }
            case 1: {
                return new FormattedListBuilder(iterator2.next(), n == 0);
            }
            case 2: {
                return new FormattedListBuilder(iterator2.next(), n == 0).append(this.two, iterator2.next(), n == 1);
            }
        }
        FormattedListBuilder formattedListBuilder = new FormattedListBuilder(iterator2.next(), n == 0);
        formattedListBuilder.append(this.start, iterator2.next(), n == 1);
        for (int i = 2; i < n2 - 1; ++i) {
            formattedListBuilder.append(this.middle, iterator2.next(), n == i);
        }
        return formattedListBuilder.append(this.end, iterator2.next(), n == n2 - 1);
    }

    public String getPatternForNumItems(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("count must be > 0");
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < n; ++i) {
            arrayList.add(String.format("{%d}", i));
        }
        return this.format(arrayList);
    }

    @Deprecated
    public ULocale getLocale() {
        return this.locale;
    }

    static String access$000(String string, StringBuilder stringBuilder) {
        return ListFormatter.compilePattern(string, stringBuilder);
    }

    ListFormatter(String string, String string2, String string3, String string4, ULocale uLocale, 1 var6_6) {
        this(string, string2, string3, string4, uLocale);
    }

    private static class Cache {
        private final ICUCache<String, ListFormatter> cache = new SimpleCache<String, ListFormatter>();

        private Cache() {
        }

        public ListFormatter get(ULocale uLocale, String string) {
            String string2 = String.format("%s:%s", uLocale.toString(), string);
            ListFormatter listFormatter = this.cache.get(string2);
            if (listFormatter == null) {
                listFormatter = Cache.load(uLocale, string);
                this.cache.put(string2, listFormatter);
            }
            return listFormatter;
        }

        private static ListFormatter load(ULocale uLocale, String string) {
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
            StringBuilder stringBuilder = new StringBuilder();
            return new ListFormatter(ListFormatter.access$000(iCUResourceBundle.getWithFallback("listPattern/" + string + "/2").getString(), stringBuilder), ListFormatter.access$000(iCUResourceBundle.getWithFallback("listPattern/" + string + "/start").getString(), stringBuilder), ListFormatter.access$000(iCUResourceBundle.getWithFallback("listPattern/" + string + "/middle").getString(), stringBuilder), ListFormatter.access$000(iCUResourceBundle.getWithFallback("listPattern/" + string + "/end").getString(), stringBuilder), uLocale, null);
        }

        Cache(1 var1_1) {
            this();
        }
    }

    static class FormattedListBuilder {
        private StringBuilder current;
        private int offset;

        public FormattedListBuilder(Object object, boolean bl) {
            this.current = new StringBuilder(object.toString());
            this.offset = bl ? 0 : -1;
        }

        public FormattedListBuilder append(String string, Object object, boolean bl) {
            int[] nArray = (int[])(bl || this.offsetRecorded() ? new int[2] : null);
            SimpleFormatterImpl.formatAndReplace(string, this.current, nArray, this.current, object.toString());
            if (nArray != null) {
                if (nArray[0] == -1 || nArray[1] == -1) {
                    throw new IllegalArgumentException("{0} or {1} missing from pattern " + string);
                }
                this.offset = bl ? nArray[1] : (this.offset += nArray[0]);
            }
            return this;
        }

        public void appendTo(Appendable appendable) {
            try {
                appendable.append(this.current);
            } catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }

        public String toString() {
            return this.current.toString();
        }

        public int getOffset() {
            return this.offset;
        }

        private boolean offsetRecorded() {
            return this.offset >= 0;
        }
    }

    @Deprecated
    public static enum Style {
        STANDARD("standard"),
        OR("or"),
        UNIT("unit"),
        UNIT_SHORT("unit-short"),
        UNIT_NARROW("unit-narrow");

        private final String name;

        private Style(String string2) {
            this.name = string2;
        }

        @Deprecated
        public String getName() {
            return this.name;
        }
    }
}

