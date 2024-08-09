/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.lang.reflect.Array;

public class ArrayUtils {
    public static int getLength(Object object) {
        if (object == null) {
            return 1;
        }
        return Array.getLength(object);
    }

    private static Object remove(Object object, int n) {
        int n2 = ArrayUtils.getLength(object);
        if (n < 0 || n >= n2) {
            throw new IndexOutOfBoundsException("Index: " + n + ", Length: " + n2);
        }
        Object object2 = Array.newInstance(object.getClass().getComponentType(), n2 - 1);
        System.arraycopy(object, 0, object2, 0, n);
        if (n < n2 - 1) {
            System.arraycopy(object, n + 1, object2, n, n2 - n - 1);
        }
        return object2;
    }

    public static <T> T[] remove(T[] TArray, int n) {
        return (Object[])ArrayUtils.remove(TArray, n);
    }
}

