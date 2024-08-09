/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public abstract class FlowersFeature<U extends IFeatureConfig>
extends Feature<U> {
    public FlowersFeature(Codec<U> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, U u) {
        BlockState blockState = this.getFlowerToPlace(random2, blockPos, u);
        int n = 0;
        for (int i = 0; i < this.getFlowerCount(u); ++i) {
            BlockPos blockPos2 = this.getNearbyPos(random2, blockPos, u);
            if (!iSeedReader.isAirBlock(blockPos2) || blockPos2.getY() >= 255 || !blockState.isValidPosition(iSeedReader, blockPos2) || !this.isValidPosition(iSeedReader, blockPos2, u)) continue;
            iSeedReader.setBlockState(blockPos2, blockState, 2);
            ++n;
        }
        return n > 0;
    }

    public abstract boolean isValidPosition(IWorld var1, BlockPos var2, U var3);

    public abstract int getFlowerCount(U var1);

    public abstract BlockPos getNearbyPos(Random var1, BlockPos var2, U var3);

    public abstract BlockState getFlowerToPlace(Random var1, BlockPos var2, U var3);
}

