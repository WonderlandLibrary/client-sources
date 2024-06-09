/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
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
/*     */ public final class ThreadLocalRandom
/*     */   extends Random
/*     */ {
/*  63 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadLocalRandom.class);
/*     */   
/*  65 */   private static final AtomicLong seedUniquifier = new AtomicLong(); private static volatile long initialSeedUniquifier; private static final long multiplier = 25214903917L; private static final long addend = 11L; private static final long mask = 281474976710655L; private long rnd;
/*     */   boolean initialized;
/*     */   private long pad0;
/*     */   
/*     */   public static void setInitialSeedUniquifier(long initialSeedUniquifier) {
/*  70 */     ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier;
/*     */   }
/*     */   private long pad1; private long pad2; private long pad3; private long pad4; private long pad5; private long pad6; private long pad7; private static final long serialVersionUID = -5851777807851030925L;
/*     */   
/*     */   public static synchronized long getInitialSeedUniquifier() {
/*  75 */     long initialSeedUniquifier = ThreadLocalRandom.initialSeedUniquifier;
/*  76 */     if (initialSeedUniquifier == 0L)
/*     */     {
/*  78 */       ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0L);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (initialSeedUniquifier == 0L) {
/*     */ 
/*     */       
/*  86 */       final BlockingQueue<byte[]> queue = (BlockingQueue)new LinkedBlockingQueue<byte>();
/*  87 */       Thread generatorThread = new Thread("initialSeedUniquifierGenerator")
/*     */         {
/*     */           public void run() {
/*  90 */             SecureRandom random = new SecureRandom();
/*  91 */             queue.add(random.generateSeed(8));
/*     */           }
/*     */         };
/*  94 */       generatorThread.setDaemon(true);
/*  95 */       generatorThread.start();
/*  96 */       generatorThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
/*     */           {
/*     */             public void uncaughtException(Thread t, Throwable e) {
/*  99 */               ThreadLocalRandom.logger.debug("An exception has been raised by {}", t.getName(), e);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 104 */       long timeoutSeconds = 3L;
/* 105 */       long deadLine = System.nanoTime() + TimeUnit.SECONDS.toNanos(3L);
/* 106 */       boolean interrupted = false;
/*     */       while (true) {
/* 108 */         long waitTime = deadLine - System.nanoTime();
/* 109 */         if (waitTime <= 0L) {
/* 110 */           generatorThread.interrupt();
/* 111 */           logger.warn("Failed to generate a seed from SecureRandom within {} seconds. Not enough entrophy?", Long.valueOf(3L));
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 119 */           byte[] seed = queue.poll(waitTime, TimeUnit.NANOSECONDS);
/* 120 */           if (seed != null) {
/* 121 */             initialSeedUniquifier = (seed[0] & 0xFFL) << 56L | (seed[1] & 0xFFL) << 48L | (seed[2] & 0xFFL) << 40L | (seed[3] & 0xFFL) << 32L | (seed[4] & 0xFFL) << 24L | (seed[5] & 0xFFL) << 16L | (seed[6] & 0xFFL) << 8L | seed[7] & 0xFFL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/* 132 */         } catch (InterruptedException e) {
/* 133 */           interrupted = true;
/* 134 */           logger.warn("Failed to generate a seed from SecureRandom due to an InterruptedException.");
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 140 */       initialSeedUniquifier ^= 0x3255ECDC33BAE119L;
/* 141 */       initialSeedUniquifier ^= Long.reverse(System.nanoTime());
/*     */       
/* 143 */       ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier;
/*     */       
/* 145 */       if (interrupted) {
/*     */         
/* 147 */         Thread.currentThread().interrupt();
/*     */ 
/*     */ 
/*     */         
/* 151 */         generatorThread.interrupt();
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     return initialSeedUniquifier;
/*     */   }
/*     */   
/*     */   private static long newSeed() {
/* 159 */     long startTime = System.nanoTime();
/*     */     while (true) {
/* 161 */       long current = seedUniquifier.get();
/* 162 */       long actualCurrent = (current != 0L) ? current : getInitialSeedUniquifier();
/*     */ 
/*     */       
/* 165 */       long next = actualCurrent * 181783497276652981L;
/*     */       
/* 167 */       if (seedUniquifier.compareAndSet(current, next)) {
/* 168 */         if (current == 0L && logger.isDebugEnabled()) {
/* 169 */           logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", new Object[] { Long.valueOf(actualCurrent), Long.valueOf(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)) }));
/*     */         }
/*     */ 
/*     */         
/* 173 */         return next ^ System.nanoTime();
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
/*     */   ThreadLocalRandom() {
/* 205 */     super(newSeed());
/* 206 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadLocalRandom current() {
/* 215 */     return InternalThreadLocalMap.get().random();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/* 225 */     if (this.initialized) {
/* 226 */       throw new UnsupportedOperationException();
/*     */     }
/* 228 */     this.rnd = (seed ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
/*     */   }
/*     */   
/*     */   protected int next(int bits) {
/* 232 */     this.rnd = this.rnd * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
/* 233 */     return (int)(this.rnd >>> 48 - bits);
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
/*     */   public int nextInt(int least, int bound) {
/* 247 */     if (least >= bound) {
/* 248 */       throw new IllegalArgumentException();
/*     */     }
/* 250 */     return nextInt(bound - least) + least;
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
/*     */   public long nextLong(long n) {
/* 263 */     if (n <= 0L) {
/* 264 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     long offset = 0L;
/* 273 */     while (n >= 2147483647L) {
/* 274 */       int bits = next(2);
/* 275 */       long half = n >>> 1L;
/* 276 */       long nextn = ((bits & 0x2) == 0) ? half : (n - half);
/* 277 */       if ((bits & 0x1) == 0) {
/* 278 */         offset += n - nextn;
/*     */       }
/* 280 */       n = nextn;
/*     */     } 
/* 282 */     return offset + nextInt((int)n);
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
/*     */   public long nextLong(long least, long bound) {
/* 296 */     if (least >= bound) {
/* 297 */       throw new IllegalArgumentException();
/*     */     }
/* 299 */     return nextLong(bound - least) + least;
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
/*     */   public double nextDouble(double n) {
/* 312 */     if (n <= 0.0D) {
/* 313 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/* 315 */     return nextDouble() * n;
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
/*     */   public double nextDouble(double least, double bound) {
/* 329 */     if (least >= bound) {
/* 330 */       throw new IllegalArgumentException();
/*     */     }
/* 332 */     return nextDouble() * (bound - least) + least;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\ThreadLocalRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */