/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.FortressPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class FortressStructure
extends Structure<NoFeatureConfig> {
    private static final List<MobSpawnInfo.Spawners> field_202381_d = ImmutableList.of(new MobSpawnInfo.Spawners(EntityType.BLAZE, 10, 2, 3), new MobSpawnInfo.Spawners(EntityType.ZOMBIFIED_PIGLIN, 5, 4, 4), new MobSpawnInfo.Spawners(EntityType.WITHER_SKELETON, 8, 5, 5), new MobSpawnInfo.Spawners(EntityType.SKELETON, 2, 5, 5), new MobSpawnInfo.Spawners(EntityType.MAGMA_CUBE, 3, 4, 4));

    public FortressStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, NoFeatureConfig noFeatureConfig) {
        return sharedSeedRandom.nextInt(5) < 2;
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    public List<MobSpawnInfo.Spawners> getSpawnList() {
        return field_202381_d;
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
            FortressPieces.Start start = new FortressPieces.Start(this.rand, (n << 4) + 2, (n2 << 4) + 2);
            this.components.add(start);
            start.buildComponent(start, this.components, this.rand);
            List<StructurePiece> list = start.pendingChildren;
            while (!list.isEmpty()) {
                int n3 = this.rand.nextInt(list.size());
                StructurePiece structurePiece = list.remove(n3);
                structurePiece.buildComponent(start, this.components, this.rand);
            }
            this.recalculateStructureSize();
            this.func_214626_a(this.rand, 48, 70);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (NoFeatureConfig)iFeatureConfig);
        }
    }
}

