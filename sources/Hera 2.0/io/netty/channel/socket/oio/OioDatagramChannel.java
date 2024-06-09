/*     */ package io.netty.channel.socket.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.oio.AbstractOioMessageChannel;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.channel.socket.DefaultDatagramChannelConfig;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OioDatagramChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements DatagramChannel
/*     */ {
/*  60 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioDatagramChannel.class);
/*     */   
/*  62 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
/*  63 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */ 
/*     */   
/*     */   private final MulticastSocket socket;
/*     */ 
/*     */   
/*     */   private final DatagramChannelConfig config;
/*     */ 
/*     */   
/*  72 */   private final DatagramPacket tmpPacket = new DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);
/*     */   
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */   private static MulticastSocket newSocket() {
/*     */     try {
/*  78 */       return new MulticastSocket(null);
/*  79 */     } catch (Exception e) {
/*  80 */       throw new ChannelException("failed to create a new socket", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioDatagramChannel() {
/*  88 */     this(newSocket());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioDatagramChannel(MulticastSocket socket) {
/*  97 */     super(null);
/*     */     
/*  99 */     boolean success = false;
/*     */     try {
/* 101 */       socket.setSoTimeout(1000);
/* 102 */       socket.setBroadcast(false);
/* 103 */       success = true;
/* 104 */     } catch (SocketException e) {
/* 105 */       throw new ChannelException("Failed to configure the datagram socket timeout.", e);
/*     */     } finally {
/*     */       
/* 108 */       if (!success) {
/* 109 */         socket.close();
/*     */       }
/*     */     } 
/*     */     
/* 113 */     this.socket = socket;
/* 114 */     this.config = (DatagramChannelConfig)new DefaultDatagramChannelConfig(this, socket);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 119 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig config() {
/* 124 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 129 */     return !this.socket.isClosed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 135 */     return (isOpen() && ((((Boolean)this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.socket.isBound()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 142 */     return this.socket.isConnected();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 147 */     return this.socket.getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 152 */     return this.socket.getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 157 */     this.socket.bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 162 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 167 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 173 */     if (localAddress != null) {
/* 174 */       this.socket.bind(localAddress);
/*     */     }
/*     */     
/* 177 */     boolean success = false;
/*     */     try {
/* 179 */       this.socket.connect(remoteAddress);
/* 180 */       success = true;
/*     */     } finally {
/* 182 */       if (!success) {
/*     */         try {
/* 184 */           this.socket.close();
/* 185 */         } catch (Throwable t) {
/* 186 */           logger.warn("Failed to close a socket.", t);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 194 */     this.socket.disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 199 */     this.socket.close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 204 */     DatagramChannelConfig config = config();
/* 205 */     RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 206 */     if (allocHandle == null) {
/* 207 */       this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
/*     */     }
/*     */     
/* 210 */     ByteBuf data = config.getAllocator().heapBuffer(allocHandle.guess());
/* 211 */     boolean free = true;
/*     */     try {
/* 213 */       this.tmpPacket.setData(data.array(), data.arrayOffset(), data.capacity());
/* 214 */       this.socket.receive(this.tmpPacket);
/*     */       
/* 216 */       InetSocketAddress remoteAddr = (InetSocketAddress)this.tmpPacket.getSocketAddress();
/*     */       
/* 218 */       int readBytes = this.tmpPacket.getLength();
/* 219 */       allocHandle.record(readBytes);
/* 220 */       buf.add(new DatagramPacket(data.writerIndex(readBytes), localAddress(), remoteAddr));
/* 221 */       free = false;
/* 222 */       return 1;
/* 223 */     } catch (SocketTimeoutException e) {
/*     */       
/* 225 */       return 0;
/* 226 */     } catch (SocketException e) {
/* 227 */       if (!e.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
/* 228 */         throw e;
/*     */       }
/* 230 */       return -1;
/* 231 */     } catch (Throwable cause) {
/* 232 */       PlatformDependent.throwException(cause);
/* 233 */       return -1;
/*     */     } finally {
/* 235 */       if (free)
/* 236 */         data.release(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     while (true) {
/*     */       ByteBuf data;
/*     */       SocketAddress remoteAddress;
/* 244 */       Object o = in.current();
/* 245 */       if (o == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 251 */       if (o instanceof AddressedEnvelope) {
/*     */         
/* 253 */         AddressedEnvelope<ByteBuf, SocketAddress> envelope = (AddressedEnvelope<ByteBuf, SocketAddress>)o;
/* 254 */         remoteAddress = envelope.recipient();
/* 255 */         data = (ByteBuf)envelope.content();
/*     */       } else {
/* 257 */         data = (ByteBuf)o;
/* 258 */         remoteAddress = null;
/*     */       } 
/*     */       
/* 261 */       int length = data.readableBytes();
/* 262 */       if (remoteAddress != null) {
/* 263 */         this.tmpPacket.setSocketAddress(remoteAddress);
/*     */       }
/* 265 */       if (data.hasArray()) {
/* 266 */         this.tmpPacket.setData(data.array(), data.arrayOffset() + data.readerIndex(), length);
/*     */       } else {
/* 268 */         byte[] tmp = new byte[length];
/* 269 */         data.getBytes(data.readerIndex(), tmp);
/* 270 */         this.tmpPacket.setData(tmp);
/*     */       } 
/*     */       try {
/* 273 */         this.socket.send(this.tmpPacket);
/* 274 */         in.remove();
/* 275 */       } catch (IOException e) {
/*     */ 
/*     */ 
/*     */         
/* 279 */         in.remove(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 286 */     if (msg instanceof DatagramPacket || msg instanceof ByteBuf) {
/* 287 */       return msg;
/*     */     }
/*     */     
/* 290 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 292 */       AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 293 */       if (e.content() instanceof ByteBuf) {
/* 294 */         return msg;
/*     */       }
/*     */     } 
/*     */     
/* 298 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress) {
/* 304 */     return joinGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
/* 309 */     ensureBound();
/*     */     try {
/* 311 */       this.socket.joinGroup(multicastAddress);
/* 312 */       promise.setSuccess();
/* 313 */     } catch (IOException e) {
/* 314 */       promise.setFailure(e);
/*     */     } 
/* 316 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 321 */     return joinGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 328 */     ensureBound();
/*     */     try {
/* 330 */       this.socket.joinGroup(multicastAddress, networkInterface);
/* 331 */       promise.setSuccess();
/* 332 */     } catch (IOException e) {
/* 333 */       promise.setFailure(e);
/*     */     } 
/* 335 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 341 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 348 */     promise.setFailure(new UnsupportedOperationException());
/* 349 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void ensureBound() {
/* 353 */     if (!isActive()) {
/* 354 */       throw new IllegalStateException(DatagramChannel.class.getName() + " must be bound to join a group.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
/* 362 */     return leaveGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 368 */       this.socket.leaveGroup(multicastAddress);
/* 369 */       promise.setSuccess();
/* 370 */     } catch (IOException e) {
/* 371 */       promise.setFailure(e);
/*     */     } 
/* 373 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 379 */     return leaveGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/*     */     try {
/* 387 */       this.socket.leaveGroup(multicastAddress, networkInterface);
/* 388 */       promise.setSuccess();
/* 389 */     } catch (IOException e) {
/* 390 */       promise.setFailure(e);
/*     */     } 
/* 392 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 398 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 405 */     promise.setFailure(new UnsupportedOperationException());
/* 406 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
/* 412 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
/* 419 */     promise.setFailure(new UnsupportedOperationException());
/* 420 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
/* 426 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
/* 432 */     promise.setFailure(new UnsupportedOperationException());
/* 433 */     return (ChannelFuture)promise;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\socket\oio\OioDatagramChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */