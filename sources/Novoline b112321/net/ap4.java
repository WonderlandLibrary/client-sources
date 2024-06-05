package net;

import net.minecraft.block.BlockFalling;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ap4 {
   public static boolean a(World var0, BlockPos var1) {
      return BlockFalling.canFallInto(var0, var1);
   }

   public static void a(BlockFalling var0, World var1, BlockPos var2) {
      var0.onEndFalling(var1, var2);
   }
}
