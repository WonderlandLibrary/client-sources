/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public interface ShortBigListIterator
/*     */   extends ShortBidirectionalIterator, BigListIterator<Short>
/*     */ {
/*     */   default void set(short k) {
/*  33 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void add(short k) {
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
/*     */   default void set(Short k) {
/*  52 */     set(k.shortValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void add(Short k) {
/*  62 */     add(k.shortValue());
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
/*  80 */       nextShort(); 
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
/*  99 */       previousShort(); 
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortBigListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */