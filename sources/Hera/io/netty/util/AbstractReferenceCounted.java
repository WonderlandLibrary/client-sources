/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ public abstract class AbstractReferenceCounted
/*     */   implements ReferenceCounted
/*     */ {
/*     */   private static final AtomicIntegerFieldUpdater<AbstractReferenceCounted> refCntUpdater;
/*     */   
/*     */   static {
/*  30 */     AtomicIntegerFieldUpdater<AbstractReferenceCounted> updater = PlatformDependent.newAtomicIntegerFieldUpdater(AbstractReferenceCounted.class, "refCnt");
/*     */     
/*  32 */     if (updater == null) {
/*  33 */       updater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "refCnt");
/*     */     }
/*  35 */     refCntUpdater = updater;
/*     */   }
/*     */   
/*  38 */   private volatile int refCnt = 1;
/*     */ 
/*     */   
/*     */   public final int refCnt() {
/*  42 */     return this.refCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setRefCnt(int refCnt) {
/*  49 */     this.refCnt = refCnt;
/*     */   }
/*     */   
/*     */   public ReferenceCounted retain() {
/*     */     int refCnt;
/*     */     do {
/*  55 */       refCnt = this.refCnt;
/*  56 */       if (refCnt == 0) {
/*  57 */         throw new IllegalReferenceCountException(0, 1);
/*     */       }
/*  59 */       if (refCnt == Integer.MAX_VALUE) {
/*  60 */         throw new IllegalReferenceCountException(2147483647, 1);
/*     */       }
/*  62 */     } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt + 1));
/*     */ 
/*     */ 
/*     */     
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   public ReferenceCounted retain(int increment) {
/*     */     int refCnt;
/*  71 */     if (increment <= 0) {
/*  72 */       throw new IllegalArgumentException("increment: " + increment + " (expected: > 0)");
/*     */     }
/*     */     
/*     */     do {
/*  76 */       refCnt = this.refCnt;
/*  77 */       if (refCnt == 0) {
/*  78 */         throw new IllegalReferenceCountException(0, 1);
/*     */       }
/*  80 */       if (refCnt > Integer.MAX_VALUE - increment) {
/*  81 */         throw new IllegalReferenceCountException(refCnt, increment);
/*     */       }
/*  83 */     } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt + increment));
/*     */ 
/*     */ 
/*     */     
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release() {
/*     */     while (true) {
/*  93 */       int refCnt = this.refCnt;
/*  94 */       if (refCnt == 0) {
/*  95 */         throw new IllegalReferenceCountException(0, -1);
/*     */       }
/*     */       
/*  98 */       if (refCntUpdater.compareAndSet(this, refCnt, refCnt - 1)) {
/*  99 */         if (refCnt == 1) {
/* 100 */           deallocate();
/* 101 */           return true;
/*     */         } 
/* 103 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release(int decrement) {
/* 110 */     if (decrement <= 0) {
/* 111 */       throw new IllegalArgumentException("decrement: " + decrement + " (expected: > 0)");
/*     */     }
/*     */     
/*     */     while (true) {
/* 115 */       int refCnt = this.refCnt;
/* 116 */       if (refCnt < decrement) {
/* 117 */         throw new IllegalReferenceCountException(refCnt, -decrement);
/*     */       }
/*     */       
/* 120 */       if (refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement)) {
/* 121 */         if (refCnt == decrement) {
/* 122 */           deallocate();
/* 123 */           return true;
/*     */         } 
/* 125 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void deallocate();
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\AbstractReferenceCounted.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */