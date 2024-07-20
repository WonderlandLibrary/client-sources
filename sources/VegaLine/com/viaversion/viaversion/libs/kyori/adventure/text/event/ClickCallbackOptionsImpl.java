/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickCallback;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

final class ClickCallbackOptionsImpl
implements ClickCallback.Options {
    static final ClickCallback.Options DEFAULT = new BuilderImpl().build();
    private final int uses;
    private final Duration lifetime;

    ClickCallbackOptionsImpl(int uses, Duration lifetime) {
        this.uses = uses;
        this.lifetime = lifetime;
    }

    @Override
    public int uses() {
        return this.uses;
    }

    @Override
    @NotNull
    public Duration lifetime() {
        return this.lifetime;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("uses", this.uses), ExaminableProperty.of("expiration", this.lifetime));
    }

    public String toString() {
        return Internals.toString(this);
    }

    static final class BuilderImpl
    implements ClickCallback.Options.Builder {
        private static final int DEFAULT_USES = 1;
        private int uses;
        private Duration lifetime;

        BuilderImpl() {
            this.uses = 1;
            this.lifetime = ClickCallback.DEFAULT_LIFETIME;
        }

        BuilderImpl(@NotNull ClickCallback.Options existing) {
            this.uses = existing.uses();
            this.lifetime = existing.lifetime();
        }

        @Override
        public @NotNull ClickCallback.Options build() {
            return new ClickCallbackOptionsImpl(this.uses, this.lifetime);
        }

        @Override
        @NotNull
        public ClickCallback.Options.Builder uses(int uses) {
            this.uses = uses;
            return this;
        }

        @Override
        @NotNull
        public ClickCallback.Options.Builder lifetime(@NotNull TemporalAmount lifetime) {
            this.lifetime = lifetime instanceof Duration ? (Duration)lifetime : Duration.from(Objects.requireNonNull(lifetime, "lifetime"));
            return this;
        }
    }
}

