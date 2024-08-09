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
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class TwistingVineFeature
extends Feature<NoFeatureConfig> {
    public TwistingVineFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        return TwistingVineFeature.func_236423_a_(iSeedReader, random2, blockPos, 8, 4, 8);
    }

    public static boolean func_236423_a_(IWorld iWorld, Random random2, BlockPos blockPos, int n, int n2, int n3) {
        if (TwistingVineFeature.func_236421_a_(iWorld, blockPos)) {
            return true;
        }
        TwistingVineFeature.func_236424_b_(iWorld, random2, blockPos, n, n2, n3);
        return false;
    }

    private static void func_236424_b_(IWorld iWorld, Random random2, BlockPos blockPos, int n, int n2, int n3) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < n * n; ++i) {
            mutable.setPos(blockPos).move(MathHelper.nextInt(random2, -n, n), MathHelper.nextInt(random2, -n2, n2), MathHelper.nextInt(random2, -n, n));
            if (!TwistingVineFeature.func_236420_a_(iWorld, mutable) || TwistingVineFeature.func_236421_a_(iWorld, mutable)) continue;
            int n4 = MathHelper.nextInt(random2, 1, n3);
            if (random2.nextInt(6) == 0) {
                n4 *= 2;
            }
            if (random2.nextInt(5) == 0) {
                n4 = 1;
            }
            int n5 = 17;
            int n6 = 25;
            TwistingVineFeature.func_236422_a_(iWorld, random2, mutable, n4, 17, 25);
        }
    }

    private static boolean func_236420_a_(IWorld iWorld, BlockPos.Mutable mutable) {
        do {
            mutable.move(0, -1, 0);
            if (!World.isOutsideBuildHeight(mutable)) continue;
            return true;
        } while (iWorld.getBlockState(mutable).isAir());
        mutable.move(0, 1, 0);
        return false;
    }

    public static void func_236422_a_(IWorld iWorld, Random random2, BlockPos.Mutable mutable, int n, int n2, int n3) {
        for (int i = 1; i <= n; ++i) {
            if (iWorld.isAirBlock(mutable)) {
                if (i == n || !iWorld.isAirBlock((BlockPos)mutable.up())) {
                    iWorld.setBlockState(mutable, (BlockState)Blocks.TWISTING_VINES.getDefaultState().with(AbstractTopPlantBlock.AGE, MathHelper.nextInt(random2, n2, n3)), 2);
                    break;
                }
                iWorld.setBlockState(mutable, Blocks.TWISTING_VINES_PLANT.getDefaultState(), 2);
            }
            mutable.move(Direction.UP);
        }
    }

    private static boolean func_236421_a_(IWorld iWorld, BlockPos blockPos) {
        if (!iWorld.isAirBlock(blockPos)) {
            return false;
        }
        BlockState blockState = iWorld.getBlockState(blockPos.down());
        return !blockState.isIn(Blocks.NETHERRACK) && !blockState.isIn(Blocks.WARPED_NYLIUM) && !blockState.isIn(Blocks.WARPED_WART_BLOCK);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

