/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ComponentSerializer<I extends Component, O extends Component, R> {
    @NotNull
    public O deserialize(@NotNull R var1);

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(value="!null -> !null; null -> null", pure=true)
    @Nullable
    default public O deseializeOrNull(@Nullable R r) {
        return this.deserializeOrNull(r);
    }

    @Contract(value="!null -> !null; null -> null", pure=true)
    @Nullable
    default public O deserializeOrNull(@Nullable R r) {
        return this.deserializeOr(r, null);
    }

    @Contract(value="!null, _ -> !null; null, _ -> param2", pure=true)
    @Nullable
    default public O deserializeOr(@Nullable R r, @Nullable O o) {
        if (r == null) {
            return o;
        }
        return this.deserialize(r);
    }

    @NotNull
    public R serialize(@NotNull I var1);

    @Contract(value="!null -> !null; null -> null", pure=true)
    @Nullable
    default public R serializeOrNull(@Nullable I i) {
        return this.serializeOr(i, null);
    }

    @Contract(value="!null, _ -> !null; null, _ -> param2", pure=true)
    @Nullable
    default public R serializeOr(@Nullable I i, @Nullable R r) {
        if (i == null) {
            return r;
        }
        return this.serialize(i);
    }
}

