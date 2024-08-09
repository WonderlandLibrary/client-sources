/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.MissingResourceException;
import java.util.logging.Logger;

public final class ICUData {
    static final String ICU_DATA_PATH = "com/ibm/icu/impl/";
    static final String PACKAGE_NAME = "icudt66b";
    public static final String ICU_BUNDLE = "data/icudt66b";
    public static final String ICU_BASE_NAME = "com/ibm/icu/impl/data/icudt66b";
    public static final String ICU_COLLATION_BASE_NAME = "com/ibm/icu/impl/data/icudt66b/coll";
    public static final String ICU_BRKITR_NAME = "brkitr";
    public static final String ICU_BRKITR_BASE_NAME = "com/ibm/icu/impl/data/icudt66b/brkitr";
    public static final String ICU_RBNF_BASE_NAME = "com/ibm/icu/impl/data/icudt66b/rbnf";
    public static final String ICU_TRANSLIT_BASE_NAME = "com/ibm/icu/impl/data/icudt66b/translit";
    public static final String ICU_LANG_BASE_NAME = "com/ibm/icu/impl/data/icudt66b/lang";
    public static final String ICU_CURR_BASE_NAME = "com/ibm/icu/impl/data/icudt66b/curr";
    public static final String ICU_REGION_BASE_NAME = "com/ibm/icu/impl/data/icudt66b/region";
    public static final String ICU_ZONE_BASE_NAME = "com/ibm/icu/impl/data/icudt66b/zone";
    public static final String ICU_UNIT_BASE_NAME = "com/ibm/icu/impl/data/icudt66b/unit";
    private static final boolean logBinaryDataFromInputStream = false;
    private static final Logger logger = null;

    public static boolean exists(String string) {
        URL uRL = null;
        uRL = System.getSecurityManager() != null ? AccessController.doPrivileged(new PrivilegedAction<URL>(string){
            final String val$resourceName;
            {
                this.val$resourceName = string;
            }

            @Override
            public URL run() {
                return ICUData.class.getResource(this.val$resourceName);
            }

            @Override
            public Object run() {
                return this.run();
            }
        }) : ICUData.class.getResource(string);
        return uRL != null;
    }

    private static InputStream getStream(Class<?> clazz, String string, boolean bl) {
        InputStream inputStream = null;
        inputStream = System.getSecurityManager() != null ? AccessController.doPrivileged(new PrivilegedAction<InputStream>(clazz, string){
            final Class val$root;
            final String val$resourceName;
            {
                this.val$root = clazz;
                this.val$resourceName = string;
            }

            @Override
            public InputStream run() {
                return this.val$root.getResourceAsStream(this.val$resourceName);
            }

            @Override
            public Object run() {
                return this.run();
            }
        }) : clazz.getResourceAsStream(string);
        if (inputStream == null && bl) {
            throw new MissingResourceException("could not locate data " + string, clazz.getPackage().getName(), string);
        }
        ICUData.checkStreamForBinaryData(inputStream, string);
        return inputStream;
    }

    static InputStream getStream(ClassLoader classLoader, String string, boolean bl) {
        InputStream inputStream = null;
        inputStream = System.getSecurityManager() != null ? AccessController.doPrivileged(new PrivilegedAction<InputStream>(classLoader, string){
            final ClassLoader val$loader;
            final String val$resourceName;
            {
                this.val$loader = classLoader;
                this.val$resourceName = string;
            }

            @Override
            public InputStream run() {
                return this.val$loader.getResourceAsStream(this.val$resourceName);
            }

            @Override
            public Object run() {
                return this.run();
            }
        }) : classLoader.getResourceAsStream(string);
        if (inputStream == null && bl) {
            throw new MissingResourceException("could not locate data", classLoader.toString(), string);
        }
        ICUData.checkStreamForBinaryData(inputStream, string);
        return inputStream;
    }

    private static void checkStreamForBinaryData(InputStream inputStream, String string) {
    }

    public static InputStream getStream(ClassLoader classLoader, String string) {
        return ICUData.getStream(classLoader, string, false);
    }

    public static InputStream getRequiredStream(ClassLoader classLoader, String string) {
        return ICUData.getStream(classLoader, string, true);
    }

    public static InputStream getStream(String string) {
        return ICUData.getStream(ICUData.class, string, false);
    }

    public static InputStream getRequiredStream(String string) {
        return ICUData.getStream(ICUData.class, string, true);
    }

    public static InputStream getStream(Class<?> clazz, String string) {
        return ICUData.getStream(clazz, string, false);
    }

    public static InputStream getRequiredStream(Class<?> clazz, String string) {
        return ICUData.getStream(clazz, string, true);
    }
}

