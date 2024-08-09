/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.InstantiationException;
import io.jsonwebtoken.lang.UnknownClassException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public final class Classes {
    private static final ClassLoaderAccessor THREAD_CL_ACCESSOR = new ExceptionIgnoringAccessor(){

        @Override
        protected ClassLoader doGetClassLoader() {
            return Thread.currentThread().getContextClassLoader();
        }
    };
    private static final ClassLoaderAccessor CLASS_CL_ACCESSOR = new ExceptionIgnoringAccessor(){

        @Override
        protected ClassLoader doGetClassLoader() {
            return Classes.class.getClassLoader();
        }
    };
    private static final ClassLoaderAccessor SYSTEM_CL_ACCESSOR = new ExceptionIgnoringAccessor(){

        @Override
        protected ClassLoader doGetClassLoader() {
            return ClassLoader.getSystemClassLoader();
        }
    };

    private Classes() {
    }

    public static <T> Class<T> forName(String string) throws UnknownClassException {
        Class<?> clazz = THREAD_CL_ACCESSOR.loadClass(string);
        if (clazz == null) {
            clazz = CLASS_CL_ACCESSOR.loadClass(string);
        }
        if (clazz == null) {
            clazz = SYSTEM_CL_ACCESSOR.loadClass(string);
        }
        if (clazz == null) {
            String string2 = "Unable to load class named [" + string + "] from the thread context, current, or " + "system/application ClassLoaders.  All heuristics have been exhausted.  Class could not be found.";
            if (string != null && string.startsWith("io.jsonwebtoken.impl")) {
                string2 = string2 + "  Have you remembered to include the jjwt-impl.jar in your runtime classpath?";
            }
            throw new UnknownClassException(string2);
        }
        return clazz;
    }

    public static InputStream getResourceAsStream(String string) {
        InputStream inputStream = THREAD_CL_ACCESSOR.getResourceStream(string);
        if (inputStream == null) {
            inputStream = CLASS_CL_ACCESSOR.getResourceStream(string);
        }
        if (inputStream == null) {
            inputStream = SYSTEM_CL_ACCESSOR.getResourceStream(string);
        }
        return inputStream;
    }

    private static URL getResource(String string) {
        URL uRL = THREAD_CL_ACCESSOR.getResource(string);
        if (uRL == null) {
            uRL = CLASS_CL_ACCESSOR.getResource(string);
        }
        if (uRL == null) {
            return SYSTEM_CL_ACCESSOR.getResource(string);
        }
        return uRL;
    }

    public static boolean isAvailable(String string) {
        try {
            Classes.forName(string);
            return true;
        } catch (UnknownClassException unknownClassException) {
            return true;
        }
    }

    public static <T> T newInstance(String string) {
        return Classes.newInstance(Classes.forName(string));
    }

    public static <T> T newInstance(String string, Class<?>[] classArray, Object ... objectArray) {
        Class<T> clazz = Classes.forName(string);
        Constructor<T> constructor = Classes.getConstructor(clazz, classArray);
        return Classes.instantiate(constructor, objectArray);
    }

    public static <T> T newInstance(String string, Object ... objectArray) {
        return Classes.newInstance(Classes.forName(string), objectArray);
    }

    public static <T> T newInstance(Class<T> clazz) {
        if (clazz == null) {
            String string = "Class method parameter cannot be null.";
            throw new IllegalArgumentException(string);
        }
        try {
            return clazz.newInstance();
        } catch (Exception exception) {
            throw new InstantiationException("Unable to instantiate class [" + clazz.getName() + "]", exception);
        }
    }

    public static <T> T newInstance(Class<T> clazz, Object ... objectArray) {
        Class[] classArray = new Class[objectArray.length];
        for (int i = 0; i < objectArray.length; ++i) {
            classArray[i] = objectArray[i].getClass();
        }
        Constructor<T> constructor = Classes.getConstructor(clazz, classArray);
        return Classes.instantiate(constructor, objectArray);
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?> ... classArray) throws IllegalStateException {
        try {
            return clazz.getConstructor(classArray);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalStateException(noSuchMethodException);
        }
    }

    public static <T> T instantiate(Constructor<T> constructor, Object ... objectArray) {
        try {
            return constructor.newInstance(objectArray);
        } catch (Exception exception) {
            String string = "Unable to instantiate instance with constructor [" + constructor + "]";
            throw new InstantiationException(string, exception);
        }
    }

    public static <T> T invokeStatic(String string, String string2, Class<?>[] classArray, Object ... objectArray) {
        try {
            Class<T> clazz = Classes.forName(string);
            return Classes.invokeStatic(clazz, string2, classArray, objectArray);
        } catch (Exception exception) {
            String string3 = "Unable to invoke class method " + string + "#" + string2 + ".  Ensure the necessary " + "implementation is in the runtime classpath.";
            throw new IllegalStateException(string3, exception);
        }
    }

    public static <T> T invokeStatic(Class<?> clazz, String string, Class<?>[] classArray, Object ... objectArray) {
        try {
            Method method = clazz.getDeclaredMethod(string, classArray);
            method.setAccessible(false);
            return (T)method.invoke(null, objectArray);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            Throwable throwable = reflectiveOperationException.getCause();
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException)throwable;
            }
            String string2 = "Unable to invoke class method " + clazz.getName() + "#" + string + ". Ensure the necessary implementation is in the runtime classpath.";
            throw new IllegalStateException(string2, reflectiveOperationException);
        }
    }

    public static <T> T getFieldValue(Object object, String string, Class<T> clazz) {
        if (object == null) {
            return null;
        }
        try {
            Field field = object.getClass().getDeclaredField(string);
            field.setAccessible(false);
            Object object2 = field.get(object);
            return clazz.cast(object2);
        } catch (Throwable throwable) {
            String string2 = "Unable to read field " + object.getClass().getName() + "#" + string + ": " + throwable.getMessage();
            throw new IllegalStateException(string2, throwable);
        }
    }

    private static abstract class ExceptionIgnoringAccessor
    implements ClassLoaderAccessor {
        private ExceptionIgnoringAccessor() {
        }

        @Override
        public Class<?> loadClass(String string) {
            Class<?> clazz = null;
            ClassLoader classLoader = this.getClassLoader();
            if (classLoader != null) {
                try {
                    clazz = classLoader.loadClass(string);
                } catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
            }
            return clazz;
        }

        @Override
        public URL getResource(String string) {
            URL uRL = null;
            ClassLoader classLoader = this.getClassLoader();
            if (classLoader != null) {
                uRL = classLoader.getResource(string);
            }
            return uRL;
        }

        @Override
        public InputStream getResourceStream(String string) {
            InputStream inputStream = null;
            ClassLoader classLoader = this.getClassLoader();
            if (classLoader != null) {
                inputStream = classLoader.getResourceAsStream(string);
            }
            return inputStream;
        }

        protected final ClassLoader getClassLoader() {
            try {
                return this.doGetClassLoader();
            } catch (Throwable throwable) {
                return null;
            }
        }

        protected abstract ClassLoader doGetClassLoader() throws Throwable;

        ExceptionIgnoringAccessor(1 var1_1) {
            this();
        }
    }

    private static interface ClassLoaderAccessor {
        public Class<?> loadClass(String var1);

        public URL getResource(String var1);

        public InputStream getResourceStream(String var1);
    }
}

