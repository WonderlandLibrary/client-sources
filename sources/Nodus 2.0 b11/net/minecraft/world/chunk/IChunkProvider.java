package net.minecraft.world.chunk;

import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public abstract interface IChunkProvider
{
  public abstract boolean chunkExists(int paramInt1, int paramInt2);
  
  public abstract Chunk provideChunk(int paramInt1, int paramInt2);
  
  public abstract Chunk loadChunk(int paramInt1, int paramInt2);
  
  public abstract void populate(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2);
  
  public abstract boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate);
  
  public abstract boolean unloadQueuedChunks();
  
  public abstract boolean canSave();
  
  public abstract String makeString();
  
  public abstract List getPossibleCreatures(EnumCreatureType paramEnumCreatureType, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract ChunkPosition func_147416_a(World paramWorld, String paramString, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getLoadedChunkCount();
  
  public abstract void recreateStructures(int paramInt1, int paramInt2);
  
  public abstract void saveExtraData();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.IChunkProvider
 * JD-Core Version:    0.7.0.1
 */