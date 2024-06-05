package net;

import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.world.chunk.Chunk;

public class l3 {
   public static boolean a(ChunkProviderClient var0) {
      return var0.unloadQueuedChunks();
   }

   public static Chunk a(ChunkProviderClient var0, int var1, int var2) {
      return var0.loadChunk(var1, var2);
   }

   public static void b(ChunkProviderClient var0, int var1, int var2) {
      var0.unloadChunk(var1, var2);
   }
}
