package net;

import net.minecraft.network.play.server.S26PacketMapChunkBulk;

public class E3 {
   public static int a(S26PacketMapChunkBulk var0) {
      return var0.getChunkCount();
   }

   public static int b(S26PacketMapChunkBulk var0, int var1) {
      return var0.getChunkX(var1);
   }

   public static int c(S26PacketMapChunkBulk var0, int var1) {
      return var0.getChunkZ(var1);
   }

   public static byte[] a(S26PacketMapChunkBulk var0, int var1) {
      return var0.getChunkBytes(var1);
   }

   public static int d(S26PacketMapChunkBulk var0, int var1) {
      return var0.getChunkSize(var1);
   }
}
