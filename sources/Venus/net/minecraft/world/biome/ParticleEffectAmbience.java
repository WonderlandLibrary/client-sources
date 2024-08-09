/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;

public class ParticleEffectAmbience {
    public static final Codec<ParticleEffectAmbience> CODEC = RecordCodecBuilder.create(ParticleEffectAmbience::lambda$static$2);
    private final IParticleData particleOptions;
    private final float probability;

    public ParticleEffectAmbience(IParticleData iParticleData, float f) {
        this.particleOptions = iParticleData;
        this.probability = f;
    }

    public IParticleData getParticleOptions() {
        return this.particleOptions;
    }

    public boolean shouldParticleSpawn(Random random2) {
        return random2.nextFloat() <= this.probability;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)ParticleTypes.CODEC.fieldOf("options")).forGetter(ParticleEffectAmbience::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("probability")).forGetter(ParticleEffectAmbience::lambda$static$1)).apply(instance, ParticleEffectAmbience::new);
    }

    private static Float lambda$static$1(ParticleEffectAmbience particleEffectAmbience) {
        return Float.valueOf(particleEffectAmbience.probability);
    }

    private static IParticleData lambda$static$0(ParticleEffectAmbience particleEffectAmbience) {
        return particleEffectAmbience.particleOptions;
    }
}

