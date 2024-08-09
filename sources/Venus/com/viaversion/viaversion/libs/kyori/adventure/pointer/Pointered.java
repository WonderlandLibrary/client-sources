/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.UnknownNullability
 */
package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

public interface Pointered {
    @NotNull
    default public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
        return this.pointers().get(pointer);
    }

    @Contract(value="_, null -> _; _, !null -> !null")
    @Nullable
    default public <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T t) {
        return this.pointers().getOrDefault(pointer, t);
    }

    default public <T> @UnknownNullability T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> supplier) {
        return this.pointers().getOrDefaultFrom(pointer, supplier);
    }

    @NotNull
    default public Pointers pointers() {
        return Pointers.empty();
    }
}

