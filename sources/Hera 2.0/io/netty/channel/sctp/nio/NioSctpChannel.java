/*     */ package io.netty.channel.sctp.nio;
/*     */ 
/*     */ import com.sun.nio.sctp.Association;
/*     */ import com.sun.nio.sctp.MessageInfo;
/*     */ import com.sun.nio.sctp.NotificationHandler;
/*     */ import com.sun.nio.sctp.SctpChannel;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.sctp.DefaultSctpChannelConfig;
/*     */ import io.netty.channel.sctp.SctpChannel;
/*     */ import io.netty.channel.sctp.SctpChannelConfig;
/*     */ import io.netty.channel.sctp.SctpMessage;
/*     */ import io.netty.channel.sctp.SctpNotificationHandler;
/*     */ import io.netty.channel.sctp.SctpServerChannel;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NioSctpChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements SctpChannel
/*     */ {
/*  63 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*  65 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSctpChannel.class);
/*     */   
/*     */   private final SctpChannelConfig config;
/*     */   
/*     */   private final NotificationHandler<?> notificationHandler;
/*     */   
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */   private static SctpChannel newSctpChannel() {
/*     */     try {
/*  75 */       return SctpChannel.open();
/*  76 */     } catch (IOException e) {
/*  77 */       throw new ChannelException("Failed to open a sctp channel.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSctpChannel() {
/*  85 */     this(newSctpChannel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSctpChannel(SctpChannel sctpChannel) {
/*  92 */     this((Channel)null, sctpChannel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSctpChannel(Channel parent, SctpChannel sctpChannel) {
/* 103 */     super(parent, sctpChannel, 1);
/*     */     try {
/* 105 */       sctpChannel.configureBlocking(false);
/* 106 */       this.config = (SctpChannelConfig)new NioSctpChannelConfig(this, sctpChannel);
/* 107 */       this.notificationHandler = (NotificationHandler<?>)new SctpNotificationHandler(this);
/* 108 */     } catch (IOException e) {
/*     */       try {
/* 110 */         sctpChannel.close();
/* 111 */       } catch (IOException e2) {
/* 112 */         if (logger.isWarnEnabled()) {
/* 113 */           logger.warn("Failed to close a partially initialized sctp channel.", e2);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 118 */       throw new ChannelException("Failed to enter non-blocking mode.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 124 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 129 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannel parent() {
/* 134 */     return (SctpServerChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 139 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public Association association() {
/*     */     try {
/* 145 */       return javaChannel().association();
/* 146 */     } catch (IOException ignored) {
/* 147 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<InetSocketAddress> allLocalAddresses() {
/*     */     try {
/* 154 */       Set<SocketAddress> allLocalAddresses = javaChannel().getAllLocalAddresses();
/* 155 */       Set<InetSocketAddress> addresses = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
/* 156 */       for (SocketAddress socketAddress : allLocalAddresses) {
/* 157 */         addresses.add((InetSocketAddress)socketAddress);
/*     */       }
/* 159 */       return addresses;
/* 160 */     } catch (Throwable ignored) {
/* 161 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig config() {
/* 167 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<InetSocketAddress> allRemoteAddresses() {
/*     */     try {
/* 173 */       Set<SocketAddress> allLocalAddresses = javaChannel().getRemoteAddresses();
/* 174 */       Set<InetSocketAddress> addresses = new HashSet<InetSocketAddress>(allLocalAddresses.size());
/* 175 */       for (SocketAddress socketAddress : allLocalAddresses) {
/* 176 */         addresses.add((InetSocketAddress)socketAddress);
/*     */       }
/* 178 */       return addresses;
/* 179 */     } catch (Throwable ignored) {
/* 180 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SctpChannel javaChannel() {
/* 186 */     return (SctpChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 191 */     SctpChannel ch = javaChannel();
/* 192 */     return (ch.isOpen() && association() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*     */     try {
/* 198 */       Iterator<SocketAddress> i = javaChannel().getAllLocalAddresses().iterator();
/* 199 */       if (i.hasNext()) {
/* 200 */         return i.next();
/*     */       }
/* 202 */     } catch (IOException e) {}
/*     */ 
/*     */     
/* 205 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/*     */     try {
/* 211 */       Iterator<SocketAddress> i = javaChannel().getRemoteAddresses().iterator();
/* 212 */       if (i.hasNext()) {
/* 213 */         return i.next();
/*     */       }
/* 215 */     } catch (IOException e) {}
/*     */ 
/*     */     
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 223 */     javaChannel().bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 228 */     if (localAddress != null) {
/* 229 */       javaChannel().bind(localAddress);
/*     */     }
/*     */     
/* 232 */     boolean success = false;
/*     */     try {
/* 234 */       boolean connected = javaChannel().connect(remoteAddress);
/* 235 */       if (!connected) {
/* 236 */         selectionKey().interestOps(8);
/*     */       }
/* 238 */       success = true;
/* 239 */       return connected;
/*     */     } finally {
/* 241 */       if (!success) {
/* 242 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 249 */     if (!javaChannel().finishConnect()) {
/* 250 */       throw new Error();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 256 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 261 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 266 */     SctpChannel ch = javaChannel();
/*     */     
/* 268 */     RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 269 */     if (allocHandle == null) {
/* 270 */       this.allocHandle = allocHandle = config().getRecvByteBufAllocator().newHandle();
/*     */     }
/* 272 */     ByteBuf buffer = allocHandle.allocate(config().getAllocator());
/* 273 */     boolean free = true;
/*     */     try {
/* 275 */       ByteBuffer data = buffer.internalNioBuffer(buffer.writerIndex(), buffer.writableBytes());
/* 276 */       int pos = data.position();
/*     */       
/* 278 */       MessageInfo messageInfo = ch.receive(data, (Object)null, this.notificationHandler);
/* 279 */       if (messageInfo == null) {
/* 280 */         return 0;
/*     */       }
/* 282 */       buf.add(new SctpMessage(messageInfo, buffer.writerIndex(buffer.writerIndex() + data.position() - pos)));
/* 283 */       free = false;
/* 284 */       return 1;
/* 285 */     } catch (Throwable cause) {
/* 286 */       PlatformDependent.throwException(cause);
/* 287 */       return -1;
/*     */     } finally {
/* 289 */       int bytesRead = buffer.readableBytes();
/* 290 */       allocHandle.record(bytesRead);
/* 291 */       if (free) {
/* 292 */         buffer.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/*     */     ByteBuffer nioData;
/* 299 */     SctpMessage packet = (SctpMessage)msg;
/* 300 */     ByteBuf data = packet.content();
/* 301 */     int dataLen = data.readableBytes();
/* 302 */     if (dataLen == 0) {
/* 303 */       return true;
/*     */     }
/*     */     
/* 306 */     ByteBufAllocator alloc = alloc();
/* 307 */     boolean needsCopy = (data.nioBufferCount() != 1);
/* 308 */     if (!needsCopy && 
/* 309 */       !data.isDirect() && alloc.isDirectBufferPooled()) {
/* 310 */       needsCopy = true;
/*     */     }
/*     */ 
/*     */     
/* 314 */     if (!needsCopy) {
/* 315 */       nioData = data.nioBuffer();
/*     */     } else {
/* 317 */       data = alloc.directBuffer(dataLen).writeBytes(data);
/* 318 */       nioData = data.nioBuffer();
/*     */     } 
/* 320 */     MessageInfo mi = MessageInfo.createOutgoing(association(), null, packet.streamIdentifier());
/* 321 */     mi.payloadProtocolID(packet.protocolIdentifier());
/* 322 */     mi.streamNumber(packet.streamIdentifier());
/*     */     
/* 324 */     int writtenBytes = javaChannel().send(nioData, mi);
/* 325 */     return (writtenBytes > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Object filterOutboundMessage(Object msg) throws Exception {
/* 330 */     if (msg instanceof SctpMessage) {
/* 331 */       SctpMessage m = (SctpMessage)msg;
/* 332 */       ByteBuf buf = m.content();
/* 333 */       if (buf.isDirect() && buf.nioBufferCount() == 1) {
/* 334 */         return m;
/*     */       }
/*     */       
/* 337 */       return new SctpMessage(m.protocolIdentifier(), m.streamIdentifier(), newDirectBuffer((ReferenceCounted)m, buf));
/*     */     } 
/*     */     
/* 340 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + " (expected: " + StringUtil.simpleClassName(SctpMessage.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(InetAddress localAddress) {
/* 347 */     return bindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 352 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 354 */         javaChannel().bindAddress(localAddress);
/* 355 */         promise.setSuccess();
/* 356 */       } catch (Throwable t) {
/* 357 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 360 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 363 */               NioSctpChannel.this.bindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 367 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(InetAddress localAddress) {
/* 372 */     return unbindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 377 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 379 */         javaChannel().unbindAddress(localAddress);
/* 380 */         promise.setSuccess();
/* 381 */       } catch (Throwable t) {
/* 382 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 385 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 388 */               NioSctpChannel.this.unbindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 392 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private final class NioSctpChannelConfig extends DefaultSctpChannelConfig {
/*     */     private NioSctpChannelConfig(NioSctpChannel channel, SctpChannel javaChannel) {
/* 397 */       super(channel, javaChannel);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void autoReadCleared() {
/* 402 */       NioSctpChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\sctp\nio\NioSctpChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */