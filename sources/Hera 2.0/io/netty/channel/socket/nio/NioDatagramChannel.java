/*     */ package io.netty.channel.socket.nio;
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
/*     */ import io.netty.channel.DefaultAddressedEnvelope;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.MembershipKey;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class NioDatagramChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements DatagramChannel
/*     */ {
/*  63 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
/*  64 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*  65 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DatagramChannelConfig config;
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<InetAddress, List<MembershipKey>> memberships;
/*     */ 
/*     */ 
/*     */   
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DatagramChannel newSocket(SelectorProvider provider) {
/*     */     try {
/*  85 */       return provider.openDatagramChannel();
/*  86 */     } catch (IOException e) {
/*  87 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static DatagramChannel newSocket(SelectorProvider provider, InternetProtocolFamily ipFamily) {
/*  92 */     if (ipFamily == null) {
/*  93 */       return newSocket(provider);
/*     */     }
/*     */     
/*  96 */     checkJavaVersion();
/*     */     
/*     */     try {
/*  99 */       return provider.openDatagramChannel(ProtocolFamilyConverter.convert(ipFamily));
/* 100 */     } catch (IOException e) {
/* 101 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkJavaVersion() {
/* 106 */     if (PlatformDependent.javaVersion() < 7) {
/* 107 */       throw new UnsupportedOperationException("Only supported on java 7+.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel() {
/* 115 */     this(newSocket(DEFAULT_SELECTOR_PROVIDER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(SelectorProvider provider) {
/* 123 */     this(newSocket(provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(InternetProtocolFamily ipFamily) {
/* 131 */     this(newSocket(DEFAULT_SELECTOR_PROVIDER, ipFamily));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(SelectorProvider provider, InternetProtocolFamily ipFamily) {
/* 140 */     this(newSocket(provider, ipFamily));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(DatagramChannel socket) {
/* 147 */     super(null, socket, 1);
/* 148 */     this.config = (DatagramChannelConfig)new NioDatagramChannelConfig(this, socket);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 153 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig config() {
/* 158 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 164 */     DatagramChannel ch = javaChannel();
/* 165 */     return (ch.isOpen() && ((((Boolean)this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || ch.socket().isBound()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 172 */     return javaChannel().isConnected();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DatagramChannel javaChannel() {
/* 177 */     return (DatagramChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 182 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 187 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 192 */     javaChannel().socket().bind(localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 198 */     if (localAddress != null) {
/* 199 */       javaChannel().socket().bind(localAddress);
/*     */     }
/*     */     
/* 202 */     boolean success = false;
/*     */     try {
/* 204 */       javaChannel().connect(remoteAddress);
/* 205 */       success = true;
/* 206 */       return true;
/*     */     } finally {
/* 208 */       if (!success) {
/* 209 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 216 */     throw new Error();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 221 */     javaChannel().disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 226 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 231 */     DatagramChannel ch = javaChannel();
/* 232 */     DatagramChannelConfig config = config();
/* 233 */     RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 234 */     if (allocHandle == null) {
/* 235 */       this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
/*     */     }
/* 237 */     ByteBuf data = allocHandle.allocate(config.getAllocator());
/* 238 */     boolean free = true;
/*     */     try {
/* 240 */       ByteBuffer nioData = data.internalNioBuffer(data.writerIndex(), data.writableBytes());
/* 241 */       int pos = nioData.position();
/* 242 */       InetSocketAddress remoteAddress = (InetSocketAddress)ch.receive(nioData);
/* 243 */       if (remoteAddress == null) {
/* 244 */         return 0;
/*     */       }
/*     */       
/* 247 */       int readBytes = nioData.position() - pos;
/* 248 */       data.writerIndex(data.writerIndex() + readBytes);
/* 249 */       allocHandle.record(readBytes);
/*     */       
/* 251 */       buf.add(new DatagramPacket(data, localAddress(), remoteAddress));
/* 252 */       free = false;
/* 253 */       return 1;
/* 254 */     } catch (Throwable cause) {
/* 255 */       PlatformDependent.throwException(cause);
/* 256 */       return -1;
/*     */     } finally {
/* 258 */       if (free) {
/* 259 */         data.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/*     */     SocketAddress remoteAddress;
/*     */     ByteBuf data;
/*     */     int writtenBytes;
/* 268 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 270 */       AddressedEnvelope<ByteBuf, SocketAddress> envelope = (AddressedEnvelope<ByteBuf, SocketAddress>)msg;
/* 271 */       remoteAddress = envelope.recipient();
/* 272 */       data = (ByteBuf)envelope.content();
/*     */     } else {
/* 274 */       data = (ByteBuf)msg;
/* 275 */       remoteAddress = null;
/*     */     } 
/*     */     
/* 278 */     int dataLen = data.readableBytes();
/* 279 */     if (dataLen == 0) {
/* 280 */       return true;
/*     */     }
/*     */     
/* 283 */     ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), dataLen);
/*     */     
/* 285 */     if (remoteAddress != null) {
/* 286 */       writtenBytes = javaChannel().send(nioData, remoteAddress);
/*     */     } else {
/* 288 */       writtenBytes = javaChannel().write(nioData);
/*     */     } 
/* 290 */     return (writtenBytes > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 295 */     if (msg instanceof DatagramPacket) {
/* 296 */       DatagramPacket p = (DatagramPacket)msg;
/* 297 */       ByteBuf content = (ByteBuf)p.content();
/* 298 */       if (isSingleDirectBuffer(content)) {
/* 299 */         return p;
/*     */       }
/* 301 */       return new DatagramPacket(newDirectBuffer((ReferenceCounted)p, content), (InetSocketAddress)p.recipient());
/*     */     } 
/*     */     
/* 304 */     if (msg instanceof ByteBuf) {
/* 305 */       ByteBuf buf = (ByteBuf)msg;
/* 306 */       if (isSingleDirectBuffer(buf)) {
/* 307 */         return buf;
/*     */       }
/* 309 */       return newDirectBuffer(buf);
/*     */     } 
/*     */     
/* 312 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 314 */       AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 315 */       if (e.content() instanceof ByteBuf) {
/* 316 */         ByteBuf content = (ByteBuf)e.content();
/* 317 */         if (isSingleDirectBuffer(content)) {
/* 318 */           return e;
/*     */         }
/* 320 */         return new DefaultAddressedEnvelope(newDirectBuffer((ReferenceCounted)e, content), e.recipient());
/*     */       } 
/*     */     } 
/*     */     
/* 324 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSingleDirectBuffer(ByteBuf buf) {
/* 333 */     return (buf.isDirect() && buf.nioBufferCount() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean continueOnWriteError() {
/* 341 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 346 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 351 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress) {
/* 356 */     return joinGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 362 */       return joinGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
/*     */ 
/*     */     
/*     */     }
/* 366 */     catch (SocketException e) {
/* 367 */       promise.setFailure(e);
/*     */       
/* 369 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 375 */     return joinGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 382 */     return joinGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 388 */     return joinGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 396 */     checkJavaVersion();
/*     */     
/* 398 */     if (multicastAddress == null) {
/* 399 */       throw new NullPointerException("multicastAddress");
/*     */     }
/*     */     
/* 402 */     if (networkInterface == null) {
/* 403 */       throw new NullPointerException("networkInterface");
/*     */     }
/*     */     
/*     */     try {
/*     */       MembershipKey key;
/* 408 */       if (source == null) {
/* 409 */         key = javaChannel().join(multicastAddress, networkInterface);
/*     */       } else {
/* 411 */         key = javaChannel().join(multicastAddress, networkInterface, source);
/*     */       } 
/*     */       
/* 414 */       synchronized (this) {
/* 415 */         List<MembershipKey> keys = null;
/* 416 */         if (this.memberships == null) {
/* 417 */           this.memberships = new HashMap<InetAddress, List<MembershipKey>>();
/*     */         } else {
/* 419 */           keys = this.memberships.get(multicastAddress);
/*     */         } 
/* 421 */         if (keys == null) {
/* 422 */           keys = new ArrayList<MembershipKey>();
/* 423 */           this.memberships.put(multicastAddress, keys);
/*     */         } 
/* 425 */         keys.add(key);
/*     */       } 
/*     */       
/* 428 */       promise.setSuccess();
/* 429 */     } catch (Throwable e) {
/* 430 */       promise.setFailure(e);
/*     */     } 
/*     */     
/* 433 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
/* 438 */     return leaveGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 444 */       return leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
/*     */     }
/* 446 */     catch (SocketException e) {
/* 447 */       promise.setFailure(e);
/*     */       
/* 449 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 455 */     return leaveGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 462 */     return leaveGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 468 */     return leaveGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 475 */     checkJavaVersion();
/*     */     
/* 477 */     if (multicastAddress == null) {
/* 478 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 480 */     if (networkInterface == null) {
/* 481 */       throw new NullPointerException("networkInterface");
/*     */     }
/*     */     
/* 484 */     synchronized (this) {
/* 485 */       if (this.memberships != null) {
/* 486 */         List<MembershipKey> keys = this.memberships.get(multicastAddress);
/* 487 */         if (keys != null) {
/* 488 */           Iterator<MembershipKey> keyIt = keys.iterator();
/*     */           
/* 490 */           while (keyIt.hasNext()) {
/* 491 */             MembershipKey key = keyIt.next();
/* 492 */             if (networkInterface.equals(key.networkInterface()) && ((
/* 493 */               source == null && key.sourceAddress() == null) || (source != null && source.equals(key.sourceAddress())))) {
/*     */               
/* 495 */               key.drop();
/* 496 */               keyIt.remove();
/*     */             } 
/*     */           } 
/*     */           
/* 500 */           if (keys.isEmpty()) {
/* 501 */             this.memberships.remove(multicastAddress);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 507 */     promise.setSuccess();
/* 508 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
/* 518 */     return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
/* 528 */     checkJavaVersion();
/*     */     
/* 530 */     if (multicastAddress == null) {
/* 531 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 533 */     if (sourceToBlock == null) {
/* 534 */       throw new NullPointerException("sourceToBlock");
/*     */     }
/*     */     
/* 537 */     if (networkInterface == null) {
/* 538 */       throw new NullPointerException("networkInterface");
/*     */     }
/* 540 */     synchronized (this) {
/* 541 */       if (this.memberships != null) {
/* 542 */         List<MembershipKey> keys = this.memberships.get(multicastAddress);
/* 543 */         for (MembershipKey key : keys) {
/* 544 */           if (networkInterface.equals(key.networkInterface())) {
/*     */             try {
/* 546 */               key.block(sourceToBlock);
/* 547 */             } catch (IOException e) {
/* 548 */               promise.setFailure(e);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 554 */     promise.setSuccess();
/* 555 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
/* 564 */     return block(multicastAddress, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
/*     */     try {
/* 575 */       return block(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
/*     */ 
/*     */     
/*     */     }
/* 579 */     catch (SocketException e) {
/* 580 */       promise.setFailure(e);
/*     */       
/* 582 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setReadPending(boolean readPending) {
/* 587 */     super.setReadPending(readPending);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\socket\nio\NioDatagramChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */