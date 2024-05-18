/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*     */ public class TrafficCounter
/*     */ {
/*  39 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(TrafficCounter.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private final AtomicLong currentWrittenBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private final AtomicLong currentReadBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private final AtomicLong cumulativeReadBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastCumulativeTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastWriteThroughput;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastReadThroughput;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private final AtomicLong lastTime = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastWrittenBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastReadBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastNonNullWrittenBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastNonNullWrittenTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastNonNullReadTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastNonNullReadBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   final AtomicLong checkInterval = new AtomicLong(1000L);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final AbstractTrafficShapingHandler trafficShapingHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ScheduledExecutorService executor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Runnable monitor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile ScheduledFuture<?> scheduledFuture;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   final AtomicBoolean monitorActive = new AtomicBoolean();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TrafficMonitoringTask
/*     */     implements Runnable
/*     */   {
/*     */     private final AbstractTrafficShapingHandler trafficShapingHandler1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final TrafficCounter counter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected TrafficMonitoringTask(AbstractTrafficShapingHandler trafficShapingHandler, TrafficCounter counter) {
/* 168 */       this.trafficShapingHandler1 = trafficShapingHandler;
/* 169 */       this.counter = counter;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 174 */       if (!this.counter.monitorActive.get()) {
/*     */         return;
/*     */       }
/* 177 */       long endTime = System.currentTimeMillis();
/* 178 */       this.counter.resetAccounting(endTime);
/* 179 */       if (this.trafficShapingHandler1 != null) {
/* 180 */         this.trafficShapingHandler1.doAccounting(this.counter);
/*     */       }
/* 182 */       this.counter.scheduledFuture = this.counter.executor.schedule(this, this.counter.checkInterval.get(), TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void start() {
/* 191 */     if (this.monitorActive.get()) {
/*     */       return;
/*     */     }
/* 194 */     this.lastTime.set(System.currentTimeMillis());
/* 195 */     if (this.checkInterval.get() > 0L) {
/* 196 */       this.monitorActive.set(true);
/* 197 */       this.monitor = new TrafficMonitoringTask(this.trafficShapingHandler, this);
/* 198 */       this.scheduledFuture = this.executor.schedule(this.monitor, this.checkInterval.get(), TimeUnit.MILLISECONDS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 206 */     if (!this.monitorActive.get()) {
/*     */       return;
/*     */     }
/* 209 */     this.monitorActive.set(false);
/* 210 */     resetAccounting(System.currentTimeMillis());
/* 211 */     if (this.trafficShapingHandler != null) {
/* 212 */       this.trafficShapingHandler.doAccounting(this);
/*     */     }
/* 214 */     if (this.scheduledFuture != null) {
/* 215 */       this.scheduledFuture.cancel(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void resetAccounting(long newLastTime) {
/* 226 */     long interval = newLastTime - this.lastTime.getAndSet(newLastTime);
/* 227 */     if (interval == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 231 */     if (logger.isDebugEnabled() && interval > 2L * checkInterval()) {
/* 232 */       logger.debug("Acct schedule not ok: " + interval + " > 2*" + checkInterval() + " from " + this.name);
/*     */     }
/* 234 */     this.lastReadBytes = this.currentReadBytes.getAndSet(0L);
/* 235 */     this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0L);
/* 236 */     this.lastReadThroughput = this.lastReadBytes / interval * 1000L;
/*     */     
/* 238 */     this.lastWriteThroughput = this.lastWrittenBytes / interval * 1000L;
/*     */     
/* 240 */     if (this.lastWrittenBytes > 0L) {
/* 241 */       this.lastNonNullWrittenBytes = this.lastWrittenBytes;
/* 242 */       this.lastNonNullWrittenTime = newLastTime;
/*     */     } 
/* 244 */     if (this.lastReadBytes > 0L) {
/* 245 */       this.lastNonNullReadBytes = this.lastReadBytes;
/* 246 */       this.lastNonNullReadTime = newLastTime;
/*     */     } 
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
/*     */   public TrafficCounter(AbstractTrafficShapingHandler trafficShapingHandler, ScheduledExecutorService executor, String name, long checkInterval) {
/* 265 */     this.trafficShapingHandler = trafficShapingHandler;
/* 266 */     this.executor = executor;
/* 267 */     this.name = name;
/* 268 */     this.lastCumulativeTime = System.currentTimeMillis();
/* 269 */     configure(checkInterval);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(long newcheckInterval) {
/* 279 */     long newInterval = newcheckInterval / 10L * 10L;
/* 280 */     if (this.checkInterval.get() != newInterval) {
/* 281 */       this.checkInterval.set(newInterval);
/* 282 */       if (newInterval <= 0L) {
/* 283 */         stop();
/*     */         
/* 285 */         this.lastTime.set(System.currentTimeMillis());
/*     */       } else {
/*     */         
/* 288 */         start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void bytesRecvFlowControl(long recv) {
/* 300 */     this.currentReadBytes.addAndGet(recv);
/* 301 */     this.cumulativeReadBytes.addAndGet(recv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void bytesWriteFlowControl(long write) {
/* 311 */     this.currentWrittenBytes.addAndGet(write);
/* 312 */     this.cumulativeWrittenBytes.addAndGet(write);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long checkInterval() {
/* 321 */     return this.checkInterval.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastReadThroughput() {
/* 329 */     return this.lastReadThroughput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastWriteThroughput() {
/* 337 */     return this.lastWriteThroughput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastReadBytes() {
/* 345 */     return this.lastReadBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastWrittenBytes() {
/* 353 */     return this.lastWrittenBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentReadBytes() {
/* 361 */     return this.currentReadBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentWrittenBytes() {
/* 369 */     return this.currentWrittenBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastTime() {
/* 376 */     return this.lastTime.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long cumulativeWrittenBytes() {
/* 383 */     return this.cumulativeWrittenBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long cumulativeReadBytes() {
/* 390 */     return this.cumulativeReadBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastCumulativeTime() {
/* 398 */     return this.lastCumulativeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetCumulativeTime() {
/* 405 */     this.lastCumulativeTime = System.currentTimeMillis();
/* 406 */     this.cumulativeReadBytes.set(0L);
/* 407 */     this.cumulativeWrittenBytes.set(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 414 */     return this.name;
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
/*     */   public synchronized long readTimeToWait(long size, long limitTraffic, long maxTime) {
/* 430 */     long now = System.currentTimeMillis();
/* 431 */     bytesRecvFlowControl(size);
/* 432 */     if (limitTraffic == 0L) {
/* 433 */       return 0L;
/*     */     }
/* 435 */     long sum = this.currentReadBytes.get();
/* 436 */     long interval = now - this.lastTime.get();
/*     */     
/* 438 */     if (interval > 10L && sum > 0L) {
/* 439 */       long time = (sum * 1000L / limitTraffic - interval) / 10L * 10L;
/* 440 */       if (time > 10L) {
/* 441 */         if (logger.isDebugEnabled()) {
/* 442 */           logger.debug("Time: " + time + ":" + sum + ":" + interval);
/*     */         }
/* 444 */         return (time > maxTime) ? maxTime : time;
/*     */       } 
/* 446 */       return 0L;
/*     */     } 
/*     */     
/* 449 */     if (this.lastNonNullReadBytes > 0L && this.lastNonNullReadTime + 10L < now) {
/* 450 */       long lastsum = sum + this.lastNonNullReadBytes;
/* 451 */       long lastinterval = now - this.lastNonNullReadTime;
/* 452 */       long time = (lastsum * 1000L / limitTraffic - lastinterval) / 10L * 10L;
/* 453 */       if (time > 10L) {
/* 454 */         if (logger.isDebugEnabled()) {
/* 455 */           logger.debug("Time: " + time + ":" + lastsum + ":" + lastinterval);
/*     */         }
/* 457 */         return (time > maxTime) ? maxTime : time;
/*     */       } 
/*     */     } else {
/*     */       
/* 461 */       sum += this.lastReadBytes;
/* 462 */       long lastinterval = 10L;
/* 463 */       long time = (sum * 1000L / limitTraffic - lastinterval) / 10L * 10L;
/* 464 */       if (time > 10L) {
/* 465 */         if (logger.isDebugEnabled()) {
/* 466 */           logger.debug("Time: " + time + ":" + sum + ":" + lastinterval);
/*     */         }
/* 468 */         return (time > maxTime) ? maxTime : time;
/*     */       } 
/*     */     } 
/* 471 */     return 0L;
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
/*     */   public synchronized long writeTimeToWait(long size, long limitTraffic, long maxTime) {
/* 487 */     bytesWriteFlowControl(size);
/* 488 */     if (limitTraffic == 0L) {
/* 489 */       return 0L;
/*     */     }
/* 491 */     long sum = this.currentWrittenBytes.get();
/* 492 */     long now = System.currentTimeMillis();
/* 493 */     long interval = now - this.lastTime.get();
/* 494 */     if (interval > 10L && sum > 0L) {
/* 495 */       long time = (sum * 1000L / limitTraffic - interval) / 10L * 10L;
/* 496 */       if (time > 10L) {
/* 497 */         if (logger.isDebugEnabled()) {
/* 498 */           logger.debug("Time: " + time + ":" + sum + ":" + interval);
/*     */         }
/* 500 */         return (time > maxTime) ? maxTime : time;
/*     */       } 
/* 502 */       return 0L;
/*     */     } 
/* 504 */     if (this.lastNonNullWrittenBytes > 0L && this.lastNonNullWrittenTime + 10L < now) {
/* 505 */       long lastsum = sum + this.lastNonNullWrittenBytes;
/* 506 */       long lastinterval = now - this.lastNonNullWrittenTime;
/* 507 */       long time = (lastsum * 1000L / limitTraffic - lastinterval) / 10L * 10L;
/* 508 */       if (time > 10L) {
/* 509 */         if (logger.isDebugEnabled()) {
/* 510 */           logger.debug("Time: " + time + ":" + lastsum + ":" + lastinterval);
/*     */         }
/* 512 */         return (time > maxTime) ? maxTime : time;
/*     */       } 
/*     */     } else {
/* 515 */       sum += this.lastWrittenBytes;
/* 516 */       long lastinterval = 10L + Math.abs(interval);
/* 517 */       long time = (sum * 1000L / limitTraffic - lastinterval) / 10L * 10L;
/* 518 */       if (time > 10L) {
/* 519 */         if (logger.isDebugEnabled()) {
/* 520 */           logger.debug("Time: " + time + ":" + sum + ":" + lastinterval);
/*     */         }
/* 522 */         return (time > maxTime) ? maxTime : time;
/*     */       } 
/*     */     } 
/* 525 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 533 */     return "Monitor " + this.name + " Current Speed Read: " + (this.lastReadThroughput >> 10L) + " KB/s, Write: " + (this.lastWriteThroughput >> 10L) + " KB/s Current Read: " + (this.currentReadBytes.get() >> 10L) + " KB Current Write: " + (this.currentWrittenBytes.get() >> 10L) + " KB";
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\traffic\TrafficCounter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */