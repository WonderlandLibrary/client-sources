package net;

import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class xd {
   public static EnumFacing a(int var0) {
      return BlockPistonExtension.getFacing(var0);
   }

   public static IBlockState a(BlockPistonExtension var0) {
      return var0.getDefaultState();
   }
}
