package com.ibm.icu.text;

import java.util.*;
import java.io.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;

public final class ListFormatter
{
    private final String two;
    private final String start;
    private final String middle;
    private final String end;
    private final ULocale locale;
    static Cache cache;
    
    @Deprecated
    public ListFormatter(final String two, final String start, final String middle, final String end) {
        this(compilePattern(two, new StringBuilder()), compilePattern(start, new StringBuilder()), compilePattern(middle, new StringBuilder()), compilePattern(end, new StringBuilder()), null);
    }
    
    private ListFormatter(final String two, final String start, final String middle, final String end, final ULocale locale) {
        this.two = two;
        this.start = start;
        this.middle = middle;
        this.end = end;
        this.locale = locale;
    }
    
    private static String compilePattern(final String pattern, final StringBuilder sb) {
        return SimpleFormatterImpl.compileToStringMinMaxArguments(pattern, sb, 2, 2);
    }
    
    public static ListFormatter getInstance(final ULocale locale) {
        return getInstance(locale, Style.STANDARD);
    }
    
    public static ListFormatter getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale), Style.STANDARD);
    }
    
    @Deprecated
    public static ListFormatter getInstance(final ULocale locale, final Style style) {
        return ListFormatter.cache.get(locale, style.getName());
    }
    
    public static ListFormatter getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public String format(final Object... items) {
        return this.format(Arrays.asList(items));
    }
    
    public String format(final Collection<?> items) {
        return this.format(items, -1).toString();
    }
    
    FormattedListBuilder format(final Collection<?> items, final int index) {
        final Iterator<?> it = items.iterator();
        final int count = items.size();
        switch (count) {
            case 0: {
                return new FormattedListBuilder("", false);
            }
            case 1: {
                return new FormattedListBuilder(it.next(), index == 0);
            }
            case 2: {
                return new FormattedListBuilder(it.next(), index == 0).append(this.two, it.next(), index == 1);
            }
            default: {
                final FormattedListBuilder builder = new FormattedListBuilder(it.next(), index == 0);
                builder.append(this.start, it.next(), index == 1);
                for (int idx = 2; idx < count - 1; ++idx) {
                    builder.append(this.middle, it.next(), index == idx);
                }
                return builder.append(this.end, it.next(), index == count - 1);
            }
        }
    }
    
    public String getPatternForNumItems(final int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("count must be > 0");
        }
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < count; ++i) {
            list.add(String.format("{%d}", i));
        }
        return this.format(list);
    }
    
    @Deprecated
    public ULocale getLocale() {
        return this.locale;
    }
    
    static {
        ListFormatter.cache = new Cache();
    }
    
    @Deprecated
    public enum Style
    {
        @Deprecated
        STANDARD("standard"), 
        @Deprecated
        DURATION("unit"), 
        @Deprecated
        DURATION_SHORT("unit-short"), 
        @Deprecated
        DURATION_NARROW("unit-narrow");
        
        private final String name;
        
        private Style(final String name) {
            this.name = name;
        }
        
        @Deprecated
        public String getName() {
            return this.name;
        }
    }
    
    static class FormattedListBuilder
    {
        private StringBuilder current;
        private int offset;
        
        public FormattedListBuilder(final Object start, final boolean recordOffset) {
            this.current = new StringBuilder(start.toString());
            this.offset = (recordOffset ? 0 : -1);
        }
        
        public FormattedListBuilder append(final String pattern, final Object next, final boolean recordOffset) {
            final int[] offsets = (int[])((recordOffset || this.offsetRecorded()) ? new int[2] : null);
            SimpleFormatterImpl.formatAndReplace(pattern, this.current, offsets, this.current, next.toString());
            if (offsets != null) {
                if (offsets[0] == -1 || offsets[1] == -1) {
                    throw new IllegalArgumentException("{0} or {1} missing from pattern " + pattern);
                }
                if (recordOffset) {
                    this.offset = offsets[1];
                }
                else {
                    this.offset += offsets[0];
                }
            }
            return this;
        }
        
        public void appendTo(final Appendable appendable) {
            try {
                appendable.append(this.current);
            }
            catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }
        
        @Override
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
    
    private static class Cache
    {
        private final ICUCache<String, ListFormatter> cache;
        
        private Cache() {
            this.cache = new SimpleCache<String, ListFormatter>();
        }
        
        public ListFormatter get(final ULocale locale, final String style) {
            final String key = String.format("%s:%s", locale.toString(), style);
            ListFormatter result = this.cache.get(key);
            if (result == null) {
                result = load(locale, style);
                this.cache.put(key, result);
            }
            return result;
        }
        
        private static ListFormatter load(final ULocale ulocale, final String style) {
            final ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", ulocale);
            final StringBuilder sb = new StringBuilder();
            return new ListFormatter(compilePattern(r.getWithFallback("listPattern/" + style + "/2").getString(), sb), compilePattern(r.getWithFallback("listPattern/" + style + "/start").getString(), sb), compilePattern(r.getWithFallback("listPattern/" + style + "/middle").getString(), sb), compilePattern(r.getWithFallback("listPattern/" + style + "/end").getString(), sb), ulocale, null);
        }
    }
}
