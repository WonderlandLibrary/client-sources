package net;

import viaversion.viaversion.api.minecraft.BlockChangeRecord;

public class u5 {
   public static int e(BlockChangeRecord var0) {
      return var0.getBlockId();
   }

   public static byte a(BlockChangeRecord var0) {
      return var0.getSectionX();
   }

   public static short b(BlockChangeRecord var0) {
      return var0.getY();
   }

   public static byte d(BlockChangeRecord var0) {
      return var0.getSectionZ();
   }

   public static void a(BlockChangeRecord var0, int var1) {
      var0.setBlockId(var1);
   }

   public static short b(BlockChangeRecord var0, int var1) {
      return var0.getY(var1);
   }

   public static byte c(BlockChangeRecord var0) {
      return var0.getSectionY();
   }
}
