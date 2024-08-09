/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;

public class AxisRotatingBlockStateProvider
extends BlockStateProvider {
    public static final Codec<AxisRotatingBlockStateProvider> CODEC = ((MapCodec)BlockState.CODEC.fieldOf("state")).xmap(AbstractBlock.AbstractBlockState::getBlock, Block::getDefaultState).xmap(AxisRotatingBlockStateProvider::new, AxisRotatingBlockStateProvider::lambda$static$0).codec();
    private final Block block;

    public AxisRotatingBlockStateProvider(Block block) {
        this.block = block;
    }

    @Override
    protected BlockStateProviderType<?> getProviderType() {
        return BlockStateProviderType.AXIS_ROTATING_STATE_PROVIDER;
    }

    @Override
    public BlockState getBlockState(Random random2, BlockPos blockPos) {
        Direction.Axis axis = Direction.Axis.getRandomAxis(random2);
        return (BlockState)this.block.getDefaultState().with(RotatedPillarBlock.AXIS, axis);
    }

    private static Block lambda$static$0(AxisRotatingBlockStateProvider axisRotatingBlockStateProvider) {
        return axisRotatingBlockStateProvider.block;
    }
}

