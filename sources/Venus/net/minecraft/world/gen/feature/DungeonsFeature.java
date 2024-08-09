/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DungeonsFeature
extends Feature<NoFeatureConfig> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final EntityType<?>[] SPAWNERTYPES = new EntityType[]{EntityType.SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.SPIDER};
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();

    public DungeonsFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        Object object;
        BlockPos blockPos2;
        int n;
        int n2;
        int n3;
        int n4 = 3;
        int n5 = random2.nextInt(2) + 2;
        int n6 = -n5 - 1;
        int n7 = n5 + 1;
        int n8 = -1;
        int n9 = 4;
        int n10 = random2.nextInt(2) + 2;
        int n11 = -n10 - 1;
        int n12 = n10 + 1;
        int n13 = 0;
        for (n3 = n6; n3 <= n7; ++n3) {
            for (n2 = -1; n2 <= 4; ++n2) {
                for (n = n11; n <= n12; ++n) {
                    blockPos2 = blockPos.add(n3, n2, n);
                    object = iSeedReader.getBlockState(blockPos2).getMaterial();
                    boolean bl = ((Material)object).isSolid();
                    if (n2 == -1 && !bl) {
                        return true;
                    }
                    if (n2 == 4 && !bl) {
                        return true;
                    }
                    if (n3 != n6 && n3 != n7 && n != n11 && n != n12 || n2 != 0 || !iSeedReader.isAirBlock(blockPos2) || !iSeedReader.isAirBlock(blockPos2.up())) continue;
                    ++n13;
                }
            }
        }
        if (n13 >= 1 && n13 <= 5) {
            for (n3 = n6; n3 <= n7; ++n3) {
                for (n2 = 3; n2 >= -1; --n2) {
                    for (n = n11; n <= n12; ++n) {
                        blockPos2 = blockPos.add(n3, n2, n);
                        object = iSeedReader.getBlockState(blockPos2);
                        if (n3 != n6 && n2 != -1 && n != n11 && n3 != n7 && n2 != 4 && n != n12) {
                            if (((AbstractBlock.AbstractBlockState)object).isIn(Blocks.CHEST) || ((AbstractBlock.AbstractBlockState)object).isIn(Blocks.SPAWNER)) continue;
                            iSeedReader.setBlockState(blockPos2, CAVE_AIR, 2);
                            continue;
                        }
                        if (blockPos2.getY() >= 0 && !iSeedReader.getBlockState(blockPos2.down()).getMaterial().isSolid()) {
                            iSeedReader.setBlockState(blockPos2, CAVE_AIR, 2);
                            continue;
                        }
                        if (!((AbstractBlock.AbstractBlockState)object).getMaterial().isSolid() || ((AbstractBlock.AbstractBlockState)object).isIn(Blocks.CHEST)) continue;
                        if (n2 == -1 && random2.nextInt(4) != 0) {
                            iSeedReader.setBlockState(blockPos2, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
                            continue;
                        }
                        iSeedReader.setBlockState(blockPos2, Blocks.COBBLESTONE.getDefaultState(), 2);
                    }
                }
            }
            block6: for (n3 = 0; n3 < 2; ++n3) {
                for (n2 = 0; n2 < 3; ++n2) {
                    int n14;
                    int n15;
                    n = blockPos.getX() + random2.nextInt(n5 * 2 + 1) - n5;
                    BlockPos blockPos3 = new BlockPos(n, n15 = blockPos.getY(), n14 = blockPos.getZ() + random2.nextInt(n10 * 2 + 1) - n10);
                    if (!iSeedReader.isAirBlock(blockPos3)) continue;
                    int n16 = 0;
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if (!iSeedReader.getBlockState(blockPos3.offset(direction)).getMaterial().isSolid()) continue;
                        ++n16;
                    }
                    if (n16 != true) continue;
                    iSeedReader.setBlockState(blockPos3, StructurePiece.correctFacing(iSeedReader, blockPos3, Blocks.CHEST.getDefaultState()), 2);
                    LockableLootTileEntity.setLootTable(iSeedReader, random2, blockPos3, LootTables.CHESTS_SIMPLE_DUNGEON);
                    continue block6;
                }
            }
            iSeedReader.setBlockState(blockPos, Blocks.SPAWNER.getDefaultState(), 2);
            TileEntity tileEntity = iSeedReader.getTileEntity(blockPos);
            if (tileEntity instanceof MobSpawnerTileEntity) {
                ((MobSpawnerTileEntity)tileEntity).getSpawnerBaseLogic().setEntityType(this.getRandomDungeonMob(random2));
            } else {
                LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", (Object)blockPos.getX(), (Object)blockPos.getY(), (Object)blockPos.getZ());
            }
            return false;
        }
        return true;
    }

    private EntityType<?> getRandomDungeonMob(Random random2) {
        return Util.getRandomObject(SPAWNERTYPES, random2);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

