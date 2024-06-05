package com.ibm.icu.impl;

import java.security.*;

public class ClassLoaderUtil
{
    private static volatile ClassLoader BOOTSTRAP_CLASSLOADER;
    
    private static ClassLoader getBootstrapClassLoader() {
        if (ClassLoaderUtil.BOOTSTRAP_CLASSLOADER == null) {
            synchronized (ClassLoaderUtil.class) {
                if (ClassLoaderUtil.BOOTSTRAP_CLASSLOADER == null) {
                    ClassLoader cl = null;
                    if (System.getSecurityManager() != null) {
                        cl = AccessController.doPrivileged((PrivilegedAction<ClassLoader>)new PrivilegedAction<ClassLoader>() {
                            @Override
                            public BootstrapClassLoader run() {
                                return new BootstrapClassLoader();
                            }
                        });
                    }
                    else {
                        cl = new BootstrapClassLoader();
                    }
                    ClassLoaderUtil.BOOTSTRAP_CLASSLOADER = cl;
                }
            }
        }
        return ClassLoaderUtil.BOOTSTRAP_CLASSLOADER;
    }
    
    public static ClassLoader getClassLoader(final Class<?> cls) {
        ClassLoader cl = cls.getClassLoader();
        if (cl == null) {
            cl = getClassLoader();
        }
        return cl;
    }
    
    public static ClassLoader getClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
            if (cl == null) {
                cl = getBootstrapClassLoader();
            }
        }
        return cl;
    }
    
    private static class BootstrapClassLoader extends ClassLoader
    {
        BootstrapClassLoader() {
            super(Object.class.getClassLoader());
        }
    }
}
