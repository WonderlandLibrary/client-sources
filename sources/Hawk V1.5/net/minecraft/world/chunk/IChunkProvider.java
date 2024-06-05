package net.minecraft.world.chunk;

import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;

public interface IChunkProvider {
   boolean chunkExists(int var1, int var2);

   boolean func_177460_a(IChunkProvider var1, Chunk var2, int var3, int var4);

   Chunk func_177459_a(BlockPos var1);

   void populate(IChunkProvider var1, int var2, int var3);

   Chunk provideChunk(int var1, int var2);

   boolean canSave();

   void func_180514_a(Chunk var1, int var2, int var3);

   boolean unloadQueuedChunks();

   BlockPos func_180513_a(World var1, String var2, BlockPos var3);

   int getLoadedChunkCount();

   List func_177458_a(EnumCreatureType var1, BlockPos var2);

   void saveExtraData();

   boolean saveChunks(boolean var1, IProgressUpdate var2);

   String makeString();
}
