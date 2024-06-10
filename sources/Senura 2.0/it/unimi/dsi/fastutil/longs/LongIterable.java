/*    */ package it.unimi.dsi.fastutil.longs;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.LongConsumer;
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
/*    */ public interface LongIterable
/*    */   extends Iterable<Long>
/*    */ {
/*    */   default void forEach(LongConsumer action) {
/* 72 */     Objects.requireNonNull(action);
/* 73 */     for (LongIterator iterator = iterator(); iterator.hasNext();) {
/* 74 */       action.accept(iterator.nextLong());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEach(Consumer<? super Long> action) {
/* 84 */     Objects.requireNonNull(action); forEach(action::accept);
/*    */   }
/*    */   
/*    */   LongIterator iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongIterable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */