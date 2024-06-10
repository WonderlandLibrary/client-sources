/*    */ package it.unimi.dsi.fastutil.doubles;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.DoubleConsumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface DoubleIterable
/*    */   extends Iterable<Double>
/*    */ {
/*    */   default void forEach(DoubleConsumer action) {
/* 72 */     Objects.requireNonNull(action);
/* 73 */     for (DoubleIterator iterator = iterator(); iterator.hasNext();) {
/* 74 */       action.accept(iterator.nextDouble());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEach(Consumer<? super Double> action) {
/* 84 */     Objects.requireNonNull(action); forEach(action::accept);
/*    */   }
/*    */   
/*    */   DoubleIterator iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleIterable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */