/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
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

    SoundImpl(@NotNull Sound.Source source, float f, float f2, OptionalLong optionalLong) {
        this.source = source;
        this.volume = f;
        this.pitch = f2;
        this.seed = optionalLong;
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

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof SoundImpl)) {
            return true;
        }
        SoundImpl soundImpl = (SoundImpl)object;
        return this.name().equals(soundImpl.name()) && this.source == soundImpl.source && ShadyPines.equals(this.volume, soundImpl.volume) && ShadyPines.equals(this.pitch, soundImpl.pitch) && this.seed.equals(soundImpl.seed);
    }

    public int hashCode() {
        int n = this.name().hashCode();
        n = 31 * n + this.source.hashCode();
        n = 31 * n + Float.hashCode(this.volume);
        n = 31 * n + Float.hashCode(this.pitch);
        n = 31 * n + this.seed.hashCode();
        return n;
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

        Lazy(@NotNull Supplier<? extends Sound.Type> supplier, @NotNull Sound.Source source, float f, float f2, OptionalLong optionalLong) {
            super(source, f, f2, optionalLong);
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

        Eager(@NotNull Key key, @NotNull Sound.Source source, float f, float f2, OptionalLong optionalLong) {
            super(source, f, f2, optionalLong);
            this.name = key;
        }

        @Override
        @NotNull
        public Key name() {
            return this.name;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
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

        BuilderImpl(@NotNull Sound sound) {
            if (sound instanceof Eager) {
                this.type(((Eager)sound).name);
            } else if (sound instanceof Lazy) {
                this.type(((Lazy)sound).supplier);
            } else {
                throw new IllegalArgumentException("Unknown sound type " + sound + ", must be Eager or Lazy");
            }
            this.source(sound.source()).volume(sound.volume()).pitch(sound.pitch()).seed(sound.seed());
        }

        @Override
        @NotNull
        public Sound.Builder type(@NotNull Key key) {
            this.eagerType = Objects.requireNonNull(key, "type");
            this.lazyType = null;
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder type(@NotNull Sound.Type type) {
            this.eagerType = Objects.requireNonNull(Objects.requireNonNull(type, "type").key(), "type.key()");
            this.lazyType = null;
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder type(@NotNull Supplier<? extends Sound.Type> supplier) {
            this.lazyType = Objects.requireNonNull(supplier, "typeSupplier");
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
        public Sound.Builder source(@NotNull Sound.Source.Provider provider) {
            return this.source(provider.soundSource());
        }

        @Override
        @NotNull
        public Sound.Builder volume(@Range(from=0L, to=0x7FFFFFFFL) float f) {
            this.volume = f;
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder pitch(@Range(from=-1L, to=1L) float f) {
            this.pitch = f;
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder seed(long l) {
            this.seed = OptionalLong.of(l);
            return this;
        }

        @Override
        @NotNull
        public Sound.Builder seed(@NotNull OptionalLong optionalLong) {
            this.seed = Objects.requireNonNull(optionalLong, "seed");
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

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }
    }
}

