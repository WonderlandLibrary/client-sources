/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BasaltDeltasFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BasaltDeltasStructure
extends Feature<BasaltDeltasFeature> {
    private static final ImmutableList<Block> field_236274_a_ = ImmutableList.of(Blocks.BEDROCK, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);
    private static final Direction[] field_236275_ac_ = Direction.values();

    public BasaltDeltasStructure(Codec<BasaltDeltasFeature> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BasaltDeltasFeature basaltDeltasFeature) {
        boolean bl = false;
        boolean bl2 = random2.nextDouble() < 0.9;
        int n = bl2 ? basaltDeltasFeature.func_242808_e().func_242259_a(random2) : 0;
        int n2 = bl2 ? basaltDeltasFeature.func_242808_e().func_242259_a(random2) : 0;
        boolean bl3 = bl2 && n != 0 && n2 != 0;
        int n3 = basaltDeltasFeature.func_242807_d().func_242259_a(random2);
        int n4 = basaltDeltasFeature.func_242807_d().func_242259_a(random2);
        int n5 = Math.max(n3, n4);
        for (BlockPos blockPos2 : BlockPos.getProximitySortedBoxPositionsIterator(blockPos, n3, 0, n4)) {
            BlockPos blockPos3;
            if (blockPos2.manhattanDistance(blockPos) > n5) break;
            if (!BasaltDeltasStructure.func_236277_a_(iSeedReader, blockPos2, basaltDeltasFeature)) continue;
            if (bl3) {
                bl = true;
                this.setBlockState(iSeedReader, blockPos2, basaltDeltasFeature.func_242806_c());
            }
            if (!BasaltDeltasStructure.func_236277_a_(iSeedReader, blockPos3 = blockPos2.add(n, 0, n2), basaltDeltasFeature)) continue;
            bl = true;
            this.setBlockState(iSeedReader, blockPos3, basaltDeltasFeature.func_242804_b());
        }
        return bl;
    }

    private static boolean func_236277_a_(IWorld iWorld, BlockPos blockPos, BasaltDeltasFeature basaltDeltasFeature) {
        BlockState blockState = iWorld.getBlockState(blockPos);
        if (blockState.isIn(basaltDeltasFeature.func_242804_b().getBlock())) {
            return true;
        }
        if (field_236274_a_.contains(blockState.getBlock())) {
            return true;
        }
        for (Direction direction : field_236275_ac_) {
            boolean bl = iWorld.getBlockState(blockPos.offset(direction)).isAir();
            if ((!bl || direction == Direction.UP) && (bl || direction != Direction.UP)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BasaltDeltasFeature)iFeatureConfig);
    }
}

