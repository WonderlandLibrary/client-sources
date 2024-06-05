package net.minecraft.client.network;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OldServerPinger {
   private final List pingDestinations = Collections.synchronizedList(Lists.newArrayList());
   private static final Logger logger = LogManager.getLogger();
   private static final Splitter PING_RESPONSE_SPLITTER = Splitter.on('\u0000').limit(6);
   private static final String __OBFID = "CL_00000892";

   public void pingPendingNetworks() {
      List var1 = this.pingDestinations;
      synchronized(this.pingDestinations) {
         Iterator var3 = this.pingDestinations.iterator();

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

   private void tryCompatibilityPing(ServerData var1) {
      ServerAddress var2 = ServerAddress.func_78860_a(var1.serverIP);
      ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler(new ChannelInitializer(this, var2, var1) {
         final OldServerPinger this$0;
         private static final String __OBFID = "CL_00000894";
         private final ServerData val$server;
         private final ServerAddress val$var2;

         protected void initChannel(Channel var1) {
            try {
               var1.config().setOption(ChannelOption.IP_TOS, 24);
            } catch (ChannelException var4) {
            }

            try {
               var1.config().setOption(ChannelOption.TCP_NODELAY, false);
            } catch (ChannelException var3) {
            }

            var1.pipeline().addLast(new ChannelHandler[]{new SimpleChannelInboundHandler(this, this.val$var2, this.val$server) {
               private final ServerAddress val$var2;
               private final ServerData val$server;
               private static final String __OBFID = "CL_00000895";
               final <undefinedtype> this$1;

               protected void channelRead0(ChannelHandlerContext var1, Object var2) {
                  this.channelRead0(var1, (ByteBuf)var2);
               }

               protected void channelRead0(ChannelHandlerContext var1, ByteBuf var2) {
                  short var3 = var2.readUnsignedByte();
                  if (var3 == 255) {
                     String var4 = new String(var2.readBytes(var2.readShort() * 2).array(), Charsets.UTF_16BE);
                     String[] var5 = (String[])Iterables.toArray(OldServerPinger.access$2().split(var4), String.class);
                     if ("§1".equals(var5[0])) {
                        int var6 = MathHelper.parseIntWithDefault(var5[1], 0);
                        String var7 = var5[2];
                        String var8 = var5[3];
                        int var9 = MathHelper.parseIntWithDefault(var5[4], -1);
                        int var10 = MathHelper.parseIntWithDefault(var5[5], -1);
                        this.val$server.version = -1;
                        this.val$server.gameVersion = var7;
                        this.val$server.serverMOTD = var8;
                        this.val$server.populationInfo = String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append(var9).append(EnumChatFormatting.DARK_GRAY).append("/").append(EnumChatFormatting.GRAY).append(var10));
                     }
                  }

                  var1.close();
               }

               {
                  this.this$1 = var1;
                  this.val$var2 = var2;
                  this.val$server = var3;
               }

               public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) {
                  var1.close();
               }

               public void channelActive(ChannelHandlerContext var1) throws Exception {
                  super.channelActive(var1);
                  ByteBuf var2 = Unpooled.buffer();

                  try {
                     var2.writeByte(254);
                     var2.writeByte(1);
                     var2.writeByte(250);
                     char[] var3 = "MC|PingHost".toCharArray();
                     var2.writeShort(var3.length);
                     char[] var4 = var3;
                     int var5 = var3.length;

                     int var6;
                     char var7;
                     for(var6 = 0; var6 < var5; ++var6) {
                        var7 = var4[var6];
                        var2.writeChar(var7);
                     }

                     var2.writeShort(7 + 2 * this.val$var2.getIP().length());
                     var2.writeByte(127);
                     var3 = this.val$var2.getIP().toCharArray();
                     var2.writeShort(var3.length);
                     var4 = var3;
                     var5 = var3.length;

                     for(var6 = 0; var6 < var5; ++var6) {
                        var7 = var4[var6];
                        var2.writeChar(var7);
                     }

                     var2.writeInt(this.val$var2.getPort());
                     var1.channel().writeAndFlush(var2).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                     var2.release();
                  } finally {
                     var2.release();
                  }
               }
            }});
         }

         {
            this.this$0 = var1;
            this.val$var2 = var2;
            this.val$server = var3;
         }
      })).channel(NioSocketChannel.class)).connect(var2.getIP(), var2.getPort());
   }

   public void clearPendingNetworks() {
      List var1 = this.pingDestinations;
      synchronized(this.pingDestinations) {
         Iterator var3 = this.pingDestinations.iterator();

         while(var3.hasNext()) {
            NetworkManager var4 = (NetworkManager)var3.next();
            if (var4.isChannelOpen()) {
               var3.remove();
               var4.closeChannel(new ChatComponentText("Cancelled"));
            }
         }

      }
   }

   static Splitter access$2() {
      return PING_RESPONSE_SPLITTER;
   }

   public void ping(ServerData var1) throws UnknownHostException {
      ServerAddress var2 = ServerAddress.func_78860_a(var1.serverIP);
      NetworkManager var3 = NetworkManager.provideLanClient(InetAddress.getByName(var2.getIP()), var2.getPort());
      this.pingDestinations.add(var3);
      var1.serverMOTD = "Pinging...";
      var1.pingToServer = -1L;
      var1.playerList = null;
      var3.setNetHandler(new INetHandlerStatusClient(this, var1, var3) {
         final OldServerPinger this$0;
         private final ServerData val$server;
         private long field_175092_e;
         private boolean field_147403_d;
         private static final String __OBFID = "CL_00000893";
         private final NetworkManager val$var3;

         public void onDisconnect(IChatComponent var1) {
            if (!this.field_147403_d) {
               OldServerPinger.access$0().error(String.valueOf((new StringBuilder("Can't ping ")).append(this.val$server.serverIP).append(": ").append(var1.getUnformattedText())));
               this.val$server.serverMOTD = String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_RED).append("Can't connect to server."));
               this.val$server.populationInfo = "";
               OldServerPinger.access$1(this.this$0, this.val$server);
            }

         }

         public void handleServerInfo(S00PacketServerInfo var1) {
            ServerStatusResponse var2 = var1.func_149294_c();
            if (var2.getServerDescription() != null) {
               this.val$server.serverMOTD = var2.getServerDescription().getFormattedText();
            } else {
               this.val$server.serverMOTD = "";
            }

            if (var2.getProtocolVersionInfo() != null) {
               this.val$server.gameVersion = var2.getProtocolVersionInfo().getName();
               this.val$server.version = var2.getProtocolVersionInfo().getProtocol();
            } else {
               this.val$server.gameVersion = "Old";
               this.val$server.version = 0;
            }

            if (var2.getPlayerCountData() != null) {
               this.val$server.populationInfo = String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append(var2.getPlayerCountData().getOnlinePlayerCount()).append(EnumChatFormatting.DARK_GRAY).append("/").append(EnumChatFormatting.GRAY).append(var2.getPlayerCountData().getMaxPlayers()));
               if (ArrayUtils.isNotEmpty(var2.getPlayerCountData().getPlayers())) {
                  StringBuilder var3 = new StringBuilder();
                  GameProfile[] var4 = var2.getPlayerCountData().getPlayers();
                  int var5 = var4.length;

                  for(int var6 = 0; var6 < var5; ++var6) {
                     GameProfile var7 = var4[var6];
                     if (var3.length() > 0) {
                        var3.append("\n");
                     }

                     var3.append(var7.getName());
                  }

                  if (var2.getPlayerCountData().getPlayers().length < var2.getPlayerCountData().getOnlinePlayerCount()) {
                     if (var3.length() > 0) {
                        var3.append("\n");
                     }

                     var3.append("... and ").append(var2.getPlayerCountData().getOnlinePlayerCount() - var2.getPlayerCountData().getPlayers().length).append(" more ...");
                  }

                  this.val$server.playerList = String.valueOf(var3);
               }
            } else {
               this.val$server.populationInfo = String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_GRAY).append("???"));
            }

            if (var2.getFavicon() != null) {
               String var8 = var2.getFavicon();
               if (var8.startsWith("data:image/png;base64,")) {
                  this.val$server.setBase64EncodedIconData(var8.substring("data:image/png;base64,".length()));
               } else {
                  OldServerPinger.access$0().error("Invalid server icon (unknown format)");
               }
            } else {
               this.val$server.setBase64EncodedIconData((String)null);
            }

            this.field_175092_e = Minecraft.getSystemTime();
            this.val$var3.sendPacket(new C01PacketPing(this.field_175092_e));
            this.field_147403_d = true;
         }

         public void handlePong(S01PacketPong var1) {
            long var2 = this.field_175092_e;
            long var4 = Minecraft.getSystemTime();
            this.val$server.pingToServer = var4 - var2;
            this.val$var3.closeChannel(new ChatComponentText("Finished"));
         }

         {
            this.this$0 = var1;
            this.val$server = var2;
            this.val$var3 = var3;
            this.field_147403_d = false;
            this.field_175092_e = 0L;
         }
      });

      try {
         var3.sendPacket(new C00Handshake(47, var2.getIP(), var2.getPort(), EnumConnectionState.STATUS));
         var3.sendPacket(new C00PacketServerQuery());
      } catch (Throwable var5) {
         logger.error(var5);
      }

   }

   static Logger access$0() {
      return logger;
   }

   static void access$1(OldServerPinger var0, ServerData var1) {
      var0.tryCompatibilityPing(var1);
   }
}
