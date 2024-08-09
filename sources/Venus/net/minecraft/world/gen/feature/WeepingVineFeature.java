/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WeepingVineFeature
extends Feature<NoFeatureConfig> {
    private static final Direction[] field_236426_a_ = Direction.values();

    public WeepingVineFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        if (!iSeedReader.isAirBlock(blockPos)) {
            return true;
        }
        BlockState blockState = iSeedReader.getBlockState(blockPos.up());
        if (!blockState.isIn(Blocks.NETHERRACK) && !blockState.isIn(Blocks.NETHER_WART_BLOCK)) {
            return true;
        }
        this.func_236428_a_(iSeedReader, random2, blockPos);
        this.func_236429_b_(iSeedReader, random2, blockPos);
        return false;
    }

    private void func_236428_a_(IWorld iWorld, Random random2, BlockPos blockPos) {
        iWorld.setBlockState(blockPos, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();
        for (int i = 0; i < 200; ++i) {
            mutable.setAndOffset(blockPos, random2.nextInt(6) - random2.nextInt(6), random2.nextInt(2) - random2.nextInt(5), random2.nextInt(6) - random2.nextInt(6));
            if (!iWorld.isAirBlock(mutable)) continue;
            int n = 0;
            for (Direction direction : field_236426_a_) {
                BlockState blockState = iWorld.getBlockState(mutable2.setAndMove(mutable, direction));
                if (blockState.isIn(Blocks.NETHERRACK) || blockState.isIn(Blocks.NETHER_WART_BLOCK)) {
                    ++n;
                }
                if (n > 1) break;
            }
            if (n != true) continue;
            iWorld.setBlockState(mutable, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
        }
    }

    private void func_236429_b_(IWorld iWorld, Random random2, BlockPos blockPos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 100; ++i) {
            BlockState blockState;
            mutable.setAndOffset(blockPos, random2.nextInt(8) - random2.nextInt(8), random2.nextInt(2) - random2.nextInt(7), random2.nextInt(8) - random2.nextInt(8));
            if (!iWorld.isAirBlock(mutable) || !(blockState = iWorld.getBlockState((BlockPos)mutable.up())).isIn(Blocks.NETHERRACK) && !blockState.isIn(Blocks.NETHER_WART_BLOCK)) continue;
            int n = MathHelper.nextInt(random2, 1, 8);
            if (random2.nextInt(6) == 0) {
                n *= 2;
            }
            if (random2.nextInt(5) == 0) {
                n = 1;
            }
            int n2 = 17;
            int n3 = 25;
            WeepingVineFeature.func_236427_a_(iWorld, random2, mutable, n, 17, 25);
        }
    }

    public static void func_236427_a_(IWorld iWorld, Random random2, BlockPos.Mutable mutable, int n, int n2, int n3) {
        for (int i = 0; i <= n; ++i) {
            if (iWorld.isAirBlock(mutable)) {
                if (i == n || !iWorld.isAirBlock((BlockPos)mutable.down())) {
                    iWorld.setBlockState(mutable, (BlockState)Blocks.WEEPING_VINES.getDefaultState().with(AbstractTopPlantBlock.AGE, MathHelper.nextInt(random2, n2, n3)), 2);
                    break;
                }
                iWorld.setBlockState(mutable, Blocks.WEEPING_VINES_PLANT.getDefaultState(), 2);
            }
            mutable.move(Direction.DOWN);
        }
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

