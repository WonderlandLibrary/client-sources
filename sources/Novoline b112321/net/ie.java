package net;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;

public class ie {
   public static BlockPos a(S23PacketBlockChange var0) {
      return var0.getBlockPosition();
   }

   public static IBlockState b(S23PacketBlockChange var0) {
      return var0.getBlockState();
   }
}
