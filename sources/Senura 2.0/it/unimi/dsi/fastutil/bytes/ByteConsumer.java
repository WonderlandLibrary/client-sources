/*    */ package it.unimi.dsi.fastutil.bytes;
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
/*    */ public interface ByteConsumer
/*    */   extends Consumer<Byte>, IntConsumer
/*    */ {
/*    */   @Deprecated
/*    */   default void accept(int t) {
/* 37 */     accept(SafeMath.safeIntToByte(t));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void accept(Byte t) {
/* 47 */     accept(t.byteValue());
/*    */   }
/*    */   default ByteConsumer andThen(ByteConsumer after) {
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
/*    */   default ByteConsumer andThen(IntConsumer after) {
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
/*    */   default Consumer<Byte> andThen(Consumer<? super Byte> after) {
/* 78 */     return super.andThen(after);
/*    */   }
/*    */   
/*    */   void accept(byte paramByte);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */