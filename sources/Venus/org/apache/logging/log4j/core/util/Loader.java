/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;

public final class Loader {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";

    private Loader() {
    }

    public static ClassLoader getClassLoader() {
        return Loader.getClassLoader(Loader.class, null);
    }

    public static ClassLoader getThreadContextClassLoader() {
        return LoaderUtil.getThreadContextClassLoader();
    }

    public static ClassLoader getClassLoader(Class<?> clazz, Class<?> clazz2) {
        ClassLoader classLoader;
        ClassLoader classLoader2 = Loader.getThreadContextClassLoader();
        ClassLoader classLoader3 = clazz == null ? null : clazz.getClassLoader();
        ClassLoader classLoader4 = classLoader = clazz2 == null ? null : clazz2.getClassLoader();
        if (Loader.isChild(classLoader2, classLoader3)) {
            return Loader.isChild(classLoader2, classLoader) ? classLoader2 : classLoader;
        }
        return Loader.isChild(classLoader3, classLoader) ? classLoader3 : classLoader;
    }

    public static URL getResource(String string, ClassLoader classLoader) {
        try {
            URL uRL;
            ClassLoader classLoader2 = Loader.getThreadContextClassLoader();
            if (classLoader2 != null) {
                LOGGER.trace("Trying to find [{}] using context class loader {}.", (Object)string, (Object)classLoader2);
                uRL = classLoader2.getResource(string);
                if (uRL != null) {
                    return uRL;
                }
            }
            if ((classLoader2 = Loader.class.getClassLoader()) != null) {
                LOGGER.trace("Trying to find [{}] using {} class loader.", (Object)string, (Object)classLoader2);
                uRL = classLoader2.getResource(string);
                if (uRL != null) {
                    return uRL;
                }
            }
            if (classLoader != null) {
                LOGGER.trace("Trying to find [{}] using {} class loader.", (Object)string, (Object)classLoader);
                uRL = classLoader.getResource(string);
                if (uRL != null) {
                    return uRL;
                }
            }
        } catch (Throwable throwable) {
            LOGGER.warn(TSTR, throwable);
        }
        LOGGER.trace("Trying to find [{}] using ClassLoader.getSystemResource().", (Object)string);
        return ClassLoader.getSystemResource(string);
    }

    public static InputStream getResourceAsStream(String string, ClassLoader classLoader) {
        try {
            InputStream inputStream;
            ClassLoader classLoader2 = Loader.getThreadContextClassLoader();
            if (classLoader2 != null) {
                LOGGER.trace("Trying to find [{}] using context class loader {}.", (Object)string, (Object)classLoader2);
                inputStream = classLoader2.getResourceAsStream(string);
                if (inputStream != null) {
                    return inputStream;
                }
            }
            if ((classLoader2 = Loader.class.getClassLoader()) != null) {
                LOGGER.trace("Trying to find [{}] using {} class loader.", (Object)string, (Object)classLoader2);
                inputStream = classLoader2.getResourceAsStream(string);
                if (inputStream != null) {
                    return inputStream;
                }
            }
            if (classLoader != null) {
                LOGGER.trace("Trying to find [{}] using {} class loader.", (Object)string, (Object)classLoader);
                inputStream = classLoader.getResourceAsStream(string);
                if (inputStream != null) {
                    return inputStream;
                }
            }
        } catch (Throwable throwable) {
            LOGGER.warn(TSTR, throwable);
        }
        LOGGER.trace("Trying to find [{}] using ClassLoader.getSystemResource().", (Object)string);
        return ClassLoader.getSystemResourceAsStream(string);
    }

    private static boolean isChild(ClassLoader classLoader, ClassLoader classLoader2) {
        if (classLoader != null && classLoader2 != null) {
            ClassLoader classLoader3;
            for (classLoader3 = classLoader.getParent(); classLoader3 != null && classLoader3 != classLoader2; classLoader3 = classLoader3.getParent()) {
            }
            return classLoader3 != null;
        }
        return classLoader != null;
    }

    public static Class<?> initializeClass(String string, ClassLoader classLoader) throws ClassNotFoundException {
        return Class.forName(string, true, classLoader);
    }

    public static Class<?> loadClass(String string, ClassLoader classLoader) throws ClassNotFoundException {
        return classLoader != null ? classLoader.loadClass(string) : null;
    }

    public static Class<?> loadSystemClass(String string) throws ClassNotFoundException {
        try {
            return Class.forName(string, true, ClassLoader.getSystemClassLoader());
        } catch (Throwable throwable) {
            LOGGER.trace("Couldn't use SystemClassLoader. Trying Class.forName({}).", (Object)string, (Object)throwable);
            return Class.forName(string);
        }
    }

    public static Object newInstanceOf(String string) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return LoaderUtil.newInstanceOf(string);
    }

    public static <T> T newCheckedInstanceOf(String string, Class<T> clazz) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return LoaderUtil.newCheckedInstanceOf(string, clazz);
    }

    public static boolean isClassAvailable(String string) {
        return LoaderUtil.isClassAvailable(string);
    }

    public static boolean isJansiAvailable() {
        return Loader.isClassAvailable("org.fusesource.jansi.AnsiRenderer");
    }
}

