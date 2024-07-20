/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface TextComponent
extends BuildableComponent<TextComponent, Builder>,
ScopedComponent<TextComponent> {
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static TextComponent ofChildren(@NotNull @NotNull ComponentLike @NotNull ... components) {
        return Component.textOfChildren(components);
    }

    @NotNull
    public String content();

    @Contract(pure=true)
    @NotNull
    public TextComponent content(@NotNull String var1);

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(Stream.of(ExaminableProperty.of("content", this.content())), BuildableComponent.super.examinableProperties());
    }

    public static interface Builder
    extends ComponentBuilder<TextComponent, Builder> {
        @NotNull
        public String content();

        @Contract(value="_ -> this")
        @NotNull
        public Builder content(@NotNull String var1);
    }
}

