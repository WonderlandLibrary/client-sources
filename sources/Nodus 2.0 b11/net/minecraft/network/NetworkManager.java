/*   1:    */ package net.minecraft.network;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Queues;
/*   4:    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*   5:    */ import io.netty.bootstrap.Bootstrap;
/*   6:    */ import io.netty.channel.Channel;
/*   7:    */ import io.netty.channel.ChannelConfig;
/*   8:    */ import io.netty.channel.ChannelException;
/*   9:    */ import io.netty.channel.ChannelFuture;
/*  10:    */ import io.netty.channel.ChannelFutureListener;
/*  11:    */ import io.netty.channel.ChannelHandlerContext;
/*  12:    */ import io.netty.channel.ChannelInitializer;
/*  13:    */ import io.netty.channel.ChannelOption;
/*  14:    */ import io.netty.channel.ChannelPipeline;
/*  15:    */ import io.netty.channel.EventLoop;
/*  16:    */ import io.netty.channel.SimpleChannelInboundHandler;
/*  17:    */ import io.netty.channel.local.LocalChannel;
/*  18:    */ import io.netty.channel.local.LocalServerChannel;
/*  19:    */ import io.netty.channel.nio.NioEventLoopGroup;
/*  20:    */ import io.netty.channel.socket.nio.NioSocketChannel;
/*  21:    */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*  22:    */ import io.netty.util.Attribute;
/*  23:    */ import io.netty.util.AttributeKey;
/*  24:    */ import io.netty.util.concurrent.GenericFutureListener;
/*  25:    */ import java.net.InetAddress;
/*  26:    */ import java.net.SocketAddress;
/*  27:    */ import java.util.Queue;
/*  28:    */ import javax.crypto.SecretKey;
/*  29:    */ import me.connorm.Nodus.event.packet.EventPacketSend;
/*  30:    */ import me.connorm.lib.EventManager;
/*  31:    */ import net.minecraft.util.ChatComponentTranslation;
/*  32:    */ import net.minecraft.util.CryptManager;
/*  33:    */ import net.minecraft.util.IChatComponent;
/*  34:    */ import net.minecraft.util.MessageDeserializer;
/*  35:    */ import net.minecraft.util.MessageDeserializer2;
/*  36:    */ import net.minecraft.util.MessageSerializer;
/*  37:    */ import net.minecraft.util.MessageSerializer2;
/*  38:    */ import org.apache.commons.lang3.Validate;
/*  39:    */ import org.apache.logging.log4j.LogManager;
/*  40:    */ import org.apache.logging.log4j.Logger;
/*  41:    */ import org.apache.logging.log4j.Marker;
/*  42:    */ import org.apache.logging.log4j.MarkerManager;
/*  43:    */ 
/*  44:    */ public class NetworkManager
/*  45:    */   extends SimpleChannelInboundHandler
/*  46:    */ {
/*  47: 46 */   private static final Logger logger = ;
/*  48: 47 */   public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
/*  49: 48 */   public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
/*  50: 49 */   public static final AttributeKey attrKeyConnectionState = new AttributeKey("protocol");
/*  51: 50 */   public static final AttributeKey attrKeyReceivable = new AttributeKey("receivable_packets");
/*  52: 51 */   public static final AttributeKey attrKeySendable = new AttributeKey("sendable_packets");
/*  53: 52 */   public static final NioEventLoopGroup eventLoops = new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
/*  54:    */   private final boolean isClientSide;
/*  55: 62 */   private final Queue receivedPacketsQueue = Queues.newConcurrentLinkedQueue();
/*  56: 65 */   private final Queue outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
/*  57:    */   private Channel channel;
/*  58:    */   private SocketAddress socketAddress;
/*  59:    */   private INetHandler netHandler;
/*  60:    */   private EnumConnectionState connectionState;
/*  61:    */   private IChatComponent terminationReason;
/*  62:    */   private static final String __OBFID = "CL_00001240";
/*  63:    */   
/*  64:    */   public NetworkManager(boolean p_i45147_1_)
/*  65:    */   {
/*  66: 87 */     this.isClientSide = p_i45147_1_;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void channelActive(ChannelHandlerContext p_channelActive_1_)
/*  70:    */     throws Exception
/*  71:    */   {
/*  72: 92 */     super.channelActive(p_channelActive_1_);
/*  73: 93 */     this.channel = p_channelActive_1_.channel();
/*  74: 94 */     this.socketAddress = this.channel.remoteAddress();
/*  75: 95 */     setConnectionState(EnumConnectionState.HANDSHAKING);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setConnectionState(EnumConnectionState p_150723_1_)
/*  79:    */   {
/*  80:103 */     this.connectionState = ((EnumConnectionState)this.channel.attr(attrKeyConnectionState).getAndSet(p_150723_1_));
/*  81:104 */     this.channel.attr(attrKeyReceivable).set(p_150723_1_.func_150757_a(this.isClientSide));
/*  82:105 */     this.channel.attr(attrKeySendable).set(p_150723_1_.func_150754_b(this.isClientSide));
/*  83:106 */     this.channel.config().setAutoRead(true);
/*  84:107 */     logger.debug("Enabled auto read");
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void channelInactive(ChannelHandlerContext p_channelInactive_1_)
/*  88:    */   {
/*  89:112 */     closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_)
/*  93:    */   {
/*  94:117 */     closeChannel(new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + p_exceptionCaught_2_ }));
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected void channelRead0(ChannelHandlerContext p_150728_1_, Packet p_150728_2_)
/*  98:    */   {
/*  99:122 */     if (this.channel.isOpen()) {
/* 100:124 */       if (p_150728_2_.hasPriority()) {
/* 101:126 */         p_150728_2_.processPacket(this.netHandler);
/* 102:    */       } else {
/* 103:130 */         this.receivedPacketsQueue.add(p_150728_2_);
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setNetHandler(INetHandler p_150719_1_)
/* 109:    */   {
/* 110:141 */     Validate.notNull(p_150719_1_, "packetListener", new Object[0]);
/* 111:142 */     logger.debug("Set listener of {} to {}", new Object[] { this, p_150719_1_ });
/* 112:143 */     this.netHandler = p_150719_1_;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void scheduleOutboundPacket(Packet p_150725_1_, GenericFutureListener... p_150725_2_)
/* 116:    */   {
/* 117:153 */     EventPacketSend theEvent = new EventPacketSend(p_150725_1_);
/* 118:154 */     EventManager.call(theEvent);
/* 119:155 */     if (theEvent.isCancelled()) {
/* 120:156 */       return;
/* 121:    */     }
/* 122:158 */     if ((this.channel != null) && (this.channel.isOpen()))
/* 123:    */     {
/* 124:160 */       flushOutboundQueue();
/* 125:161 */       dispatchPacket(p_150725_1_, p_150725_2_);
/* 126:    */     }
/* 127:    */     else
/* 128:    */     {
/* 129:165 */       this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(p_150725_1_, p_150725_2_));
/* 130:    */     }
/* 131:169 */     for (Packet addedPacket : theEvent.getPackets()) {
/* 132:171 */       if ((this.channel != null) && (this.channel.isOpen()))
/* 133:    */       {
/* 134:173 */         flushOutboundQueue();
/* 135:174 */         dispatchPacket(addedPacket, p_150725_2_);
/* 136:    */       }
/* 137:    */       else
/* 138:    */       {
/* 139:178 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(addedPacket, p_150725_2_));
/* 140:    */       }
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   private void dispatchPacket(final Packet p_150732_1_, final GenericFutureListener[] p_150732_2_)
/* 145:    */   {
/* 146:189 */     final EnumConnectionState var3 = EnumConnectionState.func_150752_a(p_150732_1_);
/* 147:190 */     final EnumConnectionState var4 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
/* 148:192 */     if (var4 != var3)
/* 149:    */     {
/* 150:194 */       logger.debug("Disabled auto read");
/* 151:195 */       this.channel.config().setAutoRead(false);
/* 152:    */     }
/* 153:198 */     if (this.channel.eventLoop().inEventLoop())
/* 154:    */     {
/* 155:200 */       if (var3 != var4) {
/* 156:202 */         setConnectionState(var3);
/* 157:    */       }
/* 158:205 */       this.channel.writeAndFlush(p_150732_1_).addListeners(p_150732_2_).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/* 159:    */     }
/* 160:    */     else
/* 161:    */     {
/* 162:209 */       this.channel.eventLoop().execute(new Runnable()
/* 163:    */       {
/* 164:    */         private static final String __OBFID = "CL_00001241";
/* 165:    */         
/* 166:    */         public void run()
/* 167:    */         {
/* 168:214 */           if (var3 != var4) {
/* 169:216 */             NetworkManager.this.setConnectionState(var3);
/* 170:    */           }
/* 171:219 */           NetworkManager.this.channel.writeAndFlush(p_150732_1_).addListeners(p_150732_2_).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/* 172:    */         }
/* 173:    */       });
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   private void flushOutboundQueue()
/* 178:    */   {
/* 179:230 */     if ((this.channel != null) && (this.channel.isOpen())) {
/* 180:232 */       while (!this.outboundPacketsQueue.isEmpty())
/* 181:    */       {
/* 182:234 */         InboundHandlerTuplePacketListener var1 = (InboundHandlerTuplePacketListener)this.outboundPacketsQueue.poll();
/* 183:235 */         dispatchPacket(var1.field_150774_a, var1.field_150773_b);
/* 184:    */       }
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void processReceivedPackets()
/* 189:    */   {
/* 190:245 */     flushOutboundQueue();
/* 191:246 */     EnumConnectionState var1 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
/* 192:248 */     if (this.connectionState != var1)
/* 193:    */     {
/* 194:250 */       if (this.connectionState != null) {
/* 195:252 */         this.netHandler.onConnectionStateTransition(this.connectionState, var1);
/* 196:    */       }
/* 197:255 */       this.connectionState = var1;
/* 198:    */     }
/* 199:258 */     if (this.netHandler != null)
/* 200:    */     {
/* 201:260 */       for (int var2 = 1000; (!this.receivedPacketsQueue.isEmpty()) && (var2 >= 0); var2--)
/* 202:    */       {
/* 203:262 */         Packet var3 = (Packet)this.receivedPacketsQueue.poll();
/* 204:263 */         var3.processPacket(this.netHandler);
/* 205:    */       }
/* 206:266 */       this.netHandler.onNetworkTick();
/* 207:    */     }
/* 208:269 */     this.channel.flush();
/* 209:    */   }
/* 210:    */   
/* 211:    */   public SocketAddress getSocketAddress()
/* 212:    */   {
/* 213:277 */     return this.socketAddress;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void closeChannel(IChatComponent p_150718_1_)
/* 217:    */   {
/* 218:285 */     if (this.channel.isOpen())
/* 219:    */     {
/* 220:287 */       this.channel.close();
/* 221:288 */       this.terminationReason = p_150718_1_;
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean isLocalChannel()
/* 226:    */   {
/* 227:298 */     return ((this.channel instanceof LocalChannel)) || ((this.channel instanceof LocalServerChannel));
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static NetworkManager provideLanClient(InetAddress p_150726_0_, int p_150726_1_)
/* 231:    */   {
/* 232:307 */     NetworkManager var2 = new NetworkManager(true);
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
/* 258:333 */     ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(eventLoops)).handler(new ChannelInitializer()
/* 259:    */     {
/* 260:    */       private static final String __OBFID = "CL_00001242";
/* 261:    */       
/* 262:    */       protected void initChannel(Channel p_initChannel_1_)
/* 263:    */       {
/* 264:    */         try
/* 265:    */         {
/* 266:315 */           p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
/* 267:    */         }
/* 268:    */         catch (ChannelException localChannelException) {}
/* 269:    */         try
/* 270:    */         {
/* 271:324 */           p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
/* 272:    */         }
/* 273:    */         catch (ChannelException localChannelException1) {}
/* 274:331 */         p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer()).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer()).addLast("packet_handler", NetworkManager.this);
/* 275:    */       }
/* 276:333 */     })).channel(NioSocketChannel.class)).connect(p_150726_0_, p_150726_1_).syncUninterruptibly();
/* 277:334 */     return var2;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public static NetworkManager provideLocalClient(SocketAddress p_150722_0_)
/* 281:    */   {
/* 282:343 */     NetworkManager var1 = new NetworkManager(true);
/* 283:    */     
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:351 */     ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(eventLoops)).handler(new ChannelInitializer()
/* 291:    */     {
/* 292:    */       private static final String __OBFID = "CL_00001243";
/* 293:    */       
/* 294:    */       protected void initChannel(Channel p_initChannel_1_)
/* 295:    */       {
/* 296:349 */         p_initChannel_1_.pipeline().addLast("packet_handler", NetworkManager.this);
/* 297:    */       }
/* 298:351 */     })).channel(LocalChannel.class)).connect(p_150722_0_).syncUninterruptibly();
/* 299:352 */     return var1;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void enableEncryption(SecretKey p_150727_1_)
/* 303:    */   {
/* 304:360 */     this.channel.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.func_151229_a(2, p_150727_1_)));
/* 305:361 */     this.channel.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.func_151229_a(1, p_150727_1_)));
/* 306:    */   }
/* 307:    */   
/* 308:    */   public boolean isChannelOpen()
/* 309:    */   {
/* 310:369 */     return (this.channel != null) && (this.channel.isOpen());
/* 311:    */   }
/* 312:    */   
/* 313:    */   public INetHandler getNetHandler()
/* 314:    */   {
/* 315:377 */     return this.netHandler;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public IChatComponent getExitMessage()
/* 319:    */   {
/* 320:385 */     return this.terminationReason;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void disableAutoRead()
/* 324:    */   {
/* 325:393 */     this.channel.config().setAutoRead(false);
/* 326:    */   }
/* 327:    */   
/* 328:    */   protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Object p_channelRead0_2_)
/* 329:    */   {
/* 330:398 */     channelRead0(p_channelRead0_1_, (Packet)p_channelRead0_2_);
/* 331:    */   }
/* 332:    */   
/* 333:    */   static class InboundHandlerTuplePacketListener
/* 334:    */   {
/* 335:    */     private final Packet field_150774_a;
/* 336:    */     private final GenericFutureListener[] field_150773_b;
/* 337:    */     private static final String __OBFID = "CL_00001244";
/* 338:    */     
/* 339:    */     public InboundHandlerTuplePacketListener(Packet p_i45146_1_, GenericFutureListener... p_i45146_2_)
/* 340:    */     {
/* 341:409 */       this.field_150774_a = p_i45146_1_;
/* 342:410 */       this.field_150773_b = p_i45146_2_;
/* 343:    */     }
/* 344:    */   }
/* 345:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.NetworkManager
 * JD-Core Version:    0.7.0.1
 */