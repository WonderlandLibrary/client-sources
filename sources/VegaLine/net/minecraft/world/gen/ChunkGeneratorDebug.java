/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkGeneratorDebug
implements IChunkGenerator {
    private static final List<IBlockState> ALL_VALID_STATES = Lists.newArrayList();
    private static final int GRID_WIDTH;
    private static final int GRID_HEIGHT;
    protected static final IBlockState AIR;
    protected static final IBlockState BARRIER;
    private final World world;

    public ChunkGeneratorDebug(World worldIn) {
        this.world = worldIn;
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        ChunkPrimer chunkprimer = new ChunkPrimer();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int k = x * 16 + i;
                int l = z * 16 + j;
                chunkprimer.setBlockState(i, 60, j, BARRIER);
                IBlockState iblockstate = ChunkGeneratorDebug.getBlockStateFor(k, l);
                if (iblockstate == null) continue;
                chunkprimer.setBlockState(i, 70, j, iblockstate);
            }
        }
        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        chunk.generateSkylightMap();
        Biome[] abiome = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();
        for (int i1 = 0; i1 < abyte.length; ++i1) {
            abyte[i1] = (byte)Biome.getIdForBiome(abiome[i1]);
        }
        chunk.generateSkylightMap();
        return chunk;
    }

    public static IBlockState getBlockStateFor(int p_177461_0_, int p_177461_1_) {
        int i;
        IBlockState iblockstate = AIR;
        if (p_177461_0_ > 0 && p_177461_1_ > 0 && p_177461_0_ % 2 != 0 && p_177461_1_ % 2 != 0 && (p_177461_0_ /= 2) <= GRID_WIDTH && (p_177461_1_ /= 2) <= GRID_HEIGHT && (i = MathHelper.abs(p_177461_0_ * GRID_WIDTH + p_177461_1_)) < ALL_VALID_STATES.size()) {
            iblockstate = ALL_VALID_STATES.get(i);
        }
        return iblockstate;
    }

    @Override
    public void populate(int x, int z) {
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Override
    @Nullable
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
        return null;
    }

    @Override
    public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
        return false;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
    }

    static {
        AIR = Blocks.AIR.getDefaultState();
        BARRIER = Blocks.BARRIER.getDefaultState();
        for (Block block : Block.REGISTRY) {
            ALL_VALID_STATES.addAll(block.getBlockState().getValidStates());
        }
        GRID_WIDTH = MathHelper.ceil(MathHelper.sqrt(ALL_VALID_STATES.size()));
        GRID_HEIGHT = MathHelper.ceil((float)ALL_VALID_STATES.size() / (float)GRID_WIDTH);
    }
}

