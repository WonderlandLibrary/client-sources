package net;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

public class ll {
   public static IBlockState a(BlockChest var0) {
      return var0.getDefaultState();
   }

   public static IBlockState b(BlockChest var0, World var1, BlockPos var2, IBlockState var3) {
      return var0.correctFacing(var1, var2, var3);
   }

   public static IBlockState a(BlockChest var0, World var1, BlockPos var2, IBlockState var3) {
      return var0.checkForSurroundingChests(var1, var2, var3);
   }

   public static ILockableContainer a(BlockChest var0, World var1, BlockPos var2) {
      return var0.getLockableContainer(var1, var2);
   }
}
