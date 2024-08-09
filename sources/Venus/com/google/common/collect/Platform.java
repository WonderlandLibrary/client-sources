/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.MapMaker;
import java.lang.reflect.Array;

@GwtCompatible(emulated=true)
final class Platform {
    static <T> T[] newArray(T[] TArray, int n) {
        Class<?> clazz = TArray.getClass().getComponentType();
        Object[] objectArray = (Object[])Array.newInstance(clazz, n);
        return objectArray;
    }

    static MapMaker tryWeakKeys(MapMaker mapMaker) {
        return mapMaker.weakKeys();
    }

    private Platform() {
    }
}

