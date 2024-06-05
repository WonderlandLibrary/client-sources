package net;

import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;

public class bu {
   public static int b(S1EPacketRemoveEntityEffect var0) {
      return var0.getEntityId();
   }

   public static int a(S1EPacketRemoveEntityEffect var0) {
      return var0.getEffectId();
   }
}
