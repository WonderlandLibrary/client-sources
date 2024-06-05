package net;

import net.minecraft.block.BlockHopper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class al0 {
   public static boolean a(int var0) {
      return BlockHopper.isEnabled(var0);
   }

   public static EnumFacing b(int var0) {
      return BlockHopper.getFacing(var0);
   }

   public static IBlockState a(BlockHopper var0) {
      return var0.getDefaultState();
   }
}
