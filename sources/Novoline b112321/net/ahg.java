package net;

import java.util.List;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

public class ahg {
   public static Chunk a(ChunkProviderServer var0, int var1, int var2) {
      return var0.loadChunk(var1, var2);
   }

   public static void b(ChunkProviderServer var0, int var1, int var2) {
      var0.dropChunk(var1, var2);
   }

   public static List a(ChunkProviderServer var0) {
      return var0.func_152380_a();
   }

   public static void b(ChunkProviderServer var0) {
      var0.unloadAllChunks();
   }
}
