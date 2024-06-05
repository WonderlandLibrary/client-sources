package net;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.server.S22PacketMultiBlockChange$BlockUpdateData;
import net.minecraft.util.BlockPos;

public class jz {
   public static short c(S22PacketMultiBlockChange$BlockUpdateData var0) {
      return var0.func_180089_b();
   }

   public static IBlockState a(S22PacketMultiBlockChange$BlockUpdateData var0) {
      return var0.getBlockState();
   }

   public static BlockPos b(S22PacketMultiBlockChange$BlockUpdateData var0) {
      return var0.getPos();
   }
}
