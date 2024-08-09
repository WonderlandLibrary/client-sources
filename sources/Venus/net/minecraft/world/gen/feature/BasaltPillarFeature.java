/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
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

public class BasaltPillarFeature
extends Feature<NoFeatureConfig> {
    public BasaltPillarFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        if (iSeedReader.isAirBlock(blockPos) && !iSeedReader.isAirBlock(blockPos.up())) {
            BlockPos.Mutable mutable = blockPos.toMutable();
            BlockPos.Mutable mutable2 = blockPos.toMutable();
            boolean bl = true;
            boolean bl2 = true;
            boolean bl3 = true;
            boolean bl4 = true;
            while (iSeedReader.isAirBlock(mutable)) {
                if (World.isOutsideBuildHeight(mutable)) {
                    return false;
                }
                iSeedReader.setBlockState(mutable, Blocks.BASALT.getDefaultState(), 2);
                bl = bl && this.func_236253_b_(iSeedReader, random2, mutable2.setAndMove(mutable, Direction.NORTH));
                bl2 = bl2 && this.func_236253_b_(iSeedReader, random2, mutable2.setAndMove(mutable, Direction.SOUTH));
                bl3 = bl3 && this.func_236253_b_(iSeedReader, random2, mutable2.setAndMove(mutable, Direction.WEST));
                bl4 = bl4 && this.func_236253_b_(iSeedReader, random2, mutable2.setAndMove(mutable, Direction.EAST));
                mutable.move(Direction.DOWN);
            }
            mutable.move(Direction.UP);
            this.func_236252_a_(iSeedReader, random2, mutable2.setAndMove(mutable, Direction.NORTH));
            this.func_236252_a_(iSeedReader, random2, mutable2.setAndMove(mutable, Direction.SOUTH));
            this.func_236252_a_(iSeedReader, random2, mutable2.setAndMove(mutable, Direction.WEST));
            this.func_236252_a_(iSeedReader, random2, mutable2.setAndMove(mutable, Direction.EAST));
            mutable.move(Direction.DOWN);
            BlockPos.Mutable mutable3 = new BlockPos.Mutable();
            for (int i = -3; i < 4; ++i) {
                for (int j = -3; j < 4; ++j) {
                    int n = MathHelper.abs(i) * MathHelper.abs(j);
                    if (random2.nextInt(10) >= 10 - n) continue;
                    mutable3.setPos(mutable.add(i, 0, j));
                    int n2 = 3;
                    while (iSeedReader.isAirBlock(mutable2.setAndMove(mutable3, Direction.DOWN))) {
                        mutable3.move(Direction.DOWN);
                        if (--n2 > 0) continue;
                    }
                    if (iSeedReader.isAirBlock(mutable2.setAndMove(mutable3, Direction.DOWN))) continue;
                    iSeedReader.setBlockState(mutable3, Blocks.BASALT.getDefaultState(), 2);
                }
            }
            return false;
        }
        return true;
    }

    private void func_236252_a_(IWorld iWorld, Random random2, BlockPos blockPos) {
        if (random2.nextBoolean()) {
            iWorld.setBlockState(blockPos, Blocks.BASALT.getDefaultState(), 2);
        }
    }

    private boolean func_236253_b_(IWorld iWorld, Random random2, BlockPos blockPos) {
        if (random2.nextInt(10) != 0) {
            iWorld.setBlockState(blockPos, Blocks.BASALT.getDefaultState(), 2);
            return false;
        }
        return true;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

