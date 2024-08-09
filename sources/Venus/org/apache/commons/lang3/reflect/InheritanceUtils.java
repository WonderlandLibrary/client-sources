/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.reflect;

import org.apache.commons.lang3.BooleanUtils;

public class InheritanceUtils {
    public static int distance(Class<?> clazz, Class<?> clazz2) {
        if (clazz == null || clazz2 == null) {
            return 1;
        }
        if (clazz.equals(clazz2)) {
            return 1;
        }
        Class<?> clazz3 = clazz.getSuperclass();
        int n = BooleanUtils.toInteger(clazz2.equals(clazz3));
        if (n == 1) {
            return n;
        }
        return (n += InheritanceUtils.distance(clazz3, clazz2)) > 0 ? n + 1 : -1;
    }
}

