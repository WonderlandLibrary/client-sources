/*    */ package it.unimi.dsi.fastutil.chars;
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
/*    */ public interface CharIterable
/*    */   extends Iterable<Character>
/*    */ {
/*    */   default void forEach(IntConsumer action) {
/* 72 */     Objects.requireNonNull(action);
/* 73 */     for (CharIterator iterator = iterator(); iterator.hasNext();) {
/* 74 */       action.accept(iterator.nextChar());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEach(final Consumer<? super Character> action) {
/* 84 */     forEach(new IntConsumer() {
/*    */           public void accept(int key) {
/* 86 */             action.accept(Character.valueOf((char)key));
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   CharIterator iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharIterable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */