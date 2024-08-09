/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class FlatChunkGenerator
extends ChunkGenerator {
    public static final Codec<FlatChunkGenerator> field_236069_d_ = ((MapCodec)FlatGenerationSettings.field_236932_a_.fieldOf("settings")).xmap(FlatChunkGenerator::new, FlatChunkGenerator::func_236073_g_).codec();
    private final FlatGenerationSettings field_236070_e_;

    public FlatChunkGenerator(FlatGenerationSettings flatGenerationSettings) {
        super(new SingleBiomeProvider(flatGenerationSettings.func_236942_c_()), new SingleBiomeProvider(flatGenerationSettings.getBiome()), flatGenerationSettings.func_236943_d_(), 0L);
        this.field_236070_e_ = flatGenerationSettings;
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return field_236069_d_;
    }

    @Override
    public ChunkGenerator func_230349_a_(long l) {
        return this;
    }

    public FlatGenerationSettings func_236073_g_() {
        return this.field_236070_e_;
    }

    @Override
    public void generateSurface(WorldGenRegion worldGenRegion, IChunk iChunk) {
    }

    @Override
    public int getGroundHeight() {
        BlockState[] blockStateArray = this.field_236070_e_.getStates();
        for (int i = 0; i < blockStateArray.length; ++i) {
            BlockState blockState;
            BlockState blockState2 = blockState = blockStateArray[i] == null ? Blocks.AIR.getDefaultState() : blockStateArray[i];
            if (Heightmap.Type.MOTION_BLOCKING.getHeightLimitPredicate().test(blockState)) continue;
            return i - 1;
        }
        return blockStateArray.length;
    }

    @Override
    public void func_230352_b_(IWorld iWorld, StructureManager structureManager, IChunk iChunk) {
        BlockState[] blockStateArray = this.field_236070_e_.getStates();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Heightmap heightmap = iChunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap heightmap2 = iChunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
        for (int i = 0; i < blockStateArray.length; ++i) {
            BlockState blockState = blockStateArray[i];
            if (blockState == null) continue;
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    iChunk.setBlockState(mutable.setPos(j, i, k), blockState, false);
                    heightmap.update(j, i, k, blockState);
                    heightmap2.update(j, i, k, blockState);
                }
            }
        }
    }

    @Override
    public int getHeight(int n, int n2, Heightmap.Type type) {
        BlockState[] blockStateArray = this.field_236070_e_.getStates();
        for (int i = blockStateArray.length - 1; i >= 0; --i) {
            BlockState blockState = blockStateArray[i];
            if (blockState == null || !type.getHeightLimitPredicate().test(blockState)) continue;
            return i + 1;
        }
        return 1;
    }

    @Override
    public IBlockReader func_230348_a_(int n, int n2) {
        return new Blockreader((BlockState[])Arrays.stream(this.field_236070_e_.getStates()).map(FlatChunkGenerator::lambda$func_230348_a_$0).toArray(FlatChunkGenerator::lambda$func_230348_a_$1));
    }

    private static BlockState[] lambda$func_230348_a_$1(int n) {
        return new BlockState[n];
    }

    private static BlockState lambda$func_230348_a_$0(BlockState blockState) {
        return blockState == null ? Blocks.AIR.getDefaultState() : blockState;
    }
}

