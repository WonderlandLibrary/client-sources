/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import java.util.function.Consumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Buildable<R, B extends Builder<R>> {
    @Deprecated
    @Contract(mutates="param1")
    @NotNull
    public static <R extends Buildable<R, B>, B extends Builder<R>> R configureAndBuild(@NotNull B b, @Nullable Consumer<? super B> consumer) {
        return (R)((Buildable)AbstractBuilder.configureAndBuild(b, consumer));
    }

    @Contract(value="-> new", pure=true)
    @NotNull
    public B toBuilder();

    @Deprecated
    public static interface Builder<R>
    extends AbstractBuilder<R> {
        @Override
        @Contract(value="-> new", pure=true)
        @NotNull
        public R build();
    }
}

