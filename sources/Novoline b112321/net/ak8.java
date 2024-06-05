package net;

import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ak8 {
   public static void a(BlockJukebox var0, World var1, BlockPos var2, IBlockState var3, ItemStack var4) {
      var0.insertRecord(var1, var2, var3, var4);
   }
}
