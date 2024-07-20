/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.CheckReturnValue
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.audience.Audience;
import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.permission.PermissionChecker;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickCallbackInternals;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickCallbackOptionsImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.util.PlatformAPI;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ClickCallback<T extends Audience> {
    public static final Duration DEFAULT_LIFETIME = Duration.ofHours(12L);
    public static final int UNLIMITED_USES = -1;

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    public static <W extends Audience, N extends W> ClickCallback<W> widen(@NotNull ClickCallback<N> original, @NotNull Class<N> type2, @Nullable Consumer<? super Audience> otherwise) {
        return audience -> {
            if (type2.isInstance(audience)) {
                original.accept((Audience)type2.cast(audience));
            } else if (otherwise != null) {
                otherwise.accept(audience);
            }
        };
    }

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    public static <W extends Audience, N extends W> ClickCallback<W> widen(@NotNull ClickCallback<N> original, @NotNull Class<N> type2) {
        return ClickCallback.widen(original, type2, null);
    }

    public void accept(@NotNull T var1);

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    default public ClickCallback<T> filter(@NotNull Predicate<T> filter) {
        return this.filter(filter, null);
    }

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    default public ClickCallback<T> filter(@NotNull Predicate<T> filter, @Nullable Consumer<? super Audience> otherwise) {
        return audience -> {
            if (filter.test(audience)) {
                this.accept(audience);
            } else if (otherwise != null) {
                otherwise.accept(audience);
            }
        };
    }

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    default public ClickCallback<T> requiringPermission(@NotNull String permission) {
        return this.requiringPermission(permission, null);
    }

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    default public ClickCallback<T> requiringPermission(@NotNull String permission, @Nullable Consumer<? super Audience> otherwise) {
        return this.filter(audience -> audience.getOrDefault(PermissionChecker.POINTER, ClickCallbackInternals.ALWAYS_FALSE).test(permission), otherwise);
    }

    @PlatformAPI
    @ApiStatus.Internal
    public static interface Provider {
        @NotNull
        public ClickEvent create(@NotNull ClickCallback<Audience> var1, @NotNull Options var2);
    }

    @ApiStatus.NonExtendable
    public static interface Options
    extends Examinable {
        @NotNull
        public static Builder builder() {
            return new ClickCallbackOptionsImpl.BuilderImpl();
        }

        @NotNull
        public static Builder builder(@NotNull Options existing) {
            return new ClickCallbackOptionsImpl.BuilderImpl(existing);
        }

        public int uses();

        @NotNull
        public Duration lifetime();

        @ApiStatus.NonExtendable
        public static interface Builder
        extends AbstractBuilder<Options> {
            @NotNull
            public Builder uses(int var1);

            @NotNull
            public Builder lifetime(@NotNull TemporalAmount var1);
        }
    }
}

