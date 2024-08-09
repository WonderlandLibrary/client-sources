/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public final class MonkeyBars {
    private MonkeyBars() {
    }

    @SafeVarargs
    @NotNull
    public static <E extends Enum<E>> Set<E> enumSet(Class<E> clazz, E @NotNull ... EArray) {
        EnumSet<E> enumSet = EnumSet.noneOf(clazz);
        Collections.addAll(enumSet, EArray);
        return Collections.unmodifiableSet(enumSet);
    }

    @NotNull
    public static <T> List<T> addOne(@NotNull List<T> list, T t) {
        if (list.isEmpty()) {
            return Collections.singletonList(t);
        }
        ArrayList<T> arrayList = new ArrayList<T>(list.size() + 1);
        arrayList.addAll(list);
        arrayList.add(t);
        return Collections.unmodifiableList(arrayList);
    }
}

