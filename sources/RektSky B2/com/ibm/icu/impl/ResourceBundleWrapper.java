package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.security.*;
import java.io.*;
import java.util.*;

public final class ResourceBundleWrapper extends UResourceBundle
{
    private ResourceBundle bundle;
    private String localeID;
    private String baseName;
    private List<String> keys;
    private static CacheBase<String, ResourceBundleWrapper, Loader> BUNDLE_CACHE;
    private static final boolean DEBUG;
    
    private ResourceBundleWrapper(final ResourceBundle bundle) {
        this.bundle = null;
        this.localeID = null;
        this.baseName = null;
        this.keys = null;
        this.bundle = bundle;
    }
    
    @Override
    protected Object handleGetObject(final String aKey) {
        ResourceBundleWrapper current = this;
        Object obj = null;
        while (current != null) {
            try {
                obj = current.bundle.getObject(aKey);
            }
            catch (MissingResourceException ex) {
                current = (ResourceBundleWrapper)current.getParent();
                continue;
            }
            break;
        }
        if (obj == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.baseName + ", key " + aKey, this.getClass().getName(), aKey);
        }
        return obj;
    }
    
    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(this.keys);
    }
    
    private void initKeysVector() {
        ResourceBundleWrapper current = this;
        this.keys = new ArrayList<String>();
        while (current != null) {
            final Enumeration<String> e = current.bundle.getKeys();
            while (e.hasMoreElements()) {
                final String elem = e.nextElement();
                if (!this.keys.contains(elem)) {
                    this.keys.add(elem);
                }
            }
            current = (ResourceBundleWrapper)current.getParent();
        }
    }
    
    @Override
    protected String getLocaleID() {
        return this.localeID;
    }
    
    @Override
    protected String getBaseName() {
        return this.bundle.getClass().getName().replace('.', '/');
    }
    
    @Override
    public ULocale getULocale() {
        return new ULocale(this.localeID);
    }
    
    public UResourceBundle getParent() {
        return (UResourceBundle)this.parent;
    }
    
    public static ResourceBundleWrapper getBundleInstance(final String baseName, final String localeID, ClassLoader root, final boolean disableFallback) {
        if (root == null) {
            root = ClassLoaderUtil.getClassLoader();
        }
        ResourceBundleWrapper b;
        if (disableFallback) {
            b = instantiateBundle(baseName, localeID, null, root, disableFallback);
        }
        else {
            b = instantiateBundle(baseName, localeID, ULocale.getDefault().getBaseName(), root, disableFallback);
        }
        if (b == null) {
            String separator = "_";
            if (baseName.indexOf(47) >= 0) {
                separator = "/";
            }
            throw new MissingResourceException("Could not find the bundle " + baseName + separator + localeID, "", "");
        }
        return b;
    }
    
    private static boolean localeIDStartsWithLangSubtag(final String localeID, final String lang) {
        return localeID.startsWith(lang) && (localeID.length() == lang.length() || localeID.charAt(lang.length()) == '_');
    }
    
    private static ResourceBundleWrapper instantiateBundle(final String baseName, final String localeID, final String defaultID, final ClassLoader root, final boolean disableFallback) {
        final String name = localeID.isEmpty() ? baseName : (baseName + '_' + localeID);
        final String cacheKey = disableFallback ? name : (name + '#' + defaultID);
        return ResourceBundleWrapper.BUNDLE_CACHE.getInstance(cacheKey, new Loader() {
            public ResourceBundleWrapper load() {
                ResourceBundleWrapper parent = null;
                final int i = localeID.lastIndexOf(95);
                boolean loadFromProperties = false;
                boolean parentIsRoot = false;
                if (i != -1) {
                    final String locName = localeID.substring(0, i);
                    parent = instantiateBundle(baseName, locName, defaultID, root, disableFallback);
                }
                else if (!localeID.isEmpty()) {
                    parent = instantiateBundle(baseName, "", defaultID, root, disableFallback);
                    parentIsRoot = true;
                }
                ResourceBundleWrapper b = null;
                try {
                    final Class<? extends ResourceBundle> cls = root.loadClass(name).asSubclass(ResourceBundle.class);
                    final ResourceBundle bx = (ResourceBundle)cls.newInstance();
                    b = new ResourceBundleWrapper(bx, null);
                    if (parent != null) {
                        b.setParent(parent);
                    }
                    b.baseName = baseName;
                    b.localeID = localeID;
                }
                catch (ClassNotFoundException e2) {
                    loadFromProperties = true;
                }
                catch (NoClassDefFoundError e3) {
                    loadFromProperties = true;
                }
                catch (Exception e) {
                    if (ResourceBundleWrapper.DEBUG) {
                        System.out.println("failure");
                    }
                    if (ResourceBundleWrapper.DEBUG) {
                        System.out.println(e);
                    }
                }
                if (loadFromProperties) {
                    try {
                        final String resName = name.replace('.', '/') + ".properties";
                        InputStream stream = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction<InputStream>() {
                            @Override
                            public InputStream run() {
                                return root.getResourceAsStream(resName);
                            }
                        });
                        if (stream != null) {
                            stream = new BufferedInputStream(stream);
                            try {
                                b = new ResourceBundleWrapper(new PropertyResourceBundle(stream), null);
                                if (parent != null) {
                                    b.setParent(parent);
                                }
                                b.baseName = baseName;
                                b.localeID = localeID;
                            }
                            catch (Exception ex) {}
                            finally {
                                try {
                                    stream.close();
                                }
                                catch (Exception ex2) {}
                            }
                        }
                        if (b == null && !disableFallback && !localeID.isEmpty() && localeID.indexOf(95) < 0 && !localeIDStartsWithLangSubtag(defaultID, localeID)) {
                            b = instantiateBundle(baseName, defaultID, defaultID, root, disableFallback);
                        }
                        if (b == null && (!parentIsRoot || !disableFallback)) {
                            b = parent;
                        }
                    }
                    catch (Exception e) {
                        if (ResourceBundleWrapper.DEBUG) {
                            System.out.println("failure");
                        }
                        if (ResourceBundleWrapper.DEBUG) {
                            System.out.println(e);
                        }
                    }
                }
                if (b != null) {
                    b.initKeysVector();
                }
                else if (ResourceBundleWrapper.DEBUG) {
                    System.out.println("Returning null for " + baseName + "_" + localeID);
                }
                return b;
            }
        });
    }
    
    static {
        ResourceBundleWrapper.BUNDLE_CACHE = new SoftCache<String, ResourceBundleWrapper, Loader>() {
            @Override
            protected ResourceBundleWrapper createInstance(final String unusedKey, final Loader loader) {
                return loader.load();
            }
        };
        DEBUG = ICUDebug.enabled("resourceBundleWrapper");
    }
    
    private abstract static class Loader
    {
        abstract ResourceBundleWrapper load();
    }
}
