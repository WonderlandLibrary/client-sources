package net;

import net.minecraft.network.play.server.S1FPacketSetExperience;

public class aIx {
   public static float b(S1FPacketSetExperience var0) {
      return var0.func_149397_c();
   }

   public static int a(S1FPacketSetExperience var0) {
      return var0.getTotalExperience();
   }

   public static int c(S1FPacketSetExperience var0) {
      return var0.getLevel();
   }
}
