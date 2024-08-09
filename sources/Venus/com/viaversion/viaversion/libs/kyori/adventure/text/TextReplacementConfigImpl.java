/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.PatternReplacementResult;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementRenderer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class TextReplacementConfigImpl
implements TextReplacementConfig {
    private final Pattern matchPattern;
    private final BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> replacement;
    private final TextReplacementConfig.Condition continuer;

    TextReplacementConfigImpl(Builder builder) {
        this.matchPattern = builder.matchPattern;
        this.replacement = builder.replacement;
        this.continuer = builder.continuer;
    }

    @Override
    @NotNull
    public Pattern matchPattern() {
        return this.matchPattern;
    }

    TextReplacementRenderer.State createState() {
        return new TextReplacementRenderer.State(this.matchPattern, this.replacement, this.continuer);
    }

    @Override
    public @NotNull TextReplacementConfig.Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("matchPattern", this.matchPattern), ExaminableProperty.of("replacement", this.replacement), ExaminableProperty.of("continuer", this.continuer));
    }

    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public @NotNull Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    static Pattern access$000(TextReplacementConfigImpl textReplacementConfigImpl) {
        return textReplacementConfigImpl.matchPattern;
    }

    static BiFunction access$100(TextReplacementConfigImpl textReplacementConfigImpl) {
        return textReplacementConfigImpl.replacement;
    }

    static TextReplacementConfig.Condition access$200(TextReplacementConfigImpl textReplacementConfigImpl) {
        return textReplacementConfigImpl.continuer;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class Builder
    implements TextReplacementConfig.Builder {
        @Nullable
        Pattern matchPattern;
        @Nullable
        @Nullable BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> replacement;
        TextReplacementConfig.Condition continuer = Builder::lambda$new$0;

        Builder() {
        }

        Builder(TextReplacementConfigImpl textReplacementConfigImpl) {
            this.matchPattern = TextReplacementConfigImpl.access$000(textReplacementConfigImpl);
            this.replacement = TextReplacementConfigImpl.access$100(textReplacementConfigImpl);
            this.continuer = TextReplacementConfigImpl.access$200(textReplacementConfigImpl);
        }

        @Override
        @NotNull
        public Builder match(@NotNull Pattern pattern) {
            this.matchPattern = Objects.requireNonNull(pattern, "pattern");
            return this;
        }

        @Override
        @NotNull
        public Builder condition(@NotNull TextReplacementConfig.Condition condition) {
            this.continuer = Objects.requireNonNull(condition, "continuation");
            return this;
        }

        @Override
        @NotNull
        public Builder replacement(@NotNull BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> biFunction) {
            this.replacement = Objects.requireNonNull(biFunction, "replacement");
            return this;
        }

        @Override
        @NotNull
        public TextReplacementConfig build() {
            if (this.matchPattern == null) {
                throw new IllegalStateException("A pattern must be provided to match against");
            }
            if (this.replacement == null) {
                throw new IllegalStateException("A replacement action must be provided");
            }
            return new TextReplacementConfigImpl(this);
        }

        @NotNull
        public TextReplacementConfig.Builder replacement(/*
         * Issues handling annotations - annotations may be inaccurate
         */
        @Nullable @NotNull BiFunction biFunction) {
            return this.replacement(biFunction);
        }

        @Override
        @NotNull
        public TextReplacementConfig.Builder condition(@NotNull TextReplacementConfig.Condition condition) {
            return this.condition(condition);
        }

        @Override
        @NotNull
        public TextReplacementConfig.Builder match(@NotNull Pattern pattern) {
            return this.match(pattern);
        }

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }

        private static PatternReplacementResult lambda$new$0(MatchResult matchResult, int n, int n2) {
            return PatternReplacementResult.REPLACE;
        }
    }
}

