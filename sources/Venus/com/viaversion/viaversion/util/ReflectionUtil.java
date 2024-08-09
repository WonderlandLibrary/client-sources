/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionUtil {
    public static Object invokeStatic(Class<?> clazz, String string) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = clazz.getDeclaredMethod(string, new Class[0]);
        return method.invoke(null, new Object[0]);
    }

    public static Object invoke(Object object, String string) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = object.getClass().getDeclaredMethod(string, new Class[0]);
        return method.invoke(object, new Object[0]);
    }

    public static <T> T getStatic(Class<?> clazz, String string, Class<T> clazz2) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(string);
        field.setAccessible(false);
        return clazz2.cast(field.get(null));
    }

    public static void setStatic(Class<?> clazz, String string, Object object) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(string);
        field.setAccessible(false);
        field.set(null, object);
    }

    public static <T> T getSuper(Object object, String string, Class<T> clazz) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getSuperclass().getDeclaredField(string);
        field.setAccessible(false);
        return clazz.cast(field.get(object));
    }

    public static <T> T get(Object object, Class<?> clazz, String string, Class<T> clazz2) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(string);
        field.setAccessible(false);
        return clazz2.cast(field.get(object));
    }

    public static <T> T get(Object object, String string, Class<T> clazz) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(string);
        field.setAccessible(false);
        return clazz.cast(field.get(object));
    }

    public static <T> T getPublic(Object object, String string, Class<T> clazz) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getField(string);
        field.setAccessible(false);
        return clazz.cast(field.get(object));
    }

    public static void set(Object object, String string, Object object2) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(string);
        field.setAccessible(false);
        field.set(object, object2);
    }

    public static final class ClassReflection {
        private final Class<?> handle;
        private final Map<String, Field> fields = new ConcurrentHashMap<String, Field>();
        private final Map<String, Method> methods = new ConcurrentHashMap<String, Method>();

        public ClassReflection(Class<?> clazz) {
            this(clazz, true);
        }

        public ClassReflection(Class<?> clazz, boolean bl) {
            this.handle = clazz;
            this.scanFields(clazz, bl);
            this.scanMethods(clazz, bl);
        }

        private void scanFields(Class<?> clazz, boolean bl) {
            if (bl && clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
                this.scanFields(clazz.getSuperclass(), true);
            }
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(false);
                this.fields.put(field.getName(), field);
            }
        }

        private void scanMethods(Class<?> clazz, boolean bl) {
            if (bl && clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
                this.scanMethods(clazz.getSuperclass(), true);
            }
            for (Method method : clazz.getDeclaredMethods()) {
                method.setAccessible(false);
                this.methods.put(method.getName(), method);
            }
        }

        public Object newInstance() throws ReflectiveOperationException {
            return this.handle.getConstructor(new Class[0]).newInstance(new Object[0]);
        }

        public Field getField(String string) {
            return this.fields.get(string);
        }

        public void setFieldValue(String string, Object object, Object object2) throws IllegalAccessException {
            this.getField(string).set(object, object2);
        }

        public <T> T getFieldValue(String string, Object object, Class<T> clazz) throws IllegalAccessException {
            return clazz.cast(this.getField(string).get(object));
        }

        public <T> T invokeMethod(Class<T> clazz, String string, Object object, Object ... objectArray) throws InvocationTargetException, IllegalAccessException {
            return clazz.cast(this.getMethod(string).invoke(object, objectArray));
        }

        public Method getMethod(String string) {
            return this.methods.get(string);
        }

        public Collection<Field> getFields() {
            return Collections.unmodifiableCollection(this.fields.values());
        }

        public Collection<Method> getMethods() {
            return Collections.unmodifiableCollection(this.methods.values());
        }
    }
}

