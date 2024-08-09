/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Objects;
import org.apache.logging.log4j.core.util.Throwables;

public final class ReflectionUtil {
    private ReflectionUtil() {
    }

    public static <T extends AccessibleObject> boolean isAccessible(T t) {
        Objects.requireNonNull(t, "No member provided");
        return Modifier.isPublic(((Member)((Object)t)).getModifiers()) && Modifier.isPublic(((Member)((Object)t)).getDeclaringClass().getModifiers());
    }

    public static <T extends AccessibleObject> void makeAccessible(T t) {
        if (!ReflectionUtil.isAccessible(t) && !t.isAccessible()) {
            t.setAccessible(false);
        }
    }

    public static void makeAccessible(Field field) {
        Objects.requireNonNull(field, "No field provided");
        if (!(ReflectionUtil.isAccessible(field) && !Modifier.isFinal(field.getModifiers()) || field.isAccessible())) {
            field.setAccessible(false);
        }
    }

    public static Object getFieldValue(Field field, Object object) {
        ReflectionUtil.makeAccessible(field);
        if (!Modifier.isStatic(field.getModifiers())) {
            Objects.requireNonNull(object, "No instance given for non-static field");
        }
        try {
            return field.get(object);
        } catch (IllegalAccessException illegalAccessException) {
            throw new UnsupportedOperationException(illegalAccessException);
        }
    }

    public static Object getStaticFieldValue(Field field) {
        return ReflectionUtil.getFieldValue(field, null);
    }

    public static void setFieldValue(Field field, Object object, Object object2) {
        ReflectionUtil.makeAccessible(field);
        if (!Modifier.isStatic(field.getModifiers())) {
            Objects.requireNonNull(object, "No instance given for non-static field");
        }
        try {
            field.set(object, object2);
        } catch (IllegalAccessException illegalAccessException) {
            throw new UnsupportedOperationException(illegalAccessException);
        }
    }

    public static void setStaticFieldValue(Field field, Object object) {
        ReflectionUtil.setFieldValue(field, null, object);
    }

    public static <T> Constructor<T> getDefaultConstructor(Class<T> clazz) {
        Objects.requireNonNull(clazz, "No class provided");
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(new Class[0]);
            ReflectionUtil.makeAccessible(constructor);
            return constructor;
        } catch (NoSuchMethodException noSuchMethodException) {
            try {
                Constructor<T> constructor = clazz.getConstructor(new Class[0]);
                ReflectionUtil.makeAccessible(constructor);
                return constructor;
            } catch (NoSuchMethodException noSuchMethodException2) {
                throw new IllegalStateException(noSuchMethodException2);
            }
        }
    }

    public static <T> T instantiate(Class<T> clazz) {
        Objects.requireNonNull(clazz, "No class provided");
        Constructor<T> constructor = ReflectionUtil.getDefaultConstructor(clazz);
        try {
            return constructor.newInstance(new Object[0]);
        } catch (InstantiationException | LinkageError throwable) {
            throw new IllegalArgumentException(throwable);
        } catch (IllegalAccessException illegalAccessException) {
            throw new IllegalStateException(illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            Throwables.rethrow(invocationTargetException.getCause());
            throw new InternalError("Unreachable");
        }
    }
}

