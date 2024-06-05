package net;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.IChunkLoader;

public class aYa {
   public static Chunk a(IChunkLoader var0, World var1, int var2, int var3) {
      return var0.loadChunk(var1, var2, var3);
   }

   public static void b(IChunkLoader var0, World var1, Chunk var2) {
      var0.saveExtraChunkData(var1, var2);
   }

   public static void a(IChunkLoader var0, World var1, Chunk var2) {
      var0.saveChunk(var1, var2);
   }

   public static void a(IChunkLoader var0) {
      var0.chunkTick();
   }
}
