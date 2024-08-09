/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class JigsawStructure
extends Structure<VillageConfig> {
    private final int field_242774_u;
    private final boolean field_242775_v;
    private final boolean field_242776_w;

    public JigsawStructure(Codec<VillageConfig> codec, int n, boolean bl, boolean bl2) {
        super(codec);
        this.field_242774_u = n;
        this.field_242775_v = bl;
        this.field_242776_w = bl2;
    }

    @Override
    public Structure.IStartFactory<VillageConfig> getStartFactory() {
        return this::lambda$getStartFactory$0;
    }

    private StructureStart lambda$getStartFactory$0(Structure structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
        return new Start(this, n, n2, mutableBoundingBox, n3, l);
    }

    public static class Start
    extends MarginedStructureStart<VillageConfig> {
        private final JigsawStructure field_242781_e;

        public Start(JigsawStructure jigsawStructure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(jigsawStructure, n, n2, mutableBoundingBox, n3, l);
            this.field_242781_e = jigsawStructure;
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, VillageConfig villageConfig) {
            BlockPos blockPos = new BlockPos(n * 16, this.field_242781_e.field_242774_u, n2 * 16);
            JigsawPatternRegistry.func_244093_a();
            JigsawManager.func_242837_a(dynamicRegistries, villageConfig, AbstractVillagePiece::new, chunkGenerator, templateManager, blockPos, this.components, this.rand, this.field_242781_e.field_242775_v, this.field_242781_e.field_242776_w);
            this.recalculateStructureSize();
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (VillageConfig)iFeatureConfig);
        }
    }
}

