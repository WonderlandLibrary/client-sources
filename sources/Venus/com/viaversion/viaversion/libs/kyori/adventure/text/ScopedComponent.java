/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
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
    default public C style(@NotNull Consumer<Style.Builder> consumer) {
        return (C)Component.super.style(consumer);
    }

    @NotNull
    default public C style(@NotNull Style.Builder builder) {
        return (C)Component.super.style(builder);
    }

    @NotNull
    default public C mergeStyle(@NotNull Component component) {
        return (C)Component.super.mergeStyle(component);
    }

    @NotNull
    default public C mergeStyle(@NotNull Component component, @NotNull Style.Merge @NotNull ... mergeArray) {
        return (C)Component.super.mergeStyle(component, mergeArray);
    }

    @NotNull
    default public C append(@NotNull Component component) {
        return (C)Component.super.append(component);
    }

    @NotNull
    default public C append(@NotNull ComponentLike componentLike) {
        return (C)Component.super.append(componentLike);
    }

    @NotNull
    default public C append(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        return (C)Component.super.append(componentBuilder);
    }

    @NotNull
    default public C mergeStyle(@NotNull Component component, @NotNull Set<Style.Merge> set) {
        return (C)Component.super.mergeStyle(component, set);
    }

    @Override
    @NotNull
    default public C color(@Nullable TextColor textColor) {
        return (C)Component.super.color(textColor);
    }

    @Override
    @NotNull
    default public C colorIfAbsent(@Nullable TextColor textColor) {
        return (C)Component.super.colorIfAbsent(textColor);
    }

    @Override
    @NotNull
    default public C decorate(@NotNull TextDecoration textDecoration) {
        return (C)Component.super.decorate(textDecoration);
    }

    @Override
    @NotNull
    default public C decoration(@NotNull TextDecoration textDecoration, boolean bl) {
        return (C)Component.super.decoration(textDecoration, bl);
    }

    @Override
    @NotNull
    default public C decoration(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return (C)Component.super.decoration(textDecoration, state);
    }

    @Override
    @NotNull
    default public C clickEvent(@Nullable ClickEvent clickEvent) {
        return (C)Component.super.clickEvent(clickEvent);
    }

    @Override
    @NotNull
    default public C hoverEvent(@Nullable HoverEventSource<?> hoverEventSource) {
        return (C)Component.super.hoverEvent((HoverEventSource)hoverEventSource);
    }

    @Override
    @NotNull
    default public C insertion(@Nullable String string) {
        return (C)Component.super.insertion(string);
    }

    @Override
    @NotNull
    default public StyleSetter insertion(@Nullable String string) {
        return this.insertion(string);
    }

    @Override
    @NotNull
    default public StyleSetter hoverEvent(@Nullable HoverEventSource hoverEventSource) {
        return this.hoverEvent(hoverEventSource);
    }

    @Override
    @NotNull
    default public StyleSetter clickEvent(@Nullable ClickEvent clickEvent) {
        return this.clickEvent(clickEvent);
    }

    @Override
    @NotNull
    default public StyleSetter decoration(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return this.decoration(textDecoration, state);
    }

    @Override
    @NotNull
    default public StyleSetter decoration(@NotNull TextDecoration textDecoration, boolean bl) {
        return this.decoration(textDecoration, bl);
    }

    @Override
    @NotNull
    default public StyleSetter decorate(@NotNull TextDecoration textDecoration) {
        return this.decorate(textDecoration);
    }

    @Override
    @NotNull
    default public StyleSetter colorIfAbsent(@Nullable TextColor textColor) {
        return this.colorIfAbsent(textColor);
    }

    @Override
    @NotNull
    default public StyleSetter color(@Nullable TextColor textColor) {
        return this.color(textColor);
    }
}

