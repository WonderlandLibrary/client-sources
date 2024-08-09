/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.PatternReplacementResult;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfigImpl;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.IntFunction2;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TextReplacementConfig
extends Buildable<TextReplacementConfig, Builder>,
Examinable {
    @NotNull
    public static Builder builder() {
        return new TextReplacementConfigImpl.Builder();
    }

    @NotNull
    public Pattern matchPattern();

    @FunctionalInterface
    public static interface Condition {
        @NotNull
        public PatternReplacementResult shouldReplace(@NotNull MatchResult var1, int var2, int var3);
    }

    public static interface Builder
    extends AbstractBuilder<TextReplacementConfig>,
    Buildable.Builder<TextReplacementConfig> {
        @Contract(value="_ -> this")
        default public Builder matchLiteral(String string) {
            return this.match(Pattern.compile(string, 16));
        }

        @Contract(value="_ -> this")
        @NotNull
        default public Builder match(@NotNull @RegExp String string) {
            return this.match(Pattern.compile(string));
        }

        @Contract(value="_ -> this")
        @NotNull
        public Builder match(@NotNull Pattern var1);

        @NotNull
        default public Builder once() {
            return this.times(1);
        }

        @Contract(value="_ -> this")
        @NotNull
        default public Builder times(int n) {
            return this.condition((arg_0, arg_1) -> Builder.lambda$times$0(n, arg_0, arg_1));
        }

        @Contract(value="_ -> this")
        @NotNull
        default public Builder condition(@NotNull IntFunction2<PatternReplacementResult> intFunction2) {
            return this.condition((arg_0, arg_1, arg_2) -> Builder.lambda$condition$1(intFunction2, arg_0, arg_1, arg_2));
        }

        @Contract(value="_ -> this")
        @NotNull
        public Builder condition(@NotNull Condition var1);

        @Contract(value="_ -> this")
        @NotNull
        default public Builder replacement(@NotNull String string) {
            Objects.requireNonNull(string, "replacement");
            return this.replacement(arg_0 -> Builder.lambda$replacement$2(string, arg_0));
        }

        @Contract(value="_ -> this")
        @NotNull
        default public Builder replacement(@Nullable ComponentLike componentLike) {
            @Nullable Component component = ComponentLike.unbox(componentLike);
            return this.replacement((arg_0, arg_1) -> Builder.lambda$replacement$3(component, arg_0, arg_1));
        }

        @Contract(value="_ -> this")
        @NotNull
        default public Builder replacement(@NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function) {
            Objects.requireNonNull(function, "replacement");
            return this.replacement((arg_0, arg_1) -> Builder.lambda$replacement$4(function, arg_0, arg_1));
        }

        @Contract(value="_ -> this")
        @NotNull
        public Builder replacement(@NotNull BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> var1);

        private static ComponentLike lambda$replacement$4(Function function, MatchResult matchResult, TextComponent.Builder builder) {
            return (ComponentLike)function.apply(builder);
        }

        private static ComponentLike lambda$replacement$3(Component component, MatchResult matchResult, TextComponent.Builder builder) {
            return component;
        }

        private static ComponentLike lambda$replacement$2(String string, TextComponent.Builder builder) {
            return builder.content(string);
        }

        private static PatternReplacementResult lambda$condition$1(IntFunction2 intFunction2, MatchResult matchResult, int n, int n2) {
            return (PatternReplacementResult)((Object)intFunction2.apply(n, n2));
        }

        private static PatternReplacementResult lambda$times$0(int n, int n2, int n3) {
            return n3 < n ? PatternReplacementResult.REPLACE : PatternReplacementResult.STOP;
        }
    }
}

