package net;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;

public class oX {
   public static int a(ServerList var0) {
      return var0.countServers();
   }

   public static ServerData b(ServerList var0, int var1) {
      return var0.getServerData(var1);
   }

   public static void a(ServerData var0) {
      ServerList.func_147414_b(var0);
   }

   public static void c(ServerList var0) {
      var0.saveServerList();
   }

   public static void b(ServerList var0) {
      var0.loadServerList();
   }

   public static void a(ServerList var0, int var1) {
      var0.removeServerData(var1);
   }

   public static void a(ServerList var0, ServerData var1) {
      var0.addServerData(var1);
   }

   public static void a(ServerList var0, int var1, int var2) {
      var0.swapServers(var1, var2);
   }
}
