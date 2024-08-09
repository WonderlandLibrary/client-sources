/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.util.ULocale;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public abstract class LocaleDisplayNames {
    private static final Method FACTORY_DIALECTHANDLING;
    private static final Method FACTORY_DISPLAYCONTEXT;

    public static LocaleDisplayNames getInstance(ULocale uLocale) {
        return LocaleDisplayNames.getInstance(uLocale, DialectHandling.STANDARD_NAMES);
    }

    public static LocaleDisplayNames getInstance(Locale locale) {
        return LocaleDisplayNames.getInstance(ULocale.forLocale(locale));
    }

    public static LocaleDisplayNames getInstance(ULocale uLocale, DialectHandling dialectHandling) {
        LocaleDisplayNames localeDisplayNames = null;
        if (FACTORY_DIALECTHANDLING != null) {
            try {
                localeDisplayNames = (LocaleDisplayNames)FACTORY_DIALECTHANDLING.invoke(null, new Object[]{uLocale, dialectHandling});
            } catch (InvocationTargetException invocationTargetException) {
            } catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
        if (localeDisplayNames == null) {
            localeDisplayNames = new LastResortLocaleDisplayNames(uLocale, dialectHandling, null);
        }
        return localeDisplayNames;
    }

    public static LocaleDisplayNames getInstance(ULocale uLocale, DisplayContext ... displayContextArray) {
        LocaleDisplayNames localeDisplayNames = null;
        if (FACTORY_DISPLAYCONTEXT != null) {
            try {
                localeDisplayNames = (LocaleDisplayNames)FACTORY_DISPLAYCONTEXT.invoke(null, new Object[]{uLocale, displayContextArray});
            } catch (InvocationTargetException invocationTargetException) {
            } catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
        if (localeDisplayNames == null) {
            localeDisplayNames = new LastResortLocaleDisplayNames(uLocale, displayContextArray, null);
        }
        return localeDisplayNames;
    }

    public static LocaleDisplayNames getInstance(Locale locale, DisplayContext ... displayContextArray) {
        return LocaleDisplayNames.getInstance(ULocale.forLocale(locale), displayContextArray);
    }

    public abstract ULocale getLocale();

    public abstract DialectHandling getDialectHandling();

    public abstract DisplayContext getContext(DisplayContext.Type var1);

    public abstract String localeDisplayName(ULocale var1);

    public abstract String localeDisplayName(Locale var1);

    public abstract String localeDisplayName(String var1);

    public abstract String languageDisplayName(String var1);

    public abstract String scriptDisplayName(String var1);

    @Deprecated
    public String scriptDisplayNameInContext(String string) {
        return this.scriptDisplayName(string);
    }

    public abstract String scriptDisplayName(int var1);

    public abstract String regionDisplayName(String var1);

    public abstract String variantDisplayName(String var1);

    public abstract String keyDisplayName(String var1);

    public abstract String keyValueDisplayName(String var1, String var2);

    public List<UiListItem> getUiList(Set<ULocale> set, boolean bl, Comparator<Object> comparator) {
        return this.getUiListCompareWholeItems(set, UiListItem.getComparator(comparator, bl));
    }

    public abstract List<UiListItem> getUiListCompareWholeItems(Set<ULocale> var1, Comparator<UiListItem> var2);

    @Deprecated
    protected LocaleDisplayNames() {
    }

    static {
        String string = ICUConfig.get("com.ibm.icu.text.LocaleDisplayNames.impl", "com.ibm.icu.impl.LocaleDisplayNamesImpl");
        Method method = null;
        Method method2 = null;
        try {
            Class<?> clazz = Class.forName(string);
            try {
                method = clazz.getMethod("getInstance", ULocale.class, DialectHandling.class);
            } catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
            try {
                method2 = clazz.getMethod("getInstance", ULocale.class, DisplayContext[].class);
            } catch (NoSuchMethodException noSuchMethodException) {}
        } catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        FACTORY_DIALECTHANDLING = method;
        FACTORY_DISPLAYCONTEXT = method2;
    }

    private static class LastResortLocaleDisplayNames
    extends LocaleDisplayNames {
        private ULocale locale;
        private DisplayContext[] contexts;

        private LastResortLocaleDisplayNames(ULocale uLocale, DialectHandling dialectHandling) {
            this.locale = uLocale;
            DisplayContext displayContext = dialectHandling == DialectHandling.DIALECT_NAMES ? DisplayContext.DIALECT_NAMES : DisplayContext.STANDARD_NAMES;
            this.contexts = new DisplayContext[]{displayContext};
        }

        private LastResortLocaleDisplayNames(ULocale uLocale, DisplayContext ... displayContextArray) {
            this.locale = uLocale;
            this.contexts = new DisplayContext[displayContextArray.length];
            System.arraycopy(displayContextArray, 0, this.contexts, 0, displayContextArray.length);
        }

        @Override
        public ULocale getLocale() {
            return this.locale;
        }

        @Override
        public DialectHandling getDialectHandling() {
            DialectHandling dialectHandling = DialectHandling.STANDARD_NAMES;
            for (DisplayContext displayContext : this.contexts) {
                if (displayContext.type() != DisplayContext.Type.DIALECT_HANDLING || displayContext.value() != DisplayContext.DIALECT_NAMES.ordinal()) continue;
                dialectHandling = DialectHandling.DIALECT_NAMES;
                break;
            }
            return dialectHandling;
        }

        @Override
        public DisplayContext getContext(DisplayContext.Type type) {
            DisplayContext displayContext = DisplayContext.STANDARD_NAMES;
            for (DisplayContext displayContext2 : this.contexts) {
                if (displayContext2.type() != type) continue;
                displayContext = displayContext2;
                break;
            }
            return displayContext;
        }

        @Override
        public String localeDisplayName(ULocale uLocale) {
            return uLocale.getName();
        }

        @Override
        public String localeDisplayName(Locale locale) {
            return ULocale.forLocale(locale).getName();
        }

        @Override
        public String localeDisplayName(String string) {
            return new ULocale(string).getName();
        }

        @Override
        public String languageDisplayName(String string) {
            return string;
        }

        @Override
        public String scriptDisplayName(String string) {
            return string;
        }

        @Override
        public String scriptDisplayName(int n) {
            return UScript.getShortName(n);
        }

        @Override
        public String regionDisplayName(String string) {
            return string;
        }

        @Override
        public String variantDisplayName(String string) {
            return string;
        }

        @Override
        public String keyDisplayName(String string) {
            return string;
        }

        @Override
        public String keyValueDisplayName(String string, String string2) {
            return string2;
        }

        @Override
        public List<UiListItem> getUiListCompareWholeItems(Set<ULocale> set, Comparator<UiListItem> comparator) {
            return Collections.emptyList();
        }

        LastResortLocaleDisplayNames(ULocale uLocale, DialectHandling dialectHandling, 1 var3_3) {
            this(uLocale, dialectHandling);
        }

        LastResortLocaleDisplayNames(ULocale uLocale, DisplayContext[] displayContextArray, 1 var3_3) {
            this(uLocale, displayContextArray);
        }
    }

    public static class UiListItem {
        public final ULocale minimized;
        public final ULocale modified;
        public final String nameInDisplayLocale;
        public final String nameInSelf;

        public UiListItem(ULocale uLocale, ULocale uLocale2, String string, String string2) {
            this.minimized = uLocale;
            this.modified = uLocale2;
            this.nameInDisplayLocale = string;
            this.nameInSelf = string2;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || !(object instanceof UiListItem)) {
                return true;
            }
            UiListItem uiListItem = (UiListItem)object;
            return this.nameInDisplayLocale.equals(uiListItem.nameInDisplayLocale) && this.nameInSelf.equals(uiListItem.nameInSelf) && this.minimized.equals(uiListItem.minimized) && this.modified.equals(uiListItem.modified);
        }

        public int hashCode() {
            return this.modified.hashCode() ^ this.nameInDisplayLocale.hashCode();
        }

        public String toString() {
            return "{" + this.minimized + ", " + this.modified + ", " + this.nameInDisplayLocale + ", " + this.nameInSelf + "}";
        }

        public static Comparator<UiListItem> getComparator(Comparator<Object> comparator, boolean bl) {
            return new UiListItemComparator(comparator, bl);
        }

        private static class UiListItemComparator
        implements Comparator<UiListItem> {
            private final Comparator<Object> collator;
            private final boolean useSelf;

            UiListItemComparator(Comparator<Object> comparator, boolean bl) {
                this.collator = comparator;
                this.useSelf = bl;
            }

            @Override
            public int compare(UiListItem uiListItem, UiListItem uiListItem2) {
                int n = this.useSelf ? this.collator.compare(uiListItem.nameInSelf, uiListItem2.nameInSelf) : this.collator.compare(uiListItem.nameInDisplayLocale, uiListItem2.nameInDisplayLocale);
                return n != 0 ? n : uiListItem.modified.compareTo(uiListItem2.modified);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((UiListItem)object, (UiListItem)object2);
            }
        }
    }

    public static enum DialectHandling {
        STANDARD_NAMES,
        DIALECT_NAMES;

    }
}

