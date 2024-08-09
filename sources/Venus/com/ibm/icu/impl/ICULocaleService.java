/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ClassLoaderUtil;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUService;
import com.ibm.icu.impl.LocaleUtility;
import com.ibm.icu.util.ULocale;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ICULocaleService
extends ICUService {
    private ULocale fallbackLocale;
    private String fallbackLocaleName;

    public ICULocaleService() {
    }

    public ICULocaleService(String string) {
        super(string);
    }

    public Object get(ULocale uLocale) {
        return this.get(uLocale, -1, null);
    }

    public Object get(ULocale uLocale, int n) {
        return this.get(uLocale, n, null);
    }

    public Object get(ULocale uLocale, ULocale[] uLocaleArray) {
        return this.get(uLocale, -1, uLocaleArray);
    }

    public Object get(ULocale uLocale, int n, ULocale[] uLocaleArray) {
        ICUService.Key key = this.createKey(uLocale, n);
        if (uLocaleArray == null) {
            return this.getKey(key);
        }
        String[] stringArray = new String[1];
        Object object = this.getKey(key, stringArray);
        if (object != null) {
            int n2 = stringArray[0].indexOf("/");
            if (n2 >= 0) {
                stringArray[0] = stringArray[0].substring(n2 + 1);
            }
            uLocaleArray[0] = new ULocale(stringArray[0]);
        }
        return object;
    }

    public ICUService.Factory registerObject(Object object, ULocale uLocale) {
        return this.registerObject(object, uLocale, -1, false);
    }

    public ICUService.Factory registerObject(Object object, ULocale uLocale, boolean bl) {
        return this.registerObject(object, uLocale, -1, bl);
    }

    public ICUService.Factory registerObject(Object object, ULocale uLocale, int n) {
        return this.registerObject(object, uLocale, n, false);
    }

    public ICUService.Factory registerObject(Object object, ULocale uLocale, int n, boolean bl) {
        SimpleLocaleKeyFactory simpleLocaleKeyFactory = new SimpleLocaleKeyFactory(object, uLocale, n, bl);
        return this.registerFactory(simpleLocaleKeyFactory);
    }

    public Locale[] getAvailableLocales() {
        Set<String> set = this.getVisibleIDs();
        Locale[] localeArray = new Locale[set.size()];
        int n = 0;
        for (String string : set) {
            Locale locale = LocaleUtility.getLocaleFromName(string);
            localeArray[n++] = locale;
        }
        return localeArray;
    }

    public ULocale[] getAvailableULocales() {
        Set<String> set = this.getVisibleIDs();
        ULocale[] uLocaleArray = new ULocale[set.size()];
        int n = 0;
        for (String string : set) {
            uLocaleArray[n++] = new ULocale(string);
        }
        return uLocaleArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String validateFallbackLocale() {
        ULocale uLocale = ULocale.getDefault();
        if (uLocale != this.fallbackLocale) {
            ICULocaleService iCULocaleService = this;
            synchronized (iCULocaleService) {
                if (uLocale != this.fallbackLocale) {
                    this.fallbackLocale = uLocale;
                    this.fallbackLocaleName = uLocale.getBaseName();
                    this.clearServiceCache();
                }
            }
        }
        return this.fallbackLocaleName;
    }

    @Override
    public ICUService.Key createKey(String string) {
        return LocaleKey.createWithCanonicalFallback(string, this.validateFallbackLocale());
    }

    public ICUService.Key createKey(String string, int n) {
        return LocaleKey.createWithCanonicalFallback(string, this.validateFallbackLocale(), n);
    }

    public ICUService.Key createKey(ULocale uLocale, int n) {
        return LocaleKey.createWithCanonical(uLocale, this.validateFallbackLocale(), n);
    }

    public static class ICUResourceBundleFactory
    extends LocaleKeyFactory {
        protected final String bundleName;

        public ICUResourceBundleFactory() {
            this("com/ibm/icu/impl/data/icudt66b");
        }

        public ICUResourceBundleFactory(String string) {
            super(true);
            this.bundleName = string;
        }

        @Override
        protected Set<String> getSupportedIDs() {
            return ICUResourceBundle.getFullLocaleNameSet(this.bundleName, this.loader());
        }

        @Override
        public void updateVisibleIDs(Map<String, ICUService.Factory> map) {
            Set<String> set = ICUResourceBundle.getAvailableLocaleNameSet(this.bundleName, this.loader());
            for (String string : set) {
                map.put(string, this);
            }
        }

        @Override
        protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
            return ICUResourceBundle.getBundleInstance(this.bundleName, uLocale, this.loader());
        }

        protected ClassLoader loader() {
            return ClassLoaderUtil.getClassLoader(this.getClass());
        }

        @Override
        public String toString() {
            return super.toString() + ", bundle: " + this.bundleName;
        }
    }

    public static class SimpleLocaleKeyFactory
    extends LocaleKeyFactory {
        private final Object obj;
        private final String id;
        private final int kind;

        public SimpleLocaleKeyFactory(Object object, ULocale uLocale, int n, boolean bl) {
            this(object, uLocale, n, bl, null);
        }

        public SimpleLocaleKeyFactory(Object object, ULocale uLocale, int n, boolean bl, String string) {
            super(bl, string);
            this.obj = object;
            this.id = uLocale.getBaseName();
            this.kind = n;
        }

        @Override
        public Object create(ICUService.Key key, ICUService iCUService) {
            if (!(key instanceof LocaleKey)) {
                return null;
            }
            LocaleKey localeKey = (LocaleKey)key;
            if (this.kind != -1 && this.kind != localeKey.kind()) {
                return null;
            }
            if (!this.id.equals(localeKey.currentID())) {
                return null;
            }
            return this.obj;
        }

        @Override
        protected boolean isSupportedID(String string) {
            return this.id.equals(string);
        }

        @Override
        public void updateVisibleIDs(Map<String, ICUService.Factory> map) {
            if (this.visible) {
                map.put(this.id, this);
            } else {
                map.remove(this.id);
            }
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(super.toString());
            stringBuilder.append(", id: ");
            stringBuilder.append(this.id);
            stringBuilder.append(", kind: ");
            stringBuilder.append(this.kind);
            return stringBuilder.toString();
        }
    }

    public static abstract class LocaleKeyFactory
    implements ICUService.Factory {
        protected final String name;
        protected final boolean visible;
        public static final boolean VISIBLE = true;
        public static final boolean INVISIBLE = false;

        protected LocaleKeyFactory(boolean bl) {
            this.visible = bl;
            this.name = null;
        }

        protected LocaleKeyFactory(boolean bl, String string) {
            this.visible = bl;
            this.name = string;
        }

        @Override
        public Object create(ICUService.Key key, ICUService iCUService) {
            if (this.handlesKey(key)) {
                LocaleKey localeKey = (LocaleKey)key;
                int n = localeKey.kind();
                ULocale uLocale = localeKey.currentLocale();
                return this.handleCreate(uLocale, n, iCUService);
            }
            return null;
        }

        protected boolean handlesKey(ICUService.Key key) {
            if (key != null) {
                String string = key.currentID();
                Set<String> set = this.getSupportedIDs();
                return set.contains(string);
            }
            return true;
        }

        @Override
        public void updateVisibleIDs(Map<String, ICUService.Factory> map) {
            Set<String> set = this.getSupportedIDs();
            for (String string : set) {
                if (this.visible) {
                    map.put(string, this);
                    continue;
                }
                map.remove(string);
            }
        }

        @Override
        public String getDisplayName(String string, ULocale uLocale) {
            if (uLocale == null) {
                return string;
            }
            ULocale uLocale2 = new ULocale(string);
            return uLocale2.getDisplayName(uLocale);
        }

        protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
            return null;
        }

        protected boolean isSupportedID(String string) {
            return this.getSupportedIDs().contains(string);
        }

        protected Set<String> getSupportedIDs() {
            return Collections.emptySet();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(super.toString());
            if (this.name != null) {
                stringBuilder.append(", name: ");
                stringBuilder.append(this.name);
            }
            stringBuilder.append(", visible: ");
            stringBuilder.append(this.visible);
            return stringBuilder.toString();
        }
    }

    public static class LocaleKey
    extends ICUService.Key {
        private int kind;
        private int varstart;
        private String primaryID;
        private String fallbackID;
        private String currentID;
        public static final int KIND_ANY = -1;

        public static LocaleKey createWithCanonicalFallback(String string, String string2) {
            return LocaleKey.createWithCanonicalFallback(string, string2, -1);
        }

        public static LocaleKey createWithCanonicalFallback(String string, String string2, int n) {
            if (string == null) {
                return null;
            }
            String string3 = ULocale.getName(string);
            return new LocaleKey(string, string3, string2, n);
        }

        public static LocaleKey createWithCanonical(ULocale uLocale, String string, int n) {
            if (uLocale == null) {
                return null;
            }
            String string2 = uLocale.getName();
            return new LocaleKey(string2, string2, string, n);
        }

        protected LocaleKey(String string, String string2, String string3, int n) {
            super(string);
            this.kind = n;
            if (string2 == null || string2.equalsIgnoreCase("root")) {
                this.primaryID = "";
                this.fallbackID = null;
            } else {
                int n2 = string2.indexOf(64);
                if (n2 == 4 && string2.regionMatches(true, 0, "root", 0, 1)) {
                    this.primaryID = string2.substring(4);
                    this.varstart = 0;
                    this.fallbackID = null;
                } else {
                    this.primaryID = string2;
                    this.varstart = n2;
                    this.fallbackID = string3 == null || this.primaryID.equals(string3) ? "" : string3;
                }
            }
            this.currentID = this.varstart == -1 ? this.primaryID : this.primaryID.substring(0, this.varstart);
        }

        public String prefix() {
            return this.kind == -1 ? null : Integer.toString(this.kind());
        }

        public int kind() {
            return this.kind;
        }

        @Override
        public String canonicalID() {
            return this.primaryID;
        }

        @Override
        public String currentID() {
            return this.currentID;
        }

        @Override
        public String currentDescriptor() {
            String string = this.currentID();
            if (string != null) {
                StringBuilder stringBuilder = new StringBuilder();
                if (this.kind != -1) {
                    stringBuilder.append(this.prefix());
                }
                stringBuilder.append('/');
                stringBuilder.append(string);
                if (this.varstart != -1) {
                    stringBuilder.append(this.primaryID.substring(this.varstart, this.primaryID.length()));
                }
                string = stringBuilder.toString();
            }
            return string;
        }

        public ULocale canonicalLocale() {
            return new ULocale(this.primaryID);
        }

        public ULocale currentLocale() {
            if (this.varstart == -1) {
                return new ULocale(this.currentID);
            }
            return new ULocale(this.currentID + this.primaryID.substring(this.varstart));
        }

        @Override
        public boolean fallback() {
            int n = this.currentID.lastIndexOf(95);
            if (n != -1) {
                while (--n >= 0 && this.currentID.charAt(n) == '_') {
                }
                this.currentID = this.currentID.substring(0, n + 1);
                return false;
            }
            if (this.fallbackID != null) {
                this.currentID = this.fallbackID;
                this.fallbackID = this.fallbackID.length() == 0 ? null : "";
                return false;
            }
            this.currentID = null;
            return true;
        }

        @Override
        public boolean isFallbackOf(String string) {
            return LocaleUtility.isFallbackOf(this.canonicalID(), string);
        }
    }
}

