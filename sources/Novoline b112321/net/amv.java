package net;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.util.BlockPos;

public class amv {
   public static BlockPos d(BlockEventData var0) {
      return var0.getPosition();
   }

   public static Block b(BlockEventData var0) {
      return var0.getBlock();
   }

   public static int a(BlockEventData var0) {
      return var0.getEventID();
   }

   public static int c(BlockEventData var0) {
      return var0.getEventParameter();
   }
}
