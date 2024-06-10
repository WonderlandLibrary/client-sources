/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReEntranceLock
/*     */ {
/*     */   private final int maxDepth;
/*  40 */   private int depth = 0;
/*     */ 
/*     */   
/*     */   private boolean semaphore = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock(int maxDepth) {
/*  48 */     this.maxDepth = maxDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDepth() {
/*  55 */     return this.maxDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  62 */     return this.depth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock push() {
/*  72 */     this.depth++;
/*  73 */     checkAndSet();
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock pop() {
/*  83 */     if (this.depth == 0) {
/*  84 */       throw new IllegalStateException("ReEntranceLock pop() with zero depth");
/*     */     }
/*     */     
/*  87 */     this.depth--;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean check() {
/*  97 */     return (this.depth > this.maxDepth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkAndSet() {
/* 106 */     return this.semaphore |= check();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock set() {
/* 115 */     this.semaphore = true;
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSet() {
/* 123 */     return this.semaphore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock clear() {
/* 132 */     this.semaphore = false;
/* 133 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\ReEntranceLock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */