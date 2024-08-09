/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;

public class DebugChunkGenerator
extends ChunkGenerator {
    public static final Codec<DebugChunkGenerator> field_236066_e_ = RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).xmap(DebugChunkGenerator::new, DebugChunkGenerator::func_242727_g).stable().codec();
    private static final List<BlockState> ALL_VALID_STATES = StreamSupport.stream(Registry.BLOCK.spliterator(), false).flatMap(DebugChunkGenerator::lambda$static$0).collect(Collectors.toList());
    private static final int GRID_WIDTH = MathHelper.ceil(MathHelper.sqrt(ALL_VALID_STATES.size()));
    private static final int GRID_HEIGHT = MathHelper.ceil((float)ALL_VALID_STATES.size() / (float)GRID_WIDTH);
    protected static final BlockState AIR = Blocks.AIR.getDefaultState();
    protected static final BlockState BARRIER = Blocks.BARRIER.getDefaultState();
    private final Registry<Biome> field_242726_j;

    public DebugChunkGenerator(Registry<Biome> registry) {
        super(new SingleBiomeProvider(registry.getOrThrow(Biomes.PLAINS)), new DimensionStructuresSettings(false));
        this.field_242726_j = registry;
    }

    public Registry<Biome> func_242727_g() {
        return this.field_242726_j;
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return field_236066_e_;
    }

    @Override
    public ChunkGenerator func_230349_a_(long l) {
        return this;
    }

    @Override
    public void generateSurface(WorldGenRegion worldGenRegion, IChunk iChunk) {
    }

    @Override
    public void func_230350_a_(long l, BiomeManager biomeManager, IChunk iChunk, GenerationStage.Carving carving) {
    }

    @Override
    public void func_230351_a_(WorldGenRegion worldGenRegion, StructureManager structureManager) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int n = worldGenRegion.getMainChunkX();
        int n2 = worldGenRegion.getMainChunkZ();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int n3 = (n << 4) + i;
                int n4 = (n2 << 4) + j;
                worldGenRegion.setBlockState(mutable.setPos(n3, 60, n4), BARRIER, 1);
                BlockState blockState = DebugChunkGenerator.getBlockStateFor(n3, n4);
                if (blockState == null) continue;
                worldGenRegion.setBlockState(mutable.setPos(n3, 70, n4), blockState, 1);
            }
        }
    }

    @Override
    public void func_230352_b_(IWorld iWorld, StructureManager structureManager, IChunk iChunk) {
    }

    @Override
    public int getHeight(int n, int n2, Heightmap.Type type) {
        return 1;
    }

    @Override
    public IBlockReader func_230348_a_(int n, int n2) {
        return new Blockreader(new BlockState[0]);
    }

    public static BlockState getBlockStateFor(int n, int n2) {
        int n3;
        BlockState blockState = AIR;
        if (n > 0 && n2 > 0 && n % 2 != 0 && n2 % 2 != 0 && (n /= 2) <= GRID_WIDTH && (n2 /= 2) <= GRID_HEIGHT && (n3 = MathHelper.abs(n * GRID_WIDTH + n2)) < ALL_VALID_STATES.size()) {
            blockState = ALL_VALID_STATES.get(n3);
        }
        return blockState;
    }

    private static Stream lambda$static$0(Block block) {
        return block.getStateContainer().getValidStates().stream();
    }
}

