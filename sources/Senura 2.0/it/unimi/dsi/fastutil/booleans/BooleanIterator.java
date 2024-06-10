/*    */ package it.unimi.dsi.fastutil.booleans;
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
/*    */ public interface BooleanIterator
/*    */   extends Iterator<Boolean>
/*    */ {
/*    */   @Deprecated
/*    */   default Boolean next() {
/* 42 */     return Boolean.valueOf(nextBoolean());
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
/*    */   default void forEachRemaining(BooleanConsumer action) {
/* 54 */     Objects.requireNonNull(action);
/* 55 */     while (hasNext()) {
/* 56 */       action.accept(nextBoolean());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEachRemaining(Consumer<? super Boolean> action) {
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
/* 87 */       nextBoolean(); 
/* 88 */     return n - i - 1;
/*    */   }
/*    */   
/*    */   boolean nextBoolean();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */