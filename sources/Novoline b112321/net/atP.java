package net;

import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;

public class atP {
   public static void a(INetHandlerStatusClient var0, S01PacketPong var1) {
      var0.handlePong(var1);
   }

   public static void a(INetHandlerStatusClient var0, S00PacketServerInfo var1) {
      var0.handleServerInfo(var1);
   }
}
