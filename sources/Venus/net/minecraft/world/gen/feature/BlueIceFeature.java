/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BlueIceFeature
extends Feature<NoFeatureConfig> {
    public BlueIceFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        if (blockPos.getY() > iSeedReader.getSeaLevel() - 1) {
            return true;
        }
        if (!iSeedReader.getBlockState(blockPos).isIn(Blocks.WATER) && !iSeedReader.getBlockState(blockPos.down()).isIn(Blocks.WATER)) {
            return true;
        }
        boolean bl = false;
        for (Direction object : Direction.values()) {
            if (object == Direction.DOWN || !iSeedReader.getBlockState(blockPos.offset(object)).isIn(Blocks.PACKED_ICE)) continue;
            bl = true;
            break;
        }
        if (!bl) {
            return true;
        }
        iSeedReader.setBlockState(blockPos, Blocks.BLUE_ICE.getDefaultState(), 2);
        block1: for (int i = 0; i < 200; ++i) {
            BlockPos blockPos2;
            BlockState blockState;
            int n = random2.nextInt(5) - random2.nextInt(6);
            int n2 = 3;
            if (n < 2) {
                n2 += n / 2;
            }
            if (n2 < 1 || (blockState = iSeedReader.getBlockState(blockPos2 = blockPos.add(random2.nextInt(n2) - random2.nextInt(n2), n, random2.nextInt(n2) - random2.nextInt(n2)))).getMaterial() != Material.AIR && !blockState.isIn(Blocks.WATER) && !blockState.isIn(Blocks.PACKED_ICE) && !blockState.isIn(Blocks.ICE)) continue;
            for (Direction direction : Direction.values()) {
                BlockState blockState2 = iSeedReader.getBlockState(blockPos2.offset(direction));
                if (!blockState2.isIn(Blocks.BLUE_ICE)) continue;
                iSeedReader.setBlockState(blockPos2, Blocks.BLUE_ICE.getDefaultState(), 2);
                continue block1;
            }
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

