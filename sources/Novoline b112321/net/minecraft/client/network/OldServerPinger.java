package net.minecraft.client.network;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import net.aGr;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger$1;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.client.C00PacketServerQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OldServerPinger {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Splitter PING_RESPONSE_SPLITTER = Splitter.on('\u0000').limit(6);
   private final List pingDestinations = Collections.synchronizedList(Lists.newArrayList());

   public void ping(ServerData var1) throws UnknownHostException {
      ServerAddress var2 = ServerAddress.b(var1.serverIP);
      NetworkManager var3 = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(var2.getIP()), var2.getPort(), false);
      this.pingDestinations.add(var3);
      var1.serverMOTD = "Pinging...";
      var1.b = -1L;
      var1.playerList = null;
      var3.setNetHandler(new OldServerPinger$1(this, var3, var1));
      NetworkManager var10000 = var3;

      try {
         var10000.sendPacket(new C00Handshake(47, var2.getIP(), var2.getPort(), EnumConnectionState.STATUS));
         var3.sendPacket(new C00PacketServerQuery());
      } catch (Throwable var5) {
         LOGGER.error(var5);
      }

   }

   private void tryCompatibilityPing(ServerData var1) {
      ServerAddress var2 = ServerAddress.b(var1.serverIP);
      ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENT_LOOP.getValue())).handler(new aGr(this, var2, var1))).channel(NioSocketChannel.class)).connect(var2.getIP(), var2.getPort());
   }

   public void pingPendingNetworks() {
      // $FF: Couldn't be decompiled
   }

   public void clearPendingNetworks() {
      // $FF: Couldn't be decompiled
   }

   static Logger access$000() {
      return LOGGER;
   }

   static void access$100(OldServerPinger var0, ServerData var1) {
      var0.tryCompatibilityPing(var1);
   }

   static Splitter access$200() {
      return PING_RESPONSE_SPLITTER;
   }
}
