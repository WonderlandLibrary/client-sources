/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BambooLeaves;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class BambooFeature
extends Feature<ProbabilityConfig> {
    private static final BlockState BAMBOO_BASE = (BlockState)((BlockState)((BlockState)Blocks.BAMBOO.getDefaultState().with(BambooBlock.PROPERTY_AGE, 1)).with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.NONE)).with(BambooBlock.PROPERTY_STAGE, 0);
    private static final BlockState BAMBOO_LARGE_LEAVES_GROWN = (BlockState)((BlockState)BAMBOO_BASE.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.LARGE)).with(BambooBlock.PROPERTY_STAGE, 1);
    private static final BlockState BAMBOO_LARGE_LEAVES = (BlockState)BAMBOO_BASE.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.LARGE);
    private static final BlockState BAMBOO_SMALL_LEAVES = (BlockState)BAMBOO_BASE.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.SMALL);

    public BambooFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, ProbabilityConfig probabilityConfig) {
        int n = 0;
        BlockPos.Mutable mutable = blockPos.toMutable();
        BlockPos.Mutable mutable2 = blockPos.toMutable();
        if (iSeedReader.isAirBlock(mutable)) {
            if (Blocks.BAMBOO.getDefaultState().isValidPosition(iSeedReader, mutable)) {
                int n2;
                int n3 = random2.nextInt(12) + 5;
                if (random2.nextFloat() < probabilityConfig.probability) {
                    n2 = random2.nextInt(4) + 1;
                    for (int i = blockPos.getX() - n2; i <= blockPos.getX() + n2; ++i) {
                        for (int j = blockPos.getZ() - n2; j <= blockPos.getZ() + n2; ++j) {
                            int n4;
                            int n5 = i - blockPos.getX();
                            if (n5 * n5 + (n4 = j - blockPos.getZ()) * n4 > n2 * n2) continue;
                            mutable2.setPos(i, iSeedReader.getHeight(Heightmap.Type.WORLD_SURFACE, i, j) - 1, j);
                            if (!BambooFeature.isDirt(iSeedReader.getBlockState(mutable2).getBlock())) continue;
                            iSeedReader.setBlockState(mutable2, Blocks.PODZOL.getDefaultState(), 2);
                        }
                    }
                }
                for (n2 = 0; n2 < n3 && iSeedReader.isAirBlock(mutable); ++n2) {
                    iSeedReader.setBlockState(mutable, BAMBOO_BASE, 2);
                    mutable.move(Direction.UP, 1);
                }
                if (mutable.getY() - blockPos.getY() >= 3) {
                    iSeedReader.setBlockState(mutable, BAMBOO_LARGE_LEAVES_GROWN, 2);
                    iSeedReader.setBlockState(mutable.move(Direction.DOWN, 1), BAMBOO_LARGE_LEAVES, 2);
                    iSeedReader.setBlockState(mutable.move(Direction.DOWN, 1), BAMBOO_SMALL_LEAVES, 2);
                }
            }
            ++n;
        }
        return n > 0;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (ProbabilityConfig)iFeatureConfig);
    }
}

