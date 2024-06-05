package net;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ajb {
   public static IBlockState a(BlockTallGrass var0) {
      return var0.getDefaultState();
   }

   public static boolean a(BlockTallGrass var0, World var1, BlockPos var2, IBlockState var3) {
      return var0.canBlockStay(var1, var2, var3);
   }
}
