/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class NoiseDependant
implements IPlacementConfig {
    public static final Codec<NoiseDependant> field_236550_a_ = RecordCodecBuilder.create(NoiseDependant::lambda$static$3);
    public final double noiseLevel;
    public final int belowNoise;
    public final int aboveNoise;

    public NoiseDependant(double d, int n, int n2) {
        this.noiseLevel = d;
        this.belowNoise = n;
        this.aboveNoise = n2;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.DOUBLE.fieldOf("noise_level")).forGetter(NoiseDependant::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("below_noise")).forGetter(NoiseDependant::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("above_noise")).forGetter(NoiseDependant::lambda$static$2)).apply(instance, NoiseDependant::new);
    }

    private static Integer lambda$static$2(NoiseDependant noiseDependant) {
        return noiseDependant.aboveNoise;
    }

    private static Integer lambda$static$1(NoiseDependant noiseDependant) {
        return noiseDependant.belowNoise;
    }

    private static Double lambda$static$0(NoiseDependant noiseDependant) {
        return noiseDependant.noiseLevel;
    }
}

