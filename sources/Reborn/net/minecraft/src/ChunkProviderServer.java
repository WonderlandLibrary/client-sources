package net.minecraft.src;

import java.util.*;
import java.io.*;

public class ChunkProviderServer implements IChunkProvider
{
    private Set chunksToUnload;
    private Chunk defaultEmptyChunk;
    private IChunkProvider currentChunkProvider;
    private IChunkLoader currentChunkLoader;
    public boolean loadChunkOnProvideRequest;
    private LongHashMap loadedChunkHashMap;
    private List loadedChunks;
    private WorldServer worldObj;
    
    public ChunkProviderServer(final WorldServer par1WorldServer, final IChunkLoader par2IChunkLoader, final IChunkProvider par3IChunkProvider) {
        this.chunksToUnload = new HashSet();
        this.loadChunkOnProvideRequest = true;
        this.loadedChunkHashMap = new LongHashMap();
        this.loadedChunks = new ArrayList();
        this.defaultEmptyChunk = new EmptyChunk(par1WorldServer, 0, 0);
        this.worldObj = par1WorldServer;
        this.currentChunkLoader = par2IChunkLoader;
        this.currentChunkProvider = par3IChunkProvider;
    }
    
    @Override
    public boolean chunkExists(final int par1, final int par2) {
        return this.loadedChunkHashMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
    }
    
    public void unloadChunksIfNotNearSpawn(final int par1, final int par2) {
        if (this.worldObj.provider.canRespawnHere()) {
            final ChunkCoordinates var3 = this.worldObj.getSpawnPoint();
            final int var4 = par1 * 16 + 8 - var3.posX;
            final int var5 = par2 * 16 + 8 - var3.posZ;
            final short var6 = 128;
            if (var4 < -var6 || var4 > var6 || var5 < -var6 || var5 > var6) {
                this.chunksToUnload.add(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
            }
        }
        else {
            this.chunksToUnload.add(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
        }
    }
    
    public void unloadAllChunks() {
        for (final Chunk var2 : this.loadedChunks) {
            this.unloadChunksIfNotNearSpawn(var2.xPosition, var2.zPosition);
        }
    }
    
    @Override
    public Chunk loadChunk(final int par1, final int par2) {
        final long var3 = ChunkCoordIntPair.chunkXZ2Int(par1, par2);
        this.chunksToUnload.remove(var3);
        Chunk var4 = (Chunk)this.loadedChunkHashMap.getValueByKey(var3);
        if (var4 == null) {
            var4 = this.safeLoadChunk(par1, par2);
            if (var4 == null) {
                if (this.currentChunkProvider == null) {
                    var4 = this.defaultEmptyChunk;
                }
                else {
                    try {
                        var4 = this.currentChunkProvider.provideChunk(par1, par2);
                    }
                    catch (Throwable var6) {
                        final CrashReport var5 = CrashReport.makeCrashReport(var6, "Exception generating new chunk");
                        final CrashReportCategory var7 = var5.makeCategory("Chunk to be generated");
                        var7.addCrashSection("Location", String.format("%d,%d", par1, par2));
                        var7.addCrashSection("Position hash", var3);
                        var7.addCrashSection("Generator", this.currentChunkProvider.makeString());
                        throw new ReportedException(var5);
                    }
                }
            }
            this.loadedChunkHashMap.add(var3, var4);
            this.loadedChunks.add(var4);
            if (var4 != null) {
                var4.onChunkLoad();
            }
            var4.populateChunk(this, this, par1, par2);
        }
        return var4;
    }
    
    @Override
    public Chunk provideChunk(final int par1, final int par2) {
        final Chunk var3 = (Chunk)this.loadedChunkHashMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
        return (var3 == null) ? ((!this.worldObj.findingSpawnPoint && !this.loadChunkOnProvideRequest) ? this.defaultEmptyChunk : this.loadChunk(par1, par2)) : var3;
    }
    
    private Chunk safeLoadChunk(final int par1, final int par2) {
        if (this.currentChunkLoader == null) {
            return null;
        }
        try {
            final Chunk var3 = this.currentChunkLoader.loadChunk(this.worldObj, par1, par2);
            if (var3 != null) {
                var3.lastSaveTime = this.worldObj.getTotalWorldTime();
                if (this.currentChunkProvider != null) {
                    this.currentChunkProvider.recreateStructures(par1, par2);
                }
            }
            return var3;
        }
        catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }
    
    private void safeSaveExtraChunkData(final Chunk par1Chunk) {
        if (this.currentChunkLoader != null) {
            try {
                this.currentChunkLoader.saveExtraChunkData(this.worldObj, par1Chunk);
            }
            catch (Exception var3) {
                var3.printStackTrace();
            }
        }
    }
    
    private void safeSaveChunk(final Chunk par1Chunk) {
        if (this.currentChunkLoader != null) {
            try {
                par1Chunk.lastSaveTime = this.worldObj.getTotalWorldTime();
                this.currentChunkLoader.saveChunk(this.worldObj, par1Chunk);
            }
            catch (IOException var3) {
                var3.printStackTrace();
            }
            catch (MinecraftException var4) {
                var4.printStackTrace();
            }
        }
    }
    
    @Override
    public void populate(final IChunkProvider par1IChunkProvider, final int par2, final int par3) {
        final Chunk var4 = this.provideChunk(par2, par3);
        if (!var4.isTerrainPopulated) {
            var4.isTerrainPopulated = true;
            if (this.currentChunkProvider != null) {
                this.currentChunkProvider.populate(par1IChunkProvider, par2, par3);
                var4.setChunkModified();
            }
        }
    }
    
    @Override
    public boolean saveChunks(final boolean par1, final IProgressUpdate par2IProgressUpdate) {
        int var3 = 0;
        for (int var4 = 0; var4 < this.loadedChunks.size(); ++var4) {
            final Chunk var5 = this.loadedChunks.get(var4);
            if (par1) {
                this.safeSaveExtraChunkData(var5);
            }
            if (var5.needsSaving(par1)) {
                this.safeSaveChunk(var5);
                var5.isModified = false;
                if (++var3 == 24 && !par1) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public void func_104112_b() {
        if (this.currentChunkLoader != null) {
            this.currentChunkLoader.saveExtraData();
        }
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        if (!this.worldObj.canNotSave) {
            for (int var1 = 0; var1 < 100; ++var1) {
                if (!this.chunksToUnload.isEmpty()) {
                    final Long var2 = this.chunksToUnload.iterator().next();
                    final Chunk var3 = (Chunk)this.loadedChunkHashMap.getValueByKey(var2);
                    var3.onChunkUnload();
                    this.safeSaveChunk(var3);
                    this.safeSaveExtraChunkData(var3);
                    this.chunksToUnload.remove(var2);
                    this.loadedChunkHashMap.remove(var2);
                    this.loadedChunks.remove(var3);
                }
            }
            if (this.currentChunkLoader != null) {
                this.currentChunkLoader.chunkTick();
            }
        }
        return this.currentChunkProvider.unloadQueuedChunks();
    }
    
    @Override
    public boolean canSave() {
        return !this.worldObj.canNotSave;
    }
    
    @Override
    public String makeString() {
        return "ServerChunkCache: " + this.loadedChunkHashMap.getNumHashElements() + " Drop: " + this.chunksToUnload.size();
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType par1EnumCreatureType, final int par2, final int par3, final int par4) {
        return this.currentChunkProvider.getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
    }
    
    @Override
    public ChunkPosition findClosestStructure(final World par1World, final String par2Str, final int par3, final int par4, final int par5) {
        return this.currentChunkProvider.findClosestStructure(par1World, par2Str, par3, par4, par5);
    }
    
    @Override
    public int getLoadedChunkCount() {
        return this.loadedChunkHashMap.getNumHashElements();
    }
    
    @Override
    public void recreateStructures(final int par1, final int par2) {
    }
}
