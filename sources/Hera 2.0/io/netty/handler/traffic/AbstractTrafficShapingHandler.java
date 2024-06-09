/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public abstract class AbstractTrafficShapingHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  45 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractTrafficShapingHandler.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long DEFAULT_CHECK_INTERVAL = 1000L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long DEFAULT_MAX_TIME = 15000L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final long MINIMAL_WAIT = 10L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TrafficCounter trafficCounter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long writeLimit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long readLimit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected long maxTime = 15000L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   protected long checkInterval = 1000L;
/*     */   
/*  89 */   private static final AttributeKey<Boolean> READ_SUSPENDED = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".READ_SUSPENDED");
/*     */   
/*  91 */   private static final AttributeKey<Runnable> REOPEN_TASK = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".REOPEN_TASK");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTrafficCounter(TrafficCounter newTrafficCounter) {
/* 100 */     this.trafficCounter = newTrafficCounter;
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
/*     */   protected AbstractTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval, long maxTime) {
/* 115 */     this.writeLimit = writeLimit;
/* 116 */     this.readLimit = readLimit;
/* 117 */     this.checkInterval = checkInterval;
/* 118 */     this.maxTime = maxTime;
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
/*     */   protected AbstractTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval) {
/* 131 */     this(writeLimit, readLimit, checkInterval, 15000L);
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
/*     */   protected AbstractTrafficShapingHandler(long writeLimit, long readLimit) {
/* 143 */     this(writeLimit, readLimit, 1000L, 15000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractTrafficShapingHandler() {
/* 150 */     this(0L, 0L, 1000L, 15000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractTrafficShapingHandler(long checkInterval) {
/* 161 */     this(0L, 0L, checkInterval, 15000L);
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
/*     */   public void configure(long newWriteLimit, long newReadLimit, long newCheckInterval) {
/* 175 */     configure(newWriteLimit, newReadLimit);
/* 176 */     configure(newCheckInterval);
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
/*     */   public void configure(long newWriteLimit, long newReadLimit) {
/* 188 */     this.writeLimit = newWriteLimit;
/* 189 */     this.readLimit = newReadLimit;
/* 190 */     if (this.trafficCounter != null) {
/* 191 */       this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(long newCheckInterval) {
/* 202 */     this.checkInterval = newCheckInterval;
/* 203 */     if (this.trafficCounter != null) {
/* 204 */       this.trafficCounter.configure(this.checkInterval);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWriteLimit() {
/* 212 */     return this.writeLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWriteLimit(long writeLimit) {
/* 219 */     this.writeLimit = writeLimit;
/* 220 */     if (this.trafficCounter != null) {
/* 221 */       this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getReadLimit() {
/* 229 */     return this.readLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadLimit(long readLimit) {
/* 236 */     this.readLimit = readLimit;
/* 237 */     if (this.trafficCounter != null) {
/* 238 */       this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCheckInterval() {
/* 246 */     return this.checkInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCheckInterval(long checkInterval) {
/* 253 */     this.checkInterval = checkInterval;
/* 254 */     if (this.trafficCounter != null) {
/* 255 */       this.trafficCounter.configure(checkInterval);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxTimeWait(long maxTime) {
/* 265 */     this.maxTime = maxTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxTimeWait() {
/* 272 */     return this.maxTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doAccounting(TrafficCounter counter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ReopenReadTimerTask
/*     */     implements Runnable
/*     */   {
/*     */     final ChannelHandlerContext ctx;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ReopenReadTimerTask(ChannelHandlerContext ctx) {
/* 293 */       this.ctx = ctx;
/*     */     }
/*     */     
/*     */     public void run() {
/* 297 */       if (!this.ctx.channel().config().isAutoRead() && AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
/*     */ 
/*     */         
/* 300 */         if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 301 */           AbstractTrafficShapingHandler.logger.debug("Channel:" + this.ctx.channel().hashCode() + " Not Unsuspend: " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */         }
/*     */         
/* 304 */         this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(Boolean.valueOf(false));
/*     */       } else {
/*     */         
/* 307 */         if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 308 */           if (this.ctx.channel().config().isAutoRead() && !AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
/* 309 */             AbstractTrafficShapingHandler.logger.debug("Channel:" + this.ctx.channel().hashCode() + " Unsuspend: " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */           } else {
/*     */             
/* 312 */             AbstractTrafficShapingHandler.logger.debug("Channel:" + this.ctx.channel().hashCode() + " Normal Unsuspend: " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 317 */         this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(Boolean.valueOf(false));
/* 318 */         this.ctx.channel().config().setAutoRead(true);
/* 319 */         this.ctx.channel().read();
/*     */       } 
/* 321 */       if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 322 */         AbstractTrafficShapingHandler.logger.debug("Channel:" + this.ctx.channel().hashCode() + " Unsupsend final status => " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 331 */     long size = calculateSize(msg);
/*     */     
/* 333 */     if (size > 0L && this.trafficCounter != null) {
/*     */       
/* 335 */       long wait = this.trafficCounter.readTimeToWait(size, this.readLimit, this.maxTime);
/* 336 */       if (wait >= 10L) {
/*     */ 
/*     */         
/* 339 */         if (logger.isDebugEnabled()) {
/* 340 */           logger.debug("Channel:" + ctx.channel().hashCode() + " Read Suspend: " + wait + ":" + ctx.channel().config().isAutoRead() + ":" + isHandlerActive(ctx));
/*     */         }
/*     */ 
/*     */         
/* 344 */         if (ctx.channel().config().isAutoRead() && isHandlerActive(ctx)) {
/* 345 */           ctx.channel().config().setAutoRead(false);
/* 346 */           ctx.attr(READ_SUSPENDED).set(Boolean.valueOf(true));
/*     */ 
/*     */           
/* 349 */           Attribute<Runnable> attr = ctx.attr(REOPEN_TASK);
/* 350 */           Runnable reopenTask = (Runnable)attr.get();
/* 351 */           if (reopenTask == null) {
/* 352 */             reopenTask = new ReopenReadTimerTask(ctx);
/* 353 */             attr.set(reopenTask);
/*     */           } 
/* 355 */           ctx.executor().schedule(reopenTask, wait, TimeUnit.MILLISECONDS);
/* 356 */           if (logger.isDebugEnabled()) {
/* 357 */             logger.debug("Channel:" + ctx.channel().hashCode() + " Suspend final status => " + ctx.channel().config().isAutoRead() + ":" + isHandlerActive(ctx) + " will reopened at: " + wait);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 365 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */   
/*     */   protected static boolean isHandlerActive(ChannelHandlerContext ctx) {
/* 369 */     Boolean suspended = (Boolean)ctx.attr(READ_SUSPENDED).get();
/* 370 */     return (suspended == null || Boolean.FALSE.equals(suspended));
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) {
/* 375 */     if (isHandlerActive(ctx))
/*     */     {
/* 377 */       ctx.read();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 384 */     long size = calculateSize(msg);
/*     */     
/* 386 */     if (size > 0L && this.trafficCounter != null) {
/*     */       
/* 388 */       long wait = this.trafficCounter.writeTimeToWait(size, this.writeLimit, this.maxTime);
/* 389 */       if (wait >= 10L) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 397 */         if (logger.isDebugEnabled()) {
/* 398 */           logger.debug("Channel:" + ctx.channel().hashCode() + " Write suspend: " + wait + ":" + ctx.channel().config().isAutoRead() + ":" + isHandlerActive(ctx));
/*     */         }
/*     */ 
/*     */         
/* 402 */         submitWrite(ctx, msg, wait, promise);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 407 */     submitWrite(ctx, msg, 0L, promise);
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
/*     */   public TrafficCounter trafficCounter() {
/* 419 */     return this.trafficCounter;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 424 */     return "TrafficShaping with Write Limit: " + this.writeLimit + " Read Limit: " + this.readLimit + " and Counter: " + ((this.trafficCounter != null) ? this.trafficCounter.toString() : "none");
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
/*     */   protected long calculateSize(Object msg) {
/* 438 */     if (msg instanceof ByteBuf) {
/* 439 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 441 */     if (msg instanceof ByteBufHolder) {
/* 442 */       return ((ByteBufHolder)msg).content().readableBytes();
/*     */     }
/* 444 */     return -1L;
/*     */   }
/*     */   
/*     */   protected abstract void submitWrite(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, long paramLong, ChannelPromise paramChannelPromise);
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\traffic\AbstractTrafficShapingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */