/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.ClassLoaderUtil;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.ICUResourceBundleImpl;
import com.ibm.icu.impl.ICUResourceBundleReader;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.URLHandler;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import com.ibm.icu.util.UResourceTypeMismatchException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ICUResourceBundle
extends UResourceBundle {
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
    static final boolean $assertionsDisabled;

    public static final ULocale getFunctionalEquivalent(String string, ClassLoader classLoader, String string2, String string3, ULocale uLocale, boolean[] blArray, boolean bl) {
        Object object;
        String string4 = uLocale.getKeywordValue(string3);
        String string5 = uLocale.getBaseName();
        String string6 = null;
        ULocale uLocale2 = new ULocale(string5);
        ULocale uLocale3 = null;
        boolean bl2 = false;
        ULocale uLocale4 = null;
        int n = 0;
        int n2 = 0;
        if (string4 == null || string4.length() == 0 || string4.equals(DEFAULT_TAG)) {
            string4 = "";
            bl2 = true;
        }
        ICUResourceBundle iCUResourceBundle = null;
        iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(string, uLocale2);
        if (blArray != null) {
            blArray[0] = false;
            object = ICUResourceBundle.getAvailEntry(string, classLoader).getULocaleList(ULocale.AvailableType.DEFAULT);
            for (int i = 0; i < ((ULocale[])object).length; ++i) {
                if (!uLocale2.equals(object[i])) continue;
                blArray[0] = true;
                break;
            }
        }
        do {
            try {
                object = (ULocale[])iCUResourceBundle.get(string2);
                string6 = ((ResourceBundle)object).getString(DEFAULT_TAG);
                if (bl2) {
                    string4 = string6;
                    bl2 = false;
                }
                uLocale3 = iCUResourceBundle.getULocale();
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            if (uLocale3 != null) continue;
            iCUResourceBundle = iCUResourceBundle.getParent();
            ++n;
        } while (iCUResourceBundle != null && uLocale3 == null);
        uLocale2 = new ULocale(string5);
        iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(string, uLocale2);
        do {
            try {
                object = (ICUResourceBundle)iCUResourceBundle.get(string2);
                ((UResourceBundle)object).get(string4);
                uLocale4 = ((ICUResourceBundle)object).getULocale();
                if (uLocale4 != null && n2 > n) {
                    string6 = ((ResourceBundle)object).getString(DEFAULT_TAG);
                    uLocale3 = iCUResourceBundle.getULocale();
                    n = n2;
                }
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            if (uLocale4 != null) continue;
            iCUResourceBundle = iCUResourceBundle.getParent();
            ++n2;
        } while (iCUResourceBundle != null && uLocale4 == null);
        if (uLocale4 == null && string6 != null && !string6.equals(string4)) {
            string4 = string6;
            uLocale2 = new ULocale(string5);
            iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(string, uLocale2);
            n2 = 0;
            do {
                try {
                    object = (ICUResourceBundle)iCUResourceBundle.get(string2);
                    ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle)((UResourceBundle)object).get(string4);
                    uLocale4 = iCUResourceBundle.getULocale();
                    if (!uLocale4.getBaseName().equals(iCUResourceBundle2.getULocale().getBaseName())) {
                        uLocale4 = null;
                    }
                    if (uLocale4 != null && n2 > n) {
                        string6 = ((ResourceBundle)object).getString(DEFAULT_TAG);
                        uLocale3 = iCUResourceBundle.getULocale();
                        n = n2;
                    }
                } catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
                if (uLocale4 != null) continue;
                iCUResourceBundle = iCUResourceBundle.getParent();
                ++n2;
            } while (iCUResourceBundle != null && uLocale4 == null);
        }
        if (uLocale4 == null) {
            throw new MissingResourceException("Could not find locale containing requested or default keyword.", string, string3 + "=" + string4);
        }
        if (bl && string6.equals(string4) && n2 <= n) {
            return uLocale4;
        }
        return new ULocale(uLocale4.getBaseName() + "@" + string3 + "=" + string4);
    }

    public static final String[] getKeywordValues(String string, String string2) {
        HashSet<String> hashSet = new HashSet<String>();
        ULocale[] uLocaleArray = ICUResourceBundle.getAvailEntry(string, ICU_DATA_CLASS_LOADER).getULocaleList(ULocale.AvailableType.DEFAULT);
        for (int i = 0; i < uLocaleArray.length; ++i) {
            try {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(string, uLocaleArray[i]);
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)uResourceBundle.getObject(string2);
                Enumeration<String> enumeration = iCUResourceBundle.getKeys();
                while (enumeration.hasMoreElements()) {
                    String string3 = enumeration.nextElement();
                    if (DEFAULT_TAG.equals(string3) || string3.startsWith("private-")) continue;
                    hashSet.add(string3);
                }
                continue;
            } catch (Throwable throwable) {
                // empty catch block
            }
        }
        return hashSet.toArray(new String[0]);
    }

    public ICUResourceBundle getWithFallback(String string) throws MissingResourceException {
        ICUResourceBundle iCUResourceBundle = this;
        ICUResourceBundle iCUResourceBundle2 = ICUResourceBundle.findResourceWithFallback(string, iCUResourceBundle, null);
        if (iCUResourceBundle2 == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), string, this.getKey());
        }
        if (iCUResourceBundle2.getType() == 0 && iCUResourceBundle2.getString().equals(NO_INHERITANCE_MARKER)) {
            throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", string, this.getKey());
        }
        return iCUResourceBundle2;
    }

    public ICUResourceBundle at(int n) {
        return (ICUResourceBundle)this.handleGet(n, null, (UResourceBundle)this);
    }

    public ICUResourceBundle at(String string) {
        if (this instanceof ICUResourceBundleImpl.ResourceTable) {
            return (ICUResourceBundle)this.handleGet(string, null, (UResourceBundle)this);
        }
        return null;
    }

    @Override
    public ICUResourceBundle findTopLevel(int n) {
        return (ICUResourceBundle)super.findTopLevel(n);
    }

    @Override
    public ICUResourceBundle findTopLevel(String string) {
        return (ICUResourceBundle)super.findTopLevel(string);
    }

    public ICUResourceBundle findWithFallback(String string) {
        return ICUResourceBundle.findResourceWithFallback(string, this, null);
    }

    public String findStringWithFallback(String string) {
        return ICUResourceBundle.findStringWithFallback(string, this, null);
    }

    public String getStringWithFallback(String string) throws MissingResourceException {
        ICUResourceBundle iCUResourceBundle = this;
        String string2 = ICUResourceBundle.findStringWithFallback(string, iCUResourceBundle, null);
        if (string2 == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), string, this.getKey());
        }
        if (string2.equals(NO_INHERITANCE_MARKER)) {
            throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", string, this.getKey());
        }
        return string2;
    }

    public UResource.Value getValueWithFallback(String string) throws MissingResourceException {
        ICUResourceBundle iCUResourceBundle;
        if (string.isEmpty()) {
            iCUResourceBundle = this;
        } else {
            iCUResourceBundle = ICUResourceBundle.findResourceWithFallback(string, this, null);
            if (iCUResourceBundle == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), string, this.getKey());
            }
        }
        ICUResourceBundleReader.ReaderValue readerValue = new ICUResourceBundleReader.ReaderValue();
        ICUResourceBundleImpl iCUResourceBundleImpl = (ICUResourceBundleImpl)iCUResourceBundle;
        readerValue.reader = iCUResourceBundleImpl.wholeBundle.reader;
        readerValue.res = iCUResourceBundleImpl.getResource();
        return readerValue;
    }

    public void getAllItemsWithFallbackNoFail(String string, UResource.Sink sink) {
        try {
            this.getAllItemsWithFallback(string, sink);
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
    }

    public void getAllItemsWithFallback(String string, UResource.Sink sink) throws MissingResourceException {
        String[] stringArray;
        ICUResourceBundle iCUResourceBundle;
        int n = ICUResourceBundle.countPathKeys(string);
        if (n == 0) {
            iCUResourceBundle = this;
        } else {
            int n2 = this.getResDepth();
            stringArray = new String[n2 + n];
            ICUResourceBundle.getResPathKeys(string, n, stringArray, n2);
            iCUResourceBundle = ICUResourceBundle.findResourceWithFallback(stringArray, n2, this, null);
            if (iCUResourceBundle == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), string, this.getKey());
            }
        }
        UResource.Key key = new UResource.Key();
        stringArray = new ICUResourceBundleReader.ReaderValue();
        iCUResourceBundle.getAllItemsWithFallback(key, (ICUResourceBundleReader.ReaderValue)stringArray, sink);
    }

    private void getAllItemsWithFallback(UResource.Key key, ICUResourceBundleReader.ReaderValue readerValue, UResource.Sink sink) {
        ICUResourceBundleImpl iCUResourceBundleImpl = (ICUResourceBundleImpl)this;
        readerValue.reader = iCUResourceBundleImpl.wholeBundle.reader;
        readerValue.res = iCUResourceBundleImpl.getResource();
        key.setString(this.key != null ? this.key : "");
        sink.put(key, readerValue, this.parent == null);
        if (this.parent != null) {
            ICUResourceBundle iCUResourceBundle;
            ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle)this.parent;
            int n = this.getResDepth();
            if (n == 0) {
                iCUResourceBundle = iCUResourceBundle2;
            } else {
                String[] stringArray = new String[n];
                this.getResPathKeys(stringArray, n);
                iCUResourceBundle = ICUResourceBundle.findResourceWithFallback(stringArray, 0, iCUResourceBundle2, null);
            }
            if (iCUResourceBundle != null) {
                iCUResourceBundle.getAllItemsWithFallback(key, readerValue, sink);
            }
        }
    }

    public static Set<String> getAvailableLocaleNameSet(String string, ClassLoader classLoader) {
        return ICUResourceBundle.getAvailEntry(string, classLoader).getLocaleNameSet();
    }

    public static Set<String> getFullLocaleNameSet() {
        return ICUResourceBundle.getFullLocaleNameSet("com/ibm/icu/impl/data/icudt66b", ICU_DATA_CLASS_LOADER);
    }

    public static Set<String> getFullLocaleNameSet(String string, ClassLoader classLoader) {
        return ICUResourceBundle.getAvailEntry(string, classLoader).getFullLocaleNameSet();
    }

    public static Set<String> getAvailableLocaleNameSet() {
        return ICUResourceBundle.getAvailableLocaleNameSet("com/ibm/icu/impl/data/icudt66b", ICU_DATA_CLASS_LOADER);
    }

    public static final ULocale[] getAvailableULocales(String string, ClassLoader classLoader, ULocale.AvailableType availableType) {
        return ICUResourceBundle.getAvailEntry(string, classLoader).getULocaleList(availableType);
    }

    public static final ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales("com/ibm/icu/impl/data/icudt66b", ICU_DATA_CLASS_LOADER, ULocale.AvailableType.DEFAULT);
    }

    public static final ULocale[] getAvailableULocales(ULocale.AvailableType availableType) {
        return ICUResourceBundle.getAvailableULocales("com/ibm/icu/impl/data/icudt66b", ICU_DATA_CLASS_LOADER, availableType);
    }

    public static final ULocale[] getAvailableULocales(String string, ClassLoader classLoader) {
        return ICUResourceBundle.getAvailableULocales(string, classLoader, ULocale.AvailableType.DEFAULT);
    }

    public static final Locale[] getAvailableLocales(String string, ClassLoader classLoader, ULocale.AvailableType availableType) {
        return ICUResourceBundle.getAvailEntry(string, classLoader).getLocaleList(availableType);
    }

    public static final Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales("com/ibm/icu/impl/data/icudt66b", ICU_DATA_CLASS_LOADER, ULocale.AvailableType.DEFAULT);
    }

    public static final Locale[] getAvailableLocales(ULocale.AvailableType availableType) {
        return ICUResourceBundle.getAvailableLocales("com/ibm/icu/impl/data/icudt66b", ICU_DATA_CLASS_LOADER, availableType);
    }

    public static final Locale[] getAvailableLocales(String string, ClassLoader classLoader) {
        return ICUResourceBundle.getAvailableLocales(string, classLoader, ULocale.AvailableType.DEFAULT);
    }

    public static final Locale[] getLocaleList(ULocale[] uLocaleArray) {
        ArrayList<Locale> arrayList = new ArrayList<Locale>(uLocaleArray.length);
        HashSet<Locale> hashSet = new HashSet<Locale>();
        for (int i = 0; i < uLocaleArray.length; ++i) {
            Locale locale = uLocaleArray[i].toLocale();
            if (hashSet.contains(locale)) continue;
            arrayList.add(locale);
            hashSet.add(locale);
        }
        return arrayList.toArray(new Locale[arrayList.size()]);
    }

    @Override
    public Locale getLocale() {
        return this.getULocale().toLocale();
    }

    private static final EnumMap<ULocale.AvailableType, ULocale[]> createULocaleList(String string, ClassLoader classLoader) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.instantiateBundle(string, ICU_RESOURCE_INDEX, classLoader, true);
        EnumMap<ULocale.AvailableType, ULocale[]> enumMap = new EnumMap<ULocale.AvailableType, ULocale[]>(ULocale.AvailableType.class);
        AvailableLocalesSink availableLocalesSink = new AvailableLocalesSink(enumMap);
        iCUResourceBundle.getAllItemsWithFallback("", availableLocalesSink);
        return enumMap;
    }

    private static final void addLocaleIDsFromIndexBundle(String string, ClassLoader classLoader, Set<String> set) {
        ICUResourceBundle iCUResourceBundle;
        try {
            iCUResourceBundle = (ICUResourceBundle)UResourceBundle.instantiateBundle(string, ICU_RESOURCE_INDEX, classLoader, true);
            iCUResourceBundle = (ICUResourceBundle)iCUResourceBundle.get(INSTALLED_LOCALES);
        } catch (MissingResourceException missingResourceException) {
            if (DEBUG) {
                System.out.println("couldn't find " + string + '/' + ICU_RESOURCE_INDEX + ".res");
                Thread.dumpStack();
            }
            return;
        }
        UResourceBundleIterator uResourceBundleIterator = iCUResourceBundle.getIterator();
        uResourceBundleIterator.reset();
        while (uResourceBundleIterator.hasNext()) {
            String string2 = uResourceBundleIterator.next().getKey();
            set.add(string2);
        }
    }

    private static final void addBundleBaseNamesFromClassLoader(String string, ClassLoader classLoader, Set<String> set) {
        AccessController.doPrivileged(new PrivilegedAction<Void>(classLoader, string, set){
            final ClassLoader val$root;
            final String val$bn;
            final Set val$names;
            {
                this.val$root = classLoader;
                this.val$bn = string;
                this.val$names = set;
            }

            @Override
            public Void run() {
                block5: {
                    try {
                        Enumeration<URL> enumeration = this.val$root.getResources(this.val$bn);
                        if (enumeration == null) {
                            return null;
                        }
                        URLHandler.URLVisitor uRLVisitor = new URLHandler.URLVisitor(this){
                            final 2 this$0;
                            {
                                this.this$0 = var1_1;
                            }

                            @Override
                            public void visit(String string) {
                                if (string.endsWith(".res")) {
                                    String string2 = string.substring(0, string.length() - 4);
                                    this.this$0.val$names.add(string2);
                                }
                            }
                        };
                        while (enumeration.hasMoreElements()) {
                            URL uRL = enumeration.nextElement();
                            URLHandler uRLHandler = URLHandler.get(uRL);
                            if (uRLHandler != null) {
                                uRLHandler.guide(uRLVisitor, true);
                                continue;
                            }
                            if (!ICUResourceBundle.access$000()) continue;
                            System.out.println("handler for " + uRL + " is null");
                        }
                    } catch (IOException iOException) {
                        if (!ICUResourceBundle.access$000()) break block5;
                        System.out.println("ouch: " + iOException.getMessage());
                    }
                }
                return null;
            }

            @Override
            public Object run() {
                return this.run();
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void addLocaleIDsFromListFile(String string, ClassLoader classLoader, Set<String> set) {
        block6: {
            try {
                InputStream inputStream = classLoader.getResourceAsStream(string + FULL_LOCALE_NAMES_LIST);
                if (inputStream == null) break block6;
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ASCII"));){
                    String string2;
                    while ((string2 = bufferedReader.readLine()) != null) {
                        if (string2.length() == 0 || string2.startsWith("#")) continue;
                        set.add(string2);
                    }
                }
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private static Set<String> createFullLocaleNameSet(String string, ClassLoader classLoader) {
        String string2 = string.endsWith(RES_PATH_SEP_STR) ? string : string + RES_PATH_SEP_STR;
        HashSet<String> hashSet = new HashSet<String>();
        String string3 = ICUConfig.get("com.ibm.icu.impl.ICUResourceBundle.skipRuntimeLocaleResourceScan", "false");
        if (!string3.equalsIgnoreCase("true")) {
            Object object;
            ICUResourceBundle.addBundleBaseNamesFromClassLoader(string2, classLoader, hashSet);
            if (string.startsWith("com/ibm/icu/impl/data/icudt66b") && (object = string.length() == 30 ? "" : (string.charAt(30) == '/' ? string.substring(31) : null)) != null) {
                ICUBinary.addBaseNamesInFileFolder((String)object, ".res", hashSet);
            }
            hashSet.remove(ICU_RESOURCE_INDEX);
            object = hashSet.iterator();
            while (object.hasNext()) {
                String string4 = (String)object.next();
                if (string4.length() != 1 && string4.length() <= 3 || string4.indexOf(95) >= 0) continue;
                object.remove();
            }
        }
        if (hashSet.isEmpty()) {
            if (DEBUG) {
                System.out.println("unable to enumerate data files in " + string);
            }
            ICUResourceBundle.addLocaleIDsFromListFile(string2, classLoader, hashSet);
        }
        if (hashSet.isEmpty()) {
            ICUResourceBundle.addLocaleIDsFromIndexBundle(string, classLoader, hashSet);
        }
        hashSet.remove("root");
        hashSet.add(ULocale.ROOT.toString());
        return Collections.unmodifiableSet(hashSet);
    }

    private static Set<String> createLocaleNameSet(String string, ClassLoader classLoader) {
        HashSet<String> hashSet = new HashSet<String>();
        ICUResourceBundle.addLocaleIDsFromIndexBundle(string, classLoader, hashSet);
        return Collections.unmodifiableSet(hashSet);
    }

    private static AvailEntry getAvailEntry(String string, ClassLoader classLoader) {
        return GET_AVAILABLE_CACHE.getInstance(string, classLoader);
    }

    private static final ICUResourceBundle findResourceWithFallback(String string, UResourceBundle uResourceBundle, UResourceBundle uResourceBundle2) {
        if (string.length() == 0) {
            return null;
        }
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)uResourceBundle;
        int n = iCUResourceBundle.getResDepth();
        int n2 = ICUResourceBundle.countPathKeys(string);
        if (!$assertionsDisabled && n2 <= 0) {
            throw new AssertionError();
        }
        String[] stringArray = new String[n + n2];
        ICUResourceBundle.getResPathKeys(string, n2, stringArray, n);
        return ICUResourceBundle.findResourceWithFallback(stringArray, n, iCUResourceBundle, uResourceBundle2);
    }

    private static final ICUResourceBundle findResourceWithFallback(String[] stringArray, int n, ICUResourceBundle object, UResourceBundle uResourceBundle) {
        if (uResourceBundle == null) {
            uResourceBundle = object;
        }
        while (true) {
            Object object2;
            ICUResourceBundle iCUResourceBundle;
            if ((iCUResourceBundle = (ICUResourceBundle)((UResourceBundle)object).handleGet((String)(object2 = stringArray[n++]), null, uResourceBundle)) == null) {
                --n;
            } else {
                if (n == stringArray.length) {
                    return iCUResourceBundle;
                }
                object = iCUResourceBundle;
                continue;
            }
            object2 = ((ICUResourceBundle)object).getParent();
            if (object2 == null) {
                return null;
            }
            int n2 = ((ICUResourceBundle)object).getResDepth();
            if (n != n2) {
                String[] stringArray2 = new String[n2 + (stringArray.length - n)];
                System.arraycopy(stringArray, n, stringArray2, n2, stringArray.length - n);
                stringArray = stringArray2;
            }
            ((ICUResourceBundle)object).getResPathKeys(stringArray, n2);
            object = object2;
            n = 0;
        }
    }

    /*
     * Unable to fully structure code
     */
    private static final String findStringWithFallback(String var0, UResourceBundle var1_1, UResourceBundle var2_2) {
        if (var0.length() == 0) {
            return null;
        }
        if (!(var1_1 instanceof ICUResourceBundleImpl.ResourceContainer)) {
            return null;
        }
        if (var2_2 == null) {
            var2_2 = var1_1;
        }
        var3_3 = (ICUResourceBundle)var1_1;
        var4_4 = var3_3.wholeBundle.reader;
        var5_5 = -1;
        var7_7 = var6_6 = var3_3.getResDepth();
        var8_8 = ICUResourceBundle.countPathKeys(var0);
        if (!ICUResourceBundle.$assertionsDisabled && var8_8 <= 0) {
            throw new AssertionError();
        }
        var9_9 = new String[var7_7 + var8_8];
        ICUResourceBundle.getResPathKeys(var0, var8_8, var9_9, var7_7);
        while (true) {
            block16: {
                block17: {
                    block15: {
                        if (var5_5 != -1) break block15;
                        var11_11 = var3_3.getType();
                        if (var11_11 != 2 && var11_11 != 8) break block16;
                        var10_10 = ((ICUResourceBundleImpl.ResourceContainer)var3_3).value;
                        ** GOTO lbl32
                    }
                    var11_11 = ICUResourceBundleReader.RES_GET_TYPE(var5_5);
                    if (!ICUResourceBundleReader.URES_IS_TABLE(var11_11)) break block17;
                    var10_10 = var4_4.getTable(var5_5);
                    ** GOTO lbl32
                }
                if (!ICUResourceBundleReader.URES_IS_ARRAY(var11_11)) {
                    var5_5 = -1;
                } else {
                    var10_10 = var4_4.getArray(var5_5);
lbl32:
                    // 3 sources

                    var11_12 = var9_9[var7_7++];
                    var5_5 = var10_10.getResource(var4_4, var11_12);
                    if (var5_5 == -1) {
                        --var7_7;
                    } else {
                        if (ICUResourceBundleReader.RES_GET_TYPE(var5_5) == 3) {
                            super.getResPathKeys(var9_9, var6_6);
                            var12_13 = ICUResourceBundle.getAliasedResource((ICUResourceBundle)var3_3, var9_9, var7_7, var11_12, var5_5, null, var2_2);
                        } else {
                            var12_13 = null;
                        }
                        if (var7_7 == var9_9.length) {
                            if (var12_13 != null) {
                                return var12_13.getString();
                            }
                            var13_14 = var4_4.getString(var5_5);
                            if (var13_14 == null) {
                                throw new UResourceTypeMismatchException("");
                            }
                            return var13_14;
                        }
                        if (var12_13 == null) continue;
                        var3_3 = var12_13;
                        var4_4 = var3_3.wholeBundle.reader;
                        var5_5 = -1;
                        var6_6 = super.getResDepth();
                        if (var7_7 == var6_6) continue;
                        var13_14 = new String[var6_6 + (var9_9.length - var7_7)];
                        System.arraycopy(var9_9, var7_7, var13_14, var6_6, var9_9.length - var7_7);
                        var9_9 = var13_14;
                        var7_7 = var6_6;
                        continue;
                    }
                }
            }
            if ((var10_10 = var3_3.getParent()) == null) {
                return null;
            }
            var3_3.getResPathKeys(var9_9, var6_6);
            var3_3 = var10_10;
            var4_4 = var3_3.wholeBundle.reader;
            var6_6 = 0;
            var7_7 = 0;
        }
    }

    private int getResDepth() {
        return this.container == null ? 0 : this.container.getResDepth() + 1;
    }

    private void getResPathKeys(String[] stringArray, int n) {
        ICUResourceBundle iCUResourceBundle = this;
        while (n > 0) {
            stringArray[--n] = iCUResourceBundle.key;
            iCUResourceBundle = iCUResourceBundle.container;
            if (!$assertionsDisabled && n == 0 != (iCUResourceBundle.container == null)) {
                throw new AssertionError();
            }
        }
    }

    private static int countPathKeys(String string) {
        if (string.isEmpty()) {
            return 1;
        }
        int n = 1;
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) != '/') continue;
            ++n;
        }
        return n;
    }

    private static void getResPathKeys(String string, int n, String[] stringArray, int n2) {
        int n3;
        if (n == 0) {
            return;
        }
        if (n == 1) {
            stringArray[n2] = string;
            return;
        }
        int n4 = 0;
        while (true) {
            n3 = string.indexOf(47, n4);
            if (!$assertionsDisabled && n3 < n4) {
                throw new AssertionError();
            }
            stringArray[n2++] = string.substring(n4, n3);
            if (n == 2) {
                if (!$assertionsDisabled && string.indexOf(47, n3 + 1) >= 0) {
                    throw new AssertionError();
                }
                break;
            }
            n4 = n3 + 1;
            --n;
        }
        stringArray[n2] = string.substring(n3 + 1);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof ICUResourceBundle) {
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)object;
            if (this.getBaseName().equals(iCUResourceBundle.getBaseName()) && this.getLocaleID().equals(iCUResourceBundle.getLocaleID())) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        if (!$assertionsDisabled) {
            throw new AssertionError((Object)"hashCode not designed");
        }
        return 1;
    }

    public static ICUResourceBundle getBundleInstance(String string, String string2, ClassLoader classLoader, boolean bl) {
        return ICUResourceBundle.getBundleInstance(string, string2, classLoader, bl ? OpenType.DIRECT : OpenType.LOCALE_DEFAULT_ROOT);
    }

    public static ICUResourceBundle getBundleInstance(String string, ULocale uLocale, OpenType openType) {
        if (uLocale == null) {
            uLocale = ULocale.getDefault();
        }
        return ICUResourceBundle.getBundleInstance(string, uLocale.getBaseName(), ICU_DATA_CLASS_LOADER, openType);
    }

    public static ICUResourceBundle getBundleInstance(String string, String string2, ClassLoader classLoader, OpenType openType) {
        if (string == null) {
            string = "com/ibm/icu/impl/data/icudt66b";
        }
        string2 = ULocale.getBaseName(string2);
        ICUResourceBundle iCUResourceBundle = openType == OpenType.LOCALE_DEFAULT_ROOT ? ICUResourceBundle.instantiateBundle(string, string2, ULocale.getDefault().getBaseName(), classLoader, openType) : ICUResourceBundle.instantiateBundle(string, string2, null, classLoader, openType);
        if (iCUResourceBundle == null) {
            throw new MissingResourceException("Could not find the bundle " + string + RES_PATH_SEP_STR + string2 + ".res", "", "");
        }
        return iCUResourceBundle;
    }

    private static boolean localeIDStartsWithLangSubtag(String string, String string2) {
        return string.startsWith(string2) && (string.length() == string2.length() || string.charAt(string2.length()) == '_');
    }

    private static ICUResourceBundle instantiateBundle(String string, String string2, String string3, ClassLoader classLoader, OpenType openType) {
        if (!$assertionsDisabled && string2.indexOf(64) >= 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && string3 != null && string3.indexOf(64) >= 0) {
            throw new AssertionError();
        }
        String string4 = ICUResourceBundleReader.getFullName(string, string2);
        char c = (char)(48 + openType.ordinal());
        String string5 = openType != OpenType.LOCALE_DEFAULT_ROOT ? string4 + '#' + c : string4 + '#' + c + '#' + string3;
        return BUNDLE_CACHE.getInstance(string5, new Loader(string4, string, string2, classLoader, openType, string3){
            final String val$fullName;
            final String val$baseName;
            final String val$localeID;
            final ClassLoader val$root;
            final OpenType val$openType;
            final String val$defaultID;
            {
                this.val$fullName = string;
                this.val$baseName = string2;
                this.val$localeID = string3;
                this.val$root = classLoader;
                this.val$openType = openType;
                this.val$defaultID = string4;
                super(null);
            }

            @Override
            public ICUResourceBundle load() {
                if (ICUResourceBundle.access$000()) {
                    System.out.println("Creating " + this.val$fullName);
                }
                String string = this.val$baseName.indexOf(46) == -1 ? "root" : "";
                String string2 = this.val$localeID.isEmpty() ? string : this.val$localeID;
                ICUResourceBundle iCUResourceBundle = ICUResourceBundle.createBundle(this.val$baseName, string2, this.val$root);
                if (ICUResourceBundle.access$000()) {
                    System.out.println("The bundle created is: " + iCUResourceBundle + " and openType=" + (Object)((Object)this.val$openType) + " and bundle.getNoFallback=" + (iCUResourceBundle != null && ICUResourceBundle.access$500(iCUResourceBundle)));
                }
                if (this.val$openType == OpenType.DIRECT || iCUResourceBundle != null && ICUResourceBundle.access$500(iCUResourceBundle)) {
                    return iCUResourceBundle;
                }
                if (iCUResourceBundle == null) {
                    int n = string2.lastIndexOf(95);
                    if (n != -1) {
                        String string3 = string2.substring(0, n);
                        iCUResourceBundle = ICUResourceBundle.access$600(this.val$baseName, string3, this.val$defaultID, this.val$root, this.val$openType);
                    } else if (this.val$openType == OpenType.LOCALE_DEFAULT_ROOT && !ICUResourceBundle.access$700(this.val$defaultID, string2)) {
                        iCUResourceBundle = ICUResourceBundle.access$600(this.val$baseName, this.val$defaultID, this.val$defaultID, this.val$root, this.val$openType);
                    } else if (this.val$openType != OpenType.LOCALE_ONLY && !string.isEmpty()) {
                        iCUResourceBundle = ICUResourceBundle.createBundle(this.val$baseName, string, this.val$root);
                    }
                } else {
                    ICUResourceBundle iCUResourceBundle2 = null;
                    string2 = iCUResourceBundle.getLocaleID();
                    int n = string2.lastIndexOf(95);
                    String string4 = ((ICUResourceBundleImpl.ResourceTable)iCUResourceBundle).findString("%%Parent");
                    if (string4 != null) {
                        iCUResourceBundle2 = ICUResourceBundle.access$600(this.val$baseName, string4, this.val$defaultID, this.val$root, this.val$openType);
                    } else if (n != -1) {
                        iCUResourceBundle2 = ICUResourceBundle.access$600(this.val$baseName, string2.substring(0, n), this.val$defaultID, this.val$root, this.val$openType);
                    } else if (!string2.equals(string)) {
                        iCUResourceBundle2 = ICUResourceBundle.access$600(this.val$baseName, string, this.val$defaultID, this.val$root, this.val$openType);
                    }
                    if (!iCUResourceBundle.equals(iCUResourceBundle2)) {
                        iCUResourceBundle.setParent(iCUResourceBundle2);
                    }
                }
                return iCUResourceBundle;
            }
        });
    }

    ICUResourceBundle get(String string, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)this.handleGet(string, hashMap, uResourceBundle);
        if (iCUResourceBundle == null) {
            iCUResourceBundle = this.getParent();
            if (iCUResourceBundle != null) {
                iCUResourceBundle = iCUResourceBundle.get(string, hashMap, uResourceBundle);
            }
            if (iCUResourceBundle == null) {
                String string2 = ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID());
                throw new MissingResourceException("Can't find resource for bundle " + string2 + ", key " + string, this.getClass().getName(), string);
            }
        }
        return iCUResourceBundle;
    }

    public static ICUResourceBundle createBundle(String string, String string2, ClassLoader classLoader) {
        ICUResourceBundleReader iCUResourceBundleReader = ICUResourceBundleReader.getReader(string, string2, classLoader);
        if (iCUResourceBundleReader == null) {
            return null;
        }
        return ICUResourceBundle.getBundle(iCUResourceBundleReader, string, string2, classLoader);
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

    @Override
    public ICUResourceBundle getParent() {
        return (ICUResourceBundle)this.parent;
    }

    @Override
    protected void setParent(ResourceBundle resourceBundle) {
        this.parent = resourceBundle;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    private boolean getNoFallback() {
        return this.wholeBundle.reader.getNoFallback();
    }

    private static ICUResourceBundle getBundle(ICUResourceBundleReader iCUResourceBundleReader, String string, String string2, ClassLoader classLoader) {
        int n = iCUResourceBundleReader.getRootResource();
        if (!ICUResourceBundleReader.URES_IS_TABLE(ICUResourceBundleReader.RES_GET_TYPE(n))) {
            throw new IllegalStateException("Invalid format error");
        }
        Object object = new WholeBundle(string, string2, classLoader, iCUResourceBundleReader);
        ICUResourceBundleImpl.ResourceTable resourceTable = new ICUResourceBundleImpl.ResourceTable((WholeBundle)object, n);
        object = resourceTable.findString("%%ALIAS");
        if (object != null) {
            return (ICUResourceBundle)UResourceBundle.getBundleInstance(string, (String)object);
        }
        return resourceTable;
    }

    protected ICUResourceBundle(WholeBundle wholeBundle) {
        this.wholeBundle = wholeBundle;
    }

    protected ICUResourceBundle(ICUResourceBundle iCUResourceBundle, String string) {
        this.key = string;
        this.wholeBundle = iCUResourceBundle.wholeBundle;
        this.container = iCUResourceBundle;
        this.parent = iCUResourceBundle.parent;
    }

    protected static ICUResourceBundle getAliasedResource(ICUResourceBundle iCUResourceBundle, String[] stringArray, int n, String string, int n2, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
        int n3;
        String string2;
        String string3;
        int n4;
        WholeBundle wholeBundle = iCUResourceBundle.wholeBundle;
        ClassLoader classLoader = wholeBundle.loader;
        String string4 = null;
        String string5 = wholeBundle.reader.getAlias(n2);
        if (hashMap == null) {
            hashMap = new HashMap();
        }
        if (hashMap.get(string5) != null) {
            throw new IllegalArgumentException("Circular references in the resource bundles");
        }
        hashMap.put(string5, "");
        if (string5.indexOf(47) == 0) {
            n4 = string5.indexOf(47, 1);
            int n5 = string5.indexOf(47, n4 + 1);
            string3 = string5.substring(1, n4);
            if (n5 < 0) {
                string2 = string5.substring(n4 + 1);
            } else {
                string2 = string5.substring(n4 + 1, n5);
                string4 = string5.substring(n5 + 1, string5.length());
            }
            if (string3.equals(ICUDATA)) {
                string3 = "com/ibm/icu/impl/data/icudt66b";
                classLoader = ICU_DATA_CLASS_LOADER;
            } else if (string3.indexOf(ICUDATA) > -1 && (n3 = string3.indexOf(45)) > -1) {
                string3 = "com/ibm/icu/impl/data/icudt66b/" + string3.substring(n3 + 1, string3.length());
                classLoader = ICU_DATA_CLASS_LOADER;
            }
        } else {
            n4 = string5.indexOf(47);
            if (n4 != -1) {
                string2 = string5.substring(0, n4);
                string4 = string5.substring(n4 + 1);
            } else {
                string2 = string5;
            }
            string3 = wholeBundle.baseName;
        }
        ICUResourceBundle iCUResourceBundle2 = null;
        ICUResourceBundle iCUResourceBundle3 = null;
        if (string3.equals(LOCALE)) {
            string3 = wholeBundle.baseName;
            string4 = string5.substring(8, string5.length());
            iCUResourceBundle2 = (ICUResourceBundle)uResourceBundle;
            while (iCUResourceBundle2.container != null) {
                iCUResourceBundle2 = iCUResourceBundle2.container;
            }
            iCUResourceBundle3 = ICUResourceBundle.findResourceWithFallback(string4, iCUResourceBundle2, null);
        } else {
            iCUResourceBundle2 = ICUResourceBundle.getBundleInstance(string3, string2, classLoader, false);
            if (string4 != null) {
                n3 = ICUResourceBundle.countPathKeys(string4);
                if (n3 > 0) {
                    stringArray = new String[n3];
                    ICUResourceBundle.getResPathKeys(string4, n3, stringArray, 0);
                }
            } else if (stringArray != null) {
                n3 = n;
            } else {
                n = iCUResourceBundle.getResDepth();
                n3 = n + 1;
                stringArray = new String[n3];
                iCUResourceBundle.getResPathKeys(stringArray, n);
                stringArray[n] = string;
            }
            if (n3 > 0) {
                iCUResourceBundle3 = iCUResourceBundle2;
                for (int n6 = 0; iCUResourceBundle3 != null && n6 < n3; iCUResourceBundle3 = iCUResourceBundle3.get(stringArray[n6], hashMap, uResourceBundle), ++n6) {
                }
            }
        }
        if (iCUResourceBundle3 == null) {
            throw new MissingResourceException(wholeBundle.localeID, wholeBundle.baseName, string);
        }
        return iCUResourceBundle3;
    }

    @Deprecated
    public final Set<String> getTopLevelKeySet() {
        return this.wholeBundle.topLevelKeys;
    }

    @Deprecated
    public final void setTopLevelKeySet(Set<String> set) {
        this.wholeBundle.topLevelKeys = set;
    }

    @Override
    protected Enumeration<String> handleGetKeys() {
        return Collections.enumeration(this.handleKeySet());
    }

    @Override
    protected boolean isTopLevelResource() {
        return this.container == null;
    }

    @Override
    public UResourceBundle findTopLevel(int n) {
        return this.findTopLevel(n);
    }

    @Override
    public UResourceBundle findTopLevel(String string) {
        return this.findTopLevel(string);
    }

    @Override
    public UResourceBundle getParent() {
        return this.getParent();
    }

    static boolean access$000() {
        return DEBUG;
    }

    static EnumMap access$100(String string, ClassLoader classLoader) {
        return ICUResourceBundle.createULocaleList(string, classLoader);
    }

    static Set access$200(String string, ClassLoader classLoader) {
        return ICUResourceBundle.createLocaleNameSet(string, classLoader);
    }

    static Set access$300(String string, ClassLoader classLoader) {
        return ICUResourceBundle.createFullLocaleNameSet(string, classLoader);
    }

    static boolean access$500(ICUResourceBundle iCUResourceBundle) {
        return iCUResourceBundle.getNoFallback();
    }

    static ICUResourceBundle access$600(String string, String string2, String string3, ClassLoader classLoader, OpenType openType) {
        return ICUResourceBundle.instantiateBundle(string, string2, string3, classLoader, openType);
    }

    static boolean access$700(String string, String string2) {
        return ICUResourceBundle.localeIDStartsWithLangSubtag(string, string2);
    }

    static {
        $assertionsDisabled = !ICUResourceBundle.class.desiredAssertionStatus();
        ICU_DATA_CLASS_LOADER = ClassLoaderUtil.getClassLoader(ICUData.class);
        BUNDLE_CACHE = new SoftCache<String, ICUResourceBundle, Loader>(){

            @Override
            protected ICUResourceBundle createInstance(String string, Loader loader) {
                return loader.load();
            }

            @Override
            protected Object createInstance(Object object, Object object2) {
                return this.createInstance((String)object, (Loader)object2);
            }
        };
        DEBUG = ICUDebug.enabled("localedata");
        GET_AVAILABLE_CACHE = new SoftCache<String, AvailEntry, ClassLoader>(){

            @Override
            protected AvailEntry createInstance(String string, ClassLoader classLoader) {
                return new AvailEntry(string, classLoader);
            }

            @Override
            protected Object createInstance(Object object, Object object2) {
                return this.createInstance((String)object, (ClassLoader)object2);
            }
        };
    }

    public static enum OpenType {
        LOCALE_DEFAULT_ROOT,
        LOCALE_ROOT,
        LOCALE_ONLY,
        DIRECT;

    }

    private static final class AvailEntry {
        private String prefix;
        private ClassLoader loader;
        private volatile EnumMap<ULocale.AvailableType, ULocale[]> ulocales;
        private volatile Locale[] locales;
        private volatile Set<String> nameSet;
        private volatile Set<String> fullNameSet;
        static final boolean $assertionsDisabled = !ICUResourceBundle.class.desiredAssertionStatus();

        AvailEntry(String string, ClassLoader classLoader) {
            this.prefix = string;
            this.loader = classLoader;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        ULocale[] getULocaleList(ULocale.AvailableType availableType) {
            if (!$assertionsDisabled && availableType == ULocale.AvailableType.WITH_LEGACY_ALIASES) {
                throw new AssertionError();
            }
            if (this.ulocales == null) {
                AvailEntry availEntry = this;
                synchronized (availEntry) {
                    if (this.ulocales == null) {
                        this.ulocales = ICUResourceBundle.access$100(this.prefix, this.loader);
                    }
                }
            }
            return this.ulocales.get((Object)availableType);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        Locale[] getLocaleList(ULocale.AvailableType availableType) {
            if (this.locales == null) {
                this.getULocaleList(availableType);
                AvailEntry availEntry = this;
                synchronized (availEntry) {
                    if (this.locales == null) {
                        this.locales = ICUResourceBundle.getLocaleList(this.ulocales.get((Object)availableType));
                    }
                }
            }
            return this.locales;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        Set<String> getLocaleNameSet() {
            if (this.nameSet == null) {
                AvailEntry availEntry = this;
                synchronized (availEntry) {
                    if (this.nameSet == null) {
                        this.nameSet = ICUResourceBundle.access$200(this.prefix, this.loader);
                    }
                }
            }
            return this.nameSet;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        Set<String> getFullLocaleNameSet() {
            if (this.fullNameSet == null) {
                AvailEntry availEntry = this;
                synchronized (availEntry) {
                    if (this.fullNameSet == null) {
                        this.fullNameSet = ICUResourceBundle.access$300(this.prefix, this.loader);
                    }
                }
            }
            return this.fullNameSet;
        }
    }

    private static final class AvailableLocalesSink
    extends UResource.Sink {
        EnumMap<ULocale.AvailableType, ULocale[]> output;

        public AvailableLocalesSink(EnumMap<ULocale.AvailableType, ULocale[]> enumMap) {
            this.output = enumMap;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                block6: {
                    ULocale.AvailableType availableType;
                    block5: {
                        block4: {
                            if (!key.contentEquals(ICUResourceBundle.INSTALLED_LOCALES)) break block4;
                            availableType = ULocale.AvailableType.DEFAULT;
                            break block5;
                        }
                        if (!key.contentEquals("AliasLocales")) break block6;
                        availableType = ULocale.AvailableType.ONLY_LEGACY_ALIASES;
                    }
                    UResource.Table table2 = value.getTable();
                    ULocale[] uLocaleArray = new ULocale[table2.getSize()];
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        uLocaleArray[n2] = new ULocale(key.toString());
                        ++n2;
                    }
                    this.output.put(availableType, uLocaleArray);
                }
                ++n;
            }
        }
    }

    private static abstract class Loader {
        private Loader() {
        }

        abstract ICUResourceBundle load();

        Loader(1 var1_1) {
            this();
        }
    }

    protected static final class WholeBundle {
        String baseName;
        String localeID;
        ULocale ulocale;
        ClassLoader loader;
        ICUResourceBundleReader reader;
        Set<String> topLevelKeys;

        WholeBundle(String string, String string2, ClassLoader classLoader, ICUResourceBundleReader iCUResourceBundleReader) {
            this.baseName = string;
            this.localeID = string2;
            this.ulocale = new ULocale(string2);
            this.loader = classLoader;
            this.reader = iCUResourceBundleReader;
        }
    }
}

