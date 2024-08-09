/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.OceanMonumentPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanMonumentStructure
extends Structure<NoFeatureConfig> {
    private static final List<MobSpawnInfo.Spawners> MONUMENT_ENEMIES = ImmutableList.of(new MobSpawnInfo.Spawners(EntityType.GUARDIAN, 1, 2, 4));

    public OceanMonumentStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_230365_b_() {
        return true;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, NoFeatureConfig noFeatureConfig) {
        for (Biome biome2 : biomeProvider.getBiomes(n * 16 + 9, chunkGenerator.func_230356_f_(), n2 * 16 + 9, 16)) {
            if (biome2.getGenerationSettings().hasStructure(this)) continue;
            return true;
        }
        for (Biome biome2 : biomeProvider.getBiomes(n * 16 + 9, chunkGenerator.func_230356_f_(), n2 * 16 + 9, 29)) {
            if (biome2.getCategory() == Biome.Category.OCEAN || biome2.getCategory() == Biome.Category.RIVER) continue;
            return true;
        }
        return false;
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    public List<MobSpawnInfo.Spawners> getSpawnList() {
        return MONUMENT_ENEMIES;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, IFeatureConfig iFeatureConfig) {
        return this.func_230363_a_(chunkGenerator, biomeProvider, l, sharedSeedRandom, n, n2, biome, chunkPos, (NoFeatureConfig)iFeatureConfig);
    }

    public static class Start
    extends StructureStart<NoFeatureConfig> {
        private boolean wasCreated;

        public Start(Structure<NoFeatureConfig> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(structure, n, n2, mutableBoundingBox, n3, l);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, NoFeatureConfig noFeatureConfig) {
            this.generateStart(n, n2);
        }

        private void generateStart(int n, int n2) {
            int n3 = n * 16 - 29;
            int n4 = n2 * 16 - 29;
            Direction direction = Direction.Plane.HORIZONTAL.random(this.rand);
            this.components.add(new OceanMonumentPieces.MonumentBuilding(this.rand, n3, n4, direction));
            this.recalculateStructureSize();
            this.wasCreated = true;
        }

        @Override
        public void func_230366_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos) {
            if (!this.wasCreated) {
                this.components.clear();
                this.generateStart(this.getChunkPosX(), this.getChunkPosZ());
            }
            super.func_230366_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, chunkPos);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (NoFeatureConfig)iFeatureConfig);
        }
    }
}

