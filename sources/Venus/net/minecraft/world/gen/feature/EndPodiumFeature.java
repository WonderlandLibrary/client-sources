/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class EndPodiumFeature
extends Feature<NoFeatureConfig> {
    public static final BlockPos END_PODIUM_LOCATION = BlockPos.ZERO;
    private final boolean activePortal;

    public EndPodiumFeature(boolean bl) {
        super(NoFeatureConfig.field_236558_a_);
        this.activePortal = bl;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        for (BlockPos object : BlockPos.getAllInBoxMutable(new BlockPos(blockPos.getX() - 4, blockPos.getY() - 1, blockPos.getZ() - 4), new BlockPos(blockPos.getX() + 4, blockPos.getY() + 32, blockPos.getZ() + 4))) {
            boolean direction = object.withinDistance(blockPos, 2.5);
            if (!direction && !object.withinDistance(blockPos, 3.5)) continue;
            if (object.getY() < blockPos.getY()) {
                if (direction) {
                    this.setBlockState(iSeedReader, object, Blocks.BEDROCK.getDefaultState());
                    continue;
                }
                if (object.getY() >= blockPos.getY()) continue;
                this.setBlockState(iSeedReader, object, Blocks.END_STONE.getDefaultState());
                continue;
            }
            if (object.getY() > blockPos.getY()) {
                this.setBlockState(iSeedReader, object, Blocks.AIR.getDefaultState());
                continue;
            }
            if (!direction) {
                this.setBlockState(iSeedReader, object, Blocks.BEDROCK.getDefaultState());
                continue;
            }
            if (this.activePortal) {
                this.setBlockState(iSeedReader, new BlockPos(object), Blocks.END_PORTAL.getDefaultState());
                continue;
            }
            this.setBlockState(iSeedReader, new BlockPos(object), Blocks.AIR.getDefaultState());
        }
        for (int i = 0; i < 4; ++i) {
            this.setBlockState(iSeedReader, blockPos.up(i), Blocks.BEDROCK.getDefaultState());
        }
        BlockPos blockPos2 = blockPos.up(2);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            this.setBlockState(iSeedReader, blockPos2.offset(direction), (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, direction));
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

