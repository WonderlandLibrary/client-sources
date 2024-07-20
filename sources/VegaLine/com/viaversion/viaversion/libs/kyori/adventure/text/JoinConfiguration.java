/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfigurationImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
public interface JoinConfiguration
extends Buildable<JoinConfiguration, Builder>,
Examinable {
    @NotNull
    public static Builder builder() {
        return new JoinConfigurationImpl.BuilderImpl();
    }

    @NotNull
    public static JoinConfiguration noSeparators() {
        return JoinConfigurationImpl.NULL;
    }

    @NotNull
    public static JoinConfiguration newlines() {
        return JoinConfigurationImpl.STANDARD_NEW_LINES;
    }

    @NotNull
    public static JoinConfiguration commas(boolean spaces) {
        return spaces ? JoinConfigurationImpl.STANDARD_COMMA_SPACE_SEPARATED : JoinConfigurationImpl.STANDARD_COMMA_SEPARATED;
    }

    @NotNull
    public static JoinConfiguration arrayLike() {
        return JoinConfigurationImpl.STANDARD_ARRAY_LIKE;
    }

    @NotNull
    public static JoinConfiguration separator(@Nullable ComponentLike separator) {
        if (separator == null) {
            return JoinConfigurationImpl.NULL;
        }
        return (JoinConfiguration)JoinConfiguration.builder().separator(separator).build();
    }

    @NotNull
    public static JoinConfiguration separators(@Nullable ComponentLike separator, @Nullable ComponentLike lastSeparator) {
        if (separator == null && lastSeparator == null) {
            return JoinConfigurationImpl.NULL;
        }
        return (JoinConfiguration)JoinConfiguration.builder().separator(separator).lastSeparator(lastSeparator).build();
    }

    @Nullable
    public Component prefix();

    @Nullable
    public Component suffix();

    @Nullable
    public Component separator();

    @Nullable
    public Component lastSeparator();

    @Nullable
    public Component lastSeparatorIfSerial();

    @NotNull
    public Function<ComponentLike, Component> convertor();

    @NotNull
    public Predicate<ComponentLike> predicate();

    @NotNull
    public Style parentStyle();

    public static interface Builder
    extends AbstractBuilder<JoinConfiguration>,
    Buildable.Builder<JoinConfiguration> {
        @Contract(value="_ -> this")
        @NotNull
        public Builder prefix(@Nullable ComponentLike var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder suffix(@Nullable ComponentLike var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder separator(@Nullable ComponentLike var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder lastSeparator(@Nullable ComponentLike var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder lastSeparatorIfSerial(@Nullable ComponentLike var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder convertor(@NotNull Function<ComponentLike, Component> var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder predicate(@NotNull Predicate<ComponentLike> var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder parentStyle(@NotNull Style var1);
    }
}

