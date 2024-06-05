package net;

import net.minecraft.block.BlockBeacon;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class aAx {
   public static void a(World var0, BlockPos var1) {
      BlockBeacon.updateColorAsync(var0, var1);
   }

   public static int a(BlockBeacon var0) {
      return var0.getLightValue();
   }
}
