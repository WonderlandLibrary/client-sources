package net;

import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.server.S00PacketServerInfo;

public class zm {
   private static String b;

   public static ServerStatusResponse a(S00PacketServerInfo var0) {
      return var0.getResponse();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("JtJfxb");
      }

   }
}
