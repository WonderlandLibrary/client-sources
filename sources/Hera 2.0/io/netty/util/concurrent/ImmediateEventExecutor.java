/*     */ package io.netty.util.concurrent;
/*     */ 
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
/*     */ public final class ImmediateEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*  24 */   public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
/*     */   
/*  26 */   private final Future<?> terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventExecutorGroup parent() {
/*  35 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop() {
/*  40 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/*  50 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/*  55 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {}
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable command) {
/*  84 */     if (command == null) {
/*  85 */       throw new NullPointerException("command");
/*     */     }
/*  87 */     command.run();
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> Promise<V> newPromise() {
/*  92 */     return new ImmediatePromise<V>(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ProgressivePromise<V> newProgressivePromise() {
/*  97 */     return new ImmediateProgressivePromise<V>(this);
/*     */   }
/*     */   
/*     */   static class ImmediatePromise<V> extends DefaultPromise<V> {
/*     */     ImmediatePromise(EventExecutor executor) {
/* 102 */       super(executor);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void checkDeadLock() {}
/*     */   }
/*     */   
/*     */   static class ImmediateProgressivePromise<V>
/*     */     extends DefaultProgressivePromise<V>
/*     */   {
/*     */     ImmediateProgressivePromise(EventExecutor executor) {
/* 113 */       super(executor);
/*     */     }
/*     */     
/*     */     protected void checkDeadLock() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\ImmediateEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */