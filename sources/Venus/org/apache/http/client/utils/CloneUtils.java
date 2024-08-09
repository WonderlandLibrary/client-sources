/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CloneUtils {
    public static <T> T cloneObject(T t) throws CloneNotSupportedException {
        if (t == null) {
            return null;
        }
        if (t instanceof Cloneable) {
            Method method;
            Class<?> clazz = t.getClass();
            try {
                method = clazz.getMethod("clone", null);
            } catch (NoSuchMethodException noSuchMethodException) {
                throw new NoSuchMethodError(noSuchMethodException.getMessage());
            }
            try {
                Object object = method.invoke(t, null);
                return (T)object;
            } catch (InvocationTargetException invocationTargetException) {
                Throwable throwable = invocationTargetException.getCause();
                if (throwable instanceof CloneNotSupportedException) {
                    throw (CloneNotSupportedException)throwable;
                }
                throw new Error("Unexpected exception", throwable);
            } catch (IllegalAccessException illegalAccessException) {
                throw new IllegalAccessError(illegalAccessException.getMessage());
            }
        }
        throw new CloneNotSupportedException();
    }

    public static Object clone(Object object) throws CloneNotSupportedException {
        return CloneUtils.cloneObject(object);
    }

    private CloneUtils() {
    }
}

