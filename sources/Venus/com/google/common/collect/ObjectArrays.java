/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Platform;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class ObjectArrays {
    static final Object[] EMPTY_ARRAY = new Object[0];

    private ObjectArrays() {
    }

    @GwtIncompatible
    public static <T> T[] newArray(Class<T> clazz, int n) {
        return (Object[])Array.newInstance(clazz, n);
    }

    public static <T> T[] newArray(T[] TArray, int n) {
        return Platform.newArray(TArray, n);
    }

    @GwtIncompatible
    public static <T> T[] concat(T[] TArray, T[] TArray2, Class<T> clazz) {
        T[] TArray3 = ObjectArrays.newArray(clazz, TArray.length + TArray2.length);
        System.arraycopy(TArray, 0, TArray3, 0, TArray.length);
        System.arraycopy(TArray2, 0, TArray3, TArray.length, TArray2.length);
        return TArray3;
    }

    public static <T> T[] concat(@Nullable T t, T[] TArray) {
        T[] TArray2 = ObjectArrays.newArray(TArray, TArray.length + 1);
        TArray2[0] = t;
        System.arraycopy(TArray, 0, TArray2, 1, TArray.length);
        return TArray2;
    }

    public static <T> T[] concat(T[] TArray, @Nullable T t) {
        T[] TArray2 = Arrays.copyOf(TArray, TArray.length + 1);
        TArray2[TArray.length] = t;
        return TArray2;
    }

    static <T> T[] toArrayImpl(Collection<?> collection, T[] TArray) {
        int n = collection.size();
        if (TArray.length < n) {
            TArray = ObjectArrays.newArray(TArray, n);
        }
        ObjectArrays.fillArray(collection, TArray);
        if (TArray.length > n) {
            TArray[n] = null;
        }
        return TArray;
    }

    static <T> T[] toArrayImpl(Object[] objectArray, int n, int n2, T[] TArray) {
        Preconditions.checkPositionIndexes(n, n + n2, objectArray.length);
        if (TArray.length < n2) {
            TArray = ObjectArrays.newArray(TArray, n2);
        } else if (TArray.length > n2) {
            TArray[n2] = null;
        }
        System.arraycopy(objectArray, n, TArray, 0, n2);
        return TArray;
    }

    static Object[] toArrayImpl(Collection<?> collection) {
        return ObjectArrays.fillArray(collection, new Object[collection.size()]);
    }

    static Object[] copyAsObjectArray(Object[] objectArray, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, objectArray.length);
        if (n2 == 0) {
            return EMPTY_ARRAY;
        }
        Object[] objectArray2 = new Object[n2];
        System.arraycopy(objectArray, n, objectArray2, 0, n2);
        return objectArray2;
    }

    @CanIgnoreReturnValue
    private static Object[] fillArray(Iterable<?> iterable, Object[] objectArray) {
        int n = 0;
        for (Object obj : iterable) {
            objectArray[n++] = obj;
        }
        return objectArray;
    }

    static void swap(Object[] objectArray, int n, int n2) {
        Object object = objectArray[n];
        objectArray[n] = objectArray[n2];
        objectArray[n2] = object;
    }

    @CanIgnoreReturnValue
    static Object[] checkElementsNotNull(Object ... objectArray) {
        return ObjectArrays.checkElementsNotNull(objectArray, objectArray.length);
    }

    @CanIgnoreReturnValue
    static Object[] checkElementsNotNull(Object[] objectArray, int n) {
        for (int i = 0; i < n; ++i) {
            ObjectArrays.checkElementNotNull(objectArray[i], i);
        }
        return objectArray;
    }

    @CanIgnoreReturnValue
    static Object checkElementNotNull(Object object, int n) {
        if (object == null) {
            throw new NullPointerException("at index " + n);
        }
        return object;
    }
}

