/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Iterator;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GlobalEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*  40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
/*     */   
/*  42 */   private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
/*     */   
/*  44 */   public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
/*     */   
/*  46 */   final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
/*  47 */   final Queue<ScheduledFutureTask<?>> delayedTaskQueue = new PriorityQueue<ScheduledFutureTask<?>>();
/*  48 */   final ScheduledFutureTask<Void> purgeTask = new ScheduledFutureTask<Void>(this, this.delayedTaskQueue, Executors.callable(new PurgeTask(), null), ScheduledFutureTask.deadlineNanos(SCHEDULE_PURGE_INTERVAL), -SCHEDULE_PURGE_INTERVAL);
/*     */ 
/*     */ 
/*     */   
/*  52 */   private final ThreadFactory threadFactory = new DefaultThreadFactory(getClass());
/*  53 */   private final TaskRunner taskRunner = new TaskRunner();
/*  54 */   private final AtomicBoolean started = new AtomicBoolean();
/*     */   
/*     */   volatile Thread thread;
/*  57 */   private final Future<?> terminationFuture = new FailedFuture(this, new UnsupportedOperationException());
/*     */   
/*     */   private GlobalEventExecutor() {
/*  60 */     this.delayedTaskQueue.add(this.purgeTask);
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutorGroup parent() {
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Runnable takeTask() {
/*  74 */     BlockingQueue<Runnable> taskQueue = this.taskQueue; while (true) {
/*     */       Runnable runnable;
/*  76 */       ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/*  77 */       if (delayedTask == null) {
/*  78 */         Runnable task = null;
/*     */         try {
/*  80 */           task = taskQueue.take();
/*  81 */         } catch (InterruptedException e) {}
/*     */ 
/*     */         
/*  84 */         return task;
/*     */       } 
/*  86 */       long delayNanos = delayedTask.delayNanos();
/*     */       
/*  88 */       if (delayNanos > 0L) {
/*     */         try {
/*  90 */           runnable = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
/*  91 */         } catch (InterruptedException e) {
/*  92 */           return null;
/*     */         } 
/*     */       } else {
/*  95 */         runnable = taskQueue.poll();
/*     */       } 
/*     */       
/*  98 */       if (runnable == null) {
/*  99 */         fetchFromDelayedQueue();
/* 100 */         runnable = taskQueue.poll();
/*     */       } 
/*     */       
/* 103 */       if (runnable != null) {
/* 104 */         return runnable;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fetchFromDelayedQueue() {
/* 111 */     long nanoTime = 0L;
/*     */     while (true) {
/* 113 */       ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/* 114 */       if (delayedTask == null) {
/*     */         break;
/*     */       }
/*     */       
/* 118 */       if (nanoTime == 0L) {
/* 119 */         nanoTime = ScheduledFutureTask.nanoTime();
/*     */       }
/*     */       
/* 122 */       if (delayedTask.deadlineNanos() <= nanoTime) {
/* 123 */         this.delayedTaskQueue.remove();
/* 124 */         this.taskQueue.add(delayedTask);
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int pendingTasks() {
/* 138 */     return this.taskQueue.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTask(Runnable task) {
/* 146 */     if (task == null) {
/* 147 */       throw new NullPointerException("task");
/*     */     }
/* 149 */     this.taskQueue.add(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/* 154 */     return (thread == this.thread);
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 159 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 164 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 170 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 185 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) {
/* 190 */     return false;
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
/*     */   public boolean awaitInactivity(long timeout, TimeUnit unit) throws InterruptedException {
/* 202 */     if (unit == null) {
/* 203 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 206 */     Thread thread = this.thread;
/* 207 */     if (thread == null) {
/* 208 */       throw new IllegalStateException("thread was not started");
/*     */     }
/* 210 */     thread.join(unit.toMillis(timeout));
/* 211 */     return !thread.isAlive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable task) {
/* 216 */     if (task == null) {
/* 217 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 220 */     addTask(task);
/* 221 */     if (!inEventLoop()) {
/* 222 */       startThread();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/* 230 */     if (command == null) {
/* 231 */       throw new NullPointerException("command");
/*     */     }
/* 233 */     if (unit == null) {
/* 234 */       throw new NullPointerException("unit");
/*     */     }
/* 236 */     if (delay < 0L) {
/* 237 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */     
/* 240 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, command, null, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/* 246 */     if (callable == null) {
/* 247 */       throw new NullPointerException("callable");
/*     */     }
/* 249 */     if (unit == null) {
/* 250 */       throw new NullPointerException("unit");
/*     */     }
/* 252 */     if (delay < 0L) {
/* 253 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */     
/* 256 */     return schedule(new ScheduledFutureTask<V>(this, this.delayedTaskQueue, callable, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/* 262 */     if (command == null) {
/* 263 */       throw new NullPointerException("command");
/*     */     }
/* 265 */     if (unit == null) {
/* 266 */       throw new NullPointerException("unit");
/*     */     }
/* 268 */     if (initialDelay < 0L) {
/* 269 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/*     */     
/* 272 */     if (period <= 0L) {
/* 273 */       throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", new Object[] { Long.valueOf(period) }));
/*     */     }
/*     */ 
/*     */     
/* 277 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), unit.toNanos(period)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/* 284 */     if (command == null) {
/* 285 */       throw new NullPointerException("command");
/*     */     }
/* 287 */     if (unit == null) {
/* 288 */       throw new NullPointerException("unit");
/*     */     }
/* 290 */     if (initialDelay < 0L) {
/* 291 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/*     */     
/* 294 */     if (delay <= 0L) {
/* 295 */       throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */ 
/*     */     
/* 299 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), -unit.toNanos(delay)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> task) {
/* 305 */     if (task == null) {
/* 306 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 309 */     if (inEventLoop()) {
/* 310 */       this.delayedTaskQueue.add(task);
/*     */     } else {
/* 312 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 315 */               GlobalEventExecutor.this.delayedTaskQueue.add(task);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 320 */     return task;
/*     */   }
/*     */   
/*     */   private void startThread() {
/* 324 */     if (this.started.compareAndSet(false, true)) {
/* 325 */       Thread t = this.threadFactory.newThread(this.taskRunner);
/* 326 */       t.start();
/* 327 */       this.thread = t;
/*     */     } 
/*     */   }
/*     */   
/*     */   final class TaskRunner
/*     */     implements Runnable {
/*     */     public void run() {
/*     */       while (true) {
/* 335 */         Runnable task = GlobalEventExecutor.this.takeTask();
/* 336 */         if (task != null) {
/*     */           try {
/* 338 */             task.run();
/* 339 */           } catch (Throwable t) {
/* 340 */             GlobalEventExecutor.logger.warn("Unexpected exception from the global event executor: ", t);
/*     */           } 
/*     */           
/* 343 */           if (task != GlobalEventExecutor.this.purgeTask) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 349 */         if (GlobalEventExecutor.this.taskQueue.isEmpty() && GlobalEventExecutor.this.delayedTaskQueue.size() == 1) {
/*     */ 
/*     */ 
/*     */           
/* 353 */           boolean stopped = GlobalEventExecutor.this.started.compareAndSet(true, false);
/* 354 */           assert stopped;
/*     */ 
/*     */           
/* 357 */           if (GlobalEventExecutor.this.taskQueue.isEmpty() && GlobalEventExecutor.this.delayedTaskQueue.size() == 1) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 366 */           if (!GlobalEventExecutor.this.started.compareAndSet(false, true)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final class PurgeTask
/*     */     implements Runnable
/*     */   {
/*     */     private PurgeTask() {}
/*     */ 
/*     */     
/*     */     public void run() {
/* 383 */       Iterator<ScheduledFutureTask<?>> i = GlobalEventExecutor.this.delayedTaskQueue.iterator();
/* 384 */       while (i.hasNext()) {
/* 385 */         ScheduledFutureTask<?> task = i.next();
/* 386 */         if (task.isCancelled())
/* 387 */           i.remove(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\GlobalEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */