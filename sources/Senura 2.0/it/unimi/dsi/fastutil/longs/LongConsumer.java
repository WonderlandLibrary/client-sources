/*    */ package it.unimi.dsi.fastutil.longs;
/*    */ 
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
/*    */ @FunctionalInterface
/*    */ public interface LongConsumer
/*    */   extends Consumer<Long>, LongConsumer
/*    */ {
/*    */   @Deprecated
/*    */   default void accept(Long t) {
/* 36 */     accept(t.longValue());
/*    */   }
/*    */   
/*    */   default LongConsumer andThen(LongConsumer after) {
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
/*    */   default Consumer<Long> andThen(Consumer<? super Long> after) {
/* 54 */     return super.andThen(after);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */