/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.SoundEvent;

public class SoundAdditionsAmbience {
    public static final Codec<SoundAdditionsAmbience> CODEC = RecordCodecBuilder.create(SoundAdditionsAmbience::lambda$static$2);
    private SoundEvent sound;
    private double tickChance;

    public SoundAdditionsAmbience(SoundEvent soundEvent, double d) {
        this.sound = soundEvent;
        this.tickChance = d;
    }

    public SoundEvent getSound() {
        return this.sound;
    }

    public double getChancePerTick() {
        return this.tickChance;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)SoundEvent.CODEC.fieldOf("sound")).forGetter(SoundAdditionsAmbience::lambda$static$0), ((MapCodec)Codec.DOUBLE.fieldOf("tick_chance")).forGetter(SoundAdditionsAmbience::lambda$static$1)).apply(instance, SoundAdditionsAmbience::new);
    }

    private static Double lambda$static$1(SoundAdditionsAmbience soundAdditionsAmbience) {
        return soundAdditionsAmbience.tickChance;
    }

    private static SoundEvent lambda$static$0(SoundAdditionsAmbience soundAdditionsAmbience) {
        return soundAdditionsAmbience.sound;
    }
}

