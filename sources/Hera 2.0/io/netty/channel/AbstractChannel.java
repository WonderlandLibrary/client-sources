/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.DefaultAttributeMap;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.util.concurrent.RejectedExecutionException;
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
/*     */ public abstract class AbstractChannel
/*     */   extends DefaultAttributeMap
/*     */   implements Channel
/*     */ {
/*  40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
/*     */   
/*  42 */   static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = new ClosedChannelException();
/*  43 */   static final NotYetConnectedException NOT_YET_CONNECTED_EXCEPTION = new NotYetConnectedException();
/*     */   
/*     */   static {
/*  46 */     CLOSED_CHANNEL_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  47 */     NOT_YET_CONNECTED_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */ 
/*     */   
/*     */   private MessageSizeEstimator.Handle estimatorHandle;
/*     */   private final Channel parent;
/*  53 */   private final long hashCode = ThreadLocalRandom.current().nextLong();
/*     */   private final Channel.Unsafe unsafe;
/*     */   private final DefaultChannelPipeline pipeline;
/*  56 */   private final ChannelFuture succeededFuture = new SucceededChannelFuture(this, null);
/*  57 */   private final VoidChannelPromise voidPromise = new VoidChannelPromise(this, true);
/*  58 */   private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);
/*  59 */   private final CloseFuture closeFuture = new CloseFuture(this);
/*     */ 
/*     */   
/*     */   private volatile SocketAddress localAddress;
/*     */ 
/*     */   
/*     */   private volatile SocketAddress remoteAddress;
/*     */   
/*     */   private volatile EventLoop eventLoop;
/*     */   
/*     */   private volatile boolean registered;
/*     */   
/*     */   private boolean strValActive;
/*     */   
/*     */   private String strVal;
/*     */ 
/*     */   
/*     */   protected AbstractChannel(Channel parent) {
/*  77 */     this.parent = parent;
/*  78 */     this.unsafe = newUnsafe();
/*  79 */     this.pipeline = new DefaultChannelPipeline(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/*  84 */     ChannelOutboundBuffer buf = this.unsafe.outboundBuffer();
/*  85 */     return (buf != null && buf.isWritable());
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel parent() {
/*  90 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPipeline pipeline() {
/*  95 */     return this.pipeline;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 100 */     return config().getAllocator();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoop eventLoop() {
/* 105 */     EventLoop eventLoop = this.eventLoop;
/* 106 */     if (eventLoop == null) {
/* 107 */       throw new IllegalStateException("channel not registered to an event loop");
/*     */     }
/* 109 */     return eventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress localAddress() {
/* 114 */     SocketAddress localAddress = this.localAddress;
/* 115 */     if (localAddress == null) {
/*     */       try {
/* 117 */         this.localAddress = localAddress = unsafe().localAddress();
/* 118 */       } catch (Throwable t) {
/*     */         
/* 120 */         return null;
/*     */       } 
/*     */     }
/* 123 */     return localAddress;
/*     */   }
/*     */   
/*     */   protected void invalidateLocalAddress() {
/* 127 */     this.localAddress = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress remoteAddress() {
/* 132 */     SocketAddress remoteAddress = this.remoteAddress;
/* 133 */     if (remoteAddress == null) {
/*     */       try {
/* 135 */         this.remoteAddress = remoteAddress = unsafe().remoteAddress();
/* 136 */       } catch (Throwable t) {
/*     */         
/* 138 */         return null;
/*     */       } 
/*     */     }
/* 141 */     return remoteAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void invalidateRemoteAddress() {
/* 148 */     this.remoteAddress = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRegistered() {
/* 153 */     return this.registered;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress localAddress) {
/* 158 */     return this.pipeline.bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress) {
/* 163 */     return this.pipeline.connect(remoteAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/* 168 */     return this.pipeline.connect(remoteAddress, localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect() {
/* 173 */     return this.pipeline.disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 178 */     return this.pipeline.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister() {
/* 183 */     return this.pipeline.deregister();
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel flush() {
/* 188 */     this.pipeline.flush();
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
/* 194 */     return this.pipeline.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/* 199 */     return this.pipeline.connect(remoteAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 204 */     return this.pipeline.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect(ChannelPromise promise) {
/* 209 */     return this.pipeline.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(ChannelPromise promise) {
/* 214 */     return this.pipeline.close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister(ChannelPromise promise) {
/* 219 */     return this.pipeline.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel read() {
/* 224 */     this.pipeline.read();
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object msg) {
/* 230 */     return this.pipeline.write(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object msg, ChannelPromise promise) {
/* 235 */     return this.pipeline.write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object msg) {
/* 240 */     return this.pipeline.writeAndFlush(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/* 245 */     return this.pipeline.writeAndFlush(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPromise newPromise() {
/* 250 */     return new DefaultChannelPromise(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelProgressivePromise newProgressivePromise() {
/* 255 */     return new DefaultChannelProgressivePromise(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newSucceededFuture() {
/* 260 */     return this.succeededFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newFailedFuture(Throwable cause) {
/* 265 */     return new FailedChannelFuture(this, null, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture closeFuture() {
/* 270 */     return this.closeFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel.Unsafe unsafe() {
/* 275 */     return this.unsafe;
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
/*     */   public final int hashCode() {
/* 288 */     return (int)this.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object o) {
/* 297 */     return (this == o);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int compareTo(Channel o) {
/* 302 */     if (this == o) {
/* 303 */       return 0;
/*     */     }
/*     */     
/* 306 */     long ret = this.hashCode - o.hashCode();
/* 307 */     if (ret > 0L) {
/* 308 */       return 1;
/*     */     }
/* 310 */     if (ret < 0L) {
/* 311 */       return -1;
/*     */     }
/*     */     
/* 314 */     ret = (System.identityHashCode(this) - System.identityHashCode(o));
/* 315 */     if (ret != 0L) {
/* 316 */       return (int)ret;
/*     */     }
/*     */ 
/*     */     
/* 320 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 331 */     boolean active = isActive();
/* 332 */     if (this.strValActive == active && this.strVal != null) {
/* 333 */       return this.strVal;
/*     */     }
/*     */     
/* 336 */     SocketAddress remoteAddr = remoteAddress();
/* 337 */     SocketAddress localAddr = localAddress();
/* 338 */     if (remoteAddr != null) {
/*     */       SocketAddress srcAddr, dstAddr;
/*     */       
/* 341 */       if (this.parent == null) {
/* 342 */         srcAddr = localAddr;
/* 343 */         dstAddr = remoteAddr;
/*     */       } else {
/* 345 */         srcAddr = remoteAddr;
/* 346 */         dstAddr = localAddr;
/*     */       } 
/* 348 */       this.strVal = String.format("[id: 0x%08x, %s %s %s]", new Object[] { Integer.valueOf((int)this.hashCode), srcAddr, active ? "=>" : ":>", dstAddr });
/* 349 */     } else if (localAddr != null) {
/* 350 */       this.strVal = String.format("[id: 0x%08x, %s]", new Object[] { Integer.valueOf((int)this.hashCode), localAddr });
/*     */     } else {
/* 352 */       this.strVal = String.format("[id: 0x%08x]", new Object[] { Integer.valueOf((int)this.hashCode) });
/*     */     } 
/*     */     
/* 355 */     this.strValActive = active;
/* 356 */     return this.strVal;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ChannelPromise voidPromise() {
/* 361 */     return this.voidPromise;
/*     */   }
/*     */   
/*     */   final MessageSizeEstimator.Handle estimatorHandle() {
/* 365 */     if (this.estimatorHandle == null) {
/* 366 */       this.estimatorHandle = config().getMessageSizeEstimator().newHandle();
/*     */     }
/* 368 */     return this.estimatorHandle;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract class AbstractUnsafe
/*     */     implements Channel.Unsafe
/*     */   {
/* 376 */     private ChannelOutboundBuffer outboundBuffer = new ChannelOutboundBuffer(AbstractChannel.this);
/*     */     
/*     */     private boolean inFlush0;
/*     */     
/*     */     public final ChannelOutboundBuffer outboundBuffer() {
/* 381 */       return this.outboundBuffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public final SocketAddress localAddress() {
/* 386 */       return AbstractChannel.this.localAddress0();
/*     */     }
/*     */ 
/*     */     
/*     */     public final SocketAddress remoteAddress() {
/* 391 */       return AbstractChannel.this.remoteAddress0();
/*     */     }
/*     */ 
/*     */     
/*     */     public final void register(EventLoop eventLoop, final ChannelPromise promise) {
/* 396 */       if (eventLoop == null) {
/* 397 */         throw new NullPointerException("eventLoop");
/*     */       }
/* 399 */       if (AbstractChannel.this.isRegistered()) {
/* 400 */         promise.setFailure(new IllegalStateException("registered to an event loop already"));
/*     */         return;
/*     */       } 
/* 403 */       if (!AbstractChannel.this.isCompatible(eventLoop)) {
/* 404 */         promise.setFailure(new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 409 */       AbstractChannel.this.eventLoop = eventLoop;
/*     */       
/* 411 */       if (eventLoop.inEventLoop()) {
/* 412 */         register0(promise);
/*     */       } else {
/*     */         try {
/* 415 */           eventLoop.execute((Runnable)new OneTimeTask()
/*     */               {
/*     */                 public void run() {
/* 418 */                   AbstractChannel.AbstractUnsafe.this.register0(promise);
/*     */                 }
/*     */               });
/* 421 */         } catch (Throwable t) {
/* 422 */           AbstractChannel.logger.warn("Force-closing a channel whose registration task was not accepted by an event loop: {}", AbstractChannel.this, t);
/*     */ 
/*     */           
/* 425 */           closeForcibly();
/* 426 */           AbstractChannel.this.closeFuture.setClosed();
/* 427 */           safeSetFailure(promise, t);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void register0(ChannelPromise promise) {
/*     */       try {
/* 436 */         if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*     */           return;
/*     */         }
/* 439 */         AbstractChannel.this.doRegister();
/* 440 */         AbstractChannel.this.registered = true;
/* 441 */         safeSetSuccess(promise);
/* 442 */         AbstractChannel.this.pipeline.fireChannelRegistered();
/* 443 */         if (AbstractChannel.this.isActive()) {
/* 444 */           AbstractChannel.this.pipeline.fireChannelActive();
/*     */         }
/* 446 */       } catch (Throwable t) {
/*     */         
/* 448 */         closeForcibly();
/* 449 */         AbstractChannel.this.closeFuture.setClosed();
/* 450 */         safeSetFailure(promise, t);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public final void bind(SocketAddress localAddress, ChannelPromise promise) {
/* 456 */       if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 461 */       if (!PlatformDependent.isWindows() && !PlatformDependent.isRoot() && Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST)) && localAddress instanceof InetSocketAddress && !((InetSocketAddress)localAddress).getAddress().isAnyLocalAddress())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 467 */         AbstractChannel.logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address (" + localAddress + ") anyway as requested.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 473 */       boolean wasActive = AbstractChannel.this.isActive();
/*     */       try {
/* 475 */         AbstractChannel.this.doBind(localAddress);
/* 476 */       } catch (Throwable t) {
/* 477 */         safeSetFailure(promise, t);
/* 478 */         closeIfClosed();
/*     */         
/*     */         return;
/*     */       } 
/* 482 */       if (!wasActive && AbstractChannel.this.isActive()) {
/* 483 */         invokeLater((Runnable)new OneTimeTask()
/*     */             {
/*     */               public void run() {
/* 486 */                 AbstractChannel.this.pipeline.fireChannelActive();
/*     */               }
/*     */             });
/*     */       }
/*     */       
/* 491 */       safeSetSuccess(promise);
/*     */     }
/*     */ 
/*     */     
/*     */     public final void disconnect(ChannelPromise promise) {
/* 496 */       if (!promise.setUncancellable()) {
/*     */         return;
/*     */       }
/*     */       
/* 500 */       boolean wasActive = AbstractChannel.this.isActive();
/*     */       try {
/* 502 */         AbstractChannel.this.doDisconnect();
/* 503 */       } catch (Throwable t) {
/* 504 */         safeSetFailure(promise, t);
/* 505 */         closeIfClosed();
/*     */         
/*     */         return;
/*     */       } 
/* 509 */       if (wasActive && !AbstractChannel.this.isActive()) {
/* 510 */         invokeLater((Runnable)new OneTimeTask()
/*     */             {
/*     */               public void run() {
/* 513 */                 AbstractChannel.this.pipeline.fireChannelInactive();
/*     */               }
/*     */             });
/*     */       }
/*     */       
/* 518 */       safeSetSuccess(promise);
/* 519 */       closeIfClosed();
/*     */     }
/*     */ 
/*     */     
/*     */     public final void close(final ChannelPromise promise) {
/* 524 */       if (!promise.setUncancellable()) {
/*     */         return;
/*     */       }
/*     */       
/* 528 */       if (this.inFlush0) {
/* 529 */         invokeLater((Runnable)new OneTimeTask()
/*     */             {
/*     */               public void run() {
/* 532 */                 AbstractChannel.AbstractUnsafe.this.close(promise);
/*     */               }
/*     */             });
/*     */         
/*     */         return;
/*     */       } 
/* 538 */       if (AbstractChannel.this.closeFuture.isDone()) {
/*     */         
/* 540 */         safeSetSuccess(promise);
/*     */         
/*     */         return;
/*     */       } 
/* 544 */       boolean wasActive = AbstractChannel.this.isActive();
/* 545 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/* 546 */       this.outboundBuffer = null;
/*     */       
/*     */       try {
/* 549 */         AbstractChannel.this.doClose();
/* 550 */         AbstractChannel.this.closeFuture.setClosed();
/* 551 */         safeSetSuccess(promise);
/* 552 */       } catch (Throwable t) {
/* 553 */         AbstractChannel.this.closeFuture.setClosed();
/* 554 */         safeSetFailure(promise, t);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 559 */         outboundBuffer.failFlushed(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/* 560 */         outboundBuffer.close(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/*     */       } finally {
/*     */         
/* 563 */         if (wasActive && !AbstractChannel.this.isActive()) {
/* 564 */           invokeLater((Runnable)new OneTimeTask()
/*     */               {
/*     */                 public void run() {
/* 567 */                   AbstractChannel.this.pipeline.fireChannelInactive();
/*     */                 }
/*     */               });
/*     */         }
/*     */         
/* 572 */         deregister(voidPromise());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public final void closeForcibly() {
/*     */       try {
/* 579 */         AbstractChannel.this.doClose();
/* 580 */       } catch (Exception e) {
/* 581 */         AbstractChannel.logger.warn("Failed to close a channel.", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public final void deregister(ChannelPromise promise) {
/* 587 */       if (!promise.setUncancellable()) {
/*     */         return;
/*     */       }
/*     */       
/* 591 */       if (!AbstractChannel.this.registered) {
/* 592 */         safeSetSuccess(promise);
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 597 */         AbstractChannel.this.doDeregister();
/* 598 */       } catch (Throwable t) {
/* 599 */         AbstractChannel.logger.warn("Unexpected exception occurred while deregistering a channel.", t);
/*     */       } finally {
/* 601 */         if (AbstractChannel.this.registered) {
/* 602 */           AbstractChannel.this.registered = false;
/* 603 */           invokeLater((Runnable)new OneTimeTask()
/*     */               {
/*     */                 public void run() {
/* 606 */                   AbstractChannel.this.pipeline.fireChannelUnregistered();
/*     */                 }
/*     */               });
/* 609 */           safeSetSuccess(promise);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 614 */           safeSetSuccess(promise);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public final void beginRead() {
/* 621 */       if (!AbstractChannel.this.isActive()) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 626 */         AbstractChannel.this.doBeginRead();
/* 627 */       } catch (Exception e) {
/* 628 */         invokeLater((Runnable)new OneTimeTask()
/*     */             {
/*     */               public void run() {
/* 631 */                 AbstractChannel.this.pipeline.fireExceptionCaught(e);
/*     */               }
/*     */             });
/* 634 */         close(voidPromise());
/*     */       } 
/*     */     }
/*     */     
/*     */     public final void write(Object msg, ChannelPromise promise) {
/*     */       int size;
/* 640 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/* 641 */       if (outboundBuffer == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 646 */         safeSetFailure(promise, AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/*     */         
/* 648 */         ReferenceCountUtil.release(msg);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       try {
/* 654 */         msg = AbstractChannel.this.filterOutboundMessage(msg);
/* 655 */         size = AbstractChannel.this.estimatorHandle().size(msg);
/* 656 */         if (size < 0) {
/* 657 */           size = 0;
/*     */         }
/* 659 */       } catch (Throwable t) {
/* 660 */         safeSetFailure(promise, t);
/* 661 */         ReferenceCountUtil.release(msg);
/*     */         
/*     */         return;
/*     */       } 
/* 665 */       outboundBuffer.addMessage(msg, size, promise);
/*     */     }
/*     */ 
/*     */     
/*     */     public final void flush() {
/* 670 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/* 671 */       if (outboundBuffer == null) {
/*     */         return;
/*     */       }
/*     */       
/* 675 */       outboundBuffer.addFlush();
/* 676 */       flush0();
/*     */     }
/*     */     
/*     */     protected void flush0() {
/* 680 */       if (this.inFlush0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 685 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/* 686 */       if (outboundBuffer == null || outboundBuffer.isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 690 */       this.inFlush0 = true;
/*     */ 
/*     */       
/* 693 */       if (!AbstractChannel.this.isActive()) {
/*     */         try {
/* 695 */           if (AbstractChannel.this.isOpen()) {
/* 696 */             outboundBuffer.failFlushed(AbstractChannel.NOT_YET_CONNECTED_EXCEPTION);
/*     */           } else {
/* 698 */             outboundBuffer.failFlushed(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/*     */           } 
/*     */         } finally {
/* 701 */           this.inFlush0 = false;
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 707 */         AbstractChannel.this.doWrite(outboundBuffer);
/* 708 */       } catch (Throwable t) {
/* 709 */         outboundBuffer.failFlushed(t);
/* 710 */         if (t instanceof java.io.IOException && AbstractChannel.this.config().isAutoClose()) {
/* 711 */           close(voidPromise());
/*     */         }
/*     */       } finally {
/* 714 */         this.inFlush0 = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public final ChannelPromise voidPromise() {
/* 720 */       return AbstractChannel.this.unsafeVoidPromise;
/*     */     }
/*     */     
/*     */     protected final boolean ensureOpen(ChannelPromise promise) {
/* 724 */       if (AbstractChannel.this.isOpen()) {
/* 725 */         return true;
/*     */       }
/*     */       
/* 728 */       safeSetFailure(promise, AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/* 729 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final void safeSetSuccess(ChannelPromise promise) {
/* 736 */       if (!(promise instanceof VoidChannelPromise) && !promise.trySuccess()) {
/* 737 */         AbstractChannel.logger.warn("Failed to mark a promise as success because it is done already: {}", promise);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final void safeSetFailure(ChannelPromise promise, Throwable cause) {
/* 745 */       if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
/* 746 */         AbstractChannel.logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
/*     */       }
/*     */     }
/*     */     
/*     */     protected final void closeIfClosed() {
/* 751 */       if (AbstractChannel.this.isOpen()) {
/*     */         return;
/*     */       }
/* 754 */       close(voidPromise());
/*     */     }
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
/*     */     private void invokeLater(Runnable task) {
/*     */       try {
/* 770 */         AbstractChannel.this.eventLoop().execute(task);
/* 771 */       } catch (RejectedExecutionException e) {
/* 772 */         AbstractChannel.logger.warn("Can't invoke task later as EventLoop rejected it", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract AbstractUnsafe newUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isCompatible(EventLoop paramEventLoop);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract SocketAddress localAddress0();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract SocketAddress remoteAddress0();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doBind(SocketAddress paramSocketAddress) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doDisconnect() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doClose() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doBeginRead() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) throws Exception {
/* 840 */     return msg;
/*     */   }
/*     */   
/*     */   static final class CloseFuture
/*     */     extends DefaultChannelPromise {
/*     */     CloseFuture(AbstractChannel ch) {
/* 846 */       super(ch);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPromise setSuccess() {
/* 851 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPromise setFailure(Throwable cause) {
/* 856 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean trySuccess() {
/* 861 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryFailure(Throwable cause) {
/* 866 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     boolean setClosed() {
/* 870 */       return super.trySuccess();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\AbstractChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */