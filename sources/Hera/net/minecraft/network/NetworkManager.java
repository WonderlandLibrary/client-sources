/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollSocketChannel;
/*     */ import io.netty.channel.local.LocalChannel;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.LazyLoadBase;
/*     */ import net.minecraft.util.MessageDeserializer;
/*     */ import net.minecraft.util.MessageDeserializer2;
/*     */ import net.minecraft.util.MessageSerializer;
/*     */ import net.minecraft.util.MessageSerializer2;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkManager
/*     */   extends SimpleChannelInboundHandler<Packet>
/*     */ {
/*  54 */   private static final Logger logger = LogManager.getLogger();
/*  55 */   public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
/*  56 */   public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
/*  57 */   public static final AttributeKey<EnumConnectionState> attrKeyConnectionState = AttributeKey.valueOf("protocol");
/*  58 */   public static final LazyLoadBase<NioEventLoopGroup> CLIENT_NIO_EVENTLOOP = new LazyLoadBase<NioEventLoopGroup>()
/*     */     {
/*     */       protected NioEventLoopGroup load()
/*     */       {
/*  62 */         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  65 */   public static final LazyLoadBase<EpollEventLoopGroup> field_181125_e = new LazyLoadBase<EpollEventLoopGroup>()
/*     */     {
/*     */       protected EpollEventLoopGroup load()
/*     */       {
/*  69 */         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  72 */   public static final LazyLoadBase<LocalEventLoopGroup> CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>()
/*     */     {
/*     */       protected LocalEventLoopGroup load()
/*     */       {
/*  76 */         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*     */   private final EnumPacketDirection direction;
/*  80 */   private final Queue<InboundHandlerTuplePacketListener> outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
/*  81 */   private final ReentrantReadWriteLock field_181680_j = new ReentrantReadWriteLock();
/*     */ 
/*     */   
/*     */   private Channel channel;
/*     */ 
/*     */   
/*     */   private SocketAddress socketAddress;
/*     */   
/*     */   private INetHandler packetListener;
/*     */   
/*     */   private IChatComponent terminationReason;
/*     */   
/*     */   private boolean isEncrypted;
/*     */   
/*     */   private boolean disconnected;
/*     */ 
/*     */   
/*     */   public NetworkManager(EnumPacketDirection packetDirection) {
/*  99 */     this.direction = packetDirection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {
/* 104 */     super.channelActive(p_channelActive_1_);
/* 105 */     this.channel = p_channelActive_1_.channel();
/* 106 */     this.socketAddress = this.channel.remoteAddress();
/*     */ 
/*     */     
/*     */     try {
/* 110 */       setConnectionState(EnumConnectionState.HANDSHAKING);
/*     */     }
/* 112 */     catch (Throwable throwable) {
/*     */       
/* 114 */       logger.fatal(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionState(EnumConnectionState newState) {
/* 123 */     this.channel.attr(attrKeyConnectionState).set(newState);
/* 124 */     this.channel.config().setAutoRead(true);
/* 125 */     logger.debug("Enabled auto read");
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext p_channelInactive_1_) throws Exception {
/* 130 */     closeChannel((IChatComponent)new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
/*     */     ChatComponentTranslation chatcomponenttranslation;
/* 137 */     if (p_exceptionCaught_2_ instanceof io.netty.handler.timeout.TimeoutException) {
/*     */       
/* 139 */       chatcomponenttranslation = new ChatComponentTranslation("disconnect.timeout", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 143 */       chatcomponenttranslation = new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + p_exceptionCaught_2_ });
/*     */     } 
/*     */     
/* 146 */     closeChannel((IChatComponent)chatcomponenttranslation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet<INetHandler> p_channelRead0_2_) throws Exception {
/* 151 */     if (this.channel.isOpen()) {
/*     */       
/*     */       try {
/*     */         
/* 155 */         p_channelRead0_2_.processPacket(this.packetListener);
/*     */       }
/* 157 */       catch (ThreadQuickExitException threadQuickExitException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetHandler(INetHandler handler) {
/* 170 */     Validate.notNull(handler, "packetListener", new Object[0]);
/* 171 */     logger.debug("Set listener of {} to {}", new Object[] { this, handler });
/* 172 */     this.packetListener = handler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPacket(Packet packetIn) {
/* 177 */     if (isChannelOpen()) {
/*     */       
/* 179 */       flushOutboundQueue();
/* 180 */       dispatchPacket(packetIn, null);
/*     */     }
/*     */     else {
/*     */       
/* 184 */       this.field_181680_j.writeLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 188 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, null));
/*     */       }
/*     */       finally {
/*     */         
/* 192 */         this.field_181680_j.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPacket(Packet packetIn, GenericFutureListener<? extends Future<? super Void>> listener, GenericFutureListener... listeners) {
/* 199 */     if (isChannelOpen()) {
/*     */       
/* 201 */       flushOutboundQueue();
/* 202 */       dispatchPacket(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener));
/*     */     }
/*     */     else {
/*     */       
/* 206 */       this.field_181680_j.writeLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 210 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener)));
/*     */       }
/*     */       finally {
/*     */         
/* 214 */         this.field_181680_j.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dispatchPacket(final Packet inPacket, final GenericFutureListener[] futureListeners) {
/* 225 */     final EnumConnectionState enumconnectionstate = EnumConnectionState.getFromPacket(inPacket);
/* 226 */     final EnumConnectionState enumconnectionstate1 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
/*     */     
/* 228 */     if (enumconnectionstate1 != enumconnectionstate) {
/*     */       
/* 230 */       logger.debug("Disabled auto read");
/* 231 */       this.channel.config().setAutoRead(false);
/*     */     } 
/*     */     
/* 234 */     if (this.channel.eventLoop().inEventLoop()) {
/*     */       
/* 236 */       if (enumconnectionstate != enumconnectionstate1)
/*     */       {
/* 238 */         setConnectionState(enumconnectionstate);
/*     */       }
/*     */       
/* 241 */       ChannelFuture channelfuture = this.channel.writeAndFlush(inPacket);
/*     */       
/* 243 */       if (futureListeners != null)
/*     */       {
/* 245 */         channelfuture.addListeners(futureListeners);
/*     */       }
/*     */       
/* 248 */       channelfuture.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */     }
/*     */     else {
/*     */       
/* 252 */       this.channel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 256 */               if (enumconnectionstate != enumconnectionstate1)
/*     */               {
/* 258 */                 NetworkManager.this.setConnectionState(enumconnectionstate);
/*     */               }
/*     */               
/* 261 */               ChannelFuture channelfuture1 = NetworkManager.this.channel.writeAndFlush(inPacket);
/*     */               
/* 263 */               if (futureListeners != null)
/*     */               {
/* 265 */                 channelfuture1.addListeners(futureListeners);
/*     */               }
/*     */               
/* 268 */               channelfuture1.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushOutboundQueue() {
/* 279 */     if (this.channel != null && this.channel.isOpen()) {
/*     */       
/* 281 */       this.field_181680_j.readLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 285 */         while (!this.outboundPacketsQueue.isEmpty())
/*     */         {
/* 287 */           InboundHandlerTuplePacketListener networkmanager$inboundhandlertuplepacketlistener = this.outboundPacketsQueue.poll();
/* 288 */           dispatchPacket(networkmanager$inboundhandlertuplepacketlistener.packet, networkmanager$inboundhandlertuplepacketlistener.futureListeners);
/*     */         }
/*     */       
/*     */       } finally {
/*     */         
/* 293 */         this.field_181680_j.readLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processReceivedPackets() {
/* 303 */     flushOutboundQueue();
/*     */     
/* 305 */     if (this.packetListener instanceof ITickable)
/*     */     {
/* 307 */       ((ITickable)this.packetListener).update();
/*     */     }
/*     */     
/* 310 */     this.channel.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress getRemoteAddress() {
/* 318 */     return this.socketAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeChannel(IChatComponent message) {
/* 326 */     if (this.channel.isOpen()) {
/*     */       
/* 328 */       this.channel.close().awaitUninterruptibly();
/* 329 */       this.terminationReason = message;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalChannel() {
/* 339 */     return !(!(this.channel instanceof LocalChannel) && !(this.channel instanceof io.netty.channel.local.LocalServerChannel));
/*     */   }
/*     */   public static NetworkManager func_181124_a(InetAddress p_181124_0_, int p_181124_1_, boolean p_181124_2_) {
/*     */     Class<NioSocketChannel> clazz;
/*     */     LazyLoadBase<NioEventLoopGroup> lazyLoadBase;
/* 344 */     final NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
/*     */ 
/*     */ 
/*     */     
/* 348 */     if (Epoll.isAvailable() && p_181124_2_) {
/*     */       
/* 350 */       Class<EpollSocketChannel> clazz1 = EpollSocketChannel.class;
/* 351 */       LazyLoadBase<EpollEventLoopGroup> lazyLoadBase1 = field_181125_e;
/*     */     }
/*     */     else {
/*     */       
/* 355 */       clazz = NioSocketChannel.class;
/* 356 */       lazyLoadBase = CLIENT_NIO_EVENTLOOP;
/*     */     } 
/*     */     
/* 359 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)lazyLoadBase.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */           {
/*     */             try {
/* 365 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/*     */             }
/* 367 */             catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 372 */             p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */           }
/* 374 */         })).channel(clazz)).connect(p_181124_0_, p_181124_1_).syncUninterruptibly();
/* 375 */     return networkmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetworkManager provideLocalClient(SocketAddress address) {
/* 384 */     final NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
/* 385 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)CLIENT_LOCAL_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */           {
/* 389 */             p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */           }
/* 391 */         })).channel(LocalChannel.class)).connect(address).syncUninterruptibly();
/* 392 */     return networkmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableEncryption(SecretKey key) {
/* 400 */     this.isEncrypted = true;
/* 401 */     this.channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new NettyEncryptingDecoder(CryptManager.createNetCipherInstance(2, key)));
/* 402 */     this.channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(1, key)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsencrypted() {
/* 407 */     return this.isEncrypted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChannelOpen() {
/* 415 */     return (this.channel != null && this.channel.isOpen());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNoChannel() {
/* 420 */     return (this.channel == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INetHandler getNetHandler() {
/* 428 */     return this.packetListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getExitMessage() {
/* 436 */     return this.terminationReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableAutoRead() {
/* 444 */     this.channel.config().setAutoRead(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompressionTreshold(int treshold) {
/* 449 */     if (treshold >= 0) {
/*     */       
/* 451 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
/*     */         
/* 453 */         ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
/*     */       }
/*     */       else {
/*     */         
/* 457 */         this.channel.pipeline().addBefore("decoder", "decompress", (ChannelHandler)new NettyCompressionDecoder(treshold));
/*     */       } 
/*     */       
/* 460 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder)
/*     */       {
/* 462 */         ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
/*     */       }
/*     */       else
/*     */       {
/* 466 */         this.channel.pipeline().addBefore("encoder", "compress", (ChannelHandler)new NettyCompressionEncoder(treshold));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 471 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder)
/*     */       {
/* 473 */         this.channel.pipeline().remove("decompress");
/*     */       }
/*     */       
/* 476 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder)
/*     */       {
/* 478 */         this.channel.pipeline().remove("compress");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkDisconnected() {
/* 485 */     if (this.channel != null && !this.channel.isOpen())
/*     */     {
/* 487 */       if (!this.disconnected) {
/*     */         
/* 489 */         this.disconnected = true;
/*     */         
/* 491 */         if (getExitMessage() != null)
/*     */         {
/* 493 */           getNetHandler().onDisconnect(getExitMessage());
/*     */         }
/* 495 */         else if (getNetHandler() != null)
/*     */         {
/* 497 */           getNetHandler().onDisconnect((IChatComponent)new ChatComponentText("Disconnected"));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 502 */         logger.warn("handleDisconnection() called twice");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class InboundHandlerTuplePacketListener
/*     */   {
/*     */     private final Packet packet;
/*     */     private final GenericFutureListener<? extends Future<? super Void>>[] futureListeners;
/*     */     
/*     */     public InboundHandlerTuplePacketListener(Packet inPacket, GenericFutureListener... inFutureListeners) {
/* 514 */       this.packet = inPacket;
/* 515 */       this.futureListeners = (GenericFutureListener<? extends Future<? super Void>>[])inFutureListeners;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\NetworkManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */