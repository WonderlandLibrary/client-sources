/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.audio.IAmbientSoundHandler;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.SoundAdditionsAmbience;

public class BiomeSoundHandler
implements IAmbientSoundHandler {
    private final ClientPlayerEntity player;
    private final SoundHandler soundHandler;
    private final BiomeManager biomeManager;
    private final Random random;
    private Object2ObjectArrayMap<Biome, Sound> activeBiomeSoundsMap = new Object2ObjectArrayMap();
    private Optional<MoodSoundAmbience> currentAmbientMoodSound = Optional.empty();
    private Optional<SoundAdditionsAmbience> currentAmbientAdditionalSound = Optional.empty();
    private float darknessAmbienceChance;
    private Biome currentBiome;

    public BiomeSoundHandler(ClientPlayerEntity clientPlayerEntity, SoundHandler soundHandler, BiomeManager biomeManager) {
        this.random = clientPlayerEntity.world.getRandom();
        this.player = clientPlayerEntity;
        this.soundHandler = soundHandler;
        this.biomeManager = biomeManager;
    }

    public float getDarknessAmbienceChance() {
        return this.darknessAmbienceChance;
    }

    @Override
    public void tick() {
        this.activeBiomeSoundsMap.values().removeIf(TickableSound::isDonePlaying);
        Biome biome = this.biomeManager.getBiomeAtPosition(this.player.getPosX(), this.player.getPosY(), this.player.getPosZ());
        if (biome != this.currentBiome) {
            this.currentBiome = biome;
            this.currentAmbientMoodSound = biome.getMoodSound();
            this.currentAmbientAdditionalSound = biome.getAdditionalAmbientSound();
            this.activeBiomeSoundsMap.values().forEach(Sound::fadeOutSound);
            biome.getAmbientSound().ifPresent(arg_0 -> this.lambda$tick$1(biome, arg_0));
        }
        this.currentAmbientAdditionalSound.ifPresent(this::lambda$tick$2);
        this.currentAmbientMoodSound.ifPresent(this::lambda$tick$3);
    }

    private void lambda$tick$3(MoodSoundAmbience moodSoundAmbience) {
        World world = this.player.world;
        int n = moodSoundAmbience.getSearchRadius() * 2 + 1;
        BlockPos blockPos = new BlockPos(this.player.getPosX() + (double)this.random.nextInt(n) - (double)moodSoundAmbience.getSearchRadius(), this.player.getPosYEye() + (double)this.random.nextInt(n) - (double)moodSoundAmbience.getSearchRadius(), this.player.getPosZ() + (double)this.random.nextInt(n) - (double)moodSoundAmbience.getSearchRadius());
        int n2 = world.getLightFor(LightType.SKY, blockPos);
        this.darknessAmbienceChance = n2 > 0 ? (this.darknessAmbienceChance -= (float)n2 / (float)world.getMaxLightLevel() * 0.001f) : (this.darknessAmbienceChance -= (float)(world.getLightFor(LightType.BLOCK, blockPos) - 1) / (float)moodSoundAmbience.getTickDelay());
        if (this.darknessAmbienceChance >= 1.0f) {
            double d = (double)blockPos.getX() + 0.5;
            double d2 = (double)blockPos.getY() + 0.5;
            double d3 = (double)blockPos.getZ() + 0.5;
            double d4 = d - this.player.getPosX();
            double d5 = d2 - this.player.getPosYEye();
            double d6 = d3 - this.player.getPosZ();
            double d7 = MathHelper.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
            double d8 = d7 + moodSoundAmbience.getOffset();
            SimpleSound simpleSound = SimpleSound.ambientWithAttenuation(moodSoundAmbience.getSound(), this.player.getPosX() + d4 / d7 * d8, this.player.getPosYEye() + d5 / d7 * d8, this.player.getPosZ() + d6 / d7 * d8);
            this.soundHandler.play(simpleSound);
            this.darknessAmbienceChance = 0.0f;
        } else {
            this.darknessAmbienceChance = Math.max(this.darknessAmbienceChance, 0.0f);
        }
    }

    private void lambda$tick$2(SoundAdditionsAmbience soundAdditionsAmbience) {
        if (this.random.nextDouble() < soundAdditionsAmbience.getChancePerTick()) {
            this.soundHandler.play(SimpleSound.ambient(soundAdditionsAmbience.getSound()));
        }
    }

    private void lambda$tick$1(Biome biome, SoundEvent soundEvent) {
        Sound sound = this.activeBiomeSoundsMap.compute(biome, (arg_0, arg_1) -> this.lambda$tick$0(soundEvent, arg_0, arg_1));
    }

    private Sound lambda$tick$0(SoundEvent soundEvent, Biome biome, Sound sound) {
        if (sound == null) {
            sound = new Sound(soundEvent);
            this.soundHandler.play(sound);
        }
        sound.fadeInSound();
        return sound;
    }

    public static class Sound
    extends TickableSound {
        private int fadeSpeed;
        private int fadeInTicks;

        public Sound(SoundEvent soundEvent) {
            super(soundEvent, SoundCategory.AMBIENT);
            this.repeat = true;
            this.repeatDelay = 0;
            this.volume = 1.0f;
            this.global = true;
        }

        @Override
        public void tick() {
            if (this.fadeInTicks < 0) {
                this.finishPlaying();
            }
            this.fadeInTicks += this.fadeSpeed;
            this.volume = MathHelper.clamp((float)this.fadeInTicks / 40.0f, 0.0f, 1.0f);
        }

        public void fadeOutSound() {
            this.fadeInTicks = Math.min(this.fadeInTicks, 40);
            this.fadeSpeed = -1;
        }

        public void fadeInSound() {
            this.fadeInTicks = Math.max(0, this.fadeInTicks);
            this.fadeSpeed = 1;
        }
    }
}

