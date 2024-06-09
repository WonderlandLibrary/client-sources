/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.IllegalReferenceCountException;
/*     */ import io.netty.util.ReferenceCounted;
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
/*     */ public abstract class AbstractReferenceCountedByteBuf
/*     */   extends AbstractByteBuf
/*     */ {
/*     */   private static final AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> refCntUpdater;
/*     */   
/*     */   static {
/*  32 */     AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> updater = PlatformDependent.newAtomicIntegerFieldUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
/*     */     
/*  34 */     if (updater == null) {
/*  35 */       updater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
/*     */     }
/*  37 */     refCntUpdater = updater;
/*     */   }
/*     */   
/*  40 */   private volatile int refCnt = 1;
/*     */   
/*     */   protected AbstractReferenceCountedByteBuf(int maxCapacity) {
/*  43 */     super(maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int refCnt() {
/*  48 */     return this.refCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setRefCnt(int refCnt) {
/*  55 */     this.refCnt = refCnt;
/*     */   }
/*     */   
/*     */   public ByteBuf retain() {
/*     */     int refCnt;
/*     */     do {
/*  61 */       refCnt = this.refCnt;
/*  62 */       if (refCnt == 0) {
/*  63 */         throw new IllegalReferenceCountException(0, 1);
/*     */       }
/*  65 */       if (refCnt == Integer.MAX_VALUE) {
/*  66 */         throw new IllegalReferenceCountException(2147483647, 1);
/*     */       }
/*  68 */     } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt + 1));
/*     */ 
/*     */ 
/*     */     
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int increment) {
/*     */     int refCnt;
/*  77 */     if (increment <= 0) {
/*  78 */       throw new IllegalArgumentException("increment: " + increment + " (expected: > 0)");
/*     */     }
/*     */     
/*     */     do {
/*  82 */       refCnt = this.refCnt;
/*  83 */       if (refCnt == 0) {
/*  84 */         throw new IllegalReferenceCountException(0, increment);
/*     */       }
/*  86 */       if (refCnt > Integer.MAX_VALUE - increment) {
/*  87 */         throw new IllegalReferenceCountException(refCnt, increment);
/*     */       }
/*  89 */     } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt + increment));
/*     */ 
/*     */ 
/*     */     
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release() {
/*     */     while (true) {
/*  99 */       int refCnt = this.refCnt;
/* 100 */       if (refCnt == 0) {
/* 101 */         throw new IllegalReferenceCountException(0, -1);
/*     */       }
/*     */       
/* 104 */       if (refCntUpdater.compareAndSet(this, refCnt, refCnt - 1)) {
/* 105 */         if (refCnt == 1) {
/* 106 */           deallocate();
/* 107 */           return true;
/*     */         } 
/* 109 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release(int decrement) {
/* 116 */     if (decrement <= 0) {
/* 117 */       throw new IllegalArgumentException("decrement: " + decrement + " (expected: > 0)");
/*     */     }
/*     */     
/*     */     while (true) {
/* 121 */       int refCnt = this.refCnt;
/* 122 */       if (refCnt < decrement) {
/* 123 */         throw new IllegalReferenceCountException(refCnt, -decrement);
/*     */       }
/*     */       
/* 126 */       if (refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement)) {
/* 127 */         if (refCnt == decrement) {
/* 128 */           deallocate();
/* 129 */           return true;
/*     */         } 
/* 131 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void deallocate();
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\AbstractReferenceCountedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */