/*     */ package io.netty.handler.timeout;
/*     */ 
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.ScheduledFuture;
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
/*     */ public class WriteTimeoutHandler
/*     */   extends ChannelOutboundHandlerAdapter
/*     */ {
/*  68 */   private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*     */ 
/*     */ 
/*     */   
/*     */   private final long timeoutNanos;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */ 
/*     */   
/*     */   public WriteTimeoutHandler(int timeoutSeconds) {
/*  81 */     this(timeoutSeconds, TimeUnit.SECONDS);
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
/*     */   public WriteTimeoutHandler(long timeout, TimeUnit unit) {
/*  93 */     if (unit == null) {
/*  94 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/*  97 */     if (timeout <= 0L) {
/*  98 */       this.timeoutNanos = 0L;
/*     */     } else {
/* 100 */       this.timeoutNanos = Math.max(unit.toNanos(timeout), MIN_TIMEOUT_NANOS);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 106 */     scheduleTimeout(ctx, promise);
/* 107 */     ctx.write(msg, promise);
/*     */   }
/*     */   
/*     */   private void scheduleTimeout(final ChannelHandlerContext ctx, final ChannelPromise future) {
/* 111 */     if (this.timeoutNanos > 0L) {
/*     */       
/* 113 */       final ScheduledFuture sf = ctx.executor().schedule(new Runnable()
/*     */           {
/*     */ 
/*     */             
/*     */             public void run()
/*     */             {
/* 119 */               if (!future.isDone()) {
/*     */                 try {
/* 121 */                   WriteTimeoutHandler.this.writeTimedOut(ctx);
/* 122 */                 } catch (Throwable t) {
/* 123 */                   ctx.fireExceptionCaught(t);
/*     */                 } 
/*     */               }
/*     */             }
/*     */           },  this.timeoutNanos, TimeUnit.NANOSECONDS);
/*     */ 
/*     */       
/* 130 */       future.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) throws Exception {
/* 133 */               sf.cancel(false);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeTimedOut(ChannelHandlerContext ctx) throws Exception {
/* 143 */     if (!this.closed) {
/* 144 */       ctx.fireExceptionCaught((Throwable)WriteTimeoutException.INSTANCE);
/* 145 */       ctx.close();
/* 146 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\timeout\WriteTimeoutHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */