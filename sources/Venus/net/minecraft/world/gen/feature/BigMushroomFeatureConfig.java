/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BigMushroomFeatureConfig
implements IFeatureConfig {
    public static final Codec<BigMushroomFeatureConfig> field_236528_a_ = RecordCodecBuilder.create(BigMushroomFeatureConfig::lambda$static$3);
    public final BlockStateProvider field_227272_a_;
    public final BlockStateProvider field_227273_b_;
    public final int field_227274_c_;

    public BigMushroomFeatureConfig(BlockStateProvider blockStateProvider, BlockStateProvider blockStateProvider2, int n) {
        this.field_227272_a_ = blockStateProvider;
        this.field_227273_b_ = blockStateProvider2;
        this.field_227274_c_ = n;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockStateProvider.CODEC.fieldOf("cap_provider")).forGetter(BigMushroomFeatureConfig::lambda$static$0), ((MapCodec)BlockStateProvider.CODEC.fieldOf("stem_provider")).forGetter(BigMushroomFeatureConfig::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("foliage_radius")).orElse(2).forGetter(BigMushroomFeatureConfig::lambda$static$2)).apply(instance, BigMushroomFeatureConfig::new);
    }

    private static Integer lambda$static$2(BigMushroomFeatureConfig bigMushroomFeatureConfig) {
        return bigMushroomFeatureConfig.field_227274_c_;
    }

    private static BlockStateProvider lambda$static$1(BigMushroomFeatureConfig bigMushroomFeatureConfig) {
        return bigMushroomFeatureConfig.field_227273_b_;
    }

    private static BlockStateProvider lambda$static$0(BigMushroomFeatureConfig bigMushroomFeatureConfig) {
        return bigMushroomFeatureConfig.field_227272_a_;
    }
}

