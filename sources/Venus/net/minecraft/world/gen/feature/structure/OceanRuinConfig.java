/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;

public class OceanRuinConfig
implements IFeatureConfig {
    public static final Codec<OceanRuinConfig> field_236561_a_ = RecordCodecBuilder.create(OceanRuinConfig::lambda$static$3);
    public final OceanRuinStructure.Type field_204031_a;
    public final float largeProbability;
    public final float clusterProbability;

    public OceanRuinConfig(OceanRuinStructure.Type type, float f, float f2) {
        this.field_204031_a = type;
        this.largeProbability = f;
        this.clusterProbability = f2;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)OceanRuinStructure.Type.field_236998_c_.fieldOf("biome_temp")).forGetter(OceanRuinConfig::lambda$static$0), ((MapCodec)Codec.floatRange(0.0f, 1.0f).fieldOf("large_probability")).forGetter(OceanRuinConfig::lambda$static$1), ((MapCodec)Codec.floatRange(0.0f, 1.0f).fieldOf("cluster_probability")).forGetter(OceanRuinConfig::lambda$static$2)).apply(instance, OceanRuinConfig::new);
    }

    private static Float lambda$static$2(OceanRuinConfig oceanRuinConfig) {
        return Float.valueOf(oceanRuinConfig.clusterProbability);
    }

    private static Float lambda$static$1(OceanRuinConfig oceanRuinConfig) {
        return Float.valueOf(oceanRuinConfig.largeProbability);
    }

    private static OceanRuinStructure.Type lambda$static$0(OceanRuinConfig oceanRuinConfig) {
        return oceanRuinConfig.field_204031_a;
    }
}

