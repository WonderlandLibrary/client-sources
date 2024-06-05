package net;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class az8 {
   private static boolean b;

   public static int d(IBlockSource var0) {
      return var0.getBlockMetadata();
   }

   public static World c(IBlockSource var0) {
      return var0.getWorld();
   }

   public static BlockPos e(IBlockSource var0) {
      return var0.getBlockPos();
   }

   public static TileEntity a(IBlockSource var0) {
      return var0.getBlockTileEntity();
   }

   public static double f(IBlockSource var0) {
      return var0.getX();
   }

   public static double g(IBlockSource var0) {
      return var0.getY();
   }

   public static double b(IBlockSource var0) {
      return var0.getZ();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(b()) {
         b(true);
      }

   }
}
