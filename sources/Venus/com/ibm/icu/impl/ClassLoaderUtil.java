/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class ClassLoaderUtil {
    private static volatile ClassLoader BOOTSTRAP_CLASSLOADER;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static ClassLoader getBootstrapClassLoader() {
        if (BOOTSTRAP_CLASSLOADER != null) return BOOTSTRAP_CLASSLOADER;
        Class<ClassLoaderUtil> clazz = ClassLoaderUtil.class;
        synchronized (ClassLoaderUtil.class) {
            if (BOOTSTRAP_CLASSLOADER != null) return BOOTSTRAP_CLASSLOADER;
            ClassLoader classLoader = null;
            classLoader = System.getSecurityManager() != null ? AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public BootstrapClassLoader run() {
                    return new BootstrapClassLoader();
                }

                @Override
                public Object run() {
                    return this.run();
                }
            }) : new BootstrapClassLoader();
            BOOTSTRAP_CLASSLOADER = classLoader;
            // ** MonitorExit[var0] (shouldn't be in output)
            return BOOTSTRAP_CLASSLOADER;
        }
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoaderUtil.getClassLoader();
        }
        return classLoader;
    }

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null && (classLoader = ClassLoader.getSystemClassLoader()) == null) {
            classLoader = ClassLoaderUtil.getBootstrapClassLoader();
        }
        return classLoader;
    }

    private static class BootstrapClassLoader
    extends ClassLoader {
        BootstrapClassLoader() {
            super(Object.class.getClassLoader());
        }
    }
}

