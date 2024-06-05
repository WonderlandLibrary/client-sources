package com.ibm.icu.impl;

import java.util.logging.*;
import java.net.*;
import java.security.*;
import java.io.*;
import java.util.*;

public final class ICUData
{
    static final String ICU_DATA_PATH = "com/ibm/icu/impl/";
    static final String PACKAGE_NAME = "icudt62b";
    public static final String ICU_BUNDLE = "data/icudt62b";
    public static final String ICU_BASE_NAME = "com/ibm/icu/impl/data/icudt62b";
    public static final String ICU_COLLATION_BASE_NAME = "com/ibm/icu/impl/data/icudt62b/coll";
    public static final String ICU_BRKITR_NAME = "brkitr";
    public static final String ICU_BRKITR_BASE_NAME = "com/ibm/icu/impl/data/icudt62b/brkitr";
    public static final String ICU_RBNF_BASE_NAME = "com/ibm/icu/impl/data/icudt62b/rbnf";
    public static final String ICU_TRANSLIT_BASE_NAME = "com/ibm/icu/impl/data/icudt62b/translit";
    public static final String ICU_LANG_BASE_NAME = "com/ibm/icu/impl/data/icudt62b/lang";
    public static final String ICU_CURR_BASE_NAME = "com/ibm/icu/impl/data/icudt62b/curr";
    public static final String ICU_REGION_BASE_NAME = "com/ibm/icu/impl/data/icudt62b/region";
    public static final String ICU_ZONE_BASE_NAME = "com/ibm/icu/impl/data/icudt62b/zone";
    public static final String ICU_UNIT_BASE_NAME = "com/ibm/icu/impl/data/icudt62b/unit";
    private static final boolean logBinaryDataFromInputStream = false;
    private static final Logger logger;
    
    public static boolean exists(final String resourceName) {
        URL i = null;
        if (System.getSecurityManager() != null) {
            i = AccessController.doPrivileged((PrivilegedAction<URL>)new PrivilegedAction<URL>() {
                @Override
                public URL run() {
                    return ICUData.class.getResource(resourceName);
                }
            });
        }
        else {
            i = ICUData.class.getResource(resourceName);
        }
        return i != null;
    }
    
    private static InputStream getStream(final Class<?> root, final String resourceName, final boolean required) {
        InputStream i = null;
        if (System.getSecurityManager() != null) {
            i = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction<InputStream>() {
                @Override
                public InputStream run() {
                    return root.getResourceAsStream(resourceName);
                }
            });
        }
        else {
            i = root.getResourceAsStream(resourceName);
        }
        if (i == null && required) {
            throw new MissingResourceException("could not locate data " + resourceName, root.getPackage().getName(), resourceName);
        }
        checkStreamForBinaryData(i, resourceName);
        return i;
    }
    
    static InputStream getStream(final ClassLoader loader, final String resourceName, final boolean required) {
        InputStream i = null;
        if (System.getSecurityManager() != null) {
            i = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction<InputStream>() {
                @Override
                public InputStream run() {
                    return loader.getResourceAsStream(resourceName);
                }
            });
        }
        else {
            i = loader.getResourceAsStream(resourceName);
        }
        if (i == null && required) {
            throw new MissingResourceException("could not locate data", loader.toString(), resourceName);
        }
        checkStreamForBinaryData(i, resourceName);
        return i;
    }
    
    private static void checkStreamForBinaryData(final InputStream is, final String resourceName) {
    }
    
    public static InputStream getStream(final ClassLoader loader, final String resourceName) {
        return getStream(loader, resourceName, false);
    }
    
    public static InputStream getRequiredStream(final ClassLoader loader, final String resourceName) {
        return getStream(loader, resourceName, true);
    }
    
    public static InputStream getStream(final String resourceName) {
        return getStream(ICUData.class, resourceName, false);
    }
    
    public static InputStream getRequiredStream(final String resourceName) {
        return getStream(ICUData.class, resourceName, true);
    }
    
    public static InputStream getStream(final Class<?> root, final String resourceName) {
        return getStream(root, resourceName, false);
    }
    
    public static InputStream getRequiredStream(final Class<?> root, final String resourceName) {
        return getStream(root, resourceName, true);
    }
    
    static {
        logger = null;
    }
}
