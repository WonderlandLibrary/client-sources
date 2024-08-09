/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;

public class GrassBlock
extends SpreadableSnowyDirtBlock
implements IGrowable {
    public GrassBlock(AbstractBlock.Properties properties) {
        super(properties);
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
        BlockPos blockPos2 = blockPos.up();
        BlockState blockState2 = Blocks.GRASS.getDefaultState();
        block0: for (int i = 0; i < 128; ++i) {
            BlockState blockState3;
            BlockPos blockPos3 = blockPos2;
            for (int j = 0; j < i / 16; ++j) {
                if (!serverWorld.getBlockState((blockPos3 = blockPos3.add(random2.nextInt(3) - 1, (random2.nextInt(3) - 1) * random2.nextInt(3) / 2, random2.nextInt(3) - 1)).down()).isIn(this) || serverWorld.getBlockState(blockPos3).hasOpaqueCollisionShape(serverWorld, blockPos3)) continue block0;
            }
            BlockState blockState4 = serverWorld.getBlockState(blockPos3);
            if (blockState4.isIn(blockState2.getBlock()) && random2.nextInt(10) == 0) {
                ((IGrowable)((Object)blockState2.getBlock())).grow(serverWorld, random2, blockPos3, blockState4);
            }
            if (!blockState4.isAir()) continue;
            if (random2.nextInt(8) == 0) {
                List<ConfiguredFeature<?, ?>> list = serverWorld.getBiome(blockPos3).getGenerationSettings().getFlowerFeatures();
                if (list.isEmpty()) continue;
                ConfiguredFeature<?, ?> configuredFeature = list.get(0);
                FlowersFeature flowersFeature = (FlowersFeature)configuredFeature.feature;
                blockState3 = flowersFeature.getFlowerToPlace(random2, blockPos3, configuredFeature.func_242767_c());
            } else {
                blockState3 = blockState2;
            }
            if (!blockState3.isValidPosition(serverWorld, blockPos3)) continue;
            serverWorld.setBlockState(blockPos3, blockState3, 0);
        }
    }
}

