/*     */ package io.netty.util.internal.chmv8;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CountedCompleter<T>
/*     */   extends ForkJoinTask<T>
/*     */ {
/*     */   private static final long serialVersionUID = 5232453752276485070L;
/*     */   final CountedCompleter<?> completer;
/*     */   volatile int pending;
/*     */   private static final Unsafe U;
/*     */   private static final long PENDING;
/*     */   
/*     */   protected CountedCompleter(CountedCompleter<?> completer, int initialPendingCount) {
/* 418 */     this.completer = completer;
/* 419 */     this.pending = initialPendingCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CountedCompleter(CountedCompleter<?> completer) {
/* 429 */     this.completer = completer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CountedCompleter() {
/* 437 */     this.completer = null;
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
/*     */   public void onCompletion(CountedCompleter<?> caller) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onExceptionalCompletion(Throwable ex, CountedCompleter<?> caller) {
/* 479 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CountedCompleter<?> getCompleter() {
/* 489 */     return this.completer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getPendingCount() {
/* 498 */     return this.pending;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setPendingCount(int count) {
/* 507 */     this.pending = count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addToPendingCount(int delta) {
/*     */     int c;
/*     */     do {
/*     */     
/* 517 */     } while (!U.compareAndSwapInt(this, PENDING, c = this.pending, c + delta));
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
/*     */   public final boolean compareAndSetPendingCount(int expected, int count) {
/* 529 */     return U.compareAndSwapInt(this, PENDING, expected, count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int decrementPendingCountUnlessZero() {
/*     */     int c;
/*     */     do {
/*     */     
/* 540 */     } while ((c = this.pending) != 0 && !U.compareAndSwapInt(this, PENDING, c, c - 1));
/*     */     
/* 542 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CountedCompleter<?> getRoot() {
/* 552 */     CountedCompleter<?> a = this; CountedCompleter<?> p;
/* 553 */     while ((p = a.completer) != null)
/* 554 */       a = p; 
/* 555 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void tryComplete() {
/*     */     int c;
/* 565 */     CountedCompleter<?> a = this, s = a;
/*     */     do {
/* 567 */       while ((c = a.pending) == 0) {
/* 568 */         a.onCompletion(s);
/* 569 */         if ((a = (s = a).completer) == null) {
/* 570 */           s.quietlyComplete();
/*     */           return;
/*     */         } 
/*     */       } 
/* 574 */     } while (!U.compareAndSwapInt(a, PENDING, c, c - 1));
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
/*     */   public final void propagateCompletion() {
/*     */     int c;
/* 589 */     CountedCompleter<?> a = this, s = a;
/*     */     do {
/* 591 */       while ((c = a.pending) == 0) {
/* 592 */         if ((a = (s = a).completer) == null) {
/* 593 */           s.quietlyComplete();
/*     */           return;
/*     */         } 
/*     */       } 
/* 597 */     } while (!U.compareAndSwapInt(a, PENDING, c, c - 1));
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
/*     */   public void complete(T rawResult) {
/* 623 */     setRawResult(rawResult);
/* 624 */     onCompletion(this);
/* 625 */     quietlyComplete(); CountedCompleter<?> p;
/* 626 */     if ((p = this.completer) != null) {
/* 627 */       p.tryComplete();
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
/*     */   public final CountedCompleter<?> firstComplete() {
/*     */     while (true) {
/*     */       int c;
/* 641 */       if ((c = this.pending) == 0)
/* 642 */         return this; 
/* 643 */       if (U.compareAndSwapInt(this, PENDING, c, c - 1)) {
/* 644 */         return null;
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
/*     */   public final CountedCompleter<?> nextComplete() {
/*     */     CountedCompleter<?> p;
/* 667 */     if ((p = this.completer) != null) {
/* 668 */       return p.firstComplete();
/*     */     }
/* 670 */     quietlyComplete();
/* 671 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void quietlyCompleteRoot() {
/* 679 */     CountedCompleter<?> a = this; while (true) {
/* 680 */       CountedCompleter<?> p; if ((p = a.completer) == null) {
/* 681 */         a.quietlyComplete();
/*     */         return;
/*     */       } 
/* 684 */       a = p;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void internalPropagateException(Throwable ex) {
/* 692 */     CountedCompleter<?> a = this, s = a;
/*     */     
/* 694 */     while (a.onExceptionalCompletion(ex, s) && (a = (s = a).completer) != null && a.status >= 0 && a.recordExceptionalCompletion(ex) == Integer.MIN_VALUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean exec() {
/* 703 */     compute();
/* 704 */     return false;
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
/*     */   public T getRawResult() {
/* 716 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setRawResult(T t) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 732 */       U = getUnsafe();
/* 733 */       PENDING = U.objectFieldOffset(CountedCompleter.class.getDeclaredField("pending"));
/*     */     }
/* 735 */     catch (Exception e) {
/* 736 */       throw new Error(e);
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
/*     */   private static Unsafe getUnsafe() {
/*     */     try {
/* 749 */       return Unsafe.getUnsafe();
/* 750 */     } catch (SecurityException tryReflectionInstead) {
/*     */       try {
/* 752 */         return AccessController.<Unsafe>doPrivileged(new PrivilegedExceptionAction<Unsafe>()
/*     */             {
/*     */               public Unsafe run() throws Exception {
/* 755 */                 Class<Unsafe> k = Unsafe.class;
/* 756 */                 for (Field f : k.getDeclaredFields()) {
/* 757 */                   f.setAccessible(true);
/* 758 */                   Object x = f.get(null);
/* 759 */                   if (k.isInstance(x))
/* 760 */                     return k.cast(x); 
/*     */                 } 
/* 762 */                 throw new NoSuchFieldError("the Unsafe"); }
/*     */             });
/* 764 */       } catch (PrivilegedActionException e) {
/* 765 */         throw new RuntimeException("Could not initialize intrinsics", e.getCause());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void compute();
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\chmv8\CountedCompleter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */