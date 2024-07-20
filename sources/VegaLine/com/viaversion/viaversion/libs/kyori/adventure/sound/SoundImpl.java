/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jetbrains.annotations.Range
 */
package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop;
import com.viaversion.viaversion.libs.kyori.adventure.util.ShadyPines;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

abstract class SoundImpl
implements Sound {
    static final Sound.Emitter EMITTER_SELF = new Sound.Emitter(){

        public String toString() {
            return "SelfSoundEmitter";
        }
    };
    private final Sound.Source source;
    private final float volume;
    private final float pitch;
    private final OptionalLong seed;
    private SoundStop stop;

    SoundImpl(@NotNull Sound.Source source, float volume, float pitch, OptionalLong seed) {
        this.source = source;
        this.volume = volume;
        this.pitch = pitch;
        this.seed = seed;
    }

    @Override
    @NotNull
    public Sound.Source source() {
        return this.source;
    }

    @Override
    public float volume() {
        return this.volume;
    }

    @Override
    public float pitch() {
        return this.pitch;
    }

    @Override
    public OptionalLong seed() {
        return this.seed;
    }

    @Override
    @NotNull
    public SoundStop asStop() {
        if (this.stop == null) {
            this.stop = SoundStop.namedOnSource(this.name(), this.source());
        }
        return this.stop;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundImpl)) {
            return false;
        }
        SoundImpl that = (SoundImpl)other;
        return this.name().equals(that.name()) && this.source == that.source && ShadyPines.equals(this.volume, that.volume) && ShadyPines.equals(this.pitch, that.pitch) && this.seed.equals(that.seed);
    }

    public int hashCode() {
        int result = this.name().hashCode();
        result = 31 * result + this.source.hashCode();
        result = 31 * result + Float.hashCode(this.volume);
        result = 31 * result + Float.hashCode(this.pitch);
        result = 31 * result + this.seed.hashCode();
        return result;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("name", this.name()), ExaminableProperty.of("source", (Object)this.source), ExaminableProperty.of("volume", this.volume), ExaminableProperty.of("pitch", this.pitch), ExaminableProperty.of("seed", this.seed));
    }

    public String toString() {
        return Internals.toString(this);
    }

    static final class Lazy
    extends SoundImpl {
        final Supplier<? extends Sound.Type> supplier;

        Lazy(@NotNull Supplier<? extends Sound.Type> supplier, @NotNull Sound.Source source, float volume, float pitch, OptionalLong seed) {
            super(source, volume, pitch, seed);
            this.supplier = supplier;
        }

        @Override
        @NotNull
        public Key name() {
            return this.supplier.get().key();
        }
    }

    static final class Eager
    extends SoundImpl {
        final Key name;

        Eager(@NotNull Key name, @NotNull Sound.Source source, float volume, float pitch, OptionalLong seed) {
            super(source, volume, pitch, seed);
            this.name = name;
        }

        @Override
        @NotNull
        public Key name() {
            return this.name;
        }
    }

    static final class BuilderImpl
    implements Sound.Builder {
        private static final float DEFAULT_VOLUME = 1.0f;
        private static final float DEFAULT_PITCH = 1.0f;
        private Key eagerType;
        private Supplier<? extends Sound.Type> lazyType;
        private Sound.Source source = Sound.Source.MASTER;
        private float volume = 1.0f;
        private float pitch = 1.0f;
        private OptionalLong seed = OptionalLong.empty();

        BuilderImpl() {
        }

        BuilderImpl(@NotNull Sound existing) {
            if (existing instanceof Eager) {
                this.type(((Eager)existing).name);
            } else if (existing instanceof Lazy) {
                this.type(((Lazy)existing).supplier);
            } else {
                throw new IllegalArgumentException("Unknown sound type " + existing + ", must be Eager or Lazy");
            }
            this.source(existing.source()).volume(existing.volume()).pitch(existing.pitch()).seed(existing.seed());
        }

        @Override
        @NotNull
        public Sound.Builder type(@NotNull Key type2) {
            this.eagerType = Objects.requireNonNull(type2, "type");
            this.lazyType = null;
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder type(@NotNull Sound.Type type2) {
            this.eagerType = Objects.requireNonNull(Objects.requireNonNull(type2, "type").key(), "type.key()");
            this.lazyType = null;
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder type(@NotNull Supplier<? extends Sound.Type> typeSupplier) {
            this.lazyType = Objects.requireNonNull(typeSupplier, "typeSupplier");
            this.eagerType = null;
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder source(@NotNull Sound.Source source) {
            this.source = Objects.requireNonNull(source, "source");
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder source(@NotNull Sound.Source.Provider source) {
            return this.source(source.soundSource());
        }

        @Override
        @NotNull
        public Sound.Builder volume(@Range(from=0L, to=0x7FFFFFFFL) float volume) {
            this.volume = volume;
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder pitch(@Range(from=-1L, to=1L) float pitch) {
            this.pitch = pitch;
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder seed(long seed) {
            this.seed = OptionalLong.of(seed);
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder seed(@NotNull OptionalLong seed) {
            this.seed = Objects.requireNonNull(seed, "seed");
            return this;
        }

        @Override
        @NotNull
        public Sound build() {
            if (this.eagerType != null) {
                return new Eager(this.eagerType, this.source, this.volume, this.pitch, this.seed);
            }
            if (this.lazyType != null) {
                return new Lazy(this.lazyType, this.source, this.volume, this.pitch, this.seed);
            }
            throw new IllegalStateException("A sound type must be provided to build a sound");
        }
    }
}

