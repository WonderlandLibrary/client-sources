/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.NetherFossilStructures;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class NetherFossilStructure
extends Structure<NoFeatureConfig> {
    public NetherFossilStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    public static class Start
    extends MarginedStructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(structure, n, n2, mutableBoundingBox, n3, l);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, NoFeatureConfig noFeatureConfig) {
            int n3;
            ChunkPos chunkPos = new ChunkPos(n, n2);
            int n4 = chunkPos.getXStart() + this.rand.nextInt(16);
            int n5 = chunkPos.getZStart() + this.rand.nextInt(16);
            int n6 = chunkGenerator.func_230356_f_();
            IBlockReader iBlockReader = chunkGenerator.func_230348_a_(n4, n5);
            BlockPos.Mutable mutable = new BlockPos.Mutable(n4, n3, n5);
            for (n3 = n6 + this.rand.nextInt(chunkGenerator.func_230355_e_() - 2 - n6); n3 > n6; --n3) {
                BlockState blockState = iBlockReader.getBlockState(mutable);
                mutable.move(Direction.DOWN);
                BlockState blockState2 = iBlockReader.getBlockState(mutable);
                if (blockState.isAir() && (blockState2.isIn(Blocks.SOUL_SAND) || blockState2.isSolidSide(iBlockReader, mutable, Direction.UP))) break;
            }
            if (n3 > n6) {
                NetherFossilStructures.func_236994_a_(templateManager, this.components, this.rand, new BlockPos(n4, n3, n5));
                this.recalculateStructureSize();
            }
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (NoFeatureConfig)iFeatureConfig);
        }
    }
}

