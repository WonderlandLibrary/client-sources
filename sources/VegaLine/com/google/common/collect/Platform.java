/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.MapMaker;
import java.lang.reflect.Array;

@GwtCompatible(emulated=true)
final class Platform {
    static <T> T[] newArray(T[] reference, int length) {
        Class<?> type2 = reference.getClass().getComponentType();
        Object[] result = (Object[])Array.newInstance(type2, length);
        return result;
    }

    static MapMaker tryWeakKeys(MapMaker mapMaker) {
        return mapMaker.weakKeys();
    }

    private Platform() {
    }
}

