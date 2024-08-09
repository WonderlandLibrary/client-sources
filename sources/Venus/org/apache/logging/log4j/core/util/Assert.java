/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.util.Collection;
import java.util.Map;

public final class Assert {
    private Assert() {
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof CharSequence) {
            return ((CharSequence)object).length() == 0;
        }
        if (object.getClass().isArray()) {
            return ((Object[])object).length == 0;
        }
        if (object instanceof Collection) {
            return ((Collection)object).isEmpty();
        }
        if (object instanceof Map) {
            return ((Map)object).isEmpty();
        }
        return true;
    }

    public static boolean isNonEmpty(Object object) {
        return !Assert.isEmpty(object);
    }

    public static <T> T requireNonEmpty(T t) {
        return Assert.requireNonEmpty(t, "");
    }

    public static <T> T requireNonEmpty(T t, String string) {
        if (Assert.isEmpty(t)) {
            throw new IllegalArgumentException(string);
        }
        return t;
    }

    public static int valueIsAtLeast(int n, int n2) {
        if (n < n2) {
            throw new IllegalArgumentException("Value should be at least " + n2 + " but was " + n);
        }
        return n;
    }
}

