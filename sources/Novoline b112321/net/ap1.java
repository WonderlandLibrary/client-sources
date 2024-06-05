package net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsConnect;
import net.minecraft.util.ChatComponentTranslation;

class ap1 extends Thread {
   final String b;
   final int c;
   final RealmsConnect a;

   ap1(RealmsConnect var1, String var2, String var3, int var4) {
      super(var2);
      this.a = var1;
      this.b = var3;
      this.c = var4;
   }

   public void run() {
      InetAddress var1 = null;

      try {
         var1 = InetAddress.getByName(this.b);
         if(RealmsConnect.access$000(this.a)) {
            return;
         }

         RealmsConnect.access$102(this.a, NetworkManager.createNetworkManagerAndConnect(var1, this.c, Minecraft.getInstance().gameSettings.func_181148_f()));
         if(RealmsConnect.access$000(this.a)) {
            return;
         }

         RealmsConnect.access$100(this.a).setNetHandler(new NetHandlerLoginClient(RealmsConnect.access$100(this.a), Minecraft.getInstance(), RealmsConnect.access$200(this.a).getProxy()));
         if(RealmsConnect.access$000(this.a)) {
            return;
         }

         RealmsConnect.access$100(this.a).sendPacket(new C00Handshake(47, this.b, this.c, EnumConnectionState.LOGIN));
         if(RealmsConnect.access$000(this.a)) {
            return;
         }

         RealmsConnect.access$100(this.a).sendPacket(new C00PacketLoginStart(Minecraft.getInstance().getSession().getProfile()));
      } catch (UnknownHostException var5) {
         Realms.clearResourcePack();
         if(RealmsConnect.access$000(this.a)) {
            return;
         }

         RealmsConnect.access$300().error("Couldn\'t connect to world", var5);
         Minecraft.getInstance().getResourcePackRepository().func_148529_f();
         Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.access$200(this.a), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{"Unknown host \'" + this.b + "\'"})));
      } catch (Exception var6) {
         Realms.clearResourcePack();
         if(RealmsConnect.access$000(this.a)) {
            return;
         }

         RealmsConnect.access$300().error("Couldn\'t connect to world", var6);
         String var3 = var6.toString();
         String var4 = var1.toString() + ":" + this.c;
         var3 = var3.replaceAll(var4, "");
         Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.access$200(this.a), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{var3})));
      }

   }

   private static UnknownHostException a(UnknownHostException var0) {
      return var0;
   }
}
