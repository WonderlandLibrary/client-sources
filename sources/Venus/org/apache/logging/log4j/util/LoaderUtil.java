/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Objects;
import org.apache.logging.log4j.util.LowLevelLogUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class LoaderUtil {
    public static final String IGNORE_TCCL_PROPERTY = "log4j.ignoreTCL";
    private static final SecurityManager SECURITY_MANAGER = System.getSecurityManager();
    private static Boolean ignoreTCCL;
    private static final boolean GET_CLASS_LOADER_DISABLED;
    private static final PrivilegedAction<ClassLoader> TCCL_GETTER;

    private LoaderUtil() {
    }

    public static ClassLoader getThreadContextClassLoader() {
        if (GET_CLASS_LOADER_DISABLED) {
            return LoaderUtil.class.getClassLoader();
        }
        return SECURITY_MANAGER == null ? TCCL_GETTER.run() : AccessController.doPrivileged(TCCL_GETTER);
    }

    public static boolean isClassAvailable(String string) {
        try {
            Class<?> clazz = LoaderUtil.loadClass(string);
            return clazz != null;
        } catch (ClassNotFoundException classNotFoundException) {
            return true;
        } catch (Throwable throwable) {
            LowLevelLogUtil.logException("Unknown error checking for existence of class: " + string, throwable);
            return true;
        }
    }

    public static Class<?> loadClass(String string) throws ClassNotFoundException {
        if (LoaderUtil.isIgnoreTccl()) {
            return Class.forName(string);
        }
        try {
            return LoaderUtil.getThreadContextClassLoader().loadClass(string);
        } catch (Throwable throwable) {
            return Class.forName(string);
        }
    }

    public static <T> T newInstanceOf(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        try {
            return clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (NoSuchMethodException noSuchMethodException) {
            return clazz.newInstance();
        }
    }

    public static <T> T newInstanceOf(String string) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return (T)LoaderUtil.newInstanceOf(LoaderUtil.loadClass(string));
    }

    public static <T> T newCheckedInstanceOf(String string, Class<T> clazz) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clazz.cast(LoaderUtil.newInstanceOf(string));
    }

    public static <T> T newCheckedInstanceOfProperty(String string, Class<T> clazz) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String string2 = PropertiesUtil.getProperties().getStringProperty(string);
        if (string2 == null) {
            return null;
        }
        return LoaderUtil.newCheckedInstanceOf(string2, clazz);
    }

    private static boolean isIgnoreTccl() {
        if (ignoreTCCL == null) {
            String string = PropertiesUtil.getProperties().getStringProperty(IGNORE_TCCL_PROPERTY, null);
            ignoreTCCL = string != null && !"false".equalsIgnoreCase(string.trim());
        }
        return ignoreTCCL;
    }

    public static Collection<URL> findResources(String string) {
        Collection<UrlResource> collection = LoaderUtil.findUrlResources(string);
        LinkedHashSet<URL> linkedHashSet = new LinkedHashSet<URL>(collection.size());
        for (UrlResource urlResource : collection) {
            linkedHashSet.add(urlResource.getUrl());
        }
        return linkedHashSet;
    }

    static Collection<UrlResource> findUrlResources(String string) {
        ClassLoader[] classLoaderArray = new ClassLoader[]{LoaderUtil.getThreadContextClassLoader(), LoaderUtil.class.getClassLoader(), GET_CLASS_LOADER_DISABLED ? null : ClassLoader.getSystemClassLoader()};
        LinkedHashSet<UrlResource> linkedHashSet = new LinkedHashSet<UrlResource>();
        for (ClassLoader classLoader : classLoaderArray) {
            if (classLoader == null) continue;
            try {
                Enumeration<URL> enumeration = classLoader.getResources(string);
                while (enumeration.hasMoreElements()) {
                    linkedHashSet.add(new UrlResource(classLoader, enumeration.nextElement()));
                }
            } catch (IOException iOException) {
                LowLevelLogUtil.logException(iOException);
            }
        }
        return linkedHashSet;
    }

    static boolean access$100() {
        return GET_CLASS_LOADER_DISABLED;
    }

    static {
        TCCL_GETTER = new ThreadContextClassLoaderGetter(null);
        if (SECURITY_MANAGER != null) {
            boolean bl;
            try {
                SECURITY_MANAGER.checkPermission(new RuntimePermission("getClassLoader"));
                bl = false;
            } catch (SecurityException securityException) {
                bl = true;
            }
            GET_CLASS_LOADER_DISABLED = bl;
        } else {
            GET_CLASS_LOADER_DISABLED = false;
        }
    }

    static class 1 {
    }

    static class UrlResource {
        private final ClassLoader classLoader;
        private final URL url;

        UrlResource(ClassLoader classLoader, URL uRL) {
            this.classLoader = classLoader;
            this.url = uRL;
        }

        public ClassLoader getClassLoader() {
            return this.classLoader;
        }

        public URL getUrl() {
            return this.url;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            UrlResource urlResource = (UrlResource)object;
            if (this.classLoader != null ? !this.classLoader.equals(urlResource.classLoader) : urlResource.classLoader != null) {
                return true;
            }
            return this.url != null ? !this.url.equals(urlResource.url) : urlResource.url != null;
        }

        public int hashCode() {
            return Objects.hashCode(this.classLoader) + Objects.hashCode(this.url);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class ThreadContextClassLoaderGetter
    implements PrivilegedAction<ClassLoader> {
        private ThreadContextClassLoaderGetter() {
        }

        @Override
        public ClassLoader run() {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader != null) {
                return classLoader;
            }
            ClassLoader classLoader2 = LoaderUtil.class.getClassLoader();
            return classLoader2 == null && !LoaderUtil.access$100() ? ClassLoader.getSystemClassLoader() : classLoader2;
        }

        @Override
        public Object run() {
            return this.run();
        }

        ThreadContextClassLoaderGetter(1 var1_1) {
            this();
        }
    }
}

