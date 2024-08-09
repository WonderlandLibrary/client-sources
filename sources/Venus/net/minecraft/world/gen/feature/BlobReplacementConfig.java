/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlobReplacementConfig
implements IFeatureConfig {
    public static final Codec<BlobReplacementConfig> field_242817_a = RecordCodecBuilder.create(BlobReplacementConfig::lambda$static$3);
    public final BlockState field_242818_b;
    public final BlockState field_242819_c;
    private final FeatureSpread field_242820_d;

    public BlobReplacementConfig(BlockState blockState, BlockState blockState2, FeatureSpread featureSpread) {
        this.field_242818_b = blockState;
        this.field_242819_c = blockState2;
        this.field_242820_d = featureSpread;
    }

    public FeatureSpread func_242823_b() {
        return this.field_242820_d;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockState.CODEC.fieldOf("target")).forGetter(BlobReplacementConfig::lambda$static$0), ((MapCodec)BlockState.CODEC.fieldOf("state")).forGetter(BlobReplacementConfig::lambda$static$1), ((MapCodec)FeatureSpread.field_242249_a.fieldOf("radius")).forGetter(BlobReplacementConfig::lambda$static$2)).apply(instance, BlobReplacementConfig::new);
    }

    private static FeatureSpread lambda$static$2(BlobReplacementConfig blobReplacementConfig) {
        return blobReplacementConfig.field_242820_d;
    }

    private static BlockState lambda$static$1(BlobReplacementConfig blobReplacementConfig) {
        return blobReplacementConfig.field_242819_c;
    }

    private static BlockState lambda$static$0(BlobReplacementConfig blobReplacementConfig) {
        return blobReplacementConfig.field_242818_b;
    }
}

