/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal;

import java.lang.reflect.Type;

public final class Primitives {
    private Primitives() {
    }

    public static boolean isPrimitive(Type type2) {
        return type2 instanceof Class && ((Class)type2).isPrimitive();
    }

    public static boolean isWrapperType(Type type2) {
        return type2 == Integer.class || type2 == Float.class || type2 == Byte.class || type2 == Double.class || type2 == Long.class || type2 == Character.class || type2 == Boolean.class || type2 == Short.class || type2 == Void.class;
    }

    public static <T> Class<T> wrap(Class<T> type2) {
        if (type2 == Integer.TYPE) {
            return Integer.class;
        }
        if (type2 == Float.TYPE) {
            return Float.class;
        }
        if (type2 == Byte.TYPE) {
            return Byte.class;
        }
        if (type2 == Double.TYPE) {
            return Double.class;
        }
        if (type2 == Long.TYPE) {
            return Long.class;
        }
        if (type2 == Character.TYPE) {
            return Character.class;
        }
        if (type2 == Boolean.TYPE) {
            return Boolean.class;
        }
        if (type2 == Short.TYPE) {
            return Short.class;
        }
        if (type2 == Void.TYPE) {
            return Void.class;
        }
        return type2;
    }

    public static <T> Class<T> unwrap(Class<T> type2) {
        if (type2 == Integer.class) {
            return Integer.TYPE;
        }
        if (type2 == Float.class) {
            return Float.TYPE;
        }
        if (type2 == Byte.class) {
            return Byte.TYPE;
        }
        if (type2 == Double.class) {
            return Double.TYPE;
        }
        if (type2 == Long.class) {
            return Long.TYPE;
        }
        if (type2 == Character.class) {
            return Character.TYPE;
        }
        if (type2 == Boolean.class) {
            return Boolean.TYPE;
        }
        if (type2 == Short.class) {
            return Short.TYPE;
        }
        if (type2 == Void.class) {
            return Void.TYPE;
        }
        return type2;
    }
}

