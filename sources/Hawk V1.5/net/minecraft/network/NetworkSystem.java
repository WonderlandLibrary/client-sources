package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.client.network.NetHandlerHandshakeMemory;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerHandshakeTCP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkSystem {
   public boolean isAlive;
   private static final String __OBFID = "CL_00001447";
   private final List networkManagers = Collections.synchronizedList(Lists.newArrayList());
   public static final LazyLoadBase eventLoops = new LazyLoadBase() {
      private static final String __OBFID = "CL_00001448";

      protected Object load() {
         return this.genericLoad();
      }

      protected NioEventLoopGroup genericLoad() {
         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).build());
      }
   };
   public static final LazyLoadBase SERVER_LOCAL_EVENTLOOP = new LazyLoadBase() {
      private static final String __OBFID = "CL_00001449";

      protected Object load() {
         return this.genericLoad();
      }

      protected LocalEventLoopGroup genericLoad() {
         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
      }
   };
   private final List endpoints = Collections.synchronizedList(Lists.newArrayList());
   private static final Logger logger = LogManager.getLogger();
   private final MinecraftServer mcServer;

   public SocketAddress addLocalEndpoint() {
      List var1 = this.endpoints;
      ChannelFuture var2;
      synchronized(this.endpoints) {
         var2 = ((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(LocalServerChannel.class)).childHandler(new ChannelInitializer(this) {
            final NetworkSystem this$0;
            private static final String __OBFID = "CL_00001451";

            {
               this.this$0 = var1;
            }

            protected void initChannel(Channel var1) {
               NetworkManager var2 = new NetworkManager(EnumPacketDirection.SERVERBOUND);
               var2.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.access$1(this.this$0), var2));
               NetworkSystem.access$0(this.this$0).add(var2);
               var1.pipeline().addLast("packet_handler", var2);
            }
         }).group((EventLoopGroup)eventLoops.getValue()).localAddress(LocalAddress.ANY)).bind().syncUninterruptibly();
         this.endpoints.add(var2);
      }

      return var2.channel().localAddress();
   }

   public void terminateEndpoints() {
      this.isAlive = false;
      Iterator var1 = this.endpoints.iterator();

      while(var1.hasNext()) {
         ChannelFuture var2 = (ChannelFuture)var1.next();

         try {
            var2.channel().close().sync();
         } catch (InterruptedException var4) {
            logger.error("Interrupted whilst closing channel");
         }
      }

   }

   public NetworkSystem(MinecraftServer var1) {
      this.mcServer = var1;
      this.isAlive = true;
   }

   static MinecraftServer access$1(NetworkSystem var0) {
      return var0.mcServer;
   }

   public void addLanEndpoint(InetAddress var1, int var2) throws IOException {
      List var3 = this.endpoints;
      synchronized(this.endpoints) {
         this.endpoints.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer(this) {
            private static final String __OBFID = "CL_00001450";
            final NetworkSystem this$0;

            protected void initChannel(Channel var1) {
               try {
                  var1.config().setOption(ChannelOption.IP_TOS, 24);
               } catch (ChannelException var4) {
               }

               try {
                  var1.config().setOption(ChannelOption.TCP_NODELAY, false);
               } catch (ChannelException var3) {
               }

               var1.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("legacy_query", new PingResponseHandler(this.this$0)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(EnumPacketDirection.SERVERBOUND)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(EnumPacketDirection.CLIENTBOUND));
               NetworkManager var2 = new NetworkManager(EnumPacketDirection.SERVERBOUND);
               NetworkSystem.access$0(this.this$0).add(var2);
               var1.pipeline().addLast("packet_handler", var2);
               var2.setNetHandler(new NetHandlerHandshakeTCP(NetworkSystem.access$1(this.this$0), var2));
            }

            {
               this.this$0 = var1;
            }
         }).group((EventLoopGroup)eventLoops.getValue()).localAddress(var1, var2)).bind().syncUninterruptibly());
      }
   }

   public void networkTick() {
      List var1 = this.networkManagers;
      synchronized(this.networkManagers) {
         Iterator var3 = this.networkManagers.iterator();

         while(true) {
            while(true) {
               NetworkManager var4;
               do {
                  if (!var3.hasNext()) {
                     return;
                  }

                  var4 = (NetworkManager)var3.next();
               } while(var4.hasNoChannel());

               if (!var4.isChannelOpen()) {
                  var3.remove();
                  var4.checkDisconnected();
               } else {
                  try {
                     var4.processReceivedPackets();
                  } catch (Exception var8) {
                     if (var4.isLocalChannel()) {
                        CrashReport var10 = CrashReport.makeCrashReport(var8, "Ticking memory connection");
                        CrashReportCategory var7 = var10.makeCategory("Ticking connection");
                        var7.addCrashSectionCallable("Connection", new Callable(this, var4) {
                           final NetworkSystem this$0;
                           private final NetworkManager val$var3;
                           private static final String __OBFID = "CL_00002272";

                           public Object call() {
                              return this.func_180229_a();
                           }

                           {
                              this.this$0 = var1;
                              this.val$var3 = var2;
                           }

                           public String func_180229_a() {
                              return this.val$var3.toString();
                           }
                        });
                        throw new ReportedException(var10);
                     }

                     logger.warn(String.valueOf((new StringBuilder("Failed to handle packet for ")).append(var4.getRemoteAddress())), var8);
                     ChatComponentText var6 = new ChatComponentText("Internal server error");
                     var4.sendPacket(new S40PacketDisconnect(var6), new GenericFutureListener(this, var4, var6) {
                        final NetworkSystem this$0;
                        private static final String __OBFID = "CL_00002271";
                        private final ChatComponentText val$var5;
                        private final NetworkManager val$var3;

                        {
                           this.this$0 = var1;
                           this.val$var3 = var2;
                           this.val$var5 = var3;
                        }

                        public void operationComplete(Future var1) {
                           this.val$var3.closeChannel(this.val$var5);
                        }
                     });
                     var4.disableAutoRead();
                  }
               }
            }
         }
      }
   }

   static List access$0(NetworkSystem var0) {
      return var0.networkManagers;
   }

   public MinecraftServer getServer() {
      return this.mcServer;
   }
}
