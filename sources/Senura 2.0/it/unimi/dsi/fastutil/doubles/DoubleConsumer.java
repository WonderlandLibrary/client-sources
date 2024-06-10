/*    */ package it.unimi.dsi.fastutil.doubles;
/*    */ 
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
/*    */ @FunctionalInterface
/*    */ public interface DoubleConsumer
/*    */   extends Consumer<Double>, DoubleConsumer
/*    */ {
/*    */   @Deprecated
/*    */   default void accept(Double t) {
/* 36 */     accept(t.doubleValue());
/*    */   }
/*    */   
/*    */   default DoubleConsumer andThen(DoubleConsumer after) {
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
/*    */   default Consumer<Double> andThen(Consumer<? super Double> after) {
/* 54 */     return super.andThen(after);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */