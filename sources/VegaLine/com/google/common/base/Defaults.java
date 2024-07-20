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

    private static <T> void put(Map<Class<?>, Object> map, Class<T> type2, T value) {
        map.put(type2, value);
    }

    @Nullable
    public static <T> T defaultValue(Class<T> type2) {
        Object t = DEFAULTS.get(Preconditions.checkNotNull(type2));
        return (T)t;
    }

    static {
        HashMap map = new HashMap();
        Defaults.put(map, Boolean.TYPE, false);
        Defaults.put(map, Character.TYPE, Character.valueOf('\u0000'));
        Defaults.put(map, Byte.TYPE, (byte)0);
        Defaults.put(map, Short.TYPE, (short)0);
        Defaults.put(map, Integer.TYPE, 0);
        Defaults.put(map, Long.TYPE, 0L);
        Defaults.put(map, Float.TYPE, Float.valueOf(0.0f));
        Defaults.put(map, Double.TYPE, 0.0);
        DEFAULTS = Collections.unmodifiableMap(map);
    }
}

