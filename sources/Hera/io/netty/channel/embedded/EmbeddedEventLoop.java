/*     */ package io.netty.channel.embedded;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.util.concurrent.AbstractEventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutorGroup;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
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
/*     */ final class EmbeddedEventLoop
/*     */   extends AbstractEventExecutor
/*     */   implements EventLoop
/*     */ {
/*  33 */   private final Queue<Runnable> tasks = new ArrayDeque<Runnable>(2);
/*     */ 
/*     */   
/*     */   public void execute(Runnable command) {
/*  37 */     if (command == null) {
/*  38 */       throw new NullPointerException("command");
/*     */     }
/*  40 */     this.tasks.add(command);
/*     */   }
/*     */   
/*     */   void runTasks() {
/*     */     while (true) {
/*  45 */       Runnable task = this.tasks.poll();
/*  46 */       if (task == null) {
/*     */         break;
/*     */       }
/*     */       
/*  50 */       task.run();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/*  56 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/*  61 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/*  67 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/*  88 */     Thread.sleep(unit.toMillis(timeout));
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(Channel channel) {
/*  94 */     return register(channel, (ChannelPromise)new DefaultChannelPromise(channel, (EventExecutor)this));
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/*  99 */     channel.unsafe().register(this, promise);
/* 100 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop() {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoop next() {
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoopGroup parent() {
/* 120 */     return (EventLoopGroup)this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\embedded\EmbeddedEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */