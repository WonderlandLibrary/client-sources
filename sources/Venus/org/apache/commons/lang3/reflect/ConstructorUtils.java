/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.MemberUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

public class ConstructorUtils {
    public static <T> T invokeConstructor(Class<T> clazz, Object ... objectArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Class<?>[] classArray = ClassUtils.toClass(objectArray);
        return ConstructorUtils.invokeConstructor(clazz, objectArray, classArray);
    }

    public static <T> T invokeConstructor(Class<T> clazz, Object[] objectArray, Class<?>[] classArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Constructor<T> constructor = ConstructorUtils.getMatchingAccessibleConstructor(clazz, classArray = ArrayUtils.nullToEmpty(classArray));
        if (constructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + clazz.getName());
        }
        if (constructor.isVarArgs()) {
            Class<?>[] classArray2 = constructor.getParameterTypes();
            objectArray = MethodUtils.getVarArgs(objectArray, classArray2);
        }
        return constructor.newInstance(objectArray);
    }

    public static <T> T invokeExactConstructor(Class<T> clazz, Object ... objectArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Class<?>[] classArray = ClassUtils.toClass(objectArray);
        return ConstructorUtils.invokeExactConstructor(clazz, objectArray, classArray);
    }

    public static <T> T invokeExactConstructor(Class<T> clazz, Object[] objectArray, Class<?>[] classArray) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        objectArray = ArrayUtils.nullToEmpty(objectArray);
        Constructor<T> constructor = ConstructorUtils.getAccessibleConstructor(clazz, classArray = ArrayUtils.nullToEmpty(classArray));
        if (constructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + clazz.getName());
        }
        return constructor.newInstance(objectArray);
    }

    public static <T> Constructor<T> getAccessibleConstructor(Class<T> clazz, Class<?> ... classArray) {
        Validate.notNull(clazz, "class cannot be null", new Object[0]);
        try {
            return ConstructorUtils.getAccessibleConstructor(clazz.getConstructor(classArray));
        } catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    public static <T> Constructor<T> getAccessibleConstructor(Constructor<T> constructor) {
        Validate.notNull(constructor, "constructor cannot be null", new Object[0]);
        return MemberUtils.isAccessible(constructor) && ConstructorUtils.isAccessible(constructor.getDeclaringClass()) ? constructor : null;
    }

    public static <T> Constructor<T> getMatchingAccessibleConstructor(Class<T> clazz, Class<?> ... classArray) {
        Validate.notNull(clazz, "class cannot be null", new Object[0]);
        try {
            Constructor<T> constructor = clazz.getConstructor(classArray);
            MemberUtils.setAccessibleWorkaround(constructor);
            return constructor;
        } catch (NoSuchMethodException noSuchMethodException) {
            Constructor<?>[] constructorArray;
            Constructor<?> constructor = null;
            for (Constructor<?> constructor2 : constructorArray = clazz.getConstructors()) {
                Constructor<?> constructor3;
                if (!MemberUtils.isMatchingConstructor(constructor2, classArray) || (constructor2 = ConstructorUtils.getAccessibleConstructor(constructor2)) == null) continue;
                MemberUtils.setAccessibleWorkaround(constructor2);
                if (constructor != null && MemberUtils.compareConstructorFit(constructor2, constructor, classArray) >= 0) continue;
                constructor = constructor3 = constructor2;
            }
            return constructor;
        }
    }

    private static boolean isAccessible(Class<?> clazz) {
        for (Class<?> clazz2 = clazz; clazz2 != null; clazz2 = clazz2.getEnclosingClass()) {
            if (Modifier.isPublic(clazz2.getModifiers())) continue;
            return true;
        }
        return false;
    }
}

