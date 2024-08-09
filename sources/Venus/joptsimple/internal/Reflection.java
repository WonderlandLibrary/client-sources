/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import joptsimple.ValueConverter;
import joptsimple.internal.Classes;
import joptsimple.internal.ConstructorInvokingValueConverter;
import joptsimple.internal.MethodInvokingValueConverter;
import joptsimple.internal.ReflectionException;

public final class Reflection {
    private Reflection() {
        throw new UnsupportedOperationException();
    }

    public static <V> ValueConverter<V> findConverter(Class<V> clazz) {
        Class<V> clazz2 = Classes.wrapperOf(clazz);
        ValueConverter<V> valueConverter = Reflection.valueOfConverter(clazz2);
        if (valueConverter != null) {
            return valueConverter;
        }
        ValueConverter<V> valueConverter2 = Reflection.constructorConverter(clazz2);
        if (valueConverter2 != null) {
            return valueConverter2;
        }
        throw new IllegalArgumentException(clazz + " is not a value type");
    }

    private static <V> ValueConverter<V> valueOfConverter(Class<V> clazz) {
        try {
            Method method = clazz.getMethod("valueOf", String.class);
            if (Reflection.meetsConverterRequirements(method, clazz)) {
                return new MethodInvokingValueConverter<V>(method, clazz);
            }
            return null;
        } catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    private static <V> ValueConverter<V> constructorConverter(Class<V> clazz) {
        try {
            return new ConstructorInvokingValueConverter<V>(clazz.getConstructor(String.class));
        } catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    public static <T> T instantiate(Constructor<T> constructor, Object ... objectArray) {
        try {
            return constructor.newInstance(objectArray);
        } catch (Exception exception) {
            throw Reflection.reflectionException(exception);
        }
    }

    public static Object invoke(Method method, Object ... objectArray) {
        try {
            return method.invoke(null, objectArray);
        } catch (Exception exception) {
            throw Reflection.reflectionException(exception);
        }
    }

    public static <V> V convertWith(ValueConverter<V> valueConverter, String string) {
        return (V)(valueConverter == null ? string : valueConverter.convert(string));
    }

    private static boolean meetsConverterRequirements(Method method, Class<?> clazz) {
        int n = method.getModifiers();
        return Modifier.isPublic(n) && Modifier.isStatic(n) && clazz.equals(method.getReturnType());
    }

    private static RuntimeException reflectionException(Exception exception) {
        if (exception instanceof IllegalArgumentException) {
            return new ReflectionException(exception);
        }
        if (exception instanceof InvocationTargetException) {
            return new ReflectionException(exception.getCause());
        }
        if (exception instanceof RuntimeException) {
            return (RuntimeException)exception;
        }
        return new ReflectionException(exception);
    }
}

