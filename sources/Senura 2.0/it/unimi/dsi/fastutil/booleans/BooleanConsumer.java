/*    */ package it.unimi.dsi.fastutil.booleans;
/*    */ 
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
/*    */ @FunctionalInterface
/*    */ public interface BooleanConsumer
/*    */   extends Consumer<Boolean>
/*    */ {
/*    */   @Deprecated
/*    */   default void accept(Boolean t) {
/* 37 */     accept(t.booleanValue());
/*    */   }
/*    */   default BooleanConsumer andThen(BooleanConsumer after) {
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
/*    */   default Consumer<Boolean> andThen(Consumer<? super Boolean> after) {
/* 54 */     return super.andThen(after);
/*    */   }
/*    */   
/*    */   void accept(boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */