/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Sharable
/*     */ public class GlobalTrafficShapingHandler
/*     */   extends AbstractTrafficShapingHandler
/*     */ {
/*  66 */   private Map<Integer, List<ToSend>> messagesQueues = new HashMap<Integer, List<ToSend>>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void createGlobalTrafficCounter(ScheduledExecutorService executor) {
/*  72 */     if (executor == null) {
/*  73 */       throw new NullPointerException("executor");
/*     */     }
/*  75 */     TrafficCounter tc = new TrafficCounter(this, executor, "GlobalTC", this.checkInterval);
/*     */     
/*  77 */     setTrafficCounter(tc);
/*  78 */     tc.start();
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
/*     */   public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit, long checkInterval, long maxTime) {
/*  98 */     super(writeLimit, readLimit, checkInterval, maxTime);
/*  99 */     createGlobalTrafficCounter(executor);
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
/*     */   public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit, long checkInterval) {
/* 117 */     super(writeLimit, readLimit, checkInterval);
/* 118 */     createGlobalTrafficCounter(executor);
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
/*     */   public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit) {
/* 133 */     super(writeLimit, readLimit);
/* 134 */     createGlobalTrafficCounter(executor);
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
/*     */   public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long checkInterval) {
/* 147 */     super(checkInterval);
/* 148 */     createGlobalTrafficCounter(executor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlobalTrafficShapingHandler(EventExecutor executor) {
/* 158 */     createGlobalTrafficCounter((ScheduledExecutorService)executor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void release() {
/* 165 */     if (this.trafficCounter != null) {
/* 166 */       this.trafficCounter.stop();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 172 */     Integer key = Integer.valueOf(ctx.channel().hashCode());
/* 173 */     List<ToSend> mq = new LinkedList<ToSend>();
/* 174 */     this.messagesQueues.put(key, mq);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 179 */     Integer key = Integer.valueOf(ctx.channel().hashCode());
/* 180 */     List<ToSend> mq = this.messagesQueues.remove(key);
/* 181 */     if (mq != null) {
/* 182 */       for (ToSend toSend : mq) {
/* 183 */         if (toSend.toSend instanceof ByteBuf) {
/* 184 */           ((ByteBuf)toSend.toSend).release();
/*     */         }
/*     */       } 
/* 187 */       mq.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class ToSend {
/*     */     final long date;
/*     */     final Object toSend;
/*     */     final ChannelPromise promise;
/*     */     
/*     */     private ToSend(long delay, Object toSend, ChannelPromise promise) {
/* 197 */       this.date = System.currentTimeMillis() + delay;
/* 198 */       this.toSend = toSend;
/* 199 */       this.promise = promise;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void submitWrite(final ChannelHandlerContext ctx, Object msg, long delay, ChannelPromise promise) {
/* 206 */     Integer key = Integer.valueOf(ctx.channel().hashCode());
/* 207 */     List<ToSend> messagesQueue = this.messagesQueues.get(key);
/* 208 */     if (delay == 0L && (messagesQueue == null || messagesQueue.isEmpty())) {
/* 209 */       ctx.write(msg, promise);
/*     */       return;
/*     */     } 
/* 212 */     ToSend newToSend = new ToSend(delay, msg, promise);
/* 213 */     if (messagesQueue == null) {
/* 214 */       messagesQueue = new LinkedList<ToSend>();
/* 215 */       this.messagesQueues.put(key, messagesQueue);
/*     */     } 
/* 217 */     messagesQueue.add(newToSend);
/* 218 */     final List<ToSend> mqfinal = messagesQueue;
/* 219 */     ctx.executor().schedule(new Runnable()
/*     */         {
/*     */           public void run() {
/* 222 */             GlobalTrafficShapingHandler.this.sendAllValid(ctx, mqfinal);
/*     */           }
/*     */         }delay, TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */   private synchronized void sendAllValid(ChannelHandlerContext ctx, List<ToSend> messagesQueue) {
/* 228 */     while (!messagesQueue.isEmpty()) {
/* 229 */       ToSend newToSend = messagesQueue.remove(0);
/* 230 */       if (newToSend.date <= System.currentTimeMillis()) {
/* 231 */         ctx.write(newToSend.toSend, newToSend.promise); continue;
/*     */       } 
/* 233 */       messagesQueue.add(0, newToSend);
/*     */     } 
/*     */ 
/*     */     
/* 237 */     ctx.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\traffic\GlobalTrafficShapingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */