/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.MutableStyleSetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ApiStatus.NonExtendable
public interface ComponentBuilder<C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>>
extends AbstractBuilder<C>,
Buildable.Builder<C>,
ComponentBuilderApplicable,
ComponentLike,
MutableStyleSetter<B> {
    @Contract(value="_ -> this")
    @NotNull
    public B append(@NotNull Component var1);

    @Contract(value="_ -> this")
    @NotNull
    default public B append(@NotNull ComponentLike componentLike) {
        return this.append(componentLike.asComponent());
    }

    @Contract(value="_ -> this")
    @NotNull
    default public B append(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        return this.append((Component)componentBuilder.build());
    }

    @Contract(value="_ -> this")
    @NotNull
    public B append(@NotNull @NotNull Component @NotNull ... var1);

    @Contract(value="_ -> this")
    @NotNull
    public B append(@NotNull @NotNull ComponentLike @NotNull ... var1);

    @Contract(value="_ -> this")
    @NotNull
    public B append(@NotNull Iterable<? extends ComponentLike> var1);

    @NotNull
    default public B appendNewline() {
        return this.append((Component)Component.newline());
    }

    @NotNull
    default public B appendSpace() {
        return this.append((Component)Component.space());
    }

    @Contract(value="_ -> this")
    @NotNull
    default public B apply(@NotNull Consumer<? super ComponentBuilder<?, ?>> consumer) {
        consumer.accept(this);
        return (B)this;
    }

    @Contract(value="_ -> this")
    @NotNull
    public B applyDeep(@NotNull Consumer<? super ComponentBuilder<?, ?>> var1);

    @Contract(value="_ -> this")
    @NotNull
    public B mapChildren(@NotNull Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> var1);

    @Contract(value="_ -> this")
    @NotNull
    public B mapChildrenDeep(@NotNull Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> var1);

    @NotNull
    public List<Component> children();

    @Contract(value="_ -> this")
    @NotNull
    public B style(@NotNull Style var1);

    @Contract(value="_ -> this")
    @NotNull
    public B style(@NotNull Consumer<Style.Builder> var1);

    @Override
    @Contract(value="_ -> this")
    @NotNull
    public B font(@Nullable Key var1);

    @Override
    @Contract(value="_ -> this")
    @NotNull
    public B color(@Nullable TextColor var1);

    @Override
    @Contract(value="_ -> this")
    @NotNull
    public B colorIfAbsent(@Nullable TextColor var1);

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    default public B decorations(@NotNull Set<TextDecoration> set, boolean bl) {
        return (B)((ComponentBuilder)MutableStyleSetter.super.decorations((Set)set, bl));
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public B decorate(@NotNull TextDecoration textDecoration) {
        return (B)this.decoration(textDecoration, TextDecoration.State.TRUE);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public B decorate(@NotNull @NotNull TextDecoration @NotNull ... textDecorationArray) {
        return (B)((ComponentBuilder)MutableStyleSetter.super.decorate(textDecorationArray));
    }

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    default public B decoration(@NotNull TextDecoration textDecoration, boolean bl) {
        return (B)this.decoration(textDecoration, TextDecoration.State.byBoolean(bl));
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public B decorations(@NotNull Map<TextDecoration, TextDecoration.State> map) {
        return (B)((ComponentBuilder)MutableStyleSetter.super.decorations((Map)map));
    }

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    public B decoration(@NotNull TextDecoration var1, @NotNull TextDecoration.State var2);

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    public B decorationIfAbsent(@NotNull TextDecoration var1, @NotNull TextDecoration.State var2);

    @Override
    @Contract(value="_ -> this")
    @NotNull
    public B clickEvent(@Nullable ClickEvent var1);

    @Override
    @Contract(value="_ -> this")
    @NotNull
    public B hoverEvent(@Nullable HoverEventSource<?> var1);

    @Override
    @Contract(value="_ -> this")
    @NotNull
    public B insertion(@Nullable String var1);

    @Contract(value="_ -> this")
    @NotNull
    default public B mergeStyle(@NotNull Component component) {
        return this.mergeStyle(component, Style.Merge.all());
    }

    @Contract(value="_, _ -> this")
    @NotNull
    default public B mergeStyle(@NotNull Component component, @NotNull Style.Merge @NotNull ... mergeArray) {
        return this.mergeStyle(component, Style.Merge.merges(mergeArray));
    }

    @Contract(value="_, _ -> this")
    @NotNull
    public B mergeStyle(@NotNull Component var1, @NotNull Set<Style.Merge> var2);

    @NotNull
    public B resetStyle();

    @Override
    @NotNull
    public C build();

    @Contract(value="_ -> this")
    @NotNull
    default public B applicableApply(@NotNull ComponentBuilderApplicable componentBuilderApplicable) {
        componentBuilderApplicable.componentBuilderApply(this);
        return (B)this;
    }

    @Override
    default public void componentBuilderApply(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        componentBuilder.append(this);
    }

    @Override
    @NotNull
    default public Component asComponent() {
        return this.build();
    }

    @Override
    @NotNull
    default public Object build() {
        return this.build();
    }

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    default public MutableStyleSetter decorations(@NotNull Set set, boolean bl) {
        return this.decorations(set, bl);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public MutableStyleSetter decorations(@NotNull Map map) {
        return this.decorations(map);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public MutableStyleSetter decorate(@NotNull @NotNull TextDecoration @NotNull [] textDecorationArray) {
        return this.decorate(textDecorationArray);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter insertion(@Nullable String string) {
        return this.insertion(string);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter hoverEvent(@Nullable HoverEventSource hoverEventSource) {
        return this.hoverEvent(hoverEventSource);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter clickEvent(@Nullable ClickEvent clickEvent) {
        return this.clickEvent(clickEvent);
    }

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    default public StyleSetter decorations(@NotNull Set set, boolean bl) {
        return this.decorations(set, bl);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter decorations(@NotNull Map map) {
        return this.decorations(map);
    }

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    default public StyleSetter decorationIfAbsent(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return this.decorationIfAbsent(textDecoration, state);
    }

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    default public StyleSetter decoration(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return this.decoration(textDecoration, state);
    }

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    default public StyleSetter decoration(@NotNull TextDecoration textDecoration, boolean bl) {
        return this.decoration(textDecoration, bl);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter decorate(@NotNull @NotNull TextDecoration @NotNull [] textDecorationArray) {
        return this.decorate(textDecorationArray);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter decorate(@NotNull TextDecoration textDecoration) {
        return this.decorate(textDecoration);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter colorIfAbsent(@Nullable TextColor textColor) {
        return this.colorIfAbsent(textColor);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter color(@Nullable TextColor textColor) {
        return this.color(textColor);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter font(@Nullable Key key) {
        return this.font(key);
    }
}

