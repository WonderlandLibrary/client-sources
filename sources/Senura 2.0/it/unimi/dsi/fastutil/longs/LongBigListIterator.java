/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public interface LongBigListIterator
/*     */   extends LongBidirectionalIterator, BigListIterator<Long>
/*     */ {
/*     */   default void set(long k) {
/*  33 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void add(long k) {
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
/*     */   default void set(Long k) {
/*  52 */     set(k.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void add(Long k) {
/*  62 */     add(k.longValue());
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
/*  80 */       nextLong(); 
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
/*  99 */       previousLong(); 
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongBigListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */