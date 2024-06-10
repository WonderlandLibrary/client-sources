/*    */ package it.unimi.dsi.fastutil.ints;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.IntConsumer;
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
/*    */ public interface IntIterable
/*    */   extends Iterable<Integer>
/*    */ {
/*    */   default void forEach(IntConsumer action) {
/* 72 */     Objects.requireNonNull(action);
/* 73 */     for (IntIterator iterator = iterator(); iterator.hasNext();) {
/* 74 */       action.accept(iterator.nextInt());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEach(Consumer<? super Integer> action) {
/* 84 */     Objects.requireNonNull(action); forEach(action::accept);
/*    */   }
/*    */   
/*    */   IntIterator iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntIterable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */