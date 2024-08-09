/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlobReplacementConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class NetherackBlobReplacementStructure
extends Feature<BlobReplacementConfig> {
    public NetherackBlobReplacementStructure(Codec<BlobReplacementConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BlobReplacementConfig blobReplacementConfig) {
        Block block = blobReplacementConfig.field_242818_b.getBlock();
        BlockPos blockPos2 = NetherackBlobReplacementStructure.func_236329_a_(iSeedReader, blockPos.toMutable().clampAxisCoordinate(Direction.Axis.Y, 1, iSeedReader.getHeight() - 1), block);
        if (blockPos2 == null) {
            return true;
        }
        int n = blobReplacementConfig.func_242823_b().func_242259_a(random2);
        boolean bl = false;
        for (BlockPos blockPos3 : BlockPos.getProximitySortedBoxPositionsIterator(blockPos2, n, n, n)) {
            if (blockPos3.manhattanDistance(blockPos2) > n) break;
            BlockState blockState = iSeedReader.getBlockState(blockPos3);
            if (!blockState.isIn(block)) continue;
            this.setBlockState(iSeedReader, blockPos3, blobReplacementConfig.field_242819_c);
            bl = true;
        }
        return bl;
    }

    @Nullable
    private static BlockPos func_236329_a_(IWorld iWorld, BlockPos.Mutable mutable, Block block) {
        while (mutable.getY() > 1) {
            BlockState blockState = iWorld.getBlockState(mutable);
            if (blockState.isIn(block)) {
                return mutable;
            }
            mutable.move(Direction.DOWN);
        }
        return null;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BlobReplacementConfig)iFeatureConfig);
    }
}

