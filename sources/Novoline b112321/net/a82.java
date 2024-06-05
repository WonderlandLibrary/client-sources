package net;

import cc.novoline.utils.ServerUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map.Entry;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentTranslation;

class a82 extends Thread {
   final String b;
   final int a;
   final GuiConnecting c;

   a82(GuiConnecting var1, String var2, String var3, int var4) {
      super(var2);
      this.c = var1;
      this.b = var3;
      this.a = var4;
   }

   public void run() {
      InetAddress var1 = null;

      try {
         if(GuiConnecting.access$000(this.c)) {
            return;
         }

         var1 = InetAddress.getByName(this.b);
         if(this.b.endsWith("hypixel.net") || this.b.endsWith("hypixel.net.")) {
            boolean var2 = false;

            for(Entry var13 : GuiConnecting.access$100().entrySet()) {
               String var5 = (String)var13.getKey();
               int var6 = ((Integer)var13.getValue()).intValue();
               if(GuiConnecting.access$200(this.c, this.b, var5, var6)) {
                  var2 = true;
                  break;
               }
            }

            GuiConnecting.access$300().warn("Connecting to *.hypixel.net, but the IP ({}) is not within any of the hypixel ranges", new Object[]{var1});
            ServerUtils.setFakeHypixel(true);
         }

         ServerUtils.checkHypixel(GuiConnecting.a(this.c).getCurrentServerData());
         GuiConnecting.access$502(this.c, NetworkManager.createNetworkManagerAndConnect(var1, this.a, GuiConnecting.e(this.c).gameSettings.func_181148_f()));
         GuiConnecting.access$500(this.c).setNetHandler(new NetHandlerLoginClient(GuiConnecting.access$500(this.c), GuiConnecting.g(this.c), GuiConnecting.access$800(this.c)));
         GuiConnecting.access$500(this.c).sendPacket(new C00Handshake(47, this.b, this.a, EnumConnectionState.LOGIN));
         GuiConnecting.access$500(this.c).sendPacket(new C00PacketLoginStart(GuiConnecting.i(this.c).getSession().getProfile()));
      } catch (UnknownHostException var7) {
         if(GuiConnecting.access$000(this.c)) {
            return;
         }

         GuiConnecting.access$300().error("Couldn\'t connect to server", var7);
         GuiConnecting.d(this.c).displayGuiScreen(new GuiDisconnected(GuiConnecting.access$800(this.c), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{"Unknown host"})));
      } catch (Exception var8) {
         if(GuiConnecting.access$000(this.c)) {
            return;
         }

         GuiConnecting.access$300().error("Couldn\'t connect to server", var8);
         String var3 = var8.toString();
         String var4 = var1.toString() + ":" + this.a;
         var3 = var3.replaceAll(var4, "");
         GuiConnecting.b(this.c).displayGuiScreen(new GuiDisconnected(GuiConnecting.access$800(this.c), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{var3})));
      }

   }

   private static UnknownHostException a(UnknownHostException var0) {
      return var0;
   }
}
