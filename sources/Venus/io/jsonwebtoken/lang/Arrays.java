/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Objects;
import java.lang.reflect.Array;
import java.util.List;

public final class Arrays {
    private Arrays() {
    }

    public static <T> int length(T[] TArray) {
        return TArray == null ? 0 : TArray.length;
    }

    public static <T> List<T> asList(T[] TArray) {
        return Objects.isEmpty(TArray) ? Collections.emptyList() : java.util.Arrays.asList(TArray);
    }

    public static int length(byte[] byArray) {
        return byArray != null ? byArray.length : 0;
    }

    public static byte[] clean(byte[] byArray) {
        return (byte[])(Arrays.length(byArray) > 0 ? byArray : null);
    }

    public static Object copy(Object object) {
        if (object == null) {
            return null;
        }
        Assert.isTrue(Objects.isArray(object), "Argument must be an array.");
        if (object instanceof Object[]) {
            return ((Object[])object).clone();
        }
        if (object instanceof boolean[]) {
            return ((boolean[])object).clone();
        }
        if (object instanceof byte[]) {
            return ((byte[])object).clone();
        }
        if (object instanceof char[]) {
            return ((char[])object).clone();
        }
        if (object instanceof double[]) {
            return ((double[])object).clone();
        }
        if (object instanceof float[]) {
            return ((float[])object).clone();
        }
        if (object instanceof int[]) {
            return ((int[])object).clone();
        }
        if (object instanceof long[]) {
            return ((long[])object).clone();
        }
        if (object instanceof short[]) {
            return ((short[])object).clone();
        }
        Class<?> clazz = object.getClass().getComponentType();
        int n = Array.getLength(object);
        Object[] objectArray = (Object[])Array.newInstance(clazz, n);
        for (int i = 0; i < n; ++i) {
            objectArray[i] = Array.get(object, i);
        }
        return objectArray;
    }
}

