/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface BooleanBigListIterator
/*     */   extends BooleanBidirectionalIterator, BigListIterator<Boolean>
/*     */ {
/*     */   default void set(boolean k) {
/*  33 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void add(boolean k) {
/*  41 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void set(Boolean k) {
/*  52 */     set(k.booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void add(Boolean k) {
/*  62 */     add(k.booleanValue());
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
/*     */   default long skip(long n) {
/*  78 */     long i = n;
/*  79 */     while (i-- != 0L && hasNext())
/*  80 */       nextBoolean(); 
/*  81 */     return n - i - 1L;
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
/*     */   default long back(long n) {
/*  97 */     long i = n;
/*  98 */     while (i-- != 0L && hasPrevious())
/*  99 */       previousBoolean(); 
/* 100 */     return n - i - 1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default int skip(int n) {
/* 107 */     return SafeMath.safeLongToInt(skip(n));
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanBigListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */