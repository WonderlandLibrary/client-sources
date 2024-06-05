package net;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.chunk.ChunkPrimer;

public class bgf {
   public static void a(ChunkPrimer var0, int var1, int var2, int var3, IBlockState var4) {
      var0.setBlockState(var1, var2, var3, var4);
   }

   public static IBlockState a(ChunkPrimer var0, int var1, int var2, int var3) {
      return var0.getBlockState(var1, var2, var3);
   }

   public static IBlockState a(ChunkPrimer var0, int var1) {
      return var0.getBlockState(var1);
   }
}
