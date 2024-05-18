package net.minecraft.world.gen;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;

public class ChunkProviderDebug implements IChunkProvider
{
    private static final String[] I;
    private static final List<IBlockState> field_177464_a;
    private final World world;
    private static final int field_177462_b;
    private static final int field_181039_c;
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return " ".length() != 0;
    }
    
    static {
        I();
        field_177464_a = Lists.newArrayList();
        final Iterator<Block> iterator = Block.blockRegistry.iterator();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            ChunkProviderDebug.field_177464_a.addAll((Collection<? extends IBlockState>)iterator.next().getBlockState().getValidStates());
        }
        field_177462_b = MathHelper.ceiling_float_int(MathHelper.sqrt_float(ChunkProviderDebug.field_177464_a.size()));
        field_181039_c = MathHelper.ceiling_float_int(ChunkProviderDebug.field_177464_a.size() / ChunkProviderDebug.field_177462_b);
    }
    
    @Override
    public int getLoadedChunkCount() {
        return "".length();
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> (0x6E ^ 0x6A), blockPos.getZ() >> (0x5 ^ 0x1));
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        int i = "".length();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (i < (0x53 ^ 0x43)) {
            int j = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (j < (0x22 ^ 0x32)) {
                final int n3 = n * (0x1D ^ 0xD) + i;
                final int n4 = n2 * (0x3F ^ 0x2F) + j;
                chunkPrimer.setBlockState(i, 0x45 ^ 0x79, j, Blocks.barrier.getDefaultState());
                final IBlockState func_177461_b = func_177461_b(n3, n4);
                if (func_177461_b != null) {
                    chunkPrimer.setBlockState(i, 0xF2 ^ 0xB4, j, func_177461_b);
                }
                ++j;
            }
            ++i;
        }
        final Chunk chunk = new Chunk(this.world, chunkPrimer, n, n2);
        chunk.generateSkylightMap();
        final BiomeGenBase[] loadBlockGeneratorData = this.world.getWorldChunkManager().loadBlockGeneratorData(null, n * (0x26 ^ 0x36), n2 * (0x86 ^ 0x96), 0x4F ^ 0x5F, 0xA9 ^ 0xB9);
        final byte[] biomeArray = chunk.getBiomeArray();
        int k = "".length();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (k < biomeArray.length) {
            biomeArray[k] = (byte)loadBlockGeneratorData[k].biomeID;
            ++k;
        }
        chunk.generateSkylightMap();
        return chunk;
    }
    
    @Override
    public void saveExtraData() {
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0014\u0011+86\u001c\u0011?(=\u0003\u001b<?25", "PtIMQ");
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
    }
    
    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return this.world.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        return "".length() != 0;
    }
    
    public ChunkProviderDebug(final World world) {
        this.world = world;
    }
    
    @Override
    public String makeString() {
        return ChunkProviderDebug.I["".length()];
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean canSave() {
        return " ".length() != 0;
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return "".length() != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static IBlockState func_177461_b(int n, int n2) {
        IBlockState blockState = null;
        if (n > 0 && n2 > 0 && n % "  ".length() != 0 && n2 % "  ".length() != 0) {
            n /= "  ".length();
            n2 /= "  ".length();
            if (n <= ChunkProviderDebug.field_177462_b && n2 <= ChunkProviderDebug.field_181039_c) {
                final int abs_int = MathHelper.abs_int(n * ChunkProviderDebug.field_177462_b + n2);
                if (abs_int < ChunkProviderDebug.field_177464_a.size()) {
                    blockState = ChunkProviderDebug.field_177464_a.get(abs_int);
                }
            }
        }
        return blockState;
    }
}
