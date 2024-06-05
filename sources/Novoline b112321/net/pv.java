package net;

import java.util.List;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;

public class pv {
   public static int a(S1CPacketEntityMetadata var0) {
      return var0.getEntityId();
   }

   public static List b(S1CPacketEntityMetadata var0) {
      return var0.func_149376_c();
   }
}
