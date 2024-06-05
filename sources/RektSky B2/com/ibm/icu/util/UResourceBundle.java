package com.ibm.icu.util;

import java.nio.*;
import com.ibm.icu.impl.*;
import java.util.*;
import java.util.concurrent.*;

public abstract class UResourceBundle extends ResourceBundle
{
    private static Map<String, RootType> ROOT_CACHE;
    public static final int NONE = -1;
    public static final int STRING = 0;
    public static final int BINARY = 1;
    public static final int TABLE = 2;
    public static final int INT = 7;
    public static final int ARRAY = 8;
    public static final int INT_VECTOR = 14;
    
    public static UResourceBundle getBundleInstance(final String baseName, final String localeName) {
        return getBundleInstance(baseName, localeName, ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(final String baseName, final String localeName, final ClassLoader root) {
        return getBundleInstance(baseName, localeName, root, false);
    }
    
    protected static UResourceBundle getBundleInstance(final String baseName, final String localeName, final ClassLoader root, final boolean disableFallback) {
        return instantiateBundle(baseName, localeName, root, disableFallback);
    }
    
    public static UResourceBundle getBundleInstance(ULocale locale) {
        if (locale == null) {
            locale = ULocale.getDefault();
        }
        return getBundleInstance("com/ibm/icu/impl/data/icudt62b", locale.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(String baseName) {
        if (baseName == null) {
            baseName = "com/ibm/icu/impl/data/icudt62b";
        }
        final ULocale uloc = ULocale.getDefault();
        return getBundleInstance(baseName, uloc.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(String baseName, final Locale locale) {
        if (baseName == null) {
            baseName = "com/ibm/icu/impl/data/icudt62b";
        }
        final ULocale uloc = (locale == null) ? ULocale.getDefault() : ULocale.forLocale(locale);
        return getBundleInstance(baseName, uloc.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(String baseName, ULocale locale) {
        if (baseName == null) {
            baseName = "com/ibm/icu/impl/data/icudt62b";
        }
        if (locale == null) {
            locale = ULocale.getDefault();
        }
        return getBundleInstance(baseName, locale.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(String baseName, final Locale locale, final ClassLoader loader) {
        if (baseName == null) {
            baseName = "com/ibm/icu/impl/data/icudt62b";
        }
        final ULocale uloc = (locale == null) ? ULocale.getDefault() : ULocale.forLocale(locale);
        return getBundleInstance(baseName, uloc.getBaseName(), loader, false);
    }
    
    public static UResourceBundle getBundleInstance(String baseName, ULocale locale, final ClassLoader loader) {
        if (baseName == null) {
            baseName = "com/ibm/icu/impl/data/icudt62b";
        }
        if (locale == null) {
            locale = ULocale.getDefault();
        }
        return getBundleInstance(baseName, locale.getBaseName(), loader, false);
    }
    
    public abstract ULocale getULocale();
    
    protected abstract String getLocaleID();
    
    protected abstract String getBaseName();
    
    protected abstract UResourceBundle getParent();
    
    @Override
    public Locale getLocale() {
        return this.getULocale().toLocale();
    }
    
    private static RootType getRootType(final String baseName, final ClassLoader root) {
        RootType rootType = UResourceBundle.ROOT_CACHE.get(baseName);
        if (rootType == null) {
            final String rootLocale = (baseName.indexOf(46) == -1) ? "root" : "";
            try {
                ICUResourceBundle.getBundleInstance(baseName, rootLocale, root, true);
                rootType = RootType.ICU;
            }
            catch (MissingResourceException ex) {
                try {
                    ResourceBundleWrapper.getBundleInstance(baseName, rootLocale, root, true);
                    rootType = RootType.JAVA;
                }
                catch (MissingResourceException e) {
                    rootType = RootType.MISSING;
                }
            }
            UResourceBundle.ROOT_CACHE.put(baseName, rootType);
        }
        return rootType;
    }
    
    private static void setRootType(final String baseName, final RootType rootType) {
        UResourceBundle.ROOT_CACHE.put(baseName, rootType);
    }
    
    protected static UResourceBundle instantiateBundle(final String baseName, final String localeName, final ClassLoader root, final boolean disableFallback) {
        final RootType rootType = getRootType(baseName, root);
        switch (rootType) {
            case ICU: {
                return ICUResourceBundle.getBundleInstance(baseName, localeName, root, disableFallback);
            }
            case JAVA: {
                return ResourceBundleWrapper.getBundleInstance(baseName, localeName, root, disableFallback);
            }
            default: {
                UResourceBundle b;
                try {
                    b = ICUResourceBundle.getBundleInstance(baseName, localeName, root, disableFallback);
                    setRootType(baseName, RootType.ICU);
                }
                catch (MissingResourceException ex) {
                    b = ResourceBundleWrapper.getBundleInstance(baseName, localeName, root, disableFallback);
                    setRootType(baseName, RootType.JAVA);
                }
                return b;
            }
        }
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
    
    public byte[] getBinary(final byte[] ba) {
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
    
    public UResourceBundle get(final String aKey) {
        final UResourceBundle obj = this.findTopLevel(aKey);
        if (obj == null) {
            final String fullName = ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID());
            throw new MissingResourceException("Can't find resource for bundle " + fullName + ", key " + aKey, this.getClass().getName(), aKey);
        }
        return obj;
    }
    
    @Deprecated
    protected UResourceBundle findTopLevel(final String aKey) {
        for (UResourceBundle res = this; res != null; res = res.getParent()) {
            final UResourceBundle obj = res.handleGet(aKey, null, this);
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }
    
    public String getString(final int index) {
        final ICUResourceBundle temp = (ICUResourceBundle)this.get(index);
        if (temp.getType() == 0) {
            return temp.getString();
        }
        throw new UResourceTypeMismatchException("");
    }
    
    public UResourceBundle get(final int index) {
        UResourceBundle obj = this.handleGet(index, null, this);
        if (obj == null) {
            obj = this.getParent();
            if (obj != null) {
                obj = obj.get(index);
            }
            if (obj == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getKey(), this.getClass().getName(), this.getKey());
            }
        }
        return obj;
    }
    
    @Deprecated
    protected UResourceBundle findTopLevel(final int index) {
        for (UResourceBundle res = this; res != null; res = res.getParent()) {
            final UResourceBundle obj = res.handleGet(index, null, this);
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }
    
    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(this.keySet());
    }
    
    @Deprecated
    @Override
    public Set<String> keySet() {
        Set<String> keys = null;
        ICUResourceBundle icurb = null;
        if (this.isTopLevelResource() && this instanceof ICUResourceBundle) {
            icurb = (ICUResourceBundle)this;
            keys = icurb.getTopLevelKeySet();
        }
        if (keys == null) {
            if (!this.isTopLevelResource()) {
                return this.handleKeySet();
            }
            TreeSet<String> newKeySet;
            if (this.parent == null) {
                newKeySet = new TreeSet<String>();
            }
            else if (this.parent instanceof UResourceBundle) {
                newKeySet = new TreeSet<String>(((UResourceBundle)this.parent).keySet());
            }
            else {
                newKeySet = new TreeSet<String>();
                final Enumeration<String> parentKeys = this.parent.getKeys();
                while (parentKeys.hasMoreElements()) {
                    newKeySet.add(parentKeys.nextElement());
                }
            }
            newKeySet.addAll(this.handleKeySet());
            keys = Collections.unmodifiableSet((Set<? extends String>)newKeySet);
            if (icurb != null) {
                icurb.setTopLevelKeySet(keys);
            }
        }
        return keys;
    }
    
    @Deprecated
    @Override
    protected Set<String> handleKeySet() {
        return Collections.emptySet();
    }
    
    public int getSize() {
        return 1;
    }
    
    public int getType() {
        return -1;
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
    
    protected UResourceBundle handleGet(final String aKey, final HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
        return null;
    }
    
    protected UResourceBundle handleGet(final int index, final HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
        return null;
    }
    
    protected String[] handleGetStringArray() {
        return null;
    }
    
    protected Enumeration<String> handleGetKeys() {
        return null;
    }
    
    @Override
    protected Object handleGetObject(final String aKey) {
        return this.handleGetObjectImpl(aKey, this);
    }
    
    private Object handleGetObjectImpl(final String aKey, final UResourceBundle requested) {
        Object obj = this.resolveObject(aKey, requested);
        if (obj == null) {
            final UResourceBundle parentBundle = this.getParent();
            if (parentBundle != null) {
                obj = parentBundle.handleGetObjectImpl(aKey, requested);
            }
            if (obj == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + aKey, this.getClass().getName(), aKey);
            }
        }
        return obj;
    }
    
    private Object resolveObject(final String aKey, final UResourceBundle requested) {
        if (this.getType() == 0) {
            return this.getString();
        }
        final UResourceBundle obj = this.handleGet(aKey, null, requested);
        if (obj != null) {
            if (obj.getType() == 0) {
                return obj.getString();
            }
            try {
                if (obj.getType() == 8) {
                    return obj.handleGetStringArray();
                }
            }
            catch (UResourceTypeMismatchException ex) {
                return obj;
            }
        }
        return obj;
    }
    
    @Deprecated
    protected boolean isTopLevelResource() {
        return true;
    }
    
    static {
        UResourceBundle.ROOT_CACHE = new ConcurrentHashMap<String, RootType>();
    }
    
    private enum RootType
    {
        MISSING, 
        ICU, 
        JAVA;
    }
}
