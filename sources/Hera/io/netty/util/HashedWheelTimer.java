/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.MpscLinkedQueueNode;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.Executors;
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
/*     */ public class HashedWheelTimer
/*     */   implements Timer
/*     */ {
/*  77 */   static final InternalLogger logger = InternalLoggerFactory.getInstance(HashedWheelTimer.class);
/*     */ 
/*     */   
/*  80 */   private static final ResourceLeakDetector<HashedWheelTimer> leakDetector = new ResourceLeakDetector<HashedWheelTimer>(HashedWheelTimer.class, 1, (Runtime.getRuntime().availableProcessors() * 4));
/*     */   
/*     */   private static final AtomicIntegerFieldUpdater<HashedWheelTimer> WORKER_STATE_UPDATER;
/*     */   private final ResourceLeak leak;
/*     */   
/*     */   static {
/*  86 */     AtomicIntegerFieldUpdater<HashedWheelTimer> workerStateUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimer.class, "workerState");
/*     */     
/*  88 */     if (workerStateUpdater == null) {
/*  89 */       workerStateUpdater = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.class, "workerState");
/*     */     }
/*  91 */     WORKER_STATE_UPDATER = workerStateUpdater;
/*     */   }
/*     */ 
/*     */   
/*  95 */   private final Worker worker = new Worker();
/*     */   
/*     */   private final Thread workerThread;
/*     */   public static final int WORKER_STATE_INIT = 0;
/*     */   public static final int WORKER_STATE_STARTED = 1;
/*     */   public static final int WORKER_STATE_SHUTDOWN = 2;
/* 101 */   private volatile int workerState = 0;
/*     */   
/*     */   private final long tickDuration;
/*     */   
/*     */   private final HashedWheelBucket[] wheel;
/*     */   private final int mask;
/* 107 */   private final CountDownLatch startTimeInitialized = new CountDownLatch(1);
/* 108 */   private final Queue<HashedWheelTimeout> timeouts = PlatformDependent.newMpscQueue();
/* 109 */   private final Queue<Runnable> cancelledTimeouts = PlatformDependent.newMpscQueue();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long startTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashedWheelTimer() {
/* 119 */     this(Executors.defaultThreadFactory());
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
/*     */   public HashedWheelTimer(long tickDuration, TimeUnit unit) {
/* 133 */     this(Executors.defaultThreadFactory(), tickDuration, unit);
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
/*     */   public HashedWheelTimer(long tickDuration, TimeUnit unit, int ticksPerWheel) {
/* 147 */     this(Executors.defaultThreadFactory(), tickDuration, unit, ticksPerWheel);
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
/*     */   public HashedWheelTimer(ThreadFactory threadFactory) {
/* 160 */     this(threadFactory, 100L, TimeUnit.MILLISECONDS);
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
/*     */   public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit) {
/* 176 */     this(threadFactory, tickDuration, unit, 512);
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
/*     */   public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel) {
/* 195 */     if (threadFactory == null) {
/* 196 */       throw new NullPointerException("threadFactory");
/*     */     }
/* 198 */     if (unit == null) {
/* 199 */       throw new NullPointerException("unit");
/*     */     }
/* 201 */     if (tickDuration <= 0L) {
/* 202 */       throw new IllegalArgumentException("tickDuration must be greater than 0: " + tickDuration);
/*     */     }
/* 204 */     if (ticksPerWheel <= 0) {
/* 205 */       throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
/*     */     }
/*     */ 
/*     */     
/* 209 */     this.wheel = createWheel(ticksPerWheel);
/* 210 */     this.mask = this.wheel.length - 1;
/*     */ 
/*     */     
/* 213 */     this.tickDuration = unit.toNanos(tickDuration);
/*     */ 
/*     */     
/* 216 */     if (this.tickDuration >= Long.MAX_VALUE / this.wheel.length) {
/* 217 */       throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", new Object[] { Long.valueOf(tickDuration), Long.valueOf(Long.MAX_VALUE / this.wheel.length) }));
/*     */     }
/*     */ 
/*     */     
/* 221 */     this.workerThread = threadFactory.newThread(this.worker);
/*     */     
/* 223 */     this.leak = leakDetector.open(this);
/*     */   }
/*     */   
/*     */   private static HashedWheelBucket[] createWheel(int ticksPerWheel) {
/* 227 */     if (ticksPerWheel <= 0) {
/* 228 */       throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
/*     */     }
/*     */     
/* 231 */     if (ticksPerWheel > 1073741824) {
/* 232 */       throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + ticksPerWheel);
/*     */     }
/*     */ 
/*     */     
/* 236 */     ticksPerWheel = normalizeTicksPerWheel(ticksPerWheel);
/* 237 */     HashedWheelBucket[] wheel = new HashedWheelBucket[ticksPerWheel];
/* 238 */     for (int i = 0; i < wheel.length; i++) {
/* 239 */       wheel[i] = new HashedWheelBucket();
/*     */     }
/* 241 */     return wheel;
/*     */   }
/*     */   
/*     */   private static int normalizeTicksPerWheel(int ticksPerWheel) {
/* 245 */     int normalizedTicksPerWheel = 1;
/* 246 */     while (normalizedTicksPerWheel < ticksPerWheel) {
/* 247 */       normalizedTicksPerWheel <<= 1;
/*     */     }
/* 249 */     return normalizedTicksPerWheel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 260 */     switch (WORKER_STATE_UPDATER.get((T)this)) {
/*     */       case 0:
/* 262 */         if (WORKER_STATE_UPDATER.compareAndSet(this, 0, 1)) {
/* 263 */           this.workerThread.start();
/*     */         }
/*     */         break;
/*     */       case 1:
/*     */         break;
/*     */       case 2:
/* 269 */         throw new IllegalStateException("cannot be started once stopped");
/*     */       default:
/* 271 */         throw new Error("Invalid WorkerState");
/*     */     } 
/*     */ 
/*     */     
/* 275 */     while (this.startTime == 0L) {
/*     */       try {
/* 277 */         this.startTimeInitialized.await();
/* 278 */       } catch (InterruptedException ignore) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Timeout> stop() {
/* 286 */     if (Thread.currentThread() == this.workerThread) {
/* 287 */       throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 293 */     if (!WORKER_STATE_UPDATER.compareAndSet(this, 1, 2)) {
/*     */       
/* 295 */       WORKER_STATE_UPDATER.set(this, 2);
/*     */       
/* 297 */       if (this.leak != null) {
/* 298 */         this.leak.close();
/*     */       }
/*     */       
/* 301 */       return Collections.emptySet();
/*     */     } 
/*     */     
/* 304 */     boolean interrupted = false;
/* 305 */     while (this.workerThread.isAlive()) {
/* 306 */       this.workerThread.interrupt();
/*     */       try {
/* 308 */         this.workerThread.join(100L);
/* 309 */       } catch (InterruptedException ignored) {
/* 310 */         interrupted = true;
/*     */       } 
/*     */     } 
/*     */     
/* 314 */     if (interrupted) {
/* 315 */       Thread.currentThread().interrupt();
/*     */     }
/*     */     
/* 318 */     if (this.leak != null) {
/* 319 */       this.leak.close();
/*     */     }
/* 321 */     return this.worker.unprocessedTimeouts();
/*     */   }
/*     */ 
/*     */   
/*     */   public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
/* 326 */     if (task == null) {
/* 327 */       throw new NullPointerException("task");
/*     */     }
/* 329 */     if (unit == null) {
/* 330 */       throw new NullPointerException("unit");
/*     */     }
/* 332 */     start();
/*     */ 
/*     */ 
/*     */     
/* 336 */     long deadline = System.nanoTime() + unit.toNanos(delay) - this.startTime;
/* 337 */     HashedWheelTimeout timeout = new HashedWheelTimeout(this, task, deadline);
/* 338 */     this.timeouts.add(timeout);
/* 339 */     return timeout;
/*     */   }
/*     */   
/*     */   private final class Worker implements Runnable {
/* 343 */     private final Set<Timeout> unprocessedTimeouts = new HashSet<Timeout>();
/*     */ 
/*     */     
/*     */     private long tick;
/*     */ 
/*     */     
/*     */     public void run() {
/* 350 */       HashedWheelTimer.this.startTime = System.nanoTime();
/* 351 */       if (HashedWheelTimer.this.startTime == 0L)
/*     */       {
/* 353 */         HashedWheelTimer.this.startTime = 1L;
/*     */       }
/*     */ 
/*     */       
/* 357 */       HashedWheelTimer.this.startTimeInitialized.countDown();
/*     */       
/*     */       do {
/* 360 */         long deadline = waitForNextTick();
/* 361 */         if (deadline <= 0L)
/* 362 */           continue;  int idx = (int)(this.tick & HashedWheelTimer.this.mask);
/* 363 */         processCancelledTasks();
/* 364 */         HashedWheelTimer.HashedWheelBucket bucket = HashedWheelTimer.this.wheel[idx];
/*     */         
/* 366 */         transferTimeoutsToBuckets();
/* 367 */         bucket.expireTimeouts(deadline);
/* 368 */         this.tick++;
/*     */       }
/* 370 */       while (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 1);
/*     */ 
/*     */       
/* 373 */       for (HashedWheelTimer.HashedWheelBucket bucket : HashedWheelTimer.this.wheel) {
/* 374 */         bucket.clearTimeouts(this.unprocessedTimeouts);
/*     */       }
/*     */       while (true) {
/* 377 */         HashedWheelTimer.HashedWheelTimeout timeout = HashedWheelTimer.this.timeouts.poll();
/* 378 */         if (timeout == null) {
/*     */           break;
/*     */         }
/* 381 */         if (!timeout.isCancelled()) {
/* 382 */           this.unprocessedTimeouts.add(timeout);
/*     */         }
/*     */       } 
/* 385 */       processCancelledTasks();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void transferTimeoutsToBuckets() {
/* 391 */       for (int i = 0; i < 100000; i++) {
/* 392 */         HashedWheelTimer.HashedWheelTimeout timeout = HashedWheelTimer.this.timeouts.poll();
/* 393 */         if (timeout == null) {
/*     */           break;
/*     */         }
/*     */         
/* 397 */         if (timeout.state() != 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 402 */           long calculated = timeout.deadline / HashedWheelTimer.this.tickDuration;
/* 403 */           timeout.remainingRounds = (calculated - this.tick) / HashedWheelTimer.this.wheel.length;
/*     */           
/* 405 */           long ticks = Math.max(calculated, this.tick);
/* 406 */           int stopIndex = (int)(ticks & HashedWheelTimer.this.mask);
/*     */           
/* 408 */           HashedWheelTimer.HashedWheelBucket bucket = HashedWheelTimer.this.wheel[stopIndex];
/* 409 */           bucket.addTimeout(timeout);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     private void processCancelledTasks() {
/*     */       while (true) {
/* 415 */         Runnable task = HashedWheelTimer.this.cancelledTimeouts.poll();
/* 416 */         if (task == null) {
/*     */           break;
/*     */         }
/*     */         
/*     */         try {
/* 421 */           task.run();
/* 422 */         } catch (Throwable t) {
/* 423 */           if (HashedWheelTimer.logger.isWarnEnabled()) {
/* 424 */             HashedWheelTimer.logger.warn("An exception was thrown while process a cancellation task", t);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long waitForNextTick() {
/* 437 */       long deadline = HashedWheelTimer.this.tickDuration * (this.tick + 1L);
/*     */       
/*     */       while (true) {
/* 440 */         long currentTime = System.nanoTime() - HashedWheelTimer.this.startTime;
/* 441 */         long sleepTimeMs = (deadline - currentTime + 999999L) / 1000000L;
/*     */         
/* 443 */         if (sleepTimeMs <= 0L) {
/* 444 */           if (currentTime == Long.MIN_VALUE) {
/* 445 */             return -9223372036854775807L;
/*     */           }
/* 447 */           return currentTime;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 456 */         if (PlatformDependent.isWindows()) {
/* 457 */           sleepTimeMs = sleepTimeMs / 10L * 10L;
/*     */         }
/*     */         
/*     */         try {
/* 461 */           Thread.sleep(sleepTimeMs);
/* 462 */         } catch (InterruptedException ignored) {
/* 463 */           if (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 2) {
/* 464 */             return Long.MIN_VALUE;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public Set<Timeout> unprocessedTimeouts() {
/* 471 */       return Collections.unmodifiableSet(this.unprocessedTimeouts);
/*     */     }
/*     */     
/*     */     private Worker() {}
/*     */   }
/*     */   
/*     */   private static final class HashedWheelTimeout
/*     */     extends MpscLinkedQueueNode<Timeout> implements Timeout {
/*     */     private static final int ST_INIT = 0;
/*     */     private static final int ST_CANCELLED = 1;
/*     */     private static final int ST_EXPIRED = 2;
/*     */     
/*     */     static {
/* 484 */       AtomicIntegerFieldUpdater<HashedWheelTimeout> updater = PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimeout.class, "state");
/*     */       
/* 486 */       if (updater == null) {
/* 487 */         updater = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimeout.class, "state");
/*     */       }
/* 489 */       STATE_UPDATER = updater;
/*     */     }
/*     */     
/*     */     private static final AtomicIntegerFieldUpdater<HashedWheelTimeout> STATE_UPDATER;
/*     */     private final HashedWheelTimer timer;
/*     */     private final TimerTask task;
/*     */     private final long deadline;
/* 496 */     private volatile int state = 0;
/*     */ 
/*     */     
/*     */     long remainingRounds;
/*     */ 
/*     */     
/*     */     HashedWheelTimeout next;
/*     */ 
/*     */     
/*     */     HashedWheelTimeout prev;
/*     */ 
/*     */     
/*     */     HashedWheelTimer.HashedWheelBucket bucket;
/*     */ 
/*     */     
/*     */     HashedWheelTimeout(HashedWheelTimer timer, TimerTask task, long deadline) {
/* 512 */       this.timer = timer;
/* 513 */       this.task = task;
/* 514 */       this.deadline = deadline;
/*     */     }
/*     */ 
/*     */     
/*     */     public Timer timer() {
/* 519 */       return this.timer;
/*     */     }
/*     */ 
/*     */     
/*     */     public TimerTask task() {
/* 524 */       return this.task;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean cancel() {
/* 530 */       if (!compareAndSetState(0, 1)) {
/* 531 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 540 */       this.timer.cancelledTimeouts.add(new Runnable()
/*     */           {
/*     */             public void run() {
/* 543 */               HashedWheelTimer.HashedWheelBucket bucket = HashedWheelTimer.HashedWheelTimeout.this.bucket;
/* 544 */               if (bucket != null) {
/* 545 */                 bucket.remove(HashedWheelTimer.HashedWheelTimeout.this);
/*     */               }
/*     */             }
/*     */           });
/* 549 */       return true;
/*     */     }
/*     */     
/*     */     public boolean compareAndSetState(int expected, int state) {
/* 553 */       return STATE_UPDATER.compareAndSet(this, expected, state);
/*     */     }
/*     */     
/*     */     public int state() {
/* 557 */       return this.state;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isCancelled() {
/* 562 */       return (state() == 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isExpired() {
/* 567 */       return (state() == 2);
/*     */     }
/*     */ 
/*     */     
/*     */     public HashedWheelTimeout value() {
/* 572 */       return this;
/*     */     }
/*     */     
/*     */     public void expire() {
/* 576 */       if (!compareAndSetState(0, 2)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 581 */         this.task.run(this);
/* 582 */       } catch (Throwable t) {
/* 583 */         if (HashedWheelTimer.logger.isWarnEnabled()) {
/* 584 */           HashedWheelTimer.logger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', t);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 591 */       long currentTime = System.nanoTime();
/* 592 */       long remaining = this.deadline - currentTime + this.timer.startTime;
/*     */       
/* 594 */       StringBuilder buf = new StringBuilder(192);
/* 595 */       buf.append(StringUtil.simpleClassName(this));
/* 596 */       buf.append('(');
/*     */       
/* 598 */       buf.append("deadline: ");
/* 599 */       if (remaining > 0L) {
/* 600 */         buf.append(remaining);
/* 601 */         buf.append(" ns later");
/* 602 */       } else if (remaining < 0L) {
/* 603 */         buf.append(-remaining);
/* 604 */         buf.append(" ns ago");
/*     */       } else {
/* 606 */         buf.append("now");
/*     */       } 
/*     */       
/* 609 */       if (isCancelled()) {
/* 610 */         buf.append(", cancelled");
/*     */       }
/*     */       
/* 613 */       buf.append(", task: ");
/* 614 */       buf.append(task());
/*     */       
/* 616 */       return buf.append(')').toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class HashedWheelBucket
/*     */   {
/*     */     private HashedWheelTimer.HashedWheelTimeout head;
/*     */ 
/*     */     
/*     */     private HashedWheelTimer.HashedWheelTimeout tail;
/*     */ 
/*     */     
/*     */     private HashedWheelBucket() {}
/*     */ 
/*     */     
/*     */     public void addTimeout(HashedWheelTimer.HashedWheelTimeout timeout) {
/* 634 */       assert timeout.bucket == null;
/* 635 */       timeout.bucket = this;
/* 636 */       if (this.head == null) {
/* 637 */         this.head = this.tail = timeout;
/*     */       } else {
/* 639 */         this.tail.next = timeout;
/* 640 */         timeout.prev = this.tail;
/* 641 */         this.tail = timeout;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void expireTimeouts(long deadline) {
/* 649 */       HashedWheelTimer.HashedWheelTimeout timeout = this.head;
/*     */ 
/*     */       
/* 652 */       while (timeout != null) {
/* 653 */         boolean remove = false;
/* 654 */         if (timeout.remainingRounds <= 0L) {
/* 655 */           if (timeout.deadline <= deadline) {
/* 656 */             timeout.expire();
/*     */           } else {
/*     */             
/* 659 */             throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", new Object[] { Long.valueOf(HashedWheelTimer.HashedWheelTimeout.access$800(timeout)), Long.valueOf(deadline) }));
/*     */           } 
/*     */           
/* 662 */           remove = true;
/* 663 */         } else if (timeout.isCancelled()) {
/* 664 */           remove = true;
/*     */         } else {
/* 666 */           timeout.remainingRounds--;
/*     */         } 
/*     */         
/* 669 */         HashedWheelTimer.HashedWheelTimeout next = timeout.next;
/* 670 */         if (remove) {
/* 671 */           remove(timeout);
/*     */         }
/* 673 */         timeout = next;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove(HashedWheelTimer.HashedWheelTimeout timeout) {
/* 678 */       HashedWheelTimer.HashedWheelTimeout next = timeout.next;
/*     */       
/* 680 */       if (timeout.prev != null) {
/* 681 */         timeout.prev.next = next;
/*     */       }
/* 683 */       if (timeout.next != null) {
/* 684 */         timeout.next.prev = timeout.prev;
/*     */       }
/*     */       
/* 687 */       if (timeout == this.head) {
/*     */         
/* 689 */         if (timeout == this.tail) {
/* 690 */           this.tail = null;
/* 691 */           this.head = null;
/*     */         } else {
/* 693 */           this.head = next;
/*     */         } 
/* 695 */       } else if (timeout == this.tail) {
/*     */         
/* 697 */         this.tail = timeout.prev;
/*     */       } 
/*     */       
/* 700 */       timeout.prev = null;
/* 701 */       timeout.next = null;
/* 702 */       timeout.bucket = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clearTimeouts(Set<Timeout> set) {
/*     */       while (true) {
/* 710 */         HashedWheelTimer.HashedWheelTimeout timeout = pollTimeout();
/* 711 */         if (timeout == null) {
/*     */           return;
/*     */         }
/* 714 */         if (timeout.isExpired() || timeout.isCancelled()) {
/*     */           continue;
/*     */         }
/* 717 */         set.add(timeout);
/*     */       } 
/*     */     }
/*     */     
/*     */     private HashedWheelTimer.HashedWheelTimeout pollTimeout() {
/* 722 */       HashedWheelTimer.HashedWheelTimeout head = this.head;
/* 723 */       if (head == null) {
/* 724 */         return null;
/*     */       }
/* 726 */       HashedWheelTimer.HashedWheelTimeout next = head.next;
/* 727 */       if (next == null) {
/* 728 */         this.tail = this.head = null;
/*     */       } else {
/* 730 */         this.head = next;
/* 731 */         next.prev = null;
/*     */       } 
/*     */ 
/*     */       
/* 735 */       head.next = null;
/* 736 */       head.prev = null;
/* 737 */       head.bucket = null;
/* 738 */       return head;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\HashedWheelTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */