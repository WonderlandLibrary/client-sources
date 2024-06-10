/*   1:    */ package net.minecraft.client.network;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import com.google.common.base.Splitter;
/*   5:    */ import com.google.common.collect.Iterables;
/*   6:    */ import com.mojang.authlib.GameProfile;
/*   7:    */ import io.netty.bootstrap.Bootstrap;
/*   8:    */ import io.netty.buffer.ByteBuf;
/*   9:    */ import io.netty.buffer.Unpooled;
/*  10:    */ import io.netty.channel.Channel;
/*  11:    */ import io.netty.channel.ChannelConfig;
/*  12:    */ import io.netty.channel.ChannelException;
/*  13:    */ import io.netty.channel.ChannelFuture;
/*  14:    */ import io.netty.channel.ChannelFutureListener;
/*  15:    */ import io.netty.channel.ChannelHandler;
/*  16:    */ import io.netty.channel.ChannelHandlerContext;
/*  17:    */ import io.netty.channel.ChannelInitializer;
/*  18:    */ import io.netty.channel.ChannelOption;
/*  19:    */ import io.netty.channel.ChannelPipeline;
/*  20:    */ import io.netty.channel.SimpleChannelInboundHandler;
/*  21:    */ import io.netty.channel.socket.nio.NioSocketChannel;
/*  22:    */ import io.netty.util.concurrent.GenericFutureListener;
/*  23:    */ import java.net.InetAddress;
/*  24:    */ import java.net.UnknownHostException;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Collections;
/*  27:    */ import java.util.Iterator;
/*  28:    */ import java.util.List;
/*  29:    */ import me.connorm.Nodus.Nodus;
/*  30:    */ import net.minecraft.client.Minecraft;
/*  31:    */ import net.minecraft.client.multiplayer.ServerAddress;
/*  32:    */ import net.minecraft.client.multiplayer.ServerData;
/*  33:    */ import net.minecraft.network.EnumConnectionState;
/*  34:    */ import net.minecraft.network.INetHandler;
/*  35:    */ import net.minecraft.network.NetworkManager;
/*  36:    */ import net.minecraft.network.ServerStatusResponse;
/*  37:    */ import net.minecraft.network.ServerStatusResponse.MinecraftProtocolVersionIdentifier;
/*  38:    */ import net.minecraft.network.ServerStatusResponse.PlayerCountData;
/*  39:    */ import net.minecraft.network.handshake.client.C00Handshake;
/*  40:    */ import net.minecraft.network.status.INetHandlerStatusClient;
/*  41:    */ import net.minecraft.network.status.client.C00PacketServerQuery;
/*  42:    */ import net.minecraft.network.status.client.C01PacketPing;
/*  43:    */ import net.minecraft.network.status.server.S00PacketServerInfo;
/*  44:    */ import net.minecraft.network.status.server.S01PacketPong;
/*  45:    */ import net.minecraft.util.ChatComponentText;
/*  46:    */ import net.minecraft.util.EnumChatFormatting;
/*  47:    */ import net.minecraft.util.IChatComponent;
/*  48:    */ import net.minecraft.util.MathHelper;
/*  49:    */ import org.apache.commons.lang3.ArrayUtils;
/*  50:    */ import org.apache.logging.log4j.LogManager;
/*  51:    */ import org.apache.logging.log4j.Logger;
/*  52:    */ 
/*  53:    */ public class OldServerPinger
/*  54:    */ {
/*  55: 53 */   private static final Splitter field_147230_a = Splitter.on('\000').limit(6);
/*  56: 54 */   private static final Logger logger = LogManager.getLogger();
/*  57: 55 */   private final List field_147229_c = Collections.synchronizedList(new ArrayList());
/*  58:    */   private static final String __OBFID = "CL_00000892";
/*  59:    */   
/*  60:    */   public void func_147224_a(final ServerData p_147224_1_)
/*  61:    */     throws UnknownHostException
/*  62:    */   {
/*  63: 60 */     ServerAddress var2 = ServerAddress.func_78860_a(p_147224_1_.serverIP);
/*  64: 61 */     final NetworkManager var3 = NetworkManager.provideLanClient(InetAddress.getByName(var2.getIP()), var2.getPort());
/*  65: 62 */     this.field_147229_c.add(var3);
/*  66: 63 */     p_147224_1_.serverMOTD = "Pinging...";
/*  67: 64 */     p_147224_1_.pingToServer = -1L;
/*  68: 65 */     p_147224_1_.field_147412_i = null;
/*  69: 66 */     var3.setNetHandler(new INetHandlerStatusClient()
/*  70:    */     {
/*  71: 68 */       private boolean field_147403_d = false;
/*  72:    */       private static final String __OBFID = "CL_00000893";
/*  73:    */       
/*  74:    */       public void handleServerInfo(S00PacketServerInfo p_147397_1_)
/*  75:    */       {
/*  76: 72 */         ServerStatusResponse var2 = p_147397_1_.func_149294_c();
/*  77: 74 */         if (var2.func_151317_a() != null) {
/*  78: 76 */           p_147224_1_.serverMOTD = var2.func_151317_a().getFormattedText();
/*  79:    */         } else {
/*  80: 80 */           p_147224_1_.serverMOTD = "";
/*  81:    */         }
/*  82: 83 */         if (var2.func_151322_c() != null)
/*  83:    */         {
/*  84: 85 */           p_147224_1_.gameVersion = var2.func_151322_c().func_151303_a();
/*  85: 86 */           p_147224_1_.field_82821_f = var2.func_151322_c().func_151304_b();
/*  86:    */         }
/*  87:    */         else
/*  88:    */         {
/*  89: 90 */           p_147224_1_.gameVersion = "Old";
/*  90: 91 */           p_147224_1_.field_82821_f = 0;
/*  91:    */         }
/*  92: 94 */         if (var2.func_151318_b() != null)
/*  93:    */         {
/*  94: 96 */           p_147224_1_.populationInfo = (EnumChatFormatting.GRAY + var2.func_151318_b().func_151333_b() + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var2.func_151318_b().func_151332_a());
/*  95: 98 */           if (ArrayUtils.isNotEmpty(var2.func_151318_b().func_151331_c()))
/*  96:    */           {
/*  97:100 */             StringBuilder var3x = new StringBuilder();
/*  98:101 */             GameProfile[] var4 = var2.func_151318_b().func_151331_c();
/*  99:102 */             int var5 = var4.length;
/* 100:104 */             for (int var6 = 0; var6 < var5; var6++)
/* 101:    */             {
/* 102:106 */               GameProfile var7 = var4[var6];
/* 103:108 */               if (var3x.length() > 0) {
/* 104:110 */                 var3x.append("\n");
/* 105:    */               }
/* 106:113 */               var3x.append(var7.getName());
/* 107:    */             }
/* 108:116 */             if (var2.func_151318_b().func_151331_c().length < var2.func_151318_b().func_151333_b())
/* 109:    */             {
/* 110:118 */               if (var3x.length() > 0) {
/* 111:120 */                 var3x.append("\n");
/* 112:    */               }
/* 113:123 */               var3x.append("... and ").append(var2.func_151318_b().func_151333_b() - var2.func_151318_b().func_151331_c().length).append(" more ...");
/* 114:    */             }
/* 115:126 */             p_147224_1_.field_147412_i = var3x.toString();
/* 116:    */           }
/* 117:    */         }
/* 118:    */         else
/* 119:    */         {
/* 120:131 */           p_147224_1_.populationInfo = (EnumChatFormatting.DARK_GRAY + "???");
/* 121:    */         }
/* 122:134 */         if (var2.func_151316_d() != null)
/* 123:    */         {
/* 124:136 */           String var8 = var2.func_151316_d();
/* 125:138 */           if (var8.startsWith("data:image/png;base64,")) {
/* 126:140 */             p_147224_1_.func_147407_a(var8.substring("data:image/png;base64,".length()));
/* 127:    */           } else {
/* 128:144 */             OldServerPinger.logger.error("Invalid server icon (unknown format)");
/* 129:    */           }
/* 130:    */         }
/* 131:    */         else
/* 132:    */         {
/* 133:149 */           p_147224_1_.func_147407_a(null);
/* 134:    */         }
/* 135:152 */         var3.scheduleOutboundPacket(new C01PacketPing(Minecraft.getSystemTime()), new GenericFutureListener[0]);
/* 136:153 */         this.field_147403_d = true;
/* 137:    */       }
/* 138:    */       
/* 139:    */       public void handlePong(S01PacketPong p_147398_1_)
/* 140:    */       {
/* 141:157 */         long var2 = p_147398_1_.func_149292_c();
/* 142:158 */         long var4 = Minecraft.getSystemTime();
/* 143:159 */         p_147224_1_.pingToServer = (var4 - var2);
/* 144:160 */         var3.closeChannel(new ChatComponentText("Finished"));
/* 145:    */       }
/* 146:    */       
/* 147:    */       public void onDisconnect(IChatComponent p_147231_1_)
/* 148:    */       {
/* 149:164 */         if (!this.field_147403_d)
/* 150:    */         {
/* 151:166 */           OldServerPinger.logger.error("Can't ping " + p_147224_1_.serverIP + ": " + p_147231_1_.getUnformattedText());
/* 152:167 */           p_147224_1_.serverMOTD = (EnumChatFormatting.DARK_RED + "Can't connect to server.");
/* 153:168 */           p_147224_1_.populationInfo = "";
/* 154:169 */           OldServerPinger.this.func_147225_b(p_147224_1_);
/* 155:    */         }
/* 156:    */       }
/* 157:    */       
/* 158:    */       public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
/* 159:    */       {
/* 160:174 */         if (p_147232_2_ != EnumConnectionState.STATUS) {
/* 161:176 */           throw new UnsupportedOperationException("Unexpected change in protocol to " + p_147232_2_);
/* 162:    */         }
/* 163:    */       }
/* 164:    */       
/* 165:    */       public void onNetworkTick() {}
/* 166:    */     });
/* 167:    */     try
/* 168:    */     {
/* 169:185 */       var3.scheduleOutboundPacket(new C00Handshake(Nodus.theNodus.getMinecraftVersion(), var2.getIP(), var2.getPort(), EnumConnectionState.STATUS), new GenericFutureListener[0]);
/* 170:186 */       var3.scheduleOutboundPacket(new C00PacketServerQuery(), new GenericFutureListener[0]);
/* 171:    */     }
/* 172:    */     catch (Throwable var5)
/* 173:    */     {
/* 174:190 */       logger.error(var5);
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   private void func_147225_b(final ServerData p_147225_1_)
/* 179:    */   {
/* 180:196 */     final ServerAddress var2 = ServerAddress.func_78860_a(p_147225_1_.serverIP);
/* 181:    */     
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:295 */     ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(NetworkManager.eventLoops)).handler(new ChannelInitializer()
/* 280:    */     {
/* 281:    */       private static final String __OBFID = "CL_00000894";
/* 282:    */       
/* 283:    */       protected void initChannel(Channel p_initChannel_1_)
/* 284:    */       {
/* 285:    */         try
/* 286:    */         {
/* 287:204 */           p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
/* 288:    */         }
/* 289:    */         catch (ChannelException localChannelException) {}
/* 290:    */         try
/* 291:    */         {
/* 292:213 */           p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
/* 293:    */         }
/* 294:    */         catch (ChannelException localChannelException1) {}
/* 295:220 */         p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { new SimpleChannelInboundHandler()
/* 296:    */         {
/* 297:    */           private static final String __OBFID = "CL_00000895";
/* 298:    */           
/* 299:    */           public void channelActive(ChannelHandlerContext p_channelActive_1_)
/* 300:    */             throws Exception
/* 301:    */           {
/* 302:225 */             super.channelActive(p_channelActive_1_);
/* 303:226 */             ByteBuf var2x = Unpooled.buffer();
/* 304:227 */             var2x.writeByte(254);
/* 305:228 */             var2x.writeByte(1);
/* 306:229 */             var2x.writeByte(250);
/* 307:230 */             char[] var3 = "MC|PingHost".toCharArray();
/* 308:231 */             var2x.writeShort(var3.length);
/* 309:232 */             char[] var4 = var3;
/* 310:233 */             int var5 = var3.length;
/* 311:237 */             for (int var6 = 0; var6 < var5; var6++)
/* 312:    */             {
/* 313:239 */               char var7 = var4[var6];
/* 314:240 */               var2x.writeChar(var7);
/* 315:    */             }
/* 316:243 */             var2x.writeShort(7 + 2 * this.val$var2.getIP().length());
/* 317:244 */             var2x.writeByte(127);
/* 318:245 */             var3 = this.val$var2.getIP().toCharArray();
/* 319:246 */             var2x.writeShort(var3.length);
/* 320:247 */             var4 = var3;
/* 321:248 */             var5 = var3.length;
/* 322:250 */             for (var6 = 0; var6 < var5; var6++)
/* 323:    */             {
/* 324:252 */               char var7 = var4[var6];
/* 325:253 */               var2x.writeChar(var7);
/* 326:    */             }
/* 327:256 */             var2x.writeInt(this.val$var2.getPort());
/* 328:257 */             p_channelActive_1_.channel().writeAndFlush(var2x).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
/* 329:    */           }
/* 330:    */           
/* 331:    */           protected void channelRead0(ChannelHandlerContext p_147219_1_, ByteBuf p_147219_2_)
/* 332:    */           {
/* 333:261 */             short var3 = p_147219_2_.readUnsignedByte();
/* 334:263 */             if (var3 == 255)
/* 335:    */             {
/* 336:265 */               String var4 = new String(p_147219_2_.readBytes(p_147219_2_.readShort() * 2).array(), Charsets.UTF_16BE);
/* 337:266 */               String[] var5 = (String[])Iterables.toArray(OldServerPinger.field_147230_a.split(var4), String.class);
/* 338:268 */               if ("§1".equals(var5[0]))
/* 339:    */               {
/* 340:270 */                 int var6 = MathHelper.parseIntWithDefault(var5[1], 0);
/* 341:271 */                 String var7 = var5[2];
/* 342:272 */                 String var8 = var5[3];
/* 343:273 */                 int var9 = MathHelper.parseIntWithDefault(var5[4], -1);
/* 344:274 */                 int var10 = MathHelper.parseIntWithDefault(var5[5], -1);
/* 345:275 */                 this.val$p_147225_1_.field_82821_f = -1;
/* 346:276 */                 this.val$p_147225_1_.gameVersion = var7;
/* 347:277 */                 this.val$p_147225_1_.serverMOTD = var8;
/* 348:278 */                 this.val$p_147225_1_.populationInfo = (EnumChatFormatting.GRAY + var9 + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var10);
/* 349:    */               }
/* 350:    */             }
/* 351:282 */             p_147219_1_.close();
/* 352:    */           }
/* 353:    */           
/* 354:    */           public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_)
/* 355:    */           {
/* 356:286 */             p_exceptionCaught_1_.close();
/* 357:    */           }
/* 358:    */           
/* 359:    */           protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Object p_channelRead0_2_)
/* 360:    */           {
/* 361:290 */             channelRead0(p_channelRead0_1_, (ByteBuf)p_channelRead0_2_);
/* 362:    */           }
/* 363:    */         } });
/* 364:    */       }
/* 365:295 */     })).channel(NioSocketChannel.class)).connect(var2.getIP(), var2.getPort());
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void func_147223_a()
/* 369:    */   {
/* 370:300 */     List var1 = this.field_147229_c;
/* 371:302 */     synchronized (this.field_147229_c)
/* 372:    */     {
/* 373:304 */       Iterator var2 = this.field_147229_c.iterator();
/* 374:306 */       while (var2.hasNext())
/* 375:    */       {
/* 376:308 */         NetworkManager var3 = (NetworkManager)var2.next();
/* 377:310 */         if (var3.isChannelOpen())
/* 378:    */         {
/* 379:312 */           var3.processReceivedPackets();
/* 380:    */         }
/* 381:    */         else
/* 382:    */         {
/* 383:316 */           var2.remove();
/* 384:318 */           if (var3.getExitMessage() != null) {
/* 385:320 */             var3.getNetHandler().onDisconnect(var3.getExitMessage());
/* 386:    */           }
/* 387:    */         }
/* 388:    */       }
/* 389:    */     }
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void func_147226_b()
/* 393:    */   {
/* 394:329 */     List var1 = this.field_147229_c;
/* 395:331 */     synchronized (this.field_147229_c)
/* 396:    */     {
/* 397:333 */       Iterator var2 = this.field_147229_c.iterator();
/* 398:335 */       while (var2.hasNext())
/* 399:    */       {
/* 400:337 */         NetworkManager var3 = (NetworkManager)var2.next();
/* 401:339 */         if (var3.isChannelOpen())
/* 402:    */         {
/* 403:341 */           var2.remove();
/* 404:342 */           var3.closeChannel(new ChatComponentText("Cancelled"));
/* 405:    */         }
/* 406:    */       }
/* 407:    */     }
/* 408:    */   }
/* 409:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.network.OldServerPinger
 * JD-Core Version:    0.7.0.1
 */