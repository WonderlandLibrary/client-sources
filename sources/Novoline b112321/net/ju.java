package net;

import net.minecraft.client.multiplayer.ServerAddress;

public class ju {
   public static ServerAddress a(String var0) {
      return ServerAddress.b(var0);
   }

   public static String b(ServerAddress var0) {
      return var0.getIP();
   }

   public static int a(ServerAddress var0) {
      return var0.getPort();
   }
}
