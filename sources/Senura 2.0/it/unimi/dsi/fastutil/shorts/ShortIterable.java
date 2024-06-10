/*    */ package it.unimi.dsi.fastutil.shorts;
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
/*    */ public interface ShortIterable
/*    */   extends Iterable<Short>
/*    */ {
/*    */   default void forEach(IntConsumer action) {
/* 72 */     Objects.requireNonNull(action);
/* 73 */     for (ShortIterator iterator = iterator(); iterator.hasNext();) {
/* 74 */       action.accept(iterator.nextShort());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEach(final Consumer<? super Short> action) {
/* 84 */     forEach(new IntConsumer() {
/*    */           public void accept(int key) {
/* 86 */             action.accept(Short.valueOf((short)key));
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   ShortIterator iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortIterable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */