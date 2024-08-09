/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class DesertWellsFeature
extends Feature<NoFeatureConfig> {
    private static final BlockStateMatcher IS_SAND = BlockStateMatcher.forBlock(Blocks.SAND);
    private final BlockState sandSlab = Blocks.SANDSTONE_SLAB.getDefaultState();
    private final BlockState sandstone = Blocks.SANDSTONE.getDefaultState();
    private final BlockState water = Blocks.WATER.getDefaultState();

    public DesertWellsFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        int n;
        int n2;
        int n3;
        blockPos = blockPos.up();
        while (iSeedReader.isAirBlock(blockPos) && blockPos.getY() > 2) {
            blockPos = blockPos.down();
        }
        if (!IS_SAND.test(iSeedReader.getBlockState(blockPos))) {
            return true;
        }
        for (n3 = -2; n3 <= 2; ++n3) {
            for (n2 = -2; n2 <= 2; ++n2) {
                if (!iSeedReader.isAirBlock(blockPos.add(n3, -1, n2)) || !iSeedReader.isAirBlock(blockPos.add(n3, -2, n2))) continue;
                return true;
            }
        }
        for (n3 = -1; n3 <= 0; ++n3) {
            for (n2 = -2; n2 <= 2; ++n2) {
                for (int i = -2; i <= 2; ++i) {
                    iSeedReader.setBlockState(blockPos.add(n2, n3, i), this.sandstone, 2);
                }
            }
        }
        iSeedReader.setBlockState(blockPos, this.water, 2);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            iSeedReader.setBlockState(blockPos.offset(direction), this.water, 2);
        }
        for (n = -2; n <= 2; ++n) {
            for (int i = -2; i <= 2; ++i) {
                if (n != -2 && n != 2 && i != -2 && i != 2) continue;
                iSeedReader.setBlockState(blockPos.add(n, 1, i), this.sandstone, 2);
            }
        }
        iSeedReader.setBlockState(blockPos.add(2, 1, 0), this.sandSlab, 2);
        iSeedReader.setBlockState(blockPos.add(-2, 1, 0), this.sandSlab, 2);
        iSeedReader.setBlockState(blockPos.add(0, 1, 2), this.sandSlab, 2);
        iSeedReader.setBlockState(blockPos.add(0, 1, -2), this.sandSlab, 2);
        for (n = -1; n <= 1; ++n) {
            for (int i = -1; i <= 1; ++i) {
                if (n == 0 && i == 0) {
                    iSeedReader.setBlockState(blockPos.add(n, 4, i), this.sandstone, 2);
                    continue;
                }
                iSeedReader.setBlockState(blockPos.add(n, 4, i), this.sandSlab, 2);
            }
        }
        for (n = 1; n <= 3; ++n) {
            iSeedReader.setBlockState(blockPos.add(-1, n, -1), this.sandstone, 2);
            iSeedReader.setBlockState(blockPos.add(-1, n, 1), this.sandstone, 2);
            iSeedReader.setBlockState(blockPos.add(1, n, -1), this.sandstone, 2);
            iSeedReader.setBlockState(blockPos.add(1, n, 1), this.sandstone, 2);
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

