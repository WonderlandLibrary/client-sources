package com.ibm.icu.impl;

import java.net.*;
import java.security.*;
import java.io.*;
import com.ibm.icu.util.*;
import java.util.*;

public class ICUResourceBundle extends UResourceBundle
{
    public static final String NO_INHERITANCE_MARKER = "\u2205\u2205\u2205";
    public static final ClassLoader ICU_DATA_CLASS_LOADER;
    protected static final String INSTALLED_LOCALES = "InstalledLocales";
    WholeBundle wholeBundle;
    private ICUResourceBundle container;
    private static CacheBase<String, ICUResourceBundle, Loader> BUNDLE_CACHE;
    private static final String ICU_RESOURCE_INDEX = "res_index";
    private static final String DEFAULT_TAG = "default";
    private static final String FULL_LOCALE_NAMES_LIST = "fullLocaleNames.lst";
    private static final boolean DEBUG;
    private static CacheBase<String, AvailEntry, ClassLoader> GET_AVAILABLE_CACHE;
    protected String key;
    public static final int RES_BOGUS = -1;
    public static final int ALIAS = 3;
    public static final int TABLE32 = 4;
    public static final int TABLE16 = 5;
    public static final int STRING_V2 = 6;
    public static final int ARRAY16 = 9;
    private static final char RES_PATH_SEP_CHAR = '/';
    private static final String RES_PATH_SEP_STR = "/";
    private static final String ICUDATA = "ICUDATA";
    private static final char HYPHEN = '-';
    private static final String LOCALE = "LOCALE";
    
    public static final ULocale getFunctionalEquivalent(final String baseName, final ClassLoader loader, final String resName, final String keyword, final ULocale locID, final boolean[] isAvailable, final boolean omitDefault) {
        String kwVal = locID.getKeywordValue(keyword);
        final String baseLoc = locID.getBaseName();
        String defStr = null;
        ULocale parent = new ULocale(baseLoc);
        ULocale defLoc = null;
        boolean lookForDefault = false;
        ULocale fullBase = null;
        int defDepth = 0;
        int resDepth = 0;
        if (kwVal == null || kwVal.length() == 0 || kwVal.equals("default")) {
            kwVal = "";
            lookForDefault = true;
        }
        ICUResourceBundle r = null;
        r = (ICUResourceBundle)UResourceBundle.getBundleInstance(baseName, parent);
        if (isAvailable != null) {
            isAvailable[0] = false;
            final ULocale[] availableULocales = getAvailEntry(baseName, loader).getULocaleList();
            for (int i = 0; i < availableULocales.length; ++i) {
                if (parent.equals(availableULocales[i])) {
                    isAvailable[0] = true;
                    break;
                }
            }
        }
        do {
            try {
                final ICUResourceBundle irb = (ICUResourceBundle)r.get(resName);
                defStr = irb.getString("default");
                if (lookForDefault) {
                    kwVal = defStr;
                    lookForDefault = false;
                }
                defLoc = r.getULocale();
            }
            catch (MissingResourceException ex) {}
            if (defLoc == null) {
                r = r.getParent();
                ++defDepth;
            }
        } while (r != null && defLoc == null);
        parent = new ULocale(baseLoc);
        r = (ICUResourceBundle)UResourceBundle.getBundleInstance(baseName, parent);
        do {
            try {
                final ICUResourceBundle irb = (ICUResourceBundle)r.get(resName);
                irb.get(kwVal);
                fullBase = irb.getULocale();
                if (fullBase != null && resDepth > defDepth) {
                    defStr = irb.getString("default");
                    defLoc = r.getULocale();
                    defDepth = resDepth;
                }
            }
            catch (MissingResourceException ex2) {}
            if (fullBase == null) {
                r = r.getParent();
                ++resDepth;
            }
        } while (r != null && fullBase == null);
        if (fullBase == null && defStr != null && !defStr.equals(kwVal)) {
            kwVal = defStr;
            parent = new ULocale(baseLoc);
            r = (ICUResourceBundle)UResourceBundle.getBundleInstance(baseName, parent);
            resDepth = 0;
            do {
                try {
                    final ICUResourceBundle irb = (ICUResourceBundle)r.get(resName);
                    final ICUResourceBundle urb = (ICUResourceBundle)irb.get(kwVal);
                    fullBase = r.getULocale();
                    if (!fullBase.getBaseName().equals(urb.getULocale().getBaseName())) {
                        fullBase = null;
                    }
                    if (fullBase != null && resDepth > defDepth) {
                        defStr = irb.getString("default");
                        defLoc = r.getULocale();
                        defDepth = resDepth;
                    }
                }
                catch (MissingResourceException ex3) {}
                if (fullBase == null) {
                    r = r.getParent();
                    ++resDepth;
                }
            } while (r != null && fullBase == null);
        }
        if (fullBase == null) {
            throw new MissingResourceException("Could not find locale containing requested or default keyword.", baseName, keyword + "=" + kwVal);
        }
        if (omitDefault && defStr.equals(kwVal) && resDepth <= defDepth) {
            return fullBase;
        }
        return new ULocale(fullBase.getBaseName() + "@" + keyword + "=" + kwVal);
    }
    
