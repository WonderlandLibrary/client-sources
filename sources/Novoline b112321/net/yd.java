package net;

import java.util.Set;
import net.Tk;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

public class yd {
   public static void a(NetHandlerPlayServer var0, String var1) {
      var0.kickPlayerFromServer(var1);
   }

   public static void a(NetHandlerPlayServer var0, Packet var1) {
      var0.sendPacket(var1);
   }

   public static NetworkManager a(NetHandlerPlayServer var0) {
      return var0.getNetworkManager();
   }

   public static void a(NetHandlerPlayServer var0, double var1, double var3, double var5, float var7, float var8, Set var9) {
      boolean var10 = Tk.c();
      var0.setPlayerLocation(var1, var3, var5, var7, var8, var9);
   }

   public static void a(NetHandlerPlayServer var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setPlayerLocation(var1, var3, var5, var7, var8);
   }
}
