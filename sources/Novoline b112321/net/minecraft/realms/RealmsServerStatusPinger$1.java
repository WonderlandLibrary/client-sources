package net.minecraft.realms;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsServerPing;
import net.minecraft.realms.RealmsServerStatusPinger;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.ArrayUtils;

class RealmsServerStatusPinger$1 implements INetHandlerStatusClient {
   private boolean field_154345_e;
   final RealmsServerPing val$p_pingServer_2_;
   final NetworkManager val$networkmanager;
   final String val$p_pingServer_1_;
   final RealmsServerStatusPinger this$0;

   RealmsServerStatusPinger$1(RealmsServerStatusPinger var1, RealmsServerPing var2, NetworkManager var3, String var4) {
      this.this$0 = var1;
      this.val$p_pingServer_2_ = var2;
      this.val$networkmanager = var3;
      this.val$p_pingServer_1_ = var4;
      this.field_154345_e = false;
   }

   public void handleServerInfo(S00PacketServerInfo var1) {
      ServerStatusResponse var2 = var1.getResponse();
      if(var2.c() != null) {
         this.val$p_pingServer_2_.nrOfPlayers = String.valueOf(var2.c().b());
         if(ArrayUtils.isNotEmpty(var2.c().c())) {
            StringBuilder var3 = new StringBuilder();

            for(GameProfile var7 : var2.c().c()) {
               if(var3.length() > 0) {
                  var3.append("\n");
               }

               var3.append(var7.getName());
            }

            if(var2.c().c().length < var2.c().b()) {
               if(var3.length() > 0) {
                  var3.append("\n");
               }

               var3.append("... and ").append(var2.c().b() - var2.c().c().length).append(" more ...");
            }

            this.val$p_pingServer_2_.playerList = var3.toString();
         }
      } else {
         this.val$p_pingServer_2_.playerList = "";
      }

      this.val$networkmanager.sendPacket(new C01PacketPing(Realms.currentTimeMillis()));
      this.field_154345_e = true;
   }

   public void handlePong(S01PacketPong var1) {
      this.val$networkmanager.closeChannel(new ChatComponentText("Finished"));
   }

   public void onDisconnect(IChatComponent var1) {
      if(!this.field_154345_e) {
         RealmsServerStatusPinger.access$000().error("Can\'t ping " + this.val$p_pingServer_1_ + ": " + var1.getUnformattedText());
      }

   }
}
