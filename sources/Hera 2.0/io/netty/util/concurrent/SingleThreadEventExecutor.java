/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SingleThreadEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*  45 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
/*     */   
/*     */   private static final int ST_NOT_STARTED = 1;
/*     */   
/*     */   private static final int ST_STARTED = 2;
/*     */   private static final int ST_SHUTTING_DOWN = 3;
/*     */   private static final int ST_SHUTDOWN = 4;
/*     */   private static final int ST_TERMINATED = 5;
/*     */   
/*  54 */   private static final Runnable WAKEUP_TASK = new Runnable()
/*     */     {
/*     */       public void run() {}
/*     */     };
/*     */   
/*     */   private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER;
/*     */   private final EventExecutorGroup parent;
/*     */   private final Queue<Runnable> taskQueue;
/*     */   
/*     */   static {
/*  64 */     AtomicIntegerFieldUpdater<SingleThreadEventExecutor> updater = PlatformDependent.newAtomicIntegerFieldUpdater(SingleThreadEventExecutor.class, "state");
/*     */     
/*  66 */     if (updater == null) {
/*  67 */       updater = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "state");
/*     */     }
/*  69 */     STATE_UPDATER = updater;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  74 */   final Queue<ScheduledFutureTask<?>> delayedTaskQueue = new PriorityQueue<ScheduledFutureTask<?>>();
/*     */   
/*     */   private final Thread thread;
/*  77 */   private final Semaphore threadLock = new Semaphore(0);
/*  78 */   private final Set<Runnable> shutdownHooks = new LinkedHashSet<Runnable>();
/*     */   
/*     */   private final boolean addTaskWakesUp;
/*     */   
/*     */   private long lastExecutionTime;
/*  83 */   private volatile int state = 1;
/*     */   
/*     */   private volatile long gracefulShutdownQuietPeriod;
/*     */   
/*     */   private volatile long gracefulShutdownTimeout;
/*     */   
/*     */   private long gracefulShutdownStartTime;
/*  90 */   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
/* 103 */     if (threadFactory == null) {
/* 104 */       throw new NullPointerException("threadFactory");
/*     */     }
/*     */     
/* 107 */     this.parent = parent;
/* 108 */     this.addTaskWakesUp = addTaskWakesUp;
/*     */     
/* 110 */     this.thread = threadFactory.newThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 113 */             boolean success = false;
/* 114 */             SingleThreadEventExecutor.this.updateLastExecutionTime();
/*     */             try {
/* 116 */               SingleThreadEventExecutor.this.run();
/* 117 */               success = true;
/* 118 */             } catch (Throwable t) {
/* 119 */               SingleThreadEventExecutor.logger.warn("Unexpected exception from an event executor: ", t);
/*     */             } finally {
/*     */               int oldState; do {
/* 122 */                 oldState = SingleThreadEventExecutor.STATE_UPDATER.get(SingleThreadEventExecutor.this);
/* 123 */               } while (oldState < 3 && !SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, oldState, 3));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 129 */               if (success && SingleThreadEventExecutor.this.gracefulShutdownStartTime == 0L) {
/* 130 */                 SingleThreadEventExecutor.logger.error("Buggy " + EventExecutor.class.getSimpleName() + " implementation; " + SingleThreadEventExecutor.class.getSimpleName() + ".confirmShutdown() must be called " + "before run() implementation terminates.");
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/*     */                 do {
/*     */                 
/* 139 */                 } while (!SingleThreadEventExecutor.this.confirmShutdown());
/*     */               } finally {
/*     */ 
/*     */                 
/*     */                 try {
/*     */                   
/* 145 */                   SingleThreadEventExecutor.this.cleanup();
/*     */                 } finally {
/* 147 */                   SingleThreadEventExecutor.STATE_UPDATER.set(SingleThreadEventExecutor.this, 5);
/* 148 */                   SingleThreadEventExecutor.this.threadLock.release();
/* 149 */                   if (!SingleThreadEventExecutor.this.taskQueue.isEmpty()) {
/* 150 */                     SingleThreadEventExecutor.logger.warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.this.taskQueue.size() + ')');
/*     */                   }
/*     */ 
/*     */ 
/*     */                   
/* 155 */                   SingleThreadEventExecutor.this.terminationFuture.setSuccess(null);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 162 */     this.taskQueue = newTaskQueue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Queue<Runnable> newTaskQueue() {
/* 172 */     return new LinkedBlockingQueue<Runnable>();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutorGroup parent() {
/* 177 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void interruptThread() {
/* 184 */     this.thread.interrupt();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Runnable pollTask() {
/*     */     Runnable task;
/* 191 */     assert inEventLoop();
/*     */     while (true) {
/* 193 */       task = this.taskQueue.poll();
/* 194 */       if (task == WAKEUP_TASK)
/*     */         continue;  break;
/*     */     } 
/* 197 */     return task;
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
/*     */   protected Runnable takeTask() {
/* 211 */     assert inEventLoop();
/* 212 */     if (!(this.taskQueue instanceof BlockingQueue)) {
/* 213 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/* 216 */     BlockingQueue<Runnable> taskQueue = (BlockingQueue<Runnable>)this.taskQueue;
/*     */     while (true) {
/* 218 */       ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/* 219 */       if (delayedTask == null) {
/* 220 */         Runnable runnable = null;
/*     */         try {
/* 222 */           runnable = taskQueue.take();
/* 223 */           if (runnable == WAKEUP_TASK) {
/* 224 */             runnable = null;
/*     */           }
/* 226 */         } catch (InterruptedException e) {}
/*     */ 
/*     */         
/* 229 */         return runnable;
/*     */       } 
/* 231 */       long delayNanos = delayedTask.delayNanos();
/* 232 */       Runnable task = null;
/* 233 */       if (delayNanos > 0L) {
/*     */         try {
/* 235 */           task = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
/* 236 */         } catch (InterruptedException e) {
/* 237 */           return null;
/*     */         } 
/*     */       }
/* 240 */       if (task == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 245 */         fetchFromDelayedQueue();
/* 246 */         task = taskQueue.poll();
/*     */       } 
/*     */       
/* 249 */       if (task != null) {
/* 250 */         return task;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fetchFromDelayedQueue() {
/* 257 */     long nanoTime = 0L;
/*     */     while (true) {
/* 259 */       ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/* 260 */       if (delayedTask == null) {
/*     */         break;
/*     */       }
/*     */       
/* 264 */       if (nanoTime == 0L) {
/* 265 */         nanoTime = ScheduledFutureTask.nanoTime();
/*     */       }
/*     */       
/* 268 */       if (delayedTask.deadlineNanos() <= nanoTime) {
/* 269 */         this.delayedTaskQueue.remove();
/* 270 */         this.taskQueue.add(delayedTask);
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Runnable peekTask() {
/* 281 */     assert inEventLoop();
/* 282 */     return this.taskQueue.peek();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasTasks() {
/* 289 */     assert inEventLoop();
/* 290 */     return !this.taskQueue.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasScheduledTasks() {
/* 298 */     assert inEventLoop();
/* 299 */     ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/* 300 */     return (delayedTask != null && delayedTask.deadlineNanos() <= ScheduledFutureTask.nanoTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int pendingTasks() {
/* 310 */     return this.taskQueue.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addTask(Runnable task) {
/* 318 */     if (task == null) {
/* 319 */       throw new NullPointerException("task");
/*     */     }
/* 321 */     if (isShutdown()) {
/* 322 */       reject();
/*     */     }
/* 324 */     this.taskQueue.add(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean removeTask(Runnable task) {
/* 331 */     if (task == null) {
/* 332 */       throw new NullPointerException("task");
/*     */     }
/* 334 */     return this.taskQueue.remove(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean runAllTasks() {
/* 343 */     fetchFromDelayedQueue();
/* 344 */     Runnable task = pollTask();
/* 345 */     if (task == null) {
/* 346 */       return false;
/*     */     }
/*     */     
/*     */     while (true) {
/*     */       try {
/* 351 */         task.run();
/* 352 */       } catch (Throwable t) {
/* 353 */         logger.warn("A task raised an exception.", t);
/*     */       } 
/*     */       
/* 356 */       task = pollTask();
/* 357 */       if (task == null) {
/* 358 */         this.lastExecutionTime = ScheduledFutureTask.nanoTime();
/* 359 */         return true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean runAllTasks(long timeoutNanos) {
/*     */     long lastExecutionTime;
/* 369 */     fetchFromDelayedQueue();
/* 370 */     Runnable task = pollTask();
/* 371 */     if (task == null) {
/* 372 */       return false;
/*     */     }
/*     */     
/* 375 */     long deadline = ScheduledFutureTask.nanoTime() + timeoutNanos;
/* 376 */     long runTasks = 0L;
/*     */     
/*     */     while (true) {
/*     */       try {
/* 380 */         task.run();
/* 381 */       } catch (Throwable t) {
/* 382 */         logger.warn("A task raised an exception.", t);
/*     */       } 
/*     */       
/* 385 */       runTasks++;
/*     */ 
/*     */ 
/*     */       
/* 389 */       if ((runTasks & 0x3FL) == 0L) {
/* 390 */         lastExecutionTime = ScheduledFutureTask.nanoTime();
/* 391 */         if (lastExecutionTime >= deadline) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 396 */       task = pollTask();
/* 397 */       if (task == null) {
/* 398 */         lastExecutionTime = ScheduledFutureTask.nanoTime();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 403 */     this.lastExecutionTime = lastExecutionTime;
/* 404 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long delayNanos(long currentTimeNanos) {
/* 411 */     ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/* 412 */     if (delayedTask == null) {
/* 413 */       return SCHEDULE_PURGE_INTERVAL;
/*     */     }
/*     */     
/* 416 */     return delayedTask.delayNanos(currentTimeNanos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateLastExecutionTime() {
/* 427 */     this.lastExecutionTime = ScheduledFutureTask.nanoTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cleanup() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void wakeup(boolean inEventLoop) {
/* 443 */     if (!inEventLoop || STATE_UPDATER.get(this) == 3) {
/* 444 */       this.taskQueue.add(WAKEUP_TASK);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/* 450 */     return (thread == this.thread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addShutdownHook(final Runnable task) {
/* 457 */     if (inEventLoop()) {
/* 458 */       this.shutdownHooks.add(task);
/*     */     } else {
/* 460 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 463 */               SingleThreadEventExecutor.this.shutdownHooks.add(task);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeShutdownHook(final Runnable task) {
/* 473 */     if (inEventLoop()) {
/* 474 */       this.shutdownHooks.remove(task);
/*     */     } else {
/* 476 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 479 */               SingleThreadEventExecutor.this.shutdownHooks.remove(task);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean runShutdownHooks() {
/* 486 */     boolean ran = false;
/*     */     
/* 488 */     while (!this.shutdownHooks.isEmpty()) {
/* 489 */       List<Runnable> copy = new ArrayList<Runnable>(this.shutdownHooks);
/* 490 */       this.shutdownHooks.clear();
/* 491 */       for (Runnable task : copy) {
/*     */         try {
/* 493 */           task.run();
/* 494 */         } catch (Throwable t) {
/* 495 */           logger.warn("Shutdown hook raised an exception.", t);
/*     */         } finally {
/* 497 */           ran = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 502 */     if (ran) {
/* 503 */       this.lastExecutionTime = ScheduledFutureTask.nanoTime();
/*     */     }
/*     */     
/* 506 */     return ran;
/*     */   }
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/*     */     boolean wakeup;
/*     */     int oldState, newState;
/* 511 */     if (quietPeriod < 0L) {
/* 512 */       throw new IllegalArgumentException("quietPeriod: " + quietPeriod + " (expected >= 0)");
/*     */     }
/* 514 */     if (timeout < quietPeriod) {
/* 515 */       throw new IllegalArgumentException("timeout: " + timeout + " (expected >= quietPeriod (" + quietPeriod + "))");
/*     */     }
/*     */     
/* 518 */     if (unit == null) {
/* 519 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 522 */     if (isShuttingDown()) {
/* 523 */       return terminationFuture();
/*     */     }
/*     */     
/* 526 */     boolean inEventLoop = inEventLoop();
/*     */ 
/*     */     
/*     */     do {
/* 530 */       if (isShuttingDown()) {
/* 531 */         return terminationFuture();
/*     */       }
/*     */       
/* 534 */       wakeup = true;
/* 535 */       oldState = STATE_UPDATER.get(this);
/* 536 */       if (inEventLoop) {
/* 537 */         newState = 3;
/*     */       } else {
/* 539 */         switch (oldState) {
/*     */           case 1:
/*     */           case 2:
/* 542 */             newState = 3;
/*     */             break;
/*     */           default:
/* 545 */             newState = oldState;
/* 546 */             wakeup = false; break;
/*     */         } 
/*     */       } 
/* 549 */     } while (!STATE_UPDATER.compareAndSet(this, oldState, newState));
/*     */ 
/*     */ 
/*     */     
/* 553 */     this.gracefulShutdownQuietPeriod = unit.toNanos(quietPeriod);
/* 554 */     this.gracefulShutdownTimeout = unit.toNanos(timeout);
/*     */     
/* 556 */     if (oldState == 1) {
/* 557 */       this.thread.start();
/*     */     }
/*     */     
/* 560 */     if (wakeup) {
/* 561 */       wakeup(inEventLoop);
/*     */     }
/*     */     
/* 564 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 569 */     return this.terminationFuture;
/*     */   }
/*     */   @Deprecated
/*     */   public void shutdown() {
/*     */     boolean wakeup;
/*     */     int oldState, newState;
/* 575 */     if (isShutdown()) {
/*     */       return;
/*     */     }
/*     */     
/* 579 */     boolean inEventLoop = inEventLoop();
/*     */ 
/*     */     
/*     */     do {
/* 583 */       if (isShuttingDown()) {
/*     */         return;
/*     */       }
/*     */       
/* 587 */       wakeup = true;
/* 588 */       oldState = STATE_UPDATER.get(this);
/* 589 */       if (inEventLoop) {
/* 590 */         newState = 4;
/*     */       } else {
/* 592 */         switch (oldState) {
/*     */           case 1:
/*     */           case 2:
/*     */           case 3:
/* 596 */             newState = 4;
/*     */             break;
/*     */           default:
/* 599 */             newState = oldState;
/* 600 */             wakeup = false; break;
/*     */         } 
/*     */       } 
/* 603 */     } while (!STATE_UPDATER.compareAndSet(this, oldState, newState));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 608 */     if (oldState == 1) {
/* 609 */       this.thread.start();
/*     */     }
/*     */     
/* 612 */     if (wakeup) {
/* 613 */       wakeup(inEventLoop);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 619 */     return (STATE_UPDATER.get((T)this) >= 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 624 */     return (STATE_UPDATER.get((T)this) >= 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 629 */     return (STATE_UPDATER.get(this) == 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean confirmShutdown() {
/* 636 */     if (!isShuttingDown()) {
/* 637 */       return false;
/*     */     }
/*     */     
/* 640 */     if (!inEventLoop()) {
/* 641 */       throw new IllegalStateException("must be invoked from an event loop");
/*     */     }
/*     */     
/* 644 */     cancelDelayedTasks();
/*     */     
/* 646 */     if (this.gracefulShutdownStartTime == 0L) {
/* 647 */       this.gracefulShutdownStartTime = ScheduledFutureTask.nanoTime();
/*     */     }
/*     */     
/* 650 */     if (runAllTasks() || runShutdownHooks()) {
/* 651 */       if (isShutdown())
/*     */       {
/* 653 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 657 */       wakeup(true);
/* 658 */       return false;
/*     */     } 
/*     */     
/* 661 */     long nanoTime = ScheduledFutureTask.nanoTime();
/*     */     
/* 663 */     if (isShutdown() || nanoTime - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout) {
/* 664 */       return true;
/*     */     }
/*     */     
/* 667 */     if (nanoTime - this.lastExecutionTime <= this.gracefulShutdownQuietPeriod) {
/*     */ 
/*     */       
/* 670 */       wakeup(true);
/*     */       try {
/* 672 */         Thread.sleep(100L);
/* 673 */       } catch (InterruptedException e) {}
/*     */ 
/*     */ 
/*     */       
/* 677 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 682 */     return true;
/*     */   }
/*     */   
/*     */   private void cancelDelayedTasks() {
/* 686 */     if (this.delayedTaskQueue.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 690 */     ScheduledFutureTask[] arrayOfScheduledFutureTask = (ScheduledFutureTask[])this.delayedTaskQueue.toArray((Object[])new ScheduledFutureTask[this.delayedTaskQueue.size()]);
/*     */ 
/*     */     
/* 693 */     for (ScheduledFutureTask<?> task : arrayOfScheduledFutureTask) {
/* 694 */       task.cancel(false);
/*     */     }
/*     */     
/* 697 */     this.delayedTaskQueue.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 702 */     if (unit == null) {
/* 703 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 706 */     if (inEventLoop()) {
/* 707 */       throw new IllegalStateException("cannot await termination of the current thread");
/*     */     }
/*     */     
/* 710 */     if (this.threadLock.tryAcquire(timeout, unit)) {
/* 711 */       this.threadLock.release();
/*     */     }
/*     */     
/* 714 */     return isTerminated();
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable task) {
/* 719 */     if (task == null) {
/* 720 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 723 */     boolean inEventLoop = inEventLoop();
/* 724 */     if (inEventLoop) {
/* 725 */       addTask(task);
/*     */     } else {
/* 727 */       startThread();
/* 728 */       addTask(task);
/* 729 */       if (isShutdown() && removeTask(task)) {
/* 730 */         reject();
/*     */       }
/*     */     } 
/*     */     
/* 734 */     if (!this.addTaskWakesUp && wakesUpForTask(task)) {
/* 735 */       wakeup(inEventLoop);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean wakesUpForTask(Runnable task) {
/* 741 */     return true;
/*     */   }
/*     */   
/*     */   protected static void reject() {
/* 745 */     throw new RejectedExecutionException("event executor terminated");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 750 */   private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/* 754 */     if (command == null) {
/* 755 */       throw new NullPointerException("command");
/*     */     }
/* 757 */     if (unit == null) {
/* 758 */       throw new NullPointerException("unit");
/*     */     }
/* 760 */     if (delay < 0L) {
/* 761 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */     
/* 764 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, command, null, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/* 770 */     if (callable == null) {
/* 771 */       throw new NullPointerException("callable");
/*     */     }
/* 773 */     if (unit == null) {
/* 774 */       throw new NullPointerException("unit");
/*     */     }
/* 776 */     if (delay < 0L) {
/* 777 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */     
/* 780 */     return schedule(new ScheduledFutureTask<V>(this, this.delayedTaskQueue, callable, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/* 786 */     if (command == null) {
/* 787 */       throw new NullPointerException("command");
/*     */     }
/* 789 */     if (unit == null) {
/* 790 */       throw new NullPointerException("unit");
/*     */     }
/* 792 */     if (initialDelay < 0L) {
/* 793 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/*     */     
/* 796 */     if (period <= 0L) {
/* 797 */       throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", new Object[] { Long.valueOf(period) }));
/*     */     }
/*     */ 
/*     */     
/* 801 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), unit.toNanos(period)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/* 808 */     if (command == null) {
/* 809 */       throw new NullPointerException("command");
/*     */     }
/* 811 */     if (unit == null) {
/* 812 */       throw new NullPointerException("unit");
/*     */     }
/* 814 */     if (initialDelay < 0L) {
/* 815 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/*     */     
/* 818 */     if (delay <= 0L) {
/* 819 */       throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */ 
/*     */     
/* 823 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), -unit.toNanos(delay)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> task) {
/* 829 */     if (task == null) {
/* 830 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 833 */     if (inEventLoop()) {
/* 834 */       this.delayedTaskQueue.add(task);
/*     */     } else {
/* 836 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 839 */               SingleThreadEventExecutor.this.delayedTaskQueue.add(task);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 844 */     return task;
/*     */   }
/*     */   
/*     */   private void startThread() {
/* 848 */     if (STATE_UPDATER.get(this) == 1 && 
/* 849 */       STATE_UPDATER.compareAndSet(this, 1, 2)) {
/* 850 */       this.delayedTaskQueue.add(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(new PurgeTask(), null), ScheduledFutureTask.deadlineNanos(SCHEDULE_PURGE_INTERVAL), -SCHEDULE_PURGE_INTERVAL));
/*     */ 
/*     */       
/* 853 */       this.thread.start();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void run();
/*     */   
/*     */   private final class PurgeTask implements Runnable {
/*     */     public void run() {
/* 861 */       Iterator<ScheduledFutureTask<?>> i = SingleThreadEventExecutor.this.delayedTaskQueue.iterator();
/* 862 */       while (i.hasNext()) {
/* 863 */         ScheduledFutureTask<?> task = i.next();
/* 864 */         if (task.isCancelled())
/* 865 */           i.remove(); 
/*     */       } 
/*     */     }
/*     */     
/*     */     private PurgeTask() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\SingleThreadEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */