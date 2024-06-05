package net;

import net.acE;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;

public class _e {
   private static acE[] b;

   public static BlockPos$MutableBlockPos a(BlockPos$MutableBlockPos var0, int var1, int var2, int var3) {
      return var0.func_181079_c(var1, var2, var3);
   }

   public static BlockPos f(BlockPos$MutableBlockPos var0) {
      return var0.west();
   }

   public static BlockPos e(BlockPos$MutableBlockPos var0) {
      return var0.east();
   }

   public static BlockPos c(BlockPos$MutableBlockPos var0) {
      return var0.north();
   }

   public static BlockPos b(BlockPos$MutableBlockPos var0) {
      return var0.south();
   }

   public static int d(BlockPos$MutableBlockPos var0) {
      return var0.getX();
   }

   public static int a(BlockPos$MutableBlockPos var0) {
      return var0.getZ();
   }

   public static int g(BlockPos$MutableBlockPos var0) {
      return var0.getY();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[2]);
      }

   }
}
