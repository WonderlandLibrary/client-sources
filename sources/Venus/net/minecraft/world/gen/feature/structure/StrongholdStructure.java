/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StrongholdPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class StrongholdStructure
extends Structure<NoFeatureConfig> {
    public StrongholdStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, NoFeatureConfig noFeatureConfig) {
        return chunkGenerator.func_235952_a_(new ChunkPos(n, n2));
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, IFeatureConfig iFeatureConfig) {
        return this.func_230363_a_(chunkGenerator, biomeProvider, l, sharedSeedRandom, n, n2, biome, chunkPos, (NoFeatureConfig)iFeatureConfig);
    }

    public static class Start
    extends StructureStart<NoFeatureConfig> {
        private final long field_236364_e_;

        public Start(Structure<NoFeatureConfig> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(structure, n, n2, mutableBoundingBox, n3, l);
            this.field_236364_e_ = l;
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, NoFeatureConfig noFeatureConfig) {
            StrongholdPieces.Stairs2 stairs2;
            int n3 = 0;
            do {
                this.components.clear();
                this.bounds = MutableBoundingBox.getNewBoundingBox();
                this.rand.setLargeFeatureSeed(this.field_236364_e_ + (long)n3++, n, n2);
                StrongholdPieces.prepareStructurePieces();
                stairs2 = new StrongholdPieces.Stairs2(this.rand, (n << 4) + 2, (n2 << 4) + 2);
                this.components.add(stairs2);
                stairs2.buildComponent(stairs2, this.components, this.rand);
                List<StructurePiece> list = stairs2.pendingChildren;
                while (!list.isEmpty()) {
                    int n4 = this.rand.nextInt(list.size());
                    StructurePiece structurePiece = list.remove(n4);
                    structurePiece.buildComponent(stairs2, this.components, this.rand);
                }
                this.recalculateStructureSize();
                this.func_214628_a(chunkGenerator.func_230356_f_(), this.rand, 10);
            } while (this.components.isEmpty() || stairs2.strongholdPortalRoom == null);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (NoFeatureConfig)iFeatureConfig);
        }
    }
}

