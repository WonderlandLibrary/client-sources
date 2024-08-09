/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 */
package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
import com.viaversion.viaversion.libs.kyori.adventure.key.KeyedValueImpl;
import java.util.Objects;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface KeyedValue<T>
extends Keyed {
    @NotNull
    public static <T> KeyedValue<T> keyedValue(@NotNull Key key, @NotNull T t) {
        return new KeyedValueImpl<T>(key, Objects.requireNonNull(t, "value"));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static <T> KeyedValue<T> of(@NotNull Key key, @NotNull T t) {
        return new KeyedValueImpl<T>(key, Objects.requireNonNull(t, "value"));
    }

    @NotNull
    public T value();
}

