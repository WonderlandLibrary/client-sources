/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$Internal
 */
package com.viaversion.viaversion.libs.kyori.adventure.bossbar;

import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBarImplementation;
import com.viaversion.viaversion.libs.kyori.adventure.bossbar.HackyBossBarPlatformBridge;
import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class BossBarImpl
extends HackyBossBarPlatformBridge
implements BossBar {
    private final List<BossBar.Listener> listeners = new CopyOnWriteArrayList<BossBar.Listener>();
    private Component name;
    private float progress;
    private BossBar.Color color;
    private BossBar.Overlay overlay;
    private final Set<BossBar.Flag> flags = EnumSet.noneOf(BossBar.Flag.class);
    @Nullable
    BossBarImplementation implementation;

    BossBarImpl(@NotNull Component component, float f, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay) {
        this.name = Objects.requireNonNull(component, "name");
        this.progress = f;
        this.color = Objects.requireNonNull(color, "color");
        this.overlay = Objects.requireNonNull(overlay, "overlay");
    }

    BossBarImpl(@NotNull Component component, float f, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull Set<BossBar.Flag> set) {
        this(component, f, color, overlay);
        this.flags.addAll(set);
    }

    @Override
    @NotNull
    public Component name() {
        return this.name;
    }

    @Override
    @NotNull
    public BossBar name(@NotNull Component component) {
        Objects.requireNonNull(component, "name");
        Component component2 = this.name;
        if (!Objects.equals(component, component2)) {
            this.name = component;
            this.forEachListener(arg_0 -> this.lambda$name$0(component2, component, arg_0));
        }
        return this;
    }

    @Override
    public float progress() {
        return this.progress;
    }

    @Override
    @NotNull
    public BossBar progress(float f) {
        BossBarImpl.checkProgress(f);
        float f2 = this.progress;
        if (f != f2) {
            this.progress = f;
            this.forEachListener(arg_0 -> this.lambda$progress$1(f2, f, arg_0));
        }
        return this;
    }

    static void checkProgress(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("progress must be between 0.0 and 1.0, was " + f);
        }
    }

    @Override
    @NotNull
    public BossBar.Color color() {
        return this.color;
    }

    @Override
    @NotNull
    public BossBar color(@NotNull BossBar.Color color) {
        Objects.requireNonNull(color, "color");
        BossBar.Color color2 = this.color;
        if (color != color2) {
            this.color = color;
            this.forEachListener(arg_0 -> this.lambda$color$2(color2, color, arg_0));
        }
        return this;
    }

    @Override
    @NotNull
    public BossBar.Overlay overlay() {
        return this.overlay;
    }

    @Override
    @NotNull
    public BossBar overlay(@NotNull BossBar.Overlay overlay) {
        Objects.requireNonNull(overlay, "overlay");
        BossBar.Overlay overlay2 = this.overlay;
        if (overlay != overlay2) {
            this.overlay = overlay;
            this.forEachListener(arg_0 -> this.lambda$overlay$3(overlay2, overlay, arg_0));
        }
        return this;
    }

    @Override
    @NotNull
    public Set<BossBar.Flag> flags() {
        return Collections.unmodifiableSet(this.flags);
    }

    @Override
    @NotNull
    public BossBar flags(@NotNull Set<BossBar.Flag> set) {
        if (set.isEmpty()) {
            EnumSet<BossBar.Flag> enumSet = EnumSet.copyOf(this.flags);
            this.flags.clear();
            this.forEachListener(arg_0 -> this.lambda$flags$4(enumSet, arg_0));
        } else if (!this.flags.equals(set)) {
            EnumSet<BossBar.Flag> enumSet = EnumSet.copyOf(this.flags);
            this.flags.clear();
            this.flags.addAll(set);
            EnumSet<BossBar.Flag> enumSet2 = EnumSet.copyOf(set);
            enumSet2.removeIf(enumSet::contains);
            EnumSet<BossBar.Flag> enumSet3 = EnumSet.copyOf(enumSet);
            enumSet3.removeIf(this.flags::contains);
            this.forEachListener(arg_0 -> this.lambda$flags$5(enumSet2, enumSet3, arg_0));
        }
        return this;
    }

    @Override
    public boolean hasFlag(@NotNull BossBar.Flag flag) {
        return this.flags.contains((Object)flag);
    }

    @Override
    @NotNull
    public BossBar addFlag(@NotNull BossBar.Flag flag) {
        return this.editFlags(flag, Set::add, BossBarImpl::onFlagsAdded);
    }

    @Override
    @NotNull
    public BossBar removeFlag(@NotNull BossBar.Flag flag) {
        return this.editFlags(flag, Set::remove, BossBarImpl::onFlagsRemoved);
    }

    @NotNull
    private BossBar editFlags(@NotNull BossBar.Flag flag, @NotNull BiPredicate<Set<BossBar.Flag>, BossBar.Flag> biPredicate, BiConsumer<BossBarImpl, Set<BossBar.Flag>> biConsumer) {
        if (biPredicate.test(this.flags, flag)) {
            biConsumer.accept(this, Collections.singleton(flag));
        }
        return this;
    }

    @Override
    @NotNull
    public BossBar addFlags(@NotNull @NotNull BossBar.Flag @NotNull ... flagArray) {
        return this.editFlags(flagArray, Set::add, BossBarImpl::onFlagsAdded);
    }

    @Override
    @NotNull
    public BossBar removeFlags(@NotNull @NotNull BossBar.Flag @NotNull ... flagArray) {
        return this.editFlags(flagArray, Set::remove, BossBarImpl::onFlagsRemoved);
    }

    @NotNull
    private BossBar editFlags(BossBar.Flag[] flagArray, BiPredicate<Set<BossBar.Flag>, BossBar.Flag> biPredicate, BiConsumer<BossBarImpl, Set<BossBar.Flag>> biConsumer) {
        if (flagArray.length == 0) {
            return this;
        }
        EnumSet<BossBar.Flag> enumSet = null;
        int n = flagArray.length;
        for (int i = 0; i < n; ++i) {
            if (!biPredicate.test(this.flags, flagArray[i])) continue;
            if (enumSet == null) {
                enumSet = EnumSet.noneOf(BossBar.Flag.class);
            }
            enumSet.add(flagArray[i]);
        }
        if (enumSet != null) {
            biConsumer.accept(this, enumSet);
        }
        return this;
    }

    @Override
    @NotNull
    public BossBar addFlags(@NotNull Iterable<BossBar.Flag> iterable) {
        return this.editFlags(iterable, Set::add, BossBarImpl::onFlagsAdded);
    }

    @Override
    @NotNull
    public BossBar removeFlags(@NotNull Iterable<BossBar.Flag> iterable) {
        return this.editFlags(iterable, Set::remove, BossBarImpl::onFlagsRemoved);
    }

    @NotNull
    private BossBar editFlags(Iterable<BossBar.Flag> iterable, BiPredicate<Set<BossBar.Flag>, BossBar.Flag> biPredicate, BiConsumer<BossBarImpl, Set<BossBar.Flag>> biConsumer) {
        EnumSet<BossBar.Flag> enumSet = null;
        for (BossBar.Flag flag : iterable) {
            if (!biPredicate.test(this.flags, flag)) continue;
            if (enumSet == null) {
                enumSet = EnumSet.noneOf(BossBar.Flag.class);
            }
            enumSet.add(flag);
        }
        if (enumSet != null) {
            biConsumer.accept(this, enumSet);
        }
        return this;
    }

    @Override
    @NotNull
    public BossBar addListener(@NotNull BossBar.Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    @Override
    @NotNull
    public BossBar removeListener(@NotNull BossBar.Listener listener) {
        this.listeners.remove(listener);
        return this;
    }

    private void forEachListener(@NotNull Consumer<BossBar.Listener> consumer) {
        for (BossBar.Listener listener : this.listeners) {
            consumer.accept(listener);
        }
    }

    private static void onFlagsAdded(BossBarImpl bossBarImpl, Set<BossBar.Flag> set) {
        bossBarImpl.forEachListener(arg_0 -> BossBarImpl.lambda$onFlagsAdded$6(bossBarImpl, set, arg_0));
    }

    private static void onFlagsRemoved(BossBarImpl bossBarImpl, Set<BossBar.Flag> set) {
        bossBarImpl.forEachListener(arg_0 -> BossBarImpl.lambda$onFlagsRemoved$7(bossBarImpl, set, arg_0));
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("name", this.name), ExaminableProperty.of("progress", this.progress), ExaminableProperty.of("color", (Object)this.color), ExaminableProperty.of("overlay", (Object)this.overlay), ExaminableProperty.of("flags", this.flags));
    }

    public String toString() {
        return Internals.toString(this);
    }

    private static void lambda$onFlagsRemoved$7(BossBarImpl bossBarImpl, Set set, BossBar.Listener listener) {
        listener.bossBarFlagsChanged(bossBarImpl, Collections.emptySet(), set);
    }

    private static void lambda$onFlagsAdded$6(BossBarImpl bossBarImpl, Set set, BossBar.Listener listener) {
        listener.bossBarFlagsChanged(bossBarImpl, set, Collections.emptySet());
    }

    private void lambda$flags$5(Set set, Set set2, BossBar.Listener listener) {
        listener.bossBarFlagsChanged(this, set, set2);
    }

    private void lambda$flags$4(Set set, BossBar.Listener listener) {
        listener.bossBarFlagsChanged(this, Collections.emptySet(), set);
    }

    private void lambda$overlay$3(BossBar.Overlay overlay, BossBar.Overlay overlay2, BossBar.Listener listener) {
        listener.bossBarOverlayChanged(this, overlay, overlay2);
    }

    private void lambda$color$2(BossBar.Color color, BossBar.Color color2, BossBar.Listener listener) {
        listener.bossBarColorChanged(this, color, color2);
    }

    private void lambda$progress$1(float f, float f2, BossBar.Listener listener) {
        listener.bossBarProgressChanged(this, f, f2);
    }

    private void lambda$name$0(Component component, Component component2, BossBar.Listener listener) {
        listener.bossBarNameChanged(this, component, component2);
    }

    @ApiStatus.Internal
    static final class ImplementationAccessor {
        private static final Optional<BossBarImplementation.Provider> SERVICE = Services.service(BossBarImplementation.Provider.class);

        private ImplementationAccessor() {
        }

        @NotNull
        static <I extends BossBarImplementation> I get(@NotNull BossBar bossBar, @NotNull Class<I> clazz) {
            @Nullable BossBarImplementation bossBarImplementation = ((BossBarImpl)bossBar).implementation;
            if (bossBarImplementation == null) {
                ((BossBarImpl)bossBar).implementation = bossBarImplementation = SERVICE.get().create(bossBar);
            }
            return (I)((BossBarImplementation)clazz.cast(bossBarImplementation));
        }
    }
}

