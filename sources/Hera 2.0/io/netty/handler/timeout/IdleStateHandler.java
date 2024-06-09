/*     */ package io.netty.handler.timeout;
/*     */ 
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
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
/*     */ public class IdleStateHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  98 */   private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*     */ 
/*     */   
/*     */   private final long readerIdleTimeNanos;
/*     */ 
/*     */   
/*     */   private final long writerIdleTimeNanos;
/*     */ 
/*     */   
/*     */   private final long allIdleTimeNanos;
/*     */ 
/*     */   
/*     */   volatile ScheduledFuture<?> readerIdleTimeout;
/*     */ 
/*     */   
/*     */   volatile long lastReadTime;
/*     */ 
/*     */   
/*     */   private boolean firstReaderIdleEvent = true;
/*     */ 
/*     */   
/*     */   volatile ScheduledFuture<?> writerIdleTimeout;
/*     */ 
/*     */   
/*     */   volatile long lastWriteTime;
/*     */ 
/*     */   
/*     */   private boolean firstWriterIdleEvent = true;
/*     */ 
/*     */   
/*     */   volatile ScheduledFuture<?> allIdleTimeout;
/*     */ 
/*     */   
/*     */   private boolean firstAllIdleEvent = true;
/*     */ 
/*     */   
/*     */   private volatile int state;
/*     */ 
/*     */   
/*     */   public IdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
/* 138 */     this(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds, TimeUnit.SECONDS);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
/* 164 */     if (unit == null) {
/* 165 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 168 */     if (readerIdleTime <= 0L) {
/* 169 */       this.readerIdleTimeNanos = 0L;
/*     */     } else {
/* 171 */       this.readerIdleTimeNanos = Math.max(unit.toNanos(readerIdleTime), MIN_TIMEOUT_NANOS);
/*     */     } 
/* 173 */     if (writerIdleTime <= 0L) {
/* 174 */       this.writerIdleTimeNanos = 0L;
/*     */     } else {
/* 176 */       this.writerIdleTimeNanos = Math.max(unit.toNanos(writerIdleTime), MIN_TIMEOUT_NANOS);
/*     */     } 
/* 178 */     if (allIdleTime <= 0L) {
/* 179 */       this.allIdleTimeNanos = 0L;
/*     */     } else {
/* 181 */       this.allIdleTimeNanos = Math.max(unit.toNanos(allIdleTime), MIN_TIMEOUT_NANOS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getReaderIdleTimeInMillis() {
/* 190 */     return TimeUnit.NANOSECONDS.toMillis(this.readerIdleTimeNanos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWriterIdleTimeInMillis() {
/* 198 */     return TimeUnit.NANOSECONDS.toMillis(this.writerIdleTimeNanos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAllIdleTimeInMillis() {
/* 206 */     return TimeUnit.NANOSECONDS.toMillis(this.allIdleTimeNanos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 211 */     if (ctx.channel().isActive() && ctx.channel().isRegistered())
/*     */     {
/*     */       
/* 214 */       initialize(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 223 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 229 */     if (ctx.channel().isActive()) {
/* 230 */       initialize(ctx);
/*     */     }
/* 232 */     super.channelRegistered(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 240 */     initialize(ctx);
/* 241 */     super.channelActive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 246 */     destroy();
/* 247 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 252 */     this.lastReadTime = System.nanoTime();
/* 253 */     this.firstReaderIdleEvent = this.firstAllIdleEvent = true;
/* 254 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 259 */     promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/* 262 */             IdleStateHandler.this.lastWriteTime = System.nanoTime();
/* 263 */             IdleStateHandler.this.firstWriterIdleEvent = IdleStateHandler.this.firstAllIdleEvent = true;
/*     */           }
/*     */         });
/* 266 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize(ChannelHandlerContext ctx) {
/* 272 */     switch (this.state) {
/*     */       case 1:
/*     */       case 2:
/*     */         return;
/*     */     } 
/*     */     
/* 278 */     this.state = 1;
/*     */     
/* 280 */     EventExecutor loop = ctx.executor();
/*     */     
/* 282 */     this.lastReadTime = this.lastWriteTime = System.nanoTime();
/* 283 */     if (this.readerIdleTimeNanos > 0L) {
/* 284 */       this.readerIdleTimeout = (ScheduledFuture<?>)loop.schedule(new ReaderIdleTimeoutTask(ctx), this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */ 
/*     */     
/* 288 */     if (this.writerIdleTimeNanos > 0L) {
/* 289 */       this.writerIdleTimeout = (ScheduledFuture<?>)loop.schedule(new WriterIdleTimeoutTask(ctx), this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */ 
/*     */     
/* 293 */     if (this.allIdleTimeNanos > 0L) {
/* 294 */       this.allIdleTimeout = (ScheduledFuture<?>)loop.schedule(new AllIdleTimeoutTask(ctx), this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void destroy() {
/* 301 */     this.state = 2;
/*     */     
/* 303 */     if (this.readerIdleTimeout != null) {
/* 304 */       this.readerIdleTimeout.cancel(false);
/* 305 */       this.readerIdleTimeout = null;
/*     */     } 
/* 307 */     if (this.writerIdleTimeout != null) {
/* 308 */       this.writerIdleTimeout.cancel(false);
/* 309 */       this.writerIdleTimeout = null;
/*     */     } 
/* 311 */     if (this.allIdleTimeout != null) {
/* 312 */       this.allIdleTimeout.cancel(false);
/* 313 */       this.allIdleTimeout = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
/* 322 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */   
/*     */   private final class ReaderIdleTimeoutTask
/*     */     implements Runnable {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     ReaderIdleTimeoutTask(ChannelHandlerContext ctx) {
/* 330 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 335 */       if (!this.ctx.channel().isOpen()) {
/*     */         return;
/*     */       }
/*     */       
/* 339 */       long currentTime = System.nanoTime();
/* 340 */       long lastReadTime = IdleStateHandler.this.lastReadTime;
/* 341 */       long nextDelay = IdleStateHandler.this.readerIdleTimeNanos - currentTime - lastReadTime;
/* 342 */       if (nextDelay <= 0L) {
/*     */         
/* 344 */         IdleStateHandler.this.readerIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, IdleStateHandler.this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */         
/*     */         try {
/*     */           IdleStateEvent event;
/* 348 */           if (IdleStateHandler.this.firstReaderIdleEvent) {
/* 349 */             IdleStateHandler.this.firstReaderIdleEvent = false;
/* 350 */             event = IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT;
/*     */           } else {
/* 352 */             event = IdleStateEvent.READER_IDLE_STATE_EVENT;
/*     */           } 
/* 354 */           IdleStateHandler.this.channelIdle(this.ctx, event);
/* 355 */         } catch (Throwable t) {
/* 356 */           this.ctx.fireExceptionCaught(t);
/*     */         } 
/*     */       } else {
/*     */         
/* 360 */         IdleStateHandler.this.readerIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class WriterIdleTimeoutTask
/*     */     implements Runnable {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     WriterIdleTimeoutTask(ChannelHandlerContext ctx) {
/* 370 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 375 */       if (!this.ctx.channel().isOpen()) {
/*     */         return;
/*     */       }
/*     */       
/* 379 */       long currentTime = System.nanoTime();
/* 380 */       long lastWriteTime = IdleStateHandler.this.lastWriteTime;
/* 381 */       long nextDelay = IdleStateHandler.this.writerIdleTimeNanos - currentTime - lastWriteTime;
/* 382 */       if (nextDelay <= 0L) {
/*     */         
/* 384 */         IdleStateHandler.this.writerIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, IdleStateHandler.this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */         
/*     */         try {
/*     */           IdleStateEvent event;
/* 388 */           if (IdleStateHandler.this.firstWriterIdleEvent) {
/* 389 */             IdleStateHandler.this.firstWriterIdleEvent = false;
/* 390 */             event = IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT;
/*     */           } else {
/* 392 */             event = IdleStateEvent.WRITER_IDLE_STATE_EVENT;
/*     */           } 
/* 394 */           IdleStateHandler.this.channelIdle(this.ctx, event);
/* 395 */         } catch (Throwable t) {
/* 396 */           this.ctx.fireExceptionCaught(t);
/*     */         } 
/*     */       } else {
/*     */         
/* 400 */         IdleStateHandler.this.writerIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class AllIdleTimeoutTask
/*     */     implements Runnable {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     AllIdleTimeoutTask(ChannelHandlerContext ctx) {
/* 410 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 415 */       if (!this.ctx.channel().isOpen()) {
/*     */         return;
/*     */       }
/*     */       
/* 419 */       long currentTime = System.nanoTime();
/* 420 */       long lastIoTime = Math.max(IdleStateHandler.this.lastReadTime, IdleStateHandler.this.lastWriteTime);
/* 421 */       long nextDelay = IdleStateHandler.this.allIdleTimeNanos - currentTime - lastIoTime;
/* 422 */       if (nextDelay <= 0L) {
/*     */ 
/*     */         
/* 425 */         IdleStateHandler.this.allIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, IdleStateHandler.this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */         
/*     */         try {
/*     */           IdleStateEvent event;
/* 429 */           if (IdleStateHandler.this.firstAllIdleEvent) {
/* 430 */             IdleStateHandler.this.firstAllIdleEvent = false;
/* 431 */             event = IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT;
/*     */           } else {
/* 433 */             event = IdleStateEvent.ALL_IDLE_STATE_EVENT;
/*     */           } 
/* 435 */           IdleStateHandler.this.channelIdle(this.ctx, event);
/* 436 */         } catch (Throwable t) {
/* 437 */           this.ctx.fireExceptionCaught(t);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 442 */         IdleStateHandler.this.allIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\timeout\IdleStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */