/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ShipwreckStructure
extends Structure<ShipwreckConfig> {
    public ShipwreckStructure(Codec<ShipwreckConfig> codec) {
        super(codec);
    }

    @Override
    public Structure.IStartFactory<ShipwreckConfig> getStartFactory() {
        return Start::new;
    }

    public static class Start
    extends StructureStart<ShipwreckConfig> {
        public Start(Structure<ShipwreckConfig> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(structure, n, n2, mutableBoundingBox, n3, l);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, ShipwreckConfig shipwreckConfig) {
            Rotation rotation = Rotation.randomRotation(this.rand);
            BlockPos blockPos = new BlockPos(n * 16, 90, n2 * 16);
            ShipwreckPieces.func_204760_a(templateManager, blockPos, rotation, this.components, this.rand, shipwreckConfig);
            this.recalculateStructureSize();
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (ShipwreckConfig)iFeatureConfig);
        }
    }
}

