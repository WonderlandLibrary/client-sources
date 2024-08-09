/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.ApiStatus$OverrideOnly
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.UnmodifiableView
 */
package com.viaversion.viaversion.libs.kyori.adventure.bossbar;

import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBarImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

@ApiStatus.NonExtendable
public interface BossBar
extends Examinable {
    public static final float MIN_PROGRESS = 0.0f;
    public static final float MAX_PROGRESS = 1.0f;
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    public static final float MIN_PERCENT = 0.0f;
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    public static final float MAX_PERCENT = 1.0f;

    @NotNull
    public static BossBar bossBar(@NotNull ComponentLike componentLike, float f, @NotNull Color color, @NotNull Overlay overlay) {
        BossBarImpl.checkProgress(f);
        return BossBar.bossBar(componentLike.asComponent(), f, color, overlay);
    }

    @NotNull
    public static BossBar bossBar(@NotNull Component component, float f, @NotNull Color color, @NotNull Overlay overlay) {
        BossBarImpl.checkProgress(f);
        return new BossBarImpl(component, f, color, overlay);
    }

    @NotNull
    public static BossBar bossBar(@NotNull ComponentLike componentLike, float f, @NotNull Color color, @NotNull Overlay overlay, @NotNull Set<Flag> set) {
        BossBarImpl.checkProgress(f);
        return BossBar.bossBar(componentLike.asComponent(), f, color, overlay, set);
    }

    @NotNull
    public static BossBar bossBar(@NotNull Component component, float f, @NotNull Color color, @NotNull Overlay overlay, @NotNull Set<Flag> set) {
        BossBarImpl.checkProgress(f);
        return new BossBarImpl(component, f, color, overlay, set);
    }

    @NotNull
    public Component name();

    @Contract(value="_ -> this")
    @NotNull
    default public BossBar name(@NotNull ComponentLike componentLike) {
        return this.name(componentLike.asComponent());
    }

    @Contract(value="_ -> this")
    @NotNull
    public BossBar name(@NotNull Component var1);

    public float progress();

    @Contract(value="_ -> this")
    @NotNull
    public BossBar progress(float var1);

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    default public float percent() {
        return this.progress();
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(value="_ -> this")
    @NotNull
    default public BossBar percent(float f) {
        return this.progress(f);
    }

    @NotNull
    public Color color();

    @Contract(value="_ -> this")
    @NotNull
    public BossBar color(@NotNull Color var1);

    @NotNull
    public Overlay overlay();

    @Contract(value="_ -> this")
    @NotNull
    public BossBar overlay(@NotNull Overlay var1);

    public @UnmodifiableView @NotNull Set<Flag> flags();

    @Contract(value="_ -> this")
    @NotNull
    public BossBar flags(@NotNull Set<Flag> var1);

    public boolean hasFlag(@NotNull Flag var1);

    @Contract(value="_ -> this")
    @NotNull
    public BossBar addFlag(@NotNull Flag var1);

    @Contract(value="_ -> this")
    @NotNull
    public BossBar removeFlag(@NotNull Flag var1);

    @Contract(value="_ -> this")
    @NotNull
    public BossBar addFlags(@NotNull @NotNull Flag @NotNull ... var1);

    @Contract(value="_ -> this")
    @NotNull
    public BossBar removeFlags(@NotNull @NotNull Flag @NotNull ... var1);

    @Contract(value="_ -> this")
    @NotNull
    public BossBar addFlags(@NotNull Iterable<Flag> var1);

    @Contract(value="_ -> this")
    @NotNull
    public BossBar removeFlags(@NotNull Iterable<Flag> var1);

    @Contract(value="_ -> this")
    @NotNull
    public BossBar addListener(@NotNull Listener var1);

    @Contract(value="_ -> this")
    @NotNull
    public BossBar removeListener(@NotNull Listener var1);

    public static enum Overlay {
        PROGRESS("progress"),
        NOTCHED_6("notched_6"),
        NOTCHED_10("notched_10"),
        NOTCHED_12("notched_12"),
        NOTCHED_20("notched_20");

        public static final Index<String, Overlay> NAMES;
        private final String name;

        private Overlay(String string2) {
            this.name = string2;
        }

        private static String lambda$static$0(Overlay overlay) {
            return overlay.name;
        }

        static {
            NAMES = Index.create(Overlay.class, Overlay::lambda$static$0);
        }
    }

    public static enum Flag {
        DARKEN_SCREEN("darken_screen"),
        PLAY_BOSS_MUSIC("play_boss_music"),
        CREATE_WORLD_FOG("create_world_fog");

        public static final Index<String, Flag> NAMES;
        private final String name;

        private Flag(String string2) {
            this.name = string2;
        }

        private static String lambda$static$0(Flag flag) {
            return flag.name;
        }

        static {
            NAMES = Index.create(Flag.class, Flag::lambda$static$0);
        }
    }

    public static enum Color {
        PINK("pink"),
        BLUE("blue"),
        RED("red"),
        GREEN("green"),
        YELLOW("yellow"),
        PURPLE("purple"),
        WHITE("white");

        public static final Index<String, Color> NAMES;
        private final String name;

        private Color(String string2) {
            this.name = string2;
        }

        private static String lambda$static$0(Color color) {
            return color.name;
        }

        static {
            NAMES = Index.create(Color.class, Color::lambda$static$0);
        }
    }

    @ApiStatus.OverrideOnly
    public static interface Listener {
        default public void bossBarNameChanged(@NotNull BossBar bossBar, @NotNull Component component, @NotNull Component component2) {
        }

        default public void bossBarProgressChanged(@NotNull BossBar bossBar, float f, float f2) {
            this.bossBarPercentChanged(bossBar, f, f2);
        }

        @Deprecated
        @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
        default public void bossBarPercentChanged(@NotNull BossBar bossBar, float f, float f2) {
        }

        default public void bossBarColorChanged(@NotNull BossBar bossBar, @NotNull Color color, @NotNull Color color2) {
        }

        default public void bossBarOverlayChanged(@NotNull BossBar bossBar, @NotNull Overlay overlay, @NotNull Overlay overlay2) {
        }

        default public void bossBarFlagsChanged(@NotNull BossBar bossBar, @NotNull Set<Flag> set, @NotNull Set<Flag> set2) {
        }
    }
}

