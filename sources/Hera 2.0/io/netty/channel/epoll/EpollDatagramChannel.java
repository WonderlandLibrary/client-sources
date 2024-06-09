/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultAddressedEnvelope;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.NotYetConnectedException;
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
/*     */ public final class EpollDatagramChannel
/*     */   extends AbstractEpollChannel
/*     */   implements DatagramChannel
/*     */ {
/*  47 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
/*  48 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(InetSocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */ 
/*     */   
/*     */   private volatile InetSocketAddress local;
/*     */   
/*     */   private volatile InetSocketAddress remote;
/*     */   
/*     */   private volatile boolean connected;
/*     */   
/*     */   private final EpollDatagramChannelConfig config;
/*     */ 
/*     */   
/*     */   public EpollDatagramChannel() {
/*  61 */     super(Native.socketDgramFd(), 1);
/*  62 */     this.config = new EpollDatagramChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  67 */     return METADATA;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  73 */     return (this.fd != -1 && ((((Boolean)this.config.<Boolean>getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.active));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/*  80 */     return this.connected;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress) {
/*  85 */     return joinGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/*  91 */       return joinGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
/*     */ 
/*     */     
/*     */     }
/*  95 */     catch (SocketException e) {
/*  96 */       promise.setFailure(e);
/*     */       
/*  98 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 104 */     return joinGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 111 */     return joinGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 117 */     return joinGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 125 */     if (multicastAddress == null) {
/* 126 */       throw new NullPointerException("multicastAddress");
/*     */     }
/*     */     
/* 129 */     if (networkInterface == null) {
/* 130 */       throw new NullPointerException("networkInterface");
/*     */     }
/*     */     
/* 133 */     promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
/* 134 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
/* 139 */     return leaveGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 145 */       return leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
/*     */     }
/* 147 */     catch (SocketException e) {
/* 148 */       promise.setFailure(e);
/*     */       
/* 150 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 156 */     return leaveGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 163 */     return leaveGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 169 */     return leaveGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 176 */     if (multicastAddress == null) {
/* 177 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 179 */     if (networkInterface == null) {
/* 180 */       throw new NullPointerException("networkInterface");
/*     */     }
/*     */     
/* 183 */     promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
/*     */     
/* 185 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
/* 192 */     return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
/* 199 */     if (multicastAddress == null) {
/* 200 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 202 */     if (sourceToBlock == null) {
/* 203 */       throw new NullPointerException("sourceToBlock");
/*     */     }
/*     */     
/* 206 */     if (networkInterface == null) {
/* 207 */       throw new NullPointerException("networkInterface");
/*     */     }
/* 209 */     promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
/* 210 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
/* 215 */     return block(multicastAddress, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
/*     */     try {
/* 222 */       return block(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
/*     */ 
/*     */     
/*     */     }
/* 226 */     catch (Throwable e) {
/* 227 */       promise.setFailure(e);
/*     */       
/* 229 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
/* 234 */     return new EpollDatagramChannelUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected InetSocketAddress localAddress0() {
/* 239 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InetSocketAddress remoteAddress0() {
/* 244 */     return this.remote;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 249 */     InetSocketAddress addr = (InetSocketAddress)localAddress;
/* 250 */     checkResolvable(addr);
/* 251 */     Native.bind(this.fd, addr.getAddress(), addr.getPort());
/* 252 */     this.local = Native.localAddress(this.fd);
/* 253 */     this.active = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     while (true) {
/* 259 */       Object msg = in.current();
/* 260 */       if (msg == null) {
/*     */         
/* 262 */         clearEpollOut();
/*     */         
/*     */         break;
/*     */       } 
/*     */       try {
/* 267 */         boolean done = false;
/* 268 */         for (int i = config().getWriteSpinCount() - 1; i >= 0; i--) {
/* 269 */           if (doWriteMessage(msg)) {
/* 270 */             done = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 275 */         if (done) {
/* 276 */           in.remove();
/*     */           continue;
/*     */         } 
/* 279 */         setEpollOut();
/*     */         
/*     */         break;
/* 282 */       } catch (IOException e) {
/*     */ 
/*     */ 
/*     */         
/* 286 */         in.remove(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private boolean doWriteMessage(Object msg) throws IOException {
/*     */     ByteBuf data;
/*     */     InetSocketAddress remoteAddress;
/*     */     int writtenBytes;
/* 294 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 296 */       AddressedEnvelope<ByteBuf, InetSocketAddress> envelope = (AddressedEnvelope<ByteBuf, InetSocketAddress>)msg;
/*     */       
/* 298 */       data = (ByteBuf)envelope.content();
/* 299 */       remoteAddress = (InetSocketAddress)envelope.recipient();
/*     */     } else {
/* 301 */       data = (ByteBuf)msg;
/* 302 */       remoteAddress = null;
/*     */     } 
/*     */     
/* 305 */     int dataLen = data.readableBytes();
/* 306 */     if (dataLen == 0) {
/* 307 */       return true;
/*     */     }
/*     */     
/* 310 */     if (remoteAddress == null) {
/* 311 */       remoteAddress = this.remote;
/* 312 */       if (remoteAddress == null) {
/* 313 */         throw new NotYetConnectedException();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 318 */     if (data.hasMemoryAddress()) {
/* 319 */       long memoryAddress = data.memoryAddress();
/* 320 */       writtenBytes = Native.sendToAddress(this.fd, memoryAddress, data.readerIndex(), data.writerIndex(), remoteAddress.getAddress(), remoteAddress.getPort());
/*     */     } else {
/*     */       
/* 323 */       ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
/* 324 */       writtenBytes = Native.sendTo(this.fd, nioData, nioData.position(), nioData.limit(), remoteAddress.getAddress(), remoteAddress.getPort());
/*     */     } 
/*     */ 
/*     */     
/* 328 */     return (writtenBytes > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 333 */     if (msg instanceof DatagramPacket) {
/* 334 */       DatagramPacket packet = (DatagramPacket)msg;
/* 335 */       ByteBuf content = (ByteBuf)packet.content();
/* 336 */       if (content.hasMemoryAddress()) {
/* 337 */         return msg;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 342 */       return new DatagramPacket(newDirectBuffer(packet, content), (InetSocketAddress)packet.recipient());
/*     */     } 
/*     */     
/* 345 */     if (msg instanceof ByteBuf) {
/* 346 */       ByteBuf buf = (ByteBuf)msg;
/* 347 */       if (buf.hasMemoryAddress()) {
/* 348 */         return msg;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 353 */       return newDirectBuffer(buf);
/*     */     } 
/*     */     
/* 356 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 358 */       AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 359 */       if (e.content() instanceof ByteBuf && (e.recipient() == null || e.recipient() instanceof InetSocketAddress)) {
/*     */ 
/*     */         
/* 362 */         ByteBuf content = (ByteBuf)e.content();
/* 363 */         if (content.hasMemoryAddress()) {
/* 364 */           return e;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 369 */         return new DefaultAddressedEnvelope(newDirectBuffer(e, content), e.recipient());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 374 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig config() {
/* 380 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 385 */     this.connected = false;
/*     */   }
/*     */   
/*     */   final class EpollDatagramChannelUnsafe
/*     */     extends AbstractEpollChannel.AbstractEpollUnsafe {
/*     */     private RecvByteBufAllocator.Handle allocHandle;
/*     */     
/*     */     public void connect(SocketAddress remote, SocketAddress local, ChannelPromise channelPromise) {
/* 393 */       boolean success = false;
/*     */       try {
/*     */         try {
/* 396 */           InetSocketAddress remoteAddress = (InetSocketAddress)remote;
/* 397 */           if (local != null) {
/* 398 */             InetSocketAddress localAddress = (InetSocketAddress)local;
/* 399 */             EpollDatagramChannel.this.doBind(localAddress);
/*     */           } 
/*     */           
/* 402 */           AbstractEpollChannel.checkResolvable(remoteAddress);
/* 403 */           EpollDatagramChannel.this.remote = remoteAddress;
/* 404 */           EpollDatagramChannel.this.local = Native.localAddress(EpollDatagramChannel.this.fd);
/* 405 */           success = true;
/*     */         } finally {
/* 407 */           if (!success) {
/* 408 */             EpollDatagramChannel.this.doClose();
/*     */           } else {
/* 410 */             channelPromise.setSuccess();
/* 411 */             EpollDatagramChannel.this.connected = true;
/*     */           } 
/*     */         } 
/* 414 */       } catch (Throwable cause) {
/* 415 */         channelPromise.setFailure(cause);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     void epollInReady() {
/* 421 */       DatagramChannelConfig config = EpollDatagramChannel.this.config();
/* 422 */       RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 423 */       if (allocHandle == null) {
/* 424 */         this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
/*     */       }
/*     */       
/* 427 */       assert EpollDatagramChannel.this.eventLoop().inEventLoop();
/* 428 */       ChannelPipeline pipeline = EpollDatagramChannel.this.pipeline();
/*     */       try {
/*     */         while (true) {
/* 431 */           ByteBuf data = null; 
/*     */           try { EpollDatagramChannel.DatagramSocketAddress remoteAddress;
/* 433 */             data = allocHandle.allocate(config.getAllocator());
/* 434 */             int writerIndex = data.writerIndex();
/*     */             
/* 436 */             if (data.hasMemoryAddress()) {
/*     */               
/* 438 */               remoteAddress = Native.recvFromAddress(EpollDatagramChannel.this.fd, data.memoryAddress(), writerIndex, data.capacity());
/*     */             } else {
/*     */               
/* 441 */               ByteBuffer nioData = data.internalNioBuffer(writerIndex, data.writableBytes());
/* 442 */               remoteAddress = Native.recvFrom(EpollDatagramChannel.this.fd, nioData, nioData.position(), nioData.limit());
/*     */             } 
/*     */ 
/*     */             
/* 446 */             if (remoteAddress == null)
/*     */             
/*     */             { 
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
/* 462 */               if (data != null)
/* 463 */                 data.release();  break; }  int readBytes = remoteAddress.receivedAmount; data.writerIndex(data.writerIndex() + readBytes); allocHandle.record(readBytes); this.readPending = false; pipeline.fireChannelRead(new DatagramPacket(data, (InetSocketAddress)localAddress(), remoteAddress)); data = null; } catch (Throwable t) { pipeline.fireChannelReadComplete(); pipeline.fireExceptionCaught(t); } finally { if (data != null) data.release();
/*     */             
/*     */             
/*     */              }
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 474 */         if (!EpollDatagramChannel.this.config().isAutoRead() && !this.readPending) {
/* 475 */           EpollDatagramChannel.this.clearEpollIn();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class DatagramSocketAddress
/*     */     extends InetSocketAddress
/*     */   {
/*     */     private static final long serialVersionUID = 1348596211215015739L;
/*     */ 
/*     */     
/*     */     final int receivedAmount;
/*     */ 
/*     */     
/*     */     DatagramSocketAddress(String addr, int port, int receivedAmount) {
/* 493 */       super(addr, port);
/* 494 */       this.receivedAmount = receivedAmount;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\EpollDatagramChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */