/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.SortedArrayStringMap;
import org.apache.logging.log4j.util.StringMap;

public class ContextDataFactory {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final String CLASS_NAME = PropertiesUtil.getProperties().getStringProperty("log4j2.ContextData");
    private static final Class<? extends StringMap> CACHED_CLASS = ContextDataFactory.createCachedClass(CLASS_NAME);
    private static final MethodHandle DEFAULT_CONSTRUCTOR = ContextDataFactory.createDefaultConstructor(CACHED_CLASS);
    private static final MethodHandle INITIAL_CAPACITY_CONSTRUCTOR = ContextDataFactory.createInitialCapacityConstructor(CACHED_CLASS);
    private static final StringMap EMPTY_STRING_MAP = ContextDataFactory.createContextData(1);

    private static Class<? extends StringMap> createCachedClass(String string) {
        if (string == null) {
            return null;
        }
        try {
            return LoaderUtil.loadClass(string).asSubclass(StringMap.class);
        } catch (Exception exception) {
            return null;
        }
    }

    private static MethodHandle createDefaultConstructor(Class<? extends StringMap> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return LOOKUP.findConstructor(clazz, MethodType.methodType(Void.TYPE));
        } catch (IllegalAccessException | NoSuchMethodException reflectiveOperationException) {
            return null;
        }
    }

    private static MethodHandle createInitialCapacityConstructor(Class<? extends StringMap> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return LOOKUP.findConstructor(clazz, MethodType.methodType(Void.TYPE, Integer.TYPE));
        } catch (IllegalAccessException | NoSuchMethodException reflectiveOperationException) {
            return null;
        }
    }

    public static StringMap createContextData() {
        if (DEFAULT_CONSTRUCTOR == null) {
            return new SortedArrayStringMap();
        }
        try {
            return DEFAULT_CONSTRUCTOR.invoke();
        } catch (Throwable throwable) {
            return new SortedArrayStringMap();
        }
    }

    public static StringMap createContextData(int n) {
        if (INITIAL_CAPACITY_CONSTRUCTOR == null) {
            return new SortedArrayStringMap(n);
        }
        try {
            return INITIAL_CAPACITY_CONSTRUCTOR.invoke(n);
        } catch (Throwable throwable) {
            return new SortedArrayStringMap(n);
        }
    }

    public static StringMap emptyFrozenContextData() {
        return EMPTY_STRING_MAP;
    }

    static {
        EMPTY_STRING_MAP.freeze();
    }
}

