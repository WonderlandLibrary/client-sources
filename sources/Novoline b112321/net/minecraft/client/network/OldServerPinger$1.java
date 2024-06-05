package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.ArrayUtils;

class OldServerPinger$1 implements INetHandlerStatusClient {
   private boolean field_147403_d;
   private boolean field_183009_e;
   private long field_175092_e;
   final NetworkManager val$networkmanager;
   final ServerData val$server;
   final OldServerPinger this$0;

   OldServerPinger$1(OldServerPinger var1, NetworkManager var2, ServerData var3) {
      this.this$0 = var1;
      this.val$networkmanager = var2;
      this.val$server = var3;
      this.field_147403_d = false;
      this.field_183009_e = false;
      this.field_175092_e = 0L;
   }

   public void handleServerInfo(S00PacketServerInfo var1) {
      if(this.field_183009_e) {
         this.val$networkmanager.closeChannel(new ChatComponentText("Received unrequested status"));
      } else {
         this.field_183009_e = true;
         ServerStatusResponse var2 = var1.getResponse();
         if(var2.getServerDescription() != null) {
            this.val$server.serverMOTD = var2.getServerDescription().getFormattedText();
         } else {
            this.val$server.serverMOTD = "";
         }

         if(var2.d() != null) {
            this.val$server.gameVersion = var2.d().a();
            this.val$server.version = var2.d().b();
         } else {
            this.val$server.gameVersion = "Old";
            this.val$server.version = 0;
         }

         if(var2.c() == null) {
            this.val$server.h = EnumChatFormatting.DARK_GRAY + "???";
         } else {
            this.val$server.h = EnumChatFormatting.GRAY + "" + var2.c().b() + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var2.c().a();
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

               this.val$server.playerList = var3.toString();
            }
         }

         if(var2.getFavicon() != null) {
            String var8 = var2.getFavicon();
            if(var8.startsWith("data:image/png;base64,")) {
               this.val$server.setBase64EncodedIconData(var8.substring("data:image/png;base64,".length()));
            } else {
               OldServerPinger.access$000().error("Invalid server icon (unknown format)");
            }
         } else {
            this.val$server.setBase64EncodedIconData((String)null);
         }

         this.field_175092_e = Minecraft.getSystemTime();
         this.val$networkmanager.sendPacket(new C01PacketPing(this.field_175092_e));
         this.field_147403_d = true;
      }

   }

   public void handlePong(S01PacketPong var1) {
      long var2 = this.field_175092_e;
      long var4 = Minecraft.getSystemTime();
      this.val$server.b = var4 - var2;
      this.val$networkmanager.closeChannel(new ChatComponentText("Finished"));
   }

   public void onDisconnect(IChatComponent var1) {
      if(!this.field_147403_d) {
         OldServerPinger.access$000().error("Can\'t ping " + this.val$server.serverIP + ": " + var1.getUnformattedText());
         this.val$server.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t connect to server.";
         this.val$server.h = "";
         OldServerPinger.access$100(this.this$0, this.val$server);
      }

   }
}
