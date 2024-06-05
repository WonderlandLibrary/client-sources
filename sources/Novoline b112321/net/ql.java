package net;

import java.util.List;
import net.minecraft.network.play.server.S20PacketEntityProperties;

public class ql {
   public static int b(S20PacketEntityProperties var0) {
      return var0.getEntityId();
   }

   public static List a(S20PacketEntityProperties var0) {
      return var0.func_149441_d();
   }
}
