/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public abstract class MultithreadEventExecutorGroup
/*     */   extends AbstractEventExecutorGroup
/*     */ {
/*     */   private final EventExecutor[] children;
/*  33 */   private final AtomicInteger childIndex = new AtomicInteger();
/*  34 */   private final AtomicInteger terminatedChildren = new AtomicInteger();
/*  35 */   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final EventExecutorChooser chooser;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
/*  46 */     if (nThreads <= 0) {
/*  47 */       throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", new Object[] { Integer.valueOf(nThreads) }));
/*     */     }
/*     */     
/*  50 */     if (threadFactory == null) {
/*  51 */       threadFactory = newDefaultThreadFactory();
/*     */     }
/*     */     
/*  54 */     this.children = (EventExecutor[])new SingleThreadEventExecutor[nThreads];
/*  55 */     if (isPowerOfTwo(this.children.length)) {
/*  56 */       this.chooser = new PowerOfTwoEventExecutorChooser();
/*     */     } else {
/*  58 */       this.chooser = new GenericEventExecutorChooser();
/*     */     } 
/*     */     
/*  61 */     for (int i = 0; i < nThreads; i++) {
/*  62 */       boolean success = false;
/*     */     }
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
/*  90 */     FutureListener<Object> terminationListener = new FutureListener()
/*     */       {
/*     */         public void operationComplete(Future<Object> future) throws Exception {
/*  93 */           if (MultithreadEventExecutorGroup.this.terminatedChildren.incrementAndGet() == MultithreadEventExecutorGroup.this.children.length) {
/*  94 */             MultithreadEventExecutorGroup.this.terminationFuture.setSuccess(null);
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*  99 */     for (EventExecutor e : this.children) {
/* 100 */       e.terminationFuture().addListener(terminationListener);
/*     */     }
/*     */   }
/*     */   
/*     */   protected ThreadFactory newDefaultThreadFactory() {
/* 105 */     return new DefaultThreadFactory(getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutor next() {
/* 110 */     return this.chooser.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventExecutor> iterator() {
/* 115 */     return children().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int executorCount() {
/* 123 */     return this.children.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Set<EventExecutor> children() {
/* 130 */     Set<EventExecutor> children = Collections.newSetFromMap(new LinkedHashMap<EventExecutor, Boolean>());
/* 131 */     Collections.addAll(children, this.children);
/* 132 */     return children;
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
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 145 */     for (EventExecutor l : this.children) {
/* 146 */       l.shutdownGracefully(quietPeriod, timeout, unit);
/*     */     }
/* 148 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 153 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 159 */     for (EventExecutor l : this.children) {
/* 160 */       l.shutdown();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 166 */     for (EventExecutor l : this.children) {
/* 167 */       if (!l.isShuttingDown()) {
/* 168 */         return false;
/*     */       }
/*     */     } 
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 176 */     for (EventExecutor l : this.children) {
/* 177 */       if (!l.isShutdown()) {
/* 178 */         return false;
/*     */       }
/*     */     } 
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 186 */     for (EventExecutor l : this.children) {
/* 187 */       if (!l.isTerminated()) {
/* 188 */         return false;
/*     */       }
/*     */     } 
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 197 */     long deadline = System.nanoTime() + unit.toNanos(timeout); EventExecutor[] arr$; int len$, i$;
/* 198 */     for (arr$ = this.children, len$ = arr$.length, i$ = 0; i$ < len$; ) { EventExecutor l = arr$[i$];
/*     */       while (true) {
/* 200 */         long timeLeft = deadline - System.nanoTime();
/* 201 */         if (timeLeft <= 0L) {
/*     */           break;
/*     */         }
/* 204 */         if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
/*     */           i$++;
/*     */         }
/*     */       }  }
/*     */     
/* 209 */     return isTerminated();
/*     */   }
/*     */   
/*     */   private static boolean isPowerOfTwo(int val) {
/* 213 */     return ((val & -val) == val);
/*     */   }
/*     */   protected abstract EventExecutor newChild(ThreadFactory paramThreadFactory, Object... paramVarArgs) throws Exception;
/*     */   
/*     */   private static interface EventExecutorChooser {
/*     */     EventExecutor next(); }
/*     */   
/*     */   private final class PowerOfTwoEventExecutorChooser implements EventExecutorChooser { private PowerOfTwoEventExecutorChooser() {}
/*     */     
/*     */     public EventExecutor next() {
/* 223 */       return MultithreadEventExecutorGroup.this.children[MultithreadEventExecutorGroup.this.childIndex.getAndIncrement() & MultithreadEventExecutorGroup.this.children.length - 1];
/*     */     } }
/*     */   
/*     */   private final class GenericEventExecutorChooser implements EventExecutorChooser {
/*     */     private GenericEventExecutorChooser() {}
/*     */     
/*     */     public EventExecutor next() {
/* 230 */       return MultithreadEventExecutorGroup.this.children[Math.abs(MultithreadEventExecutorGroup.this.childIndex.getAndIncrement() % MultithreadEventExecutorGroup.this.children.length)];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\MultithreadEventExecutorGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */