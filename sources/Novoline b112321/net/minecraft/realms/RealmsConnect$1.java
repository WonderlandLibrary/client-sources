package net.minecraft.realms;

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

class RealmsConnect$1 extends Thread {
   final String val$p_connect_1_;
   final int val$p_connect_2_;
   final RealmsConnect this$0;

   RealmsConnect$1(RealmsConnect var1, String var2, String var3, int var4) {
      super(var2);
      this.this$0 = var1;
      this.val$p_connect_1_ = var3;
      this.val$p_connect_2_ = var4;
   }

   public void run() {
      InetAddress var1 = null;

      try {
         var1 = InetAddress.getByName(this.val$p_connect_1_);
         if(RealmsConnect.access$000(this.this$0)) {
            return;
         }

         RealmsConnect.access$102(this.this$0, NetworkManager.c(var1, this.val$p_connect_2_, Minecraft.getMinecraft().gameSettings.func_181148_f()));
         if(RealmsConnect.access$000(this.this$0)) {
            return;
         }

         RealmsConnect.access$100(this.this$0).setNetHandler(new NetHandlerLoginClient(RealmsConnect.access$100(this.this$0), Minecraft.getMinecraft(), RealmsConnect.access$200(this.this$0).getProxy()));
         if(RealmsConnect.access$000(this.this$0)) {
            return;
         }

         RealmsConnect.access$100(this.this$0).sendPacket(new C00Handshake(47, this.val$p_connect_1_, this.val$p_connect_2_, EnumConnectionState.LOGIN));
         if(RealmsConnect.access$000(this.this$0)) {
            return;
         }

         RealmsConnect.access$100(this.this$0).sendPacket(new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
      } catch (UnknownHostException var5) {
         Realms.clearResourcePack();
         if(RealmsConnect.access$000(this.this$0)) {
            return;
         }

         RealmsConnect.access$300().error("Couldn\'t connect to world", var5);
         Minecraft.getMinecraft().an().g();
         Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.access$200(this.this$0), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{"Unknown host \'" + this.val$p_connect_1_ + "\'"})));
      } catch (Exception var6) {
         Realms.clearResourcePack();
         if(RealmsConnect.access$000(this.this$0)) {
            return;
         }

         RealmsConnect.access$300().error("Couldn\'t connect to world", var6);
         String var3 = var6.toString();
         String var4 = var1.toString() + ":" + this.val$p_connect_2_;
         var3 = var3.replaceAll(var4, "");
         Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.access$200(this.this$0), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{var3})));
      }

   }

   private static UnknownHostException a(UnknownHostException var0) {
      return var0;
   }
}
