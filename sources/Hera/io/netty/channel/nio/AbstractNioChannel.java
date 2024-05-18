/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ConnectTimeoutException;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public abstract class AbstractNioChannel
/*     */   extends AbstractChannel
/*     */ {
/*  50 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
/*     */ 
/*     */   
/*     */   private final SelectableChannel ch;
/*     */ 
/*     */   
/*     */   protected final int readInterestOp;
/*     */ 
/*     */   
/*     */   volatile SelectionKey selectionKey;
/*     */ 
/*     */   
/*     */   private volatile boolean inputShutdown;
/*     */ 
/*     */   
/*     */   private volatile boolean readPending;
/*     */   
/*     */   private ChannelPromise connectPromise;
/*     */   
/*     */   private ScheduledFuture<?> connectTimeoutFuture;
/*     */   
/*     */   private SocketAddress requestedRemoteAddress;
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
/*  75 */     super(parent);
/*  76 */     this.ch = ch;
/*  77 */     this.readInterestOp = readInterestOp;
/*     */     try {
/*  79 */       ch.configureBlocking(false);
/*  80 */     } catch (IOException e) {
/*     */       try {
/*  82 */         ch.close();
/*  83 */       } catch (IOException e2) {
/*  84 */         if (logger.isWarnEnabled()) {
/*  85 */           logger.warn("Failed to close a partially initialized socket.", e2);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  90 */       throw new ChannelException("Failed to enter non-blocking mode.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  96 */     return this.ch.isOpen();
/*     */   }
/*     */ 
/*     */   
/*     */   public NioUnsafe unsafe() {
/* 101 */     return (NioUnsafe)super.unsafe();
/*     */   }
/*     */   
/*     */   protected SelectableChannel javaChannel() {
/* 105 */     return this.ch;
/*     */   }
/*     */ 
/*     */   
/*     */   public NioEventLoop eventLoop() {
/* 110 */     return (NioEventLoop)super.eventLoop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SelectionKey selectionKey() {
/* 117 */     assert this.selectionKey != null;
/* 118 */     return this.selectionKey;
/*     */   }
/*     */   
/*     */   protected boolean isReadPending() {
/* 122 */     return this.readPending;
/*     */   }
/*     */   
/*     */   protected void setReadPending(boolean readPending) {
/* 126 */     this.readPending = readPending;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInputShutdown() {
/* 133 */     return this.inputShutdown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setInputShutdown() {
/* 140 */     this.inputShutdown = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract class AbstractNioUnsafe
/*     */     extends AbstractChannel.AbstractUnsafe
/*     */     implements NioUnsafe
/*     */   {
/*     */     protected AbstractNioUnsafe() {
/* 165 */       super(AbstractNioChannel.this);
/*     */     }
/*     */     protected final void removeReadOp() {
/* 168 */       SelectionKey key = AbstractNioChannel.this.selectionKey();
/*     */ 
/*     */ 
/*     */       
/* 172 */       if (!key.isValid()) {
/*     */         return;
/*     */       }
/* 175 */       int interestOps = key.interestOps();
/* 176 */       if ((interestOps & AbstractNioChannel.this.readInterestOp) != 0)
/*     */       {
/* 178 */         key.interestOps(interestOps & (AbstractNioChannel.this.readInterestOp ^ 0xFFFFFFFF));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public final SelectableChannel ch() {
/* 184 */       return AbstractNioChannel.this.javaChannel();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 190 */       if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 195 */         if (AbstractNioChannel.this.connectPromise != null) {
/* 196 */           throw new IllegalStateException("connection attempt already made");
/*     */         }
/*     */         
/* 199 */         boolean wasActive = AbstractNioChannel.this.isActive();
/* 200 */         if (AbstractNioChannel.this.doConnect(remoteAddress, localAddress)) {
/* 201 */           fulfillConnectPromise(promise, wasActive);
/*     */         } else {
/* 203 */           AbstractNioChannel.this.connectPromise = promise;
/* 204 */           AbstractNioChannel.this.requestedRemoteAddress = remoteAddress;
/*     */ 
/*     */           
/* 207 */           int connectTimeoutMillis = AbstractNioChannel.this.config().getConnectTimeoutMillis();
/* 208 */           if (connectTimeoutMillis > 0) {
/* 209 */             AbstractNioChannel.this.connectTimeoutFuture = (ScheduledFuture<?>)AbstractNioChannel.this.eventLoop().schedule((Runnable)new OneTimeTask()
/*     */                 {
/*     */                   public void run() {
/* 212 */                     ChannelPromise connectPromise = AbstractNioChannel.this.connectPromise;
/* 213 */                     ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
/*     */                     
/* 215 */                     if (connectPromise != null && connectPromise.tryFailure((Throwable)cause)) {
/* 216 */                       AbstractNioChannel.AbstractNioUnsafe.this.close(AbstractNioChannel.AbstractNioUnsafe.this.voidPromise());
/*     */                     }
/*     */                   }
/*     */                 },  connectTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */           }
/*     */           
/* 222 */           promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 225 */                   if (future.isCancelled()) {
/* 226 */                     if (AbstractNioChannel.this.connectTimeoutFuture != null) {
/* 227 */                       AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
/*     */                     }
/* 229 */                     AbstractNioChannel.this.connectPromise = null;
/* 230 */                     AbstractNioChannel.AbstractNioUnsafe.this.close(AbstractNioChannel.AbstractNioUnsafe.this.voidPromise());
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/* 235 */       } catch (Throwable t) {
/* 236 */         if (t instanceof ConnectException) {
/* 237 */           Throwable newT = new ConnectException(t.getMessage() + ": " + remoteAddress);
/* 238 */           newT.setStackTrace(t.getStackTrace());
/* 239 */           t = newT;
/*     */         } 
/* 241 */         promise.tryFailure(t);
/* 242 */         closeIfClosed();
/*     */       } 
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
/* 247 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 253 */       boolean promiseSet = promise.trySuccess();
/*     */ 
/*     */ 
/*     */       
/* 257 */       if (!wasActive && AbstractNioChannel.this.isActive()) {
/* 258 */         AbstractNioChannel.this.pipeline().fireChannelActive();
/*     */       }
/*     */ 
/*     */       
/* 262 */       if (!promiseSet) {
/* 263 */         close(voidPromise());
/*     */       }
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
/* 268 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 274 */       promise.tryFailure(cause);
/* 275 */       closeIfClosed();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void finishConnect() {
/* 283 */       assert AbstractNioChannel.this.eventLoop().inEventLoop();
/*     */       
/*     */       try {
/* 286 */         boolean wasActive = AbstractNioChannel.this.isActive();
/* 287 */         AbstractNioChannel.this.doFinishConnect();
/* 288 */         fulfillConnectPromise(AbstractNioChannel.this.connectPromise, wasActive);
/* 289 */       } catch (Throwable t) {
/* 290 */         if (t instanceof ConnectException) {
/* 291 */           Throwable newT = new ConnectException(t.getMessage() + ": " + AbstractNioChannel.this.requestedRemoteAddress);
/* 292 */           newT.setStackTrace(t.getStackTrace());
/* 293 */           t = newT;
/*     */         } 
/*     */         
/* 296 */         fulfillConnectPromise(AbstractNioChannel.this.connectPromise, t);
/*     */       }
/*     */       finally {
/*     */         
/* 300 */         if (AbstractNioChannel.this.connectTimeoutFuture != null) {
/* 301 */           AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
/*     */         }
/* 303 */         AbstractNioChannel.this.connectPromise = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final void flush0() {
/* 312 */       if (isFlushPending()) {
/*     */         return;
/*     */       }
/* 315 */       super.flush0();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void forceFlush() {
/* 321 */       super.flush0();
/*     */     }
/*     */     
/*     */     private boolean isFlushPending() {
/* 325 */       SelectionKey selectionKey = AbstractNioChannel.this.selectionKey();
/* 326 */       return (selectionKey.isValid() && (selectionKey.interestOps() & 0x4) != 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/* 332 */     return loop instanceof NioEventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {
/* 337 */     boolean selected = false;
/*     */     while (true) {
/*     */       try {
/* 340 */         this.selectionKey = javaChannel().register((eventLoop()).selector, 0, this);
/*     */         return;
/* 342 */       } catch (CancelledKeyException e) {
/* 343 */         if (!selected) {
/*     */ 
/*     */           
/* 346 */           eventLoop().selectNow();
/* 347 */           selected = true; continue;
/*     */         }  break;
/*     */       } 
/*     */     } 
/* 351 */     throw e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 359 */     eventLoop().cancel(selectionKey());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 365 */     if (this.inputShutdown) {
/*     */       return;
/*     */     }
/*     */     
/* 369 */     SelectionKey selectionKey = this.selectionKey;
/* 370 */     if (!selectionKey.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 374 */     this.readPending = true;
/*     */     
/* 376 */     int interestOps = selectionKey.interestOps();
/* 377 */     if ((interestOps & this.readInterestOp) == 0) {
/* 378 */       selectionKey.interestOps(interestOps | this.readInterestOp);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf newDirectBuffer(ByteBuf buf) {
/* 398 */     int readableBytes = buf.readableBytes();
/* 399 */     if (readableBytes == 0) {
/* 400 */       ReferenceCountUtil.safeRelease(buf);
/* 401 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/*     */     
/* 404 */     ByteBufAllocator alloc = alloc();
/* 405 */     if (alloc.isDirectBufferPooled()) {
/* 406 */       ByteBuf byteBuf = alloc.directBuffer(readableBytes);
/* 407 */       byteBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 408 */       ReferenceCountUtil.safeRelease(buf);
/* 409 */       return byteBuf;
/*     */     } 
/*     */     
/* 412 */     ByteBuf directBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 413 */     if (directBuf != null) {
/* 414 */       directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 415 */       ReferenceCountUtil.safeRelease(buf);
/* 416 */       return directBuf;
/*     */     } 
/*     */ 
/*     */     
/* 420 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf newDirectBuffer(ReferenceCounted holder, ByteBuf buf) {
/* 430 */     int readableBytes = buf.readableBytes();
/* 431 */     if (readableBytes == 0) {
/* 432 */       ReferenceCountUtil.safeRelease(holder);
/* 433 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/*     */     
/* 436 */     ByteBufAllocator alloc = alloc();
/* 437 */     if (alloc.isDirectBufferPooled()) {
/* 438 */       ByteBuf byteBuf = alloc.directBuffer(readableBytes);
/* 439 */       byteBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 440 */       ReferenceCountUtil.safeRelease(holder);
/* 441 */       return byteBuf;
/*     */     } 
/*     */     
/* 444 */     ByteBuf directBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 445 */     if (directBuf != null) {
/* 446 */       directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 447 */       ReferenceCountUtil.safeRelease(holder);
/* 448 */       return directBuf;
/*     */     } 
/*     */ 
/*     */     
/* 452 */     if (holder != buf) {
/*     */       
/* 454 */       buf.retain();
/* 455 */       ReferenceCountUtil.safeRelease(holder);
/*     */     } 
/*     */     
/* 458 */     return buf;
/*     */   }
/*     */   
/*     */   protected abstract boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2) throws Exception;
/*     */   
/*     */   protected abstract void doFinishConnect() throws Exception;
/*     */   
/*     */   public static interface NioUnsafe extends Channel.Unsafe {
/*     */     SelectableChannel ch();
/*     */     
/*     */     void finishConnect();
/*     */     
/*     */     void read();
/*     */     
/*     */     void forceFlush();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\nio\AbstractNioChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */