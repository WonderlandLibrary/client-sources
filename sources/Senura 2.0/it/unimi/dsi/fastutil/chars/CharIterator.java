/*    */ package it.unimi.dsi.fastutil.chars;
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
/*    */ public interface CharIterator
/*    */   extends Iterator<Character>
/*    */ {
/*    */   @Deprecated
/*    */   default Character next() {
/* 42 */     return Character.valueOf(nextChar());
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
/*    */   default void forEachRemaining(CharConsumer action) {
/* 54 */     Objects.requireNonNull(action);
/* 55 */     while (hasNext()) {
/* 56 */       action.accept(nextChar());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEachRemaining(Consumer<? super Character> action) {
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
/* 87 */       nextChar(); 
/* 88 */     return n - i - 1;
/*    */   }
/*    */   
/*    */   char nextChar();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */