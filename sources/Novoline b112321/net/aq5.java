package net;

import java.util.Collection;
import java.util.UUID;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

public class aq5 {
   private static String[] b;

   public static void a(NetHandlerPlayClient var0, Packet var1) {
      var0.b(var1);
   }

   public static void c(NetHandlerPlayClient var0) {
      var0.cleanup();
   }

   public static NetworkManager b(NetHandlerPlayClient var0) {
      return var0.getNetworkManager();
   }

   public static Collection a(NetHandlerPlayClient var0) {
      return var0.getPlayerInfoMap();
   }

   public static NetworkPlayerInfo a(NetHandlerPlayClient var0, String var1) {
      return var0.getPlayerInfo(var1);
   }

   public static NetworkPlayerInfo a(UUID var0) {
      return NetHandlerPlayClient.getPlayerInfo(var0);
   }

   public static void b(NetHandlerPlayClient var0, Packet var1) {
      var0.sendPacketNoEvent(var1);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[4]);
      }

   }
}
