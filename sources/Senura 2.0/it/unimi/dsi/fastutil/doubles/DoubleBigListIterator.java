/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public interface DoubleBigListIterator
/*     */   extends DoubleBidirectionalIterator, BigListIterator<Double>
/*     */ {
/*     */   default void set(double k) {
/*  33 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void add(double k) {
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
/*     */   default void set(Double k) {
/*  52 */     set(k.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void add(Double k) {
/*  62 */     add(k.doubleValue());
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
/*  80 */       nextDouble(); 
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
/*  99 */       previousDouble(); 
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleBigListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */