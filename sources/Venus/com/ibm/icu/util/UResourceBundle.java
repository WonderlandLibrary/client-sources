/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUResourceBundleReader;
import com.ibm.icu.impl.ResourceBundleWrapper;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundleIterator;
import com.ibm.icu.util.UResourceTypeMismatchException;
import com.ibm.icu.util.VersionInfo;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public abstract class UResourceBundle
extends ResourceBundle {
    private static Map<String, RootType> ROOT_CACHE = new ConcurrentHashMap<String, RootType>();
    public static final int NONE = -1;
    public static final int STRING = 0;
    public static final int BINARY = 1;
    public static final int TABLE = 2;
    public static final int INT = 7;
    public static final int ARRAY = 8;
    public static final int INT_VECTOR = 14;

    public static UResourceBundle getBundleInstance(String string, String string2) {
        return UResourceBundle.getBundleInstance(string, string2, ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    public static UResourceBundle getBundleInstance(String string, String string2, ClassLoader classLoader) {
        return UResourceBundle.getBundleInstance(string, string2, classLoader, false);
    }

    protected static UResourceBundle getBundleInstance(String string, String string2, ClassLoader classLoader, boolean bl) {
        return UResourceBundle.instantiateBundle(string, string2, classLoader, bl);
    }

    public static UResourceBundle getBundleInstance(ULocale uLocale) {
        if (uLocale == null) {
            uLocale = ULocale.getDefault();
        }
        return UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    public static UResourceBundle getBundleInstance(String string) {
        if (string == null) {
            string = "com/ibm/icu/impl/data/icudt66b";
        }
        ULocale uLocale = ULocale.getDefault();
        return UResourceBundle.getBundleInstance(string, uLocale.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    public static UResourceBundle getBundleInstance(String string, Locale locale) {
        if (string == null) {
            string = "com/ibm/icu/impl/data/icudt66b";
        }
        ULocale uLocale = locale == null ? ULocale.getDefault() : ULocale.forLocale(locale);
        return UResourceBundle.getBundleInstance(string, uLocale.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    public static UResourceBundle getBundleInstance(String string, ULocale uLocale) {
        if (string == null) {
            string = "com/ibm/icu/impl/data/icudt66b";
        }
        if (uLocale == null) {
            uLocale = ULocale.getDefault();
        }
        return UResourceBundle.getBundleInstance(string, uLocale.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    public static UResourceBundle getBundleInstance(String string, Locale locale, ClassLoader classLoader) {
        if (string == null) {
            string = "com/ibm/icu/impl/data/icudt66b";
        }
        ULocale uLocale = locale == null ? ULocale.getDefault() : ULocale.forLocale(locale);
        return UResourceBundle.getBundleInstance(string, uLocale.getBaseName(), classLoader, false);
    }

    public static UResourceBundle getBundleInstance(String string, ULocale uLocale, ClassLoader classLoader) {
        if (string == null) {
            string = "com/ibm/icu/impl/data/icudt66b";
        }
        if (uLocale == null) {
            uLocale = ULocale.getDefault();
        }
        return UResourceBundle.getBundleInstance(string, uLocale.getBaseName(), classLoader, false);
    }

    public abstract ULocale getULocale();

    protected abstract String getLocaleID();

    protected abstract String getBaseName();

    protected abstract UResourceBundle getParent();

    @Override
    public Locale getLocale() {
        return this.getULocale().toLocale();
    }

    private static RootType getRootType(String string, ClassLoader classLoader) {
        RootType rootType = ROOT_CACHE.get(string);
        if (rootType == null) {
            String string2 = string.indexOf(46) == -1 ? "root" : "";
            try {
                ICUResourceBundle.getBundleInstance(string, string2, classLoader, true);
                rootType = RootType.ICU;
            } catch (MissingResourceException missingResourceException) {
                try {
                    ResourceBundleWrapper.getBundleInstance(string, string2, classLoader, true);
                    rootType = RootType.JAVA;
                } catch (MissingResourceException missingResourceException2) {
                    rootType = RootType.MISSING;
                }
            }
            ROOT_CACHE.put(string, rootType);
        }
        return rootType;
    }

    private static void setRootType(String string, RootType rootType) {
        ROOT_CACHE.put(string, rootType);
    }

    protected static UResourceBundle instantiateBundle(String string, String string2, ClassLoader classLoader, boolean bl) {
        UResourceBundle uResourceBundle;
        RootType rootType = UResourceBundle.getRootType(string, classLoader);
        switch (1.$SwitchMap$com$ibm$icu$util$UResourceBundle$RootType[rootType.ordinal()]) {
            case 1: {
                return ICUResourceBundle.getBundleInstance(string, string2, classLoader, bl);
            }
            case 2: {
                return ResourceBundleWrapper.getBundleInstance(string, string2, classLoader, bl);
            }
        }
        try {
            uResourceBundle = ICUResourceBundle.getBundleInstance(string, string2, classLoader, bl);
            UResourceBundle.setRootType(string, RootType.ICU);
        } catch (MissingResourceException missingResourceException) {
            uResourceBundle = ResourceBundleWrapper.getBundleInstance(string, string2, classLoader, bl);
            UResourceBundle.setRootType(string, RootType.JAVA);
        }
        return uResourceBundle;
    }

    public ByteBuffer getBinary() {
        throw new UResourceTypeMismatchException("");
    }

    public String getString() {
        throw new UResourceTypeMismatchException("");
    }

    public String[] getStringArray() {
        throw new UResourceTypeMismatchException("");
    }

    public byte[] getBinary(byte[] byArray) {
        throw new UResourceTypeMismatchException("");
    }

    public int[] getIntVector() {
        throw new UResourceTypeMismatchException("");
    }

    public int getInt() {
        throw new UResourceTypeMismatchException("");
    }

    public int getUInt() {
        throw new UResourceTypeMismatchException("");
    }

    public UResourceBundle get(String string) {
        UResourceBundle uResourceBundle = this.findTopLevel(string);
        if (uResourceBundle == null) {
            String string2 = ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID());
            throw new MissingResourceException("Can't find resource for bundle " + string2 + ", key " + string, this.getClass().getName(), string);
        }
        return uResourceBundle;
    }

    @Deprecated
    protected UResourceBundle findTopLevel(String string) {
        for (UResourceBundle uResourceBundle = this; uResourceBundle != null; uResourceBundle = uResourceBundle.getParent()) {
            UResourceBundle uResourceBundle2 = uResourceBundle.handleGet(string, null, this);
            if (uResourceBundle2 == null) continue;
            return uResourceBundle2;
        }
        return null;
    }

    public String getString(int n) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)this.get(n);
        if (iCUResourceBundle.getType() == 0) {
            return iCUResourceBundle.getString();
        }
        throw new UResourceTypeMismatchException("");
    }

    public UResourceBundle get(int n) {
        UResourceBundle uResourceBundle = this.handleGet(n, null, this);
        if (uResourceBundle == null) {
            uResourceBundle = this.getParent();
            if (uResourceBundle != null) {
                uResourceBundle = uResourceBundle.get(n);
            }
            if (uResourceBundle == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getKey(), this.getClass().getName(), this.getKey());
            }
        }
        return uResourceBundle;
    }

    @Deprecated
    protected UResourceBundle findTopLevel(int n) {
        for (UResourceBundle uResourceBundle = this; uResourceBundle != null; uResourceBundle = uResourceBundle.getParent()) {
            UResourceBundle uResourceBundle2 = uResourceBundle.handleGet(n, null, this);
            if (uResourceBundle2 == null) continue;
            return uResourceBundle2;
        }
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(this.keySet());
    }

    @Override
    @Deprecated
    public Set<String> keySet() {
        Set<String> set = null;
        ICUResourceBundle iCUResourceBundle = null;
        if (this.isTopLevelResource() && this instanceof ICUResourceBundle) {
            iCUResourceBundle = (ICUResourceBundle)this;
            set = iCUResourceBundle.getTopLevelKeySet();
        }
        if (set == null) {
            if (this.isTopLevelResource()) {
                TreeSet<Object> treeSet;
                if (this.parent == null) {
                    treeSet = new TreeSet<String>();
                } else if (this.parent instanceof UResourceBundle) {
                    treeSet = new TreeSet<String>(((UResourceBundle)this.parent).keySet());
                } else {
                    treeSet = new TreeSet();
                    Enumeration<String> enumeration = this.parent.getKeys();
                    while (enumeration.hasMoreElements()) {
                        treeSet.add(enumeration.nextElement());
                    }
                }
                treeSet.addAll(this.handleKeySet());
                set = Collections.unmodifiableSet(treeSet);
                if (iCUResourceBundle != null) {
                    iCUResourceBundle.setTopLevelKeySet(set);
                }
            } else {
                return this.handleKeySet();
            }
        }
        return set;
    }

    @Override
    @Deprecated
    protected Set<String> handleKeySet() {
        return Collections.emptySet();
    }

    public int getSize() {
        return 0;
    }

    public int getType() {
        return 1;
    }

    public VersionInfo getVersion() {
        return null;
    }

    public UResourceBundleIterator getIterator() {
        return new UResourceBundleIterator(this);
    }

    public String getKey() {
        return null;
    }

    protected UResourceBundle handleGet(String string, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
        return null;
    }

    protected UResourceBundle handleGet(int n, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
        return null;
    }

    protected String[] handleGetStringArray() {
        return null;
    }

    protected Enumeration<String> handleGetKeys() {
        return null;
    }

    @Override
    protected Object handleGetObject(String string) {
        return this.handleGetObjectImpl(string, this);
    }

    private Object handleGetObjectImpl(String string, UResourceBundle uResourceBundle) {
        Object object = this.resolveObject(string, uResourceBundle);
        if (object == null) {
            UResourceBundle uResourceBundle2 = this.getParent();
            if (uResourceBundle2 != null) {
                object = uResourceBundle2.handleGetObjectImpl(string, uResourceBundle);
            }
            if (object == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + string, this.getClass().getName(), string);
            }
        }
        return object;
    }

    private Object resolveObject(String string, UResourceBundle uResourceBundle) {
        if (this.getType() == 0) {
            return this.getString();
        }
        UResourceBundle uResourceBundle2 = this.handleGet(string, null, uResourceBundle);
        if (uResourceBundle2 != null) {
            if (uResourceBundle2.getType() == 0) {
                return uResourceBundle2.getString();
            }
            try {
                if (uResourceBundle2.getType() == 8) {
                    return uResourceBundle2.handleGetStringArray();
                }
            } catch (UResourceTypeMismatchException uResourceTypeMismatchException) {
                return uResourceBundle2;
            }
        }
        return uResourceBundle2;
    }

    @Deprecated
    protected boolean isTopLevelResource() {
        return false;
    }

    private static enum RootType {
        MISSING,
        ICU,
        JAVA;

    }
}

