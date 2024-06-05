package net.minecraft.realms;

import com.google.common.collect.Lists;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerStatusPinger {
   private final List connections = Collections.synchronizedList(Lists.newArrayList());
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String __OBFID = "CL_00001854";

   public void removeAll() {
      List var1 = this.connections;
      synchronized(this.connections) {
         Iterator var3 = this.connections.iterator();

         while(var3.hasNext()) {
            NetworkManager var4 = (NetworkManager)var3.next();
            if (var4.isChannelOpen()) {
               var3.remove();
               var4.closeChannel(new ChatComponentText("Cancelled"));
            }
         }

      }
   }

   static Logger access$0() {
      return LOGGER;
   }

   public void pingServer(String var1, RealmsServerPing var2) throws UnknownHostException {
      if (var1 != null && !var1.startsWith("0.0.0.0") && !var1.isEmpty()) {
         RealmsServerAddress var3 = RealmsServerAddress.parseString(var1);
         NetworkManager var4 = NetworkManager.provideLanClient(InetAddress.getByName(var3.getHost()), var3.getPort());
         this.connections.add(var4);
         var4.setNetHandler(new INetHandlerStatusClient(this, var2, var4, var1) {
            private final RealmsServerPing val$p_pingServer_2_;
            final RealmsServerStatusPinger this$0;
            private boolean field_154345_e;
            private final String val$p_pingServer_1_;
            private final NetworkManager val$var4;
            private static final String __OBFID = "CL_00001807";

            public void handlePong(S01PacketPong var1) {
               this.val$var4.closeChannel(new ChatComponentText("Finished"));
            }

            public void onDisconnect(IChatComponent var1) {
               if (!this.field_154345_e) {
                  RealmsServerStatusPinger.access$0().error(String.valueOf((new StringBuilder("Can't ping ")).append(this.val$p_pingServer_1_).append(": ").append(var1.getUnformattedText())));
               }

            }

            public void handleServerInfo(S00PacketServerInfo var1) {
               ServerStatusResponse var2 = var1.func_149294_c();
               if (var2.getPlayerCountData() != null) {
                  this.val$p_pingServer_2_.nrOfPlayers = String.valueOf(var2.getPlayerCountData().getOnlinePlayerCount());
               }

               this.val$var4.sendPacket(new C01PacketPing(Realms.currentTimeMillis()));
               this.field_154345_e = true;
            }

            {
               this.this$0 = var1;
               this.val$p_pingServer_2_ = var2;
               this.val$var4 = var3;
               this.val$p_pingServer_1_ = var4;
               this.field_154345_e = false;
            }
         });

         try {
            var4.sendPacket(new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, var3.getHost(), var3.getPort(), EnumConnectionState.STATUS));
            var4.sendPacket(new C00PacketServerQuery());
         } catch (Throwable var6) {
            LOGGER.error(var6);
         }
      }

   }

   public void tick() {
      List var1 = this.connections;
      synchronized(this.connections) {
         Iterator var3 = this.connections.iterator();

         while(var3.hasNext()) {
            NetworkManager var4 = (NetworkManager)var3.next();
            if (var4.isChannelOpen()) {
               var4.processReceivedPackets();
            } else {
               var3.remove();
               var4.checkDisconnected();
            }
         }

      }
   }
}
