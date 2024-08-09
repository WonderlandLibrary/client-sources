/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.SwampHutPiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class SwampHutStructure
extends Structure<NoFeatureConfig> {
    private static final List<MobSpawnInfo.Spawners> SPAWN_LIST = ImmutableList.of(new MobSpawnInfo.Spawners(EntityType.WITCH, 1, 1, 1));
    private static final List<MobSpawnInfo.Spawners> CREATURE_SPAWN_LIST = ImmutableList.of(new MobSpawnInfo.Spawners(EntityType.CAT, 1, 1, 1));

    public SwampHutStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    public List<MobSpawnInfo.Spawners> getSpawnList() {
        return SPAWN_LIST;
    }

    @Override
    public List<MobSpawnInfo.Spawners> getCreatureSpawnList() {
        return CREATURE_SPAWN_LIST;
    }

    public static class Start
    extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(structure, n, n2, mutableBoundingBox, n3, l);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, NoFeatureConfig noFeatureConfig) {
            SwampHutPiece swampHutPiece = new SwampHutPiece(this.rand, n * 16, n2 * 16);
            this.components.add(swampHutPiece);
            this.recalculateStructureSize();
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (NoFeatureConfig)iFeatureConfig);
        }
    }
}

