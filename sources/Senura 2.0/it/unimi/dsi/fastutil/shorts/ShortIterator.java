/*    */ package it.unimi.dsi.fastutil.shorts;
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
/*    */ public interface ShortIterator
/*    */   extends Iterator<Short>
/*    */ {
/*    */   @Deprecated
/*    */   default Short next() {
/* 42 */     return Short.valueOf(nextShort());
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
/*    */   default void forEachRemaining(ShortConsumer action) {
/* 54 */     Objects.requireNonNull(action);
/* 55 */     while (hasNext()) {
/* 56 */       action.accept(nextShort());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEachRemaining(Consumer<? super Short> action) {
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
/* 87 */       nextShort(); 
/* 88 */     return n - i - 1;
/*    */   }
/*    */   
/*    */   short nextShort();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */