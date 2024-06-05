package net;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class a43 {
   public static void e(TileEntityPiston var0) {
      var0.clearPistonTileEntity();
   }

   public static IBlockState a(TileEntityPiston var0) {
      return var0.getPistonState();
   }

   public static float c(TileEntityPiston var0, float var1) {
      return var0.getProgress(var1);
   }

   public static boolean b(TileEntityPiston var0) {
      return var0.isExtending();
   }

   public static EnumFacing c(TileEntityPiston var0) {
      return var0.getFacing();
   }

   public static BlockPos f(TileEntityPiston var0) {
      return var0.getPos();
   }

   public static float a(TileEntityPiston var0, float var1) {
      return var0.getOffsetX(var1);
   }

   public static float d(TileEntityPiston var0, float var1) {
      return var0.getOffsetY(var1);
   }

   public static float b(TileEntityPiston var0, float var1) {
      return var0.getOffsetZ(var1);
   }

   public static boolean d(TileEntityPiston var0) {
      return var0.shouldPistonHeadBeRendered();
   }
}
