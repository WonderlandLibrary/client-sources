/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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
    public static Style style(@Nullable TextColor textColor) {
        return Style.empty().color(textColor);
    }

    @NotNull
    public static Style style(@NotNull TextDecoration textDecoration) {
        return Style.style().decoration(textDecoration, true).build();
    }

    @NotNull
    public static Style style(@Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        Builder builder = Style.style();
        builder.color(textColor);
        builder.decorate(textDecorationArray);
        return builder.build();
    }

    @NotNull
    public static Style style(@Nullable TextColor textColor, Set<TextDecoration> set) {
        Builder builder = Style.style();
        builder.color(textColor);
        if (!set.isEmpty()) {
            for (TextDecoration textDecoration : set) {
                builder.decoration(textDecoration, true);
            }
        }
        return builder.build();
    }

    @NotNull
    public static Style style(@UnknownNullability StyleBuilderApplicable @NotNull ... styleBuilderApplicableArray) {
        int n = styleBuilderApplicableArray.length;
        if (n == 0) {
            return Style.empty();
        }
        Builder builder = Style.style();
        for (int i = 0; i < n; ++i) {
            StyleBuilderApplicable styleBuilderApplicable = styleBuilderApplicableArray[i];
            if (styleBuilderApplicable == null) continue;
            styleBuilderApplicable.styleApply(builder);
        }
        return builder.build();
    }

    @NotNull
    public static Style style(@NotNull Iterable<? extends StyleBuilderApplicable> iterable) {
        Builder builder = Style.style();
        for (StyleBuilderApplicable styleBuilderApplicable : iterable) {
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
        return Style.style(arg_0 -> this.lambda$edit$0(strategy, consumer, arg_0));
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
    default public boolean hasDecoration(@NotNull TextDecoration textDecoration) {
        return StyleGetter.super.hasDecoration(textDecoration);
    }

    @Override
    public @NotNull TextDecoration.State decoration(@NotNull TextDecoration var1);

    @Override
    @NotNull
    default public Style decorate(@NotNull TextDecoration textDecoration) {
        return (Style)StyleSetter.super.decorate(textDecoration);
    }

    @Override
    @NotNull
    default public Style decoration(@NotNull TextDecoration textDecoration, boolean bl) {
        return (Style)StyleSetter.super.decoration(textDecoration, bl);
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
    default public Style merge(@NotNull Style style) {
        return this.merge(style, Merge.all());
    }

    @NotNull
    default public Style merge(@NotNull Style style, @NotNull Merge.Strategy strategy) {
        return this.merge(style, strategy, Merge.all());
    }

    @NotNull
    default public Style merge(@NotNull Style style, @NotNull Merge merge) {
        return this.merge(style, Collections.singleton(merge));
    }

    @NotNull
    default public Style merge(@NotNull Style style, @NotNull Merge.Strategy strategy, @NotNull Merge merge) {
        return this.merge(style, strategy, Collections.singleton(merge));
    }

    @NotNull
    default public Style merge(@NotNull Style style, @NotNull @NotNull Merge @NotNull ... mergeArray) {
        return this.merge(style, Merge.merges(mergeArray));
    }

    @NotNull
    default public Style merge(@NotNull Style style, @NotNull Merge.Strategy strategy, @NotNull @NotNull Merge @NotNull ... mergeArray) {
        return this.merge(style, strategy, Merge.merges(mergeArray));
    }

    @NotNull
    default public Style merge(@NotNull Style style, @NotNull Set<Merge> set) {
        return this.merge(style, Merge.Strategy.ALWAYS, set);
    }

    @NotNull
    public Style merge(@NotNull Style var1, @NotNull Merge.Strategy var2, @NotNull Set<Merge> var3);

    @NotNull
    public Style unmerge(@NotNull Style var1);

    public boolean isEmpty();

    @Override
    @NotNull
    public Builder toBuilder();

    @Override
    @NotNull
    default public Buildable.Builder toBuilder() {
        return this.toBuilder();
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
    default public StyleSetter decorations(@NotNull Map map) {
        return this.decorations(map);
    }

    @Override
    @NotNull
    default public StyleSetter decorationIfAbsent(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return this.decorationIfAbsent(textDecoration, state);
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

    @Override
    @NotNull
    default public StyleSetter font(@Nullable Key key) {
        return this.font(key);
    }

    private void lambda$edit$0(Merge.Strategy strategy, Consumer consumer, Builder builder) {
        if (strategy == Merge.Strategy.ALWAYS) {
            builder.merge(this, strategy);
        }
        consumer.accept(builder);
        if (strategy == Merge.Strategy.IF_ABSENT_ON_TARGET) {
            builder.merge(this, strategy);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
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
        default public Builder decorate(@NotNull TextDecoration textDecoration) {
            return (Builder)MutableStyleSetter.super.decorate(textDecoration);
        }

        @Override
        @Contract(value="_ -> this")
        @NotNull
        default public Builder decorate(@NotNull @NotNull TextDecoration @NotNull ... textDecorationArray) {
            return (Builder)MutableStyleSetter.super.decorate(textDecorationArray);
        }

        @Override
        @Contract(value="_, _ -> this")
        @NotNull
        default public Builder decoration(@NotNull TextDecoration textDecoration, boolean bl) {
            return (Builder)MutableStyleSetter.super.decoration(textDecoration, bl);
        }

        @Override
        @Contract(value="_ -> this")
        @NotNull
        default public Builder decorations(@NotNull Map<TextDecoration, TextDecoration.State> map) {
            return (Builder)MutableStyleSetter.super.decorations((Map)map);
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
        default public Builder merge(@NotNull Style style) {
            return this.merge(style, Merge.all());
        }

        @Contract(value="_, _ -> this")
        @NotNull
        default public Builder merge(@NotNull Style style, @NotNull Merge.Strategy strategy) {
            return this.merge(style, strategy, Merge.all());
        }

        @Contract(value="_, _ -> this")
        @NotNull
        default public Builder merge(@NotNull Style style, @NotNull @NotNull Merge @NotNull ... mergeArray) {
            if (mergeArray.length == 0) {
                return this;
            }
            return this.merge(style, Merge.merges(mergeArray));
        }

        @Contract(value="_, _, _ -> this")
        @NotNull
        default public Builder merge(@NotNull Style style, @NotNull Merge.Strategy strategy, @NotNull @NotNull Merge @NotNull ... mergeArray) {
            if (mergeArray.length == 0) {
                return this;
            }
            return this.merge(style, strategy, Merge.merges(mergeArray));
        }

        @Contract(value="_, _ -> this")
        @NotNull
        default public Builder merge(@NotNull Style style, @NotNull Set<Merge> set) {
            return this.merge(style, Merge.Strategy.ALWAYS, set);
        }

        @Contract(value="_, _, _ -> this")
        @NotNull
        public Builder merge(@NotNull Style var1, @NotNull Merge.Strategy var2, @NotNull Set<Merge> var3);

        @Contract(value="_ -> this")
        @NotNull
        default public Builder apply(@NotNull StyleBuilderApplicable styleBuilderApplicable) {
            styleBuilderApplicable.styleApply(this);
            return this;
        }

        @Override
        @NotNull
        public Style build();

        @Override
        @NotNull
        default public Object build() {
            return this.build();
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

        public static @Unmodifiable @NotNull Set<Merge> merges(Merge @NotNull ... mergeArray) {
            return MonkeyBars.enumSet(Merge.class, (Enum[])mergeArray);
        }

        @Deprecated
        @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
        public static @Unmodifiable @NotNull Set<Merge> of(Merge @NotNull ... mergeArray) {
            return MonkeyBars.enumSet(Merge.class, (Enum[])mergeArray);
        }

        static boolean hasAll(@NotNull Set<Merge> set) {
            return set.size() == ALL.size();
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

