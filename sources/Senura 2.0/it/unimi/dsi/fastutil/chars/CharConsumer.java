/*    */ package it.unimi.dsi.fastutil.chars;
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
/*    */ public interface CharConsumer
/*    */   extends Consumer<Character>, IntConsumer
/*    */ {
/*    */   @Deprecated
/*    */   default void accept(int t) {
/* 37 */     accept(SafeMath.safeIntToChar(t));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void accept(Character t) {
/* 47 */     accept(t.charValue());
/*    */   }
/*    */   default CharConsumer andThen(CharConsumer after) {
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
/*    */   default CharConsumer andThen(IntConsumer after) {
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
/*    */   default Consumer<Character> andThen(Consumer<? super Character> after) {
/* 78 */     return super.andThen(after);
/*    */   }
/*    */   
/*    */   void accept(char paramChar);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */