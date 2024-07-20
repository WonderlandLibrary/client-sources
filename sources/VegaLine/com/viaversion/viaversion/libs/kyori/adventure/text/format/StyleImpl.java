/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.DecorationMap;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class StyleImpl
implements Style {
    static final StyleImpl EMPTY = new StyleImpl(null, null, DecorationMap.EMPTY, null, null, null);
    @Nullable
    final Key font;
    @Nullable
    final TextColor color;
    @NotNull
    final DecorationMap decorations;
    @Nullable
    final ClickEvent clickEvent;
    @Nullable
    final HoverEvent<?> hoverEvent;
    @Nullable
    final String insertion;

    StyleImpl(@Nullable Key font, @Nullable TextColor color, @NotNull Map<TextDecoration, TextDecoration.State> decorations, @Nullable ClickEvent clickEvent, @Nullable HoverEvent<?> hoverEvent, @Nullable String insertion) {
        this.font = font;
        this.color = color;
        this.decorations = DecorationMap.fromMap(decorations);
        this.clickEvent = clickEvent;
        this.hoverEvent = hoverEvent;
        this.insertion = insertion;
    }

    @Override
    @Nullable
    public Key font() {
        return this.font;
    }

    @Override
    @NotNull
    public Style font(@Nullable Key font) {
        if (Objects.equals(this.font, font)) {
            return this;
        }
        return new StyleImpl(font, this.color, this.decorations, this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override
    @Nullable
    public TextColor color() {
        return this.color;
    }

    @Override
    @NotNull
    public Style color(@Nullable TextColor color) {
        if (Objects.equals(this.color, color)) {
            return this;
        }
        return new StyleImpl(this.font, color, this.decorations, this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override
    @NotNull
    public Style colorIfAbsent(@Nullable TextColor color) {
        if (this.color == null) {
            return this.color(color);
        }
        return this;
    }

    @Override
    public @NotNull TextDecoration.State decoration(@NotNull TextDecoration decoration) {
        @Nullable TextDecoration.State state = this.decorations.get(decoration);
        if (state != null) {
            return state;
        }
        throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
    }

    @Override
    @NotNull
    public Style decoration(@NotNull TextDecoration decoration, @NotNull TextDecoration.State state) {
        Objects.requireNonNull(state, "state");
        if (this.decoration(decoration) == state) {
            return this;
        }
        return new StyleImpl(this.font, this.color, this.decorations.with(decoration, state), this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override
    @NotNull
    public Style decorationIfAbsent(@NotNull TextDecoration decoration, @NotNull TextDecoration.State state) {
        Objects.requireNonNull(state, "state");
        @Nullable TextDecoration.State oldState = this.decorations.get(decoration);
        if (oldState == TextDecoration.State.NOT_SET) {
            return new StyleImpl(this.font, this.color, this.decorations.with(decoration, state), this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (oldState != null) {
            return this;
        }
        throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
    }

    @Override
    @NotNull
    public Map<TextDecoration, TextDecoration.State> decorations() {
        return this.decorations;
    }

    @Override
    @NotNull
    public Style decorations(@NotNull Map<TextDecoration, TextDecoration.State> decorations) {
        return new StyleImpl(this.font, this.color, DecorationMap.merge(decorations, this.decorations), this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override
    @Nullable
    public ClickEvent clickEvent() {
        return this.clickEvent;
    }

    @Override
    @NotNull
    public Style clickEvent(@Nullable ClickEvent event) {
        return new StyleImpl(this.font, this.color, this.decorations, event, this.hoverEvent, this.insertion);
    }

    @Override
    @Nullable
    public HoverEvent<?> hoverEvent() {
        return this.hoverEvent;
    }

    @Override
    @NotNull
    public Style hoverEvent(@Nullable HoverEventSource<?> source) {
        return new StyleImpl(this.font, this.color, this.decorations, this.clickEvent, HoverEventSource.unbox(source), this.insertion);
    }

    @Override
    @Nullable
    public String insertion() {
        return this.insertion;
    }

    @Override
    @NotNull
    public Style insertion(@Nullable String insertion) {
        if (Objects.equals(this.insertion, insertion)) {
            return this;
        }
        return new StyleImpl(this.font, this.color, this.decorations, this.clickEvent, this.hoverEvent, insertion);
    }

    @Override
    @NotNull
    public Style merge(@NotNull Style that, @NotNull Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> merges) {
        if (StyleImpl.nothingToMerge(that, strategy, merges)) {
            return this;
        }
        if (this.isEmpty() && Style.Merge.hasAll(merges)) {
            return that;
        }
        Style.Builder builder = this.toBuilder();
        builder.merge(that, strategy, merges);
        return builder.build();
    }

    @Override
    @NotNull
    public Style unmerge(@NotNull Style that) {
        if (this.isEmpty()) {
            return this;
        }
        BuilderImpl builder = new BuilderImpl(this);
        if (Objects.equals(this.font(), that.font())) {
            builder.font(null);
        }
        if (Objects.equals(this.color(), that.color())) {
            builder.color(null);
        }
        for (TextDecoration decoration : DecorationMap.DECORATIONS) {
            if (this.decoration(decoration) != that.decoration(decoration)) continue;
            builder.decoration(decoration, TextDecoration.State.NOT_SET);
        }
        if (Objects.equals(this.clickEvent(), that.clickEvent())) {
            builder.clickEvent(null);
        }
        if (Objects.equals(this.hoverEvent(), that.hoverEvent())) {
            builder.hoverEvent((HoverEventSource)null);
        }
        if (Objects.equals(this.insertion(), that.insertion())) {
            builder.insertion(null);
        }
        return builder.build();
    }

    static boolean nothingToMerge(@NotNull Style mergeFrom, @NotNull Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> merges) {
        if (strategy == Style.Merge.Strategy.NEVER) {
            return true;
        }
        if (mergeFrom.isEmpty()) {
            return true;
        }
        return merges.isEmpty();
    }

    @Override
    public boolean isEmpty() {
        return this == EMPTY;
    }

    @Override
    @NotNull
    public Style.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(this.decorations.examinableProperties(), Stream.of(ExaminableProperty.of("color", this.color), ExaminableProperty.of("clickEvent", this.clickEvent), ExaminableProperty.of("hoverEvent", this.hoverEvent), ExaminableProperty.of("insertion", this.insertion), ExaminableProperty.of("font", this.font)));
    }

    @NotNull
    public String toString() {
        return Internals.toString(this);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StyleImpl)) {
            return false;
        }
        StyleImpl that = (StyleImpl)other;
        return Objects.equals(this.color, that.color) && this.decorations.equals(that.decorations) && Objects.equals(this.clickEvent, that.clickEvent) && Objects.equals(this.hoverEvent, that.hoverEvent) && Objects.equals(this.insertion, that.insertion) && Objects.equals(this.font, that.font);
    }

    public int hashCode() {
        int result = Objects.hashCode(this.color);
        result = 31 * result + this.decorations.hashCode();
        result = 31 * result + Objects.hashCode(this.clickEvent);
        result = 31 * result + Objects.hashCode(this.hoverEvent);
        result = 31 * result + Objects.hashCode(this.insertion);
        result = 31 * result + Objects.hashCode(this.font);
        return result;
    }

    static final class BuilderImpl
    implements Style.Builder {
        @Nullable
        Key font;
        @Nullable
        TextColor color;
        final Map<TextDecoration, TextDecoration.State> decorations;
        @Nullable
        ClickEvent clickEvent;
        @Nullable
        HoverEvent<?> hoverEvent;
        @Nullable
        String insertion;

        BuilderImpl() {
            this.decorations = new EnumMap<TextDecoration, TextDecoration.State>(DecorationMap.EMPTY);
        }

        BuilderImpl(@NotNull StyleImpl style) {
            this.color = style.color;
            this.decorations = new EnumMap<TextDecoration, TextDecoration.State>(style.decorations);
            this.clickEvent = style.clickEvent;
            this.hoverEvent = style.hoverEvent;
            this.insertion = style.insertion;
            this.font = style.font;
        }

        @Override
        @NotNull
        public Style.Builder font(@Nullable Key font) {
            this.font = font;
            return this;
        }

        @Override
        @NotNull
        public Style.Builder color(@Nullable TextColor color) {
            this.color = color;
            return this;
        }

        @Override
        @NotNull
        public Style.Builder colorIfAbsent(@Nullable TextColor color) {
            if (this.color == null) {
                this.color = color;
            }
            return this;
        }

        @Override
        @NotNull
        public Style.Builder decoration(@NotNull TextDecoration decoration, @NotNull TextDecoration.State state) {
            Objects.requireNonNull(state, "state");
            Objects.requireNonNull(decoration, "decoration");
            this.decorations.put(decoration, state);
            return this;
        }

        @Override
        @NotNull
        public Style.Builder decorationIfAbsent(@NotNull TextDecoration decoration, @NotNull TextDecoration.State state) {
            Objects.requireNonNull(state, "state");
            @Nullable TextDecoration.State oldState = this.decorations.get(decoration);
            if (oldState == TextDecoration.State.NOT_SET) {
                this.decorations.put(decoration, state);
            }
            if (oldState != null) {
                return this;
            }
            throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
        }

        @Override
        @NotNull
        public Style.Builder clickEvent(@Nullable ClickEvent event) {
            this.clickEvent = event;
            return this;
        }

        @Override
        @NotNull
        public Style.Builder hoverEvent(@Nullable HoverEventSource<?> source) {
            this.hoverEvent = HoverEventSource.unbox(source);
            return this;
        }

        @Override
        @NotNull
        public Style.Builder insertion(@Nullable String insertion) {
            this.insertion = insertion;
            return this;
        }

        @Override
        @NotNull
        public Style.Builder merge(@NotNull Style that, @NotNull Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> merges) {
            Key font;
            String insertion;
            TextColor color;
            Objects.requireNonNull(that, "style");
            Objects.requireNonNull(strategy, "strategy");
            Objects.requireNonNull(merges, "merges");
            if (StyleImpl.nothingToMerge(that, strategy, merges)) {
                return this;
            }
            if (merges.contains((Object)Style.Merge.COLOR) && (color = that.color()) != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.color == null)) {
                this.color(color);
            }
            if (merges.contains((Object)Style.Merge.DECORATIONS)) {
                for (TextDecoration decoration : DecorationMap.DECORATIONS) {
                    TextDecoration.State state = that.decoration(decoration);
                    if (state == TextDecoration.State.NOT_SET) continue;
                    if (strategy == Style.Merge.Strategy.ALWAYS) {
                        this.decoration(decoration, state);
                        continue;
                    }
                    if (strategy != Style.Merge.Strategy.IF_ABSENT_ON_TARGET) continue;
                    this.decorationIfAbsent(decoration, state);
                }
            }
            if (merges.contains((Object)Style.Merge.EVENTS)) {
                HoverEvent<?> hoverEvent;
                ClickEvent clickEvent = that.clickEvent();
                if (clickEvent != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.clickEvent == null)) {
                    this.clickEvent(clickEvent);
                }
                if ((hoverEvent = that.hoverEvent()) != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.hoverEvent == null)) {
                    this.hoverEvent(hoverEvent);
                }
            }
            if (merges.contains((Object)Style.Merge.INSERTION) && (insertion = that.insertion()) != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.insertion == null)) {
                this.insertion(insertion);
            }
            if (merges.contains((Object)Style.Merge.FONT) && (font = that.font()) != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.font == null)) {
                this.font(font);
            }
            return this;
        }

        @Override
        @NotNull
        public StyleImpl build() {
            if (this.isEmpty()) {
                return EMPTY;
            }
            return new StyleImpl(this.font, this.color, this.decorations, this.clickEvent, this.hoverEvent, this.insertion);
        }

        private boolean isEmpty() {
            return this.color == null && this.decorations.values().stream().allMatch(state -> state == TextDecoration.State.NOT_SET) && this.clickEvent == null && this.hoverEvent == null && this.insertion == null && this.font == null;
        }
    }
}

