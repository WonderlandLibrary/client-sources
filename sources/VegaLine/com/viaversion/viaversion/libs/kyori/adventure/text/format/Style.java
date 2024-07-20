/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jetbrains.annotations.UnknownNullability
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.MutableStyleSetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleGetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.jetbrains.annotations.Unmodifiable;

@ApiStatus.NonExtendable
public interface Style
extends Buildable<Style, Builder>,
Examinable,
StyleGetter,
StyleSetter<Style> {
    public static final Key DEFAULT_FONT = Key.key("default");

    @NotNull
    public static Style empty() {
        return StyleImpl.EMPTY;
    }

    @NotNull
    public static Builder style() {
        return new StyleImpl.BuilderImpl();
    }

    @NotNull
    public static Style style(@NotNull Consumer<Builder> consumer) {
        return (Style)AbstractBuilder.configureAndBuild(Style.style(), consumer);
    }

    @NotNull
    public static Style style(@Nullable TextColor color) {
        return Style.empty().color(color);
    }

    @NotNull
    public static Style style(@NotNull TextDecoration decoration) {
        return Style.style().decoration(decoration, true).build();
    }

    @NotNull
    public static Style style(@Nullable TextColor color, TextDecoration @NotNull ... decorations) {
        Builder builder = Style.style();
        builder.color(color);
        builder.decorate(decorations);
        return builder.build();
    }

    @NotNull
    public static Style style(@Nullable TextColor color, Set<TextDecoration> decorations) {
        Builder builder = Style.style();
        builder.color(color);
        if (!decorations.isEmpty()) {
            for (TextDecoration decoration : decorations) {
                builder.decoration(decoration, true);
            }
        }
        return builder.build();
    }

    @NotNull
    public static Style style(@UnknownNullability StyleBuilderApplicable @NotNull ... applicables) {
        int length = applicables.length;
        if (length == 0) {
            return Style.empty();
        }
        Builder builder = Style.style();
        for (int i = 0; i < length; ++i) {
            StyleBuilderApplicable applicable = applicables[i];
            if (applicable == null) continue;
            applicable.styleApply(builder);
        }
        return builder.build();
    }

    @NotNull
    public static Style style(@NotNull Iterable<? extends StyleBuilderApplicable> applicables) {
        Builder builder = Style.style();
        for (StyleBuilderApplicable styleBuilderApplicable : applicables) {
            styleBuilderApplicable.styleApply(builder);
        }
        return builder.build();
    }

    @NotNull
    default public Style edit(@NotNull Consumer<Builder> consumer) {
        return this.edit(consumer, Merge.Strategy.ALWAYS);
    }

    @NotNull
    default public Style edit(@NotNull Consumer<Builder> consumer, @NotNull Merge.Strategy strategy) {
        return Style.style((Builder style) -> {
            if (strategy == Merge.Strategy.ALWAYS) {
                style.merge(this, strategy);
            }
            consumer.accept((Builder)style);
            if (strategy == Merge.Strategy.IF_ABSENT_ON_TARGET) {
                style.merge(this, strategy);
            }
        });
    }

    @Override
    @Nullable
    public Key font();

    @Override
    @NotNull
    public Style font(@Nullable Key var1);

    @Override
    @Nullable
    public TextColor color();

    @Override
    @NotNull
    public Style color(@Nullable TextColor var1);

    @Override
    @NotNull
    public Style colorIfAbsent(@Nullable TextColor var1);

    @Override
    default public boolean hasDecoration(@NotNull TextDecoration decoration) {
        return StyleGetter.super.hasDecoration(decoration);
    }

    @Override
    public @NotNull TextDecoration.State decoration(@NotNull TextDecoration var1);

    @Override
    @NotNull
    default public Style decorate(@NotNull TextDecoration decoration) {
        return (Style)StyleSetter.super.decorate(decoration);
    }

    @Override
    @NotNull
    default public Style decoration(@NotNull TextDecoration decoration, boolean flag) {
        return (Style)StyleSetter.super.decoration(decoration, flag);
    }

    @Override
    @NotNull
    public Style decoration(@NotNull TextDecoration var1, @NotNull TextDecoration.State var2);

    @Override
    @NotNull
    public Style decorationIfAbsent(@NotNull TextDecoration var1, @NotNull TextDecoration.State var2);

    @Override
    default public @Unmodifiable @NotNull Map<TextDecoration, TextDecoration.State> decorations() {
        return StyleGetter.super.decorations();
    }

    @Override
    @NotNull
    public Style decorations(@NotNull Map<TextDecoration, TextDecoration.State> var1);

    @Override
    @Nullable
    public ClickEvent clickEvent();

    @Override
    @NotNull
    public Style clickEvent(@Nullable ClickEvent var1);

    @Override
    @Nullable
    public HoverEvent<?> hoverEvent();

    @Override
    @NotNull
    public Style hoverEvent(@Nullable HoverEventSource<?> var1);

    @Override
    @Nullable
    public String insertion();

    @Override
    @NotNull
    public Style insertion(@Nullable String var1);

    @NotNull
    default public Style merge(@NotNull Style that) {
        return this.merge(that, Merge.all());
    }

    @NotNull
    default public Style merge(@NotNull Style that, @NotNull Merge.Strategy strategy) {
        return this.merge(that, strategy, Merge.all());
    }

    @NotNull
    default public Style merge(@NotNull Style that, @NotNull Merge merge) {
        return this.merge(that, Collections.singleton(merge));
    }

    @NotNull
    default public Style merge(@NotNull Style that, @NotNull Merge.Strategy strategy, @NotNull Merge merge) {
        return this.merge(that, strategy, Collections.singleton(merge));
    }

    @NotNull
    default public Style merge(@NotNull Style that, @NotNull @NotNull Merge @NotNull ... merges) {
        return this.merge(that, Merge.merges(merges));
    }

    @NotNull
    default public Style merge(@NotNull Style that, @NotNull Merge.Strategy strategy, @NotNull @NotNull Merge @NotNull ... merges) {
        return this.merge(that, strategy, Merge.merges(merges));
    }

    @NotNull
    default public Style merge(@NotNull Style that, @NotNull Set<Merge> merges) {
        return this.merge(that, Merge.Strategy.ALWAYS, merges);
    }

    @NotNull
    public Style merge(@NotNull Style var1, @NotNull Merge.Strategy var2, @NotNull Set<Merge> var3);

    @NotNull
    public Style unmerge(@NotNull Style var1);

    public boolean isEmpty();

    @Override
    @NotNull
    public Builder toBuilder();

    public static interface Builder
    extends AbstractBuilder<Style>,
    Buildable.Builder<Style>,
    MutableStyleSetter<Builder> {
        @Override
        @Contract(value="_ -> this")
        @NotNull
        public Builder font(@Nullable Key var1);

        @Override
        @Contract(value="_ -> this")
        @NotNull
        public Builder color(@Nullable TextColor var1);

        @Override
        @Contract(value="_ -> this")
        @NotNull
        public Builder colorIfAbsent(@Nullable TextColor var1);

        @Override
        @Contract(value="_ -> this")
        @NotNull
        default public Builder decorate(@NotNull TextDecoration decoration) {
            return (Builder)MutableStyleSetter.super.decorate(decoration);
        }

        @Override
        @Contract(value="_ -> this")
        @NotNull
        default public Builder decorate(@NotNull @NotNull TextDecoration @NotNull ... decorations) {
            return (Builder)MutableStyleSetter.super.decorate(decorations);
        }

        @Override
        @Contract(value="_, _ -> this")
        @NotNull
        default public Builder decoration(@NotNull TextDecoration decoration, boolean flag) {
            return (Builder)MutableStyleSetter.super.decoration(decoration, flag);
        }

        @Override
        @Contract(value="_ -> this")
        @NotNull
        default public Builder decorations(@NotNull Map<TextDecoration, TextDecoration.State> decorations) {
            return (Builder)MutableStyleSetter.super.decorations((Map)decorations);
        }

        @Override
        @Contract(value="_, _ -> this")
        @NotNull
        public Builder decoration(@NotNull TextDecoration var1, @NotNull TextDecoration.State var2);

        @Override
        @Contract(value="_, _ -> this")
        @NotNull
        public Builder decorationIfAbsent(@NotNull TextDecoration var1, @NotNull TextDecoration.State var2);

        @Override
        @Contract(value="_ -> this")
        @NotNull
        public Builder clickEvent(@Nullable ClickEvent var1);

        @Override
        @Contract(value="_ -> this")
        @NotNull
        public Builder hoverEvent(@Nullable HoverEventSource<?> var1);

        @Override
        @Contract(value="_ -> this")
        @NotNull
        public Builder insertion(@Nullable String var1);

        @Contract(value="_ -> this")
        @NotNull
        default public Builder merge(@NotNull Style that) {
            return this.merge(that, Merge.all());
        }

        @Contract(value="_, _ -> this")
        @NotNull
        default public Builder merge(@NotNull Style that, @NotNull Merge.Strategy strategy) {
            return this.merge(that, strategy, Merge.all());
        }

        @Contract(value="_, _ -> this")
        @NotNull
        default public Builder merge(@NotNull Style that, @NotNull @NotNull Merge @NotNull ... merges) {
            if (merges.length == 0) {
                return this;
            }
            return this.merge(that, Merge.merges(merges));
        }

        @Contract(value="_, _, _ -> this")
        @NotNull
        default public Builder merge(@NotNull Style that, @NotNull Merge.Strategy strategy, @NotNull @NotNull Merge @NotNull ... merges) {
            if (merges.length == 0) {
                return this;
            }
            return this.merge(that, strategy, Merge.merges(merges));
        }

        @Contract(value="_, _ -> this")
        @NotNull
        default public Builder merge(@NotNull Style that, @NotNull Set<Merge> merges) {
            return this.merge(that, Merge.Strategy.ALWAYS, merges);
        }

        @Contract(value="_, _, _ -> this")
        @NotNull
        public Builder merge(@NotNull Style var1, @NotNull Merge.Strategy var2, @NotNull Set<Merge> var3);

        @Contract(value="_ -> this")
        @NotNull
        default public Builder apply(@NotNull StyleBuilderApplicable applicable) {
            applicable.styleApply(this);
            return this;
        }

        @Override
        @NotNull
        public Style build();
    }

    public static enum Merge {
        COLOR,
        DECORATIONS,
        EVENTS,
        INSERTION,
        FONT;

        static final Set<Merge> ALL;
        static final Set<Merge> COLOR_AND_DECORATIONS;

        public static @Unmodifiable @NotNull Set<Merge> all() {
            return ALL;
        }

        public static @Unmodifiable @NotNull Set<Merge> colorAndDecorations() {
            return COLOR_AND_DECORATIONS;
        }

        public static @Unmodifiable @NotNull Set<Merge> merges(Merge @NotNull ... merges) {
            return MonkeyBars.enumSet(Merge.class, (Enum[])merges);
        }

        @Deprecated
        @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
        public static @Unmodifiable @NotNull Set<Merge> of(Merge @NotNull ... merges) {
            return MonkeyBars.enumSet(Merge.class, (Enum[])merges);
        }

        static boolean hasAll(@NotNull Set<Merge> merges) {
            return merges.size() == ALL.size();
        }

        static {
            ALL = Merge.merges(Merge.values());
            COLOR_AND_DECORATIONS = Merge.merges(COLOR, DECORATIONS);
        }

        public static enum Strategy {
            ALWAYS,
            NEVER,
            IF_ABSENT_ON_TARGET;

        }
    }
}

