/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.EndCityPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class EndCityStructure
extends Structure<NoFeatureConfig> {
    public EndCityStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_230365_b_() {
        return true;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, NoFeatureConfig noFeatureConfig) {
        return EndCityStructure.getYPosForStructure(n, n2, chunkGenerator) >= 60;
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    private static int getYPosForStructure(int n, int n2, ChunkGenerator chunkGenerator) {
        Random random2 = new Random(n + n2 * 10387313);
        Rotation rotation = Rotation.randomRotation(random2);
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
        return Math.min(Math.min(n7, n8), Math.min(n9, n10));
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
            int n3 = EndCityStructure.getYPosForStructure(n, n2, chunkGenerator);
            if (n3 >= 60) {
                BlockPos blockPos = new BlockPos(n * 16 + 8, n3, n2 * 16 + 8);
                EndCityPieces.startHouseTower(templateManager, blockPos, rotation, this.components, this.rand);
                this.recalculateStructureSize();
            }
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (NoFeatureConfig)iFeatureConfig);
        }
    }
}

