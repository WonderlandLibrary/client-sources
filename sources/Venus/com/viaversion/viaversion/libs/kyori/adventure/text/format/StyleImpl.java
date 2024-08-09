/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.DecorationMap;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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

    StyleImpl(@Nullable Key key, @Nullable TextColor textColor, @NotNull Map<TextDecoration, TextDecoration.State> map, @Nullable ClickEvent clickEvent, @Nullable HoverEvent<?> hoverEvent, @Nullable String string) {
        this.font = key;
        this.color = textColor;
        this.decorations = DecorationMap.fromMap(map);
        this.clickEvent = clickEvent;
        this.hoverEvent = hoverEvent;
        this.insertion = string;
    }

    @Override
    @Nullable
    public Key font() {
        return this.font;
    }

    @Override
    @NotNull
    public Style font(@Nullable Key key) {
        if (Objects.equals(this.font, key)) {
            return this;
        }
        return new StyleImpl(key, this.color, this.decorations, this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override
    @Nullable
    public TextColor color() {
        return this.color;
    }

    @Override
    @NotNull
    public Style color(@Nullable TextColor textColor) {
        if (Objects.equals(this.color, textColor)) {
            return this;
        }
        return new StyleImpl(this.font, textColor, this.decorations, this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override
    @NotNull
    public Style colorIfAbsent(@Nullable TextColor textColor) {
        if (this.color == null) {
            return this.color(textColor);
        }
        return this;
    }

    @Override
    public @NotNull TextDecoration.State decoration(@NotNull TextDecoration textDecoration) {
        @Nullable TextDecoration.State state = this.decorations.get(textDecoration);
        if (state != null) {
            return state;
        }
        throw new IllegalArgumentException(String.format("unknown decoration '%s'", textDecoration));
    }

    @Override
    @NotNull
    public Style decoration(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        Objects.requireNonNull(state, "state");
        if (this.decoration(textDecoration) == state) {
            return this;
        }
        return new StyleImpl(this.font, this.color, this.decorations.with(textDecoration, state), this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override
    @NotNull
    public Style decorationIfAbsent(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        Objects.requireNonNull(state, "state");
        @Nullable TextDecoration.State state2 = this.decorations.get(textDecoration);
        if (state2 == TextDecoration.State.NOT_SET) {
            return new StyleImpl(this.font, this.color, this.decorations.with(textDecoration, state), this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (state2 != null) {
            return this;
        }
        throw new IllegalArgumentException(String.format("unknown decoration '%s'", textDecoration));
    }

    @Override
    @NotNull
    public Map<TextDecoration, TextDecoration.State> decorations() {
        return this.decorations;
    }

    @Override
    @NotNull
    public Style decorations(@NotNull Map<TextDecoration, TextDecoration.State> map) {
        return new StyleImpl(this.font, this.color, DecorationMap.merge(map, this.decorations), this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override
    @Nullable
    public ClickEvent clickEvent() {
        return this.clickEvent;
    }

    @Override
    @NotNull
    public Style clickEvent(@Nullable ClickEvent clickEvent) {
        return new StyleImpl(this.font, this.color, this.decorations, clickEvent, this.hoverEvent, this.insertion);
    }

    @Override
    @Nullable
    public HoverEvent<?> hoverEvent() {
        return this.hoverEvent;
    }

    @Override
    @NotNull
    public Style hoverEvent(@Nullable HoverEventSource<?> hoverEventSource) {
        return new StyleImpl(this.font, this.color, this.decorations, this.clickEvent, HoverEventSource.unbox(hoverEventSource), this.insertion);
    }

    @Override
    @Nullable
    public String insertion() {
        return this.insertion;
    }

    @Override
    @NotNull
    public Style insertion(@Nullable String string) {
        if (Objects.equals(this.insertion, string)) {
            return this;
        }
        return new StyleImpl(this.font, this.color, this.decorations, this.clickEvent, this.hoverEvent, string);
    }

    @Override
    @NotNull
    public Style merge(@NotNull Style style, @NotNull Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> set) {
        if (StyleImpl.nothingToMerge(style, strategy, set)) {
            return this;
        }
        if (this.isEmpty() && Style.Merge.hasAll(set)) {
            return style;
        }
        Style.Builder builder = this.toBuilder();
        builder.merge(style, strategy, set);
        return builder.build();
    }

    @Override
    @NotNull
    public Style unmerge(@NotNull Style style) {
        if (this.isEmpty()) {
            return this;
        }
        BuilderImpl builderImpl = new BuilderImpl(this);
        if (Objects.equals(this.font(), style.font())) {
            builderImpl.font(null);
        }
        if (Objects.equals(this.color(), style.color())) {
            builderImpl.color(null);
        }
        for (TextDecoration textDecoration : DecorationMap.DECORATIONS) {
            if (this.decoration(textDecoration) != style.decoration(textDecoration)) continue;
            builderImpl.decoration(textDecoration, TextDecoration.State.NOT_SET);
        }
        if (Objects.equals(this.clickEvent(), style.clickEvent())) {
            builderImpl.clickEvent(null);
        }
        if (Objects.equals(this.hoverEvent(), style.hoverEvent())) {
            builderImpl.hoverEvent((HoverEventSource)null);
        }
        if (Objects.equals(this.insertion(), style.insertion())) {
            builderImpl.insertion(null);
        }
        return builderImpl.build();
    }

    static boolean nothingToMerge(@NotNull Style style, @NotNull Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> set) {
        if (strategy == Style.Merge.Strategy.NEVER) {
            return false;
        }
        if (style.isEmpty()) {
            return false;
        }
        return !set.isEmpty();
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

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof StyleImpl)) {
            return true;
        }
        StyleImpl styleImpl = (StyleImpl)object;
        return Objects.equals(this.color, styleImpl.color) && this.decorations.equals(styleImpl.decorations) && Objects.equals(this.clickEvent, styleImpl.clickEvent) && Objects.equals(this.hoverEvent, styleImpl.hoverEvent) && Objects.equals(this.insertion, styleImpl.insertion) && Objects.equals(this.font, styleImpl.font);
    }

    public int hashCode() {
        int n = Objects.hashCode(this.color);
        n = 31 * n + this.decorations.hashCode();
        n = 31 * n + Objects.hashCode(this.clickEvent);
        n = 31 * n + Objects.hashCode(this.hoverEvent);
        n = 31 * n + Objects.hashCode(this.insertion);
        n = 31 * n + Objects.hashCode(this.font);
        return n;
    }

    @Override
    @NotNull
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    @Override
    @NotNull
    public StyleSetter insertion(@Nullable String string) {
        return this.insertion(string);
    }

    @Override
    @NotNull
    public StyleSetter hoverEvent(@Nullable HoverEventSource hoverEventSource) {
        return this.hoverEvent(hoverEventSource);
    }

    @Override
    @NotNull
    public StyleSetter clickEvent(@Nullable ClickEvent clickEvent) {
        return this.clickEvent(clickEvent);
    }

    @Override
    @NotNull
    public StyleSetter decorations(@NotNull Map map) {
        return this.decorations(map);
    }

    @Override
    @NotNull
    public StyleSetter decorationIfAbsent(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return this.decorationIfAbsent(textDecoration, state);
    }

    @Override
    @NotNull
    public StyleSetter decoration(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return this.decoration(textDecoration, state);
    }

    @Override
    @NotNull
    public StyleSetter colorIfAbsent(@Nullable TextColor textColor) {
        return this.colorIfAbsent(textColor);
    }

    @Override
    @NotNull
    public StyleSetter color(@Nullable TextColor textColor) {
        return this.color(textColor);
    }

    @Override
    @NotNull
    public StyleSetter font(@Nullable Key key) {
        return this.font(key);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
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

        BuilderImpl(@NotNull StyleImpl styleImpl) {
            this.color = styleImpl.color;
            this.decorations = new EnumMap<TextDecoration, TextDecoration.State>(styleImpl.decorations);
            this.clickEvent = styleImpl.clickEvent;
            this.hoverEvent = styleImpl.hoverEvent;
            this.insertion = styleImpl.insertion;
            this.font = styleImpl.font;
        }

        @Override
        @NotNull
        public Style.Builder font(@Nullable Key key) {
            this.font = key;
            return this;
        }

        @Override
        @NotNull
        public Style.Builder color(@Nullable TextColor textColor) {
            this.color = textColor;
            return this;
        }

        @Override
        @NotNull
        public Style.Builder colorIfAbsent(@Nullable TextColor textColor) {
            if (this.color == null) {
                this.color = textColor;
            }
            return this;
        }

        @Override
        @NotNull
        public Style.Builder decoration(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
            Objects.requireNonNull(state, "state");
            Objects.requireNonNull(textDecoration, "decoration");
            this.decorations.put(textDecoration, state);
            return this;
        }

        @Override
        @NotNull
        public Style.Builder decorationIfAbsent(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
            Objects.requireNonNull(state, "state");
            @Nullable TextDecoration.State state2 = this.decorations.get(textDecoration);
            if (state2 == TextDecoration.State.NOT_SET) {
                this.decorations.put(textDecoration, state);
            }
            if (state2 != null) {
                return this;
            }
            throw new IllegalArgumentException(String.format("unknown decoration '%s'", textDecoration));
        }

        @Override
        @NotNull
        public Style.Builder clickEvent(@Nullable ClickEvent clickEvent) {
            this.clickEvent = clickEvent;
            return this;
        }

        @Override
        @NotNull
        public Style.Builder hoverEvent(@Nullable HoverEventSource<?> hoverEventSource) {
            this.hoverEvent = HoverEventSource.unbox(hoverEventSource);
            return this;
        }

        @Override
        @NotNull
        public Style.Builder insertion(@Nullable String string) {
            this.insertion = string;
            return this;
        }

        @Override
        @NotNull
        public Style.Builder merge(@NotNull Style style, @NotNull Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> set) {
            Object object;
            Objects.requireNonNull(style, "style");
            Objects.requireNonNull(strategy, "strategy");
            Objects.requireNonNull(set, "merges");
            if (StyleImpl.nothingToMerge(style, strategy, set)) {
                return this;
            }
            if (set.contains((Object)Style.Merge.COLOR) && (object = style.color()) != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.color == null)) {
                this.color((TextColor)object);
            }
            if (set.contains((Object)Style.Merge.DECORATIONS)) {
                for (TextDecoration textDecoration : DecorationMap.DECORATIONS) {
                    TextDecoration.State state = style.decoration(textDecoration);
                    if (state == TextDecoration.State.NOT_SET) continue;
                    if (strategy == Style.Merge.Strategy.ALWAYS) {
                        this.decoration(textDecoration, state);
                        continue;
                    }
                    if (strategy != Style.Merge.Strategy.IF_ABSENT_ON_TARGET) continue;
                    this.decorationIfAbsent(textDecoration, state);
                }
            }
            if (set.contains((Object)Style.Merge.EVENTS)) {
                HoverEvent<?> hoverEvent;
                object = style.clickEvent();
                if (object != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.clickEvent == null)) {
                    this.clickEvent((ClickEvent)object);
                }
                if ((hoverEvent = style.hoverEvent()) != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.hoverEvent == null)) {
                    this.hoverEvent(hoverEvent);
                }
            }
            if (set.contains((Object)Style.Merge.INSERTION) && (object = style.insertion()) != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.insertion == null)) {
                this.insertion((String)object);
            }
            if (set.contains((Object)Style.Merge.FONT) && (object = style.font()) != null && (strategy == Style.Merge.Strategy.ALWAYS || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.font == null)) {
                this.font((Key)object);
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
            return this.color == null && this.decorations.values().stream().allMatch(BuilderImpl::lambda$isEmpty$0) && this.clickEvent == null && this.hoverEvent == null && this.insertion == null && this.font == null;
        }

        @Override
        @NotNull
        public Style build() {
            return this.build();
        }

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }

        @Override
        @NotNull
        public StyleSetter insertion(@Nullable String string) {
            return this.insertion(string);
        }

        @Override
        @NotNull
        public StyleSetter hoverEvent(@Nullable HoverEventSource hoverEventSource) {
            return this.hoverEvent(hoverEventSource);
        }

        @Override
        @NotNull
        public StyleSetter clickEvent(@Nullable ClickEvent clickEvent) {
            return this.clickEvent(clickEvent);
        }

        @Override
        @NotNull
        public StyleSetter decorationIfAbsent(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
            return this.decorationIfAbsent(textDecoration, state);
        }

        @Override
        @NotNull
        public StyleSetter decoration(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
            return this.decoration(textDecoration, state);
        }

        @Override
        @NotNull
        public StyleSetter colorIfAbsent(@Nullable TextColor textColor) {
            return this.colorIfAbsent(textColor);
        }

        @Override
        @NotNull
        public StyleSetter color(@Nullable TextColor textColor) {
            return this.color(textColor);
        }

        @Override
        @NotNull
        public StyleSetter font(@Nullable Key key) {
            return this.font(key);
        }

        private static boolean lambda$isEmpty$0(TextDecoration.State state) {
            return state == TextDecoration.State.NOT_SET;
        }
    }
}

