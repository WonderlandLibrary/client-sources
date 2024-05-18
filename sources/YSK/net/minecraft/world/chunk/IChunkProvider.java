package net.minecraft.world.chunk;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.*;

public interface IChunkProvider
{
    int getLoadedChunkCount();
    
    boolean saveChunks(final boolean p0, final IProgressUpdate p1);
    
    void saveExtraData();
    
    void populate(final IChunkProvider p0, final int p1, final int p2);
    
    List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(final EnumCreatureType p0, final BlockPos p1);
    
    String makeString();
    
    Chunk provideChunk(final int p0, final int p1);
    
    BlockPos getStrongholdGen(final World p0, final String p1, final BlockPos p2);
    
    boolean canSave();
    
    boolean chunkExists(final int p0, final int p1);
    
    boolean func_177460_a(final IChunkProvider p0, final Chunk p1, final int p2, final int p3);
    
    Chunk provideChunk(final BlockPos p0);
    
    void recreateStructures(final Chunk p0, final int p1, final int p2);
    
    boolean unloadQueuedChunks();
}
