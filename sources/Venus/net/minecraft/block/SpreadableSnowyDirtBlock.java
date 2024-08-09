/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public abstract class SpreadableSnowyDirtBlock
extends SnowyDirtBlock {
    protected SpreadableSnowyDirtBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    private static boolean isSnowyConditions(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.up();
        BlockState blockState2 = iWorldReader.getBlockState(blockPos2);
        if (blockState2.isIn(Blocks.SNOW) && blockState2.get(SnowBlock.LAYERS) == 1) {
            return false;
        }
        if (blockState2.getFluidState().getLevel() == 8) {
            return true;
        }
        int n = LightEngine.func_215613_a(iWorldReader, blockState, blockPos, blockState2, blockPos2, Direction.UP, blockState2.getOpacity(iWorldReader, blockPos2));
        return n < iWorldReader.getMaxLightLevel();
    }

    private static boolean isSnowyAndNotUnderwater(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.up();
        return SpreadableSnowyDirtBlock.isSnowyConditions(blockState, iWorldReader, blockPos) && !iWorldReader.getFluidState(blockPos2).isTagged(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!SpreadableSnowyDirtBlock.isSnowyConditions(blockState, serverWorld, blockPos)) {
            serverWorld.setBlockState(blockPos, Blocks.DIRT.getDefaultState());
        } else if (serverWorld.getLight(blockPos.up()) >= 9) {
            BlockState blockState2 = this.getDefaultState();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos2 = blockPos.add(random2.nextInt(3) - 1, random2.nextInt(5) - 3, random2.nextInt(3) - 1);
                if (!serverWorld.getBlockState(blockPos2).isIn(Blocks.DIRT) || !SpreadableSnowyDirtBlock.isSnowyAndNotUnderwater(blockState2, serverWorld, blockPos2)) continue;
                serverWorld.setBlockState(blockPos2, (BlockState)blockState2.with(SNOWY, serverWorld.getBlockState(blockPos2.up()).isIn(Blocks.SNOW)));
            }
        }
    }
}

