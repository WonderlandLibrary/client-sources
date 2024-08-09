/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class RandomPatchFeature
extends Feature<BlockClusterFeatureConfig> {
    public RandomPatchFeature(Codec<BlockClusterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BlockClusterFeatureConfig blockClusterFeatureConfig) {
        BlockState blockState = blockClusterFeatureConfig.stateProvider.getBlockState(random2, blockPos);
        BlockPos blockPos2 = blockClusterFeatureConfig.field_227298_k_ ? iSeedReader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockPos) : blockPos;
        int n = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < blockClusterFeatureConfig.tryCount; ++i) {
            mutable.setAndOffset(blockPos2, random2.nextInt(blockClusterFeatureConfig.xSpread + 1) - random2.nextInt(blockClusterFeatureConfig.xSpread + 1), random2.nextInt(blockClusterFeatureConfig.ySpread + 1) - random2.nextInt(blockClusterFeatureConfig.ySpread + 1), random2.nextInt(blockClusterFeatureConfig.zSpread + 1) - random2.nextInt(blockClusterFeatureConfig.zSpread + 1));
            Vector3i vector3i = mutable.down();
            BlockState blockState2 = iSeedReader.getBlockState((BlockPos)vector3i);
            if (!iSeedReader.isAirBlock(mutable) && (!blockClusterFeatureConfig.isReplaceable || !iSeedReader.getBlockState(mutable).getMaterial().isReplaceable()) || !blockState.isValidPosition(iSeedReader, mutable) || !blockClusterFeatureConfig.whitelist.isEmpty() && !blockClusterFeatureConfig.whitelist.contains(blockState2.getBlock()) || blockClusterFeatureConfig.blacklist.contains(blockState2) || blockClusterFeatureConfig.requiresWater && !iSeedReader.getFluidState(((BlockPos)vector3i).west()).isTagged(FluidTags.WATER) && !iSeedReader.getFluidState(((BlockPos)vector3i).east()).isTagged(FluidTags.WATER) && !iSeedReader.getFluidState(((BlockPos)vector3i).north()).isTagged(FluidTags.WATER) && !iSeedReader.getFluidState(((BlockPos)vector3i).south()).isTagged(FluidTags.WATER)) continue;
            blockClusterFeatureConfig.blockPlacer.place(iSeedReader, mutable, blockState, random2);
            ++n;
        }
        return n > 0;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BlockClusterFeatureConfig)iFeatureConfig);
    }
}

