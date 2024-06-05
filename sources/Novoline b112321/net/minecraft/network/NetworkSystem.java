package net.minecraft.network;

import com.google.common.collect.Lists;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.List;
import net.Q7;
import net.Q9;
import net.QK;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.LazyLoadBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkSystem {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final LazyLoadBase eventLoops = new Q9();
   public static final LazyLoadBase field_181141_b = new Q7();
   public static final LazyLoadBase SERVER_LOCAL_EVENTLOOP = new QK();
   private final MinecraftServer mcServer;
   public volatile boolean isAlive;
   private final List endpoints = Collections.synchronizedList(Lists.newArrayList());
   private final List networkManagers = Collections.synchronizedList(Lists.newArrayList());

   public NetworkSystem(MinecraftServer var1) {
      this.mcServer = var1;
      this.isAlive = true;
   }

   public void addLanEndpoint(InetAddress param1, int param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public SocketAddress addLocalEndpoint() {
      // $FF: Couldn't be decompiled
   }

   public void terminateEndpoints() {
      this.isAlive = false;

      for(ChannelFuture var2 : this.endpoints) {
         ChannelFuture var10000 = var2;

         try {
            var10000.channel().close().sync();
         } catch (InterruptedException var4) {
            LOGGER.error("Interrupted whilst closing channel");
         }
      }

   }

   public void networkTick() {
      // $FF: Couldn't be decompiled
   }

   public MinecraftServer getServer() {
      return this.mcServer;
   }

   private static void lambda$networkTick$0(NetworkManager var0, ChatComponentText var1, Future var2) throws Exception {
      var0.closeChannel(var1);
   }

   static List access$000(NetworkSystem var0) {
      return var0.networkManagers;
   }

   static MinecraftServer access$100(NetworkSystem var0) {
      return var0.mcServer;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
