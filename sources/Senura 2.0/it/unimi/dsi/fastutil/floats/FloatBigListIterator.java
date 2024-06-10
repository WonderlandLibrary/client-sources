/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public interface FloatBigListIterator
/*     */   extends FloatBidirectionalIterator, BigListIterator<Float>
/*     */ {
/*     */   default void set(float k) {
/*  33 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void add(float k) {
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
/*     */   default void set(Float k) {
/*  52 */     set(k.floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void add(Float k) {
/*  62 */     add(k.floatValue());
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
/*  80 */       nextFloat(); 
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
/*  99 */       previousFloat(); 
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatBigListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */