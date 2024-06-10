/*    */ package it.unimi.dsi.fastutil.floats;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.SafeMath;
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
/*    */ public interface FloatConsumer
/*    */   extends Consumer<Float>, DoubleConsumer
/*    */ {
/*    */   @Deprecated
/*    */   default void accept(double t) {
/* 37 */     accept(SafeMath.safeDoubleToFloat(t));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void accept(Float t) {
/* 47 */     accept(t.floatValue());
/*    */   }
/*    */   default FloatConsumer andThen(FloatConsumer after) {
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
/*    */   default FloatConsumer andThen(DoubleConsumer after) {
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
/*    */   default Consumer<Float> andThen(Consumer<? super Float> after) {
/* 78 */     return super.andThen(after);
/*    */   }
/*    */   
/*    */   void accept(float paramFloat);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */