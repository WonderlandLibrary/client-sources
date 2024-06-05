package net;

import net.minecraft.network.play.server.S31PacketWindowProperty;

public class a6B {
   public static int b(S31PacketWindowProperty var0) {
      return var0.getWindowId();
   }

   public static int a(S31PacketWindowProperty var0) {
      return var0.getVarIndex();
   }

   public static int c(S31PacketWindowProperty var0) {
      return var0.getVarValue();
   }
}
