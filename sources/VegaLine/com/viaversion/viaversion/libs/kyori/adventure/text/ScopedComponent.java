/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ScopedComponent<C extends Component>
extends Component {
    @NotNull
    public C children(@NotNull List<? extends ComponentLike> var1);

    @NotNull
    public C style(@NotNull Style var1);

    @NotNull
    default public C style(@NotNull Consumer<Style.Builder> style) {
        return (C)Component.super.style(style);
    }

    @NotNull
    default public C style(@NotNull Style.Builder style) {
        return (C)Component.super.style(style);
    }

    @NotNull
    default public C mergeStyle(@NotNull Component that) {
        return (C)Component.super.mergeStyle(that);
    }

    @NotNull
    default public C mergeStyle(@NotNull Component that, @NotNull Style.Merge @NotNull ... merges) {
        return (C)Component.super.mergeStyle(that, merges);
    }

    @NotNull
    default public C append(@NotNull Component component) {
        return (C)Component.super.append(component);
    }

    @NotNull
    default public C append(@NotNull ComponentLike like) {
        return (C)Component.super.append(like);
    }

    @NotNull
    default public C append(@NotNull ComponentBuilder<?, ?> builder) {
        return (C)Component.super.append(builder);
    }

    @NotNull
    default public C mergeStyle(@NotNull Component that, @NotNull Set<Style.Merge> merges) {
        return (C)Component.super.mergeStyle(that, merges);
    }

    @Override
    @NotNull
    default public C color(@Nullable TextColor color) {
        return (C)Component.super.color(color);
    }

    @Override
    @NotNull
    default public C colorIfAbsent(@Nullable TextColor color) {
        return (C)Component.super.colorIfAbsent(color);
    }

    @Override
    @NotNull
    default public C decorate(@NotNull TextDecoration decoration) {
        return (C)Component.super.decorate(decoration);
    }

    @Override
    @NotNull
    default public C decoration(@NotNull TextDecoration decoration, boolean flag) {
        return (C)Component.super.decoration(decoration, flag);
    }

    @Override
    @NotNull
    default public C decoration(@NotNull TextDecoration decoration, @NotNull TextDecoration.State state) {
        return (C)Component.super.decoration(decoration, state);
    }

    @Override
    @NotNull
    default public C clickEvent(@Nullable ClickEvent event) {
        return (C)Component.super.clickEvent(event);
    }

    @Override
    @NotNull
    default public C hoverEvent(@Nullable HoverEventSource<?> event) {
        return (C)Component.super.hoverEvent((HoverEventSource)event);
    }

    @Override
    @NotNull
    default public C insertion(@Nullable String insertion) {
        return (C)Component.super.insertion(insertion);
    }
}

