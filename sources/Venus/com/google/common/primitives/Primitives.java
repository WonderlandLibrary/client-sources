/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@GwtIncompatible
public final class Primitives {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;

    private Primitives() {
    }

    private static void add(Map<Class<?>, Class<?>> map, Map<Class<?>, Class<?>> map2, Class<?> clazz, Class<?> clazz2) {
        map.put(clazz, clazz2);
        map2.put(clazz2, clazz);
    }

    public static Set<Class<?>> allPrimitiveTypes() {
        return PRIMITIVE_TO_WRAPPER_TYPE.keySet();
    }

    public static Set<Class<?>> allWrapperTypes() {
        return WRAPPER_TO_PRIMITIVE_TYPE.keySet();
    }

    public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TO_PRIMITIVE_TYPE.containsKey(Preconditions.checkNotNull(clazz));
    }

    public static <T> Class<T> wrap(Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        Class<?> clazz2 = PRIMITIVE_TO_WRAPPER_TYPE.get(clazz);
        return clazz2 == null ? clazz : clazz2;
    }

    public static <T> Class<T> unwrap(Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        Class<?> clazz2 = WRAPPER_TO_PRIMITIVE_TYPE.get(clazz);
        return clazz2 == null ? clazz : clazz2;
    }

    static {
        HashMap hashMap = new HashMap(16);
        HashMap hashMap2 = new HashMap(16);
        Primitives.add(hashMap, hashMap2, Boolean.TYPE, Boolean.class);
        Primitives.add(hashMap, hashMap2, Byte.TYPE, Byte.class);
        Primitives.add(hashMap, hashMap2, Character.TYPE, Character.class);
        Primitives.add(hashMap, hashMap2, Double.TYPE, Double.class);
        Primitives.add(hashMap, hashMap2, Float.TYPE, Float.class);
        Primitives.add(hashMap, hashMap2, Integer.TYPE, Integer.class);
        Primitives.add(hashMap, hashMap2, Long.TYPE, Long.class);
        Primitives.add(hashMap, hashMap2, Short.TYPE, Short.class);
        Primitives.add(hashMap, hashMap2, Void.TYPE, Void.class);
        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(hashMap);
        WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(hashMap2);
    }
}

