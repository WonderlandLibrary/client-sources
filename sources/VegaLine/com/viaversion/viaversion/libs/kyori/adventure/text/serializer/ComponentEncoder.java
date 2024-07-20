/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ComponentEncoder<I extends Component, R> {
    @NotNull
    public R serialize(@NotNull I var1);

    @Contract(value="!null -> !null; null -> null", pure=true)
    @Nullable
    default public R serializeOrNull(@Nullable I component) {
        return this.serializeOr(component, null);
    }

    @Contract(value="!null, _ -> !null; null, _ -> param2", pure=true)
    @Nullable
    default public R serializeOr(@Nullable I component, @Nullable R fallback) {
        if (component == null) {
            return fallback;
        }
        return this.serialize(component);
    }
}

