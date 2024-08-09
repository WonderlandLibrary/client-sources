/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;

public class MineshaftConfig
implements IFeatureConfig {
    public static final Codec<MineshaftConfig> field_236541_a_ = RecordCodecBuilder.create(MineshaftConfig::lambda$static$2);
    public final float probability;
    public final MineshaftStructure.Type type;

    public MineshaftConfig(float f, MineshaftStructure.Type type) {
        this.probability = f;
        this.type = type;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.floatRange(0.0f, 1.0f).fieldOf("probability")).forGetter(MineshaftConfig::lambda$static$0), ((MapCodec)MineshaftStructure.Type.field_236324_c_.fieldOf("type")).forGetter(MineshaftConfig::lambda$static$1)).apply(instance, MineshaftConfig::new);
    }

    private static MineshaftStructure.Type lambda$static$1(MineshaftConfig mineshaftConfig) {
        return mineshaftConfig.type;
    }

    private static Float lambda$static$0(MineshaftConfig mineshaftConfig) {
        return Float.valueOf(mineshaftConfig.probability);
    }
}

