package net;

import viaversion.viaversion.api.minecraft.chunks.Chunk1_8;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;

public class _2 {
   public static int c(Chunk1_8 var0) {
      return var0.getX();
   }

   public static int h(Chunk1_8 var0) {
      return var0.getZ();
   }

   public static boolean e(Chunk1_8 var0) {
      return var0.isUnloadPacket();
   }

   public static boolean a(Chunk1_8 var0) {
      return var0.isFullChunk();
   }

   public static int g(Chunk1_8 var0) {
      return var0.getBitmask();
   }

   public static ChunkSection[] d(Chunk1_8 var0) {
      return var0.getSections();
   }

   public static boolean f(Chunk1_8 var0) {
      return var0.e();
   }

   public static int[] b(Chunk1_8 var0) {
      return var0.getBiomeData();
   }
}
