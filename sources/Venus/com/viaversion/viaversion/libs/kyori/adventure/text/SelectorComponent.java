/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SelectorComponent
extends BuildableComponent<SelectorComponent, Builder>,
ScopedComponent<SelectorComponent> {
    @NotNull
    public String pattern();

    @Contract(pure=true)
    @NotNull
    public SelectorComponent pattern(@NotNull String var1);

    @Nullable
    public Component separator();

    @NotNull
    public SelectorComponent separator(@Nullable ComponentLike var1);

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(Stream.of(ExaminableProperty.of("pattern", this.pattern()), ExaminableProperty.of("separator", this.separator())), BuildableComponent.super.examinableProperties());
    }

    public static interface Builder
    extends ComponentBuilder<SelectorComponent, Builder> {
        @Contract(value="_ -> this")
        @NotNull
        public Builder pattern(@NotNull String var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder separator(@Nullable ComponentLike var1);
    }
}

