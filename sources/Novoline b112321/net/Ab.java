package net;

import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class Ab {
   public static void a(BlockTripWireHook var0, World var1, BlockPos var2, IBlockState var3, boolean var4, boolean var5, int var6, IBlockState var7) {
      var0.func_176260_a(var1, var2, var3, var4, var5, var6, var7);
   }

   public static IBlockState a(BlockTripWireHook var0, int var1) {
      return var0.getStateFromMeta(var1);
   }
}
