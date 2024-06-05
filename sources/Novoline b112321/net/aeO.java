package net;

import java.net.InetAddress;
import java.net.SocketAddress;
import net.minecraft.network.NetworkSystem;
import net.minecraft.server.MinecraftServer;

public class aeO {
   public static SocketAddress c(NetworkSystem var0) {
      return var0.addLocalEndpoint();
   }

   public static void a(NetworkSystem var0, InetAddress var1, int var2) {
      var0.addLanEndpoint(var1, var2);
   }

   public static MinecraftServer b(NetworkSystem var0) {
      return var0.getServer();
   }

   public static void a(NetworkSystem var0) {
      var0.terminateEndpoints();
   }

   public static void d(NetworkSystem var0) {
      var0.networkTick();
   }
}
