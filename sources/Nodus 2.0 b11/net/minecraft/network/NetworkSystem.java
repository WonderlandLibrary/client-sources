/*   1:    */ package net.minecraft.network;
/*   2:    */ 
/*   3:    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*   4:    */ import io.netty.bootstrap.ServerBootstrap;
/*   5:    */ import io.netty.channel.Channel;
/*   6:    */ import io.netty.channel.ChannelConfig;
/*   7:    */ import io.netty.channel.ChannelException;
/*   8:    */ import io.netty.channel.ChannelFuture;
/*   9:    */ import io.netty.channel.ChannelInitializer;
/*  10:    */ import io.netty.channel.ChannelOption;
/*  11:    */ import io.netty.channel.ChannelPipeline;
/*  12:    */ import io.netty.channel.local.LocalAddress;
/*  13:    */ import io.netty.channel.local.LocalServerChannel;
/*  14:    */ import io.netty.channel.nio.NioEventLoopGroup;
/*  15:    */ import io.netty.channel.socket.nio.NioServerSocketChannel;
/*  16:    */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*  17:    */ import io.netty.util.concurrent.Future;
/*  18:    */ import io.netty.util.concurrent.GenericFutureListener;
/*  19:    */ import java.io.IOException;
/*  20:    */ import java.net.InetAddress;
/*  21:    */ import java.net.SocketAddress;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Collections;
/*  24:    */ import java.util.Iterator;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.concurrent.Callable;
/*  27:    */ import net.minecraft.client.network.NetHandlerHandshakeMemory;
/*  28:    */ import net.minecraft.crash.CrashReport;
/*  29:    */ import net.minecraft.crash.CrashReportCategory;
/*  30:    */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*  31:    */ import net.minecraft.server.MinecraftServer;
/*  32:    */ import net.minecraft.server.network.NetHandlerHandshakeTCP;
/*  33:    */ import net.minecraft.util.ChatComponentText;
/*  34:    */ import net.minecraft.util.MessageDeserializer;
/*  35:    */ import net.minecraft.util.MessageDeserializer2;
/*  36:    */ import net.minecraft.util.MessageSerializer;
/*  37:    */ import net.minecraft.util.MessageSerializer2;
/*  38:    */ import net.minecraft.util.ReportedException;
/*  39:    */ import org.apache.logging.log4j.LogManager;
/*  40:    */ import org.apache.logging.log4j.Logger;
/*  41:    */ 
/*  42:    */ public class NetworkSystem
/*  43:    */ {
/*  44: 42 */   private static final Logger logger = ;
/*  45: 43 */   private static final NioEventLoopGroup eventLoops = new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty IO #%d").setDaemon(true).build());
/*  46:    */   private final MinecraftServer mcServer;
/*  47:    */   public volatile boolean isAlive;
/*  48: 52 */   private final List endpoints = Collections.synchronizedList(new ArrayList());
/*  49: 55 */   private final List networkManagers = Collections.synchronizedList(new ArrayList());
/*  50:    */   private static final String __OBFID = "CL_00001447";
/*  51:    */   
/*  52:    */   public NetworkSystem(MinecraftServer p_i45292_1_)
/*  53:    */   {
/*  54: 60 */     this.mcServer = p_i45292_1_;
/*  55: 61 */     this.isAlive = true;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void addLanEndpoint(InetAddress p_151265_1_, int p_151265_2_)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 69 */     List var3 = this.endpoints;
/*  62: 71 */     synchronized (this.endpoints)
/*  63:    */     {
/*  64: 73 */       this.endpoints.add(
/*  65:    */       
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:102 */         ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer()
/*  94:    */         {
/*  95:    */           private static final String __OBFID = "CL_00001448";
/*  96:    */           
/*  97:    */           protected void initChannel(Channel p_initChannel_1_)
/*  98:    */           {
/*  99:    */             try
/* 100:    */             {
/* 101: 80 */               p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
/* 102:    */             }
/* 103:    */             catch (ChannelException localChannelException) {}
/* 104:    */             try
/* 105:    */             {
/* 106: 89 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
/* 107:    */             }
/* 108:    */             catch (ChannelException localChannelException1) {}
/* 109: 96 */             p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("legacy_query", new PingResponseHandler(NetworkSystem.this)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer()).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer());
/* 110: 97 */             NetworkManager var2 = new NetworkManager(false);
/* 111: 98 */             NetworkSystem.this.networkManagers.add(var2);
/* 112: 99 */             p_initChannel_1_.pipeline().addLast("packet_handler", var2);
/* 113:100 */             var2.setNetHandler(new NetHandlerHandshakeTCP(NetworkSystem.this.mcServer, var2));
/* 114:    */           }
/* 115:102 */         }).group(eventLoops).localAddress(p_151265_1_, p_151265_2_)).bind().syncUninterruptibly());
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public SocketAddress addLocalEndpoint()
/* 120:    */   {
/* 121:111 */     List var2 = this.endpoints;
/* 122:114 */     synchronized (this.endpoints)
/* 123:    */     {
/* 124:116 */       ChannelFuture var1 = 
/* 125:    */       
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:126 */         ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(LocalServerChannel.class)).childHandler(new ChannelInitializer()
/* 135:    */         {
/* 136:    */           private static final String __OBFID = "CL_00001449";
/* 137:    */           
/* 138:    */           protected void initChannel(Channel p_initChannel_1_)
/* 139:    */           {
/* 140:121 */             NetworkManager var2 = new NetworkManager(false);
/* 141:122 */             var2.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.this.mcServer, var2));
/* 142:123 */             NetworkSystem.this.networkManagers.add(var2);
/* 143:124 */             p_initChannel_1_.pipeline().addLast("packet_handler", var2);
/* 144:    */           }
/* 145:126 */         }).group(eventLoops).localAddress(LocalAddress.ANY)).bind().syncUninterruptibly();
/* 146:127 */       this.endpoints.add(var1);
/* 147:    */     }
/* 148:    */     ChannelFuture var1;
/* 149:130 */     return var1.channel().localAddress();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void terminateEndpoints()
/* 153:    */   {
/* 154:138 */     this.isAlive = false;
/* 155:139 */     Iterator var1 = this.endpoints.iterator();
/* 156:141 */     while (var1.hasNext())
/* 157:    */     {
/* 158:143 */       ChannelFuture var2 = (ChannelFuture)var1.next();
/* 159:144 */       var2.channel().close().syncUninterruptibly();
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void networkTick()
/* 164:    */   {
/* 165:154 */     List var1 = this.networkManagers;
/* 166:156 */     synchronized (this.networkManagers)
/* 167:    */     {
/* 168:158 */       Iterator var2 = this.networkManagers.iterator();
/* 169:160 */       while (var2.hasNext())
/* 170:    */       {
/* 171:162 */         final NetworkManager var3 = (NetworkManager)var2.next();
/* 172:164 */         if (!var3.isChannelOpen())
/* 173:    */         {
/* 174:166 */           var2.remove();
/* 175:168 */           if (var3.getExitMessage() != null) {
/* 176:170 */             var3.getNetHandler().onDisconnect(var3.getExitMessage());
/* 177:172 */           } else if (var3.getNetHandler() != null) {
/* 178:174 */             var3.getNetHandler().onDisconnect(new ChatComponentText("Disconnected"));
/* 179:    */           }
/* 180:    */         }
/* 181:    */         else
/* 182:    */         {
/* 183:    */           try
/* 184:    */           {
/* 185:181 */             var3.processReceivedPackets();
/* 186:    */           }
/* 187:    */           catch (Exception var8)
/* 188:    */           {
/* 189:185 */             if (var3.isLocalChannel())
/* 190:    */             {
/* 191:187 */               CrashReport var10 = CrashReport.makeCrashReport(var8, "Ticking memory connection");
/* 192:188 */               CrashReportCategory var6 = var10.makeCategory("Ticking connection");
/* 193:189 */               var6.addCrashSectionCallable("Connection", new Callable()
/* 194:    */               {
/* 195:    */                 private static final String __OBFID = "CL_00001450";
/* 196:    */                 
/* 197:    */                 public String call()
/* 198:    */                 {
/* 199:194 */                   return var3.toString();
/* 200:    */                 }
/* 201:196 */               });
/* 202:197 */               throw new ReportedException(var10);
/* 203:    */             }
/* 204:200 */             logger.warn("Failed to handle packet for " + var3.getSocketAddress(), var8);
/* 205:201 */             final ChatComponentText var5 = new ChatComponentText("Internal server error");
/* 206:202 */             var3.scheduleOutboundPacket(new S40PacketDisconnect(var5), new GenericFutureListener[] { new GenericFutureListener()
/* 207:    */             {
/* 208:    */               private static final String __OBFID = "CL_00001451";
/* 209:    */               
/* 210:    */               public void operationComplete(Future p_operationComplete_1_)
/* 211:    */               {
/* 212:207 */                 var3.closeChannel(var5);
/* 213:    */               }
/* 214:210 */             } });
/* 215:211 */             var3.disableAutoRead();
/* 216:    */           }
/* 217:    */         }
/* 218:    */       }
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public MinecraftServer func_151267_d()
/* 223:    */   {
/* 224:220 */     return this.mcServer;
/* 225:    */   }
/* 226:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.NetworkSystem
 * JD-Core Version:    0.7.0.1
 */