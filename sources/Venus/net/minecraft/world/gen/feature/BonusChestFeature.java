/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BonusChestFeature
extends Feature<NoFeatureConfig> {
    public BonusChestFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        ChunkPos chunkPos = new ChunkPos(blockPos);
        List list = IntStream.rangeClosed(chunkPos.getXStart(), chunkPos.getXEnd()).boxed().collect(Collectors.toList());
        Collections.shuffle(list, random2);
        List list2 = IntStream.rangeClosed(chunkPos.getZStart(), chunkPos.getZEnd()).boxed().collect(Collectors.toList());
        Collections.shuffle(list2, random2);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Integer n : list) {
            for (Integer n2 : list2) {
                mutable.setPos(n, 0, n2);
                BlockPos blockPos2 = iSeedReader.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, mutable);
                if (!iSeedReader.isAirBlock(blockPos2) && !iSeedReader.getBlockState(blockPos2).getCollisionShape(iSeedReader, blockPos2).isEmpty()) continue;
                iSeedReader.setBlockState(blockPos2, Blocks.CHEST.getDefaultState(), 2);
                LockableLootTileEntity.setLootTable(iSeedReader, random2, blockPos2, LootTables.CHESTS_SPAWN_BONUS_CHEST);
                BlockState blockState = Blocks.TORCH.getDefaultState();
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockPos blockPos3 = blockPos2.offset(direction);
                    if (!blockState.isValidPosition(iSeedReader, blockPos3)) continue;
                    iSeedReader.setBlockState(blockPos3, blockState, 2);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

