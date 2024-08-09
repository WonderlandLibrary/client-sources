/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlockStateFeatureConfig
implements IFeatureConfig {
    public static final Codec<BlockStateFeatureConfig> field_236455_a_ = ((MapCodec)BlockState.CODEC.fieldOf("state")).xmap(BlockStateFeatureConfig::new, BlockStateFeatureConfig::lambda$static$0).codec();
    public final BlockState state;

    public BlockStateFeatureConfig(BlockState blockState) {
        this.state = blockState;
    }

    private static BlockState lambda$static$0(BlockStateFeatureConfig blockStateFeatureConfig) {
        return blockStateFeatureConfig.state;
    }
}

