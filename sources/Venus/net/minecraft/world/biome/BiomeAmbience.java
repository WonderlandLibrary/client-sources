/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.SoundAdditionsAmbience;

public class BiomeAmbience {
    public static final Codec<BiomeAmbience> CODEC = RecordCodecBuilder.create(BiomeAmbience::lambda$static$12);
    private final int fogColor;
    private final int waterColor;
    private final int waterFogColor;
    private final int skyColor;
    private final Optional<Integer> foliageColor;
    private final Optional<Integer> grassColor;
    private final GrassColorModifier grassColorModifier;
    private final Optional<ParticleEffectAmbience> particle;
    private final Optional<SoundEvent> ambientSound;
    private final Optional<MoodSoundAmbience> moodSound;
    private final Optional<SoundAdditionsAmbience> additionsSound;
    private final Optional<BackgroundMusicSelector> music;

    private BiomeAmbience(int n, int n2, int n3, int n4, Optional<Integer> optional, Optional<Integer> optional2, GrassColorModifier grassColorModifier, Optional<ParticleEffectAmbience> optional3, Optional<SoundEvent> optional4, Optional<MoodSoundAmbience> optional5, Optional<SoundAdditionsAmbience> optional6, Optional<BackgroundMusicSelector> optional7) {
        this.fogColor = n;
        this.waterColor = n2;
        this.waterFogColor = n3;
        this.skyColor = n4;
        this.foliageColor = optional;
        this.grassColor = optional2;
        this.grassColorModifier = grassColorModifier;
        this.particle = optional3;
        this.ambientSound = optional4;
        this.moodSound = optional5;
        this.additionsSound = optional6;
        this.music = optional7;
    }

    public int getFogColor() {
        return this.fogColor;
    }

    public int getWaterColor() {
        return this.waterColor;
    }

    public int getWaterFogColor() {
        return this.waterFogColor;
    }

    public int getSkyColor() {
        return this.skyColor;
    }

    public Optional<Integer> getFoliageColor() {
        return this.foliageColor;
    }

    public Optional<Integer> getGrassColor() {
        return this.grassColor;
    }

    public GrassColorModifier getGrassColorModifier() {
        return this.grassColorModifier;
    }

    public Optional<ParticleEffectAmbience> getParticle() {
        return this.particle;
    }

    public Optional<SoundEvent> getAmbientSound() {
        return this.ambientSound;
    }

    public Optional<MoodSoundAmbience> getMoodSound() {
        return this.moodSound;
    }

    public Optional<SoundAdditionsAmbience> getAdditionsSound() {
        return this.additionsSound;
    }

    public Optional<BackgroundMusicSelector> getMusic() {
        return this.music;
    }

