package net;

import net.acE;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

public class ir {
   private static acE[] b;

   public static double c(BlockPos var0, Vec3i var1) {
      return var0.distanceSq(var1);
   }

   public static int e(BlockPos var0) {
      return var0.getX();
   }

   public static int a(BlockPos var0) {
      return var0.getY();
   }

   public static int d(BlockPos var0) {
      return var0.getZ();
   }

   public static BlockPos h(BlockPos var0) {
      return var0.down();
   }

   public static BlockPos a(BlockPos var0, EnumFacing var1) {
      return var0.offset(var1);
   }

   public static BlockPos a(BlockPos var0, int var1, int var2, int var3) {
      return var0.a(var1, var2, var3);
   }

   public static Iterable b(BlockPos var0, BlockPos var1) {
      return BlockPos.getAllInBox(var0, var1);
   }

   public static BlockPos i(BlockPos var0) {
      return var0.up();
   }

   public static BlockPos d(BlockPos var0, int var1) {
      return var0.up(var1);
   }

   public static BlockPos a(BlockPos var0, EnumFacing var1, int var2) {
      return var0.offset(var1, var2);
   }

   public static BlockPos g(BlockPos var0) {
      return var0.north();
   }

   public static BlockPos c(BlockPos var0) {
      return var0.south();
   }

   public static BlockPos b(BlockPos var0) {
      return var0.west();
   }

   public static BlockPos f(BlockPos var0) {
      return var0.east();
   }

   public static BlockPos a(BlockPos var0, int var1) {
      return var0.down(var1);
   }

   public static double a(BlockPos var0, double var1, double var3, double var5) {
      return var0.distanceSq(var1, var3, var5);
   }

   public static BlockPos b(BlockPos var0, Vec3i var1) {
      return var0.subtract(var1);
   }

   public static Iterable a(BlockPos var0, BlockPos var1) {
      return BlockPos.getAllInBoxMutable(var0, var1);
   }

   public static BlockPos c(BlockPos var0, double var1, double var3, double var5) {
      return var0.add(var1, var3, var5);
   }

   public static BlockPos a(long var0) {
      return BlockPos.fromLong(var0);
   }

   public static long j(BlockPos var0) {
      return var0.toLong();
   }

   public static BlockPos a(BlockPos var0, Vec3i var1) {
      return var0.add(var1);
   }

   public static double b(BlockPos var0, double var1, double var3, double var5) {
      return var0.distanceSqToCenter(var1, var3, var5);
   }

   public static BlockPos c(BlockPos var0, int var1) {
      return var0.east(var1);
   }

   public static BlockPos e(BlockPos var0, int var1) {
      return var0.west(var1);
   }

   public static BlockPos b(BlockPos var0, int var1) {
      return var0.south(var1);
   }

   public static BlockPos f(BlockPos var0, int var1) {
      return var0.north(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[3]);
      }

   }
}
