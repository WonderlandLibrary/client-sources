/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractEventExecutorGroup
/*     */   implements EventExecutorGroup
/*     */ {
/*     */   public Future<?> submit(Runnable task) {
/*  34 */     return next().submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Runnable task, T result) {
/*  39 */     return next().submit(task, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Callable<T> task) {
/*  44 */     return next().submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/*  49 */     return next().schedule(command, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/*  54 */     return next().schedule(callable, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/*  59 */     return next().scheduleAtFixedRate(command, initialDelay, period, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/*  64 */     return next().scheduleWithFixedDelay(command, initialDelay, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully() {
/*  69 */     return shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
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
/*  85 */     shutdown();
/*  86 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
/*  92 */     return next().invokeAll(tasks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
/*  98 */     return next().invokeAll(tasks, timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
/* 103 */     return next().invokeAny(tasks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 109 */     return next().invokeAny(tasks, timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable command) {
/* 114 */     next().execute(command);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public abstract void shutdown();
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\AbstractEventExecutorGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */