package net;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.state.IBlockState;

public class aB8 {
   public static IBlockState a(BlockLeaves var0) {
      return var0.getDefaultState();
   }

   public static void a(BlockLeaves var0, boolean var1) {
      var0.setGraphicsLevel(var1);
   }

   public static IBlockState b(BlockLeaves var0, int var1) {
      return var0.getStateFromMeta(var1);
   }

   public static int a(BlockLeaves var0, IBlockState var1) {
      return var0.getRenderColor(var1);
   }

   public static BlockPlanks$EnumType a(BlockLeaves var0, int var1) {
      return var0.getWoodType(var1);
   }
}
