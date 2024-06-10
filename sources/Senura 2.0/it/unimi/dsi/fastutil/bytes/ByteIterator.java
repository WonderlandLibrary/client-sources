/*    */ package it.unimi.dsi.fastutil.bytes;
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
/*    */ public interface ByteIterator
/*    */   extends Iterator<Byte>
/*    */ {
/*    */   @Deprecated
/*    */   default Byte next() {
/* 42 */     return Byte.valueOf(nextByte());
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
/*    */   default void forEachRemaining(ByteConsumer action) {
/* 54 */     Objects.requireNonNull(action);
/* 55 */     while (hasNext()) {
/* 56 */       action.accept(nextByte());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEachRemaining(Consumer<? super Byte> action) {
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
/* 87 */       nextByte(); 
/* 88 */     return n - i - 1;
/*    */   }
/*    */   
/*    */   byte nextByte();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */