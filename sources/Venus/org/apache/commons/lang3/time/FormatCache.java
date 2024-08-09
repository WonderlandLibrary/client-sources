/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.time;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

abstract class FormatCache<F extends Format> {
    static final int NONE = -1;
    private final ConcurrentMap<MultipartKey, F> cInstanceCache = new ConcurrentHashMap<MultipartKey, F>(7);
    private static final ConcurrentMap<MultipartKey, String> cDateTimeInstanceCache = new ConcurrentHashMap<MultipartKey, String>(7);

    FormatCache() {
    }

    public F getInstance() {
        return this.getDateTimeInstance(3, 3, TimeZone.getDefault(), Locale.getDefault());
    }

    public F getInstance(String string, TimeZone timeZone, Locale locale) {
        Format format2;
        MultipartKey multipartKey;
        Format format3;
        if (string == null) {
            throw new NullPointerException("pattern must not be null");
        }
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if ((format3 = (Format)this.cInstanceCache.get(multipartKey = new MultipartKey(string, timeZone, locale))) == null && (format2 = this.cInstanceCache.putIfAbsent(multipartKey, format3 = this.createInstance(string, timeZone, locale))) != null) {
            format3 = format2;
        }
        return (F)format3;
    }

    protected abstract F createInstance(String var1, TimeZone var2, Locale var3);

    private F getDateTimeInstance(Integer n, Integer n2, TimeZone timeZone, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String string = FormatCache.getPatternForStyle(n, n2, locale);
        return this.getInstance(string, timeZone, locale);
    }

    F getDateTimeInstance(int n, int n2, TimeZone timeZone, Locale locale) {
        return this.getDateTimeInstance((Integer)n, (Integer)n2, timeZone, locale);
    }

    F getDateInstance(int n, TimeZone timeZone, Locale locale) {
        return this.getDateTimeInstance((Integer)n, null, timeZone, locale);
    }

    F getTimeInstance(int n, TimeZone timeZone, Locale locale) {
        return this.getDateTimeInstance(null, (Integer)n, timeZone, locale);
    }

    static String getPatternForStyle(Integer n, Integer n2, Locale locale) {
        MultipartKey multipartKey = new MultipartKey(n, n2, locale);
        String string = (String)cDateTimeInstanceCache.get(multipartKey);
        if (string == null) {
            try {
                DateFormat dateFormat = n == null ? DateFormat.getTimeInstance(n2, locale) : (n2 == null ? DateFormat.getDateInstance(n, locale) : DateFormat.getDateTimeInstance(n, n2, locale));
                string = ((SimpleDateFormat)dateFormat).toPattern();
                String string2 = cDateTimeInstanceCache.putIfAbsent(multipartKey, string);
                if (string2 != null) {
                    string = string2;
                }
            } catch (ClassCastException classCastException) {
                throw new IllegalArgumentException("No date time pattern for locale: " + locale);
            }
        }
        return string;
    }

    private static class MultipartKey {
        private final Object[] keys;
        private int hashCode;

        public MultipartKey(Object ... objectArray) {
            this.keys = objectArray;
        }

        public boolean equals(Object object) {
            return Arrays.equals(this.keys, ((MultipartKey)object).keys);
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                int n = 0;
                for (Object object : this.keys) {
                    if (object == null) continue;
                    n = n * 7 + object.hashCode();
                }
                this.hashCode = n;
            }
            return this.hashCode;
        }
    }
}

