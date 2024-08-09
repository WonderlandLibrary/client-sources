/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.UnknownNullability
 */
package com.viaversion.viaversion.libs.kyori.adventure.title;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.title.TitleImpl;
import com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart;
import com.viaversion.viaversion.libs.kyori.adventure.util.Ticks;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.time.Duration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

@ApiStatus.NonExtendable
public interface Title
extends Examinable {
    public static final Times DEFAULT_TIMES = Times.times(Ticks.duration(10L), Ticks.duration(70L), Ticks.duration(20L));

    @NotNull
    public static Title title(@NotNull Component component, @NotNull Component component2) {
        return Title.title(component, component2, DEFAULT_TIMES);
    }

    @NotNull
    public static Title title(@NotNull Component component, @NotNull Component component2, @Nullable Times times) {
        return new TitleImpl(component, component2, times);
    }

    @NotNull
    public Component title();

    @NotNull
    public Component subtitle();

    @Nullable
    public Times times();

    public <T> @UnknownNullability T part(@NotNull TitlePart<T> var1);

    public static interface Times
    extends Examinable {
        @Deprecated
        @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
        @NotNull
        public static Times of(@NotNull Duration duration, @NotNull Duration duration2, @NotNull Duration duration3) {
            return Times.times(duration, duration2, duration3);
        }

        @NotNull
        public static Times times(@NotNull Duration duration, @NotNull Duration duration2, @NotNull Duration duration3) {
            return new TitleImpl.TimesImpl(duration, duration2, duration3);
        }

        @NotNull
        public Duration fadeIn();

        @NotNull
        public Duration stay();

        @NotNull
        public Duration fadeOut();
    }
}

