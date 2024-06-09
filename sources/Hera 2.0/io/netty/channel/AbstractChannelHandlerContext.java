/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.DefaultAttributeMap;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutorGroup;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import io.netty.util.internal.RecyclableMpscLinkedQueueNode;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.SocketAddress;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractChannelHandlerContext
/*     */   extends DefaultAttributeMap
/*     */   implements ChannelHandlerContext
/*     */ {
/*     */   volatile AbstractChannelHandlerContext next;
/*     */   volatile AbstractChannelHandlerContext prev;
/*     */   private final boolean inbound;
/*     */   private final boolean outbound;
/*     */   private final AbstractChannel channel;
/*     */   private final DefaultChannelPipeline pipeline;
/*     */   private final String name;
/*     */   private boolean removed;
/*     */   final EventExecutor executor;
/*     */   private ChannelFuture succeededFuture;
/*     */   private volatile Runnable invokeChannelReadCompleteTask;
/*     */   private volatile Runnable invokeReadTask;
/*     */   private volatile Runnable invokeChannelWritableStateChangedTask;
/*     */   private volatile Runnable invokeFlushTask;
/*     */   
/*     */   AbstractChannelHandlerContext(DefaultChannelPipeline pipeline, EventExecutorGroup group, String name, boolean inbound, boolean outbound) {
/*  60 */     if (name == null) {
/*  61 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/*  64 */     this.channel = pipeline.channel;
/*  65 */     this.pipeline = pipeline;
/*  66 */     this.name = name;
/*     */     
/*  68 */     if (group != null) {
/*     */ 
/*     */       
/*  71 */       EventExecutor childExecutor = pipeline.childExecutors.get(group);
/*  72 */       if (childExecutor == null) {
/*  73 */         childExecutor = group.next();
/*  74 */         pipeline.childExecutors.put(group, childExecutor);
/*     */       } 
/*  76 */       this.executor = childExecutor;
/*     */     } else {
/*  78 */       this.executor = null;
/*     */     } 
/*     */     
/*  81 */     this.inbound = inbound;
/*  82 */     this.outbound = outbound;
/*     */   }
/*     */ 
/*     */   
/*     */   void teardown() {
/*  87 */     EventExecutor executor = executor();
/*  88 */     if (executor.inEventLoop()) {
/*  89 */       teardown0();
/*     */     } else {
/*  91 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/*  94 */               AbstractChannelHandlerContext.this.teardown0();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private void teardown0() {
/* 101 */     AbstractChannelHandlerContext prev = this.prev;
/* 102 */     if (prev != null) {
/* 103 */       synchronized (this.pipeline) {
/* 104 */         this.pipeline.remove0(this);
/*     */       } 
/* 106 */       prev.teardown();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel channel() {
/* 112 */     return this.channel;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPipeline pipeline() {
/* 117 */     return this.pipeline;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 122 */     return channel().config().getAllocator();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutor executor() {
/* 127 */     if (this.executor == null) {
/* 128 */       return channel().eventLoop();
/*     */     }
/* 130 */     return this.executor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 136 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelRegistered() {
/* 141 */     final AbstractChannelHandlerContext next = findContextInbound();
/* 142 */     EventExecutor executor = next.executor();
/* 143 */     if (executor.inEventLoop()) {
/* 144 */       next.invokeChannelRegistered();
/*     */     } else {
/* 146 */       executor.execute((Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 149 */               next.invokeChannelRegistered();
/*     */             }
/*     */           });
/*     */     } 
/* 153 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelRegistered() {
/*     */     try {
/* 158 */       ((ChannelInboundHandler)handler()).channelRegistered(this);
/* 159 */     } catch (Throwable t) {
/* 160 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelUnregistered() {
/* 166 */     final AbstractChannelHandlerContext next = findContextInbound();
/* 167 */     EventExecutor executor = next.executor();
/* 168 */     if (executor.inEventLoop()) {
/* 169 */       next.invokeChannelUnregistered();
/*     */     } else {
/* 171 */       executor.execute((Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 174 */               next.invokeChannelUnregistered();
/*     */             }
/*     */           });
/*     */     } 
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelUnregistered() {
/*     */     try {
/* 183 */       ((ChannelInboundHandler)handler()).channelUnregistered(this);
/* 184 */     } catch (Throwable t) {
/* 185 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelActive() {
/* 191 */     final AbstractChannelHandlerContext next = findContextInbound();
/* 192 */     EventExecutor executor = next.executor();
/* 193 */     if (executor.inEventLoop()) {
/* 194 */       next.invokeChannelActive();
/*     */     } else {
/* 196 */       executor.execute((Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 199 */               next.invokeChannelActive();
/*     */             }
/*     */           });
/*     */     } 
/* 203 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelActive() {
/*     */     try {
/* 208 */       ((ChannelInboundHandler)handler()).channelActive(this);
/* 209 */     } catch (Throwable t) {
/* 210 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelInactive() {
/* 216 */     final AbstractChannelHandlerContext next = findContextInbound();
/* 217 */     EventExecutor executor = next.executor();
/* 218 */     if (executor.inEventLoop()) {
/* 219 */       next.invokeChannelInactive();
/*     */     } else {
/* 221 */       executor.execute((Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 224 */               next.invokeChannelInactive();
/*     */             }
/*     */           });
/*     */     } 
/* 228 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelInactive() {
/*     */     try {
/* 233 */       ((ChannelInboundHandler)handler()).channelInactive(this);
/* 234 */     } catch (Throwable t) {
/* 235 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireExceptionCaught(final Throwable cause) {
/* 241 */     if (cause == null) {
/* 242 */       throw new NullPointerException("cause");
/*     */     }
/*     */     
/* 245 */     final AbstractChannelHandlerContext next = this.next;
/*     */     
/* 247 */     EventExecutor executor = next.executor();
/* 248 */     if (executor.inEventLoop()) {
/* 249 */       next.invokeExceptionCaught(cause);
/*     */     } else {
/*     */       try {
/* 252 */         executor.execute((Runnable)new OneTimeTask()
/*     */             {
/*     */               public void run() {
/* 255 */                 next.invokeExceptionCaught(cause);
/*     */               }
/*     */             });
/* 258 */       } catch (Throwable t) {
/* 259 */         if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 260 */           DefaultChannelPipeline.logger.warn("Failed to submit an exceptionCaught() event.", t);
/* 261 */           DefaultChannelPipeline.logger.warn("The exceptionCaught() event that was failed to submit was:", cause);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeExceptionCaught(Throwable cause) {
/*     */     try {
/* 271 */       handler().exceptionCaught(this, cause);
/* 272 */     } catch (Throwable t) {
/* 273 */       if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 274 */         DefaultChannelPipeline.logger.warn("An exception was thrown by a user handler's exceptionCaught() method while handling the following exception:", cause);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireUserEventTriggered(final Object event) {
/* 283 */     if (event == null) {
/* 284 */       throw new NullPointerException("event");
/*     */     }
/*     */     
/* 287 */     final AbstractChannelHandlerContext next = findContextInbound();
/* 288 */     EventExecutor executor = next.executor();
/* 289 */     if (executor.inEventLoop()) {
/* 290 */       next.invokeUserEventTriggered(event);
/*     */     } else {
/* 292 */       executor.execute((Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 295 */               next.invokeUserEventTriggered(event);
/*     */             }
/*     */           });
/*     */     } 
/* 299 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeUserEventTriggered(Object event) {
/*     */     try {
/* 304 */       ((ChannelInboundHandler)handler()).userEventTriggered(this, event);
/* 305 */     } catch (Throwable t) {
/* 306 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelRead(final Object msg) {
/* 312 */     if (msg == null) {
/* 313 */       throw new NullPointerException("msg");
/*     */     }
/*     */     
/* 316 */     final AbstractChannelHandlerContext next = findContextInbound();
/* 317 */     EventExecutor executor = next.executor();
/* 318 */     if (executor.inEventLoop()) {
/* 319 */       next.invokeChannelRead(msg);
/*     */     } else {
/* 321 */       executor.execute((Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 324 */               next.invokeChannelRead(msg);
/*     */             }
/*     */           });
/*     */     } 
/* 328 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelRead(Object msg) {
/*     */     try {
/* 333 */       ((ChannelInboundHandler)handler()).channelRead(this, msg);
/* 334 */     } catch (Throwable t) {
/* 335 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelReadComplete() {
/* 341 */     final AbstractChannelHandlerContext next = findContextInbound();
/* 342 */     EventExecutor executor = next.executor();
/* 343 */     if (executor.inEventLoop()) {
/* 344 */       next.invokeChannelReadComplete();
/*     */     } else {
/* 346 */       Runnable task = next.invokeChannelReadCompleteTask;
/* 347 */       if (task == null) {
/* 348 */         next.invokeChannelReadCompleteTask = task = new Runnable()
/*     */           {
/*     */             public void run() {
/* 351 */               next.invokeChannelReadComplete();
/*     */             }
/*     */           };
/*     */       }
/* 355 */       executor.execute(task);
/*     */     } 
/* 357 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelReadComplete() {
/*     */     try {
/* 362 */       ((ChannelInboundHandler)handler()).channelReadComplete(this);
/* 363 */     } catch (Throwable t) {
/* 364 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelWritabilityChanged() {
/* 370 */     final AbstractChannelHandlerContext next = findContextInbound();
/* 371 */     EventExecutor executor = next.executor();
/* 372 */     if (executor.inEventLoop()) {
/* 373 */       next.invokeChannelWritabilityChanged();
/*     */     } else {
/* 375 */       Runnable task = next.invokeChannelWritableStateChangedTask;
/* 376 */       if (task == null) {
/* 377 */         next.invokeChannelWritableStateChangedTask = task = new Runnable()
/*     */           {
/*     */             public void run() {
/* 380 */               next.invokeChannelWritabilityChanged();
/*     */             }
/*     */           };
/*     */       }
/* 384 */       executor.execute(task);
/*     */     } 
/* 386 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelWritabilityChanged() {
/*     */     try {
/* 391 */       ((ChannelInboundHandler)handler()).channelWritabilityChanged(this);
/* 392 */     } catch (Throwable t) {
/* 393 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress localAddress) {
/* 399 */     return bind(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress) {
/* 404 */     return connect(remoteAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/* 409 */     return connect(remoteAddress, localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect() {
/* 414 */     return disconnect(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 419 */     return close(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister() {
/* 424 */     return deregister(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(final SocketAddress localAddress, final ChannelPromise promise) {
/* 429 */     if (localAddress == null) {
/* 430 */       throw new NullPointerException("localAddress");
/*     */     }
/* 432 */     if (!validatePromise(promise, false))
/*     */     {
/* 434 */       return promise;
/*     */     }
/*     */     
/* 437 */     final AbstractChannelHandlerContext next = findContextOutbound();
/* 438 */     EventExecutor executor = next.executor();
/* 439 */     if (executor.inEventLoop()) {
/* 440 */       next.invokeBind(localAddress, promise);
/*     */     } else {
/* 442 */       safeExecute(executor, (Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 445 */               next.invokeBind(localAddress, promise);
/*     */             }
/*     */           }promise, null);
/*     */     } 
/*     */     
/* 450 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeBind(SocketAddress localAddress, ChannelPromise promise) {
/*     */     try {
/* 455 */       ((ChannelOutboundHandler)handler()).bind(this, localAddress, promise);
/* 456 */     } catch (Throwable t) {
/* 457 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/* 463 */     return connect(remoteAddress, null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
/* 470 */     if (remoteAddress == null) {
/* 471 */       throw new NullPointerException("remoteAddress");
/*     */     }
/* 473 */     if (!validatePromise(promise, false))
/*     */     {
/* 475 */       return promise;
/*     */     }
/*     */     
/* 478 */     final AbstractChannelHandlerContext next = findContextOutbound();
/* 479 */     EventExecutor executor = next.executor();
/* 480 */     if (executor.inEventLoop()) {
/* 481 */       next.invokeConnect(remoteAddress, localAddress, promise);
/*     */     } else {
/* 483 */       safeExecute(executor, (Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 486 */               next.invokeConnect(remoteAddress, localAddress, promise);
/*     */             }
/*     */           }promise, null);
/*     */     } 
/*     */     
/* 491 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeConnect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*     */     try {
/* 496 */       ((ChannelOutboundHandler)handler()).connect(this, remoteAddress, localAddress, promise);
/* 497 */     } catch (Throwable t) {
/* 498 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect(final ChannelPromise promise) {
/* 504 */     if (!validatePromise(promise, false))
/*     */     {
/* 506 */       return promise;
/*     */     }
/*     */     
/* 509 */     final AbstractChannelHandlerContext next = findContextOutbound();
/* 510 */     EventExecutor executor = next.executor();
/* 511 */     if (executor.inEventLoop()) {
/*     */ 
/*     */       
/* 514 */       if (!channel().metadata().hasDisconnect()) {
/* 515 */         next.invokeClose(promise);
/*     */       } else {
/* 517 */         next.invokeDisconnect(promise);
/*     */       } 
/*     */     } else {
/* 520 */       safeExecute(executor, (Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 523 */               if (!AbstractChannelHandlerContext.this.channel().metadata().hasDisconnect()) {
/* 524 */                 next.invokeClose(promise);
/*     */               } else {
/* 526 */                 next.invokeDisconnect(promise);
/*     */               } 
/*     */             }
/*     */           },  promise, null);
/*     */     } 
/*     */     
/* 532 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeDisconnect(ChannelPromise promise) {
/*     */     try {
/* 537 */       ((ChannelOutboundHandler)handler()).disconnect(this, promise);
/* 538 */     } catch (Throwable t) {
/* 539 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise promise) {
/* 545 */     if (!validatePromise(promise, false))
/*     */     {
/* 547 */       return promise;
/*     */     }
/*     */     
/* 550 */     final AbstractChannelHandlerContext next = findContextOutbound();
/* 551 */     EventExecutor executor = next.executor();
/* 552 */     if (executor.inEventLoop()) {
/* 553 */       next.invokeClose(promise);
/*     */     } else {
/* 555 */       safeExecute(executor, (Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 558 */               next.invokeClose(promise);
/*     */             }
/*     */           },  promise, null);
/*     */     } 
/*     */     
/* 563 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeClose(ChannelPromise promise) {
/*     */     try {
/* 568 */       ((ChannelOutboundHandler)handler()).close(this, promise);
/* 569 */     } catch (Throwable t) {
/* 570 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister(final ChannelPromise promise) {
/* 576 */     if (!validatePromise(promise, false))
/*     */     {
/* 578 */       return promise;
/*     */     }
/*     */     
/* 581 */     final AbstractChannelHandlerContext next = findContextOutbound();
/* 582 */     EventExecutor executor = next.executor();
/* 583 */     if (executor.inEventLoop()) {
/* 584 */       next.invokeDeregister(promise);
/*     */     } else {
/* 586 */       safeExecute(executor, (Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 589 */               next.invokeDeregister(promise);
/*     */             }
/*     */           },  promise, null);
/*     */     } 
/*     */     
/* 594 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeDeregister(ChannelPromise promise) {
/*     */     try {
/* 599 */       ((ChannelOutboundHandler)handler()).deregister(this, promise);
/* 600 */     } catch (Throwable t) {
/* 601 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext read() {
/* 607 */     final AbstractChannelHandlerContext next = findContextOutbound();
/* 608 */     EventExecutor executor = next.executor();
/* 609 */     if (executor.inEventLoop()) {
/* 610 */       next.invokeRead();
/*     */     } else {
/* 612 */       Runnable task = next.invokeReadTask;
/* 613 */       if (task == null) {
/* 614 */         next.invokeReadTask = task = new Runnable()
/*     */           {
/*     */             public void run() {
/* 617 */               next.invokeRead();
/*     */             }
/*     */           };
/*     */       }
/* 621 */       executor.execute(task);
/*     */     } 
/*     */     
/* 624 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeRead() {
/*     */     try {
/* 629 */       ((ChannelOutboundHandler)handler()).read(this);
/* 630 */     } catch (Throwable t) {
/* 631 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object msg) {
/* 637 */     return write(msg, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object msg, ChannelPromise promise) {
/* 642 */     if (msg == null) {
/* 643 */       throw new NullPointerException("msg");
/*     */     }
/*     */     
/* 646 */     if (!validatePromise(promise, true)) {
/* 647 */       ReferenceCountUtil.release(msg);
/*     */       
/* 649 */       return promise;
/*     */     } 
/* 651 */     write(msg, false, promise);
/*     */     
/* 653 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeWrite(Object msg, ChannelPromise promise) {
/*     */     try {
/* 658 */       ((ChannelOutboundHandler)handler()).write(this, msg, promise);
/* 659 */     } catch (Throwable t) {
/* 660 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext flush() {
/* 666 */     final AbstractChannelHandlerContext next = findContextOutbound();
/* 667 */     EventExecutor executor = next.executor();
/* 668 */     if (executor.inEventLoop()) {
/* 669 */       next.invokeFlush();
/*     */     } else {
/* 671 */       Runnable task = next.invokeFlushTask;
/* 672 */       if (task == null) {
/* 673 */         next.invokeFlushTask = task = new Runnable()
/*     */           {
/*     */             public void run() {
/* 676 */               next.invokeFlush();
/*     */             }
/*     */           };
/*     */       }
/* 680 */       safeExecute(executor, task, this.channel.voidPromise(), null);
/*     */     } 
/*     */     
/* 683 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeFlush() {
/*     */     try {
/* 688 */       ((ChannelOutboundHandler)handler()).flush(this);
/* 689 */     } catch (Throwable t) {
/* 690 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/* 696 */     if (msg == null) {
/* 697 */       throw new NullPointerException("msg");
/*     */     }
/*     */     
/* 700 */     if (!validatePromise(promise, true)) {
/* 701 */       ReferenceCountUtil.release(msg);
/*     */       
/* 703 */       return promise;
/*     */     } 
/*     */     
/* 706 */     write(msg, true, promise);
/*     */     
/* 708 */     return promise;
/*     */   }
/*     */ 
/*     */   
/*     */   private void write(Object msg, boolean flush, ChannelPromise promise) {
/* 713 */     AbstractChannelHandlerContext next = findContextOutbound();
/* 714 */     EventExecutor executor = next.executor();
/* 715 */     if (executor.inEventLoop()) {
/* 716 */       next.invokeWrite(msg, promise);
/* 717 */       if (flush)
/* 718 */         next.invokeFlush(); 
/*     */     } else {
/*     */       Runnable task;
/* 721 */       int size = this.channel.estimatorHandle().size(msg);
/* 722 */       if (size > 0) {
/* 723 */         ChannelOutboundBuffer buffer = this.channel.unsafe().outboundBuffer();
/*     */         
/* 725 */         if (buffer != null) {
/* 726 */           buffer.incrementPendingOutboundBytes(size);
/*     */         }
/*     */       } 
/*     */       
/* 730 */       if (flush) {
/* 731 */         task = WriteAndFlushTask.newInstance(next, msg, size, promise);
/*     */       } else {
/* 733 */         task = WriteTask.newInstance(next, msg, size, promise);
/*     */       } 
/* 735 */       safeExecute(executor, task, promise, msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object msg) {
/* 741 */     return writeAndFlush(msg, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void notifyOutboundHandlerException(Throwable cause, ChannelPromise promise) {
/* 747 */     if (promise instanceof VoidChannelPromise) {
/*     */       return;
/*     */     }
/*     */     
/* 751 */     if (!promise.tryFailure(cause) && 
/* 752 */       DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 753 */       DefaultChannelPipeline.logger.warn("Failed to fail the promise because it's done already: {}", promise, cause);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyHandlerException(Throwable cause) {
/* 759 */     if (inExceptionCaught(cause)) {
/* 760 */       if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 761 */         DefaultChannelPipeline.logger.warn("An exception was thrown by a user handler while handling an exceptionCaught event", cause);
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 768 */     invokeExceptionCaught(cause);
/*     */   }
/*     */   
/*     */   private static boolean inExceptionCaught(Throwable cause) {
/*     */     do {
/* 773 */       StackTraceElement[] trace = cause.getStackTrace();
/* 774 */       if (trace != null) {
/* 775 */         for (StackTraceElement t : trace) {
/* 776 */           if (t == null) {
/*     */             break;
/*     */           }
/* 779 */           if ("exceptionCaught".equals(t.getMethodName())) {
/* 780 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 785 */       cause = cause.getCause();
/* 786 */     } while (cause != null);
/*     */     
/* 788 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPromise newPromise() {
/* 793 */     return new DefaultChannelPromise(channel(), executor());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelProgressivePromise newProgressivePromise() {
/* 798 */     return new DefaultChannelProgressivePromise(channel(), executor());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newSucceededFuture() {
/* 803 */     ChannelFuture succeededFuture = this.succeededFuture;
/* 804 */     if (succeededFuture == null) {
/* 805 */       this.succeededFuture = succeededFuture = new SucceededChannelFuture(channel(), executor());
/*     */     }
/* 807 */     return succeededFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newFailedFuture(Throwable cause) {
/* 812 */     return new FailedChannelFuture(channel(), executor(), cause);
/*     */   }
/*     */   
/*     */   private boolean validatePromise(ChannelPromise promise, boolean allowVoidPromise) {
/* 816 */     if (promise == null) {
/* 817 */       throw new NullPointerException("promise");
/*     */     }
/*     */     
/* 820 */     if (promise.isDone()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 825 */       if (promise.isCancelled()) {
/* 826 */         return false;
/*     */       }
/* 828 */       throw new IllegalArgumentException("promise already done: " + promise);
/*     */     } 
/*     */     
/* 831 */     if (promise.channel() != channel()) {
/* 832 */       throw new IllegalArgumentException(String.format("promise.channel does not match: %s (expected: %s)", new Object[] { promise.channel(), channel() }));
/*     */     }
/*     */ 
/*     */     
/* 836 */     if (promise.getClass() == DefaultChannelPromise.class) {
/* 837 */       return true;
/*     */     }
/*     */     
/* 840 */     if (!allowVoidPromise && promise instanceof VoidChannelPromise) {
/* 841 */       throw new IllegalArgumentException(StringUtil.simpleClassName(VoidChannelPromise.class) + " not allowed for this operation");
/*     */     }
/*     */ 
/*     */     
/* 845 */     if (promise instanceof AbstractChannel.CloseFuture) {
/* 846 */       throw new IllegalArgumentException(StringUtil.simpleClassName(AbstractChannel.CloseFuture.class) + " not allowed in a pipeline");
/*     */     }
/*     */     
/* 849 */     return true;
/*     */   }
/*     */   
/*     */   private AbstractChannelHandlerContext findContextInbound() {
/* 853 */     AbstractChannelHandlerContext ctx = this;
/*     */     while (true) {
/* 855 */       ctx = ctx.next;
/* 856 */       if (ctx.inbound)
/* 857 */         return ctx; 
/*     */     } 
/*     */   }
/*     */   private AbstractChannelHandlerContext findContextOutbound() {
/* 861 */     AbstractChannelHandlerContext ctx = this;
/*     */     while (true) {
/* 863 */       ctx = ctx.prev;
/* 864 */       if (ctx.outbound)
/* 865 */         return ctx; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ChannelPromise voidPromise() {
/* 870 */     return this.channel.voidPromise();
/*     */   }
/*     */   
/*     */   void setRemoved() {
/* 874 */     this.removed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRemoved() {
/* 879 */     return this.removed;
/*     */   }
/*     */   
/*     */   private static void safeExecute(EventExecutor executor, Runnable runnable, ChannelPromise promise, Object msg) {
/*     */     try {
/* 884 */       executor.execute(runnable);
/* 885 */     } catch (Throwable cause) {
/*     */       try {
/* 887 */         promise.setFailure(cause);
/*     */       } finally {
/* 889 */         if (msg != null)
/* 890 */           ReferenceCountUtil.release(msg); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static abstract class AbstractWriteTask
/*     */     extends RecyclableMpscLinkedQueueNode<Runnable> implements Runnable {
/*     */     private AbstractChannelHandlerContext ctx;
/*     */     private Object msg;
/*     */     private ChannelPromise promise;
/*     */     private int size;
/*     */     
/*     */     private AbstractWriteTask(Recycler.Handle handle) {
/* 903 */       super(handle);
/*     */     }
/*     */ 
/*     */     
/*     */     protected static void init(AbstractWriteTask task, AbstractChannelHandlerContext ctx, Object msg, int size, ChannelPromise promise) {
/* 908 */       task.ctx = ctx;
/* 909 */       task.msg = msg;
/* 910 */       task.promise = promise;
/* 911 */       task.size = size;
/*     */     }
/*     */ 
/*     */     
/*     */     public final void run() {
/*     */       try {
/* 917 */         if (this.size > 0) {
/* 918 */           ChannelOutboundBuffer buffer = this.ctx.channel.unsafe().outboundBuffer();
/*     */           
/* 920 */           if (buffer != null) {
/* 921 */             buffer.decrementPendingOutboundBytes(this.size);
/*     */           }
/*     */         } 
/* 924 */         write(this.ctx, this.msg, this.promise);
/*     */       } finally {
/*     */         
/* 927 */         this.ctx = null;
/* 928 */         this.msg = null;
/* 929 */         this.promise = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Runnable value() {
/* 935 */       return this;
/*     */     }
/*     */     
/*     */     protected void write(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
/* 939 */       ctx.invokeWrite(msg, promise);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class WriteTask
/*     */     extends AbstractWriteTask implements SingleThreadEventLoop.NonWakeupRunnable {
/* 945 */     private static final Recycler<WriteTask> RECYCLER = new Recycler<WriteTask>()
/*     */       {
/*     */         protected AbstractChannelHandlerContext.WriteTask newObject(Recycler.Handle handle) {
/* 948 */           return new AbstractChannelHandlerContext.WriteTask(handle);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     private static WriteTask newInstance(AbstractChannelHandlerContext ctx, Object msg, int size, ChannelPromise promise) {
/* 954 */       WriteTask task = (WriteTask)RECYCLER.get();
/* 955 */       init(task, ctx, msg, size, promise);
/* 956 */       return task;
/*     */     }
/*     */     
/*     */     private WriteTask(Recycler.Handle handle) {
/* 960 */       super(handle);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void recycle(Recycler.Handle handle) {
/* 965 */       RECYCLER.recycle(this, handle);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class WriteAndFlushTask
/*     */     extends AbstractWriteTask {
/* 971 */     private static final Recycler<WriteAndFlushTask> RECYCLER = new Recycler<WriteAndFlushTask>()
/*     */       {
/*     */         protected AbstractChannelHandlerContext.WriteAndFlushTask newObject(Recycler.Handle handle) {
/* 974 */           return new AbstractChannelHandlerContext.WriteAndFlushTask(handle);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     private static WriteAndFlushTask newInstance(AbstractChannelHandlerContext ctx, Object msg, int size, ChannelPromise promise) {
/* 980 */       WriteAndFlushTask task = (WriteAndFlushTask)RECYCLER.get();
/* 981 */       init(task, ctx, msg, size, promise);
/* 982 */       return task;
/*     */     }
/*     */     
/*     */     private WriteAndFlushTask(Recycler.Handle handle) {
/* 986 */       super(handle);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
/* 991 */       super.write(ctx, msg, promise);
/* 992 */       ctx.invokeFlush();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void recycle(Recycler.Handle handle) {
/* 997 */       RECYCLER.recycle(this, handle);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\AbstractChannelHandlerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */