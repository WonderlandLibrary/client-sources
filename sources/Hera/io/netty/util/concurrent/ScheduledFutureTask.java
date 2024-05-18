/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Delayed;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ final class ScheduledFutureTask<V>
/*     */   extends PromiseTask<V>
/*     */   implements ScheduledFuture<V>
/*     */ {
/*  27 */   private static final AtomicLong nextTaskId = new AtomicLong();
/*  28 */   private static final long START_TIME = System.nanoTime();
/*     */   
/*     */   static long nanoTime() {
/*  31 */     return System.nanoTime() - START_TIME;
/*     */   }
/*     */   
/*     */   static long deadlineNanos(long delay) {
/*  35 */     return nanoTime() + delay;
/*     */   }
/*     */   
/*  38 */   private final long id = nextTaskId.getAndIncrement();
/*     */   
/*     */   private final Queue<ScheduledFutureTask<?>> delayedTaskQueue;
/*     */   
/*     */   private long deadlineNanos;
/*     */   
/*     */   private final long periodNanos;
/*     */ 
/*     */   
/*     */   ScheduledFutureTask(EventExecutor executor, Queue<ScheduledFutureTask<?>> delayedTaskQueue, Runnable runnable, V result, long nanoTime) {
/*  48 */     this(executor, delayedTaskQueue, toCallable(runnable, result), nanoTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ScheduledFutureTask(EventExecutor executor, Queue<ScheduledFutureTask<?>> delayedTaskQueue, Callable<V> callable, long nanoTime, long period) {
/*  55 */     super(executor, callable);
/*  56 */     if (period == 0L) {
/*  57 */       throw new IllegalArgumentException("period: 0 (expected: != 0)");
/*     */     }
/*  59 */     this.delayedTaskQueue = delayedTaskQueue;
/*  60 */     this.deadlineNanos = nanoTime;
/*  61 */     this.periodNanos = period;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ScheduledFutureTask(EventExecutor executor, Queue<ScheduledFutureTask<?>> delayedTaskQueue, Callable<V> callable, long nanoTime) {
/*  68 */     super(executor, callable);
/*  69 */     this.delayedTaskQueue = delayedTaskQueue;
/*  70 */     this.deadlineNanos = nanoTime;
/*  71 */     this.periodNanos = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EventExecutor executor() {
/*  76 */     return super.executor();
/*     */   }
/*     */   
/*     */   public long deadlineNanos() {
/*  80 */     return this.deadlineNanos;
/*     */   }
/*     */   
/*     */   public long delayNanos() {
/*  84 */     return Math.max(0L, deadlineNanos() - nanoTime());
/*     */   }
/*     */   
/*     */   public long delayNanos(long currentTimeNanos) {
/*  88 */     return Math.max(0L, deadlineNanos() - currentTimeNanos - START_TIME);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getDelay(TimeUnit unit) {
/*  93 */     return unit.convert(delayNanos(), TimeUnit.NANOSECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Delayed o) {
/*  98 */     if (this == o) {
/*  99 */       return 0;
/*     */     }
/*     */     
/* 102 */     ScheduledFutureTask<?> that = (ScheduledFutureTask)o;
/* 103 */     long d = deadlineNanos() - that.deadlineNanos();
/* 104 */     if (d < 0L)
/* 105 */       return -1; 
/* 106 */     if (d > 0L)
/* 107 */       return 1; 
/* 108 */     if (this.id < that.id)
/* 109 */       return -1; 
/* 110 */     if (this.id == that.id) {
/* 111 */       throw new Error();
/*     */     }
/* 113 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 119 */     assert executor().inEventLoop();
/*     */     try {
/* 121 */       if (this.periodNanos == 0L) {
/* 122 */         if (setUncancellableInternal()) {
/* 123 */           V result = this.task.call();
/* 124 */           setSuccessInternal(result);
/*     */         }
/*     */       
/*     */       }
/* 128 */       else if (!isCancelled()) {
/* 129 */         this.task.call();
/* 130 */         if (!executor().isShutdown()) {
/* 131 */           long p = this.periodNanos;
/* 132 */           if (p > 0L) {
/* 133 */             this.deadlineNanos += p;
/*     */           } else {
/* 135 */             this.deadlineNanos = nanoTime() - p;
/*     */           } 
/* 137 */           if (!isCancelled()) {
/* 138 */             this.delayedTaskQueue.add(this);
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 143 */     } catch (Throwable cause) {
/* 144 */       setFailureInternal(cause);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected StringBuilder toStringBuilder() {
/* 150 */     StringBuilder buf = super.toStringBuilder();
/* 151 */     buf.setCharAt(buf.length() - 1, ',');
/* 152 */     buf.append(" id: ");
/* 153 */     buf.append(this.id);
/* 154 */     buf.append(", deadline: ");
/* 155 */     buf.append(this.deadlineNanos);
/* 156 */     buf.append(", period: ");
/* 157 */     buf.append(this.periodNanos);
/* 158 */     buf.append(')');
/* 159 */     return buf;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\ScheduledFutureTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */