/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.concurrent.AbstractExecutorService;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RunnableFuture;
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
/*     */ public abstract class AbstractEventExecutor
/*     */   extends AbstractExecutorService
/*     */   implements EventExecutor
/*     */ {
/*     */   public EventExecutor next() {
/*  34 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop() {
/*  39 */     return inEventLoop(Thread.currentThread());
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventExecutor> iterator() {
/*  44 */     return new EventExecutorIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully() {
/*  49 */     return shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
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
/*     */   @Deprecated
/*     */   public List<Runnable> shutdownNow() {
/*  65 */     shutdown();
/*  66 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> Promise<V> newPromise() {
/*  71 */     return new DefaultPromise<V>(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ProgressivePromise<V> newProgressivePromise() {
/*  76 */     return new DefaultProgressivePromise<V>(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> Future<V> newSucceededFuture(V result) {
/*  81 */     return new SucceededFuture<V>(this, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> Future<V> newFailedFuture(Throwable cause) {
/*  86 */     return new FailedFuture<V>(this, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> submit(Runnable task) {
/*  91 */     return (Future)super.submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Runnable task, T result) {
/*  96 */     return (Future<T>)super.<T>submit(task, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Callable<T> task) {
/* 101 */     return (Future<T>)super.<T>submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
/* 106 */     return new PromiseTask<T>(this, runnable, value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
/* 111 */     return new PromiseTask<T>(this, callable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/* 117 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/* 122 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/* 127 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   @Deprecated
/*     */   public abstract void shutdown();
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/* 132 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private final class EventExecutorIterator
/*     */     implements Iterator<EventExecutor> {
/*     */     private boolean nextCalled;
/*     */     
/*     */     public boolean hasNext() {
/* 140 */       return !this.nextCalled;
/*     */     }
/*     */     private EventExecutorIterator() {}
/*     */     
/*     */     public EventExecutor next() {
/* 145 */       if (!hasNext()) {
/* 146 */         throw new NoSuchElementException();
/*     */       }
/* 148 */       this.nextCalled = true;
/* 149 */       return AbstractEventExecutor.this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 154 */       throw new UnsupportedOperationException("read-only");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\AbstractEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */