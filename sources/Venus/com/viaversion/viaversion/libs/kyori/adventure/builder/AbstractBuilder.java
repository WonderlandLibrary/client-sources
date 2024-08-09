/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.builder;

import java.util.function.Consumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface AbstractBuilder<R> {
    @Contract(mutates="param1")
    @NotNull
    public static <R, B extends AbstractBuilder<R>> R configureAndBuild(@NotNull B b, @Nullable Consumer<? super B> consumer) {
        if (consumer != null) {
            consumer.accept(b);
        }
        return b.build();
    }

    @Contract(value="-> new", pure=true)
    @NotNull
    public R build();
}

