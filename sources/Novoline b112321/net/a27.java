package net;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class a27 {
   public static boolean a(IBlockState var0) {
      return BlockRailBase.isRailBlock(var0);
   }

   public static boolean a(World var0, BlockPos var1) {
      return BlockRailBase.isRailBlock(var0, var1);
   }

   public static IProperty a(BlockRailBase var0) {
      return var0.getShapeProperty();
   }
}
