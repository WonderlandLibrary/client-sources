/*    */ package it.unimi.dsi.fastutil.ints;
/*    */ 
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
/*    */ @FunctionalInterface
/*    */ public interface IntConsumer
/*    */   extends Consumer<Integer>, IntConsumer
/*    */ {
/*    */   @Deprecated
/*    */   default void accept(Integer t) {
/* 36 */     accept(t.intValue());
/*    */   }
/*    */   
/*    */   default IntConsumer andThen(IntConsumer after) {
/* 40 */     Objects.requireNonNull(after);
/* 41 */     return t -> {
/*    */         accept(t);
/*    */         after.accept(t);
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default Consumer<Integer> andThen(Consumer<? super Integer> after) {
/* 54 */     return super.andThen(after);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */