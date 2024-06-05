package net;

import java.util.Random;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class a0O {
   public static boolean a(IGrowable var0, World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return var0.canGrow(var1, var2, var3, var4);
   }

   public static boolean b(IGrowable var0, World var1, Random var2, BlockPos var3, IBlockState var4) {
      return var0.canUseBonemeal(var1, var2, var3, var4);
   }

   public static void a(IGrowable var0, World var1, Random var2, BlockPos var3, IBlockState var4) {
      var0.grow(var1, var2, var3, var4);
   }
}
