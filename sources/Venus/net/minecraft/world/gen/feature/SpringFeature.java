/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;

public class SpringFeature
extends Feature<LiquidsConfig> {
    public SpringFeature(Codec<LiquidsConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, LiquidsConfig liquidsConfig) {
        if (!liquidsConfig.acceptedBlocks.contains(iSeedReader.getBlockState(blockPos.up()).getBlock())) {
            return true;
        }
        if (liquidsConfig.needsBlockBelow && !liquidsConfig.acceptedBlocks.contains(iSeedReader.getBlockState(blockPos.down()).getBlock())) {
            return true;
        }
        BlockState blockState = iSeedReader.getBlockState(blockPos);
        if (!blockState.isAir() && !liquidsConfig.acceptedBlocks.contains(blockState.getBlock())) {
            return true;
        }
        int n = 0;
        int n2 = 0;
        if (liquidsConfig.acceptedBlocks.contains(iSeedReader.getBlockState(blockPos.west()).getBlock())) {
            ++n2;
        }
        if (liquidsConfig.acceptedBlocks.contains(iSeedReader.getBlockState(blockPos.east()).getBlock())) {
            ++n2;
        }
        if (liquidsConfig.acceptedBlocks.contains(iSeedReader.getBlockState(blockPos.north()).getBlock())) {
            ++n2;
        }
        if (liquidsConfig.acceptedBlocks.contains(iSeedReader.getBlockState(blockPos.south()).getBlock())) {
            ++n2;
        }
        if (liquidsConfig.acceptedBlocks.contains(iSeedReader.getBlockState(blockPos.down()).getBlock())) {
            ++n2;
        }
        int n3 = 0;
        if (iSeedReader.isAirBlock(blockPos.west())) {
            ++n3;
        }
        if (iSeedReader.isAirBlock(blockPos.east())) {
            ++n3;
        }
        if (iSeedReader.isAirBlock(blockPos.north())) {
            ++n3;
        }
        if (iSeedReader.isAirBlock(blockPos.south())) {
            ++n3;
        }
        if (iSeedReader.isAirBlock(blockPos.down())) {
            ++n3;
        }
        if (n2 == liquidsConfig.rockAmount && n3 == liquidsConfig.holeAmount) {
            iSeedReader.setBlockState(blockPos, liquidsConfig.state.getBlockState(), 2);
            iSeedReader.getPendingFluidTicks().scheduleTick(blockPos, liquidsConfig.state.getFluid(), 0);
            ++n;
        }
        return n > 0;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (LiquidsConfig)iFeatureConfig);
    }
}

