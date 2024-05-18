package net.minecraft.src;

import java.util.*;

public interface IChunkProvider
{
    boolean chunkExists(final int p0, final int p1);
    
    Chunk provideChunk(final int p0, final int p1);
    
    Chunk loadChunk(final int p0, final int p1);
    
    void populate(final IChunkProvider p0, final int p1, final int p2);
    
    boolean saveChunks(final boolean p0, final IProgressUpdate p1);
    
    boolean unloadQueuedChunks();
    
    boolean canSave();
    
    String makeString();
    
    List getPossibleCreatures(final EnumCreatureType p0, final int p1, final int p2, final int p3);
    
    ChunkPosition findClosestStructure(final World p0, final String p1, final int p2, final int p3, final int p4);
    
    int getLoadedChunkCount();
    
    void recreateStructures(final int p0, final int p1);
    
    void func_104112_b();
}
