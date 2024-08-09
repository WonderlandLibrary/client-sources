/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class CoralFeature
extends Feature<NoFeatureConfig> {
    public CoralFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        BlockState blockState = ((Block)BlockTags.CORAL_BLOCKS.getRandomElement(random2)).getDefaultState();
        return this.func_204623_a(iSeedReader, random2, blockPos, blockState);
    }

    protected abstract boolean func_204623_a(IWorld var1, Random var2, BlockPos var3, BlockState var4);

    protected boolean func_204624_b(IWorld iWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        BlockPos blockPos2 = blockPos.up();
        BlockState blockState2 = iWorld.getBlockState(blockPos);
        if ((blockState2.isIn(Blocks.WATER) || blockState2.isIn(BlockTags.CORALS)) && iWorld.getBlockState(blockPos2).isIn(Blocks.WATER)) {
            iWorld.setBlockState(blockPos, blockState, 3);
            if (random2.nextFloat() < 0.25f) {
                iWorld.setBlockState(blockPos2, ((Block)BlockTags.CORALS.getRandomElement(random2)).getDefaultState(), 2);
            } else if (random2.nextFloat() < 0.05f) {
                iWorld.setBlockState(blockPos2, (BlockState)Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, random2.nextInt(4) + 1), 2);
            }
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockPos3;
                if (!(random2.nextFloat() < 0.2f) || !iWorld.getBlockState(blockPos3 = blockPos.offset(direction)).isIn(Blocks.WATER)) continue;
                BlockState blockState3 = (BlockState)((Block)BlockTags.WALL_CORALS.getRandomElement(random2)).getDefaultState().with(DeadCoralWallFanBlock.FACING, direction);
                iWorld.setBlockState(blockPos3, blockState3, 2);
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

