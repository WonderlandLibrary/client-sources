/*    */ package it.unimi.dsi.fastutil.shorts;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.SafeMath;
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
/*    */ public interface ShortConsumer
/*    */   extends Consumer<Short>, IntConsumer
/*    */ {
/*    */   @Deprecated
/*    */   default void accept(int t) {
/* 37 */     accept(SafeMath.safeIntToShort(t));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void accept(Short t) {
/* 47 */     accept(t.shortValue());
/*    */   }
/*    */   default ShortConsumer andThen(ShortConsumer after) {
/* 50 */     Objects.requireNonNull(after);
/* 51 */     return t -> {
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
/*    */   default ShortConsumer andThen(IntConsumer after) {
/* 64 */     Objects.requireNonNull(after);
/* 65 */     return t -> {
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
/*    */   default Consumer<Short> andThen(Consumer<? super Short> after) {
/* 78 */     return super.andThen(after);
/*    */   }
/*    */   
/*    */   void accept(short paramShort);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */