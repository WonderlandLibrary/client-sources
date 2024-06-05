/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.internal;

import java.lang.reflect.Type;

public final class Primitives {
    private Primitives() {
    }

    public static boolean isPrimitive(Type type) {
        if (!(type instanceof Class)) return false;
        if (!((Class)type).isPrimitive()) return false;
        return true;
    }

    public static boolean isWrapperType(Type type) {
        if (type == Integer.class) return true;
        if (type == Float.class) return true;
        if (type == Byte.class) return true;
        if (type == Double.class) return true;
        if (type == Long.class) return true;
        if (type == Character.class) return true;
        if (type == Boolean.class) return true;
        if (type == Short.class) return true;
        if (type == Void.class) return true;
        return false;
    }

    public static <T> Class<T> wrap(Class<T> type) {
        if (type == Integer.TYPE) {
            return Integer.class;
        }
        if (type == Float.TYPE) {
            return Float.class;
        }
        if (type == Byte.TYPE) {
            return Byte.class;
        }
        if (type == Double.TYPE) {
            return Double.class;
        }
        if (type == Long.TYPE) {
            return Long.class;
        }
        if (type == Character.TYPE) {
            return Character.class;
        }
        if (type == Boolean.TYPE) {
            return Boolean.class;
        }
        if (type == Short.TYPE) {
            return Short.class;
        }
        if (type != Void.TYPE) return type;
        return Void.class;
    }

    public static <T> Class<T> unwrap(Class<T> type) {
        if (type == Integer.class) {
            return Integer.TYPE;
        }
        if (type == Float.class) {
            return Float.TYPE;
        }
        if (type == Byte.class) {
            return Byte.TYPE;
        }
        if (type == Double.class) {
            return Double.TYPE;
        }
        if (type == Long.class) {
            return Long.TYPE;
        }
        if (type == Character.class) {
            return Character.TYPE;
        }
        if (type == Boolean.class) {
            return Boolean.TYPE;
        }
        if (type == Short.class) {
            return Short.TYPE;
        }
        if (type != Void.class) return type;
        return Void.TYPE;
    }
}

