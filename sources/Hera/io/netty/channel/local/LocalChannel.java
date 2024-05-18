/*     */ package io.netty.channel.local;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.SingleThreadEventExecutor;
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.AlreadyConnectedException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Collections;
/*     */ import java.util.Queue;
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
/*     */ public class LocalChannel
/*     */   extends AbstractChannel
/*     */ {
/*  47 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*     */   private static final int MAX_READER_STACK_DEPTH = 8;
/*     */   
/*  51 */   private final ChannelConfig config = (ChannelConfig)new DefaultChannelConfig((Channel)this);
/*  52 */   private final Queue<Object> inboundBuffer = new ArrayDeque();
/*  53 */   private final Runnable readTask = new Runnable()
/*     */     {
/*     */       public void run() {
/*  56 */         ChannelPipeline pipeline = LocalChannel.this.pipeline();
/*     */         while (true) {
/*  58 */           Object m = LocalChannel.this.inboundBuffer.poll();
/*  59 */           if (m == null) {
/*     */             break;
/*     */           }
/*  62 */           pipeline.fireChannelRead(m);
/*     */         } 
/*  64 */         pipeline.fireChannelReadComplete();
/*     */       }
/*     */     };
/*     */   
/*  68 */   private final Runnable shutdownHook = new Runnable()
/*     */     {
/*     */       public void run() {
/*  71 */         LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidPromise());
/*     */       }
/*     */     };
/*     */   
/*     */   private volatile int state;
/*     */   private volatile LocalChannel peer;
/*     */   private volatile LocalAddress localAddress;
/*     */   private volatile LocalAddress remoteAddress;
/*     */   private volatile ChannelPromise connectPromise;
/*     */   private volatile boolean readInProgress;
/*     */   private volatile boolean registerInProgress;
/*     */   
/*     */   public LocalChannel() {
/*  84 */     super(null);
/*     */   }
/*     */   
/*     */   LocalChannel(LocalServerChannel parent, LocalChannel peer) {
/*  88 */     super((Channel)parent);
/*  89 */     this.peer = peer;
/*  90 */     this.localAddress = parent.localAddress();
/*  91 */     this.remoteAddress = peer.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  96 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig config() {
/* 101 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalServerChannel parent() {
/* 106 */     return (LocalServerChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress localAddress() {
/* 111 */     return (LocalAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress remoteAddress() {
/* 116 */     return (LocalAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 121 */     return (this.state < 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 126 */     return (this.state == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/* 131 */     return new LocalUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/* 136 */     return loop instanceof io.netty.channel.SingleThreadEventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 141 */     return this.localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 146 */     return this.remoteAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {
/* 156 */     if (this.peer != null && parent() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 163 */       final LocalChannel peer = this.peer;
/* 164 */       this.registerInProgress = true;
/* 165 */       this.state = 2;
/*     */       
/* 167 */       peer.remoteAddress = parent().localAddress();
/* 168 */       peer.state = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 174 */       peer.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 177 */               LocalChannel.this.registerInProgress = false;
/* 178 */               peer.pipeline().fireChannelActive();
/* 179 */               peer.connectPromise.setSuccess();
/*     */             }
/*     */           });
/*     */     } 
/* 183 */     ((SingleThreadEventExecutor)eventLoop()).addShutdownHook(this.shutdownHook);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 188 */     this.localAddress = LocalChannelRegistry.register((Channel)this, this.localAddress, localAddress);
/*     */ 
/*     */     
/* 191 */     this.state = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 196 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 201 */     if (this.state <= 2) {
/*     */       
/* 203 */       if (this.localAddress != null) {
/* 204 */         if (parent() == null) {
/* 205 */           LocalChannelRegistry.unregister(this.localAddress);
/*     */         }
/* 207 */         this.localAddress = null;
/*     */       } 
/* 209 */       this.state = 3;
/*     */     } 
/*     */     
/* 212 */     final LocalChannel peer = this.peer;
/* 213 */     if (peer != null && peer.isActive()) {
/*     */ 
/*     */       
/* 216 */       EventLoop eventLoop = peer.eventLoop();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       if (eventLoop.inEventLoop() && !this.registerInProgress) {
/* 223 */         peer.unsafe().close(unsafe().voidPromise());
/*     */       } else {
/* 225 */         peer.eventLoop().execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 228 */                 peer.unsafe().close(LocalChannel.this.unsafe().voidPromise());
/*     */               }
/*     */             });
/*     */       } 
/* 232 */       this.peer = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 239 */     ((SingleThreadEventExecutor)eventLoop()).removeShutdownHook(this.shutdownHook);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 244 */     if (this.readInProgress) {
/*     */       return;
/*     */     }
/*     */     
/* 248 */     ChannelPipeline pipeline = pipeline();
/* 249 */     Queue<Object> inboundBuffer = this.inboundBuffer;
/* 250 */     if (inboundBuffer.isEmpty()) {
/* 251 */       this.readInProgress = true;
/*     */       
/*     */       return;
/*     */     } 
/* 255 */     InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
/* 256 */     Integer stackDepth = Integer.valueOf(threadLocals.localChannelReaderStackDepth());
/* 257 */     if (stackDepth.intValue() < 8) {
/* 258 */       threadLocals.setLocalChannelReaderStackDepth(stackDepth.intValue() + 1);
/*     */       try {
/*     */         while (true) {
/* 261 */           Object received = inboundBuffer.poll();
/* 262 */           if (received == null) {
/*     */             break;
/*     */           }
/* 265 */           pipeline.fireChannelRead(received);
/*     */         } 
/* 267 */         pipeline.fireChannelReadComplete();
/*     */       } finally {
/* 269 */         threadLocals.setLocalChannelReaderStackDepth(stackDepth.intValue());
/*     */       } 
/*     */     } else {
/* 272 */       eventLoop().execute(this.readTask);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 278 */     if (this.state < 2) {
/* 279 */       throw new NotYetConnectedException();
/*     */     }
/* 281 */     if (this.state > 2) {
/* 282 */       throw new ClosedChannelException();
/*     */     }
/*     */     
/* 285 */     final LocalChannel peer = this.peer;
/* 286 */     final ChannelPipeline peerPipeline = peer.pipeline();
/* 287 */     EventLoop peerLoop = peer.eventLoop();
/*     */     
/* 289 */     if (peerLoop == eventLoop()) {
/*     */       while (true) {
/* 291 */         Object msg = in.current();
/* 292 */         if (msg == null) {
/*     */           break;
/*     */         }
/* 295 */         peer.inboundBuffer.add(msg);
/* 296 */         ReferenceCountUtil.retain(msg);
/* 297 */         in.remove();
/*     */       } 
/* 299 */       finishPeerRead(peer, peerPipeline);
/*     */     } else {
/*     */       
/* 302 */       final Object[] msgsCopy = new Object[in.size()];
/* 303 */       for (int i = 0; i < msgsCopy.length; i++) {
/* 304 */         msgsCopy[i] = ReferenceCountUtil.retain(in.current());
/* 305 */         in.remove();
/*     */       } 
/*     */       
/* 308 */       peerLoop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 311 */               Collections.addAll(peer.inboundBuffer, msgsCopy);
/* 312 */               LocalChannel.finishPeerRead(peer, peerPipeline);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void finishPeerRead(LocalChannel peer, ChannelPipeline peerPipeline) {
/* 319 */     if (peer.readInProgress) {
/* 320 */       peer.readInProgress = false;
/*     */       while (true) {
/* 322 */         Object received = peer.inboundBuffer.poll();
/* 323 */         if (received == null) {
/*     */           break;
/*     */         }
/* 326 */         peerPipeline.fireChannelRead(received);
/*     */       } 
/* 328 */       peerPipeline.fireChannelReadComplete();
/*     */     } 
/*     */   }
/*     */   private class LocalUnsafe extends AbstractChannel.AbstractUnsafe { private LocalUnsafe() {
/* 332 */       super(LocalChannel.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 337 */       if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/* 341 */       if (LocalChannel.this.state == 2) {
/* 342 */         Exception cause = new AlreadyConnectedException();
/* 343 */         safeSetFailure(promise, cause);
/* 344 */         LocalChannel.this.pipeline().fireExceptionCaught(cause);
/*     */         
/*     */         return;
/*     */       } 
/* 348 */       if (LocalChannel.this.connectPromise != null) {
/* 349 */         throw new ConnectionPendingException();
/*     */       }
/*     */       
/* 352 */       LocalChannel.this.connectPromise = promise;
/*     */       
/* 354 */       if (LocalChannel.this.state != 1)
/*     */       {
/* 356 */         if (localAddress == null) {
/* 357 */           localAddress = new LocalAddress((Channel)LocalChannel.this);
/*     */         }
/*     */       }
/*     */       
/* 361 */       if (localAddress != null) {
/*     */         try {
/* 363 */           LocalChannel.this.doBind(localAddress);
/* 364 */         } catch (Throwable t) {
/* 365 */           safeSetFailure(promise, t);
/* 366 */           close(voidPromise());
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/* 371 */       Channel boundChannel = LocalChannelRegistry.get(remoteAddress);
/* 372 */       if (!(boundChannel instanceof LocalServerChannel)) {
/* 373 */         ChannelException channelException = new ChannelException("connection refused");
/* 374 */         safeSetFailure(promise, (Throwable)channelException);
/* 375 */         close(voidPromise());
/*     */         
/*     */         return;
/*     */       } 
/* 379 */       LocalServerChannel serverChannel = (LocalServerChannel)boundChannel;
/* 380 */       LocalChannel.this.peer = serverChannel.serve(LocalChannel.this);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\local\LocalChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */