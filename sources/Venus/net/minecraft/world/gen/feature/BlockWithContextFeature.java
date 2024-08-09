/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlockWithContextFeature
extends Feature<BlockWithContextConfig> {
    public BlockWithContextFeature(Codec<BlockWithContextConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BlockWithContextConfig blockWithContextConfig) {
        if (blockWithContextConfig.placeOn.contains(iSeedReader.getBlockState(blockPos.down())) && blockWithContextConfig.placeIn.contains(iSeedReader.getBlockState(blockPos)) && blockWithContextConfig.placeUnder.contains(iSeedReader.getBlockState(blockPos.up()))) {
            iSeedReader.setBlockState(blockPos, blockWithContextConfig.toPlace, 2);
            return false;
        }
        return true;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BlockWithContextConfig)iFeatureConfig);
    }
}

