/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.LinkedList;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.WoodlandMansionPieces;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class WoodlandMansionStructure
extends Structure<NoFeatureConfig> {
    public WoodlandMansionStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_230365_b_() {
        return true;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, NoFeatureConfig noFeatureConfig) {
        for (Biome biome2 : biomeProvider.getBiomes(n * 16 + 9, chunkGenerator.func_230356_f_(), n2 * 16 + 9, 32)) {
            if (biome2.getGenerationSettings().hasStructure(this)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, IFeatureConfig iFeatureConfig) {
        return this.func_230363_a_(chunkGenerator, biomeProvider, l, sharedSeedRandom, n, n2, biome, chunkPos, (NoFeatureConfig)iFeatureConfig);
    }

    public static class Start
    extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(structure, n, n2, mutableBoundingBox, n3, l);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, NoFeatureConfig noFeatureConfig) {
            Rotation rotation = Rotation.randomRotation(this.rand);
            int n3 = 5;
            int n4 = 5;
            if (rotation == Rotation.CLOCKWISE_90) {
                n3 = -5;
            } else if (rotation == Rotation.CLOCKWISE_180) {
                n3 = -5;
                n4 = -5;
            } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
                n4 = -5;
            }
            int n5 = (n << 4) + 7;
            int n6 = (n2 << 4) + 7;
            int n7 = chunkGenerator.getNoiseHeightMinusOne(n5, n6, Heightmap.Type.WORLD_SURFACE_WG);
            int n8 = chunkGenerator.getNoiseHeightMinusOne(n5, n6 + n4, Heightmap.Type.WORLD_SURFACE_WG);
            int n9 = chunkGenerator.getNoiseHeightMinusOne(n5 + n3, n6, Heightmap.Type.WORLD_SURFACE_WG);
            int n10 = chunkGenerator.getNoiseHeightMinusOne(n5 + n3, n6 + n4, Heightmap.Type.WORLD_SURFACE_WG);
            int n11 = Math.min(Math.min(n7, n8), Math.min(n9, n10));
            if (n11 >= 60) {
                BlockPos blockPos = new BlockPos(n * 16 + 8, n11 + 1, n2 * 16 + 8);
                LinkedList<WoodlandMansionPieces.MansionTemplate> linkedList = Lists.newLinkedList();
                WoodlandMansionPieces.generateMansion(templateManager, blockPos, rotation, linkedList, this.rand);
                this.components.addAll(linkedList);
                this.recalculateStructureSize();
            }
        }

        @Override
        public void func_230366_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos) {
            super.func_230366_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, chunkPos);
            int n = this.bounds.minY;
            for (int i = mutableBoundingBox.minX; i <= mutableBoundingBox.maxX; ++i) {
                for (int j = mutableBoundingBox.minZ; j <= mutableBoundingBox.maxZ; ++j) {
                    Object object2;
                    BlockPos blockPos = new BlockPos(i, n, j);
                    if (iSeedReader.isAirBlock(blockPos) || !this.bounds.isVecInside(blockPos)) continue;
                    boolean bl = false;
                    for (Object object2 : this.components) {
                        if (!((StructurePiece)object2).getBoundingBox().isVecInside(blockPos)) continue;
                        bl = true;
                        break;
                    }
                    if (!bl) continue;
                    for (int k = n - 1; k > 1 && (iSeedReader.isAirBlock((BlockPos)(object2 = new BlockPos(i, k, j))) || iSeedReader.getBlockState((BlockPos)object2).getMaterial().isLiquid()); --k) {
                        iSeedReader.setBlockState((BlockPos)object2, Blocks.COBBLESTONE.getDefaultState(), 2);
                    }
                }
            }
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (NoFeatureConfig)iFeatureConfig);
        }
    }
}

