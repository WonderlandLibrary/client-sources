package net.minecraft.client.multiplayer;

import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.*;
import net.minecraft.world.biome.*;
import java.util.*;

public class ChunkProviderClient implements IChunkProvider
{
    private Chunk blankChunk;
    private World worldObj;
    private static final String[] I;
    private List<Chunk> chunkListing;
    private LongHashMap chunkMapping;
    private static final Logger logger;
    
    @Override
    public boolean canSave() {
        return "".length() != 0;
    }
    
    @Override
    public void saveExtraData() {
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return " ".length() != 0;
    }
    
    public ChunkProviderClient(final World worldObj) {
        this.chunkMapping = new LongHashMap();
        this.chunkListing = (List<Chunk>)Lists.newArrayList();
        this.blankChunk = new EmptyChunk(worldObj, "".length(), "".length());
        this.worldObj = worldObj;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u001d\u0010\u001c6\n$\u0016Tx &\u0018\u000b6\u00179\u0018\n=C)\u0019\u001b6\bj\u0005\u0007;\b#\u001f\tx\u0017%\u001e\u0005x\u00187Q\u0003+", "JqnXc");
        ChunkProviderClient.I[" ".length()] = I("\u001b2.\u001f$&+#\u0012($\u0004*\u001e#=\u0004#\b%3}b", "VGBkM");
        ChunkProviderClient.I["  ".length()] = I("hS", "DsOIJ");
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> (0x52 ^ 0x56), blockPos.getZ() >> (0x4 ^ 0x0));
    }
    
    public Chunk loadChunk(final int n, final int n2) {
        final Chunk chunk = new Chunk(this.worldObj, n, n2);
        this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(n, n2), chunk);
        this.chunkListing.add(chunk);
        chunk.setChunkLoaded(" ".length() != 0);
        return chunk;
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return this.chunkListing.size();
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        return "".length() != 0;
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
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
            if (3 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        final Chunk chunk = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(n, n2));
        Chunk blankChunk;
        if (chunk == null) {
            blankChunk = this.blankChunk;
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            blankChunk = chunk;
        }
        return blankChunk;
    }
    
    public void unloadChunk(final int n, final int n2) {
        final Chunk provideChunk = this.provideChunk(n, n2);
        if (!provideChunk.isEmpty()) {
            provideChunk.onChunkUnload();
        }
        this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(n, n2));
        this.chunkListing.remove(provideChunk);
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public String makeString() {
        return ChunkProviderClient.I[" ".length()] + this.chunkMapping.getNumHashElements() + ChunkProviderClient.I["  ".length()] + this.chunkListing.size();
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        final long currentTimeMillis = System.currentTimeMillis();
        final Iterator<Chunk> iterator = this.chunkListing.iterator();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Chunk chunk = iterator.next();
            int n;
            if (System.currentTimeMillis() - currentTimeMillis > 5L) {
                n = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            chunk.func_150804_b(n != 0);
        }
        if (System.currentTimeMillis() - currentTimeMillis > 100L) {
            final Logger logger = ChunkProviderClient.logger;
            final String s = ChunkProviderClient.I["".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = System.currentTimeMillis() - currentTimeMillis;
            logger.info(s, array);
        }
        return "".length() != 0;
    }
}
