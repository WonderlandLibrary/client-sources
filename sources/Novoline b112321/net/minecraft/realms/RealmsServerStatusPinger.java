package net.minecraft.realms;

import com.google.common.collect.Lists;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.realms.RealmsServerAddress;
import net.minecraft.realms.RealmsServerPing;
import net.minecraft.realms.RealmsServerStatusPinger$1;
import net.minecraft.realms.RealmsSharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerStatusPinger {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List connections = Collections.synchronizedList(Lists.newArrayList());

   public void pingServer(String var1, RealmsServerPing var2) throws UnknownHostException {
      if(!var1.startsWith("0.0.0.0") && !var1.isEmpty()) {
         RealmsServerAddress var3 = RealmsServerAddress.parseString(var1);
         NetworkManager var4 = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(var3.getHost()), var3.getPort(), false);
         this.connections.add(var4);
         var4.setNetHandler(new RealmsServerStatusPinger$1(this, var2, var4, var1));
         NetworkManager var10000 = var4;

         try {
            var10000.sendPacket(new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, var3.getHost(), var3.getPort(), EnumConnectionState.STATUS));
            var4.sendPacket(new C00PacketServerQuery());
         } catch (Throwable var6) {
            LOGGER.error(var6);
         }
      }

   }

   public void tick() {
      // $FF: Couldn't be decompiled
   }

   public void removeAll() {
      // $FF: Couldn't be decompiled
   }

   static Logger access$000() {
      return LOGGER;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
