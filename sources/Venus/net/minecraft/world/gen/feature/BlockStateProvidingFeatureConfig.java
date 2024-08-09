/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlockStateProvidingFeatureConfig
implements IFeatureConfig {
    public static final Codec<BlockStateProvidingFeatureConfig> field_236453_a_ = ((MapCodec)BlockStateProvider.CODEC.fieldOf("state_provider")).xmap(BlockStateProvidingFeatureConfig::new, BlockStateProvidingFeatureConfig::lambda$static$0).codec();
    public final BlockStateProvider field_227268_a_;

    public BlockStateProvidingFeatureConfig(BlockStateProvider blockStateProvider) {
        this.field_227268_a_ = blockStateProvider;
    }

    private static BlockStateProvider lambda$static$0(BlockStateProvidingFeatureConfig blockStateProvidingFeatureConfig) {
        return blockStateProvidingFeatureConfig.field_227268_a_;
    }
}

