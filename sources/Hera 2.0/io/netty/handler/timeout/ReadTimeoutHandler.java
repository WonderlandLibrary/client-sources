/*     */ package io.netty.handler.timeout;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
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
/*     */ public class ReadTimeoutHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*  65 */   private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*     */ 
/*     */   
/*     */   private final long timeoutNanos;
/*     */ 
/*     */   
/*     */   private volatile ScheduledFuture<?> timeout;
/*     */ 
/*     */   
/*     */   private volatile long lastReadTime;
/*     */ 
/*     */   
/*     */   private volatile int state;
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */   
/*     */   public ReadTimeoutHandler(int timeoutSeconds) {
/*  83 */     this(timeoutSeconds, TimeUnit.SECONDS);
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
/*     */   public ReadTimeoutHandler(long timeout, TimeUnit unit) {
/*  95 */     if (unit == null) {
/*  96 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/*  99 */     if (timeout <= 0L) {
/* 100 */       this.timeoutNanos = 0L;
/*     */     } else {
/* 102 */       this.timeoutNanos = Math.max(unit.toNanos(timeout), MIN_TIMEOUT_NANOS);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 108 */     if (ctx.channel().isActive() && ctx.channel().isRegistered())
/*     */     {
/*     */       
/* 111 */       initialize(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 120 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 126 */     if (ctx.channel().isActive()) {
/* 127 */       initialize(ctx);
/*     */     }
/* 129 */     super.channelRegistered(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 137 */     initialize(ctx);
/* 138 */     super.channelActive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 143 */     destroy();
/* 144 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 149 */     this.lastReadTime = System.nanoTime();
/* 150 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize(ChannelHandlerContext ctx) {
/* 156 */     switch (this.state) {
/*     */       case 1:
/*     */       case 2:
/*     */         return;
/*     */     } 
/*     */     
/* 162 */     this.state = 1;
/*     */     
/* 164 */     this.lastReadTime = System.nanoTime();
/* 165 */     if (this.timeoutNanos > 0L) {
/* 166 */       this.timeout = (ScheduledFuture<?>)ctx.executor().schedule(new ReadTimeoutTask(ctx), this.timeoutNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void destroy() {
/* 173 */     this.state = 2;
/*     */     
/* 175 */     if (this.timeout != null) {
/* 176 */       this.timeout.cancel(false);
/* 177 */       this.timeout = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
/* 185 */     if (!this.closed) {
/* 186 */       ctx.fireExceptionCaught((Throwable)ReadTimeoutException.INSTANCE);
/* 187 */       ctx.close();
/* 188 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private final class ReadTimeoutTask
/*     */     implements Runnable {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     ReadTimeoutTask(ChannelHandlerContext ctx) {
/* 197 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 202 */       if (!this.ctx.channel().isOpen()) {
/*     */         return;
/*     */       }
/*     */       
/* 206 */       long currentTime = System.nanoTime();
/* 207 */       long nextDelay = ReadTimeoutHandler.this.timeoutNanos - currentTime - ReadTimeoutHandler.this.lastReadTime;
/* 208 */       if (nextDelay <= 0L) {
/*     */         
/* 210 */         ReadTimeoutHandler.this.timeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, ReadTimeoutHandler.this.timeoutNanos, TimeUnit.NANOSECONDS);
/*     */         try {
/* 212 */           ReadTimeoutHandler.this.readTimedOut(this.ctx);
/* 213 */         } catch (Throwable t) {
/* 214 */           this.ctx.fireExceptionCaught(t);
/*     */         } 
/*     */       } else {
/*     */         
/* 218 */         ReadTimeoutHandler.this.timeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\timeout\ReadTimeoutHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */