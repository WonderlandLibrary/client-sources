/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class MoodSoundAmbience {
    public static final Codec<MoodSoundAmbience> CODEC = RecordCodecBuilder.create(MoodSoundAmbience::lambda$static$4);
    public static final MoodSoundAmbience DEFAULT_CAVE = new MoodSoundAmbience(SoundEvents.AMBIENT_CAVE, 6000, 8, 2.0);
    private SoundEvent sound;
    private int tickDelay;
    private int searchRadius;
    private double offset;

    public MoodSoundAmbience(SoundEvent soundEvent, int n, int n2, double d) {
        this.sound = soundEvent;
        this.tickDelay = n;
        this.searchRadius = n2;
        this.offset = d;
    }

    public SoundEvent getSound() {
        return this.sound;
    }

    public int getTickDelay() {
        return this.tickDelay;
    }

    public int getSearchRadius() {
        return this.searchRadius;
    }

    public double getOffset() {
        return this.offset;
    }

    private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)SoundEvent.CODEC.fieldOf("sound")).forGetter(MoodSoundAmbience::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("tick_delay")).forGetter(MoodSoundAmbience::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("block_search_extent")).forGetter(MoodSoundAmbience::lambda$static$2), ((MapCodec)Codec.DOUBLE.fieldOf("offset")).forGetter(MoodSoundAmbience::lambda$static$3)).apply(instance, MoodSoundAmbience::new);
    }

    private static Double lambda$static$3(MoodSoundAmbience moodSoundAmbience) {
        return moodSoundAmbience.offset;
    }

    private static Integer lambda$static$2(MoodSoundAmbience moodSoundAmbience) {
        return moodSoundAmbience.searchRadius;
    }

    private static Integer lambda$static$1(MoodSoundAmbience moodSoundAmbience) {
        return moodSoundAmbience.tickDelay;
    }

    private static SoundEvent lambda$static$0(MoodSoundAmbience moodSoundAmbience) {
        return moodSoundAmbience.sound;
    }
}

