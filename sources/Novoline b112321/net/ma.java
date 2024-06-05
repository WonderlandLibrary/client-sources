package net;

import cc.novoline.events.events.CollideWithBlockEvent;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class ma {
   public static boolean b(CollideWithBlockEvent var0) {
      return var0.isCancelled();
   }

   public static AxisAlignedBB d(CollideWithBlockEvent var0) {
      return var0.getBoundingBox();
   }

   public static void a(CollideWithBlockEvent var0, AxisAlignedBB var1) {
      var0.setBoundingBox(var1);
   }

   public static BlockPos c(CollideWithBlockEvent var0) {
      return var0.getPos();
   }

   public static Block a(CollideWithBlockEvent var0) {
      return var0.getBlock();
   }
}
