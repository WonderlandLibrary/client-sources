/*    */ package it.unimi.dsi.fastutil.longs;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.PrimitiveIterator;
/*    */ import java.util.function.Consumer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface LongIterator
/*    */   extends PrimitiveIterator.OfLong
/*    */ {
/*    */   @Deprecated
/*    */   default Long next() {
/* 43 */     return Long.valueOf(nextLong());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEachRemaining(Consumer<? super Long> action) {
/* 53 */     Objects.requireNonNull(action); forEachRemaining(action::accept);
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
/*    */   default int skip(int n) {
/* 69 */     if (n < 0)
/* 70 */       throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 71 */     int i = n;
/* 72 */     while (i-- != 0 && hasNext())
/* 73 */       nextLong(); 
/* 74 */     return n - i - 1;
/*    */   }
/*    */   
/*    */   long nextLong();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */