/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NetherVegetationFeature;
import net.minecraft.world.gen.feature.TwistingVineFeature;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public class NyliumBlock
extends Block
implements IGrowable {
    protected NyliumBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    private static boolean isDarkEnough(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.up();
        BlockState blockState2 = iWorldReader.getBlockState(blockPos2);
        int n = LightEngine.func_215613_a(iWorldReader, blockState, blockPos, blockState2, blockPos2, Direction.UP, blockState2.getOpacity(iWorldReader, blockPos2));
        return n < iWorldReader.getMaxLightLevel();
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!NyliumBlock.isDarkEnough(blockState, serverWorld, blockPos)) {
            serverWorld.setBlockState(blockPos, Blocks.NETHERRACK.getDefaultState());
        }
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return iBlockReader.getBlockState(blockPos.up()).isAir();
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        BlockState blockState2 = serverWorld.getBlockState(blockPos);
        BlockPos blockPos2 = blockPos.up();
        if (blockState2.isIn(Blocks.CRIMSON_NYLIUM)) {
            NetherVegetationFeature.func_236325_a_(serverWorld, random2, blockPos2, Features.Configs.CRIMSON_FOREST_VEGETATION_CONFIG, 3, 1);
        } else if (blockState2.isIn(Blocks.WARPED_NYLIUM)) {
            NetherVegetationFeature.func_236325_a_(serverWorld, random2, blockPos2, Features.Configs.WARPED_FOREST_VEGETATION_CONFIG, 3, 1);
            NetherVegetationFeature.func_236325_a_(serverWorld, random2, blockPos2, Features.Configs.NETHER_SPROUTS_CONFIG, 3, 1);
            if (random2.nextInt(8) == 0) {
                TwistingVineFeature.func_236423_a_(serverWorld, random2, blockPos2, 3, 1, 2);
            }
        }
    }
}

