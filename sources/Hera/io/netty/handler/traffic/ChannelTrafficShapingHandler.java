/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ScheduledExecutorService;
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
/*     */ public class ChannelTrafficShapingHandler
/*     */   extends AbstractTrafficShapingHandler
/*     */ {
/*  54 */   private List<ToSend> messagesQueue = new LinkedList<ToSend>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval, long maxTime) {
/*  71 */     super(writeLimit, readLimit, checkInterval, maxTime);
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
/*     */   public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval) {
/*  87 */     super(writeLimit, readLimit, checkInterval);
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
/*     */   public ChannelTrafficShapingHandler(long writeLimit, long readLimit) {
/* 100 */     super(writeLimit, readLimit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelTrafficShapingHandler(long checkInterval) {
/* 111 */     super(checkInterval);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 116 */     TrafficCounter trafficCounter = new TrafficCounter(this, (ScheduledExecutorService)ctx.executor(), "ChannelTC" + ctx.channel().hashCode(), this.checkInterval);
/*     */     
/* 118 */     setTrafficCounter(trafficCounter);
/* 119 */     trafficCounter.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 124 */     if (this.trafficCounter != null) {
/* 125 */       this.trafficCounter.stop();
/*     */     }
/* 127 */     for (ToSend toSend : this.messagesQueue) {
/* 128 */       if (toSend.toSend instanceof ByteBuf) {
/* 129 */         ((ByteBuf)toSend.toSend).release();
/*     */       }
/*     */     } 
/* 132 */     this.messagesQueue.clear();
/*     */   }
/*     */   
/*     */   private static final class ToSend {
/*     */     final long date;
/*     */     final Object toSend;
/*     */     final ChannelPromise promise;
/*     */     
/*     */     private ToSend(long delay, Object toSend, ChannelPromise promise) {
/* 141 */       this.date = System.currentTimeMillis() + delay;
/* 142 */       this.toSend = toSend;
/* 143 */       this.promise = promise;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void submitWrite(final ChannelHandlerContext ctx, Object msg, long delay, ChannelPromise promise) {
/* 150 */     if (delay == 0L && this.messagesQueue.isEmpty()) {
/* 151 */       ctx.write(msg, promise);
/*     */       return;
/*     */     } 
/* 154 */     ToSend newToSend = new ToSend(delay, msg, promise);
/* 155 */     this.messagesQueue.add(newToSend);
/* 156 */     ctx.executor().schedule(new Runnable()
/*     */         {
/*     */           public void run() {
/* 159 */             ChannelTrafficShapingHandler.this.sendAllValid(ctx);
/*     */           }
/*     */         },  delay, TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */   private synchronized void sendAllValid(ChannelHandlerContext ctx) {
/* 165 */     while (!this.messagesQueue.isEmpty()) {
/* 166 */       ToSend newToSend = this.messagesQueue.remove(0);
/* 167 */       if (newToSend.date <= System.currentTimeMillis()) {
/* 168 */         ctx.write(newToSend.toSend, newToSend.promise); continue;
/*     */       } 
/* 170 */       this.messagesQueue.add(0, newToSend);
/*     */     } 
/*     */ 
/*     */     
/* 174 */     ctx.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\traffic\ChannelTrafficShapingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */