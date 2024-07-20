/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

import com.google.gson.internal.$Gson$Preconditions;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Primitives {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;

    private Primitives() {
        throw new UnsupportedOperationException();
    }

    private static void add(Map<Class<?>, Class<?>> forward, Map<Class<?>, Class<?>> backward, Class<?> key, Class<?> value) {
        forward.put(key, value);
        backward.put(value, key);
    }

    public static boolean isPrimitive(Type type2) {
        return PRIMITIVE_TO_WRAPPER_TYPE.containsKey(type2);
    }

    public static boolean isWrapperType(Type type2) {
        return WRAPPER_TO_PRIMITIVE_TYPE.containsKey($Gson$Preconditions.checkNotNull(type2));
    }

    public static <T> Class<T> wrap(Class<T> type2) {
        Class<?> wrapped = PRIMITIVE_TO_WRAPPER_TYPE.get($Gson$Preconditions.checkNotNull(type2));
        return wrapped == null ? type2 : wrapped;
    }

    public static <T> Class<T> unwrap(Class<T> type2) {
        Class<?> unwrapped = WRAPPER_TO_PRIMITIVE_TYPE.get($Gson$Preconditions.checkNotNull(type2));
        return unwrapped == null ? type2 : unwrapped;
    }

    static {
        HashMap primToWrap = new HashMap(16);
        HashMap wrapToPrim = new HashMap(16);
        Primitives.add(primToWrap, wrapToPrim, Boolean.TYPE, Boolean.class);
        Primitives.add(primToWrap, wrapToPrim, Byte.TYPE, Byte.class);
        Primitives.add(primToWrap, wrapToPrim, Character.TYPE, Character.class);
        Primitives.add(primToWrap, wrapToPrim, Double.TYPE, Double.class);
        Primitives.add(primToWrap, wrapToPrim, Float.TYPE, Float.class);
        Primitives.add(primToWrap, wrapToPrim, Integer.TYPE, Integer.class);
        Primitives.add(primToWrap, wrapToPrim, Long.TYPE, Long.class);
        Primitives.add(primToWrap, wrapToPrim, Short.TYPE, Short.class);
        Primitives.add(primToWrap, wrapToPrim, Void.TYPE, Void.class);
        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(primToWrap);
        WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(wrapToPrim);
    }
}

