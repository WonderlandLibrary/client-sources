/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.CheckReturnValue
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
    public static <W extends Audience, N extends W> ClickCallback<W> widen(@NotNull ClickCallback<N> clickCallback, @NotNull Class<N> clazz, @Nullable Consumer<? super Audience> consumer) {
        return arg_0 -> ClickCallback.lambda$widen$0(clazz, clickCallback, consumer, arg_0);
    }

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    public static <W extends Audience, N extends W> ClickCallback<W> widen(@NotNull ClickCallback<N> clickCallback, @NotNull Class<N> clazz) {
        return ClickCallback.widen(clickCallback, clazz, null);
    }

    public void accept(@NotNull T var1);

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    default public ClickCallback<T> filter(@NotNull Predicate<T> predicate) {
        return this.filter(predicate, null);
    }

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    default public ClickCallback<T> filter(@NotNull Predicate<T> predicate, @Nullable Consumer<? super Audience> consumer) {
        return arg_0 -> this.lambda$filter$1(predicate, consumer, arg_0);
    }

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    default public ClickCallback<T> requiringPermission(@NotNull String string) {
        return this.requiringPermission(string, null);
    }

    @CheckReturnValue
    @Contract(pure=true)
    @NotNull
    default public ClickCallback<T> requiringPermission(@NotNull String string, @Nullable Consumer<? super Audience> consumer) {
        return this.filter(arg_0 -> ClickCallback.lambda$requiringPermission$2(string, arg_0), consumer);
    }

    private static boolean lambda$requiringPermission$2(String string, Audience audience) {
        return audience.getOrDefault(PermissionChecker.POINTER, ClickCallbackInternals.ALWAYS_FALSE).test(string);
    }

    private void lambda$filter$1(Predicate predicate, Consumer consumer, Audience audience) {
        if (predicate.test(audience)) {
            this.accept(audience);
        } else if (consumer != null) {
            consumer.accept(audience);
        }
    }

    private static void lambda$widen$0(Class clazz, ClickCallback clickCallback, Consumer consumer, Audience audience) {
        if (clazz.isInstance(audience)) {
            clickCallback.accept((Audience)clazz.cast(audience));
        } else if (consumer != null) {
            consumer.accept(audience);
        }
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
        public static Builder builder(@NotNull Options options) {
            return new ClickCallbackOptionsImpl.BuilderImpl(options);
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

