package net;

import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class a1y {
   public static boolean a(BlockReed var0, World var1, BlockPos var2) {
      return var0.canBlockStay(var1, var2);
   }

   public static IBlockState a(BlockReed var0) {
      return var0.getDefaultState();
   }
}
