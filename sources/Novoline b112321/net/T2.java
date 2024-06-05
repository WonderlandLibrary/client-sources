package net;

import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class T2 {
   public static boolean a(BlockSkull var0, World var1, BlockPos var2) {
      return var0.canPlaceBlockAt(var1, var2);
   }

   public static IBlockState a(BlockSkull var0) {
      return var0.getDefaultState();
   }

   public static void a(BlockSkull var0, World var1, BlockPos var2, TileEntitySkull var3) {
      var0.checkWitherSpawn(var1, var2, var3);
   }

   public static boolean a(BlockSkull var0, World var1, BlockPos var2, ItemStack var3) {
      return var0.canDispenserPlace(var1, var2, var3);
   }
}
