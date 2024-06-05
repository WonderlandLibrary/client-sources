package net;

import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;

public class pf {
   public static String a(C14PacketTabComplete var0) {
      return var0.getMessage();
   }

   public static BlockPos b(C14PacketTabComplete var0) {
      return var0.getTargetBlock();
   }
}