    private static App lambda$static$12(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.INT.fieldOf("fog_color")).forGetter(BiomeAmbience::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("water_color")).forGetter(BiomeAmbience::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("water_fog_color")).forGetter(BiomeAmbience::lambda$static$2), ((MapCodec)Codec.INT.fieldOf("sky_color")).forGetter(BiomeAmbience::lambda$static$3), Codec.INT.optionalFieldOf("foliage_color").forGetter(BiomeAmbience::lambda$static$4), Codec.INT.optionalFieldOf("grass_color").forGetter(BiomeAmbience::lambda$static$5), GrassColorModifier.CODEC.optionalFieldOf("grass_color_modifier", GrassColorModifier.NONE).forGetter(BiomeAmbience::lambda$static$6), ParticleEffectAmbience.CODEC.optionalFieldOf("particle").forGetter(BiomeAmbience::lambda$static$7), SoundEvent.CODEC.optionalFieldOf("ambient_sound").forGetter(BiomeAmbience::lambda$static$8), MoodSoundAmbience.CODEC.optionalFieldOf("mood_sound").forGetter(BiomeAmbience::lambda$static$9), SoundAdditionsAmbience.CODEC.optionalFieldOf("additions_sound").forGetter(BiomeAmbience::lambda$static$10), BackgroundMusicSelector.CODEC.optionalFieldOf("music").forGetter(BiomeAmbience::lambda$static$11)).apply(instance, BiomeAmbience::new);
    }

    private static Optional lambda$static$11(BiomeAmbience biomeAmbience) {
        return biomeAmbience.music;
    }

    private static Optional lambda$static$10(BiomeAmbience biomeAmbience) {
        return biomeAmbience.additionsSound;
    }

    private static Optional lambda$static$9(BiomeAmbience biomeAmbience) {
        return biomeAmbience.moodSound;
    }

    private static Optional lambda$static$8(BiomeAmbience biomeAmbience) {
        return biomeAmbience.ambientSound;
    }

    private static Optional lambda$static$7(BiomeAmbience biomeAmbience) {
        return biomeAmbience.particle;
    }

    private static GrassColorModifier lambda$static$6(BiomeAmbience biomeAmbience) {
        return biomeAmbience.grassColorModifier;
    }

    private static Optional lambda$static$5(BiomeAmbience biomeAmbience) {
        return biomeAmbience.grassColor;
    }

    private static Optional lambda$static$4(BiomeAmbience biomeAmbience) {
        return biomeAmbience.foliageColor;
    }

    private static Integer lambda$static$3(BiomeAmbience biomeAmbience) {
        return biomeAmbience.skyColor;
    }

    private static Integer lambda$static$2(BiomeAmbience biomeAmbience) {
        return biomeAmbience.waterFogColor;
    }

    private static Integer lambda$static$1(BiomeAmbience biomeAmbience) {
        return biomeAmbience.waterColor;
    }

    private static Integer lambda$static$0(BiomeAmbience biomeAmbience) {
        return biomeAmbience.fogColor;
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static enum GrassColorModifier implements IStringSerializable
    {
        NONE("none"){

            @Override
            public int getModifiedGrassColor(double d, double d2, int n) {
                return n;
            }
        }
        ,
        DARK_FOREST("dark_forest"){

            @Override
            public int getModifiedGrassColor(double d, double d2, int n) {
                return (n & 0xFEFEFE) + 2634762 >> 1;
            }
        }
        ,
        SWAMP("swamp"){

            @Override
            public int getModifiedGrassColor(double d, double d2, int n) {
                double d3 = Biome.INFO_NOISE.noiseAt(d * 0.0225, d2 * 0.0225, true);
                return d3 < -0.1 ? 5011004 : 6975545;
            }
        };

        private final String name;
        public static final Codec<GrassColorModifier> CODEC;
        private static final Map<String, GrassColorModifier> NAME_TO_MODIFIER_MAP;

        public abstract int getModifiedGrassColor(double var1, double var3, int var5);

        private GrassColorModifier(String string2) {
            this.name = string2;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String getString() {
            return this.name;
        }

        public static GrassColorModifier byName(String string) {
            return NAME_TO_MODIFIER_MAP.get(string);
        }

        private static GrassColorModifier lambda$static$0(GrassColorModifier grassColorModifier) {
            return grassColorModifier;
        }

        static {
            CODEC = IStringSerializable.createEnumCodec(GrassColorModifier::values, GrassColorModifier::byName);
            NAME_TO_MODIFIER_MAP = Arrays.stream(GrassColorModifier.values()).collect(Collectors.toMap(GrassColorModifier::getName, GrassColorModifier::lambda$static$0));
        }
    }

    public static class Builder {
        private OptionalInt fogColor = OptionalInt.empty();
        private OptionalInt waterColor = OptionalInt.empty();
        private OptionalInt waterFogColor = OptionalInt.empty();
        private OptionalInt skyColor = OptionalInt.empty();
        private Optional<Integer> foliageColor = Optional.empty();
        private Optional<Integer> grassColor = Optional.empty();
        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;
        private Optional<ParticleEffectAmbience> particle = Optional.empty();
        private Optional<SoundEvent> ambientSound = Optional.empty();
        private Optional<MoodSoundAmbience> moodSound = Optional.empty();
        private Optional<SoundAdditionsAmbience> additionsSound = Optional.empty();
        private Optional<BackgroundMusicSelector> music = Optional.empty();

        public Builder setFogColor(int n) {
            this.fogColor = OptionalInt.of(n);
            return this;
        }

        public Builder setWaterColor(int n) {
            this.waterColor = OptionalInt.of(n);
            return this;
        }

        public Builder setWaterFogColor(int n) {
            this.waterFogColor = OptionalInt.of(n);
            return this;
        }

        public Builder withSkyColor(int n) {
            this.skyColor = OptionalInt.of(n);
            return this;
        }

        public Builder withFoliageColor(int n) {
            this.foliageColor = Optional.of(n);
            return this;
        }

        public Builder withGrassColor(int n) {
            this.grassColor = Optional.of(n);
            return this;
        }

        public Builder withGrassColorModifier(GrassColorModifier grassColorModifier) {
            this.grassColorModifier = grassColorModifier;
            return this;
        }

        public Builder setParticle(ParticleEffectAmbience particleEffectAmbience) {
            this.particle = Optional.of(particleEffectAmbience);
            return this;
        }

        public Builder setAmbientSound(SoundEvent soundEvent) {
            this.ambientSound = Optional.of(soundEvent);
            return this;
        }

        public Builder setMoodSound(MoodSoundAmbience moodSoundAmbience) {
            this.moodSound = Optional.of(moodSoundAmbience);
            return this;
        }

        public Builder setAdditionsSound(SoundAdditionsAmbience soundAdditionsAmbience) {
            this.additionsSound = Optional.of(soundAdditionsAmbience);
            return this;
        }

        public Builder setMusic(BackgroundMusicSelector backgroundMusicSelector) {
            this.music = Optional.of(backgroundMusicSelector);
            return this;
        }

        public BiomeAmbience build() {
            return new BiomeAmbience(this.fogColor.orElseThrow(Builder::lambda$build$0), this.waterColor.orElseThrow(Builder::lambda$build$1), this.waterFogColor.orElseThrow(Builder::lambda$build$2), this.skyColor.orElseThrow(Builder::lambda$build$3), this.foliageColor, this.grassColor, this.grassColorModifier, this.particle, this.ambientSound, this.moodSound, this.additionsSound, this.music);
        }

        private static IllegalStateException lambda$build$3() {
            return new IllegalStateException("Missing 'sky' color.");
        }

        private static IllegalStateException lambda$build$2() {
            return new IllegalStateException("Missing 'water fog' color.");
        }

        private static IllegalStateException lambda$build$1() {
            return new IllegalStateException("Missing 'water' color.");
        }

        private static IllegalStateException lambda$build$0() {
            return new IllegalStateException("Missing 'fog' color.");
        }
    }
}

