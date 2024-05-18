package net.minecraft.world.gen;

import net.minecraft.world.chunk.storage.*;
import java.io.*;
import net.minecraft.world.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.*;
import net.minecraft.world.biome.*;
import com.google.common.collect.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public class ChunkProviderServer implements IChunkProvider
{
    private LongHashMap id2ChunkMap;
    private List<Chunk> loadedChunks;
    private Chunk dummyChunk;
    private static final String[] I;
    private static final Logger logger;
    private WorldServer worldObj;
    public boolean chunkLoadOverride;
    private IChunkProvider serverChunkGenerator;
    private Set<Long> droppedChunksSet;
    private IChunkLoader chunkLoader;
    
    private void saveChunkData(final Chunk chunk) {
        if (this.chunkLoader != null) {
            try {
                chunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
                this.chunkLoader.saveChunk(this.worldObj, chunk);
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            catch (IOException ex) {
                ChunkProviderServer.logger.error(ChunkProviderServer.I[0x5F ^ 0x57], (Throwable)ex);
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            catch (MinecraftException ex2) {
                ChunkProviderServer.logger.error(ChunkProviderServer.I[0xCD ^ 0xC4], (Throwable)ex2);
            }
        }
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        if (!this.worldObj.disableLevelSaving) {
            int i = "".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (i < (0x20 ^ 0x44)) {
                if (!this.droppedChunksSet.isEmpty()) {
                    final Long n = this.droppedChunksSet.iterator().next();
                    final Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(n);
                    if (chunk != null) {
                        chunk.onChunkUnload();
                        this.saveChunkData(chunk);
                        this.saveChunkExtraData(chunk);
                        this.id2ChunkMap.remove(n);
                        this.loadedChunks.remove(chunk);
                    }
                    this.droppedChunksSet.remove(n);
                }
                ++i;
            }
            if (this.chunkLoader != null) {
                this.chunkLoader.chunkTick();
            }
        }
        return this.serverChunkGenerator.unloadQueuedChunks();
    }
    
    public void dropChunk(final int n, final int n2) {
        if (this.worldObj.provider.canRespawnHere()) {
            if (!this.worldObj.isSpawnChunk(n, n2)) {
                this.droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(n, n2));
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
        }
        else {
            this.droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(n, n2));
        }
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
        final Chunk provideChunk = this.provideChunk(n, n2);
        if (!provideChunk.isTerrainPopulated()) {
            provideChunk.func_150809_p();
            if (this.serverChunkGenerator != null) {
                this.serverChunkGenerator.populate(chunkProvider, n, n2);
                provideChunk.setChunkModified();
            }
        }
    }
    
    @Override
    public boolean canSave() {
        int n;
        if (this.worldObj.disableLevelSaving) {
            n = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public String makeString() {
        return ChunkProviderServer.I[0x9A ^ 0x90] + this.id2ChunkMap.getNumHashElements() + ChunkProviderServer.I[0xBF ^ 0xB4] + this.droppedChunksSet.size();
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public List<Chunk> func_152380_a() {
        return this.loadedChunks;
    }
    
    public void unloadAllChunks() {
        final Iterator<Chunk> iterator = this.loadedChunks.iterator();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Chunk chunk = iterator.next();
            this.dropChunk(chunk.xPosition, chunk.zPosition);
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public void saveExtraData() {
        if (this.chunkLoader != null) {
            this.chunkLoader.saveExtraData();
        }
    }
    
    private void saveChunkExtraData(final Chunk chunk) {
        if (this.chunkLoader != null) {
            try {
                this.chunkLoader.saveExtraChunkData(this.worldObj, chunk);
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            catch (Exception ex) {
                ChunkProviderServer.logger.error(ChunkProviderServer.I[0x6C ^ 0x6B], (Throwable)ex);
            }
        }
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> (0x5 ^ 0x1), blockPos.getZ() >> (0x5B ^ 0x5F));
    }
    
    @Override
    public int getLoadedChunkCount() {
        return this.id2ChunkMap.getNumHashElements();
    }
    
    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return this.serverChunkGenerator.getPossibleCreatures(enumCreatureType, blockPos);
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        int length = "".length();
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.loadedChunks);
        int i = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (i < arrayList.size()) {
            final Chunk chunk = arrayList.get(i);
            if (b) {
                this.saveChunkExtraData(chunk);
            }
            if (chunk.needsSaving(b)) {
                this.saveChunkData(chunk);
                chunk.setModified("".length() != 0);
                if (++length == (0xDE ^ 0xC6) && !b) {
                    return "".length() != 0;
                }
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        final Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(n, n2));
        Chunk chunk2;
        if (chunk == null) {
            if (!this.worldObj.isFindingSpawnPoint() && !this.chunkLoadOverride) {
                chunk2 = this.dummyChunk;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                chunk2 = this.loadChunk(n, n2);
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
        }
        else {
            chunk2 = chunk;
        }
        return chunk2;
    }
    
    private static void I() {
        (I = new String[0xBC ^ 0xB0])["".length()] = I(".\u0010\u000b) \u001f\u0001\u0007\"p\f\r\u0006)\"\n\u001c\u0001\"7K\u0006\r;p\b\u0000\u001d\";", "khhLP");
        ChunkProviderServer.I[" ".length()] = I("5\u0019::(V\u0005 t!\u0013Q(1-\u0013\u0003. &\u0012", "vqOTC");
        ChunkProviderServer.I["  ".length()] = I("\u0014>\u0007\u0017$1>\n", "XQdvP");
        ChunkProviderServer.I["   ".length()] = I("K&zp#", "nBVUG");
        ChunkProviderServer.I[0x1C ^ 0x18] = I(":;\u0004+,\u0003;\u0019b0\u000b'\u001f", "jTwBX");
        ChunkProviderServer.I[0x46 ^ 0x43] = I("\f0\u0003\u0006\u0006*!\u0002\u0011", "KUmct");
        ChunkProviderServer.I[0x7C ^ 0x7A] = I("\u000f\b7\u0004,\"@6H$#\u0006&H+$\u0012,\u0003", "LgBhH");
        ChunkProviderServer.I[0x92 ^ 0x95] = I("7\u0000054\u001aH1y#\u0015\u0019 y5\u001a\u001b,-9\u0011\u001c", "toEYP");
        ChunkProviderServer.I[0x76 ^ 0x7E] = I("\f\u0002:\u000b !J;G7.\u001b*G''\u0018!\f", "OmOgD");
        ChunkProviderServer.I[0x3C ^ 0x35] = I("\"\u001a\u0006\u001a\u0002\u000fR\u0007V\u0015\u0000\u0003\u0016V\u0005\t\u0000\u001d\u001d]A\u0014\u001f\u0004\u0003\u0000\u0011\nV\u000f\u000fU\u0006\u0005\u0003A\u0017\nV\u0007\u000f\u001a\u0007\u001e\u0003\u0013U\u001a\u0018\u0015\u0015\u0014\u001d\u0015\u0003A\u001a\u0015V+\b\u001b\u0016\u0015\u0014\u0000\u0013\u0007I", "ausvf");
        ChunkProviderServer.I[0x18 ^ 0x12] = I("<&\u0017%<\u001d\u0000\r&7\u0004\u0000\u000401\nyE", "oCeSY");
        ChunkProviderServer.I[0x24 ^ 0x2F] = I("c1\u0006\u0019\u001byU", "Cutvk");
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        if (this.serverChunkGenerator != null && this.serverChunkGenerator.func_177460_a(chunkProvider, chunk, n, n2)) {
            this.provideChunk(n, n2).setChunkModified();
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public ChunkProviderServer(final WorldServer worldObj, final IChunkLoader chunkLoader, final IChunkProvider serverChunkGenerator) {
        this.droppedChunksSet = Collections.newSetFromMap(new ConcurrentHashMap<Long, Boolean>());
        this.chunkLoadOverride = (" ".length() != 0);
        this.id2ChunkMap = new LongHashMap();
        this.loadedChunks = (List<Chunk>)Lists.newArrayList();
        this.dummyChunk = new EmptyChunk(worldObj, "".length(), "".length());
        this.worldObj = worldObj;
        this.chunkLoader = chunkLoader;
        this.serverChunkGenerator = serverChunkGenerator;
    }
    
    private Chunk loadChunkFromFile(final int n, final int n2) {
        if (this.chunkLoader == null) {
            return null;
        }
        try {
            final Chunk loadChunk = this.chunkLoader.loadChunk(this.worldObj, n, n2);
            if (loadChunk != null) {
                loadChunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
                if (this.serverChunkGenerator != null) {
                    this.serverChunkGenerator.recreateStructures(loadChunk, n, n2);
                }
            }
            return loadChunk;
        }
        catch (Exception ex) {
            ChunkProviderServer.logger.error(ChunkProviderServer.I[0x2A ^ 0x2C], (Throwable)ex);
            return null;
        }
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return this.serverChunkGenerator.getStrongholdGen(world, s, blockPos);
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return this.id2ChunkMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(n, n2));
    }
    
    public Chunk loadChunk(final int n, final int n2) {
        final long chunkXZ2Int = ChunkCoordIntPair.chunkXZ2Int(n, n2);
        this.droppedChunksSet.remove(chunkXZ2Int);
        Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(chunkXZ2Int);
        if (chunk == null) {
            chunk = this.loadChunkFromFile(n, n2);
            if (chunk == null) {
                if (this.serverChunkGenerator == null) {
                    chunk = this.dummyChunk;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    try {
                        chunk = this.serverChunkGenerator.provideChunk(n, n2);
                        "".length();
                        if (2 == 0) {
                            throw null;
                        }
                    }
                    catch (Throwable t) {
                        final CrashReport crashReport = CrashReport.makeCrashReport(t, ChunkProviderServer.I["".length()]);
                        final CrashReportCategory category;
                        final CrashReportCategory crashReportCategory = category = crashReport.makeCategory(ChunkProviderServer.I[" ".length()]);
                        final String s = ChunkProviderServer.I["  ".length()];
                        final String s2 = ChunkProviderServer.I["   ".length()];
                        final Object[] array = new Object["  ".length()];
                        array["".length()] = n;
                        array[" ".length()] = n2;
                        category.addCrashSection(s, String.format(s2, array));
                        crashReportCategory.addCrashSection(ChunkProviderServer.I[0x67 ^ 0x63], chunkXZ2Int);
                        crashReportCategory.addCrashSection(ChunkProviderServer.I[0x73 ^ 0x76], this.serverChunkGenerator.makeString());
                        throw new ReportedException(crashReport);
                    }
                }
            }
            this.id2ChunkMap.add(chunkXZ2Int, chunk);
            this.loadedChunks.add(chunk);
            chunk.onChunkLoad();
            chunk.populateChunk(this, this, n, n2);
        }
        return chunk;
    }
}
