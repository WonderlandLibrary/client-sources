/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Range
 */
package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundImpl;
import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

@ApiStatus.NonExtendable
public interface Sound
extends Examinable {
    @NotNull
    public static Builder sound() {
        return new SoundImpl.BuilderImpl();
    }

    @NotNull
    public static Builder sound(@NotNull Sound existing) {
        return new SoundImpl.BuilderImpl(existing);
    }

    @NotNull
    public static Sound sound(@NotNull Consumer<Builder> configurer) {
        return (Sound)AbstractBuilder.configureAndBuild(Sound.sound(), configurer);
    }

    @NotNull
    public static Sound sound(@NotNull Key name, @NotNull Source source, float volume, float pitch) {
        return (Sound)Sound.sound().type(name).source(source).volume(volume).pitch(pitch).build();
    }

    @NotNull
    public static Sound sound(@NotNull Type type2, @NotNull Source source, float volume, float pitch) {
        Objects.requireNonNull(type2, "type");
        return Sound.sound(type2.key(), source, volume, pitch);
    }

    @NotNull
    public static Sound sound(@NotNull Supplier<? extends Type> type2, @NotNull Source source, float volume, float pitch) {
        return (Sound)Sound.sound().type(type2).source(source).volume(volume).pitch(pitch).build();
    }

    @NotNull
    public static Sound sound(@NotNull Key name, @NotNull Source.Provider source, float volume, float pitch) {
        return Sound.sound(name, source.soundSource(), volume, pitch);
    }

    @NotNull
    public static Sound sound(@NotNull Type type2, @NotNull Source.Provider source, float volume, float pitch) {
        return Sound.sound(type2, source.soundSource(), volume, pitch);
    }

    @NotNull
    public static Sound sound(@NotNull Supplier<? extends Type> type2, @NotNull Source.Provider source, float volume, float pitch) {
        return Sound.sound(type2, source.soundSource(), volume, pitch);
    }

    @NotNull
    public Key name();

    @NotNull
    public Source source();

    public float volume();

    public float pitch();

    @NotNull
    public OptionalLong seed();

    @NotNull
    public SoundStop asStop();

    public static interface Builder
    extends AbstractBuilder<Sound> {
        @NotNull
        public Builder type(@NotNull Key var1);

        @NotNull
        public Builder type(@NotNull Type var1);

        @NotNull
        public Builder type(@NotNull Supplier<? extends Type> var1);

        @NotNull
        public Builder source(@NotNull Source var1);

        @NotNull
        public Builder source(@NotNull Source.Provider var1);

        @NotNull
        public Builder volume(@Range(from=0L, to=0x7FFFFFFFL) float var1);

        @NotNull
        public Builder pitch(@Range(from=-1L, to=1L) float var1);

        @NotNull
        public Builder seed(long var1);

        @NotNull
        public Builder seed(@NotNull OptionalLong var1);
    }

    public static interface Emitter {
        @NotNull
        public static Emitter self() {
            return SoundImpl.EMITTER_SELF;
        }
    }

    public static interface Type
    extends Keyed {
        @Override
        @NotNull
        public Key key();
    }

    public static enum Source {
        MASTER("master"),
        MUSIC("music"),
        RECORD("record"),
        WEATHER("weather"),
        BLOCK("block"),
        HOSTILE("hostile"),
        NEUTRAL("neutral"),
        PLAYER("player"),
        AMBIENT("ambient"),
        VOICE("voice");

        public static final Index<String, Source> NAMES;
        private final String name;

        private Source(String name) {
            this.name = name;
        }

        static {
            NAMES = Index.create(Source.class, source -> source.name);
        }

        public static interface Provider {
            @NotNull
            public Source soundSource();
        }
    }
}

