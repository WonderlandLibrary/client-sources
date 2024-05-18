/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.Signal;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.concurrent.CancellationException;
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
/*     */ public class DefaultPromise<V>
/*     */   extends AbstractFuture<V>
/*     */   implements Promise<V>
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
/*  35 */   private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
/*     */   
/*     */   private static final int MAX_LISTENER_STACK_DEPTH = 8;
/*     */   
/*  39 */   private static final Signal SUCCESS = Signal.valueOf(DefaultPromise.class.getName() + ".SUCCESS");
/*  40 */   private static final Signal UNCANCELLABLE = Signal.valueOf(DefaultPromise.class.getName() + ".UNCANCELLABLE");
/*  41 */   private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(new CancellationException());
/*     */   
/*     */   static {
/*  44 */     CANCELLATION_CAUSE_HOLDER.cause.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final EventExecutor executor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Object result;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object listeners;
/*     */ 
/*     */ 
/*     */   
/*     */   private LateListeners lateListeners;
/*     */ 
/*     */ 
/*     */   
/*     */   private short waiters;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPromise(EventExecutor executor) {
/*  75 */     if (executor == null) {
/*  76 */       throw new NullPointerException("executor");
/*     */     }
/*  78 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DefaultPromise() {
/*  83 */     this.executor = null;
/*     */   }
/*     */   
/*     */   protected EventExecutor executor() {
/*  87 */     return this.executor;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/*  92 */     return isCancelled0(this.result);
/*     */   }
/*     */   
/*     */   private static boolean isCancelled0(Object result) {
/*  96 */     return (result instanceof CauseHolder && ((CauseHolder)result).cause instanceof CancellationException);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancellable() {
/* 101 */     return (this.result == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 106 */     return isDone0(this.result);
/*     */   }
/*     */   
/*     */   private static boolean isDone0(Object result) {
/* 110 */     return (result != null && result != UNCANCELLABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSuccess() {
/* 115 */     Object result = this.result;
/* 116 */     if (result == null || result == UNCANCELLABLE) {
/* 117 */       return false;
/*     */     }
/* 119 */     return !(result instanceof CauseHolder);
/*     */   }
/*     */ 
/*     */   
/*     */   public Throwable cause() {
/* 124 */     Object result = this.result;
/* 125 */     if (result instanceof CauseHolder) {
/* 126 */       return ((CauseHolder)result).cause;
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
/* 133 */     if (listener == null) {
/* 134 */       throw new NullPointerException("listener");
/*     */     }
/*     */     
/* 137 */     if (isDone()) {
/* 138 */       notifyLateListener(listener);
/* 139 */       return this;
/*     */     } 
/*     */     
/* 142 */     synchronized (this) {
/* 143 */       if (!isDone()) {
/* 144 */         if (this.listeners == null) {
/* 145 */           this.listeners = listener;
/*     */         }
/* 147 */         else if (this.listeners instanceof DefaultFutureListeners) {
/* 148 */           ((DefaultFutureListeners)this.listeners).add(listener);
/*     */         } else {
/* 150 */           GenericFutureListener<? extends Future<V>> firstListener = (GenericFutureListener<? extends Future<V>>)this.listeners;
/*     */           
/* 152 */           this.listeners = new DefaultFutureListeners(firstListener, listener);
/*     */         } 
/*     */         
/* 155 */         return this;
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     notifyLateListener(listener);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
/* 165 */     if (listeners == null) {
/* 166 */       throw new NullPointerException("listeners");
/*     */     }
/*     */     
/* 169 */     for (GenericFutureListener<? extends Future<? super V>> l : listeners) {
/* 170 */       if (l == null) {
/*     */         break;
/*     */       }
/* 173 */       addListener(l);
/*     */     } 
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
/* 180 */     if (listener == null) {
/* 181 */       throw new NullPointerException("listener");
/*     */     }
/*     */     
/* 184 */     if (isDone()) {
/* 185 */       return this;
/*     */     }
/*     */     
/* 188 */     synchronized (this) {
/* 189 */       if (!isDone()) {
/* 190 */         if (this.listeners instanceof DefaultFutureListeners) {
/* 191 */           ((DefaultFutureListeners)this.listeners).remove(listener);
/* 192 */         } else if (this.listeners == listener) {
/* 193 */           this.listeners = null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
/* 203 */     if (listeners == null) {
/* 204 */       throw new NullPointerException("listeners");
/*     */     }
/*     */     
/* 207 */     for (GenericFutureListener<? extends Future<? super V>> l : listeners) {
/* 208 */       if (l == null) {
/*     */         break;
/*     */       }
/* 211 */       removeListener(l);
/*     */     } 
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> sync() throws InterruptedException {
/* 218 */     await();
/* 219 */     rethrowIfFailed();
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> syncUninterruptibly() {
/* 225 */     awaitUninterruptibly();
/* 226 */     rethrowIfFailed();
/* 227 */     return this;
/*     */   }
/*     */   
/*     */   private void rethrowIfFailed() {
/* 231 */     Throwable cause = cause();
/* 232 */     if (cause == null) {
/*     */       return;
/*     */     }
/*     */     
/* 236 */     PlatformDependent.throwException(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> await() throws InterruptedException {
/* 241 */     if (isDone()) {
/* 242 */       return this;
/*     */     }
/*     */     
/* 245 */     if (Thread.interrupted()) {
/* 246 */       throw new InterruptedException(toString());
/*     */     }
/*     */     
/* 249 */     synchronized (this) {
/* 250 */       while (!isDone()) {
/* 251 */         checkDeadLock();
/* 252 */         incWaiters();
/*     */         try {
/* 254 */           wait();
/*     */         } finally {
/* 256 */           decWaiters();
/*     */         } 
/*     */       } 
/*     */     } 
/* 260 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
/* 266 */     return await0(unit.toNanos(timeout), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean await(long timeoutMillis) throws InterruptedException {
/* 271 */     return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> awaitUninterruptibly() {
/* 276 */     if (isDone()) {
/* 277 */       return this;
/*     */     }
/*     */     
/* 280 */     boolean interrupted = false;
/* 281 */     synchronized (this) {
/* 282 */       while (!isDone()) {
/* 283 */         checkDeadLock();
/* 284 */         incWaiters();
/*     */         try {
/* 286 */           wait();
/* 287 */         } catch (InterruptedException e) {
/*     */           
/* 289 */           interrupted = true;
/*     */         } finally {
/* 291 */           decWaiters();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     if (interrupted) {
/* 297 */       Thread.currentThread().interrupt();
/*     */     }
/*     */     
/* 300 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
/*     */     try {
/* 306 */       return await0(unit.toNanos(timeout), false);
/* 307 */     } catch (InterruptedException e) {
/*     */       
/* 309 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeoutMillis) {
/*     */     try {
/* 316 */       return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
/* 317 */     } catch (InterruptedException e) {
/*     */       
/* 319 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
/* 324 */     if (isDone()) {
/* 325 */       return true;
/*     */     }
/*     */     
/* 328 */     if (timeoutNanos <= 0L) {
/* 329 */       return isDone();
/*     */     }
/*     */     
/* 332 */     if (interruptable && Thread.interrupted()) {
/* 333 */       throw new InterruptedException(toString());
/*     */     }
/*     */     
/* 336 */     long startTime = System.nanoTime();
/* 337 */     long waitTime = timeoutNanos;
/* 338 */     boolean interrupted = false;
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
/*     */     try {
/*     */     
/*     */     } finally {
/* 378 */       if (interrupted) {
/* 379 */         Thread.currentThread().interrupt();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkDeadLock() {
/* 388 */     EventExecutor e = executor();
/* 389 */     if (e != null && e.inEventLoop()) {
/* 390 */       throw new BlockingOperationException(toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> setSuccess(V result) {
/* 396 */     if (setSuccess0(result)) {
/* 397 */       notifyListeners();
/* 398 */       return this;
/*     */     } 
/* 400 */     throw new IllegalStateException("complete already: " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trySuccess(V result) {
/* 405 */     if (setSuccess0(result)) {
/* 406 */       notifyListeners();
/* 407 */       return true;
/*     */     } 
/* 409 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> setFailure(Throwable cause) {
/* 414 */     if (setFailure0(cause)) {
/* 415 */       notifyListeners();
/* 416 */       return this;
/*     */     } 
/* 418 */     throw new IllegalStateException("complete already: " + this, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryFailure(Throwable cause) {
/* 423 */     if (setFailure0(cause)) {
/* 424 */       notifyListeners();
/* 425 */       return true;
/*     */     } 
/* 427 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 432 */     Object result = this.result;
/* 433 */     if (isDone0(result) || result == UNCANCELLABLE) {
/* 434 */       return false;
/*     */     }
/*     */     
/* 437 */     synchronized (this) {
/*     */       
/* 439 */       result = this.result;
/* 440 */       if (isDone0(result) || result == UNCANCELLABLE) {
/* 441 */         return false;
/*     */       }
/*     */       
/* 444 */       this.result = CANCELLATION_CAUSE_HOLDER;
/* 445 */       if (hasWaiters()) {
/* 446 */         notifyAll();
/*     */       }
/*     */     } 
/*     */     
/* 450 */     notifyListeners();
/* 451 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setUncancellable() {
/* 456 */     Object result = this.result;
/* 457 */     if (isDone0(result)) {
/* 458 */       return !isCancelled0(result);
/*     */     }
/*     */     
/* 461 */     synchronized (this) {
/*     */       
/* 463 */       result = this.result;
/* 464 */       if (isDone0(result)) {
/* 465 */         return !isCancelled0(result);
/*     */       }
/*     */       
/* 468 */       this.result = UNCANCELLABLE;
/*     */     } 
/* 470 */     return true;
/*     */   }
/*     */   
/*     */   private boolean setFailure0(Throwable cause) {
/* 474 */     if (cause == null) {
/* 475 */       throw new NullPointerException("cause");
/*     */     }
/*     */     
/* 478 */     if (isDone()) {
/* 479 */       return false;
/*     */     }
/*     */     
/* 482 */     synchronized (this) {
/*     */       
/* 484 */       if (isDone()) {
/* 485 */         return false;
/*     */       }
/*     */       
/* 488 */       this.result = new CauseHolder(cause);
/* 489 */       if (hasWaiters()) {
/* 490 */         notifyAll();
/*     */       }
/*     */     } 
/* 493 */     return true;
/*     */   }
/*     */   
/*     */   private boolean setSuccess0(V result) {
/* 497 */     if (isDone()) {
/* 498 */       return false;
/*     */     }
/*     */     
/* 501 */     synchronized (this) {
/*     */       
/* 503 */       if (isDone()) {
/* 504 */         return false;
/*     */       }
/* 506 */       if (result == null) {
/* 507 */         this.result = SUCCESS;
/*     */       } else {
/* 509 */         this.result = result;
/*     */       } 
/* 511 */       if (hasWaiters()) {
/* 512 */         notifyAll();
/*     */       }
/*     */     } 
/* 515 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V getNow() {
/* 521 */     Object result = this.result;
/* 522 */     if (result instanceof CauseHolder || result == SUCCESS) {
/* 523 */       return null;
/*     */     }
/* 525 */     return (V)result;
/*     */   }
/*     */   
/*     */   private boolean hasWaiters() {
/* 529 */     return (this.waiters > 0);
/*     */   }
/*     */   
/*     */   private void incWaiters() {
/* 533 */     if (this.waiters == Short.MAX_VALUE) {
/* 534 */       throw new IllegalStateException("too many waiters: " + this);
/*     */     }
/* 536 */     this.waiters = (short)(this.waiters + 1);
/*     */   }
/*     */   
/*     */   private void decWaiters() {
/* 540 */     this.waiters = (short)(this.waiters - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyListeners() {
/* 550 */     Object listeners = this.listeners;
/* 551 */     if (listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 555 */     EventExecutor executor = executor();
/* 556 */     if (executor.inEventLoop()) {
/* 557 */       InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
/* 558 */       int stackDepth = threadLocals.futureListenerStackDepth();
/* 559 */       if (stackDepth < 8) {
/* 560 */         threadLocals.setFutureListenerStackDepth(stackDepth + 1);
/*     */         try {
/* 562 */           if (listeners instanceof DefaultFutureListeners) {
/* 563 */             notifyListeners0(this, (DefaultFutureListeners)listeners);
/*     */           } else {
/* 565 */             final GenericFutureListener<? extends Future<V>> l = (GenericFutureListener<? extends Future<V>>)listeners;
/*     */             
/* 567 */             notifyListener0(this, l);
/*     */           } 
/*     */         } finally {
/* 570 */           this.listeners = null;
/* 571 */           threadLocals.setFutureListenerStackDepth(stackDepth);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 577 */     if (listeners instanceof DefaultFutureListeners) {
/* 578 */       final DefaultFutureListeners dfl = (DefaultFutureListeners)listeners;
/* 579 */       execute(executor, new Runnable()
/*     */           {
/*     */             public void run() {
/* 582 */               DefaultPromise.notifyListeners0(DefaultPromise.this, dfl);
/* 583 */               DefaultPromise.this.listeners = null;
/*     */             }
/*     */           });
/*     */     } else {
/* 587 */       final GenericFutureListener<? extends Future<V>> l = (GenericFutureListener<? extends Future<V>>)listeners;
/*     */       
/* 589 */       execute(executor, new Runnable()
/*     */           {
/*     */             public void run() {
/* 592 */               DefaultPromise.notifyListener0(DefaultPromise.this, l);
/* 593 */               DefaultPromise.this.listeners = null;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void notifyListeners0(Future<?> future, DefaultFutureListeners listeners) {
/* 600 */     GenericFutureListener[] arrayOfGenericFutureListener = (GenericFutureListener[])listeners.listeners();
/* 601 */     int size = listeners.size();
/* 602 */     for (int i = 0; i < size; i++) {
/* 603 */       notifyListener0(future, arrayOfGenericFutureListener[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyLateListener(GenericFutureListener<?> l) {
/* 613 */     EventExecutor executor = executor();
/* 614 */     if (executor.inEventLoop()) {
/* 615 */       if (this.listeners == null && this.lateListeners == null) {
/* 616 */         InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
/* 617 */         int stackDepth = threadLocals.futureListenerStackDepth();
/* 618 */         if (stackDepth < 8) {
/* 619 */           threadLocals.setFutureListenerStackDepth(stackDepth + 1);
/*     */           try {
/* 621 */             notifyListener0(this, l);
/*     */           } finally {
/* 623 */             threadLocals.setFutureListenerStackDepth(stackDepth);
/*     */           } 
/*     */           return;
/*     */         } 
/*     */       } else {
/* 628 */         LateListeners lateListeners = this.lateListeners;
/* 629 */         if (lateListeners == null) {
/* 630 */           this.lateListeners = lateListeners = new LateListeners();
/*     */         }
/* 632 */         lateListeners.add(l);
/* 633 */         execute(executor, lateListeners);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 641 */     execute(executor, new LateListenerNotifier(l));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void notifyListener(EventExecutor eventExecutor, final Future<?> future, final GenericFutureListener<?> l) {
/* 647 */     if (eventExecutor.inEventLoop()) {
/* 648 */       InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
/* 649 */       int stackDepth = threadLocals.futureListenerStackDepth();
/* 650 */       if (stackDepth < 8) {
/* 651 */         threadLocals.setFutureListenerStackDepth(stackDepth + 1);
/*     */         try {
/* 653 */           notifyListener0(future, l);
/*     */         } finally {
/* 655 */           threadLocals.setFutureListenerStackDepth(stackDepth);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 661 */     execute(eventExecutor, new Runnable()
/*     */         {
/*     */           public void run() {
/* 664 */             DefaultPromise.notifyListener0(future, l);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static void execute(EventExecutor executor, Runnable task) {
/*     */     try {
/* 671 */       executor.execute(task);
/* 672 */     } catch (Throwable t) {
/* 673 */       rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void notifyListener0(Future future, GenericFutureListener<Future> l) {
/*     */     try {
/* 680 */       l.operationComplete(future);
/* 681 */     } catch (Throwable t) {
/* 682 */       if (logger.isWarnEnabled()) {
/* 683 */         logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", t);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Object progressiveListeners() {
/* 693 */     Object listeners = this.listeners;
/* 694 */     if (listeners == null)
/*     */     {
/* 696 */       return null;
/*     */     }
/*     */     
/* 699 */     if (listeners instanceof DefaultFutureListeners) {
/*     */       
/* 701 */       DefaultFutureListeners dfl = (DefaultFutureListeners)listeners;
/* 702 */       int progressiveSize = dfl.progressiveSize();
/* 703 */       switch (progressiveSize) {
/*     */         case 0:
/* 705 */           return null;
/*     */         case 1:
/* 707 */           for (GenericFutureListener<?> l : dfl.listeners()) {
/* 708 */             if (l instanceof GenericProgressiveFutureListener) {
/* 709 */               return l;
/*     */             }
/*     */           } 
/* 712 */           return null;
/*     */       } 
/*     */       
/* 715 */       GenericFutureListener[] arrayOfGenericFutureListener = (GenericFutureListener[])dfl.listeners();
/* 716 */       GenericProgressiveFutureListener[] arrayOfGenericProgressiveFutureListener = new GenericProgressiveFutureListener[progressiveSize];
/* 717 */       for (int i = 0, j = 0; j < progressiveSize; i++) {
/* 718 */         GenericFutureListener<?> l = arrayOfGenericFutureListener[i];
/* 719 */         if (l instanceof GenericProgressiveFutureListener) {
/* 720 */           arrayOfGenericProgressiveFutureListener[j++] = (GenericProgressiveFutureListener)l;
/*     */         }
/*     */       } 
/*     */       
/* 724 */       return arrayOfGenericProgressiveFutureListener;
/* 725 */     }  if (listeners instanceof GenericProgressiveFutureListener) {
/* 726 */       return listeners;
/*     */     }
/*     */     
/* 729 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void notifyProgressiveListeners(final long progress, final long total) {
/* 735 */     Object listeners = progressiveListeners();
/* 736 */     if (listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 740 */     final ProgressiveFuture<V> self = (ProgressiveFuture<V>)this;
/*     */     
/* 742 */     EventExecutor executor = executor();
/* 743 */     if (executor.inEventLoop()) {
/* 744 */       if (listeners instanceof GenericProgressiveFutureListener[]) {
/* 745 */         notifyProgressiveListeners0(self, (GenericProgressiveFutureListener<?>[])listeners, progress, total);
/*     */       } else {
/*     */         
/* 748 */         notifyProgressiveListener0(self, (GenericProgressiveFutureListener)listeners, progress, total);
/*     */       }
/*     */     
/*     */     }
/* 752 */     else if (listeners instanceof GenericProgressiveFutureListener[]) {
/* 753 */       final GenericProgressiveFutureListener[] array = (GenericProgressiveFutureListener[])listeners;
/*     */       
/* 755 */       execute(executor, new Runnable()
/*     */           {
/*     */             public void run() {
/* 758 */               DefaultPromise.notifyProgressiveListeners0(self, (GenericProgressiveFutureListener<?>[])array, progress, total);
/*     */             }
/*     */           });
/*     */     } else {
/* 762 */       final GenericProgressiveFutureListener<ProgressiveFuture<V>> l = (GenericProgressiveFutureListener<ProgressiveFuture<V>>)listeners;
/*     */       
/* 764 */       execute(executor, new Runnable()
/*     */           {
/*     */             public void run() {
/* 767 */               DefaultPromise.notifyProgressiveListener0(self, l, progress, total);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void notifyProgressiveListeners0(ProgressiveFuture<?> future, GenericProgressiveFutureListener<?>[] listeners, long progress, long total) {
/* 776 */     for (GenericProgressiveFutureListener<?> l : listeners) {
/* 777 */       if (l == null) {
/*     */         break;
/*     */       }
/* 780 */       notifyProgressiveListener0(future, l, progress, total);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void notifyProgressiveListener0(ProgressiveFuture future, GenericProgressiveFutureListener<ProgressiveFuture> l, long progress, long total) {
/*     */     try {
/* 788 */       l.operationProgressed(future, progress, total);
/* 789 */     } catch (Throwable t) {
/* 790 */       if (logger.isWarnEnabled())
/* 791 */         logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationProgressed()", t); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class CauseHolder {
/*     */     final Throwable cause;
/*     */     
/*     */     CauseHolder(Throwable cause) {
/* 799 */       this.cause = cause;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 805 */     return toStringBuilder().toString();
/*     */   }
/*     */   
/*     */   protected StringBuilder toStringBuilder() {
/* 809 */     StringBuilder buf = new StringBuilder(64);
/* 810 */     buf.append(StringUtil.simpleClassName(this));
/* 811 */     buf.append('@');
/* 812 */     buf.append(Integer.toHexString(hashCode()));
/*     */     
/* 814 */     Object result = this.result;
/* 815 */     if (result == SUCCESS) {
/* 816 */       buf.append("(success)");
/* 817 */     } else if (result == UNCANCELLABLE) {
/* 818 */       buf.append("(uncancellable)");
/* 819 */     } else if (result instanceof CauseHolder) {
/* 820 */       buf.append("(failure(");
/* 821 */       buf.append(((CauseHolder)result).cause);
/* 822 */       buf.append(')');
/*     */     } else {
/* 824 */       buf.append("(incomplete)");
/*     */     } 
/* 826 */     return buf;
/*     */   }
/*     */   
/*     */   private final class LateListeners
/*     */     extends ArrayDeque<GenericFutureListener<?>> implements Runnable {
/*     */     private static final long serialVersionUID = -687137418080392244L;
/*     */     
/*     */     LateListeners() {
/* 834 */       super(2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 839 */       if (DefaultPromise.this.listeners == null) {
/*     */         while (true) {
/* 841 */           GenericFutureListener<?> l = poll();
/* 842 */           if (l == null) {
/*     */             break;
/*     */           }
/* 845 */           DefaultPromise.notifyListener0(DefaultPromise.this, l);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 850 */         DefaultPromise.execute(DefaultPromise.this.executor(), this);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class LateListenerNotifier implements Runnable {
/*     */     private GenericFutureListener<?> l;
/*     */     
/*     */     LateListenerNotifier(GenericFutureListener<?> l) {
/* 859 */       this.l = l;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 864 */       DefaultPromise<V>.LateListeners lateListeners = DefaultPromise.this.lateListeners;
/* 865 */       if (this.l != null) {
/* 866 */         if (lateListeners == null) {
/* 867 */           DefaultPromise.this.lateListeners = lateListeners = new DefaultPromise.LateListeners();
/*     */         }
/* 869 */         lateListeners.add(this.l);
/* 870 */         this.l = null;
/*     */       } 
/*     */       
/* 873 */       lateListeners.run();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\DefaultPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */