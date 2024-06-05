package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.lang.reflect.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.lang.*;
import java.util.*;

public abstract class LocaleDisplayNames
{
    private static final Method FACTORY_DIALECTHANDLING;
    private static final Method FACTORY_DISPLAYCONTEXT;
    
    public static LocaleDisplayNames getInstance(final ULocale locale) {
        return getInstance(locale, DialectHandling.STANDARD_NAMES);
    }
    
    public static LocaleDisplayNames getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }
    
    public static LocaleDisplayNames getInstance(final ULocale locale, final DialectHandling dialectHandling) {
        LocaleDisplayNames result = null;
        if (LocaleDisplayNames.FACTORY_DIALECTHANDLING != null) {
            try {
                result = (LocaleDisplayNames)LocaleDisplayNames.FACTORY_DIALECTHANDLING.invoke(null, locale, dialectHandling);
            }
            catch (InvocationTargetException ex) {}
            catch (IllegalAccessException ex2) {}
        }
        if (result == null) {
            result = new LastResortLocaleDisplayNames(locale, dialectHandling);
        }
        return result;
    }
    
    public static LocaleDisplayNames getInstance(final ULocale locale, final DisplayContext... contexts) {
        LocaleDisplayNames result = null;
        if (LocaleDisplayNames.FACTORY_DISPLAYCONTEXT != null) {
            try {
                result = (LocaleDisplayNames)LocaleDisplayNames.FACTORY_DISPLAYCONTEXT.invoke(null, locale, contexts);
            }
            catch (InvocationTargetException ex) {}
            catch (IllegalAccessException ex2) {}
        }
        if (result == null) {
            result = new LastResortLocaleDisplayNames(locale, contexts);
        }
        return result;
    }
    
    public static LocaleDisplayNames getInstance(final Locale locale, final DisplayContext... contexts) {
        return getInstance(ULocale.forLocale(locale), contexts);
    }
    
    public abstract ULocale getLocale();
    
    public abstract DialectHandling getDialectHandling();
    
    public abstract DisplayContext getContext(final DisplayContext.Type p0);
    
    public abstract String localeDisplayName(final ULocale p0);
    
    public abstract String localeDisplayName(final Locale p0);
    
    public abstract String localeDisplayName(final String p0);
    
    public abstract String languageDisplayName(final String p0);
    
    public abstract String scriptDisplayName(final String p0);
    
    @Deprecated
    public String scriptDisplayNameInContext(final String script) {
        return this.scriptDisplayName(script);
    }
    
    public abstract String scriptDisplayName(final int p0);
    
    public abstract String regionDisplayName(final String p0);
    
    public abstract String variantDisplayName(final String p0);
    
    public abstract String keyDisplayName(final String p0);
    
    public abstract String keyValueDisplayName(final String p0, final String p1);
    
    public List<UiListItem> getUiList(final Set<ULocale> localeSet, final boolean inSelf, final Comparator<Object> collator) {
        return this.getUiListCompareWholeItems(localeSet, UiListItem.getComparator(collator, inSelf));
    }
    
    public abstract List<UiListItem> getUiListCompareWholeItems(final Set<ULocale> p0, final Comparator<UiListItem> p1);
    
    @Deprecated
    protected LocaleDisplayNames() {
    }
    
    static {
        final String implClassName = ICUConfig.get("com.ibm.icu.text.LocaleDisplayNames.impl", "com.ibm.icu.impl.LocaleDisplayNamesImpl");
        Method factoryDialectHandling = null;
        Method factoryDisplayContext = null;
        try {
            final Class<?> implClass = Class.forName(implClassName);
            try {
                factoryDialectHandling = implClass.getMethod("getInstance", ULocale.class, DialectHandling.class);
            }
            catch (NoSuchMethodException ex) {}
            try {
                factoryDisplayContext = implClass.getMethod("getInstance", ULocale.class, DisplayContext[].class);
            }
            catch (NoSuchMethodException ex2) {}
        }
        catch (ClassNotFoundException ex3) {}
        FACTORY_DIALECTHANDLING = factoryDialectHandling;
        FACTORY_DISPLAYCONTEXT = factoryDisplayContext;
    }
    
    public enum DialectHandling
    {
        STANDARD_NAMES, 
        DIALECT_NAMES;
    }
    
    public static class UiListItem
    {
        public final ULocale minimized;
        public final ULocale modified;
        public final String nameInDisplayLocale;
        public final String nameInSelf;
        
        public UiListItem(final ULocale minimized, final ULocale modified, final String nameInDisplayLocale, final String nameInSelf) {
            this.minimized = minimized;
            this.modified = modified;
            this.nameInDisplayLocale = nameInDisplayLocale;
            this.nameInSelf = nameInSelf;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof UiListItem)) {
                return false;
            }
            final UiListItem other = (UiListItem)obj;
            return this.nameInDisplayLocale.equals(other.nameInDisplayLocale) && this.nameInSelf.equals(other.nameInSelf) && this.minimized.equals(other.minimized) && this.modified.equals(other.modified);
        }
        
        @Override
        public int hashCode() {
            return this.modified.hashCode() ^ this.nameInDisplayLocale.hashCode();
        }
        
        @Override
        public String toString() {
            return "{" + this.minimized + ", " + this.modified + ", " + this.nameInDisplayLocale + ", " + this.nameInSelf + "}";
        }
        
        public static Comparator<UiListItem> getComparator(final Comparator<Object> comparator, final boolean inSelf) {
            return new UiListItemComparator(comparator, inSelf);
        }
        
        private static class UiListItemComparator implements Comparator<UiListItem>
        {
            private final Comparator<Object> collator;
            private final boolean useSelf;
            
            UiListItemComparator(final Comparator<Object> collator, final boolean useSelf) {
                this.collator = collator;
                this.useSelf = useSelf;
            }
            
            @Override
            public int compare(final UiListItem o1, final UiListItem o2) {
                final int result = this.useSelf ? this.collator.compare(o1.nameInSelf, o2.nameInSelf) : this.collator.compare(o1.nameInDisplayLocale, o2.nameInDisplayLocale);
                return (result != 0) ? result : o1.modified.compareTo(o2.modified);
            }
        }
    }
    
    private static class LastResortLocaleDisplayNames extends LocaleDisplayNames
    {
        private ULocale locale;
        private DisplayContext[] contexts;
        
        private LastResortLocaleDisplayNames(final ULocale locale, final DialectHandling dialectHandling) {
            this.locale = locale;
            final DisplayContext context = (dialectHandling == DialectHandling.DIALECT_NAMES) ? DisplayContext.DIALECT_NAMES : DisplayContext.STANDARD_NAMES;
            this.contexts = new DisplayContext[] { context };
        }
        
        private LastResortLocaleDisplayNames(final ULocale locale, final DisplayContext... contexts) {
            this.locale = locale;
            System.arraycopy(contexts, 0, this.contexts = new DisplayContext[contexts.length], 0, contexts.length);
        }
        
        @Override
        public ULocale getLocale() {
            return this.locale;
        }
        
        @Override
        public DialectHandling getDialectHandling() {
            DialectHandling result = DialectHandling.STANDARD_NAMES;
            for (final DisplayContext context : this.contexts) {
                if (context.type() == DisplayContext.Type.DIALECT_HANDLING && context.value() == DisplayContext.DIALECT_NAMES.ordinal()) {
                    result = DialectHandling.DIALECT_NAMES;
                    break;
                }
            }
            return result;
        }
        
        @Override
        public DisplayContext getContext(final DisplayContext.Type type) {
            DisplayContext result = DisplayContext.STANDARD_NAMES;
            for (final DisplayContext context : this.contexts) {
                if (context.type() == type) {
                    result = context;
                    break;
                }
            }
            return result;
        }
        
        @Override
        public String localeDisplayName(final ULocale locale) {
            return locale.getName();
        }
        
        @Override
        public String localeDisplayName(final Locale locale) {
            return ULocale.forLocale(locale).getName();
        }
        
        @Override
        public String localeDisplayName(final String localeId) {
            return new ULocale(localeId).getName();
        }
        
        @Override
        public String languageDisplayName(final String lang) {
            return lang;
        }
        
        @Override
        public String scriptDisplayName(final String script) {
            return script;
        }
        
        @Override
        public String scriptDisplayName(final int scriptCode) {
            return UScript.getShortName(scriptCode);
        }
        
        @Override
        public String regionDisplayName(final String region) {
            return region;
        }
        
        @Override
        public String variantDisplayName(final String variant) {
            return variant;
        }
        
        @Override
        public String keyDisplayName(final String key) {
            return key;
        }
        
        @Override
        public String keyValueDisplayName(final String key, final String value) {
            return value;
        }
        
        @Override
        public List<UiListItem> getUiListCompareWholeItems(final Set<ULocale> localeSet, final Comparator<UiListItem> comparator) {
            return Collections.emptyList();
        }
    }
}
