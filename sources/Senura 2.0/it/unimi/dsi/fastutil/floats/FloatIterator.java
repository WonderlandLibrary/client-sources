/*    */ package it.unimi.dsi.fastutil.floats;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Objects;
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
/*    */ public interface FloatIterator
/*    */   extends Iterator<Float>
/*    */ {
/*    */   @Deprecated
/*    */   default Float next() {
/* 42 */     return Float.valueOf(nextFloat());
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
/*    */   default void forEachRemaining(FloatConsumer action) {
/* 54 */     Objects.requireNonNull(action);
/* 55 */     while (hasNext()) {
/* 56 */       action.accept(nextFloat());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEachRemaining(Consumer<? super Float> action) {
/* 67 */     Objects.requireNonNull(action); forEachRemaining(action::accept);
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
/* 83 */     if (n < 0)
/* 84 */       throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 85 */     int i = n;
/* 86 */     while (i-- != 0 && hasNext())
/* 87 */       nextFloat(); 
/* 88 */     return n - i - 1;
/*    */   }
/*    */   
/*    */   float nextFloat();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */