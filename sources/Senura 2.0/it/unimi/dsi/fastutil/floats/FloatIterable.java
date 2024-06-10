/*    */ package it.unimi.dsi.fastutil.floats;
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
/*    */ public interface FloatIterable
/*    */   extends Iterable<Float>
/*    */ {
/*    */   default void forEach(DoubleConsumer action) {
/* 72 */     Objects.requireNonNull(action);
/* 73 */     for (FloatIterator iterator = iterator(); iterator.hasNext();) {
/* 74 */       action.accept(iterator.nextFloat());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEach(final Consumer<? super Float> action) {
/* 84 */     forEach(new DoubleConsumer() {
/*    */           public void accept(double key) {
/* 86 */             action.accept(Float.valueOf((float)key));
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   FloatIterator iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatIterable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */