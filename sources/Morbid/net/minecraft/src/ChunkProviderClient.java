package net.minecraft.src;

import java.util.*;

public class ChunkProviderClient implements IChunkProvider
{
    private Chunk blankChunk;
    private LongHashMap chunkMapping;
    private List chunkListing;
    private World worldObj;
    
    public ChunkProviderClient(final World par1World) {
        this.chunkMapping = new LongHashMap();
        this.chunkListing = new ArrayList();
        this.blankChunk = new EmptyChunk(par1World, 0, 0);
        this.worldObj = par1World;
    }
    
    @Override
    public boolean chunkExists(final int par1, final int par2) {
        return true;
    }
    
    public void unloadChunk(final int par1, final int par2) {
        final Chunk var3 = this.provideChunk(par1, par2);
        if (!var3.isEmpty()) {
            var3.onChunkUnload();
        }
        this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
        this.chunkListing.remove(var3);
    }
    
    @Override
    public Chunk loadChunk(final int par1, final int par2) {
        final Chunk var3 = new Chunk(this.worldObj, par1, par2);
        this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(par1, par2), var3);
        var3.isChunkLoaded = true;
        return var3;
    }
    
    @Override
    public Chunk provideChunk(final int par1, final int par2) {
        final Chunk var3 = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
        return (var3 == null) ? this.blankChunk : var3;
    }
    
    @Override
    public boolean saveChunks(final boolean par1, final IProgressUpdate par2IProgressUpdate) {
        return true;
    }
    
    @Override
    public void func_104112_b() {
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }
    
    @Override
    public boolean canSave() {
        return false;
    }
    
    @Override
    public void populate(final IChunkProvider par1IChunkProvider, final int par2, final int par3) {
    }
    
    @Override
    public String makeString() {
        return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements();
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType par1EnumCreatureType, final int par2, final int par3, final int par4) {
        return null;
    }
    
    @Override
    public ChunkPosition findClosestStructure(final World par1World, final String par2Str, final int par3, final int par4, final int par5) {
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return this.chunkListing.size();
    }
    
    @Override
    public void recreateStructures(final int par1, final int par2) {
    }
}