    public static final String[] getKeywordValues(final String baseName, final String keyword) {
        final Set<String> keywords = new HashSet<String>();
        final ULocale[] locales = getAvailEntry(baseName, ICUResourceBundle.ICU_DATA_CLASS_LOADER).getULocaleList();
        for (int i = 0; i < locales.length; ++i) {
            try {
                final UResourceBundle b = UResourceBundle.getBundleInstance(baseName, locales[i]);
                final ICUResourceBundle irb = (ICUResourceBundle)b.getObject(keyword);
                final Enumeration<String> e = irb.getKeys();
                while (e.hasMoreElements()) {
                    final String s = e.nextElement();
                    if (!"default".equals(s) && !s.startsWith("private-")) {
                        keywords.add(s);
                    }
                }
            }
            catch (Throwable t) {}
        }
        return keywords.toArray(new String[0]);
    }
    
    public ICUResourceBundle getWithFallback(final String path) throws MissingResourceException {
        final ICUResourceBundle actualBundle = this;
        final ICUResourceBundle result = findResourceWithFallback(path, actualBundle, null);
        if (result == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), path, this.getKey());
        }
        if (result.getType() == 0 && result.getString().equals("\u2205\u2205\u2205")) {
            throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", path, this.getKey());
        }
        return result;
    }
    
    public ICUResourceBundle at(final int index) {
        return (ICUResourceBundle)this.handleGet(index, null, this);
    }
    
    public ICUResourceBundle at(final String key) {
        if (this instanceof ICUResourceBundleImpl.ResourceTable) {
            return (ICUResourceBundle)this.handleGet(key, null, this);
        }
        return null;
    }
    
    public ICUResourceBundle findTopLevel(final int index) {
        return (ICUResourceBundle)super.findTopLevel(index);
    }
    
    public ICUResourceBundle findTopLevel(final String aKey) {
        return (ICUResourceBundle)super.findTopLevel(aKey);
    }
    
    public ICUResourceBundle findWithFallback(final String path) {
        return findResourceWithFallback(path, this, null);
    }
    
    public String findStringWithFallback(final String path) {
        return findStringWithFallback(path, this, null);
    }
    
    public String getStringWithFallback(final String path) throws MissingResourceException {
        final ICUResourceBundle actualBundle = this;
        final String result = findStringWithFallback(path, actualBundle, null);
        if (result == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), path, this.getKey());
        }
        if (result.equals("\u2205\u2205\u2205")) {
            throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", path, this.getKey());
        }
        return result;
    }
    
    public void getAllItemsWithFallbackNoFail(final String path, final UResource.Sink sink) {
        try {
            this.getAllItemsWithFallback(path, sink);
        }
        catch (MissingResourceException ex) {}
    }
    
    public void getAllItemsWithFallback(final String path, final UResource.Sink sink) throws MissingResourceException {
        final int numPathKeys = countPathKeys(path);
        ICUResourceBundle rb;
        if (numPathKeys == 0) {
            rb = this;
        }
        else {
            final int depth = this.getResDepth();
            final String[] pathKeys = new String[depth + numPathKeys];
            getResPathKeys(path, numPathKeys, pathKeys, depth);
            rb = findResourceWithFallback(pathKeys, depth, this, null);
            if (rb == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), path, this.getKey());
            }
        }
        final UResource.Key key = new UResource.Key();
        final ICUResourceBundleReader.ReaderValue readerValue = new ICUResourceBundleReader.ReaderValue();
        rb.getAllItemsWithFallback(key, readerValue, sink);
    }
    
    private void getAllItemsWithFallback(final UResource.Key key, final ICUResourceBundleReader.ReaderValue readerValue, final UResource.Sink sink) {
        final ICUResourceBundleImpl impl = (ICUResourceBundleImpl)this;
        readerValue.reader = impl.wholeBundle.reader;
        readerValue.res = impl.getResource();
        key.setString((this.key != null) ? this.key : "");
        sink.put(key, readerValue, this.parent == null);
        if (this.parent != null) {
            final ICUResourceBundle parentBundle = (ICUResourceBundle)this.parent;
            final int depth = this.getResDepth();
            ICUResourceBundle rb;
            if (depth == 0) {
                rb = parentBundle;
            }
            else {
                final String[] pathKeys = new String[depth];
                this.getResPathKeys(pathKeys, depth);
                rb = findResourceWithFallback(pathKeys, 0, parentBundle, null);
            }
            if (rb != null) {
                rb.getAllItemsWithFallback(key, readerValue, sink);
            }
        }
    }
    
    public static Set<String> getAvailableLocaleNameSet(final String bundlePrefix, final ClassLoader loader) {
        return getAvailEntry(bundlePrefix, loader).getLocaleNameSet();
    }
    
    public static Set<String> getFullLocaleNameSet() {
        return getFullLocaleNameSet("com/ibm/icu/impl/data/icudt62b", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
    }
    
    public static Set<String> getFullLocaleNameSet(final String bundlePrefix, final ClassLoader loader) {
        return getAvailEntry(bundlePrefix, loader).getFullLocaleNameSet();
    }
    
    public static Set<String> getAvailableLocaleNameSet() {
        return getAvailableLocaleNameSet("com/ibm/icu/impl/data/icudt62b", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
    }
    
    public static final ULocale[] getAvailableULocales(final String baseName, final ClassLoader loader) {
        return getAvailEntry(baseName, loader).getULocaleList();
    }
    
    public static final ULocale[] getAvailableULocales() {
        return getAvailableULocales("com/ibm/icu/impl/data/icudt62b", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
    }
    
    public static final Locale[] getAvailableLocales(final String baseName, final ClassLoader loader) {
        return getAvailEntry(baseName, loader).getLocaleList();
    }
    
    public static final Locale[] getAvailableLocales() {
        return getAvailEntry("com/ibm/icu/impl/data/icudt62b", ICUResourceBundle.ICU_DATA_CLASS_LOADER).getLocaleList();
    }
    
    public static final Locale[] getLocaleList(final ULocale[] ulocales) {
        final ArrayList<Locale> list = new ArrayList<Locale>(ulocales.length);
        final HashSet<Locale> uniqueSet = new HashSet<Locale>();
        for (int i = 0; i < ulocales.length; ++i) {
            final Locale loc = ulocales[i].toLocale();
            if (!uniqueSet.contains(loc)) {
                list.add(loc);
                uniqueSet.add(loc);
            }
        }
        return list.toArray(new Locale[list.size()]);
    }
    
    @Override
    public Locale getLocale() {
        return this.getULocale().toLocale();
    }
    
    private static final ULocale[] createULocaleList(final String baseName, final ClassLoader root) {
        ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.instantiateBundle(baseName, "res_index", root, true);
        bundle = (ICUResourceBundle)bundle.get("InstalledLocales");
        final int length = bundle.getSize();
        int i = 0;
        final ULocale[] locales = new ULocale[length];
        final UResourceBundleIterator iter = bundle.getIterator();
        iter.reset();
        while (iter.hasNext()) {
            final String locstr = iter.next().getKey();
            if (locstr.equals("root")) {
                locales[i++] = ULocale.ROOT;
            }
            else {
                locales[i++] = new ULocale(locstr);
            }
        }
        bundle = null;
        return locales;
    }
    
    private static final void addLocaleIDsFromIndexBundle(final String baseName, final ClassLoader root, final Set<String> locales) {
        ICUResourceBundle bundle;
        try {
            bundle = (ICUResourceBundle)UResourceBundle.instantiateBundle(baseName, "res_index", root, true);
            bundle = (ICUResourceBundle)bundle.get("InstalledLocales");
        }
        catch (MissingResourceException e) {
            if (ICUResourceBundle.DEBUG) {
                System.out.println("couldn't find " + baseName + '/' + "res_index" + ".res");
                Thread.dumpStack();
            }
            return;
        }
        final UResourceBundleIterator iter = bundle.getIterator();
        iter.reset();
        while (iter.hasNext()) {
            final String locstr = iter.next().getKey();
            locales.add(locstr);
        }
    }
    
    private static final void addBundleBaseNamesFromClassLoader(final String bn, final ClassLoader root, final Set<String> names) {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                try {
                    final Enumeration<URL> urls = root.getResources(bn);
                    if (urls == null) {
                        return null;
                    }
                    final URLHandler.URLVisitor v = new URLHandler.URLVisitor() {
                        @Override
                        public void visit(final String s) {
                            if (s.endsWith(".res")) {
                                final String locstr = s.substring(0, s.length() - 4);
                                names.add(locstr);
                            }
                        }
                    };
                    while (urls.hasMoreElements()) {
                        final URL url = urls.nextElement();
                        final URLHandler handler = URLHandler.get(url);
                        if (handler != null) {
                            handler.guide(v, false);
                        }
                        else {
                            if (!ICUResourceBundle.DEBUG) {
                                continue;
                            }
                            System.out.println("handler for " + url + " is null");
                        }
                    }
                }
                catch (IOException e) {
                    if (ICUResourceBundle.DEBUG) {
                        System.out.println("ouch: " + e.getMessage());
                    }
                }
                return null;
            }
        });
    }
    
    private static void addLocaleIDsFromListFile(final String bn, final ClassLoader root, final Set<String> locales) {
        try {
            final InputStream s = root.getResourceAsStream(bn + "fullLocaleNames.lst");
            if (s != null) {
                final BufferedReader br = new BufferedReader(new InputStreamReader(s, "ASCII"));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.length() != 0 && !line.startsWith("#")) {
                            locales.add(line);
                        }
                    }
                }
                finally {
                    br.close();
                }
            }
        }
        catch (IOException ex) {}
    }
    
    private static Set<String> createFullLocaleNameSet(final String baseName, final ClassLoader loader) {
        final String bn = baseName.endsWith("/") ? baseName : (baseName + "/");
        final Set<String> set = new HashSet<String>();
        final String skipScan = ICUConfig.get("com.ibm.icu.impl.ICUResourceBundle.skipRuntimeLocaleResourceScan", "false");
        if (!skipScan.equalsIgnoreCase("true")) {
            addBundleBaseNamesFromClassLoader(bn, loader, set);
            if (baseName.startsWith("com/ibm/icu/impl/data/icudt62b")) {
                String folder;
                if (baseName.length() == "com/ibm/icu/impl/data/icudt62b".length()) {
                    folder = "";
                }
                else if (baseName.charAt("com/ibm/icu/impl/data/icudt62b".length()) == '/') {
                    folder = baseName.substring("com/ibm/icu/impl/data/icudt62b".length() + 1);
                }
                else {
                    folder = null;
                }
                if (folder != null) {
                    ICUBinary.addBaseNamesInFileFolder(folder, ".res", set);
                }
            }
            set.remove("res_index");
            final Iterator<String> iter = set.iterator();
            while (iter.hasNext()) {
                final String name = iter.next();
                if ((name.length() == 1 || name.length() > 3) && name.indexOf(95) < 0) {
                    iter.remove();
                }
            }
        }
        if (set.isEmpty()) {
            if (ICUResourceBundle.DEBUG) {
                System.out.println("unable to enumerate data files in " + baseName);
            }
            addLocaleIDsFromListFile(bn, loader, set);
        }
        if (set.isEmpty()) {
            addLocaleIDsFromIndexBundle(baseName, loader, set);
        }
        set.remove("root");
        set.add(ULocale.ROOT.toString());
        return Collections.unmodifiableSet((Set<? extends String>)set);
    }
    
    private static Set<String> createLocaleNameSet(final String baseName, final ClassLoader loader) {
        final HashSet<String> set = new HashSet<String>();
        addLocaleIDsFromIndexBundle(baseName, loader, set);
        return Collections.unmodifiableSet((Set<? extends String>)set);
    }
    
    private static AvailEntry getAvailEntry(final String key, final ClassLoader loader) {
        return ICUResourceBundle.GET_AVAILABLE_CACHE.getInstance(key, loader);
    }
    
    private static final ICUResourceBundle findResourceWithFallback(final String path, final UResourceBundle actualBundle, final UResourceBundle requested) {
        if (path.length() == 0) {
            return null;
        }
        final ICUResourceBundle base = (ICUResourceBundle)actualBundle;
        final int depth = base.getResDepth();
        final int numPathKeys = countPathKeys(path);
        assert numPathKeys > 0;
        final String[] keys = new String[depth + numPathKeys];
        getResPathKeys(path, numPathKeys, keys, depth);
        return findResourceWithFallback(keys, depth, base, requested);
    }
    
    private static final ICUResourceBundle findResourceWithFallback(String[] keys, int depth, ICUResourceBundle base, UResourceBundle requested) {
        if (requested == null) {
            requested = base;
        }
        while (true) {
            final String subKey = keys[depth++];
            final ICUResourceBundle sub = (ICUResourceBundle)base.handleGet(subKey, null, requested);
            if (sub == null) {
                --depth;
                final ICUResourceBundle nextBase = base.getParent();
                if (nextBase == null) {
                    return null;
                }
                final int baseDepth = base.getResDepth();
                if (depth != baseDepth) {
                    final String[] newKeys = new String[baseDepth + (keys.length - depth)];
                    System.arraycopy(keys, depth, newKeys, baseDepth, keys.length - depth);
                    keys = newKeys;
                }
                base.getResPathKeys(keys, baseDepth);
                base = nextBase;
                depth = 0;
            }
            else {
                if (depth == keys.length) {
                    return sub;
                }
                base = sub;
            }
        }
    }
    
    private static final String findStringWithFallback(final String path, final UResourceBundle actualBundle, UResourceBundle requested) {
        if (path.length() == 0) {
            return null;
        }
        if (!(actualBundle instanceof ICUResourceBundleImpl.ResourceContainer)) {
            return null;
        }
        if (requested == null) {
            requested = actualBundle;
        }
        ICUResourceBundle base = (ICUResourceBundle)actualBundle;
        ICUResourceBundleReader reader = base.wholeBundle.reader;
        int res = -1;
        int depth;
        int baseDepth = depth = base.getResDepth();
        final int numPathKeys = countPathKeys(path);
        assert numPathKeys > 0;
        String[] keys = new String[depth + numPathKeys];
        getResPathKeys(path, numPathKeys, keys, depth);
        while (true) {
            Label_0379: {
                ICUResourceBundleReader.Container readerContainer;
                if (res == -1) {
                    final int type = base.getType();
                    if (type != 2 && type != 8) {
                        break Label_0379;
                    }
                    readerContainer = ((ICUResourceBundleImpl.ResourceContainer)base).value;
                }
                else {
                    final int type = ICUResourceBundleReader.RES_GET_TYPE(res);
                    if (ICUResourceBundleReader.URES_IS_TABLE(type)) {
                        readerContainer = reader.getTable(res);
                    }
                    else {
                        if (!ICUResourceBundleReader.URES_IS_ARRAY(type)) {
                            res = -1;
                            break Label_0379;
                        }
                        readerContainer = reader.getArray(res);
                    }
                }
                final String subKey = keys[depth++];
                res = readerContainer.getResource(reader, subKey);
                if (res == -1) {
                    --depth;
                }
                else {
                    ICUResourceBundle sub;
                    if (ICUResourceBundleReader.RES_GET_TYPE(res) == 3) {
                        base.getResPathKeys(keys, baseDepth);
                        sub = getAliasedResource(base, keys, depth, subKey, res, null, requested);
                    }
                    else {
                        sub = null;
                    }
                    if (depth != keys.length) {
                        if (sub == null) {
                            continue;
                        }
                        base = sub;
                        reader = base.wholeBundle.reader;
                        res = -1;
                        baseDepth = base.getResDepth();
                        if (depth == baseDepth) {
                            continue;
                        }
                        final String[] newKeys = new String[baseDepth + (keys.length - depth)];
                        System.arraycopy(keys, depth, newKeys, baseDepth, keys.length - depth);
                        keys = newKeys;
                        depth = baseDepth;
                        continue;
                    }
                    if (sub != null) {
                        return sub.getString();
                    }
                    final String s = reader.getString(res);
                    if (s == null) {
                        throw new UResourceTypeMismatchException("");
                    }
                    return s;
                }
            }
            final ICUResourceBundle nextBase = base.getParent();
            if (nextBase == null) {
                return null;
            }
            base.getResPathKeys(keys, baseDepth);
            base = nextBase;
            reader = base.wholeBundle.reader;
            baseDepth = (depth = 0);
        }
    }
    
    private int getResDepth() {
        return (this.container == null) ? 0 : (this.container.getResDepth() + 1);
    }
    
    private void getResPathKeys(final String[] keys, int depth) {
        ICUResourceBundle b = this;
        while (depth > 0) {
            keys[--depth] = b.key;
            b = b.container;
            assert depth == 0 == (b.container == null);
        }
    }
    
    private static int countPathKeys(final String path) {
        if (path.isEmpty()) {
            return 0;
        }
        int num = 1;
        for (int i = 0; i < path.length(); ++i) {
            if (path.charAt(i) == '/') {
                ++num;
            }
        }
        return num;
    }
    
    private static void getResPathKeys(final String path, int num, final String[] keys, int start) {
        if (num == 0) {
            return;
        }
        if (num == 1) {
            keys[start] = path;
            return;
        }
        int i = 0;
        while (true) {
            final int j = path.indexOf(47, i);
            assert j >= i;
            keys[start++] = path.substring(i, j);
            if (num == 2) {
                assert path.indexOf(47, j + 1) < 0;
                keys[start] = path.substring(j + 1);
            }
            else {
                i = j + 1;
                --num;
            }
        }
    }
    
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ICUResourceBundle) {
            final ICUResourceBundle o = (ICUResourceBundle)other;
            if (this.getBaseName().equals(o.getBaseName()) && this.getLocaleID().equals(o.getLocaleID())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    public static ICUResourceBundle getBundleInstance(final String baseName, final String localeID, final ClassLoader root, final boolean disableFallback) {
        return getBundleInstance(baseName, localeID, root, disableFallback ? OpenType.DIRECT : OpenType.LOCALE_DEFAULT_ROOT);
    }
    
    public static ICUResourceBundle getBundleInstance(final String baseName, ULocale locale, final OpenType openType) {
        if (locale == null) {
            locale = ULocale.getDefault();
        }
        return getBundleInstance(baseName, locale.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, openType);
    }
    
    public static ICUResourceBundle getBundleInstance(String baseName, String localeID, final ClassLoader root, final OpenType openType) {
        if (baseName == null) {
            baseName = "com/ibm/icu/impl/data/icudt62b";
        }
        localeID = ULocale.getBaseName(localeID);
        ICUResourceBundle b;
        if (openType == OpenType.LOCALE_DEFAULT_ROOT) {
            b = instantiateBundle(baseName, localeID, ULocale.getDefault().getBaseName(), root, openType);
        }
        else {
            b = instantiateBundle(baseName, localeID, null, root, openType);
        }
        if (b == null) {
            throw new MissingResourceException("Could not find the bundle " + baseName + "/" + localeID + ".res", "", "");
        }
        return b;
    }
    
    private static boolean localeIDStartsWithLangSubtag(final String localeID, final String lang) {
        return localeID.startsWith(lang) && (localeID.length() == lang.length() || localeID.charAt(lang.length()) == '_');
    }
    
    private static ICUResourceBundle instantiateBundle(final String baseName, final String localeID, final String defaultID, final ClassLoader root, final OpenType openType) {
        assert localeID.indexOf(64) < 0;
        assert defaultID.indexOf(64) < 0;
        final String fullName = ICUResourceBundleReader.getFullName(baseName, localeID);
        final char openTypeChar = (char)(48 + openType.ordinal());
        final String cacheKey = (openType != OpenType.LOCALE_DEFAULT_ROOT) ? (fullName + '#' + openTypeChar) : (fullName + '#' + openTypeChar + '#' + defaultID);
        return ICUResourceBundle.BUNDLE_CACHE.getInstance(cacheKey, new Loader() {
            public ICUResourceBundle load() {
                if (ICUResourceBundle.DEBUG) {
                    System.out.println("Creating " + fullName);
                }
                final String rootLocale = (baseName.indexOf(46) == -1) ? "root" : "";
                String localeName = localeID.isEmpty() ? rootLocale : localeID;
                ICUResourceBundle b = ICUResourceBundle.createBundle(baseName, localeName, root);
                if (ICUResourceBundle.DEBUG) {
                    System.out.println("The bundle created is: " + b + " and openType=" + openType + " and bundle.getNoFallback=" + (b != null && b.getNoFallback()));
                }
                if (openType == OpenType.DIRECT || (b != null && b.getNoFallback())) {
                    return b;
                }
                if (b == null) {
                    final int i = localeName.lastIndexOf(95);
                    if (i != -1) {
                        final String temp = localeName.substring(0, i);
                        b = instantiateBundle(baseName, temp, defaultID, root, openType);
                    }
                    else if (openType == OpenType.LOCALE_DEFAULT_ROOT && !localeIDStartsWithLangSubtag(defaultID, localeName)) {
                        b = instantiateBundle(baseName, defaultID, defaultID, root, openType);
                    }
                    else if (openType != OpenType.LOCALE_ONLY && !rootLocale.isEmpty()) {
                        b = ICUResourceBundle.createBundle(baseName, rootLocale, root);
                    }
                }
                else {
                    UResourceBundle parent = null;
                    localeName = b.getLocaleID();
                    final int j = localeName.lastIndexOf(95);
                    final String parentLocaleName = ((ICUResourceBundleImpl.ResourceTable)b).findString("%%Parent");
                    if (parentLocaleName != null) {
                        parent = instantiateBundle(baseName, parentLocaleName, defaultID, root, openType);
                    }
                    else if (j != -1) {
                        parent = instantiateBundle(baseName, localeName.substring(0, j), defaultID, root, openType);
                    }
                    else if (!localeName.equals(rootLocale)) {
                        parent = instantiateBundle(baseName, rootLocale, defaultID, root, openType);
                    }
                    if (!b.equals(parent)) {
                        b.setParent(parent);
                    }
                }
                return b;
            }
        });
    }
    
    ICUResourceBundle get(final String aKey, final HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
        ICUResourceBundle obj = (ICUResourceBundle)this.handleGet(aKey, aliasesVisited, requested);
        if (obj == null) {
            obj = this.getParent();
            if (obj != null) {
                obj = obj.get(aKey, aliasesVisited, requested);
            }
            if (obj == null) {
                final String fullName = ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID());
                throw new MissingResourceException("Can't find resource for bundle " + fullName + ", key " + aKey, this.getClass().getName(), aKey);
            }
        }
        return obj;
    }
    
    public static ICUResourceBundle createBundle(final String baseName, final String localeID, final ClassLoader root) {
        final ICUResourceBundleReader reader = ICUResourceBundleReader.getReader(baseName, localeID, root);
        if (reader == null) {
            return null;
        }
        return getBundle(reader, baseName, localeID, root);
    }
    
    @Override
    protected String getLocaleID() {
        return this.wholeBundle.localeID;
    }
    
    @Override
    protected String getBaseName() {
        return this.wholeBundle.baseName;
    }
    
    @Override
    public ULocale getULocale() {
        return this.wholeBundle.ulocale;
    }
    
    public boolean isRoot() {
        return this.wholeBundle.localeID.isEmpty() || this.wholeBundle.localeID.equals("root");
    }
    
    public ICUResourceBundle getParent() {
        return (ICUResourceBundle)this.parent;
    }
    
    @Override
    protected void setParent(final ResourceBundle parent) {
        this.parent = parent;
    }
    
    @Override
    public String getKey() {
        return this.key;
    }
    
    private boolean getNoFallback() {
        return this.wholeBundle.reader.getNoFallback();
    }
    
    private static ICUResourceBundle getBundle(final ICUResourceBundleReader reader, final String baseName, final String localeID, final ClassLoader loader) {
        final int rootRes = reader.getRootResource();
        if (!ICUResourceBundleReader.URES_IS_TABLE(ICUResourceBundleReader.RES_GET_TYPE(rootRes))) {
            throw new IllegalStateException("Invalid format error");
        }
        final WholeBundle wb = new WholeBundle(baseName, localeID, loader, reader);
        final ICUResourceBundleImpl.ResourceTable rootTable = new ICUResourceBundleImpl.ResourceTable(wb, rootRes);
        final String aliasString = rootTable.findString("%%ALIAS");
        if (aliasString != null) {
            return (ICUResourceBundle)UResourceBundle.getBundleInstance(baseName, aliasString);
        }
        return rootTable;
    }
    
    protected ICUResourceBundle(final WholeBundle wholeBundle) {
        this.wholeBundle = wholeBundle;
    }
    
    protected ICUResourceBundle(final ICUResourceBundle container, final String key) {
        this.key = key;
        this.wholeBundle = container.wholeBundle;
        this.container = container;
        this.parent = container.parent;
    }
    
    protected static ICUResourceBundle getAliasedResource(final ICUResourceBundle base, String[] keys, int depth, final String key, final int _resource, HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
        final WholeBundle wholeBundle = base.wholeBundle;
        ClassLoader loaderToUse = wholeBundle.loader;
        String keyPath = null;
        final String rpath = wholeBundle.reader.getAlias(_resource);
        if (aliasesVisited == null) {
            aliasesVisited = new HashMap<String, String>();
        }
        if (aliasesVisited.get(rpath) != null) {
            throw new IllegalArgumentException("Circular references in the resource bundles");
        }
        aliasesVisited.put(rpath, "");
        String bundleName;
        String locale;
        if (rpath.indexOf(47) == 0) {
            final int i = rpath.indexOf(47, 1);
            final int j = rpath.indexOf(47, i + 1);
            bundleName = rpath.substring(1, i);
            if (j < 0) {
                locale = rpath.substring(i + 1);
            }
            else {
                locale = rpath.substring(i + 1, j);
                keyPath = rpath.substring(j + 1, rpath.length());
            }
            if (bundleName.equals("ICUDATA")) {
                bundleName = "com/ibm/icu/impl/data/icudt62b";
                loaderToUse = ICUResourceBundle.ICU_DATA_CLASS_LOADER;
            }
            else if (bundleName.indexOf("ICUDATA") > -1) {
                final int idx = bundleName.indexOf(45);
                if (idx > -1) {
                    bundleName = "com/ibm/icu/impl/data/icudt62b/" + bundleName.substring(idx + 1, bundleName.length());
                    loaderToUse = ICUResourceBundle.ICU_DATA_CLASS_LOADER;
                }
            }
        }
        else {
            final int i = rpath.indexOf(47);
            if (i != -1) {
                locale = rpath.substring(0, i);
                keyPath = rpath.substring(i + 1);
            }
            else {
                locale = rpath;
            }
            bundleName = wholeBundle.baseName;
        }
        ICUResourceBundle bundle = null;
        ICUResourceBundle sub = null;
        if (bundleName.equals("LOCALE")) {
            bundleName = wholeBundle.baseName;
            keyPath = rpath.substring("LOCALE".length() + 2, rpath.length());
            for (bundle = (ICUResourceBundle)requested; bundle.container != null; bundle = bundle.container) {}
            sub = findResourceWithFallback(keyPath, bundle, null);
        }
        else {
            bundle = getBundleInstance(bundleName, locale, loaderToUse, false);
            int numKeys;
            if (keyPath != null) {
                numKeys = countPathKeys(keyPath);
                if (numKeys > 0) {
                    keys = new String[numKeys];
                    getResPathKeys(keyPath, numKeys, keys, 0);
                }
            }
            else if (keys != null) {
                numKeys = depth;
            }
            else {
                depth = base.getResDepth();
                numKeys = depth + 1;
                keys = new String[numKeys];
                base.getResPathKeys(keys, depth);
                keys[depth] = key;
            }
            if (numKeys > 0) {
                sub = bundle;
                for (int k = 0; sub != null && k < numKeys; sub = sub.get(keys[k], aliasesVisited, requested), ++k) {}
            }
        }
        if (sub == null) {
            throw new MissingResourceException(wholeBundle.localeID, wholeBundle.baseName, key);
        }
        return sub;
    }
    
    @Deprecated
    public final Set<String> getTopLevelKeySet() {
        return this.wholeBundle.topLevelKeys;
    }
    
    @Deprecated
    public final void setTopLevelKeySet(final Set<String> keySet) {
        this.wholeBundle.topLevelKeys = keySet;
    }
    
    @Override
    protected Enumeration<String> handleGetKeys() {
        return Collections.enumeration(this.handleKeySet());
    }
    
    @Override
    protected boolean isTopLevelResource() {
        return this.container == null;
    }
    
    static {
        ICU_DATA_CLASS_LOADER = ClassLoaderUtil.getClassLoader(ICUData.class);
        ICUResourceBundle.BUNDLE_CACHE = new SoftCache<String, ICUResourceBundle, Loader>() {
            @Override
            protected ICUResourceBundle createInstance(final String unusedKey, final Loader loader) {
                return loader.load();
            }
        };
        DEBUG = ICUDebug.enabled("localedata");
        ICUResourceBundle.GET_AVAILABLE_CACHE = new SoftCache<String, AvailEntry, ClassLoader>() {
            @Override
            protected AvailEntry createInstance(final String key, final ClassLoader loader) {
                return new AvailEntry(key, loader);
            }
        };
    }
    
    protected static final class WholeBundle
    {
        String baseName;
        String localeID;
        ULocale ulocale;
        ClassLoader loader;
        ICUResourceBundleReader reader;
        Set<String> topLevelKeys;
        
        WholeBundle(final String baseName, final String localeID, final ClassLoader loader, final ICUResourceBundleReader reader) {
            this.baseName = baseName;
            this.localeID = localeID;
            this.ulocale = new ULocale(localeID);
            this.loader = loader;
            this.reader = reader;
        }
    }
    
    private abstract static class Loader
    {
        abstract ICUResourceBundle load();
    }
    
    private static final class AvailEntry
    {
        private String prefix;
        private ClassLoader loader;
        private volatile ULocale[] ulocales;
        private volatile Locale[] locales;
        private volatile Set<String> nameSet;
        private volatile Set<String> fullNameSet;
        
        AvailEntry(final String prefix, final ClassLoader loader) {
            this.prefix = prefix;
            this.loader = loader;
        }
        
        ULocale[] getULocaleList() {
            if (this.ulocales == null) {
                synchronized (this) {
                    if (this.ulocales == null) {
                        this.ulocales = createULocaleList(this.prefix, this.loader);
                    }
                }
            }
            return this.ulocales;
        }
        
        Locale[] getLocaleList() {
            if (this.locales == null) {
                this.getULocaleList();
                synchronized (this) {
                    if (this.locales == null) {
                        this.locales = ICUResourceBundle.getLocaleList(this.ulocales);
                    }
                }
            }
            return this.locales;
        }
        
        Set<String> getLocaleNameSet() {
            if (this.nameSet == null) {
                synchronized (this) {
                    if (this.nameSet == null) {
                        this.nameSet = createLocaleNameSet(this.prefix, this.loader);
                    }
                }
            }
            return this.nameSet;
        }
        
        Set<String> getFullLocaleNameSet() {
            if (this.fullNameSet == null) {
                synchronized (this) {
                    if (this.fullNameSet == null) {
                        this.fullNameSet = createFullLocaleNameSet(this.prefix, this.loader);
                    }
                }
            }
            return this.fullNameSet;
        }
    }
    
    public enum OpenType
    {
        LOCALE_DEFAULT_ROOT, 
        LOCALE_ROOT, 
        LOCALE_ONLY, 
        DIRECT;
    }
}
