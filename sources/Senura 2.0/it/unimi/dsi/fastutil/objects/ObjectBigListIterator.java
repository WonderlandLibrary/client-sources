/*    */ package it.unimi.dsi.fastutil.objects;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.BigListIterator;
/*    */ import it.unimi.dsi.fastutil.SafeMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ObjectBigListIterator<K>
/*    */   extends ObjectBidirectionalIterator<K>, BigListIterator<K>
/*    */ {
/*    */   default void set(K k) {
/* 34 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void add(K k) {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default long skip(long n) {
/* 59 */     long i = n;
/* 60 */     while (i-- != 0L && hasNext())
/* 61 */       next(); 
/* 62 */     return n - i - 1L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default long back(long n) {
/* 78 */     long i = n;
/* 79 */     while (i-- != 0L && hasPrevious())
/* 80 */       previous(); 
/* 81 */     return n - i - 1L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int skip(int n) {
/* 88 */     return SafeMath.safeLongToInt(skip(n));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectBigListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */