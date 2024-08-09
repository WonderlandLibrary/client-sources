/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@GwtIncompatible
public final class Defaults {
    private static final Map<Class<?>, Object> DEFAULTS;

    private Defaults() {
    }

    private static <T> void put(Map<Class<?>, Object> map, Class<T> clazz, T t) {
        map.put(clazz, t);
    }

    @Nullable
    public static <T> T defaultValue(Class<T> clazz) {
        Object object = DEFAULTS.get(Preconditions.checkNotNull(clazz));
        return (T)object;
    }

    static {
        HashMap hashMap = new HashMap();
        Defaults.put(hashMap, Boolean.TYPE, false);
        Defaults.put(hashMap, Character.TYPE, Character.valueOf('\u0000'));
        Defaults.put(hashMap, Byte.TYPE, (byte)0);
        Defaults.put(hashMap, Short.TYPE, (short)0);
        Defaults.put(hashMap, Integer.TYPE, 0);
        Defaults.put(hashMap, Long.TYPE, 0L);
        Defaults.put(hashMap, Float.TYPE, Float.valueOf(0.0f));
        Defaults.put(hashMap, Double.TYPE, 0.0);
        DEFAULTS = Collections.unmodifiableMap(hashMap);
    }
}

