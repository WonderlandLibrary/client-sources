/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ComponentLike {
    @NotNull
    public static List<Component> asComponents(@NotNull List<? extends ComponentLike> list) {
        return ComponentLike.asComponents(list, null);
    }

    @NotNull
    public static List<Component> asComponents(@NotNull List<? extends ComponentLike> list, @Nullable Predicate<? super Component> predicate) {
        Objects.requireNonNull(list, "likes");
        int n = list.size();
        if (n == 0) {
            return Collections.emptyList();
        }
        @Nullable ArrayList<Component> arrayList = null;
        for (int i = 0; i < n; ++i) {
            @Nullable ComponentLike componentLike = list.get(i);
            if (componentLike == null) {
                throw new NullPointerException("likes[" + i + "]");
            }
            Component component = componentLike.asComponent();
            if (predicate != null && !predicate.test(component)) continue;
            if (arrayList == null) {
                arrayList = new ArrayList<Component>(n);
            }
            arrayList.add(component);
        }
        if (arrayList == null) {
            return Collections.emptyList();
        }
        arrayList.trimToSize();
        return Collections.unmodifiableList(arrayList);
    }

    @Nullable
    public static Component unbox(@Nullable ComponentLike componentLike) {
        return componentLike != null ? componentLike.asComponent() : null;
    }

    @Contract(pure=true)
    @NotNull
    public Component asComponent();
}

